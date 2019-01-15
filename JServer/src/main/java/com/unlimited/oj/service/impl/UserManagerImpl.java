package com.unlimited.oj.service.impl;

import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.security.userdetails.UsernameNotFoundException;

import com.unlimited.oj.Constants;
import com.unlimited.oj.dao.RoleDao;
import com.unlimited.oj.dao.UserDao;
import com.unlimited.oj.dao.support.Page;
import com.unlimited.oj.dao.support.QueryCondition;
import com.unlimited.oj.model.Role;
import com.unlimited.oj.model.User;
import com.unlimited.oj.service.UserExistsException;
import com.unlimited.oj.service.UserManager;
import com.unlimited.oj.service.UserService;
import com.unlimited.oj.util.Tool;
import com.unlimited.oj.webapp.action.AdminAction;
import com.unlimited.webserver.model.Lab;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DataIntegrityViolationException;

import javax.jws.WebService;
import javax.persistence.EntityExistsException;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of UserManager interface.
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@WebService(serviceName = "UserService", endpointInterface = "com.unlimited.oj.service.UserService")
public class UserManagerImpl extends GenericManagerImpl<User, Long> implements UserManager, UserService
{
	private UserDao userDao;
	private PasswordEncoder passwordEncoder;
    private RoleDao roleDao;

	/**
	 * Set the Dao for communication with the data layer.
	 * 
	 * @param userDao
	 *            the UserDao that communicates with the database
	 */
	@Required
	public void setUserDao(UserDao userDao)
	{	
		super.dao = userDao;
		this.userDao = userDao;
	}

	/**
	 * Set the PasswordEncoder used to encrypt passwords.
	 * 
	 * @param passwordEncoder
	 *            the PasswordEncoder implementation
	 */
	@Required
	public void setPasswordEncoder(PasswordEncoder passwordEncoder)
	{
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * {@inheritDoc}
	 */
	public User getUser(String userId)
	{
		return userDao.get(new Long(userId));
	}

	/**
	 * {@inheritDoc}
	 */
	public List<User> getUsers()
	{
		return userDao.getAll();
	}

	/**
	 * {@inheritDoc}
	 */
	public void saveUser(User user) throws UserExistsException
	{

		if (user.getId() == null)
		{
			// if new user, lowercase userId
			user.setUsername(user.getUsername().toLowerCase());
		}

		// Get and prepare password management-related artifacts
		boolean passwordChanged = false;
		if (passwordEncoder != null)
		{// dao.getUserPassword(user.getUsername()) vs user.getPassword() is
			// always same, need other method, Checkie 2008.08.22
			// Check whether we have to encrypt (or re-encrypt) the password
			if (user.getId() == null)
			{
				// New user, always encrypt
				passwordChanged = true;
			} else if (user.getOldPassword()!=null && !user.getPassword().equals(user.getOldPassword()))
			{
				passwordChanged = true;
			}

			// If password was changed (or new user), encrypt it
			if (passwordChanged)
			{
				log.warn(user.getUsername() + "'s password is changed");
				user.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));
				user.setConfirmPassword(user.getPassword());
				user.setOldPassword(user.getPassword());
			}
		} else
		{
			log.warn("PasswordEncoder not set, skipping password encryption...");
		}

		try
		{
			userDao.save(user);
		} catch (DataIntegrityViolationException e)
		{
			e.printStackTrace();
			log.warn(e.getMessage());
			throw new UserExistsException("User '" + user.getUsername() + "' already exists!");
		} catch (EntityExistsException e)
		{ // needed for JPA
			e.printStackTrace();
			log.warn(e.getMessage());
			throw new UserExistsException("User '" + user.getUsername() + "' already exists!");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeUser(String userId)
	{
		log.debug("removing user: " + userId);
		userDao.remove(userDao.get(new Long(userId)));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param username
	 *            the login name of the human
	 * @return User the populated user object
	 * @throws UsernameNotFoundException
	 *             thrown when username not found
	 */
	public User getUserByUsername(String username) throws UsernameNotFoundException
	{
		return (User) userDao.getUserByUsername(username);
	}

	public List<User> getAllHeroBoardUsers()
	{
		return userDao.getAllHeroBoardUsers();
	}

	public User getUserByEmail(String email) throws UsernameNotFoundException
	{
		try
		{
			return userDao.getUserByEmail(email);
		} catch (Exception e)
		{
			return null;
		}
	}

	@Override
	public List getAllUserInfo() {
		// TODO Auto-generated method stub
		return userDao.getAllUserInfo();
	}

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }


    public List<Role> getRoles() {
        return roleDao.getAll();
    }

    public Role getRole(String rolename) {
        return roleDao.getRoleByName(rolename);
    }


    public void saveRole(Role role) {
        roleDao.save(role);
    }

	@Override
	public Role getRole(Long roleId) {
		return roleDao.get(roleId);
	}

	@Override
	public void removeRole(Role role) {
		roleDao.remove(role);
	}

	@Override
	public Role getRoleByName(String rolename) {
		return roleDao.getRoleByName(rolename);
	}

	@Override
	public List getUsersByPreName(String preName) {
		return userDao.getUsersByPreName(preName);
	}

	@Override
	public User getUserByToken(String token) {
		return userDao.getUserByToken(token) ;
	}

	@Override
	public Page getPageOfUsersByRoleName(int pageNo, int pageSize, String roleName, String orderBy, boolean sort) {
		return userDao.getPageOfUsersByRoleName(pageNo, pageSize, roleName, orderBy, sort);
	}

	@Override
	public User getUserByAlias(String alias) {
		return userDao.getUserByAlias(alias);
	}
	public static void main(String [] args) throws Exception
	{
		User test = new User();
		test.setUsername("test!");
		UserManagerImpl handle = new UserManagerImpl();
		handle.getUserByUsername("12");
		 
		System.out.print("ss");
	}
}
