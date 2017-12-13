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
    <div class="headline-v2"><h2>专家复审批次列表</h2></div>
    
    <div class="search_detail pb0">
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
              <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">批次名称：</div>
              <div class="col-xs-8 f0 lh0">
                <input type="text" name="batchName" value="${relName}" class="w100p h32 mb0">
              </div>
            </div>
          </div>
          
          <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10">
            <div class="row">
              <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">批次创建时间：</div>
              <div class="col-xs-8 f0 lh0">
                <input id="auditAt" name="createdAt" class="Wdate w100p h32 mb0" value='<fmt:formatDate value="${auditAt}" pattern="YYYY-MM-dd"/>' type="text" onClick="WdatePicker()">
              </div>
            </div>
          </div>
          
          <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10">
            <div class="row">
              <div class="col-xs-12 f0">
                <button type="button" class="btn mb0 mr5 h32" onclick="batchList_search()">查询</button>
                <button type="reset" class="btn mb0 mr0 h32">重置</button>
              </div>
            </div>
          </div>
        </div>
        </div>
      </form>
    </div>
      
    <!-- 表格开始-->
    <div class="content table_box">
      <table class="table table-bordered table-condensed table-hover table-striped againAudit_table">
        <thead>
          <tr>
            <th class="w50">序号</th>
            <th>批次名称</th>
            <th class="w180">批次创建时间</th>
          </tr>
        </thead>
        <tbody id="list_content"></tbody>
      </table>
      <div id="pagediv" align="right"></div>
    </div>
      
  </div>
  <!-- 内容结束 -->
  
  <script src="${pageContext.request.contextPath}/js/ses/ems/againAudit/batchList.js"></script>
  <script src="${pageContext.request.contextPath}/js/ses/ems/againAudit/processing.js"></script>
  <script src="${pageContext.request.contextPath}/js/ses/ems/againAudit/search.js"></script>
  <script>
    var root_url = '${pageContext.request.contextPath}';  // 根目录
    
    $(function () {
      $('#list_content').listConstructor();
    });
  </script>
    
</body>
</html>