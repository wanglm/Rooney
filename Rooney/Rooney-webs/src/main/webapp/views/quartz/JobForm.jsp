<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/jsp/Tablib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/commons/jsp/Meta.jsp"%>
<link rel="stylesheet" type="text/css" href="commons/css/common.css">
<title>编辑Quartz任务</title>
</head>
<body>
<%@include file="/views/main/Main_Navbar.jsp"%>
<div class="container main-row-top">
		<div class="panel panel-default">
			<div class="panel-heading">编辑任务信息</div>
			<div class="panel-body">
				<form class="form-horizontal" role="form">
					<input type="hidden" name="job.id" value="${job.id}">
					<div class="form-group">
						<label for="jobName" class="col-sm-2 control-label">任务英文名</label>
						<div class="col-xs-4">
							<input type="text" class="form-control " id="jobName" name="job.jobName" value="${job.jobName}"
								placeholder="只允许英文">
						</div>
					</div>
					<div class="form-group">
						<label for="jobNameCn" class="col-sm-2 control-label">任务名称</label>
						<div class="col-xs-4">
							<input type="text" class="form-control " id="jobNameCn" name="job.jobNameCn" value="${job.jobNameCn}"
								placeholder="自定义任务名称">
						</div>
					</div>
					<div class="form-group">
						<label for="jobGroup" class="col-sm-2 control-label">任务组</label>
						<div class="col-xs-4">
							<input type="text" class="form-control " id="jobGroup" name="job.jobGroup" value="${job.jobGroup}"
								placeholder="只允许英文">
						</div>
					</div>
					<div class="form-group">
						<label for="jobGroupCn" class="col-sm-2 control-label">任务组名称</label>
						<div class="col-xs-4">
							<input type="text" class="form-control " id="jobGroupCn" name="job.jobGroupCn" value="${job.jobGroupCn}"
								placeholder="自定义任务组名称">
						</div>
					</div>
					<div class="form-group">
						<label for="jobUseClass" class="col-sm-2 control-label">执行任务类</label>
						<div class="col-xs-4">
							<input type="text" class="form-control " id="jobUseClass" name="job.jobUseClass" value="${job.jobUseClass}"
								placeholder="org.Rooney.apps.spring.jobs包下的类名">
						</div>
					</div>
					<div class="form-group">
						<label for="cronExpression" class="col-sm-2 control-label">cron表达式</label>
						<div class="col-xs-4">
							<input type="text" class="form-control " id="cronExpression" name="job.cronExpression" value="${job.cronExpression}"
								placeholder="Linux的cron表达式">
						</div>
					</div>
					<div class="form-group">
						<label for="jobCronText" class="col-sm-2 control-label">执行时间</label>
						<div class="col-xs-4">
							<input type="text" class="form-control " id="jobCronText" name="job.jobCronText" value="${job.jobCronText}"
								placeholder="表达式说明">
						</div>
					</div>
					<div class="form-group">
						<label for="executeStrategy" class="col-sm-2 control-label">任务执行策略</label>
						<div class="col-xs-4">
							<!-- <input type="email" class="form-control " id="executeStrategy" name="job.executeStrategy"
								placeholder="3种选择"> -->
							<select class="form-control " id="executeStrategy" name="job.executeStrategy">
								<option value="0">忽略错过的执行</option>
								<option value="1">以错过的执行重新计算执行频率</option>
								<option value="2">立即触发错过的执行，原频率不变</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="exceptionStrategy" class="col-sm-2 control-label">任务出错策略</label>
						<div class="col-xs-4">
							<!-- <input type="email" class="form-control " id="exceptionStrategy" name="job.exceptionStrategy"
								placeholder="3种选择"> -->
							<select class="form-control " id="exceptionStrategy" name="job.exceptionStrategy">
								<option value="0">重新执行任务</option>
								<option value="1">停止该出错任务的所有触发可能</option>
								<option value="2">停止该出错任务的本次触发</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="jobDsc" class="col-sm-2 control-label">备注</label>
						<div class="col-xs-4">
							<textarea class="form-control" name="job.jobDsc"
								id="jobDsc" placeholder="任务详细说明" rows="5" cols="8">${job.jobDsc}</textarea>
						</div>
					</div>
					<div class="btn-group">
					    <button type="button" class="btn btn-success">确定</button>
					    <button type="button" class="btn btn-default">取消</button>
				    </div>
				</form>
			</div>
		</div>
	</div>
</body>
<%@include file="/commons/jsp/Javascript.jsp"%>
<script>
$('.btn-success').on('click',function(){
	$.post('Jobs.action?method=save',$('.form-horizontal').serialize(),function(json){
        if(json.success){
        	location.href="Jobs.action?method=show";
        }else{
        	alert('保存失败！');
        }
	});
});
</script>
</html>