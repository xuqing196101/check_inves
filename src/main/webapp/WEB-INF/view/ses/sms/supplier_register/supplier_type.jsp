<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>

<head>
<%@ include file="/reg_head.jsp"%>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<c:if test="${currSupplier.status == 2}">
	<%@ include file="/WEB-INF/view/ses/sms/supplier_register/supplier_purchase_dept.jsp"%>
</c:if>
<title>供应商注册</title>
<%@ include file="/WEB-INF/view/common/validate.jsp"%>
<script src="${pageContext.request.contextPath}/public/easyui/jquery.easyui.min.js"></script>
<link href="${pageContext.request.contextPath}/public/easyui/themes/icon.css" media="screen" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/easyui/themes/default/easyui.css" media="screen" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/sms/supplier_register/supplier_type.js"></script>
<style type="text/css">
.textbox.combo {
	border: 0px !important;
}
.cue_province {
	position: absolute;
	left: 265px;
	top: 70px;
	height: 25px;
	line-height: 25px;
	color: #ef0000;
	font-size: 12px;
}
</style>
</head>

<body>
	<div class="wrapper">
		<!-- 隐藏域 -->
		<input type="hidden" id="supplierId" value="${currSupplier.id}" />
		<input type="hidden" id="supplierSt" value="${currSupplier.status}" />
		<input type="hidden" id="typePageField" value="${typePageField}" />
		<input type="hidden" id="proPageField" value="${proPageField}" />
		<input type="hidden" id="engPageField" value="${engPageField}" />
		<input type="hidden" id="sellPageField" value="${sellPageField}" />
		<input type="hidden" id="servePageField" value="${servePageField}" />
		<input type="hidden" id="flagSupplierType" value="${flagSupplierType}" />
		<input type="hidden" id="flagProduct" value="${flagProduct}" />
		<input type="hidden" id="flagSell" value="${flagSell}" />
		<input type="hidden" id="flagProject" value="${flagProject}" />
		<input type="hidden" id="flagServer" value="${flagServer}" />
		<input type="hidden" id="flagSupplierTypeAudit" value="${flagSupplierTypeAudit}" />
		<input type="hidden" id="infoSupplierTypeAudit" value="${infoSupplierTypeAudit}" />
		<input type="hidden" id="quaListJson" value='${quaListJson}' />
		<!-- 项目戳开始 -->
		<jsp:include page="/WEB-INF/view/ses/sms/supplier_register/common_jump.jsp">
			<jsp:param value="${currSupplier.id}" name="supplierId"/>
			<jsp:param value="${currSupplier.status}" name="supplierSt"/>
			<jsp:param value="2" name="currentStep"/>
		</jsp:include>
		<!-- <div class="container clear margin-top-30">
			<h2 class="step_flow">
				<span id="sp1" class="new_step current fl" onclick="updateStep('1')"><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span>
	            <span id="sp2" class="new_step current fl"><i class="">2</i><div class="line"></div> <span class="step_desc_01">供应商类型</span> </span>
	            <span id="ty3" class="new_step fl"><i class="">3</i><div class="line"></div> <span class="step_desc_02">产品类别</span> </span>
	            <span id="sp4" class="new_step fl"><i class="">4</i><div class="line"></div> <span class="step_desc_01">资质文件维护</span> </span>
	            <span id="sp5" class="new_step fl"><i class="">5</i><div class="line"></div> <span class="step_desc_02">销售合同</span> </span>
	            <span id="sp6" class="new_step fl"><i class="">6</i><div class="line"></div> <span class="step_desc_01">采购机构</span> </span>
	            <span id="sp7" class="new_step fl"><i class="">7</i><div class="line"></div> <span class="step_desc_02">承诺书和申请表</span> </span>
	            <span id="sp8" class="new_step fl new_step_last"><i class="">8</i> <span class="step_desc_01">提交审核</span> </span>
	            <div class="clear"></div>
			</h2>
		</div> -->
		<!--详情开始-->
		<div class="sevice_list container mt60">
			<h2>供应商类型</h2>
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="col-md-5 col-sm-6 col-xs-6 title tr"></div>
				<div class="col-md-7 col-sm-6 col-xs-12 service_list">
					<c:forEach items="${scxsList }" var="obj">
						<span id="${obj.id}"
							<c:if test="${fn:contains(typePageField,obj.id)}">style="color: red;" onmouseover="errorMsg(this,'${obj.id }','supplierType_page')"</c:if>><input
							type="checkbox" name="chkItem" onclick="checks(this)"
							<c:if test="${isSalePass=='0' and obj.code=='SALES'}">disabled="disabled"</c:if>
							value="${obj.code}" /> ${obj.name }</span>
					</c:forEach>
					<c:forEach items="${gcfwList }" var="obj">
						<span id="${obj.id}"
							<c:if test="${fn:contains(typePageField,obj.id)}">style="color: red;" onmouseover="errorMsg(this,'${obj.id }','supplierType_page')"</c:if>><input
							type="checkbox" name="chkItem" onclick="checks(this)"
							value="${obj.code }" />${obj.name } </span>
					</c:forEach>
				</div>
			</div>
		</div>

		<div class="container opacity_0" id="tab_div">
			<div class="magazine-page">
				<div class="col-md-12 col-sm-12 col-xs-12 p0 tab-v2 job-content">
					<ul id="page_ul_id" class="nav nav-tabs supplier_tab">
						<li id="productId" style="display:none;"><a
							aria-expanded="true"
							onclick="init_web_upload_in('#production_div')"
							href="#production_div" data-toggle="tab" class=" f18">物资-生产型专业信息</a>
						</li>
						<li id="salesId" style="display:none;"><a
							aria-expanded="false" onclick="init_web_upload_in('#sale_div')"
							href="#sale_div" data-toggle="tab" class="f18">物资-销售型专业信息</a></li>
						<li id="projectId" style="display:none;"><a
							aria-expanded="false"
							onclick="init_web_upload_in('#project_div')" href="#project_div"
							data-toggle="tab" class="f18">工程专业信息</a></li>
						<li id="serviceId" style="display:none;"><a
							aria-expanded="false" onclick="init_web_upload_in('#server_div')"
							href="#server_div" data-toggle="tab" class="f18">服务专业信息</a></li>
					</ul>

					<div style="margin-top: 40px; position:relative;">
						<form id="save_pro_form_id"
							action="${pageContext.request.contextPath}/supplier/perfect_professional.html"
							method="post">
							<input type="hidden" name="formToken" value="${formToken}" />
							<input type="hidden" name="id" id="sid"
								value="${currSupplier.id}" /> <input type="hidden" name="flag" />
							<input type="hidden" name="defaultPage" value="${defaultPage}" />
							<input type="hidden" name="old" value="">
							<div id="tab_content_div_id"
								class="tab-content p0 bgwhite border0">
								<!-- 物资生产型专业信息 -->

								<div class="tab-pane fades" id="production_div">
									<div class=" ">
										<h2 class="list_title">物资-生产型专业信息</h2>
										<ul class="list-unstyled f14 overflow_h">
											<input type="hidden" name="supplierMatPro.id"
												value="${currSupplier.supplierMatPro.id}" />
											<input type="hidden" name="supplierMatPro.supplierId"
												value="${currSupplier.id}" />

											<fieldset
												class="col-md-12 col-sm-12 col-xs-12 border_font mt20">
												<legend>产品研发能力 </legend>

												<li class="col-md-3 col-sm-6 col-xs-12"><span
													class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i
														class="red">*</i> 技术人员数量比例(%)：</span>
													<div
														class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.scaleTech" maxlength="10"
															required value="${currSupplier.supplierMatPro.scaleTech}" 
															<c:if test="${!fn:contains(proPageField,'scaleTech')&&currSupplier.status==2}">readonly='readonly'</c:if>
															<c:if test="${fn:contains(proPageField,'scaleTech')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'scaleTech','mat_pro_page')"</c:if> 
															onkeyup="value=value.replace(/[^\d.]/g,'')"
															onblur="validatePercentage2(this.value)"/>
														<span class="add-on cur_point">i</span> <span
															class="input-tip">不能为空</span>
														<div class="cue">${stech }</div>
														<div class="cue">
															<sf:errors path="supplierMatPro.scaleTech" />
														</div>
													</div></li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span
													class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i
														class="red">*</i> 高级技术人员数量比例(%)：</span>
													<div
														class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.scaleHeightTech" maxlength="10"
															required value="${currSupplier.supplierMatPro.scaleHeightTech}" 
															<c:if test="${!fn:contains(proPageField,'scaleHeightTech')&&currSupplier.status==2}">readonly='readonly'</c:if>
															<c:if test="${fn:contains(proPageField,'scaleHeightTech')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'scaleHeightTech','mat_pro_page')"</c:if> 
															onkeyup="value=value.replace(/[^\d.]/g,'')"
															onblur="validatePercentage2(this.value)"/>
														<span class="add-on cur_point">i</span> <span
															class="input-tip">不能为空</span>
														<div class="cue">${height }</div>
														<div class="cue">
															<sf:errors path="supplierMatPro.scaleHeightTech" />
														</div>
													</div></li>

												<li class="col-md-3 col-sm-6 col-xs-12"><span
													class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i
														class="red">*</i> 研发部门名称：</span>
													<div
														class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.researchName"
															required maxlength="20"
															value="${currSupplier.supplierMatPro.researchName}" <c:if test="${!fn:contains(proPageField,'researchName')&&currSupplier.status==2}">readonly='readonly' </c:if>
															<c:if test="${fn:contains(proPageField,'researchName')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'researchName','mat_pro_page')"</c:if> />
														<span class="add-on cur_point">i</span> <span
															class="input-tip">不能为空</span>
														<div class="cue">${reName }</div>
														<div class="cue">
															<sf:errors path="supplierMatPro.researchName" />
														</div>
													</div></li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span
													class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i
														class="red">*</i> 研发部门人数：</span>
													<div
														class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.totalResearch"
															required onkeyup="value=value.replace(/[^\d]/g,'')" onblur="return validatePositiveInteger(this.value);"
															value="${currSupplier.supplierMatPro.totalResearch}" <c:if test="${!fn:contains(proPageField,'totalResearch')&&currSupplier.status==2}">readonly='readonly' </c:if>
															<c:if test="${fn:contains(proPageField,'totalResearch')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'totalResearch','mat_pro_page')"</c:if> />
														<span class="add-on cur_point">i</span> <span
															class="input-tip">不能为空，且为数字</span>
														<div class="cue">${tRe }</div>
														<div class="cue">
															<sf:errors path="supplierMatPro.totalResearch" />
														</div>
													</div></li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span
													class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i
														class="red">*</i> 研发部门负责人：</span>
													<div
														class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.researchLead"
															required maxlength="20"
															value="${currSupplier.supplierMatPro.researchLead}" <c:if test="${!fn:contains(proPageField,'researchLead')&&currSupplier.status==2}">readonly='readonly' </c:if>
															<c:if test="${fn:contains(proPageField,'researchLead')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'researchLead','mat_pro_page')"</c:if> />
														<span class="add-on cur_point">i</span> <span
															class="input-tip">不能为空</span>
														<div class="cue">${leader }</div>
														<div class="cue">
															<sf:errors path="supplierMatPro.researchLead" />
														</div>
													</div></li>
												<li class="col-md-12 col-xs-12 col-sm-12 mb25"><span
													class="col-md-12 col-xs-12 col-sm-12 padding-left-5">
														承担国家军队科研项目：</span>
													<div class="col-md-12 col-xs-12 col-sm-12 p0">
														<textarea class="col-md-12 col-xs-12 col-sm-12 h80"
															name="supplierMatPro.countryPro" id="countryPro" maxlength="1000"
															onkeyup="checkCharLimit('countryPro','limit_char_countryPro',1000);if(value.length==1000){layer.msg('字数过多，不可超过1000字！')}" <c:if test="${!fn:contains(proPageField,'countryPro')&&currSupplier.status==2}">readonly='readonly' </c:if>
															<c:if test="${fn:contains(proPageField,'countryPro')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'countryPro','mat_pro_page')"</c:if>>${currSupplier.supplierMatPro.countryPro}</textarea>
														<span class="sm_tip fr">还可输入 <span id="limit_char_countryPro">1000</span> 个字</span>
														<div class="cue">
															<sf:errors path="supplierMatPro.countryPro" />
														</div>
													</div></li>
												<li class="col-md-12 col-xs-12 col-sm-12 mb25"><span
													class="col-md-12 col-xs-12 col-sm-12 padding-left-5">
														获得国家军队科技奖项：</span>
													<div class="col-md-12 col-xs-12 col-sm-12 p0">
														<textarea class="col-md-12 col-xs-12 col-sm-12 h80"
															name="supplierMatPro.countryReward" id="countryReward" maxlength="1000"
															onkeyup="checkCharLimit('countryReward','limit_char_countryReward',1000);if(value.length==1000){layer.msg('字数过多，不可超过1000字！')}" <c:if test="${!fn:contains(proPageField,'countryReward')&&currSupplier.status==2}">readonly='readonly' </c:if>
															<c:if test="${fn:contains(proPageField,'countryReward')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'countryReward','mat_pro_page')"</c:if>>${currSupplier.supplierMatPro.countryReward}</textarea>
														<span class="sm_tip fr">还可输入 <span id="limit_char_countryReward">1000</span> 个字</span>
														<div class="cue">
															<sf:errors path="supplierMatPro.countryReward" />
														</div>
													</div></li>
											</fieldset>
										</ul>

										<div class="col-md-12 col-sm-12 col-xs-12 border_font mt20">
											<span class="font_line"><font class="red">*</font> 资质证书信息 </span>
											<div class="col-md-12 col-sm-12 col-xs-12 mb10 p0">
												<c:choose>
                         	<c:when test="${currSupplier.status==2 }">
                           	<button class="btn btn-Invalid"  type="button" disabled="disabled">新增</button>
                           </c:when>
                           <c:otherwise>
                             <button type="button" class="btn btn-windows add" onclick="addCertPro()">新增</button>
                           </c:otherwise>
                         </c:choose>
												<button type="button" class="btn btn-windows delete"
													onclick="delCertPro()">删除</button>
												<span class="red">${cert_pro }</span>
											</div>
											<div class="col-md-12 col-xs-12 col-sm-12 over_auto p0">
												<table
													class="table table-bordered table-condensed mt5 table_wrap left_table table_input m_table_fixed_border">
													<thead>
														<tr>
															<th class="info"><input type="checkbox"
																onchange="checkAll(this, 'cert_pro_list_tbody_id')" />
															</th>
															<th class="info" style="width: 120px">资质证书名称</th>
															<th class="info">证书编号</th>
															<th class="info">资质等级</th>
															<th class="info">发证机关或机构</th>
															<th class="info">有效期（起始时间）</th>
															<th class="info">有效期（结束时间）</th>
															<th class="info">证书状态</th>
															<th class="info w200">证书图片（可上传多张）</th>
														</tr>
													</thead>
													<tbody id="cert_pro_list_tbody_id">
														<c:set var="certProNumber" value="0" />
														<c:forEach
															items="${currSupplier.supplierMatPro.listSupplierCertPros}"
															var="certPro" varStatus="vs">
															<tr <c:if test="${fn:contains(proPageField,certPro.id)}"> onmouseover="errorMsg(this,'${certPro.id}','mat_pro_page')"</c:if>>
																<td class="tc">
																	<input type="checkbox" class="border0"
																	value="${certPro.id}" /> <input type="hidden"
																	required="required" 
																	name="supplierMatPro.listSupplierCertPros[${certProNumber}].id"
																	value="${certPro.id}" class="mt5 border0">
																	<div class="cue">
																		<sf:errors
																			path="supplierMatPro.listSupplierCertPros[${certProNumber}].id" />
																	</div></td>
																<td class="tc"
																	<c:if test="${fn:contains(proPageField,certPro.id)}">style="border: 1px solid red;" </c:if>>
																	 <div class="w200 fl"><input
                                  required="required" type="text"   <c:if test="${certPro.name == '质量管理体系认证证书' and vs.index == 0}">readonly="readonly"</c:if>
																	name="supplierMatPro.listSupplierCertPros[${certProNumber}].name"
																	value="${certPro.name}" class="border0" />
																	</div>
																</td>
																<td class="tc"
																	<c:if test="${fn:contains(proPageField,certPro.id)}">style="border: 1px solid red;" </c:if>>
																	<div class="w150 fl"><input
																	required="required" type="text" maxlength="150"  <c:if test="${!fn:contains(proPageField,certPro.id)&&currSupplier.status==2}">readonly="readonly"</c:if>
																	name="supplierMatPro.listSupplierCertPros[${certProNumber}].code"
																	value="${certPro.code}" class="border0" />
																	</div>
																</td>
																<td class="tc"
																	<c:if test="${fn:contains(proPageField,certPro.id)}">style="border: 1px solid red;" </c:if>><input
																	required="required" type="text" maxlength="150"   <c:if test="${!fn:contains(proPageField,certPro.id)&&currSupplier.status==2}">readonly="readonly"</c:if>
																	name="supplierMatPro.listSupplierCertPros[${certProNumber}].levelCert"
																	value="${certPro.levelCert}" class="border0" /></td>
																<td class="tc"
																	<c:if test="${fn:contains(proPageField,certPro.id)}">style="border: 1px solid red;" </c:if>>
																	  <div class="w200 fl">
																	  <input
																	required="required" type="text" maxlength="150"  <c:if test="${!fn:contains(proPageField,certPro.id)&&currSupplier.status==2}">readonly="readonly"</c:if>
																	name="supplierMatPro.listSupplierCertPros[${certProNumber}].licenceAuthorith"
																	value="${certPro.licenceAuthorith}" class="border0" />
																	 </div>
																</td>
																<td class="tc"
																	<c:if test="${fn:contains(proPageField,certPro.id)}">style="border: 1px solid red;" </c:if>>
																	<input type="text" required="required"
																	<c:if test="${(fn:contains(proPageField,certPro.id)&&currSupplier.status==2) || currSupplier.status==-1 || empty(currSupplier.status)}"> onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',readOnly:true})"</c:if>
																	name="supplierMatPro.listSupplierCertPros[${certProNumber}].expStartDate"
																	value="<fmt:formatDate value="${certPro.expStartDate}" pattern="yyyy-MM-dd "/>"
																	class="border0" /></td>
																<td class="tc"
																	<c:if test="${fn:contains(proPageField,certPro.id)}">style="border: 1px solid red;" </c:if>>
																	<input type="text" required="required"
																	<c:if test="${(fn:contains(proPageField,certPro.id)&&currSupplier.status==2) || currSupplier.status==-1 || empty(currSupplier.status)}"> onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d',readOnly:true})"</c:if>
																	name="supplierMatPro.listSupplierCertPros[${certProNumber}].expEndDate"
																	value="<fmt:formatDate value="${certPro.expEndDate}" pattern="yyyy-MM-dd "/>"
																	class="border0" /></td>
																<td class="tc"
																	<c:if test="${fn:contains(proPageField,certPro.id)}">style="border: 1px solid red;" </c:if>><input
																	required="required" type="text" maxlength="15"  <c:if test="${!fn:contains(proPageField,certPro.id)&&currSupplier.status==2}">readonly="readonly"</c:if>
																	name="supplierMatPro.listSupplierCertPros[${certProNumber}].mot"
																	value="${certPro.mot}" class="border0" /></td>
																<td class="tc"
																	<c:if test="${fn:contains(proPageField,certPro.id)}">style="border: 1px solid red;" </c:if>>
																	<div class="fl w200">
																	<c:if test="${(fn:contains(proPageField,certPro.id)&&currSupplier.status==2 ) || currSupplier.status==-1 || empty(currSupplier.status)}">  <u:upload
																		singleFileSize="${properties['file.picture.upload.singleFileSize']}"
																		exts="${properties['file.picture.type']}"
																		id="pro_up_${certProNumber}" multiple="true"
																		businessId="${certPro.id}"
																		typeId="${supplierDictionaryData.supplierProCert}"
																		sysKey="${sysKey}" auto="true" /> </c:if> 
																	<c:if test="${!fn:contains(proPageField,certPro.id)&&currSupplier.status==2}"> 	<u:show showId="pro_show_${certProNumber}"  delete="false" businessId="${certPro.id}" typeId="${supplierDictionaryData.supplierProCert}" sysKey="${sysKey}" /></c:if>
																	<c:if test="${currSupplier.status==-1 || empty(currSupplier.status) ||fn:contains(proPageField,certPro.id)}"> <u:show showId="pro_show_${certProNumber}" businessId="${certPro.id}" typeId="${supplierDictionaryData.supplierProCert}" sysKey="${sysKey}" /></c:if>
																	
																	</div>
																</td>
															</tr>
															<c:set var="certProNumber" value="${certProNumber + 1}" />
														</c:forEach>
													</tbody>
												</table>
												<input type="hidden" id="certProNumber"
													value=${certProNumber}>
											</div>
										</div>
									</div>
								</div>
								<!-- 物资销售型专业信息 -->
								<div class="tab-pane fades" id="sale_div">
									<h2 class="list_title">物资-销售专业信息</h2>
									<ul class="list-unstyled">
										<input type="hidden" name="supplierMatSell.id"
											value="${currSupplier.supplierMatSell.id}" />
										<input type="hidden" name="supplierMatSell.supplierId"
											value="${currSupplier.id}" />
									</ul>

									<div class="col-md-12 col-sm-12 col-xs-12 border_font mt20">
										<span class="font_line"> 资质证书信息 </span>
										<div class="col-md-12 col-sm-12 col-xs-12 p0">
											<c:choose>
                       	<c:when test="${currSupplier.status==2 }">
                         	<button class="btn btn-Invalid"  type="button" disabled="disabled">新增</button>
                         </c:when>
                         <c:otherwise>
                           <button type="button" class="btn" onclick="addCertSell()">新增</button>
                         </c:otherwise>
                       </c:choose>
											<button type="button" class="btn" onclick="delCertSell()">删除</button>
											<span class="red">${sale_cert }</span>
										</div>
										<div class="col-md-12 col-sm-12 col-xs-12 over_auto p0">
											<table id="share_table_id"
												class="table table-bordered table-condensed mt5 table_input left_table table_wrap m_table_fixed_border">
												<thead>
													<tr>
														<th class="info"><input type="checkbox"
															onchange="checkAll(this, 'cert_sell_list_tbody_id')" />
														</th>
														<th class="info">资质证书名称</th>
														<th class="info">证书编号</th>
														<th class="info">资质等级</th>
														<th class="info">发证机关或机构</th>
														<th class="info">有效期（起始时间）</th>
														<th class="info">有效期（结束时间）</th>
														<th class="info">证书状态</th>
														<th class="info w200">证书图片（可上传多张）</th>
													</tr>
												</thead>
												<tbody id="cert_sell_list_tbody_id">
													<c:set var="certSaleNumber" value="0" />
													<c:forEach
														items="${currSupplier.supplierMatSell.listSupplierCertSells}"
														var="certSell" varStatus="vs">
														<tr
															<c:if test="${fn:contains(sellPageField,certSell.id)}"> onmouseover="errorMsg(this,'${certSell.id}','mat_sell_page')"</c:if>>
															<td class="tc"
																<c:if test="${fn:contains(sellPageField,certSell.id)}">style="border: 1px solid red;" </c:if>>
																<input type="checkbox" value="${certSell.id}"
																class="border0" /> <input type="hidden"
																required="required"
																name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].id"
																value="${certSell.id}" class="border0"></td>
															<td class="tc"
																<c:if test="${fn:contains(sellPageField,certSell.id)}">style="border: 1px solid red;" </c:if>>
																<div class="w200 fl">
																	<input type="text" name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].name"
																		<c:if test="${!fn:contains(sellPageField,certSell.id)&&currSupplier.status==2}">readonly="readonly"</c:if>  value="${certSell.name}" class="border0" />
															  </div>
															</td> 
															<td class="tc"
																<c:if test="${fn:contains(sellPageField,certSell.id)}">style="border: 1px solid red;" </c:if>>
																<div class="w150 fl">
																	<input type="text" maxlength="150" <c:if test="${!fn:contains(sellPageField,certSell.id)&&currSupplier.status==2 }">readonly="readonly"</c:if>
																		name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].code"
																		value="${certSell.code}" class="border0" />
																</div>
															</td>
															<td class="tc"
																<c:if test="${fn:contains(sellPageField,certSell.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" maxlength="30" <c:if test="${!fn:contains(sellPageField,certSell.id)&&currSupplier.status==2}">readonly="readonly"</c:if>
																name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].levelCert"
																value="${certSell.levelCert}" class="border0" />
															</td>
															<td class="tc"
																<c:if test="${fn:contains(sellPageField,certSell.id)}">style="border: 1px solid red;" </c:if>>
																<div class="w200 fl">
																  <input type="text" maxlength="30" <c:if test="${!fn:contains(sellPageField,certSell.id)&&currSupplier.status==2}">readonly="readonly"</c:if>
																	name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].licenceAuthorith"
																	value="${certSell.licenceAuthorith}" class="border0" />
																</div>
															</td>
															<td class="tc"
																<c:if test="${fn:contains(sellPageField,certSell.id)}">style="border: 1px solid red;" </c:if>>
																<input type="text"
																<c:if test="${(fn:contains(sellPageField,certSell.id)&&currSupplier.status==2 ) || currSupplier.status==-1 || empty(currSupplier.status)}">onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',readOnly:true})"</c:if> 
																name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].expStartDate"
																value="<fmt:formatDate value="${certSell.expStartDate}" pattern="yyyy-MM-dd "/>"
																class="border0" /></td>
															<td class="tc"
																<c:if test="${fn:contains(sellPageField,certSell.id)}">style="border: 1px solid red;" </c:if>>
																<input type="text"
																<c:if test="${(fn:contains(sellPageField,certSell.id)&&currSupplier.status==2 ) || currSupplier.status==-1 || empty(currSupplier.status)}">onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d',readOnly:true})"</c:if> 
																name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].expEndDate"
																value="<fmt:formatDate value="${certSell.expEndDate}" pattern="yyyy-MM-dd "/>"
																class="border0" /></td>
															<td class="tc"
																<c:if test="${fn:contains(sellPageField,certSell.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" maxlength="15" <c:if test="${!fn:contains(sellPageField,certSell.id)&&currSupplier.status==2}">readonly="readonly"</c:if>
																name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].mot"
																value="${certSell.mot}" class="border0" />
															</td>
															<td class="tc"
																<c:if test="${fn:contains(sellPageField,certSell.id)}">style="border: 1px solid red;" </c:if>>
																<div class="w200 fl">
																	<c:if test="${(fn:contains(sellPageField,certSell.id)&&currSupplier.status==2) || currSupplier.status==-1 || empty(currSupplier.status)}">  	<u:upload
																		singleFileSize="${properties['file.picture.upload.singleFileSize']}"
																		exts="${properties['file.picture.type']}"
																		id="sale_up_${certSaleNumber}" multiple="true"
																		businessId="${certSell.id}"
																		typeId="${supplierDictionaryData.supplierSellCert}"
																		sysKey="${sysKey}" auto="true" /></c:if>
																<c:if test="${!fn:contains(sellPageField,certSell.id)&&currSupplier.status==2}"> 	<u:show showId="sale_show_${certSaleNumber}" delete="false"    businessId="${certSell.id}" typeId="${supplierDictionaryData.supplierSellCert}" sysKey="${sysKey}" /> </c:if>
																<c:if test="${currSupplier.status==-1 || empty(currSupplier.status) || fn:contains(sellPageField,certSell.id)}"> <u:show showId="sale_show_${certSaleNumber}"     businessId="${certSell.id}" typeId="${supplierDictionaryData.supplierSellCert}" sysKey="${sysKey}" /> </c:if>
																
																</div></td>
														</tr>
														<c:set var="certSaleNumber" value="${certSaleNumber + 1}" />
													</c:forEach>
												</tbody>
											</table>
											<input type="hidden" id="certSaleNumber"
												value=${certSaleNumber}>
										</div>
									</div>
								</div>

								<!-- 工程专业信息 -->
								<div class="tab-pane fades" id="project_div">
									<h2 class="list_title">工程专业信息</h2>

									<!--   <div class="col-md-5 title"><span class="star_red fl">*</span>工程专业信息：</div> -->
									<input type="hidden" name="supplierMatEng.id"
										value="${currSupplier.supplierMatEng.id }" /> <input
										type="hidden" name="supplierMatEng.supplierId"
										value="${currSupplier.id}" />

									<fieldset
										class="col-md-12 col-sm-12 col-xs-12 border_font mt10">
										<legend> 保密工程业绩 </legend>
										<div class="red" style="font-size: 16px;">注：合同一律不得上传涉军涉密合同和对账单，仅有涉军涉密合同和对账单的，上传情况说明（<a href="${pageContext.request.contextPath}/supplier/download_report.html"> 下载模板</a>），将合同电子版以光盘送采购机构。</div>
										
										<ul class="list-unstyled overflow_h">
											<li class="col-md-3 col-sm-6 col-xs-12 pl10"><span
												class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i
													class="red">*</i> 是否有国家或军队保密工程业绩</span>
													
												<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
													<select name="supplierMatEng.isHavingConAchi"
														id="isHavingConAchi" onchange="onchangeConAchi(this)"
														<c:if test="${fn:contains(engPageField,'isHavingConAchi')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'isHavingConAchi')"</c:if>>
														<option value="0"
															<c:if test="${currSupplier.supplierMatEng.isHavingConAchi == '0'}">selected</c:if>>无</option>
														<option value="1"
															<c:if test="${currSupplier.supplierMatEng.isHavingConAchi == '1'}">selected</c:if>>有</option>
													</select>
												</div></li>
											<li class="col-md-3 col-sm-6 col-xs-12"><span
												class="col-md-12 col-xs-12 col-sm-12 padding-left-5 white">操作</span>
												<div class="col-md-12 col-xs-12 col-sm-12 p0 mb25 h30">

												</div></li>
											<li class="col-md-3 col-sm-6 col-xs-12"><span
												class="col-md-12 col-xs-12 col-sm-12 padding-left-5 white">操作</span>
												<div class="col-md-12 col-xs-12 col-sm-12 p0 mb25 h30">

												</div></li>
											<li class="col-md-3 col-sm-6 col-xs-12"><span
												class="col-md-12 col-xs-12 col-sm-12 padding-left-5 white">操作</span>
												<div class="col-md-12 col-xs-12 col-sm-12 p0 mb25 h30">

												</div></li>
												<div class="clear"></div>
												<c:if test="${currSupplier.supplierMatEng.isHavingConAchi eq '1'}">
													<div id="conAchiDiv">
														<li class="col-md-3 col-sm-6 col-xs-12 pl10">
															<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">
																<i class="red">*</i>
																承包合同主要页及保密协议：
															</span>
															<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0"
																<c:if test="${fn:contains(engPageField,'supplierConAch')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'supplierConAch')"</c:if>>
																<c:if test="${(fn:contains(engPageField,'supplierConAch')&&currSupplier.status==2) || currSupplier.status==-1 || empty(currSupplier.status)}">
																  	<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}"
																		  businessId="${currSupplier.id}" sysKey="${sysKey}"
																		  typeId="${supplierDictionaryData.supplierConAch}"
																		  exts="${properties['file.picture.type']}" id="conAch_up"
																		  multiple="true" auto="true" maxcount="5"/>
																  </c:if>
																<c:if test="${!fn:contains(engPageField,'supplierConAch')&&currSupplier.status==2}">
																  <u:show showId="conAch_show" delete="false"
																		businessId="${currSupplier.id}" sysKey="${sysKey}"
																		typeId="${supplierDictionaryData.supplierConAch}"/>
															 	</c:if>
																<c:if test="${currSupplier.status==-1 || empty(currSupplier.status) || fn:contains(engPageField,'supplierConAch')}">
																  <u:show showId="conAch_show"
																		businessId="${currSupplier.id}" sysKey="${sysKey}"
																		typeId="${supplierDictionaryData.supplierConAch}"/>
																</c:if>
																<div class="cue">${err_conAch}</div>
															</div>
														</li>
														<li class="col-md-12 col-xs-12 col-sm-12 mb25"><span
															class="col-md-12 col-xs-12 col-sm-12 padding-left-5">
																<i class="red">* </i>国家或军队保密工程业绩：</span>
															<div class="col-md-12 col-xs-12 col-sm-12 p0">
																<textarea class="col-md-12 col-xs-12 col-sm-12 h80"
																	name="supplierMatEng.confidentialAchievement" id="conAchi" maxlength="1000" 
																	onkeyup="checkCharLimit('conAchi','limit_char_conAchi',1000);if(value.length==1000){layer.msg('字数过多，不可超过1000字！')}" 
																	<c:if test="${currSupplier.supplierMatEng.isHavingConAchi == '1'}">required="required"</c:if>
																	<c:if test="${!fn:contains(engPageField,'confidentialAchievement')&&currSupplier.status==2}">readonly="readonly"</c:if>
																	<c:if test="${fn:contains(engPageField,'confidentialAchievement')}">style="border: 1px solid red;" onmouseover="errorMsg(this,'confidentialAchievement','mat_eng_page')"</c:if>>${currSupplier.supplierMatEng.confidentialAchievement}</textarea>
																<span class="sm_tip fr">还可输入 <span id="limit_char_conAchi">1000</span> 个字</span>
																<div class="cue">
																	<span class="red">${secret }</span>
																	<sf:errors path="supplierMatEng.confidentialAchievement" />
																</div>
															</div></li>
													</div>
												</c:if>
										</ul>
									</fieldset>

									<fieldset
										class="col-md-12 col-sm-12 col-xs-12 border_font mt10">
										<span class="font_line"> <font class="red">*</font> 承揽业务范围：省级行政区对应合同主要页 （体现甲乙双方盖章及工程名称、地点的相关页）</span>
										<div class="red" style="font-size: 16px;">注：合同一律不得上传涉军涉密合同和对账单，仅有涉军涉密合同和对账单的，上传情况说明（<a href="${pageContext.request.contextPath}/supplier/download_report.html"> 下载模板</a>），将合同电子版以光盘送采购机构。</div>
										
										<div class="ml20">
											省、直辖市：
										    <select multiple="multiple" size="5" id="areaSelect" onchange="disAreaFile(this)"  title="按住CTRL+鼠标左键可多选和取消选择">
										    	<c:forEach items="${rootArea}" var="area" varStatus="st">
									    	  	<option value="${area.id}" <c:if test="${fn:contains(currSupplier.supplierMatEng.businessScope,area.id)}">selected="selected"</c:if>>${area.name}</option>
										    	</c:forEach>
										    </select>
										    <div class="cue_province">${province}</div>
										</div>
										<ul class="list-unstyled overflow_h">
											<input type="hidden" name="supplierMatEng.businessScope" id="businessScope" value="${currSupplier.supplierMatEng.businessScope}"/>
											<%-- <c:forEach items="${rootArea}" var="ra" varStatus="st">
												<c:set var="flag" value="0"/>
												<c:forEach items="${currSupplier.supplierMatEng.businessScopeAreas}" var="area">
													<c:if test="${ra.id == area.id}">
														<c:set var="flag" value="1"/>
														<li class="col-md-3 col-sm-6 col-xs-12 pl10" id="area_${area.id}" >
															<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">${area.name}</span>
															<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0"
																<c:if test="${fn:contains(engPageField,area.name)}">style="border: 1px solid red;" onmouseover="errorMsg(this,'${area.name}','mat_eng_page')"</c:if>>
																<c:if test="${(fn:contains(engPageField,area.name)&&currSupplier.status==2) || currSupplier.status==-1 || empty(currSupplier.status)}">  	<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" maxcount="5" businessId="${currSupplier.id}_${area.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierProContract}" exts="${properties['file.picture.type']}" id="conAch_up_${st.index+1}" multiple="true" auto="true" /></c:if>
																<c:if test="${!fn:contains(engPageField,area.name)&&currSupplier.status==2}">  <u:show showId="area_show_${st.index+1}" delete="false" businessId="${currSupplier.id}_${area.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierProContract}" /></c:if>
																<c:if test="${currSupplier.status==-1 || empty(currSupplier.status) || fn:contains(engPageField,area.name)}">  <u:show showId="area_show_${st.index+1}" businessId="${currSupplier.id}_${area.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierProContract}" /></c:if>
																<div class="cue">${area.errInfo}</div>
															</div>
														</li>
													</c:if>
												</c:forEach>
												<c:if test="${flag == 0}">
													<li class="col-md-3 col-sm-6 col-xs-12 pl10" id="area_${ra.id}" style="display: none;">
														<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">${ra.name}</span>
														<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
															<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" maxcount="5" businessId="${currSupplier.id}_${ra.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierProContract}" exts="${properties['file.picture.type']}" id="conAch_up_${st.index+1}" multiple="true" auto="true" />
															<u:show showId="area_show_${st.index+1}" businessId="${currSupplier.id}_${ra.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierProContract}" />
														</div>
													</li>
												</c:if>
											</c:forEach> --%>
											<c:forEach items="${rootArea}" var="area" varStatus="st">
												<li class="col-md-3 col-sm-6 col-xs-12 pl10" id="area_${area.id}" >
													<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">${area.name}</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0"
														<c:if test="${fn:contains(engPageField,area.name)}">style="border: 1px solid red;" onmouseover="errorMsg(this,'${area.name}','mat_eng_page')"</c:if>>
														<c:if test="${(fn:contains(engPageField,area.name)&&currSupplier.status==2) || currSupplier.status==-1 || empty(currSupplier.status)}">  	<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" maxcount="5" businessId="${currSupplier.id}_${area.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierProContract}" exts="${properties['file.picture.type']}" id="conAch_up_${st.index+1}" multiple="true" auto="true" /></c:if>
														<c:if test="${!fn:contains(engPageField,area.name)&&currSupplier.status==2}">  <u:show showId="area_show_${st.index+1}" delete="false" businessId="${currSupplier.id}_${area.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierProContract}" /></c:if>
														<c:if test="${currSupplier.status==-1 || empty(currSupplier.status) || fn:contains(engPageField,area.name)}">  <u:show showId="area_show_${st.index+1}" businessId="${currSupplier.id}_${area.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierProContract}" /></c:if>
														<div class="cue">${area.errInfo}</div>
													</div>
												</li>
											</c:forEach>
										</ul>
									</fieldset>
									
									<div class="col-md-12 col-sm-12 col-xs-12 border_font mt20">
										<span class="font_line"> 资质证书信息 </span>
										<div class="col-md-12 col-xs-12 col-sm-12 p0">
											<%-- <c:choose>
                       	<c:when test="${currSupplier.status==2 }">
                         	<button class="btn btn-Invalid"  type="button" disabled="disabled">新增</button>
                         </c:when>
                         <c:otherwise>
                           <button type="button" class="btn" onclick="addEngQua()">新增</button>
                         </c:otherwise>
                       </c:choose> --%>
                      <button type="button" class="btn" onclick="addEngQua()">新增</button>
											<button type="button" class="btn" onclick="delEngQua()">删除</button>
											<span class="red">${eng_qua }</span>
										</div>
										<div class="col-md-12 col-xs-12 col-sm-12 over_auto p0">
											<table id="share_table_id"
												class="table table-bordered table-condensed mt5 table_wrap left_table table_input m_table_fixed_border">
												<thead>
													<tr>
														<th class="info"><input type="checkbox"
															onchange="checkAll(this, 'eng_qua_list_tbody_id')" />
														</th>
														<th class="info">资质证书名称</th>
														<th class="info">证书编号</th>
														<th class="info">资质等级</th>
														<th class="info">发证机关或机构</th>
														<th class="info">有效期（起始时间）</th>
														<th class="info">有效期（结束时间）</th>
														<th class="info">证书状态</th>
														<th class="info w200">证书图片（可上传多张）</th>
													</tr>
												</thead>
												<tbody id="eng_qua_list_tbody_id">
													<c:set var="engQuaNumber" value="0"></c:set>
													<c:forEach
														items="${currSupplier.supplierMatEng.listSupplierEngQuas}"
														var="engQua" varStatus="vs">
														<tr
															<c:if test="${fn:contains(engPageField,engQua.id)}"> onmouseover="errorMsg(this,'${engQua.id}','mat_eng_page')"</c:if>>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,engQua.id)}">style="border: 1px solid red;" </c:if>>
																<input type="checkbox" class="border0" 
																	value="${engQua.id}" /> <input type="hidden"
																	<c:if test="${fn:contains(engPageField,engQua.id)}">readonly='readonly' </c:if> 
																	name="supplierMatEng.listSupplierEngQuas[${engQuaNumber}].id"
																	value="${engQua.id}"></td>
															<!-- 工程资质证书名称 -->
															<td class="tc"
																<c:if test="${fn:contains(engPageField,engQua.id)}">style="border: 1px solid red;" </c:if>>
																<div class="w200">
																 	<input type="text" class="border0"   maxlength="30" 
																 		<c:if test="${!fn:contains(engPageField,engQua.id)&&currSupplier.status==2}">readonly='readonly' </c:if>
																		name="supplierMatEng.listSupplierEngQuas[${engQuaNumber}].name"
																		value="${engQua.name}" />
																 </div>
															</td>
															<!--工程 证书编号 -->
															<td class="tc"
																<c:if test="${fn:contains(engPageField,engQua.id)}">style="border: 1px solid red;" </c:if>>
																<div class="w150">
															 		<input type="text" class="border0" maxlength="150"
																 		<c:if test="${!fn:contains(engPageField,engQua.id)&&currSupplier.status==2}">readonly='readonly' </c:if>
																		name="supplierMatEng.listSupplierEngQuas[${engQuaNumber}].code"
																		value="${engQua.code}" />
																</div>
															</td>
															
															<!--工程 资质等级 -->	
															<td class="tc"
																<c:if test="${fn:contains(engPageField,engQua.id)}">style="border: 1px solid red;" </c:if>><input maxlength="30" 
																type="text" class="border0" <c:if test="${!fn:contains(engPageField,engQua.id)&&currSupplier.status==2}">readonly='readonly' </c:if>
																name="supplierMatEng.listSupplierEngQuas[${engQuaNumber}].levelCert"
																value="${engQua.levelCert}" />
															</td>
														
															<!--工程 发证机关或机构 -->	
															<td class="tc"
																<c:if test="${fn:contains(engPageField,engQua.id)}">style="border: 1px solid red;" </c:if>>
																<div class="w200">
															 		<input type="text" class="border0" maxlength="60" 
															    <c:if test="${!fn:contains(engPageField,engQua.id)&&currSupplier.status==2}">readonly='readonly' </c:if>
																	name="supplierMatEng.listSupplierEngQuas[${engQuaNumber}].licenceAuthorith"
																	value="${engQua.licenceAuthorith}" />
																</div>
															</td>
														
															<%-- <td class="tc"
																<c:if test="${fn:contains(engPageField,engQua.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" class="border0"
																<c:if test="${(fn:contains(engPageField,engQua.id)&&currSupplier.status==2) ||currSupplier.status==-1 || empty(currSupplier.status)}">onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',readOnly:true})" </c:if>
																name="supplierMatEng.listSupplierEngQuas[${engQuaNumber}].expStartDate"
																value="<fmt:formatDate value="${engQua.expStartDate}" pattern="yyyy-MM-dd "/>" />
															</td>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,engQua.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" class="border0"
																<c:if test="${(fn:contains(engPageField,engQua.id)&&currSupplier.status==2) ||currSupplier.status==-1 || empty(currSupplier.status)}">onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d',readOnly:true})" </c:if>
																name="supplierMatEng.listSupplierEngQuas[${engQuaNumber}].expEndDate"
																value="<fmt:formatDate value="${engQua.expEndDate}" pattern="yyyy-MM-dd "/>" />
															</td> --%>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,engQua.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" class="border0"
																onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',readOnly:true})"
																name="supplierMatEng.listSupplierEngQuas[${engQuaNumber}].expStartDate"
																value="<fmt:formatDate value="${engQua.expStartDate}" pattern="yyyy-MM-dd "/>" />
															</td>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,engQua.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" class="border0"
																readonly="readonly" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d',readOnly:true})"
																name="supplierMatEng.listSupplierEngQuas[${engQuaNumber}].expEndDate"
																value="<fmt:formatDate value="${engQua.expEndDate}" pattern="yyyy-MM-dd "/>" />
															</td>
															<!-- 工程 证书状态 -->	
															<td class="tc"
																<c:if test="${fn:contains(engPageField,engQua.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" class="border0" maxlength="15" <c:if test="${!fn:contains(engPageField,engQua.id)&&currSupplier.status==2}">readonly='readonly' </c:if>
																name="supplierMatEng.listSupplierEngQuas[${engQuaNumber}].mot"
																value="${engQua.mot}" />
															</td>
															<!-- 图片 -->
															<td class="tc"
																<c:if test="${fn:contains(engPageField,engQua.id)}">style="border: 1px solid red;" </c:if>>
																<div class="fl w200">
																<%-- <c:if test="${(fn:contains(engPageField,engQua.id)&&currSupplier.status==2 ) || currSupplier.status==-1 || empty(currSupplier.status)}">	 <u:upload
																	singleFileSize="${properties['file.picture.upload.singleFileSize']}"
																	exts="${properties['file.picture.type']}"
																	id="eng_qua_up_${engQuaNumber}" multiple="true"
																	businessId="${engQua.id}"
																	typeId="${supplierDictionaryData.supplierEngQua}"
																	sysKey="${sysKey}" auto="true" /></c:if> 
																	<c:if test="${!fn:contains(engPageField,engQua.id)&&currSupplier.status==2 }">	 <u:show showId="eng_qua_show_${engQuaNumber}" delete="false"  businessId="${engQua.id}" 	typeId="${supplierDictionaryData.supplierEngQua}" sysKey="${sysKey}" /> </c:if>
																	<c:if test="${currSupplier.status==-1 || empty(currSupplier.status)||fn:contains(engPageField,engQua.id)}">	 <u:show showId="eng_qua_show_${engQuaNumber}"   businessId="${engQua.id}" 	typeId="${supplierDictionaryData.supplierEngQua}" sysKey="${sysKey}" /> </c:if> --%>
																	<u:upload
																	singleFileSize="${properties['file.picture.upload.singleFileSize']}"
																	exts="${properties['file.picture.type']}"
																	id="eng_qua_up_${engQuaNumber}" multiple="true"
																	businessId="${engQua.id}"
																	typeId="${supplierDictionaryData.supplierEngQua}"
																	sysKey="${sysKey}" auto="true" />
																	<u:show showId="eng_qua_show_${engQuaNumber}" businessId="${engQua.id}" typeId="${supplierDictionaryData.supplierEngQua}" sysKey="${sysKey}" />
																</div>
															</td>
														</tr>
														<c:set var="engQuaNumber" value="${engQuaNumber + 1}"></c:set>
													</c:forEach>
												</tbody>
											</table>
											<input type="hidden" id="engQuaNumber"
												value="${engQuaNumber}">
										</div>
									</div>

									<div class="col-md-12 col-sm-12 col-xs-12 border_font mt20">
										<span class="font_line">取得注册资质的人员信息 </span>
										<div class="fl col-md-12 col-xs-12 col-sm-12 p0">
											<c:choose>
                       	<c:when test="${currSupplier.status==2 }">
                         	<button class="btn btn-Invalid"  type="button" disabled="disabled">新增</button>
                        </c:when>
                        <c:otherwise>
                          <button type="button" class="btn" onclick="addRegPerson()">新增</button>
                        </c:otherwise>
                      </c:choose>
										 	<button type="button" class="btn" onclick="delRegPerson()">删除</button>
											<span class="red">${eng_persons }</span>
										</div>
										<div
											class="col-md-12 col-xs-12 col-sm-12 p0 over_auto clear">
											<table
												class="table table-bordered table-condensed mt5 table_input left_table table_wrap m_table_fixed_border">
												<thead>
													<tr>
														<th class="info"><input type="checkbox"
															onchange="checkAll(this, 'reg_person_list_tbody_id')" />
														</th>
														<th class="info">注册资格名称</th>
														<th class="info">注册人姓名</th>
													</tr>
												</thead>
												<tbody id="reg_person_list_tbody_id">
													<c:set var="certPersonNumber" value="0" />
													<c:forEach
														items="${currSupplier.supplierMatEng.listSupplierRegPersons}"
														var="regPerson" varStatus="vs">
														<tr
															<c:if test="${fn:contains(engPageField,regPerson.id)}"> onmouseover="errorMsg(this,'${regPerson.id}','mat_eng_page')"</c:if>>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,regPerson.id)}">style="border: 1px solid red;" </c:if>>
																<input type="checkbox" class="border0"
																value="${regPerson.id}" /> <input type="hidden"
																required="required"
																name="supplierMatEng.listSupplierRegPersons[${certPersonNumber}].id"
																value="${regPerson.id}"></td>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,regPerson.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" class="border0"   <c:if test="${!fn:contains(engPageField,regPerson.id)&&currSupplier.status==2}"> readonly='readonly'</c:if>
																name="supplierMatEng.listSupplierRegPersons[${certPersonNumber}].regType"
																value="${regPerson.regType}" />
															</td>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,regPerson.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" class="border0" <c:if test="${!fn:contains(engPageField,regPerson.id)&&currSupplier.status==2}"> readonly='readonly'</c:if>
																name="supplierMatEng.listSupplierRegPersons[${certPersonNumber}].regNumber"
																value="${regPerson.regNumber}" />
															</td>
														</tr>
														<c:set var="certPersonNumber"
															value="${certPersonNumber + 1}" />
													</c:forEach>
												</tbody>
											</table>
											<input type="hidden" id="certPersonNumber"
												value="${certPersonNumber}">
										</div>
									</div>

									<div class="col-md-12 col-sm-12 col-xs-12 border_font mt20">
										<span class="font_line"><font class="red">*</font> 供应商资质（认证）证书信息</span>
										<div class="fl col-md-12 col-xs-12 col-sm-12 p0">
											<c:choose>
                       	<c:when test="${currSupplier.status==2 }">
                         	<button class="btn btn-Invalid"  type="button" disabled="disabled">新增</button>
                         </c:when>
                         <c:otherwise>
                           <button type="button" class="btn" onclick="addCertEng()">新增</button>
                         </c:otherwise>
                       </c:choose>
											<button type="button" class="btn" onclick="delCertEng()">删除</button>
											<span class="red">${eng_cert}</span>
										</div>
										<div
											class="clear over_auto col-md-12 col-xs-12 col-sm-12 p0">
											<table
												class="table table-bordered table-condensed mt5 table_input left_table m_table_fixed_border">
												<thead>
													<tr class="space_nowrap">
														<th class="info"><input type="checkbox"
															onchange="checkAll(this, 'cert_eng_list_tbody_id')" />
														</th>
														<th class="info">证书名称</th>
														<th class="info">证书编号</th>
														<th class="info">资质等级</th>
														<th class="info">发证机关或机构</th>
														<th class="info">
															<div class="w120">发证日期</div></th>
														<th class="info minw100">证书有效期截止日期</th>
														<th class="info">证书状态</th>
													<!-- 	<th class="info w200">证书图片（可上传多张）</th> -->
													</tr>
												</thead>
												<tbody id="cert_eng_list_tbody_id">
													<c:set var="certEngNumber" value="0" />
													<c:forEach
														items="${currSupplier.supplierMatEng.listSupplierCertEngs}"
														var="certEng" varStatus="vs">
														<tr
															<c:if test="${fn:contains(engPageField,certEng.id)}"> onmouseover="errorMsg(this,'${certEng.id}','mat_eng_page')"</c:if>>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,certEng.id)}">style="border: 1px solid red;" </c:if>>
																<input type="checkbox" class="border0"
																value="${certEng.id}" /> <input type="hidden" <c:if test="${!fn:contains(engPageField,certEng.id)&&currSupplier.status==2}"> readonly='readonly' </c:if>
																name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].id"
																value="${certEng.id}"></td>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,certEng.id)}">style="border: 1px solid red;" </c:if>>
																<div class="w200">
																	<input  <c:if test="${!fn:contains(engPageField,certEng.id)&&currSupplier.status==2}"> readonly='readonly' </c:if>
																type="text" required="required" class="border0" maxlength="60"
																name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].certType"
																value="${certEng.certType}" />
																</div>
															</td>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,certEng.id)}">style="border: 1px solid red;" </c:if>>
																<div class="w150">
																	<input <c:if test="${!fn:contains(engPageField,certEng.id)&&currSupplier.status==2}"> readonly='readonly' </c:if>
																class="w120 border0" required="required" type="text" maxlength="150"
																name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].certCode"
																value="${certEng.certCode}" />
																</div>
															</td>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,certEng.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" required="required" class="border0"   <c:if test="${!fn:contains(engPageField,certEng.id)&&currSupplier.status==2}"> readonly='readonly' </c:if>
																name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].certMaxLevel"
																value="${certEng.certMaxLevel}" />
															</td>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,certEng.id)}">style="border: 1px solid red;" </c:if>>
																 <div class="w200">
																 	<input <c:if test="${!fn:contains(engPageField,certEng.id)&&currSupplier.status==2}"> readonly='readonly' </c:if>
																type="text" required="required" class="border0"
																name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].licenceAuthorith"
																value="${certEng.licenceAuthorith}" />
															     </div>
															</td>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,certEng.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" required="required" class="border0"
																<c:if test="${(fn:contains(engPageField,certEng.id)&&currSupplier.status==2 ) ||currSupplier.status==-1 || empty(currSupplier.status)}">onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',readOnly:true})"</c:if>
																name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].expStartDate"
																value="<fmt:formatDate value="${certEng.expStartDate}" pattern="yyyy-MM-dd"/>" />
															</td>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,certEng.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" required="required" class="border0"
																<c:if test="${(fn:contains(engPageField,certEng.id)&&currSupplier.status==2) ||currSupplier.status==-1 || empty(currSupplier.status)}">onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d',readOnly:true})"</c:if>
																name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].expEndDate"
																value="<fmt:formatDate value="${certEng.expEndDate}" pattern="yyyy-MM-dd"/>"
																pattern="yyyy-MM-dd" />
															</td>
															<td class="tc" <c:if test="${fn:contains(engPageField,certEng.id)}">style="border: 1px solid red;" </c:if>>
																<input type="text" required="required" class="border0" maxlength="15" name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].certStatus" value="${certEng.certStatus}" <c:if test="${!fn:contains(engPageField,certEng.id)&&currSupplier.status==2}"> readonly='readonly' </c:if>  />
															</td>
															<%-- <td class="tc">
																<div class="w200 fl">
																	<u:upload
																		singleFileSize="${properties['file.picture.upload.singleFileSize']}"
																		exts="${properties['file.picture.type']}"
																		id="eng_up_${certEngNumber}" multiple="true"
																		businessId="${certEng.id}"
																		typeId="${supplierDictionaryData.supplierEngCert}"
																		sysKey="${sysKey}" auto="true" />
																	<u:show showId="eng_show_${certEngNumber}"
																		businessId="${certEng.id}"
																		typeId="${supplierDictionaryData.supplierEngCert}"
																		sysKey="${sysKey}" />
																</div></td> --%>
														</tr>
														<c:set var="certEngNumber" value="${certEngNumber + 1}" />
													</c:forEach>
												</tbody>
											</table>
											<input type="hidden" id="certEngNumber"
												value="${certEngNumber}">
										</div>
									</div>

									<div class="col-md-12 col-sm-12 col-xs-12 border_font mt20">
										<span class="font_line"><font class="red">*</font> 供应商资质证书详细信息 </span>
										<div class="col-md-12 col-md-12 col-xs-12 col-sm-12 p0">
											<c:choose>
                       	<c:when test="${currSupplier.status==2 }">
                         	<button class="btn btn-Invalid"  type="button" disabled="disabled">新增</button>
                         </c:when>
                         <c:otherwise>
                           <button type="button" class="btn" onclick="addAptitute()">新增</button>
                         </c:otherwise>
                       </c:choose>
											<button type="button" class="btn" onclick="delAptitute()">删除</button>
											<span class="red">${eng_aptitutes }</span>
										</div>
										<div class="over_auto clear col-md-12 col-xs-12 col-sm-12 p0">
											<table
												class="table table-bordered table-condensed mt5 table_input left_table m_table_fixed_border">
												<thead>
													<tr class="space_nowrap">
														<th class="info"><input type="checkbox"
															onchange="checkAll(this, 'aptitute_list_tbody_id')" />
														</th>
														<th class="info w150">证书名称</th>
														<th class="info w150">证书编号</th>
														<th class="info w200">资质类型</th>
														<th class="info">资质序列</th>
														<th class="info">专业类别</th>
														<th class="info w200">资质等级</th>
														<th class="info">是否主项资质</th>
														<th>证书图片（可上传多张）</th>
													</tr>
												</thead>
												<tbody id="aptitute_list_tbody_id">
													<c:set var="certAptNumber" value="0" />
													<c:forEach
														items="${currSupplier.supplierMatEng.listSupplierAptitutes}"
														var="aptitute" varStatus="vs">
														<tr <c:if test="${fn:contains(engPageField,aptitute.id)}"> onmouseover="errorMsg(this,'${aptitute.id}','mat_eng_page')"</c:if>>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,aptitute.id)}">style="border: 1px solid red;" </c:if>>
																<input type="checkbox" class="border0"
																value="${aptitute.id}" /> <input type="hidden"
																name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].id"
																value="${aptitute.id}"></td>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,aptitute.id)}">style="border: 1px solid red;" </c:if>>
																 <div class="w200">
																 	<input <c:if test="${!fn:contains(engPageField,aptitute.id)&&currSupplier.status==2}">readonly='readonly' </c:if>
																type="text" required="required" class="border0" maxlength="30"
																name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].certName"
																value="${aptitute.certName}" />
																  </div>
															</td>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,aptitute.id)}">style="border: 1px solid red;" </c:if>>
																  <div class="w150"><input <c:if test="${!fn:contains(engPageField,aptitute.id)&&currSupplier.status==2}">readonly='readonly' </c:if>
																type="text" required="required" class="border0" maxlength="150"
																name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].certCode"
																value="${aptitute.certCode}" />
																</div>
															</td>
															<td class="tc" <c:if test="${fn:contains(engPageField,aptitute.id)}">style="border: 1px solid red;" </c:if>>
																<select title="cnjewfn" id="certType_${certAptNumber}" class="w100p border0 certTypeSelect" name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].certType" style="width:200px;border: none;">
															    <c:set var="tempForShowOption" value="go" scope="page"/>
															    <option value="-1" selected="selected">请选择</option>
															    <c:forEach items="${quaList}" var="qua">
																		<option value="${qua.id}" <c:if test="${aptitute.certType eq qua.id}">selected</c:if>>${qua.name}</option>
																		<c:if test="${aptitute.certType eq qua.id}">
																			<c:set var="tempForShowOption" value="notgo"/>
																		</c:if>
																	</c:forEach>
																	<c:if test="${tempForShowOption eq 'go' }">
																		<option value="${aptitute.certType}" selected="selected">${aptitute.certType}</option>
																	</c:if>
																</select>
															</td>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,aptitute.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" required="required" class="border0" maxlength="80" <c:if test="${!fn:contains(engPageField,aptitute.id)&&currSupplier.status==2}">readonly='readonly' </c:if>
																name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].aptituteSequence"
																value="${aptitute.aptituteSequence}" />
															</td>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,aptitute.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" required="required" class="border0" maxlength="100" <c:if test="${!fn:contains(engPageField,aptitute.id)&&currSupplier.status==2}">readonly='readonly' </c:if>
																name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].professType"
																value="${aptitute.professType}" />
															</td>
															<td class="tc" <c:if test="${fn:contains(engPageField,aptitute.id)}">style="border: 1px solid red;" </c:if>>
																<!-- 
																<select name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].aptituteLevel" class="w100p border0"></select>
																 -->
																<select id="certGrade_${certAptNumber}" title="cnjewfnGrade" name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].aptituteLevel" class="w100p border0" style="width:200px;border: none;">
                                  <%-- <c:if test="${tempForShowOption eq 'go' }">
																		<option selected="selected">${aptitute.aptituteLevel}</option>
																	</c:if> --%>
																	<%-- <option selected="selected">${aptitute.aptituteLevel}</option> --%>
																	<%-- <c:set var="tempForShowOption" value="notgo"/> --%>
																</select>
																<input type="hidden" id="certLevel_${certAptNumber}" value="${aptitute.aptituteLevel}">
																<script type="text/javascript">
																	function initCombo(){
																		var currSupplierSt = '${currSupplier.status}';
																		var index = "${certAptNumber}";
																		
																		var obj_type = $("#certType_"+index);
																		var options_type = {
																			panelHeight : 240,
																			onSelect : function(record) {
																				getAptLevelSelect(record);
																			},
																			onChange : function() {
																				$("#certLevel_"+index).val("");
																				getAptLevel(obj_type, true);
																			},
																		};
																		if(currSupplierSt == '2'){
																			options_type.disabled = true;
																			//$(this).parent("td").css("border") == '1px solid rgb(255, 0, 0)'
																			if(obj_type.parent("td").attr("style") == 'border: 1px solid red;'){
																				options_type.disabled = false;
																			}
																		}
																		obj_type.combobox(options_type);
																		
																		var obj_grade = $("#certGrade_"+index);
																		var options_grade = {
																			onChange : function() {
	                                      //console.log($obj.combobox("getText"));
	                                      //tempSave();
	                                    },
																		};
																		if(currSupplierSt == '2'){
																			options_grade.disabled = true;
																			//$(this).parent("td").css("border") == '1px solid rgb(255, 0, 0)'
																			if(obj_grade.parent("td").attr("style") == 'border: 1px solid red;'){
																				options_grade.disabled = false;
																			}
																		}
																		obj_grade.combobox(options_grade);
																	}
																	
																	initCombo();
																</script>
															</td>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,aptitute.id)}">style="border: 1px solid red;" </c:if>>
																<select
																name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].isMajorFund"
																class="w100p border0" <c:if test="${!fn:contains(engPageField,aptitute.id)&&currSupplier.status==2}">onchange="this.selectedIndex=this.defaultIndex;"</c:if> >
																	<option value="1"
																		<c:if test="${aptitute.isMajorFund==1}"> selected="selected"</c:if>>是</option>
																	<option value="0"
																		<c:if test="${aptitute.isMajorFund==0}"> selected="selected"</c:if>>否</option>
																</select>
															</td>
															
															<td class="tc" <c:if test="${fn:contains(engPageField,aptitute.id)}">style="border: 1px solid red;" </c:if>>
																<div class="w200 fl">
																	<c:if test="${(fn:contains(engPageField,aptitute.id)&&currSupplier.status==2 ) || currSupplier.status==-1 || empty(currSupplier.status)}">
																		<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="eng_up_${certAptNumber}" multiple="true" businessId="${aptitute.id}" typeId="${supplierDictionaryData.supplierEngCert}" sysKey="${sysKey}" auto="true" />
																	</c:if>
																	<c:if test="${!fn:contains(engPageField,aptitute.id)&&currSupplier.status==2 }">
																		<u:show showId="eng_show_${certAptNumber}" delete="false" businessId="${aptitute.id}" typeId="${supplierDictionaryData.supplierEngCert}" sysKey="${sysKey}" />
																	</c:if>
																	<c:if test="${currSupplier.status==-1 || empty(currSupplier.status) || fn:contains(engPageField,aptitute.id)}">
																		<u:show showId="eng_show_${certAptNumber}" businessId="${aptitute.id}" typeId="${supplierDictionaryData.supplierEngCert}" sysKey="${sysKey}" />
																	</c:if>
																</div>
															</td>
														</tr>
														<c:set var="certAptNumber" value="${certAptNumber + 1}" />
													</c:forEach>
												</tbody>
											</table>
											<input type="hidden" id="certAptNumber"
												value="${certAptNumber}">
										</div>
									</div>
								</div>

								<div class="tab-pane fades" id="server_div">

									<h2 class="list_title">服务专业信息</h2>
									<ul class="list-unstyled">
										<input type="hidden" name="supplierMatSe.id"
											value="${currSupplier.supplierMatSe.id}" />
										<input type="hidden" name="supplierMatSe.supplierId"
											value="${currSupplier.id}" />
									</ul>

									<div class="col-md-12 col-sm-12 col-xs-12 border_font mt20">
										<span class="font_line"> 资质证书信息 </span>
										<div class="col-md-12 col-xs-12 col-sm-12 p0">
											<c:choose>
                       	<c:when test="${currSupplier.status==2 }">
                         	<button class="btn btn-Invalid"  type="button" disabled="disabled">新增</button>
                         </c:when>
                         <c:otherwise>
                           <button type="button" class="btn" onclick="addCertSe()">新增</button>
                         </c:otherwise>
                       </c:choose>
											<button type="button" class="btn" onclick="delCertSe()">删除</button>
											<span class="red">${fw_cert }</span>
										</div>
										<div class="col-md-12 col-xs-12 col-sm-12 over_auto p0">
											<table id="share_table_id"
												class="table table-bordered table-condensed mt5 table_wrap left_table table_input m_table_fixed_border">
												<thead>
													<tr>
														<th class="info"><input type="checkbox"
															onchange="checkAll(this, 'cert_se_list_tbody_id')" />
														</th>
														<th class="info">资质证书名称</th>
														<th class="info">证书编号</th>
														<th class="info">资质等级</th>
														<th class="info">发证机关或机构</th>
														<th class="info">有效期（起始时间）</th>
														<th class="info">有效期（结束时间）</th>
														<th class="info">证书状态</th>
														<th class="info w200">证书图片（可上传多张）</th>
													</tr>
												</thead>
												<tbody id="cert_se_list_tbody_id">
													<c:set var="certSeNumber" value="0"></c:set>
													<c:forEach
														items="${currSupplier.supplierMatSe.listSupplierCertSes}"
														var="certSe" varStatus="vs">
														<tr
															<c:if test="${fn:contains(servePageField,certSe.id)}"> onmouseover="errorMsg(this,'${certSe.id}','mat_serve_page')"</c:if>>
															<td class="tc"
																<c:if test="${fn:contains(servePageField,certSe.id)}">style="border: 1px solid red;" </c:if>>
																<input type="checkbox" class="border0" 
																	value="${certSe.id}" /> <input type="hidden"
																	<c:if test="${fn:contains(servePageField,certSe.id)}">readonly='readonly' </c:if> 
																	name="supplierMatSe.listSupplierCertSes[${certSeNumber}].id"
																	value="${certSe.id}"></td>
															<!-- 服务资质证书名称 -->
															<td class="tc"
																<c:if test="${fn:contains(servePageField,certSe.id)}">style="border: 1px solid red;" </c:if>>
																<div class="w200">
																 	<input type="text" class="border0"   maxlength="30" 
																 		<c:if test="${!fn:contains(servePageField,certSe.id)&&currSupplier.status==2}">readonly='readonly' </c:if>
																		name="supplierMatSe.listSupplierCertSes[${certSeNumber}].name"
																		value="${certSe.name}" />
																 </div>
															</td>
															<!--服务 证书编号 -->
															<td class="tc"
																<c:if test="${fn:contains(servePageField,certSe.id)}">style="border: 1px solid red;" </c:if>>
																<div class="w150">
															 		<input type="text" class="border0" maxlength="150"
																 		<c:if test="${!fn:contains(servePageField,certSe.id)&&currSupplier.status==2}">readonly='readonly' </c:if>
																		name="supplierMatSe.listSupplierCertSes[${certSeNumber}].code"
																		value="${certSe.code}" />
																</div>
															</td>
															
															<!--服务 资质等级 -->	
															<td class="tc"
																<c:if test="${fn:contains(servePageField,certSe.id)}">style="border: 1px solid red;" </c:if>><input maxlength="30" 
																type="text" class="border0" <c:if test="${!fn:contains(servePageField,certSe.id)&&currSupplier.status==2}">readonly='readonly' </c:if>
																name="supplierMatSe.listSupplierCertSes[${certSeNumber}].levelCert"
																value="${certSe.levelCert}" />
															</td>
														
															<!--服务 发证机关或机构 -->	
															<td class="tc"
																<c:if test="${fn:contains(servePageField,certSe.id)}">style="border: 1px solid red;" </c:if>>
																<div class="w200">
															 		<input type="text" class="border0" maxlength="60" 
															    <c:if test="${!fn:contains(servePageField,certSe.id)&&currSupplier.status==2}">readonly='readonly' </c:if>
																	name="supplierMatSe.listSupplierCertSes[${certSeNumber}].licenceAuthorith"
																	value="${certSe.licenceAuthorith}" />
																</div>
															</td>
														
															<td class="tc"
																<c:if test="${fn:contains(servePageField,certSe.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" class="border0"
																<c:if test="${(fn:contains(servePageField,certSe.id)&&currSupplier.status==2) ||currSupplier.status==-1 || empty(currSupplier.status)}">onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',readOnly:true})" </c:if>
																name="supplierMatSe.listSupplierCertSes[${certSeNumber}].expStartDate"
																value="<fmt:formatDate value="${certSe.expStartDate}" pattern="yyyy-MM-dd "/>" />
															</td>
															<td class="tc"
																<c:if test="${fn:contains(servePageField,certSe.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" class="border0"
																<c:if test="${(fn:contains(servePageField,certSe.id)&&currSupplier.status==2) ||currSupplier.status==-1 || empty(currSupplier.status)}">onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d',readOnly:true})" </c:if>
																name="supplierMatSe.listSupplierCertSes[${certSeNumber}].expEndDate"
																value="<fmt:formatDate value="${certSe.expEndDate}" pattern="yyyy-MM-dd "/>" />
															</td>
															<!-- 服务证书状态 -->	
															<td class="tc"
																<c:if test="${fn:contains(servePageField,certSe.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" class="border0" maxlength="15" <c:if test="${!fn:contains(servePageField,certSe.id)&&currSupplier.status==2}">readonly='readonly' </c:if>
																name="supplierMatSe.listSupplierCertSes[${certSeNumber}].mot"
																value="${certSe.mot}" />
															</td>
															<!-- 图片 -->
															<td class="tc"
																<c:if test="${fn:contains(servePageField,certSe.id)}">style="border: 1px solid red;" </c:if>>
																<div class="fl w200">
																<c:if test="${(fn:contains(servePageField,certSe.id)&&currSupplier.status==2 ) || currSupplier.status==-1 || empty(currSupplier.status)}">	 <u:upload
																	singleFileSize="${properties['file.picture.upload.singleFileSize']}"
																	exts="${properties['file.picture.type']}"
																	id="se_up_${certSeNumber}" multiple="true"
																	businessId="${certSe.id}"
																	typeId="${supplierDictionaryData.supplierServeCert}"
																	sysKey="${sysKey}" auto="true" /></c:if> 
																	<c:if test="${!fn:contains(servePageField,certSe.id)&&currSupplier.status==2 }">	 <u:show showId="se_show_${certSeNumber}" delete="false"  businessId="${certSe.id}" 	typeId="${supplierDictionaryData.supplierServeCert}" sysKey="${sysKey}" /> </c:if>
																	<c:if test="${currSupplier.status==-1 || empty(currSupplier.status)||fn:contains(servePageField,certSe.id)}">	 <u:show showId="se_show_${certSeNumber}"   businessId="${certSe.id}" 	typeId="${supplierDictionaryData.supplierServeCert}" sysKey="${sysKey}" /> </c:if>
																</div>
															</td>
														</tr>
														<c:set var="certSeNumber" value="${certSeNumber + 1}"></c:set>
													</c:forEach>
												</tbody>
											</table>
											<input type="hidden" id="certSeNumber"
												value="${certSeNumber}">
										</div>
									</div>

								</div>
								<input type="hidden" id="supplierTypes" name="supplierTypeIds" value="${currSupplier.supplierTypeIds }" />
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="btmfix">
		<div style="margin-top: 15px;text-align: center;">
			<button type="button"
				class="btn padding-left-20 padding-right-20 margin-5"
				onclick="prev();">上一步</button>
			<button type="button"
				class="btn padding-left-20 padding-right-20 margin-5"
				onclick="ajaxSave();">暂存</button>
			<input type="button"
				class="btn padding-left-20 padding-right-20 margin-5" value="下一步"
				onclick="next(1)" />
		</div>
	</div>
<div class="footer_margin">
   		<jsp:include page="../../../../../index_bottom.jsp"></jsp:include>
 </div>
</body>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/regex.js"></script>

</html>