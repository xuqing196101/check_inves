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
                myTool : {
                    show : true,
                    title : '查询所有',
                    icon : 'image://'+globalPath+'/public/backend/images/pie.png',
                    onclick : function (){
                        findAllSupplier();
                    }
                },
				dataView : {
					show : true,
					readOnly : false,
                    optionToContent: function (opt) {
                        let axisData = opt.series[0].data; //坐标数据
                        let series = opt.series; //折线图数据
                        let tdHeads = '<td  style="padding: 0 10px">类型</td>'; //表头
                        let tdBodys = ''; //数据
                        series.forEach(function (item) {
                            //组装表头
                            tdHeads += `<td style="padding: 0 10px">${item.name}</td>`;
                        });
                        let table = `<table border="1" style="margin-left:20px;border-collapse:collapse;font-size:14px;text-align:center"><tbody><tr>${tdHeads} </tr>`;
                        for (let i = 0, l = axisData.length; i < l; i++) {
                            for (let j = 0; j < series.length; j++) {
                                //组装表数据
                                tdBodys += `<td>${ series[j].data[i].value}</td>`;
                            }
                            table += `<tr><td style="padding: 0 10px">${axisData[i].name}</td>${tdBodys}</tr>`;
                            tdBodys = '';
                        }
                        table += '</tbody></table>';
                        return table;
                    }
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
				/*restore : {
					show : true
				},*/
				saveAsImage : {
					show : true
				}
			}
		},
		calculable : true,
		series : [ {
			name : '供应商数量',
		} ]
	};

	// 供应商企业性质
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
                myTool : {
                    show : true,
                    title : '查询所有',
                    icon : 'image://'+globalPath+'/public/backend/images/pie.png',
                    onclick : function (){
                        findAllSupplier();
                    }
                },
				dataView : {
					show : true,
					readOnly : false,
                    optionToContent: function (opt) {
                        let axisData = opt.series[0].data; //坐标数据
                        let series = opt.series; //折线图数据
                        let tdHeads = '<td  style="padding: 0 10px">类型</td>'; //表头
                        let tdBodys = ''; //数据
                        series.forEach(function (item) {
                            //组装表头
                            tdHeads += `<td style="padding: 0 10px">${item.name}</td>`;
                        });
                        let table = `<table border="1" style="margin-left:20px;border-collapse:collapse;font-size:14px;text-align:center"><tbody><tr>${tdHeads} </tr>`;
                        for (let i = 0, l = axisData.length; i < l; i++) {
                            for (let j = 0; j < series.length; j++) {
                                //组装表数据
                                tdBodys += `<td>${ series[j].data[i].value}</td>`;
                            }
                            table += `<tr><td style="padding: 0 10px">${axisData[i].name}</td>${tdBodys}</tr>`;
                            tdBodys = '';
                        }
                        table += '</tbody></table>';
                        return table;
                    }
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
				/*restore : {
					show : true
				},*/
				saveAsImage : {
					show : true
				}
			}
		},
		calculable : true,
		series : [ {
			name : '供应商数量',
			radius : ['50%', '70%']
		} ]
	};
	
	// 各采购机构供应商数量
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
                myTool : {
                    show : true,
                    title : '查询所有',
                    icon : 'image://'+globalPath+'/public/backend/images/pie.png',
                    onclick : function (){
                        findAllSupplier();
                    }
                },
				dataView : {
					show : true,
					readOnly : false,
                    optionToContent: function (opt) {
                        let axisData = opt.series[0].data; //坐标数据
                        let series = opt.series; //折线图数据
                        let tdHeads = '<td  style="padding: 0 10px">采购机构</td>'; //表头
                        let tdBodys = ''; //数据
                        series.forEach(function (item) {
                            //组装表头
                            tdHeads += `<td style="padding: 0 10px">${item.name}</td>`;
                        });
                        let table = `<table border="1" style="margin-left:20px;border-collapse:collapse;font-size:14px;text-align:center"><tbody><tr>${tdHeads} </tr>`;
                        for (let i = 0, l = axisData.length; i < l; i++) {
                            for (let j = 0; j < series.length; j++) {
                                //组装表数据
                                tdBodys += `<td>${ series[j].data[i].value}</td>`;
                            }
                            table += `<tr><td style="padding: 0 10px">${axisData[i].srcData.name}</td>${tdBodys}</tr>`;
                            tdBodys = '';
                        }
                        table += '</tbody></table>';
                        return table;
                    }
				},
				magicType : {
					show : true,
					type : [ 'line', 'bar' ]
				},
				/*restore : {
					show : true
				},*/
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
		        	  name: "供应商数量",
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
	
	// 各品目下的供应商数量
	$.ajax({
		url : globalPath + "/resAnalyze/analyzeSupplierCateType.do",
		type : "POST", // 请求方式
		dataType : "json", // 返回格式为json
		success : function(data) {
			$("#supplierCateType").echartsTemplate({
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
			var supplierCateType = $("#supplierCateType").echartsTemplate("getMyChart", null);
			supplierCateType.on('click', function(params) {
				window.location.href = globalPath + "/supplierQuery/readOnlyList.html?supplierType="+params.name+"&supplierTypeIds="+params.data.id+"&judge=5&sign=2";
			});
		}
	});
	
	// 供应商企业性质下的数量
	$.ajax({
		url : globalPath + "/resAnalyze/analyzeSupplierByNature.do",
		type : "POST", // 请求方式
		dataType : "json", // 返回格式为json
		success : function(data) {
			// 供应商企业性质
			$("#supplierNature").echartsTemplate({
				dataList : data,
				YField : 'value',
				groupField : 'group',
				chartType : 'pie',
				customEchartsOptions : function(res, dataList, option) {
					return optionNature;
				}
			});
			// 获取echart对应的对象
			var supplierNature = $("#supplierNature").echartsTemplate("getMyChart", null);
			supplierNature.on('click', function(params) {
				// 获取供应商企业类型
				window.location.href = globalPath + "/supplierQuery/readOnlyList.html?businessNature="+params.data.id+"&judge=5&sign=2";
			});
		}
	});
	
	
	// 统计不同采购机构下的供应商
	$.ajax({
		url : globalPath + "/resAnalyze/selectSupByOrg.do",
		type : "POST", // 请求方式
		dataType : "json", // 返回格式为json
		success : function(data) {
			// 供应商企业性质
			$("#supplierOrg").echartsTemplate({
				dataList:data,
				XField:'name',
				YField:'value',
				groupField:'group',
				chartType:'bar',
				XTitle:'机构',
				YTitle:'数量/个',
				customEchartsOptions:function(res,dataList,option){
					return optionOrgSup;
				}
			});
			// 获取echart对应的对象
			var supplierOrg = $("#supplierOrg").echartsTemplate("getMyChart", null);
			supplierOrg.on('click', function(params) {
				window.location.href = globalPath + "/supplierQuery/readOnlyList.html?orgId="+params.data.id+"&judge=5&sign=2";
			});
		}
	});
});

function findAllSupplier(){
    window.location.href = globalPath + "/supplierQuery/readOnlyList.html?judge=5&sign=2";
}