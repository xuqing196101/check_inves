<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>专家开始考试</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		function test(){
			$.ajax({
				type:"POST",
				dataType:"json",
				url:"<%=basePath%>expertExam/judgeQualy.html",
				success:function(data){
	       			if(data==1){
	       				window.location.href = "<%=path %>/expertExam/test.html";
	       			}else{
	       				alert("您已被取消考试资格");
	       			}
	       		}
	       	});
			
		}
	</script>
  </head>
  
  <body>
  	<div style="width:960px;height:200px;margin:0 auto;font-size:24px;">
  		各位专家,本次考试需要在${testCycle }个月内完成所有题目,并且答题及格才生效.如果未在规定时间完成题目,一律取消专家资格!
  	</div>
  	<div style="width:960px;height:200px;margin:0 auto;font-size:24px;">
  		<input type="button" value="开始考试" onclick="test()"/>
  	</div>
    
  </body>
</html>
