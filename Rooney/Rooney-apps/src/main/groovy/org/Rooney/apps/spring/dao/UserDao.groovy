package org.Rooney.apps.spring.dao

import org.Rooney.apps.entities.Users;

interface UserDao {
	Users getUser(String username,String userpass)
	Users getUser(long id)
	List<Users> list()
	boolean addUser(Users user)
	boolean updateUser(Users user)
	boolean delete(long id)
}
