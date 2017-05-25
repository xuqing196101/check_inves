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
					<li>
						<a href="javascript:jumppage('${pageContext.request.contextPath}/post/getlist.html')">帖子管理</a>
					</li>
					<li class="active"><a>帖子详情</a></li>
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
								<a aria-expanded="true" href="javascript:void(0)" data-toggle="tab" class="f18">帖子详情</a>
							</li>
						</ul>
						<div class="tab-content padding-top-20 over_hideen">
							<div class="tab-pane fade active in" id="tab-1">
								<h2 class="count_flow jbxx mb10">基本信息</h2>
								<table class="table table-bordered">
									<tbody>
										<tr>
											<td class="bggrey" width="12%">帖子名称：</td>
											<td colspan="5">${post.name }</td>
										</tr>
										<tr>
											<td class="bggrey" width="12%">所属版块：</td>
											<td>${post.park.name }</td>
											<td class="bggrey" width="12%">所属主题：</td>
											<td>${post.topic.name }</td>
											<td class="bggrey" width="12%">发帖时间：</td>
											<td>
												<fmt:formatDate value="${post.publishedAt }" pattern="yyyy-MM-dd HH:mm:ss" />
											</td>
										</tr>
										<tr>
											<td class="bggrey" width="12%">发帖人：</td>
											<td>${post.user.relName }</td>
											<td class="bggrey" width="12%">最后回复人：</td>
											<td>${post.lastReplyer.relName}</td>
											<td class="bggrey" width="12%">最后回复时间：</td>
											<td>
												<fmt:formatDate value="${post.lastReplyedAt }" pattern="yyyy-MM-dd HH:mm:ss" />
											</td>
										</tr>
										<tr>
											<td class="bggrey" width="12%">是否置顶：</td>
											<td>
												<c:choose>
													<c:when test="${post.isTop == 0}">
														不置顶
													</c:when>
													<c:otherwise>
														置顶
													</c:otherwise>
												</c:choose>
											</td>
											<td class="bggrey" width="12%">是否锁定：</td>
											<td>
												<c:choose>
													<c:when test="${post.isLocking == 0}">
														不锁定
													</c:when>
													<c:otherwise>
														锁定
													</c:otherwise>
												</c:choose>
											</td>
											<td class="bggrey" width="12%">回复量：</td>
											<td>${post.replycount}</td>
										</tr>
									</tbody>
								</table>
								<h2 class="count_flow jbxx">帖子介绍</h2>
								<div class="col-md-12 col-sm-12 col-cs-12 p0">
									<textarea class="h130 col-md-12 col-xs-12 col-sm-12 mb20" title="不超过800个字" readonly="readonly">${content}</textarea>
								</div>

								<h2 class="count_flow jbxx clear">已上传附件</h2>
								<div class="col-md-12 col-sm-12 col-cs-12 p0">
									<u:show showId="post_attach_show" delete="false" businessId="${post.id}" sysKey="${sysKey}" typeId="${typeId}" />
								</div>

								<!-- 底部按钮 -->
								<div class="col-md-12 tc">
									<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<script type="text/javascript">
				//实例化编辑器
				//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
				var ue = UE.getEditor('editor');
				var content = '${post.content}';
				ue.ready(function() {
					ue.setContent(content);
					ue.setDisabled([]);
				});
			</script>
	</body>

</html>