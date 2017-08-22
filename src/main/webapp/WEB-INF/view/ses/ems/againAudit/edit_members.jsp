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
    <div class="headline-v2"><h2>配置审核组成员</h2></div>
    
    <!-- 表格开始-->
    <div class="col-md-12 pl20 mt10 mb10">
      <button type="button" class="btn" onclick="add_members()">添加</button>
      <button type="button" class="btn">删除</button>
      <button type="button" class="btn">设置密码</button>
    </div>
    
    <div class="content table_box">
      <table class="table table-bordered table-condensed table-hover table-striped againAudit_table">
        <thead>
          <tr>
            <th class="info w50">选择</th>
            <th class="info">用户名</th>
            <th class="info">专家姓名</th>
            <th class="info">单位</th>
            <th class="info">技术职称（职务）</th>
          </tr>
        </thead>
        <tbody id="list_content"></tbody>
      </table>
      <div id="pagediv" align="right"></div>
    </div>
      
  </div>
  <!-- 内容结束 -->
  
  <!-- 添加审核组成员弹出窗 -->
  <div class="hide mt20" id="modal_addMembers">
  <div class="form-horizontal w100p pl20 pr20 over_hideen">
    <div class="form-group">
      <label class="col-sm-3 text-right pt6">用户名</label>
      <div class="col-sm-9">
        <input type="text" class="form-control w100p" name="loginName" placeholder="请输入用户名">
      </div>
    </div>
    <div class="form-group">
      <label class="col-sm-3 text-right pt6">专家姓名</label>
      <div class="col-sm-9">
        <input type="text" class="form-control w100p" name="relName" placeholder="请输入专家姓名">
      </div>
    </div>
    <div class="form-group">
      <label class="col-sm-3 text-right pt6">单位</label>
      <div class="col-sm-9">
        <input type="text" class="form-control w100p" name="orgName" placeholder="请输入单位">
      </div>
    </div>
    <div class="form-group">
      <label class="col-sm-3 text-right pt6">技术职称（职务）</label>
      <div class="col-sm-9">
        <input type="text" class="form-control w100p" name="duties" placeholder="请输入技术职称（职务）">
      </div>
    </div>
  </div>
  </div>
  <!-- End 添加审核组成员弹出窗 -->
  
  <script src="${pageContext.request.contextPath}/js/ses/ems/againAudit/editMembers.js"></script>
  <script src="${pageContext.request.contextPath}/js/ses/ems/againAudit/processing.js"></script>
  <script>
    var list_url = '${pageContext.request.contextPath}/expertAgainAudit/findExpertReviewTeam.do';  // 列表地址
    var add_url = '${pageContext.request.contextPath}/expertAgainAudit/addExpertReviewTeam.do';  // 添加地址
    var select_ids = [];
    
    $(function () {
      console.log(getUrlParam('groupId'));
      $('#list_content').listConstructor({
        url: list_url,
        groupId: getUrlParam('groupId')
      });
    });
  </script>
    
</body>
</html>