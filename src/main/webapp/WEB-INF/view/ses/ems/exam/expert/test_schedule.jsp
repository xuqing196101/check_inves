<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp"%>
	</head>

	<body>
		<div class="container">
			<div class="headline-v2">
				<h2>考试安排</h2>
			</div>
		</div>

		<div class="container">
			<div class="mt20 tc p15_10 w400 f22 center">
				<c:if test="${message!=null }">
					${message }
				</c:if>
				<c:if test="${rule!=null }">
					<table class="table table-bordered table-condensed table-hover">
						<thead>
							<tr class="info">
								<th>考试开始时间</th>
								<th>考试截止时间</th>
							</tr>
						</thead>
						<tbody>
							<tr class="tc">
								<td>
									<fmt:formatDate value="${rule.startTime}" pattern="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td>
									<fmt:formatDate value="${rule.testLong}" pattern="yyyy-MM-dd HH:mm:ss" />
								</td>
							</tr>
						</tbody>
					</table>
				</c:if>
				<div class="clear"></div>
			</div>
		</div>

	</body>

</html>