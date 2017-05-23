<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML >
<html>
<head>
	<jsp:include page="/WEB-INF/view/common.jsp"/>
    <title>合同基本信息修改页</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
    <script type="text/javascript">
    	function next(){
    		var id = "${id}";
    		var supid = "${supid}";
    		window.location.href="${pageContext.request.contextPath}/purchaseContract/createTextContract.html?id="+id+"&supid="+supid;
    	}
    	
    	function cancel(){
    		window.location.href="${pageContext.request.contextPath}/purchaseContract/selectAllPuCon.html";
    	}
    </script>
<body>
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0);"> 首页</a></li><li><a href="javascript:void(0);">保障作业</a></li><li><a href="javascript:void(0);">采购合同管理</a></li><li class="active"><a href="javascript:void(0);">合同明细信息</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
<!-- 新增模板开始-->
   <div class="container">
   		<%--<form action="${pageContext.request.contextPath}/pqinfo/save.html" method="post">
   		--%><div class="headline-v2">
   			<h2>明细信息</h2>
   		</div>
   		<div class="container table_box">
      	<table class="table table-bordered table-condensed table-hover">
	      <thead>
			<tr>
				<th class="info w50">序号</th>
				<th class="info">编号</th>
				<th class="info">物资名称</th>
				<th class="info">品牌商标</th>
				<th class="info">规格型号</th>
				<th class="info">计量单位</th>
				<th class="info">数量</th>
				<th class="info">单价(元)</th>
				<th class="info">合计金额(元)</th>
				<th class="info">交付时间</th>
				<th class="info">备注</th>
			</tr>
		</thead>
		<c:forEach items="${requList}" var="reque" varStatus="vs">
			<tr>
				<td class="tc">${(vs.index+1)}</td>
				<td class="tl pl20">${reque.serialNumber}</td>
				<td class="tl pl20">${reque.goodsName}</td>
				<td class="tl pl20">${reque.brand}</td>
				<td class="tl pl20">${reque.stand}</td>
				<td class="tc">${reque.item}</td>
				<td class="tc">${reque.purchaseCount}</td>
				<td class="tr ">${reque.price}</td>
				<td class="tr ">${reque.budget}</td>
				<td class="tc">${reque.deliverDate}</td>
				<td class="tl pl20">${reque.memo}</td>
			</tr>
   		</c:forEach>
	</table>
    </div>
  		<div  class="col-md-12 tc mt20">
   			<button class="btn" onclick="next()">下一步</button>
   			<button class="btn btn-windows cancel" onclick="cancel()" type="button">取消</button>
  		</div>
  	<%--</form>
 --%></div>
</body>
</html>
