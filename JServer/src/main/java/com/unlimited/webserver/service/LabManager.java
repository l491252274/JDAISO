package com.unlimited.webserver.service;

import java.io.FileNotFoundException;
import java.util.List;

import com.unlimited.oj.dao.*;
import com.unlimited.oj.dao.support.Page;
import com.unlimited.oj.model.*;
import com.unlimited.oj.service.GenericManager;
import com.unlimited.webserver.dao.LabDao;
import com.unlimited.webserver.model.Lab;


public interface LabManager extends GenericManager<Lab, Long> {

    void setLabDao(LabDao labDao);
    Lab getLab(String labId);
    Lab getLabByroomid(String roomid) throws FileNotFoundException;
    
    void saveLab(Lab lab) throws Exception;
    void removeLab(String roomid);
    
   
    List showallLab();
    /*
    // Role
    void setRoleDao(RoleDao roleDao);
    List getRoles();
    Role getRole(Long roleId);
    void saveRole(Role role);
    void removeRole(Role role);
    //void removeRoleByName(String rolename);
    Role getRoleByName(String rolename);
    */

}
