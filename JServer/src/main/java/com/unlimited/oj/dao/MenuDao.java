/***********************************************************************
 * Module:  MenuDao.java
 * Author:  develop
 * Purpose: Defines data access interface for class Menu
 ***********************************************************************/
 
package com.unlimited.oj.dao;

import java.util.*;
import java.io.Serializable;
import com.unlimited.oj.model.*;
import com.unlimited.oj.dao.GenericDao;
import com.unlimited.oj.dao.DaoException;

/**
 * DAO interface that defines data access methods for class Menu
 * 
 */
public interface MenuDao extends GenericDao<Menu,Long> 
{  
	List getMenusByParentId(Long parentId);
	List getMenusByType(String type);
}