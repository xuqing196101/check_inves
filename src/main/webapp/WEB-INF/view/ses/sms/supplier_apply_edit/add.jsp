<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="../../../common.jsp"%>
		<title>变更信息</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/bootstrap.min.css" type="text/css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/style.css" type="text/css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/app.css" type="text/css" />
		<script type="text/javascript">
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
						var rootArea = "${suppliers.address}";
						if(rootArea)
							rootArea = rootArea.split(",")[0];
						if(rootArea) {
							autoSelected("root_area_select_id", rootArea);
							loadChildren();
						}

					},
				});
			}

			function loadChildren() {
				var id = $("#root_area_select_id").find("option:selected").attr("id");
				if(id) {
					$.ajax({
						url: globalPath + "/area/find_area_by_parent_id.do",
						type: "post",
						dataType: "json",
						data: {
							id: id
						},
						success: function(result) {
							var html = "";
							for(var i = 0; i < result.length; i++) {
								html += "<option value='" + result[i].name + "'>" + result[i].name + "</option>";
							}
							$("#children_area_select_id").empty();
							$("#children_area_select_id").append(html);

							// 自动选中
							var childrenArea = "${suppliers.address}";
							if(childrenArea)
								childrenArea = childrenArea.split(",")[1];
							if(childrenArea) {
								autoSelected("children_area_select_id", childrenArea);
							}
						},
					});
				}
			}

			$(function() {
				loadRootArea();
				autoSelected("business_select_id", "${suppliers.businessType}");
				autoSelected("overseas_branch_select_id", "${suppliers.overseasBranch}");
			});

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

			function save() {
				$("#form_id").submit();
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
						<a href="javascript:void(0);">个人信息</a>
					</li>
					<li>
						<a href="javascript:void(0);">企业基本信息变更申请</a>
					</li>
				</ul>
			</div>
		</div>
		<div class="container container_box">
			<div class=" content height-350">
				<form id="form_id" action="${pageContext.request.contextPath}/supplier_edit/save.html" method="post">
					<input name="procurementDepId" type="hidden" value="${suppliers.procurementDepId }" />
					<div class="col-md-12 tab-v2 job-content">
						<input name=id value="${suppliers.id }" type="hidden">
						<h2 class="count_flow"><i>1</i>企业基本信息</h2>
						<ul class="ul_list">
							<li class="col-md-3 col-sm-6 col-xs-12 pl15">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="supplierName2"><i class="red">*</i>供应商名称：</span>
								<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
									<input id="supplierName" name="supplierName" value="${suppliers.supplierName } " type="text">
									<span class="add-on">i</span>
									<div class="cue">${err_msg_supplierName}</div>
								</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12 ">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="website2"><i class="red">*</i>公司网址：</span>
								<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
									<input id="website" name="website" value="${suppliers.website } " type="text">
									<span class="add-on">i</span>
									<div class="cue">${err_msg_website}</div>
								</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12 ">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="foundDate2"><i class="red">*</i>成立日期：</span>
								<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
									<fmt:formatDate value="${suppliers.foundDate}" pattern="yyyy-MM-dd" var="foundDate" />
									<input type="text" name="foundDate" readonly="readonly" onClick="WdatePicker()" name="foundDate" value="${foundDate}" />
									<span class="add-on">i</span>
									<div class="cue">${err_msg_foundDate}</div>
								</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12 ">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="businessType2">营业执照登记类型：</span>
								<div class="select_common">
									<select class="w230" name="businessType" id="business_select_id">
										<option>国有企业</option>
										<option>外资企业</option>
										<option>民营企业</option>
										<option>股份制企业</option>
										<option>私营企业</option>
									</select>
								</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12 ">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="address2"><i class="red">*</i>地址：</span>
								<div class="select_min">
									<select class="w110" id="root_area_select_id" onchange="loadChildren()" name="address"></select>
									<select class="w110" id="children_area_select_id" name="address"></select>
								</div>
								<div class="cue">${err_msg_address}</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12 ">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="bankName2"><i class="red">*</i>开户行名称：</span>
								<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
									<input id="bankName" name="bankName" value="${suppliers.bankName } " type="text">
									<span class="add-on">i</span>
									<div class="cue">${err_msg_bankName}</div>
								</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12 ">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="bankAccount2"><i class="red">*</i>开户行账户：</span>
								<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
									<input id="bankAccount" name="bankAccount" value="${suppliers.bankAccount } " type="text">
									<span class="add-on">i</span>
									<div class="cue">${err_msg_bankAccount}</div>
								</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12 "><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="postCode2"><i class="red">*</i>邮编：</span>
								<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
									<input id="postCode" name="postCode" value="${suppliers.postCode }" type="text">
									<span class="add-on">i</span>
									<div class="cue">${err_msg_postCode}</div>
								</div>
							</li>
						</ul>

						<h2 class="count_flow"><i>2</i>资质资信</h2>
						<ul class="ul_list hand">
							<li class="col-md-6 p0 mb25">
								<span class="col-md-5 padding-left-5"><i class="red">*</i> 近三个月完税凭证</span>
								<u:upload id="taxcert_up" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierTaxCert}" auto="true" />
								<u:show showId="taxcert_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierTaxCert}" />
								<div class="cue">${err_taxCert}</div>
							</li>

							<li id="bill_li_id" class="col-md-6 p0 mb25">
								<span class="col-md-5 padding-left-5"><i class="red">*</i> 近三年银行基本账户年末对账单</span>
								<u:upload id="billcert_up" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBillCert}" auto="true" />
								<u:show showId="billcert_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBillCert}" />
								<div class="cue">${err_bil}</div>
							</li>

							<li id="security_li_id" class="col-md-6 p0 mt10 mb25">
								<span class="col-md-5 padding-left-5"><i class="red">*</i> 近三个月缴纳社会保险金凭证</span>
								<u:upload id="curitycert_up" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierSecurityCert}" auto="true" />
								<u:show showId="curitycert_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierSecurityCert}" />
								<div class="cue">${err_security}</div>
							</li>

							<li id="breach_li_id" class="col-md-6 p0 mt10 mb25">
								<span class="col-md-5 padding-left-5"><i class="red">*</i> 近三年内无重大违法记录声明</span>
								<u:upload id="bearchcert_up" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBearchCert}" auto="true" />
								<u:show showId="bearchcert_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBearchCert}" />
								<div class="cue">${err_bearch}</div>
							</li>
						</ul>

						<h2 class="count_flow"><i>3</i>法人代表人信息</h2>
						<ul class="ul_list">
							<li class="col-md-3 col-sm-6 col-xs-12 pl15">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="legalName2"><i class="red">*</i>姓名：</span>
								<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
									<input id="legalName" name="legalName" value="${suppliers.legalName } " type="text">
									<span class="add-on">i</span>
									<div class="cue">${err_legalName}</div>
								</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12 "><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="legaIdCard2"><i class="red">*</i>身份证号：</span>
								<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
									<input id="legalIdCard" name="legalIdCard" value="${suppliers.legalIdCard }" type="text">
									<span class="add-on">i</span>
									<div class="cue">${err_legalCard}</div>
								</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12 "><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="legalTelephone2"><i class="red">*</i>固定电话：</span>
								<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
									<input id="legalTelephone" name="legalTelephone" value="${suppliers.legalTelephone }" type="text">
									<span class="add-on">i</span>
									<div class="cue">${err_legalPhone }</div>
								</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12 "><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="legalMobile2"><i class="red">*</i>手机：</span>
								<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
									<input id="legalMobile" name="legalMobile" value="${suppliers.legalMobile }" type="text">
									<span class="add-on">i</span>
									<div class="cue">${err_legalMobile}</div>
								</div>
							</li>
						</ul>

						<h2 class="count_flow"><i>4</i>联系人信息</h2>
						<ul class="ul_list">
							<li class="col-md-3 col-sm-6 col-xs-12 pl15">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="contactName2"><i class="red">*</i>姓名：</span>
								<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
									<input id="contactName" name="contactName" value="${suppliers.contactName } " type="text">
									<span class="add-on">i</span>
									<div class="cue">${err_conName}</div>
								</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12 ">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="contactFax2"><i class="red">*</i>传真：</span>
								<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
									<input id="contactFax" name="contactFax" value="${suppliers.contactFax } " type="text">
									<span class="add-on">i</span>
									<div class="cue">${err_fax }</div>
								</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12 ">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="contactTelephone1"><i class="red">*</i>固定电话：</span>
								<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
									<input id="contactTelephone" name="contactTelephone" value="${suppliers.contactTelephone }" type="text">
									<span class="add-on">i</span>
									<div class="cue">${err_catMobile }</div>
								</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12 ">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="contactMobile2"><i class="red">*</i>手机：</span>
								<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
									<input id="contactMobile" name="contactMobile" value="${suppliers.contactMobile }" type="text">
									<span class="add-on">i</span>
									<div class="cue">${err_catTelphone }</div>
								</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12 ">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="contactEmail2"><i class="red">*</i>邮箱：</span>
								<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
									<input id="contactEmail" name="contactEmail" value="${suppliers.contactEmail } " type="text">
									<span class="add-on">i</span>
									<div class="cue">${err_catEmail }</div>
								</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12 ">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="contactAddress2"><i class="red">*</i>地址：</span>
								<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
									<input id="contactAddress" name="contactAddress" value="${suppliers.contactAddress } " type="text">
									<span class="add-on">i</span>
									<div class="cue">${err_conAddress }</div>
								</div>
							</li>
						</ul>

						<h2 class="count_flow"><i>5</i>营业执照</h2>
						<ul class="ul_list">
							<li class="col-md-3 col-sm-6 col-xs-12 pl15">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="creditCode2"><i class="red">*</i>统一社会信用代码：</span>
								<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
									<input id="creditCode" name="creditCode" value="${suppliers.creditCode } " type="text">
									<span class="add-on">i</span>
									<div class="cue">${err_creditCide }</div>
								</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12 ">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="registAuthority2"><i class="red">*</i>登记机关：</span>
								<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
									<input id="registAuthority" name="registAuthority" value="${suppliers.registAuthority } " type="text">
									<span class="add-on">i</span>
									<div class="cue">${err_reAuthoy }</div>
								</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12 ">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="registFund2"><i class="red">*</i>注册资本：</span>
								<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
									<input id="registFund" name="registFund" value="${suppliers.registFund } " type="text">
									<span class="add-on">i</span>
									<div class="cue">${err_fund }</div>
								</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="businessEndDate2"><i class="red">*</i>营业开始时间：</span>
								<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
									<fmt:formatDate value="${suppliers.businessStartDate}" pattern="yyyy-MM-dd" var="businessStartDate" />
									<input type="text" readonly="readonly" onClick="WdatePicker()" name="businessStartDate" value="${businessStartDate}" />
									<span class="add-on">i</span>
									<div class="cue">${err_sDate }</div>
								</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="businessStartDate2"><i class="red">*</i>营业截止时间：</span>
								<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
									<fmt:formatDate value="${suppliers.businessEndDate}" pattern="yyyy-MM-dd" var="businessEndDate" />
									<input type="text" readonly="readonly" onClick="WdatePicker()" name="businessEndDate" value="${businessEndDate}" />
									<span class="add-on">i</span>
									<div class="cue">${err_eDate }</div>
								</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12 ">
								<span class="fl" id="businessAddress2"><i class="red">*</i>生产或经营地址：</span>
								<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
									<input id="businessAddress" name="businessAddress" value="${suppliers.businessAddress } " type="text">
									<span class="add-on">i</span>
									<div class="cue">${err_bAddress }</div>
								</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12 ">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="businessPostCode2"><i class="red">*</i>邮编：</span>
								<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
									<input id="businessPostCode" name="businessPostCode" value="${suppliers.businessPostCode } " type="text">
									<span class="add-on">i</span>
									<div class="cue">${err_bCode }</div>
								</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12 ">
								<span class="col-md-5 padding-left-5"><i class="red">*</i> 营业执照:</span>
								<u:show showId="business_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" />
								<u:upload id="business_up" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" auto="true" />
								<div>${err_business }</div>
							</li>
							<li class="col-md-12 col-sm-12 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="businessScope2">经营范围：</span>
								<div class="col-md-12 col-sm-12 col-xs-12 p0">
									<div class="row">
										<textarea class="col-md-12 col-sm-12 col-xs-12 hand h130" name="businessScope" id="businessScope">${suppliers.businessScope }</textarea>
										<div class="cue"></div>
									</div>
								</div>
							</li>
						</ul>
					</div>

					<h2 class="count_flow"><i>6</i>境外分支</h2>
					<ul class="ul_list">
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">境外分支结构</span>
							<div class="select_common">
								<select class="w230" name="overseasBranch" id="overseas_branch_select_id">
									<option value="1">有</option>
									<option value="0">无</option>
								</select>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 "><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="branchCountry2">境外分支所在国家：</span>
							<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<input id="branchCountry" name="branchCountry" value="${suppliers.branchCountry } " type="text">
								<span class="add-on">i</span>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 "><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="branchAddress2">分支地址：</span>
							<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<input id="branchAddress" name="branchAddress" value="${suppliers.branchAddress } " type="text">
								<span class="add-on">i</span>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 "><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="branchName2">机构名称：</span>
							<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<input id="branchName" name="branchName" value="${suppliers.branchName } " type="text">
								<span class="add-on">i</span>
							</div>
						</li>
						<li class="col-md-12 col-sm-12 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="branchBusinessScope2">分支生产经营范围：</span>
							<div class="col-md-12 col-sm-12 col-xs-12 p0">
								<div class="row">
									<textarea class="col-md-12 col-sm-12 col-xs-12 hand h130" name="branchBusinessScope" id="branchBusinessScope">${suppliers.branchBusinessScope }</textarea>
								</div>
							</div>
						</li>
					</ul>
				</form>
			</div>
			<div class="col-md-12 add_regist tc">
				<button type="button" class="btn btn-windows save" onclick="save()">提交</button>
				<input class="btn btn-windows reset" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
			</div>
		</div>

	</body>

</html>