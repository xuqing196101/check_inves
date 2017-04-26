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
			  <th class="info">缓存名称</th>
			  <th class="info">缓存内容</th>
			</tr>
		 <thead>
		 <tbody>
		    <tr>
		      <td class="tl pl20" width="15%">${cache.name}</td>
		      <td class="tl pl20">${cache.content}</td>
		    </tr>
		 </tbody>
		</table>
	  </div>
	</div>
  </body>
</html>
