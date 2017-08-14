<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
  <%@ include file="/WEB-INF/view/common.jsp" %>
</head>
<body>

	<!-- 面包屑导航开始 -->
	<div class="margin-top-10 breadcrumbs ">
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
			<a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/expertAudit/list.html?sign=3')">专家复审分配列表</a>
			</c:if>
		</li>
	</ul>
	<div class="clear"></div>
	</div>
	</div>
	<!-- 面包屑导航结束 -->

  <!-- 内容开始 -->
  <div class="container">
    <div class="headline-v2"><h2>专家复审分配列表</h2></div>
    
    <div class="search_detail">
      <form id="form_id" action="${pageContext.request.contextPath}/expertAudit/basicInfo.html" method="post">
        <input name="expertId" type="hidden" />
        <input name="sign" type="hidden" value="${sign }"/>
        <input name="tableType" type="hidden" value=""/>
      </form>
      <form action="${pageContext.request.contextPath}/expertAgainAudit/againAuditList.html" method="post" id="formSearch" class="mb0">
        <input type="hidden" name="pageNum" id="pageNum">
        <input type="hidden" name="sign" value="${sign }">
        <ul class="demand_list">
          <li>
            <label class="fl">采购机构：</label>
            <input type="text" name="relName" value="${relName }">
          </li>
          <li>
            <label class="fl">提交复审时间：</label>
            <span>
              <input id="auditAt" name="auditAt" class="Wdate w178 fl" value='<fmt:formatDate value="${auditAt}" pattern="YYYY-MM-dd"/>' type="text" onClick="WdatePicker()">
            </span>
          </li>
          <li>
            <button type="button" class="btn mb5" onclick="againAudit_search()">查询</button>
            <button type="button" class="btn mb5" onclick="resetForm()">重置</button>
          </li>
        </ul>
        <div class="clear"></div>
      </form>
    </div>
      
    <!-- 表格开始-->
    <div class="col-md-12 pl20 mt10 mb10">
      <button class="btn btn_create_review_batches" type="button">创建复审批次</button>
    </div>

    <div class="content table_box">
      <table class="table table-bordered table-condensed table-hover table-striped againAudit_table">
        <thead>
          <tr>
            <th class="info w50">选择</th>
            <th class="info w100">序号</th>
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
      
  </div>
  <!-- 内容结束 -->
  
  <!-- 弹出框 -->
  <div class="dnone col-xs-12 pt20 pb20" id="create_review_batches">
    <div class="search_detail ml0 mt0 mb10">
      <ul class="demand_list">
        <li>
          <label class="fl">批次名称：</label>
          <input type="text" name="" value="">
        </li>
        <li>
          <label class="fl">批次编号：</label>
          <span>
            <input type="text" name="" value="">
          </span>
        </li>
      </ul>
      <div class="clear"></div>
    </div>
    
    <table class="table table-bordered table-hover">
      <thead>
        <tr>
          <th class="info w50">序号</th>
          <th class="info">专家姓名</th>
          <th class="info">专业职称</th>
          <th class="info">提交复审时间</th>
        </tr>
      </thead>
      <tbody id="crb_content"></tbody>
    </table>
  </div>
  <!-- End 弹出框 -->
  
  <script src="${pageContext.request.contextPath}/js/ses/ems/againAudit/list.js"></script>
  <script src="${pageContext.request.contextPath}/js/ses/ems/againAudit/processing.js"></script>
  <script>
    var ajax_url = '${pageContext.request.contextPath}/expertAgainAudit/againAuditList.do';
    
    $(function () {
      $('#list_content').listConstructor({
        url: ajax_url
      });
      
      // 搜索
      var relName = $('[name=relName]').val();  // 获取采购机构名称
      var auditAt = $('[name=auditAt]').val();  // 获取提交复审时间
      againAudit_search([relName,auditAt], ajax_url);
      
      // 创建复审批次
      $('.btn_create_review_batches').bind('click', function () {
        var select_ids = "";  // 储存id的数组
        if ($('.againAudit_table').find('.select_item').length > 0) {
	        $('.againAudit_table').find('.select_item').each(function () {
	          if ($(this).is(':checked')) {
        		  select_ids += $(this).val()+",";
	          }
	        });
	        select_ids = select_ids.substring(0, select_ids.length-1);
        }
        create_review_batches(ajax_url, select_ids);
      });
    });
  </script>
    
</body>
</html>