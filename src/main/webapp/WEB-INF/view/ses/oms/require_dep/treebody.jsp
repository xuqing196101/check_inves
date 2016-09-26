<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>

	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
        <link href="${pageContext.request.contextPath}/public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/public/ZHH/css/common.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/style.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/line-icons.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/app.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/application.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/header-v4.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/header-v5.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/img-hover.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/page_job.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/shop.style.css" media="screen" rel="stylesheet">
	
	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/public/ztree/css/zTreeStyle.css">
	
    <script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/public/ztree/jquery.ztree.core.js"></script>
    <!--导航js-->
    <script src="${pageContext.request.contextPath}/public/ZHH/js/jquery_ujs.js"></script>
    <script src="${pageContext.request.contextPath}/public/ZHH/js/bootstrap.min.js"></script>
    </head>
<div class="tab-content">
	<div class="tab-pane fade active in" id="show_ztree_content">
		<div class="panel-heading overflow-h margin-bottom-20 no-padding"
			id="ztree_title">
			<h2 class="panel-title heading-sm pull-left">
				<i class="fa fa-bars"></i> ${orgnization.name } <span
					class="label rounded-2x label-u">正常</span>
			</h2>
			<!-- <div class="pull-right">
				<a class="btn btn-sm btn-default" href="javascript:void(0)"
					onClick=""><i class="fa fa-search-plus"></i> 详细</a> <a
					class="btn btn-sm btn-default" href="javascript:void(0)" onClick=""><i
					class="fa fa-wrench"></i> 修改</a> <a class="btn btn-sm btn-default"
					href="javascript:void(0)" onClick=""><i class="fa fa-plus"></i>
					增加下属单位</a> <a class="btn btn-sm btn-default" data-toggle="modal"
					href=""><i class="fa fa-plus"></i> 增加人员</a>
			</div> -->
		</div>
		<div id="ztree_content">
			<div class="tab-v2">
				<!-- <ul class="nav nav-tabs bgwhite">
					<li class="active"><a href="#dep_tab-0" data-toggle="tab"
						class="s_news"><h4>详细信息</h4> </a></li>
				</ul> -->
				<div class="tab-content">
					<div class="tab-pane fade in active" id="dep_tab-0">
						<div class="show_obj">
							<table class="table table-striped table-bordered">
								<tbody>
									<input type="hidden" id="defaultid" value="${orgnization.id }"/>
									<tr>
										<td width="25%">名称：</td>
										<td width="25%">${orgnization.name }</td>
										<td width="25%">邮编：</td>
										<td width="25%">${orgnization.shortName }</td>
									</tr>
									<tr>
										<td width="25%">地址：</td>
										<td width="25%">${orgnization.address}</td>
										<td width="25%">电话：</td>
										<td width="25%">${orgnization.mobile}</td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="panel-heading overflow-h margin-bottom-20 no-padding"
							id="ztree_title">
							<h2 class="panel-title heading-sm pull-left">
								<i class="fa fa-bars"></i> 需求部门人员信息 <span
									class="label rounded-2x label-u">正常</span>
							</h2>
							<div class="pull-right">
								<a class="btn btn-sm btn-default" href="javascript:void(0)"
									onClick=""><i class="fa fa-search-plus"></i> 添加人员</a> <a
									class="btn btn-sm btn-default" href="javascript:void(0)"
									onClick=""><i class="fa fa-wrench"></i> 修改人员</a> <a
									class="btn btn-sm btn-default" href="javascript:void(0)"
									onClick=""><i class="fa fa-plus"></i> 删除人员</a> <a
									class="btn btn-sm btn-default" data-toggle="modal" href=""><i
									class="fa fa-plus"></i> 人员授权</a>
							</div>
						</div>
						<div class="panel panel-grey clear mt5">
							<div class="panel-heading">
								<h3 class="panel-title">
									<i class="fa fa-users"></i> 需求部门人员列表
								</h3>
							</div>
							<div class="panel-body">
								<table class="table table-bordered table-hover">
									<thead>
										<tr>
											<th><input type="checkbox" /></th>
											<th>序号</th>
											<th>姓名</th>
											<th>手机</th>
											<th>电话</th>
											<!-- <th>传真</th> -->
											<th>详细地址</th>
											<th>军网邮箱</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${userlist}" var="u" varStatus="vs">
											<tr class="cursor">
												<!-- 选择框 -->
												<td onclick="null" class="tc"><input onclick="check()"
													type="checkbox" name="chkItem" value="${u.id}" />
												</td>
												<!-- 姓名 -->
												<td class="tc" onclick="show('${u.id}');">${vs.index+1}</td>
												<!-- 标题 -->
												<td class="tc" onclick="show('${u.id}');">${u.relName}</td>
												<!-- 内容 -->
												<td class="tc" onclick="show('${u.id}');">${u.mobile}</td>
												<!-- 创建人-->
												<td class="tc" onclick="show('${u.id}');">${u.telephone}</td>
												<!-- 是否发布 -->
												<%-- <td class="tc" onclick="show('${u.id}');">${p.gender}</td> --%>
												<!-- 是否发布 -->
												<td class="tc" onclick="show('${u.id}');">${u.address}</td>
												<!-- 是否发布 -->
												<td class="tc" onclick="show('${u.id}');">${u.email}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
						<div class="panel-heading overflow-h margin-bottom-20 no-padding"
							id="ztree_title">
							<h2 class="panel-title heading-sm pull-left">
								<i class="fa fa-bars"></i> 采购管理部门信息 <span
									class="label rounded-2x label-u">正常</span>
							</h2>
						</div>
						<div class="panel panel-grey clear mt5">
							<div class="panel-heading">
								<h3 class="panel-title">
									<i class="fa fa-users"></i> 采购管理部门列表
								</h3>
							</div>
							<div class="panel-body">
								<table class="table table-bordered table-hover">
									<thead>
										<tr>
											<!-- <th><input type="checkbox" /></th> -->
											<th>序号</th>
											<th>名称</th>
											<th>简称</th>
											<th>组织机构代码</th>
											<th>电话</th>
											<th>所在地市</th>
											<th>详细地址</th>
											<th>邮编</th>
											<th>传真</th>
											<th>网站地址</th>
											<th>负责人</th>
											<th>监管负责人身份证号码</th>
											<th>监管机构性质</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${oList}" var="p" varStatus="vs">
											<tr class="cursor">
												<!-- 选择框 -->
												<%-- <td onclick="null" class="tc"><input onclick="check()"
													type="checkbox" name="chkItem" value="${p.id}" />
												</td> --%>
												<!-- 序号 -->
												<td class="tc" onclick="show('${p.id}');">${vs.index+1}</td>
												<!-- 标题 -->
												<td class="tc" onclick="show('${p.id}');">${p.name}</td>
												<!-- 内容 -->
												<td class="tc" onclick="show('${p.id}');">${p.shortName}</td>
												<!-- 创建人-->
												<td class="tc" onclick="show('${p.id}');">${p.orgCode}</td>
												<!-- 是否发布 -->
												<td class="tc" onclick="show('${p.id}');">${p.mobile}</td>
												<!-- 是否发布 -->
												<td class="tc" onclick="show('${p.id}');">${p.areaId}</td>
												<!-- 是否发布 -->
												<td class="tc" onclick="show('${p.id}');">${p.detailAddr}</td>
												<!-- 是否发布 -->
												<td class="tc" onclick="show('${p.id}');">${p.postCode}</td>
												<!-- 是否发布 -->
												<td class="tc" onclick="show('${p.id}');">${p.fax}</td>
												<!-- 创建人-->
												<td class="tc" onclick="show('${p.id}');">${p.website}</td>
												<!-- 是否发布 -->
												<td class="tc" onclick="show('${p.id}');">${p.princinpal}</td>
												<!-- 是否发布 -->
												<%-- <td class="tc" onclick="show('${p.id}');">${p.quaCode}</td> --%>
												<!-- 是否发布 -->
												<td class="tc" onclick="show('${p.id}');">${p.princinpalIdCard}</td>
												<!-- 是否发布 -->
												<td class="tc" onclick="show('${p.id}');">${p.nature}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<!-- active 是展示 -->
					<div class="tab-pane fade in" id="dep_tab-1">
						<div class="content-boxes-v2 space-lg-hor content-sm ">
							<h2 class="heading-sm">
								<i class="icon-custom icon-sm icon-bg-red fa fa-lightbulb-o"></i>
								<span>抱歉，没有找到相关信息。</span>
							</h2>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>