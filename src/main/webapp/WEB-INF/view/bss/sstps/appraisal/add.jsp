<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>添加采购合同</title>
	
<script type="text/javascript">
  function contractType(type){ 
	  $("#contract").empty();
	  $.ajax({
		  url:"<%=basePath%>appraisalContract/selectContract.do?contractType="+type,
	      type:"POST",
	      success:function(contracts){
	    	  if(contracts){
	    		  $("#contract").html("<option></option>");
	    		  $.each(JSON.parse(contracts),function(i,contract){
	    			  $("#contract").append("<option value="+contract.id+">"+contract.name+"</option>");
	    		  });
	    	  }
	       }
	  });	
  }
  
  function contractInfo(){
	  var id = $("#contract").val();
	  $.ajax({
		  url:"<%=basePath%>appraisalContract/selectContractId.do?id="+id,
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
  
  function check(){
	  var name = $("#contractName").val();
	  alert(name);
	  if(name==null&&name==""){
		  return false;
	  }else{
		  return true;
	  }
		
  }
  
</script>
	
  </head>

 <body>
   
   <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">添加合同</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
   
   <form onsubmit="return check()" action="<%=basePath %>appraisalContract/save.html" method="post" enctype="multipart/form-data">
   
	<div class="container">
	 	<div class="headline-v2">
	  		 <h2>添加合同</h2>
	 	</div>
	 	
	 	<div class="container padding-left-25 padding-right-25">
			<table class="table table-bordered">
				 <tobody>
				  	<tr>
				 		<td width="25%" class="bggrey tr">合同类型：</td>
				 		<td width="25%">
				 			<select class="w230" id="type" name="type" onchange="contractType(this.options[this.selectedIndex].value)">
				 				<option value="0">单一来源</option>
				 				<option value="1">采购合同</option>
				 			</select>
				 		</td>
				 		<td width="25%" class="bggrey tr">合同名称：</td>
				 		<td width="25%">
				 			<select class="w230" id="contract" name="contractId" onchange="contractInfo()">
				 			</select>
				 			<input type="hidden" id="contractName" name="name">
				 		</td>
				 	</tr>
				 	<tr>
				 		<td width="25%" class="bggrey tr">供应商名称：</td>
				 		<td width="25%">
				 			<input id="supplierName" name="supplierName" type="text" class="w230 mb0" readonly>
				 		</td>
				 		<td width="25%" class="bggrey tr">合同编号：</td>
				 		<td width="25%" >
				 			<input id="contractCode" name="code" type="text" class="w230 mb0" readonly>
				 		</td>
				 	</tr>
				 	<tr>
				 		<td width="25%" class="bggrey tr">采购机构：</td>
				 		<td width="25%">
				 			<input id="purchaseDepName" name="purchaseDepName" type="text" class="w230 mb0" readonly>
				 		</td>
				 		<td width="25%" class="bggrey tr">合同金额：</td>
				 		<td width="25%">
				 			<input id="money" name="money" type="text" class="w230 mb0" readonly>
				 		</td>
				 	</tr>
				 </tobody>
			</table>
        </div>
	 	
	 	<div  class="col-md-12">
	   		<div class="mt40 tc mb50">
			    <button class="btn btn-windows add" type="submit" id="submit" name="submit">确定</button>
			    <button class="btn btn-windows cancel" type="button" onclick="location.href='javascript:history.go(-1);'">取消</button>
			</div>
		</div>
	
	</div>	
	
	</form>	
		  
 </body>
</html>
