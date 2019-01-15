/***********************************************************************
 * Module:  OjTreeNodeDao.java
 * Author:  benQ
 * Purpose: Defines data access interface for class OjTreeNode
 ***********************************************************************/
 
package com.unlimited.oj.dao;

import java.util.*;
import java.io.Serializable;
import com.unlimited.oj.model.*;
import com.unlimited.oj.dao.GenericDao;
import com.unlimited.oj.dao.DaoException;

/**
 * DAO interface that defines data access methods for class OjTreeNode
 * 
 */
public interface OjTreeNodeDao extends GenericDao<OjTreeNode,Long> 
{  
    List<OjTreeNode> getChildOjTreeNodeByPid(Long Pid);
    List<OjTreeNode> getVisibleChildOjTreeNodeByPid(Long Pid);
    List<OjTreeNode> getChildOjTreeNodeByPid(Long Pid, String sign);
    List<OjTreeNode> getOffspringOjTreeNodeByPid(Long Pid);
    List<OjTreeNode> getVisibleOffspringOjTreeNodeByPid(Long Pid);
    List<OjTreeNode> getOffspringOjTreeNodeByPid(Long Pid, String sign);
    OjTreeNode getOjTreeNodeByConnectPoint(String connectPoint);
    OjTreeNode getOjTreeNodeByConnectPoint(String connectPoint, String sign);
    int getOrderNumberOfNewChild(Long Pid);
}