package org.Rooney.apps.spring.controller

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.Rooney.apps.entities.HightCharts;
import org.Rooney.apps.entities.PyScript
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

	/**表格页
	 * @param model
	 * @return pyscript/Script.jsp
	 */
	@RequestMapping(params="method=show")
	String show(Model model){
		try{
			model.addAttribute("selects", PyScriptUtils.getScriptNames())
			model.addAttribute("list", service.list(null,null,null, null,"0"))
			model.addAttribute("initTotal", service.countForQuery(null,null, null,null, null, null))
		}catch(Exception e){
			log.error("PyScriptCotroller show 报错：",e)
		}
		return "pyscript/Script"
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

	/**图表页
	 * @return pyscript/Chart.jsp
	 */
	@RequestMapping(params="method=charts")
	String charts(){
		return "pyscript/Chart"
	}
	@RequestMapping(params="method=ajaxCharts")
	@ResponseBody
	HightCharts ajaxCharts(@RequestParam(value="scriptName",required=false)String scriptName,
			@RequestParam(value="logName",required=false)String logName,
			@RequestParam(value="methodName",required=false)String methodName,
			@RequestParam(value="stepName",required=false)String stepName,
			@RequestParam(value="sd",required=false)String sd,
			@RequestParam(value="ed",required=false)String ed){
		
	}
}
