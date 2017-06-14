$(function() {
	// 各类型数量
	optionCateType = {
		title : {
			x : 'center'
		},
		tooltip : {
			trigger : 'item',
			formatter : "{a} <br/>{b} : {c} ({d}%)"
		},
		toolbox : {
			show : true,
			feature : {
				mark : {
					show : true
				},
				dataView : {
					show : true,
					readOnly : false
				},
				magicType : {
					show : true,
					type : [ 'pie', 'funnel' ],
					option : {
						funnel : {
							x : '25%',
							width : '50%',
							funnelAlign : 'left',
							max : 1548
						}
					}
				},
				restore : {
					show : true
				},
				saveAsImage : {
					show : true
				}
			}
		},
		calculable : true,
		series : [ {
			name : '合同数量',
		} ]
	};

	// 专家企业性质
	optionNature = {
		tooltip : {
			trigger : 'item',
			formatter : "{a} <br/>{b} : {c} ({d}%)"
		},
		toolbox : {
			show : true,
			feature : {
				mark : {
					show : true
				},
				dataView : {
					show : true,
					readOnly : false
				},
				magicType : {
					show : true,
					type : [ 'pie', 'funnel' ],
					option : {
						funnel : {
							x : '25%',
							width : '50%',
							funnelAlign : 'center',
							max : 1548
						}
					}
				},
				restore : {
					show : true
				},
				saveAsImage : {
					show : true
				}
			}
		},
		calculable : true,
		series : [ {
			name : '人员数量',
			radius : ['50%', '70%']
		} ]
	};
	
	// 各采购机构人员数量
	optionOrgSup = {
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
		        	  //name: "人员数量",
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
	
	// 各采购机构完成采购合同数量及总金额
	$.ajax({
		url : globalPath + "/resAnalyze/selectPurContractCountAndMoney.do",
		type : "POST", // 请求方式
		dataType : "json", // 返回格式为json
		success : function(data) {
			$("#purContractTotal").echartsTemplate({
				dataList:data,
				XField:'name',
				YField:'value',
				groupField:'group',
				chartType:'bar',
				XTitle:'机构',
				YTitle:'数量',
				customEchartsOptions:function(res,dataList,option){
					return optionOrgSup;
				}
			});
			// 获取echart对应的对象
			var supplierOrg = $("#purContractTotal").echartsTemplate("getMyChart", null);
			supplierOrg.on('click', function(params) {
				window.location.href = globalPath + "/purchaseContract/readOnlyList.html?status=2&purchaseDepName="+params.data.id;
			});
		}
	});
	
	// 
	$.ajax({
		url : globalPath + "/resAnalyze/selectPurProjectByWay.do",
		type : "POST", // 请求方式
		dataType : "json", // 返回格式为json
		success : function(data) {
			$("#purContractByProductType").echartsTemplate({
				dataList : data,
				XField : 'name',
				YField : 'value',
				groupField : 'group',
				chartType : 'pie',
				customEchartsOptions : function(res, dataList, option) {
					return optionCateType;
				}
			});
			// 获取echart对应的对象
			var supplierCateType = $("#purContractByProductType").echartsTemplate("getMyChart", null);
			supplierCateType.on('click', function(params) {
				$.ajax({
					url : globalPath + "/resAnalyze/findDicts.do",
					type : "POST", // 请求方式
					data:{
						dictType:"purProject"
					},
					dataType : "json", // 返回格式为json
					success : function(data) {
						$.each(data,function(index, ele){
							if(ele.name == params.name){
								window.location.href = globalPath + "/project/selectByProject.html?purchaseType="+ele.id;
							}
						})
					}
				});
			});
		}
	});
	
});