<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/front.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>专家得分页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		$(function(){
			var score = ${score};
			var passStandard = "${rule.passStandard}";
			if(score>=passStandard){
				$("#isPass").html("恭喜您通过了本场考试");
			}else{
				$("#isPass").html("很遗憾,您未通过本场考试");
			}
		})
		
		//重考方法
		function isReDo(){
			$.ajax({
				type:"POST",
				dataType:"json",
				url:"${pageContext.request.contextPath }/expertExam/judgeReTake.html",
				success:function(data){
	       			if(data==0){
	       				layer.alert("很抱歉,考试时间已截止",{offset: ['222px', '390px']});
						$(".layui-layer-shade").remove();
	       			}else if(data==1){
	       				layer.confirm('您确定现在要重考吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
	       					layer.close(index);
	       					window.location.href = "${pageContext.request.contextPath }/expertExam/test.html";
	       				});
	       			}
	       		}
	       	});
			
		}
		
		//退出
		function exitExam(){
			window.location.href = "${pageContext.request.contextPath }/expertExam/exitExam.html"
		}
	</script>

  </head>
  
  <body>
  	<div class="container tc">
  		<div class="score_box border1">
  			<div id="isPass" class="f18"></div>
  			<div><span class="f18">得分：</span><span class="f22 red">${score }</span><span class="f18">分</span></div>
  			<div class="f18 mt10">感谢您的参与!</div>
  			<div class="mt20">
  			  	<button class="btn" type="button" onclick="isReDo()" id="isReDo">重考</button>
  			  	<button class="btn" type="button" onclick="exitExam()" id="exitExam">退出</button>
  		    </div>
  		</div>
  	</div>
  </body>
</html>
