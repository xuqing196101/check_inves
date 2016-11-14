<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>燃料动力费明细</title>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/public/layer/layer.js"></script>
    <script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>

<script type="text/javascript">

function onStep(){
	var proId = $("#proId").val();
	window.location.href="<%=basePath%>specialCost/checkGetAll.do?productId="+proId;
}

</script>

  </head>
  
  <body>
  
  	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">供应商报价</a></li><li><a href="#">燃料动力费明细</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
  
  <div class="container">
	 	<div class="headline-v2">
	  		 <h2>燃料动力费明细</h2>
	 	</div>
		
   </div>
	
	<input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
	
	<div class="container margin-top-5">
	 	<form action="<%=basePath %>burningPower/checkUpdate.html?productId=${proId }" method="post" enctype="multipart/form-data">
	 	<div class="container padding-left-25 padding-right-25">
			<table class="table table-bordered table-condensed">
				<thead>
					<tr>
						<th rowspan="2" class="info">序号</th>
						<th rowspan="2" class="info">一级项目</th>
						<th rowspan="2" class="info">二级项目</th>
						<th rowspan="2" class="info">三级项目</th>
						<th rowspan="2" class="info">计量单位</th>
						<th colspan="3" class="info">报价前2年</th>
						<th colspan="3" class="info">报价前1年</th>
						<th colspan="3" class="info">报价当年</th>
						<th rowspan="2" class="info">审核核准金额</th>
						<th rowspan="2" class="info">备   注</th>
					</tr>
					<tr>
						<th class="info">数量</th>
						<th class="info">平均单价</th>
						<th class="info">金额</th>
						<th class="info">数量</th>
						<th class="info">平均单价</th>
						<th class="info">金额</th>
						<th class="info">数量</th>
						<th class="info">平均单价</th>
						<th class="info">金额</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${list}" var="bp" varStatus="vs">
					<tr>
						<td class="tc"><input type="hidden" name="burningPowers['${vs.index }'].id" value="${bp.id }" />${vs.index+1 }</td>
						<td class="tc">${bp.firsetProduct }</td>
						<td class="tc">${bp.secondProduct }</td>
						<td class="tc">${bp.thirdProduct }</td>
						<td class="tc">${bp.unit }</td>
						<td class="tc">${bp.tyaAcount }</td>
						<td class="tc">${bp.tyaAvgPrice }</td>
						<td class="tc">${bp.tyaMoney }</td>
						<td class="tc">${bp.oyaAcount }</td>
						<td class="tc">${bp.oyaAvgPrice }</td>
						<td class="tc">${bp.oyaMoney }</td>
						<td class="tc">${bp.newAcount }</td>
						<td class="tc">${bp.newAvgPrice }</td>
						<td class="tc">${bp.newMoney }</td>
						<td class="tc"><input type="text" value='${bp.approvedMoney }' name="burningPowers['${vs.index }'].approvedMoney"></td>
						<td class="tc">${bp.remark }</td>
					</tr>
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
	 	 </form> 
  </div>
  
  </body>
</html>
