<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML >
<html>

	<head>
		<%@ include file="../../../common.jsp"%>
		<title>详细信息</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/bootstrap.min.css" type="text/css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/style.css" type="text/css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/app.css" type="text/css" />
		<style type="text/css">

		</style>
		<script type="text/javascript">
			$(function() {
				loadRootArea();
				autoSelected("business_select_id", "${currSupplier.businessType}");
				autoSelected("overseas_branch_select_id", "${currSupplier.overseasBranch}");
				showReason();
			});

			function loadRootArea() {
				$.ajax({
					url: globalPath + "/area/find_root_area.do",
					type: "post",
					dataType: "json",
					success: function(result) {
						var html = "";
						html += "<option value=''>请选择</option>";
						for(var i = 0; i < result.length; i++) {
							html += "<option id='" + result[i].id + "' value='" + result[i].name + "'>" + result[i].name + "</option>";
						}
						$("#root_area_select_id").append(html);

						// 自动选中
						var rootArea = "${currSupplier.address}";
						if(rootArea)
							rootArea = rootArea.split(",")[0];
						if(rootArea) {
							autoSelected("root_area_select_id", rootArea);
							loadChildren();
						}

					},
				});
			}

			function autoSelected(id, v) {
				if(v) {
					$("#" + id).find("option").each(function() {
						var value = $(this).val();
						if(value == v) {
							$(this).prop("selected", true);
						} else {
							$(this).prop("selected", false);
						}
					});
				}
			}

			function showReason() {
				var supplierId = "${currSupplier.id}";
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

			function shenhe(str) {
				if(str == 'tongguo') {
					window.location.href = '${pageContext.request.contextPath}/supplier_edit/auditEnd.html?auditStatus=1&id=' + '${currSupplier.id}';
				} else {
					layer.confirm('您确定要退回吗?', {
						title: '提示',
						offset: ['122px', '360px'],
						shade: 0.01
					}, function(index) {
						//layer.close(index);
						window.location.href = '${pageContext.request.contextPath}/supplier_edit/auditEnd.html?auditStatus=2&id=' + '${currSupplier.id}';
					});

				}
			}

			function reason(ele) {
				var auditField = $(ele).parents("li").find("span").text().replace("：", ""); //审批的字段名字
				var content = $("#" + ele.id + "3").val();
				layer.prompt({
					title: '请填写不通过理由',
					formType: 2,
					offset: '200px'
				}, function(text) {
					$.ajax({
						url: "${pageContext.request.contextPath}/supplier_edit/saveReason.html",
						type: "post",
						data: "&name=" + auditField + "&auditReason=" + text + "&content=" + content + "&supplierId=${currSupplier.id}",
						success: function(result) {
							// $("#"+ele.id).empty();
							layer.msg("审核不通过的理由是：" + text, {
								offset: '200px'
							});
							location.reload();
						},
					});
				});
			}

			function out(text) {
				layer.msg("修改之前是：" + text, {
					offset: '200px'
				});
			}
		</script>
	</head>

	<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:void(0);"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0);">供应商个人信息</a>
					</li>
					<li>
						<a href="javascript:void(0);">供应商审核</a>
					</li>
				</ul>
			</div>
		</div>
		<div class="container container_box">
			<div class=" content height-350">
				<div class="col-md-12 tab-v2 job-content">
					<h2 class="count_flow"><i>1</i>企业基本信息</h2>
					<ul class="ul_list">
						<li class="col-md-3 col-sm-6 col-xs-12 pl15">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="supplierName2">供应商名称：</span>
							<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<c:if test="${not empty result.supplierName }">
									<input id="supplierName3" onclick="out('${result.supplierName}')" value="${currSupplier.supplierName }" type="text">
									<div id="supplierName" onclick="reason(this)" class="abolish">×</div>
								</c:if>
								<c:if test="${ empty result.supplierName }">
									<input readonly value="${currSupplier.supplierName }" type="text">
								</c:if>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 ">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="website2">公司网址：</span>
							<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<c:if test="${not empty result.website }">
									<input id="website3" type="text" readonly onclick="out('${result.website}')" value="${currSupplier.website}">
									<div id="website" onclick="reason(this)" class="abolish">×</div>
								</c:if>
								<c:if test="${empty result.website }">
									<input type="text" readonly value="${currSupplier.website}">
								</c:if>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 ">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="foundDate2">成立日期：</span>
							<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<c:if test="${not empty result.foundDate }">
									<input id="foundDate3" type="text" readonly onClick="WdatePicker()" onclick="out('<fmt:formatDate value=" ${result.foundDate} " pattern="yyyy-MM-dd "  />')" value="<fmt:formatDate value=" ${currSupplier.foundDate} " pattern="yyyy-MM-dd "  />" />
									<div id="foundDate" onclick="reason(this)" class="abolish">×</div>
								</c:if>
								<c:if test="${empty result.foundDate }">
									<input type="text" readonly onClick="WdatePicker()" value="<fmt:formatDate value=" ${currSupplier.foundDate} " pattern="yyyy-MM-dd "  />" />
								</c:if>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 ">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="businessType2">营业执照登记类型：</span>
							<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<c:if test="${not empty result.businessType }">
									<input type="text" id="businessType3" readonly onclick="out('${result.businessType}')" value="${currSupplier.businessType}" />
									<div id=businessType onclick="reason(this)" class="abolish">×</div>
								</c:if>
								<c:if test="${empty result.businessType }">
									<input type="text" readonly value="${currSupplier.businessType}" />
								</c:if>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 ">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="address2">地址：</span>
							<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<c:if test="${not empty result.address }">
									<input id="address3" type="text" readonly onclick="out('${result.address}')" value="${currSupplier.address}" />
									<div id="address" onclick="reason(this)" class="abolish">×</div>
								</c:if>
								<c:if test="${empty result.address }">
									<input type="text" readonly value="${currSupplier.address}" />
								</c:if>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 ">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="bankName2">开户行名称：</span>
							<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<c:if test="${not empty result.bankName }">
									<input id="bankName3" type="text" readonly onclick="out('${result.bankName}')" value="${currSupplier.bankName}" />
									<div id="bankName" onclick="reason(this)" class="abolish">×</div>
								</c:if>
								<c:if test="${empty result.bankName }">
									<input type="text" readonly value="${currSupplier.bankName}" />
								</c:if>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 ">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="bankAccount2">开户行账户：</span>
							<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<c:if test="${not empty result.bankAccount }">
									<input id="bankAccount3" type="text" readonly onclick="out('${result.bankAccount}')" value="${currSupplier.bankAccount}" />
									<div id="bankAccount" onclick="reason(this)" class="abolish">×</div>
								</c:if>
								<c:if test="${empty result.bankAccount }">
									<input type="text" readonly value="${currSupplier.bankAccount}" />
								</c:if>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 "><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="postCode2">邮编：</span>
							<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<c:if test="${not empty result.postCode }">
									<input id="postCode3" type="text" readonly onclick="out('${result.postCode}')" value="${currSupplier.postCode}" />
									<div id="postCode" onclick="reason(this)" class="abolish">×</div>
								</c:if>
								<c:if test="${empty result.postCode }">
									<input type="text" readonly value="${currSupplier.postCode}" />
								</c:if>
							</div>
						</li>
					</ul>

					<h2 class="count_flow"><i>2</i>资质资信</h2>
					<ul class="ul_list hand">
						<li class="col-md-3 col-sm-6 col-xs-12 "><span class="hand" onclick="reason1(this,'taxCert');" onclick="this.style.border='solid 1px #FF0000'" onmouseout="this.style.border='solid 1px #FFFFFF'">近三个月完税凭证：</span>
							<up:show showId="taxcert_show" delete="false" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show" businessId="${currSupplier.recordId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierTaxCert}" />
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 "><span class="hand" onclick="reason1(this,'billCert');" onclick="this.style.border='solid 1px #FF0000'" onmouseout="this.style.border='solid 1px #FFFFFF'">近三年银行基本账户年末对账单：</span>
							<up:show showId="billcert_show" delete="false" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show" businessId="${currSupplier.recordId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBillCert}" />
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 "><span class="hand" onclick="reason1(this,'securityCert');" onclick="this.style.border='solid 1px #FF0000'" onmouseout="this.style.border='solid 1px #FFFFFF'">近三个月缴纳社会保险金凭证：</span>
							<up:show showId="curitycert_show" delete="false" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show" businessId="${currSupplier.recordId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierSecurityCert}" />
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 "><span class="hand" onclick="reason1(this,'breachCert');" onclick="this.style.border='solid 1px #FF0000'" onmouseout="this.style.border='solid 1px #FFFFFF'">近三年内无重大违法记录声明：</span>
							<up:show showId="bearchcert_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show" delete="false" businessId="${currSupplier.recordId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBearchCert}" />
						</li>
					</ul>

					<h2 class="count_flow"><i>3</i>法人代表人信息</h2>
					<ul class="ul_list">
						<li class="col-md-3 col-sm-6 col-xs-12 pl15">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="legalName2">姓名：</span>
							<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<c:if test="${not empty result.legalName }">
									<input id="legalName3" type="text" readonly onclick="out('${result.legalName}')" value="${currSupplier.legalName}" />
									<div id="legalName" onclick="reason(this)" class="abolish">×</div>
								</c:if>
								<c:if test="${empty result.legalName }">
									<input type="text" readonly value="${currSupplier.legalName}" />
								</c:if>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 "><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="legaIdCard2">身份证号：</span>
							<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<c:if test="${not empty result.legalIdCard }">
									<input id="legalIdCard3" type="text" onclick="out('${result.legalIdCard}')" readonly value="${currSupplier.legalIdCard}" />
									<div id="legalIdCard" onclick="reason(this)" class="abolish">×</div>
								</c:if>
								<c:if test="${empty result.legalIdCard }">
									<input type="text" readonly value="${currSupplier.legalIdCard}" />
								</c:if>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 "><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="legalTelephone2">固定电话：</span>
							<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<c:if test="${not empty result.legalTelephone }">
									<input id="legalTelephone3" type="text" onclick="out('${result.legalTelephone}')" readonly value="${currSupplier.legalTelephone}" />
									<div id="legalTelephone" onclick="reason(this)" class="abolish">×</div>
								</c:if>
								<c:if test="${empty result.legalTelephone }">
									<input type="text" readonly value="${currSupplier.legalTelephone}" />
								</c:if>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 "><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="legalMobile2">手机：</span>
							<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<c:if test="${not empty result.legalMobile }">
									<input id="legalMobile3" type="text" onclick="out('${result.legalMobile}')" readonly value="${currSupplier.legalMobile}" />
									<div id="legalMobile" onclick="reason(this)" class="abolish">×</div>
								</c:if>
								<c:if test="${empty result.legalMobile }">
									<input type="text" readonly value="${currSupplier.legalMobile}" />
								</c:if>
							</div>
						</li>
					</ul>

					<h2 class="count_flow"><i>4</i>联系人信息</h2>
					<ul class="ul_list">
						<li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="contactName2">姓名：</span>
							<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<c:if test="${not empty result.contactName }">
									<input id="contactName3" type="text" onclick="out('${result.contactName}')" readonly value="${currSupplier.contactName}" />
									<div id="contactName" onclick="reason(this)" class="abolish">×</div>
								</c:if>
								<c:if test="${empty result.contactName }">
									<input type="text" readonly value="${currSupplier.contactName}" />
								</c:if>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 "><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="contactFax2">传真：</span>
							<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<c:if test="${not empty result.contactFax }">
									<input id="contactFax3" type="text" onclick="out('${result.contactFax}')" readonly value="${currSupplier.contactFax}" />
									<div id="contactFax" onclick="reason(this)" class="abolish">×</div>
								</c:if>
								<c:if test="${empty result.contactFax }">
									<input type="text" readonly value="${currSupplier.contactFax}" />
								</c:if>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 "><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="contactTelephone1">固定电话：</span>
							<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<c:if test="${not empty result.contactTelephone }">
									<input id="contactTelephone3" type="text" onclick="out('${result.contactTelephone}')" readonly value="${currSupplier.contactTelephone}" />
									<div id="contactTelephone" onclick="reason(this)" class="abolish">×</div>
								</c:if>
								<c:if test="${empty result.contactTelephone }">
									<input type="text" readonly value="${currSupplier.contactTelephone}" />
								</c:if>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 "><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="contactMobile2">手机：</span>
							<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<c:if test="${not empty result.contactMobile }">
									<input id="contactMobile3" type="text" onclick="out('${result.contactMobile}')" readonly value="${currSupplier.contactMobile}" />
									<div id="contactMobile" onclick="reason(this)" class="abolish">×</div>
								</c:if>
								<c:if test="${empty result.contactMobile }">
									<input type="text" readonly value="${currSupplier.contactMobile}" />
								</c:if>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 "><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="contactEmail2">邮箱：</span>
							<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<c:if test="${not empty result.contactEmail }">
									<input id="contactEmail3" type="text" readonly onclick="out('${result.contactEmail}')" value="${currSupplier.contactEmail}" />
									<div id="contactEmail3" onclick="reason(this)" class="abolish">×</div>
								</c:if>
								<c:if test="${empty result.contactEmail }">
									<input type="text" readonly value="${currSupplier.contactEmail}" />
								</c:if>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 "><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="contactAddress2">地址：</span>
							<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<c:if test="${not empty result.contactAddress }">
									<input id="contactAddress3" type="text" readonly onclick="out('${result.contactAddress}')" value="${currSupplier.contactAddress}" />
									<div id="contactAddress" onclick="reason(this)" class="abolish">×</div>
								</c:if>
								<c:if test="${empty result.contactAddress }">
									<input type="text" readonly value="${currSupplier.contactAddress}" />
								</c:if>
							</div>
						</li>
					</ul>

					<h2 class="count_flow"><i>5</i>营业执照</h2>
					<ul class="ul_list">
						<li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="creditCode2">统一社会信用代码：</span>
							<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<c:if test="${not empty result.creditCode }">
									<input id="creditCode3" type="text" readonly onclick="out('${result.creditCode}')" value="${currSupplier.creditCode}" />
									<div id="creditCode" onclick="reason(this)" class="abolish">×</div>
								</c:if>
								<c:if test="${empty result.creditCode }">
									<input type="text" readonly value="${currSupplier.creditCode}" />
								</c:if>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 "><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="registAuthority2">登记机关：</span>
							<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<c:if test="${not empty result.registAuthority }">
									<input id="registAuthority3" type="text" readonly onclick="out('${result.registAuthority}')" value="${currSupplier.registAuthority}" />
									<div id="registAuthority" onclick="reason(this)" class="abolish">×</div>
								</c:if>
								<c:if test="${empty result.registAuthority }">
									<input type="text" readonly value="${currSupplier.registAuthority}" />
								</c:if>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 "><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="registFund2">注册资本：</span>
							<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<c:if test="${not empty result.registFund }">
									<input id="registFund3" type="text" readonly onclick="out('${result.registFund}')" value="${currSupplier.registFund}" />
									<div id="registFund" onclick="reason(this)" class="abolish">×</div>
								</c:if>
								<c:if test="${empty result.registFund }">
									<input type="text" readonly value="${currSupplier.registFund}" />
								</c:if>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 "><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="businessEndDate2">营业开始时间：</span>
							<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<c:if test="${not empty result.businessStartDate }">
									<input id="businessStartDate3" type="text" readonly onclick="out('<fmt:formatDate value=" ${currSupplier.businessStartDate} " pattern="yyyy-MM-dd " />')" value="<fmt:formatDate value=" ${currSupplier.businessStartDate} " pattern="yyyy-MM-dd " />" />
									<div id="businessStartDate" onclick="reason(this)" class="abolish">×</div>
								</c:if>
								<c:if test="${empty result.businessStartDate }">
									<input type="text" readonly value="<fmt:formatDate value=" ${currSupplier.businessStartDate} " pattern="yyyy-MM-dd " />"/>
								</c:if>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 "><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="businessStartDate2">营业截止时间：</span>
							<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<c:if test="${not empty result.businessEndDate }">
									<input id="businessEndDate3" type="text" readonly onclick="out('<fmt:formatDate value=" ${currSupplier.businessEndDate} " pattern="yyyy-MM-dd " />')" value="<fmt:formatDate value=" ${currSupplier.businessEndDate} " pattern="yyyy-MM-dd " />" />
									<div id="businessEndDate" onclick="reason(this)" class="abolish">×</div>
								</c:if>
								<c:if test="${empty result.businessEndDate }">
									<input type="text" readonly value="<fmt:formatDate value=" ${currSupplier.businessEndDate} " pattern="yyyy-MM-dd " />"/>
								</c:if>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 "><span class="fl" id="businessAddress2">生产或经营地址：</span>
							<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<c:if test="${not empty result.businessAddress }">
									<input id="businessAddress3" type="text" readonly onclick="out('${result.businessAddress}')" value="${currSupplier.businessAddress}" />
									<div id="businessAddress" onclick="reason(this)" class="abolish">×</div>
								</c:if>
								<c:if test="${empty result.businessAddress }">
									<input type="text" readonly value="${currSupplier.businessAddress}" />
								</c:if>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 "><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="businessPostCode2">邮编：</span>
							<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<c:if test="${not empty result.businessPostCode }">
									<input id="businessPostCode3" type="text" readonly onclick="out('${result.businessPostCode}')" value="${currSupplier.businessPostCode}" />
									<div id="businessPostCode" onclick="reason(this)" class="abolish">×</div>
								</c:if>
								<c:if test="${empty result.businessPostCode }">
									<input type="text" readonly value="${currSupplier.businessPostCode}" />
								</c:if>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 "><span class="" onclick="reason1(this,'businessCert');">营业执照：</span>
							<up:show showId="business_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show" businessId="${currSupplier.recordId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" />
						</li>
						<li class="col-md-12 col-sm-12 col-xs-12 "><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="businessScope2">经营范围：</span>
							<div class="col-md-12 col-sm-12 col-xs-12 p0">
								<c:if test="${not empty result.businessScope }">
									<%-- <input  id="businessScope3" type="text" readonly onclick="out('${result.businessScope}')"  value="${currSupplier.businessScope}" /> --%>
									<textarea id="businessScope3" class="col-md-12 col-sm-12 col-xs-12 hand h130" readonly onclick="out('${result.businessScope}')">${currSupplier.businessScope }</textarea>
									<div id="businessScope" onclick="reason(this)" class="abolish">×</div>
								</c:if>
								<c:if test="${empty result.businessScope }">
									<%-- <input  type="text" readonly value="${currSupplier.businessScope}"/> --%>
									<textarea class="col-md-12 col-sm-12 col-xs-12 hand h130" readonly>${currSupplier.businessScope }</textarea>
								</c:if>
							</div>
						</li>
					</ul>
				</div>

				<h2 class="count_flow"><i>6</i>境外分支</h2>
				<ul class="ul_list">
					<li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="overseasBranch2">境外分支机构：</span>
						<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
							<c:if test="${not empty result.overseasBranch }">
								<c:if test="${currSupplier.overseasBranch==0 }">
									<input id="overseasBranch3" type="text" onclick="out('${result.overseasBranch}')" readonly value="无" />
								</c:if>
								<c:if test="${currSupplier.overseasBranch==1 }">
									<input id="overseasBranch3" type="text" onclick="out('${result.overseasBranch}')" readonly value="有" />
								</c:if>
								<div id="overseasBranch" onclick="reason(this)" class="abolish">×</div>
							</c:if>
							<c:if test="${empty result.overseasBranch }">
								<c:if test="${currSupplier.overseasBranch==0 }">
									<input id="overseasBranch3" type="text" readonly value="无" />
								</c:if>
								<c:if test="${currSupplier.overseasBranch==1 }">
									<input id="overseasBranch3" type="text" readonly value="有" />
								</c:if>
							</c:if>
						</div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12 "><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="branchCountry2">境外分支所在国家：</span>
						<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
							<c:if test="${not empty result.branchCountry }">
								<input id="branchCountry3" type="text" readonly onclick="out('${result.branchCountry}')" value="${currSupplier.branchCountry}" />
								<div id="branchCountry" onclick="reason(this)" class="abolish">×</div>
							</c:if>
							<c:if test="${empty result.branchCountry }">
								<input type="text" readonly value="${currSupplier.branchCountry}" />
							</c:if>
						</div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12 "><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="branchAddress2">分支地址：</span>
						<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
							<c:if test="${not empty result.branchAddress }">
								<input id="branchAddress3" type="text" readonly onclick="out('${result.branchAddress}')" value="${currSupplier.branchAddress}" />
								<div id="branchAddress" onclick="reason(this)" class="abolish">×</div>
							</c:if>
							<c:if test="${empty result.branchAddress }">
								<input type="text" readonly value="${currSupplier.branchAddress}" />
							</c:if>
						</div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12 "><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="branchName2">机构名称：</span>
						<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
							<c:if test="${not empty result.branchName }">
								<input id="branchName3" type="text" readonly onclick="out('${result.branchName}')" value="${currSupplier.branchName}" />
								<div id="branchName" onclick="reason(this)" class="abolish">×</div>
							</c:if>
							<c:if test="${empty result.branchAddress }">
								<input type="text" readonly value="${currSupplier.branchName}" />
							</c:if>
						</div>
					</li>
					<li class="col-md-12 col-sm-12 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="branchBusinessScope2">分支生产经营范围：</span>
						<div class="col-md-12 col-sm-12 col-xs-12 p0">
							<c:if test="${not empty result.branchBusinessScope }">
								<%--  <input  id="branchBusinessScope3" type="text" readonly  onclick="out('${result.branchBusinessScope}')" value="${currSupplier.branchBusinessScope}" /> --%>
								<textarea class="col-md-12 col-sm-12 col-xs-12 hand h130" id="branchBusinessScope3" readonly onclick="out('${result.branchBusinessScope}')">${currSupplier.branchBusinessScope }</textarea>
								<div id="branchBusinessScope" onclick="reason(this)" class="abolish">×</div>
							</c:if>
							<c:if test="${empty result.branchBusinessScope }">
								<%-- <input  type="text" readonly value="${currSupplier.branchBusinessScope}"/> --%>
								<textarea class="col-md-12 col-sm-12 col-xs-12 hand h130" readonly id="branchBusinessScope3">${currSupplier.branchBusinessScope }</textarea>
							</c:if>
						</div>
					</li>
				</ul>
				<div class="col-md-12 tc">
					<input class="btn btn-windows reset" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
					<input class="btn btn-windows git" value="审核通过" type="button" onclick="shenhe('tongguo')">
					<input class="btn btn-windows git" value="审核退回" type="button" onclick="shenhe('tuihui')">
				</div>
			</div>
		</div>
	</body>

</html>