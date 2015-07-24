package org.Rooney.apps.spring.service

import org.Rooney.apps.entities.Jobs;
import org.Rooney.apps.entities.ResultMsg;
import org.Rooney.apps.entities.TableDatas;

/**定时任务service
 * @author ming
 *
 */
interface JobService {
	/**展示页面
	 * @return
	 */
	List<Jobs> list()
	
	Jobs getJob(Jobs job)

	TableDatas<Jobs> ajax(String jobStatus,String cronExpression,
	String draw,String start,String length,String orderColumn,String orderDir)
	
	long countForQuery(String jobStatus,String cronExpression)

	/**保存
	 * @param job
	 * @return
	 */
	ResultMsg save(Jobs job)

	/**删除
	 * @param job
	 * @return
	 */
	ResultMsg delete(Jobs job)

	/**暂停
	 * @param job
	 * @return
	 */
	ResultMsg pause(Jobs job)
	/**恢复
	 * @param job
	 * @return
	 */
	ResultMsg resume(Jobs job)
}
