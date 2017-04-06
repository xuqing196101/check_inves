<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<ul class="ul_list">
	<div class="content table_box">
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="info">序号</th>
		  <th class="info" width="20%">定型产品名称</th>
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
		  	<c:if test="${ totalCountPriceBigDecimal != '00' }">
		  		${ totalCountPriceBigDecimal}
		  	</b>
		  	</c:if>
		  </td>
			<td></td>
		</tr>
		<c:forEach items="${ oBProductInfoList }" var="productInfo" varStatus="vs">
			<tr>
			  <td class="tc">${ vs.index + 1 }</td>
			  <td class="tc" id="t_${productInfo.id}" onmouseout="closePrompt()" onmouseover="showPrompt('${ productInfo.obProduct.id }', 't_${productInfo.id}')">${ productInfo.obProduct.name } </td>
			  <td class="tc">${ productInfo.limitedPrice }</td>
			  <td class="tc">${ productInfo.purchaseCount }</td>
			  <td class="tc">${ productInfo.totalMoney}</td>
			  <td class="tc">${ productInfo.remark }</td>
			</tr>
		</c:forEach>
	</table>
  </div>
 </ul>