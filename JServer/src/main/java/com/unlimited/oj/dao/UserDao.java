package com.unlimited.oj.dao;

import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;

import com.unlimited.oj.dao.support.Page;
import com.unlimited.oj.model.User;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User Data Access Object (GenericDao) interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface UserDao extends GenericDao<User, Long>{

    /**
     * Gets users information based on login name.
     * @param username the user's username
     * @return userDetails populated userDetails object
     * @throws org.springframework.security.userdetails.UsernameNotFoundException thrown when user not found in database
     */
    @Transactional
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    /**
     * Gets a list of users ordered by the uppercase version of their username.
     *
     * @return List populated list of users
     */
    //List<User> getUsers();

    /**
     * Saves a user's information.
     * @param user the object to be saved
     * @return the persisted User object
     */
    //User saveUser(User user);

    /**
     * Retrieves the password in DB for a user
     * @param username the user's username
     * @return the password in DB, if the user is already persisted
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    String getUserPassword(String username);
    
    @Transactional
    public User getUserByUsername(String username) throws UsernameNotFoundException;
    
    @Transactional
    public User getUserByEmail(String email) throws UsernameNotFoundException;
    
    @Transactional
    public List<User> getAllHeroBoardUsers();
    
    public List getAllUserInfo();
    
    List getUsersByPreName(String preName);
    
    User getUserByToken(String token);

    Page getPageOfUsersByRoleName(int pageNo, int pageSize, String roleName, String orderBy, boolean sort);
    
    User getUserByAlias(String alias);
}
