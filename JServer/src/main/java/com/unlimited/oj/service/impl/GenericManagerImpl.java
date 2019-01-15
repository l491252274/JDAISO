package com.unlimited.oj.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.unlimited.oj.dao.GenericDao;
import com.unlimited.oj.dao.support.Page;
import com.unlimited.oj.dao.support.QueryCondition;
import com.unlimited.oj.service.GenericManager;
import java.util.LinkedList;

/**
 * This class serves as the Base class for all other Managers - namely to hold
 * common CRUD methods that they might all use. You should only need to extend
 * this class when your require custom CRUD logic.
 *
 * <p>To register this class in your Spring context file, use the following XML.
 * <pre>
 *     &lt;bean id="userManager" class="com.unlimited.oj.service.impl.GenericManagerImpl"&gt;
 *         &lt;constructor-arg&gt;
 *             &lt;bean class="com.unlimited.oj.dao.hibernate.GenericDaoHibernate"&gt;
 *                 &lt;constructor-arg value="com.unlimited.oj.model.User"/&gt;
 *                 &lt;property name="sessionFactory" ref="sessionFactory"/&gt;
 *             &lt;/bean&gt;
 *         &lt;/constructor-arg&gt;
 *     &lt;/bean&gt;
 * </pre>
 *
 * <p>If you're using iBATIS instead of Hibernate, use:
 * <pre>
 *     &lt;bean id="userManager" class="com.unlimited.oj.service.impl.GenericManagerImpl"&gt;
 *         &lt;constructor-arg&gt;
 *             &lt;bean class="com.unlimited.oj.dao.ibatis.GenericDaoiBatis"&gt;
 *                 &lt;constructor-arg value="com.unlimited.oj.model.User"/&gt;
 *                 &lt;property name="dataSource" ref="dataSource"/&gt;
 *                 &lt;property name="sqlMapClient" ref="sqlMapClient"/&gt;
 *             &lt;/bean&gt;
 *         &lt;/constructor-arg&gt;
 *     &lt;/bean&gt;
 * </pre>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 * @param <T> a type variable
 * @param <PK> the primary key for that type
 */
public class GenericManagerImpl<T, PK extends Serializable> implements GenericManager<T, PK> {
    /**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Log log = LogFactory.getLog(getClass());

    /**
     * GenericDao instance, set by constructor of this class
     */
    protected GenericDao<T, PK> dao;

    /**
     * {@inheritDoc}
     */
    public List<T> getAll() {
        return dao.getAll();
    }

    public List<T> getAll(String sign) {
        return dao.getAll(sign);
    }

    /**
     * {@inheritDoc}
     */
    public T get(PK id) {
        return dao.get(id);
    }

    public T get(PK id, String sign) {
        return dao.get(id, sign);
    }

    /**
     * {@inheritDoc}
     */
    public boolean exists(PK id) {
        return dao.exists(id);
    }

    /**
     * {@inheritDoc}
     */
    public void save(T object) {
        dao.save(object);
    }

    /**
     * {@inheritDoc}
     */
    public void remove(PK id) {
        dao.remove(id);
    }
    
    public Page getPage(int pageNo, int pageSize)
    {
        if (pageNo<=0)
            pageNo = 1;
        if (pageSize<=0)
            pageSize = Page.DEFAULT_PAGE_SIZE;

        return dao.pagedQuery(null, pageNo, pageSize, null, false);
    }

    public Page getPage(int pageNo, int pageSize, String sign)
    {
        if (pageNo<=0)
            pageNo = 1;
        if (pageSize<=0)
            pageSize = Page.DEFAULT_PAGE_SIZE;

        List<QueryCondition> conditions = new LinkedList<QueryCondition>();
        QueryCondition item = new QueryCondition("sign", "eq", sign);
        conditions.add(item);
        return dao.pagedQuery(conditions, pageNo, pageSize, null, false);
    }

    public Page getPage(int pageNo, int pageSize, String orderBy, boolean sort)
    {
        if (pageNo<=0)
            pageNo = 1;
        if (pageSize<=0)
            pageSize = Page.DEFAULT_PAGE_SIZE;

        return dao.pagedQuery(null, pageNo, pageSize, orderBy, sort);
    }

    public Page getPage(int pageNo, int pageSize, String orderBy, boolean sort, String sign)
    {
        if (pageNo<=0)
            pageNo = 1;
        if (pageSize<=0)
            pageSize = Page.DEFAULT_PAGE_SIZE;

        List<QueryCondition> conditions = new LinkedList<QueryCondition>();
        QueryCondition item = new QueryCondition("sign", "eq", sign);
        conditions.add(item);
        return dao.pagedQuery(conditions, pageNo, pageSize, orderBy, sort);
    }

    public Page getPageBySearch(int pageNo, int pageSize, String key, Object value)
    {
        if (pageNo<=0)
            pageNo = 1;
        if (pageSize<=0)
            pageSize = Page.DEFAULT_PAGE_SIZE;

        List<QueryCondition> conditions = new LinkedList<QueryCondition>();
        QueryCondition item = new QueryCondition(key, "like", value);
        conditions.add(item);
        return dao.pagedQuery(conditions, pageNo, pageSize, null, false);
    }
    
    public Page getPageBySearch(int pageNo, int pageSize, String key, Object value, String orderBy, boolean sort)
    {
        if (pageNo<=0)
            pageNo = 1;
        if (pageSize<=0)
            pageSize = Page.DEFAULT_PAGE_SIZE;

        List<QueryCondition> conditions = new LinkedList<QueryCondition>();
        QueryCondition item = new QueryCondition(key, "like", value);
        conditions.add(item);
        return dao.pagedQuery(conditions, pageNo, pageSize, orderBy, sort);
    }

    public void update(T object)
    {
        dao.update(object);
    }

    public void clear()
    {
        dao.clear();
    }

	@Override
	public void flush() {
		dao.flush();
	}
}
