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
    
    <div class="search_detail pb0">
      <form id="form_id" action="${pageContext.request.contextPath}/expertAudit/basicInfo.html" method="post">
        <input name="expertId" type="hidden" />
        <input name="sign" type="hidden" value="${sign }"/>
        <input name="tableType" type="hidden" value=""/>
      </form>
      <form action="${pageContext.request.contextPath}/expertAgainAudit/againAuditList.html" method="post" id="formSearch" class="mb0">
        <input type="hidden" name="pageNum" id="pageNum">
        <input type="hidden" name="sign" value="${sign }">
        <ul class="demand_list">
          <li class="mb10">
            <label class="fl">采购机构：</label>
            <select class="w220" name="orgName"></select>
          </li>
          <li class="mb10">
            <label class="fl">初审合格时间：</label>
            <span>
              <input id="startTime" name="startTime" class="Wdate w220" type="text" value="" onfocus="var endDate=$dp.$('endDate');WdatePicker({onpicked:function(){startTime.focus();},maxDate:'#F{$dp.$D(\'endTime\')}'})">
              <span>-</span>
              <input id="endTime" name="endTime" value="" class="Wdate w220" type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}'})">
            </span>
          </li>
          <li class="mb10">
            <label class="fl">专家类型：</label>
            <select class="w220" name="expertsFrom"></select>
          </li>
          <li class="select2-nosearch mb10">
            <label class="fl">专家类别：</label>
            <div class="fl w220">
            <select multiple name="expertsTypeId">
            </select>
            </div>
          </li>
          <li class="mb10">
            <button type="button" class="btn mb0" onclick="allotList_search()">查询</button>
            <button type="reset" class="btn mb0" id="againAudit_reset">重置</button>
          </li>
        </ul>
        <div class="clear"></div>
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
          <div class="over_hidden">
            <button type="button" class="btn btn-windows reverse m0" onclick="againAudit_reverseSelection('list_content')">反选</button>
            <button type="button" class="btn btn-windows add mb0 ml5" onclick="addto_selected()">添加到已选分组</button>
            <div class="fr h30 lh30">共有 <span id="unselect_expertTotal" class="red"></span> 名专家</div>
          </div>
          <table class="table table-bordered table-hover mb0 mt10 againAudit_table unselected_table fixed_columns">
            <thead>
              <tr>
                <th class="w30"><input type="checkbox" name="checkAll" class="unselected_checkAll" onclick="againAudit_checkAll(this, 'list_content')"></th>
                <th class="w50">序号</th>
                <th class="w100">采购机构</th>
                <th class="w140">专家姓名</th>
                <th class="w50">性别</th>
                <th>专家类型</th>
                <th class="w80">专家类别</th>
                <th class="w180">工作单位</th>
                <th class="w140">专业职称</th>
                <th class="w120">初审合格时间</th>
              </tr>
            </thead>
            <tbody id="list_content"></tbody>
          </table>
        </div>
        <!-- End 未选 -->
        
        <!-- 已选 -->
        <div class="tab-pane fade" id="tab_selected">
          <div class="over_hidden">
            <button type="button" class="btn btn-windows reverse m0" onclick="againAudit_reverseSelection('selected_content')">反选</button>
            <button type="button" class="btn btn-windows withdraw mb0 ml5" onclick="remove_selected()">移除已选分组</button>
            <button type="button" class="btn btn-windows add mb0 ml5" onclick="create_review_batches()">创建复审批次</button>
            <div class="fr h30 lh30">共有 <span id="select_expertTotal" class="red"></span> 名专家</div>
          </div>
          <table class="table table-bordered table-hover mb0 mt10 againAudit_table selected_table fixed_columns" style="display: none;">
            <thead>
              <tr>
                <th class="w30"><input type="checkbox" name="checkAll" class="selected_checkAll" onclick="againAudit_checkAll(this, 'selected_content')"></th>
                <th class="w50">序号</th>
                <th class="w100">采购机构</th>
                <th class="w140">专家姓名</th>
                <th class="w50">性别</th>
                <th>专家类型</th>
                <th class="w80">专家类别</th>
                <th class="w180">工作单位</th>
                <th class="w140">专业职称</th>
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
  
  <!-- 弹出框 -->
  <div class="dnone col-xs-12 p0" id="create_review_batches">
    <div class="search_detail m0">
      <ul class="demand_list">
        <li class="w100p mr0">
          <label class="fl w110 h30 lh30"><i class="red">*</i> 批次名称：</label>
          <input type="text" name="batchName" value="">
        </li>
        <li class="w100p mr0 mt10">
          <label class="fl w110 h30 lh30"><i class="red">*</i> 专家编号规则：</label>
          <span>
            <input type="text" name="batchNumber" value="">
          </span>
        </li>
      </ul>
      <div class="clear"></div>
    </div>
    
    <%-- <table class="table table-bordered table-hover">
      <thead>
        <tr>
          <th class="w50">序号</th>
          <th>专家姓名</th>
          <th>专业职称</th>
          <th>初审合格时间</th>
        </tr>
      </thead>
      <tbody id="crb_content"></tbody>
    </table> --%>
  </div>
  <!-- End 弹出框 -->
  
  <script src="${pageContext.request.contextPath}/js/ses/ems/againAudit/againAudit.js"></script>
  <script src="${pageContext.request.contextPath}/js/ses/ems/againAudit/processing.js"></script>
  <script src="${pageContext.request.contextPath}/js/ses/ems/againAudit/search.js"></script>
  <script>
    var list_url = '${pageContext.request.contextPath}/expertAgainAudit/againAuditList.do';  // 列表地址
    var batch_url = '${pageContext.request.contextPath}/expertAgainAudit/createBatch.do';  // 创建复审批次地址
    var temporary_init_url = '${pageContext.request.contextPath}/expertAgainAudit/selectBatchTemporary.do';  // 已选分组初始化地址
    var temporary_url = '${pageContext.request.contextPath}/expertAgainAudit/addBatchTemporary.do';  // 添加到已选分组地址
    var remove_temporary_url = '${pageContext.request.contextPath}/expertAgainAudit/deleteBatchTemporary.do';  // 移除已选分组地址
    var select_ids = [];  // 已选的专家id集合
    var unselect_ids = [];  // 未选的专家id集合
    var is_init = 0;
    
    $(function () {
      // 构建列表
      $('#list_content').listConstructor({
        url: list_url
      });
      
      // 构建暂存数据
      temporary_init();
      
      // 重置操作
      $('#againAudit_reset').on('click', function () {
        $('[name=expertsTypeId]').select2('val', '');
      });
      
      $('#selected_tab a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
        $($(e.target).attr('href')).find('.fixed_columns').show();
        $($(e.relatedTarget).attr('href')).find('.fixed_columns').hide();
      })
      
      // 表头跟随
      $(window).scroll(function () {
        var thead_offsetTop = 0;
        var window_offsetTop = $(window).scrollTop();
        var table_width = 0;
        $('.fixed_columns').each(function () {
          if ($(this).css('display') != 'none') {
            var _this = $(this);
            table_width = $(this).width();
            thead_offsetTop = $(this).offset().top;
            
            if (window_offsetTop >= thead_offsetTop) {
              if ($('#fixed_box').length <= 0) {
                $('body').append('<div id="fixed_box"><table class="table table-bordered table-condensed table-hover table-striped mb0"></table></div>');
                $('#fixed_box table').html($(this).find('thead').html());
                if ($(this).find('[name=checkAll]').is(':checked')) {
                  $('#fixed_box [name=checkAll]').prop('checked', true);
                }
                $('#fixed_box').css({
                  width: (table_width + 2),
                  position: 'fixed',
                  top: 0,
                  left: '50%',
                  marginLeft: '-' + (table_width / 2 - 9) + 'px'
                });
                $('#fixed_box [name=checkAll]').bind('click', function () {
                  var checkAll_class = $(this).attr('class');
                  if ($(this).is(':checked')) {
                    $('.' + checkAll_class).prop('checked', true);
                  } else {
                    $('.' + checkAll_class).prop('checked', false);
                  }
                });
              }
            } else {
              $('#fixed_box').remove();
            }
          }
        });
      });
    });
  </script>
    
</body>
</html>