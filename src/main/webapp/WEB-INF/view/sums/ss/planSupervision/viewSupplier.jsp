<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript">
    </script>
  </head>

  <body>
    <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
            <tr>
              <th class="w50 info">序号</th>
              <th class="info">供应商名称</th>
              <th class="info">关联的包名</th>
              <th class="info">是否到场</th>
              <th class="info">投标文件</th>
            </tr>
          </thead>
          <c:forEach items="${packages.saleTenderList}" var="list" varStatus="vs">
            <c:if test="${not empty list.suppliers.supplierName}">
              <tr>
              <td class="tc">${vs.index+1}</td>
              <td class="tl">${list.suppliers.supplierName}</td>
              <td class="tl">${packages.name}</td>
              <td class="tc">
                <c:if test="${empty list.suppliers.isturnUp}">请选择</c:if>
                <c:if test="${not empty list.suppliers.isturnUp and list.suppliers.isturnUp == 0}">已到场</c:if>
                <c:if test="${not empty list.suppliers.isturnUp and list.suppliers.isturnUp == 1}">未到场</c:if>
              </td>
              <td>
                <a class="mt3 color7171C6" href="javascript:download('${list.suppliers.bidFileId}', '${sysKey}')">${list.suppliers.bidFileName}</a>             
              </td>
            </tr>
            </c:if>
          </c:forEach>
        </table>
      </div>
  </body>

</html>