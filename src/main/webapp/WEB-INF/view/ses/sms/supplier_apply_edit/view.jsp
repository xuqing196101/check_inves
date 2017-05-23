<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
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
		<script type="text/javascript">
			$(function() {
				if('${suppliers.status}' == 2) {
					showReason();
				}
			});

			function showReason() {
				var supplierId = "${suppliers.id}";
				var left = document.body.clientWidth - 500;
				var top = window.screen.availHeight / 2 - 150;
				layer.open({
					type: 2,
					title: '审核反馈',
					closeBtn: 0, //不显示关闭按钮
					skin: 'layui-layer-lan', //加上边框
					area: ['500px', '300px'], //宽高
					offset: [top, left],
					shade: 0,
					maxmin: true,
					shift: 2,
					content: globalPath + '/supplier_edit/reasonList.html?supplierId=' + supplierId, //url
				});
			}
		</script>
	</head>

	<body>
		<div class=" breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:void(0);"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0);">支撑系统</a>
					</li>
					<li>
						<a href="javascript:void(0);">供应商管理</a>
					</li>
					<li class="active">
						<a href="javascript:void(0);">信息变更</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<!-- 项目戳开始 -->
		<div class="container clear">
			<!--详情开始-->
			<div class="container content height-350">
				<div class="row magazine-page">
					<div class="col-md-12 tab-v2 job-content">
						<div class="padding-top-10">
							<ul class="nav nav-tabs bgdd">
								<li class="active">
									<a aria-expanded="true" href="#tab-1" data-toggle="tab" id="essential">基本信息</a>
								</li>
								<c:if test="${supplier.status ==2 }">
									<li class="">
										<a aria-expanded="true" href="#tab-2" data-toggle="tab">问题汇总</a>
									</li>
								</c:if>
							</ul>
							<div class="tab-content padding-top-20">
								<div class="tab-pane fade active in height-450" id="tab-1">
									<div class="content height-350">
										<div class="tab-pane fade active in">
											<h2 class="count_flow jbxx">企业基本信息</h2>
											<table class="table table-bordered">
												<tbody>
													<tr>
														<td class="bggrey">供应商名称：</td>
														<td>${suppliers.supplierName}</td>
														<td class="bggrey ">公司网址：</td>
														<td>${suppliers.website}</td>
													</tr>
													<tr>
														<td class="bggrey ">成立日期：</td>
														<td>
															<fmt:formatDate value='${suppliers.foundDate}' pattern='yyyy-MM-dd' />
														</td>
														<td class="bggrey">营业执照登记类型：</td>
														<td onmouseover="out('${suppliers.businessType }')">${suppliers.businessType}</td>
													</tr>
													<tr>
														<td class="bggrey">地址：</td>
														<td>${suppliers.address}</td>
														<td class="bggrey">开户行名称：</td>
														<td>${suppliers.bankName}</td>
													</tr>
													<tr>
														<td class="bggrey">开户行账户：</td>
														<td>${suppliers.bankAccount}</td>
														<td class="bggrey">邮编：</td>
														<td colspan="3">${suppliers.postCode }</td>
													</tr>
												</tbody>
											</table>
										</div>

										<div class="tab-pane fade active in">
											<h2 class="count_flow jbxx">资质资信</h2>
											<table class="table table-bordered">
												<tbody>
													<tr>
														<td class="bggrey">近三个月完税凭证：</td>
														<td>
															<up:show showId="taxcert_show" delete="flase" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,auditopinion_show,auditopinion_show" businessId="${suppliers.recordId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierTaxCert}" />
														</td>
														<td class="bggrey">近三年银行账单：</td>
														<td>
															<up:show showId="billcert_show" delete="flase" groups="" businessId="${suppliers.recordId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBillCert}" />
														</td>
													</tr>
													<tr>
														<td class="bggrey">近三个月保险凭证：</td>
														<td>
															<up:show showId="curitycert_show" delete="flase" groups="" businessId="${suppliers.recordId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierSecurityCert}" />
														</td>
														<td class="bggrey">近三年违法记录：</td>
														<td>
															<up:show showId="bearchcert_show" delete="flase" groups="" businessId="${suppliers.recordId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBearchCert}" />
														</td>
													</tr>
												</tbody>
											</table>
										</div>

										<div class="tab-pane fade active in">
											<h2 class="count_flow jbxx">法定代表人信息</h2>
											<table class="table table-bordered">
												<tbody>
													<tr>
														<td class="bggrey">法定代表人姓名：</td>
														<td>${suppliers.legalName}</td>
														<td class="bggrey ">身份证号：</td>
														<td>${suppliers.legalIdCard}</td>
													</tr>
													<tr>
														<td class="bggrey ">固定电话：</td>
														<td>${suppliers.legalTelephone}</td>
														<td class="bggrey">手机：</td>
														<td>${suppliers.legalMobile }</td>
													</tr>
												</tbody>
											</table>
										</div>
										<div class="tab-pane fade active in">
											<h2 class="count_flow jbxx">联系人信息</h2>
											<table class="table table-bordered">
												<tbody>
													<tr>
														<td class="bggrey">姓名：</td>
														<td>${suppliers.contactName } </td>
														<td class="bggrey">传真：</td>
														<td>${suppliers.contactFax }</td>
													</tr>
													<tr>
														<td class="bggrey">地址：</td>
														<td>${suppliers.contactAddress}</td>
														<td class="bggrey">固定电话：</td>
														<td>${suppliers.contactTelephone }</td>
													</tr>
													<tr>
														<td class="bggrey">手机：</td>
														<td>${suppliers.contactMobile } </td>
														<td class="bggrey">邮箱：</td>
														<td>${suppliers.contactEmail }</td>
													</tr>
												</tbody>
											</table>
										</div>
										<div class="tab-pane fade active in">
											<h2 class="count_flow jbxx">营业执照</h2>
											<table class="table table-bordered">
												<tbody>
													<tr>
														<td class="bggrey">统一社会信用代码：</td>
														<td>${suppliers.creditCode } </td>
														<td class="bggrey">登记机关：</td>
														<td>${suppliers.registAuthority}</td>
													</tr>

													<tr>
														<td class="bggrey">注册资本：</td>
														<td>${suppliers.registFund }</td>
														<td class="bggrey">营业期限：</td>
														<td>
															<fmt:formatDate value="${suppliers.businessStartDate}" pattern="yyyy-MM-dd" />至
															<fmt:formatDate value="${suppliers.businessEndDate}" pattern="yyyy-MM-dd" /> </td>
													</tr>
													<tr>
														<td class="bggrey">邮编：</td>
														<td> ${suppliers.businessPostCode } </td>
														<td class="bggrey">营业执照：</td>
														<td class="hand">${suppliers.businessCert}</td>
													</tr>
													<tr>
														<td class="bggrey">生产或经营地址：</td>
														<td>${suppliers.businessAddress}</td>
														<td class="bggrey">营业范围：</td>
														<td>${suppliers.businessScope}</td>
													</tr>
												</tbody>
											</table>
										</div>
										<div class="tab-pane fade active in">
											<h2 class="count_flow jbxx">境外分支</h2>
											<table class="table table-bordered">
												<tbody>
													<tr>
														<td class="bggrey">境外分支机构：</td>
														<td>
															<c:if test="${suppliers.overseasBranch==1 }">有</c:if>
															<c:if test="${suppliers.overseasBranch==0 }">无</c:if>
														</td>
														<td class="bggrey">国家：</td>
														<td>${suppliers.branchCountry}</td>
													</tr>
													<tr>
														<td class="bggrey">分支地址：</td>
														<td>${suppliers.branchAddress}</td>
														<td class="bggrey">机构名称：</td>
														<td>${suppliers.branchName}</td>
													</tr>
													<tr>
														<td class="bggrey">分支生产经营范围：</td>
														<td colspan="3">${suppliers.branchBusinessScope } </td>
													</tr>
												</tbody>
											</table>
										</div>

									</div>
									<div class=" tc">
										<input class="btn btn-windows reset" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
									</div>
								</div>
								<div class="tab-pane fade height-450" id="tab-2">
									<table class="table table-bordered table-condensed">
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
									<div class=" tc">
										<input class="btn btn-windows reset" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>

</html>