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
	<div class="container-fluid main-row-top">
		<div class="row">
			<div class="col-md-9">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">时间走势图</h3>
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
							<div class="btn-group" role="group" aria-label="...">
	  							<button type="button" id="pd" class="btn btn-default">天</button>
	  							<button type="button" id="pw" class="btn btn-default">周</button>
	  							<button type="button" id="pm" class="btn btn-default">月</button>
	  							<button type="button" id="ps" class="btn btn-default">季</button>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<div class="input-group-addon"> 
									<div class="dropdown">
										<div class="glyphicon glyphicon-file dropdown-toggle" id="dropdownMenu1"
											data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
										</div>
										<ul class="dropdown-menu" role="menu"
											aria-labelledby="dropdownMenu1">
											<c:forEach items="${selects}" var="se">
												<li role="presentation"><a role="menuitem"
													tabindex="-1" href="javascript:void(0)"
													onclick="fGetSelect('${se.name}','${se.value}')">${se.name}</a>
												</li>
											</c:forEach>
										</ul>
									</div>
								</div> 
								<input type="text" class="form-control" id="scriptName"
									placeholder="脚本名称"> <input type="hidden"
									id="hScriptName">
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon"> <span
									class="glyphicon glyphicon-list-alt"></span>
								</span> <input type="text" class="form-control" id="logName"
									placeholder="日志名称">
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon"> <span
									class="glyphicon glyphicon-list-alt"></span>
								</span> <input type="text" class="form-control" id="methodName"
									placeholder="方法名">
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon"> <span
									class="glyphicon glyphicon-list-alt"></span>
								</span> <input type="text" class="form-control" id="stepName"
									placeholder="步骤名">
							</div>
						</div>
						<div class="form-group">
							<div id="sdt" class="input-group">
								<span class="input-group-addon"> <span
									class="add-on glyphicon glyphicon-time"> </span>
								</span> <input type="text" class="form-control sd" placeholder="开始时间,yyyy-MM-dd hh:mm:ss">
							</div>
						</div>
						<div class="form-group">
							<div id="edt" class="input-group">
								<span class="input-group-addon"> <span
									class="icon-minus glyphicon glyphicon-time"></span>
								</span> <input type="text" class="form-control ed" placeholder="结束时间,yyyy-MM-dd hh:mm:ss">
							</div>
						</div>
						<button class="btn btn-default" type="button" id="reset">重置</button>
						<button class="btn btn-primary" type="submit" id="query">查询</button>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">各步骤时间比例饼状图</h3>
					</div>
					<div class="panel-body">
						<div id="hc-pie" class="chart-pie"></div>
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">数据量变化图</h3>
					</div>
					<div class="panel-body">
						<div id="hc-area" class="chart-area"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<%@include file="/commons/jsp/Javascript.jsp"%>
<script type="text/javascript">
	var chartOptions = {
		chart : {
			type : ''
		},
		title : null,
		credits : {
			enabled : false
		},
		exporting : {
			enabled : false
		},
		xAxis : {
			type: 'datetime',
			tickmarkPlacement : 'on',
			title : {
				enabled : false
			},
			dateTimeLabelFormats : {
				millisecond : '%H:%M:%S.%L',
			}
		},
		yAxis : {
			title : {
				text : ''
			},
			min: 0
		},
		tooltip : {
			shared : true,
			valueSuffix : ' 秒'
		},
		plotOptions : {
			line : {
				dataLabels : {
					enabled : true
				}
			}
		},
		series : []
	};

	$(function(){
		Highcharts.setOptions({
        	global: {
            	useUTC: false
        	}
    	});
		lineChart(null);
	})
	function lineChart(data) {
		$.post('PyScript.action?method=lineCharts',data, function(json) {
			chartOptions.series = json.series;
			chartOptions.chart.type='line';
			chartOptions.yAxis.title.text='最长运行时间(秒)';
			$('#hc-line').highcharts(chartOptions);
		});
	}

	function areaChart(data){
		$.post('PyScript.action?method=areaCharts',data, function(json) {
			chartOptions.series = json.series;
			chartOptions.chart.type='area';
			chartOptions.yAxis.title.text='数据量)';
			$('#hc-area').highcharts(chartOptions);
		});
	}

	function fGetSelect(name,value){
		$('#scriptName').val(name);
		$('#hScriptName').val(value);
	}

	$('#reset').on('click',function(){
		$('#scriptName').val('');
		$('#hScriptName').val('');
		$('#logName').val('');
		$('#methodName').val('');
		$('#stepName').val('');
		$('.sd').val('');
		$('.ed').val('');
	});
	$('#hc-pie').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title : null,
        credits : {enabled : false},
		exporting : {enabled : false},
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    }
                }
            }
        },
        series: [{
            type: 'pie',
            name: '各步骤时间占比',
            data: [
                ['Firefox',   45.0],
                ['IE',       26.8],
                {
                    name: 'Chrome',
                    y: 12.8,
                    sliced: true,
                    selected: true
                },
                ['Safari',    8.5],
                ['Opera',     6.2],
                ['Others',   0.7]
            ]
        }]
    });
</script>
</html>