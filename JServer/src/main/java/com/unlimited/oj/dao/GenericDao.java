/***********************************************************************
 * Module:  GenericDao.java
 * Author:  benQ
 * Purpose: Defines common interface for data access
 ***********************************************************************/

package com.unlimited.oj.dao;


import java.util.*;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.*;
import com.unlimited.oj.dao.support.Page;
import com.unlimited.oj.dao.support.QueryCondition;

import org.hibernate.type.Type;

/** @author <a href="mailto:checkie_chen@scau.edu.cn">Checkie</a>
 * @param <T> a type variable
 * @param <PK> the primary key for that type */
public interface GenericDao <T,PK extends Serializable>
{
   /** 
    * 得到所有对象
    */
   public List<T> getAll();
   
   /**
    * 得到所有具有特定签名的对象
    */
   public List<T> getAll(String sign);

   /**
    * 根据ID取得对象
    * @param id
    */
   public T get(PK id);
   

   /**
    * 根据ID取得具有特定签名的对象
    * @param id
    * @author Checkie
    */
   public T get(PK id, String sign);

   /**
    * 判断对象是否存在
    * @param id
    * @return the modified user (with a primary key set if they're new)
    */
   public boolean exists(PK id);
   
	/**
	 * 保存对象.
	 */
   public void save(T object);
   
   
	/**
	 * 获取全部对象,带排序字段与升降序参数.
	 */
	public List<T> getAll(String orderBy, boolean isAsc);

    /**
     * 更新数据库中的一条记录。
     *
     * @param o 要更新的数据库中的记录。
     */
    public void update(Object o);

    /**
	 * 删除对象.
	 */
	public void remove(Object o);

	/**
	 * 根据ID删除对象.
	 */
	public void removeById(Serializable id);

	public void flush();

	public void clear();

	/**
	 * 创建Query对象. 对于需要first,max,fetchsize,cache,cacheRegion等诸多设置的函数,可以在返回Query后自行设置.
	 * 留意可以连续设置,如下：
	 * <pre>
	 * dao.getQuery(hql).setMaxResult(100).setCacheable(true).list();
	 * </pre>
	 * 调用方式如下：
	 * <pre>
	 *        dao.createQuery(hql)
	 *        dao.createQuery(hql,arg0);
	 *        dao.createQuery(hql,arg0,arg1);
	 *        dao.createQuery(hql,new Object[arg0,arg1,arg2])
	 * </pre>
	 *
	 * @param values 可变参数.
	 */
	public Query createQuery(String hql, Object... values);

	/**
	 * 创建Criteria对象.
	 *
	 * @param criterions 可变的Restrictions条件列表,见{@link #createQuery(String,Object...)}
	 */
	public Criteria createCriteria(Criterion... criterions);

	/**
	 * 创建Criteria对象，带排序字段与升降序字段.
	 *
	 * @see #createCriteria(Class,Criterion[])
	 */
	public Criteria createCriteria(String orderBy, boolean isAsc, Criterion... criterions);

	/**
	 * 根据hql查询,直接使用HibernateTemplate的find函数.
	 *
	 * @param values 可变参数,见{@link #createQuery(String,Object...)}
	 */
	public List<T> find(String hql, Object... values);

	/**
	 * 根据属性名和属性值查询对象.
	 *
	 * @return 符合条件的对象列表
	 */
	public List<T> findBy(String propertyName, Object value);

	/**
	 * 根据属性名和属性值查询对象,带排序参数.
	 */
	public List<T> findBy(String propertyName, Object value, String orderBy, boolean isAsc);

	/**
	 * 根据属性名和属性值查询唯一对象.
	 *
	 * @return 符合条件的唯一对象 or null if not found.
	 */
	public T findUniqueBy(String propertyName, Object value);

	/**
	 * 分页查询函数，使用hql.
	 *
	 * @param pageNo 页号,从1开始.
	 */
	public Page pagedQuery(String hql, int pageNo, int pageSize, Object... values);

	/**
	 * 分页查询函数，使用已设好查询条件与排序的<code>Criteria</code>.
	 *
	 * @param pageNo 页号,从1开始.
	 * @return 含总记录数和当前页数据的Page对象.
	 */
	public Page pagedQuery(Criteria criteria, int pageNo, int pageSize);

    /**
     * 分页查询函数，根据this.persistentClass和查询条件参数,排序参数创建默认的<code>Criteria</code>.
     * 
     * 该方法可以直接在业务逻辑层中使用
     *
     * @param conditions    条件列表.
     * @param pageNo 页号,从1开始.
     * @param pageSize 每页记录数.
     * @param orderBy 排序的列名.
     * @param isAsc
     * @return 含总记录数和当前页数据的Page对象.
     */
	public Page pagedQuery(List<QueryCondition> conditions, int pageNo, int pageSize, String orderBy, boolean isAsc);

    /**
     * 执行hql语句从数据库中查询最多maxResults条记录，HQL语句可以含有参数
     *
     * @param hql        查询使用hql语句
     * @param values     Hibernate的Query类的setParameters需要的参数
     * @param types      Hibernate的Query类的setParameters需要的参数
     * @param maxResults 最多查询的记录数目
     *
     * @return           查询结果
     */
    public List find(String hql, Object[] values, Type[] types,int maxResults);

    /**
	 * 分页查询函数，根据this.persistentClass和查询条件参数创建默认的<code>Criteria</code>.
	 *
	 * @param pageNo 页号,从1开始.
	 * @return 含总记录数和当前页数据的Page对象.
	 */
	public Page pagedQuery(int pageNo, int pageSize, Criterion... criterions);

	/**
	 * 分页查询函数，根据this.persistentClass和查询条件参数,排序参数创建默认的<code>Criteria</code>.
	 *
	 * @param pageNo 页号,从1开始.
	 * @return 含总记录数和当前页数据的Page对象.
	 */
	public Page pagedQuery(int pageNo, int pageSize, String orderBy, boolean isAsc,
						   Criterion... criterions);

	/**
	 * 判断对象某些属性的值在数据库中是否唯一.
	 *
	 * @param uniquePropertyNames 在POJO里不能重复的属性列表,以逗号分割 如"name,loginid,password"
	 */
	public boolean isUnique(Object entity, String uniquePropertyNames);

	/**
	 * 取得对象的主键值,辅助函数.
	 */
	public Serializable getId(Object entity) throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException;

	/**
	 * 取得对象的主键名,辅助函数.
	 */
	public String getIdName(Class clazz);

	/**
	 * 得到指定页
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
    Page getPage(int pageNo, int pageSize);

    /**
     * 得到排序的指定而
     * @param pageNo
     * @param pageSize
     * @param orderBy
     * @param sort
     * @return
     */
    Page getPage(int pageNo, int pageSize, String orderBy, boolean sort);

    /**
     * 通过查询条件得到排序的指定页
     * @param pageNo
     * @param pageSize
     * @param key
     * @param value
     * @return
     */
    Page getPageBySearch(int pageNo, int pageSize, String key, Object value);
	
    void deleteAll(List list);
}