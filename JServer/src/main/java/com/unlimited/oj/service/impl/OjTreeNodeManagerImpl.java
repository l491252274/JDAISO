package com.unlimited.oj.service.impl;

import java.util.List;

import com.unlimited.oj.dao.OjTreeNodeDao;
import com.unlimited.oj.dao.support.Page;
import com.unlimited.oj.model.OjTreeNode;
import com.unlimited.oj.service.OjTreeNodeManager;

/**
 * Implementation of OjTreeNodeManager interface.
 * 
 * @author <a href="mailto:dan@getrolling.com">Dan Kibler</a>
 */
public class OjTreeNodeManagerImpl extends GenericManagerImpl<OjTreeNode, Long> implements OjTreeNodeManager {
    private OjTreeNodeDao dao;

    public void setOjTreeNodeDao(OjTreeNodeDao dao) {
    	super.dao = dao;
        this.dao = dao;
    }

    /**
     * {@inheritDoc}
     */
    public List<OjTreeNode> getOjTreeNodes() {
        return dao.getAll();
    }

    /**
     * {@inheritDoc}
     */
    public void saveOjTreeNode(OjTreeNode ojTreeNode) {
        dao.save(ojTreeNode);
    }

    public List<OjTreeNode> getChildOjTreeNodeByPid(Long Pid)
    {
        return dao.getChildOjTreeNodeByPid(Pid);
    }

    public List<OjTreeNode> getChildOjTreeNodeByPid(Long Pid, String sign)
    {
        return dao.getChildOjTreeNodeByPid(Pid, sign);
    }

    public List<OjTreeNode> getOffspringOjTreeNodeByPid(Long Pid)
    {
        return dao.getOffspringOjTreeNodeByPid(Pid);
    }

    public List<OjTreeNode> getOffspringOjTreeNodeByPid(Long Pid, String sign)
    {
        return dao.getOffspringOjTreeNodeByPid(Pid, sign);
    }

    public OjTreeNode getOjTreeNodeByConnectPoint(String connectPoint)
    {
        return dao.getOjTreeNodeByConnectPoint(connectPoint);
    }

    public OjTreeNode getOjTreeNodeByConnectPoint(String connectPoint, String sign)
    {
        return dao.getOjTreeNodeByConnectPoint(connectPoint, sign);
    }

    public int getOrderNumberOfNewChild(Long Pid)
    {
        return dao.getOrderNumberOfNewChild(Pid);
    }

	@Override
	public List<OjTreeNode> getVisibleChildOjTreeNodeByPid(Long Pid) {
		return dao.getVisibleChildOjTreeNodeByPid(Pid);
	}

	@Override
	public List<OjTreeNode> getVisibleOffspringOjTreeNodeByPid(Long Pid) {
		return dao.getVisibleOffspringOjTreeNodeByPid(Pid);
	}
}