package com.unlimited.appserver.enums;

public enum AppEcho {
	SUCCESS(0), PARAMETER_WRONG(1), PASSWORD_WRONG(2), PERMISSION_DENIED(3), TOKEN_WRONG(4), ACCOUNT_NOT_EXISTS(
			5), SERVLCE_WRONG(6), RESULT_NULL(7), UNKNOW(1001);
	
	private int index;

	private AppEcho(int x) {
		index=x;
	}
	
	public int getCode(){
		return index;
	}
	
	public AppEcho valueOf(int x){
		for(AppEcho i:AppEcho.values()){
			if(i.getCode()==x){
				return i;
			}
		}
		return UNKNOW;
	}
}
