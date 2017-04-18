<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="tab-content padding-left-20 padding-right-20">
	    	<table class="table table-bordered">
		<thead>
		<tr>
		  <th class="info">序号</th>
		  <th class="info">定型产品名称</th>
		  <th class="info">限价（元）</th>
		  <th class="info">采购数量</th>
		  <th class="info">总价（万元）</th>
		  <th class="info" width="25%">备注信息</th>
		</tr>
		</thead>
		<tr>
		  <td class="tc"></td>
		  <td class="tc" colspan="3">合计</td>
		  <td class="tc">
		  	<b>
		  	<c:if test="${ totalCountPriceBigDecimal != '0.0000' }">
		  		${ totalCountPriceBigDecimal}
		  	</b>
		  	</c:if>
		  </td>
			<td></td>
		</tr>
		<c:forEach items="${ oBProductInfoList }" var="productInfo" varStatus="vs">
			<tr>
			  <td class="tc">${ vs.index + 1 }</td>
			  <td class="tc" id="t_${productInfo.id}" onmouseout="closePrompt()" onmouseover="showPrompt('${ productInfo.obProduct.id }', 't_${productInfo.id}')" title="${ productInfo.obProduct.name }">
				<c:if test="${fn:length(productInfo.obProduct.name) > 30 }">${fn:substring(productInfo.obProduct.name, 0, 30)}...</c:if>
				<c:if test="${fn:length(productInfo.obProduct.name) <= 30 }">${productInfo.obProduct.name }</c:if>			  
			  </td>
			  <td class="tc">${ productInfo.limitedPrice }</td>
			  <td class="tc">${ productInfo.purchaseCount }</td>
			  <td class="tc">${ productInfo.totalMoney}</td>
			  <td class="tc" title="${ productInfo.remark }">
			  	<c:if test="${fn:length(productInfo.remark) > 30 }">${fn:substring(productInfo.remark, 0, 30)}...</c:if>
				<c:if test="${fn:length(productInfo.remark) <= 30 }">${productInfo.remark }</c:if>			  
			  </td>
			</tr>
		</c:forEach>
	</table>
  </div>
