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

function contractDateil(id){
	location.href="${pageContext.request.contextPath }/contractSupervision/contractDateil.html?id="+id;
}
function openProject(id,contractId){
	location.href="${pageContext.request.contextPath }/contractSupervision/projectDateil.html?id="+id+"&contractId="+contractId;
}

function openPlan(id,contractId){
	location.href="${pageContext.request.contextPath }/contractSupervision/planList.html?id="+id+"&contractId="+contractId;
}
function openDemand(id,contractId){
	location.href="${pageContext.request.contextPath }/contractSupervision/demandList.html?id="+id+"&contractId="+contractId;
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
					<img alt="" onclick="openDemand('${project.id}','${contract.id}')" src="${pageContext.request.contextPath}/public/backend/images/u43.png"> 
					<%-- <c:forEach items="${lists}" var="obj">
		              <p class="ml20 tl">需求部门：${obj.department}</p>
		              <p class="ml20 tl">需求名称：${obj.planName}</p>
		              <p class="ml20 tl">编报时间：<fmt:formatDate type='date' value='${obj.createdAt}' pattern=" yyyy-MM-dd HH:mm:ss " /></p>
		              <p class="ml20 tl">联系人：${obj.userId}</p>
		            </c:forEach> --%>
					</td>
					<td class="h365 tc">
					 <img alt="" onclick="openPlan('${project.id}','${contract.id}')" src="${pageContext.request.contextPath}/public/backend/images/u43.png"> 
                      <%-- <c:forEach items="${list}" var="obj">
				            <p class="ml20 tl">采购管理部门：${obj.purchaseId}</p>
				            <p class="ml20 tl">计划名称：${obj.fileName}</p>
				            <p class="ml20 tl">计划编号：${obj.planNo}</p>
				            <p class="ml20 tl">计划下达时间：<fmt:formatDate type='date' value='${obj.updatedAt}' pattern=" yyyy-MM-dd HH:mm:ss " /></p>
				            <p class="ml20 tl">联系人：${obj.userId}</p>
			            </c:forEach>   --%>
					</td>
					<td class="h365 tc" >
					   <img alt="" onclick="openProject('${project.id}','${contract.id}')" src="${pageContext.request.contextPath}/public/backend/images/u43.png"> 
					</td>
					<td class="h365 tc">
					   <img alt="" onclick="contractDateil('${contract.id}')" src="${pageContext.request.contextPath}/public/backend/images/u43.png"> 
					</td>
				</tr>
				
			</tbody>
		</table>
		<div class="col-md-12 col-xs-12 col-sm-12 tc mt20">
        <button class="btn btn-windows back" onclick="window.history.go(-1)" type="button">返回</button>
      </div>
		</div>
	</div>


</body>
</html>