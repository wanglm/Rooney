package org.Rooney.apps.spring.dao

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.Rooney.apps.entities.Pages;
import org.Rooney.apps.entities.PyScript;
import org.Rooney.apps.entities.Pages.Page
import org.springframework.jdbc.core.RowMapper

class PyScriptDaoImpl extends BaseDao implements PyScriptDao{

	@Override
	public List<PyScript> find(String scriptName,String logName,String methodName,String stepName, String sd, String ed,
			String start,String length,String orderColumn,String orderDir) {
		def flag=false
		def len=Integer.valueOf(length)
		def begin=Integer.valueOf(start)
		def end=begin+len
		begin=begin>0?begin+1:begin
		def sql=new StringBuilder()
		def pre="""
				SELECT t.* FROM 
				(SELECT row_number() OVER(ORDER BY ${orderColumn} ${orderDir}) AS rowNum,* FROM hadoop_watcher
                """
		def fins=""") t 
				WHERE rowNum BETWEEN ${begin} AND ${end}
				"""
		sql.append(pre)
		if(scriptName){
			flag=true
			sql.append(" WHERE script_name='${scriptName}'")
		}
		if(logName){
			if(flag){
				sql.append(' AND')
			}else{
				sql.append(' WHERE')
				flag=true
			}
			sql.append(" log_name='${logName}'")
		}
		if(methodName){
			if(flag){
				sql.append(' AND')
			}else{
				sql.append(' WHERE')
				flag=true
			}
			sql.append(" method_name='${methodName}'")
		}
		if(stepName){
			if(flag){
				sql.append(' AND')
			}else{
				sql.append(' WHERE')
				flag=true
			}
			sql.append(" step_name='${stepName}'")
		}
		if(sd){
			if(flag){
				sql.append(' AND')
			}else{
				sql.append(' WHERE')
				flag=true
			}
			sql.append(" script_end>='${sd}'")
		}
		if(ed){
			if(flag){
				sql.append(' AND')
			}
			sql.append(" script_begin<='${ed}'")
		}
		sql.append(fins)
		RowMapper<PyScript> rm={ResultSet rs,int rowNum->
			def ps=new PyScript()
			ps.num=rowNum+1
			ps.id=rs.getInt(2)
			ps.scriptName=rs.getString(3)
			ps.logName=rs.getString(4)
			ps.scriptBegin=rs.getString(5)
			ps.scriptEnd=rs.getString(6)
			ps.errorMsg=rs.getString(7)
			ps.methodName=rs.getString(8)
			ps.stepName=rs.getString(9)
			ps.methodBegin=rs.getString(10)
			ps.methodEnd=rs.getString(11)
			ps.methodTime=rs.getString(12)
			return ps
		}
		return st.query(sql.toString(), rm);
	}

	@Override
	public long countForQuery(String scriptName, String logName,String methodName,String stepName, String sd, String ed) {
		def flag=false
		def sql=new StringBuilder()
		sql.append("SELECT COUNT(1) FROM hadoop_watcher")
		if(scriptName){
			flag=true
			sql.append(" WHERE script_name='${scriptName}'")
		}
		if(logName){
			if(flag){
				sql.append(' AND')
			}else{
				sql.append(' WHERE')
				flag=true
			}
			sql.append(" log_name='${logName}'")
		}
		if(methodName){
			if(flag){
				sql.append(' AND')
			}else{
				sql.append(' WHERE')
				flag=true
			}
			sql.append(" method_name='${methodName}'")
		}
		if(stepName){
			if(flag){
				sql.append(' AND')
			}else{
				sql.append(' WHERE')
				flag=true
			}
			sql.append(" step_name='${stepName}'")
		}
		if(sd){
			if(flag){
				sql.append(' AND')
			}else{
				sql.append(' WHERE')
				flag=true
			}
			sql.append(" script_end>='${sd}'")
		}
		if(ed){
			if(flag){
				sql.append(' AND')
			}
			sql.append(" script_begin<='${ed}'")
		}
		return st.queryForObject(sql.toString(), Long);
	}
}
