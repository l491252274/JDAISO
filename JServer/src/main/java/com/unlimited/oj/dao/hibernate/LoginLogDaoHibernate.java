/***********************************************************************
 * Module:  LoginLogDaoHibernate.java
 * Author:  benQ
 * Purpose: Defines data access implementation class for class LoginLog
 ***********************************************************************/
package com.unlimited.oj.dao.hibernate;

import java.util.*;
import java.io.Serializable;
import com.unlimited.oj.model.*;
import com.unlimited.oj.dao.GenericDao;
import com.unlimited.oj.dao.DaoException;

import com.unlimited.oj.dao.LoginLogDao;
import com.unlimited.oj.dao.hibernate.GenericDaoHibernate;
import com.unlimited.oj.dao.support.Page;
import com.unlimited.oj.dao.support.QueryCondition;

import org.hibernate.Criteria;

/**
 * Class that implements CustomerDao interface
 * 
 */
public class LoginLogDaoHibernate extends GenericDaoHibernate<LoginLog, Long> implements LoginLogDao
{

    public LoginLogDaoHibernate()
    {
        super(LoginLog.class);
        // TODO Auto-generated constructor stub
    }

    public void deleteAll()
    {
        Page page = pagedQuery("from LoginLog", 1, 10000);
        List temp = null;
        while((temp=page.getList())!=null && temp.size()>0)
        {
            getHibernateTemplate().deleteAll(temp);
            page = pagedQuery("from LoginLog", 1, 10000);
            getHibernateTemplate().clear();
        }
    }

	@Override
	public Page getPageByUsername(String username, int pageNo, int pageSize, String orderBy, boolean sort) {
        if (pageSize <= 0)
            pageSize = Page.DEFAULT_PAGE_SIZE;

        List<QueryCondition> conditions = new LinkedList<QueryCondition>();
        QueryCondition condition = new QueryCondition("userName", "eq", username);
        conditions.add(condition);
        return pagedQuery(conditions, pageNo, pageSize, orderBy, sort);
	}
}
