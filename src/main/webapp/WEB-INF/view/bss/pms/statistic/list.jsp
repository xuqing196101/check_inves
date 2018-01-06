<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>

		<title>采购需求管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />

		<link href="${pageContext.request.contextPath}/public/highmap/js/font-awesome.css" media="screen" rel="stylesheet">

		<script type="text/javascript">
		$(function(){
			  laypage({
				    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
				    pages: "${info.pages}", //总页数
				    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
				    skip: true, //是否开启跳页
				    total: "${info.total}",
				    startRow: "${info.startRow}",
				    endRow: "${info.endRow}",
				    groups: "${info.pages}">=3?3:"${info.pages}", //连续显示分页数
				    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
				    	var page = location.search.match(/page=(\d+)/);
				    	if(page==null){
				    		page = {};
				    		var data = "${info.pageNum}";
				    		page[0]=data;
				    		page[1]=data;
				    	}
				        return page ? page[1] : 1;
				    }(), 
				    jump: function(e, first){ //触发分页后的回调
				        if(!first){ //一定要加此判断，否则初始时会无限刷新
				        	$("#page").val(e.curr);
							$("#add_form").submit();
				        }
				    }
				});
			    $(document).keyup(function(event) {
					if (event.keyCode == 13) {
						$("#form1").submit();
					}
				});
		  });
			/*分页  */
			 /* $(function(){ */
			/* 
	  if (!window.console || !console.firebug){
		    var names = ["log", "debug", "info", "warn", "error", "assert", "dir", "dirxml", "group", "groupEnd", "time", "timeEnd", "count", "trace", "profile", "profileEnd"];

		    window.console = {};
		    for (var i = 0; i < names.length; ++i)
		        window.console[names[i]] = function() {}
		}
	   */

			/* laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${info.pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    total: "${info.total}",
		    startRow: "${info.startRow}",
		    endRow: "${info.endRow}",
		    skip: true, //是否开启跳页
		    groups: "${info.pages}">=3?3:"${info.pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
		    	var page = location.search.match(/page=(\d+)/);
		    	if(page==null){
		    		page = {};
		    		var data = "${list.pageNum}";
		    		page[0]=data;
		    		page[1]=data;
		    	}
		        return page ? page[1] : 1;
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		            if(!first){ //一定要加此判断，否则初始时会无限刷新
		        	$("#page").val(e.curr);
		        	  $("#add_form").submit();
		        	
		     
		        }  
		    }
		});
  });  */
			/*   $(function(){
				  
					});
			    */
			function bar(sign) {
				$("#div_table").hide();
				$.ajax({
					url: '${pageContext.request.contextPath}/statistic/bar.html',
					type: "post",
					data: $("#add_form").serialize(),
					dataType: "json",
					success: function(data) {
						var dataAxis = data.name;
						var data = data.data;
						var yMax = data.max;
						var dataShadow = [];
						for(var i = 0; i < data.length; i++) {
							dataShadow.push(yMax);
						}
						option = {
								title: {
									text: '需求部门统计',
									x: 'center'
								},
							    color: ['#3398DB'],
							    tooltip : {
							        trigger: 'axis',
							        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
							            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
							        }
							    },
							    grid: {
							        left: '3%',
							        right: '4%',
							        bottom: '10%',
							        containLabel: true
							    },
							    yAxis: [
							            {
							                type: 'value',
							                name: '（万元）'
							            }
							        ],
							    xAxis : [
							        {
							            type : 'category',
							            axisLabel:{
					                         interval:0,
					                         rotate:45,
					                         margin:2,
					                         textStyle:{
					                             color:"#222"
					                         },
							            },
							            data : dataAxis,
							            axisTick: {
							                alignWithLabel: true
							            }
							        }
							    ],
							    yAxis : [
							        {
							            type : 'value',
							            name: '单位（万元）'
							        }
							    ],
							    series : [
							        {
							            type:'bar',
							            barWidth: '35',
							            data:data,
							            name:'金额',
							        }
							    ]
							};
						$("#funsionCharts_div_id").html("");
						var myChart = echarts.init(document.getElementById("funsionCharts_div_id"));
						myChart.setOption(option);
						myChart.hideLoading();
						$("#funsionCharts_div_id").show();
						$("#container").hide();
					}
				});

			}

			function pipe(sign) {
				$("#div_table").hide();
				$.ajax({
					url: '${pageContext.request.contextPath}/statistic/pipe.html',
					type: "post",
					data: $("#add_form").serialize(),
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
					dataType: "json",
					success: function(data) {
						option = {
							title: {
								text: '采购方式统计',
								x: 'center'
							},
							tooltip: {
								trigger: 'item',
								formatter: "{a} <br/>{b} : {c} ({d}%)"
							},
							legend: {
								orient: 'vertical',
								left: '200px',
								data: data.type
							},
							series: [{
								name: '采购方式',
								type: 'pie',
								radius: '55%',
								center: ['50%', '60%'],
								data: data.maps,
								itemStyle: {
									emphasis: {
										shadowBlur: 10,
										shadowOffsetX: 0,
										shadowColor: 'rgba(0, 0, 0, 0.5)'
									}
								}
							}]
						};
						$("#funsionCharts_div_id").html("");
						var myChart = echarts.init(document.getElementById("funsionCharts_div_id"));
						myChart.setOption(option);
						myChart.hideLoading();
						$("#funsionCharts_div_id").show();
						$("#container").hide();

					},

				});

			}

			function line(sign) {
				var swf = "Line.swf";
				$("#div_table").hide();
				$.ajax({
					url: '${pageContext.request.contextPath}/statistic/line.html',
					type: "post",
					data: $("#add_form").serialize(),
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
					dataType: "json",
					success: function(data) {
						option = {
							title: {
								text: '按月统计',
								x: 'center'
							},
							tooltip: {
								trigger: 'axis'
							},
							legend: {
								data: ['采购金额统计']
							},
							grid: {
								left: '3%',
								right: '4%',
								bottom: '3%',
								containLabel: true
							},
							toolbox: {
								feature: {
									saveAsImage: {}
								}
							},
							xAxis: {
								type: 'category',
								boundaryGap: false,
								data: data.month
							},
							yAxis: {
								type: 'value',
								name:'单位（万元）'
							},
							series: data.line
						};
						$("#funsionCharts_div_id").html("");
						var myChart = echarts.init(document.getElementById("funsionCharts_div_id"));
						myChart.setOption(option);
						myChart.hideLoading();
						$("#funsionCharts_div_id").show();
						$("#container").hide();
					},

				});

			}

			/* $(function() {
				option = {
					title: {
						text: '省市采购',
						x: 'center'
					},
					tooltip: {
						trigger: 'item'
					},
					legend: {
						orient: 'vertical',
						x: 'left',
						data: ['iphone3']
					},
					dataRange: {
						min: 0,
						max: 2500,
						x: 'left',
						y: 'bottom',
						text: ['高', '低'], // 文本，默认为数值文本
						calculable: true
					},
					toolbox: {
						show: true,
						orient: 'vertical',
						x: 'right',
						y: 'center',
						feature: {
							mark: {
								show: true
							},
							dataView: {
								show: true,
								readOnly: false
							},
							restore: {
								show: true
							},
							saveAsImage: {
								show: true
							}
						}
					},
					roamController: {
						show: true,
						x: 'right',
						mapTypeControl: {
							'china': true
						}
					},
					series: [{
							name: '中国',
							type: 'map',
							mapType: 'china',
							roam: false,
							itemStyle: {
								normal: {
									label: {
										show: true
									}
								},
								emphasis: {
									label: {
										show: true
									}
								}
							},
							data: "${data}"
						}

					]
				};

				var myChart = echarts.init(document.getElementById("container"));
				myChart.setOption(option);
				myChart.hideLoading(); */

				/*  var chart = echarts.init(document.getElementById('main'));
			 chart.setOption({
			     series: [{
			         type: 'map',
			         map: 'china'
			     }]
		  $.get('public/echarts/china.json', function (chinaJson) {
				    echarts.registerMap('china', chinaJson);
				    var chart = echarts.init(document.getElementById('main'));
				    chart.setOption({
				        series: [{
				            type: 'map',
				            map: 'china'
				        }]
				    });
				}); */
			/* }); */
            
			function maps() {
				$("#funsionCharts_div_id").hide();
				$("#container").show();
				$("#div_table").hide();

			}

			function resetQuery() {
				$("#add_form").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
			}
		</script>
	</head>

	<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0);">保障作业系统</a>
					</li>
					<li>
						<a href="javascript:void(0);">采购计划管理</a>
					</li>
					<li class="active">
						<a href="javascript:jumppage('${pageContext.request.contextPath}/statistic/list.html');">采购计划汇总统计</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<!-- 录入采购计划开始-->
		<div class="container">
			<div class="headline-v2">
				<h2>采购计划列表
	  </h2>
			</div>

			<div class="search_detail">
			<form id="add_form" action="${pageContext.request.contextPath}/statistic/list.html" class="mb0" method="post">
			<input type="hidden" name="page" id="page">
			<div class="m_row_5">
	    <div class="row">
	      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
	        <div class="row">
	          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">计划名称：</div>
	          <div class="col-xs-8 f0 lh0">
							<input type="text" name="fileName" value="${collectPlan.fileName }" class="w100p h32 f14 mb0">
	          </div>
	        </div>
	      </div>
	      
	      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
	        <div class="row">
	          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">计划类别：</div>
	          <div class="col-xs-8 f0 lh0">
							<select name="goodsType" class="w100p h32 f14">
								<option value="" >请选择</option>
								<c:forEach items="${dic}" var="dic">
								<option value="${dic.id}" <c:if test="${dic.id==collectPlan.goodsType}"> selected</c:if> >${dic.name }</option>
								</c:forEach>
							</select>
	          </div>
	        </div>
	      </div>
				
				<div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
	        <div class="row">
	          <div class="col-xs-12 f0">
							<input class="btn mb0 h32" type="submit" name="" value="查询">
							<input type="button" onclick="resetQuery()" class="btn mb0 mr0 h32" value="重置">
						</div>
	        </div>
	      </div>
	    </div>
	    </div>
			</form>
			</div>
			<div class="col-md-12 pl20 mt10">
				<input class="btn-u" type="button" name="" value="按计划查询" onclick="javascript:location.href='${pageContext.request.contextPath}/statistic/list.html'" />
				<input class="btn-u" type="button" name="" value="按明细查询" onclick="javascript:location.href='${pageContext.request.contextPath}/statistic/detailList.html'" />
				<input class="btn-u" type="button" name="" value="按需求部门统计" onclick="bar(1)" />
				<input class="btn-u" type="button" name="" value="按采购方式统计" onclick="pipe(1)" />
				<input class="btn-u" type="button" name="" value="按月统计" onclick="line(1)" />
			</div>

			<div id="div_table" class="content table_box">
				<table class="table table-bordered table-condensed table-hover">
					<thead>
						<tr>
							<!-- 		  <th class="info w30"><input type="checkbox" id="checkAll" onclick="selectAll()"  alt=""></th>
 -->
							<th class="info w50">序号</th>
							<th class="info" width="38%">计划名称</th>
							<th class="info" width="15%">计划类别</th>
							<!--   <th class="info">需求部门</th>
		  <th class="info">采购方式</th>
		  <th class="info">采购机构</th> -->
							<th class="info" width="12%">预算（万元）</th>
							<th class="info" width="15%">汇总时间</th>
							<th class="info">状态</th>
						</tr>
					</thead>
					<c:forEach items="${info.list}" var="obj" varStatus="vs">
						<tr style="cursor: pointer;">
							<td class="tc w50" >${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>

							<td class="tl" >
								${obj.fileName }
							</td>
							<td>
							  <c:forEach items="${dic}" var="dic">
							     <c:if test="${dic.id==obj.goodsType}">
							        ${dic.name}
							     </c:if>
							  </c:forEach>
							</td>
							<td class="tr" >
								<fmt:formatNumber type="number" pattern="#,##0.00" value="${obj.budget}" />
							</td>
							<td class="tc" ><fmt:formatDate value="${obj.createdAt }"/></td>
							<td class="tl" >

								<c:if test="${obj.status=='1' }">
										   待审核设置
								  </c:if>
								  <c:if test="${obj.status=='2' }">
										   已下达
								  </c:if> 
								  <c:if test="${obj.status=='3' }">
										   第一轮审核
								  </c:if>
								  <c:if test="${obj.status=='4' }">
										    第二轮审核人员设置
								  </c:if>
								  <c:if test="${obj.status=='5' }">
										 第二轮审核
								  </c:if>
								  <c:if test="${obj.status=='6' }">
										   第三轮审核人员设置
								  </c:if>
								  <c:if test="${obj.status=='7' }">
										   第三轮审核
								  </c:if>
								  <c:if test="${obj.status=='8' }">
                                         审核结束
                                  </c:if>
                                  <c:if test="${obj.status=='12' }">
                                         直接下达
                                  </c:if>
							</td>

						</tr>

					</c:forEach>

				</table>

				<div id="pagediv" align="right"></div>
			</div>
		</div>

		<div id="funsionCharts_div_id" style="width:800px;height:450px;display: none;margin: 0 auto;overflow: auto;"></div>

		<div id="container" style="display: none;height: 700px;min-width: 310px;margin: 0 auto;width: 800px;"></div>

	</body>

</html>