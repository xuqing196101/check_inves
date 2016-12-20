<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp"%>
		<script type="text/javascript">
			//判断专家是否可以开始考试
			function test() {
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "${pageContext.request.contextPath }/expertExam/judgeTest.html",
					success: function(data) {
						if(data == 0) {
							layer.alert("很抱歉,考试时间已截止", {
								offset: ['30%', '40%']
							});
							$(".layui-layer-shade").remove();
						} else if(data == 1) {
							window.location.href = "${pageContext.request.contextPath }/expertExam/test.html";
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
					<h2 class="red tc">请在读完下面内容之后,点击“开始考试”进入考试界面！</h2> 考生须知：本次考试需要在
					<fmt:formatDate value="${offTime }" pattern="yyyy-MM-dd HH:mm" />之前完成，并且答题及格才生效。如果未在规定时间内完成题目，一律取消专家资格！
				</div>
				<div class="col-md-12 col-sm-12 col-xs-12 mt10 tc">
					<input type="button" value="开始考试" onclick="test()" class="btn" />
				</div>
			</div>
		</c:if>
	</body>

</html>