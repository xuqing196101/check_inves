<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/reg_head.jsp"%>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<c:if test="${currSupplier.status == 2}">
			<%@ include file="/WEB-INF/view/ses/sms/supplier_register/supplier_purchase_dept.jsp"%>
		</c:if>
		<title>供应商注册</title>
		<style type="text/css">
			.sm_tip{
				color: gray;
				font-size: 14px;
				font-weight: normal;
				margin-top: 5px;
			}
		</style>
		<%@ include file="/WEB-INF/view/common/validate.jsp"%>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/sms/supplier_register/basic_info.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/regex.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/bms/user/add.js"></script>
	</head>

	<body>
		<!-- 隐藏域 -->
		<input type="hidden" id="supplierId" value="${currSupplier.id}" />
		<input type="hidden" id="supplierSt" value="${currSupplier.status}" />
		<input type="hidden" id="audit" value="${audit}" />
		<input type="hidden" id="error" value="${error}" />
		<div class="wrapper">
			<!-- 项目戳开始 -->
			<jsp:include page="/WEB-INF/view/ses/sms/supplier_register/common_jump.jsp">
				<jsp:param value="${currSupplier.id}" name="supplierId"/>
				<jsp:param value="${currSupplier.status}" name="supplierSt"/>
				<jsp:param value="1" name="currentStep"/>
			</jsp:include>
			<!-- <div class="container clear margin-top-30">
				<h2 class="step_flow">
					<span id="sp1" class="new_step current fl"><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span>
		            <span id="sp2" class="new_step fl"><i class="">2</i><div class="line"></div> <span class="step_desc_01">供应商类型</span> </span>
		            <span id="ty3" class="new_step fl"><i class="">3</i><div class="line"></div> <span class="step_desc_02">产品类别</span> </span>
		            <span id="sp4" class="new_step fl"><i class="">4</i><div class="line"></div> <span class="step_desc_01">资质文件维护</span> </span>
		            <span id="sp5" class="new_step fl"><i class="">5</i><div class="line"></div> <span class="step_desc_02">销售合同</span> </span>
		            <span id="sp6" class="new_step fl"><i class="">6</i><div class="line"></div> <span class="step_desc_01">采购机构</span> </span>
		            <span id="sp7" class="new_step fl"><i class="">7</i><div class="line"></div> <span class="step_desc_02">承诺书和申请表</span> </span>
		            <span id="sp8" class="new_step fl new_step_last"><i class="">8</i> <span class="step_desc_01">提交审核</span> </span>
		            <div class="clear"></div>
				</h2>
			</div> -->
			<!--基本信息-->
			<div class="container container_box">
				<form id="basic_info_form_id" action="${pageContext.request.contextPath}/supplier/perfect_basic.html" method="post">
					<input name="id" value="${currSupplier.id}" type="hidden" />
					<input name="flag" type="hidden" />
					<legend class="col-md-12 col-xs-12 col-sm-12 p0">
						<h2 class="count_flow"> <i>1</i> 基本信息</h2>
						<fieldset class="col-md-12 col-sm-12 col-xs-12 border_font">
							<legend>供应商信息</legend>
							<ul class="list-unstyled f14">
								<li class="col-md-3 col-sm-6 col-xs-12 pl10">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 供应商名称</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
									   <!-- onkeyup="replaceAndSetPos(this,/[^\u4e00-\u9fa5（）()\w]/g,'')"  修改-->
										<%--<input id="supplierName_input_id" type="text" name="supplierName" required="required" onkeyup="value=value.replace(/[^\u4e00-\u9fa5（）()\w]/g,'')" manlength="50" value="${currSupplier.supplierName}" <c:if test="${fn:contains(audit,'supplierName')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'supplierName','basic_page')"</c:if> />--%>
										<input id="supplierName_input_id" type="text" name="supplierName" required="required" maxlength="300" value="${currSupplier.supplierName}"
											<c:if test="${!fn:contains(audit,'supplierName')&&currSupplier.status==2}">readonly="readonly"</c:if>
											<c:if test="${fn:contains(audit,'supplierName')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'supplierName','basic_page')"</c:if>
											class="txtTempSave"/>
										<input type="hidden" id="name_span" name="name_flag"/>
                    <span class="add-on">i</span>
										<span class="input-tip">不能为空</span>
										<div class="cue"> ${err_msg_supplierName } </div>
										<div class="cue">
											<sf:errors path="supplierName" />
										</div>

									</div>
								</li>
								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">网址</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="website" isUrl="isUrl" value="${currSupplier.website}" <c:if test="${!fn:contains(audit,'website')&&currSupplier.status==2}">readonly="readonly"</c:if>    <c:if test="${fn:contains(audit,'website')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'website','basic_page')"</c:if> >
										<span class="add-on cur_point">i</span>
										<span class="input-tip">例如：www.baidu.com</span>
										<div class="cue"> ${err_msg_website } </div>
										<div class="cue">
											<sf:errors path="website" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 成立日期</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<fmt:formatDate value="${currSupplier.foundDate}" pattern="yyyy-MM-dd" var="foundDate" />
										<input type="text" readonly="readonly" name="foundDate" value="${foundDate}" <c:if test="${(fn:contains(audit,'foundDate')&&currSupplier.status==2)||currSupplier.status==-1}">onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'{%y-3}-%M-%d'})" </c:if> <c:if test="${fn:contains(audit,'foundDate')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'foundDate','basic_page')"</c:if> />
										<span class="add-on cur_point">i</span>
										<span class="input-tip">成立时间须大于三年 </span>
										<div class="cue"> ${err_msg_foundDate } </div>
									</div>
								</li>

							<%-- 	<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 营业执照登记类型</span>
									<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
										<select required name="businessType" id="business_select_id" <c:if test="${fn:contains(audit,'businessType')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'businessType','basic_page')"</c:if>>
											<c:forEach items="${company }" var="obj">
												<option value="${obj.id }" <c:if test="${obj.id==currSupplier.businessType }">selected="selected"</c:if> >${obj.name }</option>
											</c:forEach>
										</select>
									</div>
								</li> --%>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 企业性质</span>
									<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
										<select required name="businessNature" id="nature_select_id" <c:if test="${!fn:contains(audit,'businessNature')&&currSupplier.status==2}">onchange="this.selectedIndex=this.defaultIndex;"</c:if>   <c:if test="${fn:contains(audit,'businessNature')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'businessNature','basic_page')"</c:if>>
											<c:forEach items="${nature }" var="obj">
												<option value="${obj.id }" <c:if test="${obj.id eq currSupplier.businessNature}">selected="selected"</c:if>>${obj.name}</option>
											</c:forEach>
										</select>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 基本账户开户银行</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="bankName" maxlength="50" required="required" value="${currSupplier.bankName}" <c:if test="${!fn:contains(audit,'bankName')&&currSupplier.status==2}">readonly="readonly"</c:if>   <c:if test="${fn:contains(audit,'bankName')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'bankName','basic_page')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空</span>
										<div class="cue"> ${err_msg_bankName } </div>
										<div class="cue">
											<sf:errors path="bankName" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-xs-12 col-sm-12  padding-left-5"><i class="red">*</i> 银行账号</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="bankAccount" isBankCard="true" required="required" value="${currSupplier.bankAccount}" <c:if test="${!fn:contains(audit,'bankAccount')&&currSupplier.status==2}">readonly="readonly"</c:if>   <c:if test="${fn:contains(audit,'bankAccount')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'bankAccount','basic_page')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空</span>
										<div class="cue"> ${err_msg_bankAccount } </div>
										<div class="cue">
											<sf:errors path="bankAccount" />
										</div>
									</div>
								</li>
 
								<li id="breach_li_id" class="col-md-3 col-sm-6 col-xs-12 mb25">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"  <c:if test="${fn:contains(audit,'supplierBank')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'supplierBank','basic_page')"</c:if>><i class="red">*</i> 基本账户开户许可证</span>
									<div class="col-md-12 col-sm-12 col-xs-12 p0">
										<c:if test="${(fn:contains(audit,'supplierBank')&&currSupplier.status==2) || currSupplier.status==-1 || currSupplier.status==1}">	 <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="bank_up" maxcount="1" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBank}" auto="true" /> </c:if>
									  <c:if test="${!fn:contains(audit,'supplierBank')&&currSupplier.status==2}">	 <u:show showId="bank_show" delete="false"   groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBank}" /></c:if>
									  <c:if test="${currSupplier.status==-1 || currSupplier.status==1 || fn:contains(audit,'supplierBank')}">	 <u:show showId="bank_show"  groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBank}" /></c:if>
										<div class="cue"> ${err_supplierBank } </div>
									</div>
								</li>
							</ul>
						</fieldset>

						<fieldset class="col-md-12 border_font mt20">
							<legend>营业执照</legend>
							<ul class="list-unstyled f14">
								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 营业执照登记类型</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p10">
										<c:if test="${fn:length(currSupplier.businessType)!=32}">
											<input type="text" name="businessType"  required="required" value="${currSupplier.businessType}" <c:if test="${!fn:contains(audit,'businessType')&&currSupplier.status==2}">readonly="readonly"</c:if>    <c:if test="${fn:contains(audit,'businessType')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'businessType','basic_page')"</c:if>/>
											<span class="add-on cur_point">i</span>
											<span class="input-tip">不能为空</span>
										</c:if>
										<c:if test="${fn:length(currSupplier.businessType)==32}">
											<c:forEach items="${company }" var="obj">
										 		<c:if test="${obj.id==currSupplier.businessType }">
													<input type="text" name="businessType" required="required" value=" ${obj.name }" <c:if test="${!fn:contains(audit,'businessType')&&currSupplier.status==2}">readonly="readonly"</c:if> <c:if test="${fn:contains(audit,'businessType')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'businessType','basic_page')"</c:if>/>
													<span class="add-on cur_point">i</span>
													<span class="input-tip">不能为空</span>
											 	</c:if> 
											</c:forEach>
										</c:if>
									</div>
								</li>
								<li class="col-md-3 col-sm-6 col-xs-12 pl10">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>统一社会信用代码</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="creditCode" required maxlength="18" id="creditCode" onkeyup="value=value.replace(/[^\d|a-zA-Z]/g,'')" onblur="checkCreditCode(this.value);" value="${currSupplier.creditCode}" 
											<c:if test="${!fn:contains(audit,'creditCode')&&currSupplier.status==2}">readonly="readonly"</c:if>
											<c:if test="${fn:contains(audit,'creditCode')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'creditCode','basic_page')"</c:if>
											class="txtTempSave"/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">18位数字或数字字母</span>
										<div class="cue"> ${err_creditCide} </div>
										<div class="cue">
											<sf:errors path="creditCode" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 登记机关</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="registAuthority" required maxlength="20" value="${currSupplier.registAuthority}"  <c:if test="${!fn:contains(audit,'registAuthority')&&currSupplier.status==2}">readonly="readonly"</c:if>  <c:if test="${fn:contains(audit,'registAuthority')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'registAuthority','basic_page')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，长度不可大于20位</span>
										<div class="cue"> ${err_reAuthoy } </div>
										<div class="cue">
											<sf:errors path="registAuthority" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 注册资本（人民币：万元）</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="registFund" onblur="validateMoney(this.value, 4);" required maxlength="19" value="${currSupplier.registFund}" <c:if test="${!fn:contains(audit,'registFund')&&currSupplier.status==2}">readonly="readonly"</c:if>   <c:if test="${fn:contains(audit,'registFund')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'registFund','basic_page')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，值不可小于零</span>
										<div class="cue" id="err_fund"> ${err_fund } </div>
										<div class="cue">
											<sf:errors path="registFund" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 营业期限   <input type="checkbox" name="branchName" onclick="return checkYyqx(this);" 
										<c:if test="${currSupplier.branchName=='1'}"> checked='true'</c:if>
										<%-- <c:if test="${currSupplier.status==2 && !fn:contains(audit,'businessStartDate')}"> disabled='disabled'</c:if> --%>   
										value="${currSupplier.branchName }"> 长期</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<%-- <fmt:formatDate value="${currSupplier.businessStartDate}" pattern="yyyy-MM-dd" var="businessStartDate" /> --%>
										<input id="expireDate" type="text" readonly="readonly" 
											<%-- <c:if test="${(fn:contains(audit,'businessStartDate')&&currSupplier.status==2) || currSupplier.status==-1 || currSupplier.branchName!='1'}">onClick="WdatePicker()"</c:if>  --%>
											onclick="controlExpireDate();"
											name="businessStartDate" value="<fmt:formatDate value="${currSupplier.businessStartDate}" pattern="yyyy-MM-dd"/>" 
											<c:if test="${fn:contains(audit,'businessStartDate')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'businessStartDate','basic_page')"</c:if>
											<%-- <c:if test="${currSupplier.branchName=='1'}">disabled="disabled"</c:if> --%> 
										/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">如果勾选长期,可不填写有效期</span>
										<div class="cue"> ${err_sDate } </div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12 mb25"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5" <c:if test="${fn:contains(audit,'businessCert')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'businessCert','basic_page')"</c:if>><i class="red">*</i> 营业执照</span>
									<div class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0">
										<c:if test="${(fn:contains(audit,'businessCert')&&currSupplier.status==2 ) || currSupplier.status==-1 || currSupplier.status==1}"> <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="business_up" maxcount="1" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" auto="true" /></c:if>
										<c:if test="${!fn:contains(audit,'businessCert')&&currSupplier.status==2 }">  <u:show showId="business_show" delete="false"  groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" /></c:if>
									    <c:if test="${currSupplier.status==-1 || currSupplier.status==1 || fn:contains(audit,'businessCert')}">  <u:show showId="business_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" /></c:if>
										<div class="cue"> ${err_business} </div>
									</div>
								</li>

								<li class="col-md-12 col-xs-12 col-sm-12 mb25">
									<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> <i class="red">* </i>经营范围（按照营业执照填写）</span>
									<div class="col-md-12 col-xs-12 col-sm-12 p0">
										<textarea class="col-md-12 col-xs-12 col-sm-12 h80" maxlength="1000"  
											onkeyup="checkCharLimit('businessScope','limit_char_businessScope',1000);if(value.length==1000){layer.msg('字数过多，不可超过1000字！')}" 
											required="required" name="businessScope" id="businessScope"
											<c:if test="${!fn:contains(audit,'businessScope')&&currSupplier.status==2}">readonly="readonly"</c:if>
											<c:if test="${fn:contains(audit,'businessScope')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'businessScope','basic_page')"</c:if>>${currSupplier.businessScope}</textarea>
										<span class="sm_tip fr">还可输入 <span id="limit_char_businessScope">1000</span> 个字</span>
										<div class="cue">
											<sf:errors path="businessScope" />
										</div>
									</div>
								</li>
							</ul>
						</fieldset>
						
						<fieldset class="col-md-12 col-sm-12 col-xs-12 border_font mt20">
							<legend>法定代表人信息</legend>
							<ul class="list-unstyled f14">
								<li class="col-md-3 col-sm-6 col-xs-12 pl10">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 姓名</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="legalName" required maxlength="10" value="${currSupplier.legalName}" <c:if test="${!fn:contains(audit,'legalName')&&currSupplier.status==2}">readonly="readonly"</c:if>  <c:if test="${fn:contains(audit,'legalName')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'legalName','basic_page')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空</span>
										<div class="cue"> ${err_legalName } </div>
										<div class="cue">
											<sf:errors path="legalName" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 身份证号</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="legalIdCard" required value="${currSupplier.legalIdCard}" onblur="return checkIdCard(this.value);"
											<c:if test="${!fn:contains(audit,'legalIdCard')&&currSupplier.status==2}">readonly="readonly"</c:if>
											<c:if test="${fn:contains(audit,'legalIdCard')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'legalIdCard','basic_page')"</c:if>
											class="txtTempSave"/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，长度为18位</span>
										<div class="cue"> ${err_legalCard } </div>
										<div class="cue">
											<sf:errors path="legalIdCard" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5" <c:if test="${fn:contains(audit,'supplierIdentityUp')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'supplierIdentityUp','basic_page')"</c:if>><i class="red">*</i> 身份证复印件（正反面在一张上）</span>
									<div class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0">
										<c:if test="${(fn:contains(audit,'supplierIdentityUp')&&currSupplier.status==2) || currSupplier.status==-1 || currSupplier.status==1}"> <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="bearchcert_up_up" maxcount="1" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentityUp}" auto="true" /></c:if>
										<c:if test="${!fn:contains(audit,'supplierIdentityUp')&&currSupplier.status==2}">  <u:show showId="bearchcert_up_show" delete="false"  groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentityUp}" /></c:if>
										<c:if test="${currSupplier.status==-1 || currSupplier.status==1 || fn:contains(audit,'supplierIdentityUp')}">  <u:show showId="bearchcert_up_show"   groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentityUp}" /></c:if>
										<div class="cue"> ${err_identityUp } </div>
									</div>
								</li>


								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 固定电话</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="legalMobile" required  onkeyup="value=value.replace(/[^\d-]/g,'')" value="${currSupplier.legalMobile}"  <c:if test="${!fn:contains(audit,'legalMobile')&&currSupplier.status==2}">readonly="readonly"</c:if>  <c:if test="${fn:contains(audit,'legalMobile')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'legalMobile','basic_page')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，如: 0101234567</span>
										<div class="cue"> ${err_legalMobile } </div>
										<div class="cue">
											<sf:errors path="legalMobile" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 手机</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="legalTelephone" onkeyup="value=value.replace(/[^\d]/g,'')" required isPhone="true" value="${currSupplier.legalTelephone}"  <c:if test="${!fn:contains(audit,'legalTelephone')&&currSupplier.status==2}">readonly="readonly"</c:if> <c:if test="${fn:contains(audit,'legalTelephone')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'legalTelephone','basic_page')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，11位手机号码</span>
										<div class="cue"> ${err_legalPhone } </div>
										<div class="cue">
											<sf:errors path="legalTelephone" />
										</div>
									</div>
								</li>

							</ul>
						</fieldset>
						
						
						<fieldset class="col-md-12 col-xs-12 col-sm-12 border_font mt20">
							<legend>地址信息</legend>
							<ul class="list-unstyled f14">
								<li class="col-md-3 col-sm-6 col-xs-12 pl10">
									<span class="col-md-12 col-xs-12 col-sm-12  padding-left-5 "><i class="red">*</i> 住所邮编</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="postCode" required isZipCode="true" value="${currSupplier.postCode}"   <c:if test="${!fn:contains(audit,'postCode')&&currSupplier.status==2}">readonly="readonly"</c:if>   <c:if test="${fn:contains(audit,'postCode')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'postCode','basic_page')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，长度为6位</span>
										<div class="cue"> ${err_msg_postCode } </div>
										<div class="cue">
											<sf:errors path="postCode" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 住所地址（营业执照上的登记地址）</span>
									<div class="col-md-12 col-xs-12 col-sm-12 select_common p0">
										<div class="col-md-5 col-xs-5 col-sm-5 mr5 p0">
											<select id="root_area_select_id_addr"  
												<c:if test="${(fn:contains(audit,'address')&&currSupplier.status==2)||currSupplier.status==-1}">onchange="loadAreaSelect(this,'#children_area_select_id_addr')"</c:if>
												<c:if test="${!fn:contains(audit,'address')&&currSupplier.status==2}">onchange="this.selectedIndex=this.defaultIndex;"</c:if>
												<c:if test="${fn:contains(audit,'address')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'address','basic_page')"</c:if>>
												<option value="" >请选择</option>
												<c:forEach items="${province }" var="prov">
													<c:if test="${prov.id==area.parentId }">
														<option value="${prov.id }" selected="selected">${prov.name }</option>
													</c:if>
													<c:if test="${prov.id!=area.parentId }">
														<option value="${prov.id }">${prov.name }</option>
													</c:if>
												</c:forEach>
											</select>
										</div>
										<div class="col-md-5 col-xs-5 col-sm-5 mr5 p0">
											<select id="children_area_select_id_addr" name="address"  <c:if test="${!fn:contains(audit,'address')&&currSupplier.status==2}">onchange="this.selectedIndex=this.defaultIndex;"</c:if>  <c:if test="${fn:contains(audit,'address')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'address','basic_page')"</c:if>>
												<option value="" >请选择</option>
												<c:forEach items="${city }" var="city">
													<c:if test="${city.id==currSupplier.address }">
														<option value="${city.id }" selected="selected">${city.name }</option>
													</c:if>
													<c:if test="${city.id!=currSupplier.address }">
														<option value="${city.id }">${city.name }</option>
													</c:if>
												</c:forEach>
											</select>
										</div>
										<div class="cue"> ${err_msg_address } </div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 住所详细地址</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="detailAddress" placeholder="街道名称，门牌号。" value="${currSupplier.detailAddress}" required maxlength="50" <c:if test="${!fn:contains(audit,'detailAddress')&&currSupplier.status==2}">readonly="readonly"</c:if> <c:if test="${fn:contains(audit,'detailAddress')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'detailAddress','basic_page')"</c:if>>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">请如实填写单位地址！</span>
										<div class="cue">${err_detailAddress } </div>
										<div class="cue">
											<sf:errors path="detailAddress" />
										</div>
									</div>
								</li>
								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5 white">操作</span>
									<div class="col-md-12 col-xs-12 col-sm-12 p0 mb25 h30">
									</div>
								</li>
								<div id="address_list_body">
									<div class="col-md-12 col-sm-12 col-xs-12 p0 mb5">
								 		<c:choose>
											<c:when test="${currSupplier.status==2 }">
												<button class="btn btn-Invalid"  type="button" disabled="disabled">新增</button>
											</c:when>
											<c:otherwise>
												<button class="btn btn-windows add" type="button" onclick="addAddress()">新增</button>
											</c:otherwise>
										</c:choose>
										<button class="btn btn-windows delete" type="button" onclick="delAddress()">删除</button>
										<span class="red">${err_address_token}</span>
									</div>
									<div class="col-md-12 col-sm-12 col-xs-12 p0 over_auto">
									    <table id="address_table_id" class="table table-bordered table-condensed mt5 table_wrap table_input left_table m_table_fixed_border">
									        <thead>
									            <tr>
									                <th class="info" style="width:3%;"><input type="checkbox" onchange="checkAll(this, 'address_list_tbody_id')" /></th>
									                <th class="info" style="width:13%;"><font color="red">*</font> 生产或经营地址邮编</th>
									                <th class="info" style="width:23%;"><font color="red">*</font> 生产或经营地址（填写所有地址）</th>
									                <th class="info"><font color="red">*</font> 生产或经营详细地址</th>
									                <th class="info" style="width:22%;"><font color="red">*</font> 房产证明或租赁协议</th>
									            </tr>
									        </thead>
									        <tbody id="address_list_tbody_id">
									        <c:set var="addressNumber" value="0" />
									        <c:forEach items="${currSupplier.addressList}" var="addr" varStatus="vs">
									            <tr >
									                <td class="tc"><input type="checkbox" value="${addr.id}" /></td>
									                <td class="tc" <c:if test="${fn:contains(audit,addr.id)}">style="border: 1px solid red;" onmouseover="errorMsg(this,'${addr.id }','basic_page')"</c:if>>
									                    <input type="text" onkeyup="value=value.replace(/[^\d]/g,'')" onblur="validatePostCode(this.value)"
									                    <c:if test="${!fn:contains(audit,addr.id)&&currSupplier.status==2}">readonly="readonly"</c:if>  required class="w200 border0 address_zip_code" name="addressList[${vs.index }].code" value="${addr.code}" />
									                    <input type='hidden' name='addressList[${vs.index }].id' value='${addr.id}'>
									                </td>
									                <td class="tc" <c:if test="${fn:contains(audit,addr.id)}">style="border: 1px solid red;" onmouseover="errorMsg(this,'${addr.id }','basic_page')"</c:if>>
									                    <div class="col-md-5 col-xs-5 col-sm-5 mr5 p0 ml20">
									                        <select id="root_area_select_id_${vs.index }" class="w100p" onchange="loadAreaSelect(this,'#children_area_select_id_${vs.index }')" name="addressList[${vs.index }].provinceId">
									                            <option value="">请选择</option>
									                            <c:forEach items="${province }" var="prov">
									                                <c:if test="${prov.id==addr.provinceId }">
									                                    <option value="${prov.id }" selected="selected">${prov.name }</option>
									                                </c:if>
									                                <c:if test="${prov.id!=addr.provinceId }">
									                                    <option value="${prov.id }">${prov.name }</option>
									                                </c:if>
									                            </c:forEach>
									                        </select>
									                    </div>
									                    <div class="col-md-5 col-xs-5 col-sm-5 mr5 p0">
									                        <select id="children_area_select_id_${vs.index }" class="w100p" name="addressList[${vs.index }].address">
									                            <option value="">请选择</option>
									                            <c:forEach items="${addr.areaList }" var="city">
									                                <c:if test="${city.id==addr.address }">
									                                    <option value="${city.id }" selected="selected">${city.name }</option>
									                                </c:if>
									                                <c:if test="${city.id!=addr.address }">
									                                    <option value="${city.id }">${city.name }</option>
									                                </c:if>
									                            </c:forEach>
									                        </select>
									                    </div>
									                </td>
									                <td class="tc" <c:if test="${fn:contains(audit,addr.id)}">style="border: 1px solid red;" onmouseover="errorMsg(this,'${addr.id }','basic_page')"</c:if>>
									                    <input type="text" class="w200 border0" <c:if test="${!fn:contains(audit,addr.id)&&currSupplier.status==2}">readonly="readonly"</c:if>  placeholder="街道名称，门牌号。" name="addressList[${vs.index }].detailAddress" required maxlength="50" value="${addr.detailAddress }" >
									                </td>
									                <td class="tc" <c:if test="${fn:contains(audit,addr.id)}">style="border: 1px solid red;" onmouseover="errorMsg(this,'${addr.id }','basic_page')"</c:if>>
									                    <div class="w200 fl">
									                        <%-- <c:choose>
									                        	<c:when test="${!fn:contains(audit,addr.id)&&currSupplier.status==2}">
									                        		<u:show showId="house_show_${addressNumber}" delete="false" businessId="${addr.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierHousePoperty}" />
									                        	</c:when>
									                        	<c:otherwise>
									                        		<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="house_up_${addressNumber}" multiple="true" businessId="${addr.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierHousePoperty}" auto="true" />
									                        		<u:show showId="house_show_${addressNumber}" businessId="${addr.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierHousePoperty}" />
									                        	</c:otherwise>
									                        </c:choose> --%>
									                        <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="house_up_${addressNumber}" multiple="true" businessId="${addr.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierHousePoperty}" auto="true" />
									                     		<u:show showId="house_show_${addressNumber}" businessId="${addr.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierHousePoperty}" />
									                        <c:if test="${vs.index == err_house_token}">
									                            <div class="cue">  </div>
									                        </c:if>
									                    </div>
									                </td>
									            </tr>
									            <c:set var="addressNumber" value="${addressNumber + 1}" />
									        </c:forEach>
									        <input type="hidden" id="addressNumber" value="${addressNumber}"/>
									        </tbody>
									    </table>
									</div>
								</div>
							</ul>
						</fieldset>

						<fieldset class="col-md-12 col-xs-12 col-sm-12 border_font mt20">
							<legend>资质资信</legend>
							<ul class="list-unstyled f14">
								<li class="col-md-6 col-sm-6 col-xs-12 mb25 pl10">
									<span class="col-md-6 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 近三个月完税凭证</span>
									<div class="col-md-6 col-sm-12 col-xs-12 p0"
										<c:if test="${fn:contains(audit,'taxCert')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'taxCert','basic_page')"</c:if>>
										<c:if test="${(fn:contains(audit,'taxCert')&&currSupplier.status==2 )||(currSupplier.status==-1 || currSupplier.status==1)}"> <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="taxcert_up" multiple="true" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierTaxCert}" auto="true" /></c:if>
										<c:if test="${!fn:contains(audit,'taxCert')&&currSupplier.status==2}">  <u:show showId="taxcert_show"  delete="false" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierTaxCert}" /></c:if>
										<c:if test="${currSupplier.status==-1 || currSupplier.status==1 || fn:contains(audit,'taxCert')}">  <u:show showId="taxcert_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierTaxCert}" /></c:if>
										<div class="cue"> ${err_taxCert } </div>
									</div>
								</li>

								<li id="bill_li_id" class="col-md-6 col-sm-6 col-xs-12 mb25">
									<span class="col-md-6 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 近三年银行基本账户年末对账单</span>
									<div class="col-md-6 col-sm-12 col-xs-12 p0"
										<c:if test="${fn:contains(audit,'billCert')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'billCert','basic_page')"</c:if>>
										<c:if test="${(fn:contains(audit,'billCert')&&currSupplier.status==2 )|| currSupplier.status==-1 || currSupplier.status==1}">	<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="billcert_up" multiple="true" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBillCert}" auto="true" /></c:if>
										<c:if test="${!fn:contains(audit,'billCert')&&currSupplier.status==2 }">	<u:show showId="billcert_show" delete="false"    groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBillCert}" /></c:if>
										<c:if test="${currSupplier.status==-1 || currSupplier.status==1 || fn:contains(audit,'billCert')}">	<u:show showId="billcert_show"     groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBillCert}" /></c:if>
										<div class="cue"> ${err_bil } </div>
									</div>
								</li>

								<li id="security_li_id" class="col-md-6 col-sm-6 col-xs-12 mb25 h30">
									<span class="col-md-6 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 近三个月缴纳社会保险金凭证</span>
									<div class="col-md-6 col-sm-12 col-xs-12 p0"
										<c:if test="${fn:contains(audit,'securityCert')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'securityCert','basic_page')"</c:if>>
										<c:if test="${(fn:contains(audit,'securityCert')&&currSupplier.status==2) || currSupplier.status==-1 || currSupplier.status==1}">	<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="curitycert_up" multiple="true" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierSecurityCert}" auto="true" /></c:if>
								    <c:if test="${!fn:contains(audit,'securityCert')&&currSupplier.status==2}">	 	<u:show showId="curitycert_show" delete="false"    groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierSecurityCert}" /></c:if>
										<c:if test="${currSupplier.status==-1 || currSupplier.status==1 || fn:contains(audit,'securityCert')}">	 	<u:show showId="curitycert_show"    groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierSecurityCert}" /></c:if>
										<div class="cue"> ${err_security } </div>
									</div>
								</li>

								<li class="col-md-6 col-sm-6 col-xs-12 mb25 mb25">
									<span class="col-md-6 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 近三年内有无重大违法记录</span>
									<div class="col-md-6 col-sm-12 col-xs-12 p0 h30">
										<select name="isIllegal" id="isIllegal" class="fl mr10 w120"
											<c:if test="${fn:contains(audit,'isIllegal')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'isIllegal','basic_page')"</c:if>>
											<option value='' disabled selected style="display: none;">请选择</option>
											<option value="0" <c:if test="${currSupplier.isIllegal eq '0'}">selected</c:if>>无</option>
											<option value="1" <c:if test="${currSupplier.isIllegal eq '1'}">selected</c:if>>有</option>
										</select>
										<div class="cue"> ${err_isIllegal } </div>
									</div>
								</li>
								<li class="col-md-6 col-sm-6 col-xs-12 mb25">
									<span class="col-md-6 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 国家或军队保密资格证书</span>
									<div class="col-md-6 col-sm-12 col-xs-12 p0 h30">
										<select name="isHavingConCert" id="isHavingConCert" onchange="onchangeBearch(this)" class="fl mr10 w120"
											<c:if test="${fn:contains(audit,'isHavingConCert')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'isHavingConCert','basic_page')"</c:if>>
											<option value='' disabled selected style="display: none;">请选择</option>
											<option value="0" <c:if test="${currSupplier.isHavingConCert eq '0'}">selected</c:if>>无</option>
											<option value="1" <c:if test="${currSupplier.isHavingConCert eq '1'}">selected</c:if>>有</option>
										</select>
										<div class="cue"> ${err_isHavingConCert } </div>
									</div>
								</li>
								<li class="col-md-6 col-sm-6 col-xs-12 mb25" id="bearchCertDiv" <c:if test="${currSupplier.isHavingConCert ne '1'}">style="display:none;"</c:if>>
									<span class="col-md-6 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 保密资格证书</span>
									<div class="col-md-6 col-sm-12 col-xs-12 p0"
										<c:if test="${fn:contains(audit,'supplierBearchCert')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'supplierBearchCert','basic_page')"</c:if>>
										<c:if test="${(fn:contains(audit,'supplierBearchCert')&&currSupplier.status==2 ) || currSupplier.status==-1 || currSupplier.status==1 || (fn:contains(audit,'isHavingConCert')&&currSupplier.status==2&&currSupplier.isHavingConCert==0)}"><u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="bearchcert_up" multiple="true" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBearchCert}" auto="true" /></c:if>
										<c:if test="${!fn:contains(audit,'supplierBearchCert')&&currSupplier.status==2}"> 	<u:show showId="bearchcert_show"  delete="false" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBearchCert}" /></c:if>
										<c:if test="${currSupplier.status==-1 || currSupplier.status==1 ||fn:contains(audit,'supplierBearchCert')}"> <u:show showId="bearchcert_show"   businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBearchCert}" /></c:if>
										<div class="cue"> ${err_bearch } </div>
									</div>
								</li>
							</ul>
						</fieldset>
						

						<fieldset class="col-md-12 col-sm-12 col-xs-12 border_font mt20">
							<legend>注册联系人</legend>
							<ul class="list-unstyled f14">
								<li class="col-md-3 col-sm-6 col-xs-12 pl10">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><i class="red">*</i> 姓名</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="contactName" required maxlength="10" value="${currSupplier.contactName}" <c:if test="${!fn:contains(audit,'contactName')&&currSupplier.status==2}">readonly="readonly"</c:if>  <c:if test="${fn:contains(audit,'contactName')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'contactName','basic_page')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空</span>
										<div class="cue"> ${err_conName } </div>
										<div class="cue">
											<sf:errors path="contactName" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 传真</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="contactFax" required isFax="true" onkeyup="value=value.replace(/[^\d-]/g,'')" value="${currSupplier.contactFax}"  <c:if test="${!fn:contains(audit,'contactFax')&&currSupplier.status==2}">readonly="readonly"</c:if>  <c:if test="${fn:contains(audit,'contactFax')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'contactFax','basic_page')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，如: 0101234567</span>
										<div class="cue"> ${err_fax } </div>
										<div class="cue">
											<sf:errors path="contactFax" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 固定电话</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="contactMobile" required  onkeyup="value=value.replace(/[^\d-]/g,'')" value="${currSupplier.contactMobile}" <c:if test="${!fn:contains(audit,'contactMobile')&&currSupplier.status==2}">readonly="readonly"</c:if>  <c:if test="${fn:contains(audit,'contactMobile')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'contactMobile','basic_page')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，如: 0101234567</span>
										<div class="cue"> ${err_catMobile } </div>
										<div class="cue">
											<sf:errors path="contactMobile" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 手机</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="mobile" required isPhone="true" onkeyup="value=value.replace(/[^\d]/g,'')" value="${currSupplier.mobile}"  <c:if test="${!fn:contains(audit,'mobile')&&currSupplier.status==2}">readonly="readonly"</c:if>   <c:if test="${fn:contains(audit,'mobile')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'mobile','basic_page')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空,11位手机号码</span>
										<div class="cue"> ${err_catTelphone } </div>
										<div class="cue">
											<sf:errors path="mobile" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 邮箱</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="email" name="contactEmail" required email value="${currSupplier.contactEmail}" <c:if test="${!fn:contains(audit,'contactEmail')&&currSupplier.status==2}">readonly="readonly"</c:if>  <c:if test="${fn:contains(audit,'contactEmail')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'contactEmail','basic_page')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，如：123456@qq.com</span>
										<div class="cue"> ${err_catEmail } </div>
										<div class="cue">
											<sf:errors path="contactEmail" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 地址</span>
									<div class="col-md-12 col-xs-12 col-sm-12 select_common p0">
										<div class="col-md-5 col-xs-5 col-sm-5 mr5 p0">
											<select id="root_area_select_id_concat" name="concatProvince" onchange="loadAreaSelect(this,'#children_area_select_id_concat')" <c:if test="${fn:contains(audit,'concatCity')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'concatCity','basic_page')"</c:if>>
												<option value="">请选择</option>
												<c:forEach items="${province }" var="prov">
													<c:if test="${prov.id==currSupplier.concatProvince }">
														<option value="${prov.id }" selected="selected">${prov.name }</option>
													</c:if>
													<c:if test="${prov.id!=currSupplier.concatProvince }">
														<option value="${prov.id }">${prov.name }</option>
													</c:if>
												</c:forEach>

											</select>
										</div>
										<div class="col-md-5 col-xs-5 col-sm-5 mr5 p0">
											<select id="children_area_select_id_concat" name="concatCity" <c:if test="${fn:contains(audit,'concatCity')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'concatCity','basic_page')"</c:if>>
												<option value="">请选择</option>
												<c:forEach items="${currSupplier.concatCityList }" var="city">
													<c:if test="${city.id==currSupplier.concatCity}">
														<option value="${city.id }" selected="selected">${city.name }</option>
													</c:if>
													<c:if test="${city.id!=currSupplier.concatCity}">
														<option value="${city.id }">${city.name }</option>
													</c:if>
												</c:forEach>
											</select>
										</div>
										<div class="cue">${err_city} </div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 详细地址</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="contactAddress" required maxlength="50" value="${currSupplier.contactAddress}"  <c:if test="${!fn:contains(audit,'contactAddress')&&currSupplier.status==2}">readonly="readonly"</c:if>   <c:if test="${fn:contains(audit,'contactAddress')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'contactAddress','basic_page')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空</span>
										<div class="cue"> ${err_conAddress } </div>
										<div class="cue">
											<sf:errors path="contactAddress" />
										</div>
									</div>
								</li>
							</ul>
						</fieldset>

						<fieldset class="col-md-12 border_font mt20">
							<legend>本单位负责军队业务的人员信息</legend>
							<ul class="list-unstyled f14">
								<li class="col-md-3 col-sm-6 col-xs-12 pl10">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><i class="red">*</i> 姓名</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="armyBusinessName" required maxlength="10" <c:if test="${!fn:contains(audit,'armyBusinessName')&&currSupplier.status==2}">readonly="readonly"</c:if>   value="${currSupplier.armyBusinessName}" <c:if test="${fn:contains(audit,'armyBusinessName')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'armyBusinessName','basic_page')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空</span>
										<div class="cue"> ${err_armName} </div>
										<div class="cue">
											<sf:errors path="armyBusinessName" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 传真</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="armyBusinessFax" required isFax="true" onkeyup="value=value.replace(/[^\d-]/g,'')"  <c:if test="${!fn:contains(audit,'armyBusinessFax')&&currSupplier.status==2}">readonly="readonly"</c:if> value="${currSupplier.armyBusinessFax}" <c:if test="${fn:contains(audit,'armyBusinessFax')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'armyBusinessFax','basic_page')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，如: 0101234567</span>
										<div class="cue"> ${err_armFax } </div>
										<div class="cue">
											<sf:errors path="armyBusinessFax" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 固定电话</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="armyBuinessMobile" required   onkeyup="value=value.replace(/[^\d-]/g,'')" value="${currSupplier.armyBuinessMobile}"  <c:if test="${!fn:contains(audit,'armyBuinessMobile')&&currSupplier.status==2}">readonly="readonly"</c:if> <c:if test="${fn:contains(audit,'armyBuinessMobile')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'armyBuinessMobile','basic_page')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，如: 0101234567</span>
										<div class="cue"> ${err_armMobile } </div>
										<div class="cue">
											<sf:errors path="armyBuinessMobile" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 手机</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="armyBuinessTelephone" required isPhone="true" onkeyup="value=value.replace(/[^\d]/g,'')" value="${currSupplier.armyBuinessTelephone}" <c:if test="${!fn:contains(audit,'armyBuinessTelephone')&&currSupplier.status==2}">readonly="readonly"</c:if>  <c:if test="${fn:contains(audit,'armyBuinessTelephone')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'armyBuinessTelephone','basic_page')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，11位手机号码</span>
										<div class="cue"> ${err_armTelephone } </div>
										<div class="cue">
											<sf:errors path="armyBuinessTelephone" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 邮箱</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="email" name="armyBuinessEmail" required email value="${currSupplier.armyBuinessEmail}"  <c:if test="${!fn:contains(audit,'armyBuinessEmail')&&currSupplier.status==2}">readonly="readonly"</c:if>   <c:if test="${fn:contains(audit,'armyBuinessEmail')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'armyBuinessEmail','basic_page')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，如：123456@qq.com</span>
										<div class="cue"> ${err_armEmail } </div>
										<div class="cue">
											<sf:errors path="armyBuinessEmail" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 地址</span>
									<div class="col-md-12 col-xs-12 col-sm-12 select_common p0">
										<div class="col-md-5 col-xs-5 col-sm-5 mr5 p0">
											<select id="root_area_select_id_army" name="armyBuinessProvince" onchange="loadAreaSelect(this,'#children_area_select_id_army')" <c:if test="${fn:contains(audit,'armyBuinessCity')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'armyBuinessCity','basic_page')"</c:if>>
												<option value="">请选择</option>
												<c:forEach items="${province }" var="prov">
													<c:if test="${prov.id==currSupplier.armyBuinessProvince }">
														<option value="${prov.id }" selected="selected">${prov.name }</option>
													</c:if>
													<c:if test="${prov.id!=currSupplier.armyBuinessProvince }">
														<option value="${prov.id }">${prov.name }</option>
													</c:if>
												</c:forEach>

											</select>
										</div>
										<div class="col-md-5 col-xs-5 col-sm-5 mr5 p0">
											<select id="children_area_select_id_army" name="armyBuinessCity" <c:if test="${fn:contains(audit,'armyBuinessCity')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'armyBuinessCity','basic_page')"</c:if>>
												<option value="">请选择</option>
												<c:forEach items="${currSupplier.armyCity }" var="city">
													<c:if test="${city.id==currSupplier.armyBuinessCity }">
														<option value="${city.id }" selected="selected">${city.name }</option>
													</c:if>
													<c:if test="${city.id!=currSupplier.armyBuinessCity }">
														<option value="${city.id }">${city.name }</option>
													</c:if>
												</c:forEach>
											</select>
										</div>
										<div class="cue"> ${err_armCity }</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 详细地址</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="armyBuinessAddress" required maxlength="50" value="${currSupplier.armyBuinessAddress}"  <c:if test="${!fn:contains(audit,'armyBuinessAddress')&&currSupplier.status==2}">readonly="readonly"</c:if> 
											<c:if test="${fn:contains(audit,'armyBuinessAddress')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'armyBuinessAddress','basic_page')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空</span>
										<div class="cue"> ${err_armAddress } </div>
										<div class="cue">
											<sf:errors path="armyBuinessAddress" />
										</div>
									</div>
								</li>
							</ul>
						</fieldset>

						<%-- <fieldset class="col-md-12 border_font mt20">
							<legend>营业执照</legend>
							<ul class="list-unstyled f14">
								<li class="col-md-3 col-sm-6 col-xs-12 pl10">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>统一社会信用代码</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="creditCode"  required maxlength="18" id="creditCode" onkeyup="value=value.replace(/[^\d|a-zA-Z]/g,'')" value="${currSupplier.creditCode}" <c:if test="${fn:contains(audit,'creditCode')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'creditCode','basic_page')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<!-- <span class="input-tip">不能为空，18位数字或字母</span> -->
										<div class="cue"> ${err_creditCide} </div>
										<div class="cue">
											<sf:errors path="creditCode" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 登记机关</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="registAuthority" required maxlength="20" value="${currSupplier.registAuthority}" <c:if test="${fn:contains(audit,'registAuthority')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'registAuthority','basic_page')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，长度不可大于20位</span>
										<div class="cue"> ${err_reAuthoy } </div>
										<div class="cue">
											<sf:errors path="registAuthority" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 注册资本（人民币：万元）</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="registFund" onblur="validateMoney(this.value, 4)" required value="${currSupplier.registFund}" <c:if test="${fn:contains(audit,'registFund')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'registFund','basic_page')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，值不可小于零</span>
										<div class="cue" id="err_fund"> ${err_fund } </div>
										<div class="cue">
											<sf:errors path="registFund" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 营业期限   <input type="checkbox" name="branchName" onclick="checkYyqx(this);" <c:if test="${currSupplier.branchName=='1'}"> checked='true'</c:if>   value="1"> 长期</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<fmt:formatDate value="${currSupplier.businessStartDate}" pattern="yyyy-MM-dd" var="businessStartDate" />
										<input id="expireDate" type="text" readonly="readonly" onClick="WdatePicker()" name="businessStartDate" value="${businessStartDate}" <c:if test="${fn:contains(audit,'businessStartDate')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'businessStartDate','basic_page')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">如果勾选长期,可不填写有效期</span>
										<div class="cue"> ${err_sDate } </div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12 mb25"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5" <c:if test="${fn:contains(audit,'businessCert')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'businessCert','basic_page')"</c:if>><i class="red">*</i> 营业执照</span>
									<div class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0">
										<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="business_up" maxcount="1" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" auto="true" />
										<u:show showId="business_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" />
										<div class="cue"> ${err_business} </div>
									</div>
								</li>

								<li class="col-md-12 col-xs-12 col-sm-12 mb25">
									<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> <i class="red">* </i>经营范围（按照营业执照填写）</span>
									<div class="col-md-12 col-xs-12 col-sm-12 p0">
										<textarea class="col-md-12 col-xs-12 col-sm-12 h80" maxlength="1000"  onkeyup="if(value.length==1000){layer.msg('字数过多，不可超过1000字！')}" required="required" name="businessScope" <c:if test="${fn:contains(audit,'businessScope')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'businessScope','basic_page')"</c:if>>${currSupplier.businessScope}</textarea>
										<div class="cue">
											<sf:errors path="businessScope" />
										</div>
									</div>
								</li>
							</ul>
						</fieldset> --%>

						<h2 class="count_flow clear pt20"> <i>2</i> 境外信息</h2>
						<fieldset class="col-md-12 border_font mt20">
							<legend>境外分支</legend>
							<ul class="list-unstyled f14" id="list-unstyled">
								<li class="col-md-3 col-sm-6 col-xs-12 pl10">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red"></i>境外分支机构</span>
									<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
										<select name="overseasBranch" onchange="onchangeBranch(this)" id="overseas_branch_select_id" <c:if test="${!fn:contains(audit,'overseasBranch')&&currSupplier.status==2}">onchange="this.selectedIndex=this.defaultIndex;"</c:if> <c:if test="${fn:contains(audit,'overseasBranch')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'overseasBranch','basic_page')"</c:if>>
											<option value="0" <c:if test="${currSupplier.overseasBranch eq '0'}">selected</c:if>>无</option>
											<option value="1" <c:if test="${currSupplier.overseasBranch eq '1'}">selected</c:if>>有</option>
										</select>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5 white">操作</span>
									<div class="col-md-12 col-xs-12 col-sm-12 p0 mb25 h30">

									</div>
								</li>
								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5 white">操作</span>
									<div class="col-md-12 col-xs-12 col-sm-12 p0 mb25 h30">

									</div>
								</li>
								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5 white">操作</span>
									<div class="col-md-12 col-xs-12 col-sm-12 p0 mb25 h30">

									</div>
								</li>
							</ul>
							<ul id="branch_list_body" class="list-unstyled clear" <c:if test="${currSupplier.overseasBranch ne '1'}">style="display:none;"</c:if>>
								<c:forEach items="${currSupplier.branchList }" var="bran" varStatus="vs">
									<li class="col-md-3 col-sm-6 col-xs-12 pl10">
										<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">* </i>机构名称</span>
										<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
											<input class= 'cBranchName' type="text" name="branchList[${vs.index }].organizationName" id="sup_branchName" required maxlength="50" value="${bran.organizationName}"  <c:if test="${!fn:contains(audit,'organizationName')&&currSupplier.status==2}">readonly="readonly"</c:if>  <c:if test="${fn:contains(audit,'organizationName_'.concat(bran.id))}">style="border: 1px solid red;" onmouseover="errorMsg(this,'organizationName_${bran.id }','basic_page')"</c:if>/>
											<span class="add-on cur_point">i</span>
											<span class="input-tip">不能为空</span>
											<div class="cue">
												<sf:errors path="branchList[${vs.index }].organizationName" />
											</div>
										</div>
									</li>
									
									<%-- <li class="col-md-3 col-sm-6 col-xs-12">
										<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5 "><i class="red">* </i>所在洲</span>
										<div class="select_common col-md-12 col-sm-12 col-xs-12  p0">
											<select class='cOverseas' <c:if test="${!fn:contains(audit,'overseasBranch')&&currSupplier.status==2}">onchange=""</c:if> <c:if test="${fn:contains(audit,'countryName_'.concat(bran.id))}">style="border: 1px solid red;" onmouseover="errorMsg(this,'countryName_${bran.id }','basic_page')"</c:if>>
												<option value="">请选择</option>
												<c:forEach items="${continents }" var="continent">
													<option value="${continent.id }" <c:if test="${bran.cnre.cnr.continentId==continent.id}">selected='selected' </c:if> >${continent.name }</option>
												</c:forEach>
											</select>
										</div>
									</li> --%>
									
									<li class="col-md-4 col-sm-6 col-xs-12">
										<%-- <input type="hidden" name="branchList[${vs.index }].cnre" value="${bran.cnre }" /> --%>
										<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5 "><i class="red">* </i>所在国家（地区）</span>
										<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
											<div class="col-md-3 col-xs-5 col-sm-5 mr5 p0">
												<select class='cOverseas' id="select_continent_${vs.index}" onchange="loadCountrySelect(this,'#select_country_${vs.index}');" <c:if test="${!fn:contains(audit,'overseasBranch')&&currSupplier.status==2}">onchange=""</c:if> <c:if test="${fn:contains(audit,'countryName_'.concat(bran.id))}">style="border: 1px solid red;" onmouseover="errorMsg(this,'countryName_${bran.id }','basic_page')"</c:if>>
													<option value="">请选择</option>
													<c:forEach items="${continents }" var="continent">
														<option value="${continent.id }" <c:if test="${bran.cnre.cnr.continentId eq continent.id}">selected='selected' </c:if> >${continent.name }</option>
													</c:forEach>
												</select>
											</div>
											<div class="col-md-8 col-xs-5 col-sm-5 mr5 p0">
												<select class='cOverseas' id="select_country_${vs.index}" name="branchList[${vs.index }].country" required <c:if test="${!fn:contains(audit,'overseasBranch')&&currSupplier.status==2}">onchange=""</c:if> <c:if test="${fn:contains(audit,'countryName_'.concat(bran.id))}">style="border: 1px solid red;" onmouseover="errorMsg(this,'countryName_${bran.id }','basic_page')"</c:if>>
													<option value="">请选择</option>
													<c:forEach items="${bran.cnre.cnrList }" var="cnr">
														<option value="${cnr.nationId }" <c:if test="${bran.country eq cnr.nationId}">selected='selected' </c:if> >${cnr.nationName }</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</li>

									<%-- <li class="col-md-3 col-sm-6 col-xs-12">
										<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5 "><i class="red">* </i>所在国家（地区）</span>
										<div class="select_common col-md-12 col-sm-12 col-xs-12  p0">
											<select class='cOverseas' name="branchList[${vs.index }].country" required <c:if test="${!fn:contains(audit,'overseasBranch')&&currSupplier.status==2}">onchange=""</c:if> <c:if test="${fn:contains(audit,'countryName_'.concat(bran.id))}">style="border: 1px solid red;" onmouseover="errorMsg(this,'countryName_${bran.id }','basic_page')"</c:if>>
												<option value="">请选择</option>
												<c:forEach items="${foreign }" var="fr">
													<option value="${fr.id }" <c:if test="${bran.country==fr.id}">selected='selected' </c:if> >${fr.name }</option>
												</c:forEach>
											</select>
										</div>
									</li> --%>

									<li class="col-md-3 col-sm-6 col-xs-12">
										<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">* </i>详细地址</span>
										<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
											<input class='cDetailAdddress' type="text" name="branchList[${vs.index }].detailAddress" required maxlength="50" id="sup_branchAddress" value="${bran.detailAddress}"
												<c:if test="${!fn:contains(audit,'detailAddress_'.concat(bran.id))&&currSupplier.status==2}">readonly="readonly"</c:if>  
												<c:if test="${fn:contains(audit,'detailAddress_'.concat(bran.id))}">style="border: 1px solid red;" onmouseover="errorMsg(this,'detailAddress_${bran.id }','basic_page')"</c:if>/>
											<span class="add-on cur_point">i</span>
											<span class="input-tip">不能为空</span>
											<div class="cue">
												<sf:errors path="branchList[${vs.index }].detailAddress" />
											</div>
										</div>
									</li>

									<li class="col-md-2 col-sm-6 col-xs-12">
										<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5 white">操作</span>
										<div class="col-md-12 col-xs-12 col-sm-12 p0 mb25 h30">
											<c:choose>
                         <c:when test="${currSupplier.status==2 }">
                         	<input type="button" disabled="disabled" class="btn list_btn btn-Invalid" value="十" />
                         </c:when>
                         <c:otherwise>
                           <input type="button" onclick="addBranch(this)" class="btn list_btn" value="十" />
                         </c:otherwise>
                       </c:choose>
											<input type="button" onclick="delBranch(this)" class="btn list_btn" value="一" />
										 	<input type="hidden" name="branchList[${vs.index }].id"  required  value="${bran.id}"/>
										</div>
									</li>

									<li class="col-md-12 col-xs-12 col-sm-12 mb25">
										<span class="col-md-12 c ol-xs-12 col-sm-12 padding-left-5"><i class="red">* </i>生产经营范围</span>
										<div class="col-md-12 col-xs-12 col-sm-12 p0">
											<textarea class="cPrdArea col-md-12 col-xs-12 col-sm-12 h80" maxlength="1000"
												onkeyup="checkCharLimit('branchbusinessSope_${vs.index }','limit_char_branchbusinessSope_${vs.index }',1000);if(value.length==1000){layer.msg('字数过多，不可超过1000字！')}" 
												id="branchbusinessSope_${vs.index }" required name="branchList[${vs.index }].businessSope" 
												<c:if test="${!fn:contains(audit,'businessSope')&&currSupplier.status==2}">readonly="readonly"</c:if>
												<c:if test="${fn:contains(audit,'businessSope_'.concat(bran.id))}">style="border: 1px solid red;" onmouseover="errorMsg(this,'businessSope_${bran.id }','basic_page')"</c:if>>${bran.businessSope}</textarea>
											<span class="sm_tip fr">还可输入 <span id="limit_char_branchbusinessSope_${vs.index }">1000</span> 个字</span>
											<div class="cue">
												<sf:errors path="branchList[${vs.index }].businessSope" />
											</div>
										</div>
									</li>
								</c:forEach>
							</ul>
						</fieldset>
						<!-- 财务信息 -->
						<h2 class="count_flow clear pt20"> <i>3</i> 近三年财务信息
	  					<span class="red"> ${err_bearchFile}</span></h2>
						<div class="padding-top-10 clear" id="financeInfo">
							<c:forEach items="${currSupplier.listSupplierFinances}" var="finance" varStatus="vs">
								<h2 class="count_flow clear">${finance.year}年财务信息  <span style="float:right" class="b">（金额单位：万元） </span>  </h2>
								<div class="col-md-12 col-xs-12 col-sm-12 border_font <c:if test="${vs.index != 2}">mb10</c:if>">
									<div class="col-md-12 col-sm-12 col-xs-12 p0 over_auto">
										<div class="col-md-12 col-sm-12 col-xs-12 p0 tl">
											<span class="red"></span>
										</div>
										<div class="col-md-12 col-sm-12 col-xs-12 p0">
											<table class="table table-bordered table-condensed mt5 table_wrap table_input">
												<thead>
													<tr>
														<th class="w50 info">年份</th>
														<th class="info"><font color=red>*</font> 会计事务所名称</th>
														<th class="info"><font color=red>*</font> 事务所联系电话</th>
														<th class="info"><font color=red>*</font> 审计人姓名（2人）</th>
														<th class="info"><font color=red>*</font> 资产总额</th>
														<th class="info"><font color=red>*</font> 负债总额</th>
														<th class="info"><font color=red>*</font> 净资产总额</th>
														<th class="info"><font color=red>*</font> 营业收入</th>
													</tr>
												</thead>
												<tbody id="finance_list_tbody_id">
													<c:set var="infoId" value="${finance.id }_info" />
													<tr <c:if test="${fn:contains(audit,infoId)}"> onmouseover="errorMsg(this,'${infoId}','basic_page')"</c:if>>
														<td class="tc" <c:if test="${fn:contains(audit,infoId)}">style="border: 1px solid red;"</c:if>>
															<input type="hidden" name="listSupplierFinances[${vs.index }].id" value="${finance.id}" required>
															<input type="text" readonly="readonly" required="required" class="w50 border0 tc" name="listSupplierFinances[${vs.index }].year" value="${finance.year}"  > </td>
														<td class="tc" <c:if test="${fn:contains(audit,infoId)}">style="border: 1px solid red;"</c:if>>
															<input type="text" required="required" class="w200 border0" name="listSupplierFinances[${vs.index }].name" value="${finance.name}"  <c:if test="${!fn:contains(audit,infoId)&&currSupplier.status==2}">readonly="readonly"</c:if> >
														</td>
														<td class="tc" <c:if test="${fn:contains(audit,infoId)}">style="border: 1px solid red;"</c:if>>
															<input type="text" required="required" class="w100 border0" name="listSupplierFinances[${vs.index }].telephone" value="${finance.telephone}"
																onkeyup="value=value.replace(/[^\d-]/g,'')" 
																onpropertychange="value=value.replace(/[^\d-]/g,'')" 
																oninput="value=value.replace(/[^\d-]/g,'')" 
															<c:if test="${!fn:contains(audit,infoId)&&currSupplier.status==2}">readonly="readonly"</c:if> >
														</td>
														<td class="tc" <c:if test="${fn:contains(audit,infoId)}">style="border: 1px solid red;"</c:if>>
															<input type="text" required="required" class="w200 border0" name="listSupplierFinances[${vs.index }].auditors" value="${finance.auditors}" <c:if test="${!fn:contains(audit,infoId)&&currSupplier.status==2}">readonly="readonly"</c:if> >
														</td>
														<td class="tc" <c:if test="${fn:contains(audit,infoId)}">style="border: 1px solid red;"</c:if>>
															<input type="text" required="required" class="w80 border0" onblur="validateMoney(this.value, 4, false)" name="listSupplierFinances[${vs.index }].totalAssets" value="${finance.totalAssets}" <c:if test="${!fn:contains(audit,infoId)&&currSupplier.status==2}">readonly="readonly"</c:if> >
														</td>
														<td class="tc" <c:if test="${fn:contains(audit,infoId)}">style="border: 1px solid red;"</c:if>>
															<input type="text" required="required" class="w80 border0" onblur="validateMoney(this.value, 4)" name="listSupplierFinances[${vs.index }].totalLiabilities" value="${finance.totalLiabilities}" <c:if test="${!fn:contains(audit,infoId)&&currSupplier.status==2}">readonly="readonly"</c:if> >
														</td>
														<td class="tc" <c:if test="${fn:contains(audit,infoId)}">style="border: 1px solid red;"</c:if>>
															<input type="text" required="required" class="w80 border0" onblur="validateMoney(this.value, 4, false)" name="listSupplierFinances[${vs.index }].totalNetAssets" value="${finance.totalNetAssets}" <c:if test="${!fn:contains(audit,infoId)&&currSupplier.status==2}">readonly="readonly"</c:if> >
														</td>
														<td class="tc" <c:if test="${fn:contains(audit,infoId)}">style="border: 1px solid red;"</c:if>>
															<input type="text" required="required" class="w80 border0" onblur="validateMoney(this.value, 4)" name="listSupplierFinances[${vs.index }].taking" value="${finance.taking}" 
															<c:if test="${!fn:contains(audit,infoId)&&currSupplier.status==2}">readonly="readonly"</c:if>
																class="txtTempSave"/>
														</td>
													</tr>
												</tbody>
											</table>

											<table id="finance_attach_list_id" class="table table-bordered table-condensed mt5 table_wrap table_input">
												<thead>
													<tr>
														<th class="w50 info">年份</th>
														<!-- <th class="info"><font color=red>*</font> 审计报告书中的审计报告</th> -->
														<th class="info"><font color=red>*</font> 审计报告书中的审计报告</th>
														<th class="info"><font color=red>*</font> 资产负债表</th>
														<th class="info"><font color=red>*</font> 财务利润表</th>
														<th class="info"><font color=red>*</font> 现金流量表</th>
														<th class="info">所有者权益变动表</th>
													</tr>
												</thead>
												<tbody id="finance_attach_list_tbody_id">
													<c:set var="file" value="${finance.id }_file" />
													<tr <c:if test="${fn:contains(audit,file)}"> onmouseover="errorMsg(this,'${file}','basic_page')"</c:if>>
														<td class="tc" <c:if test="${fn:contains(audit,file)}">style="border: 1px solid red;" </c:if>>${finance.year}</td>
														<td class="tc" <c:if test="${fn:contains(audit,file)}">style="border: 1px solid red;" </c:if>>
														  <div class="w200 fl">
																<c:if test="${(fn:contains(audit,file)&&currSupplier.status==2) || currSupplier.status==-1 || currSupplier.status==1}"> 	<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="fina_${vs.index}_audit_up" multiple="true" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${finance.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierAuditOpinion}" auto="true" /> </c:if>
																<c:if test="${!fn:contains(audit,file)&&currSupplier.status==2}">  <u:show showId="fina_${vs.index}_audit" delete="false"     groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${finance.id}" typeId="${supplierDictionaryData.supplierAuditOpinion}" sysKey="${sysKey}" /></c:if>
														  	<c:if test="${currSupplier.status==-1 || currSupplier.status==1 || fn:contains(audit,file)}">  <u:show showId="fina_${vs.index}_audit"     groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${finance.id}" typeId="${supplierDictionaryData.supplierAuditOpinion}" sysKey="${sysKey}" /></c:if>
														   </div>
														</td>
														<td class="tc" <c:if test="${fn:contains(audit,file)}">style="border: 1px solid red;" </c:if>>
														  <div class="w200 fl">
																<c:if test="${(fn:contains(audit,file)&&currSupplier.status==2) || currSupplier.status==-1 || currSupplier.status==1}">  <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="fina_${vs.index}_lia_up" multiple="true" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${finance.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierLiabilities}" auto="true" /></c:if>
												       	<c:if test="${!fn:contains(audit,file)&&currSupplier.status==2}"> 	<u:show showId="fina_${vs.index}_lia" delete="false"   groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${finance.id}" typeId="${supplierDictionaryData.supplierLiabilities}" sysKey="${sysKey}" /></c:if>
												  	   	<c:if test="${currSupplier.status==-1 || currSupplier.status==1 || fn:contains(audit,file)}"> 	<u:show showId="fina_${vs.index}_lia"    groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${finance.id}" typeId="${supplierDictionaryData.supplierLiabilities}" sysKey="${sysKey}" /></c:if>
														  </div>
														</td>
														<td class="tc" <c:if test="${fn:contains(audit,file)}">style="border: 1px solid red;" </c:if>>
														  <div class="w200 fl">
																<c:if test="${(fn:contains(audit,file)&&currSupplier.status==2 ) || currSupplier.status==-1 || currSupplier.status==1}"> <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="fina_${vs.index}_pro_up" multiple="true" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${finance.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierProfit}" auto="true" /></c:if>
																<c:if test="${!fn:contains(audit,file)&&currSupplier.status==2}">  <u:show showId="fina_${vs.index}_pro"  delete="false"   groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${finance.id}" typeId="${supplierDictionaryData.supplierProfit}" sysKey="${sysKey}" /></c:if>
																<c:if test="${currSupplier.status==-1 || currSupplier.status==1 || fn:contains(audit,file)}">  <u:show showId="fina_${vs.index}_pro"    groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${finance.id}" typeId="${supplierDictionaryData.supplierProfit}" sysKey="${sysKey}" /></c:if>
														  </div>
														</td>
														<td class="tc" <c:if test="${fn:contains(audit,file)}">style="border: 1px solid red;" </c:if>>
														  <div class="w200 fl">
																<c:if test="${(fn:contains(audit,file)&&currSupplier.status==2)|| currSupplier.status==-1 || currSupplier.status==1}"> 	<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="fina_${vs.index}_cash_up" multiple="true" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${finance.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierCashFlow}" auto="true" /></c:if>
																<c:if test="${!fn:contains(audit,file)&&currSupplier.status==2}"> <u:show showId="fina_${vs.index}_cash" delete="false"   groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${finance.id}" typeId="${supplierDictionaryData.supplierCashFlow}" sysKey="${sysKey}" /></c:if>
	 													  	<c:if test="${currSupplier.status==-1 || currSupplier.status==1 || fn:contains(audit,file)}"> <u:show showId="fina_${vs.index}_cash"    groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${finance.id}" typeId="${supplierDictionaryData.supplierCashFlow}" sysKey="${sysKey}" /></c:if>
	 													  </div>
	 													</td>
														<td class="tc" <c:if test="${fn:contains(audit,file)}">style="border: 1px solid red;" </c:if>>
														  <div class="w200 fl">
																<c:if test="${(fn:contains(audit,file)&&currSupplier.status==2 ) || currSupplier.status==-1 || currSupplier.status==1}"> 	<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="fina_${vs.index}_change_up" multiple="true" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${finance.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierOwnerChange}" auto="true" /></c:if>
																<c:if test="${!fn:contains(audit,file)&&currSupplier.status==2 }">  <u:show showId="fina_${vs.index}_change" delete="false" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${finance.id}" typeId="${supplierDictionaryData.supplierOwnerChange}" sysKey="${sysKey}" /></c:if>
														  	<c:if test="${currSupplier.status==-1 || currSupplier.status==1 || fn:contains(audit,file)}">  <u:show showId="fina_${vs.index}_change"  groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${finance.id}" typeId="${supplierDictionaryData.supplierOwnerChange}" sysKey="${sysKey}" /></c:if>
														  </div>
														</td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>

					<div class="padding-top-10 clear">
						<h2 class="count_flow clear pt20"> <i>4</i><font color=red>*</font> 出资人（股东）信息  （说明：出资人（股东）多于10人的，可以列出出资金额前十位的信息，但所列的出资比例应高于50%）</h2>
						<div class="col-md-12 col-sm-12 col-xs-12 p0 ul_list mb20">
							<div class="col-md-12 col-sm-12 col-xs-12 p15 mt20">
								<div class="col-md-12 col-sm-12 col-xs-12 p0 mb5">
									<c:choose>
                    <c:when test="${currSupplier.status==2 }">
                    	<button class="btn btn-Invalid"  type="button" disabled="disabled">新增</button>
                    </c:when>
                    <c:otherwise>
                      <button class="btn btn-windows add" type="button" onclick="addStockholder()">新增</button>
               			</c:otherwise>
                  </c:choose>
									<button class="btn btn-windows delete" type="button" onclick="delStockholder()">删除</button>
									<span class="red">${stock }</span>
								</div>
								<div class="col-md-12 col-sm-12 col-xs-12 p0 over_auto">
									<table id="share_table_id" class="table table-bordered table-condensed mt5 table_wrap table_input left_table m_table_fixed_border">
										<thead>
											<tr>
												<th class="info"><input type="checkbox" onchange="checkAll(this, 'stockholder_list_tbody_id')" />
												</th>
												<th class="info">出资人性质</th>
												<th class="info">出资人名称或姓名</th>
												<th class="info">证件类型</th>
												<th class="info">统一社会信用代码或身份证号码</th>
												<th class="info">出资金额或股份（万元/万份）</th>
												<th class="info">比例（%）</th>
											</tr>
										</thead>
										<tbody id="stockholder_list_tbody_id">
											<c:forEach items="${currSupplier.listSupplierStockholders}" var="stockholder" varStatus="stockvs">
												<tr <c:if test="${fn:contains(audit,stockholder.id)}"> onmouseover="errorMsg(this,'${stockholder.id}','basic_page')"</c:if>>
													<td class="tc" <c:if test="${fn:contains(audit,stockholder.id)}">style="border: 1px solid red;" </c:if>><input type="checkbox" value="${stockholder.id}" <c:if test="${fn:contains(audit,stockholder.id)}">readonly='readonly'</c:if>  />
														<input type="hidden" name='listSupplierStockholders[${stockvs.index }].id' value="${stockholder.id}" />
														<input type="hidden" name='listSupplierStockholders[${stockvs.index }].supplierId' value="${stockholder.supplierId}" />
													</td>
													<td class="tc" <c:if test="${fn:contains(audit,stockholder.id)}">style="border: 1px solid red;" </c:if>>
														<select name="listSupplierStockholders[${stockvs.index }].nature" class="w100p border0" onchange="onchangeNature(this.value,'${stockvs.index}')">
															<option value="1" <c:if test="${stockholder.nature==1}"> selected="selected"</c:if> >法人</option>
															<option value="2" <c:if test="${stockholder.nature==2}"> selected="selected"</c:if> >自然人</option>
														</select>
													</td>
													<td class="tc" <c:if test="${fn:contains(audit,stockholder.id)}">style="border: 1px solid red;" </c:if>> <input type='text' style='border:0px;' maxlength="50" name='listSupplierStockholders[${stockvs.index }].name' value='${stockholder.name}'  <c:if test="${!fn:contains(audit,stockholder.id)&&currSupplier.status==2}">readonly='readonly'</c:if>  > </td>
													<td class="tc" <c:if test="${fn:contains(audit,stockholder.id)}">style="border: 1px solid red;" </c:if>>
														<select name="listSupplierStockholders[${stockvs.index }].identityType" class="w100p border0">
															<option value="1" <c:if test="${stockholder.identityType==1}"> selected="selected"</c:if> >
															  <c:if test="${empty stockholder.nature}">统一社会信用代码</c:if>
																<c:if test="${stockholder.nature==1}">统一社会信用代码</c:if>
																<c:if test="${stockholder.nature==2}">居民二代身份证</c:if>
															</option>
															<option value="2" <c:if test="${stockholder.identityType==2}"> selected="selected"</c:if> >其他</option>
														</select>
													</td>
													<td class="tc" <c:if test="${fn:contains(audit,stockholder.id)}">style="border: 1px solid red;" </c:if>>
														<input type='text' style='border:0px;' name='listSupplierStockholders[${stockvs.index }].identity' maxlength="${stockholder.identityType==1?18:60}" onkeyup="value=value.replace(/[^\d|a-zA-Z]/g,'')" onchange="value=value.replace(/[^\d|a-zA-Z]/g,'')" value='${stockholder.identity}' <c:if test="${!fn:contains(audit,stockholder.id)&&currSupplier.status==2}">readonly='readonly'</c:if>>
													</td>
													<td class="tc" <c:if test="${fn:contains(audit,stockholder.id)}">style="border: 1px solid red;" </c:if>> <input type='text' style='border:0px;' class="shares" name='listSupplierStockholders[${stockvs.index }].shares' onblur="validateMoney(this.value, 4, false)" value='${stockholder.shares}' <c:if test="${!fn:contains(audit,stockholder.id)&&currSupplier.status==2}">readonly='readonly'</c:if> > </td>
													<td class="tc" <c:if test="${fn:contains(audit,stockholder.id)}">style="border: 1px solid red;" </c:if>> <input type='text' style='border:0px;' class="proportion_vali" name='listSupplierStockholders[${stockvs.index }].proportion' value='${stockholder.proportion}' <c:if test="${!fn:contains(audit,stockholder.id)&&currSupplier.status==2}">readonly='readonly'</c:if> 
													 	onkeyup="value=value.replace(/[^\d.]/g,'')" onblur="validatePercentage2(this.value)"/></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					
					<!-- 售后服务机构信息 -->
					<div class="clear">
						<h2 class="count_flow clear pt20"> <i>5</i><font color=red>*</font> 售后服务机构</h2>
						<div class="col-md-12 col-sm-12 col-xs-12 p0 ul_list mb20">
							<div class="col-md-12 col-sm-12 col-xs-12 p15 mt20">
								<div class="col-md-12 col-sm-12 col-xs-12 p0 mb5">
									<c:choose>
                  	<c:when test="${currSupplier.status==2 }">
                   		<button class="btn btn-Invalid" type="button" disabled="disabled">新增</button>
                   	</c:when>
                   	<c:otherwise>
                   		<button class="btn btn-windows add" type="button" onclick="addAfterSaleDep()">新增</button>
                   	</c:otherwise>
                 	</c:choose>
									<button class="btn btn-windows delete" type="button" onclick="delAfterSaleDep()">删除</button>
									<span class="red">${afterSale}</span>
								</div>
								<div class="col-md-12 col-sm-12 col-xs-12 p0 over_auto">
									<table id="share_table_id" class="table table-bordered table-condensed mt5 table_wrap table_input left_table m_table_fixed_border">
										<thead>
											<tr>
												<th class="info"><input type="checkbox" onchange="checkAll(this, 'afterSaleDep_list_tbody_id')" />
												</th>
												<th class="info">分支（或服务）机构名称</th>
												<th class="info">类别</th>
												<th class="info">所在省市县</th>
												<th class="info">负责人</th>
												<th class="info">联系电话</th>
											</tr>
										</thead>
										<tbody id="afterSaleDep_list_tbody_id">
											<c:forEach items="${currSupplier.listSupplierAfterSaleDep}" var="afterSaleDep" varStatus="dep">
												<tr <c:if test="${fn:contains(audit,afterSaleDep.id)}"> onmouseover="errorMsg(this,'${afterSaleDep.id}','basic_page')"</c:if>>
													<input type="hidden" name='listSupplierAfterSaleDep[${dep.index }].id' value="${afterSaleDep.id}" />
													<input type="hidden" name='listSupplierAfterSaleDep[${dep.index }].supplierId' value="${afterSaleDep.supplierId}" />
													<td class="tc" <c:if test="${fn:contains(audit,afterSaleDep.id)}">style="border: 1px solid red;" </c:if>>
														<input type="checkbox" value="${afterSaleDep.id}" />
													</td>
													<td class="tc" <c:if test="${fn:contains(audit,afterSaleDep.id)}">style="border: 1px solid red;" </c:if>>
													 <div class="w300 fl">
													 	<input type='text' style='border:0px;' name='listSupplierAfterSaleDep[${dep.index }].name' maxlength="90" value='${afterSaleDep.name}' <c:if test="${!fn:contains(audit,afterSaleDep.id)&&currSupplier.status==2}">readonly='readonly' </c:if>>
													 </div> 
													</td>
													<td class="tc" <c:if test="${fn:contains(audit,afterSaleDep.id)}">style="border: 1px solid red;" </c:if>>
													  <div class="w120 fl">
														<select name="listSupplierAfterSaleDep[${dep.index }].type" class="w100p border0" <c:if test="${!fn:contains(audit,afterSaleDep.id)&&currSupplier.status==2}">onchange="this.selectedIndex=this.defaultIndex;"</c:if>>
															<option value="1" <c:if test="${afterSaleDep.type == 1}"> selected="selected"</c:if> >自营</option>
															<option value="2" <c:if test="${afterSaleDep.type == 2}"> selected="selected"</c:if> >合作</option>
														</select>
													   </div>
													</td>
													<td class="tc" <c:if test="${fn:contains(audit,afterSaleDep.id)}">style="border: 1px solid red;" </c:if>> 
													  <div class="fl w200">
													  	<input type='text' style='border:0px;' name='listSupplierAfterSaleDep[${dep.index }].address' maxlength="50" value='${afterSaleDep.address}' <c:if test="${!fn:contains(audit,afterSaleDep.id)&&currSupplier.status==2}">readonly='readonly' </c:if>>
													  </div>
												    </td>
													<td class="tc" <c:if test="${fn:contains(audit,afterSaleDep.id)}">style="border: 1px solid red;" </c:if>> 
													 <div class="fl w200">
													    <input type='text' style='border:0px;' name='listSupplierAfterSaleDep[${dep.index }].leadName' maxlength="20" value='${afterSaleDep.leadName}' <c:if test="${!fn:contains(audit,afterSaleDep.id)&&currSupplier.status==2}">readonly='readonly' </c:if>>
													 </div>
											    </td>
													<td class="tc" <c:if test="${fn:contains(audit,afterSaleDep.id)}">style="border: 1px solid red;" </c:if>> 
													   <div class="fl w200">
													   		<input type='text' style='border:0px;' name='listSupplierAfterSaleDep[${dep.index }].mobile' onkeyup="value=value.replace(/[^\d-]/g,'')" value='${afterSaleDep.mobile}' <c:if test="${!fn:contains(audit,afterSaleDep.id)&&currSupplier.status==2}">readonly='readonly' </c:if>>
													   </div>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					
					<!-- 参加政府或军队采购经历登记表 -->
					<div class="clear">
						<h2 class="count_flow clear pt20"> <i>6</i> 参加政府或军队采购经历 </h2>
						<div class="col-md-12 col-sm-12 col-xs-12 p0 ul_list mb20">
							<div class="col-md-12 col-sm-12 col-xs-12 p15 mt20">
								<div class="col-md-12 col-sm-12 col-xs-12 p0 mb20">
									<textarea class="col-md-12 col-xs-12 col-sm-12 h80" maxlength="1000"  
										onkeyup="checkCharLimit('purchaseExperience','limit_char_purchaseExperience',1000);if(value.length==1000){layer.msg('字数过多，不可超过1000字！')}" 
										name="purchaseExperience" id="purchaseExperience"
										<c:if test="${!fn:contains(audit,'purchaseExperience')&&currSupplier.status==2}">readonly='readonly' </c:if>
										<c:if test="${fn:contains(audit,'purchaseExperience')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'purchaseExperience','basic_page')"</c:if>>${currSupplier.purchaseExperience}</textarea>
									<span class="sm_tip fr">还可输入 <span id="limit_char_purchaseExperience">1000</span> 个字</span>
								</div>
							</div>
						</div>
					</div>
					<div class="clear">
						<h2 class="count_flow clear pt20"> <i>7</i><font color=red>*</font> 公司简介 </h2>
						<div class="col-md-12 col-sm-12 col-xs-12 p0 ul_list mb50">
							<div class="col-md-12 col-sm-12 col-xs-12 p15 mt20">
								<div class="col-md-12 col-sm-12 col-xs-12 p0 mb20">
									<textarea class="col-md-12 col-xs-12 col-sm-12 h80" required="required" maxlength="1000"  
										onkeyup="checkCharLimit('description','limit_char_description',1000);if(value.length==1000){layer.msg('字数过多，不可超过1000字！')}" 
										placeholder="包括供应商的基本情况、组织机构设置、人员情况以及产品信息等内容，字数请控制在1000字以内。" 
										name="description" id="description"
										<c:if test="${!fn:contains(audit,'description')&&currSupplier.status==2}">readonly='readonly' </c:if>
										<c:if test="${fn:contains(audit,'description')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'description','basic_page')"</c:if>>${currSupplier.description}</textarea>
									<span class="sm_tip fr">还可输入 <span id="limit_char_description">1000</span> 个字</span>
									<div class="cue" style="margin-top:50px;">
										<sf:errors path="description" />
									</div>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>

		<input type="hidden" id="addrIndex" value="${fn:length(currSupplier.addressList)}">
		<input type="hidden" id="branchIndex" value="${fn:length(currSupplier.branchList)}">
		<input type="hidden" id="stockIndex" value="${fn:length(currSupplier.listSupplierStockholders)}">
		<input type="hidden" id="afterSaleIndex" value="${fn:length(currSupplier.listSupplierAfterSaleDep)}">
		<div class="btmfix">
			<div style="margin-top: 15px;text-align: center;">
				<button type="button" class="btn save" onclick="temporarySave();">暂存</button>
				<button type="button" class="btn" onclick="saveBasicInfo('1')">下一步</button>
			</div>
		</div>
		<div class="footer_margin">
 			<jsp:include page="../../../../../index_bottom.jsp"></jsp:include>
 		</div>
		
	</body>
</html>