package org.Rooney.api.time;

/**
 * @Description quartz配置项 策略详细描述： {@link org.Azgalor.base.time.QuartzJob}
 * @author ming
 * @date 2014-7-28
 * 
 */
public class QuartzJobConfig {
	// 执行策略
	/**
	 * withMisfireHandlingInstructionDoNothing
	 * 不触发立即执行,等待下次Cron触发频率到达时刻开始按照Cron频率依次执行
	 */
	public static final int EXECUTE_STRATEGY_DONOTHING = 0;
	/**
	 * withMisfireHandlingInstructionIgnoreMisfires
	 * 以错过的第一个频率时间立刻开始执行,重做错过的所有频率周期后,当下一次触发频率发生时间大于当前时间后，再按照正常的Cron频率依次执行
	 */
	public static final int EXECUTE_STRATEGY_IGNORE_MISFIRES = 1;
	/**
	 * withMisfireHandlingInstructionFireAndProceed
	 * 以当前时间为触发频率立刻触发一次执行,然后按照Cron频率依次执行
	 */
	public static final int EXECUTE_STRATEGY_FIRE_AND_PROCEED = 2;
	// 出错策略
	/**
	 * setRefireImmediately 重新执行任务
	 */
	public static final int EXCEPTION_STRATEGY_REFIRE = 0;
	/**
	 * setUnscheduleAllTriggers 立即停止所有相关这个任务的触发器,任务不会再运行
	 */
	public static final int EXCEPTION_STRATEGY_STOP_ALL = 1;
	/**
	 * setUnscheduleFiringTrigger 立即停止针对当前执行任务的触发器，其他触发器不受影响
	 */
	public static final int EXCEPTION_STRATEGY_STOP_FIRING = 2;

}
