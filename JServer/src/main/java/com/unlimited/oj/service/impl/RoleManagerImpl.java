package com.unlimited.oj.service.impl;

import java.util.List;

import com.unlimited.oj.dao.RoleDao;
import com.unlimited.oj.dao.support.Page;
import com.unlimited.oj.model.Role;
import com.unlimited.oj.service.RoleManager;

/**
 * Implementation of RoleManager interface.
 * 
 * @author <a href="mailto:dan@getrolling.com">Dan Kibler</a>
 */
public class RoleManagerImpl extends GenericManagerImpl<Role, Long> implements RoleManager {
    private RoleDao dao;

    public void setRoleDao(RoleDao dao) {
    	super.dao = dao;
        this.dao = dao;
    }

    /**
     * {@inheritDoc}
     */
    public List<Role> getRoles(Role role) {
        return dao.getAll();
    }

    /**
     * {@inheritDoc}
     */
    public Role getRole(String rolename) {
        return dao.getRoleByName(rolename);
    }

    /**
     * {@inheritDoc}
     */
    public void saveRole(Role role) {
        dao.save(role);
    }

    /**
     * {@inheritDoc}
     */
    public void removeRole(String rolename) {
        dao.removeRole(rolename);
    }
}