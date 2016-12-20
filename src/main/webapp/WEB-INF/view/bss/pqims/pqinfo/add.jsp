<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../../../common.jsp"%>

  <title>登记质检报告</title>
  <script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/upload.js"></script>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/public/upload/upload.css" type="text/css" />

  <script type="text/javascript">
  function selectByCode(){
		var code= $("#contractCode").val();
		$.ajax({
			type:"POST",
			dataType:"json",
			url:"${pageContext.request.contextPath}/purchaseContract/selectByCode.do?code="+code,
			success:function(json){
				if(json.code=="ErrCode"){
					 $("#contractId").val(json.id);
					 $("#contractName").val(json.name);
					 $("#projectType").val(json.purchaseType);	
					 $("#procurementId").val(json.supplierPurId);	
					 $("#supplierName").val(json.supplierDepName);
					 $("#contractCodeErr").text("合同编号不存在");
				}else{
				 $("#contractId").val(json.id);
				 $("#contractName").val(json.name);
				 $("#projectType").val(json.purchaseType);	
				 $("#procurementId").val(json.supplierPurId);	
				 $("#supplierName").val(json.supplierDepName);
				 $("#contractCodeErr").text("");
				}
     		}
     	});
	}
	$(function(){
		$("#type").val("${pqinfo.type}");
		$("#conclusion").val("${pqinfo.conclusion}");
	})
	function goback(){
		window.location.href="${pageContext.request.contextPath}/pqinfo/getAll.html";
	  	
	}
    </script>
 </head>
<body>
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">保障作业</a></li><li><a href="javascript:void(0)">产品质量管理</a></li><li class="active"><a href="javascript:void(0)">登记质检报告</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>

<!-- 新增模板开始-->
   <div class="container container_box">
   		<form action="${pageContext.request.contextPath}/pqinfo/save.html" method="post" enctype="multipart/form-data">
   		<div>
   			<h2 class="count_flow"><i>1</i>合同基本信息</h2>
   			<ul class="ul_list">
   				<input type="hidden" id="contractId" name="contract.id" value="${pqinfo.contract.id }">
   				<input type="hidden" name="id" value = '${pqinfoId}'>
		    	<li class="col-md-3 col-sm-6 col-xs-12">
			   		<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>合同编号：</span>
			   		<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        		<input class="span5 contractCode" id="contractCode" name="contract.code" onblur="selectByCode()" type="text" value="${pqinfo.contract.code }">
		        		<span class="add-on">i</span>
		        		<span class="input-tip">填写合同编号合同信息自动生成</span>
		        		<div class="cue" id="contractCodeErr">${ERR_contract_code }</div>
		       		</div>
			 	</li>

   				<li class="col-md-3 col-sm-6 col-xs-12">
			   		<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">合同名称：</span>
			   		<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
			   		  	<input class="span5 projectType" type="text" id="contractName" name="contract.name" readonly="readonly" value="${pqinfo.contract.name }">
		       		  	<span class="add-on">i</span>
						<span class="input-tip">填写合同编号合同信息自动生成</span>
			 	    </div>
			 	</li>


   				<li class="col-md-3 col-sm-6 col-xs-12">
			   		<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">项目类别：</span>
		        	<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        		<input class="span5 projectType" id="projectType" type="text" name="projectType" readonly="readonly" value = '${pqinfo.projectType}'>
		        		<span class="add-on">i</span>
		        		<span class="input-tip">填写合同编号合同信息自动生成</span>
       				</div>
			 	</li>

    		 	<li class="col-md-3 col-sm-6 col-xs-12">
			   		<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">统一社会信用代码：</span>
		        	<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        		<input class="span5 procurementId" id="procurementId" type="text"  readonly="readonly" value = '${pqinfo.contract.supplierPurId}'>
		        		<span class="add-on">i</span>
		        		<span class="input-tip">填写合同编号合同信息自动生成</span>
       				</div>
			 	</li>
		    	<li class="col-md-3 col-sm-6 col-xs-12">
			   		<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">供应商名称：</span>
			   		<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        		<input class="span5 supplier_name" id="supplierName" type="text"  value = '${pqinfo.contract.supplierDepName}' readonly="readonly">
		        		<span class="add-on">i</span>
                		<span class="input-tip">填写合同编号合同信息自动生成</span>
		       		</div>
			 	</li>
			 </ul>
			</div>
			
			<div class="padding-top-10 clear">
    			<h2 class="count_flow"><i>2</i>质检登记</h2>
   				<ul class="ul_list">			
				
    		 <li class="col-md-3 col-sm-6 col-xs-12 pl15">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>质检单位：</span>
		        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
		        	<input class="span5" name="unit" value = '${pqinfo.unit}' type="text">
		        	<span class="add-on">i</span>
		        	<div class="cue">${ERR_unit}</div>
       			</div>
			 </li>
		     <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>质检类型：</span>
			   <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
		        	<select id="type" name ="type"  >
						<option value="-请选择-">请选择</option>
						<option value="首件检验">首件检验</option>
						<option value="生产验收">生产验收</option>
						<option value="出厂验收">出厂验收</option>
						<option value="到货验收">到货验收</option>
	  				</select>
	  				<input type="hidden" id="ty" var ="${pqinfo.type}" >
	  				<div class="cue">${ERR_type}</div>
	  			</div>
			 </li>
    		 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>质检地点：</span>
		        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
		        	<input  name="place" value = '${pqinfo.place}' type="text">
		        	<span class="add-on">i</span>
              		<span class="input-tip">填写质检地点</span>
		        	<div class="cue">${ERR_place}</div>
       			</div>
			 </li>
			<li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>质检日期：</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input class=" Wdate" name="dateString" type="text" value="<fmt:formatDate value='${pqinfo.date}' pattern='yyyy-MM-dd'/>" onfocus="WdatePicker({isShowWeek:true})">
		        <div class="cue">${ERR_pqdate}</div>
		       </div>
			 </li>
    		 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>质检人员：</span>
		        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
		        	<input  name="inspectors"  value = '${pqinfo.inspectors}'  type="text">
		        	<span class="add-on">i</span>
		        	<div class="cue">${ERR_inspectors}</div>
       			</div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>质检情况：</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input  name="condition" type="text" value = '${pqinfo.condition}'  >
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
	  				<input type="hidden" id="conc" var ="${pqinfo.conclusion}" >
	  				<div class="cue">${ERR_conclusion}</div>
	  			</div>
			 </li>
			 <li class="col-md-12 col-sm-12 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>质检详细情况：</span>
			   <div class="col-md-12 col-sm-12 col-xs-12 p0">
		        <textarea class="h130 col-md-12 col-sm-12 col-xs-12 " name="detail" title="不超过800个字" placeholder="不超过800个字">${pqinfo.detail}</textarea>
		       </div>
		       <div class="clear red">${ERR_detail}</div>
			 </li>
			 <li class="col-md-12 col-sm-12 col-xs-12 mt10" id="picNone" >
	   			<span class="fl">质检报告：</span>
	    		<div class="fl">
	        		<up:upload id="artice_up"  businessId="${pqinfoId }" sysKey="${sysKey}" typeId="${attachTypeId }" auto="true" exts="png,jpeg,jpg,bmp,git" />
					<up:show showId="artice_show"  businessId="${pqinfoId }" sysKey="${sysKey}" typeId="${attachTypeId }"/>
				</div>
	 		</li>
		     <%--
		     <li class="col-md-12 p0 mt10">
			   <span class="">质检报告：</span>
			   <div class="fl " id="uploadAttach" >
			  	 <input id="pic" type="file" class="toinline" name="attaattach"/>
		       </div>
			 </li>
		      --%>
   		</ul>

  		<div  class="col-md-12 col-sm-12 col-xs-12 tc mt20">
    			<button class="btn btn-windows save" type="submit">保存</button>
    			<button class="btn btn-windows back" onclick="goback()" type="button">返回</button>
  		</div>
  		</div>
  	</form>
 </div>

</body>
</html>
