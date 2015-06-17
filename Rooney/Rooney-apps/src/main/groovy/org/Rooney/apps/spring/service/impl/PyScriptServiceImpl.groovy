package org.Rooney.apps.spring.service.impl

import java.util.List;

import org.Rooney.apps.entities.Pages;
import org.Rooney.apps.entities.PyScript;
import org.Rooney.apps.entities.TableDatas;
import org.Rooney.apps.spring.dao.PyScriptDao;
import org.Rooney.apps.spring.service.PyScriptService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

class PyScriptServiceImpl implements PyScriptService{
	@Qualifier('pyScriptDao')
	@Autowired
	private PyScriptDao dao

	@Override
	public List<PyScript> list(String scriptName,String logName, String sd, String ed,String page) {
		return dao.find(scriptName,logName,null,null,sd,ed,page,"100","script_begin","DESC")
	}

	@Override
	public TableDatas<PyScript> ajax(String scriptName, String logName,String methodName,String stepName, String sd,
			String ed,String draw,String start,String length,String orderColumn,String orderDir) {
		def td=new TableDatas()
		td.draw=Integer.valueOf(draw)
		td.recordsFiltered=countForQuery(scriptName, logName,methodName,stepName, sd, ed)
		td.recordsTotal=countForQuery(null,null,null,null,null,null)
		td.data=dao.find(scriptName, logName,methodName,stepName, sd, ed, start,length,getOrderColumn(orderColumn),orderDir)
		return td;
	}

	@Override
	public long countForQuery(String scriptName, String logName,String methodName,String stepName, String sd,
			String ed) {
		return dao.countForQuery(scriptName, logName,stepName, sd, ed);
	}


	private String getOrderColumn(String orderColumn){
		def n=Integer.valueOf(orderColumn)
		String column=null
		switch(n){
			case 0:column="id"
				break
			case 2:column="script_name"
				break
			case 3:column="log_name"
				break
			case 4:column="script_begin"
				break
			case 5:column="script_end"
				break
			case 6:column="method_name"
				break
			case 7:column="step_name"
				break
			case 8:column="method_begin"
				break
			case 9:column="method_end"
				break
			case 10:column="method_time"
				break
			case 11:column="error_msg"
				break
		}
		return column
	}
}
