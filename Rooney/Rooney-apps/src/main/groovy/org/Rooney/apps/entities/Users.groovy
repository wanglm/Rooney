package org.Rooney.apps.entities

class Users extends BaseEntity{
	private String username
	private String userpass
	private String userEmail
	private long userOrgId //用户所属组织的Id
	private int userType //用户类型,0是超级管理员
	private String userDsc

}
