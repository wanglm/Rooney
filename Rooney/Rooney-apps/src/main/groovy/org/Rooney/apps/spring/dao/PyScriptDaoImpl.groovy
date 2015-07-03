package org.Rooney.apps.spring.dao

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.Rooney.apps.entities.Pages;
import org.Rooney.apps.entities.PieCharts;
import org.Rooney.apps.entities.PyScript;
import org.Rooney.apps.entities.Pages.Page
import org.Rooney.apps.entities.ResultMsg;
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository;
@Repository
class PyScriptDaoImpl extends BaseDao implements PyScriptDao{

	@Override
	public List<PyScript> find(String scriptName,String logName,String methodName,String stepName, String sd, String ed,
			String start,String length,String orderColumn,String orderDir) {
		def len=Integer.valueOf(length)
		def begin=Integer.valueOf(start)
		def end=begin+len
		begin=begin>0?begin+1:begin
		def sql=new StringBuilder()
		def pre="""
				SELECT t.* FROM 
				(SELECT row_number() OVER(ORDER BY ${orderColumn} ${orderDir}) AS rowNum,* FROM hadoop_watcher
                 WHERE error_type IS NULL
				"""
		def fins=""") t 
				WHERE rowNum BETWEEN ${begin} AND ${end}
				"""
		sql.append(pre)
		if(scriptName){
			sql.append(" AND script_name='${scriptName}'")
		}
		if(logName){
			sql.append(" AND log_name='${logName}'")
		}
		if(methodName){
			sql.append(" AND method_name='${methodName}'")
		}
		if(stepName){
			sql.append(" AND step_name='${stepName}'")
		}
		if(sd){
			sql.append(" AND script_end>='${sd}'")
		}
		if(ed){
			sql.append(" AND script_begin<='${ed}'")
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

		def sql=new StringBuilder()
		sql.append("SELECT COUNT(1) FROM hadoop_watcher WHERE error_type IS NULL")
		if(scriptName){
			sql.append(" AND script_name='${scriptName}'")
		}
		if(logName){
			sql.append(" AND log_name='${logName}'")
		}
		if(methodName){
			sql.append(" AND method_name='${methodName}'")
		}
		if(stepName){
			sql.append(" AND step_name='${stepName}'")
		}
		if(sd){
			sql.append(" AND script_end>='${sd}'")
		}
		if(ed){
			sql.append(" AND script_begin<='${ed}'")
		}
		return st.queryForObject(sql.toString(), Long);
	}

	@Override
	public List<PyScript> findCharts(String scriptName, String logName,
			String methodName, String stepName, String sd, String ed) {
		def pre="""
				SELECT t.script_name,t.log_name,t.script_begin,MAX(t.method_time) AS times 
				FROM hadoop_watcher t WHERE t.script_begin>'${sd}' AND t.script_begin<'${ed}' AND t.error_type IS NULL
				"""
		def fins=" GROUP BY t.script_name,t.log_name,t.script_begin"
		def sql=new StringBuilder()
		sql.append(pre)
		if(scriptName){
			sql.append(" AND script_name='${scriptName}'")
		}else{
			//只显示cpc监控
			sql.append(" AND script_name='cpc_tj.py'")
		}
		if(logName){
			sql.append(" AND log_name='${logName}'")
		}
		if(methodName){
			sql.append(" AND method_name='${methodName}'")
		}
		if(stepName){
			sql.append(" AND step_name='${stepName}'")
		}
		sql.append(fins)
		RowMapper<PyScript> rm={ResultSet rs,int rowNum->
			def ps=new PyScript()
			ps.scriptName=rs.getString(1)
			ps.logName=rs.getString(2)
			ps.scriptBegin=rs.getString(3)
			def time=rs.getString(4)
			ps.methodTime=time.substring(0, time.indexOf("."))
			return ps
		}
		return st.query(sql.toString(),rm)
	}

	@Override
	public List<PyScript> findForCheck(String scriptName, String logName,
			String stepName, String sd, String ed, String start, String length,
			String orderColumn, String orderDir) {
		def len=Integer.valueOf(length)
		def begin=Integer.valueOf(start)
		def end=begin+len
		begin=begin>0?begin+1:begin
		def sql=new StringBuilder()
		def pre="""
				SELECT t.* FROM 
				(SELECT row_number() OVER(ORDER BY ${orderColumn} ${orderDir}) AS rowNum,* FROM hadoop_watcher 
				WHERE error_msg<>'' AND error_type<>'pass'
                """
		def fins=""") t 
				WHERE rowNum BETWEEN ${begin} AND ${end}
				"""
		sql.append(pre)
		if(scriptName){
			sql.append(" AND script_name='${scriptName}'")
		}
		if(logName){
			sql.append(" AND log_name='${logName}'")
		}
		if(stepName){
			sql.append(" AND step_name='${stepName}'")
		}
		if(sd){
			sql.append(" AND script_begin>='${sd}'")
		}
		if(ed){
			sql.append(" AND script_begin<='${ed}'")
		}
		sql.append(fins)
		RowMapper<PyScript> rm={ResultSet rs,int rowNum->
			def ps=new PyScript()
			ps.num=rowNum+1
			ps.id=rs.getInt(2)
			ps.scriptName=rs.getString(3)
			ps.logName=rs.getString(4)
			ps.scriptBegin=rs.getString(5)
			ps.errorMsg=rs.getString(7)
			ps.stepName=rs.getString(9)
			return ps
		}
		return st.query(sql.toString(), rm);
	}

	@Override
	public long countForCheck(String scriptName, String logName,
			String stepName, String sd, String ed) {
		def sql=new StringBuilder()
		sql.append("SELECT COUNT(1) FROM hadoop_watcher WHERE error_msg<>'' AND error_type<>'pass'")
		if(scriptName){
			sql.append(" AND script_name='${scriptName}'")
		}
		if(logName){
			sql.append(" AND log_name='${logName}'")
		}
		if(stepName){
			sql.append(" AND step_name='${stepName}'")
		}
		if(sd){
			sql.append(" AND script_end>='${sd}'")
		}
		if(ed){
			sql.append(" AND script_begin<='${ed}'")
		}
		return st.queryForObject(sql.toString(), Long);
	}

	@Override
	public ResultMsg pass(List<String> list) {
		def msg=new ResultMsg()
		def sql=new StringBuilder()
		sql.append("UPDATE hadoop_watcher SET error_type='pass' WHERE id IN (")
		def mSize=list.size()
		if(mSize>0){
			for(def i=0;i<mSize;i++){
				sql.append(list.get(i))
				if(i!=mSize-1){
					sql.append(",")
				}
			}
			sql.append(")")
			def num=st.update(sql.toString())
			if(num>0){
				msg.success=true
			}else{
				msg.msg="没有指定id的数据更改！"
			}
		}else{
			msg.msg="id不可以为空！"
		}
		return msg;
	}

	@Override
	public List<PieCharts> findPie(String scriptName, String logName,
			String methodName, String stepName, String sd, String ed) {
		def sql=new StringBuilder()
		sql.append("SELECT ")
		return null;
	}
}
