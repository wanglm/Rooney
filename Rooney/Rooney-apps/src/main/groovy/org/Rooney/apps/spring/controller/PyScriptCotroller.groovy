package org.Rooney.apps.spring.controller

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.Rooney.apps.entities.HighCharts;
import org.Rooney.apps.entities.PyScript
import org.Rooney.apps.entities.ResultMsg;
import org.Rooney.apps.entities.TableDatas;
import org.Rooney.apps.spring.service.PyScriptService;
import org.Rooney.apps.utils.PyScriptUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Controller
@RequestMapping("/PyScript")
class PyScriptCotroller {
	private Logger log=LogManager.getLogger(PyScriptCotroller)
	@Qualifier('pyScriptService')
	@Autowired
	private PyScriptService service

	/**跳转表格页
	 * @param page 'script':pyscript/Script,'check':pyscript/Check
	 * @param model
	 * @return
	 */
	@RequestMapping(params="method=show")
	String show(@RequestParam("page")String page,Model model){
		def pages="error/404"
		try{
			def list=null
			def initTotal=null
			if(page=='check'){
				//跳转到数据收集页面
				pages="pyscript/Check"
				list=service.listForCheck(null, null, null, null, ,"0")
				initTotal=service.countForCheck(null,null, null,null, null)
			}else if(page=='script'){
				//跳转到脚本展示页面
				pages="pyscript/Script"
				list=service.list(null,null,null, null,"0")
				initTotal=service.countForQuery(null,null, null,null, null, null)
				model.addAttribute("selects", PyScriptUtils.getScriptNames())
			}
			model.addAttribute("list", list)
			model.addAttribute("initTotal", initTotal)
		}catch(Exception e){
			log.error("PyScriptCotroller show 报错：",e)
		}
		return pages
	}

	/**表格ajax查询
	 * @param scriptName
	 * @param logName
	 * @param stepName
	 * @param sd
	 * @param ed
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params="method=ajax")
	TableDatas<PyScript> ajax(@RequestParam(value="scriptName",required=false)String scriptName,
			@RequestParam(value="logName",required=false)String logName,
			@RequestParam(value="methodName",required=false)String methodName,
			@RequestParam(value="stepName",required=false)String stepName,
			@RequestParam(value="sd",required=false)String sd,
			@RequestParam(value="ed",required=false)String ed,HttpServletRequest request){
		TableDatas<PyScript> td=null
		try{
			def draw=request.getParameter("draw")
			def start=request.getParameter("start")
			def length=request.getParameter("length")
			def orderColumn=request.getParameter("order[0][column]")
			def order=request.getParameter("order[0][dir]")
			td=service.ajax(scriptName,logName,methodName,stepName,sd, ed,draw,start,length,orderColumn,order)
		}catch(Exception e){
			log.error("PyScriptCotroller ajax 报错：",e)
		}
		return td
	}


	@ResponseBody
	@RequestMapping(params="method=ajaxForCheck")
	TableDatas<PyScript> ajaxForCheck(@RequestParam(value="scriptName",required=false)String scriptName,
			@RequestParam(value="logName",required=false)String logName,
			@RequestParam(value="stepName",required=false)String stepName,
			@RequestParam(value="sd",required=false)String sd,
			@RequestParam(value="ed",required=false)String ed,HttpServletRequest request){
		TableDatas<PyScript> td=null
		try{
			def draw=request.getParameter("draw")
			def start=request.getParameter("start")
			def length=request.getParameter("length")
			def orderColumn=request.getParameter("order[0][column]")
			def order=request.getParameter("order[0][dir]")
			td=service.ajaxForCheck(scriptName, logName, stepName, sd, ed, draw, start, length, orderColumn, order)
		}catch(Exception e){
			log.error("PyScriptCotroller ajaxForCheck 报错：",e)
		}
		return td
	}

	/**忽略无影响的出错信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params="method=pass")
	ResultMsg pass(@RequestParam('ids[]')List<String> list){
		def result=null
		try{
			result=service.pass(list)
		}catch(Exception e){
			log.error("PyScriptCotroller pass 报错：",e)
		}
		return result
	}

	/**图表页
	 * @return pyscript/Chart.jsp
	 */
	@RequestMapping(params="method=charts")
	String charts(Model model){
		model.addAttribute("selects", PyScriptUtils.getScriptNames())
		return "pyscript/Chart"
	}


	@RequestMapping(params="method=lineCharts")
	@ResponseBody
	HighCharts lineCharts(@RequestParam(value="scriptName",required=false)String scriptName,
			@RequestParam(value="logName",required=false)String logName,
			@RequestParam(value="methodName",required=false)String methodName,
			@RequestParam(value="stepName",required=false)String stepName,
			@RequestParam(value="sd",required=false)String sd,
			@RequestParam(value="ed",required=false)String ed,
			@RequestParam(value="by",required=false)String by){
		def hc=null
		try{
			hc=service.ajaxCharts(scriptName, logName, methodName, stepName, sd, ed, by)
		}catch(Exception e){
			log.error("PyScriptCotroller ajaxCharts 报错：",e)
		}
		return hc
	}

	@RequestMapping(params="method=pieCharts")
	@ResponseBody
	HighCharts pieCharts(@RequestParam(value="scriptName",required=false)String scriptName,
			@RequestParam(value="logName",required=false)String logName,
			@RequestParam(value="methodName",required=false)String methodName,
			@RequestParam(value="stepName",required=false)String stepName,
			@RequestParam(value="sd",required=false)String sd,
			@RequestParam(value="ed",required=false)String ed,
			@RequestParam(value="by",required=false)String by){
		def hc=null
		try{
			hc=service.ajaxCharts(scriptName, logName, methodName, stepName, sd, ed, by)
		}catch(Exception e){
			log.error("PyScriptCotroller ajaxCharts 报错：",e)
		}
		return hc
	}
}
