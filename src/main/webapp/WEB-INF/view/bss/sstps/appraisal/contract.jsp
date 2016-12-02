<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>合同信息</title>
    
    
<script type="text/javascript">
function dayin() {
	var LODOP = getLodop();
	if (LODOP) {
		LODOP.ADD_PRINT_HTM(0, 0, "100%", "100%",
				document.getElementById("div_print").innerHTML);
		LODOP.PREVIEW();
	}
}

function sub(){
	var id = $("#id").val();
	window.location.href = "${pageContext.request.contextPath}/appraisalContract/update.html?id="+id;
}



</script>
  </head>
  
  <body>	
 		 
 <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">审价合同</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
   
	<div class="container">
	 	<div class="headline-v2">
	  		 <h2>添加合同</h2>
	 	</div>
	 	<input type="hidden" id="id" value="${contracts.id }" name="id">
	 	<div class="container padding-left-25 padding-right-25" id="div_print">
			<table class="table table-bordered">
				 <tobody>
				  	<tr>
				 		<td width="25%" class="bggrey tr">合同类型：</td>
				 		<td width="25%">
				 			${contracts.purchaseType }
				 		</td>
				 		<td width="25%" class="bggrey tr">合同名称：</td>
				 		<td width="25%">
				 			${contracts.name }
				 		</td>
				 	</tr>
				 	<tr>
				 		<td width="25%" class="bggrey tr">供应商名称：</td>
				 		<td width="25%">
				 			${contracts.supplierName }
				 		</td>
				 		<td width="25%" class="bggrey tr">合同编号：</td>
				 		<td width="25%" >
				 			${contracts.code }
				 		</td>
				 	</tr>
				 	<tr>
				 		<td width="25%" class="bggrey tr">采购机构：</td>
				 		<td width="25%">
				 			${contracts.purchaseDepName }
				 		</td>
				 		<td width="25%" class="bggrey tr">合同金额(万元)：</td>
				 		<td width="25%">
				 			${contracts.money }
				 		</td>
				 	</tr>
				 </tobody>
			</table>
        </div>
	 	
	 	<div  class="col-md-12">
	   		<div class="mt40 tc mb50">
			    <button class="btn" type="button" onclick="dayin()">打印</button>
			    <button class="btn btn-windows git" type="button" onclick="sub()">提交</button>
			    <button class="btn btn-windows cancel" type="button" onclick="location.href='javascript:history.go(-1);'">取消</button>
			</div>
		</div>
	
	</div>	
  
  </body>
</html>
