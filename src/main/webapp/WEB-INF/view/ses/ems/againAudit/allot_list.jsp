<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
  <%@ include file="/WEB-INF/view/common.jsp" %>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/public/m_fixedTable/m_fixedTable.css"><!-- 锁表头锁表列 -->
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
        <div class="m_row_5">
        <div class="row">
          <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10">
            <div class="row">
              <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">采购机构：</div>
              <div class="col-xs-8 f0 lh0">
                <select class="w100p h32 f14" name="orgName"></select>
              </div>
            </div>
          </div>
          
          <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10">
            <div class="row">
              <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">初审合格开始时间：</div>
              <div class="col-xs-8 f0 lh0">
                <input id="startTime" name="startTime" class="Wdate w100p h32 f14 mb0" type="text" value="" onfocus="var endDate=$dp.$('endDate');WdatePicker({onpicked:function(){startTime.focus();},maxDate:'#F{$dp.$D(\'endTime\')}'})">
              </div>
            </div>
          </div>
          
          <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10">
            <div class="row">
              <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">初审合格结束时间：</div>
              <div class="col-xs-8 f0 lh0">
                <input id="endTime" name="endTime" value="" class="Wdate w100p h32 f14 mb0" type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}'})">
              </div>
            </div>
          </div>
          
          <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10">
            <div class="row">
              <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">专家类型：</div>
              <div class="col-xs-8 f0 lh0">
                <select class="w100p h32 f14" name="expertsFrom"></select>
              </div>
            </div>
          </div>
          
          <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10">
            <div class="row">
              <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">专家类别：</div>
              <div class="col-xs-8 f0 lh0 m_select2">
                <select multiple name="expertsTypeId" class="w100p h32 f14"></select>
              </div>
            </div>
          </div>
        </div>
        </div>
        <div class="tc">
          <button type="button" class="btn mb0 h32" onclick="allotList_search()">查询</button>
          <button type="reset" class="btn mb0 mr0 h32">重置</button>
        </div>
      </form>
    </div>
    
    <div class="pl20 tab-v2">
      <ul class="nav nav-tabs" id="selected_tab">
        <li class="active"><a href="#tab_unselected" data-toggle="tab" class="f16">未选</a></li>
        <li><a href="#tab_selected" data-toggle="tab" class="f16">已选</a></li>
      </ul>
      
      <div class="tab-content p20">
        <!-- 未选 -->
        <div class="tab-pane fade in active" id="tab_unselected">
          <div class="over_hidden mb10">
            <button type="button" class="btn btn-windows reverse m0" onclick="againAudit_reverseSelection('list_content')">反选</button>
            <button type="button" class="btn btn-windows add mb0 ml5" onclick="addto_selected()">添加到已选分组</button>
            <div class="fr h32 lh32">共有 <span id="unselect_expertTotal" class="red"></span> 名专家</div>
          </div>
          <table class="table table-bordered table-hover mb0 againAudit_table fixed_columns">
            <thead>
              <tr>
                <th class="w30"><input type="checkbox" name="checkAll" class="unselected_checkAll" onclick="againAudit_checkAll(this, 'list_content')"></th>
                <th class="w50">序号</th>
                <th class="w100">采购机构</th>
                <th class="w140">专家姓名</th>
                <th class="w50">性别</th>
                <th class="w80">专家类型</th>
                <th>专家类别</th>
                <th class="w180">工作单位</th>
                <th class="w140">专业职称(职务)</th>
                <th class="w120">初审合格时间</th>
              </tr>
            </thead>
            <tbody id="list_content"></tbody>
          </table>
        </div>
        <!-- End 未选 -->
        
        <!-- 已选 -->
        <div class="tab-pane fade" id="tab_selected">
          <div class="over_hidden mb10">
            <button type="button" class="btn btn-windows reverse m0" onclick="againAudit_reverseSelection('selected_content')">反选</button>
            <button type="button" class="btn btn-windows withdraw mb0 ml5" onclick="remove_selected()">移除已选分组</button>
            <button type="button" class="btn btn-windows add mb0 ml5" onclick="create_review_batches()">创建复审批次</button>
            <div class="fr h32 lh32">共有 <span id="select_expertTotal" class="red"></span> 名专家</div>
          </div>
          <table class="table table-bordered table-hover mb0 againAudit_table fixed_columns">
            <thead>
              <tr>
                <th class="w30"><input type="checkbox" name="checkAll" class="selected_checkAll" onclick="againAudit_checkAll(this, 'selected_content')"></th>
                <th class="w50">序号</th>
                <th class="w100">采购机构</th>
                <th class="w140">专家姓名</th>
                <th class="w50">性别</th>
                <th class="w80">专家类型</th>
                <th>专家类别</th>
                <th class="w180">工作单位</th>
                <th class="w140">专业职称(职务)</th>
                <th class="w120">初审合格时间</th>
              </tr>
            </thead>
            <tbody id="selected_content"></tbody>
          </table>
        </div>
        <!-- End 已选 -->
      </div>
    </div>
      
  </div>
  <!-- 内容结束 -->
  
  <!-- 创建复审批次弹出框 -->
  <div class="dnone col-xs-12 p0" id="create_review_batches">
    <div class="search_detail m0">
      <ul class="demand_list">
        <li class="w100p mr0">
          <label class="fl w130 h32 lh32"><i class="red">*</i> 批次名称：</label>
          <input type="text" name="batchName" value="">
        </li>
        <li class="w100p mr0 mt10">
          <label class="fl w130 h32 lh32"><i class="red">*</i> 专家编号规则：</label>
          <input type="text" name="batchNumber" value="">
        </li>
      </ul>
      <div class="clear"></div>
    </div>
  </div>
  <!-- End 创建复审批次弹出框 -->
  
  <script src="${pageContext.request.contextPath}/public/m_fixedTable/m_fixedTable.js"></script><!-- 锁表头锁表列 -->
  <script src="${pageContext.request.contextPath}/js/ses/ems/againAudit/againAudit.js"></script><!-- 未选列表 -->
  <script src="${pageContext.request.contextPath}/js/ses/ems/againAudit/againAudit_t.js"></script><!-- 已选列表 -->
  <script src="${pageContext.request.contextPath}/js/ses/ems/againAudit/processing.js"></script><!-- 公共方法库 -->
  <script src="${pageContext.request.contextPath}/js/ses/ems/againAudit/search.js"></script><!-- 公共搜索库 -->
  <script>
    var root_url = '${pageContext.request.contextPath}';  // 根目录地址
    var select_ids = [];  // 已选的专家id集合
    var unselect_ids = [];  // 未选的专家id集合
    var is_init = 0;  // 是否为第一次初始化（避免搜索等操作重置插件）
    
    $(function () {
      $('#list_content').listConstructor();  // 构建列表
      $('#selected_content').listConstructor_t();  // 构建暂存数据
      
      // 重置操作（有select2插件的情况下）
      $('button[type=reset]').on('click', function () {
        $('[name=expertsTypeId]').select2('val', '');
      });
      
      // 切换已选、未选标签页时重置锁表头锁表列
      $('#selected_tab a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
        $('.fixed_columns').m_fixedTable();
      });
    });
  </script>
    
</body>
</html>