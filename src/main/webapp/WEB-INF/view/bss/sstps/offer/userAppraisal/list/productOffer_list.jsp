<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
    <%@ include file="../../../../../common.jsp"%>
    
    <title>产 品 审 价 详 细 情 况</title>
   
<script type="text/javascript">
function onStep(){
	var proId = $("#proId").val();
	window.location.href="${pageContext.request.contextPath}/comCostDis/userGetAll.do?productId="+proId;
}

function cancel(){
	window.location.href="${pageContext.request.contextPath}/offer/userAppraisalList.html";
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
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">审价人员审价</a></li><li><a href="javascript:void(0)">产 品 审 价 详 细 情 况</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
   
   
   <div class="container">
	 	<div class="headline-v2">
	  		 <h2>产 品 审 价 详 细 情 况</h2>
	 	</div>
   </div>
   
   <form action="${pageContext.request.contextPath}/auditSummary/userUpdate.html?productId=${proId }" method="post" enctype="multipart/form-data">
   
   <input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
   <input type="hidden" id="apid" name="id" value="${ap.id }" />
   <div class="container margin-top-5">
	 	<div class="container padding-left-25 padding-right-25">
			<table class="table table-bordered table-condensed">
				<tr>
					<th class="info">产品名称</th>
					<td class="w500">${ap.contractProduct.name }</td>
					<th class="info w100" rowspan="4">审核结果</th>
					<th class="info">企业报价</th>
					<td ><input type="text" name="companyPrice"  class="w300" value="${ap.companyPrice }"/></td>
				</tr>
				<tr>
					<th class="info">生产单位</th>
					<td >${ap.produceUnit }</td>
					<th class="info">审核意见</th>
					<td ><input type="text" name="auditOpinion"  class="w300" value="${ap.auditOpinion }"/></td>
				</tr>
				<tr>
					<th class="info">订货数量</th>
					<td >${ap.orderAcount }</td>
					<th class="info">单位核减</th>
					<td ><input type="text" name="unitSubtract"  class="w300" value="${ap.unitSubtract }"/></td>
				</tr>
				<tr>
					<th class="info">计量单位</th>
					<td >${ap.measuringUnit }</td>
					<th class="info">总量核减</th>
					<td ><input type="text" name="acountSubtract"  class="w300" value="${ap.acountSubtract}"/></td>
				</tr>
				<tr>
					<th class="info">审核人员</th>
					<td colspan="4" ><input type="text" name="auditUser"   value="${ap.auditUser }"/></td>
				</tr>
			</table>
		</div>
	</div>
	
	<div class="container margin-top-5">
	 	<div class="container padding-left-25 padding-right-25">
			<table id="table1" class="table table-bordered table-condensed">
					<tr>
						<th class="info">序号</th>
						<th class="info" colspan="2">项目名称</th>
						<th class="info">单台报价</th>
						<th class="info">审核结果</th>
						<th class="info">审核差额</th>
						<th class="info">审减率</th>
						<th class="info">备注</th>
					</tr>
				<c:forEach items="${list}" var="cc" varStatus="vs">
					<tr>
						<td class="tc w50">
							<input type="hidden" name="plcc[${(vs.index)}].id" value="${cc.id }" />${vs.index+1 }
							<input type="hidden" name="plcc[${(vs.index)}].status" value="${cc.status }" />
						</td>
						<td class="tc w80">${cc.projectName }</td>
						<td class="tc w100">${cc.secondProject }</td>
						<td class="tc w80">${cc.singleOffer }</td>
						<td class="tc w80"><input type="text" class="m0 p0  border0 w80 tr" name="plcc[${(vs.index)}].additResult" value="${cc.additResult}" /></td>
						<td class="tc w80"><input type="text" class="m0 p0  border0 w80 tr" name="plcc[${(vs.index)}].difference" value="${cc.difference }" /></td>
						<td class="tc w80"><input type="text" class="m0 p0  border0 w80 tr" name="plcc[${(vs.index)}].reduce" value="${cc.reduce }" /></td>
						<td class="tc w200"> ${cc.remark }</td>
					</tr>
				</c:forEach>
			</table>
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
