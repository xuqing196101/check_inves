<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<%@ include file ="/WEB-INF/view/common/webupload.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
	<title>供应商报价页面</title>
</head>
<body>
<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)">竞价结果查询</a></li>
		   </ul>
        <div class="clear"></div>
      </div>
    </div>
    <!-- 修改订列表开始-->
   <div class="container container_box">
   <div>
   <div>
    <h2 class="count_flow"><i>1</i>竞价基本信息</h2>
		<%@ include file="/WEB-INF/view/bss/ob/biddingSpectacular/biddingInfoCommon.jsp" %>
  </div> 
	<c:if test="${selectInfoByPID!=null and selectInfoByPID.size()>0}">
	 <h2 class="count_flow"><i>2</i>供应商信息</h2>
	   <%@ include file ="/WEB-INF/view/bss/ob/supplier/supplierCommon.jsp" %>
		</c:if>
  </div>
 </div>
</body>
</html>