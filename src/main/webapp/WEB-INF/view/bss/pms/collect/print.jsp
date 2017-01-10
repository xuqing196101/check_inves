<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
	<head>

	<title>采购需求管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="${pageContext.request.contextPath}/public/backend/css/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/public/backend/css/common.css" media="screen" rel="stylesheet" type="text/css">  

	<link href="${pageContext.request.contextPath}/public/backend/css/bootstrap.min.css" media="print" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/public/backend/css/common.css" media="print" rel="stylesheet" type="text/css">  

	<style>
		@page {
   			size: auto;
   			/* margin: 27mm 30mm 27mm 30mm; */
		}
		
		div.chapter, div.appendix {
    		page-break-after: always;
		}
	</style>
	
	<style media='print' type='text/css'>
		.noprint {
			display:none;
		}
		
		.w200 {
			width: 200px;
		}
		
		body {
			margin: 50px;
		}
	</style>
	
	<script type="text/javascript">
		
		function custom_close(){
			if 
			(confirm("您确定要关闭本页吗？")){
				window.opener=null;
				window.open('','_self');
				window.close();
			}
			else{}
		}
	
	</script>
</head>

<body>
 
	<div class="container">
		<div class="print_container clear margin-top-30  h365" >

			<form id="acc_form" action="${pageContext.request.contextPath }/accept/update.html" method="post">
				<table id='print_table' class="table table-bordered table-condensed mt5">
					<thead>
					<tr class="space_nowrap">
							<th class="info" colspan="14">事业部门需求</th>
						 	<c:if test="${auditTurn!=null }">
						 		<th class="info" >一轮审核</th>
						 	</c:if>
							 <c:if test="${auditTurn==2||auditTurn==3}">
						 		<th class="info" >二轮审核</th>
						 	</c:if>
						 	<c:if test="${auditTurn==3}">
						 		<th class="info" >三轮审核</th>
						 	</c:if>
						</tr>
					
						<tr class="space_nowrap">
							<th class="info w50">序号</th>
							<th class="info">需求部门</th>
							<th class="info">物资类别及<br>物种名称</th>
							<th class="info">规格型号</th>
							<th class="info">质量技术标准<br>（技术参数）</th>
							<th class="info">计量<br>单位</th>
							<th class="info">采购<br>数量</th>
							<th class="info">单位<br>（元）</th>
							<th class="info">预算金额<br>（万元）</th>
							<th class="info">交货期限</th>
							<th class="info">采购方式</th>
							<th class="info">采购机构</th>
							<th class="info">供应商名称</th>
					 		<th class="info w200">备注</th>
				 	 		
							<c:if test="${auditTurn!=null }">
						 		<th class="info" >一轮审核意见</th>
						 	</c:if>
							 <c:if test="${auditTurn==2||auditTurn==3}">
						 		<th class="info" >二轮审核意见</th>
						 	</c:if>
						 	<c:if test="${auditTurn==3}">
						 		<th class="info" >三轮审核意见</th>
						 	</c:if>
						</tr>
					</thead>

					<c:forEach items="${list }" var="obj" varStatus="vs">
						<tr>
							<td class="tc w50" style="vertical-align:middle" >${obj.seq } 
							</td>
							<td class="tl"> ${obj.department }  </td>
							<td class="tl">${obj.goodsName }</td>
							<td class="tl"> ${obj.stand }</td>
							<td class="tl"> ${obj.qualitStand }</td>
							<td class="tl"> ${obj.item }</td>
							<td class="tl">${obj.purchaseCount }</td>
							<td class="tl">${obj.price }</td>
							<td class="tr">${obj.budget }</td>
							<td>${obj.deliverDate }</td>
							<td>
								<c:forEach items="${kind}" var="kind">
									<c:if test="${kind.id == obj.purchaseType}">${kind.name}</c:if>
								</c:forEach>
							</td>
							<td>
								<c:forEach items="${org}" var="org">
									<c:if test="${org.orgId == obj.organization}">${org.name}</c:if>
								</c:forEach>
							</td>
							
							
							
							<td class="tl">${obj.supplier }</td>
							<td class="tl w200">${obj.memo }</td>
							<c:if test="${auditTurn!=null }">

						 		<td>${obj.oneAdvice }</td>
						 	</c:if>
							 <c:if test="${auditTurn==2||auditTurn==3}">
						 		<td>${obj.twoAdvice }</td>
						 	</c:if>
						 	<c:if test="${auditTurn==3}">
						 		<td>${obj.threeAdvice }</td>
						 	</c:if>
						 	
						<%-- 	
							<c:forEach items="${all }" var="al">
							
								
								<td class="tl pl20">
									<c:forEach items="${audits }" var="as">
									<c:if test="${as.purchaseId==obj.id and as.auditParamId==al.id }">
										${as.paramValue }
									</c:if>
					 
								</td>
							
							</c:forEach> --%>
						</tr>

					</c:forEach>
				</table>
			
			</form>
				<input class="btn btn-windows print noprint" type="button" onclick="window.print();" value="打印"> 
				<input class="btn btn-windows back noprint" value="关闭" type="button" onclick="custom_close()">
		</div>
		
	</div>


</body>
</html>
