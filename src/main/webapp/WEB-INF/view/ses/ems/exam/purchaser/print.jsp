<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>打印预览页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		//打印功能
		function dayin() {
			var LODOP = getLodop();
			if (LODOP) {
				LODOP.PRINT_INIT("打印表格"); 
				LODOP.ADD_PRINT_TABLE("0","0","100%","100%",document.getElementById("div_print").innerHTML);
				
			    LODOP.PREVIEW(); 
			}
		}
	</script>
	
	<style type="text/css">
		table thead th{
			border:1px solid black;
		}
		table tbody td{
			border:1px solid black;
		}
	</style>
  </head>
  
  <body>
  	<div class="container">
   		<div class="col-md-10">
  			<input type="button" class="btn btn-windows pl13" value="打印表格" onclick="dayin()" id="print"/>
  		</div>
  	</div>
  
	  	<div class="container margin-top-5">
     		<div class="content padding-left-25 padding-right-25 padding-top-5" id="div_print">
	   			<table class="table table-bordered table-condensed">
			  		<thead>
			  			<th>序号</th>
			  			<th>姓名</th>
			  			<th>考试编号</th>
			  			<th>所属单位</th>
			  			<th>得分</th>
			  		</thead>
			  		<tbody>
			  			 <c:forEach items="${paperUserList }" var="paper" varStatus="p">
				  			 <tr>
				    			<td class="tc">${p.index+1 }</td>
								<td class="tc">${paper.userName }</td>
								<td class="tc">${paper.code }</td>
								<td class="tc">${paper.unitName }</td>
								<td class="tc">${paper.score }</td>
				    		</tr>
			    		</c:forEach>
			  		</tbody>
	  			</table>
	  		</div>
   		</div>
  </body>
</html>
