<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>

<!DOCTYPE HTML>
<html>

<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<script type="text/javascript">
/** 文件发售 **/
function sell(id,type) {
  layer.open({
    type: 2, //page层
    area: ['1000px', '500px'],
    title: '查看',
    shade: 0.01, //遮罩透明度
    moveType: 1, //拖拽风格，0是默认，1是传统拖动
    shift: 1, //0-6的动画形式，-1不开启
    shadeClose: true,
    content: '${pageContext.request.contextPath}/planSupervision/viewSell.html?packageId=' + id + '&type=' + type,
  });
}
/** 开标 **/
function bid(id) {
  layer.open({
    type: 2, //page层
    area: ['1000px', '500px'],
    title: '查看开标',
    shade: 0.01, //遮罩透明度
    moveType: 1, //拖拽风格，0是默认，1是传统拖动
    shift: 1, //0-6的动画形式，-1不开启
    shadeClose: true,
    content: '${pageContext.request.contextPath}/planSupervision/bidAnnouncement.html?packageId=' + id,
  });
}

function openPrint(projectId,packageId){
    window.open("${pageContext.request.contextPath}/packageExpert/openPrint.html?packageId="+packageId+"&projectId="+projectId, "打印检查汇总表");
  }
function openPrints(projectId,packageId){
    window.open("${pageContext.request.contextPath}/packageExpert/expertConsult.html?packageId="+packageId+"&projectId="+projectId+"&flag=1", "评审汇总表");
  }
</script>
<style>
.flow_tips {
	color: #bbbbbb;
	padding: 0px;
	height: 70px;
}

.flow_tips a {
	display: block;
	width: 100%;
	height: 100%;
}

.flow_tips .tip_main {
	position: relative;
}

.tip_btn {
	padding: 2px 0px;
	border-radius: 4px !important;
	background-color: #e9e9e5;
	z-index: 1000;
}

.pre_btn a p,.current_btn a p {
	color: #ffffff;
}

.flow_tips.pre_btn .tip_btn {
	background-color: #009fa1;
	color: #ffffff;
}

.flow_tips.current_btn .tip_btn {
	background-color: #ffb522;
	color: #ffffff;
	z-index: 1000;
}

.tip_btn a p {
	margin-bottom: 0px;
	text-align: center;
	font-size: 1.2rem;
}

.tip_line {
	position: relative;
	border: 2px solid #e9e9e5;
	top: 20px;
	padding: 0px;
}

.flow_tips.current_btn .tip_line {
	border: 2px solid #ffb522;
}

.flow_tips.pre_btn .tip_line {
	border: 2px solid #009fa1;
}

.flow_tips.pre_btn .tip_down {
	border: 2px solid #009fa1;
}

.tip_down {
	height: 50px;
	border: 2px solid #e9e9e5;
	width: 0px;
	position: absolute;
	top: 25px;
}

.current_btn .tip_down {
	border: 2px solid #ffb522;
}

.last_r {
	float: right;
}

@media ( min-width :768px) {
	.tip_down {
		left: 29%;
	}
	.tip_down {
		display: none;
	}
	.round_r .tip_down {
		display: block;
	}
	.round_r .tip_line {
		display: none;
	}
	.round_l .tip_down {
		display: block;
	}
}

@media ( max-width :767px) {
	.tip_down {
		display: block;
	}
	.tip_line {
		display: none;
	}
}
</style>
</head>

<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="javascript:void(0)">首页</a></li>
				<li><a href="javascript:void(0)">业务监管系统</a></li>
				<li><a href="javascript:void(0)">采购业务监督</a></li>
				<li class="active"><a href="javascript:void(0)">采购项目监督</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<div class="container container_box">
		<div>
			<h2 class="count_flow">
				<i>1</i>项目基本信息
			</h2>
			<ul class="ul_list">
				<table class="table table-bordered mt10">
					<tbody>
						<tr>
							<td width="25%" class="info">项目名称：</td>
							<td width="25%">${project.name}</td>
							<td width="25%" class="info">项目编号：</td>
							<td width="25%">${project.projectNumber}</td>
						</tr>
						<tr>
							<td width="25%" class="info">计划名称：</td>
							<td width="25%">${name}</td>
							<td width="25%" class="info">计划编号：</td>
							<td width="25%">${number}</td>
						</tr>
						<tr>
							<td width="25%" class="info">需求部门：</td>
							<td width="25%"></td>
							<td width="25%" class="info">采购管理部门：</td>
							<td width="25%">${org.name}</td>
						</tr>
						<tr>
							<td width="25%" class="info">项目状态：</td>
							<td width="25%">${project.status}</td>
							<td width="25%" class="info">创建人：</td>
							<td width="25%">${project.appointMan}</td>
						</tr>
						<tr>
							<td width="25%" class="info">创建日期：</td>
							<td width="25%"><fmt:formatDate value='${project.createAt}'
									pattern='yyyy年MM月dd日  HH:mm:ss' /></td>
							<td width="25%" class="info"></td>
							<td width="25%"></td>
						</tr>
					</tbody>
				</table>
			</ul>
		</div>
		<div class="padding-top-10 clear">
			<h2 class="count_flow">
				<i>2</i>执行进度
			</h2>
			<ul class="ul_list">
				<div class="col-md-12 col-xs-12 col-sm-12 flow_more">
					<div class="flow_tips col-md-2 col-sm-2 col-xs-12 pre_btn">
						<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
							<a href="javascript:void(0);">
								<p class="tip_main">采购需求编报</p>
								<p class="tip_time">2016-08-08</p>
							</a>
						</div>
						<div class="tip_line col-md-5 col-sm-3"></div>
						<div class="tip_down col-xs-offset-6"></div>
					</div>

					<div
						class="flow_tips col-md-2 col-sm-2 col-xs-12 pre_btn last_small">
						<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
							<a href="javascript:void(0);">
								<p class="tip_main">采购需求受理</p>
								<p class="tip_time">2016-08-08</p>
							</a>
						</div>
						<div class="tip_line col-md-5 col-sm-3"></div>
						<div class="tip_down col-xs-offset-6"></div>
					</div>

					<div class="flow_tips col-md-2 col-sm-2 col-xs-12 pre_btn small_r">
						<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
							<a href="javascript:void(0);">
								<p class="tip_main">预研任务下达</p>
								<p class="tip_time">2016-08-08</p>
							</a>
						</div>
						<div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
						<div class="tip_down col-xs-offset-6"></div>
					</div>

					<div class="flow_tips col-md-2 col-sm-2 col-xs-12 pre_btn small_r">
						<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
							<a href="javascript:void(0);">
								<p class="tip_main">采购计划审核</p>
								<p class="tip_time">2016-08-08</p>
							</a>
						</div>
						<div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
						<div class="tip_down col-xs-offset-6"></div>
					</div>

					<div class="flow_tips col-md-2 col-sm-2 col-xs-12 pre_btn  ">
						<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
							<a href="javascript:void(0);">
								<p class="tip_main">采购计划下达</p>
								<p class="tip_time">2016-08-08</p>
							</a>
						</div>
						<div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
						<div class="tip_down col-xs-offset-6"></div>
					</div>

					<div
						class="flow_tips col-md-2 col-sm-2 col-xs-12 current_btn round_tips round_r">
						<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
							<a href="javascript:void(0);">
								<p class="tip_main">采购任务受领</p>
								<p class="tip_time">2016-08-08</p>
							</a>
						</div>
						<div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
						<div
							class="tip_down col-xs-offset-6 col-sm-offset-1 col-md-offset-1  col-md-offset-0"></div>

					</div>

					<div class="flow_tips col-md-2 col-sm-2 col-xs-12 last_r">
						<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
							<a href="javascript:void(0);">
								<p class="tip_main">采购项目立项</p>
								<p class="tip_time">2016-08-08</p>
							</a>
						</div>
						<div class="tip_down col-xs-offset-6"></div>
					</div>

					<div class="flow_tips col-md-2 col-sm-2 col-xs-12 last_r">
						<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
							<a href="javascript:void(0);">
								<p class="tip_main">采购文件编报</p>
								<p class="tip_time">2016-08-08</p>
							</a>
						</div>
						<div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
						<div class="tip_down col-xs-offset-6"></div>
					</div>

					<div class="flow_tips col-md-2 col-sm-2 col-xs-12 last_r">
						<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
							<a href="javascript:void(0);">
								<p class="tip_main">采购公告发布</p>
								<p class="tip_time">2016-08-08</p>
							</a>
						</div>
						<div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
						<div class="tip_down col-xs-offset-6"></div>
					</div>

					<div class="flow_tips col-md-2 col-sm-2 col-xs-12 last_r">
						<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
							<a href="javascript:void(0);">
								<p class="tip_main">供应商抽取</p>
								<p class="tip_time">2016-08-08</p>
							</a>
						</div>
						<div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
						<div class="tip_down col-xs-offset-6"></div>
					</div>

					<div class="flow_tips col-md-2 col-sm-2 col-xs-12 last_r">
						<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
							<a href="javascript:void(0);">
								<p class="tip_main">采购文件发售</p>
								<p class="tip_time">2016-08-08</p>
							</a>
						</div>
						<div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
						<div class="tip_down col-xs-offset-6"></div>
					</div>
					<div
						class="flow_tips col-md-2 col-sm-2 col-xs-12 round_tips round_l last_r">
						<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
							<a href="javascript:void(0);">
								<p class="tip_main">评审专家抽取</p>
								<p class="tip_time">2016-08-08</p>
							</a>
						</div>
						<div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
						<div
							class="tip_down col-xs-offset-6 col-sm-offset-1 col-md-offset-0"></div>
					</div>
					<div class="flow_tips col-md-2 col-sm-2 col-xs-12">
						<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
							<a href="javascript:void(0);">
								<p class="tip_main">开标</p>
								<p class="tip_time">2016-08-08</p>
							</a>
						</div>
						<div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
						<div class="tip_down col-xs-offset-6"></div>
					</div>
					<div class="flow_tips col-md-2 col-sm-2 col-xs-12">
						<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
							<a href="javascript:void(0);">
								<p class="tip_main">采购项目评审</p>
								<p class="tip_time">2016-08-08</p>
							</a>
						</div>
						<div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
						<div class="tip_down col-xs-offset-6"></div>
					</div>
					<div class="flow_tips col-md-2 col-sm-2 col-xs-12">
						<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
							<a href="javascript:void(0);">
								<p class="tip_main">中标供应商确定</p>
								<p class="tip_time">2016-08-08</p>
							</a>
						</div>
						<div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
						<div class="tip_down col-xs-offset-6"></div>
					</div>
					<div class="flow_tips col-md-2 col-sm-2 col-xs-12">
						<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
							<a href="javascript:void(0);">
								<p class="tip_main">采购合同签订</p>
								<p class="tip_time">2016-08-08</p>
							</a>
						</div>
						<div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
						<div class="tip_down col-xs-offset-6"></div>
					</div>
					<div class="flow_tips col-md-2 col-sm-2 col-xs-12">
						<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
							<a href="javascript:void(0);">
								<p class="tip_main">采购合同履约</p>
								<p class="tip_time">2016-08-08</p>
							</a>
						</div>
						<div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
						<div class="tip_down col-xs-offset-6"></div>
					</div>
					<div class="flow_tips col-md-2 col-sm-2 col-xs-12">
						<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
							<a href="javascript:void(0);">
								<p class="tip_main">质量校验验收</p>
								<p class="tip_time">2016-08-08</p>
							</a>
						</div>
					</div>

				</div>
			</ul>


		</div>
		<div class="padding-top-10 clear">
			<h2 class="count_flow">
				<i>3</i>项目实施明细
			</h2>
			<ul class="ul_list">
				<div>
					<h2 class="count_flow">
						<i>1</i>采购需求编报
					</h2>

					<div class="content table_box">
						<table
							class="table table-bordered table-condensed table-hover table-striped">
							<thead>
								<tr>
									<th width="40%" class="info">采购需求名称</th>
									<th width="20%" class="info">需求部门</th>
									<th width="20%" class="info">编报人</th>
									<th width="20%" class="info">编报时间</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="tc">${purchaseRequired.planName}</td>
									<td class="tc">${purchaseRequired.department}</td>
									<td>${purchaseRequired.userId}</td>
									<td><fmt:formatDate value="${purchaseRequired.createdAt}"
											pattern="yyyy-MM-dd HH:mm:ss" /></td>
								</tr>
							</tbody>
						</table>

					</div>

				</div>
				<div>
					<h2 class="count_flow">
						<i>2</i>采购需求受理
					</h2>

					<div class="content table_box">
						<table
							class="table table-bordered table-condensed table-hover table-striped">
							<thead>
								<tr>
									<th width="40%" class="info">受理结果</th>
									<th width="20%" class="info">采购管理部门</th>
									<th width="20%" class="info">受理人</th>
									<th width="20%" class="info">受理时间</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="tc"><button class="btn" onclick="viewDemand();"
											type="button">查看</button></td>
									<td class="tc">${org.name}</td>
									<td>${auditPerson.userId}</td>
									<td><fmt:formatDate value="${auditPerson.createdAt}"
											pattern="yyyy-MM-dd HH:mm:ss" /></td>
								</tr>
							</tbody>
						</table>

					</div>

				</div>
				<div>
					<h2 class="count_flow">
						<i>3</i>预研任务下达
					</h2>

					<div class="content table_box">
						<table
							class="table table-bordered table-condensed table-hover table-striped">
							<thead>
								<tr>
									<th width="40%" class="info">预研通知书名称</th>
									<th width="20%" class="info">采购管理部门</th>
									<th width="20%" class="info">下达人</th>
									<th width="20%" class="info">下达时间</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="tc"><button class="btn" onclick="viewDemand();"
											type="button">查看</button></td>
									<td class="tc">${task.orgName}</td>
									<td>${task.createrId}</td>
									<td><fmt:formatDate value="${task.giveTime}"
											pattern="yyyy-MM-dd HH:mm:ss" /></td>
								</tr>
							</tbody>
						</table>

					</div>

				</div>
				<div>
					<h2 class="count_flow">
						<i>4</i>采购计划审核
					</h2>

					<div class="content table_box">
						<table
							class="table table-bordered table-condensed table-hover table-striped">
							<thead>
								<tr>
									<th width="40%" class="info">审核轮次</th>
									<th width="20%" class="info">审核人员</th>
									<th width="20%" class="info">审核意见</th>
									<th width="20%" class="info">审核时间</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${auditPersonsList}" var="obj" varStatus="vs">
									<tr>
										<td class="tc">第${(vs.index+1)}轮</td>
										<td>${obj.name}</td>
										<td><button class="btn" onclick="viewDemand();"
												type="button">查看</button></td>
										<td><fmt:formatDate value='${obj.createDate}'
												pattern='yyyy-MM-dd HH:mm:ss' /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<div>
					<h2 class="count_flow">
						<i>5</i>采购任务下达
					</h2>

					<div class="content table_box">
						<table
							class="table table-bordered table-condensed table-hover table-striped">
							<thead>
								<tr>
									<th width="20%" class="info">采购任务名称</th>
									<th width="20%" class="info">任务文号</th>
									<th width="20%" class="info">采购管理部门</th>
									<th width="20%" class="info">下达人</th>
									<th width="20%" class="info">下达时间</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>${collectPlan.fileName}</td>
									<td>${collectPlan.planNo}</td>
									<td>${collectPlan.purchaseId}</td>
									<td>${collectPlan.userId}</td>
									<td><fmt:formatDate value='${collectPlan.updatedAt}'
											pattern='yyyy-MM-dd HH:mm:ss' /></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div>
					<h2 class="count_flow">
						<i>6</i>采购任务受领
					</h2>

					<div class="content table_box">
						<table
							class="table table-bordered table-condensed table-hover table-striped">
							<thead>
								<tr>
									<th width="20%" class="info">采购任务名称</th>
									<th width="20%" class="info">采购机构</th>
									<th width="20%" class="info">任务性质</th>
									<th width="20%" class="info">受领人</th>
									<th width="20%" class="info">受领时间</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>${pltask.name}</td>
									<td>${pltask.purchaseId}</td>
									<td><c:if test="${pltask.taskNature eq '0'}">正常</c:if> <c:if
											test="${pltask.taskNature eq '1'}">预研</c:if></td>
									<td>${pltask.userId}</td>
									<td><fmt:formatDate value='${pltask.acceptTime}'
											pattern='yyyy-MM-dd HH:mm:ss' /></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div>
					<h2 class="count_flow">
						<i>7</i>采购项目立项
					</h2>

					<div class="content table_box">
						<table
							class="table table-bordered table-condensed table-hover table-striped">
							<thead>
								<tr>
									<th width="25%" class="info">采购项目名称</th>
									<th width="15%" class="info">立项审批文件</th>
									<th width="15%" class="info">立项部门</th>
									<th width="15%" class="info">任务性质</th>
									<th width="15%" class="info">立项人</th>
									<th width="15%" class="info">立项时间</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>${project.name}</td>
									<td class="tc"><button class="btn"
											onclick="viewUpload('${uploadId}');" type="button">查看</button></td>
									<td>${project.purchaseDepName}</td>
									<td><c:if test="${project.isRehearse==1}">预研</c:if> <c:if
											test="${project.isRehearse!=1}">正常</c:if></td>
									<td>${project.appointMan}</td>
									<td><fmt:formatDate value='${project.createAt}'
											pattern='yyyy-MM-dd HH:mm:ss' /></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>

				<div>
					<h2 class="count_flow">
						<i>8</i>采购文件编报
					</h2>

					<div class="content table_box">
						<table
							class="table table-bordered table-condensed table-hover table-striped">
							<thead>
								<tr>
									<th width="20%" class="info">采购文件名称</th>
									<th width="15%" class="info">编制人</th>
									<th width="15%" class="info">提报时间</th>
									<th width="35%" class="info">审核意见</th>
									<th width="15%" class="info">意见批复时间</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>${fileName}</td>
									<td>${operatorName}</td>
									<td><fmt:formatDate value='${project.approvalTime}'
											pattern='yyyy-MM-dd HH:mm:ss' /></td>
									<td></td>
									<td><fmt:formatDate value='${project.replyTime}'
											pattern='yyyy年MM月dd日  HH:mm:ss' /></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div>
					<h2 class="count_flow">
						<i>9</i>采购公告发布
					</h2>

					<div class="content table_box">
						<table
							class="table table-bordered table-condensed table-hover table-striped">
							<thead>
								<tr>
									<th width="40%" class="info">公告名称</th>
									<th width="30%" class="info">编制人</th>
									<th width="30%" class="info">编制时间</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>${articles.name}</td>
									<td>${articles.userId}</td>
									<td><fmt:formatDate value='${articles.createdAt}'
											pattern='yyyy-MM-dd HH:mm:ss' /></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div>
					<h2 class="count_flow">
						<i>10</i>供应商抽取
					</h2>

					<div class="content table_box">
						<table
							class="table table-bordered table-condensed table-hover table-striped">
							<thead>
								<tr>
									<th width="25%" class="info">抽取记录</th>
									<th width="25%" class="info">抽取人</th>
									<th width="25%" class="info">监督人</th>
									<th width="25%" class="info">抽取时间</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div>
					<h2 class="count_flow">
						<i>11</i>采购文件发售
					</h2>
					<div class="content table_box">
						<table
							class="table table-bordered table-condensed table-hover table-striped">
							<thead>
								<tr>
									<th width="40%" class="info">文件发售记录</th>
									<th width="30%" class="info">操作人</th>
									<th width="30%" class="info">发售时间</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><button class="btn" onclick="sell('${packageId}','1')"
											type="button">查看</button></td>
									<td></td>
									<td>
									   ${begin}
									   <c:if test="${end!=null}">
									      -${end}
									   </c:if>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div>
					<h2 class="count_flow">
						<i>12</i>评审专家抽取
					</h2>
					<div class="content table_box">
						<table
							class="table table-bordered table-condensed table-hover table-striped">
							<thead>
								<tr>
									<th width="30%" class="info">抽取记录</th>
									<th width="25%" class="info">抽取人</th>
									<th width="25%" class="info">监督人</th>
									<th width="20%" class="info">抽取时间</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div>
					<h2 class="count_flow">
						<i>13</i>开标
					</h2>
					<div class="content table_box">
						<table
							class="table table-bordered table-condensed table-hover table-striped">
							<thead>
								<tr>
									<th width="30%" class="info">投标记录</th>
									<th width="25%" class="info">开标一览表</th>
									<th width="25%" class="info">开标人</th>
									<th width="20%" class="info">开标时间</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><button class="btn" onclick="sell('${packageId}','2')"
											type="button">查看</button></td>
									<td><button class="btn" onclick="bid('${packageId}')"
											type="button">查看</button></td>
									<td></td>
									<td><fmt:formatDate value='${project.bidDate}'
											pattern='yyyy-MM-dd HH:mm:ss' /></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div>
					<h2 class="count_flow">
						<i>14</i>采购项目评审
					</h2>
					<div class="content table_box">
						<table
							class="table table-bordered table-condensed table-hover table-striped">
							<thead>
								<tr>
									<th width="30%" class="info">文件名称</th>
									<th width="25%" class="info">查看评审专家打分表</th>
									<th width="25%" class="info">查看汇总表</th>
									<th width="20%" class="info">评审时间</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>资格性符合性检查</td>
									<td><c:forEach items="${experts}" var="obj" varStatus="vs">
											<c:set value="${vs.index}" var="index"></c:set>
											<a
												href="${pageContext.request.contextPath}/packageExpert/printView.html?projectId=${project.id}&packageId=${packageId}&expertId=${experts[index].id}"
												target="view_window">${experts[index].relName}</a>
										</c:forEach></td>
									<td class="tc"><button class="btn"
											onclick="openPrint('${project.id}','${packageId}')"
											type="button">查看</button></td>
									<td></td>
								</tr>
								<tr>
									<td>技术商务评分（审查）</td>
									<td><c:forEach items="${experts}" var="obj" varStatus="vs">
											<c:set value="${vs.index}" var="index"></c:set>
											<a
												href="${pageContext.request.contextPath}/packageExpert/printView.html?projectId=${project.id}&packageId=${packageId}&expertId=${experts[index].id}&auditType=1"
												target="view_window">${experts[index].relName}</a>
										</c:forEach></td>
									<td class="tc"><button class="btn"
											onclick="openPrints('${project.id}','${packageId}')"
											type="button">查看</button></td>
									<td></td>
								</tr>
								<%-- <tr>
                  <td>专家评审报告</td>
                  <td>
                    <c:forEach items="${expertIdList}" var="obj" varStatus="vs">
                      <c:if test="${obj.isGroupLeader == 1}">组长:${obj.expertId}</c:if>
                    </c:forEach>
                  </td>
                  <td class="tc"><button class="btn" onclick="bid('${packageId}')" type="button">查看</button></td>
                  <td>
                  </td>
                </tr> --%>
							</tbody>
						</table>
					</div>
				</div>
				<div>
					<h2 class="count_flow">
						<i>15</i>中标公示发布
					</h2>
					<div class="content table_box">
						<table
							class="table table-bordered table-condensed table-hover table-striped">
							<thead>
								<tr>
									<th width="40%" class="info">中标公示名称</th>
									<th width="30%" class="info">编制人</th>
									<th width="30%" class="info">编制时间</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>${articleList.name}</td>
									<td>${articleList.userId}</td>
									<td><fmt:formatDate value='${articleList.createdAt}'
											pattern='yyyy-MM-dd HH:mm:ss' /></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div>
					<h2 class="count_flow">
						<i>16</i>中标供应商确定
					</h2>
					<div class="content table_box">
						<table
							class="table table-bordered table-condensed table-hover table-striped">
							<thead>
								<tr>
									<th width="25%" class="info">中标供应商名称</th>
									<th width="25%" class="info">评分排序</th>
									<th width="25%" class="info">操作人</th>
									<th width="25%" class="info">确定时间</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${pass}" var="s">
								<tr>
									<td>
									      ${s.supplier.supplierName}
									  </td>
									<td></td>
									<td></td>
									<td><fmt:formatDate value="${s.confirmTime}" pattern="yyyy-MM-dd HH:mm:ss"/>  </td>
								</tr>
								 </c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<div>
					<h2 class="count_flow">
						<i>17</i>采购合同签订
					</h2>
					<div class="content table_box">
						<table
							class="table table-bordered table-condensed table-hover table-striped">
							<thead>
								<tr>
									<th width="25%" class="info">合同名称</th>
									<th width="25%" class="info">甲方</th>
									<th width="25%" class="info">乙方</th>
									<th width="25%" class="info">签订时间</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>${purchaseContract.name}</td>
									<td>${purchaseContract.purchaseDepName}</td>
									<td>${purchaseContract.supplierDepName}</td>
									<td>
									 <fmt:formatDate value="${purchaseContract.formalAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div>
					<h2 class="count_flow">
						<i>18</i>采购合同履约
					</h2>
					<div class="content table_box">
						<table
							class="table table-bordered table-condensed table-hover table-striped">
							<thead>
								<tr>
									<th width="25%" class="info">履约记录</th>
									<th width="25%" class="info">甲方</th>
									<th width="25%" class="info">乙方</th>
									<th width="25%" class="info">签订时间</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div>
					<h2 class="count_flow">
						<i>19</i>采购质检验收
					</h2>
					<div class="content table_box">
						<table
							class="table table-bordered table-condensed table-hover table-striped">
							<thead>
								<tr>
									<th width="25%" class="info">验收记录</th>
									<th width="25%" class="info">质检专家</th>
									<th width="25%" class="info">质检单位</th>
									<th width="25%" class="info">验收时间</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</ul>
		</div>
		<div class="col-md-12 col-xs-12 col-sm-12 tc mt20">
			<button class="btn btn-windows back" onclick="window.history.go(-1)"
				type="button">返回</button>
		</div>
	</div>
</body>

</html>