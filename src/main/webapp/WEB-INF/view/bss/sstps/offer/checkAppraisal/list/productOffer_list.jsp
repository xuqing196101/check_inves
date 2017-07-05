<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
    <%@ include file="../../../../../common.jsp"%>
    
    <title>产 品 复 审 详 细 情 况</title>
   
<script type="text/javascript">
function onStep(){
	var proId = $("#proId").val();
	window.location.href="${pageContext.request.contextPath}/comCostDis/userGetAllCheck.do?productId="+proId;
}

function cancel(){
	window.location.href="${pageContext.request.contextPath}/offer/checkList.html";
}

function printz(){
	window.print();
}

jQuery.fn.rowspan = function(colIdx) {
	  return this.each(function(){
	  var that;
	  $('tr', this).each(function(row) {
	    	  $('td:eq('+colIdx+')', this).filter(':visible').each(function(col) {
	    		  if (that!=null && $(this).html() == $(that).html()) {
			    	  rowspan = $(that).attr("rowSpan");
			    	  if (rowspan == undefined) {
			    	  	$(that).attr("rowSpan",1);
			    	  	rowspan = $(that).attr("rowSpan");
			    	  }
			    	  rowspan = Number(rowspan)+1;
			    	  $(that).attr("rowSpan",rowspan);
			    	  $(this).hide(); 
			    	  } else {
			    	  	that = this;
			    	  }
		    	  });
	    	  });
	  });
	  }
$(document).ready(function() {
    $("#table1").rowspan(1);
})
</script>
    
  </head>
  
  <body>

  <!--面包屑导航开始-->
  <div class="margin-top-10 breadcrumbs ">
	  <div class="container">
		  <ul class="breadcrumb margin-left-0">
			  <li>
				  <a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
			  </li>
			  <li>
				  <a href="javascript:void(0);"> 保障作业</a>
			  </li>
			  <li>
				  <a href="javascript:void(0);"> 单一来源审价</a>
			  </li>
			  <li>
				  <a href="javascript:jumppage('${pageContext.request.contextPath}/offer/checkList.html')">审价人员复审</a>
			  </li>
			  <li><a href="javascript:void(0)">产品复审详细情况</a></li>
		  </ul>
		  <div class="clear"></div>
	  </div>
  </div>
   
   
   <div class="container">
	 	<div class="headline-v2">
	  		 <h2>产 品 复 审 详 细 情 况</h2>
	 	</div>
   </div>
   
   <form action="${pageContext.request.contextPath}/auditSummary/userUpdateCheck.html?productId=${proId }" method="post" enctype="multipart/form-data">
   
   <input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
   <input type="hidden" id="apid" name="id" value="${ap.id }" />
   <div class="container margin-top-5">
	 	<div class="container padding-left-25 padding-right-25">
			<table class="table table-bordered table-condensed">
				<tr>
					<th class="info">产品名称</th>
					<td class="tc">${ap.contractProduct.name }</td>
					<th class="info" rowspan="4">审核结果</th>
					<th class="info">企业报价</th>
					<td class="tc">${ap.companyPrice }</td>
					<%-- <th class="info" rowspan="4">复审结果</th>
					<th class="info">企业报价</th>
					<td class="tc"><input type="text" name="checkCompanyPrice"  class="" value="${ap.checkCompanyPrice }"/></td>
					 --%>
				</tr>
				<tr>
					<th class="info">生产单位</th>
					<td class="tc">${ap.produceUnit }</td>
					<th class="info">审核意见</th>
					<td class="tc">${ap.auditOpinion }</td>
					<%-- <th class="info">审核意见</th>
					<td class="tc"><input type="text" name="checkAuditOpinion"  class="" value="${ap.checkAuditOpinion }"/></td> --%>
				</tr>
				<tr>
					<th class="info">订货数量</th>
					<td class="tc">${ap.orderAcount }</td>
					<th class="info">单位核减</th>
					<td class="tc">${ap.unitSubtract }</td>
					<%-- <th class="info">单位核减</th>
					<td class="tc"><input type="text" name="checkUnitSubtract"  class="" value="${ap.checkUnitSubtract }"/></td> --%>
				</tr>
				<tr>
					<th class="info">计量单位</th>
					<td class="tc">${ap.measuringUnit }</td>
					<th class="info">总量核减</th>
					<td class="tc">${ap.acountSubtract}</td>
					<%-- <th class="info">总量核减</th>
					<td class="tc"><input type="text" name="checkAcountSubtract"  class="" value="${ap.checkAcountSubtract}"/></td> --%>
				</tr>
				<tr>
					<th class="info">审核人员</th>
					<td class="tl" colspan="4">${ap.auditUser }</td>
				</tr>
			</table>
		</div>
	</div>
	
	<div class="container margin-top-5">
	 	<div class="container padding-left-25 padding-right-25">
			<table id="table1" class="table table-bordered table-condensed">
					<tr>
						<th class="info">序号</th>
						<th class="info">项目类型</th>
						<th class="info">项目名称</th>
						<th class="info">单台报价</th>
						<th class="info">审核结果</th>
						<th class="info">审核差额</th>
						<th class="info">审减率</th>
						<!-- <th class="info">复审结果</th>
						<th class="info">复审差额</th>
						<th class="info">复审减率</th> -->
						<th class="info">备注</th>
					</tr>
				<c:forEach items="${list}" var="cc" varStatus="vs">
					<tr>
						<td class="tc w50">
							<input type="hidden" name="plcc[${(vs.index)}].id" value="${cc.id }" />${vs.index+1 }
							<input type="hidden" name="plcc[${(vs.index)}].status" value="${cc.status }" />
						</td>
						<td class="tc w80">${cc.projectName }</td>
						<td class="tc w200">${cc.secondProject }</td>
						<td class="tc w80" >${cc.singleOffer }</td>
						<td class="tc w80">${cc.additResult}</td>
						<td class="tc w80">${cc.difference }</td>
						<td class="tc w80">${cc.reduce }</td>
						<%-- <td class="tc w80"><input type="text" class="w80" name="plcc[${(vs.index)}].checkResult" value="${cc.checkResult}" /></td>
						<td class="tc w80"><input type="text" class="w80" name="plcc[${(vs.index)}].checkDifference" value="${cc.checkDifference }" /></td>
						<td class="tc w80"><input type="text" class="w80" name="plcc[${(vs.index)}].checkReduce" value="${cc.checkReduce }" /></td> --%>
						<td class="tc w80">${cc.remark }</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	
   
   	<div  class="col-md-12">
		<div class="mt40 tc mb50">
		    <button class="btn" type="button" onclick="onStep()">上一步</button>
		    <button class="btn" type="button" onclick="printz()">打印审价结果确认书</button>
		    <button class="btn" type="submit" >提交</button>
		    <button class="btn btn-windows cancel" type="button" onclick="cancel()">关闭</button>
		 </div>
	</div>
  	
  	 </form>
  	
  </body>
</html>
