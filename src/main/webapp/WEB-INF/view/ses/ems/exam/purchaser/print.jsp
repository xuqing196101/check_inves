<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
	function dayin() {
		var LODOP = getLodop();
		if (LODOP) {
			LODOP.ADD_PRINT_HTM(0, 0, "100%", "100%",
					document.getElementById("div_print").innerHTML);
			LODOP.PREVIEW();
		}
	}
	</script>

  </head>
  
  <body>
  	<input type="button" value="打印" onclick="dayin()" id="abc"/>
  	<div id="div_print">
  	<table>
  		<thead><tr>
  			<th style="width:200px;height:30px;border:1px solid black;">选择</th>
  			<th style="width:200px;height:30px;border:1px solid black;">序号</th>
  			<th style="width:200px;height:30px;border:1px solid black;">题干</th>
  			<th style="width:200px;height:30px;border:1px solid black;">选项</th>
  			<th style="width:200px;height:30px;border:1px solid black;">答案</th>
  			
  		</thead>
  		<tbody>
  			 <c:forEach items="${examPool }" var="e">
  			 <tr>
    			<td style="width:200px;height:30px;border:1px solid black;">${e.point }</td>
    			<td style="width:200px;height:30px;border:1px solid black;">${e.topic }</td>
    			<td style="width:200px;height:30px;border:1px solid black;">${e.answer }</td>
    			<td style="width:200px;height:30px;border:1px solid black;">${e.point }</td>
    			<td style="width:200px;height:30px;border:1px solid black;">${e.point }</td>
    		</tr>
    		</c:forEach>
  		</tbody>
  	</table>
   </div>
  </body>
</html>
