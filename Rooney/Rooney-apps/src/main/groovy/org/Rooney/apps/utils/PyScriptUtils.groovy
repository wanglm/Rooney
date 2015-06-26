package org.Rooney.apps.utils

import org.Rooney.apps.entities.Selects

/**python脚本监控工具类
 * @author ming
 *
 */
class PyScriptUtils {
	/**获取脚本的中文名
	 * @param script
	 * @return
	 */
	static List<Selects> getScriptNames(){
		String[][] scripts=[
			['cpc收入统计', 'cpc_tj.py'],
			['广告展示统计', 'show_log_tj.py']
		]
		List<Selects> list=new ArrayList<Selects>(2)
		scripts.each{  s->
			def selects=new Selects()
			selects.name=s[0]
			selects.value=s[1]
			list.add(selects)
		}
		return list
	}
}
