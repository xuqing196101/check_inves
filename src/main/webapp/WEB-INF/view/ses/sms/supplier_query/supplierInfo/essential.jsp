<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en">

	<head>
		<%@ include file="../../../../common.jsp"%>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
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
				if(str == "supplierType") {
					action = "${pageContext.request.contextPath}/supplierQuery/supplierType.html";
				}
				if(str == "zizhi") {
					action = "${pageContext.request.contextPath}/supplierQuery/aptitude.html";
				}
				if(str == "contract") {
					action = "${pageContext.request.contextPath}/supplierQuery/contract.html";
				}
				if(str == "supplierType") {
					action = "${pageContext.request.contextPath}/supplierQuery/supplierType.html";
				}
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			};

			/* function fanhui() {
				if('${judge}' == 2) {
					window.location.href = "${pageContext.request.contextPath}/supplierQuery/selectByCategory.html";
				} else {
					window.location.href = "${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html?address=" + encodeURI(encodeURI('${suppliers.address}')) + "&judge=${judge}";
				};
			}; */
			function fanhui() {
				if('${judge}' == 2) {
					window.location.href = "${pageContext.request.contextPath}/supplierQuery/selectByCategory.html";
				} else {
					var action = "${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html";
					$("#form_back").attr("action", action);
					$("#form_back").submit();
				};
			};

			//为只读
			$(function() {
				$(":input").each(function() {
					$(this).attr("readonly", "readonly");
				});
			});
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
						<a href="#">支撑环境</a>
					</li>
					<li>
						<a href="#">供应商管理</a>
					</li>
					<li>
						<a href="#">供应商查看</a>
					</li>
				</ul>
			</div>
		</div>
		<div class="container container_box">
			<div class=" content height-350">
				<div class="col-md-12 tab-v2 job-content">
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
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('supplierType');">供应商类型</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('item');">产品类别</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('zizhi');">资质文件</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('contract');">销售合同</a>
						</li>
						<!-- <li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('chengxin');">诚信记录</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('updateHistory');">历史修改记录</a>
						</li> -->
					</ul>

					<form id="form_id" action="" method="post">
						<input name="supplierId" id="id" value="${suppliers.id }" type="hidden">
						<input name="judge" value="${judge}" type="hidden">
						<input name="sign" value="${sign}" type="hidden">
					</form>
					<form id="form_back" action="" method="post">
						<input name="judge" value="${judge}" type="hidden">
						<c:if test="${sign!=1 and sign!=2 }">
							<input name="address" value="${suppliers.address}" type="hidden">
						</c:if>
						<input name="sign" value="${sign}" type="hidden">
					</form>
					<h2 class="count_flow"><i>1</i>供应商信息</h2>
					<ul class="ul_list">
						<table class="table table-bordered">
							<tbody>
								<tr>
									<td class="bggrey" width="20%">供应商名称：</td>
									<td width="30%">${suppliers.supplierName}</td>
									<td class="bggrey" width="20%">网址：</td>
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
									<td class="bggrey" width="20%">银行账号：</td>
									<td width="30%">${suppliers.bankAccount}</td>
									<td class="bggrey" width="20%" width="20%"> 基本账户开户银行：</td>
									<td>${suppliers.bankName}</td>
								</tr>
								<tr>
									<td class="bggrey" width="20%">企业性质：</td>
									<td width="30%">${suppliers.businessNature}</td>
									<td class="bggrey" width="20%">基本账户开户许可证：</td>
									<td width="30%">
										<u:show showId="bank_show" delete="false" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBank}" />
									</td>
								</tr>
							</tbody>
						</table>
					</ul>

					<h2 class="count_flow"><i>2</i>地址信息</h2>
					<ul class="ul_list">
						<table class="table table-bordered">
							<tbody>
								<tr>
									<td class="bggrey" width="20%">住所邮编：</td>
									<td width="30%">${suppliers.postCode}</td>
									<td class="bggrey" width="20%"> 住所地址：</td>
									<td>${suppliers.address}</td>
								</tr>
								<tr>
									<td class="bggrey" width="20%">住所详细地址：</td>
									<td colspan="3">${suppliers.detailAddress}</td>
							</tbody>
						</table>

						<c:forEach items="${supplierAddress }" var="supplierAddress" varStatus="vs">
							<table class="table table-bordered">
								<tbody>
									<tr>
										<td class="bggrey" width="20%">生产或经营地址邮编：</td>
										<td width="30%">${supplierAddress.code}</td>
										<td class="bggrey" width="20%">生产或经营地址：</td>
										<td>${supplierAddress.parentName }${supplierAddress.subAddressName }</td>
									</tr>
									<tr>
										<td class="bggrey" width="20%">生产或经营详细地址：</td>
										<td colspan="3">${supplierAddress.detailAddress}</td>
									</tr> 
								</tbody>
							</table>
						</c:forEach>
					</ul>
					<div class="tab-pane fade active in">
						<h2 class="count_flow"><i>3</i>资质资信</h2>
						<ul class="ul_list">
							<table class="table table-bordered">
								<tbody>
									<tr>
										<td class="bggrey" width="20%">近三个月完税凭证：</td>
										<td width="30%">
											<u:show showId="taxcert_show" delete="false" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierTaxCert}" />
										</td>
										<td class="bggrey" width="20%">近三年银行基本账户年末对账单：</td>
										<td width="30%">
											<u:show showId="billcert_show" delete="false" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBillCert}" />
										</td>
									</tr>
									<tr>
										<td class="bggrey" width="20%">近三个月缴纳社会保险金凭证：</td>
										<td width="30%">
											<u:show showId="curitycert_show" delete="false" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierSecurityCert}" />
										</td>
										<td class="bggrey" width="20%">近三年内有无重大违法记录：</td>
										<%-- <td width="30%">
											<u:show showId="bearchcert_show"  delete="false" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBearchCert}" />
										</td> --%>
										<td width="30%">
											<c:if test="${suppliers.isIllegal == 1}">有</c:if>
											<c:if test="${suppliers.isIllegal == 2}">无</c:if>
										</td>
									</tr>
									<tr>
										<c:if test="${suppliers.isHavingConCert eq '0'}">
											<td class="bggrey" width="20%">国家或军队保密资格证书：</td>
											<td width="30%">无</td>
										</c:if>
										<c:if test="${suppliers.isHavingConCert eq '1'}">
											<td class="bggrey" width="20%">保密资格证书：</td>
											<td width="30%">
												<u:show showId="bearchcert_show" delete="false" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBearchCert}" />
											</td>
										</c:if>
									</tr>
								</tbody>
							</table>
						</ul>
					</div>

					<div class="tab-pane fade active in">
						<h2 class="count_flow"><i>4</i>法定代表人信息</h2>
						<ul class="ul_list">
							<table class="table table-bordered">
								<tbody>
									<tr>
										<td class="bggrey" width="20%">姓名：</td>
										<td width="30%">${suppliers.legalName}</td>
										<td class="bggrey" width="20%">身份证号：</td>
										<td width="30%">${suppliers.legalIdCard}</td>
									</tr>
									<tr>
										<td class="bggrey" width="20%">固定电话：</td>
										<td width="30%">${suppliers.legalMobile}</td>
										<td class="bggrey" width="20%">手机：</td>
										<td width="30%">${suppliers.legalTelephone }</td>
									</tr>
									<tr>
										<td class="bggrey" width="20%">身份证复印件:</td>
										<td width="30%">
											<u:show showId="bearchcert_up_show" delete="false" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentityUp}" />
										</td>
									</tr>
								</tbody>
							</table>
						</ul>
					</div>
					<div class="tab-pane fade active in">
						<h2 class="count_flow"><i>5</i>注册联系人</h2>
						<ul class="ul_list">
							<table class="table table-bordered">
								<tbody>
									<tr>
										<td class="bggrey" width="20%">姓名：</td>
										<td width="30%">${suppliers.contactName } </td>
										<td class="bggrey" width="20%">传真：</td>
										<td width="30%">${suppliers.contactFax }</td>
									</tr>
									<tr>
										<td class="bggrey" width="20%">固定电话：</td>
										<td width="30%">${suppliers.contactMobile }</td>
										<td class="bggrey" width="20%">手机：</td>
										<td width="30%">${suppliers.mobile } </td>
									</tr>
									<tr>
										<td class="bggrey" width="20%">邮箱：</td>
										<td width="30%">${suppliers.contactEmail }</td>
										<td class="bggrey" width="20%">地址：</td>
										<td width="30%">${suppliers.contactAddress}</td>
									</tr>
									<tr>
										<td class="bggrey" width="20%">详细地址：</td>
										<td colspan="3">${suppliers.contactAddress}</td>
									</tr>
								</tbody>
							</table>
						</ul>
					</div>

					<div class="tab-pane fade active in">
						<h2 class="count_flow"><i>6</i>军队业务联系人</h2>
						<ul class="ul_list">
							<table class="table table-bordered">
								<tbody>
									<tr>
										<td class="bggrey" width="20%">姓名：</td>
										<td width="30%">${suppliers.armyBusinessName } </td>
										<td class="bggrey" width="20%">传真：</td>
										<td width="30%">${suppliers.armyBusinessFax}</td>
									</tr>

									<tr>
										<td class="bggrey" width="20%">固定电话：</td>
										<td width="30%">${suppliers.armyBuinessMobile }</td>
										<td class="bggrey" width="20%">手机：</td>
										<td width="30%">${suppliers.armyBuinessTelephone }</td>
									</tr>
									<tr>
										<td class="bggrey" width="20%">邮编：</td>
										<td width="30%"> ${suppliers.armyBuinessEmail } </td>
										<td class="bggrey" width="20%">地址：</td>
										<td width="30%">${suppliers.armyBuinessAddress}</td>
									</tr>
									<tr>
										<td class="bggrey" width="20%">详细地址：</td>
										<td colspan="3">${suppliers.armyBuinessAddress}</td>
									</tr>
								</tbody>
							</table>
						</ul>
					</div>

					<div class="tab-pane fade active in">
						<h2 class="count_flow"><i>7</i>营业执照</h2>
						<ul class="ul_list">
							<table class="table table-bordered">
								<tbody>
									<tr>
										<td class="bggrey" width="20%">统一社会信用代码：</td>
										<td width="30%">${suppliers.creditCode } </td>
										<td class="bggrey" width="20%">登记机关：</td>
										<td width="30%">${suppliers.registAuthority}</td>
									</tr>

									<tr>
										<td class="bggrey" width="20%">注册资本（人民币：万元）：</td>
										<td width="30%">${suppliers.registFund }</td>
										<td class="bggrey" width="20%">有效期：</td>
										<td width="30%">
											<c:if test="${suppliers.branchName eq '1'}">长期有效</c:if>
											<c:if test="${suppliers.branchName eq '0'}">
												<fmt:formatDate value="${suppliers.businessStartDate}" pattern="yyyy-MM-dd" />
											</c:if>
											<%-- <fmt:formatDate value="${suppliers.businessEndDate}" pattern="yyyy-MM-dd" /> </td> --%>
									</tr>
									
									<tr>
										<td class="bggrey" width="20%">营业执照：</td>
										<td class="hand" width="30%">
											<u:show showId="business_show" delete="false" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" />
										</td>
									</tr>
									<tr>
										<td class="bggrey" width="20%">经营范围：</td>
										<td colspan="3">${suppliers.businessScope } </td>
									</tr>
									<%-- <tr>
									  <td class="bggrey" width="20%">邮编：</td>
										<td width="30%"> ${suppliers.businessPostCode } </td>
										<td class="bggrey" width="20%">营业范围：</td>
										<td width="30%">${suppliers.businessScope}</td>
									</tr> --%>
								</tbody>
							</table>
						</ul>
					</div>

					<div class="tab-pane fade active in">
						<h2 class="count_flow"><i>8</i>境外分支</h2>
						<ul class="ul_list">
							<c:if test="${suppliers.overseasBranch==0}">
								<table class="table table-bordered">
									<tbody>
										<tr>
											<td class="bggrey" width="20%">境外分支机构：</td>
											<td width="30%">无</td>
										</tr>
									</tbody>
								</table>
							</c:if>
							<c:if test="${suppliers.overseasBranch==1}">
								<c:forEach items="${supplierBranchList }" var="supplierBranch" varStatus="vs">
									<table class="table table-bordered">
										<tbody>
											<tr>
												<td class="bggrey" width="20%">机构名称：</td>
												<td width="30%">${supplierBranch.organizationName}</td>
												<td class="bggrey" width="20%">所在国家(地区)：</td>
												<td width="30%">${supplierBranch.countryName}</td>

											</tr>
											<tr>
												<td class="bggrey" width="20%">详细地址：</td>
												<td colspan="3">${supplierBranch.detailAddress}</td>
											</tr>
											<tr>
												<td class="bggrey" width="20%">分支生产经营范围：</td>
												<td colspan="3">${supplierBranch.businessSope } </td>
											</tr>
										</tbody>
									</table>
								</c:forEach>
							</c:if>
						</ul>
					</div>

					<div class="tab-pane fade active in">
						<h2 class="count_flow"><i>9</i>售后服务机构</h2>
						<ul class="ul_list">
							<table class="table table-bordered  table-condensed table-hover">
								<thead>
									<tr>
										<th class="info w50">序号</th>
										<th class="info">分支（或服务）机构名称</th>
										<th class="info">类别</th>
										<th class="info">所在省市</th>
										<th class="info">负责人</th>
										<th class="info">联系电话</th>
									</tr>
								</thead>
								<tbody id="finance_attach_list_tbody_id">
									<c:forEach items="${listSupplierAfterSaleDep}" var="a" varStatus="vs">
										<tr>
											<td class="tc w50">${vs.index + 1}</td>
											<td class="tc">${a.name}</td>
											<td class="tc">
												<c:if test="${a.type == 1}">自营</c:if>
												<c:if test="${a.type == 2}">合作</c:if>
											</td>
											<td class="tc">${a.address}</td>
											<td class="tc">${a.leadName}</td>
											<td class="tc">${a.mobile}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</ul>

					</div>

					<h2 class="count_flow"><i>10</i>参加政府或军队采购经历</h2>
					<ul class="ul_list">
						<li class="col-md-12 col-sm-12 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" style="display:none">参加政府或军队采购经历登记表：</span>
							<div class="col-md-12 col-sm-12 col-xs-12 p0">
								<textarea class="col-md-12 col-xs-12 col-sm-12 h80" id="purchaseExperience" onclick="reason(this)" <c:if test="${fn:contains(field,'purchaseExperience')}"> style="border: 1px solid #FF8C00;"  onMouseOver="isCompare('purchaseExperience');"</c:if>>${suppliers.purchaseExperience }</textarea>
							</div>
						</li>
					</ul>
					<h2 class="count_flow"><i>11</i>公司简介</h2>
					<ul class="ul_list">
						<li class="col-md-12 col-sm-12 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" style="display:none">公司简介：</span>
							<div class="col-md-12 col-sm-12 col-xs-12 p0">
								<textarea class="col-md-12 col-xs-12 col-sm-12 h80" id="description" onclick="reason(this)" <c:if test="${fn:contains(field,'description')}"> style="border: 1px solid #FF8C00;"  onMouseOver="isCompare('description');"</c:if>>${suppliers.description }</textarea>
							</div>
						</li>
					</ul>

				</div>
				<div class="col-md-12 tc">
					<c:if test="${empty person }">
						<button class="btn btn-windows back" onclick="fanhui()">返回</button>
					</c:if>
				</div>
			</div>
	</body>

</html>