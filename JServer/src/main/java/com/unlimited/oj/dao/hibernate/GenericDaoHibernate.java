/***********************************************************************
 * Module:  GenericDaoHibernate.java
 * Author:  benQ
 * Purpose: GenericDaoHibernate implements GenericDao
 ***********************************************************************/

package com.unlimited.oj.dao.hibernate;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.unlimited.oj.dao.GenericDao;
import com.unlimited.oj.dao.support.Page;
import com.unlimited.oj.dao.support.QueryCondition;
import com.unlimited.oj.dao.utils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * @author <a href="mailto:checkie_chen@scau.edu.cn">Checkie Chen</a>
 * @param <T> a type variable
 * @param <PK> the primary key for that type 
 */
public class GenericDaoHibernate<T, PK extends Serializable> extends
        HibernateDaoSupport implements GenericDao<T, PK>
{

    private Class<T> persistentClass;

    /** Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Log log = LogFactory.getLog(getClass());

    /** Constructor that takes in a class to see which type of entity to persist
     * @param persistentClass
     * @return List of populated objects
     */
    public GenericDaoHibernate(final Class<T> persistentClass)
    {
        this.persistentClass = persistentClass;
    }

    /** 
     * 得到所有对象
     */
    public List<T> getAll()
    {
        return getHibernateTemplate().loadAll(this.persistentClass);
    }

    /**
     * 得到所有具有特定签名的对象
     */
    public List<T> getAll(String sign)
    {
        return findBy("sign", sign);
    }

    /**
     * 根据ID取得对象
     * @param id
     */
    public T get(PK id)
    {
        T entity = (T) getHibernateTemplate().get(this.persistentClass, id);
        /*
            if (entity == null) 
            {
                log.warn("Uh oh, '" + this.persistentClass + "' object with id '" + id + "' not found...");
                throw new ObjectRetrievalFailureException(this.persistentClass, id);
            }
        */
        return entity;
    }

    /**
     * 根据ID取得具有特定签名的对象
     * @param id
     * @author Checkie
     */
    public T get(PK id, String sign)
    {
        List list = getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(this.persistentClass)
                .add(org.hibernate.criterion.Restrictions.eq("id", id))
                .add(org.hibernate.criterion.Restrictions.eq("sign", sign)));;
        if(list==null || list.size()<1)
            return null;
        return (T)list.get(0);
    }

    /** 
     * 判断对象是否存在
     * @param id
     * @return the modified user (with a primary key set if they're new)
     */
    public boolean exists(PK id)
    {
        T entity = (T) getHibernateTemplate().get(this.persistentClass, id);
        return entity != null;
    }

    /**
     * 保存对象.
     */
    public void save(T object)
    {
        getHibernateTemplate().saveOrUpdate(object);
        getHibernateTemplate().flush();
    }

    /**
     * 获取全部对象,带排序字段与升降序参数.
     */
    public List<T> getAll(String orderBy, boolean isAsc)
    {
        Assert.hasText(orderBy);
        if (isAsc)
            return getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(this.persistentClass).addOrder(Order.asc(orderBy)));
        else
            return getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(this.persistentClass).addOrder(Order.desc(orderBy)));
    }

    /**
     * 更新数据库中的一条记录。
     *
     * @param o 要更新的数据库中的记录。
     */
    public void update(Object o)
    {
        getHibernateTemplate().merge(o);
    }

    /**
     * 删除对象.
     */
    public void remove(Object o)
    {
        getHibernateTemplate().delete(o);
        getHibernateTemplate().flush();
    }

    /**
     * 根据ID删除对象.
     */
    public void removeById(Serializable id)
    {
        remove(get((PK) id));
    }

    public void flush()
    {
        getHibernateTemplate().flush();
    }

    public void clear()
    {
        getHibernateTemplate().clear();
    }

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
    public Query createQuery(String hql, Object... values)
    {
        Assert.hasText(hql);
        Query query = getSession().createQuery(hql);
        for (int i = 0; i < values.length; i++)
        {
            query.setParameter(i, values[i]);
        }
        return query;
    }

    /**
     * 创建Criteria对象.
     *
     * @param criterions 可变的Restrictions条件列表,见{@link #createQuery(String,Object...)}
     */
    public Criteria createCriteria(Criterion... criterions)
    {
        Criteria criteria = getSession().createCriteria(this.persistentClass);
        for (Criterion c : criterions)
        {
            criteria.add(c);
        }
        return criteria;
    }

    /**
     * 创建Criteria对象，带排序字段与升降序字段.
     *
     * @see #createCriteria(Class,Criterion[])
     */
    public Criteria createCriteria(String orderBy, boolean isAsc,
            Criterion... criterions)
    {
        Assert.hasText(orderBy);

        Criteria criteria = createCriteria(criterions);

        if (isAsc)
            criteria.addOrder(Order.asc(orderBy));
        else
            criteria.addOrder(Order.desc(orderBy));

        return criteria;
    }

    /**
     * 根据hql查询,直接使用HibernateTemplate的find函数.
     *
     * @param values 可变参数,见{@link #createQuery(String,Object...)}
     */
    public List<T> find(String hql, Object... values)
    {
        Assert.hasText(hql);
        return getHibernateTemplate().find(hql, values);
    }

    /**
     * 根据属性名和属性值查询对象.
     *
     * @return 符合条件的对象列表
     */
    public List<T> findBy(String propertyName, Object value)
    {
        Assert.hasText(propertyName);
        return createCriteria(Restrictions.eq(propertyName, value)).list();
    }

    /**
     * 根据属性名和属性值查询对象,带排序参数.
     */
    public List<T> findBy(String propertyName, Object value, String orderBy,
            boolean isAsc)
    {
        Assert.hasText(propertyName);
        Assert.hasText(orderBy);
        return createCriteria(orderBy, isAsc, Restrictions.eq(propertyName, value)).list();
    }

    /**
     * 根据属性名和属性值查询唯一对象.
     *
     * @return 符合条件的唯一对象 or null if not found.
     */
    public T findUniqueBy(String propertyName, Object value)
    {
        Assert.hasText(propertyName);
        return (T) createCriteria(Restrictions.eq(propertyName, value)).uniqueResult();
    }

    /**
     * 分页查询函数，使用hql.
     * 
     * 该方法只在继承该类的DAO类中使用,不建议在Manager类中直接使用,这样会使分层混乱
     *
     * @param pageNo 页号,从1开始.
     * 
     */
    public Page pagedQuery(String hql, int pageNo, int pageSize,
            Object... values)
    {
        Assert.hasText(hql);
        Assert.isTrue(pageNo >= 1, "pageNo should start from 1");
        // Count查询
        String countQueryString = " select count (*) " + removeSelect(removeOrders(hql));
        List countlist = getHibernateTemplate().find(countQueryString, values);
        int totalCount = ((Long) countlist.get(0)).intValue();

        if (totalCount < 1)
            return new Page();
        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        Query query = createQuery(hql, values);
        List list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();

        return new Page(startIndex, totalCount, pageSize, list);
    }

    /**
     * 分页查询函数，使用已设好查询条件与排序的<code>Criteria</code>.
     * 
     * 该方法只在继承该类的DAO类中使用,不建议在Manager类中直接使用,这样会使分层混乱
     *
     * @param pageNo 页号,从1开始.
     * @return 含总记录数和当前页数据的Page对象.
     */
    public Page pagedQuery(Criteria criteria, int pageNo, int pageSize)
    {
        Assert.notNull(criteria);
        Assert.isTrue(pageNo >= 1, "pageNo should start from 1");
        CriteriaImpl impl = (CriteriaImpl) criteria;

        // 先把Projection和OrderBy条件取出来,清空两者来执行Count操作
        Projection projection = impl.getProjection();
        List<CriteriaImpl.OrderEntry> orderEntries;
        try
        {
            orderEntries = (List) BeanUtils.forceGetProperty(impl, "orderEntries");
            BeanUtils.forceSetProperty(impl, "orderEntries", new ArrayList());
        } catch (Exception e)
        {
            throw new InternalError(" Runtime Exception impossibility throw ");
        }

        // 执行查询
        int totalCount = (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();

        // 将之前的Projection和OrderBy条件重新设回去
        criteria.setProjection(projection);
        if (projection == null)
        {
            criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        }

        try
        {
            BeanUtils.forceSetProperty(impl, "orderEntries", orderEntries);
        } catch (Exception e)
        {
            throw new InternalError(" Runtime Exception impossibility throw ");
        }

        // 返回分页对象
        if (totalCount < 1)
            return new Page();

        if (pageSize<=0)
            pageSize = Page.DEFAULT_PAGE_SIZE;
        
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List list = criteria.setFirstResult(startIndex).setMaxResults(pageSize).list();
        
        return new Page(startIndex, totalCount, pageSize, list);
    }

    /**
     * 执行hql语句从数据库中查询最多maxResults条记录，HQL语句可以含有参数
     *
     * @param hql        查询使用hql语句
     * @param values     Hibernate的Query类的setParameters需要的参数
     * @param types      Hibernate的Query类的setParameters需要的参数
     * @param maxResults 最多查询的记录数目
     *
     * @return           查询结果
     *        这个方法有时候有问题
     */
    public List find(String hql, Object[] values, Type[] types, int maxResults)
    {
        Session session = getSession();
        Query query = session.createQuery(hql);
        query.setParameters(values, types);
        query.setMaxResults(maxResults);
        List lst = query.list();
        session.close();
        return lst;
    }

    /**
     * 执行hql语句从数据库中查询最多maxResults条记录，HQL语句不可以含有参数
     *
     * @param hql        查询使用hql语句
     * @param maxResults 最多查询的记录数目
     * 
     * @return           以POJO形式封装的查询结果
     */
    public List find(String hql, int maxResults)
    {
        Session session = getSession();
        Query query = session.createQuery(hql);
        query.setMaxResults(maxResults);
        List lst = query.list();
        session.close();
        return lst;
    }

    /**
     * 分页查询函数，根据this.persistentClass和查询条件参数创建默认的<code>Criteria</code>.
     * 
     * 该方法只在继承该类的DAO类中使用,不建议在Manager类中直接使用,这样会使分层混乱
     *
     * @param pageNo 页号,从1开始.
     * @return 含总记录数和当前页数据的Page对象.
     */
    public Page pagedQuery(int pageNo, int pageSize, Criterion... criterions)
    {
        Criteria criteria = createCriteria(criterions);
        return pagedQuery(criteria, pageNo, pageSize);
    }

    /**
     * 分页查询函数，根据this.persistentClass和查询条件参数,排序参数创建默认的<code>Criteria</code>.
     * 
     * 该方法只在继承该类的DAO类中使用,不建议在Manager类中直接使用,这样会使分层混乱
     *
     * @param pageNo 页号,从1开始.
     * @return 含总记录数和当前页数据的Page对象.
     */
    public Page pagedQuery(int pageNo, int pageSize, String orderBy,
            boolean isAsc, Criterion... criterions)
    {
        Criteria criteria = createCriteria(orderBy, isAsc, criterions);
        return pagedQuery(criteria, pageNo, pageSize);
    }

    /**
     * 分页查询函数，根据this.persistentClass和查询条件参数,排序参数创建默认的<code>Criteria</code>.
     * 
     * 该方法可以直接在业务逻辑层中使用
     *
     * @param map    Conditions, 关键字列表.
     * @param pageNo 页号,从1开始.
     * @param pageSize 每页记录数.
     * @param orderBy 排序的列名.
     * @param isAsc
     * @return 含总记录数和当前页数据的Page对象.
     */
    public Page pagedQuery(List<QueryCondition> conditions, int pageNo, int pageSize, String orderBy, boolean isAsc)
    {
        Criteria criteria = getSession().createCriteria(persistentClass);
        if (conditions != null)
        {
            for (QueryCondition condition : conditions)
            {
            	if(condition.getOper().equalsIgnoreCase("eq"))
            		criteria.add(Property.forName((String) condition.getKey()).eq(condition.getValue()));
            	else if(condition.getOper().equalsIgnoreCase("gt"))
            		criteria.add(Property.forName((String) condition.getKey()).gt(condition.getValue()));
            	else if(condition.getOper().equalsIgnoreCase("ge"))
            		criteria.add(Property.forName((String) condition.getKey()).ge(condition.getValue()));
            	else if(condition.getOper().equalsIgnoreCase("lt"))
            		criteria.add(Property.forName((String) condition.getKey()).lt(condition.getValue()));
            	else if(condition.getOper().equalsIgnoreCase("le"))
            		criteria.add(Property.forName((String) condition.getKey()).le(condition.getValue()));
            	else if(condition.getOper().equalsIgnoreCase("like"))
            		criteria.add(Property.forName((String) condition.getKey()).like(condition.getValue()));
            	else if(condition.getOper().equalsIgnoreCase("order"))
                        criteria.addOrder((Boolean)condition.getValue() ? Property.forName((String) condition.getKey()).asc() : Property.forName((String) condition.getKey()).desc());
            }
        }
        if (orderBy != null)
            criteria.addOrder(isAsc ? Property.forName(orderBy).asc() : Property.forName(orderBy).desc());

        return pagedQuery(criteria, pageNo, pageSize);
    }

    /**
     * 判断对象某些属性的值在数据库中是否唯一.
     *
     * @param uniquePropertyNames 在POJO里不能重复的属性列表,以逗号分割 如"name,loginid,password"
     */
    public boolean isUnique(Object entity, String uniquePropertyNames)
    {
        Assert.hasText(uniquePropertyNames);
        Criteria criteria = createCriteria().setProjection(Projections.rowCount());
        String[] nameList = uniquePropertyNames.split(",");
        try
        {
            // 循环加入唯一列
            for (String name : nameList)
            {
                criteria.add(Restrictions.eq(name, PropertyUtils.getProperty(entity, name)));
            }

            // 以下代码为了如果是update的情况,排除entity自身.

            String idName = getIdName(this.persistentClass);

            // 取得entity的主键值
            Serializable id = getId(entity);

            // 如果id!=null,说明对象已存在,该操作为update,加入排除自身的判断
            if (id != null)
                criteria.add(Restrictions.not(Restrictions.eq(idName, id)));
        } catch (Exception e)
        {
            ReflectionUtils.handleReflectionException(e);
        }
        return (Integer) criteria.uniqueResult() == 0;
    }

    /**
     * 取得对象的主键值,辅助函数.
     */
    public Serializable getId(Object entity) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException
    {
        Assert.notNull(entity);
        Assert.notNull(this.persistentClass);
        return (Serializable) PropertyUtils.getProperty(entity, getIdName(this.persistentClass));
    }

    /**
     * 取得对象的主键名,辅助函数.
     */
    public String getIdName(Class clazz)
    {
        Assert.notNull(clazz);
        ClassMetadata meta = getSessionFactory().getClassMetadata(clazz);
        Assert.notNull(meta, "Class " + clazz + " not define in hibernate session factory.");
        String idName = meta.getIdentifierPropertyName();
        Assert.hasText(idName, clazz.getSimpleName() + " has no identifier property define.");
        return idName;
    }

    /**
     * 去除hql的select 子句，未考虑union的情况,用于pagedQuery.
     *
     * @see #pagedQuery(String,int,int,Object[])
     */
    private static String removeSelect(String hql)
    {
        Assert.hasText(hql);
        int beginPos = hql.toLowerCase().indexOf("from");
        Assert.isTrue(beginPos != -1, " hql : " + hql + " must has a keyword 'from'");
        return hql.substring(beginPos);
    }

    /**
     * 去除hql的orderby 子句，用于pagedQuery.
     *
     * @see #pagedQuery(String,int,int,Object[])
     */
    private static String removeOrders(String hql)
    {
        Assert.hasText(hql);
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(hql);
        StringBuffer sb = new StringBuffer();
        while (m.find())
        {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return sb.toString();
    }

	@Override
	public Page getPage(int pageNo, int pageSize)
	{
        if (pageNo<=0)
            pageNo = 1;
        if (pageSize<=0)
            pageSize = Page.DEFAULT_PAGE_SIZE;

        return pagedQuery(null, pageNo, pageSize, null, false);
	}

	@Override
	public Page getPage(int pageNo, int pageSize, String orderBy, boolean sort)
	{
        if (pageNo<=0)
            pageNo = 1;
        if (pageSize<=0)
            pageSize = Page.DEFAULT_PAGE_SIZE;

        return pagedQuery(null, pageNo, pageSize, orderBy, sort);
	}

	@Override
	public Page getPageBySearch(int pageNo, int pageSize, String key,
			Object value)
	{
        if (pageNo<=0)
            pageNo = 1;
        if (pageSize<=0)
            pageSize = Page.DEFAULT_PAGE_SIZE;

        List<QueryCondition> conditions = new LinkedList<QueryCondition>();
        QueryCondition item = new QueryCondition(key, "like", value);
        conditions.add(item);
        return pagedQuery(conditions, pageNo, pageSize, null, false);
	}

	@Override
	public void deleteAll(List list) {
		getHibernateTemplate().deleteAll(list);
	}
}