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
public class UserRoleAuthorizationInterceptor implements Interceptor
{

    private static final long serialVersionUID = 5067790608840427509L;
    private HashMap daos = null;
    private String auth_administrators = null;
    private String auth_owners = null;
    private String auth_objectIdKeyName = null;
    private String auth_objectType = null;

    /**
     * Intercept the action invocation and check to see if the user has the proper role.
     * @param invocation the current action invocation
     * @return the method's return value, or null after setting HttpServletResponse.SC_FORBIDDEN
     * @throws Exception when setting the error on the response fails
     */
    public String intercept(ActionInvocation invocation)
    {
        HttpServletRequest request = ServletActionContext.getRequest();

        if(request.getAttribute("auth_administrators")!=null)
            auth_administrators = (String)request.getAttribute("auth_administrators");
        if(request.getAttribute("auth_owners")!=null)
            auth_owners = (String)request.getAttribute("auth_owners");
        if(request.getAttribute("auth_objectIdKeyName")!=null)
            auth_objectIdKeyName = (String)request.getAttribute("auth_objectIdKeyName");
        if(request.getAttribute("auth_objectType")!=null)
            auth_objectType = (String)request.getAttribute("auth_objectType");

        try
        {
            if (auth_administrators != null)
            {
                String[] administrators = auth_administrators.split(",");
                for (String admin : administrators)
                {
                    //System.out.println(admin);
                    if (request.isUserInRole(admin))
                    {
                        return invocation.invoke();
                    }
                }
            }

            if (auth_owners != null && auth_objectIdKeyName!=null && auth_objectType!=null)
            {
                String[] owners = auth_owners.split(",");
                for (String owner : owners)
                {
                    //System.out.println("*"+owner);
                    if (request.isUserInRole(owner))
                    {
                        if (auth_objectType != null && auth_objectIdKeyName != null && !"".equals(auth_objectType))
                        {
                            String id = request.getParameter(auth_objectIdKeyName);
                            GenericDao dao = (GenericDao) daos.get(auth_objectType);
                            if(id==null || "".equals(id.trim()))
                                return invocation.invoke();
                            else
                            {
                                if (id != null && dao != null)
                                {
                                    Object o = dao.get(new Long(id));
                                    if(o==null)
                                        return invocation.invoke();
                                    IOwner obj = (IOwner) o;
                                    if (obj.isOwner(request.getRemoteUser()) || obj.isAuthor(request.getRemoteUser()))
                                    {
                                        return invocation.invoke();
                                    }
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        request.getSession().setAttribute("messages", "You cann't access.");
        return "mainMenu";
    }

    public void setDaos(HashMap daos)
    {
        this.daos = daos;
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

    public void setAuth_administrators(String auth_administrators)
    {
        this.auth_administrators = auth_administrators;
    }

}
