/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unlimited.oj.webapp.filter;

import com.unlimited.oj.dao.support.Page;
import com.unlimited.oj.util.*;
import com.unlimited.oj.util.Tool;
import java.util.*;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.constructs.blocking.BlockingCache;
import net.sf.ehcache.constructs.blocking.LockTimeoutException;
import net.sf.ehcache.constructs.web.AlreadyCommittedException;
import net.sf.ehcache.constructs.web.AlreadyGzippedException;
import net.sf.ehcache.constructs.web.PageInfo;
import net.sf.ehcache.constructs.web.filter.FilterNonReentrantException;
import net.sf.ehcache.constructs.web.filter.SimplePageCachingFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import java.util.logging.Logger;
import java.util.logging.Level;
import net.sf.ehcache.constructs.web.filter.CachingFilter;

/**
 *
 * @author benQ
 */
public class UojSimplePageCachingFilter extends SimplePageCachingFilter
{

    private static final Logger LOG = Logger.getLogger(CachingFilter.class.getName());
    private final static transient Log log = LogFactory.getLog(UojSimplePageCachingFilter.class);
    private static BlockingCache thisCache = null;
    private static String language = "zh";

    public static String generateKey(HttpServletRequest httpRequest, String originalKey, String format)
    {
        String newKey;
        if (format == null || format.equals(""))
            newKey = originalKey;
        else
        {
            newKey = format;
            if (httpRequest != null)
            {
                newKey = Tool.StringsReplace(newKey, "[SESSION]", httpRequest.getSession().getId());
                newKey = Tool.StringsReplace(newKey, "[USER]", httpRequest.getRemoteUser());
            }

            int nPos1, nPos2 = -1;
            String queryKey, queryValue;
            while ((nPos1 = newKey.indexOf('{', nPos2 + 1)) >= 0)
            {
                nPos2 = newKey.indexOf('}', nPos1);
                if (nPos2 < 0)
                    break;
                queryKey = newKey.substring(nPos1 + 1, nPos2);
                if (httpRequest != null && httpRequest.getParameter(queryKey) != null)
                    queryValue = httpRequest.getParameter(queryKey);
                else
                    queryValue = "";
                newKey = Tool.StringsReplace(newKey, newKey.substring(nPos1, nPos2 + 1), queryValue);
            }
        }

        newKey = "[" + LocaleFilter.locale_language + "]" + newKey;
        return newKey;
    }

    @Override
    protected String calculateKey(HttpServletRequest httpRequest)
    {
        String superkey = httpRequest.getRequestURI() +
                (httpRequest.getQueryString() != null ? "?" + httpRequest.getQueryString() : "");
        String newKey = superkey;
        List<CacheItem> cacheList = CacheType.getAllCache();
        if (cacheList != null)
        {
            for (CacheItem item : cacheList)
            {
                if (superkey.matches(item.getKey()))
                {
                    newKey = generateKey(httpRequest, superkey, item.getFormat());
                    break;
                }
            }
        }
        log.debug("Ehcache Key=" + newKey);
        return newKey;
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws AlreadyGzippedException, AlreadyCommittedException, FilterNonReentrantException, LockTimeoutException, Exception
    {
        if (thisCache == null)
            thisCache = blockingCache;
        List<CacheItem> cacheList = CacheType.getAllCache();
        if (cacheList != null)
        {
            String key = request.getRequestURI() +
                    (request.getQueryString() != null ? "?" + request.getQueryString() : "");
            for (CacheItem item : cacheList)
            {
                if (key.matches(item.getKey()))
                {
//                    System.out.println(key);
//                    System.out.println(item.getKey());
//                    System.out.println(key.matches(item.getKey()));
                    super.doFilter(request, response, chain);
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }

    public static void expireCache(String cacheName, String key)
    {
        if (thisCache != null)
        {// 自动在KEY前加上language，对其它程序来说是透明的
            key = "[" + LocaleFilter.locale_language + "]" + key;
            thisCache.remove(key);
            log.debug("expire cache=" + key);
        }
    }

    protected PageInfo buildPageInfo(final HttpServletRequest request, final HttpServletResponse response,
            final FilterChain chain) throws Exception
    {
        // Look up the cached page
        final String key = calculateKey(request);
        PageInfo pageInfo = null;
        String originalThreadName = Thread.currentThread().getName();
        try
        {
            checkNoReentry(request);
            Element element = blockingCache.get(key);
            if (element == null || element.getObjectValue() == null)
            {
                try
                {
                    // Page is not cached - build the response, cache it, and send to client
                    pageInfo = buildPage(request, response, chain);
                    List heads = pageInfo.getHeaders();
                    boolean cachable = true;
                    for (int i = 0; i < pageInfo.getHeaders().size(); i++)
                    {
                        for (int t = 0; t < ((String[]) heads.get(i)).length; t++)
                        {
                            if (((String[]) heads.get(i))[t].equals("_NoCache"))
                            {
                                cachable = false;
                                break;
                            }
                        }
                    }
                    if (pageInfo.isOk() && cachable)
                    {
                        if (LOG.isLoggable(Level.FINEST))
                        {
                            LOG.finest("PageInfo ok. Adding to cache " + blockingCache.getName() + " with key " + key);
                        }
                        log.debug("Cache. - " + key);
                        blockingCache.put(new Element(key, pageInfo));
                    } else
                    {
                        if (LOG.isLoggable(Level.FINE))
                        {
                            LOG.fine("PageInfo was not ok(200). Putting null into cache " + blockingCache.getName() + " with key " + key);
                        }
                        log.debug("NO Cache. - " + key);
                        blockingCache.put(new Element(key, null));
                    }
                } catch (final Throwable throwable)
                {
                    // Must unlock the cache if the above fails. Will be logged at Filter
                    blockingCache.put(new Element(key, null));
                    throw new Exception(throwable);
                }
            } else
            {
                pageInfo = (PageInfo) element.getObjectValue();
            }
        } catch (LockTimeoutException e)
        {
            //do not release the lock, because you never acquired it
            throw e;
        } finally
        {
            Thread.currentThread().setName(originalThreadName);
        }
        return pageInfo;
    }
}
