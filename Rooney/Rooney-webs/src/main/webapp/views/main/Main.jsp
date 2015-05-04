<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/jsp/Tablib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/commons/jsp/Meta.jsp"%>
<link rel="stylesheet" type="text/css" href="commons/css/common.css">
<title>系统主页</title>
</head>
<body class="body-color">
	<%@include file="Main_Navbar.jsp"%>
	<div class="container">
		<div class="row main-row-top">
			<div class="col-md-8">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">走势图</h3>
					</div>
					<div class="panel-body">
						<div id="hc-line" class="main-row-top-line"></div>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">当前时间</h3>
					</div>
					<div class="panel-body">
						<div id="hc-clock" class="main-row-top-colock"></div>
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">最近执行任务</h3>
					</div>
					<div class="panel-body main-row-top-heigh">
						<ul>
							<li>日志统计</li>
							<li>数据库统计</li>
							<li>用户统计</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="row main-row-top">
			<div class="col-md-6">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">饼状图</h3>
					</div>
					<div class="panel-body">
						<div id="hc-pie" class="main-row-under"></div>
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">HDFS硬件信息</h3>
					</div>
					<div class="panel-body">
						<div class="row">
							<div id="hc-seed"  class="col-md-5 main-row-under"></div>
							<div id="hc-rpm"  class="col-md-5 main-row-under"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
		<%@include file="Main_List_Group.jsp"%>
</body>
<%@include file="/commons/jsp/Javascript.jsp"%>
<script src="commons/js/main.js"></script>
<script>
		$('#hc-line')
				.highcharts(
						{
							chart : {
								type : 'area'
							},
							title : null,
							credits : {
								enabled : false
							},
							exporting :{
								enabled : false
							},
							xAxis : {
								categories : [ '1750', '1800', '1850', '1900',
										'1950', '1999', '2050' ],
								tickmarkPlacement : 'on',
								title : {
									enabled : false
								}
							},
							yAxis : {
								title : {
									text : 'Billions'
								},
								labels : {
									formatter : function() {
										return this.value / 1000;
									}
								}
							},
							tooltip : {
								shared : true,
								valueSuffix : ' millions'
							},
							plotOptions : {
								area : {
									stacking : 'normal',
									lineColor : '#666666',
									lineWidth : 1,
									marker : {
										lineWidth : 1,
										lineColor : '#666666'
									}
								}
							},
							series : [ {
								name : 'Asia',
								data : [ 502, 635, 809, 947, 1402, 3634, 5268 ]
							}, {
								name : 'Africa',
								data : [ 106, 107, 111, 133, 221, 767, 1766 ]
							}, {
								name : 'Europe',
								data : [ 163, 203, 276, 408, 547, 729, 628 ]
							}, {
								name : 'America',
								data : [ 18, 31, 54, 156, 339, 818, 1201 ]
							}, {
								name : 'Oceania',
								data : [ 2, 2, 2, 6, 13, 30, 46 ]
							} ]
						});
fSolid('#hc-seed','#hc-rpm');
fColock('#hc-clock');
fPie('#hc-pie')
</script>
</html>