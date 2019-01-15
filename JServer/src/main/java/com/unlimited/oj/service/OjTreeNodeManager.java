package com.unlimited.oj.service;

import com.unlimited.oj.dao.OjTreeNodeDao;
import com.unlimited.oj.model.OjTreeNode;

import java.util.List;

/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 * @author <a href="mailto:dan@getrolling.com">Dan Kibler </a>
 */
public interface OjTreeNodeManager extends GenericManager<OjTreeNode, Long> {
    /**
     * {@inheritDoc}
     */
    List getOjTreeNodes();

    /**
     * {@inheritDoc}
     */
    void setOjTreeNodeDao(OjTreeNodeDao dao);
    void saveOjTreeNode(OjTreeNode ojTreeNode);

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
