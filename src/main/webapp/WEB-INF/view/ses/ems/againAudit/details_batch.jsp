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
    <div class="headline-v2"><h2>2017年 专家复审 第三批次</h2></div>
    
    <!-- 表格开始-->
    <div class="col-md-12 pl20 mt10 mb10">
      <button type="button" class="btn btn-windows group" onclick="jump_batchGroup()">批次分组</button>
      <button type="button" class="btn btn-windows config" onclick="jump_auditBatch()">审核配置</button>
      <button type="button" class="btn btn-windows apply">上传复审批准件</button>
    </div>
    
    <div class="content table_box">
      <table class="table table-bordered table-condensed table-hover table-striped againAudit_table">
        <thead>
          <tr>
            <th class="info w150">批次编号</th>
            <th class="info">采购机构</th>
            <th class="info">专家姓名</th>
            <th class="info w50">性别</th>
            <th class="info w300">工作单位</th>
            <th class="info">专业职称</th>
            <th class="info">提交复审时间</th>
            <th class="info">审核组</th>
            <th class="info">复审专家</th>
            <th class="info">审核状态</th>
            <th class="info">审核时间</th>
          </tr>
        </thead>
        <tbody id="list_content"></tbody>
      </table>
      <div id="pagediv" align="right"></div>
      <div class="mt20 text-center">
        <button type="button" class="btn btn-windows back" onclick="javascript:history.back()">返回</button>
      </div>
    </div>
      
  </div>
  <!-- 内容结束 -->
  
  <script src="${pageContext.request.contextPath}/js/ses/ems/againAudit/batchDetails.js"></script>
  <script src="${pageContext.request.contextPath}/js/ses/ems/againAudit/processing.js"></script>
  <script>
    var list_url = '${pageContext.request.contextPath}/expertAgainAudit/findBatchDetails.do';  // 列表地址
    
    $(function () {
      $('#list_content').listConstructor({
        url: list_url,
        data: {
          batchId: getUrlParam('batchId')
        }
      });
    });
    
    //  跳转到批次分组
    function jump_batchGroup() {
      window.location.href = '${pageContext.request.contextPath}/expertAgainAudit/groupBatch.html?batchId='+getUrlParam('batchId');
    }
    // 跳转批次审核
    function jump_auditBatch() {
      window.location.href = '${pageContext.request.contextPath}/expertAgainAudit/auditBatch.html?batchId='+getUrlParam('batchId');
    }
  </script>
    
</body>
</html>