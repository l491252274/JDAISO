package com.unlimited.webserver.webapp.action;

import com.unlimited.oj.model.*;
import com.unlimited.oj.dao.*;
import com.unlimited.oj.service.*;

import org.springframework.security.AccessDeniedException;
import org.springframework.security.context.SecurityContextHolder;
 
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Preparable;
import com.unlimited.oj.util.Tool;

import com.unlimited.oj.webapp.action.BaseAction;
import com.unlimited.oj.webapp.util.RequestUtil;

import org.springframework.mail.MailException;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationTrustResolver;
import org.springframework.security.AuthenticationTrustResolverImpl;
import org.springframework.security.AccessDeniedException;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.struts2.ServletActionContext;
import com.unlimited.oj.Constants;
import com.unlimited.oj.dao.support.Page;
import com.unlimited.oj.model.*;
import com.unlimited.oj.pojo.DataChart;
import com.unlimited.oj.webapp.util.RequestUtil;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.MailException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.sql.Timestamp;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.unlimited.oj.enums.*;
import com.unlimited.oj.util.ApplicationConfig;
import com.unlimited.oj.util.LanguageType;
import com.unlimited.oj.util.Tool;
import com.unlimited.webserver.model.Lab;
import com.unlimited.webserver.service.LabManager;

/**
 * Action for facilitating Lab Management feature.
 */
public class LabAction extends BaseAction
{

	//private static final long serialVersionUID = 6776558938712115191L;
	private Page labs;
	private Lab lab;
	private List<Lab> labList;
    private int id;
    //public LabManager labManager;
    
    // 供groupMission statistics使用
    private String statisticsMsg; // 页面显示信息
    private String figData; // 画图数据
    
    private String charger;
    
	/**
	 * Grab the entity from the database before populating with request
	 * parameters
	 */

	private void lab_prepare()
	{
		try
		{
			if (getRequest().getMethod().equalsIgnoreCase("get"))
			{
				// if a lab's id is passed in
				String labId = getRequest().getParameter("id");
				if (labId == null)
					labId = getRequest().getParameter("labId");
				if (labId != null && !"".equals(labId))
					lab = labManager.getLab(labId);
			} else if (getRequest().getMethod().equalsIgnoreCase("post"))
			{
				String labId = getRequest().getParameter("lab.id");
				// prevent failures on new
				if (labId != null && !"".equals(labId))
					lab = labManager.getLab(labId);
			}
		} catch (Exception ex)
		{
		}
		
	}

	@Override
	public void prepare()
	{
		super.prepare();
		lab_prepare();
		
	}

	/**
	 * Holder for labs to display on list screen
	 * 
	 * @return list of labs
	 */
	public Page getLabs()
	{
		return labs;
	}

	public void setLabid(long id)
	{
		this.lab.id = id;
	}
	public long getLabid()
	{
		return lab.id;
	}
	public Lab getLab()
	{
		return lab;
	}

	public void setLab(Lab lab)
	{
		this.lab = lab;
	}
	
	public String getLabroomid()
	{
		return lab.roomid;
	}

	public void setLab(String roomid)
	{
		this.lab.roomid = lab.roomid;
	}
	
	public String getLabfloor()
	{
		return lab.floor;
	}

	public void setLabfloor(String floor)
	{
		this.lab.floor = lab.floor;
	}
	
	public String lab_sucess_PUBLIC()
	{
		return SUCCESS;
	}
	
	public String Lab_add_PUBLIC()
	{
		/*
    		/*test for search
    		Lab aa = new Lab();
    		aa.setFloor("aa");
    		aa.setId(1);
    		aa.setRoomid("11");
    		Lab bb = labmanager.getLabByroomid("11");
    		System.out.println(bb.getFloor());
    		*/
    		/*test for addlab
    		Lab aa = new Lab();
    		aa.setFloor("43aa36");
    		aa.setId(4);
    		aa.setRoomid("431122");
    		labmanager.saveLab(aa);
    		*/
    		/*test for delete
    		labmanager.removeLab("2");
    		*/
    		/*test for modify
    		Lab aa = new Lab();
    		aa.setFloor("modify");
    		aa.setId(3);
    		aa.setRoomid("1");
    		labmanager.update(aa);
    		*/
		String labids = getRequest().getParameter("labid");
		String labroomid = getRequest().getParameter("roomid");
		String labfloor = getRequest().getParameter("floor");
		  
		long labid = Long.parseLong(labids);
		
		
		if(labids==null)
		{
			saveMessage("labid is null.");
			return SUCCESS;
		}
		if(labroomid==null)
		{
			saveMessage("labroomid is null");
			return SUCCESS;
		}
		if(labfloor==null)
		{
			saveMessage("labfloor is null");
			return SUCCESS;
		}
		
 		try
		{
 			 
 			Lab testlab = new Lab();
 			testlab.setFloor(labfloor);
 			testlab.setId(labid);
 			testlab.setRoomid(labroomid);
			//此处lab无效
			labManager.saveLab(testlab);
			 
			saveMessage(labroomid + " is Added.");
		} catch (Exception ex)
		{
			ex.printStackTrace();
			saveMessage("Add lab fail!");
			return SUCCESS;
		}
 		return SUCCESS;
	}
		
	/**
	 * Delete the lab.
	 * 
	 * @return success
	 * @throws IOException
	 */
	public String Lab_delete_ADMIN() throws IOException
	{
		String labroomid = getRequest().getParameter("roomid");
		if (labroomid == null)
		{
			saveMessage("labroomid is null");
			return SUCCESS;
		}
		labManager.removeLab(lab.getId().toString());
		return SUCCESS();
	}

	public String lab_search_PUBLIC() throws IOException
	{
		String labroomid = getRequest().getParameter("roomid");
		if (labroomid == null)
		{
			saveMessage("labroomid is null");
			return SUCCESS;
		}
		lab = labManager.getLabByroomid(labroomid);
		/*
		 * 这里我不知道怎么反射到JSP上,先用savemessage
		 * 
		 * */
		saveMessage("lab: "+lab.id + "\nroomid: " + lab.roomid + "\nfloor: " + lab.floor);
		return SUCCESS();
		
	}
	public String lab_update_ADMIN() 
	{
		String labids = getRequest().getParameter("labid");
		String labroomid = getRequest().getParameter("roomid");
		String labfloor = getRequest().getParameter("floor");
		long labid = Long.parseLong(labids);
		
		
		if(labids==null)
		{
			saveMessage("labid is null.");
			return SUCCESS;
		}
		if(labroomid==null)
		{
			saveMessage("labroomid is null");
			return SUCCESS;
		}
		if(labfloor==null)
		{
			saveMessage("labfloor is null");
			return SUCCESS;
		}
		
 		try
		{
			
 			Lab testlab = new Lab();
 			testlab.setFloor(labfloor);
 			testlab.setId(labid);
 			testlab.setRoomid(labroomid);
			labManager.saveLab(testlab);
			saveMessage(labroomid + " is updated.");
		} catch (Exception ex)
		{
			saveMessage("update lab fail!");
			return SUCCESS;
		}
 		return SUCCESS;
		
	}

	/**
	 * Fetch all labs from database and put into local "labs" variable for
	 * retrieval in the UI.
	 * 
	 * @return "success" if no exceptions thrown
	 */
	public String lab_list_ADMINEXAM_ADMINEXERCISE_ADMINCONTEST_ROLES()
	{
		return lab_list_PUBLIC();
	}
	public String lab_list_PUBLIC()
	{
		// labs = labManager.getLabs();
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
		labs = labManager.getPage(page, -1, "id", true);
		List<Lab> list = labs.getList();
		List newList = new ArrayList();
		Lab preLab = null;
		int n = 0;
		for (Lab u : list)
		{
			if (preLab != null && u.getId().equals(preLab.getId()))
			{
				n++;
			} else
			{
				newList.add(u);
			}
			;
			preLab = u;
		}
		if (n > 0)
		{
			labs.setList(newList);
		}
		return SUCCESS();
	}
}
