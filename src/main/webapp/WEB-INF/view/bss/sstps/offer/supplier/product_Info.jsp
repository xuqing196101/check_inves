		<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>


<!DOCTYPE HTML >
<html>
  <head>
    
    <%@ include file="../../../../common.jsp"%>
    <title>装备（产品）技术资料概述</title>

<script type="text/javascript">
function goBack(){
	var contractId = $("#contractProductId").val();
	window.location.href="${pageContext.request.contextPath}/offer/selectProduct.html?contractId="+contractId;
}
</script>
  </head>
  
  <body>
  
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
	   <div class="container">
		   <ul class="breadcrumb margin-left-0">
			   <li>
				   <a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
			   </li>
			   <li>
				   <a href="javascript:void(0);"> 保障作业</a>
			   </li>
			   <li>
				   <a href="javascript:void(0);"> 单一来源审价</a>
			   </li>
			   <li>
				   <a href="javascript:jumppage('${pageContext.request.contextPath}/offer/list.html')">供应商报价</a>
			   </li>
		   </ul>
		   <div class="clear"></div>
	   </div>
   </div>
  
  <form action="${pageContext.request.contextPath}/offerProduct/save.html" method="post">
	<div class="container">
	 	<div class="headline-v2">
	  		 <h2>装备（产品）技术资料概述</h2>
	 	</div>
	 	
	 	<input type="hidden" id="contractProductId" name="contractProduct.id" class="w230 mb0" value="${contractProduct.id }">
	 	<input type="hidden" id="id" name="id" class="w230 mb0" value="${productInfo.id }">
	 	
	 	<div class="container padding-left-25 padding-right-25 mt5">
			<ul class="list-unstyled ul_list">
			     <li class="col-md-6 col-sm-6 col-xs-12 pl15">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>产品名称：</span>
				   <div class="input-append input_group col-sm-12 col-xs-12 p0">
			        <input type="text" id="name" name="name" class="col-md-8 " value="${contractProduct.name }" readonly>
			        <div class="cue"></div>
			       </div>
				 </li>
				 <li class="col-md-6 col-sm-6 col-xs-12 pl15">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>设计单位：</span>
				   <div class="input-append input_group col-sm-12 col-xs-12 p0">
			        <input id="designDepartment" name="designDepartment" type="text" class="col-md-8 m0 col-sm-12 col-xs-12" value="${productInfo.designDepartment }" >
			        <div class="cue">${ERR_designDepartment}</div>
			       </div>
				 </li>
				 <li class="col-md-6 col-sm-6 col-xs-12 pl15">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>产品概述：</span>
				   <div class="input-append input_group col-sm-12 col-xs-12 p0">
			        <textarea class="col-md-12 col-sm-12 col-xs-12 h100" id="productOverview" name="productOverview" title="不超过4000个字" placeholder="不超过4000个字">${productInfo.productOverview }</textarea>
			        <div class="cue mt70">${ERR_productOverview}</div>
			       </div>
				 </li>
				 <li class="col-md-6 col-sm-6 col-xs-12 pl15">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>产品生产过程概述：</span>
				   <div class="input-append input_group col-sm-12 col-xs-12 p0">
			        <textarea class="col-md-12 col-sm-12 col-xs-12 h100" id="productProcess" name="productProcess"  title="不超过4000个字" placeholder="不超过4000个字">${productInfo.productProcess }</textarea>
			        <div class="cue mt70">${ERR_productProcess}</div>
			       </div>
				 </li>
				 <li class="col-md-6 col-sm-6 col-xs-12 pl15">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>产品技术状况概述：</span>
				   <div class="input-append input_group col-sm-12 col-xs-12 p0">
			        <textarea class="col-md-12 col-sm-12 col-xs-12 h100" id="productSkill" name="productSkill"  title="不超过4000个字" placeholder="不超过4000个字">${productInfo.productSkill }</textarea>
			        <div class="cue mt70">${ERR_productSkill}</div>
			       </div>
				 </li>
				 <li class="col-md-6 col-sm-6 col-xs-12 pl15">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>结论：</span>
				   <div class="input-append input_group col-sm-12 col-xs-12 p0">
			        <textarea class="col-md-12 col-sm-12 col-xs-12 h100" id="conclusion" name="conclusion"  title="不超过4000个字" placeholder="不超过4000个字">${productInfo.conclusion }</textarea>
			        <div class="cue mt70">${ERR_conclusion}</div>
			       </div>
				 </li>
			</ul>
			
			<%-- <table class="table table-bordered">
				 <tobody>
				  	<tr>
				 		<td width="10%" class="bggrey tr">产品名称：</td>
				 		<td width="25%">
				 			<input type="text" id="name" name="name" class="col-md-8 border0 m0" value="${contractProduct.name }" readonly>
				 		</td>
				 	</tr>
				 	<tr>
				 		<td width="10%" class="bggrey tr"><div class="red star_red">*</div>设计单位：</td>
				 		<td width="25%">
				 			<input id="designDepartment" name="designDepartment" type="text" class="col-md-8 m0 col-sm-12 col-xs-12" value="${productInfo.designDepartment }" >
				 		</td>
				 	</tr>
				 	<tr>
				 		<td width="10%" class="bggrey tr"><div class="red star_red">*</div>产品概述：</td>
				 		<td width="25%">
				 		<textarea class="col-md-8 col-sm-12 col-xs-12 h100" id="productOverview" name="productOverview" title="不超过4000个字" placeholder="不超过4000个字">${productInfo.productOverview } </textarea>
				 		</td>
				 	</tr>
				 	<tr>
				 		<td width="10%" class="bggrey tr"> <div class="red star_red">*</div>产品生产过程概述：</td>
				 		<td width="25%">
				 			<textarea class="col-md-8 col-sm-12 col-xs-12 h100" id="productProcess" name="productProcess"  title="不超过4000个字" placeholder="不超过4000个字">${productInfo.productProcess }</textarea>
				 		</td>
				 	</tr>
				 	<tr>
				 		<td width="10%" class="bggrey tr"><div class="red star_red">*</div>产品技术状况概述：</td>
				 		<td width="25%">
				 			<textarea class="col-md-8 col-sm-12 col-xs-12 h100" id="productSkill" name="productSkill"  title="不超过4000个字" placeholder="不超过4000个字">${productInfo.productSkill }</textarea>
				 		</td>
				 	</tr>
				 	<tr>
				 		<td width="10%" class="bggrey tr"><div class="red star_red">*</div>结论：</td>
				 		<td width="25%">
				 			<textarea class="col-md-8 col-sm-12 col-xs-12 h100" id="conclusion" name="conclusion"  title="不超过4000个字" placeholder="不超过4000个字">${productInfo.conclusion }</textarea>
				 		</td>
				 	</tr>
				 </tobody>
			</table> --%>
        </div>
	 	
	 	<div  class="col-md-12">
	   		<div class="mt40 tc mb50">
			    <button class="btn" type="submit">下一步</button>
			    <button class="btn btn-windows cancel" type="button" onclick="location.href='javascript:history.go(-1);'">取消</button>
			</div>
		</div>
	
	  </div>	
	</form>	
  
  
  </body>
</html>
