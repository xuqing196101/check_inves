<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
  <%@ include file="../../../../../common.jsp"%>
    
    <title>综合费用汇总分配计算明细</title>
	
	
<script type="text/javascript">

function onStep(){
	var proId = $("#proId").val();
	window.location.href="${pageContext.request.contextPath}/productQuota/userGetAllCheck.do?productId="+proId;
}

</script>

  </head>
  
  <body>
  
  	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)">首页</a></li><li><a href="javascript:void(0)">审价人员复审</a></li><li><a href="javascript:void(0)">综合费用汇总分配计算明细</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
  
  <div class="container">
	 	<div class="headline-v2">
	  		 <h2>综合费用汇总分配计算明细</h2>
	 	</div>
	 	
   </div>
	
	<form id="formID" name="form1" action="${pageContext.request.contextPath}/comCostDis/userUpdateCheck.html?productId=${proId }" method="post" enctype="multipart/form-data">
	
	<div class="container margin-top-5">
	 	<div class="container padding-left-25 padding-right-25">
			<table class="table table-bordered table-condensed">
				<thead>
					<tr>
						<th rowspan="2" class="info">序号</th>
						<th rowspan="2" class="info">项目名称</th>
						<th colspan="2" class="info">报价前2年</th>
						<th colspan="2" class="info">报价前1年</th>
						<th colspan="2" class="info">报价当年</th>
						<th colspan="2" class="info">审核核准数</th>
						<!-- <th colspan="2" class="info">复审核准数</th> -->
						<th rowspan="2" class="info">备   注</th>
					</tr>
					<tr>
						<th class="info">发生额</th>
						<th class="info">费用率(元/小时)</th>
						<th class="info">发生额</th>
						<th class="info">费用率(元/小时)</th>
						<th class="info">发生额</th>
						<th class="info">费用率(元/小时)</th>
						<th class="info">发生额</th>
						<th class="info">费用率(元/小时)</th>
						<!-- <th class="info">发生额</th>
						<th class="info">费用率(元/小时)</th> -->
					</tr>
				</thead>
				
				
				<input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
				<tbody>
				<c:forEach items="${list}" var="ccd" varStatus="vs">
				<c:if test="${ccd.status==0}">
					<tr>
						<td class="tc" class="w30">
							<input type="hidden" name="plccd[${(vs.index)}].id" value="${ccd.id }"/>
							<input type="hidden" name="plccd[${(vs.index)}].status" value="${ccd.status }"  />
							${vs.index+1 }
						</td>
						<td class="tl">${ccd.projectName }</td>
						<td class="tr">${ccd.tyaAmount }</td>
						<td class="tr">${ccd.tyaFee }</td>
						<td class="tr">${ccd.oyaAmout }</td>
						<td class="tr">${ccd.oyaFee }</td>
						<td class="tr">${ccd.newAmount }</td>
						<td class="tr">${ccd.newFee }</td>
						<td class="tr">${ccd.subtractWentDutch }</td>
						<td class="tr">${ccd.subtractFee }</td>
						<%-- <td class="tc"><input type="text" name="plccd[${(vs.index)}].checkWentDutch" value="${ccd.checkWentDutch }" class="border0 mb0 w80"/></td>
						<td class="tc"><input type="text" name="plccd[${(vs.index)}].checkFee" value="${ccd.checkFee }" class="border0 mb0 w80"/></td> --%>
						<td class="tl">${ccd.remark }</td>
					</tr>
					</c:if>
				</c:forEach>
				</tbody>
				
			</table>
		</div>
	</div>
	
		<div class="container margin-top-5">
	 	<div class="container padding-left-25 padding-right-25">
			<table class="table table-bordered table-condensed">
				<thead>
					<tr>
						<th class="info w50">序号</th>
						<th class="info">项目名称</th>
						<th class="info">报价前2年</th>
						<th class="info">报价前1年</th>
						<th class="info">报价当年</th>
						<th class="info">审核核准数</th>
						<!-- <th class="info">复审核准数</th> -->
						<th class="info">备   注</th>
					</tr>
				</thead>
				
				<input type="hidden" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly/>
				<tbody>
				<c:forEach items="${list}" var="ccd" varStatus="vss">
				<c:if test="${ccd.status==1 }">
					<tr>
						<td class="tc" class="w50">
							<input type="hidden" name="plccd[${(vss.index)}].id" value="${ccd.id }"/>
							<input type="hidden" name="plccd[${(vss.index)}].status" value="${ccd.status }"  />
							${vss.index+1 }
						</td>
						<td class="tl">${ccd.projectName }</td>
						<td class="tr w100">${ccd.tyaActual }</td>
						<td class="tr w100">${ccd.oyaActual }</td>
						<td class="tr w100">${ccd.newActual }</td>
						<td class="tr w100">${ccd.subtractActual }</td>
						<%-- <td class="tc w100"><input type="text" name="plccd[${(vss.index)}].checkActual" value="${ccd.checkActual }" class="border0 mb0 w80"/></td> --%>
						<td class="tl" width="30%">${ccd.remark }</td>
					</tr>
					</c:if>
				</c:forEach>
				</tbody>
			</table>
		</div>
		
		<div  class="col-md-12">
		   <div class="mt40 tc mb50">
		    	<button class="btn" type="button" onclick="onStep()">上一步</button>
		     	<button class="btn" type="submit">下一步</button>
			</div>
	 	 </div>
	 	 
  </div>
  </form>
  
  </body>
</html>
