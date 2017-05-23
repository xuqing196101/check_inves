<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<title>查看考卷</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript">
		</script>
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
						<a href="javascript:void(0);">考卷管理</a>
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
								<a aria-expanded="true" href="javascript:void(0)" data-toggle="tab" class="f18">考试详情</a>
							</li>
						</ul>
						<div class="tab-content padding-top-20 over_hideen">
							<div class="tab-pane fade active in" id="tab-1">
								<table class="table table-bordered">
									<tbody>
										<tr>
											<td class="bggrey " width="12%">试卷名称：</td>
											<td colspan="3">${examPaper.name }</td>
										</tr>
										<tr>
											<td class="bggrey " width="12%">试卷编号：</td>
											<td colspan="3">${examPaper.code }</td>
										</tr>
										<tr>
											<td class="bggrey " width="12%">题型结构：</td>
											<td colspan="3">${typeDistribution }</td>
										</tr>
										<tr>
											<td class="bggrey " width="12%">总分值：</td>
											<td width="38%">${examPaper.score }分</td>
											<td class="bggrey " width="12%">及格标准：</td>
											<td width="38%">${examPaper.passStandard }分</td>
										</tr>
										<tr>
											<td class="bggrey " width="12%">考试开始时间：</td>
											<td width="38%">${startTime }</td>
											<td class="bggrey " width="12%">考试结束时间：</td>
											<td width="38%">${offTime }</td>
										</tr>
										<tr>
											<td class="bggrey " width="12%">考卷年度：</td>
											<td width="38%">${examPaper.year }</td>
											<td class="bggrey " width="12%">答题用时：</td>
											<td width="38%">${examPaper.testTime }分钟</td>
										</tr>
										<tr>
											<td class="bggrey " width="12%">创建时间：</td>
											<td width="38%">
												<fmt:formatDate value="${examPaper.createdAt }" pattern="yyyy-MM-dd" />
											</td>
											<td class="bggrey " width="12%">修改时间：</td>
											<td width="38%">
												<fmt:formatDate value="${examPaper.updatedAt }" pattern="yyyy-MM-dd" />
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

		<!-- 按钮 -->
		<div class="col-md-12 col-sm-12 col-xs-12 mt10 tc">
			<input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
		</div>
	</body>

</html>