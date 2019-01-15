package com.unlimited.oj.dao.hibernate;

import org.hibernate.Query;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import com.unlimited.oj.dao.UserDao;
import com.unlimited.oj.dao.support.Page;
import com.unlimited.oj.dao.support.QueryCondition;
import com.unlimited.oj.model.User;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import javax.persistence.Table;

import java.util.LinkedList;
import java.util.List;

/**
 * This class interacts with Spring's HibernateTemplate to save/delete and
 * retrieve User objects.
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a> Modified by
 *         <a href="mailto:dan@getrolling.com">Dan Kibler</a> Extended to
 *         implement Acegi UserDetailsService interface by David Carter
 *         david@carter.net Modified by <a href="mailto:bwnoll@gmail.com">Bryan
 *         Noll</a> to work with the new BaseDaoHibernate implementation that
 *         uses generics.
 */
public class UserDaoHibernate extends GenericDaoHibernate<User, Long> implements UserDao, UserDetailsService
{

	/**
	 * Constructor that sets the entity to User.class.
	 */
	public UserDaoHibernate()
	{
		super(User.class);
	}

	/**
	 * {@inheritDoc}
	 */
	// @SuppressWarnings("unchecked")
	// public List<User> getUsers() {
	// return getHibernateTemplate().find("from User u order by
	// upper(u.user_name)");
	// }
	/**
	 * {@inheritDoc}
	 */
	// public User saveUser(User user) {
	// log.debug("user's id: " + user.getId());
	// getHibernateTemplate().saveOrUpdate(user);
	// necessary to throw a DataIntegrityViolation and catch it in UserManager
	// getHibernateTemplate().flush();
	// return user;
	// }
	/**
	 * Overridden simply to call the saveUser method. This is happenening
	 * because saveUser flushes the session and saveObject of BaseDaoHibernate
	 * does not.
	 * 
	 * @param user
	 *            the user to save
	 * @return the modified user (with a primary key set if they're new)
	 */
	// @Override
	// public void save(User user) {
	// this.saveUser(user);
	// }
	/**
	 * {@inheritDoc}
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		return (UserDetails) getUserByUsername(username);
	}

	/**
	 * {@inheritDoc}
	 */
	public String getUserPassword(String username)
	{
		SimpleJdbcTemplate jdbcTemplate = new SimpleJdbcTemplate(SessionFactoryUtils.getDataSource(getSessionFactory()));
		Table table = AnnotationUtils.findAnnotation(User.class, Table.class);
		return jdbcTemplate.queryForObject("select password from " + table.name() + " where user_name=?", String.class, username);

	}

	public User getUserByUsername(String username) throws UsernameNotFoundException
	{
		List users = getHibernateTemplate().find("from User where user_name=?", username);
		if (users == null || users.isEmpty())
		{
			throw new UsernameNotFoundException("user '" + username + "' not found...");
		} else
		{
			return (User) users.get(0);
		}
	}

	/**
	 * get all users who take apart in board
	 */
	@SuppressWarnings("unchecked")
	public List<User> getAllHeroBoardUsers()
	{
		String hql = "from User u where u.accountValid=1";

		return (List<User>) find(hql);
	}

    public User getUserByEmail(String email) throws UsernameNotFoundException
    {
        if (email==null)
            return null;
		List users = getHibernateTemplate().find("from User u where u.enabled=1 and lower(u.email)=?", email.toLowerCase());
		if (users == null || users.isEmpty())
		{
			return null;
		} else
		{
			return (User) users.get(0);
		}
    }

	@Override
	public List getAllUserInfo() {
        String hql = "select id,username,firstName,lastName from User";
        List list = getHibernateTemplate().find(hql);
        return list;
	}

	@Override
	public List getUsersByPreName(String preName) {
        String hql = "from User where username LIKE ?";
        List list = getHibernateTemplate().find(hql, preName+"%");
        return list;
	}

	@Override
	public User getUserByToken(String token) {
        if (token==null)
            return null;
		List users = getHibernateTemplate().find("from User u where u.enabled=1 and token=?", token);
		if (users == null || users.isEmpty())
		{
			return null;
		} else
		{
			return (User) users.get(0);
		}
	}

	@Override
	public Page getPageOfUsersByRoleName(int pageNo, int pageSize, String roleName, String orderBy, boolean sort) {
		if(pageSize<=0)
			pageSize = 50;
		if(pageNo<1)
			pageNo = 1;
        String _hql = "select count(u) from User u join u.roleList r where r.name=?";
        Query _query = createQuery(_hql, roleName);
        List _list = _query.list();
        long total = (Long)_list.get(0);
        if(total==0)
        	return null;
        String hql;
        if(orderBy!=null)
        {
	        if(sort)
	        	hql = "select u from User u join u.roleList r where r.name=? order by " + orderBy + " asc";
	        else
	        	hql = "select u from User u join u.roleList r where r.name=? order by " + orderBy + " desc";
        }
        else
        	hql = "select u from User u join u.roleList r where r.name=?";
        Query query = createQuery(hql, roleName);
        List list = query.setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize).list();
        if (list!=null && list.size() > 0)
        {
            return new Page((pageNo-1)*pageSize+1, (int)total, pageSize, list);
        } else
        {
            return null;
        }
	}

	@Override
	public User getUserByAlias(String alias) {
		List users = getHibernateTemplate().find("from User where alias=?", alias);
		if (users == null || users.isEmpty())
		{                                                                                                          
			throw new UsernameNotFoundException("user alias '" + alias + "' not found...");
		} else
		{
			return (User) users.get(0);
		}
	}

}
