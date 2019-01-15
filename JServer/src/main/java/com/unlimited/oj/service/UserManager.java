package com.unlimited.oj.service;

import java.util.List;

import org.springframework.security.userdetails.UsernameNotFoundException;
import com.unlimited.oj.dao.*;
import com.unlimited.oj.dao.support.Page;
import com.unlimited.oj.model.*;


/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *  Modified by <a href="mailto:dan@getrolling.com">Dan Kibler </a> 
 */
public interface UserManager extends GenericManager<User, Long> {

    /**
     * Convenience method for testing - allows you to mock the DAO and set it on an interface.
     * @param userDao the UserDao implementation to use
     */
    void setUserDao(UserDao userDao);
    User getUser(String userId);
    User getUserByUsername(String username) throws UsernameNotFoundException;
    User getUserByEmail(String email) throws UsernameNotFoundException;
    User getUserByAlias(String alias);
    List getUsers();
    void saveUser(User user) throws UserExistsException;
    void removeUser(String userId);
    List getUsersByPreName(String preName);
    Page getPageOfUsersByRoleName(int pageNo, int pageSize, String roleName, String orderBy, boolean sort);
    User getUserByToken(String token);
   
    List getAllUserInfo();
    
    // Role
    void setRoleDao(RoleDao roleDao);
    List getRoles();
    Role getRole(Long roleId);
    void saveRole(Role role);
    void removeRole(Role role);
    //void removeRoleByName(String rolename);
    Role getRoleByName(String rolename);

}
