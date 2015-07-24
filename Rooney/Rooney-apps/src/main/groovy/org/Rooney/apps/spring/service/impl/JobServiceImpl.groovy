package org.Rooney.apps.spring.service.impl

import java.util.List

import javax.servlet.http.HttpSession;

import org.Rooney.apps.entities.Jobs;
import org.Rooney.apps.entities.ResultMsg;
import org.Rooney.apps.entities.TableDatas;
import org.Rooney.apps.entities.Users;
import org.Rooney.apps.spring.dao.JobDao
import org.Rooney.apps.spring.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
@Service
class JobServiceImpl implements JobService {
	@Autowired
	@Qualifier("jobDao")
	private JobDao dao
	@Autowired
	private HttpSession session

	private int getJobType(){
		return getUser().userType
	}

	private Users getUser(){
		return (Users)session.getAttribute("user")
	}

	@Override
	public List<Jobs> list() {
		def list=dao.find(null, null, "0", "100", "create_time", "DESC",getJobType())
		return list
	}

	@Override
	public TableDatas<Jobs> ajax(String jobStatus, String cronExpression,
			String draw, String start, String length, String orderColumn,
			String orderDir) {
		def td=new TableDatas()
		td.draw=Integer.valueOf(draw)
		td.recordsFiltered=countForQuery(jobStatus, cronExpression)
		td.recordsTotal=countForQuery(null,null)
		td.data=dao.find(jobStatus, cronExpression,start,length,getOrderColumn(orderColumn),orderDir,getJobType())
		return td
	}

	@Override
	public long countForQuery(String jobStatus, String cronExpression) {
		return dao.countForQuery(jobStatus, cronExpression,getJobType());
	}
	/**表格获取排序的字段名
	 * @param orderColumn
	 * @return
	 */
	private String getOrderColumn(String orderColumn){
		def n=Integer.valueOf(orderColumn)
		String column=null
		switch(n){
			case 0:column="id"
				break
			case 13:column="create_time"
				break
			case 14:column="update_time"
				break
		}
		return column
	}
	@Override
	public ResultMsg save(Jobs job) {
		def msg=null
		if(job.id){
			job.updateId=getUser().id
			job.updateTime=System.currentTimeMillis()
			msg=dao.update(job)
		}else{
			job.createId=getUser().id
			job.createTime=System.currentTimeMillis()
			msg=dao.insert(job)
		}
		return msg;
	}

	@Override
	public ResultMsg delete(Jobs job) {
		def msg=dao.delete(job.id)
		return msg;
	}

	@Override
	public ResultMsg pause(Jobs job) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMsg resume(Jobs job) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Jobs getJob(Jobs job) {
		return dao.findOne(job.id);
	}
}
