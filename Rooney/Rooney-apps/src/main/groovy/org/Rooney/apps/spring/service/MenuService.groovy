package org.Rooney.apps.spring.service

import org.Rooney.apps.entities.Menus

interface MenuService {
	Menus getMenu(int userType)
	boolean savePages(long id)
	boolean deletePages(long id)
}
