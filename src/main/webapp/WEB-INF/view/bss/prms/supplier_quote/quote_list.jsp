<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'expert_list.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  <script type="text/javascript">
	//查看供应商报价
	 function supplierView(supplierId){
	    var projectId=$("#projectId").val();
		location.href="${pageContext.request.contextPath}/packageExpert/supplierQuote.html?projectId="+projectId+"&supplierId="+supplierId;
	 }
	 
function ycDiv(obj, index){
	if ($(obj).hasClass("jbxx") && !$(obj).hasClass("zhxx")) {
		$(obj).removeClass("jbxx");
		$(obj).addClass("zhxx");
	} else {
		if ($(obj).hasClass("zhxx") && !$(obj).hasClass("jbxx")) {
			$(obj).removeClass("zhxx");
			$(obj).addClass("jbxx");
		}
	}
	
	var divObj = new Array();
	divObj = $(".p0" + index);
	for (var i =0; i < divObj.length; i++) {
    	if ($(divObj[i]).hasClass("p0"+index) && $(divObj[i]).hasClass("hide")) {
    		$(divObj[i]).removeClass("hide");
    	} else {
    		if ($(divObj[i]).hasClass("p0"+index)) {
    			$(divObj[i]).addClass("hide");
    		}
    	}
	};
}
  </script>
  <body>
	    <h2 class="list_title">供应商报价信息</h2>
   		<div class="clear">
<c:set value="1" var ="count"></c:set>
<c:forEach items="${treeMap }" var="treemap" varStatus="vsKey">
	<c:forEach items="${treemap.key }" var="treemapKey" varStatus="vs">
		<div>
			 <h2 onclick="ycDiv(this,'${index}')" class="count_flow jbxx hand">包名:<span class="f14 blue">${fn:substringBefore(treemapKey, "|")}</span>
			 </h2>
        </div>
        <div class="p0${index}">
		<table class="table table-bordered table-condensed mt5">
			<thead>
				<tr>
					<th class="w50 info">序号</th>
				  	<th class="info">供应商名称</th>
				  	<th class="info">报价(单位：万元)</th>
			    </tr>
			</thead>
		<c:forEach items="${treemap.value}" var="treemapValue" varStatus="vs">
				<c:set value="${count+1 }" var="index"></c:set>
				<tr>
				    <td class="tc w50">${vs.index+1}</td>
				    <td class="tc">${treemapValue.suppliers.supplierName}</td>
					<td class="tc">${treemapValue.total}</td>
			    </tr>
		</c:forEach>
		</table>
		</div>
	</c:forEach>
</c:forEach>
</div>
  </body>
</html>
