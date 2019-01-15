package com.unlimited.webserver.dao.hibernate;

import com.unlimited.oj.model.User;

import org.hibernate.Query;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

import com.unlimited.oj.dao.UserDao;
import com.unlimited.oj.dao.hibernate.GenericDaoHibernate;
import com.unlimited.oj.dao.support.Page;
import com.unlimited.oj.dao.support.QueryCondition;
import com.unlimited.webserver.dao.LabDao;
import com.unlimited.webserver.model.Lab;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import javax.persistence.Table;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;



public class LabDaoHibernate extends GenericDaoHibernate<Lab, Long> implements LabDao
{

	public LabDaoHibernate()
	{
		super(Lab.class);
	}
	 public Lab saveLab(Lab lab) {
	 log.debug("lab's id: " + lab.getId());
	 getHibernateTemplate().saveOrUpdate(lab);
	 //necessary to throw a DataIntegrityViolation and catch it in LabManager
	 getHibernateTemplate().flush();
	 return lab;
	 }
	/**
	 
	 * 
	 * @param lab
	 *            the lab to save
	 * @return the modified lab (with a primary key set if they're new)
	 */
	 @Override
	 public void save(Lab lab) {
	 this.saveLab(lab);
	 }
    public Lab getLabByroomid(String roomid) throws FileNotFoundException
    {
	List Labs = getHibernateTemplate().find("from Lab where roomid=?", roomid);
	if (Labs == null || Labs.isEmpty())
	{
		throw new FileNotFoundException("lab '" + roomid + "' not found...");
	} else
	{
		return (Lab) Labs.get(0);
	}
    }
    @Override
    public List showallLab(){
    	String hql = "select id,roomid,floor from Lab";
        List list = getHibernateTemplate().find(hql);
        return list;
    }
    

}