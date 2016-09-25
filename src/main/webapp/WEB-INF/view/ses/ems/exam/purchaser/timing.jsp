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
			document.getElementById("time").innerHTML = 1 + "分钟" + 0 + "秒"; 
			$("#startExam").hide();
		})
		
		//倒计时
		var timeLeft = 5*60*1000-1000;//这里设定的时间是5分钟 
		function countTime(){ 
		    if(timeLeft==0){
		    	$("#startExam").show();
		    	$("#time").hide();
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
  	<div class="container tc f22 fw">
  		考卷已生成完毕,距离考试还有5分钟,请您耐心等待,谢谢!
  	</div>
  	
  	<div class="tc w200 container h80 border1">
  		<div id="ready">
  			倒计时
  		</div>
  		<div id="time"></div>
  	</div>
  	
  	
  	<form action="<%=path %>/purchaserExam/test.html" method="post">
	  	<!-- 按钮 -->
  		<div class="padding-top-10 clear" id="startExam">
			<div class="col-md-12 pl200 ">
				<div class="mt40 tc mb50">
	  				<input type="submit" class="btn" value="开始考试"/>
	  			</div>
	  		</div>
	  	</div>
	  	<input type="hidden" name="paperId" value="${paperId }"/>
  	</form>
  </body>
</html>
