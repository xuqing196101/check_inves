<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
	</head>

	<body onload="initData()">
		<!--面包屑导航开始-->
		<jsp:include page="navigation.jsp" flush="ture" />

		<div class="container container_box">
			<div class=" content height-350">
				<div class="col-md-12 tab-v2 job-content">
					<ul class="ul_list hand count_flow">
						<h2 class="count_flow"><i>1</i>临时专家信息</h2>
						<table class="table table-bordered table-condensed ">
							<tr>
								<td width="12%" class="bggrey">专家姓名</td>
								<td width="25%">${expert.relName}</td>
								<td width="12%" class="bggrey">居民身份证号码</td>
								<td width="25%">${expert.idCardNumber}</td>
							</tr>
						</table>
					</ul>
					<div class="tc mt20 clear col-md-12 col-sm-12 col-xs-12">
						<a class="btn btn-windows reset" href="${pageContext.request.contextPath}/expert/findAllExpert.html">返回</a>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>