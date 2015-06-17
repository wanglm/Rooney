<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/jsp/Tablib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/commons/jsp/Meta.jsp"%>
<link rel="stylesheet" type="text/css" href="commons/css/common.css">
<title>Python脚本监控</title>
</head>
<body>
	<div class="container-fluid">
		<div class="row row-offcanvas row-offcanvas-right">
			<div class="panel panel-default">
				<div class="panel-heading">
					<div class="toolbar">
        				<div class="row">
							<div class='col-sm-3'>
								<div class="form-group"> 
									<div class="input-group">
										<span class="input-group-addon">
											<span class="glyphicon glyphicon-file"></span> 
										</span> 
										<input type="text" class="form-control" id="scriptName" 
											placeholder="脚本名称">
										<input type="hidden" id="hScriptName">
										<div class="input-group-addon">
											<div class="dropdown">
											  <div class="dropdown-toggle" id="dropdownMenu1" 
											  data-toggle="dropdown" aria-expanded="true">
											    <span class="caret"></span>
											  </div>
											  <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
											  	<c:forEach items="${selects}" var="se">
											    	<li role="presentation">
											    		<a role="menuitem" tabindex="-1" href="javascript:void(0)" 
											    		onclick="fGetSelect('${se.name}','${se.value}')">${se.name}</a>
											    	</li>
											  	</c:forEach>
											  </ul>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class='col-sm-3'>
								<div class="form-group"> 
									<div class="input-group">
										<span class="input-group-addon">
											<span class="glyphicon glyphicon-list-alt"></span> 
										</span>
										<input type="text" 
										class="form-control" id="logName" placeholder="日志名称">
									</div>
								</div>
							</div>
							<div class='col-sm-3'>
								<div class="form-group"> 
									<div class="input-group">
										<span class="input-group-addon">
											<span class="glyphicon glyphicon-list-alt"></span> 
										</span>
										<input type="text" 
										class="form-control" id="stepName" placeholder="步骤名">
									</div>
								</div>
							</div>
        				</div>
        				<div class="row">
        					<div class='col-sm-3'>
								<div class="form-group"> 
									<div class="input-group">
										<span class="input-group-addon">
											<span class="glyphicon glyphicon-list-alt"></span> 
										</span>
										<input type="text" 
										class="form-control" id="methodName" placeholder="方法名">
									</div>
								</div>
							</div>
        					<div class='col-sm-3'>
								<div class="form-group"> 
									<div id="sdt" class="input-group">
										<span class="input-group-addon">
											<span class="add-on glyphicon glyphicon-time">
											</span>
										</span>
										<input type="text" class="form-control sd" placeholder="开始时间">
									</div>
								</div>
        					</div>
	        				<div class='col-sm-3'>
								<div class="form-group"> 
									<div id="edt" class="input-group">
										<span class="input-group-addon">
											<span class="icon-minus glyphicon glyphicon-arrow-right"></span>
					                    </span> 
										<input type="text" class="form-control ed" placeholder="结束时间">
									</div>
								</div>
							</div>
							<button class="btn btn-primary" type="submit" id="query">查询</button>
							<a href="PyScript.action?method=charts" class="btn btn-default" >图表展示</a>
        				</div>
					</div>
				</div>
				<div class="panel-body">
					<div class="table-responsive">
						<!-- <table class="table table-striped table-hover table-bordered"> -->
						<table id="script-table" class="display" cellspacing="0">
							<thead>
								<tr>
									<th>id</th>
									<th>NO</th>
									<th>脚本</th>
									<th>日志</th>
									<th>脚本开始</th>
									<th>脚本结束</th>
									<th>方法</th>
									<th>步骤</th>
									<th>方法开始</th>
									<th>方法结束</th>
									<th>方法执行时间(秒)</th>
									<th>错误信息</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${list}" var="py" varStatus="vs">
									<tr>
										<td id="table-data-id">${py.id}</td>
										<td>${vs.index+1}</td>
										<td>${py.scriptName}</td>
										<td>${py.logName}</td>
										<td>${py.scriptBegin}</td>
										<td>${py.scriptEnd}</td>
										<td>${py.methodName}</td>
										<td>${py.stepName}</td>
										<td>${py.methodBegin}</td>
										<td>${py.methodEnd}</td>
										<td>${py.methodTime}</td>
										<td>${py.errorMsg}</td>
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
/*$('.datetimepicker').datetimepicker({
    format: 'YYYY-MM-DD hh:mm:ss',
    locale:"zh-cn"
});
$("#sdt").on("dp.change", function (e) {
            $('#edt').data("DateTimePicker").minDate(e.date);
        });
$("#edt").on("dp.change", function (e) {
            $('#sdt').data("DateTimePicker").maxDate(e.date);
        });*/
var dTable=$('#script-table').DataTable({
	//是否ajax初始化
	"ajaxInit":false,
	//初始化数据总数
	"initTotal":"${initTotal}",
	"pageLength": 100,
	"processing": true,
    "serverSide": true,
    "searching": false,
    "ajax": {
    	"url": "PyScript.action?method=ajax",
        "type": "POST"},
    "columns": [
    		{ "data": "id",
    			"visible": false },
    		{ "data": "num","orderable": false },
            { "data": "scriptName","orderable": false },
            { "data": "logName","orderable": false },
            { "data": "scriptBegin" },
            { "data": "scriptEnd" },
            { "data": "methodName","orderable": false },
            { "data": "stepName","orderable": false },
            { "data": "methodBegin" },
            { "data": "methodEnd" },
            { "data": "methodTime" },
            { "data": "errorMsg","orderable": false }
        ]
});

$('#query').on('click',function(){
	var vSd=$('.sd').val();
	var vEd=$('.ed').val();
	var vHidden=$('#hScriptName').val()
	var vScriptName=$('#scriptName').val();
	var vStepName=$('#stepName').val();
	var vMethodName=$('#methodName').val();
	if(vHidden!=""){
		vScriptName=vHidden;
	}
	var vLogName=$('#logName').val();
	var url="PyScript.action?method=ajax&scriptName="+vScriptName
			+"&logName="+vLogName+"&methodName="+vMethodName+"&stepName="+vStepName+"&sd="+vSd+"&ed="+vEd
	dTable.ajax.url(url).load();
});
$('#scriptName').on('change',function(){
	$('#hScriptName').val('');
});

function fGetSelect(name,value){
	$('#scriptName').val(name);
	$('#hScriptName').val(value);
}
</script>
</html>