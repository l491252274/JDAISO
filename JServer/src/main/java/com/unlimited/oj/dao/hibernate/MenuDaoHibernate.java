/***********************************************************************
 * Module:  MenuDaoHibernate.java
 * Author:  develop
 * Purpose: Defines data access implementation class for class Menu
 ***********************************************************************/
 
package com.unlimited.oj.dao.hibernate;

import java.util.*;
import java.io.Serializable;

import org.hibernate.criterion.DetachedCriteria;

import com.unlimited.oj.model.*;
import com.unlimited.oj.dao.GenericDao;
import com.unlimited.oj.dao.DaoException;

import com.unlimited.oj.dao.MenuDao;
import com.unlimited.oj.dao.hibernate.GenericDaoHibernate;

/**
 * Class that implements CustomerDao interface
 * 
 */
public class MenuDaoHibernate extends GenericDaoHibernate<Menu,Long> implements MenuDao 
{
	public MenuDaoHibernate()
	{
		super(Menu.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List getMenusByParentId(Long parentId) {
        String hql = "from Menu where parentID=? order by resourceOrder asc";
        List list = getHibernateTemplate().find(hql, parentId);
        if (list.size() > 0)
        {
            return list;
        } else
        {
            return null;
        }
	}

	@Override
	public List getMenusByType(String type) {
        String hql = "from Menu where resourceType=? order by resourceOrder asc";
        List list = getHibernateTemplate().find(hql, type);
        if (list.size() > 0)
        {
            return list;
        } else
        {
            return null;
        }
	}
}