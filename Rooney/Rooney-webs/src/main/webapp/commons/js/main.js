/**
 * Get the current time
 */
function getNow() {
	var now = new Date();

	return {
		hours : now.getHours() + now.getMinutes() / 60,
		minutes : now.getMinutes() * 12 / 60 + now.getSeconds() * 12 / 3600,
		seconds : now.getSeconds() * 12 / 60
	};
}

/**
 * Pad numbers
 */
function pad(number, length) {
	// Create an array of the remaining length + 1 and join it with 0's
	return new Array((length || 2) + 1 - String(number).length).join(0)
			+ number;
}

var now = getNow();
// 生成时钟
function fColock(domId) {
	// Create the chart
	$(domId)
			.highcharts(
					{

						chart : {
							type : 'gauge',
							plotBackgroundColor : null,
							plotBackgroundImage : null,
							plotBorderWidth : 0,
							plotShadow : false,
							height : 200
						},

						credits : {
							enabled : false
						},
						exporting : {
							enabled : false
						},
						title : null,

						pane : {
							background : [
									{
									// default background
									},
									{
										// reflex for supported browsers
										backgroundColor : Highcharts.svg ? {
											radialGradient : {
												cx : 0.5,
												cy : -0.4,
												r : 1.9
											},
											stops : [
													[ 0.5,
															'rgba(255, 255, 255, 0.2)' ],
													[ 0.5,
															'rgba(200, 200, 200, 0.2)' ] ]
										}
												: null
									} ]
						},

						yAxis : {
							labels : {
								distance : -20
							},
							min : 0,
							max : 12,
							lineWidth : 0,
							showFirstLabel : false,

							minorTickInterval : 'auto',
							minorTickWidth : 1,
							minorTickLength : 5,
							minorTickPosition : 'inside',
							minorGridLineWidth : 0,
							minorTickColor : '#666',

							tickInterval : 1,
							tickWidth : 2,
							tickPosition : 'inside',
							tickLength : 10,
							tickColor : '#666',
							title : null
						},

						tooltip : {
							formatter : function() {
								return this.series.chart.tooltipText;
							}
						},

						series : [ {
							data : [ {
								id : 'hour',
								y : now.hours,
								dial : {
									radius : '60%',
									baseWidth : 4,
									baseLength : '95%',
									rearLength : 0
								}
							}, {
								id : 'minute',
								y : now.minutes,
								dial : {
									baseLength : '95%',
									rearLength : 0
								}
							}, {
								id : 'second',
								y : now.seconds,
								dial : {
									radius : '100%',
									baseWidth : 1,
									rearLength : '20%'
								}
							} ],
							animation : false,
							dataLabels : {
								enabled : false
							}
						} ]
					},

					// Move
					function(chart) {
						setInterval(function() {

							now = getNow();

							var hour = chart.get('hour'), minute = chart
									.get('minute'), second = chart
									.get('second'),
							// run animation unless we're wrapping around from
							// 59 to
							// 0
							animation = now.seconds === 0 ? false : {
								easing : 'easeOutElastic'
							};

							// Cache the tooltip text
							chart.tooltipText = pad(Math.floor(now.hours), 2)
									+ ':' + pad(Math.floor(now.minutes * 5), 2)
									+ ':' + pad(now.seconds * 5, 2);

							hour.update(now.hours, true, animation);
							minute.update(now.minutes, true, animation);
							second.update(now.seconds, true, animation);

						}, 1000);

					});
}

// Extend jQuery with some easing (copied from jQuery UI)
$.extend($.easing, {
	easeOutElastic : function(x, t, b, c, d) {
		var s = 1.70158;
		var p = 0;
		var a = c;
		if (t == 0)
			return b;
		if ((t /= d) == 1)
			return b + c;
		if (!p)
			p = d * .3;
		if (a < Math.abs(c)) {
			a = c;
			var s = p / 4;
		} else
			var s = p / (2 * Math.PI) * Math.asin(c / a);
		return a * Math.pow(2, -10 * t)
				* Math.sin((t * d - s) * (2 * Math.PI) / p) + c + b;
	}
});

// 该方法有待优化，在目前参数写死，只能生成2个图
function fSolid(domId1, domId2) {
	var gaugeOptions = {

		chart : {
			type : 'solidgauge'
		},

		title : null,

		pane : {
			center : [ '50%', '85%' ],
			size : '140%',
			startAngle : -90,
			endAngle : 90,
			background : {
				backgroundColor : (Highcharts.theme && Highcharts.theme.background2)
						|| '#EEE',
				innerRadius : '60%',
				outerRadius : '100%',
				shape : 'arc'
			}
		},

		tooltip : {
			enabled : false
		},

		// the value axis
		yAxis : {
			stops : [ [ 0.1, '#55BF3B' ], // green
			[ 0.5, '#DDDF0D' ], // yellow
			[ 0.9, '#DF5353' ] // red
			],
			lineWidth : 0,
			minorTickInterval : null,
			tickPixelInterval : 400,
			tickWidth : 0,
			title : {
				y : -70
			},
			labels : {
				y : 16
			}
		},

		plotOptions : {
			solidgauge : {
				dataLabels : {
					y : 5,
					borderWidth : 0,
					useHTML : true
				}
			}
		}
	};

	// The speed gauge
	$(domId1)
			.highcharts(
					Highcharts
							.merge(
									gaugeOptions,
									{
										yAxis : {
											min : 0,
											max : 200,
											title : {
												text : 'Speed'
											}
										},

										credits : {
											enabled : false
										},
										exporting : {
											enabled : false
										},

										series : [ {
											name : 'Speed',
											data : [ 80 ],
											dataLabels : {
												format : '<div style="text-align:center"><span style="font-size:25px;color:'
														+ ((Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black')
														+ '">{y}</span><br/>'
														+ '<span style="font-size:12px;color:silver">km/h</span></div>'
											},
											tooltip : {
												valueSuffix : ' km/h'
											}
										} ]

									}));

	// The RPM gauge
	$(domId2)
			.highcharts(
					Highcharts
							.merge(
									gaugeOptions,
									{
										credits : {
											enabled : false
										},
										exporting : {
											enabled : false
										},
										yAxis : {
											min : 0,
											max : 5,
											title : {
												text : 'RPM'
											}
										},

										series : [ {
											name : 'RPM',
											data : [ 1 ],
											dataLabels : {
												format : '<div style="text-align:center"><span style="font-size:25px;color:'
														+ ((Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black')
														+ '">{y:.1f}</span><br/>'
														+ '<span style="font-size:12px;color:silver">* 1000 / min</span></div>'
											},
											tooltip : {
												valueSuffix : ' revolutions/min'
											}
										} ]

									}));

	// Bring life to the dials
	setInterval(function() {
		// Speed
		var chart = $(domId1).highcharts(), point, newVal, inc;

		if (chart) {
			point = chart.series[0].points[0];
			inc = Math.round((Math.random() - 0.5) * 100);
			newVal = point.y + inc;

			if (newVal < 0 || newVal > 200) {
				newVal = point.y - inc;
			}

			point.update(newVal);
		}

		// RPM
		chart = $(domId2).highcharts();
		if (chart) {
			point = chart.series[0].points[0];
			inc = Math.random() - 0.5;
			newVal = point.y + inc;

			if (newVal < 0 || newVal > 5) {
				newVal = point.y - inc;
			}

			point.update(newVal);
		}
	}, 2000);
}

function fPie(domId) {
	// Radialize the colors
	Highcharts.getOptions().colors = Highcharts.map(
			Highcharts.getOptions().colors, function(color) {
				return {
					radialGradient : {
						cx : 0.5,
						cy : 0.3,
						r : 0.7
					},
					stops : [
							[ 0, color ],
							[
									1,
									Highcharts.Color(color).brighten(-0.3).get(
											'rgb') ] // darken
					]
				};
			});
	// Build the chart
	$(domId)
			.highcharts(
					{
						chart : {
							plotBackgroundColor : null,
							plotBorderWidth : null,
							plotShadow : false
						},
						credits : {
							enabled : false
						},
						exporting : {
							enabled : false
						},
						title : null,
						tooltip : {
							pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
						},
						plotOptions : {
							pie : {
								allowPointSelect : true,
								cursor : 'pointer',
								dataLabels : {
									enabled : true,
									format : '<b>{point.name}</b>: {point.percentage:.1f} %',
									style : {
										color : (Highcharts.theme && Highcharts.theme.contrastTextColor)
												|| 'black'
									},
									connectorColor : 'silver'
								}
							}
						},
						series : [ {
							type : 'pie',
							name : 'Browser share',
							data : [ [ 'Firefox', 45.0 ], [ 'IE', 26.8 ], {
								name : 'Chrome',
								y : 12.8,
								sliced : true,
								selected : true
							}, [ 'Safari', 8.5 ], [ 'Opera', 6.2 ],
									[ 'Others', 0.7 ] ]
						} ]
					});
}