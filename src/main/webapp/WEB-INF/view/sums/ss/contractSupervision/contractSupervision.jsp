<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML >
<head>
<jsp:include page="/WEB-INF/view/common.jsp" />
<title>合同详细</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript">
function openFile(id){
	location.href="${pageContext.request.contextPath }/contractSupervision/filePage.html?id="+id;
}
function contractDateil(id){
	location.href="${pageContext.request.contextPath }/contractSupervision/contractDateil.html?id="+id;
}

</script>
</head>

<body>
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="#"> 首页</a></li>
				<li><a href="#">业务监管系统</a></li>
				<li><a href="#">采购业务监督</a></li>
				<li><a href="#">采购合同监督</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<div class="container">
		<div class="headline-v2">
			<h2>合同监督</h2>
		</div>
		<div class="content table_box">
		<button class="btn btn-windows edit" onclick="contractDateil('${contract.id}')">查看任务汇总</button>
		<table class="table table-bordered mt5">
			<tbody>
				<tr>
					<td class="w300 tc">采购需求</td>
					<td class="w300 tc">采购计划</td>
					<td class="w300 tc">采购项目</td>
					<td class="w300 tc">采购合同</td>
				</tr>
				<tr>
					<td class="h365 tc">
					<c:forEach items="${lists}" var="obj">
		              <p class="ml20 tl">需求部门：${obj.department}</p>
		              <p class="ml20 tl">需求名称：${obj.planName}</p>
		              <p class="ml20 tl">编报时间：<fmt:formatDate type='date' value='${obj.createdAt}' pattern=" yyyy-MM-dd HH:mm:ss " /></p>
		              <p class="ml20 tl">联系人：${obj.userId}</p>
		            </c:forEach>
					</td>
					<td class="h365 tc">
					
                      <c:forEach items="${list}" var="obj">
				            <p class="ml20 tl">采购管理部门：${obj.purchaseId}</p>
				            <p class="ml20 tl">计划名称：${obj.fileName}</p>
				            <p class="ml20 tl">计划编号：${obj.planNo}</p>
				            <p class="ml20 tl">计划下达时间：<fmt:formatDate type='date' value='${obj.updatedAt}' pattern=" yyyy-MM-dd HH:mm:ss " /></p>
				            <p class="ml20 tl">联系人：${obj.userId}</p>
			            </c:forEach>  
					</td>
					<td class="h365 tc">
					        <p class="ml20 tl">项目名称：${project.name}</p>
				            <p class="ml20 tl">项目编号：${project.projectNumber}</p>
				            <p class="ml20 tl">采购方式：${project.dictionary.name}</p>
				            <p class="ml20 tl">联系人：</p>
					</td>
					<td class="h365 tc">
					   <img alt="" onclick="openFile('${contract.id}')" src="${pageContext.request.contextPath}/public/backend/images/u43.png">
					   
					        <p class="ml20 tl">合同名称：${contract.name}</p>
				            <p class="ml20 tl">合同编号：${contract.code}</p>
				            <p class="ml20 tl">签订时间：</p>
					</td>
				</tr>
				<tr>
					<td class="h50 tc">
					<div class="w200 mt5">审核人：</div>
					</td>
					<td class="h50 tc">
					<div class="w200 mt5">审核人：</div>
					</td>
					<td class="h50 tc">
					<div class="w200 mt5">审核人：</div>
					</td>
					<td class="h50 tc">
					   <div class="w200 mt5">审核人：</div>
					   <div class="w200 mt5">负责人：</div>
					   
					</td>
				</tr>
			</tbody>
		</table>
		</div>
	</div>


</body>
</html>