<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>




<title>采购需求管理</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">




  <jsp:include page="/WEB-INF/view/common.jsp"/> 

<script type="text/javascript">
	/** 全选全不选 */
	function selectAll(){
		 var checklist = document.getElementsByName ("chkItem");
		 var checkAll = document.getElementById("checkAll");
		 if(checkAll.checked){
			   for(var i=0;i<checklist.length;i++)
			   {
			      checklist[i].checked = true;
			   } 
			 }else{
			  for(var j=0;j<checklist.length;j++)
			  {
			     checklist[j].checked = false;
			  }
		 	}
		}
	
	/** 单选 */
	function check(){
		 var count=0;
		 var checklist = document.getElementsByName ("chkItem");
		 var checkAll = document.getElementById("checkAll");
		 for(var i=0;i<checklist.length;i++){
			   if(checklist[i].checked == false){
				   checkAll.checked = false;
				   break;
			   }
			   for(var j=0;j<checklist.length;j++){
					 if(checklist[j].checked == true){
						   checkAll.checked = true;
						   count++;
					   }
				 }
		   }
	}
 
 
	function printdiv(printpage)
	{
	var headstr = "<html><head><title></title></head><body>";
	var footstr = "</body>";
	var newstr = document.all.item(printpage).innerHTML;
	var oldstr = document.body.innerHTML;
	document.body.innerHTML = headstr+newstr+footstr;
	window.print(); 
	document.body.innerHTML = oldstr;
	return false;
	}
	</script>

</script>
</head>

<body>
	<!--面包屑导航开始-->
 
	<div class="container">
		<div class="container clear margin-top-30 over_scroll h365" >

			<form id="acc_form" action="${pageContext.request.contextPath }/accept/update.html" method="post">
				<table class="table table-bordered table-condensed mt5">
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
					 		<th class="info">备注</th>
				 	 		
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
							<td class="tc w50">${obj.seq } 
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
							<td class="tl">${obj.memo }</td>
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
		</div>
		
		 <div class="col-md-12 col-sm-12 col-xs-12 tc mt10">
				<input class="btn btn-windows print" type="button" onclick="printdiv('acc_form')" value="打印"> 
				<input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
		</div>

	</div>

</body>
</html>
