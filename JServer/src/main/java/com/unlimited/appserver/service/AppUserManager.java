package com.unlimited.appserver.service;

import java.util.List;

import com.unlimited.appserver.dao.AppUserDao;
import com.unlimited.appserver.dao.exception.AppUserNotFoundException;
import com.unlimited.appserver.model.AppUser;

/**
 * 
 * Business Service Interface to handle communication between web and
 * persistence layer.
 * @author 佳洋
 *
 */
public interface AppUserManager {
	
	/**
     * Convenience method for testing - allows you to mock the DAO and set it on an interface.
     * @param dao the AppUserDao implementation to use
     */
	void setAppUserDao(AppUserDao appUserDao);
	
	/**
    * Retrieves a user by userId.  
    * An exception is thrown if user not found
    *
    * @param id the identifier for the AppUser
    * @return AppUser
    */
   AppUser getUser(String id) throws AppUserNotFoundException;
   
   
   /**
    * find appUser by account 
    * An exception is thrown if user not found
    * 
    * @param account
    * @return AppUser which account is account(param)
    * @throws AppUserNotFoundException
    */
   AppUser getAppUserByAccount(String account) throws AppUserNotFoundException;
   
   /**
    * Retrieves a list of AppUsers, filtering with parameters on a AppUser object
    * @return List
    */
   List<AppUser> getAppUsers();
   
   
   /**
    * Judge the password is correct to the account
    * throws an exception if the account isn't exists
    * @param account the user's account
    * @param password the user's password
    * @return boolean
    * @throws AppUserNotFoundException
    */
   boolean isPasswordCorrect(String account,String password) throws AppUserNotFoundException;
   
   
   /**
    * Save the user's information
    * @param user the user to save
    */
   void save(AppUser user);
   
   
   /**
    * Delete the user
    * @param user the user to delete
    */
   void remove(AppUser user);

}
