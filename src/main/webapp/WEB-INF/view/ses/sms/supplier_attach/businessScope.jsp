<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
  </head>

  <body>
    <div class="container">
      <!-- 列表 -->
      <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
            <tr>
              <td></td>
            </tr>
          </thead>
          <c:forEach items="${result.list}" var="s" varStatus="vs">
            <tr>
              <td></td>
            </tr>
          </c:forEach>
        </table>
      </div>
    </div>
  </body>

</html>