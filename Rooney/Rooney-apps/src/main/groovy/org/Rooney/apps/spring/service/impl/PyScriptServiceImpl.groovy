package org.Rooney.apps.spring.service.impl

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map.Entry

import org.Rooney.apps.entities.HighCharts;
import org.Rooney.apps.entities.HighCharts.Datas;
import org.Rooney.apps.entities.HighCharts.Point
import org.Rooney.apps.entities.PieCharts;
import org.Rooney.apps.entities.PyScript;
import org.Rooney.apps.entities.ResultMsg;
import org.Rooney.apps.entities.TableDatas;
import org.Rooney.apps.spring.dao.PyScriptDao;
import org.Rooney.apps.spring.service.PyScriptService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
@Service
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
		return td
	}

	@Override
	public long countForQuery(String scriptName, String logName,String methodName,String stepName, String sd,
			String ed) {
		return dao.countForQuery(scriptName, logName,methodName,stepName, sd, ed)
	}




	@Override
	public HighCharts ajaxCharts(String scriptName, String logName,
			String methodName, String stepName, String sd, String ed,String by) {
		SimpleDateFormat sdf=new SimpleDateFormat('yyyy-MM-dd HH:mm:ss')
		def pointStart=null
		//默认查看一天前至当前时间
		if(!by){
			by='d'
		}
		if(sd){
			pointStart=sdf.parse(sd).time
		}else{
			def isd=mathDefault(by)
			pointStart=isd.toEpochMilli()
			sd=sdf.format(Date.from(isd))
		}
		if(!ed){
			ed=sdf.format(Date.from(Instant.now()))
		}
		def list=dao.findCharts(scriptName, logName, methodName, stepName, sd, ed)
		def hc=new HighCharts()
		Map<String,List<Point>> map=new HashMap<String,List<Point>>()
		list.forEach({ PyScript e->
			def point=new Point()
			def name=e.scriptName+'-'+e.logName
			def mTime=Integer.valueOf(e.methodTime).value
			point.name=e.scriptBegin
			point.y=mTime
			point.x=sdf.parse(e.scriptBegin).getTime()
			if(map.containsKey(name)){
				map.get(name).add(point)
			}else{
				List<Point> mList=new ArrayList<Point>()
				mList.add(point)
				map.put(name, mList)
			}
		})

		List<Datas> series=new ArrayList<Datas>(map.size())
		map.entrySet().forEach({ Entry<String,List<Point>> e->
			def datas=new HighCharts.Datas()
			datas.name=e.key
			datas.data=e.value
			datas.pointStart=pointStart
			series.add(datas)
		})
		hc.series=series
		return hc
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


	/**没有给定时间区间的情况下，按天，月，季，年生成时间区间
	 * @param by
	 * @return
	 */
	private Instant mathDefault(String by){
		Duration duration = null
		switch(by){
			case 'd':
				duration=Duration.ofDays(1)
				break
			case 'm':
				duration=Duration.of(1,ChronoUnit.MONTHS)
				break
			case 's':
				duration=Duration.of(3,ChronoUnit.MONTHS)
				break
			case 'y':
				duration=Duration.of(1,ChronoUnit.YEARS)
				break
		}
		return Instant.now().minus(duration)
	}	

	@Override
	public List<PyScript> listForCheck(String scriptName, String logName,
			String sd, String ed, String page) {
		return dao.findForCheck(scriptName,logName,null,sd,ed,page,"100","script_begin","DESC");
	}

	@Override
	public TableDatas<PyScript> ajaxForCheck(String scriptName, String logName,
			String stepName, String sd, String ed, String draw, String start,
			String length, String orderColumn, String orderDir) {
		def td=new TableDatas()
		td.draw=Integer.valueOf(draw)
		td.recordsFiltered=countForCheck(scriptName, logName, stepName, sd, ed)
		td.recordsTotal=countForCheck(null,null,null,null,null)
		td.data=dao.findForCheck(scriptName, logName,stepName, sd, ed, start,length,getOrderColumn(orderColumn),orderDir)
		return td
	}

	@Override
	public long countForCheck(String scriptName, String logName,
			String stepName, String sd, String ed) {
		return dao.countForCheck(scriptName, logName, stepName, sd, ed);
	}

	@Override
	public ResultMsg pass(List<String> list) {
		return dao.pass(list);
	}

	@Override
	public List<PieCharts> pieCharts(String scriptName, String logName,
			String methodName, String stepName, String sd, String ed, String by) {
		// TODO Auto-generated method stub
		return null;
	}
}
