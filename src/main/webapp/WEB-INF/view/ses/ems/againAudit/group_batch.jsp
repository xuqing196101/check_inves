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
      <button type="button" class="btn btn-windows add" onclick="add_batch()">创建新分组</button>
      <button type="button" class="btn btn-windows addTo" onclick="show_hasGroud()">添加至已有分组</button>
      <span class="pl20">自动分为</span> <input type="text" name="" id="autoGroup_num" value="" class="m0 w50"> <span>组</span>
      <button type="button" class="btn" onclick="auto_group()">自动分组</button>
    </div>
    
    <div class="content table_box">
      <table class="table table-bordered table-condensed table-hover table-striped againAudit_table">
        <thead>
          <tr>
            <th class="info w50">选择</th>
            <th class="info w200">批次编号</th>
            <th class="info w100">采购机构</th>
            <th class="info w100">专家姓名</th>
            <th class="info w50">性别</th>
            <th class="info w300">工作单位</th>
            <th class="info w100">专业职称</th>
            <th class="info w150">提交复审时间</th>
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
      <button type="button" class="btn btn-windows save" onclick="finish_groupBatch()">完成</button>
      <%-- <button type="button" class="btn btn-windows reset" onclick="cancel_groupBatch()">取消</button> --%>
      <button type="button" class="btn btn-windows reset" onclick="javascript:history.back()">返回</button>
    </div>
      
  </div>
  <!-- 内容结束 -->
  
  <!-- 已有分组弹窗内容 -->
  <div id="group_list"></div>
  <!-- End 已经分组弹窗内容 -->
  
  <script src="${pageContext.request.contextPath}/js/ses/ems/againAudit/batchGroup.js"></script>
  <script src="${pageContext.request.contextPath}/js/ses/ems/againAudit/processing.js"></script>
  <script>
    var list_url = '${pageContext.request.contextPath}/expertAgainAudit/findBatchDetails.do';  // 列表地址
    var newGroup_url = '${pageContext.request.contextPath}/expertAgainAudit/findExpertGroupDetails.do';  // 新分组地址
    var del_url = '${pageContext.request.contextPath}/expertAgainAudit/delExpertGroupDetails.do';  // 删除地址
    var add_url = '${pageContext.request.contextPath}/expertAgainAudit/expertGrouping.do';  // 添加到新分组地址
    var getGroup_url = '${pageContext.request.contextPath}/expertAgainAudit/getGroups.do';  // 获取已有分组
    var addGroup_url = '${pageContext.request.contextPath}/expertAgainAudit/expertAddGroup.do';  // 添加到已有分组地址
    var finish_url = '${pageContext.request.contextPath}/expertAgainAudit/checkComplete.do';  // 完成校验地址
    var autoGroup_url = '${pageContext.request.contextPath}/expertAgainAudit/automaticGrouping.do';  // 自动分组地址
    var select_ids = [];  // id集合
    var batch_id = '';  // 批次id
    var select_groupId = '';  // 选择的分组id
    
    $(function () {
      init_list(list_url, newGroup_url);
    });
  </script>
    
</body>
</html>