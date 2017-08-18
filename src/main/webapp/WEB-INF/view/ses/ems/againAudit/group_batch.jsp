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
    <div class="headline-v2"><h2>批次专家分组</h2></div>
    
    <!-- 表格开始-->
    <div class="col-md-12 pl20 mt10 mb10">
      <button type="button" class="btn" onclick="found_new_batch('${pageContext.request.contextPath}/expertAgainAudit/expertGrouping.do')">创建新分组</button>
      <button type="button" class="btn" onclick="add_hasGroud('${pageContext.request.contextPath}/expertAgainAudit/expertAddGroup.do')">添加至已有分组</button>
    </div>
    
    <div class="content table_box">
      <table class="table table-bordered table-condensed table-hover table-striped againAudit_table">
        <thead>
          <tr>
            <th class="info w50">选择</th>
            <th class="info w100">批次编号</th>
            <th class="info">采购机构</th>
            <th class="info">专家姓名</th>
            <th class="info">性别</th>
            <th class="info">工作单位</th>
            <th class="info">专业职称</th>
            <th class="info">提交复审时间</th>
          </tr>
        </thead>
        <tbody id="list_content"></tbody>
      </table>
      <div id="pagediv" align="right"></div>
    </div>
    
    <!-- 专家分组 -->
    <div class="pl20" id="group_batch_box"></div>
    <!-- End 专家分组 -->
    
    <div class="text-center mt20">
      <button type="button" class="btn">完成</button>
      <button type="button" class="btn">取消</button>
    </div>
      
  </div>
  <!-- 内容结束 -->
  
  <script src="${pageContext.request.contextPath}/js/ses/ems/againAudit/batchGroup.js"></script>
  <script src="${pageContext.request.contextPath}/js/ses/ems/againAudit/processing.js"></script>
  <script>
    var list_url = '${pageContext.request.contextPath}/expertAgainAudit/findBatchDetails.do';  // 列表地址
    var newGroup_url = '${pageContext.request.contextPath}/expertAgainAudit/findExpertGroupDetails.do';  // 新分组地址
    var del_url = '${pageContext.request.contextPath}/expertAgainAudit/delExpertGroupDetails.do';  // 删除地址
    var select_ids = [];  // id集合
    var batch_id = '';  // 批次id
    
    $(function () {
      init_list();
    });
    
    function init_list() {
      $('#list_content').listConstructor({
        url: list_url,
        newGroup_url: newGroup_url,
        data: {
          batchId: getUrlParam('batchId'),
          status: '14'
        },
        data_new: {
          batchId: getUrlParam('batchId')
        }
      });
    }
  </script>
    
</body>
</html>