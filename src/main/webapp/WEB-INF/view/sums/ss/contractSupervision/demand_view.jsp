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
        window.location.href = "${pageContext.request.contextPath}/contractSupervision/viewDetail.html?id="+id+"&type="+type+"&contractId=${contractId}";
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
            <a href="javascript:void(0)">采购合同监督</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <div class="container">
      <div class="headline-v2">
        <h2>采购需求列表</h2>
      </div>
      <div class="col-md-12 pl20 mt10">
        <button class="btn btn-windows back" onclick="window.history.go(-1)" type="button">返回</button>
      </div>
      <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
            <tr class="info">
              <th class="w50">序号</th>
              <th>需求名称</th>
              <th>填报人</th>
              <th>填报时间</th>
              <th>金额</th>
              <th>状态</th>
              <th>进度</th>
            </tr>
          </thead>
          <tbody id="tbody_id">
            <c:forEach items="${listRequired}" var="obj" varStatus="vs">
              <tr class="pointer">
                <td class="tc w50">${(vs.index+1)}</td>
                <td class="tl pl20" onclick="view('${obj.uniqueId}','0')">${obj.planName}</td>
                <td class="tl pl20" onclick="view('${obj.uniqueId}','0')">${obj.userId}</td>
                <td class="tl pl20" onclick="view('${obj.uniqueId}','0')">
                  <fmt:formatDate type='date' value='${obj.createdAt}' pattern=" yyyy-MM-dd HH:mm:ss " />
                </td>
                <td class="tl pl20" onclick="view('${obj.uniqueId}','0')">${obj.budget}</td>
                <td class="tc">
                   <c:if test="${obj.status eq '1'}">未提交</c:if>
                   <c:if test="${obj.status eq '4'}">受理退回</c:if> 
                   <c:if test="${obj.status eq '2' || obj.status eq '3' || obj.status eq '5'}">已提交</c:if>
                </td>
                <td class="tc">
                  <a href="javascript:void(0)" onclick="view('${obj.uniqueId}','0');">查看</a>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </body>
</html>
