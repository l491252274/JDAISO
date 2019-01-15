package com.unlimited.appserver.service.impl;

import java.util.List;

import com.unlimited.appserver.dao.AppProblemTreeKeyStringDao;
import com.unlimited.appserver.model.AppProblemTreeKeyString;
import com.unlimited.appserver.service.AppProblemTreeKeyStringManager;
import com.unlimited.oj.service.impl.GenericManagerImpl;

public class AppProblemTreeKeyStringManagerImpl extends GenericManagerImpl<AppProblemTreeKeyString,Long> implements AppProblemTreeKeyStringManager {

	
	private AppProblemTreeKeyStringDao appProblemTreeKeyStringDao;

	public AppProblemTreeKeyStringDao getAppProblemTreeKeyStringDao() {
		return appProblemTreeKeyStringDao;
	}

	public void setAppProblemTreeKeyStringDao(AppProblemTreeKeyStringDao appProblemTreeKeyStringDao) {
		super.dao=appProblemTreeKeyStringDao;
		this.appProblemTreeKeyStringDao = appProblemTreeKeyStringDao;
	}

	public List<AppProblemTreeKeyString> getAllKeyString() {
		return appProblemTreeKeyStringDao.getAllKeyString();
	}
}
