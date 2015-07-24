package org.Rooney.apps.spring.controller

import javax.servlet.http.HttpServletRequest;

import org.Rooney.apps.entities.Jobs;
import org.Rooney.apps.entities.ResultMsg;
import org.Rooney.apps.entities.TableDatas;
import org.Rooney.apps.spring.service.JobService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**定时任务controller
 * @author ming
 *
 */
@Controller
@RequestMapping("/Jobs")
class JobController {
	private Logger log=LogManager.getLogger(JobController)
	@Autowired
	@Qualifier("jobService")
	private JobService service

	@RequestMapping(params="method=show")
	String show(Model model){
		try{
			model.addAttribute("list", service.list())
			model.addAttribute("initTotal", service.countForQuery(null, null))
		}catch(Exception e){
			log.error("方法show出错:---", e)
		}
		return "quartz/Jobs"
	}

	@RequestMapping(params="method=ajax")
	@ResponseBody
	TableDatas<Jobs> ajax(@RequestParam(value="jobStatus",required=false)String jobStatus,
		@RequestParam(value="cronExpression",required=false)String cronExpression,HttpServletRequest request){
		def result=null
		try{
			def draw=request.getParameter("draw")
			def start=request.getParameter("start")
			def length=request.getParameter("length")
			def orderColumn=request.getParameter("order[0][column]")
			def order=request.getParameter("order[0][dir]")
			result= service.ajax(jobStatus, cronExpression,draw,start,length,orderColumn,order)
		}catch(Exception e){
			log.error("方法ajax出错:---", e)
		}
		return result
	}
	@RequestMapping(params="method=save")
	@ResponseBody
	ResultMsg save(Jobs job){
		def msg=null
		try{
			msg=service.save(null)
		}catch(Exception e){
			log.error("方法save出错:---", e)
		}
		return msg
	}
	@RequestMapping(params="method=delete")
	@ResponseBody
	ResultMsg delete(@RequestParam("id")String id){
		def msg=null
		try{
			msg=service.delete(null)
		}catch(Exception e){
			log.error("方法delete出错:---", e)
		}
		return msg
	}
	@RequestMapping(params="method=pause")
	@ResponseBody
	ResultMsg pause(@RequestParam("id")String id){
		def msg=null
		try{
			def job=new Jobs()
			job.id=Long.valueOf(id)
			msg=service.pause(job)
		}catch(Exception e){
			log.error("方法pause出错:---", e)
		}
		return msg
	}
	@RequestMapping(params="method=resume")
	@ResponseBody
	ResultMsg resume(@RequestParam("id")String id){
		def msg=null
		try{
			msg=service.resume(null)
		}catch(Exception e){
			log.error("方法resume出错:---", e)
		}
		return msg
	}
	
	
	@RequestMapping(params="method=edit")
	String edit(@RequestParam(value="id",required=false)String id,Model model){
		if(id){
			def job=new Jobs()
			job.id=Long.valueOf(id).value
			model.addAttribute("job", service.getJob(job))
		}
		return "quartz/JobForm"
	}
}
