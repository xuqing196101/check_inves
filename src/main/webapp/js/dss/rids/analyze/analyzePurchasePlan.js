$(function() {
	
	/**
	 * pie图
	 */
	optionPlan = {
		title : {
			top : 0,
			left : '43%'
		},
		tooltip : {
	        trigger: 'axis'
	    },
		legend : {
			bottom : 0
		},
		toolbox : {
			show : true,
			feature : {
				dataView : {
					show : true,
					readOnly : false
				},
				magicType : {
					show : true,
					type : [ 'line', 'bar' ]
				},
				restore : {
					show : true
				},
				saveAsImage : {
					show : true
				}
			},
			dataView: {show: true, readOnly: true}, 
		},
		calculable : true,
		xAxis  : [ {
			axisLabel:{interval: 0,
				formatter:function(val){
				    return val.split("").join("\n");
				}
		}
		} ],
		 series: [
		          {
		              itemStyle: {
		                  normal: {
		                      color: function(params) {
		                          // build a color map as your need.
		                          var colorList = [
		                            '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
		                             '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
		                             '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
		                          ];
		                          return colorList[params.dataIndex]
		                      },
		                      label: {
		                          show: true,
		                          position: 'top',
		                          //formatter: '{b}\n{c}'
		                      }
		                  }
		              },
		              markPoint: {
		                  tooltip: {
		                      trigger: 'item',
		                      backgroundColor: 'rgba(0,0,0,0)',
		                      formatter: function(params){
		                          return '<img src="' 
		                                  + params.data.symbol.replace('image://', '')
		                                  + '"/>';
		                      }
		                  },
		                  
		              }
		          }
		      ]
		      
	};
	
	optionPlan1 = {
			title : {
				top : 0,
				left : '43%'
			},
			tooltip : {
				trigger: 'axis'
			},
			legend : {
				bottom : 0
			},
			toolbox : {
				show : true,
				feature : {
					dataView : {
						show : true,
						readOnly : false
					},
					magicType : {
						show : true,
						type : [ 'line', 'bar' ]
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				},
				dataView: {show: true, readOnly: true}, 
			},
			calculable : true,
			xAxis  : [ {
				axisLabel:{interval: 0,
					formatter:function(val){
						return val.split("").join("\n");
					}
				}
			} ],
			series: [
			         {
			        	 name: "总金额",
			        	 itemStyle: {
			        		 normal: {
			        			 color: function(params) {
			        				 // build a color map as your need.
			        				 var colorList = [
			        				                  '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
			        				                  '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
			        				                  '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
			        				                  ];
			        				 return colorList[params.dataIndex]
			        			 },
			        			 label: {
			        				 show: true,
			        				 position: 'top',
			        				 formatter: '{b}\n{c}'
			        			 }
			        		 }
			        	 },
			        	 markPoint: {
			        		 tooltip: {
			        			 trigger: 'item',
			        			 backgroundColor: 'rgba(0,0,0,0)',
			        			 formatter: function(params){
			        				 return '<img src="' 
			        				 + params.data.symbol.replace('image://', '')
			        				 + '"/>';
			        			 }
			        		 },
			        		 
			        	 }
			         }
			         ]
			
	};
	
	// 近5年下达采购计划批次和金额
	$.ajax({
		url : globalPath + "/resAnalyze/selectNowFiveYearAllBudgetByPlan.do",
		type : "POST", // 请求方式
		dataType : "json", // 返回格式为json
		success : function(data) {
			$("#nowFiveYearPurPlanBudgetBYOrg").echartsTemplate({
				dataList:data,
				XField:'name',
				YField:'value',
				groupField:'group',
				chartType:'bar',
				XTitle:'年份',
				YTitle:'金额&批次',
				customEchartsOptions:function(res,dataList,option){
					return optionPlan;
				}
			});
			// 获取echart对应的对象
			var supplierOrg = $("#nowFiveYearPurPlanBudgetBYOrg").echartsTemplate("getMyChart", null);
			supplierOrg.on('click', function(params) {
				window.location.href = globalPath + "/taskassgin/viewPlan.html?date="+params.name;
			});
		}
	});
	
	// 各采购管理部门下达采购计划总金额
	$.ajax({
		url : globalPath + "/resAnalyze/selectManageBudget.do",
		type : "POST", // 请求方式
		dataType : "json", // 返回格式为json
		success : function(data) {
			$("#purPlanBudgetByManageOrg").echartsTemplate({
				dataList:data,
				XField:'name',
				YField:'value',
				groupField:'group',
				chartType:'bar',
				XTitle:'部门',
				YTitle:'金额/万元',
				customEchartsOptions:function(res,dataList,option){
					return optionPlan1;
				}
			});
			// 获取echart对应的对象
			var supplierOrg = $("#purPlanBudgetByManageOrg").echartsTemplate("getMyChart", null);
			supplierOrg.on('click', function(params) {
				window.location.href = globalPath + "/taskassgin/viewPlan.html?orgId="+params.data.id;
			});
		}
	});
	
	// 采购机构获取前10名的总金额
	$.ajax({
		url : globalPath + "/resAnalyze/selectPlanBudget.do",
		type : "POST", // 请求方式
		dataType : "json", // 返回格式为json
		success : function(data) {
			$("#purPlanBudgetByOrg").echartsTemplate({
				dataList:data,
				XField:'name',
				YField:'value',
				groupField:'group',
				chartType:'bar',
				XTitle:'部门',
				YTitle:'金额/万元',
				customEchartsOptions:function(res,dataList,option){
					return optionPlan1;
				}
			});
			// 获取echart对应的对象
			var supplierOrg = $("#purPlanBudgetByOrg").echartsTemplate("getMyChart", null);
			supplierOrg.on('click', function(params) {
				window.location.href = globalPath + "/taskassgin/viewPlan.html?orgnizationId="+params.data.id;
			});
		}
	});
	
});