package org.Rooney.apps.spring.service

import org.Rooney.apps.entities.Users;

interface LoginService {
	/**登录方法
	 * @param username
	 * @param userpass
	 * @return 
	 */
	Users login(String username,String userpass)
}
