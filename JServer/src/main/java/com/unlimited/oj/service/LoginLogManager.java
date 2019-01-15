package com.unlimited.oj.service;

import java.util.List;

import com.unlimited.oj.dao.LoginLogDao;
import com.unlimited.oj.dao.support.Page;
import com.unlimited.oj.model.LoginLog;


/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *  Modified by <a href="mailto:dan@getrolling.com">Dan Kibler </a> 
 */
public interface LoginLogManager extends GenericManager<LoginLog, Long> {

    /**
     * Convenience method for testing - allows you to mock the DAO and set it on an interface.
     * @param loginLogDao the LoginLogDao implementation to use
     */
    void setLoginLogDao(LoginLogDao loginLogDao);

    /**
     * Retrieves a loginLog by loginLogId.  An exception is thrown if loginLog not found
     *
     * @param loginLogId the identifier for the loginLog
     * @return LoginLog
     */
    LoginLog getLoginLog(String loginLogId);

    /**
     * Saves a loginLog's information.
     *
     * @param loginLog the loginLog's information
     * @throws ObjectExistsException thrown when loginLog already exists
     * @return loginLog the updated loginLog object
     */
    void saveLoginLog(LoginLog loginLog) throws Exception;

    /**
     * Removes a loginLog from the database by their loginLogId
     *
     * @param loginLogId the loginLog's id
     */
    void removeLoginLog(String loginLogId);

    void deleteAllLoginLog();
    
    Page getPageByUsername(String username,int pageNo, int pageSize, String orderBy, boolean sort);
}
