<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<script type="text/javascript">
	function showQuoteHistory(data){
	    var projectId="${projectId}";
	    var supplierId = "${supplierId}";
	    location.href="${pageContext.request.contextPath}/adPackageExpert/supplierQuote.html?timestamp="+data.value+"&projectId="+projectId+"&supplierId="+supplierId;
	}
</script>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<title>标书管理</title>
</head>
<body>
    <!-- 项目戳开始 -->
       <c:if test="${purchaserType eq 'JZXTP' || dd.code eq 'DYLY' }">
            <div class="col-md-12 tc">
			  	<span >报价历史查看：</span>
					  	<select onchange="showQuoteHistory(this)">
					  	    <c:if test="${empty listDate }">
					  	    <option value=''>暂无报价历史</option>
					  	    </c:if>
					  	    <c:forEach items="${listDate }" var="ld" varStatus="vs">
					  	    	<c:if test="${timestamp ne null and timestamp ne ''}">
						  	    	<option <c:if test="${timestamp eq ld}">selected</c:if> value='<fmt:formatDate value="${ld}" pattern="YYYY-MM-dd HH:mm:ss"/>'>第${vs.index+1 }次报价</option>
					  	    	</c:if>
					  	    	<c:if test="${timestamp eq null or timestamp eq ''}">
						  	    	<option <c:if test="${vs.index+1 eq length}">selected</c:if> value='<fmt:formatDate value="${ld}" pattern="YYYY-MM-dd HH:mm:ss"/>'>第${vs.index+1 }次报价</option>
					  	    	</c:if>
					  	    </c:forEach>
					  	</select>
			 </div>
	    </c:if>
  <!--详情开始-->
    <div class="row magazine-page">
      <div class="col-md-12 tab-v2 job-content">
        <div class="padding-top-10">
          <ul class="nav nav-tabs bgdd">
          <c:forEach items="${listPackage }"  var="obj" varStatus="vs" >
		     <c:if test="${vs.index==0 }">
		     	<li class="active">
		     		<a aria-expanded="true" href="#tab-${vs.index+1 }" data-toggle="tab" title="${obj.name }" >
		     			<c:choose>
		     				<c:when test="${fn:length(obj.name)>3}">${fn:substring(obj.name, 0, 3)}...</c:when>
		     				<c:otherwise>${obj.name}</c:otherwise>
		     			</c:choose>
		     		</a>
		     	</li>
		     </c:if>
		     <c:if test="${vs.index>0 }">
		     	<li class="">
		     		<a aria-expanded="true" href="#tab-${vs.index+1 }" data-toggle="tab" title="${obj.name }" >
		     			<c:choose>
		     				<c:when test="${fn:length(obj.name)>3}">${fn:substring(obj.name, 0, 3)}...</c:when>
		     				<c:otherwise>${obj.name}</c:otherwise>
		     			</c:choose>
		     		</a>
		    	 </li>
		      </c:if>
		  </c:forEach>
          </ul>
            <div class="tab-content">
             <c:forEach items="${listQuote }" var="listQuote1" varStatus="vs" >
             <c:set var="total" value="0"></c:set>
                  		<c:if test="${vs.index==0 }">
	                  		<div class="tab-pane fade active in height-450" id="tab-${vs.index+1 }">
									<table id="tb${vs.index }" class="table table-bordered table-condensed mt5">
							        <thead>
							        <tr>
							          <th class="info w50">序号</th>
							          <th class="info">物资名称</th>
							          <th class="info">规格型号</th>
							          <th class="info">质量技术标准</th>
							          <th class="info">计量单位</th>
							          <th class="info">采购数量</th>
							          <th class="info">单价（元）</th>
							          <th class="info">小计</th>
							          <th class="info">交货时间</th>
							          <th class="info">备注</th>
							        </tr>
							        </thead>
							        
							          <c:forEach items="${listQuote1}" var="lq" varStatus="vs">
							            <tr class="hand">
							              <td class="tc w50">${lq.advancedDetail.serialNumber }</td>
							              <td class="tc">${lq.advancedDetail.goodsName }</td>
							              <td class="tc">${lq.advancedDetail.stand }</td>
							              <td class="tc">${lq.advancedDetail.qualitStand }</td>
							              <td class="tc">${lq.advancedDetail.item }</td>
							              <td class="tc">${lq.advancedDetail.purchaseCount }</td>
							              <td class="tc">${lq.quotePrice }</td>
							              <td class="tc">${lq.total }</td>
							               <c:set var="total" value="${total+lq.total }"></c:set>
							              <td class="tc"><fmt:formatDate value="${lq.deliveryTime }" pattern="YYYY-MM-dd"/></td>
							              <td class="tc">${lq.remark }</td>
							            </tr>
							         </c:forEach>  
							         <tr>
							         	<td class="tr" colspan="2"><b>总金额(元):</b></td>
							         	<td class="tl" colspan="8">${total }</td>
							         </tr>
							      </table>
		                    </div>
                  		</c:if>
                  		<c:if test="${vs.index!=0 }">
                  		  <c:set var="total2" value="0"></c:set>
                  		  <div class="tab-pane fade in height-450" id="tab-${vs.index+1 }">
									<table id="tb${vs.index }" class="table table-bordered table-condensed mt5">
							        <thead>
							        <tr>
							          <th class="info w50">序号</th>
							          <th class="info">物资名称</th>
							          <th class="info">规格型号</th>
							          <th class="info">质量技术标准</th>
							          <th class="info">计量单位</th>
							          <th class="info">采购数量</th>
							          <th class="info">报价金额</th>
							          <th class="info">报价小计</th>
							          <th class="info">交货时间</th>
							          <th class="info">备注</th>
							        </tr>
							        </thead>
							       
							           <c:forEach items="${listQuote1}" var="lq" varStatus="vs">
							            <tr class="hand">
							              <td class="tc w50">${lq.advancedDetail.serialNumber }</td>
							              <td class="tc">${lq.advancedDetail.goodsName }</td>
							              <td class="tc">${lq.advancedDetail.stand }</td>
							              <td class="tc">${lq.advancedDetail.qualitStand }</td>
							              <td class="tc">${lq.advancedDetail.item }</td>
							              <td class="tc">${lq.advancedDetail.purchaseCount }</td>
							              <td class="tc">${lq.quotePrice }</td>
							              <td class="tc">${lq.total }</td>
							              <c:set var="total2" value="${total2+lq.total }"></c:set>
							              <td class="tc"><fmt:formatDate value="${lq.deliveryTime }" pattern="YYYY-MM-dd"/></td>
							              <td class="tc">${lq.remark }</td>
							            </tr>
							         </c:forEach>  
							         <tr>
							         	<td class="tr" colspan="2"><b>总金额(元):</b></td>
							         	<td class="tl" colspan="8">${total2}</td>
							         </tr>
							      </table>
	                 	  </div>
	                 </c:if>
		     </c:forEach>
              </div>
          </div>
      </div>
    </div>
   <div class=" tc col-md-12 mt5">
       <button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
	</div>
</body>
</html>
