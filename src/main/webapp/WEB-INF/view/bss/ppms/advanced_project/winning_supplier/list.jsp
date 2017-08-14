<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <base href="${pageContext.request.contextPath}/">

    <title>确定中标供应商</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
  </head>
  <script type="text/javascript">
    /**展示信息**/
    function view(id) {
      var supplierId = "";
      $("input[name='supplierId']").each(function() {
        supplierId += $(this).val() + ",";
      });
      supplierId = supplierId.substring(0, supplierId.length - 1);
      window.location.href = "${pageContext.request.contextPath}/adWinningSupplier/viewPackageSupplier.html?&packageId=" + id + "&flowDefineId=${flowDefineId}&projectId=${projectId}&supplierId=" + supplierId;
    }

    /** 确认中标供应商  */
    function confirm(id) {
      window.location.href = "${pageContext.request.contextPath}/adWinningSupplier/confirmSupplier.html?packageId=" + id + "&flowDefineId=${flowDefineId}&projectId=${projectId}";
    }

  </script>

  <body>
    <h2 class="list_title mb0 clear">包列表</h2>
    <div class="content table_box pl0">
      <table class="table table-bordered table-condensed table-hover table-striped">
        <thead>
          <tr>
            <th class="w50 info">序号</th>
            <th class="info">包名</th>
            <th class="info">中标供应商信息</th>
          </tr>
        </thead>
        <c:forEach items="${packList}" var="pack" varStatus="vs">
          <tr>
            <td class="tc w30">${vs.count}</td>
            <td class="tc">${pack.name }
              <c:if test="${pack.projectStatus eq 'YZZ'}">
                <span class="star_red">[已终止]</span>
              </c:if>
            </td>
            <td class="tc">
              <c:choose>
                <c:when test="${fn:length(pack.listCheckPasses) != 0}">
                  <a href="javascript:void(0);" onclick="view('${pack.id}');">
                    <c:forEach items="${pack.listCheckPasses}" var="list">
                      ${list.supplier.supplierName}<input type="hidden" name="supplierId" value="${list.supplier.id}" />
                    </c:forEach>
                  </a>
                </c:when>
                <c:otherwise>
                  <button class="btn btn-windows add" <c:if test="${pack.projectStatus eq 'YZZ'}">disabled="disabled"</c:if> onclick="confirm('${pack.id}');" type="button">选择供应商</button>
                  <c:set value="1" var="values" />
                </c:otherwise>
              </c:choose>
            </td>
          </tr>
        </c:forEach>
      </table>
    </div>
  </body>

</html>