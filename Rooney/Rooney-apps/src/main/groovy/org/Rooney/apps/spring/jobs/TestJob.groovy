package org.Rooney.apps.spring.jobs

import org.Rooney.api.time.BaseJob;
import org.Rooney.apps.AppConfig;
import org.Rooney.apps.entities.ResultMsg;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;

class TestJob extends BaseJob{
	@Autowired
	@Qualifier("brokerMessagingTemplate")
	private SimpMessagingTemplate st
	@Override
	protected void runJob(JobExecutionContext context) {
		def isSendMsg=true
		def msg=new ResultMsg()
		msg.success=isSendMsg
		msg.msg="有新的消息更新"
		def url=AppConfig.INSTANCE.getValue("websocket.app.broker")+"/pyCheck"
		println "some message..."
		st.convertAndSend(url, msg)
	}

}
