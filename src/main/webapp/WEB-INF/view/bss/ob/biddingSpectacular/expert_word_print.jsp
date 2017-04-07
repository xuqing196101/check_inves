<%@ page language="java" import="java.util.*,java.net.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<%@page contentType="application/vnd.ms-word;charset=GBK"%>
<%
String path = request.getContextPath();

String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>

<head>

<base href="<%=basePath%>">

<title>My JSP 'creatWord.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">

<meta http-equiv="cache-control" content="no-cache">

<meta http-equiv="expires" content="0">   

<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">

<meta http-equiv="description" content="This is my page">

<!--

<link rel="stylesheet" type="text/css" href="styles.css">

-->

<%

String fileName = "竞价结果信息表.doc";

//对中文文件名编码 

fileName = URLEncoder.encode(fileName, "utf-8"); 

byte[] yte = fileName.getBytes("GB2312"); 

String unicoStr = new String(yte, "utf-8");

response.setHeader("Content-disposition", "attachment; filename=" + unicoStr);     

%>
</head>

<body>
<div class="table-a" style="width:85%;margin:auto;">
	<table width="100%" border="1">
	    <tbody>
	      <tr>
			<td colspan="6" align="center"><br/><b>${ obProject.name }</b><br/>竞价结果信息表<br/><br/></td>
		  </tr> 
		  <tr>
		    <td><b>竞价项目编号</b></td>
		    <td>${ obProject.projectNumber }</td>
		    <td><b>竞价项目名称</b></td>
		    <td>${ obProject.name }</td>
		  </tr>
		  <tr>
		    <td><b>交货地点</b></td>
		    <td>${ obProject.deliveryAddress }</td>
		     <td><b>交货时间</b></td>
		    <td><fmt:formatDate value="${ obProject.deliveryDeadline }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		  </tr>
		  <tr>
		    <td><b>运杂费</b></td>
		    <td>${ transportFees }</td>
		    <c:if test="">
			    <td colspan="2"></td>
		    </c:if>
		    <c:if test="${ empty obProject.transportFeesPrice }">
			    <td colspan="2"></td>
		    </c:if>
		    <c:if test="${ !empty obProject.transportFeesPrice }">
			    <td><b>运杂费用（元）</b></td>
			    <td>${obProject.transportFeesPrice}</td>
		    </c:if>
		  </tr>
		  <tr>
		    <td><b>需求单位</b></td>
		    <td>${ demandUnit }</td>
		    <td><b>采购机构</b></td>
		    <td>${ orgName }</td>
		  </tr>
		  <tr>
		    <td><b>需求联系人</b></td>
		    <td>${ obProject.contactName }</td>
		    <td><b>采购联系人：</b></td>
		    <td>${ obProject.orgContactName }</td>
		  </tr>
		  <tr>
		    <td><b>需求联系电话</b></td>
		    <td>${ obProject.contactTel }</td>
		    <td><b>采购联系电话</b></td>
		    <td>${ obProject.orgContactTel }</td>
		  </tr>
		</tbody>
	</table>
	
	<c:forEach items="${listres}" var="supplier" varStatus="pi">
		<table width="100%" border="1">
			<thead>
				<tr>
					<td colspan="6">供应商名称：${supplier.supplier.supplierName} 排名：第${supplier.ranking}名</td>
			  	</tr>
				<tr colspan="6">
					<td colspan="6">
					第一轮确认成交比例：
					<c:if test="${supplier.status == 1 || supplier.status == 2 }">
			  			<c:if test="${supplier.firstproportion != null }">${supplier.firstproportion }%</c:if>
			  			<c:if test="${supplier.firstproportion == null }">0%</c:if>
					</c:if>
					第二轮确认成交比例：
					<c:if test="${supplier.status == 1 || supplier.status == 2 }">
			  			<c:if test="${supplier.secondproportion != null }">${supplier.secondproportion }%</c:if>
			  			<c:if test="${supplier.secondproportion == null }">0%</c:if>
					</c:if>
					<c:if test="${supplier.status == 1 || supplier.status == 2 }">
					  	<span>总成交比例：${supplier.proportion}%</span>
					</c:if>
					
			 		状态：
					<c:if test="${supplier.status == -1 && supplier.proportion == 0}">未中标</c:if>
					<c:if test="${supplier.status == -1 && supplier.proportion != 0}">未中标</c:if>
					<c:if test="${supplier.status == 0}">未中标</c:if>
					<c:if test="${supplier.status == 1 || supplier.status == 2 }">中标</c:if>
					</td>
				</tr>
				<tr>
				  <th align="center" width="3%">序号</th>
				  <th>产品名称</th>
				  <th>数量</th>
				  <th>自报单价（元）</th>
				  <th>成交单价（元）</th>
				  <th>成交总价（万元）</th>
				</tr>
			</thead>
		
			<!-- 成交的 -->
			<c:if test="${supplier.status == 1 || supplier.status == 2}">
			<c:forEach items="${supplier.obResultSubtabulation}" var="product">
			<c:if test="${product.supplierId == supplier.supplierId}">
				<c:set value="${ total + product.totalMoney }" var = "total" ></c:set>
			</c:if>
			</c:forEach>
				<tr>
				  <td></td>
				  <td align="center" colspan="4">合计</td>
				  <td>${ total }</td>
				</tr>
				<c:forEach items="${supplier.obResultSubtabulation}" var="product" varStatus="va">
					<c:if test="${product.supplierId == supplier.supplierId}">
						<tr>
							<td>${va.index+1 }</td>
				  			<td>${product.product.name }</td>
				  			<td>${product.resultNumber }</td>
							<td>
								<fmt:formatNumber value='${product.myOfferMoney }' pattern='#,##,###.00'/>
							</td>
				  			<td>
				  				<fmt:formatNumber value='${product.dealMoney }' pattern='#,##,###.00'/>
				  			</td>
				  			<td>${product.totalMoney }</td>
				  		</tr>
					</c:if>
				</c:forEach>
				<c:set value="0" var = "total" ></c:set>
			</c:if>
		
			<!-- 放弃的 -->
			<c:if test="${supplier.status == -1}">
				<tr>
				  <td></td>
				  <td align="center" colspan="4">合计</td>
				  <td>${ total }</td>
				</tr>
				<c:forEach items="${supplier.OBResultsInfo}" var="product" varStatus="va">
					<c:if test="${product.supplierId == supplier.supplierId}">
					<tr>
						<td>${va.index+1 }</td>
				  		<td>${product.obProduct.name }</td>
				  		<td>0</td>
						<td>
							<fmt:formatNumber value='${product.myOfferMoney }' pattern='#,##,###.00'/>
						</td>
				  		<td></td>
				  		<td></td>
				  	</tr>
					</c:if>
				</c:forEach>
			</c:if>
		
			<c:if test="${supplier.status == 0}">
				<tr>
				  <td></td>
				  <td align="center" colspan="4">合计</td>
				  <td></td>
				</tr>
				<c:forEach items="${supplier.OBResultsInfo}" var="product" varStatus="va">
					<c:if test="${product.supplierId == supplier.supplierId}">
					<tr>
						<td>${va.index+1 }</td>
				  		<td>${product.obProduct.name }</td>
				  		<td>0</td>
						<td>
							<fmt:formatNumber value='${product.myOfferMoney }' pattern='#,##,###.00'/>
						</td>
				  		<td></td>
				  		<td>0</td>
				  	</tr>
					</c:if>
				</c:forEach>
			</c:if>
		</table>
	</c:forEach>
	
	<div align="center">
		<span><font size="3">供应商确认中标比例为<b>${countProportion }%</b>，未中标比例为<b>${100 - countProportion }%</b>.</font></span>
	</div>
	
	</div>
</body>
</html>