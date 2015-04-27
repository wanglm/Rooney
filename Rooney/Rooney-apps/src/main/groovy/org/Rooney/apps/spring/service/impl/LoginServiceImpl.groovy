package org.Rooney.apps.spring.service.impl

import org.Rooney.apps.entities.Users;
import org.Rooney.apps.spring.dao.UserDao;
import org.Rooney.apps.spring.service.LoginService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
@Service
class LoginServiceImpl implements LoginService {
	@Autowired
	@Qualifier("userDao")
	private UserDao userDao

	@Override
	public Users login(String username, String userpass) {
		return userDao.getUser(username, userpass);
	}

}
