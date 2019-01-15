package com.unlimited.oj.service.impl;

import java.util.List;

import com.unlimited.oj.dao.*;
import com.unlimited.oj.dao.support.Page;
import com.unlimited.oj.model.*;
import com.unlimited.oj.service.*;

/**
 * Implementation of OjTreeNodeManager interface.
 * 
 * @author <a href="mailto:dan@getrolling.com">Dan Kibler</a>
 */
public class CommonManagerImpl implements CommonManager {
    private MenuDao menuDao;
    
	@Override
	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

	@Override
	public Menu getMenu(String menuId) {
		return menuDao.get(Long.parseLong(menuId));
	}

	@Override
	public void saveMenu(Menu menu) throws Exception {
		menuDao.save(menu);
	}

	@Override
	public void removeMenu(Menu menu) {
		menuDao.remove(menu);
	}

	@Override
	public Page getMenuPage(int pageNo, int pageSize, String orderBy,
			boolean sort) {
		return menuDao.getPageBySearch(pageNo, pageSize, orderBy, sort);
	}

	@Override
	public List getMenusByParentId(Long parentId) {
		return menuDao.getMenusByParentId(parentId);
	}

	@Override
	public List getMenusByType(String type) {
		return menuDao.getMenusByType(type);
	}

	@Override
	public List getMenusAll() {
		return menuDao.getAll();
	}

	@Override
	public void deleteAllMenu(List list) {
		menuDao.deleteAll(list);
	}


}