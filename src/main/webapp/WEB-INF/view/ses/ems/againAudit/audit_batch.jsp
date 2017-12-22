<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
  <%@ include file="/WEB-INF/view/common.jsp" %>
</head>
<body>

	<!-- 面包屑导航开始 -->
	<div class="margin-top-10 breadcrumbs">
	<div class="container">
	<ul class="breadcrumb margin-left-0">
		<li>
		  <a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')">首页</a>
		</li>
		<li>
		  <a href="javascript:void(0)">支撑系统</a>
		</li>
		<li>
		  <a href="javascript:void(0)">专家管理</a>
		</li>
		<li>
			<c:if test="${sign == 1}">
			<a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/expertAudit/list.html?sign=1')">专家初审</a>
			</c:if>
			<c:if test="${sign == 2}">
			<a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/expertAudit/list.html?sign=2')">专家复审</a>
			</c:if>
			<c:if test="${sign == 3}">
			<a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/expertAudit/list.html?sign=3')">专家复审批次列表</a>
			</c:if>
		</li>
	</ul>
	<div class="clear"></div>
	</div>
	</div>
	<!-- 面包屑导航结束 -->

  <!-- 内容开始 -->
  <div class="container">
    <div class="headline-v2"><h2>批次审核</h2></div>
    
    <!-- 专家分组 -->
    <div class="pl20" id="group_batch_box"></div>
    
    <div class="text-center mt20">
      <button type="button" class="btn btn-windows back" onclick="javascript:history.back()">返回</button>
    </div>
    <!-- End 专家分组 -->
      
  </div>
  <!-- 内容结束 -->
  
  <script src="${pageContext.request.contextPath}/js/ses/ems/againAudit/auditBatch.js"></script><!-- 列表构造 -->
  <script src="${pageContext.request.contextPath}/js/ses/ems/againAudit/processing.js"></script><!-- 公共方法库 -->
  <script>
    var root_url = '${pageContext.request.contextPath}';  // 根目录地址
    
    // loading
    var index_load = layer.load(1, {
      shade: [1, '#FFF']
    });
    
    $(function () {
      $('#group_batch_box').listConstructor();
    });
  </script>
    
</body>
</html>