<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML >
<html>
  <head>
  	<jsp:include page="/WEB-INF/view/common.jsp"/>
    <title>包明细列表</title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="${pageContext.request.contextPath}/public/easyui/jquery.easyui.min.js"></script>
	<link href="${pageContext.request.contextPath}/public/easyui/themes/icon.css" media="screen" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/public/easyui/themes/default/easyui.css" media="screen" rel="stylesheet" type="text/css">
  </head>
  
  <body>
    
   <div class="col-md-12 col-sm-12 col-xs-12 p0 over_scroll">
      <div class="padding-top-10 clear">
          <table id="detailtable" class="table table-striped table-bordered table-hover">
			 <thead>
				<tr>
				 <td class="info tc">编号</td>
				 <td class="info tc">物资名称</td>
				 <td class="info tc">规格型号</td>
				 <td class="info tc">质量技术标准</td>
				 <td class="info tc">计量单位</td>
				 <td class="info tc">采购数量</td>
				 <td class="info tc">单价</td>
				 <td class="info tc">备注</td>
				 <!-- <td class="info tc">采购方式</td> -->
				</tr>
			 </thead>
			 <c:forEach items="${theSubject}" var="detail" varStatus="vt">
			    <tr>
			       <td class="tr pr20 pointer">${detail.serialNumber}</td>
			       <td>${detail.goodsName}</td>
			       <td >${detail.stand}</td>
			       <td >${detail.qualitStand}</td>
			       <td>${detail.item}</td>
			       <td class="tr">${detail.purchaseCount}</td>
			       <td class="tr">${detail.unitPrice}</td>
			       <td class="tr">${detail.trademark}</td>
			      <%--  <td>${detail.purchaseType}</td> --%>
			    </tr>
			 
			 </c:forEach>
		   </table>
      </div>
    </div>
  <div class="col-md-12 col-xs-12 col-sm-12 tc mt20">
        <button class="btn btn-windows back" onclick="window.history.go(-1)" type="button">返回</button>
      </div>
   
</body>
</html>
