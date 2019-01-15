package com.unlimited.appserver.service;

import java.util.List;

import com.unlimited.appserver.dao.AppProblemTreeKeyStringDao;
import com.unlimited.appserver.model.AppProblemTreeKeyString;

public interface AppProblemTreeKeyStringManager {
	void setAppProblemTreeKeyStringDao( AppProblemTreeKeyStringDao  appProblemTreeKeyStringDao);
	List<AppProblemTreeKeyString> getAllKeyString();
}
