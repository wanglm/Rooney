<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/jsp/Tablib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/commons/jsp/Meta.jsp"%>
<link rel="stylesheet" type="text/css" href="commons/css/common.css">
<title>数据检查展示</title>
</head>
<body>
	<%@include file="/views/main/Main_Navbar.jsp"%>
	<div class="container-fluid main-row-top">
		<div class="row row-offcanvas row-offcanvas-right">
			<div class="panel panel-default">
				<div class="panel-heading">
					<div class="toolbar">
        				<div class="row">
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
							<button class="btn btn-default" type="button" id="update">忽略</button>
        				</div>
					</div>
				</div>
				<div class="panel-body">
					<div class="table-responsive">
						<table id="script-table" class="display" cellspacing="0">
							<thead>
								<tr>
									<th class="table-data-hidden">id</th>
									<th>NO</th>
									<th>脚本</th>
									<th>日志</th>
									<th>脚本开始</th>
									<th>步骤</th>
									<th>错误信息</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${list}" var="py" varStatus="vs">
									<tr>
										<td class="table-data-hidden">${py.id}</td>
										<td>${vs.index+1}</td>
										<td>${py.scriptName}</td>
										<td>${py.logName}</td>
										<td>${py.scriptBegin}</td>
										<td>${py.stepName}</td>
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
    	"url": "PyScript.action?method=ajaxForCheck",
        "type": "POST"},
    "columns": [
    		{ "data": "id",
    			"visible": false },
    		{ "data": "num","orderable": false },
            { "data": "scriptName","orderable": false },
            { "data": "logName","orderable": false },
            { "data": "scriptBegin" },
            { "data": "stepName","orderable": false },
            { "data": "errorMsg","orderable": false }
        ]
});

$('#query').on('click',function(){
	var vSd=$('.sd').val();
	var vEd=$('.ed').val();
	var vHidden=$('#hScriptName').val()
	var vScriptName=$('#scriptName').val();
	var vStepName=$('#stepName').val();
	if(vHidden!=""){
		vScriptName=vHidden;
	}
	var vLogName=$('#logName').val();
	var url="PyScript.action?method=ajaxForCheck&logName="+vLogName+"&stepName="+vStepName+"&sd="+vSd+"&ed="+vEd
	dTable.ajax.url(url).load();
});

var vIds=[]
$('#script-table tbody').on( 'click', 'tr', function () {
        $(this).toggleClass('selected');
        var vId=$(this).find('#table-data-hidden').text();
        if(this.id!=''){
        	vId=this.id
        }
        var index = $.inArray(vId, vIds);
 
        if ( index === -1 ) {
            vIds.push( vId );
        } else {
            vIds.splice( index, 1 );
        }
  });
$('#update').on('click',function () {
	if(confirm('确定忽略？')){
		$.post("PyScript.action?method=pass",{ids:vIds},function(json){
			if(json.success){
				dTable.rows('.selected').remove().draw( false );
				alert("忽略成功！");
			}else{
				alert(json.msg);
			}
		});
		vIds=[];
	}
  });
</script>
</html>