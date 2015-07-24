package org.Rooney.apps.spring.service

import java.util.List

import org.Rooney.apps.entities.HadoopFiles
import org.Rooney.apps.entities.ResultMsg
import org.Rooney.apps.entities.TableDatas

/**日志操作service
 * @author ming
 *
 */
interface LogService {
	/**将原始日志写入数据库，利用数据库自动生成long类型的id
	 * @return ResultMsg
	 */
	ResultMsg saveIDFileToDB(String id)
	/**从数据库读取生成id文件
	 * @return ResultMsg
	 */
	ResultMsg readDBToIDFile()

	/**id文件存入hdfs
	 * @return ResultMsg
	 */
	ResultMsg putIDFileIntoHDFS()

	/**日志文件存入hdfs
	 * @return ResultMsg
	 */
	ResultMsg putLogIntoHDFS(String id)
	
	

	/**查询hadoop处理的文件
	 * @param fileName
	 * @param type 文件类型
	 * @param sd
	 * @param ed
	 * @param page
	 * @return
	 */
	List<HadoopFiles> list(String fileName,String type,String sd,String ed,String page)
	
	/**查询数量
	 * @param fileName
	 * @param type
	 * @param sd
	 * @param ed
	 * @return
	 */
	long countForQuery(String fileName,String type,String sd, String ed)
	
	/**ajax查询hadoop处理的文件
	 * @param fileName
	 * @param type 文件类型
	 * @param sd
	 * @param ed
	 * @param draw
	 * @param start
	 * @param length
	 * @param orderColumn
	 * @param orderDir
	 * @return
	 */
	TableDatas<HadoopFiles> ajax(String fileName,String type,String sd, String ed,
		String draw,String start,String length,String orderColumn,String orderDir)
}
