<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE HTML >
<head>
<jsp:include page="/WEB-INF/view/common.jsp" />
<title>合同详细</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

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
			<h2>合同列表</h2>
		</div>
		<table class="table table-bordered">
			<tbody>
				<tr>
					<td class="w350 tc">采购需求</td>
					<td class="w350 tc">采购计划</td>
					<td class="w350 tc">采购项目</td>
					<td class="w350 tc">采购合同</td>
					
				</tr>
				<tr>
					<td class="h365 tc"></td>
					<td class="h365 tc"></td>
					<td class="h365 tc"></td>
					<td class="h365 tc">
					   <img alt="" src="${pageContext.request.contextPath}/public/backend/images/u43.png">
					</td>
					
				</tr>
				<tr>
					<td class="h50 tc"></td>
					<td class="h50 tc"></td>
					<td class="h50 tc"></td>
					<td class="h50 tc"></td>
					
				</tr>

			</tbody>
		</table>
	</div>


</body>
</html>