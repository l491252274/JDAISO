/***********************************************************************
 * Module:  Page.java
 * Author:  benQ
 * Purpose: Defines page class
 ***********************************************************************/

package com.unlimited.oj.dao.support;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;

/**
 * 分页对象. 包含当前页数据及分页信息如总记录数.
 * 可以直接用于displayTag
 *
 * @author Checkie
 */
@SuppressWarnings("serial")
public class Page implements PaginatedList, Serializable {

	public static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * 每页的记录数
     */
    private int objectsPerPage = DEFAULT_PAGE_SIZE;

	private int start; // 当前页第一条数据在List中的位置,从0开始
	private List list; // 当前页中存放的记录,类型一般为List
	private int fullListSize; // 总记录数
        private String version; // 记录版本，对缓存有用

	/**
	 * 构造方法，只构造空页.
	 */
	public Page() {
		this(0, 0, DEFAULT_PAGE_SIZE, new ArrayList());
	}

	/**
	 * 默认构造方法.
	 *
	 * @param start	 本页数据在数据库中的起始位置
	 * @param totalSize 数据库中总记录条数
	 * @param pageSize  本页容量
	 * @param data	  本页包含的数据
	 */
	public Page(int start, int totalSize, int pageSize, List list) {
		this.objectsPerPage = pageSize;
		this.start = start;
		this.fullListSize = totalSize;
		this.list = list;
	}

	/**
	 * 取总页数.
	 */
	public long getTotalPageCount() {
		if (fullListSize % objectsPerPage == 0)
			return fullListSize / objectsPerPage;
		else
			return fullListSize / objectsPerPage + 1;
	}

	/**
	 * 该页是否有下一页.
	 */
	public boolean hasNextPage() {
		return this.getPageNumber() < this.getTotalPageCount() - 1;
	}

	/**
	 * 该页是否有上一页.
	 */
	public boolean hasPreviousPage() {
		return this.getPageNumber() > 1;
	}

	/**
	 * 获取任一页第一条数据在数据集的位置，每页条数使用默认值.
	 *
	 * @see #getStartOfPage(int,int)
	 */
	protected static int getStartOfPage(int pageNo) {
		return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
	}

	/**
	 * 获取任一页第一条数据在数据集的位置.
	 *
	 * @param pageNo   从1开始的页号
	 * @param pageSize 每页记录条数
	 * @return 该页第一条数据
	 */
	public static int getStartOfPage(int pageNo, int pageSize) {
		return (pageNo - 1) * pageSize;
	}

    /**
     * 取总记录数.
     */
    public int getFullListSize()
    {
        return this.fullListSize;
    }

    public List getList()
    {
        return list;
    }

    /**
     * 取每页数据容量.
     */
    public int getObjectsPerPage()
    {
        return objectsPerPage;
    }

    /**
     * 取该页当前页码,页码从1开始.
     */
    public int getPageNumber()
    {
        return start / objectsPerPage + 1;
    }

    public String getSearchId()
    {
        return null;
    }

    public String getSortCriterion()
    {
        return null;
    }

    public SortOrderEnum getSortDirection()
    {
        return null;
    }

    public void setList(List list)
    {
        this.list = list;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }


}