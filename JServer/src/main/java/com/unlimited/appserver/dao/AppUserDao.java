package com.unlimited.appserver.dao;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.unlimited.appserver.dao.exception.AppUserNotFoundException;
import com.unlimited.appserver.model.AppUser;
import com.unlimited.oj.dao.GenericDao;


public interface AppUserDao extends GenericDao<AppUser, Long> {
	
	/**
	 * 通过用户账号去查找用户信息
	 * @param account 被查询的用户的账号
	 * @return	Appuser 查询结果
	 * @throws AppUserNotFoundException
	 */
	@Transactional
	AppUser getAppUserByAccount(String account) throws AppUserNotFoundException;
	
	
	/**
	 * 根据用户账号查询用户密码
	 * @param account 被查询的用户的账号
	 * @return password 对应用户的密码
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	String getAppUserPassword(String account);
	
	/**
	 * 查询一个账号和密码是否对应
	 * @param account 被查询的账号
	 * @param password	被查询账号的密码
	 * @return	boolean 查询结果
	 * @throws AppUserNotFoundException
	 */
	@Transactional
	boolean isPasswordRight(String account,String password) throws AppUserNotFoundException;
	

}
