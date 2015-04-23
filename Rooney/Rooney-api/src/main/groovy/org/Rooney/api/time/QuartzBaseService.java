package org.Rooney.api.time;

import java.util.List;

/**
 * @Description quartz核心功能接口
 * @author ming
 * @date 2014-7-28
 * 
 */
public interface QuartzBaseService {

	/**
	 * @Description 初始化定时任务
	 * @param job
	 *            定时任务pojo
	 * @param clz
	 *            定时任务功能类
	 * @return true
	 * @throws Exception
	 *             全部错误
	 * @author ming
	 */
	public boolean initJob(QuartzJob job, Class<? extends BaseJob> clz)
			throws Exception;

	/**
	 * @Description 获取已经添加到quartz的任务
	 * @return List<SchedulerJob>
	 * @throws Exception
	 *             全部错误
	 * @author ming
	 */
	public List<QuartzJob> getJobList() throws Exception;

	/**
	 * @Description 获取运行中的任务
	 * @return List<SchedulerJob>
	 * @throws Exception
	 *             全部错误
	 * @author ming
	 */
	public List<QuartzJob> getExecuteJobsList() throws Exception;

	/**
	 * @Description 暂停运行中的任务
	 * @param job
	 *            定时任务pojo
	 * @return true
	 * @throws Exception
	 *             全部错误
	 * @author ming
	 */
	public boolean pauseJob(QuartzJob job) throws Exception;

	/**
	 * @Description 恢复任务
	 * @param job
	 *            定时任务pojo
	 * @return true
	 * @throws Exception
	 *             全部错误
	 * @author ming
	 */
	public boolean resumeJob(QuartzJob job) throws Exception;

	/**
	 * @Description 删除任务,即使是正在运行的任务也可以删除，不需要暂停
	 * @param job
	 *            定时任务pojo
	 * @return true
	 * @throws Exception
	 *             全部错误
	 * @author ming
	 */
	public boolean deleteJob(QuartzJob job) throws Exception;

	/**
	 * @Description 立即执行一次任务，通常用来做测试 ,其中jobKey是随机的
	 * @param job
	 *            定时任务pojo
	 * @return true
	 * @throws Exception
	 *             全部错误
	 * @author ming
	 */
	public boolean testJob(QuartzJob job) throws Exception;

	/**
	 * @Description 更新已经存在的任务并重新执行
	 * @param job
	 *            定时任务pojo
	 * @return true
	 * @throws Exception
	 *             全部错误
	 * @author ming
	 */
	public boolean updateJob(QuartzJob job) throws Exception;

}
