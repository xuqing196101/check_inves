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
	window.location.href="${pageContext.request.contextPath}/productQuota/select.html?proId="+proId;
}
</script>     
  </head>
  
  <body>
  
 <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">供应商报价</a></li><li><a href="javascript:void(0)">产品报价</a></li><li><a href="javascript:void(0)">产品工时定额明细</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
   
   
  <div class="container container_box"> 
    <form action="${pageContext.request.contextPath}/productQuota/update.html" method="post">
   
   <input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
   <input type="hidden" id="id" name="id" class="w230 mb0" value="${pq.id }" readonly>
   
   <div>
   <h2 class="f16 count_flow mt40"><i>01</i>基础信息</h2>
   <ul class="ul_list mb20">
   <li class="col-md-3 col-sm-6 col-xs-12 pl15">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>零组部件名称：</span>
	   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
        <input type="text" id="partsName" name="partsName" value="${pq.partsName }" class="w220">
       	<div class="cue">${ERR_partsName}</div>
       </div>
	 </li>
	 <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>零组部件图纸：</span>
	   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
        <input type="text" id="partsDrawingCode" name="partsDrawingCode" value="${pq.partsDrawingCode }" class="w220">
       	<div class="cue">${ERR_partsDrawingCode}</div>
       </div>
	 </li>
	 <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>工序名称：</span>
	   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
        <input type="text" id="processName" name="processName" value="${pq.processName }" class="w220">
       	<div class="cue">${ERR_processName}</div>
       </div>
	 </li>
	 <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>计量单位：</span>
	   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
        <input id="measuringUnit" name="measuringUnit" value="${pq.measuringUnit }" type="text" class="w220">
       </div>
	 </li>
   </ul>
   </div>
   
   <div class="padding-top-10 clear">
   <h2 class="f16 count_flow mt40"><i>02</i>单位产品工时定额</h2>
   <ul class="ul_list mb20">
   <li class="col-md-3 col-sm-6 col-xs-12 pl15">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">准结工时：</span>
	   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
        <input type="text" id="offer" name="offer" value="${pq.offer }" class="w220">
       </div>
	 </li>
	 <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">加工工时：</span>
	   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
        <input type="text" id="processingOffer" name="processingOffer" value="${pq.processingOffer }" class="w220">
       </div>
	 </li>
	 <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">装配工时：</span>
	   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
        <input type="text" id="assemblyOffer" name="assemblyOffer" value="${pq.assemblyOffer }" class="w220">
       </div>
	 </li>
	 <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">调试工时：</span>
	   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
        <input type="text" id="debuggingOffer" name="debuggingOffer" value="${pq.debuggingOffer }" class="w220">
       </div>
	 </li>
	 <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">试验工时：</span>
	   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
        <input type="text" id="testOffer" name="testOffer" value="${pq.testOffer }" class="w220">
       </div>
	 </li>
	 <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">其他工时：</span>
	   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
        <input type="text" id="otherOffer" name="otherOffer" value="${pq.otherOffer }" class="w220">
       </div>
	 </li>
	 <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">小计：</span>
	   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
        <input type="text" id="subtotalOffer" name="subtotalOffer" value="${pq.subtotalOffer }" class="w220">
       </div>
	 </li>
   </ul>
   </div>
   
   <div class="padding-top-10 clear">
   <h2 class="f16 count_flow mt40"><i>03</i>其他</h2>
   <ul class="ul_list mb20">
   <li class="col-md-3 col-sm-6 col-xs-12 pl15">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">配套数量：</span>
	   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
        <input type="text" id="assortOffer" name="assortOffer" value="${pq.assortOffer }" class="w220">
       </div>
	 </li>
	 <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">单位产品工时审核核定数：</span>
	   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
        <input type="text" id="approvedOffer" name="approvedOffer" value="${pq.approvedOffer }" class="w220">
       </div>
	 </li>
	 <li class="col-md-12 col-sm-12 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">备注：</span>
	   <div class="col-md-12 p0 mt5">
        <textarea class="col-md-12 h80" id="remark" name="remark" title="不超过250个字" placeholder="不超过250个字">${pq.remark }</textarea>
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
