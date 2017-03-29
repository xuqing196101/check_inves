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
    <div class="clear">
      <div class="over_hideen">
        <h2>包名:<span class="f15 blue">${packages.name}</span></h2>
      </div>
      <table class="table table-bordered table-condensed table-hover table-striped">
        <thead>
          <tr>
            <th class="info ">供应商名称</th>
            <th class="info w150">军队业务联系人姓名</th>
            <th class="info w150">军队业务联系人电话</th>
            <th class="info w150">发售日期</th>
            <th class="info w100">标书费状态</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${packages.saleTenderList}" var="obj" varStatus="vs">
            <tr>
              <td class="tc opinter " title="${obj.suppliers.supplierName}">
                <c:choose>
                  <c:when test="${fn:length(obj.suppliers.supplierName) > 12}">
                    ${fn:substring(obj.suppliers.supplierName, 0, 10)}......
                  </c:when>
                  <c:otherwise>
                    ${obj.suppliers.supplierName}
                  </c:otherwise>
                </c:choose>
              </td>
              <td class="tc opinter w150">
                ${obj.suppliers.armyBusinessName}
                <input type="hidden" value="${obj.suppliers.id }" id="supplierId" />
              </td>
              <td class="tc opinter w150">${obj.suppliers.armyBuinessTelephone}</td>

              <td class="tc opinter w150">
                <fmt:formatDate value='${obj.createdAt}' pattern='yyyy-MM-dd' />
              </td>
              <td class="tc opinter w100">
                <c:if test="${obj.statusBid==1}">未缴纳</c:if>
                <c:if test="${obj.statusBid==2}">已缴纳</c:if>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </body>

</html>