<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<title>查看法律类题目</title>
	</head>

	<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:void(0);">首页</a>
					</li>
					<li>
						<a href="javascript:void(0);">支撑环境</a>
					</li>
					<li>
						<a href="javascript:void(0);">题库管理</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>

		<div class="container content pt0">
			<div class="row magazine-page">
				<div class="col-md-12 col-sm-12 col-cs-12 tab-v2">
					<div class="padding-top-10">
						<ul class="nav nav-tabs bgwhite">
							<li class="active">
								<a aria-expanded="true" href="javascript:void(0)" data-toggle="tab" class="f18">法律类专家题目详情</a>
							</li>
						</ul>
						<div class="tab-content padding-top-20 over_hideen">
							<div class="tab-pane fade active in" id="tab-1">
								<table class="table table-bordered">
									<tbody>
										<tr>
											<td class="bggrey " width="10%">题干：</td>
											<td colspan="3">${lawQue.topic }</td>

										</tr>
										<tr>
											<td class="bggrey " width="10%">选项：</td>
											<td colspan="3">${lawQue.items }</td>
										</tr>
										<tr>
											<td class="bggrey " width="10%">答案：</td>
											<td width="40%">${lawAnswer }</td>
											<td class="bggrey" width="10%">题型：</td>
											<td width="40%">
												<c:if test="${lawQue.questionTypeId==1 }">
													单选题
												</c:if>
												<c:if test="${lawQue.questionTypeId==2 }">
													多选题
												</c:if>
											</td>
										</tr>
										<tr>
											<td class="bggrey " width="10%">创建时间：</td>
											<td width="40%">
												<fmt:formatDate value="${lawQue.createdAt }" pattern="yyyy-MM-dd" />
											</td>
											<td class="bggrey" width="10%">修改时间：</td>
											<td width="40%">
												<fmt:formatDate value="${lawQue.updatedAt }" pattern="yyyy-MM-dd" />
											</td>
										</tr>
									</tbody>
								</table>

							</div>

						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- 底部按钮 -->
		<div class="col-md-12 col-sm-12 col-xs-12 mt10 tc">
			<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
		</div>
	</body>

</html>