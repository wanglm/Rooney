package org.Rooney.apps.spring.dao

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List

import org.Rooney.apps.entities.Users;
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository;
@Repository
class UserDaoImpl extends BaseDao implements UserDao {
	
	private class UserMapper implements RowMapper{

		@Override
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			def user=new Users()
			user.id=rs.getLong(1)
			user.username=rs.getString(2)
			user.userpass=rs.getString(3)
			user.userEmail=rs.getString(4)
			user.userOrgId=rs.getLong(5)
			user.userType=rs.getInt(6)
			user.userDsc=rs.getString(7)
			user.createId=rs.getLong(8)
			user.createTime=rs.getLong(9)
			user.updateId=rs.getLong(10)
			user.updateTime=rs.getLong(11)
			return user;
		}
	}

	@Override
	public Users getUser(String username, String userpass) {
		def sql="select * from hadoop_user where user_name='${username}' and user_pass='${userpass}'"
		return template.queryForObject(sql, new UserMapper());
	}

	@Override
	public Users getUser(long id) {
		def sql="select * from hadoop_user where id=${id}"
		return template.queryForObject(sql, new UserMapper());
	}

	@Override
	public List<Users> list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addUser(Users user) {
		def sql="""insert into hadoop_user(user_name,user_pass,user_email,user_org_id,user_type,user_dsc,create_id,create_time)
				 values ('${user.username}','${user.userpass}','${user.userEmail}',${user.userOrgId},${user.userType}),
						'${user.userDsc}',${user.createId},${user.createTime})"""
		return exeSql(sql);
	}

	@Override
	public boolean updateUser(Users user) {
		def sql="""update hadoop_user set user_name='${user.username}',user_pass='${user.userpass}',user_email='${user.userEmail}',user_org_id=${user.userOrgId},
					user_type=${user.userType},user_dsc='${user.userDsc}',update_id=${user.updateTime},update_time=${user.updateTime} where id=${user.id}"""
		return exeSql(sql);
	}

	@Override
	public boolean delete(long id) {
		def sql="delete * from hadoop_user where id=${id}"
		return exeSql(sql);
	}
}
