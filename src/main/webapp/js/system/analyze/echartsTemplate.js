
/**
 * Author: Easong
 * 说明文档
 * 
 * 使用方法  jquery.echartsTemplate(本模板的option（不是echarts的option）);
 * 
 * 必须参数：
 * 
 * * * * *  url  :null,//请求连接  String   如有dataList   则不需要
 * 
		queryParam  :null,//请求连接时的查询参数    Object   {}  如不需要可不填
 *				
		title  :null,//图表标题           String   如不需要可不填
 *
 * * *	chartType  :'line',//图表类型     String   可用的值有  line 折线图，bar 柱形图， pie 饼图 pie_ring 环行 heatmap 热力地图 map 地图 radar 雷达图

 * * *  dataList  :null,//数据组   后台以List<Map<String,Object>>  的数据传过来   如有url  则不需要
		
 * * *	groupField  :null,//分组字段   
		
 * * *	XField  :null,//X轴字段    饼图，环行图，雷达图没有X轴
		
 * * *	YField  :null,//Y轴字段
 * 
 * 		XTitle:null,//x轴名称    用于显示X轴的单位
		YTitle:null,//Y轴名称    用于显示Y轴的单位
 * 		tooltip:false, // 提示框  默认不使用模板提示框
 * 		formatterX:null,//格式化X轴
		formatterY:null,//格式化Y轴
		formatYValue:false,//是否使用模板格式化Y轴刻度   默认false
 * 		yNameSuffix:false,//是否使用模板格式化Y轴名称（加单位后缀）   默认false
 * 
 * 
 * 可选：
 * 		dataZoom: true,    boolean   是否启用模板的缩放功能
 * 		smooth:false      boolean  是否启用平滑显示
		theme  :null,//主题   String
		customTitle : null,//自定义标题样式
			列：    dataList  为过滤后的数据
						function(dataList){
							return {text:'自定义标题'};
						}
		customXAxis : null,//自定义X轴     用法同上   
		customYAxis : null,//自定义Y轴  用法同上 
		customSeries : null,//自定义系列值  用法同上 （此方法需要返回数组）
		customEchartsOptions : null,//自定义option    用法同上 （此方法需要返回option）  
						此方法会传三个参数，第一个为请求到的数据，第二个为过滤后的数据，第三个为模板生成的option
						function(res,dataList,option){}
									注：自定义option 只会覆盖自定义的属性，不会覆盖所有属性，所以不需要返回新的option
									如  模板生成的是  {
										title:{
											text:'模板标题'
										}
										series:[
											{
												name:'系列名称',
												data:[0,1,2,3,4]
											}
										]
									}
									自定义为
									{
										series:[
											{
												name:'系列名称2',
												
											}
										]
									}
									
									结果为
									{
										title:{
											text:'模板标题'
										}
										series:[
											{
												name:'系列名称2',
												data:[0,1,2,3,4]
											}
										]
									}
									
									
		loadFilter:null,//过滤请求到的数据 交给模板生成图表  
						如请求到的数据不是模板需要的数据，其data属性才是我们需要的数据，则可以这样
						function(res){return res.data;}
		
		onLoadSuccess:null//图表生成后触发 
						此方法会传两个参数，第一个为请求到的数据，第二个为过滤后的数据
						function(res,dataList){}
						
	
 * * * * * * * * * * * * * * * * * * * * * 方 法  * * * * * * * * * *
 * 
 * 方法的使用方式为    jquery.echartsTemplate(method,param);
 * 
 *     方法名称                 参数                   说明
 * echartsTemplate			none 				返回该模板的option
 * 
 * options					none				返回生成的echarts图表的option
 * 
 * getMyChart				none				返回生成的echarts对象
 * 
 * getMaxY					none				返回生成的echatrs图标的Y轴最大值数组（双Y轴时为两个元素）  
 * 
 * formatY					Integer				格式化Y轴名称    按照数值给出单位
 * 
 */






(function($){
	var echartsTypes = {
			line:'line',//折线图
			bar:'bar',//柱形图
			pie:'pie',//饼图
			pie_ring:'pie_ring',//环行
			heatmap:'heatmap',//热力地图
			map:'map',//地图
			radar:'radar'
	}
	
	
	var groupList = new Array();
	var xValueList = new Array();
	var yValueList = new Array();
	
	$.fn.echartsTemplate = function (optOrMhd,params){
		var vthis = this;
		if (typeof optOrMhd == "string") {
			return $.fn.echartsTemplate.methods[optOrMhd](this, params);
		}
		//初始化图表数据
		groupList = new Array();
		xValueList = new Array();
		yValueList = new Array();
		
		var _this =$(this); 
		var opt = $.extend({},$.fn.echartsTemplate.defaults,optOrMhd);
		var myChart = echarts.init(_this[0]);
		if(opt.url){
			myChart.showLoading(
			{
				text:'加载中',
				effect:'whirling',
				textStyle:{fontSize:20}
			});
			doPost(opt.url,opt.queryParam,function(result)
			{
				myChart.hideLoading();
				//初始化图表数据
				groupList = new Array();
				xValueList = new Array();
				yValueList = new Array();
				//如果请求成功，把数据给opt
				opt.dataList = result;
				opt.result = result;
				
				//加载图表
				loadEchartData(opt,_this,optOrMhd,myChart,vthis);
				
			},
			"json"/*,
			function(status,XMLHttpRequest) //请求完成后最终执行参数
			{
				
				if(!rptTimes)
					rptTimes=1;
				ajaxStatus(status,chartDivId,XMLHttpRequest,rptTimes,function(repeatTimes)
				{
					doAjax4Chart(myChart,chartType,echarts,url,params,chartDivId,optionField,callBack4Option,callBack,callBack4Result,callBack4Pre,oneTheme,call4AjaxComp,repeatTimes);
				});
				if(call4AjaxComp)
					call4AjaxComp(status,XMLHttpRequest);
			}*/);
		}else{
			opt.result = opt.dataList;
			loadEchartData(opt,_this,optOrMhd,myChart,vthis);
		}
		
		
		
		
		/*if(callBack)
			callBack(result,myChart,echarts);*/
	};
	
	$.fn.echartsTemplate.methods = {
		echartsTemplate : function(jq) {
			var opts = $(jq[0]).data("echartsTemplate");//jq.datagrid.options;
			return opts;
		},
		options : function(jq){
			var echartsOptions = $(jq[0]).data("echartsOptions");
			return echartsOptions;
		},
		getMyChart:function(jq){
			var myChart = $(jq[0]).data("myChart");
			return myChart;
		},
		//获取Y轴最大值
		getMaxY:function(jq){
			var echartsOptions = $(jq[0]).data("echartsOptions");
			var maxArr = new Array();
			for(var i=0;i<echartsOptions.series.length;i++){
				var newArray = new Array();
				for(var k=0;k<echartsOptions.series[i].data.length;k++){
					newArray.push(echartsOptions.series[0].data[k].value);
				}
				var max = Math.max.apply(null, newArray);
				maxArr.push(max);
			}
			return maxArr;
		},
		formatY:function(jq,value){
			
		}
	};
	
	
	$.fn.echartsTemplate.defaults = {
		url:null,//请求连接
		queryParam:null,//请求连接时的查询参数
		title:null,//图表标题
		chartType:'line',//图表类型
		dataList:null,//数据组   后台以List<Map<String,Object>>  的数据传过来
		divId:null,//要生成的DIV  id属性
		groupField:null,//分组字段
		XField:null,//X轴字段
		YField:null,//Y轴字段
		TooltipTitle:null,//提示框组件单位
		tooltip:false, // 默认不使用模板提示框
		XTitle:null,//x轴名称    用于显示X轴的单位
		YTitle:null,//Y轴名称    用于显示Y轴的单位
		formatterX:null,//格式化X轴
		formatterY:null,//格式化Y轴
		theme:null,//主题
		dataZoom:false,//是否启用数据缩放
		smooth:false,//是否平滑显示
		formatterX:null,//格式化X轴的值()
		formatterY:null,//格式化Y轴的值（）
		formatYValue:false,//是否使用模板格式化Y轴刻度   默认false
		yNameSuffix:false,//是否使用模板格式化Y轴名称（加单位后缀）  默认false
		customTitle : null,//自定义标题样式
		customXAxis : null,//自定义X轴   //有data覆盖原有data    没有保留原有data   
		customYAxis : null,//自定义Y轴  //同上
		customSeries : null,//自定义系列值
		customEchartsOptions : null,//自定义
		loadFilter:null,//过滤请求到的数据
		onLoadSuccess:null//图表生成后触发
	};
	
	
	/**
	 * 加载图表数据
	 */
	function loadEchartData(opt,_this,optOrMhd,myChart,vthis){
		_this.data("myChart", myChart);
		_this.data("echartsTemplate", opt);
		
		//如果有数据拦截，修改数据
		if(opt.loadFilter){
			opt.dataList = opt.loadFilter(opt.result);
		}
		
		if(!opt.dataList){
			if(opt.customEchartsOptions){
				var option = opt.customEchartsOptions(opt.result,opt.dataList,null);
				if(option){
					//showLoading(myChart);
					myChart.setOption(option);
					
					//讲echarts   option  放入缓存
					_this.data("echartsOptions", newOption);
					_this.data("myChart", myChart);
					$(window).resize(function()
					{
						myChart.resize();
					});
				}else{
					_this.text('暂无数据，请重新选择查询条件');
				}
				
				if(opt.onLoadSuccess)
					opt.onLoadSuccess(opt.result,opt.dataList);
			}
			return;
		}else if(opt.dataList.length==0){
			_this.text('暂无数据，请重新选择查询条件');
			if(opt.onLoadSuccess)
				opt.onLoadSuccess(opt.result,opt.dataList);
			return ;
		}
		optOrMhd = optOrMhd || {};
		var temple = _this.echartsTemplate;
		var opts;
		if (temple) {
			opts = $.extend({},temple.options, opt);
			temple.options = opts;
		} 
		//_this.datagrid.options = opt;
		
		
		var title = getTitle(opt);
		var tooltip = getTooltip(opt);
	    var legend = getLegend(opt);
	    var grid = getGrid();
	    var xAxis = getXAxis(opt.chartType,opt.dataList,opt.XField,opt.XTitle,opt.customXAxis,opt.formatterX);
	    
	    var series = getSeries(opt);
		var dataZoom = opt.dataZoom?getDataZoom(opt.chartType):null;
		var toolbox = getToolbox(opt.chartType);
		var color = getColor();
		var option={
				title : title,
			    tooltip : tooltip,
			    legend: legend,
			    grid: grid,
			    xAxis : xAxis,
			    series : series,
			    dataZoom : dataZoom,
			    toolbox : toolbox,
			    color:color
			};
		_this.data("echartsOptions", option);
		var yAxis = getYAxis(opt,vthis);
		option.yAxis = yAxis;
		var newOption = option;
		if(opt.customEchartsOptions){
			newOption = $.extend(true,{},option,opt.customEchartsOptions(opt.result,opt.dataList,option));
		}
		
//		console.log(newOption);
		
		//showLoading(myChart);
		myChart.setOption(newOption);
		
		//讲echarts   option  放入缓存
		_this.data("echartsOptions", newOption);
		
		//是否设置主题
		if(opt.theme)
			myChart.setTheme(opt.theme);
		
		$(window).resize(function()
		{
			myChart.resize();
		});
		
		if(opt.onLoadSuccess)
			opt.onLoadSuccess(opt.result,opt.dataList);
		
		
	}
	
	/**
	 * 获得图表标题属性   title
	 * 标题组件，包含主标题和副标题。
	 */
	function getTitle(opt){
		
		if(opt.customTitle){
			return opt.customTitle(opt.dataList);
		}
		
		var title = {
			text:(opt.title || '')
		}
		return title;
	}
	
	/**
	 * 获得图例   legend
	 * 图例组件。
	 * 图例组件展现了不同系列的标记(symbol)，颜色和名字。可以通过点击图例控制哪些系列不显示
	 */
	function getLegend(opt){
		
		var legend = {
		        orient: 'vertical',
		        x: 'left',
		        //bottom:'20'
		    }
		
		
		if(opt.customLegend)
			return opt.customLegend(top.dataList);
		
		
		var data = opt.dataList;
		var groupField = opt.groupField;
		//如果分组字段为空 则不要图例
		if(!groupField){
			return {};
		}
			
		
		for(var i=0;i<data.length;i++){
			var groupValue = data[i][groupField];
			if($.inArray(groupValue,groupList)==-1){
				groupList.push(groupValue);
			}
		}
		
		var chartType=opt.chartType;
		if(chartType==echartsTypes.line || chartType==echartsTypes.bar ){
			return $.extend({},{data:groupList});
		}else if( chartType==echartsTypes.pie || chartType==echartsTypes.pie_ring || chartType==echartsTypes.radar){
			return $.extend({},legend,{data:groupList});
		}
			
		return legend;
	}
	
	
	/**
	 * 获得提示框组件   tooltip
	 * 
	 */
	function getTooltip(opt){
		if(!opt.tooltip){
			return {
		        trigger: 'axis'
		    }
		}
		
		var tooltip = {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    }
		
		if(opt.chartType==echartsTypes.pie || opt.chartType==echartsTypes.pie_ring || opt.chartType==echartsTypes.radar ){
			return  {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    }
		}
		
		if(opt.chartType==echartsTypes.line || opt.chartType==echartsTypes.bar){
			if(opt.title){
				return  {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        },
			        formatter: "{a} <br/>{b}: {c} "+(opt.TooltipTitle||"")
			    }
			}else{
				return  {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        },
			        formatter: "{b}: {c} "+(opt.TooltipTitle||"")
			    }
			}
			
		}
		
		return tooltip;
	}
	
	/**
	 * 获得grid组件   grid  (可以稍微改变表格位置)
	 * 直角坐标系内绘图网格，单个 grid 内最多可以放置上下两个 X 轴，左右两个 Y 轴。可以在网格上绘制折线图，柱状图，散点图（气泡图）。
	 * 在 ECharts 2.x 里单个 echarts 实例中最多只能存在一个 grid 组件，在 ECharts 3 中可以存在任意个 grid 组件。
	 */
	function getGrid(title){
		var grid = {
		        left: '3%',
		        right: '40',
		        bottom: '40',
		        containLabel: true
		    }
		return grid;
	}
	
	/**
	 * 获得X轴属性   xAxis  
	 * 直角坐标系 grid 中的 x 轴，一般情况下单个 grid 组件最多只能放左右两个 x 轴，多于两个 x 轴需要通过配置 offset 属性防止同个位置多个 x 轴的重叠。
	 */
	function getXAxis(type,data,XField,XTitle,customXAxis,formatterX){
		
		if(customXAxis)
			return customXAxis(data);
		
		var xAxisList = new Array();
		
		var xAxis = {};
		if(type==echartsTypes.pie || type==echartsTypes.radar || type==echartsTypes.pie_ring){
			return null;
		}else{
			var flag = false; //是否按字符串排序
			for(var i=0;i<data.length;i++){
				var node = data[i];
				var value = node[XField];
				/*if(formatterX){
					value = formatterX(node[XField]);
				}*/
				if($.inArray(value,xValueList)==-1){
					if((typeof value=='number') && value.constructor==Number){
						flag = true;
					}
					xValueList.push(value);
				}
			}
			//如果是数值类型，则按大小排序，如果是其他类型，则不排序，按取值顺序
			if(flag){
				xValueList.sort(function(a,b){
					return a-b;
				});
				
				xAxis = {
					name : XTitle || '',
					type: 'value',
					boundaryGap: false,
					data : xValueList
				}
			}else{
				//xValueList.sort();
				xAxis = {
					name : XTitle || '',
					type : 'category',
					boundaryGap: false,
					data : xValueList
				}
			}
			if(type==echartsTypes.bar){
				xAxis.boundaryGap=true;
				/*xAxis.axisLabel={interval:0,rotate:30};
				xAxis.axisTick={interval:0};*/
			}
			/*if(type==echartsTypes.line){
				xAxis.axisLabel={rotate:30};
			}*/
			
			//如果格式化X轴的值
			if(formatterX){
				xAxis.axisLabel={
					formatter:function(value,index){
						return formatterX(value,index);
					}
				}
			}
			
			xAxisList.push(xAxis);
			return xAxisList;
		}
	}
	
	/**
	 * 获得Y轴属性   yAxis 
	 * 直角坐标系 grid 中的 y 轴，一般情况下单个 grid 组件最多只能放左右两个 y 轴，多于两个 y 轴需要通过配置 offset 属性防止同个位置多个 Y 轴的重叠。
	 */
	function getYAxis(opt,vthis){
		
		if(opt.customYAxis)
			return opt.customYAxis(opt.dataList);
			
		yAxisList = [];
		
		var yAxis = {
				type: 'value',
				name: opt.YTitle || '',
		        axisLabel: {
		            formatter: function(value,index){
		            	return value;
		            }
		        }
			}
		
		if(opt.yNameSuffix){
			var max = $.fn.echartsTemplate.methods['getMaxY'](vthis, null);
			var suffix = '（'+((max/100000000)>1?('亿'):((max/10000)>1?('万'):('')))+'）';
			yAxis.name = (opt.YTitle || '')+suffix;
		}
		
		
		if(opt.formatterY){
			yAxis.axisLabel.formatter=function(value,index){
				return opt.formatterY(value,index);
			}
		}else if(opt.formatYValue){
			yAxis.axisLabel.formatter=function(value,index){
				return ((value/100000000)>1?((value/100000000).toFixed(0)):((value/10000)>1?((value/10000).toFixed(0)):(value)));
			}
		}
		
		if(opt.chartType==echartsTypes.pie || opt.chartType==echartsTypes.pie_ring || opt.chartType==echartsTypes.radar){
			return yAxisList;
		}else{
			yAxisList.push(yAxis);
			return yAxisList;
		}
	}
	
	
	/**
	 * dataZoom[i] 组件 用于区域缩放，从而能自由关注细节的数据信息，或者概览数据整体，或者去除离群点的影响。
	 */
	function getDataZoom(type){
		
		//只要X轴的缩放
		var dataZoom_bar_line = [
			                {
			                    type: 'slider',
			                    show: true,
			                    xAxisIndex: [0]
			                },
			                {
			                    type: 'inside',
			                    xAxisIndex: [0]
			                }
			            ]
		//不要缩放
		var dataZoom_null = null
		
		//启动内置缩放和滑动条缩放
		var dataZoom_map = [
			                {
			                    type: 'slider',
			                    show: true,
			                    xAxisIndex: [0]
			                },
			                {
			                    type: 'slider',
			                    show: true,
			                    yAxisIndex: [0],
			                    left: '93%'
			                },
			                {
			                    type: 'inside',
			                    xAxisIndex: [0]
			                },
			                {
			                    type: 'inside',
			                    yAxisIndex: [0]
			                }
			            ]
		
		if(type==echartsTypes.bar || type==echartsTypes.line){
			return dataZoom_bar_line;
		}else if(type==echartsTypes.map || type==echartsTypes.heatmap){
			return dataZoom_map;
		}else {
			return dataZoom_null;
		}
	}
	
	
	/**
	 * toolbox 工具栏。内置有导出图片，数据视图，动态类型切换，数据区域缩放，重置五个工具。
	 */
	function getToolbox(type){
		var toolbox = {
		        show: true,
		        feature: {
		            /*dataZoom: {
		                yAxisIndex: 'none'
		            },*/     //启用区域缩放
		            //dataView: {readOnly: false},  //启用数据视图
		            magicType: {type: ['line', 'bar']},   //转换为折线图，柱形图
		            restore: {},  //还原
		            saveAsImage: {}  //保存为图片
		        }
		    }
		
		var toolbox_other = {
		        show: true,
		        feature: {
		            /*dataZoom: {
		                yAxisIndex: 'none'
		            },*/     //启用区域缩放
		            //dataView: {readOnly: false},  //启用数据视图
		            //magicType: {type: ['line', 'bar']},   //转换为折线图，柱形图
		            restore: {},  //还原
		            saveAsImage: {}  //保存为图片
		        }
		    }
		/*if(type==echartsTypes.bar || type==echartsTypes.line){
			return toolbox;
		}else{
			return toolbox_other;
		}*/
		return {};
	}
	
	/**
	 * series[i] 系列列表。每个系列通过 type 决定自己的图表类型
	 */
	function getSeries(opt){
		if(opt.customSeries){
			return opt.customSeries(opt.dataList);
		}
		
		var data = opt.dataList;
		
		if(opt.chartType==echartsTypes.pie || opt.chartType==echartsTypes.radar || opt.chartType==echartsTypes.pie_ring){
			var serie = {
					name:group,
					type:opt.chartType==echartsTypes.pie_ring?'pie':opt.chartType,
				};
			if(opt.chartType==echartsTypes.pie_ring){
				serie.radius= ['50%', '70%'];
				serie.avoidLabelOverlap= false;
			}
			if(opt.chartType==echartsTypes.line){
				serie.smooth=true;
			}
			
			var serieData = new Array();
			
			for(var i=0;i<groupList.length;i++){
				var group = groupList[i];
				for(var j=0;j<data.length;j++){
					var value = data[j][opt.YField];

					// ---新加
					var id = data[j].id;
					//如果分组一样，则拿到Y值
					if(data[j][opt.groupField]==group){
						
						serieData.push({
							value:value,
							name:group,
							id:id
						});
						break;
					}
					
				}
			}
			
			serie.data = serieData;
			
			yValueList.push(serie);
			
		}else{
			if(groupList.length>0){
				for(var i=0;i<groupList.length;i++){
					var group = groupList[i];
					var serie = {
						name:group,
						type:opt.chartType==echartsTypes.pie_ring?'pie':opt.chartType,
					};
					
					if(opt.chartType==echartsTypes.pie_ring){
						serie.radius= ['50%', '70%'];
						serie.avoidLabelOverlap= false;
					}
					
					var serieData = new Array();
					for(var k=0;k<xValueList.length;k++){
						var xValue = xValueList[k];
						
						var yValue = 0;
						var srcData = null;
						var id;
						for(var j=0;j<data.length;j++){
							var value = data[j][opt.XField];
							// 获取id
							id = data[j].id;
							
							//如果分组一样，X轴一样，则拿到Y值
							if(data[j][opt.groupField]==group && value==xValue){
								yValue = data[j][opt.YField];
								srcData = data[j];
								break;
							}
						}
						
						serieData.push({
							name:group,
							value:yValue,
							srcData:srcData,
							id:id
						});
					}
					serie.data = serieData;
					
					yValueList.push(serie);
					
				}
			}else{
				var serie = {
					name:opt.title || '',
					type:opt.chartType==echartsTypes.pie_ring?'pie':opt.chartType,
				};
				
				if(opt.chartType==echartsTypes.pie_ring){
					serie.radius= ['50%', '70%'];
					serie.avoidLabelOverlap= false;
				}
				
				var serieData = new Array();
				for(var k=0;k<xValueList.length;k++){
					var xValue = xValueList[k];
					var yValue = 0;
					var srcData = null;
					for(var j=0;j<data.length;j++){
						var value = data[j][opt.XField];
						
						//如果X轴一样，则拿到Y值
						if(value==xValue){
							yValue = data[j][opt.YField];
							srcData = data[j];
							break;
						}
					}
					serieData.push({
						name:group,
						value:yValue,
						srcData:srcData
					});
				}
				serie.data = serieData;
				yValueList.push(serie);
			}
		}
		return yValueList;
	}
	
	
	/**
	 * color  调色盘颜色列表。如果系列没有设置颜色，则会依次循环从该列表中取颜色作为系列颜色。
	 * 默认为：
		['#c23531','#2f4554', '#61a0a8', '#d48265', '#91c7ae','#749f83',  '#ca8622', '#bda29a','#6e7074', '#546570', '#c4ccd3']
	 */
	function getColor(){

		return ['#ff6f5d','#71b0ef','#2bc7ff','#5ad5b5','#35cd6a','#b4df4e','#ffef00','#ffd000','#fcac02','#fa7b00', '#61a0a8', '#d48265'];
		// return ['#c23531','#2f4554', '#61a0a8', '#d48265', '#91c7ae','#749f83',  '#ca8622', '#bda29a','#6e7074', '#546570', '#c4ccd3'];
	}
	
	
	
	
})(jQuery);