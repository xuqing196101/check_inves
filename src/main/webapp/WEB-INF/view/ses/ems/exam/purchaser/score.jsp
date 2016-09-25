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
	<script type="text/javascript" src="${ pageContext.request.contextPath }/public/layer/layer.js"></script>
	<script type="text/javascript" src="${ pageContext.request.contextPath }/public/layer/extend/layer.ext.js"></script>
	<link href="${ pageContext.request.contextPath }/public/layer/skin/layer.css" rel="stylesheet" type="text/css" />
	<link href="${ pageContext.request.contextPath }/public/layer/skin/layer.ext.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
		var count = parseInt("${count}");
		
		if("${time}"){
			var timeLeft = "${time}";
		}else{
			var timeLeft = 30*60*1000-1000;//这里设定的时间是30分钟 
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
			var score = "${score}";
			var isAllowRetake = "${isAllowRetake}";
			if(score < 60&&isAllowRetake == 1){
				$("#reTake").show();
				$("#div_time").show();
			}else if(score >= 60){
				$("#reTake").hide();
				$("#div_time").hide();
			}else{
				$("#div_time").hide();
			}
		})
		
		//重考方法
		function reTake(){
			layer.confirm('您确定现在重考吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				layer.close(index);
				var paperId = "${paperId}";
				window.location.href = "<%=path%>/purchaserExam/reTake.html?paperId="+paperId+"&time="+timeLeft;
			});
		}
	</script>
	
  </head>
  
  <body onload="countTime()">
  
  		<div class="container tc border1">
  			<div id="isPass" class="f18"></div>
  			<div><span class="f14">得分:</span><span class="f22">${score }</span><span class="f14">分</span></div>
  			<div class="f18 mt10">感谢您的参与!</div>
  			<input type="button" value="重考" class="btn" onclick="reTake()" id="reTake"/>
  		</div>
  		
  		<div class="container tc" id="div_time">
  			<div id="ready">
  				重考剩余时间
  			</div>
  			<div id="time"></div>
  		</div>
  </body>
</html>
