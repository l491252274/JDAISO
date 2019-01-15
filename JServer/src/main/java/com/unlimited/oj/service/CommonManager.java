package com.unlimited.oj.service;

import java.util.List;

import com.unlimited.oj.dao.*;
import com.unlimited.oj.dao.support.Page;
import com.unlimited.oj.model.*;


/**
 * Group Service Interface to handle communication between web and
 * persistence layer.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *  Modified by <a href="mailto:dan@getrolling.com">Dan Kibler </a> 
 */
public interface CommonManager {
    void setMenuDao(MenuDao menuDao);
    Menu getMenu(String menuId);
    void saveMenu(Menu menu) throws Exception;
    void removeMenu(Menu menu);
    void deleteAllMenu(List list);
    Page getMenuPage(int pageNo, int pageSize, String orderBy, boolean sort);
    List getMenusByParentId(Long parentId);
    List getMenusByType(String type);
    List getMenusAll();

}
