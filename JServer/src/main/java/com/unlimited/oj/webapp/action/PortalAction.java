package com.unlimited.oj.webapp.action;

import com.unlimited.oj.dao.support.Page;
import com.unlimited.oj.model.*;
import com.unlimited.oj.util.ApplicationConfig;
import com.unlimited.oj.util.Tool;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Action for facilitating Problem Management feature.
 */
public class PortalAction extends BaseAction
{

    private Page newss;
    private String downloadFolder;
    private static final long serialVersionUID = 6776111111712115191L;

    /**
     * Grab the entity from the database before populating with request
     * parameters
     */
    public void prepare()
    {
        super.prepare();
    }

    /**
     * Default: just returns "success"
     *
     * @return "success"
     */
    public String execute()
    {
        return SUCCESS();
    }

    /**
     * Sends gameProblems to "mainMenu" when !from.equals("list"). Sends everyone
     * else to "cancel"
     *
     * @return "mainMenu" or "cancel"
     */
    public String cancel()
    {
        if (!"list".equals(from))
        {
            return "mainMenu";
        }
        return "cancel";
    }

    public String mainPage()
    {

        return showBBS();
    }
    

    public String showBBS()
    {
        int page = 1, sort = 1, order = 2;
        try
        {
            page = Integer.parseInt(getRequest().getParameter("page"));
        } catch (Exception e)
        {// not need handle
        }
        try
        {
            sort = Integer.parseInt(getRequest().getParameter("sort"));
        } catch (Exception e)
        {// not need handle
        }
        try
        {
            order = Integer.parseInt(getRequest().getParameter("order"));
        } catch (Exception e)
        {// not need handle
        }

        return SUCCESS();
    }

    public String delayShow()
    {
        String queryString = getRequest().getQueryString();
        if(queryString!=null)
            getRequest().getSession().setAttribute("_queryString", queryString);
        else
             getRequest().getSession().removeAttribute("_queryString");
        Object waitForSeconds =  getRequest().getSession().getAttribute("waitForSeconds");
        //延时，代码级控制
        long delayTime = 10; //默认10S
        try
        {
        	delayTime = Long.parseLong((String)waitForSeconds);
        }catch(Exception ex){};
        getRequest().getSession().setAttribute("_showAfter", System.currentTimeMillis()+delayTime);
        return SUCCESS();
    }

    public Page getNewss()
    {
        return newss;
    }

    public String deleteDownloadFiles()
    {
    	if(!currentUser.isAdministrator() && 
    			!currentUser.isContestAdministrator() &&
    			!currentUser.isExamAdministrator())
		{
            List<String> args = new ArrayList<String>();
            args.add("you have no right.");
            addActionError(getText("errors.invalid", args));
            return ERROR();
		}
    	
    	if(downloadFolder.trim().equals(""))
    	{
            List<String> args = new ArrayList<String>();
            args.add("Path invalid.");
            addActionError(getText("errors.invalid", args));
            return ERROR();
    	}
        File dir = new File(Tool.fixPath(ApplicationConfig.getApplicationRootPath() +
                    downloadFolder));
        if(!dir.exists())
            saveMessage(dir.getAbsolutePath() + " not exists.");
        else
        {
            Tool.delete(dir);
            saveMessage("delete done.");
        }
        return SUCCESS();
    }

    public String getDownloadFolder()
    {
        return downloadFolder;
    }

    public void setDownloadFolder(String downloadFolder)
    {
        this.downloadFolder = downloadFolder;
    }

}
