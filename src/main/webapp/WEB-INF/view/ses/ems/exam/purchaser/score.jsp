<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>采购人得分页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		var count = parseInt("${count}");
		var score = "${score}";
		var isAllowRetake = "${isAllowRetake}";
		var thirty = "";
		if("${thirty}"){
			thirty = "${thirty}";
		}
		var pass = "${pass}";
		if(isAllowRetake==1){
			var offTime = "${examPaper.offTime}";
			var off = new Date(Date.parse(offTime.replace(/-/g, "/"))); 
			var test = "${examPaper.testTime}";
			var date3 = off.getTime()-new Date().getTime();
			var days = Math.floor(date3/(24*3600*1000));
			var leave1 = date3%(24*3600*1000);
			var hours = Math.floor(leave1/(3600*1000));
			var leave2 = leave1%(3600*1000);
			var minutes = Math.floor(leave2/(60*1000));
			var leave3 = leave2%(60*1000);
			var seconds = Math.round(leave3/1000);
			if("${thirty}"){
				if("${time}"){
					var timeLeft = "${time}";
				}else{
					var timeLeft = 30*60*1000-1000;//这里设定的时间是30分钟 
				}
			}else{
				var timeLeft = minutes*60*1000-1000 + seconds*1000;
			}
		}
		
		
		function countTime(){
		    if(timeLeft<=0){
		    	$("#reTake").hide();
		    	$("#div_time").hide();
		        return; 
		    } 
		    var startMinutes = parseInt(timeLeft / (60 * 1000), 10); 
		    var startSec = parseInt((timeLeft - startMinutes * 60 * 1000)/1000); 
		    document.getElementById("time").innerHTML = startMinutes + "分钟" + startSec + "秒"; 
		    timeLeft = timeLeft - 1000;
		    setTimeout('countTime()',1000); 
		 }
		
		$(function(){
			$("#reTake").hide();
			if(score < pass&&isAllowRetake == 1){
				$("#isPass").html("很遗憾,您未通过本场考试");
				$("#reTake").show();
				$("#div_time").show();
			}else if(score >= pass){
				$("#isPass").html("恭喜您通过了本场考试");
				$("#reTake").hide();
				$("#div_time").hide();
			}else{
				$("#isPass").html("很遗憾,您未通过本场考试!");
				$("#div_time").hide();
			}
		})
		
		//重考方法
		function reTake(){
			layer.confirm('您确定现在重考吗?', {title:'提示',offset: ['30%','40%'],shade:0.01}, function(index){
				layer.close(index);
				var paperId = "${paperId}";
				window.location.href = "${pageContext.request.contextPath }/purchaserExam/reTake.do?paperId="+paperId+"&time="+timeLeft+"&thirty="+thirty;
			});
		}
		
		//退出
		function exitExam(){
			if(score < 60&&isAllowRetake == 1){
				layer.confirm('您确定要退出吗?若您选择退出,将不得再次参加本次考试!', {title:'提示',offset: ['30%','40%'],shade:0.01}, function(index){
					layer.close(index);
					window.location.href = "${pageContext.request.contextPath }/purchaserExam/exitExam.html";
				});
			}else if(score >= 60){
				window.location.href = "${pageContext.request.contextPath }/purchaserExam/exitExam.html";
			}else{
				window.location.href = "${pageContext.request.contextPath }/purchaserExam/exitExam.html";
			}
		}
	</script>
	
  </head>
  
  <body onload="countTime()">
  	<div class="container tc"> 
  		 <div class="score_box border1">
  		    <div><span class="f18">得分：</span><span class="f22 red">${score }</span><span class="f18">分</span></div>
  			<!--<div class="f18">感谢您的参与!</div>-->
  			<div id="isPass" class="f18 mt10"></div>
  		    <div id="div_time" class="tc mt10">
  				重考剩余时间:<span id="time"></span>
  				<div class="clear"></div>
  		    </div>
  			<div class="mt20">
	  			<button type="button" class="btn" onclick="reTake()" id="reTake">重考</button>
	  			<button type="button" class="btn" onclick="exitExam()" id="exitExam">退出</button>
  		    </div>	  
  		 </div>
  	</div>
  </body>
</html>
