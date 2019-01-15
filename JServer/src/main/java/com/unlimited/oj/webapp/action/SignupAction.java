package com.unlimited.oj.webapp.action;

import org.springframework.security.AccessDeniedException;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Preparable;
import com.unlimited.oj.Constants;
import com.unlimited.oj.model.Role;
import com.unlimited.oj.model.User;
import com.unlimited.oj.service.UserExistsException;
import com.unlimited.oj.service.impl.UserManagerImpl;
import com.unlimited.oj.util.Tool;
import com.unlimited.oj.webapp.util.RequestUtil;
import com.unlimited.webserver.model.Lab;

import org.springframework.mail.MailException;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Action to allow new users to sign up.
 */
public class SignupAction extends BaseAction
{

    private static final long serialVersionUID = 6558317334878272308L;
    private User user;
    private String cancel;

    @Override
    public void prepare()
    {
        super.prepare();
    }

    public void setCancel(String cancel)
    {
        this.cancel = cancel;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    /**
     * Return an instance of the user - to display when validation errors occur
     *
     * @return a populated user
     */
    public User getUser()
    {
        return user;
    }

    /**
     * When method=GET, "input" is returned. Otherwise, "success" is returned.
     *
     * @return cancel, input or success
     */
    @Override
    public String execute()
    {
        if (cancel != null)
        {
            return CANCEL;
        }
        if (ServletActionContext.getRequest().getMethod().equals("GET"))
        {
            return INPUT();
        }
        return SUCCESS();
    }

    /**
     * Returns "input"
     *
     * @return "input" by default
     */
    @Override
    public String doDefault()
    {
        return INPUT();
    }

    /**
     * Save the user, encrypting their passwords if necessary
     *
     * @return success when good things happen
     * @throws Exception
     *             when bad things happen
     */
    public String save()
    {	
    	
    	
    	try {
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//System.err.println("--------------------------------\n");
			e.printStackTrace();
			//System.exit(-1);
		}
		
		return SUCCESS();
		/**
        if(getConfiguration().get("AccountCanRegister")!=null &&
                !((String)getConfiguration().get("AccountCanRegister")).equalsIgnoreCase("true"))
        {
            saveMessage("Register is denied.");
            return INPUT;
        }
        User u = null;
        try
        {
            u = userManager.getUserByUsername(user.getUsername());
        } catch (Exception e)
        {
        }
        if (u != null)
        {
            saveMessage(u.getUsername() + " already exists.");
            System.err.println(u.getUsername() + " already exists.");
            return INPUT();
        }
        try
        {
            u = userManager.getUserByEmail(user.getEmail());
        } catch (Exception e)
        {
        }
        if (u != null)
        {
            saveMessage("Email " + user.getEmail() + " was already used.");
            System.err.println("Email " + user.getEmail() + " was already used.");
            return INPUT();
        }
        user.setAccountLocked(false);
        user.setAccountExpired(false);
        user.setCredentialsExpired(false);
        try
        {
            if(getConfiguration().get("AccountNeedActived")!=null &&
                ((String)getConfiguration().get("AccountNeedActived")).equalsIgnoreCase("true"))
            {
                user.setEnabled(false); // The new account is disabled which need actived(刚注册的用户处于未激活状态)

                String email = user.getEmail();
                try
                {
                    String activateCode = Tool.md5Convert(getConfiguration().get(Constants.PROJECT_NAME) + user.getUsername() + email); // 使用web.xml中参数+用户名+Email做为
                    mailMessage.setTo(user.getUsername() + "<" + user.getEmail() + ">");
                    mailMessage.setSubject("Welcome to OnlineJudge, Your Activate Code");

                    Map<String, Object> model = new HashMap<String, Object>();
                    model.put("user", user);
                    model.put("message", "Use following information to activate your Account. ACM/ICPC\n\n");
                    int nPort = getRequest().getLocalPort();
                    if (nPort != 80)
                    {
                        model.put("applicationURL", "http://" + getRequest().getServerName() + ":" +
                                nPort + "/" + getConfiguration().get(Constants.PROJECT_NAME) +
                                "/activeAccount.html?username=" + user.getUsername() + "&activateCode=" + activateCode + "&active\n\n");
                    } else
                    {
                        model.put("applicationURL", "http://" + getRequest().getServerName() +
                                "/" + getConfiguration().get(Constants.PROJECT_NAME) +
                                "/activeAccount.html?username=" + user.getUsername() + "&activateCode=" + activateCode + "&active\n\n");
                    }
                    mailEngine.sendMessage(mailMessage, "activateAccount.vm", model);
                } catch (Exception e)
                {
                    addActionError("send mail error.");
                    return INPUT();
                }
            }
            else
                user.setEnabled(true);

            // 添加权限
            Role role = roleManager.getRole(Constants.USER_ROLE);
            try
            {
                user.addRoleList(role);
            }catch(Exception e){}
            
            userManager.saveUser(user); // Save the infomation of the new User(保存新用户的注册信息)
            
        } catch (AccessDeniedException ade)
        {
            saveMessage("You have no right.");
            return INPUT();
        } catch (UserExistsException e)
        {
            log.warn(e.getMessage());
            List<String> args = new ArrayList<String>();
            args.add(user.getUsername());
            args.add(user.getEmail());
            addActionError(getText("errors.existing.user", args));

            // redisplay the unencrypted passwords
            user.setPassword(user.getConfirmPassword());
            return INPUT();
        }

        if(!user.getEnabled())
            saveMessage("Please check your EMAIL and active your account.");
        else
        {
            // log user in automatically
            saveMessage(getText("user.registered"));
            getSession().setAttribute(Constants.REGISTERED, Boolean.TRUE);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    user.getUsername(), user.getConfirmPassword(), user.getAuthorities());
            auth.setDetails(user);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        // Send an account information e-mail
//		mailMessage.setSubject(getText("signup.email.subject"));
//
//		try
//		{
//			sendUserMessage(user, getText("signup.email.message"), RequestUtil
//					.getAppURL(getRequest()));
//		} catch (MailException me)
//		{
//			addActionError(me.getMostSpecificCause().getMessage());
//		}

        return SUCCESS();**/
    }

     /**
     * Activate User Account
     *
     * @return
     */
    public String activeAccount()
    {
        String strUsername = getRequest().getParameter("username");
        if (strUsername == null)
        {
            return ERROR();
        }
        user = userManager.getUserByUsername(strUsername);
        if (user == null)
        {
            saveMessage(strUsername + " not exists.");
            return INPUT();
        }

        if (user.isEnabled())
        {
            saveMessage("You account is already activated.");
            return ERROR();
        }

        String ac = getRequest().getParameter("activateCode");
        String activateCode = Tool.md5Convert(getConfiguration().get(Constants.PROJECT_NAME) + user.getUsername() + user.getEmail()); // 使用web.xml中参数+用户名+Email做为

        if (activateCode.equals(ac))
        {
            user.setEnabled(true); // Set Account Enabled(设置帐号有效)
            try
            {
                user.setOldPassword(user.getPassword()); // Not change password
                userManager.saveUser(user);
            } catch (UserExistsException e)
            {
                e.printStackTrace();
            }
            saveMessage("Your account was activated.");
        } else
        {
            saveMessage("Wrong Activate Code.");
        }
        return SUCCESS();
    }
    
    public String fetchUserAccountCode()
    {
        String email = getRequest().getParameter("email");
        if(!Tool.checkEmail(email))
        {
            saveMessage("Email error");
            return ERROR();
        }
        User user = userManager.getUserByEmail(email);
        if(user==null)
        {
            saveMessage("No user regitser with " + email);
            return ERROR();
        }
        return SUCCESS();
    }
    
}
