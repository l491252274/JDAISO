package com.unlimited.oj.dao.hibernate;

import java.util.List;

import com.unlimited.oj.dao.LookupDao;
import com.unlimited.oj.model.Role;

/**
 * Hibernate implementation of LookupDao.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class LookupDaoHibernate extends GenericDaoHibernate<Role,Long> implements LookupDao {

    public LookupDaoHibernate()
	{
		super(Role.class);
		// TODO Auto-generated constructor stub
	}

	/**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<Role> getRoles() {
        log.debug("Retrieving all role names...");

        return getHibernateTemplate().find("from Role order by name");
    }
}
