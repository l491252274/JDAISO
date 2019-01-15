/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unlimited.oj.webapp.filter;

//类TimeHandler必须实现MethodInterceptor接口
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TimerFilter implements Filter
{

    private static final Log log = LogFactory.getLog(TimerFilter.class);

    //重写invoke()方法
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
    {
        long procTime = System.currentTimeMillis();
        String url = ((HttpServletRequest)request).getRequestURI(); // 获得调用URL
        //logger.log(Level.INFO, url + " 开始执行");
        try
        {
            chain.doFilter(request, response);
        }
        catch(Exception e)
        {
        }
        finally
        {
            //计算执行时间
            Object lhbt = com.unlimited.oj.util.ApplicationConfig.getValue("launchTimeFilter");
            if (lhbt != null && ((String) lhbt).equalsIgnoreCase("true"))
            {
                procTime = System.currentTimeMillis() - procTime;
                log.info("[" + url + "] 执行结束, 共用了 " + procTime + "毫秒");
            }
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException
    {
    }

    public void destroy()
    {
    }
}
