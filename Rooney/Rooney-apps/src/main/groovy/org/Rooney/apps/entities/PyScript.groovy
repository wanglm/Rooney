package org.Rooney.apps.entities

import java.io.Serializable;

/**监控python脚本类
 * @author ming
 *
 */
class PyScript extends BaseEntity{
	private String scriptName //脚本名
	private String logName	//日志名
	private String scriptBegin //脚本开始时间
	private String scriptEnd	//脚本结束时间
	private String errorMsg		//错误信息
	private String methodName	//方法名
	private String stepName		//步骤名
	private String methodBegin	//方法开始时间
	private String methodEnd	//方法结束时间
	private String methodTime	//方法执行时间
	private String errorType    //错误类型，默认为null，可以忽略的为"pass"
	public String getScriptName() {
		return scriptName;
	}
	public void setScriptName(String scriptName) {
		this.scriptName = scriptName;
	}
	public String getLogName() {
		return logName;
	}
	public void setLogName(String logName) {
		this.logName = logName;
	}
	public String getScriptBegin() {
		return scriptBegin;
	}
	public void setScriptBegin(String scriptBegin) {
		this.scriptBegin = scriptBegin;
	}
	public String getScriptEnd() {
		return scriptEnd;
	}
	public void setScriptEnd(String scriptEnd) {
		this.scriptEnd = scriptEnd;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	public String getMethodBegin() {
		return methodBegin;
	}
	public void setMethodBegin(String methodBegin) {
		this.methodBegin = methodBegin;
	}
	public String getMethodEnd() {
		return methodEnd;
	}
	public void setMethodEnd(String methodEnd) {
		this.methodEnd = methodEnd;
	}
	public String getMethodTime() {
		return methodTime;
	}
	public void setMethodTime(String methodTime) {
		this.methodTime = methodTime;
	}
	public String getErrorType() {
		return errorType;
	}
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	
}
