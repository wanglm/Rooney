<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/jsp/Tablib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/commons/jsp/Meta.jsp"%>
<link rel="stylesheet" type="text/css" href="commons/css/common.css">
<title>Quartz定时任务</title>
</head>
<body>
	<%@include file="/views/main/Main_Navbar.jsp"%>
	<div class="container-fluid main-row-top">
		<div class="row row-offcanvas row-offcanvas-right">
			<div class="panel panel-default">
				<div class="panel-heading">
					<div class="toolbar">
						<span class="add-on glyphicon glyphicon-time">
							<i class="icon-calendar"></i>
						</span> 
						<input type="text" class="datepicker sd" placeholder="开始时间"> 
						<span class="icon-minus glyphicon glyphicon-arrow-right"></span> 
						<input type="text" class="datepicker ed" placeholder="结束时间"> 
						<span class="add-on glyphicon glyphicon-pencil"></span> 
						<input type="text" id="cron" placeholder="cron表达式">
						<select>
							<option>执行中</option>
							<option>暂停</option>
							<option>出错</option>
						</select>
						<button class="btn btn-primary" type="submit" id="query">查询</button>
						<a href="Jobs.action?method=edit"> <span
							class="glyphicon glyphicon-plus pull-right"></span>
						</a>
					</div>
				</div>
				<div class="panel-body">
					<div class="table-responsive">
						<!-- <table class="table table-striped table-hover table-bordered"> -->
						<table id="jobs-table" class="display" cellspacing="0">
							<thead>
								<tr>
									<th class="table-data-hidden">id</th>
									<th class="table-data-hidden">jobName</th>
									<th class="table-data-hidden">jobGroup</th>
									<th class="table-data-hidden">cronExpression</th>
									<th class="table-data-hidden">executeStrategy</th>
									<th class="table-data-hidden">exceptionStrategy</th>
									<th>NO</th>
									<th>任务名</th>
									<th>任务组名</th>
									<th>任务状态</th>
									<th>执行时间</th>
									<th>执行任务类</th>
									<th>用户类型</th>
									<th>创建时间</th>
									<th>修改时间</th>
									<th>备注</th>
									<th width="5%" class="TAC">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${list}" var="job" varStatus="vs">
									<tr>
										<td class="table-data-hidden">${job.id}</td>
										<td class="table-data-hidden">${job.jobName}</td>
										<td class="table-data-hidden">${job.jobGroup}</td>
										<td class="table-data-hidden">${job.cronExpression}</td>
										<td class="table-data-hidden">${job.executeStrategy}</td>
										<td class="table-data-hidden">${job.exceptionStrategy}</td>
										<td>${vs.index+1}</td>
										<td>${job.jobNameCn}</td>
										<td>${job.jobGroupCn}</td>
										<td>${job.jobStatus}</td>
										<td>${job.jobCronText}</td>
										<td>${job.jobUseClass}</td>
										<td>${job.jobType}</td>
										<td>${job.createTime}</td>
										<td>${job.updateTime}</td>
										<td>${job.jobDsc}</td>
										<td><a href="javascript:void(0)" onclick="fUpdate(${user.id})"><span
												class="glyphicon glyphicon-wrench" ></span></a> 
											<a href="javascript:void(0);" onclick="fDelete(${user.id})"><span
												class="glyphicon glyphicon-trash"></span></a></td>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<%@include file="/commons/jsp/Javascript.jsp"%>
<script type="text/javascript">
$(".datepicker").datepicker({
        dateFormat:'yy-mm-dd'
    });
	var dTable = $('#jobs-table').DataTable({
		//是否ajax初始化
		"ajaxInit" : false,
		//初始化数据总数
		"initTotal" : "${initTotal}",
		"pageLength" : 100,
		"processing" : true,
		"serverSide" : true,
		"searching" : false,
		"ajax" : {
			"url" : "Jobs.action?method=ajax",
			"type" : "POST"
		},
		"columns" : [ {
			"data" : "id",
			"visible" : false
		}, {
			"data" : "jobName",
			"visible" : false
		}, {
			"data" : "jobGroup",
			"visible" : false
		}, {
			"data" : "cronExpression",
			"visible" : false
		}, {
			"data" : "executeStrategy",
			"visible" : false
		}, {
			"data" : "exceptionStrategy",
			"visible" : false
		}, {
			"data" : "num",
			"orderable" : false
		}, {
			"data" : "jobNameCn",
			"orderable" : false
		}, {
			"data" : "jobGroupCn",
			"orderable" : false
		}, {
			"data" : "jobStatus",
			"orderable" : false
		}, {
			"data" : "jobCronText",
			"orderable" : false
		}, {
			"data" : "jobUseClass",
			"orderable" : false
		}, {
			"data" : "jobType",
			"orderable" : false
		}, {
			"data" : "createTime"
		}, {
			"data" : "updateTime"
		}, {
			"data" : "jobDsc",
			"orderable" : false
		},{
			"orderable" : false,
			"render":function ( data, type, row ){
				var vStr="<a href='javascript:void(0)' onclick='fUpdate(${user.id})'>";
				vStr+="<span class='glyphicon glyphicon-wrench'></span></a>";
				vStr+="<a href='javascript:void(0)' onclick='fDelete(${user.id})'>";
				vStr+="<span class='glyphicon glyphicon-trash'></span></a>";
				return vStr;
			}
		}]
	});

$('#query').on('click', function() {
	var url = ""
	dTable.ajax.url(url).load();
});

function fUpdate(vId){
	location.href='Jobs.action?method=edit&id='+vId;
}

function fDelete(vId){
	$.post('Jobs.action?method=delete',{id:vId},function(){});
}

</script>
</html>