package com.unlimited.oj.webapp.action;

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
import com.unlimited.oj.pojo.DataNewUserItem;
import com.unlimited.oj.service.UserExistsException;
import com.unlimited.oj.webapp.action.AdminAction.newUserItem;
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

/**
 * Action for facilitating User Management feature.
 */
public class UserAction extends BaseAction
{

	private static final long serialVersionUID = 6776558938712115191L;
	private Page users;
	private User user;
	private List<User> userList;
    private String id;
	private DataChart dataChartGlobal;
	private DataChart dataChartRecent;
	private String searchKey;
	private String searchValue;


	private List<Map<String, String>> sourceList = null;
	private Page userActionLogs;
	private String addBatchUserData; // 用于addBatchUsers方法
	private String addBatchGroupData;
	private String updateBatchBadgeData; // 用于updateBatchBadges方法

    private String badgeMsg;
    
    private String forgetUsername;
    private String forgetEmail;
    
    private String encodeUsername;
    private String userRank;
    
    private String returnValue;
    private List<Map<String, String>> sexTypeList = null;

    private Map<String, String> mapPings = null;
    
    private String selectedGBoard;
    private String selectedGExam;
    
    // 供groupMission statistics使用
    private String statisticsMsg; // 页面显示信息
    private String figData; // 画图数据
    
    private String charger;
    
	/**
	 * Grab the entity from the database before populating with request
	 * parameters
	 */

	private void user_prepare()
	{
		try
		{
			if (getRequest().getMethod().equalsIgnoreCase("get"))
			{
				// if a user's id is passed in
				String userId = getRequest().getParameter("id");
				if (userId == null)
					userId = getRequest().getParameter("userId");
				if (userId != null && !"".equals(userId))
					user = userManager.getUser(userId);
			} else if (getRequest().getMethod().equalsIgnoreCase("post"))
			{
				String userId = getRequest().getParameter("user.id");
				// prevent failures on new
				if (userId != null && !"".equals(userId))
					user = userManager.getUser(userId);
			}
		} catch (Exception ex)
		{
		}
		sexTypeList = getsexTypeListMap();
	}

	@Override
	public void prepare()
	{
		super.prepare();
		user_prepare();
		
		sourceList = new LinkedList<Map<String, String>>();
	}

	/**
	 * Holder for users to display on list screen
	 * 
	 * @return list of users
	 */
	public Page getUsers()
	{
		return users;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}
	
	public String user_fetchPassword_PUBLIC()
	{
		return SUCCESS;
	}

	public String user_sendNewPassword_PUBLIC()
	{
		if(forgetUsername==null || "".equals(forgetUsername))
		{
			saveMessage("You must input username.");
			return "mainError";
		}
		if(forgetEmail==null || "".equals(forgetEmail))
		{
			saveMessage("You must input email.");
			return "mainError";
		}
		forgetEmail = forgetEmail.trim();
		User user = null;
		try
		{
			user = userManager.getUserByUsername(forgetUsername);
		} catch( Exception ex)
		{
			
		}
		if(user==null)
		{
			saveMessage("No such user -> " + forgetUsername);
			return "mainError";
		}
		String email = user.getEmail();
		if(email==null || "".equals(email.trim()))
		{
			saveMessage("Email have not be set, you can not fetch the password. please contact the administrator.");
			return "mainError";
		}
		email = email.trim();
		if(!email.toLowerCase().equals(forgetEmail.toLowerCase()))
		{
			saveMessage("wrong email!");
			return "mainError";
		}
		
		// 新密码，然后发送邮件
		String newPass = (new Long((int)(Math.random()*1000000))).toString();

		try
		{
			mailMessage.setTo(forgetUsername + "<" + forgetEmail + ">");
			mailMessage
					.setSubject("You have call the forget-password-fetch function, here is your new password");

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("username", forgetUsername);
			model.put("password", newPass);
			model.put("message",
					"Click the following link to enable the new password");
			String validCode = Tool.md5Convert(forgetUsername + newPass + System.currentTimeMillis()/86400000); //当天触发有效
			int nPort = getRequest().getLocalPort();
			if (nPort != 80)
			{
				model.put("applicationURL", "http://"
						+ getRequest().getServerName() + ":" + nPort + "/"
						+ getConfiguration().get(Constants.PROJECT_NAME) + "/user_user_updatePassword_PUBLIC.html?username=" + forgetUsername + 
						"&password=" + newPass + "&validCode=" + validCode);
			} else
			{
				model.put("applicationURL", "http://"
						+ getRequest().getServerName() + "/"
						+ getConfiguration().get(Constants.PROJECT_NAME) + "/user_user_updatePassword_PUBLIC.html?username=" + forgetUsername + 
						"&password=" + newPass + "&validCode=" + validCode);
			}
			mailEngine.sendMessage(mailMessage, "fetchPassword.vm", model);
		} catch (Exception e)
		{
			e.printStackTrace();
			return "mainError";
		}

		saveMessage("New password was sent to your email.");
		
		return SUCCESS;
	}
	
	public String user_updatePassword_PUBLIC()
	{
		String username = getRequest().getParameter("username");
		String password = getRequest().getParameter("password");
		String validCode = getRequest().getParameter("validCode");

		if(username==null)
		{
			saveMessage("username is null.");
			return SUCCESS;
		}
		if(password==null)
		{
			saveMessage("password is null");
			return SUCCESS;
		}
		if(validCode==null)
		{
			saveMessage("validCode is null");
			return SUCCESS;
		}
		User user = null;
		try
		{
			user = userManager.getUserByUsername(username);
		} catch( Exception ex)
		{
			
		}
		if(user==null)
		{
			saveMessage("No such user -> " + username);
			return SUCCESS;
		}
		String validCode2 = Tool.md5Convert(username + password + System.currentTimeMillis()/86400000); //当天触发有效
		if(!validCode2.equals(validCode))
		{
			saveMessage("validCode is invalid. You must click the link in the same day when you get the link.");
			return SUCCESS;
		}
		try
		{
			user.setOldPassword("_8_");
			user.setPassword(password);
			user.setConfirmPassword(password);
			userManager.saveUser(user);
			
			saveMessage(username + "'s password is updated.");
		} catch (Exception ex)
		{
			saveMessage("Update user fail!");
			return SUCCESS;
		}
		return SUCCESS;
	}

	/**
	 * Delete the user passed in.
	 * 
	 * @return success
	 * @throws IOException
	 */
	public String delete() throws IOException
	{
		if (!getRequest().isUserInRole(Constants.ADMIN_ROLE))
		{
			getResponse().sendError(HttpServletResponse.SC_FORBIDDEN);
			return "mainPage";
		}

		userManager.removeUser(user.getId().toString());

		List<String> args = new ArrayList<String>();
		args.add(user.getFullName());
		saveMessage(getText("user.deleted", args));

		return SUCCESS();
	}

	public String user_editSelf_PUBLIC() throws IOException
	{
		if(ApplicationConfig.getValue("PlatformSecurityLevel")!=null)
		{
			try
			{
				int level = Integer.parseInt(ApplicationConfig.getValue("PlatformSecurityLevel"));
				if(level<1)
				{
					saveMessage("Modify user's information is forbidden");
					return "mainPage";
				}
			}catch(Exception e){};
		}
		HttpServletRequest request = getRequest();
		user = userManager.getUserByUsername(request.getRemoteUser());
		if(user.getUsername().equalsIgnoreCase("guest"))
		{
			saveMessage("Guest cann't be edit.");
			return "mainPage";
		}

		return user_edit_PUBLIC();
	}
	/**
	 * Grab the user from the database based on the "id" passed in.
	 * 
	 * @return success if user found
	 * @throws IOException
	 *             can happen when sending a "forbidden" from
	 *             response.send"mainError"
	 */
	public String user_edit_PUBLIC() throws IOException
	{
		if(user==null)
		{
			// if a user's id is passed in
			if (id != null)
				
			{
				// lookup the user using that id
				user = userManager.getUser(id);
				if (user != null
						&& !user.getUsername().equals(
								getCurrentUser().getUsername())
						&& !getCurrentUser().isAdministrator()
						&& !((getCurrentUser().isContestAdministrator() || 
									getCurrentUser().isExerciseAdministrator())
								&& !user.isAdministrator()
								&& !user.isContestAdministrator()
								&& !user.isExerciseAdministrator()
								&& !user.isExamAdministrator()))
				{
					addActionError("You have no right to edit ["
							+ user.getUsername() + "]");
					return "mainError";
				}
			} else
			{
				user = new User();
				user.addRoleList(new Role(Constants.USER_ROLE));
			}
		}
		else
		{
			if (!user.getUsername().equals(
							getCurrentUser().getUsername())
					&& !getCurrentUser().isAdministrator()
						&& !((getCurrentUser().isContestAdministrator() || 
									getCurrentUser().isExerciseAdministrator())
								&& !user.isAdministrator()
								&& !user.isContestAdministrator()
								&& !user.isExerciseAdministrator()
								&& !user.isExamAdministrator()))
			{
				addActionError("You have no right to edit ["
						+ user.getUsername() + "]");
				return "mainError";
			}
		}
		user.setOldPassword(user.getPassword()); // set old password = password

		if (user.getUsername() != null)
		{
			user.setConfirmPassword(user.getPassword());

			encodeUsername = passwordEncoder.encodePassword(user.getUsername()+user.getId(), null);
			
			// if user logged in with remember me, display a warning that they
			// can't change passwords
			log.debug("checking for remember me login...");

			AuthenticationTrustResolver resolver = new AuthenticationTrustResolverImpl();
			SecurityContext ctx = SecurityContextHolder.getContext();

			if (ctx != null)
			{
				Authentication auth = ctx.getAuthentication();

				if (resolver.isRememberMe(auth))
				{
					getSession().setAttribute("cookieLogin", "true");
					saveMessage(getText("userProfile.cookieLogin"));
				}
			}
		}

		return SUCCESS();
	}

	public String user_edit_ADMIN() throws IOException
	{
		return user_edit_PUBLIC();
	}
	
	private String catString(String src, String str)
	{
		if(src==null) return str;
		else return src+"<br/>"+str;
	}
	
	public int badgeScore(String code)
	{
		String[] items = code.split(",");
		int i, score = 0;
		int[] counts = new int[20];
		double[] rates = new double[20];
		rates[1] = 1;
		for(i=2; i<20; i++)
			rates[i] = rates[i-1] + Math.pow(0.8, i-1); 
		for(String item: items)
		{
			item = item.trim();
			if(!"".equals(item))
			{
				if(item.equals("10")) counts[0]++;
				else if(item.equals("21")) counts[1]++;
				else if(item.equals("22")) counts[2]++;
				else if(item.equals("23")) counts[3]++;
				else if(item.equals("31")) counts[4]++;
				else if(item.equals("32")) counts[5]++;
				else if(item.equals("33")) counts[6]++;
				else if(item.equals("41")) counts[7]++;
				else if(item.equals("42")) counts[8]++;
			}
		}
		for(i=0; i<9; i++)
		{
			if(counts[i]>0)
			{
				switch(i)
				{
				case 0:
					score += 2500*rates[counts[i]];
					break;
				case 1:
					score += 2000*rates[counts[i]];
					break;
				case 2:
					score += 1200*rates[counts[i]];
					break;
				case 3:
					score += 800*rates[counts[i]];
					break;
				case 4:
					score += 1300*rates[counts[i]];
					break;
				case 5:
					score += 700*rates[counts[i]];
					break;
				case 6:
					score += 500*rates[counts[i]];
					break;
				case 7:
					score += 300*rates[counts[i]];
					break;
				case 8:
					score += 100*rates[counts[i]];
					break;
				}
			}
		}
		return score;
	}

	public String user_view_PUBLIC() throws IOException
	{
		String sUserId = getRequest().getParameter("userId");
		if (sUserId != null && !"".equals(sUserId))
			user = userManager.getUser(sUserId);

		if (user == null)
		{
			user = new User();
			user.addRoleList(new Role(Constants.USER_ROLE));
		}
	
		return SUCCESS();
	}

	/**
	 * Default: just returns "success"
	 * 
	 * @return "success"
	 */
	@Override
	public String execute()
	{
		return SUCCESS();
	}

	/**
	 * Sends users to "mainMenu" when !from.equals("list"). Sends everyone else
	 * to "cancel"
	 * 
	 * @return "mainMenu" or "cancel"
	 */
	@Override
	public String cancel()
	{
		if (!"list".equals(from))
		{
			return "mainMenu";
		}
		return "cancel";
	}
	
	public String user_donateMoney_PUBLIC()
	{
		return SUCCESS();
	}
	
	public String user_detainMoneyForm_ADMIN()
	{
		return SUCCESS();
	}
	
	public String user_detainMoneyForm_Submit_ADMIN()
	{
		if(user==null)
		{
			saveMessage("Can not find the user.");
			return "mainError";
		}
		if(user.getId().equals(currentUser.getId()))
		{
			saveMessage("Can not detain money to yourself.");
			return "mainError";
		}
		String money = getRequest().getParameter("money");
		int num = 0;
		try
		{
			num = Integer.parseInt(money);
		}
		catch (Exception ex)
		{
			
		}
		if (num<1)
		{
			saveMessage("Check your input.");
			return "mainError";
		}
		String reason = getRequest().getParameter("reason");
		if(reason==null) reason = "";
		
		int detain = num;

		try
		{
			userManager.saveUser(currentUser);
			saveMessage(user.getUsername() + "'s points -" + detain);
		} catch (Exception ade)
		{
			ade.printStackTrace();
			return "mainError";
		}
    	
		return SUCCESS();
	}
	
	public String user_resetPassword_ADMINEXERCISE_ADMINEXAM_ADMINCONTEST_ROLES()
	{
		if (getCurrentUser() == null)
		{
			addActionError("Current user is null");
			return "mainError";
		}
		if(!"admin".equals(currentUser.getUsername()) && 
				user.isAdministrator() && 
				!currentUser.getUsername().equals(user.getUsername()) )
		{// 只有admin能改其它管理员的内容
			addActionError("You haven't right to do this.");
			return "mainError";
		}
		if(!currentUser.isAdministrator() && 
				(user.isExamAdministrator() || user.isContestAdministrator() || user.isExerciseAdministrator()) && 
				!currentUser.getUsername().equals(user.getUsername()) )
		{// 只有管理员才能改单项管理员的内容
			addActionError("You haven't right to do this.");
			return "mainError";
		}
		try
		{
			user.setPassword(passwordEncoder.encodePassword(user.getUsername(), null));
			user.setConfirmPassword(user.getPassword());
			user.setOldPassword(user.getPassword());
			
			userManager.saveUser(user);
			saveMessage("user's password reseted.");
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return SUCCESS();
	}

	public String user_save_ADMIN() throws Exception
	{
		return user_save_PUBLIC();
	}
	/**
	 * Save user
	 * 
	 * @return success if everything worked, otherwise input
	 * @throws IOException
	 *             when setting "access denied" fails on response
	 */
	public String user_save_PUBLIC() throws Exception
	{

		boolean isNew = (user.getId() == null);
		// only attempt to change roles if user is admin
		// for other users, prepa(re() method will handle populating
		if (isNew)
		{
			addActionError("Please register first");
			return "mainError";
		}

		if(!user.getPassword().equals(user.getConfirmPassword()))
		{
			addActionError("Tow passwords do not match.");
			return "mainError";
		}
		
		if (getCurrentUser() == null)
		{
			addActionError("Current user is null");
			return "mainError";
		}
		if(!"admin".equals(currentUser.getUsername()) && 
				user.isAdministrator() && 
				!currentUser.getUsername().equals(user.getUsername()) )
		{// 只有admin能改其它管理员的内容
			addActionError("You haven't right to do this.");
			return "mainError";
		}
		if(!currentUser.isAdministrator() && 
				(user.isExamAdministrator() || user.isContestAdministrator() || user.isExerciseAdministrator()) && 
				!currentUser.getUsername().equals(user.getUsername()) )
		{// 只有管理员才能改单项管理员的内容
			addActionError("You haven't right to do this.");
			return "mainError";
		}
		if(!currentUser.isAdministrator())
		{
			String _temp = passwordEncoder.encodePassword(user.getUsername()+user.getId(), null);
			if(!encodeUsername.equals(_temp))
			{
				addActionError("Username cann't be changed!");
				return "mainError";
			}
		}
		if(!currentUser.isAdministrator() && 
				!currentUser.isExerciseAdministrator() &&
				!currentUser.isExerciseAdministrator() &&
				!currentUser.isContestAdministrator())
		{// 防止普通用户用工具编辑网页元素，修改非自己的帐号
			if(!user.getId().equals(currentUser.getId()))
			{
				addActionError("What are you doing?");
				return "mainError";
			}
		}
		// Sensitive key field
		if(!currentUser.isAdministrator())
		{
			boolean valid = true;
			if(getRequest().getParameter("user.money")!=null) valid = false;
			else if(getRequest().getParameterValues("userRoles")!=null) valid = false;
			else if(getRequest().getParameter("user.lastName")!=null) valid = false;
			else if(getRequest().getParameter("user.firstName")!=null) valid = false;
			if(!valid)
			{
				addActionError("Something strange is happened.");
				return "mainError";
			}
		}
		if(!currentUser.isAdministrator() && !currentUser.isExerciseAdministrator() && !currentUser.isExamAdministrator() && !currentUser.isContestAdministrator())
		{
			boolean valid = true;			
			if(getRequest().getParameter("user.school")!=null) valid = false;
			else if(getRequest().getParameter("user.className")!=null) valid = false;
			else if(getRequest().getParameter("user.studentNumber")!=null) valid = false;
			if(!valid)
			{
				addActionError("Something strange is happened.");
				return "mainError";
			}
		}		
		if (!user.getUsername().equals(getCurrentUser().getUsername())
				&& !getCurrentUser().isAdministrator()
				&& !((getCurrentUser().isContestAdministrator() || getCurrentUser()
						.isExerciseAdministrator())
						&& !user.isAdministrator()
						&& !user.isContestAdministrator()
						&& !user.isExerciseAdministrator()
						&& !user.isExamAdministrator())) // Version>=6为校队队员
		{
			addActionError("You have no right to save [" + user.getUsername()
					+ "]");
			return "mainError";
		}
		if (getCurrentUser().isAdministrator())
		{// 管理员操作，需要特殊处理的项
			user.getRoleList().clear(); // APF-788: Removing roles from user
			// doesn't work
			String[] userRoles = getRequest().getParameterValues("userRoles");

			for (int i = 0; userRoles != null && i < userRoles.length; i++)
			{
				String roleName = userRoles[i];
				Role role = userManager.getRoleByName(roleName);
				if (role != null)
				{
					try
					{
						user.addRoleList(role);
					} catch (Exception e)
					{
					}
				} else
				{
					log.info("ROLE[" + roleName + "] is null.");
				}
			}
		}
		try
		{
			if (getCurrentUser().isAdministrator())
			{
				if (getRequest().getParameter("user.enabled") != null)
				{
					user.setEnabled(true);
				} else
				{
					user.setEnabled(false);
				}
			}
			userManager.saveUser(user);
			saveMessage("User is updated.");
		} catch (AccessDeniedException ade)
		{
			// thrown by UserSecurityAdvice configured in aop:advisor
			// userManagerSecurity
			log.warn(ade.getMessage());
			getResponse().sendError(HttpServletResponse.SC_FORBIDDEN);
			return INPUT();
		} catch (UserExistsException e)
		{
			List<String> args = new ArrayList<String>();
			args.add(user.getUsername());
			args.add(user.getEmail());
			addActionError(getText("errors.existing.user", args));

			// redisplay the unencrypted passwords
			user.setPassword(user.getConfirmPassword());
			return INPUT();
		}
		addActionMessage(getText("user.updated"));
		return SUCCESS();
	}

	public String user_list_ADMINEXAM_ADMINEXERCISE_ADMINCONTEST_ROLES()
	{
		return user_list_PUBLIC();
	}
	
	public String user_list_ADMIN()
	{
		return user_listBySearchUsername_ADMINEXERCISE_ADMINEXAM_ADMINCONTEST_ROLES();
	}

	/**
	 * Fetch all users from database and put into local "users" variable for
	 * retrieval in the UI.
	 * 
	 * @return "success" if no exceptions thrown
	 */
	public String user_list_PUBLIC()
	{
		// users = userManager.getUsers();
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
		users = userManager.getPage(page, -1, "id", true);
		List<User> list = users.getList();
		List newList = new ArrayList();
		User preUser = null;
		int n = 0;
		for (User u : list)
		{
			if (preUser != null && u.getId().equals(preUser.getId()))
			{
				n++;
			} else
			{
				newList.add(u);
			}
			;
			preUser = u;
		}
		if (n > 0)
		{
			users.setList(newList);
		}

		return SUCCESS();
	}

	public String registerPKUAccount()
	{
		if (user == null)
		{
			return "mainError";
		}

		return SUCCESS();
	}

	public String user_listBySearchUsername_ADMIN()
	{
		return user_listBySearchUsername_PUBLIC();
	}

	public String user_listBySearchRole_ADMIN()
	{
		// users = userManager.getUsers();
		int page = 1, sort = 1, order = 2;
		if (getRequest().getParameter("searchKey") != null
				&& !getRequest().getParameter("searchKey").equals(""))
		{
			searchKey = getRequest().getParameter("searchKey");
		}
		if (getRequest().getParameter("searchValue") != null
				&& !getRequest().getParameter("searchValue").equals(""))
		{
			searchValue = getRequest().getParameter("searchValue");
		}

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
		if (searchValue == null || searchValue.equals(""))
		{
			users = userManager.getPage(page, 50, "id", false);
		} else
		{
			users = userManager.getPageOfUsersByRoleName(page, 50, searchValue, "user_name", true);
		}
		if(users!=null)
		{
			List<User> list = users.getList();
			List newList = new ArrayList();
			User preUser = null;
			int n = 0;
			for (User u : list)
			{
				if (preUser != null && u.getId().equals(preUser.getId()))
				{
					n++;
				} else
				{
					newList.add(u);
				}
				;
				preUser = u;
			}
			if (n > 0)
			{
				users.setList(newList);
			}
		}

		return SUCCESS();
	}
	
	public String user_listBySearchUsername_ADMINEXERCISE_ADMINEXAM_ADMINCONTEST_ROLES()
	{
		// users = userManager.getUsers();
		int page = 1, sort = 1, order = 2;
		if (getRequest().getParameter("searchKey") != null
				&& !getRequest().getParameter("searchKey").equals(""))
		{
			searchKey = getRequest().getParameter("searchKey");
		}
		if (getRequest().getParameter("searchValue") != null
				&& !getRequest().getParameter("searchValue").equals(""))
		{
			searchValue = getRequest().getParameter("searchValue");
		}

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
		if (searchValue == null || searchValue.equals(""))
		{
			users = userManager.getPage(page, 50, "id", true);
		} else
		{
			users = userManager.getPageBySearch(page, 50, searchKey,
					searchValue, "id", true);
		}
		List<User> list = users.getList();
		List newList = new ArrayList();
		User preUser = null;
		int n = 0;
		for (User u : list)
		{
			if (preUser != null && u.getId().equals(preUser.getId()))
			{
				n++;
			} else
			{
				newList.add(u);
			}
			;
			preUser = u;
		}
		if (n > 0)
		{
			users.setList(newList);
		}

		return SUCCESS();
	}

	public DataChart getDataChartGlobal()
	{
		return dataChartGlobal;
	}

	public DataChart getDataChartRecent()
	{
		return dataChartRecent;
	}

	public void setSearchKey(String searchKey)
	{
		this.searchKey = searchKey;
	}

	public void setSearchValue(String searchValue)
	{
		this.searchValue = searchValue;
	}

	public String getSearchKey()
	{
		return searchKey;
	}

	public String getSearchValue()
	{
		return searchValue;
	}

	public List<Map<String, String>> getSourceList()
	{
		return sourceList;
	}

	public String user_listBySearchUsername_PUBLIC()
	{
		// users = userManager.getUsers();
		int page = 1, sort = 1, order = 2;
		if (getRequest().getParameter("searchKey") != null
				&& !getRequest().getParameter("searchKey").equals(""))
		{
			searchKey = getRequest().getParameter("searchKey");
		}
		if (getRequest().getParameter("searchValue") != null
				&& !getRequest().getParameter("searchValue").equals(""))
		{
			searchValue = getRequest().getParameter("searchValue");
		}

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
		if (searchValue == null || searchValue.equals(""))
		{
			users = userManager.getPage(page, 50, "grade", false);
		} else
		{
			users = userManager.getPageBySearch(page, 50, searchKey,
					searchValue, "grade", false);
		}
		List<User> list = users.getList();
		List newList = new ArrayList();
		User preUser = null;
		int n = 0;
		for (User u : list)
		{
			if (preUser != null && u.getId().equals(preUser.getId()))
			{
				n++;
			} else
			{
				newList.add(u);
			}
			;
			preUser = u;
		}
		if (n > 0)
		{
			users.setList(newList);
		}

		return SUCCESS();
	}

	public Page getUserActionLogs()
	{
		return userActionLogs;
	}

	public String user_batchUpdateUsersForm_ADMIN()
	{
		return SUCCESS;
	}

	public String user_batchUpdateUsersForm_SubmitSave_ADMIN()
	{
		String sLine;
		List<DataNewUserItem> userList = new LinkedList<DataNewUserItem>();

		Scanner cin = new Scanner(new StringReader(addBatchUserData));
		while (cin.hasNext())
		{
			sLine = cin.nextLine();
			sLine = Tool.StringsReplace(sLine, "，", ",");
			// System.err.println(sLine);
			String[] info = sLine.split(",");
			if (info.length < 2)
				continue;

			User user = null;
			try
			{
				user = userManager.getUserByUsername(info[0]);
			} catch (Exception ex)
			{
			}
			if (user == null)
			{
				saveMessage("Account [" + info[0] + "] does not exists.");
				continue;
			}
			if(info[1].trim().length()>0)
			{// 密码	
				user.setPassword(info[1].trim());
				user.setConfirmPassword(info[1].trim());
				user.setOldPassword(new Long(System.currentTimeMillis()).toString());
			}
			if (info.length > 2)
			{
			}
			if (info.length > 3)
			{// 真名
				if(info[3].trim().length()>0)
				{
					if(info[3].trim().toUpperCase().equals("NULL"))
						user.setFirstName("");
					else
						user.setFirstName(info[3].trim());
				}
			}
			if (info.length > 4)
			{
			}
			if (info.length > 5)
			{// 使能
				if(info[5].trim().length()>0)
				{
					if(info[5].trim().toUpperCase().equals("TRUE"))
						user.setEnabled(true);
					else if(info[5].trim().toUpperCase().equals("FALSE"))
						user.setEnabled(false);
				}
			}

			try
			{
				userManager.saveUser(user);
			} catch (UserExistsException ex)
			{
				Logger.getLogger(AdminAction.class.getName()).log(
						Level.SEVERE, null, ex);
				continue;
			}
			saveMessage("Account [" + user.getUsername() + "] is updated.");
		}

		return SUCCESS();
	}
	
	public String user_batchAddUsersForm_ADMIN()
	{
		return SUCCESS;
	}

	public String user_batchAddUsersForm_SubmitSave_ADMIN()
	{
		String sLine;
		List<DataNewUserItem> userList = new LinkedList<DataNewUserItem>();

		Scanner cin = new Scanner(new StringReader(addBatchUserData));
		while (cin.hasNext())
		{
			sLine = cin.nextLine();
			sLine = Tool.StringsReplace(sLine, "，", ",");
			sLine = Tool.StringsReplace(sLine, "	", ",");
			sLine = Tool.StringsReplace(sLine, " ", "");
			// System.err.println(sLine);
			String[] info = sLine.split(",");
			if (info.length < 2)
				continue;
			DataNewUserItem u = new DataNewUserItem();
			u.userName = info[0];
			u.pass = info[1];
			if (info.length > 2)
				u.trueName = info[2];
			else
				u.trueName = "";
			if (info.length > 3)
				u.studentNumber = info[3];
			else
				u.studentNumber = "";
			if (info.length > 4)
				u.className = info[4];
			else
				u.className = "";
			if (info.length > 5)
				u.school = info[5];
			else
				u.school = "";
			if (info.length > 6)
				u.alias = info[6];
			else
				u.alias = "";
			if (info.length > 7)
				u.location = info[7];
			else
				u.location = "";
			
			// System.out.printf(sUserName + " ");
			// System.out.println(sPass );

			User user = null;
			try
			{
				user = userManager.getUserByUsername(u.userName);
			} catch (Exception ex)
			{
			}
			if (user == null)
				u.isNew = true;

			userList.add(u);
		}
		for (DataNewUserItem item : userList)
		{
			if (item.isNew)
			{// 创建该帐号
				User user = new User();
				user.setEmail("");
				user.setFirstName(item.trueName);
				user.setLastName("");
				user.setPassword(item.pass);
				user.setConfirmPassword(item.pass);
				user.setPhoneNumber("");
				user.setUsername(item.userName);
				user.addRoleList(userManager.getRoleByName(Constants.USER_ROLE));
				user.setEnabled(true);
				try
				{
					userManager.saveUser(user);
				} catch (UserExistsException ex)
				{
					Logger.getLogger(AdminAction.class.getName()).log(
							Level.SEVERE, null, ex);
				}

				saveMessage("Account [" + item.userName + "] is added.");
			} else
				saveMessage("Account [" + item.userName + "] already exists.");
		}

		return SUCCESS();
	}
	
	public String user_batchUpdateBadges_ADMIN()
	{
		return SUCCESS;
	}
	
	public void setAddBatchUserData(String addBatchUserData)
	{
		this.addBatchUserData = addBatchUserData;
	}

	public String getBadgeMsg()
	{
		return badgeMsg;
	}

	public void setForgetUsername(String forgetUsername) {
		this.forgetUsername = forgetUsername;
	}

	public void setForgetEmail(String forgetEmail) {
		this.forgetEmail = forgetEmail;
	}

	public String getUpdateBatchBadgeData() {
		return updateBatchBadgeData;
	}

	public void setUpdateBatchBadgeData(String updateBatchBadgeData) {
		this.updateBatchBadgeData = updateBatchBadgeData;
	}

	public String getEncodeUsername() {
		return encodeUsername;
	}

	public void setEncodeUsername(String encodeUsername) {
		this.encodeUsername = encodeUsername;
	}

	public String getUserRank() {
		return userRank;
	}
	
	public String getReturnValue()
	{
		return returnValue;
	}
	
	public String user_exist_PUBLIC()
	{
		returnValue = "";
		String callback = getRequest().getParameter("callback");
		String userName = getRequest().getParameter("userName");
		if(callback == null) 
			returnValue = "callback is null";
		else if(userName!=null)
		{
			User user = null;
			try
			{
				user = userManager.getUserByUsername(userName);
			}catch(Exception ex){}
			if(user!=null) returnValue = callback + "(1)";
			else returnValue = callback + "(0)";
		}
		else 
			returnValue = callback + "(0)";
		return SUCCESS;
	}
	
	public String group_batchAddGroupsForm_ADMIN()
	{
		return SUCCESS;
	}

	public String getCharger() {
		return charger;
	}

	public void setCharger(String charger) {
		this.charger = charger;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setSelectedGBoard(String selectedGBoard) {
		this.selectedGBoard = selectedGBoard;
	}

	public String getStatisticsMsg() {
		return statisticsMsg;
	}

	public String getFigData() {
		return figData;
	}
	
	public String user_generateTokenForm_PUBLIC()
	{
		if(user==null)
		{
			saveMessage("user is null");
			return "mainPage";
		}
		return SUCCESS;
	}

	public Map<String, String> getMapPings() {
		return mapPings;
	}


	public void setSelectedGExam(String selectedGExam) {
		this.selectedGExam = selectedGExam;
	}
	
    private List<Map<String, String>> getsexTypeListMap()
    {
        Locale locale = LocaleContextHolder.getLocale();
        ResourceBundle rb = ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale);
        List<Map<String, String>> sexTypeListMap = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("key", new Integer(0).toString());
        map.put("value", rb.getString("enums.SexType.UNKNOWN"));
        sexTypeListMap.add(map);

        map = new HashMap<String, String>();
        map.put("key", new Integer(1).toString());
        map.put("value", rb.getString("enums.SexType.MALE"));
        sexTypeListMap.add(map);

        map = new HashMap<String, String>();
        map.put("key", new Integer(2).toString());
        map.put("value", rb.getString("enums.SexType.FEMALE"));
        sexTypeListMap.add(map);

        return sexTypeListMap;
    }

	public List<Map<String, String>> getSexTypeList() {
		return sexTypeList;
	}
	
	public String user_heartBeat_PUBLIC()
	{
		return SUCCESS();
	}
}
