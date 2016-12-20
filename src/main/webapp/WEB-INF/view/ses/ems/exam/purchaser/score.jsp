<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp"%>
		<script type="text/javascript">
			var score = parseFloat("${score}");
			var pass = parseFloat("${pass}");
			$(function() {
				if(score < pass) {
					$("#isPass").html("很遗憾,您未通过本场考试!");
				} else {
					$("#isPass").html("恭喜您通过了本场考试");
				}
			})

			//退出
			function exitExam() {
				window.location.href = "${pageContext.request.contextPath }/purchaserExam/exitExam.html";
			}
		</script>

	</head>

	<body onload="countTime()">
		<div class="container tc">
			<div class="score_box border1">
				<div><span class="f18">得分：</span><span class="f22 red">${score }</span><span class="f18">分</span></div>
				<div id="isPass" class="f18 mt10"></div>
				<div class="mt20">
					<button type="button" class="btn" onclick="exitExam()" id="exitExam">退出</button>
				</div>
			</div>
		</div>
	</body>

</html>