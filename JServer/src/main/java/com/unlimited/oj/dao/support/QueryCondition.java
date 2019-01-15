/***********************************************************************
 * Module:  QueryCondition.java
 * Author:  benQ
 * Purpose: Defines QueryCondition class
 ***********************************************************************/

package com.unlimited.oj.dao.support;


public class QueryCondition
{
	private String key;
	private String oper;
	private Object value;
	
	public QueryCondition(String key, String oper, Object value)
	{
		this.key = key;
		this.oper = oper;
		this.value = value;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public String getOper()
	{
		return oper;
	}

	public void setOper(String oper)
	{
		this.oper = oper;
	}

	public Object getValue()
	{
		return value;
	}

	public void setValue(Object value)
	{
		this.value = value;
	}
}