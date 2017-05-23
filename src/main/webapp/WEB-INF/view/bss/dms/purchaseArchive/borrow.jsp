<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
        <%@ include file="/WEB-INF/view/front.jsp"%>
		<title>采购档案借阅</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript">
			$(function() {

			});
		</script>
	</head>

	<body>
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:void(0);">首页</a>
					</li>
					<li>
						<a href="javascript:void(0);">保障作业</a>
					</li>
					<li>
						<a href="javascript:void(0);">采购档案借阅</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>

		<div class="col-md-12">
			<div class="container mt20 dangan_tip">
				<span class="fl">当前浏览人：${user.relName }</span>
			</div>
		</div>

		<div class="container border1 mt10">
			<h2 class="tc dangan_file">${archive.name }档案</h2>
			<div class="col-md-12 mt20">
				<span class="fr">档案编号:${archive.code }</span>
			</div>
			<div class="col-md-12 mt20">
				<c:forEach items="${archiveList }" var="a">
					<div class="col-md-4 dangan_mian">
						<div class="col-md-12 dangan_pic">
							<c:if test="${a.status==1 }">
								<img src="${pageContext.request.contextPath }/file/viewFile.html?path=${a.path}" width="100%" height="100%">
							</c:if>
						</div>
						<div class="col-md-12 tc mt10">
							<a href="${pageContext.request.contextPath }/purchaseArchive/downloadFile.do?id=${a.attachmentId }">${a.name }</a>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</body>

</html>