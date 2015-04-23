package org.Rooney.api.time;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @Description quartz核心功能实现
 * @author ming
 * @date 2014-7-28
 * 
 */
public class QuartzBaseServiceImpl implements QuartzBaseService {
	/**
	 * spring注入定时器Scheduler
	 */
	@Autowired
	@Qualifier("scheduler")
	private Scheduler scheduler;

	@Override
	public boolean initJob(QuartzJob job, Class<? extends BaseJob> clz)
			throws Exception {
		boolean isSuccess = false;
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(),
				job.getJobGroup());
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		JobDetail jobDetail = JobBuilder.newJob(clz)
				.withIdentity(job.getJobName(), job.getJobGroup()).build();
		// 将数据存储在JobDataMap是job可以在运行时使用
		jobDetail.getJobDataMap().put("SchedulerJob", job);

		// 表达式调度构建器,可以设置任务触发规则，比如错过触发时间后的规则
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
				.cronSchedule(job.getCronExpression());
		// 设置定时任务错过时间的执行策略
		switch (job.getExecuteStrategy()) {
		case QuartzJobConfig.EXECUTE_STRATEGY_FIRE_AND_PROCEED: {
			scheduleBuilder = scheduleBuilder
					.withMisfireHandlingInstructionFireAndProceed();
		}
		case QuartzJobConfig.EXECUTE_STRATEGY_IGNORE_MISFIRES: {
			scheduleBuilder = scheduleBuilder
					.withMisfireHandlingInstructionIgnoreMisfires();
		}
		default: {
			// 默认QuartzJobConfig.EXECUTE_STRATEGY_DONOTHING的情况下
			scheduleBuilder = scheduleBuilder
					.withMisfireHandlingInstructionDoNothing();
		}
		}

		// 按新的cronExpression表达式构建一个新的trigger
		trigger = TriggerBuilder.newTrigger()
				.withIdentity(job.getJobName(), job.getJobGroup())
				.withSchedule(scheduleBuilder).build();

		scheduler.scheduleJob(jobDetail, trigger);
		isSuccess = true;
		return isSuccess;
	}

	@Override
	public List<QuartzJob> getJobList() throws Exception {
		List<QuartzJob> list = new ArrayList<QuartzJob>();
		GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
		Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
		for (JobKey jobKey : jobKeys) {
			List<? extends Trigger> triggers = scheduler
					.getTriggersOfJob(jobKey);
			for (Trigger trigger : triggers) {
				QuartzJob job = new QuartzJob();
				job.setJobName(jobKey.getName());
				job.setJobGroup(jobKey.getGroup());
				Trigger.TriggerState triggerState = scheduler
						.getTriggerState(trigger.getKey());
				job.setJobStatus(triggerState.name());
				if (trigger instanceof CronTrigger) {
					CronTrigger cronTrigger = (CronTrigger) trigger;
					String cronExpression = cronTrigger.getCronExpression();
					job.setCronExpression(cronExpression);
				}
				list.add(job);
			}
		}
		return list;
	}

	@Override
	public List<QuartzJob> getExecuteJobsList() throws Exception {
		List<QuartzJob> list = null;
		List<JobExecutionContext> executingJobs = scheduler
				.getCurrentlyExecutingJobs();
		list = new ArrayList<QuartzJob>(executingJobs.size());
		for (JobExecutionContext executingJob : executingJobs) {
			QuartzJob job = new QuartzJob();
			JobDetail jobDetail = executingJob.getJobDetail();
			JobKey jobKey = jobDetail.getKey();
			Trigger trigger = executingJob.getTrigger();
			job.setJobName(jobKey.getName());
			job.setJobGroup(jobKey.getGroup());
			Trigger.TriggerState triggerState = scheduler
					.getTriggerState(trigger.getKey());
			job.setJobStatus(triggerState.name());
			if (trigger instanceof CronTrigger) {
				CronTrigger cronTrigger = (CronTrigger) trigger;
				String cronExpression = cronTrigger.getCronExpression();
				job.setCronExpression(cronExpression);
			}
			list.add(job);
		}
		return list;
	}

	@Override
	public boolean pauseJob(QuartzJob job) throws Exception {
		boolean isSuccess = false;
		JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
		scheduler.pauseJob(jobKey);
		isSuccess = true;
		return isSuccess;
	}

	@Override
	public boolean resumeJob(QuartzJob job) throws Exception {
		boolean isSuccess = false;
		JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
		scheduler.resumeJob(jobKey);
		isSuccess = true;
		return isSuccess;
	}

	@Override
	public boolean deleteJob(QuartzJob job) throws Exception {
		boolean isSuccess = false;
		JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
		scheduler.deleteJob(jobKey);
		isSuccess = true;
		return isSuccess;
	}

	@Override
	public boolean testJob(QuartzJob job) throws Exception {
		boolean isSuccess = false;
		JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
		scheduler.triggerJob(jobKey);
		isSuccess = true;
		return isSuccess;
	}

	@Override
	public boolean updateJob(QuartzJob job) throws Exception {
		boolean isSuccess = false;
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(),
				job.getJobGroup());

		// 获取trigger
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		// 表达式调度构建器
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
				.cronSchedule(job.getCronExpression());

		// 设置定时任务错过时间的执行策略
		switch (job.getExecuteStrategy()) {
		case QuartzJobConfig.EXECUTE_STRATEGY_FIRE_AND_PROCEED: {
			scheduleBuilder = scheduleBuilder
					.withMisfireHandlingInstructionFireAndProceed();
		}
		case QuartzJobConfig.EXECUTE_STRATEGY_IGNORE_MISFIRES: {
			scheduleBuilder = scheduleBuilder
					.withMisfireHandlingInstructionIgnoreMisfires();
		}
		default: {
			// 默认QuartzJobConfig.EXECUTE_STRATEGY_DONOTHING的情况下
			scheduleBuilder = scheduleBuilder
					.withMisfireHandlingInstructionDoNothing();
		}
		}

		// 按新的cronExpression表达式重新构建trigger
		trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
				.withSchedule(scheduleBuilder).build();

		// 按新的trigger重新设置job执行
		scheduler.rescheduleJob(triggerKey, trigger);
		isSuccess = true;
		return isSuccess;
	}

}
