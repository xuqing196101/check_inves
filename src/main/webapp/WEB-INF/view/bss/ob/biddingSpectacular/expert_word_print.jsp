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
String fileName=request.getAttribute("projectName").toString();
String UserAgent = request.getHeader("USER-AGENT").toLowerCase();  
      String tem="";
        if (UserAgent != null) {  
            if (UserAgent.indexOf("msie") >= 0)  
                 tem="IE";  
            if (UserAgent.indexOf("firefox") >= 0)  
                tem= "FF";  
            if (UserAgent.indexOf("safari") >= 0)  
                tem= "SF";  
        }
        if ("FF".equals(tem)) {  
            // 针对火狐浏览器处理方式不一样了  
            fileName = new String(fileName.getBytes("UTF-8"),"iso-8859-1") + ".doc";  
        }else{
        fileName = URLEncoder.encode(fileName, "UTF-8")+ ".doc";
        }    
//对中文文件名编码 
response.setHeader("Content-disposition", "attachment; filename=" + fileName);     

%>
<style type="text/css">
table{
text-align:center;
table-layout:fixed;
empty-cells:show;
border-collapse: collapse;
margin:0 auto;
}
h1,h2,h3{
margin:0;
padding:0;
}
.table{
border:1px solid #000000;
color:#000000;
}
.table th {
background-repeat:repeat-x;
}
.table td,.table th{
border:1px solid  #000000;
padding:0 1em 0;
}
.table tr.alter{
background-color: #000000;
} 
</style> 
</head>

<body>
<div class="table-a" style="width:85%;margin:auto;">
	<table width="100%"  border="1">
	    <tbody>
	      <tr>
			<td colspan="6" align="center"><br/><b>(${ obProject.name })</b><br/>竞价结果信息表<br/><br/></td>
		  </tr> 
		  <tr>
		    <td><b>竞价项目名称</b></td>
		    <td colspan="2">${ obProject.name }</td>
		    <td><b>竞价项目编号</b></td>
		    <td  colspan="2">${ obProject.projectNumber }</td>
		  </tr>
		    <tr>
		    <td><b>成交供应商数</b></td>
		    <td colspan="2">${ obProject.tradedSupplierCount }</td>
		    <td><b>供应商比例</b></td>
		    <td  colspan="2">
		    <c:if test="${obProject.tradedSupplierCount==1 }">100</c:if>
		    <c:if test="${obProject.tradedSupplierCount==2}">70:30</c:if>
		    <c:if test="${obProject.tradedSupplierCount==3}">50:30:20</c:if>
		    <c:if test="${obProject.tradedSupplierCount==4}">40:30:20:10</c:if>
		    <c:if test="${obProject.tradedSupplierCount==5}">30:25:20:15:10</c:if>
		    <c:if test="${obProject.tradedSupplierCount==6}">25:21:19:15:12:8</c:if>
		    </td>
		  </tr>
		  <tr>
		     <td><b>交货时间</b></td>
		    <td colspan="2"><fmt:formatDate value="${ obProject.deliveryDeadline }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		    <td><b>交货地点</b></td>
		    <td colspan="2">${ obProject.deliveryAddress }</td>
		  </tr>
		  
		  <tr>
		    <td><b>需求部门</b></td>
		    <td colspan="2">${ demandUnit }</td>
		    <td><b>采购机构</b></td>
		    <td colspan="2">${ orgName }</td>
		  </tr>
		  <tr>
		    <td><b>需求部门联系人</b></td>
		    <td colspan="2">${ obProject.contactName }</td>
		    <td><b>采购机构联系人：</b></td>
		    <td colspan="2">${ obProject.orgContactName }</td>
		  </tr>
		  <tr>
		    <td><b>需求部门联系电话</b></td>
		    <td colspan="2">${ obProject.contactTel }</td>
		    <td><b>采购机构联系电话</b></td>
		    <td colspan="2">${ obProject.orgContactTel }</td>
		  </tr>
		  <tr>
		    <td><b>运杂费支付方式</b></td>
		    <td colspan="2">${ transportFees }</td>
		    <c:if test="${ empty obProject.transportFeesPrice }">
		        <td><b>运杂费金额（元）</b></td>
			    <td colspan="2"></td>
		    </c:if>
		    <c:if test="${ !empty obProject.transportFeesPrice }">
			    <td><b>运杂费金额（元）</b></td>
			    <td colspan="2">${obProject.transportFeesPrice}</td>
		    </c:if>
		  </tr>
		  <tr>
		   <td><b>竞价内容</b></td>
		    <td colspan="5" style="height:70px">${obProject.content }</td>
		  </tr>
		</tbody>
	</table>
	 <table width="100%" border="1">
	          <thead>
				<tr>
				  <th align="center" width="3%">序号</th>
				  <th>产品名称</th>
				  <th>限价（元）</th>
				  <th>数量</th>
				  <th>备注</th>
				</tr>
	<c:forEach items="${obProductInfoList}" var="su" varStatus="pi">
				<tr>
				  <td >${pi.index+1 }</td>
				   <td>${su.obProduct.name}</td>
				    <td>${su.limitedPrice}</td>
				     <td>${su.purchaseCount}</td>
				      <td>${su.remark}</td>
				</tr>
	</c:forEach>
	   <tr>
	 <td colspan="5"><span style='color:red'>供应商确认中标占比为：<b>${countProportion }%</b>，未确认中标占比为：<b>${100 - countProportion }%</b>。</span></td>
	   </tr>
			</thead>
      </table>
	
	<c:forEach items="${listres}" var="supplier" varStatus="pi">
		<table width="100%" border="1">
			<thead>
				<tr>
					<td colspan="6">第${supplier.ranking}名    供应商名称：${supplier.supplier.supplierName} 成交占比 ${supplier.proportion}%</td>
			  	</tr>
				<%-- <tr colspan="6">
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
				</tr> --%>
				<tr>
				  <th align="center" width="3%">序号</th>
				  <th>产品名称</th>
				  <th>成交数量</th>
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
				<%-- <tr>
				  <td></td>
				  <td align="center" colspan="4">合计</td>
				  <td>${ total }</td>
				</tr> --%>
				<c:forEach items="${supplier.obResultSubtabulation}" var="product" varStatus="va">
					<c:if test="${product.supplierId == supplier.supplierId}">
						<tr>
							<td>${va.index+1 }</td>
				  			<td>${product.product.name }</td>
				  			<td>${product.resultNumber }</td>
							<td>
							<c:if test="${flag == true }">
								<fmt:formatNumber value='${product.myOfferMoney }' pattern='#,##,###.00'/>
							</c:if>
							<c:if test="${flag == false }">
						<c:forEach items="${supplier.OBResultsInfo}" var="productri" varStatus="va">
							<c:if test="${productri.supplierId == supplier.supplierId && productri.biddingId == '2' && productri.productId == product.productId}">
								<fmt:formatNumber value='${productri.myOfferMoney }' pattern='#,##,###.00'/>
							</c:if>
						</c:forEach>	
							</c:if>
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
				<%-- <tr>
				  <td></td>
				  <td align="center" colspan="4">合计</td>
				  <td>${ total }</td>
				</tr> --%>
				<c:forEach items="${supplier.OBResultsInfo}" var="product" varStatus="va">
					<c:if test="${product.supplierId == supplier.supplierId && product.biddingId == '1'}" >
					<tr>
						<td>${va.index+1 }</td>
				  		<td>${product.obProduct.name }</td>
				  		<td>0</td>
						<td>
							<c:if test="${flag == true }">
								<fmt:formatNumber value='${product.myOfferMoney }' pattern='#,##,###.00'/>
							</c:if>
							<c:if test="${flag == false }">
						<c:forEach items="${supplier.OBResultsInfo}" var="productri" varStatus="va">
							<c:if test="${productri.supplierId == supplier.supplierId && productri.biddingId == '2' && productri.productId == product.productId}">
								<fmt:formatNumber value='${productri.myOfferMoney }' pattern='#,##,###.00'/>
							</c:if>
						</c:forEach>	
							</c:if>
							</td>
				  		<td></td>
				  		<td></td>
				  	</tr>
					</c:if>
				</c:forEach>
			</c:if>
		<!-- 未中标 -->
			<c:if test="${supplier.status == 0}">
				<!-- <tr>
				  <td></td>
				  <td align="center" colspan="4">合计</td>
				  <td></td>
				</tr>-->
				<c:forEach items="${supplier.OBResultsInfo}" var="product" varStatus="va">
					<c:if test="${product.supplierId == supplier.supplierId && product.biddingId == '0'}">
					<tr>
						<td>${va.index+1 }</td>
				  		<td>${product.obProduct.name }</td>
				  		<td>0</td>
						<td>
							<c:if test="${flag == true }">
								<fmt:formatNumber value='${product.myOfferMoney }' pattern='#,##,###.00'/>
							</c:if>
							<c:if test="${flag == false }">
						<c:forEach items="${supplier.OBResultsInfo}" var="productri" varStatus="va">
							<c:if test="${productri.supplierId == supplier.supplierId && productri.biddingId == '2' && productri.productId == product.productId}">
								<fmt:formatNumber value='${productri.myOfferMoney }' pattern='#,##,###.00'/>
							</c:if>
						</c:forEach>	
							</c:if>
							</td>
				  		<td></td>
				  		<td>0</td>
				  	</tr>
					</c:if>
				</c:forEach>
			</c:if>
		</table>
	</c:forEach>
	
	</div>
</body>
</html>