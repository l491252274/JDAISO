package com.unlimited.appserver.dao.hibernate;

import java.util.List;

import com.unlimited.appserver.dao.AppProblemTreeKeyStringDao;
import com.unlimited.appserver.model.AppProblemTreeKeyString;
import com.unlimited.oj.dao.hibernate.GenericDaoHibernate;

public class AppProblemTreeKeyStringDaoHibernate extends GenericDaoHibernate<AppProblemTreeKeyString, Long> implements AppProblemTreeKeyStringDao{

	
	/**
	 * Constructor that sets the entity to AppProblemTreeKeyString.class.
	 */
	public AppProblemTreeKeyStringDaoHibernate() {
		super(AppProblemTreeKeyString.class);
	}

	@Override
	public List<AppProblemTreeKeyString> getAllKeyString() {
		return super.getAll();
	}

}
