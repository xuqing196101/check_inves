<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<ul class="ul_list">
	<div class="content table_box">
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="w30 info"><input alt="" type="checkbox"></th>
		  <th class="info">序号</th>
		  <th class="info">定型产品名称</th>
		  <th class="info">限价（元）</th>
		  <th class="info">采购数量</th>
		  <th class="info">总价（万元）</th>
		  <th class="info">备注信息</th>
		</tr>
		</thead>
		<tr>
		  <td class="tc"><input type="checkbox" alt=""></td>
		  <td class="tc"></td>
		  <td class="tc" colspan="3">合计</td>
		  <td class="tc">
		  	<c:if test="${ totalCountPriceBigDecimal != '00' }">
		  		${ totalCountPriceBigDecimal}
		  	</c:if>
		  </td>
		  <td class="tc"></td>
		</tr>
		<c:forEach items="${ oBProductInfoList }" var="productInfo" varStatus="vs">
			<tr>
			  <td class="tc"><input type="checkbox" alt=""></td>
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