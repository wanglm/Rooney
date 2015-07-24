package org.Rooney.apps.spring.dao

import java.util.List;

import org.Rooney.apps.entities.Jobs;
import org.Rooney.apps.entities.ResultMsg;

interface JobDao {
	List<Jobs> find(String jobStatus,String cronExpression,
	String start,String length,String orderColumn,String orderDir,int jobType)
	
	Jobs findOne(long id)

	long countForQuery(String jobStatus,String cronExpression,int jobType)

	ResultMsg insert(Jobs job)
	ResultMsg update(Jobs job)
	ResultMsg delete(long id)
}
