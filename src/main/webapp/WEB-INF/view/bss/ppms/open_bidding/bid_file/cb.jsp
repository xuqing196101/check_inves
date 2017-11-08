<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>

<html class=" js cssanimations csstransitions time_animations" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<%@ include file="/WEB-INF/view/common.jsp"%>
<script type="text/javascript">	
	$(function() {
  		//获取查看或操作权限
       	var isOperate = $('#isOperate', window.parent.document).val();
       	if(isOperate == 0) {
       		//只具有查看权限，隐藏操作按钮
					$(":button").each(function(){ 
						$(this).hide();
		            }); 
				}
				
				var bidDate = "${bidDate}";
				if(bidDate){
					$(":button").each(function(){ 
						$(this).hide();
		      }); 
		      layer.alert("请到项目基本信息处填写开标时间");
				}
    });	
	$(function(){
	   if('${date}'>0){
	   	setTimeout("openNewWindow()", 500);
	 	setInterval("showTime()", 1000);
	   } else {
	   	$("#showDiv").removeClass("hide");
	   	$("#showH").addClass("hide");
	   	$("#showTime").addClass("hide");
	   	$(".kaibiao_time").addClass("hide");
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
					//加这个是因为0秒的时候有偶发bug时间为负数且长度很长
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
	    y=oRect.top-100;  
		var projectId = $("#projectId").val();
		$.ajax({
			url:"${pageContext.request.contextPath}/open_bidding/checkIsQuote.do",
			type:"post",
			data:{projectId:projectId},
			success:function(data){
				if (data == "1") {
					window.location.href = "${pageContext.request.contextPath}/open_bidding/changtotal.html?projectId=" + projectId + "&flowDefineId=${flowDefineId}";
					return;
				} else if (data == "2") {
					layer.msg("报价已完成,且是唱明细",{offset: [y, x]});
					return;
				} else {
					layer.confirm('确认后将不可修改此次操作？', {title: '提示',offset: [y,x],shade: 0.01}, 
					function(index) {
						layer.close(index);
						window.location.href = "${pageContext.request.contextPath}/open_bidding/changtotal.html?projectId=" + projectId + "&flowDefineId=${flowDefineId}";
					});
				}
			}
		});
		
	}
	
	function changmingxi(obj) {
		var x,y;  
	    oRect = obj.getBoundingClientRect();  
	    x=oRect.left-100;  
	    y=oRect.top-100;  
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
					window.location.href = "${pageContext.request.contextPath}/open_bidding/changmingxi.html?projectId=" + projectId + "&flowDefineId=${flowDefineId}";
					return;
				} else {
					layer.confirm('确认后将不可修改此次操作？', {title: '提示',offset: [y,x],shade: 0.01}, 
					function(index) {
						layer.close(index);
						window.location.href = "${pageContext.request.contextPath}/open_bidding/changmingxi.html?projectId=" + projectId + "&flowDefineId=${flowDefineId}";
					});
				}
			}
		});
	}
	 function openNewWindow(){
	 	window.open("${pageContext.request.contextPath}/open_bidding/openNewWidow.html?projectId=${project.id}");
	 }; 
</script>
</head>
<body class="announce">
<!-- 表格开始-->
   <div class="col-md-6 col-sm-8 col-xs-10 col-md-offset-4 col-sm-offset-2 project_name">
	   <div class="col-md-12 col-sm-12 col-xs-12">项目名称：${project.name}</div>
	   <div class="col-md-12 col-sm-12 col-xs-12">项目编号：${project.projectNumber}</div>
   </div>
   <div class="kaibiao_time time_mt col-md-6 col-sm-8 col-xs-10 col-md-offset-4 col-sm-offset-2">
       <span id="showH" class="col-md-12 col-sm-12 col-xs-12 kb_title"><span class="time">距离</span>开标时间还有：</span>
       <div  id="showTime" class="col-md-12 col-xs-12 col-sm-12 yomi_time"></div>
       <button id="quanping" class="btn" onclick="openNewWindow()">全屏</button>
   </div>
    <div id="showDiv" class="clear hide">
	  <input type="hidden" id ="projectId" value="${project.id}" />
	 <div class="tc col-md-12 col-sm-12 col-xs-12 mt20">
	 	<button class="btn hand" onclick="changtotal(this)">唱总价</button>
	 	<button class="btn hand" onclick="changmingxi(this)">唱明细</button>
	 	<button id="quanping" class="btn" onclick="openNewWindow()">全屏</button>
	 </div>
</div>
</body>
</html>
