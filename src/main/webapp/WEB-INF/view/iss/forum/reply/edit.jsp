<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html> 
	<head>
		<%@ include file="/WEB-INF/view/common.jsp"%>
		<script type="text/javascript">
			function back() {
				window.location.href = "${pageContext.request.contextPath }/reply/backReply.html";
			}
		</script>
	</head>

	<body>

		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
					<li><a>信息服务</a></li>
					<li><a>论坛管理</a></li>
					<li class="active">
						<a href="javascript:jumppage('${pageContext.request.contextPath}/reply/getlist.html')">回复管理</a>
					</li>
					<li class="active">
						<a>回复修改</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<!-- 新增页面开始 -->
		<div class="container container_box">
			<form action="${ pageContext.request.contextPath }/reply/update.html" method="post">
				<div>
					<h2 class="list_title">修改回复</h2>
					<input name="replyId" type="hidden" value='${reply.id}'>
					<ul class="ul_list mb20">
						<li class="col-md-12 col-sm-12 col-xs-12 pl15">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"> <div class="red fl">*</div>回复内容：</span>
							<div class="col-md-12 col-sm-12 col-xs-12 p0">
								<script id="editor" name="content" type="text/plain" class="col-md-12 col-sm-12 col-xs-12 p0"></script>
							</div>
							<div class="red clear f12">${ERR_content}</div>
				</div>
				</li>
				</ul>
				<!-- 底部按钮 -->
				<div class="col-md-12 col-sm-12 col-xs-12 tc">
					<button class="btn btn-windows save" type="submit">更新</button>
					<button class="btn btn-windows back" onclick="back()" type="button">返回</button>
				</div>
		</div>

		</div>
		</form>
		</div>
		<script type="text/javascript">
			//实例化编辑器
			//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例

			var option = {
				toolbars: [
					[
						'undo', 'redo', '|',
						'bold', 'italic', 'underline', 'formatmatch', 'autotypeset', '|', 'forecolor', 'backcolor',
						'fontfamily', 'fontsize', '|',
						'indent', '|',
						'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'emotion'
					]
				]

			}
			var ue = UE.getEditor('editor', option);
			var content = '${reply.content}';
			ue.ready(function() {
				ue.setContent(content);
			});
		</script>
	</body>

</html>