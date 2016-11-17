<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>添加采购合同</title>

<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/public/select2/js/select2.js"></script>
<link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet" />
	
<script type="text/javascript">
  function contractType(type){ 
	  $("#contractName").val("");
	  $("#supplierName").val("");
	  $("#contractCode").val("");
	  $("#money").val("");
	  $("#purchaseDepName").val("");
	  $("#contract").select2("val", "");
	  $("#contract").empty();
	  $.ajax({
		  contentType: "application/json;charset=UTF-8",
		  url:"${pageContext.request.contextPath}/appraisalContract/selectContract.do?purchaseType="+type,
	      type:"POST",
	      dataType: "json",
	      success:function(contracts){
	    	  if(contracts){
	    		  $("#contract").append("<option></option>");
	    		  $.each(contracts,function(i,contract){
	    			  if(contract.name != null && contract.name != ''){
	    				  $("#contract").append("<option value="+contract.id+">"+contract.name+"</option>");
	    			  }
	    		  });
	    	  }
	       }
	  });	
  }
  
  $(function(){
	  $("#contract").select2();
	  $("#contract").select2("val","${appraisalContract.purchaseContract.id}");
	  $("#purchaseType").val("${appraisalContract.purchaseType}");
  })
  
  function contractInfo(){
	  $("#contractName").val("");
	  $("#supplierName").val("");
	  $("#contractCode").val("");
	  $("#money").val("");
	  $("#purchaseDepName").val("");
	  var id = $("#contract").val();
	  $.ajax({
		  url:"${pageContext.request.contextPath}/appraisalContract/selectContractId.do?id="+id,
	      type:"POST",
	      success:function(contract){
	    	  var con = JSON.parse(contract);
	    	  $("#contractName").val(con.name);
	    	  $("#supplierName").val(con.supplierDepName);
	    	  $("#contractCode").val(con.code);
	    	  $("#money").val(con.money);
	    	  $("#purchaseDepName").val(con.purchaseDepName);
	      }
	  });
  }
  
  function goBack(){
	  window.location.href="${pageContext.request.contextPath}/appraisalContract/select.html";
  }
  
</script>
  
  </head>

 <body>
   
   <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">添加审价合同</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
   
   <form  action="${pageContext.request.contextPath}/appraisalContract/save.html" method="post">
   
	<div class="container">
	 	<div class="headline-v2">
	  		 <h2>添加审价合同</h2>
	 	</div>
	 	
	 	<form id="form1">
	 	<div class="container padding-left-25 padding-right-25">
			<table class="table table-bordered">
				 <tobody>
				  	<tr>
				 		<td width="25%" class="bggrey tr"><div class="star_red">＊</div>合同类型：</td>
				 		<td width="25%">
				 			<select class="w230" id="purchaseType" name=purchaseType onchange="contractType(this.options[this.selectedIndex].value)">
				 				<option value=""></option>
				 				<option value="单一来源">单一来源</option>
				 				<option value="询价">询价</option>
				 				<option value="邀请招标">邀请招标</option>
				 				<option value="公开招标">公开招标</option>
				 				<option value="竞价性谈判">竞价性谈判</option>
				 			</select>
				 			<div class="red f12 clear">${ERR_purchaseType}</div>
				 		</td>
				 		<td width="25%" class="bggrey tr"><div class="star_red">＊</div>合同名称：</td>
				 		<td width="25%">
				 			<select class="w230" id="contract" name="contractId" onchange="contractInfo()">
				 			</select>
				 			<input type="hidden" id="contractName" name="name" value="${appraisalContract.name }">
				 			<div class="red f12 clear">${ERR_contractId}</div>
				 		</td>
				 	</tr>
				 	<tr>
				 		<td width="25%" class="bggrey tr">供应商名称：</td>
				 		<td width="25%">
				 			<input id="supplierName" name="supplierName" value="${appraisalContract.supplierName }" type="text" class="w230 mb0 border0" readonly>
				 		</td>
				 		<td width="25%" class="bggrey tr">合同编号：</td>
				 		<td width="25%" >
				 			<input id="contractCode" name="code" value="${appraisalContract.code }" type="text" class="w230 mb0 border0" readonly>
				 		</td>
				 	</tr>
				 	<tr>
				 		<td width="25%" class="bggrey tr">采购机构：</td>
				 		<td width="25%">
				 			<input id="purchaseDepName" name="purchaseDepName" value="${appraisalContract.purchaseDepName }" type="text" class="w230 mb0 border0" readonly>
				 		</td>
				 		<td width="25%" class="bggrey tr">合同金额：</td>
				 		<td width="25%">
				 			<input id="money" name="money" type="text" value="${appraisalContract.money }" class="w230 mb0 border0" readonly>
				 		</td>
				 	</tr>
				 </tobody>
			</table>
        </div>
        </form>
        
	 	<div  class="col-md-12">
	   		<div class="mt40 tc mb50">
			    <button class="btn btn-windows add" type="submit" id="submit" name="submit">确定</button>
			    <button class="btn btn-windows cancel" type="button" onclick="goBack()">取消</button>
			</div>
		</div>
	
	</div>	
	
	</form>	
		  
 </body>
</html>
