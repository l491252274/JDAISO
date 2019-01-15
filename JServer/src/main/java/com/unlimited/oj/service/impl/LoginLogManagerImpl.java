package com.unlimited.oj.service.impl;

import com.unlimited.oj.dao.LoginLogDao;
import com.unlimited.oj.dao.support.Page;
import com.unlimited.oj.dao.support.QueryCondition;
import com.unlimited.oj.model.LoginLog;
import com.unlimited.oj.service.LoginLogManager;

import java.util.*;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityExistsException;

/**
 * Implementation of SolutionManager interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class LoginLogManagerImpl extends GenericManagerImpl<LoginLog, Long> implements
        LoginLogManager
{

    private LoginLogDao dao;

    /**
     * Set the Dao for communication with the data layer.
     * @param dao the LoginLogDao that communicates with the database
     */
    @Required
    public void setLoginLogDao(LoginLogDao dao)
    {
        super.dao = dao;
        this.dao = dao;
    }

    /**
     * {@inheritDoc}
     */
    public LoginLog getLoginLog(String loginLogId)
    {
        return dao.get(new Long(loginLogId));
    }

    /**
     * {@inheritDoc}
     */
    public void saveLoginLog(LoginLog loginLog) throws Exception
    {
        try
        {
            dao.save(loginLog);
        } catch (DataIntegrityViolationException e)
        {
            e.printStackTrace();
            log.fatal(e.getMessage());
            throw new Exception("LoginLog '" + loginLog.getId() + "' already exists!");
        } catch (EntityExistsException e)
        { // needed for JPA
            e.printStackTrace();
            log.warn(e.getMessage());
            throw new Exception("LoginLog '" + loginLog.getId() + "' already exists!");
        }
    }

    /**
     * {@inheritDoc}
     */
    public void removeLoginLog(String loginLogId)
    {
        log.debug("removing loginLog: " + loginLogId);
        dao.remove(dao.get(new Long(loginLogId)));
    }

    public void deleteAllLoginLog()
    {
        dao.deleteAll();
    }

	@Override
	public Page getPageByUsername(String username,int pageNo, int pageSize, String orderBy, boolean sort) {
		// TODO Auto-generated method stub
		return dao.getPageByUsername(username, pageNo, pageSize, orderBy, sort);
	}
}
