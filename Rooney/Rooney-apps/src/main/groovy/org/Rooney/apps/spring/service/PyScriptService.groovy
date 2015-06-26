package org.Rooney.apps.spring.service

import org.Rooney.apps.entities.HighCharts;
import org.Rooney.apps.entities.Pages;
import org.Rooney.apps.entities.PieCharts;
import org.Rooney.apps.entities.PyScript;
import org.Rooney.apps.entities.ResultMsg;
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
	 * @param page
	 * @return
	 */
	List<PyScript> list(String scriptName,String logName,String sd,String ed,String page)
	/**查询python的脚本数据收集监控信息
	 * @param scriptName
	 * @param logName
	 * @param sd
	 * @param ed
	 * @param page
	 * @return
	 */
	List<PyScript> listForCheck(String scriptName,String logName,String sd,String ed,String page)

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
	
	/**ajax查询数据收集监控信息
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
	TableDatas<PyScript> ajaxForCheck(String scriptName, String logName,String stepName,String sd, String ed,
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
	
	/**计算数据量
	 * @param scriptName
	 * @param logName
	 * @param stepName
	 * @param sd
	 * @param ed
	 * @return
	 */
	long countForCheck(String scriptName, String logName,String stepName,String sd, String ed)
	
	/**忽略无关紧要的出错信息
	 * @param list id集合
	 * @return
	 */
	ResultMsg pass(List<String> list)
	/**线性图
	 * @param scriptName
	 * @param logName
	 * @param methodName
	 * @param stepName
	 * @param sd
	 * @param ed
	 * @param by 横坐标显示区间，默认当天
	 * @return
	 */
	HighCharts ajaxCharts(String scriptName, String logName,String methodName,String stepName,String sd, String ed,String by)
	
	/**饼状图
	 * @param scriptName
	 * @param logName
	 * @param methodName
	 * @param stepName
	 * @param sd
	 * @param ed
	 * @param by
	 * @return
	 */
	List<PieCharts> pieCharts(String scriptName, String logName,String methodName,String stepName,String sd, String ed,String by)
}
