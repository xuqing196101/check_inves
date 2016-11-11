<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>


<title>项目管理</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="${pageContext.request.contextPath}/public/ZHH/css/style.css" media="screen" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/My97DatePicker/WdatePicker.js"></script>


<script type="text/javascript">
	$(function() {
		$("#hide_detail").hide();
	});

	function print(id) {
		window.location.href = "${pageContext.request.contextPath}/project/print.html?id="
				+ id;
	}
	function view(sign) {
		if (sign) {
			$("#hide_detail").show();
		} else {
			$("#hide_detail").hide();
		}
	}
</script>
</head>

<body>
	<!--面包屑导航开始-->
	<div class="container content pt0">
		<div class="row magazine-page">
			<div class="col-md-12 tab-v2">
				<div class="padding-top-10">
					<ul class="nav nav-tabs bgwhite">
						<li class="active"><a aria-expanded="true" href="#tab-1"
							data-toggle="tab" class="s_news f18">详细信息</a></li>
						<li class=""><a aria-expanded="false" href="#tab-2"
							data-toggle="tab" class="s_news f18">项目明细</a></li>
							<li class=""><a aria-expanded="false" href="#tab-3"
                            data-toggle="tab" class="fujian f18">项目表单</a></li>
						<li class=""><a aria-expanded="false" href="#tab-4"
							data-toggle="tab" class="fujian f18">打印报批文件</a></li>
					</ul>
					<div class="tab-content padding-top-20">
						<div class="tab-pane fade active in" id="tab-1">
							<h2 class="count_flow jbxx">基本信息</h2>
							<form id="save_form_id" action="${pageContext.request.contextPath}/project/addProject.html" method="post" target="_parent">
							<table class="table table-bordered">
								<tbody>
									<tr>
										<td class="bggrey">项目编号:</td>
										<td>${project.projectNumber}<input type="hidden" name="id" value="${project.id}"/></td>
										<td class="bggrey">项目名称:</td>
										<td>${project.name}</td>
									</tr>
									<tr>
										<td class="bggrey">负责人姓名:</td>
										<td>${project.principal}</td>
										<td class="bggrey">负责人联系电话:</td>
										<td>${project.ipone}</td>
									</tr>
									<tr>
										<td class="bggrey">联系人姓名:</td>
										<td><input name="linkman" value="${project.linkman}" /></td>
										<td class="bggrey">联系人联系电话:</td>
										<td><input name="linkmanIpone" value="${project.linkmanIpone}" /></td>
									</tr>
									<tr>
										<td class="bggrey">招标单位:</td>
										<td>${project.sectorOfDemand}</td>
										<td class="bggrey">联系地址:</td>
										<td>${project.address}</td>
									</tr>
									<tr>
										<td class="bggrey">邮编:</td>
										<td>${project.postcode}</td>
										<td class="bggrey">最少供应商人数:</td>
										<td><input name="supplierNumber" value="${project.supplierNumber}" /></td>
									</tr>
									<tr>
										<td class="bggrey">报价标准分值:</td>
										<td>${project.offerStandard}</td>
										<td class="bggrey">预算报价（万元）:</td>
										<td>${project.budgetAmount}</td>
									</tr>
									<%--  <tr>
									        <c:forEach items="${info.list}" var="obj" varStatus="vs">
									          <td class="bggrey tr">${obj.name}密码:</td><td>${obj.passWord}</td>
									          </c:forEach>
									          <td class="bggrey tr">评分细则:</td><td>${project.scoringRubric}</td>
									        </tr> --%>
									<tr>
										<td class="bggrey">采购方式:</td>
										<td>${project.purchaseType}</td>
										<td class="bggrey">投标截止时间:</td>
										<td>${project.deadline}</td>
									</tr>
									<tr>
										<td class="bggrey">开标时间:</td>
										<%-- <td>${project.bidDate}<input name="bidDate"/></td> --%>
										<td><input  readonly="readonly" value="<fmt:formatDate type='date' value='${project.bidDate }' dateStyle="default" pattern="yyyy-MM-dd"/>" name="bidDate" id="birthday" type="text" onclick='WdatePicker()'></td>
										<td class="bggrey">开标地点:</td>
										<td><input name="bidAddress" value="${project.bidAddress}"/></td>
									</tr>
									<tr>
										<td class="bggrey">招标文件报批时间:</td>
										<td>${project.approvalTime}</td>
										<td class="bggrey">招标文件批复时间:</td>
										<td>${project.replyTime}</td>
									</tr>
									<tr>
										<td class="bggrey">需求计划提报时间:</td>
										<td>${project.demandFromTime}</td>
										<td class="bggrey">${task.name}采购任务下达时间:</td>
										<td><fmt:formatDate value='${task.giveTime}'
												pattern='yyyy年MM月dd日  HH:mm:ss' /></td>
									</tr>
									<tr>
										<td class="bggrey">${task.name}采购任务受理时间:</td>
										<td><fmt:formatDate value='${task.acceptTime}'
												pattern='yyyy年MM月dd日  HH:mm:ss' /></td>
										<td class="bggrey">采购项目立项时间:</td>
										<td><fmt:formatDate value='${project.createAt}'
												pattern='yyyy年MM月dd日  HH:mm:ss' /></td>
									</tr>
									<tr>
										<td class="bggrey">采购项目实施时间:</td>
										<td><fmt:formatDate value='${project.startTime}'
												pattern='yyyy年MM月dd日  HH:mm:ss' /></td>
										<td class="bggrey">招标公告发布时间:</td>
										<td>${project.noticeNewsTime}</td>
									</tr>
									<tr>
										<td class="bggrey">招标公告审批时间:</td>
										<td>${project.appTime}</td>
										<td class="bggrey">供应商报名时间:</td>
										<td>${project.signUpTime}</td>
									</tr>
									<tr>
										<td class="bggrey">报名截止时间:</td>
										<td>${project.applyDeanline}</td>
										<td class="bggrey">售后维护时间:</td>
										<td>${project.maintenanceTime}</td>
									</tr>
									<tr>
										<td class="bggrey">发送中标通知书时间:</td>
										<td>${project.noticeTime}</td>
										<td class="bggrey">项目结束时间:</td>
										<td>${project.endTime}</td>
									</tr>
									<tr>
										<td class="bggrey">合同签订时间:</td>
										<td>${project.signingTime}</td>
										<td class="bggrey">验收时间:</td>
										<td>${project.acceptanceTime}</td>
									</tr>
								</tbody>
							</table>
							 <div class="col-md-12 tc mt20" >
					               <button class="btn btn-windows save"  type="submit">保存</button>
					         </div>
						  </form>
						</div>
						<div class="tab-pane fade " id="tab-2">
							<table class="table table-bordered table-condensed mt5">

                                <thead>
                                    <tr>
                                        <th class="info w50">序号</th>
                                        <th class="info">需求部门</th>
                                        <th class="info">物资名称</th>
                                        <th class="info">规格型号</th>
                                        <th class="info">质量技术标准</th>
                                        <th class="info">计量单位</th>
                                        <th class="info">采购数量</th>
                                        <th class="info">单价（元）</th>
                                        <th class="info">预算金额（万元）</th>
                                        <th class="info">交货期限</th>
                                        <th class="info">采购方式建议</th>
                                        <th class="info">供应商名称</th>
                                        <th class="info">是否申请办理免税</th>
                                        <th class="info">物资用途（进口）</th>
                                        <th class="info">使用单位（进口）</th>
                                    </tr>
                                </thead>
                                <c:forEach items="${lists}" var="obj" varStatus="vs">
                                    <tr style="cursor: pointer;">
                                        <td class="tc w50">${obj.serialNumber}</td>
                                        <td class="tc">${obj.department}</td>
                                        <td class="tc">${obj.goodsName}</td>
                                        <td class="tc">${obj.stand}</td>
                                        <td class="tc">${obj.qualitStand}</td>
                                        <td class="tc">${obj.item}</td>
                                        <td class="tc">${obj.purchaseCount}</td>
                                        <td class="tc">${obj.price}</td>
                                        <td class="tc">${obj.budget}</td>
                                        <td class="tc">${obj.deliverDate}</td>
                                        <td class="tc">${obj.purchaseType}</td>
                                        <td class="tc">${obj.supplier}</td>
                                        <td class="tc">${obj.isFreeTax}</td>
                                        <td class="tc">${obj.goodsUse}</td>
                                        <td class="tc">${obj.useUnit}</td>
                                    </tr>

                                </c:forEach>


                            </table>
						</div>

						<div class="tab-pane fade " id="tab-3" style="position：relative">
				        </div>
			</div>
		</div>
	</div>


</body>
</html>
