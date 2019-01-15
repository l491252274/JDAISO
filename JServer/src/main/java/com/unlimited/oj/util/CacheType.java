/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unlimited.oj.util;

import java.util.*;

import org.apache.commons.logging.*;


/**
 *
 * @author benQ
 */

public class CacheType {

    private static HashMap<String, CacheItem> mapCacheType = null;
    private static boolean m_ready;
    private final static Log log = LogFactory.getLog(CacheType.class);

    static
    {
        mapCacheType = new HashMap<String, CacheItem>();
        m_ready = false;
    }

    public static void init(boolean force)
    {
        if(force)
            m_ready = false;
        init();
    }

    private static void init()
    {
        if(!m_ready)
        {
            mapCacheType.clear();
            int nCount = Integer.parseInt(ApplicationConfig.getValue("CacheCount"));
            int i;
            log.info("Cache count=" + nCount);
            for(i=1; i<=nCount; i++)
            {
                String sCache = ApplicationConfig.getValue("Cache"+i);
                String[] values= sCache.split(",");
                if(values.length>=2)
                {
                    CacheItem item = new CacheItem();
                    item.setName(values[0]);
                    item.setKey(values[1]);
                    if(values.length>=3)
                        item.setFormat(values[2]);
                    else
                        item.setFormat("");
                    mapCacheType.put(item.getName(), item);
                    log.info("Add cache" + i + "(" + item.getName() + ")");
                }

            }
            m_ready = true;
        }
    }

    public static CacheItem getCache(String name)
    {
        if(!m_ready)
            init();
        return mapCacheType.get(name);
    }

    public static List<CacheItem> getAllCache()
    {
        if(!m_ready)
            init();
        return new ArrayList<CacheItem>(mapCacheType.values());
    }
}
