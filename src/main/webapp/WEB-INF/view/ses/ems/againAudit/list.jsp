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
			<a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/expertAudit/list.html?sign=3')">专家复查</a>
			</c:if>
		</li>
	</ul>
	<div class="clear"></div>
	</div>
	</div>
	<!-- 面包屑导航结束 -->

  <!-- 内容开始 -->
  <div class="container">
  
    <div class="headline-v2">
      <h2>专家复审分配列表</h2>
    </div>
    
    <h2 class="search_detail">
      <form id="form_id" action="${pageContext.request.contextPath}/expertAudit/basicInfo.html" method="post">
        <input name="expertId" type="hidden" />
        <input name="sign" type="hidden" value="${sign }"/>
        <input name="tableType" type="hidden" value=""/>
      </form>
      <form action="${pageContext.request.contextPath}/expertAudit/list.html" method="post" id="formSearch" class="mb0">
        <input type="hidden" name="pageNum" id="pageNum">
        <input type="hidden" name="sign" value="${sign }">
        <ul class="demand_list">
          <li>
            <label class="fl">专家姓名：</label>
            <input type="text" name="relName" value="${relName }">
          </li>
          <li>
            <label class="fl">状态：</label>
            <select name="status" class="w178" id="status">
              <option value="">全部</option>
              <c:if test="${sign == 1}">
                <option <c:if test="${state eq '0'}">selected</c:if> value="0">待初审</option>
                <option <c:if test="${state eq '1'}">selected</c:if> value="1">初审合格</option>
                <option <c:if test="${state eq '3'}">selected</c:if> value="3">退回修改</option>
                <option <c:if test="${state eq '2'}">selected</c:if> value="2">初审未合格</option>
              </c:if>
              <c:if test="${sign == 2}">
                <option <c:if test="${state eq '1'}">selected</c:if> value="1">待复审</option>
                <option <c:if test="${state eq '-2'}">selected</c:if> value="-2">复审预合格</option>
                <option <c:if test="${state eq '-3'}">selected</c:if> value="-3">公示中</option>
                <option <c:if test="${state eq '4'}">selected</c:if> value="4">复审合格</option>
                <option <c:if test="${state eq '5'}">selected</c:if> value="5">复审不合格</option>
              </c:if>
              <c:if test="${sign == 3}">
                <option <c:if test="${state eq '6'}">selected</c:if> value="6">待复查</option>
                <option <c:if test="${state eq '7'}">selected</c:if> value="7">复查合格</option>
                <option <c:if test="${state eq '8'}">selected</c:if> value="8">复查未合格</option>
              </c:if>
            </select>
          </li>
          <li>
            <label class="fl">审核时间：</label>
            <span>
              <input id="auditAt" name="auditAt" class="Wdate w178 fl" value='<fmt:formatDate value="${auditAt}" pattern="YYYY-MM-dd"/>' type="text" onClick="WdatePicker()" />
            </span>
          </li>
        </ul>
        <div class="col-md-12 clear tc mt10">
          <input class="btn" value="查询" type="submit">
        <button onclick="resetForm();" class="btn" type="button">重置</button>
        </div>
        <div class="clear"></div>
      </form>
    </h2>
      
    <!-- 表格开始-->
    <div class="col-md-12 pl20 mt10">
      <button class="btn btn-windows check" type="button" onclick="shenhe();">审核</button>
      <c:if test="${sign == 2 or sign == 3}">
        <a class="btn btn-windows apply" onclick='publish()' type="button">发布</a>
      </c:if>
      <c:if test="${sign == 1 }">
        <a class="btn btn-windows input" onclick='downloadTable(1)' href="javascript:void(0)">下载初审表</a>
      </c:if>
      <c:if test="${sign == 2 }">
        <a class="btn btn-windows input" onclick='downloadTable(2)' href="javascript:void(0)">下载入库复审表</a>
        <button class="btn btn-windows add" type="button" onclick="tianjia();">添加签字人员</button>
      </c:if>
      <c:if test="${sign == 3 }">
        <a class="btn btn-windows input" onclick='downloadTable(3)' href="javascript:void(0)">下载复查表</a>
      </c:if>
    </div>

    <div class="content table_box">
      <table class="table table-bordered table-condensed table-hover table-striped">
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
  
  <script src="${pageContext.request.contextPath}/js/ses/ems/againAudit/list.js"></script>
  <script>
    $('#list_content').listConstructor({
      url: '${pageContext.request.contextPath}/expertAgainAudit/againAuditList.do'
    });
  </script>
    
</body>
</html>