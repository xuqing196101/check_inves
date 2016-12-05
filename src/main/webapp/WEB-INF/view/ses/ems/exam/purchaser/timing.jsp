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
			document.getElementById("time").innerHTML = 5+"分钟"+0+"秒";
		})
		
		//倒计时
		var timeLeft = 5*60*1000;//这里设定的时间是5分钟 
		function countTime(){
		    if(timeLeft==0){
		    	$("#form").submit();
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
  	<div class="container tc f22 fw mt20">
  		考卷已生成完毕,待倒计时结束之后，即可开考,请您耐心等待,谢谢!
	  	<div class="score_box border1">
	  		<div id="ready">
	  			倒计时
	  		</div>
	  		<div id="time"></div>
	  		<form action="${pageContext.request.contextPath }/purchaserExam/test.html" method="post" id="form">
				<input type="hidden" name="paperId" value="${examPaper.id }"/>
				<input type="hidden" name="questionType" value="${questionType }"/>
				<input type="hidden" name="questionAnswer" value="${questionAnswer }"/>
				<input type="hidden" name="questionId" value="${questionId }"/>
				<input type="hidden" name="queCount" value="${queCount }"/>
				<input type="hidden" name="singlePoint" value="${singlePoint }"/>
				<input type="hidden" name="multiplePoint" value="${multiplePoint }"/>
				<input type="hidden" name="judgePoint" value="${judgePoint }"/>
				<input type="hidden" name="singleNum" value="${singleNum }"/>
				<input type="hidden" name="multipleNum" value="${multipleNum }"/>
				<input type="hidden" name="judgeNum" value="${judgeNum }"/>
	  		</form>
	  	</div>
  	</div>
  	
  	
  	
  </body>
</html>
