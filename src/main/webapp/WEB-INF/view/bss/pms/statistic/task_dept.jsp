<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<link href="${pageContext.request.contextPath}/public/highmap/js/font-awesome.css" media="screen" rel="stylesheet">
<script type="text/javascript">
    $(function(){
				var dataAxis =eval('${name}');
				var data = eval('${data}');
				var yMax = '${max}';
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
				$("#funsionCharts").html("");
				var myChart = echarts.init(document.getElementById("funsionCharts"));
				myChart.setOption(option);
				myChart.hideLoading();
		});
  </script>
</head>

<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a
					href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')">
						首页</a></li>
				<li><a href="javascript:void(0);">保障作业系统</a></li>
				<li><a href="javascript:void(0);">采购计划管理</a></li>
				<li class="active"><a
					href="javascript:jumppage('${pageContext.request.contextPath}/statistic/taskList.html');">任务查询统计</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
		<div class="container">
		<div class="headline-v2 fl">
			<h2>按需求部门统计</h2>
		</div>
	<div class="col-md-12 pl20 mt10">
				<input class="btn-u" type="button" name="" value="按明细查询" onclick="javascript:location.href='${pageContext.request.contextPath}/statistic/taskDetailList.html'" />
				<input class="btn-u" type="button" name="" value="按任务查询" onclick="javascript:location.href='${pageContext.request.contextPath}/statistic/taskList.html'" />
				<input class="btn-u" type="button" name="" value="按需求部门统计" onclick="javascript:location.href='${pageContext.request.contextPath}/statistic/charDept.html'" />
				<input class="btn-u" type="button" name="" value="按采购方式统计" onclick="javascript:location.href='${pageContext.request.contextPath}/statistic/charType.html'" />
				<input class="btn-u" type="button" name="" value="按月统计" onclick="javascript:location.href='${pageContext.request.contextPath}/statistic/charMonth.html'" />
  </div>
  <div id="funsionCharts" style="width:1000px;height:450px;margin: 0 auto;overflow: auto;"></div>
  </div>
</body>
</html>
