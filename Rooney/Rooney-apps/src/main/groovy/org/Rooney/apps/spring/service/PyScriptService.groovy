package org.Rooney.apps.spring.service

import org.Rooney.apps.entities.Pages;
import org.Rooney.apps.entities.PyScript;
import org.Rooney.apps.entities.TableDatas

/**python脚本监控的相关service
 * @author ming
 *
 */
interface PyScriptService {
	/**查询python的脚本监控信息
	 * @param scriptName
	 * @param logName
	 * @param sd
	 * @param ed
	 * @return
	 */
	List<PyScript> list(String scriptName,String logName,String sd,String ed,String page)

	/**ajax查询方式
	 * @param scriptName
	 * @param logName
	 * @param stepName
	 * @param sd
	 * @param ed
	 * @param draw
	 * @param start
	 * @param length
	 * @param orderColumn
	 * @param orderDir
	 * @return
	 */
	TableDatas<PyScript> ajax(String scriptName, String logName,String methodName,String stepName,String sd, String ed,
	String draw,String start,String length,String orderColumn,String orderDir)


	/**计算数据量
	 * @param scriptName
	 * @param logName
	 * @param methodName
	 * @param stepName
	 * @param sd
	 * @param ed
	 * @return
	 */
	long countForQuery(String scriptName, String logName,String methodName,String stepName,String sd, String ed)
}
