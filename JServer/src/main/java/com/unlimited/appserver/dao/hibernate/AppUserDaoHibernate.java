package com.unlimited.appserver.dao.hibernate;

import java.util.ArrayList;

import javax.persistence.Table;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import com.unlimited.appserver.dao.AppUserDao;
import com.unlimited.appserver.dao.exception.AppUserNotFoundException;
import com.unlimited.appserver.model.AppUser;
import com.unlimited.oj.dao.hibernate.GenericDaoHibernate;


/**
 * This class interacts with Spring's HibernateTemplate to save/delete and
 * retrieve AppUser objects.
 * @author 佳洋
 *
 */
public class AppUserDaoHibernate extends GenericDaoHibernate<AppUser, Long> implements AppUserDao{

	
	/**
	 * Constructor that sets the entity to AppUser.class.
	 */
	public AppUserDaoHibernate() {
		super(AppUser.class);
	}


	@Override
	public AppUser getAppUserByAccount(String account) throws AppUserNotFoundException {

		@SuppressWarnings("unchecked")
		ArrayList<AppUser> list=(ArrayList<AppUser>) getHibernateTemplate().find("from appuser where account=?", account);
		if(list==null||list.isEmpty()){
			throw new AppUserNotFoundException(account);
		}
		return list.get(0);
	}

	
	@Override
	public String getAppUserPassword(String account){
		SimpleJdbcTemplate jdbcTemplate = new SimpleJdbcTemplate(SessionFactoryUtils.getDataSource(getSessionFactory()));
		Table table = AnnotationUtils.findAnnotation(AppUser.class, Table.class);
		return jdbcTemplate.queryForObject("select password from " + table.name() + " where account=?", String.class, account);

	}

	
	
	@Override
	public boolean isPasswordRight(String account, String password){
		String ansString=getAppUserPassword(account);
		if(ansString==null){
			return false;
		}
		return password.equals(ansString);
	}

}
