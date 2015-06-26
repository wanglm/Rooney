package org.Rooney.apps.spring.dao

import java.sql.ResultSet
import java.util.List

import javax.servlet.http.HttpSession

import org.Rooney.apps.entities.Jobs;
import org.Rooney.apps.entities.ResultMsg;
import org.Rooney.apps.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

class JobDaoImpl extends BaseDao implements JobDao {
	@Autowired
	private HttpSession session

	@Override
	public List<Jobs> find(String jobStatus, String cronExpression,
			String start, String length, String orderColumn, String orderDir) {
		def sql=new StringBuilder()
		def jobType=getJobType()
		sql.append("SELECT * FROM jobs WHERE job_type>=${jobType}")
		if(jobStatus){
			sql.append(" AND job_status='${jobStatus}'")
		}
		if(cronExpression){
			sql.append(" AND job_cron='${cronExpression}'")
		}
		sql.append(" ORDER BY ${orderColumn} ${orderDir} limit ${start},${length}")
		RowMapper<Jobs> rm={ ResultSet rs,int rowNum->
			def job=new Jobs()
			job.num=rowNum+1
			job.id=rs.getLong(1)
			job.jobName=rs.getString(2)
			job.jobGroup=rs.getString(3)
			job.cronExpression=rs.getString(4)
			job.jobCronText=rs.getString(5)
			job.executeStrategy=rs.getString(6)
			job.exceptionStrategy=rs.getString(7)
			job.jobStatus=rs.getString(8)
			job.jobUseClass=rs.getString(9)
			job.jobGroupCn=rs.getString(10)
			job.jobNameCn=rs.getString(11)
			job.jobType=rs.getInt(12)
			job.jobDsc=rs.getString(13)
			job.createId=rs.getLong(14)
			job.createTime=rs.getLong(15)
			job.updateId=rs.getLong(16)
			job.updateTime=rs.getLong(17)
		}
		return template.query(sql.toString(), rm);
	}

	@Override
	public long countForQuery(String jobStatus, String cronExpression) {
		def sql=new StringBuilder()
		def jobType=getJobType()
		sql.append("SELECT * FROM hadoop_quartz_job WHERE job_type>=${jobType}")
		if(jobStatus){
			sql.append(" AND job_status='${jobStatus}'")
		}
		if(cronExpression){
			sql.append(" AND job_cron='${cronExpression}'")
		}
		return template.queryForObject(sql.toString(), Long);
	}

	@Override
	public ResultMsg insert(Jobs job) {
		def msg=new ResultMsg()
		def createId=getUser().id
		def createTime=System.currentTimeMillis()
		def sql="""
				INSERT INTO hadoop_quartz_job(job_name,job_group,job_cron,job_cron_text,
				job_strategy_exe,job_strategy_exc,job_status,job_use_class,job_group_cn,
				job_name_cn,job_type,job_dsc,create_id,create_time) VALUES (
				'${job.jobName}','${job.jobGroup}','${job.cronExpression}','${job.jobCronText}',
				'${job.executeStrategy}','${job.exceptionStrategy}','${job.jobStatus}','${job.jobUseClass}',
				'${job.jobGroupCn}','${job.jobNameCn}',${job.jobType},'${job.jobDsc}',${createId},${createTime})
				"""
		def n=template.update(sql)
		if(n>0){
			msg.success=true
		}else{
			msg.msg="添加数据库失败！"
		}
		return msg;
	}

	@Override
	public ResultMsg update(Jobs job) {
		def msg=new ResultMsg()
		def updateId=getUser().id
		def updateTime=System.currentTimeMillis()
		def sql="""
				UPDATE hadoop_quartz_job SET job_name='${job.jobName}',job_group='${job.jobGroup}',job_cron='${job.cronExpression}',
				job_cron_text='${job.jobCronText}',job_strategy_exe='${job.executeStrategy}',job_strategy_exc='${job.exceptionStrategy}',
				job_status='${job.jobStatus}',job_use_class='${job.jobUseClass}',job_group_cn='${job.jobGroupCn}',
				job_name_cn='${job.jobNameCn}',job_type=${job.jobType},job_dsc='${job.jobDsc}',update_id=${updateId},update_time=${updateTime}
				 WHERE id=${job.id}
				"""
		def n=template.update(sql)
		if(n>0){
			msg.success=true
		}else{
			msg.msg="更新数据库失败！"
		}
		return msg;
	}

	@Override
	public ResultMsg delete(long id) {
		def msg=new ResultMsg()
		def sql="DELETE FROM hadoop_quartz_job WHERE id=${id}"
		def n=template.update(sql)
		if(n>0){
			msg.success=true
		}else{
			msg.msg="更新数据库失败！"
		}
		return msg;
	}

	private int getJobType(){
		return getUser().userType
	}

	private Users getUser(){
		return (Users)session.getAttribute("user")
	}
}
