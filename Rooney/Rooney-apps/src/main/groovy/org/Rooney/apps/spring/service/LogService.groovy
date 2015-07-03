package org.Rooney.apps.spring.service

import org.Rooney.apps.entities.ResultMsg;

/**日志操作service
 * @author ming
 *
 */
interface LogService {
	/**将原始日志写入数据库，利用数据库自动生成long类型的id
	 * @return ResultMsg
	 */
	ResultMsg writeIDFileToDB(String id)
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
}
