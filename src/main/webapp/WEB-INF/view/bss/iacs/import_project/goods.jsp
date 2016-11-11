<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>物资进口申请表</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/My97DatePicker/WdatePicker.js"></script>
</head>

<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="#"> 首页</a></li>
				<li><a href="#">业务管理</a></li>
				<li><a href="#">订单中心</a></li>
				<li class="active"><a href="#">修改订单</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>

	<!-- 修改订列表开始-->
	<div class="container container_box">
	
		<h2 class="count_flow">
			<i>1</i>物资进口申请表
		</h2>
	
		<form>
			<div>
				<!-- 表单 -->
				<ul class="ul_list">
					<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5">物资进口管理部门</span>
						<div class="input-append">
							<input class="span5" id="appendedInput" type="text"> <span class="add-on">i</span>
						</div>
					</li>
					<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5">填报时间</span>
						<div class="input-append">
							<input class="Wdate w230" id="appendedInput" type="text" readonly="readonly" onClick="WdatePicker()">
							<%--<input class="span5" id="appendedInput" type="text"> <span class="add-on">i</span>
						--%></div>
					</li>
					<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5">申报项目名称</span>
						<div class="input-append">
							<input class="span5" id="appendedInput" type="text"> <span class="add-on">i</span>
						</div>
					</li>
					<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5">金额单位</span>
						 <div class="select_common">
        					<select class="fz14">
				          		<option>选项一</option>
					          	<option>选项二</option>
					        </select>
       					</div>
					</li>

				</ul>
				
				<!-- 表格 -->
				<table class="table table-bordered table-condensed table-hover mt10">
					<thead>
						<tr>
							<th class="w30 info"><input type="checkbox" alt=""></th>
							<th class="w50 info">序号</th>
							<th class="info">凭证编号</th>
							<th class="info">名称</th>
							<th class="info">总金额（元）</th>
							<th class="info">
								<div class="">
									<select class="form-control input-lg">
										<option value="">全部状态</option>
									</select>
								</div>
							</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="tc w30"><input type="checkbox" alt=""></td>
							<td class="tc w50">1</td>
							<td>BG-XY-IT20131120106054</td>
							<td>2013-11-20 台式机采购项目</td>
							<td class="tc">¥40,000.00</td>
							<td class="tc">
								<div class="">
									<select class="form-control input-lg">
										<option value="">全部状态</option>
									</select>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</form>
	</div>
</body>
</html>
