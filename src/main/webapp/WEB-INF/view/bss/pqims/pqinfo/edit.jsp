<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/tld/upload" prefix="up" %>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
    <title>查看质检信息</title>
     <script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/upload.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/upload/upload.css" type="text/css" />   
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/public/select2/js/select2.js"></script>
<link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath }/public/select2/js/select2_locale_zh-CN.js"></script>
	
<script type="text/javascript">
	

$(function(){
	  $("#contract").select2();
})	
	$(function(){
		$("#purchaseType").val("${pqinfo.projectType}");
		$("#type").val("${pqinfo.type}");
		$("#conclusion").val("${pqinfo.conclusion}");
		var type=$("#purchaseType").val();
		if(type!=null && type!="" && type!="-请选择-"){
			$.ajax({
				contentType: "application/json;charset=UTF-8",
				  url:"${pageContext.request.contextPath}/pqinfo/selectContract.do?purchaseType="+type,
			      type:"POST",
			      dataType: "json",
			      success:function(purchaseContracts) {     
		              if (purchaseContracts) {           
		                $("#contract").html("<option></option>");                
		                $.each(purchaseContracts, function(i, purchaseContract) {  
		              	  if(purchaseContract.name != null && purchaseContract.name!=''){
		              		  $("#contract").append("<option  value="+purchaseContract.id+">"+purchaseContract.name+"</option>"); 
		              	  }	                                              
		                });  
		              }
		              $("#contract").select2("val", "${pqinfo.contract.id}"); 
		          }
				
			});
		}
	});
  	


function contractType(type){
	  $("#contractCode").val("");
	  $("#supplierName").val("");
	  $("#procurementId").val("");
      $("#contractName").val("");
	  $("#contract").select2("val", "");
	  $("#contract").empty();
	$.ajax({
		contentType: "application/json;charset=UTF-8",
		  url:"${pageContext.request.contextPath}/pqinfo/selectContract.do?purchaseType="+type,
	      type:"POST",
	      dataType: "json",
	      success:function(purchaseContracts) {     
              if (purchaseContracts) {           
                $("#contract").html("<option></option>");                
                $.each(purchaseContracts, function(i, purchaseContract) {  
              	  if(purchaseContract.name != null && purchaseContract.name!=''){
              		  $("#contract").append("<option  value="+purchaseContract.id+">"+purchaseContract.name+"</option>"); 
              	  }	                                              
                });  
              }
          }
		
	});
}
	

function change(){
	var id = $("#contract").val();
	  $.ajax({
		  url:"${pageContext.request.contextPath}/appraisalContract/selectContractId.do?id="+id,
	      type:"POST",
	      success:function(contract){
	    	  var con = JSON.parse(contract);
	    	  $("#contractName").val(con.name);
	    	  $("#contractCode").val(con.code);
	    	  $("#supplierName").val(con.supplierDepName);
	    	  $("#procurementId").val(con.supplierPurId);
	      }
	  });
}
</script>
  
    </head>
<body>
 
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   		<li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">保障作业</a></li><li><a href="javascript:void(0)">产品质量管理</a></li><li class="active"><a href="javascript:void(0)">修改质检报告</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
<!-- 修改订列表开始-->
   <div class="container container_box">
   		<form action="${pageContext.request.contextPath}/pqinfo/update.html" method="post"  enctype="multipart/form-data">
   		<div>
   		<h2 class="count_flow">修改质检报告</h2>
   		<ul class="ul_list">
   			<input type="hidden" class="id" name="id" value = '${pqinfo.id}'>
   			<input type="hidden" id="contractId" class="contract_id" name="contract.id" value = '${pqinfo.contract.id}'>
   			
			 <li class="col-md-3 col-sm-6 col-xs-12 pl15">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>项目类别：</span>
			   <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
		        	<select id="purchaseType" name="projectType" onchange="contractType(this.options[this.selectedIndex].value)">
						<option value="-请选择-">请选择</option>
						<option value="询价">询价</option>
						<option value="单一来源">单一来源</option>
						<option value="邀请招标">邀请招标</option>
						<option value="公开招标">公开招标</option>
						<option value="竞争性谈判">竞争性谈判</option>
	  				</select> 
	  				<div id="contractCodeErr" class="cue">${ERR_projectType}</div>
	  			</div>
			 </li>
			 
		     	<li class="col-md-3 col-sm-6 col-xs-12">
			   		<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>合同名称：</span>
			   		<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
			   		<select id="contract" class="col-md-12 col-sm-12 col-xs-12 p0" onchange="change()"></select>
			   		<input type="hidden" id="contractName" name="contract.name" value="${pqinfo.contract.name }">
		       		<div id="contractCodeErr" class="cue">${ERR_contract_name}</div>
		       </div>
			 </li>
			 
		    	<li class="col-md-3 col-sm-6 col-xs-12">
			   		<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">合同编号：</span>
			   		<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        	<input class="span5 contractCode" id="contractCode" name="contract.code" value = '${pqinfo.contract.code}'  type="text"  readonly="readonly">
		        	<span class="add-on">i</span>
       			</div>
			 </li>
    		 	<li class="col-md-3 col-sm-6 col-xs-12">
			   		<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">供应商组织机构代码：</span>
		        	<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        	<input class="span5 procurementId" id="procurementId" name="procurementId"  value = '${pqinfo.contract.supplierPurId}' type="text"  readonly="readonly">
		        	<span class="add-on">i</span>
       			</div>
			 </li>
			 
		    	<li class="col-md-3 col-sm-6 col-xs-12">
			   		<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">供应商名称：</span>
			   		<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input class="span5 supplier_name" id="supplierName" name="supplier_name" value = '${pqinfo.contract.supplierDepName}' type="text"  readonly="readonly">
		        <span class="add-on">i</span>
		       </div>
			 </li>
    		 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>质检单位：</span>
		        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
		        	<input name="unit" value = '${pqinfo.unit}'  type="text">
		        	<span class="add-on">i</span>
		        	<div class="cue">${ERR_unit}</div>
       			</div>
			 </li>
		     <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>质检类型：</span>
			   <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
		        	<select id="type" name =type  >
						<option value="-请选择-">请选择</option>
						<option value="首件检验">首件检验</option>
						<option value="生产验收">生产验收</option>
						<option value="出厂验收">出厂验收</option>
						<option value="到货验收">到货验收</option>
	  				</select> 
	  				<div class="cue">${ERR_type}</div>
	  			</div>
			 </li>
    		 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>质检地点：</span>
		        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
		        	<input name="place" value = '${pqinfo.place}'  type="text">
		        	<span class="add-on">i</span>
		        <div class="cue">${ERR_place}</div>
       			</div>
			 </li>
			<li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>质检日期：</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input class="Wdate" name="date" value="<fmt:formatDate value='${pqinfo.date}' pattern='yyyy-MM-dd'/>"  type="text">
		       <div class="cue">${ERR_pqdate}</div>
		       </div>
			 </li>
    		 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>质检人员：</span>
		        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
		        	<input name="inspectors" value = '${pqinfo.inspectors}'  type="text">
		        	<span class="add-on">i</span>
		        <div class="cue">${ERR_inspectors}</div>
       			</div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>质检情况：</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input name="condition" value = '${pqinfo.condition}'  type="text">
		        <span class="add-on">i</span>
		       <div class="cue">${ERR_condition}</div>
		       </div>
			 </li>
    		 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>质检结论：</span>
			   <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
		        	<select id="conclusion" name ="conclusion">
						<option value="-请选择-" >请选择</option>
						<option value="合格">合格</option>
						<option value="不合格">不合格</option>
	  				</select> 
	  			<div class="cue">${ERR_conclusion}</div>
	  			</div>
			 </li>
			 <li class="col-md-12 col-sm-12 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>详细情况：</span>
			   <div class="col-md-12 col-sm-12 col-xs-12 p0">
		         	<textarea class="h130 col-md-12 col-sm-12 col-xs-12 " name="detail" title="不超过800个字" placeholder="不超过800个字" >${pqinfo.detail}</textarea>
		       </div>
		       <div class="clear red">${ERR_detail}</div>
			 </li>
			 	 
			 <li class="col-md-12 col-sm-12 col-xs-12 mt10" id="picNone" >
	   			<span class="fl">图片上传：</span>
	    		<div class="fl">
	        		<up:upload id="artice_up"  businessId="${pqinfoID }" sysKey="${pqinfoKey}" typeId="${attachtypeId }" auto="true" />
					<up:show showId="artice_show"  businessId="${pqinfoID }" sysKey="${pqinfoKey}" typeId="${attachtypeId }"/>
				</div>
	 		</li>
   		</ul>

  		<div  class="col-md-12 col-sm-12 col-xs-12 tc mt20">
    			<button class="btn btn-windows save" type="submit">更新</button>
    			<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
  		</div>
  		</div>
  		</form>
 	</div>

</body>
</html>
