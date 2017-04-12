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
			  <th class="info">自报单价（元）</th>
			  <th class="info">总价（万元）</th>
			  <th class="info" width="25%">备注信息</th>
			</tr>
			</thead>
			<tr>
              <td></td>
			  <td class="tc" colspan="4">合计</td>
			  <td class="tc"><b>${ totalCountPriceBigDecimalSecond }</b></td>
			  <td class="tc"></td>
			</tr>
			<c:forEach items="${ oBResultsInfoSecond }" var="oBResultsInfo" varStatus="vs">
				<tr>
				  <td class="tc">${ vs.index + 1 }</td>
				  <td class="tc" id="t_${oBResultsInfo.id}${ vs.index + 1 }" onmouseout="closePrompt()" onmouseover="showPrompt('${ oBResultsInfo.obProduct.id }', 't_${oBResultsInfo.id}${ vs.index + 1 }')">${ oBResultsInfo.obProduct.name }</td>
				  <td class="tc">${ oBResultsInfo.limitPrice }</td>
				  <td class="tc">${ oBResultsInfo.resultsNumber }</td>
				  <td class="tc">${ oBResultsInfo.myOfferMoney }</td>
				  <td class="tc">${ oBResultsInfo.dealMoney}</td>
				  <td class="tc">${ oBResultsInfo.remark }</td>
				</tr>
			</c:forEach>
		</table>
	  </div>
  </ul>