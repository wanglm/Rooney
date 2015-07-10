package org.Rooney.apps.spring.dao


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.Rooney.apps.entities.HadoopFiles;
import org.Rooney.apps.entities.Options;
import org.Rooney.apps.entities.PyScript;
import org.Rooney.apps.utils.Dates;
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
		RowMapper<Options> rm={ ResultSet rs, int rowNum->
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

	@Override
	public long countForQuery(String fileName, String type, String sd, String ed) {
		def sql=new StringBuilder()
		sql.append("SELECT COUNT(1) FROM hadoop_files WHERE id>0")
		if(fileName){
			sql.append(" AND file_name='${fileName}'")
		}
		if(type){
			sql.append(" AND file_type='${type}'")
		}
		if(sd){
			def sdate=Dates.getDate(sd)
			sql.append(" AND create_time>=${sdate}")
		}
		if(ed){
			def edate=Dates.getDate(ed)
			sql.append(" AND create_time<=${edate}")
		}
		return template.queryForObject(sql.toString(), Long)
	}

	@Override
	public List<PyScript> find(String fileName, String type, String sd,
			String ed, String start, String length, String orderColumn,
			String orderDir) {
		def sql=new StringBuilder()
		sql.append("SELECT * FROM hadoop_files WHERE id>0")
		if(fileName){
			sql.append(" AND file_name='${fileName}'")
		}
		if(type){
			sql.append(" AND file_type='${type}'")
		}
		if(sd){
			def sdate=Dates.getDate(sd)
			sql.append(" AND create_time>=${sdate}")
		}
		if(ed){
			def edate=Dates.getDate(ed)
			sql.append(" AND create_time<=${edate}")
		}
		sql.append(" ORDER BY ${orderColumn} ${orderDir} limit ${start},${length}")
		RowMapper<HadoopFiles> rm={ ResultSet rs,int rowNum->
			def hf=new HadoopFiles()
			hf.num=rowNum+1
			hf.id=rs.getLong(1)
			hf.fileName=rs.getString(2)
			hf.type=rs.getString(3)
			hf.createId=rs.getLong(4)
			hf.createTime=rs.getLong(5)
			hf.updateId=rs.getLong(6)
			hf.updateTime=rs.getLong(7)
		}
		return template.query(sql.toString(), rm)
	}
}
