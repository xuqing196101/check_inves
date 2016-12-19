<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
	<!--<![endif]-->

	<head>
		<%@ include file="../../../common.jsp"%>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>基本信息</title>
		<!-- Meta -->
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">
	</head>

	<body>
		<!-- 项目戳开始 -->
		<div class="container clear">
			<!--详情开始-->
			<div class="container content ">
				<div class="row magazine-page">
					<div class="col-md-12 tab-v2 job-content">
						<div class="">
							<div class="tab-content padding-top-20">
								<div class="tab-pane fade  height-450" id="tab-1">
								</div>
								<div class="tab-pane fade active in height-450" id="tab-2">
									<table id="tb" class="table table-bordered table-condensed">
										<thead>
											<tr>
												<th class="info w50">序号</th>
												<th class="info">审批字段</th>
												<th class="info">审批内容</th>
												<th class="info">不通过理由</th>
											</tr>
										</thead>
										<c:forEach items="${srList }" var="list" varStatus="vs">
											<tr>
												<td class="tc">${vs.index + 1}</td>
												<td class="tc">${list.name }</td>
												<td class="tc">${list.content }</td>
												<td class="tc">${list.auditReason}</td>
											</tr>
										</c:forEach>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>

</html>