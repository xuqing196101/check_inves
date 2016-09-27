<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>

	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
</head>
<script type="text/javascript">
  	/** 全选全不选 */
	$(function(){
		$("#projectType").val('${pqinfo.projectType}');
		$("#type").val('${pqinfo.type}');
		$("#conclusion").val('${pqinfo.conclusion}');
	});
  	
	function selectByCode(){
		var code= $(".contract_code").val();
		$.ajax({
			type:"POST",
			dataType:"json",
			url:"<%=basePath%>purchaseContract/selectByCode.do?code="+code,
			success:function(json){
					 $(".contract_id").val(json.id);
					 $(".contract_name").val(json.name);
					 $(".supplier_name").val(json.supplier.name);
					 $(".procurementId").val(json.supplier.procurementId);
					 
       		}
       	});
}
  </script>
<body>
 
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   		<li><a href="#"> 首页</a></li><li><a href="#">保障作业</a></li><li><a href="#">产品质量管理</a></li><li class="active"><a href="#">修改质检报告</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
<!-- 修改订列表开始-->
   <div class="container">
   		<form action="<%=basePath%>pqinfo/update.html" method="post">
   		<div class="headline-v2">
   			<h2>修改质检报告</h2>
   		</div>
   		<ul class="list-unstyled list-flow p0_20">
   			<input type="hidden" class="id" name="id" value = '${pqinfo.id}'>
   			<input type="hidden" class="contract_id" name="contract_id" value = '${pqinfo.contract.id}'>
		     <li class="col-md-6  p0 ">
			   <span class="">合同编号：</span>
			   <div class="input-append">
		        <input class="span2 contract_code" name="contract_code" id="contract_code" type="text" value = '${pqinfo.contract.code}' onblur="selectByCode()">
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">合同名称：</span>
		        <div class="input-append ">
		        	<input class="span2 contract_name" name="contract_name" value = '${pqinfo.contract.name}'  type="text"  readonly="readonly">
       			</div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">供应商组织机构代码：</span>
		        <div class="input-append ">
		        	<input class="span2 procurementId" name="procurementId"  value = '${pqinfo.contract.supplier.procurementId}' type="text"  readonly="readonly">
       			</div>
			 </li>
		     <li class="col-md-6  p0 ">
			   <span class="">供应商名称：</span>
			   <div class="input-append">
		        <input class="span2 supplier_name" name="supplier_name" value = '${pqinfo.contract.supplier.supplierName}' type="text"  readonly="readonly">
		       </div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="fl">项目类别：</span>
			   <div class="btn-group ">
		        	<select id="projectType" name ="projectType" class="w220" >
						<option value="0">请选择</option>
	  				</select> 
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">质检单位：</span>
		        <div class="input-append ">
		        	<input class="span2" name="unit" value = '${pqinfo.unit}'  type="text">
       			</div>
			 </li>
		     <li class="col-md-6  p0 ">
			   <span class="fl">质检类型：</span>
			   <div class="btn-group ">
		        	<select id="type" name =type class="w220" >
						<option>请选择</option>
						<option value="首件检验">首件检验</option>
						<option value="生产验收">生产验收</option>
						<option value="出厂验收">出厂验收</option>
						<option value="到货验收">到货验收</option>
	  				</select> 
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">质检地点：</span>
		        <div class="input-append ">
		        	<input class="span2" name="place" value = '${pqinfo.place}'  type="text">
       			</div>
			 </li>
			<li class="col-md-6  p0 ">
			   <span class="">质检日期：</span>
			   <div class="input-append">
		        <input class="span2" name="date" value = '${pqinfo.date}'  type="text">
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">质检人员：</span>
		        <div class="input-append ">
		        	<input class="span2" name="inspectors" value = '${pqinfo.inspectors}'  type="text">
       			</div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="">质检情况：</span>
			   <div class="input-append">
		        <input class="span2" name="condition" value = '${pqinfo.condition}'  type="text">
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="fl">质检结论：</span>
		        <div class="btn-group ">
		        	<select id="conclusion" name ="conclusion" class="w220" >
						<option value="" >请选择</option>
						<option value="合格">合格</option>
						<option value="不合格">不合格</option>
	  				</select> 
       			</div>
			 </li>
			 <li class="col-md-12  p0 ">
			   <span class="fl">详细情况：</span>
			   <div class="col-md-12 pl200 fn mt5 pwr9">
		        <input class="span2" name="detail" value = '${pqinfo.detail}'  type="text">
		       </div>
			 </li>
   		</ul>
   		<ul class="list-unstyled list-flow p0_20">
		     <li class="col-md-6  p0 ">
			   <span class="">质检报告：</span>
			   <div class="input-append ">
		        <input class="span2" name="report" type="text" value = '${pqinfo.report}' >
		        <button type="button" class="btn ml20 mt1">附件上传</button>
		       </div>
			 </li>
		</ul>
  		<div  class="col-md-12 ml185">
   			<div class="fl padding-10 ">
    			<button class="btn btn-windows reset" type="submit">更新</button>
    			<button class="btn btn-windows git" onclick="history.go(-1)" type="button">返回</button>
			</div>
  		</div>
  		</form>
 	</div>

</body>
</html>
