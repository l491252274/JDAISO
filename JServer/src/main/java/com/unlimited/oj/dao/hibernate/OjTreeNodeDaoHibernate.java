/***********************************************************************
 * Module:  OjTreeNodeDaoHibernate.java
 * Author:  benQ
 * Purpose: Defines data access implementation class for class OjTreeNode
 ***********************************************************************/
 
package com.unlimited.oj.dao.hibernate;

import java.util.*;
import java.io.Serializable;
import com.unlimited.oj.model.*;
import com.unlimited.oj.dao.GenericDao;
import com.unlimited.oj.dao.DaoException;

import com.unlimited.oj.dao.OjTreeNodeDao;
import com.unlimited.oj.dao.hibernate.GenericDaoHibernate;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Class that implements CustomerDao interface
 * 
 */
public class OjTreeNodeDaoHibernate extends GenericDaoHibernate<OjTreeNode,Long> implements OjTreeNodeDao 
{
	public OjTreeNodeDaoHibernate()
	{
		super(OjTreeNode.class);
		// TODO Auto-generated constructor stub
	}

    public List<OjTreeNode> getChildOjTreeNodeByPid(Long Pid)
    {
        if(Pid == null)
            return null;
        String hql = "from OjTreeNode otn where otn.pid=? order by otn.orderNum asc";
        List list = find(hql, Pid);
        if(list.size()>0)
                return list;
        else
                return null;
    }
    

	@Override
	public List<OjTreeNode> getVisibleChildOjTreeNodeByPid(Long Pid) {
        if(Pid == null)
            return null;
        String hql = "from OjTreeNode otn where otn.pid=? and otn.visible=1 order by otn.orderNum asc";
        List list = find(hql, Pid);
        if(list.size()>0)
                return list;
        else
                return null;
	}

    public List<OjTreeNode> getChildOjTreeNodeByPid(Long Pid, String sign)
    {
        if(Pid == null)
            return null;
        String hql = "from OjTreeNode otn where otn.pid=? and otn.sign=? order by otn.orderNum asc";
        List list = find(hql, Pid, sign);
        if(list.size()>0)
                return list;
        else
                return null;
    }

    // 取得所有子孙
    public List<OjTreeNode> getOffspringOjTreeNodeByPid(Long Pid)
    {
        if(Pid == null)
            return null;
        OjTreeNode ojTreeNode;
        LinkedList<OjTreeNode> queue = new LinkedList<OjTreeNode>();
        List<OjTreeNode> list = new  LinkedList<OjTreeNode>(); // 用于返回
	String hql = "from OjTreeNode otn where otn.pid=? order by otn.orderNum asc";
        ojTreeNode = get(Pid); // 取得根对象
        if(ojTreeNode!=null)
        {
            list.add(ojTreeNode);
            hql = "from OjTreeNode otn where otn.pid=? order by otn.orderNum asc";
            List tmpList = find(hql, Pid);
            copyList(tmpList, list);
            copyList(tmpList, queue);
            while(!queue.isEmpty() && list.size()<999) // 取得所有子孙，999起限制作用
            {
                ojTreeNode = queue.poll();
                if(ojTreeNode.getType()<100)
                {// 目录节点
                    tmpList = find(hql, ojTreeNode.getId());
                    copyList(tmpList, list);
                    copyList(tmpList, queue);
                }
            }
            if(list.size()>0)
                return list;
        }
		return null;
    }

    // 取得所有子孙
    public List<OjTreeNode> getOffspringOjTreeNodeByPid(Long Pid, String sign)
    {
        if(Pid == null)
            return null;
        OjTreeNode ojTreeNode;
        LinkedList<OjTreeNode> queue = new LinkedList<OjTreeNode>();
        List<OjTreeNode> list = new  LinkedList<OjTreeNode>(); // 用于返回
	String hql = "from OjTreeNode otn where otn.pid=? and otn.sign=? order by otn.orderNum asc";
        ojTreeNode = get(Pid); // 取得根对象
        if(ojTreeNode!=null)
        {
            list.add(ojTreeNode);
            hql = "from OjTreeNode otn where otn.pid=? and otn.sign=? order by otn.orderNum asc";
            List tmpList = find(hql, Pid, sign);
            copyList(tmpList, list);
            copyList(tmpList, queue);
            while(!queue.isEmpty() && list.size()<999) // 取得所有子孙，999起限制作用
            {
                ojTreeNode = queue.poll();
                if(ojTreeNode.getType()<100)
                {// 目录节点
                    tmpList = find(hql, ojTreeNode.getId());
                    copyList(tmpList, list);
                    copyList(tmpList, queue);
                }
            }
            if(list.size()>0)
                return list;
        }
		return null;
    }

    private void copyList(List list1, List list2)
    {
        if(list1==null || list2==null)
            return;
        if(list1.size()>0)
        {
            for(Object obj: list1)
                list2.add(obj);
        }
    }

    public OjTreeNode getOjTreeNodeByConnectPoint(String connectPoint)
    {
        if(connectPoint == null)
            return null;
        String hql = "from OjTreeNode otn where otn.connectPointer=?";
        List list = find(hql, connectPoint);
        if(list.size()>0)
                return (OjTreeNode)list.get(0);
        else
                return null;
    }

    public OjTreeNode getOjTreeNodeByConnectPoint(String connectPoint, String sign)
    {
        if(connectPoint == null)
            return null;
        String hql = "from OjTreeNode otn where otn.connectPointer=? and otn.sign=?";
        List list = find(hql, connectPoint, sign);
        if(list.size()>0)
                return (OjTreeNode)list.get(0);
        else
                return null;
    }

    public int getOrderNumberOfNewChild(Long Pid)
    {
        if(Pid == null)
            return 999;
        String hql = "from OjTreeNode otn where otn.pid=? order by otn.orderNum desc";
        List<OjTreeNode> list = find(hql, Pid);
        if(list.size()>0)
            return list.get(0).getOrderNum() + 10;
        else
            return 10;
    }

	@Override
	public List<OjTreeNode> getVisibleOffspringOjTreeNodeByPid(Long Pid) {
        if(Pid == null)
            return null;
        OjTreeNode ojTreeNode;
        LinkedList<OjTreeNode> queue = new LinkedList<OjTreeNode>();
        List<OjTreeNode> list = new  LinkedList<OjTreeNode>(); // 用于返回
        ojTreeNode = get(Pid); // 取得根对象
        if(ojTreeNode!=null && ojTreeNode.getVisible())
        {
            list.add(ojTreeNode);
            String hql = "from OjTreeNode otn where otn.pid=? and otn.visible=1 order by otn.orderNum asc";
            List tmpList = find(hql, Pid);
            copyList(tmpList, list);
            copyList(tmpList, queue);
            while(!queue.isEmpty() && list.size()<999) // 取得所有子孙，999起限制作用
            {
                ojTreeNode = queue.poll();
                if(ojTreeNode.getType()<100)
                {// 目录节点
                    tmpList = find(hql, ojTreeNode.getId());
                    copyList(tmpList, list);
                    copyList(tmpList, queue);
                }
            }
            if(list.size()>0)
                return list;
        }
		return null;
	}

}