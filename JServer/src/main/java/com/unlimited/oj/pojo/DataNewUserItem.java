	package com.unlimited.oj.pojo;
	
	public class DataNewUserItem
	{
		public String userName;
		public String pass;
		public String school;
		public String studentNumber;
		public String className;
		public String trueName;
		public String nick;
		public boolean isNew;
		public String alias;
		public String location;

		public DataNewUserItem()
		{
			userName = pass = className = trueName = nick = alias = location = "";
			isNew = false;
		}
	}