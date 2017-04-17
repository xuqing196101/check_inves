<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script src="${pageContext.request.contextPath}/public/easyui/jquery.easyui.min.js"></script>
    <link href="${pageContext.request.contextPath}/public/easyui/themes/icon.css" media="screen" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/public/easyui/themes/default/easyui.css" media="screen" rel="stylesheet" type="text/css">
    <script type="text/javascript">
      function view(id,type){
        window.location.href = "${pageContext.request.contextPath}/planSupervision/viewPack.html?id="+id+"&type="+type+"&planId=${planId}";
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
            <a href="javascript:void(0)">采购计划监督</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <div class="container">
      <div class="headline-v2">
        <h2>项目列表</h2>
      </div>
      <div class="col-md-12 pl20 mt10">
        <button class="btn btn-windows back" onclick="window.history.go(-1)" type="button">返回</button>
      </div>
      <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
            <tr class="info">
              <th class="w50">序号</th>
              <th>项目名称</th>
              <th>项目编号</th>
              <th>采购机构名称</th>
              <th>采购方式</th>
              <th>创建时间</th>
              <th>创建人</th>
              <th>项目状态</th>
            </tr>
          </thead>
          <tbody id="tbody_id">
            <c:forEach items="${listProject}" var="obj" varStatus="vs">
              <tr class="pointer">
                <td class="tc w50">${(vs.index+1)}</td>
                <td class="tl pl20" onclick="view('${obj.id}','1')">
                  <a href="javascript:void(0)" onclick="view('${obj.id}','1');">${obj.name}</a>
                </td>
                <td class="tl pl20" onclick="view('${obj.id}','1')">${obj.projectNumber}</td>
                <td class="tc " onclick="view('${obj.id}','1')">${obj.purchaseDepId}</td>
                <td class="tc " onclick="view('${obj.id}','1')">
                  <c:forEach items="${kind}" var="kind">
                    <c:if test="${kind.id == obj.purchaseType}">${kind.name}</c:if>
                  </c:forEach>
                </td>
                <td class="tl pl20" onclick="view('${obj.id}','1')">
                  <fmt:formatDate type='date' value='${obj.createAt}' pattern=" yyyy-MM-dd HH:mm:ss " />
                </td>
                <td class="tl pl20" onclick="view('${obj.id}','1')">${obj.appointMan}</td>
                <td class="tc">${obj.status}</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </body>
</html>
