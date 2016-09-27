<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>专家得分页面</title>
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
		$(function(){
			var score = ${score};
			if(score>=60){
				$("#isPass").html("恭喜您通过了本场考试");
				$("#isReDo").hide();
			}else{
				$("#isPass").html("很遗憾,您未通过本场考试");
				$("#isReDo").show();
			}
		})
		
		function isReDo(){
			layer.confirm('您确定现在要重考吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				layer.close(index);
				window.location.href = "<%=path%>/expertExam/test.html";
			});
		}
	</script>

  </head>
  
  <body>
  		<div class="container tc">
  		  <div class="score_box border1">
  			<div id="isPass" class="f18"></div>
  			<div><span class="f18">得分:</span><span class="f22 red">${score }</span><span class="f18">分</span></div>
  			<div class="f18 mt10">感谢您的参与!</div>
  			<div class="mt20">
  			  <button class="btn" type="button" onclick="isReDo()" id="isReDo">重考</button>
  			  <button class="btn" type="button" onclick="isReDo()" id="isReDo">退出考试系统</button>
  		    </div>
  		    </div>
  		</div>
    	
  </body>
</html>
