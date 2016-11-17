<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>修改</title>
<script type="text/javascript">
function down(){
	var proId = $("#proId").val();
	window.location.href="${pageContext.request.contextPath}/yearPlan/select.html?proId="+proId;
}
</script>    
  </head>
  
  <body>
  
 <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">供应商报价</a></li><li><a href="javascript:void(0)">产品报价</a></li><li><a href="javascript:void(0)">应付工资明细</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
   
   
  <div class="container container_box"> 
    <form action="${pageContext.request.contextPath}/yearPlan/update.html" method="post">
   
   <input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
   <input type="hidden" id="id" name="id" class="w230 mb0" value="${yp.id }" readonly>
   
   <div>
   <h2 class="f16 count_flow mt40"><i>01</i>材料信息</h2>
   <ul class="ul_list mb20">
   <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5"><div class="star_red">＊</div>项目名称：</span>
	   <div class="input-append">
        <input type="text" id="projectName" name="projectName" value="${yp.projectName }" class="w220">
       </div>
	 </li>
	 <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5"><div class="star_red">＊</div>产品名称：</span>
	   <div class="input-append">
        <input type="text" id="productName" name="productName" value="${yp.productName }" class="w220">
       </div>
	 </li>
	 <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5"><div class="star_red">＊</div>计量单位：</span>
	   <div class="input-append">
        <input id="measuringUnit" name="measuringUnit" type="text" value="${yp.measuringUnit }" class="w220">
       </div>
	 </li>
   </ul>
   </div>
   
   <div class="padding-top-10 clear">
   <h2 class="f16 count_flow mt40"><i>02</i>报价前2年</h2>
   <ul class="ul_list mb20">
   <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5">单位产品定额工时：</span>
	   <div class="input-append">
        <input type="text" id="tyaHourUnit" name="tyaHourUnit" value="${yp.tyaHourUnit }" class="w220">
       </div>
	 </li>
	 <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5">计划产量：</span>
	   <div class="input-append">
        <input type="text" id="tyaInvestAcount" name="tyaInvestAcount" value="${yp.tyaInvestAcount }" class="w220">
       </div>
	 </li>
	 <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5">工时合计：</span>
	   <div class="input-append">
        <input type="text" id="tyaHourTotal" name="tyaHourTotal" value="${yp.tyaHourTotal }" class="w220">
       </div>
	 </li>
   </ul>
   </div>
   
   <div class="padding-top-10 clear">
   <h2 class="f16 count_flow mt40"><i>03</i>报价前1年</h2>
   <ul class="ul_list mb20">
   <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5">单位产品定额工时：</span>
	   <div class="input-append">
        <input type="text" id="oyaHourUnit" name="oyaHourUnit" value="${yp.oyaHourUnit }" class="w220">
       </div>
	 </li>
	 <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5">计划产量：</span>
	   <div class="input-append">
        <input type="text" id="oyaInvestAcount" name="oyaInvestAcount" value="${yp.oyaInvestAcount }" class="w220">
       </div>
	 </li>
	 <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5">工时合计：</span>
	   <div class="input-append">
        <input type="text" id="oyaHourTotal" name="oyaHourTotal" value="${yp.oyaHourTotal }" class="w220">
       </div>
	 </li>
   </ul>
   </div>
   
   <div class="padding-top-10 clear">
   <h2 class="f16 count_flow mt40"><i>04</i>报价当年</h2>
   <ul class="ul_list mb20">
   <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5">单位产品定额工时：</span>
	   <div class="input-append">
        <input type="text" id="newHourUnit" name="newHourUnit" value="${yp.newHourUnit }" class="w220">
       </div>
	 </li>
	 <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5">计划产量：</span>
	   <div class="input-append">
        <input type="text" id="newInvestAcount" name="newInvestAcount" value="${yp.newInvestAcount }" class="w220">
       </div>
	 </li>
	 <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5">工时合计：</span>
	   <div class="input-append">
        <input type="text" id="newHourTotal" name="newHourTotal" value="${yp.newHourTotal }" class="w220">
       </div>
	 </li>
   </ul>
   </div>
   
    <div class="padding-top-10 clear">
   <h2 class="f16 count_flow mt40"><i>05</i>其他</h2>
   <ul class="ul_list mb20">
	 <li class="col-md-12 margin-0 padding-0">
	   <span class="fl col-md-12 padding-left-5">备注：</span>
	   <div class="col-md-12 p0 mt5">
        <textarea class="col-md-12 h80" id="remark" name="remark" title="不超过250个字" placeholder="不超过250个字">${yp.remark }</textarea>
       </div>
	 </li>
   </ul>
   </div>
   
	 	<div  class="col-md-12">
	   		<div class="mt40 tc mb50">
			    <button class="btn btn-windows edit" type="submit">修改</button>
			    <button class="btn btn-windows cancel" type="button" onclick="down()">取消</button>
			</div>
		</div>
	
</form>	
</div>
		  
  </body>
</html>
