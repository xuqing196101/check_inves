<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<link href="${pageContext.request.contextPath}/public/highmap/js/font-awesome.css" media="screen" rel="stylesheet">
<script type="text/javascript">
    $(function(){
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
					data: eval('${type}')
				},
				series: [{
					name: '采购方式',
					type: 'pie',
					radius: '55%',
					center: ['50%', '60%'],
					data: eval('${data}'),
					itemStyle: {
						emphasis: {
							shadowBlur: 10,
							shadowOffsetX: 0,
							shadowColor: 'rgba(0, 0, 0, 0.5)'
						}
					}
				}]
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
			<h2>按采购方式统计</h2>
		</div>
	<div class="col-md-12 pl20 mt10">
				<input class="btn-u" type="button" name="" value="按任务查询" onclick="javascript:location.href='${pageContext.request.contextPath}/statistic/taskList.html'" />
				<input class="btn-u" type="button" name="" value="按明细查询" onclick="javascript:location.href='${pageContext.request.contextPath}/statistic/taskDetailList.html'" />
				<input class="btn-u" type="button" name="" value="按需求部门统计" onclick="javascript:location.href='${pageContext.request.contextPath}/statistic/charDept.html'" />
				<input class="btn-u" type="button" name="" value="按采购方式统计" onclick="javascript:location.href='${pageContext.request.contextPath}/statistic/charType.html'" />
				<input class="btn-u" type="button" name="" value="按月统计" onclick="javascript:location.href='${pageContext.request.contextPath}/statistic/charMonth.html'" />
  </div>
  <div id="funsionCharts" style="width:800px;height:450px;margin: 0 auto;overflow: auto;"></div>
  </div>
</body>
</html>
