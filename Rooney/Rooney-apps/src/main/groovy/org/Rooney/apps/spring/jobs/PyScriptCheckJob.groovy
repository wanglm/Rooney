package org.Rooney.apps.spring.jobs

import org.Rooney.api.time.BaseJob
import org.Rooney.apps.AppConfig;
import org.Rooney.apps.entities.ResultMsg
import org.Rooney.apps.spring.service.PyScriptService;
import org.quartz.JobExecutionContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.messaging.simp.SimpMessagingTemplate

class PyScriptCheckJob extends BaseJob {
	@Autowired
	@Qualifier("brokerMessagingTemplate")
	private SimpMessagingTemplate st
	@Autowired
	@Qualifier("pyScriptService")
	private PyScriptService service
	private final String NUM="num" //存储的key
	@Override
	protected void runJob(JobExecutionContext context) {
		def num=service.countForCheck(null,null, null,null, null)
		def lastNum=null
		def isSendMsg=false
		def map=context.jobDetail.jobDataMap
		if(map.containsKey(NUM)){
			lastNum=(long)map.get(NUM)
			if(lastNum!=num){
				isSendMsg=true
			}
		}else{
			isSendMsg=true
		}
		map.put(NUM, num)
		if(isSendMsg){
			def msg=new ResultMsg()
			msg.success=isSendMsg
			msg.msg="有新的消息更新"
			def url=AppConfig.INSTANCE.getValue("websocket.app.broker")+"/pyCheck"
			st.convertAndSend(url, msg)
		}
	}
}
