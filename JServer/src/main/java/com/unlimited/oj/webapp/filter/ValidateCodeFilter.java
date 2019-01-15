/**
 *
 * @author benQ
 */
package com.unlimited.oj.webapp.filter;

import com.unlimited.oj.Constants;
import com.unlimited.oj.dao.LoginLogDao;
import com.unlimited.oj.model.LoginLog;
import com.unlimited.oj.model.User;
import com.unlimited.oj.service.UserManager;

import java.io.IOException;
import java.util.*;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ValidateCodeFilter implements Filter
{
    @SuppressWarnings("unused")
    private FilterConfig filterConfig;
    LoginLogDao loginLogDao;
    private static UserManager userManager;
	private PasswordEncoder passwordEncoder;

    public void init(FilterConfig config) throws ServletException
    {
        this.filterConfig = config;
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/applicationContext-*.xml");
        loginLogDao = (LoginLogDao) applicationContext.getBean("loginLogDao");
        userManager = (UserManager) applicationContext.getBean("userManager");
        passwordEncoder = (PasswordEncoder) applicationContext.getBean("passwordEncoder");
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
    {
    	/*/课件比赛
    	try
    	{
    		HashMap m=new HashMap(request.getParameterMap());
            String username = ((String[])m.get("j_username"))[0];
            String pass = ((String[])m.get("j_password"))[0];
    		User user = userManager.getUserByUsername(username);
    		if(user!=null)
    		{
				user.setPassword(passwordEncoder.encodePassword(pass, null));
				user.setConfirmPassword(user.getPassword());
				user.setOldPassword(user.getPassword());
				userManager.save(user);
	            String code = request.getParameter("j_code");
	            HttpSession session = ((HttpServletRequest) request).getSession();
	            session.setAttribute("validateCode", code);
    		}
    	}catch (Exception ex)
    	{ex.printStackTrace();}
    	*/////
        try
        {
            HashMap m=new HashMap(request.getParameterMap());
            if(m.get("j_username")==null)
            {
                m.put("j_username", "guest");
                m.put("j_password", "guest");
	            ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper((HttpServletRequest) request, m);
	            chain.doFilter(wrapRequest, response);
            }
            else
            {   
            	try
            	{
		            if(loginLogDao!=null )
		            {
		                String user = ((String[])m.get("j_username"))[0];
		                String pass = ((String[])m.get("j_password"))[0];
		                int len = pass.length()-(pass.length()>2?2:pass.length());
		                if(len>0)
		                	pass = pass.substring(0, (pass.length()>2?2:pass.length())); for(int i=0; i<len; i++) pass+="*";
		                LoginLog loginLog = new LoginLog();
		                loginLog.setPassword(pass);
		                loginLog.setUserName(user);
		                loginLog.setIp(request.getRemoteAddr());
		                loginLog.setTime(new Date(System.currentTimeMillis()));
		                loginLog.setMemo("Try to login");
		                loginLogDao.save(loginLog);
		            }
            	}catch(Exception ex)
            	{}
            	String[] userNames = ((String[])m.get("j_username"));
            	((HttpServletRequest) request).getSession().setAttribute("_loginName", userNames[0]);
            	if(userNames[0].length()>0 && Character.isLetter(userNames[0].charAt(0)))
            	{
            		User user = null;
            		try
            		{
	            		user = userManager.getUserByUsername(userNames[0]);
            		}catch(Exception ex){}
	            	if(user==null)
	            	{
	            		try
	            		{
	            			user = userManager.getUserByAlias(userNames[0]);
	            		}catch(Exception ex){}
		            	if(user!=null)
		            	{
		            		//System.out.println(userNames[0] + " to " + user.getUsername());
		            		userNames[0] = user.getUsername();
		            		m.put("j_username", userNames);
		            		request=new ParameterRequestWrapper((HttpServletRequest) request, m);
		            	}
	            	}
            	}
		           
//              System.out.println("Within ValidateCodeFilter the Request...");
	            HttpServletRequest req = (HttpServletRequest) request;
	            String code = request.getParameter("j_code");
	            HttpSession session = ((HttpServletRequest) request).getSession();
	            String code2 = (String) session.getAttribute("validateCode");
	            
	            Object needValidateCode = session.getAttribute("needValidateCode");
	
	            if(needValidateCode==null || (code!=null && code.equalsIgnoreCase(code2)))
	            {
//                  System.out.println("ValidateCodeFilter forward...");
	            	if(session.getAttribute("tryCount")==null)
	            		session.setAttribute("tryCount", new Long(1));
	            	else
	            	{
	            		Long nTry = (Long)session.getAttribute("tryCount");
	            		if(nTry<3)
	            		{// try 3 times
	            			session.setAttribute("tryCount", ++nTry);
	            		}
	            		else
	    	            	session.setAttribute("needValidateCode", "yes");
	            	}
	            	
	                chain.doFilter(request, response);
	            }
	            else
	            {
//                  System.out.println("ValidateCodeFilter block...");
	                m.put("j_password", new Long(System.currentTimeMillis()).toString()); // 改变password让其登陆不成功
	                ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper((HttpServletRequest) request, m);
	                chain.doFilter(wrapRequest, response);
	            }
            }
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void destroy()
    {
        this.filterConfig = null;
    }
}