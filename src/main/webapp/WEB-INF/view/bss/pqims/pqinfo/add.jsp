<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    
    <title>登记质检报告</title>
   
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">	
	
  </head>
    <script type="text/javascript">
    function selectByCode(){
    		var code= $(".contract_code").val();
			$.ajax({
				type:"POST",
				dataType:"json",
				url:"${pageContext.request.contextPath}/purchaseContract/selectByCode.do?code="+code,
				success:function(json){
					if(json.code==("ErrCode")){
						$(".contract_id").val(json.id);
	  					 $(".contract_name").val(json.name);
	  					 $(".supplier_id").val(json.supplierPurId);	
	  					 $(".supplier_name").val(json.supplierDepName);
						 $("#contractCodeErr").text("合同编号不存在");
					}else{
  					 $(".contract_id").val(json.id);
  					 $(".contract_name").val(json.name);
  					 $(".supplier_id").val(json.supplierPurId);	
  					 $(".supplier_name").val(json.supplierDepName);
  					 $("#contractCodeErr").text("");
					}
	       		}
	       	});
	}
    
    function addAttach(){
		html="<input id='pic' type='file' class='toinline span2' name='report'/><a href='#' onclick='deleteattach(this)' class='toinline red redhover'>x</a><br/>";
		$("#uploadAttach").append(html);
	}
    function deleteattach(obj){
		$(obj).prev().remove();
		$(obj).next().remove();
		$(obj).remove();
	}
    $(function(){
    	if(${pqinfo.projectType}!=null&&${pqinfo.projectType}!=""){
			$("#projectType").val('${pqinfo.projectType}');
		}else{
			$("#projectType").val('-请选择-');
		}
    	if(${pqinfo.type}!=null&&${pqinfo.type}!=""){
			$("#type").val('${pqinfo.type}');
		}else{
			$("#type").val('-请选择-');
		}
    	if(${pqinfo.conclusion}!=null&&${pqinfo.conclusion}!=""){
			$("#conclusion").val('${pqinfo.conclusion}');
		}else{
			$("#conclusion").val('-请选择-');
		}
	});
    </script>
<body>
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">保障作业</a></li><li><a href="#">产品质量管理</a></li><li class="active"><a href="#">登记质检报告</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
<!-- 新增模板开始-->
   <div class="container">
   		<form action="${pageContext.request.contextPath}/pqinfo/save.html" method="post" enctype="multipart/form-data">
   		<div class="headline-v2">
   			<h2>登记质检报告</h2>
   		</div>
   		<ul class="list-unstyled list-flow p0_20">
   			<input type="hidden" class="contract_id" name="contract.id">
		     <li class="col-md-6  p0 ">
			   <span class="">合同编号：</span>
			   <div class="input-append">
		        <input class="span2 contract_code" name="contract.code" id="contract_code" type="text" onblur="selectByCode()" value="${pqinfo.contract.code }">
		        <div id="contractCodeErr" class="validate">${ERR_contract_code}</div>
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">合同名称：</span>
		        <div class="input-append ">
		        	<input class="span2 contract_name" value = '${pqinfo.contract.name}' type="text"  readonly="readonly" >
		        	<div class="validate"></div>
       			</div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">供应商组织机构代码：</span>
		        <div class="input-append ">
		        	<input class="span2 supplier_id" type="text"  readonly="readonly" value = '${pqinfo.contract.supplierPurId}'>
		        	<div class="validate"></div>
       			</div>
			 </li>
		     <li class="col-md-6  p0 ">
			   <span class="">供应商名称：</span>
			   <div class="input-append">
		        <input class="span2 supplier_name" type="text"  value = '${pqinfo.contract.supplierDepName}' readonly="readonly">
		        <div class="validate"></div>
		       </div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="fl">项目类别：</span>
			   <div class="select_common mb10">
		        	<select id="projectType" name ="projectType" class="w220" >
						<option value="-请选择-">请选择</option>
						<option value="询价">询价</option>
						<option value="单一来源">单一来源</option>
						<option value="邀请招标">邀请招标</option>
						<option value="公开招标">公开招标</option>
						<option value="竞争性谈判">竞争性谈判</option>
	  				</select> 
	  				<div class="validate">${ERR_projectType}</div>
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">质检单位：</span>
		        <div class="input-append ">
		        	<input class="span2" name="unit" value = '${pqinfo.unit}' type="text">
		        	<div class="validate">${ERR_unit}</div>
       			</div>
			 </li>
		     <li class="col-md-6  p0 ">
			   <span class="fl">质检类型：</span>
			   <div class="select_common mb10">
		        	<select id="type" name =type class="w220" >
						<option value="-请选择-">请选择</option>
						<option value="首件检验">首件检验</option>
						<option value="生产验收">生产验收</option>
						<option value="出厂验收">出厂验收</option>
						<option value="到货验收">到货验收</option>
	  				</select> 
	  				<div class="validate">${ERR_type}</div>
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">质检地点：</span>
		        <div class="input-append ">
		        	<input class="span2" name="place" value = '${pqinfo.place}' type="text">
		        	<div class="validate">${ERR_place}</div>
       			</div>
			 </li>
			<li class="col-md-6  p0 ">
			   <span class="">质检日期：</span>
			   <div class="input-append">
		        <input class="span2 Wdate" name="dateString" type="text" value="<fmt:formatDate value='${pqinfo.date}' pattern='yyyy-MM-dd'/>" onfocus="WdatePicker({isShowWeek:true})">
		        <div class="validate">${ERR_pqdate}</div>
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">质检人员：</span>
		        <div class="input-append ">
		        	<input class="span2" name="inspectors"  value = '${pqinfo.inspectors}'  type="text">
		        	<div class="validate">${ERR_inspectors}</div>
       			</div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="">质检情况：</span>
			   <div class="input-append">
		        <input class="span2" name="condition" type="text" value = '${pqinfo.condition}'  >
		        <div class="validate">${ERR_condition}</div>
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="fl">质检结论：</span>
		        <div class="select_common mb10">
		        	<select id="conclusion" name ="conclusion" class="w220" >
						<option value="-请选择-" >请选择</option>
						<option value="合格">合格</option>
						<option value="不合格">不合格</option>
	  				</select> 
	  				<div class="validate">${ERR_conclusion}</div>
       			</div>
			 </li>
			 <li class="col-md-12  p0 ">
			   <span class="fl">详细情况：</span>
			   <div class="col-md-12 pl200 fn mt5 pwr9">
		        <textarea class="text_area col-md-12 " name="detail" title="不超过800个字" placeholder="不超过800个字">${pqinfo.detail}</textarea>
		        <div class="red">${ERR_detail}</div>
		       </div>
			 </li>
   		</ul>
   		<ul class="list-unstyled list-flow p0_20">
		     <li class="col-md-6  p0 ">
			   <span class="">质检报告：</span>
			   <div class="fl " id="uploadAttach" >
			  	 <input id="pic" type="file" class="toinline" name="attaattach"/>
		       </div>
			 </li>
		</ul>
  		<div  class="col-md-12 tc mt20">
    			<button class="btn btn-windows save" type="submit">保存</button>
    			<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
  		</div>
  		
  	</form>
 </div>
 
</body>
</html>
