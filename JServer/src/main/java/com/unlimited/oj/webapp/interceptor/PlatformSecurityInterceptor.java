package com.unlimited.oj.webapp.interceptor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.unlimited.oj.dao.GenericDao;
import com.unlimited.oj.iface.IOwner;
import java.util.HashMap;

/**
 * Security interceptor checks to see if users are in the specified roles
 * before proceeding.  Similar to Spring's UserRoleAuthorizationInterceptor.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 * @see org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor
 */
public class PlatformSecurityInterceptor implements Interceptor
{

    private static final long serialVersionUID = 5067888608840427509L;
    private int securityLevel = 9;
    private String[] administrators;

    /**
     * Intercept the action invocation and check to see if the user has the proper role.
     * @param invocation the current action invocation
     * @return the method's return value, or null after setting HttpServletResponse.SC_FORBIDDEN
     * @throws Exception when setting the error on the response fails
     */
    public String intercept(ActionInvocation invocation) throws Exception
    {
        HttpServletRequest request = ServletActionContext.getRequest();

        if (this.administrators != null)
        {
            for (String admin : this.administrators)
            {
                if (request.isUserInRole(admin))
                {
                    return invocation.invoke();
                }
            }
        }

        java.util.Map config = (HashMap) request.getSession().getServletContext().getAttribute(com.unlimited.oj.Constants.CONFIG);
        // so unit tests don't puke when nothing's been set
        if (config != null && config.get("PlatformSecurityLevel")!=null)
        {
            String sl = (String)config.get("PlatformSecurityLevel");
            int security = Integer.parseInt(sl);
            if(security>=securityLevel)
                return invocation.invoke();
        }

        request.getSession().setAttribute("messages", "You cann't access.");
        return "error";
    }

    /**
     * This method currently does nothing.
     */
    public void destroy()
    {
    }

    /**
     * This method currently does nothing.
     */
    public void init()
    {
    }

    public final void setAdministrators(String[] administrators)
    {
        this.administrators = administrators;
        if (this.administrators != null)
        {
            if (this.administrators.length == 1) //转为多值
            {
                this.administrators = this.administrators[0].split(",");
            }
        }
    }

    public void setSecurityLevel(int securityLevel)
    {
        this.securityLevel = securityLevel;
    }


}
