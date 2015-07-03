package org.Rooney.apps.spring.dao


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.Rooney.apps.entities.Options;
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.RowMapper;

class LogDaoImpl extends BaseDao implements LogDao {

	private int saveDB(List<String> list,String tableName,String columName){
		def sql="INSERT INTO ${tableName}(${columName},create_time) VALUES (?,?)"
		def time=System.currentTimeMillis()
		BatchPreparedStatementSetter pss=new BatchPreparedStatementSetter(){
					void setValues(PreparedStatement ps, int i) throws SQLException{
						ps.setString(list.get(i), 0)
						ps.setLong(time, 1)
					}
					int getBatchSize(){
						return list.size()
					}
				}
		return template.batchUpdate(sql,pss)
	}
	
	private List<Options> findDB(String tableName,String columName){
		def sql="SELECT id,${columName} FROM ${tableName}"
		RowMapper<Options> rm={
			ResultSet rs, int rowNum->
			def option=new Options()
			option.id=rs.getLong(1)
			option.name=rs.getLong(2)
			return option
		}
		return template.query(sql, rm)
	}
	@Override
	public int saveUser(List<String> userIds) {
		return this.saveDB(userIds, "hadoop_recommend_user", "user_id")
	}

	@Override
	public List<Options> findUser() {
		return this.findDB("hadoop_recommend_user", "user_id")
	}

	@Override
	public int saveItem(List<String> itemIds) {
		return this.saveDB(itemIds, "hadoop_recommend_item", "item_id")
	}

	@Override
	public List<Options> findItem() {
		return this.findDB("hadoop_recommend_item", "item_id")
	}

	@Override
	public String findName(String id) {
		def sql="SELECT log_name FROM hadoop_log WHERE id=${id}"
		return template.query(sql,String);
	}

}
