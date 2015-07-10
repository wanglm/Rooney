package org.Rooney.apps.spring.service.impl

import java.util.List;

import org.Rooney.apps.entities.HadoopFiles;
import org.Rooney.apps.entities.Options
import org.Rooney.apps.entities.ResultMsg
import org.Rooney.apps.entities.TableDatas;
import org.Rooney.apps.spring.dao.LogDao
import org.Rooney.apps.spring.service.LogService
import org.Rooney.hadoop.ConfigPath;
import org.Rooney.hadoop.HAppConfig;
import org.Rooney.hadoop.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
@Service
class LogServiceImpl implements LogService {
	@Autowired
	@Qualifier("logDao")
	private LogDao dao

	@Override
	public ResultMsg writeIDFileToDB(String id) {
		def msg=new ResultMsg()
		def fileName=dao.findName(id)
		def localUrl=HAppConfig.INSTANCE.getValue("logPath")+"/"+fileName
		def file=new File(localUrl)
		List<String> userIDs=new ArrayList<String>();
		List<String> itemIDs=new ArrayList<String>();
		file.newReader().with{ e->
			def line=null
			while((line=e.readLine())!=null){
				def lineData=line.split(",")
				userIDs.add(lineData[0])
				itemIDs.add(lineData[1])
			}
		}
		def n=dao.saveUser(userIDs)
		def m=dao.saveItem(itemIDs)
		if(n>0&&m>0){
			msg.success=true
		}else{
			msg.msg="导入数据库生成id存储失败！"
		}
		return msg
	}

	@Override
	public ResultMsg readDBToIDFile() {
		def msg=new ResultMsg()
		List<Options> users=dao.findUser()
		List<Options> items=dao.findItem()
		toIDFile("userID","userIdLocal",users)
		toIDFile("itemID","itemIdLocal",items)
		msg.success=true
		return msg;
	}
	private void toIDFile(String fileName,String key,List<Options> list){
		def today=FileUtils.getToday()
		def url=new StringBuilder()
		url.append(HAppConfig.INSTANCE.getValue("${key}"))
		url.append("/${fileName}-${today}")
		new File(url.toString()).newWriter().with{ e->
			list.forEach({ Options o->
				e.writeLine(o.id+","+o.name)
			})
		}
	}

	@Override
	public ResultMsg putIDFileIntoHDFS() {
		def msg=new ResultMsg()
		def today=FileUtils.getToday()
		FileUtils.putFileToHDFS("userID-${today}", ConfigPath.USER_ID)
		FileUtils.putFileToHDFS("itemID-${today}", ConfigPath.ITEM_ID)
		msg.success=true
		return msg;
	}

	@Override
	public ResultMsg putLogIntoHDFS(String id) {
		def msg=new ResultMsg()
		def fileName=dao.findName(id)
		FileUtils.putFileToHDFS(fileName, ConfigPath.LOG_HDFS_PATH)
		msg.success=true
		return msg;
	}

	@Override
	public List<HadoopFiles> list(String fileName, String type, String sd,
			String ed, String page) {
		return dao.find(fileName, type, sd, ed, page, "100", "create_time","DESC")
	}

	@Override
	public TableDatas<HadoopFiles> ajax(String fileName, String type,
			String sd, String ed, String draw, String start, String length,
			String orderColumn, String orderDir) {
		def td=new TableDatas()
		td.draw=Integer.valueOf(draw)
		td.recordsFiltered=countForQuery(fileName,type,sd,ed)
		td.recordsTotal=countForQuery(null,null,null,null)
		td.data=dao.find(fileName, type,sd, ed, start,length,getOrderColumn(orderColumn),orderDir)
		return td
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
			case 2:column="file_name"
				break
			case 3:column="file_type"
				break
			case 4:column="create_id"
				break
			case 5:column="create_time"
				break
			case 6:column="update_id"
				break
			case 7:column="update_time"
				break
		}
		return column
	}

	@Override
	public long countForQuery(String fileName, String type, String sd, String ed) {
		return dao.countForQuery(fileName, type, sd, ed)
	}
}
