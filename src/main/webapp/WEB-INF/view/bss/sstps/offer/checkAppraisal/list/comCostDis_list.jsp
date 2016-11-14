<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>综合费用汇总分配计算明细</title>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/public/layer/layer.js"></script>
    <script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>

<script type="text/javascript">

function onStep(){
	var proId = $("#proId").val();
	window.location.href="<%=basePath%>productQuota/checkGetAll.do?productId="+proId;
}

</script>

  </head>
  
  <body>
  
  	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">供应商报价</a></li><li><a href="#">综合费用汇总分配计算明细</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
  
  <div class="container">
	 	<div class="headline-v2">
	  		 <h2>综合费用汇总分配计算明细</h2>
	 	</div>
	 	
   </div>
	
	<form id="formID" name="form1" action="<%=basePath%>comCostDis/checkUpdate.html?productId=${proId }" method="post" enctype="multipart/form-data">
	
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
						<td class="tc">${ccd.projectName }</td>
						<td class="tc">${ccd.tyaAmount }</td>
						<td class="tc">${ccd.tyaFee }</td>
						<td class="tc">${ccd.oyaAmout }</td>
						<td class="tc">${ccd.oyaFee }</td>
						<td class="tc">${ccd.newAmount }</td>
						<td class="tc">${ccd.newFee }</td>
						<td class="tc"><input type="text" name="plccd[${(vs.index)}].subtractWentDutch" value="${ccd.subtractWentDutch }" class="border0 mb0"/></td>
						<td class="tc"><input type="text" name="plccd[${(vs.index)}].subtractFee" value="${ccd.subtractFee }" class="border0 mb0"/></td>
						<td class="tc">${ccd.remark }</td>
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
						<th class="info">序号</th>
						<th class="info">项目名称</th>
						<th class="info">报价前2年</th>
						<th class="info">报价前1年</th>
						<th class="info">报价当年</th>
						<th class="info">审核核准数</th>
						<th class="info">备   注</th>
					</tr>
				</thead>
				
				<input type="hidden" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
				<tbody>
				<c:forEach items="${list}" var="ccd" varStatus="vss">
				<c:if test="${ccd.status==1 }">
					<tr>
						<td class="tc" class="w30">
							<input type="hidden" name="plccd[${(vss.index)}].id" value="${ccd.id }"/>
							<input type="hidden" name="plccd[${(vss.index)}].status" value="${ccd.status }"  />
							${vss.index+1 }
						</td>
						<td class="tc">${ccd.projectName }</td>
						<td class="tc">${ccd.tyaActual }</td>
						<td class="tc">${ccd.oyaActual }</td>
						<td class="tc">${ccd.newActual }</td>
						<td class="tc"><input type="text" name="plccd[${(vss.index)}].subtractActual" value="${ccd.subtractActual }" class="border0 mb0"/></td>
						<td class="tc">${ccd.remark }</td>
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
