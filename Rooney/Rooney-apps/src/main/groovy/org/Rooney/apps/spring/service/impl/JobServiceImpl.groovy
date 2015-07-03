package org.Rooney.apps.spring.service.impl

import java.util.List

import org.Rooney.apps.entities.Jobs;
import org.Rooney.apps.entities.ResultMsg;
import org.Rooney.apps.entities.TableDatas;
import org.Rooney.apps.spring.service.JobService;
import org.springframework.stereotype.Service;
@Service
class JobServiceImpl implements JobService {

	@Override
	public List<Jobs> list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TableDatas<Jobs> ajax(String jobStatus, String cronExpression,
			String draw, String start, String length, String orderColumn,
			String orderDir) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long countForQuery(String jobStatus, String cronExpression) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ResultMsg save(Jobs job) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMsg delete(Jobs job) {
		// TODO Auto-generated method stub
		return null;
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

}
