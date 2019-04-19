if(typeof TIS === 'undefined') {
	TIS = {}
}
$.extend(TIS, {
	/**
	*config 实例
	*	config = {
			id : 'chart_container',	//渲染目标
			title : 'echart 标题',	//图表标题,不传不显示
			type : 'line',			//图表类型, 可以传递一个数组，例如 type: ['line','bar','scatter'],   可选 line , bar , pie, area , scatter
			toolbox : false,		//不传默认为true
			smooth: true,			//不传递默认为false
			showDataZoom : true ,	//不传递默认为false,是否显示区域缩放的工具条,大多用于渲染历史数据,配合 area 图使用
			labels : ['日期','产量'],		//中文名
			columns : ['riqi','chanliang'],		//字段名
			yAxisNames:['%','dsf'],
 			yAxisIndexs: [0,1],
			datas : [{riqi: '2018-05-01',chanliang :120},
					 {riqi: '2018-05-03',chanliang :130},
					 {riqi: '2018-05-06',chanliang :160}],	//数据
			onClick : function(params){
				console.log(params)
			}
		}
		
		TIS.renderEcharts(config)
	 */
	renderEcharts: function(config) {
		var chartContainer = $("#" + config.id);
		//设置默认高度 350
		if(chartContainer.height() == 0) chartContainer.height(350);

		var option = {};
		//判断图表类型
		var chartType = "";
		if(typeof(config.type) == 'string') {
			chartType = config.type;
		} else if(Array.isArray(config.type) && config.type.length > 0) {
			chartType = config.type[0];
		}

		switch(chartType) {
			case 'pie':
				option = TIS.getPieChartOption(config);
				break;
			case 'line':
			case 'scatter':
			case 'area':
			case 'bar':
				option = TIS.getOhterChartOption(config);
				break;
			default:
				TIS.alert("你使用了TIS不支持的图表类型");
				break;
		}

		if(config.title) {
			option.title = {
				text: config.title
			}
		}
		var chart = echarts.init(document.getElementById(config.id));
		chart.setOption(option);
		$(window).resize(function() {
			chart.resize()
		});
		setTimeout(function() {
			chart.resize();
		}, 200);
		if(config.onClick) {
			chart.on('click', function(params) {
				config.onClick(params);
			})
		}
		return chart;
	},
	/**
	 * 生成PIE chart option
	 */
	getPieChartOption: function(config) {
		var legendData = [],
			seriesData = [];
		var seriesName = config.labels[1];
		for(var i = 0; i < config.datas.length; i++) {
			var it = config.datas[i];
			if(!(legendData.indexOf(it[config.columns[0]]) > 0)) {
				legendData.push(it[config.columns[0]]);
				seriesData.push({
					name: it[config.columns[0]],
					value: it[config.columns[1]]
				})
			}
		}
		var option = {
			tooltip: {
				trigger: 'item',
				formatter: '{b} <br/> 产量：{c}  <br/>占比：{d}% '
			},
			legend: {
				bottom: 'bottom',
				data: legendData
			},
			series: [{
				name: seriesName,
				type: 'pie',
				radius: '65%',
				center: ['50%', '45%'],
				data: seriesData,
				label: {
					normal: {
						formatter: '{b}\n 产量：{c} \n 占比：{d}%'
					}
				},
				itemStyle: {
					emphasis: {
						shadowBlur: 10,
						shadowOffsetX: 0,
						shadowColor: 'rgba(0, 0, 0, 0.5)'
					}
				}
			}]
		};
		if(!(config.toolbox == false)) {
			option.toolbox = {
				feature: {
					saveAsImage: {},
				}
			};
		}
		return option
	},

	/**
	 * 生成其他图的option
	 */
	getOhterChartOption: function(config) {
		var xAxisdata = [],
			seriesData = [],
			legendData = [];
		var seriesName = config.labels[1];
		for(var i = 1; i < config.labels.length; i++) {
			legendData.push(config.labels[i]);
		}

		for(var i = 0; i < config.datas.length; i++) {
			var it = config.datas[i];
			if(!(xAxisdata.indexOf(it[config.columns[0]]) > 0)) {
				xAxisdata.push(it[config.columns[0]]);
			}
		}
		var allDatas = {};
		for(var i = 1; i < config.columns.length; i++) {
			allDatas[config.columns[i]] = []
		}

		for(var j = 0; j < config.datas.length; j++) {
			for(var i = 1; i < config.columns.length; i++) {
				var column = config.columns[i];
				allDatas[column].push(config.datas[j][column])
			}
		}

		var isBoundaryGap = false;

		for(var i = 1; i < config.labels.length; i++) {
			var chartType = "";
			if(typeof(config.type) == 'string') {
				chartType = config.type;
			} else if(Array.isArray(config.type) && config.type.length > 0) {
				if(config.type[i - 1]) {
					chartType = config.type[i - 1];
				} else {
					chartType = config.type[0];
				}
			}

			if(chartType == 'bar') {
				isBoundaryGap = true;
			}

			if(chartType == 'area') {
				seriesData.push({
					name: config.labels[i],
					type: 'line',
					data: allDatas[config.columns[i]],
					areaStyle: {
						normal: {}
					},
					smooth: config.smooth
				})
			} else {
				seriesData.push({
					name: config.labels[i],
					type: chartType,
					data: allDatas[config.columns[i]],
					smooth: config.smooth,
					markPoint: {
						data: [{
								type: 'max',
								name: '最大值'
							},
							{
								type: 'min',
								name: '最小值'
							}
						]
					},
					markLine: {
						data: [{
							type: 'average',
							name: '平均值'
						}]
					}
				});
			}

}
        option = {
			tooltip: {
				trigger: 'axis',
			},
			legend: {
				data: legendData
			},
			grid: {
				left: '3%',
				//				right : '4%',
				containLabel: true
			},
			xAxis: [{
				type: 'category',
				boundaryGap: isBoundaryGap,
				data: xAxisdata
			}],
			yAxis: [{
				type: 'value'
			}],
			series: seriesData
		};
		if(!(config.toolbox == false)) {
			option.toolbox = {
				feature: {
					saveAsImage: {},
					magicType: {
						type: ['line', 'bar']
					}
				}
			};
		}
		if(config.yAxisNames && config.yAxisNames.length > 0) {
			option.yAxis = [];
			for(var i = 0; i < config.yAxisNames.length; i++) {
				var it = config.yAxisNames[i];
				option.yAxis.push({
					name: it,
					type: 'value'
				})
			}
		}
		if(config.yAxisIndexs && config.yAxisIndexs.length > 0) {
			for(var i = 0; i < option.series.length; i++) {
				var it = option.series[i];
				if(config.yAxisIndexs.length > i) {
					it.yAxisIndex = config.yAxisIndexs[i];
				}
			}
		}
		if((config.showDataZoom)) {
			option.dataZoom = [{
				handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
				start: 0,
				end: 100,
				handleSize: '80%',
				handleStyle: {
					color: '#fff',
					shadowBlur: 3,
					shadowColor: 'rgba(0, 0, 0, 0.6)',
					shadowOffsetX: 2,
					shadowOffsetY: 2,
				}
			}];
		} else {
			option.grid.top = 60;
			option.grid.bottom = '4%';
		}
		return option;
	}
});