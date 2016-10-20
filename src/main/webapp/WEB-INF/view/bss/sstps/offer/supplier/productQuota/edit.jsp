<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>修改</title>
    
  </head>
  
  <body>
  
 <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">供应商报价</a></li><li><a href="#">产品报价</a></li><li><a href="#">产品工时定额明细</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
   
   
  <div class="container bggrey border1 mt20""> 
    <form action="<%=basePath %>productQuota/update.html" method="post" enctype="multipart/form-data">
   
   <input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
   <input type="hidden" id="id" name="id" class="w230 mb0" value="${pq.id }" readonly>
   
   <div>
   <h2 class="f16 count_flow mt40"><i>01</i>基础信息</h2>
   <ul class="list-unstyled list-flow ul_list">
   <li class="col-md-6 p0">
	   <span class="">零组部件名称：</span>
	   <div class="input-append">
        <input type="text" id="partsName" name="partsName" value="${pq.partsName }" class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">零组部件图纸：</span>
	   <div class="input-append">
        <input type="text" id="partsDrawingCode" name="partsDrawingCode" value="${pq.partsName }" class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">工序名称：</span>
	   <div class="input-append">
        <input type="text" id="processName" name="processName" value="${pq.processName }" class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">计量单位：</span>
	   <div class="input-append">
        <input id="measuringUnit" name="measuringUnit" value="${pq.measuringUnit }" type="text" class="w220">
       </div>
	 </li>
   </ul>
   </div>
   
   <div class="padding-top-10 clear">
   <h2 class="f16 count_flow mt40"><i>02</i>单位产品工时定额</h2>
   <ul class="list-unstyled list-flow ul_list">
   <li class="col-md-6 p0">
	   <span class="">准结工时：</span>
	   <div class="input-append">
        <input type="text" id="offer" name="offer" value="${pq.offer }" class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">加工工时：</span>
	   <div class="input-append">
        <input type="text" id="processingOffer" name="processingOffer" value="${pq.processingOffer }" class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">装配工时：</span>
	   <div class="input-append">
        <input type="text" id="assemblyOffer" name="assemblyOffer" value="${pq.assemblyOffer }" class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">调试工时：</span>
	   <div class="input-append">
        <input type="text" id="debuggingOffer" name="debuggingOffer" value="${pq.debuggingOffer }" class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">试验工时：</span>
	   <div class="input-append">
        <input type="text" id="testOffer" name="testOffer" value="${pq.testOffer }" class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">其他工时：</span>
	   <div class="input-append">
        <input type="text" id="otherOffer" name="otherOffer" value="${pq.otherOffer }" class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">小计：</span>
	   <div class="input-append">
        <input type="text" id="subtotalOffer" name="subtotalOffer" value="${pq.subtotalOffer }" class="w220">
       </div>
	 </li>
   </ul>
   </div>
   
   <div class="padding-top-10 clear">
   <h2 class="f16 count_flow mt40"><i>03</i>其他</h2>
   <ul class="list-unstyled list-flow ul_list">
   <li class="col-md-6 p0">
	   <span class="">配套数量：</span>
	   <div class="input-append">
        <input type="text" id="assortOffer" name="assortOffer" value="${pq.assortOffer }" class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">单位产品工时审核核定数：</span>
	   <div class="input-append">
        <input type="text" id="approvedOffer" name="approvedOffer" value="${pq.approvedOffer }" class="w220">
       </div>
	 </li>
	 <li class="col-md-12  p0 ">
	   <span class="fl">备注：</span>
	   <div class="col-md-12 pl200 fn mt5 pwr9">
        <textarea class="text_area col-md-12 " id="remark" name="remark" title="不超过250个字" placeholder="不超过250个字">${pq.remark }</textarea>
       </div>
	 </li>
   </ul>
   </div>
   
	 	<div  class="col-md-12">
	   		<div class="mt40 tc mb50">
			    <button class="btn btn-windows add" type="submit">确定</button>
			    <button class="btn btn-windows cancel" type="button" onclick="location.href='javascript:history.go(-1);'">取消</button>
			</div>
		</div>
	
</form>	
</div>
		  
  </body>
</html>
