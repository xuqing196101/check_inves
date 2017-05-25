<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp"%>
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
						<a>回复详情</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<!-- 新增页面开始 -->
		<div class="container content pt0">
			<div class="row magazine-page">
				<div class="col-md-12 col-sm-12 col-cs-12 tab-v2">
					<div class="padding-top-10">
						<ul class="nav nav-tabs bgwhite">
							<li class="active">
								<a aria-expanded="true" href="javascript:void(0)" data-toggle="tab" class="f18">回复详情</a>
							</li>
						</ul>
						<div class="tab-content padding-top-20 over_hideen">
							<div class="tab-pane fade active in" id="tab-1">
								<h2 class="count_flow jbxx">基本信息</h2>
								<table class="table table-bordered">
									<tbody>
										<%-- <tr>
	                <td class="bggrey ">所属版块：</td>
	                <td>${reply.park.name }</td>
	                <td class="bggrey ">所属主题：</td>
	                <td>${reply.post.name }</td>
	            </tr> --%>
										<tr>
											<td class="bggrey" width="10%">所属帖子：</td>
											<td>${reply.post.name }</td>
											<td class="bggrey" width="10%">发表人：</td>
											<td>${reply.user.relName}</td>
										</tr>
										<tr>
											<td class="bggrey" width="10%">发表时间：</td>
											<td>
												<fmt:formatDate value="${reply.publishedAt }" pattern="yyyy-MM-dd HH:mm:ss" />
											</td>
											<td class="bggrey" width="10%">更新时间：</td>
											<td>
												<fmt:formatDate value="${reply.updatedAt }" pattern="yyyy-MM-dd" />
											</td>
										</tr>
									</tbody>
								</table>
								<h2 class="count_flow jbxx">回复内容</h2>
								<div class="col-md-12 col-sm-12 col-cs-12 p0">
									<script id="editor" name="content" type="text/plain" class="mt20" readonly="readonly">
									</script>
								</div>
								<!-- 底部按钮 -->
								<div class="col-md-12 tc">
									<button class="btn btn-windows back mt10" onclick="history.go(-1)" type="button">返回</button>
								</div>
							</div>
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
										'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'emotion',
									]
								]

							}
							var ue = UE.getEditor('editor', option);
							var content = '${reply.content}';
							ue.ready(function() {
								ue.setContent(content);
								ue.setDisabled([]);
							});
						</script>

	</body>

</html>