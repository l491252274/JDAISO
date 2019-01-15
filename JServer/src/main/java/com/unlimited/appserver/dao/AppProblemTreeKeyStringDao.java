package com.unlimited.appserver.dao;

import java.util.List;

import com.unlimited.appserver.model.AppProblemTreeKeyString;
import com.unlimited.oj.dao.GenericDao;

public interface AppProblemTreeKeyStringDao extends GenericDao<AppProblemTreeKeyString,Long>{
	
	List<AppProblemTreeKeyString> getAllKeyString();

}
