package com.unlimited.appserver.service.impl;

import java.util.List;

import com.unlimited.appserver.dao.AppUserDao;
import com.unlimited.appserver.dao.exception.AppUserNotFoundException;
import com.unlimited.appserver.model.AppUser;
import com.unlimited.appserver.service.AppUserManager;
import com.unlimited.oj.service.impl.GenericManagerImpl;

public class AppUserManagerImpl extends GenericManagerImpl<AppUser, Long> implements AppUserManager{

	
	private AppUserDao appUserDao;
	
	@Override
	public void setAppUserDao(AppUserDao appUserDao) {
		super.dao=appUserDao;
		this.appUserDao=appUserDao;
	}
	
	public AppUserDao getAppUserDao(){
		return appUserDao;
	}

	@Override
	public AppUser getUser(String id) throws AppUserNotFoundException {
		AppUser ans=null;
		ans=appUserDao.get(new Long(id));
		if(ans==null){
			throw new AppUserNotFoundException(id);
		}
		return ans;
	}

	@Override
	public AppUser getAppUserByAccount(String account)
			throws AppUserNotFoundException {
		return appUserDao.getAppUserByAccount(account);
	}

	@Override
	public List<AppUser> getAppUsers() {
		return appUserDao.getAll();
	}

	@Override
	public boolean isPasswordCorrect(String account, String password)
			throws AppUserNotFoundException {
		AppUser user = null;
		try
		{
			user=appUserDao.getAppUserByAccount(account);
		}catch(Exception e){}
		if(user==null) return false;
		return user.getPassword().equals(password);
	}

	@Override
	public void save(AppUser user) {
		appUserDao.save(user);
	}

	@Override
	public void remove(AppUser user) {
		appUserDao.remove(user);
	}



}
