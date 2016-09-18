<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>采购人开始考试页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		$(function(){
			$("#startExam").hide();
		})
		
		//倒计时
		var timeLeft = 1*60*1000;//这里设定的时间是1分钟 
		function countTime(){ 
		    if(timeLeft==0){
		    	$("#startExam").show();
		    	$("#ready").html("亲,可以开始考试了!");
		        return; 
		    } 
		    var startMinutes = parseInt(timeLeft / (60 * 1000), 10); 
		    var startSec = parseInt((timeLeft - startMinutes * 60 * 1000)/1000); 
		    document.getElementById("time").innerHTML = startMinutes + "分钟" + startSec + "秒"; 
		    timeLeft = timeLeft - 1000;
		    setTimeout('countTime()',1000); 
		 }
	</script>
  </head>
  
  <body onload="countTime()">
  	<div style="width:960px;height:200px;font-size:24px;line-height:200px;margin:0 auto;text-align:center;">
  		考卷已生成完毕,距离考试还有5分钟,请您耐心等待,谢谢!
  	</div>
  	<div style="width:960px;height:200px;font-size:12px;line-height:200px;margin:0 auto;text-align:center;" id="ready">
  		倒计时<span id="time"></span>
  	</div>
  	<form action="<%=path %>/purchaserExam/test.html" method="post">
	  	<div style="width:960px;height:200px;line-height:200px;margin:0 auto;text-align:center;" id="startExam">
	  		<input type="submit" value="开始考试"/>
	  	</div>
	  	<input type="hidden" name="paperId" value="${paperId }"/>
  	</form>
  </body>
</html>
