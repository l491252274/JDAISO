package com.unlimited.oj.util;

import java.util.*;

public class ProblemSource
{
	private static List<String> problemSourceList = new ArrayList<String>();

	static
	{
		problemSourceList = new ArrayList<String>();
        problemSourceList.add("pku");
        problemSourceList.add("timus");
        problemSourceList.add("zju");
        problemSourceList.add("hdu");
	}
	
	public static List<String> getProblemSourceList()
	{
		return  problemSourceList;
	}
}
