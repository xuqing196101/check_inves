<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
	<script type="text/javascript">
		//判断专家是否可以开始考试
		function test(){
			$.ajax({
				type:"POST",
				dataType:"json",
				url:"${pageContext.request.contextPath }/expertExam/judgeQualy.html",
				success:function(data){
	       			if(data==0){
	       				layer.alert("很抱歉,考试时间已截止",{offset: ['30%','40%']});
						$(".layui-layer-shade").remove();
	       			}else if(data==1){
	       				window.location.href = "${pageContext.request.contextPath }/expertExam/test.html";
	       			}else if(data==2){
	       				layer.alert("很抱歉,您不是专家,无法参加考试",{offset: ['30%','40%']});
						$(".layui-layer-shade").remove();
	       			}else if(data==3){
	       				layer.alert("考试时间尚未开始",{offset: ['30%','40%']});
						$(".layui-layer-shade").remove();
	       			}else if(data==4){
	       				layer.alert("很抱歉，您未被添加到今年的考试中",{offset: ['30%','40%']});
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
  	<c:if test="${offTime!=null }">
  		<div class="container mt10">
		  	<div class="col-md-12 f22">
		  	           <h2 class="red tc">请在读完下面内容之后,点击“开始考试”进入考试界面！</h2>
		  		考生须知：本次考试需要在<fmt:formatDate value="${offTime }" pattern="yyyy-MM-dd HH:mm"/>之前完成，并且答题及格才生效，具体考试详情请查看自己的考试安排。如果未在规定时间内完成题目，一律取消专家资格！
		  	</div>
		  	<div class="col-md-12 p0 tc mt20">
		  		<input type="button" value="开始考试" onclick="test()" class="btn"/>
		  	</div>
   		</div>
  	</c:if>
  </body>
</html>
