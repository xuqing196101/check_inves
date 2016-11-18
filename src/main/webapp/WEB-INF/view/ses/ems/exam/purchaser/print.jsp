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
  </head>
  
  <body>
	  	<div class="container">
	   		<div class="col-md-12 pl20 mt10">
	  			<input type="button" class="btn" value="打印表格" onclick="dayin()" id="print"/>
	  		</div>
  
		  	<!-- 表格开始 -->
	  		<div class="content table_box" id="div_print">
	   			<table class="table table-bordered table-condensed">
			  		<thead>
			  			<tr class="info">
				  			<th class="w50">序号</th>
				  			<th class="w100">姓名</th>
				  			<th>身份证号</th>
				  			<th>试卷编号</th>
				  			<th>所属单位</th>
				  			<th>得分</th>
			  			</tr>
			  		</thead>
			  		<tbody>
			  			 <c:forEach items="${paperUserList }" var="paper" varStatus="p">
				  			 <tr class="tc">
				    			<td>${p.index+1 }</td>
								<td>${paper.userName }</td>
								<td>${paper.code }</td>
								<td>${paper.unitName }</td>
								<td>${paper.score }</td>
				    		</tr>
			    		</c:forEach>
			  		</tbody>
	  			</table>
	  		</div>
	  	</div>
  </body>
</html>
