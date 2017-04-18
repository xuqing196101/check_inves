<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
	<title>竞价信息查看页面</title>
	<%@ include file="/WEB-INF/view/common.jsp" %>
	<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
	<%@ include file="/WEB-INF/view/bss/ob/common/obShowProductCommon.jsp"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/ajaxfileupload.js"></script>
</head>
<body>
<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)">竞价管理</a></li><li><a href="javascript:void(0)">竞价信息查看</a></li>
		   </ul>
        <div class="clear"></div>
      </div>
    </div>
   <!-- 修改订列表开始-->
   <div class="container container_box" onmouseover="closePrompt()">
   <div class="mt10">
	    <button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
   </div> 
   <div>
    <h2 class="count_flow"><i>1</i>竞价基本信息</h2>
    <div class="ul_list">
		<%@ include file="/WEB-INF/view/bss/ob/biddingSpectacular/biddingInfoCommon.jsp" %>
     </div>
  </div> 
  <div class="clear" ></div>
  <div>
	 <h2 class="count_flow"><i>2</i>竞价规则详情</h2>
	   <%@ include file="/WEB-INF/view/bss/ob/biddingRules/ruleCommon.jsp" %>
  </div>
  <div>
	 <h2 class="count_flow"><i>3</i>产品信息</h2>
	   <%@ include file="/WEB-INF/view/bss/ob/supplier/productIssueInfoCommon.jsp" %>
  </div>
 </div>
</body>
</html>