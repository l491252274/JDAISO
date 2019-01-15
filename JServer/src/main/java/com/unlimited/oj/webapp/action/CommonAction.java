package com.unlimited.oj.webapp.action;

import net.sf.json.JSONObject;

import org.hibernate.Hibernate;
import org.springframework.security.AccessDeniedException;
import com.unlimited.oj.Constants;
import com.unlimited.oj.dao.support.Page;
import com.unlimited.oj.enums.OjTreeNodeType;
import com.unlimited.oj.enums.TreeNodeType;
import com.unlimited.oj.model.*;
import com.unlimited.oj.pojo.DataProblem;
import com.unlimited.oj.service.UserExistsException;
import com.unlimited.oj.util.*;

import com.unlimited.oj.webapp.filter.UojSimplePageCachingFilter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Action for facilitating News Management feature.
 */
public class CommonAction extends BaseAction
{

	private static final long serialVersionUID = 6733222938712115191L;

    private List<OjTreeNode> ojTreeNodes;
    private OjTreeNode ojTreeNode;
    private OjTreeNode ojTreeNodeParent;
    private String currentPath;
    private Page keyToProblems;
    private List<Menu> menuList;
    private Menu menu;

    private String clozeCode;
    private String searchKey;  // search
    private String searchValue; // search
    private String searchPage;
    private String searchSource;
    
	private List<Map<String, String>> ojTreeNodeTypeList = null;
    private List<DataProblem> problemNodes;
    
    File inDataFile;
    File outDataFile;
    File filePicture;
    private String fileContentType;
    private String fileFileName;
    private String name;

	private Page tagList;
	
	//用在keyToProblem中
	private String comment;
	private String evaluate;
	private Boolean hasAccepted;
	
	//用于返回ajex调用时的结果
	private String returnValue;
	

	/**
	 * Grab the entity from the database before populating with request
	 * parameters
	 */
	public void prepare()
	{
		super.prepare();

		ojTreeNode_prepare();
		menu_prepare();
	}
	
    
    public void menu_prepare()
    {
        if (getRequest().getMethod().equalsIgnoreCase("post"))
        {// post method
            String menuId = getRequest().getParameter("menu.id");
            if (menuId != null && !"".equals(menuId))
            	menu = commonManager.getMenu(menuId);
        } else
        {// get method
            String menuId = getRequest().getParameter("menuId");
            if (menuId != null && !"".equals(menuId))
            {
            	menu = commonManager.getMenu(menuId);
            }
        }
    }


    public void ojTreeNode_prepare()
	{
		if (getRequest().getMethod().equalsIgnoreCase("post"))
		{
			String ojTreeNodeId = getRequest().getParameter("ojTreeNode.id");
			// prevent failures on new
			if (ojTreeNodeId != null && !"".equals(ojTreeNodeId))
				ojTreeNode = ojTreeNodeManager.get(new Long(ojTreeNodeId));
		} else
		{// get
			String ojTreeNodeId = getRequest().getParameter("ojTreeNodeId");
			if (ojTreeNodeId != null && !"".equals(ojTreeNodeId))
				ojTreeNode = ojTreeNodeManager.get(new Long(ojTreeNodeId));
		}
		
		ojTreeNodeTypeList = getOjTreeNodeTypeListMap();
	}

	public String test()
	{
		return SUCCESS();
	}

	public String showGroupList()
	{
		return SUCCESS();
	}

	public String showGroup()
	{
		return SUCCESS();
	}

	public Page getTagList()
	{
		return tagList;
	}

    private List<Map<String, String>> getOjTreeNodeTypeListMap()
    {
        Locale locale = LocaleContextHolder.getLocale();
        ResourceBundle rb = ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale);
        List<Map<String, String>> ojTreeNodeTypeListMap = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("key", new Integer(OjTreeNodeType.CATALOG.getValue()).toString());
        map.put("value", rb.getString("enums.OjTreeNodeType.CATALOG"));
        ojTreeNodeTypeListMap.add(map);

        map = new HashMap<String, String>();
        map.put("key", new Integer(OjTreeNodeType.PROBLEMNODE.getValue()).toString());
        map.put("value", rb.getString("enums.OjTreeNodeType.PROBLEMNODE"));
        ojTreeNodeTypeListMap.add(map);

        map = new HashMap<String, String>();
        map.put("key", new Integer(OjTreeNodeType.BOARDNODE.getValue()).toString());
        map.put("value", rb.getString("enums.OjTreeNodeType.BOARDNODE"));
        ojTreeNodeTypeListMap.add(map);

        map = new HashMap<String, String>();
        map.put("key", new Integer(OjTreeNodeType.EXAMNODE.getValue()).toString());
        map.put("value", rb.getString("enums.OjTreeNodeType.EXAMNODE"));
        ojTreeNodeTypeListMap.add(map);

        return ojTreeNodeTypeListMap;
    }

	public String ojTreeNode_list_PUBLIC()
	{
		ojTreeNodeParent = null;
		currentPath = "";
		String ojTreeNodeParentId = getRequest().getParameter("pid");

		if (ojTreeNodeParentId != null && !"".equals(ojTreeNodeParentId))
			ojTreeNodeParent = ojTreeNodeManager.get(new Long(
					ojTreeNodeParentId));

		if (ojTreeNodeParent == null)
			ojTreeNodes = ojTreeNodeManager
					.getChildOjTreeNodeByPid(new Long(-1));
		else
		{
			OjTreeNode obj = ojTreeNodeParent;
			do
			{
				currentPath = "<a href='common_ojTreeNode_list_PUBLIC.html?pid="
						+ obj.getId()
						+ "'>"
						+ obj.getName()
						+ "</a>"
						+ ((currentPath == null || "".equals(currentPath)) ? ""
								: " -> " + currentPath);
				obj = ojTreeNodeManager.get(obj.getPid());
			} while (obj != null);
			ojTreeNodes = ojTreeNodeManager
					.getChildOjTreeNodeByPid(ojTreeNodeParent.getId());
		}
		return SUCCESS();
	}

	public String ojTreeNode_edit_ADMINEXERCISE_ADMINEXAM_ADMINCONTEST_ROLES() throws IOException
	{
		// HttpServletRequest request = getRequest();
		if (ojTreeNode==null)
		{// Create a new contest
			ojTreeNode = new OjTreeNode();
			ojTreeNode.setPid(new Long(-1));
			ojTreeNode.setOrderNum(999);
			String pid = getRequest().getParameter("pid");
			if (pid != null && !"".equals(pid))
			{
				ojTreeNodeParent = ojTreeNodeManager.get(new Long(pid));
				if (ojTreeNodeParent != null)
				{
					ojTreeNode.setPid(new Long(pid));
					ojTreeNode.setOrderNum(ojTreeNodeManager
							.getOrderNumberOfNewChild(new Long(pid)));
				}
			}
		}
		return SUCCESS();
	}

	public String ojTreeNode_delete_ADMINEXERCISE_ADMINEXAM_ADMINCONTEST_ROLES() throws IOException
	{
		if (ojTreeNode == null)
		{
			saveMessage("ojTreeNode is null");
			return "mainError";
		}
		ojTreeNodeManager.remove(ojTreeNode.getId());

		List<String> args = new ArrayList<String>();
		args.add(ojTreeNode.getTitle());
		saveMessage(getText("ojTreeNode.deleted", args));

		return SUCCESS();
	}

	public String ojTreeNode_save_ADMINEXERCISE_ADMINEXAM_ADMINCONTEST_ROLES() throws IOException
	{
		boolean isNew = (ojTreeNode.getId() == null);
		if (isNew)
			ojTreeNode.setSign(getCurrentUser().getUsername());

		try
		{
			ojTreeNodeManager.save(ojTreeNode);
		} catch (Exception e)
		{
			e.printStackTrace();
			return "mainError";
		}

		return SUCCESS();
	}

	public String ojTreeNode_listProblemNodes_PUBLIC()
	{
		problemNodes = new LinkedList<DataProblem>();
		String ojTreeNodeParentId = getRequest().getParameter("pid");
		if (ojTreeNodeParentId != null && !"".equals(ojTreeNodeParentId))
			ojTreeNodeParent = ojTreeNodeManager.get(new Long(
					ojTreeNodeParentId));

		if (ojTreeNodeParent != null)
		{
			ojTreeNodes = ojTreeNodeManager
					.getVisibleChildOjTreeNodeByPid(ojTreeNodeParent.getId());
			if (ojTreeNodes != null)
			{
				for (OjTreeNode item : ojTreeNodes)
				{
					int type = item.getType();
					if (type == TreeNodeType.ProblemNode.getValue())
					{// 是题目结点
						Long probId = item.getProblemId();
						if (probId != null)
						{
							DataProblem dp = new DataProblem();
							dp.title = item.getName();
							problemNodes.add(dp);
						}
					}
				}
			}
		}
		return SUCCESS();
	}

	public String getCurrentPath()
	{
		return currentPath;
	}

	public List<OjTreeNode> getOjTreeNodes()
	{
		return ojTreeNodes;
	}

	public void setOjTreeNode(OjTreeNode ojTreeNode)
	{
		this.ojTreeNode = ojTreeNode;
	}

	public OjTreeNode getOjTreeNode()
	{
		return ojTreeNode;
	}

	public void setCurrentPath(String currentPath)
	{
		this.currentPath = currentPath;
	}

	public OjTreeNode getOjTreeNodeParent()
	{
		return ojTreeNodeParent;
	}

	public List<DataProblem> getProblemNodes()
	{
		return problemNodes;
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
     * Sends solutions to "mainMenu" when !from.equals("list"). Sends everyone
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
    
    private boolean isLetterAnd_(char ch)
    {
    	return ((ch>='a' && ch<='z') || (ch>='A' && ch<='Z') || ch == '_' );
    }
    
    private int findSubString(StringBuffer code, StringBuffer sb, int pos)
    {
    	if(code==null || sb==null || code.length()<sb.length())
    		return -1;
    	for(;pos<=code.length()-sb.length();pos++)
    	{
    		int j=0;
    		for(; j<sb.length(); j++)
    			if(code.charAt(pos+j)!=sb.charAt(j)) break;
    		if(j>=sb.length()) return pos;
    	}
    	return -1;
    }

    private boolean CheckForbiddenCode(StringBuffer code)
    {
    	Map config = getConfiguration();
    	if(code!=null && code.length()>1 && config.get("ForbiddenCodeFilter")!=null && "true".equalsIgnoreCase((String)config.get("ForbiddenCodeFilter")))
    	{
			int ahfNum=0, ffNum=0, afNum=0;
			String[] ahf = null, ff = null, af = null; 
    		if(config.get("AllowedHeadFiles")!=null)
    		{
    			ahfNum = Integer.parseInt((String)config.get("AllowedHeadFiles"));
    			ahf = new String[ahfNum];
    			for(int i=0; i<ahfNum; i++)
    			{
    				String key = "AHF" + (new Integer(i+1)).toString();
    				if(config.get(key)!=null)
    				{
    					ahf[i] = (String)config.get(key);
    				}
    			}
    		}
    		if(config.get("ForbiddenFunctions")!=null)
    		{
    			ffNum = Integer.parseInt((String)config.get("ForbiddenFunctions"));
    			ff = new String[ffNum];
    			for(int i=0; i<ffNum; i++)
    			{
    				String key = "FF" + (new Integer(i+1)).toString();
    				if(config.get(key)!=null)
    				{
    					ff[i] = (String)config.get(key);
    				}
    			}
    		}
    		if(config.get("AllowedFunctions")!=null)
    		{
    			afNum = Integer.parseInt((String)config.get("AllowedFunctions"));
    			af = new String[afNum];
    			for(int i=0; i<afNum; i++)
    			{
    				String key = "AF" + (new Integer(i+1)).toString();
    				if(config.get(key)!=null)
    				{
    					af[i] = (String)config.get(key);
    				}
    			}
    		}
    		code.append("#ok#");
    		// 判断禁用的头文件
    		int pos1=0, pos2;
    		do
    		{
    			int pos0 = findSubString(code, new StringBuffer("#"), pos1);
    			int pos = findSubString(code, new StringBuffer("include"), pos1);
    			if(pos<0) break;
    			pos1 = pos + 8;
        		while (pos1<code.length() && !isLetterAnd_(code.charAt(pos1))) pos1++;
        		if(pos1>=code.length()) break;
        		pos2=pos1+1;
        		while (pos2<code.length() && isLetterAnd_(code.charAt(pos2))) pos2++;
        		if(pos2>=code.length()) break;
        		String str = code.substring(pos1, pos2);
    			//判断
        		int k=0;
    			for(; k<ahfNum; k++)
    				if(ahf[k].equals(str)) break;
    			if(k>=ahfNum)
    			{
    				saveMessage("Use forbidden head file "+str);
    				return false;
    			}
        		pos1=pos2;
    		}while(true);
    		// 允许可用函数
    		for(int i=0; i<afNum; i++)
			{
    			int nPos = code.indexOf(af[i]);
    			if(nPos>=0)
    			{
    				code.replace(nPos, nPos+af[i].length(), "");
    			}
			}
    		// 判断禁用的函数
    		pos1=0;
    		do
    		{
        		while (pos1<code.length() && !isLetterAnd_(code.charAt(pos1))) pos1++;
        		if(pos1>=code.length()) break;
        		pos2=pos1+1;
        		while (pos2<code.length() && isLetterAnd_(code.charAt(pos2))) pos2++;
        		if(pos2>=code.length()) break;
        		String str = code.substring(pos1, pos2);
    			//判断
        		int k=0;
    			for(; k<ffNum; k++)
    			{
    				if(ff[k].equals(str))
    				{
        				saveMessage("Use forbidden function "+str);
        				return false;
    				}
    			}
        		pos1=pos2;
    		}while(true);
    	}
    	return true;
    }

    public String getSearchKey()
    {
        return searchKey;
    }

    public void setSearchKey(String searchKey)
    {
        this.searchKey = searchKey;
    }

    public String getSearchValue()
    {
        return searchValue;
    }

    public void setSearchValue(String searchValue)
    {
        this.searchValue = searchValue;
    }

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}
    
	public Boolean getHasAccepted() {
		return hasAccepted;
	}
    
	public void setSearchPage(String searchPage) {
		this.searchPage = searchPage;
	}

	public String getSearchPage() {
		return searchPage;
	}

	public String getSearchSource() {
		return searchSource;
	}

	public void setSearchSource(String searchSource) {
		this.searchSource = searchSource;
	}

    public String menu_get_PUBLIC()
    {
    	User cu = getCurrentUser();
    	List<Role> roles = cu.getRoleList();
    	StringBuffer ret = new StringBuffer();
    	ret.append('[');
    	String resourceType = getRequest().getParameter("resourceType");
    	if(resourceType!=null)
    	{
    		List list = commonManager.getMenusByType(resourceType);
	    	if(list!=null)
	    	{
	    		// 可见控制
	    		Map<Long, Menu> mapLocks = new HashMap<Long, Menu>();
		    	for(Object item: list)
		    	{
		    		Menu menu = (Menu)item;
		    		mapLocks.put(menu.getId(), menu);
		    	}
	    		
	    		boolean first = true;
		    	for(Object item: list)
		    	{
		    		// 权限控制
		    		String authority = ((Menu)item).getAuthority();
		    		boolean hasRight = false;
		    		if(authority!=null)
		    		{
		    			String[] rights = authority.split(";");
		    			for(String ri : rights)
		    			{
		    				if("".equals(ri.trim())) continue;
		    				for(Role r:roles)
		    				{
		    					if(r.getName().equalsIgnoreCase(ri))
		    					{
		    						hasRight = true;
		    						break;
		    					}
		    				}
		    			}
		    		}
		    		if(!hasRight) continue;
		    		
		    		// 可见控制，如果该项或其祖先不可见，则其不可见
		    		boolean visible = true;
		    		Menu _ite = (Menu)item; 
		    		do
		    		{
		    			if(!_ite.getVisible()) {visible=false; break;}
		    			_ite = mapLocks.get(_ite.getParentID());
		    			
		    		} while(_ite!=null);
		    		if(!visible) continue;
		    		
		    			
		    		if(first)
		    			ret.append('{');
		    		else
		    			ret.append(",{");
	
		    	    ret.append("\"accessPath\":\"");
		    	    ret.append("/");ret.append(getConfiguration().get(Constants.PROJECT_NAME));
		    	    ret.append(((Menu)item).getAccessPath());ret.append("\",");
		    		ret.append("\"parentID\":");ret.append(((Menu)item).getParentID());ret.append(",");
		    		ret.append("\"resourceGrade\":");ret.append(((Menu)item).getResourceGrade());ret.append(",");
		    		ret.append("\"resourceID\":");ret.append(((Menu)item).getId());ret.append(",");
		    		ret.append("\"resourceName\":\"");ret.append(((Menu)item).getResourceName());ret.append("\",");
		    		ret.append("\"resourceOrder\":");ret.append(((Menu)item).getResourceOrder());
		    		
		    		ret.append('}');
		    		first = false;
		    	}
	    	}
    	}
    	
    	returnValue = (ret.append(']')).toString();
    	//System.out.println(returnValue);
    	return SUCCESS;
    }

	public String getReturnValue() {
		return returnValue;
	}

	public List<Map<String, String>> getOjTreeNodeTypeList() {
		return ojTreeNodeTypeList;
	}

	public String menu_list_ADMIN()
	{
		menuList = commonManager.getMenusAll();
		return SUCCESS;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public List<Menu> getMenuList() {
		return menuList;
	}
    
	public String menu_edit_ADMIN()
	{
		if(menu == null)
		{
	    	String sParentId = getRequest().getParameter("parentId");
	    	if(sParentId==null)
	    	{
	    		saveMessage("parentId is null");
	    		return "mainPage";
	    	}
	    	menu = new Menu();
	    	menu.setParentID(Long.parseLong(sParentId));
	    	menu.setAuthor(currentUser.getUsername());
	    	menu.setVisible(true);
		}
		return SUCCESS;
	}
	
	public String menu_save_ADMIN()
	{
		if(menu == null)
		{
			saveMessage("menu is null");
			return "mainPage";
		}
		try
		{
			commonManager.saveMenu(menu);
		}catch(Exception ex){}
		return SUCCESS;
	}

	private List<Menu> list = new ArrayList<Menu>();
	private void findSubmenus(Menu menu)
	{
		list.add(menu);
		List list = commonManager.getMenusByParentId(menu.getId());
		if(list!=null)
		{
			for(Menu item: (List<Menu>)list)
				findSubmenus(item);
		}
	}
	
	public String menu_delete_ADMIN()
	{
		if(menu == null)
		{
			saveMessage("menu is null");
			return "mainPage";
		}
		
		list.clear();
		findSubmenus(menu);
		try
		{
			commonManager.deleteAllMenu(list);
		}catch(Exception ex){ex.printStackTrace();}
		return SUCCESS;
	}
}
