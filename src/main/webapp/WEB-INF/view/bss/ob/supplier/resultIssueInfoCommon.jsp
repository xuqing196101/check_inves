<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="tab-content padding-left-20 padding-right-20">
		<h2 class="count_flow">
			<span class="font_sblck">名次：</span>  
		    <span class="margin-left-10 font_sblck">第${confirmResult.ranking }名</span>
	    	<span class="font_sblck">成交比例 ：</span>
	    	<span class="margin-left-10 font_sblck">${confirmResult.proportion }%</span>
	    </h2>
		<table class="table table-bordered table-condensed table-hover">
			<thead>
			<tr>
			  <th class="w30 info" width="10%">序号</th>
			  <th class="tc" width="20%">产品名称</th>
			  <th class="tc">采购数量</th>
			  <th class="tc">自报单价（元）</th>
			  <th class="tc">成交单价（元）</th>
			  <th class="tc">成交总价（万元）</th>
			</tr>
			</thead>
			<tr>
			  <td class="tc"></td>
			  <td class="tc" colspan="4">合计</td>
			  <td class="tc"><b>${ confirmFirstTotalFigureStr }</b></td>
			</tr>
			<c:forEach items="${confirmResult.obResultSubtabulation }" var="resultSub" varStatus="vs">
			<tr>
			  <td class="tc" title="theProductId2" width="5%">
			  	 ${vs.index + 1 }
			  </td>
			  <td class="tc" id="t_${resultSub.id}" onmouseout="closePrompt()" onmouseover="showPrompt('${ resultSub.product.id }', 't_${resultSub.id}')">${resultSub.product.name }</td>
			  <td class="tc" title="theProductCount2">
			 	 ${resultSub.resultNumber}
			  </td>
			  <td class="tc">${resultSub.myOfferMoney }</td>
			  <td class="tc">${resultSub.dealMoney}</td>
			  <td class="tc">${resultSub.totalMoney }</td>
			</tr>
			</c:forEach>
		</table>
</div>