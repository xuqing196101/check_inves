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
 
 
  
    
 
 
</script>
</head>

<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="#"> 首页</a></li>
				<li><a href="#">保障作业系统</a></li>
				<li><a href="#">采购计划管理</a></li>
				<li class="active"><a href="#">采购需求管理</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<div class="container">
		<div class="headline-v2 fl">
			<h2>计划明细</h2>
		</div>
		<div class="container clear margin-top-30">

			<form id="acc_form" action="${pageContext.request.contextPath }/accept/update.html" method="post">
				<table class="table table-bordered table-condensed mt5">
					<thead>
					<tr>
							<th class="info" colspan="13">事业部门需求</th>
						 
							<c:forEach items="${bean }" var="obj">
								<th class="info" colspan="${obj.size}q">${obj.name }</th>
							</c:forEach>
						</tr>
					
						<tr>
							<th class="info w50">序号</th>
							<th class="info">需求部门</th>
							<th class="info">物资类别及物种名称</th>
							<th class="info">规格型号</th>
							<th class="info">质量技术标准（技术参数）</th>
							<th class="info">计量单位</th>
							<th class="info">采购数量</th>
							<th class="info">单位（元）</th>
							<th class="info">预算金额（万元）</th>
							<th class="info">交货期限</th>
							<th class="info">采购方式建议</th>
							<th class="info">供应商名称</th>
					 		<th class="info">备注</th>
							<c:forEach items="${all }" var="p">
											<th class="info">
											  <c:if test="${p.param=='1'}">
												  	采购方式
												  </c:if>
												   <c:if test="${p.param=='2'}">
												  	采购机构
												  </c:if>
												
												   <c:if test="${p.param=='3'}">
												     	其他建议
														
												  </c:if>
												    <c:if test="${p.param=='4'}">
													 技术参数意见
										  </c:if>
											</th>
							</c:forEach>
							
						</tr>
					</thead>

					<c:forEach items="${list }" var="obj" varStatus="vs">
						<tr>
							<td class="tc w50">${obj.seq } 
							</td>
							<td> ${obj.department }  </td>
							<td>${obj.goodsName }</td>
							<td class="tc"> ${obj.stand }</td>
							<td class="tc"> ${obj.qualitStand }</td>
							<td class="tc"> ${obj.item }</td>
							<td class="tc">${obj.purchaseCount }</td>
							<td class="tc">${obj.price }</td>
							<td class="tc">${obj.budget }</td>
							<td>${obj.deliverDate }</td>
							<td>
								<c:forEach items="${kind}" var="kind">
									<c:if test="${kind.id == obj.purchaseType}">${kind.name}</c:if>
								</c:forEach>
							</td>
							<td class="tc">${obj.supplier }</td>
							<td class="tc">${obj.memo }
							
							<c:forEach items="${all }" var="al">
							
								
								<td class="tc">
									<c:forEach items="${audits }" var="as">
									<c:if test="${as.purchaseId==obj.id and as.auditParamId==al.id }">
										${as.paramValue }
									</c:if>
								 </c:forEach>
								<%-- <td class="tc">${obj.oneOrganiza }
								<td class="tc">${obj.oneAdvice }
								<td class="tc">${obj.twoTechAdvice }
								<td class="tc">${obj.twoAdvice } --%>
								</td>
							
							</c:forEach>
						</tr>

					</c:forEach>
				</table>
			 
				<input class="btn btn-windows save" type="button" onclick="window.print()" value="打印"> 
				<input class="btn btn-windows reset" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
			</form>
		</div>
	</div>

</body>
</html>
