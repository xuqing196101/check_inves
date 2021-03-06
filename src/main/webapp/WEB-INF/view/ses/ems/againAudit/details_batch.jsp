<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
  <%@ include file="/WEB-INF/view/common.jsp" %>
  <%@ include file="/WEB-INF/view/common/webupload.jsp" %>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/public/m_fixedTable/m_fixedTable.css">
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
        <div class="m_row_5">
        <div class="row">
          <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
            <div class="row">
              <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">采购机构：</div>
              <div class="col-xs-8 f0 lh0">
                <select class="w100p h32 f14" name="orgName"></select>
              </div>
            </div>
          </div>
          
          <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
            <div class="row">
              <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">专家类型：</div>
              <div class="col-xs-8 f0 lh0">
                <select class="w100p h32 f14" name="expertsFrom"></select>
              </div>
            </div>
          </div>
          
          <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
            <div class="row">
              <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">专家类别：</div>
              <div class="col-xs-8 f0 lh0 m_select2">
                <select class="w100p h32 f14" name="expertsTypeId" multiple></select>
              </div>
            </div>
          </div>
          
          <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
            <div class="row">
              <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">审核组：</div>
              <div class="col-xs-8 f0 lh0">
                <select class="w100p h32 f14" name="groupId"></select>
              </div>
            </div>
          </div>
          
          <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
            <div class="row">
              <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">审核结论：</div>
              <div class="col-xs-8 f0 lh0">
                <select class="w100p h32 f14" name="status">
                  <option value="">全部</option>
                  <option value="-3">复审合格</option>
                  <option value="5">复审不合格</option>
                  <option value="10">复审退回修改</option>
                </select>
              </div>
            </div>
          </div>
        </div>
        </div>
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
  
  <script src="${pageContext.request.contextPath}/public/m_fixedTable/m_fixedTable.js"></script><!-- 锁表头锁表列 -->
  <script src="${pageContext.request.contextPath}/js/ses/ems/againAudit/batchDetails.js"></script><!-- 列表构造 -->
  <script src="${pageContext.request.contextPath}/js/ses/ems/againAudit/processing.js"></script><!-- 公共方法库 -->
  <script src="${pageContext.request.contextPath}/js/ses/ems/againAudit/search.js"></script><!-- 公共搜索库 -->
  <script>
    var root_url = '${pageContext.request.contextPath}';  // 根目录地址
    var select_ids = [];  // 选择的专家id集合
    var is_init = 0;
    
    // loading
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
      $('#table_content').listConstructor();
      
      // 重置操作
      $('#againAudit_reset').on('click', function () {
        $('[name=expertsTypeId]').select2('val', '');
      });
    });
  </script>
    
</body>
</html>