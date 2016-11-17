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
	window.location.href="${pageContext.request.contextPath}/wagesPayable/select.html?proId="+proId;
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
    <form action="${pageContext.request.contextPath}/wagesPayable/update.html" method="post">
   
   <input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
   <input type="hidden" id="id" name="id" class="w230 mb0" value="${wp.id }" readonly>
   
   <div>
   <h2 class="f16 count_flow mt40"><i>01</i>材料信息</h2>
   <ul class="ul_list mb20">
   <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5"><div class="star_red">＊</div>部门：</span>
	   <div class="input-append">
        <input type="text" id="department" name="department" value="${wp.department }" class="w220">
       	<div class="cue">${ERR_department}</div>
       </div>
	 </li>
	 <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5"><div class="star_red">＊</div>上级项目：</span>
	   <div class="input-append">
        <input type="text" id="firsetProduct" name="firsetProduct" value="${wp.firsetProduct }"  class="w220">
       	<div class="cue">${ERR_firsetProduct}</div>
       </div>
	 </li>
	 <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5"><div class="star_red">＊</div>项目名称：</span>
	   <div class="input-append">
        <input id="secondProduct" name="secondProduct" value="${wp.secondProduct }"  type="text" class="w220">
       	<div class="cue">${ERR_secondProduct}</div>
       </div>
	 </li>
   </ul>
   </div>
   
   <div class="padding-top-10 clear">
   <h2 class="f16 count_flow mt40"><i>02</i>报价前2年</h2>
   <ul class="ul_list mb20">
   <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5">基本生产人员：</span>
	   <div class="input-append">
        <input type="text" id="tyaProduceUser" name="tyaProduceUser" value="${wp.tyaProduceUser }"  class="w220">
       </div>
	 </li>
	 <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5">车间管理人员：</span>
	   <div class="input-append">
        <input type="text" id="tyaWorkshopUser" name="tyaWorkshopUser" value="${wp.tyaWorkshopUser }"  class="w220">
       </div>
	 </li>
	 <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5">管理人员：</span>
	   <div class="input-append">
        <input type="text" id="tyaManageUser" name="tyaManageUser" value="${wp.tyaOtherUser }"  class="w220">
       </div>
	 </li>
	 <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5">其他人员：</span>
	   <div class="input-append">
        <input type="text" id="tyaOtherUser" name="tyaOtherUser" value="${wp.tyaOtherUser }"  class="w220">
       </div>
	 </li>
	 <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5">合计：</span>
	   <div class="input-append">
        <input type="text" id="tyaTotal" name="tyaTotal" value="${wp.tyaTotal }"  class="w220">
       </div>
	 </li>
   </ul>
   </div>
   
   <div class="padding-top-10 clear">
   <h2 class="f16 count_flow mt40"><i>03</i>报价前1年</h2>
   <ul class="ul_list mb20">
   <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5">基本生产人员：</span>
	   <div class="input-append">
        <input type="text" id="oyaProduceUser" name="oyaProduceUser" value="${wp.oyaProduceUser }"  class="w220">
       </div>
	 </li>
	 <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5">车间管理人员：</span>
	   <div class="input-append">
        <input type="text" id="oyaWorkshopUser" name="oyaWorkshopUser" value="${wp.oyaWorkshopUser }"  class="w220">
       </div>
	 </li>
	 <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5">管理人员：</span>
	   <div class="input-append">
        <input type="text" id="oyaManageUser" name="oyaManageUser" value="${wp.oyaManageUser }"  class="w220">
       </div>
	 </li>
	 <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5">其他人员：</span>
	   <div class="input-append">
        <input type="text" id="oyaOtherUser" name="oyaOtherUser" value="${wp.oyaTotal }"  class="w220">
       </div>
	 </li>
	 <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5">合计：</span>
	   <div class="input-append">
        <input type="text" id="oyaTotal" name="oyaTotal" value="${wp.oyaTotal }"  class="w220">
       </div>
	 </li>
   </ul>
   </div>
   
   <div class="padding-top-10 clear">
   <h2 class="f16 count_flow mt40"><i>04</i>报价当年</h2>
   <ul class="ul_list mb20">
   <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5">基本生产人员：</span>
	   <div class="input-append">
        <input type="text" id="newProduceUser" name="newProduceUser" value="${wp.newProduceUser }"  class="w220">
       </div>
	 </li>
	 <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5">车间管理人员：</span>
	   <div class="input-append">
        <input type="text" id="newWorkshopUser" name="newWorkshopUser" value="${wp.newWorkshopUser }"  class="w220">
       </div>
	 </li>
	 <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5">管理人员：</span>
	   <div class="input-append">
        <input type="text" id="newManageUser" name="newManageUser" value="${wp.newManageUser }"  class="w220">
       </div>
	 </li>
	 <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5">其他人员：</span>
	   <div class="input-append">
        <input type="text" id="newOtherUser" name="newOtherUser" value="${wp.newOtherUser }"  class="w220">
       </div>
	 </li>
	 <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5">合计：</span>
	   <div class="input-append">
        <input type="text" id="newTotal" name="newTotal" value="${wp.newTotal }"  class="w220">
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
        <textarea class="col-md-12 h80" id="remark" name="remark" title="不超过250个字" placeholder="不超过250个字">${wp.remark }</textarea>
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
