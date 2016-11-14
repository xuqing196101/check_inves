<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/view/front.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>考试安排</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	

  </head>
  
  <body>
	  	<div class="container">
		   <div class="headline-v2">
		   		<h2>考试安排</h2>
		   </div>
	   	</div>
  
    	<div class="container">
	      	<div class="mt20 tc p15_10 w400 f22 center">
	        	<c:if test="${message!=null }">
	        		${message }
	        	</c:if>
	        	<c:if test="${rule!=null }">
	        		<table class="table table-bordered table-condensed table-hover">
						<thead>
							<tr class="info">
							    <th>考试开始时间</th>
								<th>考试截止时间</th>
							</tr>
						</thead>
						<tbody>
							<tr class="tc">
								<td><fmt:formatDate value="${rule.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td><fmt:formatDate value="${rule.testLong}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							</tr>
						</tbody>
					</table>
	        	</c:if>
	        	<div class="clear"></div>
	      	</div>
    	</div>
    
    	
  </body>
</html>
