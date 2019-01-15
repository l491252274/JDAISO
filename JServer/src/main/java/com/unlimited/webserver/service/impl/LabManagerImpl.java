package com.unlimited.webserver.service.impl;


import org.springframework.security.providers.encoding.PasswordEncoder;

import com.unlimited.oj.Constants;
import com.unlimited.oj.dao.RoleDao;
import com.unlimited.oj.dao.support.Page;
import com.unlimited.oj.dao.support.QueryCondition;
import com.unlimited.oj.model.Role;
import com.unlimited.oj.service.impl.GenericManagerImpl;
import com.unlimited.oj.util.Tool;
import com.unlimited.oj.webapp.action.AdminAction;
import com.unlimited.webserver.dao.LabDao;
import com.unlimited.webserver.model.Lab;
import com.unlimited.webserver.service.LabManager;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DataIntegrityViolationException;

import javax.jws.WebService;
import javax.persistence.EntityExistsException;

import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LabManagerImpl extends GenericManagerImpl<Lab, Long> implements LabManager{

	private LabDao labDao;
	private boolean isNew;
	
	@Required
	public void setLabDao(LabDao labDao)
	{
		super.dao = labDao;
		this.labDao = labDao;
	}

	public Lab getLab(String labId)
	{
		return labDao.get(new Long(labId));
	}


	public Lab getLabByroomid(String roomid) throws FileNotFoundException
	{
		return (Lab) labDao.getLabByroomid(roomid);
	}
	
	public void saveLab(Lab lab) throws Exception
	{

		if (lab.getId() == null)
		{
			// if new Lab, lowercase LabId
			lab.setRoomid(lab.getRoomid().toLowerCase());
		}
		
	
		if (lab.getId() == null)
		{
			// New Lab, always encrypt
			isNew = true;
		} 
		if (isNew)
			{
				log.warn(lab.getRoomid() + "The lab is New");
				lab.setId(lab.getId());
				lab.setRoomid(lab.getRoomid());
				lab.setFloor(lab.getFloor());
			}
		
		try
		{
			labDao.saveLab(lab);
		} catch (DataIntegrityViolationException e)
		{
			e.printStackTrace();
			log.warn(e.getMessage());
			throw new FileNotFoundException("Lab '" + lab.getRoomid() + "' already exists!");
		} catch (EntityExistsException e)
		{ // needed for JPA
			e.printStackTrace();
			log.warn(e.getMessage());
			throw new FileNotFoundException("Lab '" + lab.getRoomid() + "' already exists!");
		}
	}

	
	public void removeLab(String Roomid)
	{
		log.debug("removing Lab: " + Roomid);
		labDao.remove(labDao.get(new Long(Roomid)));
	}
	
	public List showallLab() {
		// TODO Auto-generated method stub
		return labDao.showallLab();
	}

	 

}
