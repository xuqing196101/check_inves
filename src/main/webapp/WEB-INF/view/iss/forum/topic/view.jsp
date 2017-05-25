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
					<li><a href="javascript:jumppage('${pageContext.request.contextPath}/topic/getlist.html')">主题管理</a></li>
					<li class="active">
						<a>主题详情</a>
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
								<a aria-expanded="true" href="javascript:void(0)" data-toggle="tab" class="f18">主题详情</a>
							</li>
						</ul>
						<div class="tab-content padding-top-20 over_hideen">
							<div class="tab-pane fade active in" id="tab-1">
								<h2 class="count_flow jbxx">基本信息</h2>
								<table class="table table-bordered">
									<tbody>
										<tr>
											<td class="bggrey" width="10%">主题名称：</td>
											<td colspan="3">${topic.name}</td>
										</tr>
										<tr>
											<td class="bggrey" width="10%">所属版块：</td>
											<td>${topic.park.name}</td>
											<td class="bggrey" width="10%">创建人：</td>
											<td>${topic.user.relName}</td>
										</tr>
										<tr>
											<td class="bggrey" width="10%">帖子数：</td>
											<td>${topic.postcount}</td>
											<td class="bggrey" width="10%">回复量：</td>
											<td>${topic.replycount}</td>
										</tr>
										<tr>
											<td class="bggrey" width="10%">创建时间：</td>
											<td>
												<fmt:formatDate value="${topic.createdAt }" pattern="yyyy-MM-dd" />
											</td>
											<td class="bggrey" width="10%">更新时间：</td>
											<td>
												<fmt:formatDate value="${topic.updatedAt }" pattern="yyyy-MM-dd" />
											</td>
										</tr>
									</tbody>
								</table>
								<h2 class="count_flow jbxx">主题介绍</h2>
								<div class="col-md-12 col-sm-12 col-cs-12 p0">
									<textarea class="h130 col-md-12 col-xs-12 col-sm-12" title="不超过800个字" readonly="readonly">${topic.content}</textarea>
								</div>

								<!-- 底部按钮 -->
								<div class="col-md-12 col-xs-12 col-sm-12 tc mt10">
									<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
								</div>
							</div>
						</div>

	</body>

</html>