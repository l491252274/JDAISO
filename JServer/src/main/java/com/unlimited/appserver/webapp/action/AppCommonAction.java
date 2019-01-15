package com.unlimited.appserver.webapp.action;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.opensymphony.xwork2.Preparable;
import com.unlimited.oj.util.ApplicationConfig;
import com.unlimited.oj.webapp.action.BaseAction;

public class AppCommonAction extends BaseAction implements Preparable{
	
	private static final long serialVersionUID = 1392527123459488875L;

	private String returnString=null;
	private String token=null;
	
	@Override
	public void prepare() {
		super.prepare();
		token=getRequest().getParameter("token");
	}
	
	public String app_getVersion_PUBLIC()
	{
		returnString = ApplicationConfig.getValue("appVersion");
		if(returnString==null) returnString="";
		return "appReturn";
	}

	public String getReturnString() {
		return returnString;
	}
}
