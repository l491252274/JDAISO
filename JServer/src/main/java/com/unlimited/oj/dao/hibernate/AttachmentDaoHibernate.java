/***********************************************************************
 * Module:  AttachmentDaoHibernate.java
 * Author:  benQ
 * Purpose: Defines data access implementation class for class Attachment
 ***********************************************************************/
 
package com.unlimited.oj.dao.hibernate;

import java.util.*;
import java.io.Serializable;
import com.unlimited.oj.model.*;
import com.unlimited.oj.dao.GenericDao;
import com.unlimited.oj.dao.DaoException;

import com.unlimited.oj.dao.AttachmentDao;
import com.unlimited.oj.dao.hibernate.GenericDaoHibernate;

/**
 * Class that implements CustomerDao interface
 * 
 */
public class AttachmentDaoHibernate extends GenericDaoHibernate<Attachment,Long> implements AttachmentDao 
{
	public AttachmentDaoHibernate()
	{
		super(Attachment.class);
		// TODO Auto-generated constructor stub
	}
}