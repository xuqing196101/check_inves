<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en">
	<!--<![endif]-->
	<head>
		<%@ include file="../../../../common.jsp"%>
		<script type="text/javascript">
			function tijiao(str) {
				var action;
				if(str == "essential") {
					action = "${pageContext.request.contextPath}/supplierQuery/essential.html";
				}
				if(str == "financial") {
					action = "${pageContext.request.contextPath}/supplierQuery/financial.html";
				}
				if(str == "shareholder") {
					action = "${pageContext.request.contextPath}/supplierQuery/shareholder.html";
				}
				if(str == "materialProduction") {
					action = "${pageContext.request.contextPath}/supplierQuery/materialProduction.html";
				}
				if(str == "materialSales") {
					action = "${pageContext.request.contextPath}/supplierQuery/materialSales.html";
				}
				if(str == "engineering") {
					action = "${pageContext.request.contextPath}/supplierQuery/engineering.html";
				}
				if(str == "service") {
					action = "${pageContext.request.contextPath}/supplierQuery/serviceInformation.html";
				}
				if(str == "chengxin") {
					action = "${pageContext.request.contextPath}/supplierQuery/list.html";
				}
				if(str == "item") {
					action = "${pageContext.request.contextPath}/supplierQuery/item.html";
				}
				if(str == "product") {
					action = "${pageContext.request.contextPath}/supplierQuery/product.html";
				}
				if(str == "updateHistory") {
					action = "${pageContext.request.contextPath}/supplierQuery/showUpdateHistory.html";
				}
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}

			function fanhui() {
				if('${category}' == 1) {
					window.location.href = "${pageContext.request.contextPath}/supplierQuery/selectByCategory.html";
				} else {
					window.location.href = "${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html?address=" + encodeURI(encodeURI('${suppliers.address}')) + "&status=${status}";
				}
			}
		</script>
	</head>

	<body>
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="#"> 首页</a>
					</li>
					<li>
						<a href="#">支撑系统</a>
					</li>
					<li>
						<a href="#">供应商查看</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<!-- 项目戳开始 -->
		<div class="container clear mt10">
			<!--详情开始-->
			<div class="container content pt0">
				<div class="tab-v2">
					<ul class="nav nav-tabs bgwhite">
						<li class="active">
							<a aria-expanded="true" href="#tab-1" data-toggle="tab" class="f18" onclick="tijiao('essential');">基本信息</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('financial');">财务信息</a>
						</li>
						<li class="">
							<a aria-expanded="fale" href="#tab-3" data-toggle="tab" class="f18" onclick="tijiao('shareholder');">股东信息</a>
						</li>
						<c:if test="${fn:contains(suppliers.supplierType, '生产')}">
							<li class="">
								<a aria-expanded="fale" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('materialProduction');">物资-生产型专业信息</a>
							</li>
						</c:if>
						<c:if test="${fn:contains(suppliers.supplierType, '销售')}">
							<li class="">
								<a aria-expanded="fale" href="#tab-3" data-toggle="tab" class="f18" onclick="tijiao('materialSales');">物资-销售型专业信息</a>
							</li>
						</c:if>
						<c:if test="${fn:contains(suppliers.supplierType, '工程')}">
							<li class="">
								<a aria-expanded="false" href="#tab-3" data-toggle="tab" class="f18" onclick="tijiao('engineering');">工程-专业信息</a>
							</li>
						</c:if>
						<c:if test="${fn:contains(suppliers.supplierType, '服务')}">
							<li class="">
								<a aria-expanded="false" href="#tab-3" data-toggle="tab" class="f18" onclick="tijiao('service');">服务-专业信息</a>
							</li>
						</c:if>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('item');">品目信息</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-3" data-toggle="tab" class="f18" onclick="tijiao('product');">产品信息</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('chengxin');">诚信记录</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('updateHistory');">历史修改记录</a>
						</li>
					</ul>
					<div class="tab-content padding-top-20">
						<div class="tab-pane fade active in">
							<form id="form_id" action="" method="post">
								<input name="supplierId" id="id" value="${suppliers.id }" type="hidden">
							</form>
							<h2 class="count_flow jbxx">企业基本信息</h2>
							<table class="table table-bordered">
								<tbody>
									<tr>
										<td class="bggrey" width="20%">供应商名称：</td>
										<td width="30%">${suppliers.supplierName}</td>
										<td class="bggrey" width="20%">公司网址：</td>
										<td>${suppliers.website}</td>
									</tr>
									<tr>
										<td class="bggrey" width="20%">成立日期：</td>
										<td>
											<fmt:formatDate value='${suppliers.foundDate}' pattern='yyyy-MM-dd' />
										</td>
										<td class="bggrey" width="20%">营业执照登记类型：</td>
										<td onmouseover="out('${suppliers.businessType }')">${suppliers.businessType}</td>
									</tr>
									<tr>
										<td class="bggrey" width="20%">地址：</td>
										<td>${suppliers.address}</td>
										<td class="bggrey" width="20%" width="20%">开户行名称：</td>
										<td>${suppliers.bankName}</td>
									</tr>
									<tr>
										<td class="bggrey" width="20%">开户行账户：</td>
										<td width="30%">${suppliers.bankAccount}</td>
										<td class="bggrey" width="20%">邮编：</td>
										<td width="30%">${suppliers.postCode }</td>
									</tr>
								</tbody>
							</table>
						</div>

						<div class="tab-pane fade active in">
							<h2 class="count_flow jbxx">资质资信</h2>
							<table class="table table-bordered">
								<tbody>
									<tr>
										<td class="bggrey" width="20%">近三个月完税凭证：</td>
										<td width="30%">
											<up:show showId="taxcert_show" delete="flase" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,auditopinion_show,auditopinion_show,business_show" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierTaxCert}" />
										</td>
										<td class="bggrey" width="20%">近三年银行账单：</td>
										<td width="30%">
											<up:show showId="billcert_show" delete="flase" groups="" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBillCert}" />
										</td>
									</tr>
									<tr>
										<td class="bggrey" width="20%">近三个月保险凭证：</td>
										<td width="30%">
											<up:show showId="curitycert_show" delete="flase" groups="" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierSecurityCert}" />
										</td>
										<td class="bggrey" width="20%">近三年违法记录：</td>
										<td width="30%">
											<up:show showId="bearchcert_show" delete="flase" groups="" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBearchCert}" />
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
										<td class="bggrey" width="20%">法定代表人姓名：</td>
										<td width="30%">${suppliers.legalName}</td>
										<td class="bggrey" width="20%">身份证号：</td>
										<td width="30%">${suppliers.legalIdCard}</td>
									</tr>
									<tr>
										<td class="bggrey" width="20%">固定电话：</td>
										<td width="30%">${suppliers.legalTelephone}</td>
										<td class="bggrey" width="20%">手机：</td>
										<td width="30%">${suppliers.legalMobile }</td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="tab-pane fade active in">
							<h2 class="count_flow jbxx">联系人信息</h2>
							<table class="table table-bordered">
								<tbody>
									<tr>
										<td class="bggrey" width="20%">姓名：</td>
										<td width="30%">${suppliers.contactName } </td>
										<td class="bggrey" width="20%">传真：</td>
										<td width="30%">${suppliers.contactFax }</td>
									</tr>
									<tr>
										<td class="bggrey" width="20%">地址：</td>
										<td width="30%">${suppliers.contactAddress}</td>
										<td class="bggrey" width="20%">固定电话：</td>
										<td width="30%">${suppliers.contactTelephone }</td>
									</tr>
									<tr>
										<td class="bggrey" width="20%">手机：</td>
										<td width="30%">${suppliers.contactMobile } </td>
										<td class="bggrey" width="20%">邮箱：</td>
										<td width="30%">${suppliers.contactEmail }</td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="tab-pane fade active in">
							<h2 class="count_flow jbxx">营业执照</h2>
							<table class="table table-bordered">
								<tbody>
									<tr>
										<td class="bggrey" width="20%">统一社会信用代码：</td>
										<td width="30%">${suppliers.creditCode } </td>
										<td class="bggrey" width="20%">登记机关：</td>
										<td width="30%">${suppliers.registAuthority}</td>
									</tr>

									<tr>
										<td class="bggrey" width="20%">注册资本：</td>
										<td width="30%">${suppliers.registFund }</td>
										<td class="bggrey" width="20%">营业期限：</td>
										<td width="30%">
											<fmt:formatDate value="${suppliers.businessStartDate}" pattern="yyyy-MM-dd" />至
											<fmt:formatDate value="${suppliers.businessEndDate}" pattern="yyyy-MM-dd" /> </td>
									</tr>
									<tr>
										<td class="bggrey" width="20%">邮编：</td>
										<td width="30%"> ${suppliers.businessPostCode } </td>
										<td class="bggrey" width="20%">营业执照：</td>
										<td class="hand" width="30%">
											<up:show showId="business_show" delete="flase" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" />
										</td>
									</tr>
									<tr>
										<td class="bggrey" width="20%">生产或经营地址：</td>
										<td width="30%">${suppliers.businessAddress}</td>
										<td class="bggrey" width="20%">营业范围：</td>
										<td width="30%">${suppliers.businessScope}</td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="tab-pane fade active in">
							<h2 class="count_flow jbxx">境外分支</h2>
							<table class="table table-bordered">
								<tbody>
									<tr>
										<td class="bggrey" width="20%">境外分支机构：</td>
										<td width="30%">
											<c:if test="${suppliers.overseasBranch==0}">无</c:if>
											<c:if test="${suppliers.overseasBranch==1}">有</c:if>
										</td>
										<td class="bggrey" width="20%">国家：</td>
										<td width="30%">${suppliers.branchCountry}</td>
									</tr>
									<tr>
										<td class="bggrey" width="20%">分支地址：</td>
										<td width="30%">${suppliers.branchAddress}</td>
										<td class="bggrey" width="20%">机构名称：</td>
										<td width="30%">${suppliers.branchName}</td>
									</tr>
									<tr>
										<td class="bggrey" width="20%">分支生产经营范围：</td>
										<td colspan="3">${suppliers.branchBusinessScope } </td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="col-md-12 tc">
					<c:if test="${empty person }">
						<button class="btn btn-windows back" onclick="fanhui()">返回</button>
					</c:if>
			</div>
			</div>
		</div>
	</body>
</html>