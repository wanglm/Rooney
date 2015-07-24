package org.Rooney.apps.spring.controller

import org.Rooney.apps.entities.ResultMsg;
import org.Rooney.apps.spring.service.LogService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/Log")
class LogController {
	private Logger log=LogManager.getLogger(LogController)
	@Autowired
	@Qualifier("logService")
	private LogService service

	@RequestMapping(params="method=show")
	String show(){
		return "logs/LogShow"
	}

	/**将文件写入数据库
	 * @param id
	 * @return
	 */
	@RequestMapping(params="method=convert")
	@ResponseBody
	ResultMsg convert(@RequestParam("id") String id){
		def msg=null
		try{
			msg=service.saveIDFileToDB(id)
		}catch(Exception e){
			log.error("转换失败！",e)
		}
		return msg
	}
}
