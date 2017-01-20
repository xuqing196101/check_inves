<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'expert_list.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  <script type="text/javascript">
	function removeSupplier (supplierId, packageId) {
		var projectId = "${projectId}";
		var removedReason = layer.prompt({
		    title : '请填写移除的理由：', 
		    formType : 2, 
		    offset : '100px',
		},function(text){
			//$("tab-8").load("${pageContext.request.contextPath}/packageExpert/removeSaleTender.html?supplierId="+supplierId+"&packageId="+packageId+"&projectId="+projectId);
			$.ajax({
				url: "${pageContext.request.contextPath}/packageExpert/removeSaleTender.do",
				data: {"supplierId": supplierId, "packageId": packageId, "projectId": projectId},
				success: function (response) {
					layer.msg("移除成功!",{offset: ['100px', '350px']});
					//window.location.reload();
					$("#"+supplierId).html("已移除");
					$("#"+supplierId).next().children().attr("disabled","disabled");
				},
				error: function () {
					layer.msg("抱歉,移除失败!",{offset: ['100px', '350px']});
					layer.close(removedReason);
				}
			});
		});
	}
  </script>
  <body>
	    <h2 class="list_title">确定供应商</h2>
   		<input type="hidden" id="projectId" value="${projectId}">
    	<table class="table table-bordered table-condensed table-hover table-striped">
			<thead>
			<tr>
			  <!-- <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th> -->
			  <th class="w50 info">序号</th>
			  <th class="info">包名</th>
			  <th class="info">供应商名称</th>
			  <th class="info">状态</th>
			  <th class="info">操作</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${supplierList}" var="supp" varStatus="vs">
			  <tr>
			    <td class="tc w50">${vs.count}</td>
			    <td class="tc">${supp.packageNames}</td>
			    <td class="tc">${supp.suppliers.supplierName}</td>
			    <td class="tc" id="${supp.suppliers.id}">
			    <c:if test="${supp.isFirstPass == 0 && supp.isRemoved eq '0'}">不合格</c:if>
			    <c:if test="${supp.isFirstPass == 1 && supp.isRemoved eq '0'}">合格</c:if>
			    <c:if test="${supp.isFirstPass == null && supp.isRemoved eq '0'}">符合性审查未结束</c:if>
			    <c:if test="${supp.isRemoved eq '1'}">已移除</c:if>
			    <c:if test="${supp.isRemoved eq '2'}">已放弃报价</c:if>
			    </td>
			    <td class="tc"><input <c:if test="${supp.isFirstPass != 1 or supp.isRemoved ne '0' or supp.isFinish == 1}">disabled="disabled"</c:if> type="button" value="移除" onclick="removeSupplier('${supp.suppliers.id}','${supp.packages}')" class="btn"></td>
			  </tr>
			</c:forEach>
			</tbody>
		</table>
  </body>
</html>
