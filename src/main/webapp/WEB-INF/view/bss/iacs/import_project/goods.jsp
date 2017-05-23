<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<title>物资进口申请表</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
</head>

<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="javascript:void(0);"> 首页</a></li>
				<li><a href="javascript:void(0);">业务管理</a></li>
				<li><a href="javascript:void(0);">订单中心</a></li>
				<li class="active"><a href="javascript:void(0);">修改订单</a></li>
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
        					<select class="">
				          		<option value="1" selected="selected">万美元</option>
					          	<option value="2">美元</option>
					          	<option value="3">人民币</option>
					        </select>
       					</div>
					</li>

				</ul>
				
				<!-- 表格 -->
				<table class="table table-bordered table-condensed table-hover mt10">
					<thead>
						<tr>
							<th class="w50 info">序号</th>
							<th class="info">进口单位</th>
							<th class="info">物资名称</th>
							<th class="info">规格型号</th>
							<th class="info">计量单位</th>
							<th class="info">数量</th>
							<th class="info">单价</th>
							<th class="info">金额</th>
							<th class="info">涉密等级</th>
							<th class="info">进口类别</th>
							<th class="info">免税类别</th>
							<th class="info">目录归类</th>
							<th class="info">进口海关</th>
							<th class="info">经费来源</th>
							<th class="info">使用单位</th>
							<th class="info">使用用途</th>
							<th class="info">采购文件名称及文号</th>
							<th class="info">备注</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list}" var="cr" varStatus="vs">
							<tr>
 								<td class="tc w50">${vs.index + 1}</td>
								<td class="tc"></td>
								<td class="tl pl20">${cr.goodsName}</td>
								<td class="tl pl20">${cr.stand}</td>
								<td class="tl pl20">${cr.item}</td>
								<td class="tl pl20">${cr.purchaseCount}</td>
								<td class="tl pl20">${cr.price}</td>
								<td class="tl pl20">${cr.amount}</td>
								<td class="tl pl20">
									<div class="">
										<select class="form-control input-lg w50">
											<option value="1">绝密</option>
											<option value="2">机密</option>
											<option value="3">秘密</option>
											<option value="4">公开</option>
										</select>
									</div>
								</td>
								<td class="tl pl20">
									<div class="">
										<select class="form-control input-lg w50">
											<option value="1">进口许可产品</option>
											<option value="2">自动进口许可产品</option>
											<option value="3">放开产品</option>
										</select>
									</div>
								</td>
								<td class="tc">
									<div class="">
										<select class="form-control input-lg w50">
											<option value="1">军事装备免税</option>
											<option value="2">军事装备免税审批</option>
											<option value="3">科研教学免税</option>
											<option value="4">国家鼓励投资项目免税</option>
											<option value="5">非免税</option>
										</select>
									</div>
								</td>
								<td class="tc">
									
								</td>
								<td class="tc">
									
								</td>
								<td class="tc">
									<div class="">
										<select class="form-control input-lg w50">
											<option value="1">事业费</option>
											<option value="2">装备费</option>
											<option value="3">基本建设费</option>
											<option value="4">战备物资储备费</option>
											<option value="5">教育训练费</option>
											<option value="6">科研费</option>
											<option value="7">政府专项经费</option>
											<option value="8">其他经费</option>
										</select>
									</div>
								</td>
								<td class="tc">
									
								</td>
								<td class="tc">
									<div class="">
										<select class="form-control input-lg w50">
											<option value="1">战备储备</option>
											<option value="2">国防工程建设</option>
											<option value="3">军事行动保障</option>
											<option value="4">装备配套</option>
											<option value="5">装备维修</option>
											<option value="6">军事科研</option>
											<option value="7">院校教学</option>
											<option value="8">军品生产</option>
										</select>
									</div>
								</td>
								<td class="tc">
									
								</td>
								<td class="tc">
									
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</form>
	</div>
</body>
</html>
