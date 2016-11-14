<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>添加</title>

<script type="text/javascript">
function down(){
	var proId = $("#proId").val();
	window.location.href="${pageContext.request.contextPath}/outsourcingCon/select.html?proId="+proId;
}
</script>
  </head>
  
  <body>
  
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">添加外协加工件消耗定额明细</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>

<div class="container bggrey border1 mt20"> 
    <form action="${pageContext.request.contextPath}/outsourcingCon/save.html" method="post">
   
   <input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
   
   <div>
   <h2 class="f16 count_flow mt40"><i>01</i>材料信息</h2>
   <ul class="list-unstyled list-flow ul_list">
   <li class="col-md-6 p0">
	   <span class=""><i class="red">＊</i>外协加工工件名称：</span>
	   <div class="input-append">
        <input type="text" class="w220" id="outsourcingName" name="outsourcingName" value="${out.outsourcingName }">
        <div class="validate">${ERR_outsourcingName}</div>
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class=""><i class="red">＊</i>规格型号：</span>
	   <div class="input-append">
        <input id="norm" name="norm" type="text" value="${out.norm }" class="w220">
        <div class="validate">${ERR_norm}</div>
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class=""><i class="red">＊</i>图纸位置号(代号)：</span>
	   <div class="input-append">
        <input id="paperCode" name="paperCode" type="text" value="${out.paperCode }" class="w220">
        <div class="validate">${ERR_paperCode}</div>
       </div>
	 </li>
   </ul>
   </div>
   
   <div class="padding-top-10 clear">
   <h2 class="f16 count_flow mt40"><i>02</i>所属加工生产装配工艺消耗定额（数量、质量、含税金额）</h2>
   <ul class="list-unstyled list-flow ul_list">
   <li class="col-md-6 p0">
	   <span class="">数量：</span>
	   <div class="input-append">
        <input type="text" id="workAmout" name="workAmout" value="${out.workAmout }" class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">单件重：</span>
	   <div class="input-append">
        <input type="text" id="workWeight" name="workWeight" value="${out.workWeight }" class="w220">
       </div>
	 </li>
	 
	 <li class="col-md-6  p0 ">
	   <span class="">重量小计：</span>
	   <div class="input-append">
        <input type="text" id="workWeightTotal" name="workWeightTotal" value="${out.workWeightTotal }" class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">单价(元)：</span>
	   <div class="input-append">
        <input type="text" id="workPrice" name="workPrice" value="${out.workMoney }" class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">金额：</span>
	   <div class="input-append">
        <input type="text" id="workMoney" name="workMoney" value="${out.workMoney }" class="w220">
       </div>
	 </li>
   </ul>
   </div>
   
   
   <div class="padding-top-10 clear">
   <h2 class="f16 count_flow mt40"><i>03</i>消耗定额审核核准数（含税金额）</h2>
   <ul class="list-unstyled list-flow ul_list">
   <li class="col-md-6 p0">
	   <span class="">数量：</span>
	   <div class="input-append">
        <input type="text" id="consumeAmout" name="consumeAmout" value="${out.consumeAmout }" class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">单价(元)：</span>
	   <div class="input-append">
        <input type="text" id="consumePrice" name="consumePrice" value="${out.consumePrice }" class="w220">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">金额：</span>
	   <div class="input-append">
        <input type="text" id="consumeMoney" name="consumeMoney" value="${out.consumeMoney }" class="w220">
       </div>
	 </li>
   </ul>
   </div>
   
    <div class="padding-top-10 clear">
   <h2 class="f16 count_flow mt40"><i>04</i>其他</h2>
   <ul class="list-unstyled list-flow ul_list">
   <li class="col-md-12 p0">
	   <span class=""><i class="red">＊</i>供货单位：</span>
	   <div class="input-append">
        <input type="text" id="supplyUnit" name="supplyUnit" value="${out.supplyUnit }" class="w220">
        <div class="validate">${ERR_supplyUnit}</div>
       </div>
	 </li>
	 <li class="col-md-12  p0 ">
	   <span class="fl">备注：</span>
	   <div class="col-md-12 pl200 fn mt5 pwr9">
        <textarea class="text_area col-md-12 " id="remark" name="remark" title="不超过200个字" placeholder="不超过200个字">${out.remark }</textarea>
       </div>
	 </li>
   </ul>
   </div>
   
	 	<div  class="col-md-12">
	   		<div class="mt40 tc mb50">
			    <button class="btn btn-windows save" type="submit">确定</button>
			    <button class="btn btn-windows cancel" type="button" onclick="down()">取消</button>
			</div>
		</div>
	
</form>	
</div>
		  
  </body>
</html>
