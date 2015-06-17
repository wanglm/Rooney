<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/jsp/Tablib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/commons/jsp/Meta.jsp"%>
<link rel="stylesheet" type="text/css" href="commons/css/common.css">
<title>脚本图表</title>
</head>
<body>
	<%@include file="/views/main/Main_Navbar.jsp"%>
	<div class="container-fluid">
		<div class="row main-row-top">
			<div class="col-md-9">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">走势图</h3>
					</div>
					<div class="panel-body">
						<div id="hc-line" class="main-row-top-line"></div>
					</div>
				</div>
			</div>
			<div class="col-md-3">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">查询选项</h3>
					</div>
					<div class="panel-body">
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
						<div class="form-group"> 
							<div class="input-group">
								<span class="input-group-addon">
									<span class="glyphicon glyphicon-list-alt"></span> 
								</span>
								<input type="text" 
								  class="form-control" id="logName" placeholder="日志名称">
								</div>
						</div>
						<div class="form-group"> 
							<div class="input-group">
								<span class="input-group-addon">
									<span class="glyphicon glyphicon-list-alt"></span> 
								</span>
								<input type="text" 
									class="form-control" id="stepName" placeholder="方法名">
							</div>
						</div>
						<div class="form-group"> 
							<div class="input-group">
								<span class="input-group-addon">
									<span class="glyphicon glyphicon-list-alt"></span> 
								</span>
								<input type="text" 
									class="form-control" id="stepName" placeholder="步骤名">
							</div>
						</div>
						<div class="form-group"> 
							<div id="sdt" class="input-group">
								<span class="input-group-addon">
									<span class="add-on glyphicon glyphicon-time">
									</span>
								</span>
								<input type="text" class="form-control sd" placeholder="开始时间">
							</div>
						</div>
						<div class="form-group"> 
							<div id="edt" class="input-group">
								<span class="input-group-addon">
									<span class="icon-minus glyphicon glyphicon-arrow-right"></span>
					            </span> 
								<input type="text" class="form-control ed" placeholder="结束时间">
							</div>
						</div>
						<button class="btn btn-default" type="button" id="reset">重置</button>
						<button class="btn btn-primary" type="submit" id="query">查询</button>
					</div>
				</div>
		</div>
	</div>
</body>
<%@include file="/commons/jsp/Javascript.jsp"%>
<script type="text/javascript">
var	chartOptions={
				chart : {
					type : 'line'
				},
				title : null,
				credits : {
					enabled : false
				},
				exporting : {
					enabled : false
				},
				xAxis : {
					categories : '',
					tickmarkPlacement : 'on',
					title : {
						enabled : false
					}
				},
				yAxis : {
					title : {
						text : '10秒'
					},
					labels : {
						formatter : function() {
							return this.value / 10;
						}
					}
				},
				tooltip : {
					shared : true,
					valueSuffix : ' 秒'
				},
				plotOptions : {
					line: {
                		dataLabels: {
                    		enabled: true
                		}
            		}
				},
				series : []
			};
function ajaxChart(){
	$.post('',function(json){
		chartOptions.xAxis.categories=json.categories;
		chartOptions.series=json.series;
		$('#hc-line').highcharts(chartOptions);
	});
}
</script>
</html>