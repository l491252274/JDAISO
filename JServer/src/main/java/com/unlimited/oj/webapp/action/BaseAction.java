package com.unlimited.oj.webapp.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.net.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.unlimited.oj.Constants;
import com.unlimited.oj.model.User;
import com.unlimited.oj.service.*;
import com.unlimited.oj.util.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.unlimited.appserver.service.AppProblemTreeKeyStringManager;
import com.unlimited.appserver.service.AppUserManager;


import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.unlimited.oj.model.OjTreeNode;
import com.unlimited.webserver.service.LabManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import org.springframework.security.providers.encoding.PasswordEncoder;

/**
 * Implementation of <strong>ActionSupport</strong> that contains convenience
 * methods for subclasses. For example, getting the current user and saving
 * messages/errors. This class is intended to be a base class for all Action
 * classes.
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */

class UserInfoItem
{
	public Long userId;
	public String userName;
	public int grade;
	public int money;
	public int contribution;
	public int ac; //ac 题目数
	public String fullname;
	public boolean anonymous;
	
	/***************************************/
	public int rank; //用于年级排名
	public Long nextUserTarget; //用于年级排名
	public int nextAcTarget; //用于年级排名
	/***************************************/
	
}

class UserOnlineItem
{
	long start;
	long end;
	Long userId;
	String userName;
	long ip;
	String sip;
	int count;
}

class UserSubmitStatus
{
	long lastSubmitTime=0; // 最近一次提交的时间
	long lastClickTime=0; // 最近一次点击提交按钮的时间
	long continueClickCount=0; // 连续点击的次数
	long limitedBefore=0; // 这个时间前不能再点击提交或触发提交
}

public class BaseAction extends ActionSupport implements Preparable
{

    private static final long serialVersionUID = 3525445612504421307L;
    /**
     * Constant for cancel result String
     */
    public static final String CANCEL = "cancel";
    protected User currentUser;
    protected static Map<Long, UserInfoItem> mapUserInfo =  new HashMap<Long, UserInfoItem>();
    static long updateTimeUserInfo = 0;
    static ResourceBundle bundle = null;
    /**
     * Transient log to prevent session synchronization issues - children can
     * use instance for logging.
     */
    protected transient final Log log = LogFactory.getLog(getClass());

    protected LoginLogManager loginLogManager;
    protected UserManager userManager;
    protected LabManager labManager;
    protected RoleManager roleManager;
    protected OjTreeNodeManager ojTreeNodeManager;
    protected CommonManager commonManager;
    
    protected AppUserManager appUserManager;
    protected AppProblemTreeKeyStringManager appProblemTreeKeyStringManager;
    
    protected String downloadPath;
    
    // public parameters of server
    private String svrYear;
    private String svrMonth;
    private String svrDay;

    protected static Map<Long, UserOnlineItem> mapUserOnline = new HashMap<Long, UserOnlineItem>();
    protected static Map<String, UserSubmitStatus> mapUserSubmitStatus = new HashMap<String, UserSubmitStatus>();

    /**
     * Indicator if the user clicked cancel
     */
    protected String cancel;
    /**
     * Indicator for the page the user came from.
     */
    protected String from;
    /**
     * Set to "delete" when a "delete" request parameter is passed in
     */
    protected String delete;
    /**
     * Set to "save" when a "save" request parameter is passed in
     */
    protected String save;
    /**
     * MailEngine for sending e-mail
     */
    protected MailEngine mailEngine;
    /**
     * A message pre-populated with default data
     */
    protected SimpleMailMessage mailMessage;
    /**
     * Velocity template to use for e-mailing
     */
    protected String templateName;

    protected PasswordEncoder passwordEncoder;

    public BaseAction() {
		super();
	}


	/**
     * Simple method that returns "cancel" result
     *
     * @return "cancel"
     */
    public String cancel()
    {
        return CANCEL;
    }


    protected void setNoCache()
    {
        getResponse().setHeader("_NoCache", "1");
    }
    /**
     * Save the message in the session, appending if messages already exist
     *
     * @param msg
     *            the message to put in the session
     */
    @SuppressWarnings("unchecked")
    protected void saveMessage(String msg)
    {
        List messages = (List) getRequest().getSession().getAttribute("messages");
        if (messages == null)
        {
            messages = new ArrayList();
            setNoCache();
        }
        messages.add(msg);
        getRequest().getSession().setAttribute("messages", messages);
    }

    /**
     * Convenience method to get the Configuration HashMap from the servlet
     * context.
     *
     * @return the user's populated form from the session
     */
    protected Map getConfiguration()
    {
        Map config = (HashMap) getSession().getServletContext().getAttribute(Constants.CONFIG);
        // so unit tests don't puke when nothing's been set
        if (config == null)
        {
            return new HashMap();
        }
        return config;
    }

    /**
     * Convenience method to get the request
     *
     * @return current request
     */
    protected HttpServletRequest getRequest()
    {
        return ServletActionContext.getRequest();
    }

    /**
     * Convenience method to get the response
     *
     * @return current response
     */
    protected HttpServletResponse getResponse()
    {
        return ServletActionContext.getResponse();
    }

    /**
     * Convenience method to get the session. This will create a session if one
     * doesn't exist.
     *
     * @return the session from the request (request.getSession()).
     */
    protected HttpSession getSession()
    {
        return getRequest().getSession();
    }

    protected ServletContext getServletContext()
    {
        return ServletActionContext.getServletContext();
    }

    /**
     * Convenience method to send e-mail to users
     *
     * @param user
     *            the user to send to
     * @param msg
     *            the message to send
     * @param url
     *            the URL to the application (or where ever you'd like to send
     *            them)
     */
    protected void sendUserMessage(User user, String msg, String url)
    {
        if (log.isDebugEnabled())
        {
            log.debug("sending e-mail to user [" + user.getEmail() + "]...");
        }

        mailMessage.setTo(user.getUsername() + "<" + user.getEmail() + ">");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("user", user);
        // TODO: figure out how to get bundle specified in struts.xml
        // model.put("bundle", getTexts());
        model.put("message", msg);
        model.put("applicationURL", url);
        mailEngine.sendMessage(mailMessage, templateName, model);
    }

    public void setMailEngine(MailEngine mailEngine)
    {
        this.mailEngine = mailEngine;
    }

    public void setMailMessage(SimpleMailMessage mailMessage)
    {
        this.mailMessage = mailMessage;
    }

    public void setTemplateName(String templateName)
    {
        this.templateName = templateName;
    }

    /**
     * Convenience method for setting a "from" parameter to indicate the
     * previous page.
     *
     * @param from
     *            indicator for the originating page
     */
    public void setFrom(String from)
    {
        this.from = from;
    }

    public void setDelete(String delete)
    {
        this.delete = delete;
    }

    public void setSave(String save)
    {
        this.save = save;
    }

    public User getCurrentUser()
    {
        return currentUser;
    }

    /**
     *
     * @throws Exception
     */
    public void prepare()
    {	
        // Set userManager and labmanager
        ServletContext context = getRequest().getSession().getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
        userManager = (UserManager) ctx.getBean("userManager");
        labManager  = (LabManager) ctx.getBean("labManager");
        ojTreeNodeManager = (OjTreeNodeManager) ctx.getBean("ojTreeNodeManager");
         
        // Set currentUser
        String username = getRequest().getRemoteUser();
        if (username != null)
            currentUser = userManager.getUserByUsername(username);
        else
            currentUser = null;

        // set the menu and submenu
        String cp = getRequest().getParameter("connectpoint");
        if (cp != null && !"".equals(cp))
            getRequest().getSession().setAttribute("connectpoint", cp);
              
        // set User Info
        if(System.currentTimeMillis()-updateTimeUserInfo>1800000)
        {
        	mapUserInfo.clear();
        	List list = userManager.getAllUserInfo();
        	for(Object item: list)
        	{
        		Object[] items = (Object[])item;
        		if(items.length>=8)
        		{
        			UserInfoItem uii = new UserInfoItem();
        			uii.userId = (Long)items[0];
        			uii.userName = (String)items[1];
       				uii.grade = (Integer)items[2];
        			uii.money = (Integer)items[3];
        			uii.contribution = (Integer)items[4];
        			uii.fullname = (String)items[6]+(String)items[5];
        			uii.anonymous = (Boolean)items[7];
        			mapUserInfo.put(uii.userId, uii);
        		}        		
        	}
        	
        	updateTimeUserInfo = System.currentTimeMillis();
        }
        
        Calendar now=Calendar.getInstance();
        svrYear = new Integer(now.get(Calendar.YEAR)).toString();
        svrMonth = new Integer(now.get(Calendar.MONTH)).toString();
        svrDay = new Integer(now.get(Calendar.DATE)).toString();
    }

    public void setUserManager(UserManager userManager)
    {
        this.userManager = userManager;
    }
    public void setLabManager(LabManager labManager)
    {
        this.labManager = labManager;
    }
    public void setOjTreeNodeManager(OjTreeNodeManager ojTreeNodeManager)
    {
        this.ojTreeNodeManager = ojTreeNodeManager;
    }

    public void setAuth_administrators(String auth_administrators)
    {
        getRequest().setAttribute("auth_administrators", auth_administrators);
    }

    public void setAuth_objectIdKeyName(String auth_objectIdKeyName)
    {
        getRequest().setAttribute("auth_objectIdKeyName", auth_objectIdKeyName);
    }

    public void setAuth_objectType(String auth_objectType)
    {
        getRequest().setAttribute("auth_objectType", auth_objectType);
    }

    public void setAuth_owners(String auth_owners)
    {
        getRequest().setAttribute("auth_owners", auth_owners);
    }

    public void setLastSubmitProblemId(Long id)
    {
        if (id != null)
            getRequest().getSession().setAttribute("_lastSubmitProblemId", id);
    }

    public Long getLastSubmitProblemId()
    {
        if (getRequest().getSession().getAttribute("_lastSubmitProblemId") != null)
            return (Long) getRequest().getSession().getAttribute("_lastSubmitProblemId");
        else
            return new Long(0);
    }

    public void setLastSubmitTime(Date date)
    {
        if (date != null)
            getRequest().getSession().setAttribute("_lastSubmitTime", date);
    }

    public Date getLastSubmitTime()
    {
        if (getRequest().getSession().getAttribute("_lastSubmitTime") != null)
            return (Date) getRequest().getSession().getAttribute("_lastSubmitTime");
        else
        {
            Calendar cal = Calendar.getInstance();
            cal.set(1900, 1, 1);
            return cal.getTime();
        }
    }

     public boolean isOJMonitorRun() throws IOException
    {
        if(getCurrentUser().isAdministrator())
            return true;
        
        Socket socket = null;
        BufferedReader in = null;
        try
        {
            String host = getRequest().getRemoteHost();
            if (host.equals("0:0:0:0:0:0:0:1"))
                host = "192.168.1.2";
            socket = new Socket();
            InetAddress ip = InetAddress.getByName(host);
            SocketAddress socketAddress = new InetSocketAddress(ip, 1909);
            socket.connect(socketAddress, 100);
            if (socket.isConnected())
            {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //            out.println("Hello");
                if (in.read() == 48)
                {
                    return true;
                }
            }
        } catch (IOException e)
        {
        } finally
        {
            if (in != null)
                in.close();
            if (socket != null && !socket.isClosed())
                socket.close();
        }
        return false;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder)
    {
        this.passwordEncoder = passwordEncoder;
    }

    protected String SUCCESS()
    {
        if(hasActionErrors() || hasFieldErrors() ||
                getRequest().getSession().getAttribute("messages")!=null ||
                    getRequest().getSession().getAttribute("errors")!=null ||
                    getCurrentUser()!=null &&
                            (getCurrentUser().isAdministrator() ||
                             getCurrentUser().isContestAdministrator() ||
                             getCurrentUser().isExamAdministrator() ||
                             getCurrentUser().isExerciseAdministrator()))
            setNoCache();
        return SUCCESS;
    }

    protected String ERROR()
    {
        setNoCache();
        return ERROR;
    }

    protected String INPUT()
    {
        setNoCache();
        return INPUT;
    }

    public void setLoginLogManager(LoginLogManager loginLogManager)
    {
        this.loginLogManager = loginLogManager;
    }
	
	public String getUsername(Long userId)
	{
		UserInfoItem cuui = mapUserInfo.get(userId);
		if(cuui==null)
			return "";
		else
			return cuui.userName;
	}
	
	public String getUserFullName(Long userId)
	{
		UserInfoItem cuui = mapUserInfo.get(userId);
		if(cuui==null)
			return "";
		else
			return cuui.fullname;
	}

	private long ipTOint(String ip)
	{
		long ret = 0;
		if(ip==null) return 0;
		String[] items = ip.split("\\.");
		if(items.length>3)
		{
			for(String item:items)
				ret = ret*256+Integer.parseInt(item);
		}
		else
			ret = 0;
		return ret;
	}

    public String getDownloadPath()
	{
		return downloadPath;
	}

	public String getDownloadFolder()
    {
        if (downloadPath == null)
            return "";
        String path = Tool.StringsReplace(downloadPath, "\\", File.separator);
        path = Tool.StringsReplace(path, "/", File.separator);
        int nPos = path.lastIndexOf(File.separator);
        if (nPos >= 0)
            return path.substring(0, nPos);
        else
            return path;
    }

	protected boolean judgeDelayTime()
	{
		if(getRequest().getSession().getAttribute("_showAfter")==null)
			return false;
		long showAfter = System.currentTimeMillis();
		try
		{
			showAfter = Long.parseLong(getRequest().getSession().getAttribute("_showAfter").toString());
		}catch(Exception ex){};
		if(System.currentTimeMillis() - showAfter<100)
			return false;
		
		getRequest().getSession().removeAttribute("_showAfter");
		return true;
	}
	
	protected String getBundleValue(String key)
	{
		if(bundle==null)
		{
			Locale locale = LocaleContextHolder.getLocale();
			bundle = ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale);
		}
		if(bundle.getString(key)!=null)
			return bundle.getString(key);
		else
			return "";
	}
	public static String getUserIP(Long userId)
	{
		UserOnlineItem uoi = mapUserOnline.get(userId);
		if(uoi!=null)
			return uoi.sip;
		return "";
	}


	public String getSvrYear() {
		return svrYear;
	}


	public String getSvrMonth() {
		return svrMonth;
	}


	public String getSvrDay() {
		return svrDay;
	}

	
	static long SubmitStatus_Falt = 5000; // 5秒内连续点击两次，算失误
	static long SubmitStatus_Continue = 10000; // 10秒内连续点击两次，算连击

	public synchronized int userClickSubmitButtonHandle(String key)
	{
		if(currentUser==null) return -1; // 当前用户为空
		long now = System.currentTimeMillis();
		SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd  hh:mm:ss ");   
		String   nowDate   =   sDateFormat.format(new   java.util.Date());   
		Long userId = currentUser.getId();

        String intervalOfSameSubmits = "10"; // 默认10秒
        if(ApplicationConfig.getValue("IntervalOfSameSubmits")!=null)
            intervalOfSameSubmits = ApplicationConfig.getValue("IntervalOfSameSubmits");
        UserSubmitStatus uss = mapUserSubmitStatus.get(new Long(userId).toString()+key);
		if(uss!=null)
		{
			if(now-uss.lastClickTime < SubmitStatus_Falt) return -2; // 5秒内再次点击Submit，误击
			if(now-uss.lastClickTime < SubmitStatus_Continue) // 10秒点击Submit两次，判为连击
			{
				uss.continueClickCount++;
				if(uss.continueClickCount>=10)
				{// 超过十次连击，等待时间加60秒
					uss.limitedBefore+=60000;
					uss.continueClickCount=0;
				}				
			}
			else if(now-uss.lastClickTime > SubmitStatus_Continue)
				uss.continueClickCount=0;
			uss.lastClickTime = now;

			if(now < uss.limitedBefore)
				return (int)(uss.limitedBefore-now)/1000; // 等待XXX秒
		}
		else
		{// 没有记录，就创建
			uss = new UserSubmitStatus();
			uss.continueClickCount = 0;
			uss.lastClickTime = now;
			mapUserSubmitStatus.put(new Long(userId).toString()+key, uss);
		}		
		return 0;
	}

	public synchronized int userSubmitHandle(String key)
	{
		if(currentUser==null) return -1; // 当前用户为空
		long now = System.currentTimeMillis();
		SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd  hh:mm:ss ");   
		String   nowDate   =   sDateFormat.format(new   java.util.Date());   
		Long userId = currentUser.getId();

        String intervalOfSameSubmits = "10"; // 默认10秒
        if(ApplicationConfig.getValue("IntervalOfSameSubmits")!=null)
            intervalOfSameSubmits = ApplicationConfig.getValue("IntervalOfSameSubmits");
        UserSubmitStatus uss = mapUserSubmitStatus.get(new Long(userId).toString()+key);
		if(uss!=null)
		{
			if(now < uss.limitedBefore)
				return (int)(uss.limitedBefore-now)/1000; // 等待XXX秒
			uss.lastSubmitTime = now;
			uss.limitedBefore = now + Integer.parseInt(intervalOfSameSubmits)*1000;
		}
		else
		{// 没有记录，就创建
			uss = new UserSubmitStatus();
			uss.lastSubmitTime = now;
			uss.limitedBefore = now + Integer.parseInt(intervalOfSameSubmits)*1000;
			mapUserSubmitStatus.put(new Long(userId).toString()+key, uss);
		}		
		return 0;	
	}


	public void setAppUserManager(AppUserManager appUserManager) {
		this.appUserManager = appUserManager;
	}


	public void setAppProblemTreeKeyStringManager(
			AppProblemTreeKeyStringManager appProblemTreeKeyStringManager) {
		this.appProblemTreeKeyStringManager = appProblemTreeKeyStringManager;
	}

	public void setCommonManager(CommonManager commonManager) {
		this.commonManager = commonManager;
	}


	public void setRoleManager(RoleManager roleManager) {
		this.roleManager = roleManager;
	}
	
	
	
	
}
