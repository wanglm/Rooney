package org.Rooney.api.time;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

/**
 * 定时任务父类: 业务重写runjob(JobExecutionContext context)方法 ; 任务分有状态和无状态，
 * 有状态就是无法并发执行，上一次任务没有执行完，下一次任务就不会执行， 无状态则可以同时执行
 * 
 * @Email ysuwlm@Gmail.com
 * @author ming
 * 
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public abstract class BaseJob implements Job {

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		QuartzJob job = (QuartzJob) context.getJobDetail()
				.getJobDataMap().get("SchedulerJob");
		// 获取错误处理策略
		int exc = job.getExceptionStrategy();
		try {
			this.runJob(context);
		} catch (Exception e) {
			JobExecutionException je = new JobExecutionException(e);
			switch (exc) {
			case QuartzJobConfig.EXCEPTION_STRATEGY_REFIRE: {
				je.setRefireImmediately(true);// 重新执行任务
			}
			case QuartzJobConfig.EXCEPTION_STRATEGY_STOP_ALL: {
				je.setUnscheduleAllTriggers(true);// 立即停止所有相关这个任务的触发器,任务不会再运行
			}
			default: {
				je.setUnscheduleFiringTrigger(true);// 立即停止针对当前执行任务的触发器，其他触发器不受影响
			}
			}
			throw je;
		}
	}

	/**
	 * 具体业务方法
	 * 
	 * @param job
	 */
	protected abstract void runJob(JobExecutionContext context);
}
