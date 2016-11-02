<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>添加</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  
  <body>
  
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">添加专项费用明细</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
   
   <form action="<%=basePath %>specialCost/save.html" method="post" enctype="multipart/form-data">
   
   <input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
   
	<div class="container">
	 	<div class="headline-v2">
	  		 <h2>添加专项费用明细</h2>
	 	</div>
	 	
	 	<div class="container padding-left-25 padding-right-25">
			<table class="table table-bordered">
				 <tobody>
				  	<tr>
				 		<td width="25%" class="bggrey tr">项目名称：</td>
				 		<td width="25%">
				 			<input type="text" id="projectName" name="projectName">
				 		</td>
				 		<td width="25%" class="bggrey tr">项目明细：</td>
				 		<td width="25%">
				 			<input id="productDetal" name="productDetal" type="text" class="w230 mb0" >
				 		</td>
				 	</tr>
				 	<tr>
				 		<td width="25%" class="bggrey tr">名称：</td>
				 		<td width="25%" >
				 			<input id="name" name="name" type="text" class="w230 mb0" >
				 		</td>
				 		<td width="25%" class="bggrey tr">规格型号：</td>
				 		<td width="25%" >
				 			<input id="norm" name="norm" type="text" class="w230 mb0" >
				 		</td>
				 	</tr>
				 	<tr>
				 		<td width="25%" class="bggrey tr">计量单位：</td>
				 		<td width="25%" >
				 			<input id="measuringUnit" name="measuringUnit" type="text" class="w230 mb0" >
				 		</td>
				 		<td width="25%" class="bggrey tr">数量（消耗使用）：</td>
				 		<td width="25%" >
				 			<input id="amount" name="amount" type="text" class="w230 mb0" >
				 		</td>
				 	</tr>
				 	<tr>
				 		<td width="25%" class="bggrey tr">单价：</td>
				 		<td width="25%" >
				 			<input id="price" name="price" type="text" class="w230 mb0" >
				 		</td>
				 		<td width="25%" class="bggrey tr">金额：</td>
				 		<td width="25%" >
				 			<input id="money" name="money" type="text" class="w230 mb0" >
				 		</td>
				 	</tr>
				 	<tr>
				 		<td width="25%" class="bggrey tr">分摊数量：</td>
				 		<td width="25%" >
				 			<input id="proportionAmout" name="proportionAmout" type="text" class="w230 mb0" >
				 		</td>
				 		<td width="25%" class="bggrey tr">单位产品分摊额：</td>
				 		<td width="25%" >
				 			<input id="proportionPrice" name="proportionPrice" type="text" class="w230 mb0" >
				 		</td>
				 	</tr>
				 	<tr>
				 		<td width="25%" class="bggrey tr">备注：</td>
				 		<td width="25%" >
				 			<input id="remark" name="remark" type="text" class="w230 mb0" >
				 		</td>
				 	</tr>
				 </tobody>
			</table>
        </div>
        
	 	<div  class="col-md-12">
	   		<div class="mt40 tc mb50">
			    <button class="btn btn-windows save" type="submit">确定</button>
			    <button class="btn btn-windows cancel" type="button" onclick="location.href='javascript:history.go(-1);'">取消</button>
			</div>
		</div>
	
	</div>	
</form>	
		  
  </body>
</html>
