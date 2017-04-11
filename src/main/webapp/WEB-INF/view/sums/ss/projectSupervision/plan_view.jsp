<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript">
      function view(id,type){
        window.location.href = "${pageContext.request.contextPath}/planSupervision/viewDetail.html?id="+id+"&type="+type+"&projectId=${projectId}";
      }
    </script>
  </head>

  <body>
    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:void(0)">首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">业务监管系统</a>
          </li>
          <li>
            <a href="javascript:void(0)">采购业务监督</a>
          </li>
          <li class="active">
            <a href="javascript:void(0)">采购项目监督</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container">
      <div class="headline-v2 fl">
        <h2>采购计划列表</h2>
      </div>
      <div class="col-md-12 pl20 mt10">
        <button class="btn btn-windows back" onclick="window.history.go(-1)" type="button">返回</button>
      </div>
      <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
            <tr>
              <th class="info w50">序号</th>
              <th class="info">采购计划名称</th>
              <th class="info">预算总金额（万元）</th>
              <th class="info">编制人</th>
              <th class="info">编制时间</th>
            </tr>
          </thead>
          <c:forEach items="${list}" var="obj" varStatus="vs">
            <tr style="cursor: pointer;">
              <td class="tc w50">${(vs.index+1)}</td>
              <td class="tl pl20" width="35%">
                <a href="javascript:void(0)" onclick="view('${obj.id}','1');">${obj.fileName}</a>
              </td>
              <td class="tr pr20 w140">
                <fmt:formatNumber>${obj.budget }</fmt:formatNumber>
              </td>
              <td class="tr pr20 w140">${obj.userId}</td>
              <td class="tc">
                <fmt:formatDate value="${obj.createdAt }" pattern="yyyy-MM-dd" />
              </td>
              <%-- <td class="tl pl20">
                <c:if test="${obj.status == 1}">审核轮次设置</c:if>
                <c:if test="${obj.status == 2}">已下达</c:if>
                <c:if test="${obj.status == 3}">第一轮审核</c:if>
                <c:if test="${obj.status == 4}">第二轮审核人员设置</c:if>
                <c:if test="${obj.status == 5}">第二轮审核</c:if>
                <c:if test="${obj.status == 6}">第三轮审核人员设置</c:if>
                <c:if test="${obj.status == 7}">第三轮审核</c:if>
                <c:if test="${obj.status == 12}">未下达</c:if>
              </td>
              <td class="tc">
                <a href="javascript:void(0)" onclick="view('${obj.id}','1');">进入</a>
              </td> --%>
            </tr>
          </c:forEach>
        </table>
      </div>
    </div>
  </body>

</html>