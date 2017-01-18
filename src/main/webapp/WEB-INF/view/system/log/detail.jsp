<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html>
  <head>
  <%@ include file="/WEB-INF/view/common.jsp"%>
  </head>
  <body>
    <div>
	  <div class="openLayer_table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
			<tr>
			  <th class="info">方法</th>
			  <th class="info">参数</th>
			  <c:if test="${systemLog.logType == 2 }">
			    <th class="info">异常</th>
			  </c:if>
			</tr>
		 <thead>
		 <tbody>
		    <tr>
		      <td class="tl pl20">${systemLog.method}</td>
		      <td class="tl pl20">${systemLog.params}</td>
		      <c:if test="${systemLog.logType == 2 }">
			    <td class="tl pl20">${systemLog.exceptionDetail}</td>
			  </c:if>
		    </tr>
		 </tbody>
		</table>
	  </div>
	</div>
  </body>
</html>
