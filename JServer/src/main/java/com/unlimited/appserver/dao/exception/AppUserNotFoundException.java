package com.unlimited.appserver.dao.exception;

/**
 * this class related to AppUserDao
 * when query an appuser, if the user not exists
 * this exception will throws
 * @author 佳洋
 *
 */
public class AppUserNotFoundException extends Exception{

	private static final long serialVersionUID = 3927369578070727299L;

	
	public AppUserNotFoundException(){
		super("can't find user");
	}
	
	public AppUserNotFoundException(String message){
		super("can't find user:"+message);
	}

}
