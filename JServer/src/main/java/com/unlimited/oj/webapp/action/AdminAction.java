package com.unlimited.oj.webapp.action;

import com.unlimited.oj.service.UserExistsException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.hibernate.Hibernate;

import com.unlimited.oj.Constants;
import com.unlimited.oj.dao.support.Page;
import com.unlimited.oj.model.*;
import com.unlimited.oj.util.ApplicationConfig;
import com.unlimited.oj.util.ProblemSource;
import com.unlimited.oj.util.Tool;
import com.unlimited.oj.webapp.filter.URLDistributeFilter;
import com.unlimited.oj.webapp.filter.URLDistributeFilter;
import java.io.*;
import java.util.*;
import javax.servlet.ServletContext;

/**
 * 管理员的各种操作
 * 
 * @author benQ
 * 
 */
class Block
{
	public int l, r;
}

class Pair implements Comparator
{
	public Long id;
	public int num;
	public Pair(Long id, int num){this.id = id; this.num = num;}
	public Pair(){};
	@Override
	public int compare(Object o1, Object o2) {
		return ((Pair)o2).num - ((Pair)o1).num;
	}
}

/**
 * Action for facilitating Problem Management feature.
 */
public class AdminAction extends BaseAction
{

	private static final long serialVersionUID = 6776111118712115191L;
	private HttpClient httpclient = new HttpClient();
	private String addGameAttendsData; // 用于addGameAttends方法
	private String addBatchFightData; // 用于addBatchFights方法
	private String applicationConfig; // 用于saveApplicationConfig方法
	private String setBatchStarProblems; // 用于setBatchStarProblems方法
	private Page loginLogs;
	
	private String downloadPath;
	private String searchValue;
	private String searchKey;

	/**
	 * Grab the entity from the database before populating with request
	 * parameters
	 */
	public void prepare()
	{
		super.prepare();
	}

	class newUserItem
	{
		public String userName;
		public String pass;
		public String className;
		public String trueName;
		public String nick;
		public boolean isNew;

		newUserItem()
		{
			userName = pass = className = trueName = nick = "";
			isNew = false;
		}
	}

	public String updateApplicationConfig() throws IOException
	{
		if (applicationConfig == null)
		{
			saveMessage("applicationConfig is null.");
			return SUCCESS();
		}
		String sOS = System.getProperty("os.name").toLowerCase();
		ServletContext context = getRequest().getSession().getServletContext();
		String applicationRootPath = context.getRealPath("/");
		String configueFileName = Tool.fixPath(Tool
				.fixPath(applicationRootPath) + "WEB-INF/classes/parameters")
				+ (sOS.indexOf("windows") >= 0 ? "applicationConfig-windows.properties"
						: "applicationConfig-linux.properties");
		File file = new File(configueFileName);
		if (file.exists())
		{
			String bak = configueFileName + "." + System.currentTimeMillis();
			file.renameTo(new File(bak));
		}
		file = new File(configueFileName);
		BufferedWriter br = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file)));
		br.write(applicationConfig);
		br.close();

		// update application configue
		Map<String, Object> config = (Map<String, Object>) context
				.getAttribute(Constants.CONFIG);
		ApplicationConfig.init(applicationRootPath, true);
		Properties props = ApplicationConfig.getProperties();
		if (props != null)
		{
			Set keys = props.keySet();
			Iterator it = keys.iterator();
			String key;
			while (it.hasNext())
			{
				key = (String) it.next();
				config.put(key, props.getProperty(key));
			}
		}
		context.setAttribute(Constants.CONFIG, config);

		// 更新URL重定向机制的参数
		URLDistributeFilter.initFilter();

		saveMessage("Application config is updated.");
		return SUCCESS();
	}

	public String showApplicationConfig() throws IOException
	{
		applicationConfig = "";
		String sOS = System.getProperty("os.name").toLowerCase();
		String applicationRootPath = getRequest().getSession()
				.getServletContext().getRealPath("/");
		String configeFileName = Tool.fixPath(Tool.fixPath(applicationRootPath)
				+ "WEB-INF/classes/parameters")
				+ (sOS.indexOf("windows") >= 0 ? "applicationConfig-windows.properties"
						: "applicationConfig-linux.properties");
		File file = new File(configeFileName);
		if (file.exists())
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			String strLine;
			StringBuffer sb = new StringBuffer();
			boolean firstLine = true;
			while ((strLine = br.readLine()) != null)
			{
				if (firstLine)
				{
					sb.append(strLine);
					firstLine = false;
				} else
				{
					sb.append("\n").append(strLine);
				}
			} // end while
			br.close();
			applicationConfig = sb.toString();
		}

		return SUCCESS();
	}

	public String getApplicationConfig()
	{
		return applicationConfig;
	}

	public void setApplicationConfig(String applicationConfig)
	{
		this.applicationConfig = applicationConfig;
	}

	public Page getLoginLogs()
	{
		return loginLogs;
	}

	public String loginLog_list_ADMIN()
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
		
		if(searchValue!=null && !"".equals(searchValue))
		{
			loginLogs = loginLogManager.getPageByUsername(searchValue, page, -1, "id", false);
		}
		else
			loginLogs = loginLogManager.getPage(page, -1, "id", false);
		return SUCCESS();
	}

	public String loginLog_deleteAll_ADMIN()
	{
		loginLogManager.deleteAllLoginLog();
		return SUCCESS();
	}

	public String loginLog_dumpAll_ADMIN()
	{
		// 导出数据
		OutputStream bw;
		File fileWorkPath = new File(Tool.fixPath(ApplicationConfig
				.getApplicationRootPath()
				+ ApplicationConfig.getValue("DownloadPath"))
				+ System.currentTimeMillis()
				+ (int) (Math.random() * 100000000));
		fileWorkPath.mkdirs();
		File exportZipTargetFolder = new File(Tool.fixPath(fileWorkPath
				.getAbsolutePath()) + "1");
		exportZipTargetFolder.mkdir();
		File exportFolder = new File(Tool.fixPath(exportZipTargetFolder
				.getAbsolutePath()) + "LoginLog");
		exportFolder.mkdir();

		java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		int pageNo = 1;
		Page page = loginLogManager.getPage(pageNo++, 1000, "id", true);
		List<LoginLog> list;
		try
		{
			bw = new FileOutputStream(Tool.fixPath(exportFolder
					.getAbsolutePath()) + "data.csv");
			bw.write("time,user name,ip,memo\n".getBytes());
			while ((list = page.getList()) != null && list.size() > 0)
			{
				for (LoginLog item : list)
				{
					bw.write(simpleDateFormat.format(item.getTime()).getBytes());
					bw.write(",".getBytes());
					bw.write(item.getUserName().getBytes());
					bw.write(",".getBytes());
					bw.write(item.getIp().getBytes());
					bw.write(",".getBytes());
					bw.write(item.getMemo().getBytes());
					bw.write("\n".getBytes());
				}
				page = loginLogManager.getPage(pageNo++, 1000, "id", true);
			}
			bw.close();
		} catch (Exception ex)
		{
		}

		String zipFilePath = Tool.fixPath(fileWorkPath.getAbsolutePath())
				+ "LoginLog.zip";
		try
		{
			Tool.zip(zipFilePath, exportZipTargetFolder.getAbsolutePath());
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		downloadPath = Tool.StringsReplace(
				zipFilePath.substring(ApplicationConfig
						.getApplicationRootPath().length()), "\\", "/");

		saveMessage("Export, done!");

		return SUCCESS();
	}

	public String getDownloadPath()
	{
		return downloadPath;
	}

	public String routine_manage_ADMIN()
	{
		return SUCCESS();
	}

	public String adminRoutine_ADMIN_CONTEST()
	{
		return SUCCESS();
	}

	public void setSetBatchStarProblems(String setBatchStarProblems)
	{
		this.setBatchStarProblems = setBatchStarProblems;
	}

	public String starProblem_setBatch_ADMIN()
	{
		return SUCCESS();
	}

	public String problem_onlineJudgeTestPage_ADMIN()
	{
		return SUCCESS();
	}
	
	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	
	
}

