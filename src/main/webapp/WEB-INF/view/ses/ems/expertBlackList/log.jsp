<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>专家黑名单历史记录</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
</head>
<body>
  <!--面包屑导航开始-->
  <div class="margin-top-10 breadcrumbs ">
    <div class="container">
      <ul class="breadcrumb margin-left-0">
        <li><a href="#"> 首页</a></li><li><a href="#">评审专家管理</a></li><li><a href="#">不良专家名单管理</a></li><li class="active"><a href="#">增加信息</a></li>
      </ul>
    </div>
  </div>
  <div class="container margin-top-5">
    <div class="content padding-left-25 padding-right-25 padding-top-5">
      <table class="table table-bordered table-condensed">
        <thead>
          <tr>
            <th class="info w50">序号</th>
            <th class="info">操作人</th>
            <th class="info">操作类型</th>
            <th class="info">专家</th>
            <th class="info">操作时间</th>
          </tr>
        </thead>
         <c:forEach items="${log }" var="log" varStatus="vs">
          <tr>
              <td class="tc w50">${vs.index+1}</td>
              <td class="tc">${log.operator }</td>
              <td class="tc">
                <c:if test="${log.operationType == 0}">新增</c:if>
                <c:if test="${log.operationType == 1}">修改</c:if>
                <c:if test="${log.operationType == 2}">移除</c:if>
             </td>
             <td class="tc">${log.expertId }</td>
             <td class="tc"><fmt:formatDate type='date' value='${log.operationDate }' dateStyle="default" pattern="yyyy-MM-dd"/></td>
          </tr>
        </c:forEach>
      </table>
      <%-- <c:forEach items="${log }" var="log">
        <p>
        <fmt:formatDate type='date' value='${log.operationDate }' dateStyle="default" pattern="yyyy-MM-dd"/>
        ${log.operator }
        <c:if test="${log.operationType == 0}">新增</c:if>
        <c:if test="${log.operationType == 1}">修改</c:if>
        <c:if test="${log.operationType == 2}">移除</c:if>
        ${log.expertId }专家
        </p>
      </c:forEach> --%>
      <div id="pagediv" align="right"></div>
      <div class="margin-bottom-0  categories">
      <div class="col-md-12 add_regist tc">
        <a class="btn btn-windows reset"  onclick="location.href='javascript:history.go(-1);'">返回</a>
       </div>
    </div>
    </div>
  </div>
</body>
</html>
