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
	<script type="text/javascript" src="${pageContext.request.contextPath }/public/layer/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/public/layer/extend/layer.ext.js"></script>
	<link href="${pageContext.request.contextPath }/public/layer/skin/layer.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath }/public/layer/skin/layer.ext.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
		function test(){
			$.ajax({
				type:"POST",
				dataType:"json",
				url:"${pageContext.request.contextPath }/expertExam/judgeQualy.html",
				success:function(data){
	       			if(data==0){
	       				layer.alert("很抱歉,考试日期已截止",{offset: ['222px', '390px']});
						$(".layui-layer-shade").remove();
	       			}else if(data==1){
	       				window.location.href = "${pageContext.request.contextPath }/expertExam/test.html";
	       			}else if(data==2){
	       				layer.alert("对不起,您不是专家,无权参加考试",{offset: ['222px', '390px']});
						$(".layui-layer-shade").remove();
	       			}else if(data==3){
	       				layer.alert("考试时间尚未开始",{offset: ['222px', '390px']});
						$(".layui-layer-shade").remove();
	       			}
	       		}
	       	});
			
		}
	</script>
  </head>
  
  <body>
  	<c:if test="${message!=null }">
  		<div class="container mt10">
		  	<div class="col-md-12 f22 tc">
		  		${message }
		  	</div>
   		</div>
  	</c:if>
  	<c:if test="${testCycle!=null }">
  		<div class="container mt10">
		  	<div class="col-md-12 f22">
		  		各位专家，本次考试需要在${testCycle }个月内完成所有题目，并且答题及格才生效。如果未在规定时间完成题目，一律取消专家资格！
		  	</div>
		  	<div class="col-md-12 p0 tc mt20">
		  		<input type="button" value="开始考试" onclick="test()" class="btn"/>
		  	</div>
   		</div>
  	</c:if>
  </body>
</html>
