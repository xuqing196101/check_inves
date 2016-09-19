<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
		
		$(function(){
			$("#reTake").hide();
			var score = "${score}";
			var isAllowRetake = "${isAllowRetake}";
			if(score < 60&&isAllowRetake == 1){
				$("#reTake").show();
			}
		})
		
		//重考方法
		function reTake(){
			layer.confirm('您确定要重考吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				layer.close(index);
				var paperId = "${paperId}";
				count = count+1;
				alert(count);
				if(count==1){
					alert(12);
					window.location.href = "<%=path%>/purchaserExam/reTake.html?paperId="+paperId+"&count="+count;
				}else{
					alert(23);
					window.location.href = "<%=path%>/purchaserExam/reTakes.html?paperId="+paperId+"&count="+count;
				}
				
			});
		}
	</script>
	
  </head>
  
  <body>
  
  		<div class="container tc border1">
  			<div id="isPass" class="f18"></div>
  			<div><span class="f14">得分:</span><span class="f22">${score }</span><span class="f14">分</span></div>
  			<div class="f18 mt10">感谢您的参与!</div>
  			<input type="button" value="重考" class="btn" onclick="reTake()" id="reTake"/>
  		</div>
  </body>
</html>
