<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
  <%@ include file="/WEB-INF/view/common.jsp" %>
  <%@ include file="/WEB-INF/view/common/webupload.jsp" %>
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
    <div class="headline-v2"><h2 id="head_tit"></h2></div>
    
    <div class="search_detail hide">
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
            <label class="fl">专家类型：</label>
            <select class="w220" name="expertsFrom"></select>
          </li>
          <li class="select2-nosearch mb10">
            <label class="fl">专家类别：</label>
            <div class="fl w220 h30">
              <select class="w220 h30" name="expertsTypeId" multiple></select>
            </div>
          </li>
          <li class="mb10">
            <label class="fl">审核组：</label>
            <select class="w220" name="groupId"></select>
          </li>
          <li class="mb10">
            <label class="fl">审核结论：</label>
            <select class="w220" name="status">
              <option value="">全部</option>
              <option value="-3">复审合格</option>
              <option value="5">复审不合格</option>
              <option value="10">复审退回修改</option>
            </select>
          </li>
        </ul>
        <div class="clear"></div>
        <div class="tc">
          <button type="button" class="btn mb0 canDisable" onclick="detailsBatch_search()">查询</button>
          <button type="reset" class="btn mb0 mr0 canDisable" id="againAudit_reset">重置</button>
        </div>
      </form>
    </div>
    
    <!-- 表格开始-->
    <div class="col-md-12 pl20 pr0 mt10 mb10" id="btn_group">
      <div class="m_inline pic_upload">
        <u:upload id="pic_checkword" businessId="${batchId}" sysKey="3" typeId="da6ab7e73b8d464d8d8d46013dd70e43" buttonName="上传复审批准件" auto="true" multiple="true"/>
        <u:show showId="pic_checkword" businessId="${batchId}" sysKey="3" typeId="da6ab7e73b8d464d8d8d46013dd70e43" />
      </div>
      <div class="clear"></div>
    </div>
    
    <div class="content table_box">
      <div id="table_content"></div>
      <%-- <div id="pagediv" align="right"></div> --%>
    </div>
    
    <div class="mt20 pl20 text-center">
      <button type="button" id="back_btn" class="btn btn-windows back hide" onclick="location='${pageContext.request.contextPath}/expertAgainAudit/findBatchList.html'">返回</button>
    </div>
      
  </div>
  <form id="form_id" action="${pageContext.request.contextPath}/expertAudit/basicInfo.html" method="post">
  <input name="expertId" type="hidden" />
  <input name="tableType" type="hidden" value=""/>
  </form>
  <form id="form_expertReview" action="${pageContext.request.contextPath}/expertAgainAudit/downloadExpertReview.html" method="post">
  <input name="batchId" type="hidden" value="${batchId}" />
  </form>
  <!-- 内容结束 -->
  
  <link rel="stylesheet" href="${pageContext.request.contextPath}/public/m_fixedTable/m_fixedTable.css">
  <script src="${pageContext.request.contextPath}/public/m_fixedTable/m_fixedTable.js"></script>
  <script src="${pageContext.request.contextPath}/js/ses/ems/againAudit/batchDetails.js"></script>
  <script src="${pageContext.request.contextPath}/js/ses/ems/againAudit/processing.js"></script>
  <script src="${pageContext.request.contextPath}/js/ses/ems/againAudit/search.js"></script>
  <script>
  	var batchId = '${batchId}';
    var root_url = '${pageContext.request.contextPath}';  // 根目录地址
    var list_url = '${pageContext.request.contextPath}/expertAgainAudit/findBatchDetails.do';  // 列表地址
    var audit_url = '${pageContext.request.contextPath}/expertAgainAudit/checkGroupStatus.do';  // 校验地址
    var jump_auditBatch_url = '${pageContext.request.contextPath}/expertAgainAudit/groupBatch.html?batchId='+batchId;
    var take_effect_url = '${pageContext.request.contextPath}/expertAgainAudit/takeEffect.do';  // 生效地址
    var reexamination_url = '${pageContext.request.contextPath}/expertAgainAudit/againReview.do';  // 取消重新复审地址
    var cancel_reexamination_url = '${pageContext.request.contextPath}/expertAgainAudit/cancelReview.do';  // 取消重新复审地址
    var select_ids = [];  // 选择的专家id集合
    var is_init = 0;
    
    // loadding
    var indexLoad;
    index_load(true);
    function index_load(on_off) {
    	if (on_off) {
    		indexLoad = layer.load(1, {
 	        shade: [1, '#FFF']
 	      });
    	} else {
    		layer.close(indexLoad);
    	}
    }
    
    $(function () {
      $('#table_content').listConstructor({
        url: list_url,
        data: {
          batchId: batchId
        }
      });
      
      // 重置操作
      $('#againAudit_reset').on('click', function () {
        $('[name=expertsTypeId]').select2('val', '');
      });
    });
    
    // 跳转批次审核
    function jump_auditBatch() {
      window.location.href = '${pageContext.request.contextPath}/expertAgainAudit/auditBatch.html?batchId='+batchId;
    }
    
    //下载
    function downloadTable(id) {
      var state = $("#" + id + "").parent("tr").find("td").eq(10).text(); //.trim();
      state = trim(state);
      if(state =="预复审结束") {
      	$.ajax({
      		url: "${pageContext.request.contextPath}/expertAudit/findExpertInfo.do",
      	  data:{"id":id},
      	  type: "post",
      	  success: function(data){
      		  if(data.isReviewEnd != 1){
      			  $("input[name='tableType']").val('2');
   	          $("input[name='expertId']").val(id);
   	          $("#form_id").attr("action", "${pageContext.request.contextPath}/expertAudit/download.html");
   	          $("#form_id").submit();
      		  }else {
   	          layer.msg("该专家已复审结束，请刷新页面 !", {offset: '100px',});
      		  }
      	  }
      	});
        
      } else {
        layer.msg("请选择预复审结束的专家 !", {
          offset: '100px',
        });
      }
    }
    
    function trim(str) { //删除左右两端的空格
      return str.replace(/(^\s*)|(\s*$)/g, "");
    }
    
    //下载
    function downloadReviewTable() {
			var id="${batchId}";
      $("input[name='batchId']").val(id);
      $("#form_expertReview").attr("action", "${pageContext.request.contextPath}/expertAgainAudit/downloadExpertReview.html");
      $("#form_expertReview").submit();
    }
    
    //复审结束（审核专家操作）
    function reviewEnd(expertId) {
    	$.ajax({
        url: "${pageContext.request.contextPath}/expertAgainAudit/reviewEnd.do",
        data: {"expertId" : expertId},
        success: function (data) {
          if(data.status == 200){
        	  layer.msg("操作成功",{offset:'100px'});
            $('#table_content').listConstructor({
              url: list_url,
              data: {
                batchId: batchId
              }
            });
        	  // window.setTimeout(function(){
        		//   window.location.reload();
        	  // },1000);
          }
        },error: function(){
        	layer.msg("操作失败",{offset:'100px'});
        }
      });
    }
    
    //复审批准（资源服务中心）
    // function reviewConfirm(){
    // 	var ids = [];
    // 	$('input[type="checkbox"]:checked').each(function() {
    //     var id = $(this).val();
    //    	var state = $("#" + id + "").parent("tr").find("td").eq(10).text(); //.trim();
    //     state = trim(state);
    //     if(state !="" && state == "复审结束"){
    //     	ids.push(id);
    //     }
    //   });
    // 		$.ajax({
 	  //       url: "${pageContext.request.contextPath}/expertAgainAudit/reviewConfirm.do",
 	  //       data: {"expertIds" : ids},
 	  //       type: "post",
 	  //       traditional:true,
 	  //       success: function (data) {
 	  //         if(data.status == 500){
 	  //           layer.msg("操作成功",{offset:'100px'});
 	  //           window.setTimeout(function(){
 	  //             window.location.reload();
 	  //           },1000);
 	  //         }
 	  //         if(data.status == 503){
 	  //           layer.msg("请选择复审结束的专家",{offset:'100px'});
 	  //         }
 	  //       },error: function(){
 	  //         layer.msg("操作失败",{offset:'100px'});
 	  //       }
 	  //     });
    // }
    
    //查看
    function viewDetails(expertId) {
      window.open("${pageContext.request.contextPath}/expertAudit/basicInfo.html?expertId="+expertId+"&sign=2&isCheck=yes");
    }
  </script>
    
</body>
</html>