<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="ul_list">
	 <c:forEach items="${selectInfoByPID}" var="supplier" varStatus="pi">
	 <ul class="ul_list">
	  <li class="col-md-3 col-sm-6 col-xs-12">
	  <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
	  <span class="fl block">供应商名称：${supplier.remark}</span>
	   </div>
	  </li>
	   <li class="col-md-3 col-sm-6 col-xs-12">
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
	  <span class="fl block">排名：</span><span>第${supplier.ranking}名</span>
	  </div>
	  </li>
	  <c:if test="${supplier.status==-1}">
	   <li class="col-md-3 col-sm-6 col-xs-12">
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
	  <span class="fl block">状态：</span>
	  <span>未中标</span>
	  </div>
	  </li>
	  </c:if>
	   <c:if test="${supplier.status!=-1}">
	    <li class="col-md-3 col-sm-6 col-xs-12">
	    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
	  <span class="fl block">成交比例：</span><span>${supplier.proportion}%</span>
	  </div>
	  </li>
	  </c:if>
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="w30 info">序号</th>
		  <th class="info" width="30%">产品名称</th>
		  <th class="info">数量</th>
		  <th class="info">自报单价（元）</th>
		  <th class="info">成交总价（元）</th>
		</tr>
		</thead>
		<c:set value="0" var="sum" />
		<tr>
		  <td class="tc"></td>
		  <td class="tc" colspan="3">合计</td>
		   <c:set value="${sum + supplier.resultCount*supplier.offerPrice}" var="sum" />
		  <td class="tc">${sum}</td>
		</tr>
		<tr>
		 <c:forEach items="${plist }" var="bidproduct" varStatus="pi">
		  <td class="tc">${pi.index + 1 }</td>
		  <td class="tc">${bidproduct.remark }</td>
		  </c:forEach>
		  <td class="tc">${supplier.resultCount }</td>
		  <td class="tc">${supplier.offerPrice }</td>
		  <td class="tc">${supplier.resultCount*supplier.offerPrice}</td>
		</tr>
	</table>
	</ul>
		</c:forEach>
  </div>