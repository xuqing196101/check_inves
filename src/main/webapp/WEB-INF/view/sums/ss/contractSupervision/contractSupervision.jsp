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
<script type="text/javascript">
function openFile(id){
	location.href="${pageContext.request.contextPath }/contractSupervision/filePage.html?id="+id;
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
		<table class="table table-bordered">
			<tbody>
				<tr>
					<td class="w300 tc">采购需求</td>
					<td class="w300 tc">采购计划</td>
					<td class="w300 tc">采购项目</td>
					<td class="w300 tc">采购合同</td>
				</tr>
				<tr>
					<td class="h365 tc">
					<img alt=""  src="${pageContext.request.contextPath}/public/backend/images/u43.png">
					</td>
					<td class="h365 tc">
					<img alt=""  src="${pageContext.request.contextPath}/public/backend/images/u43.png">
					</td>
					<td class="h365 tc">
					<img alt="" src="${pageContext.request.contextPath}/public/backend/images/u43.png">
					</td>
					<td class="h365 tc">
					   <img alt="" onclick="openFile('${contract.id}')" src="${pageContext.request.contextPath}/public/backend/images/u43.png">
					   <div class="w100 mt5 fl">合同名称：</div>
					   <div class="mt5 fl">${contract.name}</div>
					   <div class="clear"></div>
					   <div class="w100 mt5 fl">合同编号：</div>
					   <div class="mt5 fl">${contract.code}</div>
					   <div class="clear"></div>
					   <div class="w100 mt5 fl">签订时间：</div>
					   <div class=" mt5 fl"></div>
					   <div class="clear"></div>
					</td>
				</tr>
				<tr>
					<td class="h50 tc"></td>
					<td class="h50 tc"></td>
					<td class="h50 tc"></td>
					<td class="h50 tc">
					   <div class="w200 mt5">审核人：</div>
					   <div class="w200 mt5">负责人：</div>
					   <div class="mt5 tc">
                             <button class="btn btn-windows edit" onclick="">查看任务汇总</button>
                       </div>
					</td>
				</tr>
			</tbody>
		</table>
		</div>
	</div>


</body>
</html>