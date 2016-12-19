<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>
<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="${pageContext.request.contextPath}/">

<title>各包分配专家</title>
<script type="text/javascript">

	function selectAll(obj){
		// 全选/全不选
		$("input[name='checkItem']").each(function(i,result){
			result.checked = obj.checked;
		});
	}
	var y = 150;
	var x = 300;
	function toTotal(){
	   var packageId = "${packageId}";
	   var projectId = "${projectId}";
	   $.ajax({
			url:"${pageContext.request.contextPath}/packageExpert/isGather.do",
			data:{"packageIds":packageId, "projectId":projectId},
			async:false,
			success:function (response) {
				if (response == "ok") {
					$.ajax({
						 url:'${pageContext.request.contextPath}/packageExpert/scoreTotal.do',
						 data:{"packageId":packageId,"projectId":projectId},
						 async:false,
						 success:function(){
							 layer.alert("已汇总",{offset: [y, x], shade:0.01});
						 },
						 error: function(){
							 layer.alert("汇总失败,请稍后重试!",{offset: [y, x], shade:0.01});
						 }
					 });
				} else {
					layer.alert(response + "不满足汇总条件!", {
						offset : [ y, x ],
						shade : 0.01
					});
				}
			}
		});
	}
	//查看供应商报价
	function supplierView(supplierId) {
		var projectId = $("#projectId").val();
		location.href = "${pageContext.request.contextPath}/packageExpert/supplierQuote.html?projectId="
				+ projectId + "&supplierId=" + supplierId;
	}
	function is_Null(size){
		if (size == "0" || size == 0) {
			layer.confirm('暂无数据', {
				btn : [ '返回' ],
				shade: false //不显示遮罩
			//按钮
			}, function() {
				window.location.href='${pageContext.request.contextPath}/packageExpert/toScoreAudit.html?projectId=${projectId}&flowDefineId=${flowDefineId}';
			});		
		}
	}
	
	//返回
	function goBack(url){
		$("#tab-6").load(url);
	}
</script>
</head>
<body onload="is_Null('${packExpertExtList.size()}')">

<div class="container">
  <!-- 表格开始-->
  <div class="container">
    <c:if test="${packExpertExtList.size()>0 }">
    <!-- 循环包 -->
	<form id="formTable">
	<c:forEach items="${packageList }" var="pack" varStatus="vs">
	  <c:if test="${pack.id eq packageId}">
		<h3>包名称：${pack.name }</h3>
	    <div class="mb5 fr">
		  <button class="btn btn-windows input" onclick="window.print();" type="button">打印信息</button>
		  <button class="btn" onclick="toTotal()" type="button">汇总</button>
		  <button class="btn" onclick="" type="button">复核</button>
		  <button class="btn" onclick="" type="button">结束</button>
		</div>
		<!--循环供应商  -->
		<table class="table table-bordered table-condensed table-hover table-striped">
		  <thead>
			<tr>
			  <th><input type="checkbox" id="checkAll" onchange="selectAll(this)"></th>
			  <th class="info">专家/供应商</th>
			  <c:forEach items="${supplierList}" var="supplier">
			    <c:if test="${fn:contains(supplier.packages,packageId)}">
			  	  <th class="info">${supplier.suppliers.supplierName}</th>
			  	</c:if>
			  </c:forEach>
			</tr>	
		  </thead>
		  <!-- 遍历该包内的专家,控制行数 -->
		  <c:forEach items="${expertList }" var="ext">
			<tr>
			  <td><input type="checkbox" name="checkItem" value="${ext.expert.id}"></td>
			  <td>${ext.expert.relName}</td>
			  <!-- 遍历该包供应商控制分数的显示 -->
			  <c:forEach items="${supplierList}" var="supplier">
			    <c:if test="${fn:contains(supplier.packages,packageId)}">
			      <c:set var="flag" value="0"/>
			      <!-- 遍历专家给供应商打的分数 -->
			  	  <c:forEach items="${expertScoreList}" var="score">
			  	    <c:if test="${score.packageId eq packageId and score.expertId eq ext.expert.id and score.supplierId eq supplier.suppliers.id}">
			  	      <!-- 如果有分数就设置flag=1 -->
			  	      <c:set var="flag" value="1"/>
			  	      <c:set var="scores" value="${score.score}"/>
			  	    </c:if>
			  	  </c:forEach>
			  	  <!-- 根据flag的值判断有没有分数值 -->
			  	  <c:if test="${flag eq '1'}">
			  	    <td>${scores}</td>
			  	  </c:if>
			  	  <c:if test="${flag eq '0'}">
			  	    <td>暂无分数</td>
			  	  </c:if>
			  	</c:if>
			  </c:forEach>
			</tr>
		  </c:forEach>
		</table>
	  </c:if>
	</c:forEach>
    </form>
    </c:if>
  </div>
  <div align="center">
	<input type="button" class="btn btn-windows back" value="返回" onclick="goBack('${pageContext.request.contextPath}/packageExpert/toScoreAudit.html?projectId=${projectId}&flowDefineId=${flowDefineId}')">
  </div>
</div>
</body>
</html>
