<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
	<title>竞价结果查询页面</title>
	
	<script type="text/javascript">
		function printResult(){
			window.location.href="${pageContext.request.contextPath}/ob_project/printResult.html?id=${projectId}";
		}
	</script>
</head>
<body>
<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
       <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">保障作业</a></li><li><a href="javascript:void(0)">定型产品竞价</a></li>
		   <li class="active"><a href="javascript:void(0)">竞价看板</a></li><li class="active"><a href="javascript:void(0)">竞价结果查询</a></li>
		</ul>
        <div class="clear"></div>
      </div>
    </div>
    
<!-- 添加供应商列表页面开始 -->
<div class="container">
	<div class="headline-v2">
     	<h2>竞价标题：${projectName }</h2>
	</div> 
	<!-- 表格开始 -->
	<div class="col-md-12 pl20 mt10">
		<span><font size="3">供应商确认中标比例为${countProportion }%，未中标比例为${100 - countProportion }%.</font></span>
		<button class="btn btn-windows print" onclick="printResult()">打印结果</button>
	    <button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
	</div>
	<h2 class="count_flow">结果信息</h2>
		<%@ include file ="/WEB-INF/view/bss/ob/supplier/supplierCommon.jsp" %>
   </div>
</body>
</html>