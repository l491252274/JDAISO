package com.unlimited.oj.webapp.interceptor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.unlimited.oj.util.ApplicationConfig;
import com.unlimited.oj.util.Tool;
import java.util.Date;
import javax.servlet.http.HttpSession;

/**
 *
 * @author <a href="mailto:checkie@vip.qq.com">Matt Raible</a>
 * @see 
 */
public class RequestDelayInterceptor implements Interceptor {
    private static final long serialVersionUID = 5067220611840427509L;
    private int delay = 20; // 延时(秒)
    private float minInterval = 1; // 两次点击最小时间间隔0.5秒
    private long rushTimes = 6; //冲击时间

    /**
     * 
     * @param invocation the current action invocation
     * @return the method's return value, or null after setting HttpServletResponse.SC_FORBIDDEN
     * @throws Exception when setting the error on the response fails
     */
    public String intercept(ActionInvocation invocation) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();

        if(session.getAttribute("_last_request_time")==null)
        {
            session.setAttribute("_rush_time_left", new Long(rushTimes));
            session.setAttribute("_time_punish", "0");
            session.setAttribute("_last_request_time", new Long(System.currentTimeMillis()));
            return invoke(request, invocation);
        }
        else
        {
            Long last = (Long)session.getAttribute("_last_request_time");
            Long now = System.currentTimeMillis();
            if(Math.abs(now-last)>1000*delay)
            {
                session.setAttribute("_rush_time_left", new Long(rushTimes));
                session.setAttribute("_time_punish", "0");
                session.setAttribute("_last_request_time", new Long(now));
                return invoke(request, invocation);
            }
            else
            {
                String _punish = (String)session.getAttribute("_time_punish");
                if(_punish.equals("0"))
                {
                    Long _rush = (Long)session.getAttribute("_rush_time_left");
                    if(Math.abs(now-last)<1000*minInterval) // 小时最小间隔
                    {
                        _rush = _rush - 1;
                        if(_rush <= 0)
                        {
                            session.setAttribute("_time_punish", "1");
                        }
                    }
                    else
                    {
                        _rush = _rush + (int)(Math.abs(now-last)/(1000*minInterval));
                        if(_rush>rushTimes) _rush = rushTimes;
                    }
                    session.setAttribute("_rush_time_left", new Long(_rush));
                    session.setAttribute("_last_request_time", new Long(now));
                    return invoke(request, invocation);
                }
            }
            //HttpServletResponse response = ServletActionContext.getResponse();
            //response.sendError(HttpServletResponse.SC_FORBIDDEN, "You need wait for " + Math.abs(now-last)/1000 + " seconds");
            request.getSession().setAttribute("messages", "You need wait for " + (delay-Math.abs(now-last)/1000) + " seconds");
            return "error";
        }
    }

    private String invoke(HttpServletRequest request, ActionInvocation invocation) throws Exception
    {
        //System.out.println(request.getParameter("waitForSeconds"));
        if(request.getParameter("waitForSeconds")!=null)
        {
            int delayShowOffset = 0;
            if(ApplicationConfig.getValue("DelayShowOffset")!=null)
                delayShowOffset = Integer.parseInt(ApplicationConfig.getValue("DelayShowOffset"));
            int second = Integer.parseInt(request.getParameter("waitForSeconds"));
            if(second + delayShowOffset > 0)
            {
                if(request.getSession().getAttribute("_queryString")!=null)
                    request.getSession().setAttribute("_url", request.getRequestURI() + "?" +
                            request.getSession().getAttribute("_queryString"));
                else
                    request.getSession().setAttribute("_url", request.getRequestURI());
                request.getSession().setAttribute("_waitForSeconds", second + delayShowOffset);
                return "waitForSecond";
            }
        }
        return invocation.invoke();
    }

    public void setDelay(int delay)
    {
        this.delay = delay;
    }

    public void setMinInterval(float minInterval)
    {
        this.minInterval = minInterval;
    }

    public void setRushTimes(long rushTimes)
    {
        this.rushTimes = rushTimes;
    }


    /**
     * This method currently does nothing.
     */
    public void destroy() {
    }

    /**
     * This method currently does nothing.
     */
    public void init() {
    }
}
