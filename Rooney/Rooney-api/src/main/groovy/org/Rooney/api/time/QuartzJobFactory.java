package org.Rooney.api.time;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;

/**
 * @Description 自定义jobfactory，使job可以通过spring注入，像普通组件一样在xml配置
 * @author ming
 * @date 2014-7-28
 * 
 */
public class QuartzJobFactory extends AdaptableJobFactory {
	/**
	 * @see org.springframework.beans.factory.config.AutowireCapableBeanFactory
	 */
	@Autowired
	private AutowireCapableBeanFactory beanFactory;

	@Override
	protected Object createJobInstance(TriggerFiredBundle bundle)
			throws Exception {
		// 调用父类的方法,源码不要改动
		Object jobInstance = super.createJobInstance(bundle);
		// 进行依赖注入
		beanFactory.autowireBean(jobInstance);
		return jobInstance;
	}

}
