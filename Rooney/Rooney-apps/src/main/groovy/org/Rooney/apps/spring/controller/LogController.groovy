package org.Rooney.apps.spring.controller

import org.Rooney.apps.entities.ResultMsg;
import org.Rooney.apps.spring.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/Log")
class LogController {
	@Autowired
	@Qualifier("logService")
	private LogService service
	
	/**将文件写入数据库
	 * @param id
	 * @return
	 */
	@RequestMapping(params="method=convert")
	@ResponseBody
	ResultMsg convert(@RequestParam("id") String id){
		return service.writeIDFileToDB(id)
	}

}
