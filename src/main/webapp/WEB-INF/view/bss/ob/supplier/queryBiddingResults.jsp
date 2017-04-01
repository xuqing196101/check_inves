<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<%@ include file ="/WEB-INF/view/common/webupload.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
	<title>供应商报价页面</title>
</head>
<body>
<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)">竞价结果查询</a></li>
		   </ul>
        <div class="clear"></div>
      </div>
    </div>
    <!-- 修改订列表开始-->
   <div class="container container_box">
   <div class="mt10">
	    <button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
   </div>
   <div>
   <div>
    <h2 class="count_flow"><i>1</i>竞价基本信息</h2>
		<%@ include file="/WEB-INF/view/bss/ob/supplier/biddingInfoCommon.jsp" %>
  </div>
  <div>
	  <h2 class="count_flow"><i>2</i>产品信息</h2>
	   <ul class="ul_list">
		<div class="content table_box">
	    	<table class="table table-bordered table-condensed table-hover table-striped">
			<thead>
			<tr>
			  <th class="w30 info">序号</th>
			  <th class="info">定型产品名称</th>
			  <th class="info">限价（元）</th>
			  <th class="info">采购数量</th>
			  <th class="info">总价（元）</th>
			  <th class="info">备注信息</th>
			</tr>
			</thead>
			<tr>
			  <td class="tc"></td>
			  <td class="tc" colspan="3">合计</td>
			  <td class="tc">
			  	<c:if test="${ totalCountPriceBigDecimal != '00' }">
			  		${ totalCountPriceBigDecimal }
			  	</c:if>
			  </td>
			  <td class="tc"></td>
			</tr>
			<c:forEach items="${ oBProductInfoList }" var="productInfo" varStatus="vs">
				<tr>
				  <td class="tc">${ vs.index + 1 }</td>
				  <td class="tc" id="t_${productInfo.id}" onmousemove="showPrompt('${ productInfo.obProduct.id }', 't_${productInfo.id}')">${ productInfo.obProduct.name } </td>
				  <td class="tc">${ productInfo.limitedPrice }</td>
				  <td class="tc">${ productInfo.purchaseCount }</td>
				  <td class="tc">${ productInfo.totalMoneyStr }</td>
				  <td class="tc">${ productInfo.remark }</td>
				</tr>
			</c:forEach>
		</table>
	  </div>
	  </ul>
	 </div>
  
	 <h2 class="count_flow"><i>3</i>供应商信息</h2>
	   <%@ include file ="/WEB-INF/view/bss/ob/supplier/supplierCommon.jsp" %>
  </div>
 </div>
</body>
</html>