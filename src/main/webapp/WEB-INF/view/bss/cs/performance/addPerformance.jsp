<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]-->
<head>
<%@ include file="/WEB-INF/view/common.jsp" %> 
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>
	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
	<script type="text/javascript">
	$(document).ready(function(){  
	   var perTy = "${performance.completedStatus}";
	   $("#completedStatus").val(perTy);
	});
	function back(){
		window.location.href="${pageContext.request.contextPath}/purchaseContract/selectFormalContract.html";
	}
	</script>
</head>
<body>
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0);"> 首页</a></li><li><a href="javascript:void(0);">履约情况管理</a></li><li><a href="javascript:void(0);">履约情况登记</a></li></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
<!-- 修改订列表开始-->

	<div class="container container_box">
     <div>
   <h2 class="list_title">新增履约情况</h2>
  	<form action="${pageContext.request.contextPath}/performance/addPerformance.html" method="post" id="form">
  	<input type="hidden" name="contractId" value="${contractId}"/>
  		<ul class="list-unstyled ul_list">
     <li class="col-md-3 col-sm-6 col-xs-12 pl15">
	   <span class="col-md-12 col-sm-12 col-xs-12 p0"><div class="red star_red">*</div>合同草稿签订时间：</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input type="text" name="draftSignedAt" value="<fmt:formatDate value="${performance.draftSignedAt }" pattern="yyyy-MM-dd HH:mm:ss" />" id="draftSignedAt" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate mb0 w220"/>
        <div class="cue">${ERR_draftSignedAt}</div>
       </div>
	 </li> 
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 p0"><div class="red star_red">*</div>正式合同签订时间：</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input type="text" name="formalSignedAt" value="<fmt:formatDate value="${performance.formalSignedAt }" pattern="yyyy-MM-dd HH:mm:ss" />" id="formalSignedAt" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate mb0 w220"/>
        <div class="cue">${ERR_formalSignedAt}</div>
       </div>
	 </li> 
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 p0"><div class="red star_red">*</div>交付日期：</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
	    <input type="text" name="delivery" value="<fmt:formatDate value="${performance.delivery }" pattern="yyyy-MM-dd HH:mm:ss" />" id="delivery" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate mb0 w220"/>
	    <div class="cue">${ERR_delivery}</div>
       </div>
	 </li> 
	 <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 p0"><div class="red star_red">*</div>交货进度：</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input id="deliverySchedule" type="text" value="${performance.deliverySchedule }" name="deliverySchedule">
        <div class="cue">${ERR_deliverySchedule}</div>
       </div>
	 </li>
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 p0"><div class="red star_red">*</div>资金支付百分比(%)：</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input id="fundsPaid" type="text" value="${performance.fundsPaid }" name="fundsPaid">
        <div class="cue">${ERR_fundsPaid}</div>
       </div>
	 </li>
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 p0"><div class="red star_red">*</div>质量检验结果：</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input id="checkMass" value="${performance.checkMass }" name="checkMass" type="text">
        <div class="cue">${ERR_checkMass}</div>
       </div>
	 </li>
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 p0"><div class="red star_red">*</div>合同执行状态：</span>
	   <div class="select_common input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <select name="completedStatus" id="completedStatus" class="contract_name">
        	<option></option>
        	<option value="0">执行中</option>
        	<option value="1">终止</option>
        	<option value="2">变更</option>
        	<option value="3">完成</option>
        </select>
        <div class="cue">${ERR_completedStatus}</div>
        </div>
	 </li> 
  	</ul>
  	<!-- 按钮 -->
		<div class="col-md-12 tc ">
		 	<input type="submit" value="保存" class="btn btn-windows save"/>
   			<input class="btn btn-windows back" value="返回" type="button" onclick="back()">
		</div>
  	</form>
  	</div>
 </div>
</body>
</html>
