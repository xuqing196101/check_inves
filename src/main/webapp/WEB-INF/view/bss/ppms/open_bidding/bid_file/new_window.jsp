<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>

<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<%@ include file="/WEB-INF/view/common.jsp"%>
<script type="text/javascript">		
	$(function(){
	   if('${date}'>0){
	 	setInterval("showTime()", 1000);
	   } else {
	   	$("#showDiv").removeClass("hide");
	   	$("#showH").addClass("hide");
	   	$("#showTime").addClass("hide");
	   } 
	 });
	 
	function showTime(){
	  if('${date}'>0){
	    var projectId=$("#projectId").val();
	   $.ajax({
		url:"${pageContext.request.contextPath}/open_bidding/showTime.do",
		type:"post",
		data:{projectId:projectId},
		success:function(data){
				var bidTime=data;
				if(bidTime<=0){
					$("#showH").addClass("hide");
					$("#showTime").addClass("hide");
					$("#showDiv").removeClass("hide");
				}else{
					var day=bidTime/(60*60*24*1000);
					day=parseInt(day);
					if(day<1){
					    day=0;
						var hour=bidTime/(60*60*1000);
						hour=parseInt(hour);
						if(hour<1){
						    hour=0;
							var minutes=bidTime/(60*1000);
							minutes=parseInt(minutes);
							if(minutes<1){
								var second=bidTime/1000;
								second=parseInt(second);
							}else{
							    second=(bidTime-hour*60*60*1000-60*60*24*1000*day-minutes*60*1000)/1000;
							    second=parseInt(second);
							};
						}else{
							var minutes=(bidTime-hour*60*60*1000-60*60*24*1000*day)/(60*1000);
							var second=0;
							minutes=parseInt(minutes);
							if(minutes<1){
							    minutes=0;
								second=(bidTime-hour*60*60*1000-60*60*24*1000*day)/1000;
								second=parseInt(second);
							}else{
							    second=(bidTime-hour*60*60*1000-60*60*24*1000*day-minutes*60*1000)/1000;
							    second=parseInt(second);
							};
						};
					}else{
						var hour=(bidTime-60*60*24*1000*day)/(60*60*1000);
						hour=parseInt(hour);
						if(hour<1){
						    hour=0;
							var minutes=(bidTime-60*60*24*1000*day)/(60*1000);
							minutes=parseInt(minutes);
							if(minutes<1){
								var second=(bidTime-60*60*24*1000*day)/1000;
								second=parseInt(second);
							}else{
							    second=(bidTime-hour*60*60*1000-60*60*24*1000*day-minutes*60*1000)/1000;
							    second=parseInt(second);
							};
						}else{
							var minutes=(bidTime-hour*60*60*1000-60*60*24*1000*day)/(60*1000);
							var second=0;
							minutes=parseInt(minutes);
							if(minutes<1){
							    minutes=0;
								second=(bidTime-hour*60*60*1000-60*60*24*1000*day)/1000;
								second=parseInt(second);
							}else{
							    second=(bidTime-hour*60*60*1000-60*60*24*1000*day-minutes*60*1000)/1000;
							    second=parseInt(second);
							};
						};
					};
					var time="<span class='yomi'>"+day+"</span>"+"天"+"<span class='yomi'>"+hour+"</span>"+"时"+"<span class='yomi'>"+minutes+"</span>"+"分"+"<span class='yomi'>"+second+"</span>"+"秒";
					var timeLength = day+""+hour+""+minutes+""+second;
					if (timeLength.length < 9) {
						$("#showTime").html(time);
					}
				};
				}
		}); 
		}
	};
	
	function changtotal(obj) {
		var x,y;  
	    oRect = obj.getBoundingClientRect();  
	    x=oRect.left-50;  
	    y=oRect.top-200;  
		var projectId = $("#projectId").val();
		$.ajax({
			url:"${pageContext.request.contextPath}/open_bidding/checkIsQuote.do",
			type:"post",
			data:{projectId:projectId},
			success:function(data){
				if (data == "1") {
					window.location.href = "${pageContext.request.contextPath}/open_bidding/changtotal.html?projectId=" + projectId;
					return;
				} else if (data == "2") {
					layer.msg("报价已完成,且是唱明细",{offset: [y, x]});
					return;
				} else {
					layer.confirm('确认后将不可修改此次操作？', {title: '提示',offset: [y,x],shade: 0.01}, 
					function(index) {
						layer.close(index);
						window.location.href = "${pageContext.request.contextPath}/open_bidding/changtotal.html?projectId=" + projectId;
					});
				}
			}
		});
	}
	
	function changmingxi(obj) {
		var x,y;  
	    oRect = obj.getBoundingClientRect();  
	    x=oRect.left-100;  
	    y=oRect.top-200;  
	    var projectId = $("#projectId").val();
	    $.ajax({
			url:"${pageContext.request.contextPath}/open_bidding/checkIsQuote.do",
			type:"post",
			data:{projectId:projectId},
			success:function(data){
				if (data == "1") {
					layer.msg("报价已完成,且是唱总价",{offset: [y, x]});
					return;
				} else if (data == "2") {
					window.location.href = "${pageContext.request.contextPath}/open_bidding/changmingxi.html?projectId=" + projectId;
					return;
				} else {
					layer.confirm('确认后将不可修改此次操作？', {title: '提示',offset: [y,x],shade: 0.01}, 
					function(index) {
						layer.close(index);
						window.location.href = "${pageContext.request.contextPath}/open_bidding/changmingxi.html?projectId=" + projectId;
					});
				}
			}
		});
	}
</script>
</head>
<body class="announce">
<!-- 表格开始-->
	<button class="btn mt10" onclick="javascript:window.close()">关闭窗口</button>
   <div class="col-md-6 col-sm-8 col-xs-10 col-md-offset-4 col-sm-offset-2 project_name kaibiao_window">
	   <div class="col-md-12 col-sm-12 col-xs-12">项目名称：${project.name}</div>
	   <div class="col-md-12 col-sm-12 col-xs-12">项目编号：${project.projectNumber}</div>
   </div>
   <div class="kaibiao_time col-md-6 col-sm-8 col-xs-10 col-md-offset-4 col-sm-offset-2 kaibiao_all">
       <div id="showH" class="col-md-12 col-sm-12 col-xs-12 kb_title"><span class="time">距离</span>开标时间还有：</div>
       <div  id="showTime" class="col-md-12 col-xs-12 col-sm-12"></div>
   </div>
    <div id="showDiv" class="clear hide">
	  <input type="hidden" id ="projectId" value="${project.id}" />
	 <div class="tc col-md-12 col-sm-12 col-xs-12 kaibiao_btn">
	 	<button class="btn hand" onclick="changtotal(this)">唱总价</button>
	 	<button class="btn hand" onclick="changmingxi(this)">唱明细</button>
	 </div>
</div>
</body>
</html>
