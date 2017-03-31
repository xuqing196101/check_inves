<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<script type="text/javascript">
  //关闭
	function closePrompts(){
	layer.closeAll('tips');
	}
	  // 显示
    function showPrompts(id,selectID){
   		  if(selectID){
   		  $.ajax({
				url: "${pageContext.request.contextPath }/product/productType.do",
				type: "POST",
				async: false,
				data: {productId:selectID},
				success: function(data) {
				if(data){
       	  layer.tips("产品规格型号："+data.standardModel+"<br/>"+"质量技术标准："+data.qualityTechnicalStandard, 
       	    '#'+id, {tips: [2, '#78BA32'],time:-1});
				}else{
				 inder=layer.tips("", 
       	    '#'+id, {tips: [2, '#78BA32']});
				}
		      },error:function(){
		       layer.tips("错误！", 
       	    '#'+id, {tips: [2, '#78BA32']});
		      }
           });
           }
       	}
    function format (num,id) {
    	if(num){
     		var content=  (num.toFixed(2) + '').replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');
     		$("#"+id).html(content);
    	}
    }
</script>
<div class="ul_list" onmouseover="closePrompts()">
<c:if test="${size == 0 }">
	<h2 class="count_flow">无报价信息</h2>
</c:if>
	 <c:forEach items="${listres}" var="supplier" varStatus="pi">
	 <ul class="ul_list">
	  <li class="col-md-3 col-sm-6 col-xs-12">
	  <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
	  <span class="fl block">供应商名称：${supplier.supplier.supplierName}</span>
	   </div>
	  </li>
	   <li class="col-md-3 col-sm-6 col-xs-12">
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
	  <span class="fl block">排名：</span><span>第${supplier.ranking}名</span>
	  </div>
	  </li>
	  <li class="col-md-3 col-sm-6 col-xs-12">
	    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
	  		<span class="fl block">成交比例：</span><span>
	  		<c:if test="${supplier.status == -1 || supplier.status == 0}">0%</c:if>
	  		<c:if test="${supplier.status == 1 || supplier.status == 2 }">${supplier.proportion}%</c:if></span>
	  	</div>
	  </li>
	  <li class="col-md-3 col-sm-6 col-xs-12">
  			<div
  				class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
  				<span class="fl block">状态：</span>
  				<span>
					<c:if test="${supplier.status == -1}">未确认，已放弃</c:if>
					<c:if test="${supplier.status == 0}">未接受，已放弃</c:if>
					<c:if test="${supplier.status == 1 || supplier.status == 2 }">已接受</c:if>
				</span>
  			</div>
	   </li>

    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="w50 info">序号</th>
		  <th class="info" width="30%">产品名称</th>
		  <th class="info">数量</th>
		  <th class="info">自报单价（元）</th>
		  <th class="info">成交单价（元）</th>
		  <th class="info">成交总价（元）</th>
		</tr>
		</thead>
		<%-- <c:set value="0" var="sum" />
		<tr>
		  <td class="tc"></td>
		  <td class="tc" colspan="4">合计</td>
		  <c:if test="${supplier.status!=-1}">
		   <c:set value="${sum + supplier.resultCount*supplier.offerPrice}" var="sum" />
		  <td class="tc" id="sum2" onload="format('${sum}','sum2')">${sum}</td>
		  </c:if> 
		   <c:if test="${supplier.status==-1}"> 
		    <c:forEach items="${supplier.OBResultsInfo }" var="bidproduct" varStatus="pi">
		   <c:set value="${sum + bidproduct.dealMoney}" var="sum"  />
		  <td class="tc" id="sum1" format('${sum}','sum1')">${sum}</td>
		  </c:forEach>
		  </c:if> 
		</tr>
		 <c:if test="${supplier.status==-1}">
		 <c:forEach items="${supplier.OBResultsInfo }" var="bidproduct" varStatus="pi">
		<tr>
		  <td class="tc">${pi.index + 1 }</td>
		  <%  String item1 =UUID.randomUUID().toString().toUpperCase().replace("-", ""); %>
		  <td class="tc" id="${supplier.id}${bidproduct.productId}<%=item1 %>"  onmouseover="showPrompts('${supplier.id}${bidproduct.productId}<%=item1 %>', '${bidproduct.productId}')">${bidproduct.remark }</td>
		  <td class="tc">${bidproduct.resultsNumber }</td>
		  <td class="tc">${bidproduct.myOfferMoney }</td>
		  <td class="tc">${bidproduct.myOfferMoney }</td>
		  <td class="tc" ><span id="dealMoney" onclick="format('${bidproduct.dealMoney}','dealMoney')">${bidproduct.dealMoney}</span></td>
		</tr>
		  </c:forEach>
		</c:if> 
		 <c:if test="${supplier.status!=-1}">
		<tr>
		 <c:forEach items="${supplier.productInfo }" var="bidproduct" varStatus="pi">
		  <td class="tc">${pi.index + 1 }</td>
		   <%  String item2 =UUID.randomUUID().toString().toUpperCase().replace("-", ""); %>
		  <td class="tc" id="${supplier.id}${bidproduct.productId}<%=item2 %>" onmouseover="showPrompts('${supplier.id}${bidproduct.productId}<%=item2 %>', '${bidproduct.productId}')">${bidproduct.remark }</td>
		  </c:forEach>
		  <td class="tc">${supplier.resultCount }</td>
		  <td class="tc">${supplier.offerPrice }</td>
		  <td class="tc">${bidproduct.dealMoney }</td>
		  <td class="tc" id="offerPrice" onclick="format('${supplier.resultCount*supplier.offerPrice}','offerPrice')">${supplier.resultCount*supplier.offerPrice}</td>
		</tr>
		</c:if>  --%>
		
		<!-- 成交的 -->
		<c:if test="${supplier.status == 1 || supplier.status == 2}">
		<c:forEach items="${listres}" var="product">
			<c:if test="${product.supplierId == supplier.supplierId}">
				<c:set value="${ total + product.dealMoney * product.resultNumber }" var = "total" ></c:set>
			</c:if>
		</c:forEach>
			<tr>
			  <td class="tc"></td>
			  <td class="tc" colspan="4">合计</td>
			  <td class="tc">
			  	<fmt:formatNumber value='${total }' pattern='#,##,###.00'/>
			  </td>
			</tr>
			<c:forEach items="${listres}" var="product" varStatus="va">
				<c:if test="${product.supplierId == supplier.supplierId}">
					<tr>
						<td class="tc">${va.index+1 }</td>
			  			<td class="tc">${product.product.name }</td>
			  			<td class="tc">${product.resultNumber }</td>
						<td class="tc">${product.myOfferMoney }</td>
			  			<td class="tc">${product.dealMoney }</td>
			  			<td class="tc">
			  			<fmt:formatNumber value='${product.dealMoney * product.resultNumber }' pattern='#,##,###.00'/>
			  			</td>
			  		</tr>
				</c:if>
			</c:forEach>
		</c:if>
		
		<!-- 放弃的 -->
		<c:if test="${supplier.status == -1}">
			<tr>
			  <td class="tc"></td>
			  <td class="tc" colspan="4">合计</td>
			  <td class="tc"></td>
			</tr>
			<c:forEach items="${listres}" var="product" varStatus="va">
				<c:if test="${product.supplierId == supplier.supplierId}">
				<tr>
					<td class="tc">${va.index+1 }</td>
			  		<td class="tc">${product.product.name }</td>
			  		<td class="tc"></td>
					<td class="tc"></td>
			  		<td class="tc"></td>
			  		<td class="tc"></td>
			  	</tr>
				</c:if>
			</c:forEach>
		</c:if>
		
		<!--  -->
		<c:if test="${supplier.status == 0}">
			<tr>
			  <td class="tc"></td>
			  <td class="tc" colspan="4">合计</td>
			  <td class="tc"></td>
			</tr>
			<c:forEach items="${listres}" var="product" varStatus="va">
				<c:if test="${product.supplierId == supplier.supplierId}">
				<tr>
					<td class="tc">${va.index+1 }</td>
			  		<td class="tc">${product.product.name }</td>
			  		<td class="tc"></td>
					<td class="tc">${product.myOfferMoney }</td>
			  		<td class="tc"></td>
			  		<td class="tc"></td>
			  	</tr>
				</c:if>
			</c:forEach>
		</c:if>
	</table>
	</ul>
		</c:forEach>
  </div>