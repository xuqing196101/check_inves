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
            <select class="w220" name="orgName"></select>
          </li>
          <li>
            <label class="fl">初审合格时间：</label>
            <span>
              <input id="quaStartDate" name="quaStartDate" class="Wdate w220" type="text" value="" onfocus="var endDate=$dp.$('endDate');WdatePicker({onpicked:function(){quaStartDate.focus();},maxDate:'#F{$dp.$D(\'quaEdndate\')}'})">
              <span>-</span>
              <input id="quaEdndate" name="quaEdndate" value="" class="Wdate w220" type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'quaStartDate\')}'})">
            </span>
          </li>
          <li class="mt10">
            <label class="fl">专家类型：</label>
            <select class="w220" name="expertsFrom"></select>
          </li>
          <li class="mt10 select2-nosearch">
            <label class="fl">专家类别：</label>
            <div class="fl w220">
            <select multiple name="expertsTypeId">
            </select>
            </div>
          </li>
          <li class="mt10">
            <button type="button" class="btn mb5" onclick="allotList_search()">查询</button>
            <button type="reset" class="btn mb5">重置</button>
          </li>
        </ul>
        <div class="clear"></div>
      </form>
    </div>
      
    <!-- 表格开始-->
    <div class="col-md-12 pl20 mt10 mb10">
      <button type="button" class="btn btn-windows add" onclick="create_review_batches()">创建复审批次</button>
    </div>

    <div class="content table_box">
      <table class="table table-bordered table-condensed table-hover table-striped againAudit_table">
        <thead>
          <tr>
            <th class="info w50"><input type="checkbox" name="checkAll" onclick="checkAll(this)"></th>
            <th class="info w100">序号</th>
            <th class="info">采购机构</th>
            <th class="info">专家姓名</th>
            <th class="info">性别</th>
            <th class="info">专家类型</th>
            <th class="info">专家类别</th>
            <th class="info">工作单位</th>
            <th class="info">专业职称</th>
            <th class="info">初审合格时间</th>
          </tr>
        </thead>
        <tbody id="list_content"></tbody>
      </table>
    </div>
      
  </div>
  <!-- 内容结束 -->
  
  <!-- 弹出框 -->
  <div class="dnone col-xs-12 pt20 pb20" id="create_review_batches">
    <div class="search_detail ml0 mt0 mb10">
      <ul class="demand_list">
        <li>
          <label class="fl"><i class="red">*</i> 批次名称：</label>
          <input type="text" name="batchName" value="">
        </li>
        <li>
          <label class="fl"><i class="red">*</i> 专家编号规则：</label>
          <span>
            <input type="text" name="batchNumber" value="">
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
          <th class="info">初审合格时间</th>
        </tr>
      </thead>
      <tbody id="crb_content"></tbody>
    </table>
  </div>
  <!-- End 弹出框 -->
  
  <script src="${pageContext.request.contextPath}/js/ses/ems/againAudit/againAudit.js"></script>
  <script src="${pageContext.request.contextPath}/js/ses/ems/againAudit/processing.js"></script>
  <script src="${pageContext.request.contextPath}/js/ses/ems/againAudit/search.js"></script>
  <script>
    var list_url = '${pageContext.request.contextPath}/expertAgainAudit/againAuditList.do';  // 列表地址
    var batch_url = '${pageContext.request.contextPath}/expertAgainAudit/createBatch.do';  // 创建复审批次地址
    var select_ids = [];  // 选择的专家id集合
    var is_init = 0;
    // var str = '';
    // var orgName = [];
    // var expertsFrom = [];
    // var expertsTypeId = [];
    
    $(function () {
      // 构建列表
      $('#list_content').listConstructor({
        url: list_url
      });
      
      // for (var orgName_i in orgName) {
      //   str += '<option value="'+ orgName[orgName_i].shortName +'">'+ orgName[orgName_i].shortName +'</option>';
      // }
      // $('[name=orgName]').html('<option value="">请选择</option>' + str);
      // str = '';
      // for (var expertsFrom_i in expertsFrom) {
      //   str += '<option value="'+ expertsFrom[expertsFrom_i].name +'">'+ expertsFrom[expertsFrom_i].name +'</option>';
      // }
      // $('[name=expertsFrom]').html('<option value="">全部</option>' + str);
      // str = '';
      // for (var expertsTypeId_i in expertsTypeId) {
      //   str += '<option value="'+ expertsTypeId[expertsTypeId_i].name +'">'+ expertsTypeId[expertsTypeId_i].name +'</option>';
      // }
      // $('[name=expertsTypeId]').html('<option value="">全部</option>' + str);
      // str = '';
      // $('[name=expertsTypeId]').select2({
      //   placeholder: '全部',
      //   closeOnSelect: false,
      //   minimumResultsForSearch: -1
      // });
    });
  </script>
    
</body>
</html>