package org.Rooney.apps.spring.dao

import java.util.List;

import org.Rooney.apps.entities.Pages
import org.Rooney.apps.entities.PieCharts;
import org.Rooney.apps.entities.PyScript;
import org.Rooney.apps.entities.ResultMsg;

/**监控的数据库基本操作service
 * @author ming
 *
 */
interface PyScriptDao {
	/**脚本展示查询
	 * @param scriptName
	 * @param logName
	 * @param methodName
	 * @param stepName
	 * @param sd
	 * @param ed
	 * @param start
	 * @param length
	 * @param orderColumn
	 * @param orderDir
	 * @return
	 */
	List<PyScript> find(String scriptName, String logName,String methodName,String stepName,String sd, String ed,
	String start,String length,String orderColumn,String orderDir)
	/**脚本展示数据量
	 * @param scriptName
	 * @param logName
	 * @param methodName
	 * @param stepName
	 * @param sd
	 * @param ed
	 * @return
	 */
	long countForQuery(String scriptName, String logName,String methodName,String stepName,String sd, String ed)

	/**数据收集出错查询
	 * @param scriptName
	 * @param logName
	 * @param stepName
	 * @param sd
	 * @param ed
	 * @param start
	 * @param length
	 * @param orderColumn
	 * @param orderDir
	 * @return
	 */
	List<PyScript> findForCheck(String scriptName, String logName,String stepName,String sd, String ed,
	String start,String length,String orderColumn,String orderDir)
	/**数据收集出错统计
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

	/**线性图展示
	 * @param scriptName
	 * @param logName
	 * @param methodName
	 * @param stepName
	 * @param sd
	 * @param ed
	 * @return
	 */
	List<PyScript> findCharts(String scriptName, String logName,String methodName,String stepName,String sd, String ed)
	
	List<PieCharts> findPie(String scriptName, String logName,String methodName,String stepName,String sd, String ed)
}
