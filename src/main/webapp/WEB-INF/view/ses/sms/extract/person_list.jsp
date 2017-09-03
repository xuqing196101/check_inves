<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
  <%@ include file="/WEB-INF/view/common/tags.jsp" %>
  <%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  <script type="text/javascript">
  	
  	
  	
  </script>
  <body>
  	<form action="" method="post" >
  		姓名：<input name="name">
  		单位：<input name="compary">
  		职务：<input name="duty">
  		军衔：<input name="rank">
  		<input type="submit" class="btn list_btn" value="查询">
  		<input type="reset" class="btn list_btn" value="重置">
  	</form>
     <table class="table table-bordered table-condensed table_input left_table">
              <thead>				
	              <tr>
	                  <th class="info"><input type="checkbox" onclick="checkAll(this)"> </th>
	                  <th class="info">序号</th>
	                  <th class="info" width="15%">姓名</th>
	                  <th class="info" width="40%">单位</th>
	                  <th class="info" width="15%">职务</th>
	                  <th class="info" width="15%">军衔</th>
	              </tr>
              </thead>
              <tbody>
              <c:forEach var="p"  varStatus="v" items="${persons }">
	              <tr>
	              	<td> <input type="checkbox" value="${p.id }"> </td>
	              	<td> ${v.count} </td>
	              	<td> ${p.name} </td>
	              	<td> ${p.compary} </td>
	              	<td> ${p.duty} </td>
	              	<td> ${p.rank}</td>
	              </tr>
              </c:forEach>
            </tbody>
          </table>
  </body>
</html>
