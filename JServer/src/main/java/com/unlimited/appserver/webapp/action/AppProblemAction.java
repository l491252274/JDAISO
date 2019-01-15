package com.unlimited.appserver.webapp.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Preparable;
import com.unlimited.appserver.enums.AppEcho;
import com.unlimited.appserver.model.AppProblemTreeKeyString;
import com.unlimited.appserver.util.HttpRequest;
import com.unlimited.appserver.webapp.OJAPI;
import com.unlimited.oj.webapp.action.BaseAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 
 * 对于安卓APP中问题相关的动作的action
 * 用returnString返回需要的结果
 * 
 * @author 佳洋
 * 
 */
public class AppProblemAction extends BaseAction implements Preparable{

	private static final long serialVersionUID = -2077215408148107819L;
	
	private String returnString;
	private String problemId=null;
	private String token=null;
	private String parentId=null;
	
	public String user_echo_PUBLIC(){
		setReturnString("hello world");
		return SUCCESS;
	}
	
	
	/**
	 * 
	 * 根据问题的ID获取一个问题
	 * 
	 * @request problemId:需要获取的问题的id
	 * @request token:该用户的token
	 * 
	 * @response echo:返回码
	 * @response message:错误信息，若无错误则为null
	 * @response id:问题的id
	 * @response title:问题的标题
	 * @response timeLimit:问题的时间限制
	 * @response memoryLimit:问题的空间限制
	 * @response description:问题的描述
	 * @response input:问题输入的描述
	 * @response output:问题输出的描述
	 * @response sampleInput:样例输入
	 * @response sampleOutput:样例输出
	 * @response source:来源
	 * @response tip:帮助
	 * 
	 * 
	 */
	public String problem_getProblem_PUBLIC() {

		Map<String, String> map = new HashMap<String, String>();
		map.put("problemId", problemId);
		map.put("token", token);
		JSONObject jsonObject = new JSONObject();

		if (problemId == null) {
			jsonObject.put("echo", AppEcho.PARAMETER_WRONG.getCode());
			jsonObject.put("message", "缺少问题ID");
			returnString = jsonObject.toString();
			return SUCCESS;
		}

		if (token == null) {
			jsonObject.put("echo", AppEcho.PARAMETER_WRONG.getCode());
			jsonObject.put("message", "缺少token");
			returnString = jsonObject.toString();
			return SUCCESS;
		}

		try {
			@SuppressWarnings("deprecation")
			JSONObject json = new JSONObject(HttpRequest.doGet(OJAPI.GET_PROBLEM_BY_ID, map));
			int echo = json.getInt("echo");
//			System.out.println("");
//			System.out.println("");
//			System.out.println("");
//			System.out.println("echo="+echo);
			switch (echo) {
			case 0:
				jsonObject = json;
				break;
			case 2:
				jsonObject.put("echo", AppEcho.RESULT_NULL.getCode());
				jsonObject.put("message", "结果为空");
				break;
			case 3:
				jsonObject.put("echo", AppEcho.TOKEN_WRONG.getCode());
				jsonObject.put("message", "token错误");
				break;
			case 4:
				System.out.println("no access, problemId=" + problemId);
				jsonObject.put("echo", AppEcho.PERMISSION_DENIED.getCode());
				jsonObject.put("message", "权限不足");
				break;

			default:
				jsonObject.put("echo", AppEcho.UNKNOW.getCode());
				jsonObject.put("message", "未知错误");
				break;
			}
			returnString = jsonObject.toString();
		} catch (IOException e) {
			e.printStackTrace();
			jsonObject.put("echo", AppEcho.SERVLCE_WRONG.getCode());
			jsonObject.put("message", "服务器无法连接到OJ服务器");
			returnString = jsonObject.toString();
		}
		return SUCCESS;
	}
	
	/**
	 * 根据用户传递的Tree id，获取该id的子孙结点信息
	 * 
	 * @request token:用户的token
	 * @request parentId:查询的结点的id
	 * 
	 * 
	 * @response echo:返回码
	 * @response message:错误信息，若无错误则为null
	 * @response notes:{ id:结点id name:结点名称 type:结点类型 parentId:父亲结点id
	 *           problemIdLink:连接的问题的id order:在共级中排列的先后顺序 }返回的结点的信息
	 */
	public String problem_getProblemTreeByParentId_PUBLIC() {
		
		JSONObject jsonObject = new JSONObject();

		if (parentId == null) {
			jsonObject.put("echo", AppEcho.PARAMETER_WRONG.getCode());
			jsonObject.put("message", "缺少问题结点ID");
			returnString = jsonObject.toString();
			return SUCCESS;
		}

		if (token == null) {
			jsonObject.put("echo", AppEcho.PARAMETER_WRONG.getCode());
			jsonObject.put("message", "缺少token");
			returnString = jsonObject.toString();
			return SUCCESS;
		}
		
		//如果要获取的是根节点，则返回所有树的信息
		if(parentId.equals("-1")){
			
			List<AppProblemTreeKeyString> list = appProblemTreeKeyStringManager.getAllKeyString();

			ArrayList<Long> result = new ArrayList<Long>();
			/*
			 * 将获得的id放入result中
			 */
			for (AppProblemTreeKeyString key : list) {
				
				try {
					Map<String, String> map = new HashMap<String, String>();
					map.put("token", token);
					map.put("keystring", key.getKeyString());
					@SuppressWarnings("deprecation")
					JSONObject json = new JSONObject(HttpRequest.doGet(OJAPI.GET_PROBLEM_TREE_BY_KEYSTRING, map));
					if (json.getInt("echo") == 0) {
						result.add(json.getLong("nodeId"));
					}

				} catch (IOException e) {
					e.printStackTrace();
					jsonObject.put("echo", AppEcho.SERVLCE_WRONG.getCode());
					jsonObject.put("message", "服务器无法连接到OJ服务器");
					returnString = jsonObject.toString();
					return SUCCESS;
				}
			}
			
			if(result.size()==0){
				jsonObject.put("echo", AppEcho.RESULT_NULL.getCode());
				jsonObject.put("message", "结果为空，可能为token错误导致");
				returnString=jsonObject.toString();
				return SUCCESS;
			}


			for (Long id : result) {
				
				try {
					
					Map<String, String> map=new HashMap<String, String>();
					map.put("token", token);
					map.put("parentId", String.valueOf(id));
					@SuppressWarnings("deprecation")
					JSONObject json=new JSONObject(HttpRequest.doGet(OJAPI.GET_PROBLEM_TREE,map));
					
					JSONArray treeArray=json.getJSONArray("notes");
					for(int i=0;i<treeArray.length();i++){
						JSONObject tree=treeArray.getJSONObject(i);
						if(tree.getLong("id")==id){
							tree.remove("parentId");
							tree.put("parentId", -1);
						}
						jsonObject.put("notes", tree);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					jsonObject.put("echo", AppEcho.SERVLCE_WRONG.getCode());
					jsonObject.put("message", "服务器无法连接到OJ服务器");
					returnString = jsonObject.toString();
					return SUCCESS;
				}
			}
			jsonObject.put("echo", AppEcho.SUCCESS.getCode());
			returnString=jsonObject.toString();

			
			return SUCCESS;
		}
		
		

		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("parentId", parentId);
			map.put("token", token);
			@SuppressWarnings("deprecation")
			JSONObject json = new JSONObject(HttpRequest.doGet(OJAPI.GET_PROBLEM_TREE, map));
			int echo = json.getInt("echo");
			switch (echo) {
			case 0:
				jsonObject = json;
				break;
			case 2:
				jsonObject.put("echo", AppEcho.RESULT_NULL.getCode());
				jsonObject.put("message", "结果为空");
				break;
			case 3:
				jsonObject.put("echo", AppEcho.TOKEN_WRONG.getCode());
				jsonObject.put("message", "token错误");
				break;
			case 4:
				jsonObject.put("echo", AppEcho.PERMISSION_DENIED.getCode());
				jsonObject.put("message", "权限不足");
				break;

			default:
				jsonObject.put("echo", AppEcho.UNKNOW.getCode());
				jsonObject.put("message", "未知错误");
				break;
			}
			returnString = json.toString();
		} catch (IOException e) {
			e.printStackTrace();
			jsonObject.put("echo", AppEcho.SERVLCE_WRONG.getCode());
			jsonObject.put("message", "服务器无法连接到OJ服务器");
			returnString = jsonObject.toString();
		}
		return SUCCESS;
	}

	


	
	@Override
	public void prepare() {
		super.prepare();
		problemId=getRequest().getParameter("problemId");
		token=getRequest().getParameter("token");
		parentId=getRequest().getParameter("parentId");
	}

	public String getReturnString() {
		return returnString;
	}

	public void setReturnString(String returnString) {
		this.returnString = returnString;
	}

}
