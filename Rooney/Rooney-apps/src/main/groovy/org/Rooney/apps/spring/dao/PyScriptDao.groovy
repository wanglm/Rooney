package org.Rooney.apps.spring.dao

import org.Rooney.apps.entities.Pages
import org.Rooney.apps.entities.PyScript;

/**监控的数据库基本操作service
 * @author ming
 *
 */
interface PyScriptDao {
	List<PyScript> find(String scriptName, String logName,String methodName,String stepName,String sd, String ed,
		String start,String length,String orderColumn,String orderDir)
	long countForQuery(String scriptName, String logName,String methodName,String stepName,String sd, String ed)
}
