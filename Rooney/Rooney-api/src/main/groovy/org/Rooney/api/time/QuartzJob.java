package org.Rooney.api.time;

/**
 * @Description 定时任务数据模型父类 quartz必须属性类
 * @author ming
 * @date 2014-7-28
 * 
 */
public class QuartzJob {

	/** 任务名称 */
	protected String jobName;

	/** 任务分组 */
	protected String jobGroup;

	/** 任务状态 默认0正常 */
	protected String jobStatus;

	/** 任务运行时间表达式 */
	protected String cronExpression;

	/**
	 * 任务执行策略：Misfire处理规则 取值： {@link org.Azgalor.base.time.QuartzJobConfig}
	 * CronTrigger withMisfireHandlingInstructionDoNothing
	 * ——不触发立即执行,等待下次Cron触发频率到达时刻开始按照Cron频率依次执行
	 * 
	 * withMisfireHandlingInstructionIgnoreMisfires
	 * ——以错过的第一个频率时间立刻开始执行,重做错过的所有频率周期后,当下一次触发频率发生时间大于当前时间后，再按照正常的Cron频率依次执行
	 * 
	 * withMisfireHandlingInstructionFireAndProceed
	 * ——以当前时间为触发频率立刻触发一次执行,然后按照Cron频率依次执行
	 */
	protected int executeStrategy;

	/**
	 * 定时任务错误策略 取值： {@link org.Azgalor.base.time.QuartzJobConfig}
	 * {@link org.quartz.JobExecutionException} setRefireImmediately 重新执行任务
	 * setUnscheduleAllTriggers 立即停止所有相关这个任务的触发器,任务不会再运行
	 * setUnscheduleFiringTrigger 立即停止针对当前执行任务的触发器，其他触发器不受影响
	 */
	protected int exceptionStrategy;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public int getExecuteStrategy() {
		return executeStrategy;
	}

	public void setExecuteStrategy(int executeStrategy) {
		this.executeStrategy = executeStrategy;
	}

	public int getExceptionStrategy() {
		return exceptionStrategy;
	}

	public void setExceptionStrategy(int exceptionStrategy) {
		this.exceptionStrategy = exceptionStrategy;
	}

}
