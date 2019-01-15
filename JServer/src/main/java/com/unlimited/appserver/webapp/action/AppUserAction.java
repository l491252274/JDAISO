package com.unlimited.appserver.webapp.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.opensymphony.xwork2.Preparable;
import com.unlimited.appserver.dao.exception.AppUserNotFoundException;
import com.unlimited.appserver.enums.AppEcho;
import com.unlimited.appserver.enums.ServerEcho;
import com.unlimited.appserver.model.AppUser;
import com.unlimited.appserver.util.HttpRequest;
import com.unlimited.appserver.webapp.OJAPI;
import com.unlimited.oj.util.Tool;
import com.unlimited.oj.webapp.action.BaseAction;

/**
 * 
 * 对于安卓APP中用户相关的动作的action 用returnString返回需要的结果
 * 
 * @author 佳洋
 * 
 */
public class AppUserAction extends BaseAction implements Preparable {
	private static final long serialVersionUID = 677614448712115191L;

	private String returnString;

	private String account = null;
	private String newPassword = null;
	private String password = null;
	private String token = null;
	private String mail = null;
	private String nickname = null;
	private String userId = null;
	private String userAccount = null;

	public void prepare() {
		super.prepare();
		account = getRequest().getParameter("account");
		password = getRequest().getParameter("password");
		userId = getRequest().getParameter("userId");
		mail = getRequest().getParameter("mail");
		nickname = getRequest().getParameter("nickname");
		token = getRequest().getParameter("token");
		newPassword = getRequest().getParameter("newPassword");
		userAccount = getRequest().getParameter("userAccount");

	}

	/**
	 * 返回当前最新客户端的版本
	 * 
	 * @response version:当前的版本
	 */
	public String user_echo_PUBLIC() {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("version", "1.0");

		returnString = jsonObject.toString();
		return SUCCESS;
	}

	/**
	 * check if the account is exists in database
	 * 
	 * 
	 * @request: account:account to query
	 * 
	 * @response echo:the return state
	 * @response message:error message, null if success
	 * @response exists: true if account exists, false otherwise
	 * 
	 */
	public String user_checkAccountExists_PUBLIC() {

		JSONObject jsonObject = new JSONObject();

		// 判断参数是否齐全
		if (account == null) {

			jsonObject.put("echo", AppEcho.PARAMETER_WRONG.getCode());
			jsonObject.put("message", "缺少账号");
			returnString = jsonObject.toString();
			return SUCCESS;
		}

		// 判断账号是否存在
		try {
			appUserManager.getAppUserByAccount(account);
		} catch (AppUserNotFoundException e) {
			e.printStackTrace();
			jsonObject.put("echo", AppEcho.SUCCESS.getCode());
			jsonObject.put("exists", false);
			returnString = jsonObject.toString();
			return SUCCESS;
		}

		jsonObject.put("echo", AppEcho.SUCCESS.getCode());
		jsonObject.put("exists", true);
		returnString = jsonObject.toString();

		return SUCCESS;
	}

	/**
	 * 
	 * update appuser if the account is exists, update it if not exists, create
	 * it
	 * 
	 * @return status
	 * 
	 * @request userId: id of appuser, can be null
	 * @request account: account of user, can be null id and account can not
	 *          both null
	 * @request password: old password of the user ,can not be null, if the
	 *          action is create user, this is the initial password
	 * @request newPassword: the newPassword of the user, if it is null,
	 *          password will not be change
	 * @request token: the token of the user, if it is null, token will not be
	 *          change
	 * @request mail: the mail of the user, if it is null, mail will not be
	 *          change
	 * @requset nickname: the nickname of the user, if it is null, nickname will
	 *          not be change
	 * 
	 * @response echo:the return state
	 * @response message:error message, null if success
	 * @response id:the id of the user
	 * 
	 */
	public String user_updateAppAccount_PUBLIC() {
		JSONObject jsonObject = new JSONObject();

		if (userId == null && account == null) {
			jsonObject.put("echo", AppEcho.PARAMETER_WRONG.getCode());
			jsonObject.put("message", "账号和userId不能同时为空");
			returnString = jsonObject.toString();
			return SUCCESS;
		}

		if (password == null) {
			jsonObject.put("echo", AppEcho.PARAMETER_WRONG.getCode());
			jsonObject.put("message", "密码不能为空");
			returnString = jsonObject.toString();
			return SUCCESS;
		}

		AppUser user = null;
		boolean useAccount = false;// 如果这个变量为true，则用account来获取用户信息

		/*
		 * 如果存在id，则用id获取用户信息 如果不存在id，则用account获取用户信息 如果数据库不存在对应数据，直接返回对应信息
		 */
		if (userId != null) {
			try {
				user = appUserManager.getUser(userId);
			} catch (AppUserNotFoundException e) {
				e.printStackTrace();
				useAccount = true;
			}
		} else {
			useAccount = true;
		}

		if (useAccount) {
			try {
				user = appUserManager.getAppUserByAccount(account);
			} catch (AppUserNotFoundException e) {
				e.printStackTrace();
			}
		}

		/*
		 * 如果user==null，则创建一个用户 否则更新用户信息
		 */
		if (user == null) {
			/*
			 * 新用户的初始nickname为他的账号
			 * 
			 */
			user = new AppUser();
			user.setAccount(account);
			user.setPassword(password);
			user.setNickname(account);
			appUserManager.save(user);

			try {
				user = appUserManager.getAppUserByAccount(account);
			} catch (AppUserNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jsonObject.put("echo", AppEcho.SUCCESS.getCode());
			jsonObject.put("id", user.getId());
			returnString = jsonObject.toString();

		} else {
			/*
			 * 更新用户信息
			 */
			if (newPassword == null) {
				newPassword = password;
			}
			if (nickname == null) {
				nickname = user.getNickname();
			}
			if (mail == null) {
				mail = user.getMail();
			}
			if (token == null) {
				token = user.getToken();
			}
			user.setPassword(newPassword);
			user.setNickname(nickname);
			user.setMail(mail);
			user.setToken(token);
			appUserManager.save(user);

			jsonObject.put("echo", AppEcho.SUCCESS.getCode());
			jsonObject.put("id", user.getId());
			returnString = jsonObject.toString();

		}
		return SUCCESS;
	}

	/**
	 * 检查一个用户的账号与其密码是否匹配
	 * 
	 * @request account:用户的账号
	 * @request password:用户的密码
	 * 
	 * @response echo:返回码
	 * @response message:错误信息，如无错误则为null
	 * @response id:用户的id
	 * @response mail:用户的mail
	 * @response token:用户的token
	 * @response nickname:用户的nickname
	 * 
	 */
	public String user_checkAppUserPassword_PUBLIC() {

		JSONObject jsonObject = new JSONObject();

		/*
		 * 检查参数是否完整
		 */
		if (account == null) {
			jsonObject.put("echo", AppEcho.PARAMETER_WRONG.getCode());
			jsonObject.put("message", "缺少账号");
			returnString = jsonObject.toString();
			return SUCCESS;
		}
		if (password == null) {
			jsonObject.put("echo", AppEcho.PARAMETER_WRONG.getCode());
			jsonObject.put("message", "缺少密码");
			returnString = jsonObject.toString();
			return SUCCESS;
		}

		try {
			if (appUserManager.isPasswordCorrect(account, password)) {// 如果密码正确，则返回id
				jsonObject.put("echo", AppEcho.SUCCESS.getCode());
				AppUser appUser = appUserManager.getAppUserByAccount(account);
				jsonObject.put("id", appUser.getId());
				jsonObject.put("mail", appUser.getMail());
				jsonObject.put("token", appUser.getToken());
				jsonObject.put("nickname", appUser.getNickname());
				returnString = jsonObject.toString();
			} else {
				// 密码不正确
				jsonObject.put("echo", AppEcho.PASSWORD_WRONG.getCode());
				jsonObject.put("message", "密码错误");
				returnString = jsonObject.toString();
			}
		} catch (AppUserNotFoundException e) {
			e.printStackTrace();
			// 账号不存在
			jsonObject.put("echo", AppEcho.ACCOUNT_NOT_EXISTS.getCode());
			jsonObject.put("message", "账号不存在");
			returnString = jsonObject.toString();
		}
		return SUCCESS;
	}

	/**
	 * 通过id或account获取一个用户的信息 如果是要获取本人信息，需要传递查询者的账号和密码 返回用户的id、账号、昵称和邮箱
	 * 如果被查询的用户是查询者本人，则再返回token
	 * 
	 * @request userAccount:查询者的账号
	 * @request password:查询者的密码,如果不是查询本人的信息，可以为空
	 * @request account:查询的账号
	 * @request userId:查询的id account和id不能同时为空
	 * 
	 * 
	 * @response echo:返回码
	 * @response message:错误信息，若无错误则为null
	 * @response id:返回的id
	 * @response account:返回的账号
	 * @response mail:返回的邮箱
	 * @response nickname:返回的昵称
	 * @response token:返回的token
	 * 
	 */
	public String user_getAppUser_PUBLIC() {
		JSONObject jsonObject = new JSONObject();

		/*
		 * 判断参数是否合法
		 */
		if (userId == null && account == null) {
			jsonObject.put("echo", AppEcho.PARAMETER_WRONG.getCode());
			jsonObject.put("message", "缺少被查询者信息");
			returnString = jsonObject.toString();
			return "appReturn";
		}

		/*
		 * 获取查询的用户的信息
		 */
		AppUser user = null;
		if (account != null) {
			try {
				user = appUserManager.getAppUserByAccount(account);
			} catch (AppUserNotFoundException e) {
				e.printStackTrace();
				jsonObject.put("echo", AppEcho.ACCOUNT_NOT_EXISTS.getCode());
				jsonObject.put("message", "查询的账号不存在");
				returnString = jsonObject.toString();
				return "appReturn";
			}
		}
		if (userId != null) {
			try {
				user = appUserManager.getUser(userId);
			} catch (AppUserNotFoundException e) {
				e.printStackTrace();
				jsonObject.put("echo", AppEcho.ACCOUNT_NOT_EXISTS.getCode());
				jsonObject.put("message", "查询的账号不存在");
				returnString = jsonObject.toString();
				return "appReturn";
			}
		}

		jsonObject.put("echo", AppEcho.SUCCESS.getCode());
		/*
		 * 验证查询的用户是否和被查询的用户一致 如果一致，则将token添加至返回内容中
		 */
		if (userAccount != null && password != null && userAccount.equals(user.getAccount())) {
			try {
				if (appUserManager.isPasswordCorrect(userAccount, password)) {
					jsonObject.put("token", user.getToken());
				} else {
					jsonObject.put("echo", AppEcho.PASSWORD_WRONG.getCode());
					jsonObject.put("message", "密码错误，无法获取用户token");
				}
			} catch (AppUserNotFoundException e) {
				e.printStackTrace();
			}
		}

		jsonObject.put("id", user.getId());
		jsonObject.put("account", user.getAccount());
		jsonObject.put("mail", user.getMail());
		jsonObject.put("nickname", user.getNickname());
		returnString = jsonObject.toString();

		return "appReturn";
	}

	public String getReturnString() {
		return returnString;
	}
	
	public String user_signIn_PUBLIC()
	{
		JSONObject jsonObject = new JSONObject();

		/*
		 * 检查参数是否完整
		 */
		if (account == null) {
			jsonObject.put("echo", AppEcho.PARAMETER_WRONG.getCode());
			jsonObject.put("message", "缺少账号");
			returnString = jsonObject.toString();
			return "appReturn";
		}
		if (password == null) {
			jsonObject.put("echo", AppEcho.PARAMETER_WRONG.getCode());
			jsonObject.put("message", "缺少密码");
			returnString = jsonObject.toString();
			return "appReturn";
		}
		
		Map<String, String> map=new HashMap<String, String>();
		map.put("username", account);
		map.put("password", password);
		@SuppressWarnings("deprecation")
		JSONObject json = null;
		try {
			json = new JSONObject(HttpRequest.doGet(OJAPI.GET_USER_SIGNIN,map));
		} catch (IOException e) {
		}
		if(json!=null)
		{
			if(ServerEcho.getServerEcho(json.getInt("echo"))==ServerEcho.Success)
			{
				AppUser appUser = null;
				try
				{
					appUser = appUserManager.getAppUserByAccount(account);
				}catch(Exception e){}
				if(appUser == null)
				{// 创建app用户
					appUser = new AppUser();
					appUser.setAccount(json.getString("username"));
				}
				try
				{
					appUser.setNickname(json.getString("nick"));
					appUser.setPassword(Tool.md5Convert(json.getString("username")));
					appUser.setToken(json.getString("token"));
					appUser.setFullname(json.getString("fullName"));
				}catch(Exception e){e.printStackTrace();}
				try
				{
					appUserManager.save(appUser);
				}catch(Exception e){}
				jsonObject.put("id", appUser.getId());
				jsonObject.put("mail", appUser.getMail());
				jsonObject.put("token", appUser.getToken());
				jsonObject.put("nickname", appUser.getNickname());
				jsonObject.put("fullname",appUser.getFullname());
				jsonObject.put("echo", AppEcho.SUCCESS.getCode());
				returnString = jsonObject.toString();
				return "appReturn";
			}
			else
			{
				jsonObject.put("echo", json.getInt("echo"));
				jsonObject.put("message", getText(ServerEcho.getServerEcho(json.getInt("echo")).getName()));
				returnString = jsonObject.toString();
				return "appReturn";	
			}
		}
		jsonObject.put("echo", AppEcho.SERVLCE_WRONG.getCode());
		jsonObject.put("message", "服务器返回错误");
		returnString = jsonObject.toString();
		return "appReturn";				
	}

}
