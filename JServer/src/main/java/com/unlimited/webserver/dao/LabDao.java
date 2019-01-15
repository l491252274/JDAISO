package com.unlimited.webserver.dao;

import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;

import com.unlimited.oj.dao.GenericDao;
import com.unlimited.oj.dao.support.Page;
import com.unlimited.webserver.model.Lab;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.util.List;
public interface LabDao extends GenericDao<Lab, Long>{


    @Transactional
    public Lab getLabByroomid(String roomid) throws FileNotFoundException;
    
    public List showallLab();
    public Lab saveLab(Lab lab);
    public void save(Lab lab);

}