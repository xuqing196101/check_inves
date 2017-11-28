<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML >
<html>

	<head>
		<%@ include file="/reg_head.jsp"%>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<c:if test="${currSupplier.status == 2}">
			<%@ include file="/WEB-INF/view/ses/sms/supplier_register/supplier_purchase_dept.jsp"%>
		</c:if>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/sms/supplier_register/aptitude.js"></script>
		<title>供应商注册</title>
	</head>
	<body>
		<!-- 隐藏域 -->
		<input type="hidden" id="supplierId" value="${currSupplier.id}" />
		<input type="hidden" id="supplierSt" value="${currSupplier.status}" />
		<input type="hidden" id="flag_proQua" value="${fn:contains(currSupplier.supplierTypeIds, 'PRODUCT') and fn:length(proQua) > 0}" />
		<input type="hidden" id="flag_saleQua" value="${fn:contains(currSupplier.supplierTypeIds, 'SALES') and fn:length(saleQua) > 0}" />
		<input type="hidden" id="flag_projectQua" value="${fn:contains(currSupplier.supplierTypeIds, 'PROJECT') and fn:length(projectQua) > 0}" />
		<input type="hidden" id="flag_serviceQua" value="${fn:contains(currSupplier.supplierTypeIds, 'SERVICE') and fn:length(serviceQua) > 0}" />
		<input type="hidden" id="aptitude_error" value="${aptitude_error}" />
		<input type="hidden" id="flagSupplierTypeAudit" value="${flagSupplierTypeAudit}" />
		<input type="hidden" id="infoSupplierTypeAudit" value="${infoSupplierTypeAudit}" />
		<!-- 项目戳开始 -->
		<div class="wrapper">
			<jsp:include page="/WEB-INF/view/ses/sms/supplier_register/common_jump.jsp">
				<jsp:param value="${currSupplier.id}" name="supplierId"/>
				<jsp:param value="${currSupplier.status}" name="supplierSt"/>
				<jsp:param value="4" name="currentStep"/>
			</jsp:include>
			<!-- <div class="container clear margin-top-30">
				<h2 class="step_flow">
					<span id="sp1" class="new_step current fl" onclick="updateStep('1')"><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span>
		            <span id="sp2" class="new_step current fl" onclick="updateStep('2')"><i class="">2</i><div class="line"></div> <span class="step_desc_01">供应商类型</span> </span>
		            <span id="ty3" class="new_step current fl" onclick="updateStep('3')"><i class="">3</i><div class="line"></div> <span class="step_desc_02">产品类别</span> </span>
		            <span id="sp4" class="new_step current fl"><i class="">4</i><div class="line"></div> <span class="step_desc_01">资质文件维护</span> </span>
		            <span id="sp5" class="new_step fl"><i class="">5</i><div class="line"></div> <span class="step_desc_02">销售合同</span> </span>
		            <span id="sp6" class="new_step fl"><i class="">6</i><div class="line"></div> <span class="step_desc_01">采购机构</span> </span>
		            <span id="sp7" class="new_step fl"><i class="">7</i><div class="line"></div> <span class="step_desc_02">承诺书和申请表</span> </span>
		            <span id="sp8" class="new_step fl new_step_last"><i class="">8</i> <span class="step_desc_01">提交审核</span> </span>
		            <div class="clear"></div>
				</h2>
			</div> -->
			<!--基本信息-->
			<div class="container content ">
				<div class="row magazine-page">
					<div class="col-md-12 tab-v2 job-content">
						<div class="padding-top-10">
							<ul id="page_ul_id" class="nav nav-tabs bgdd supplier_tab">
								<c:set value="0" var="liCount" />
								<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PRODUCT') and fn:length(proQua) > 0}">
									<c:set value="${liCount+1}" var="liCount" />
									<li id="li_id_1" class="active">
										<a aria-expanded="true" onmouseup="init_web_upload_in('#tab-1')" href="#tab-1" data-toggle="tab" class="f18">物资-生产型资质信息</a>
									</li>
								</c:if>
								<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SALES') and fn:length(saleQua) > 0}">
									<li id="li_id_2" class='<c:if test="${liCount == 0}">active</c:if>'>
										<a aria-expanded="false" onmouseup="init_web_upload_in('#tab-2')" href="#tab-2" data-toggle="tab" class="f18">物资-销售型资质信息</a>
									</li>
									<c:set value="${liCount+1}" var="liCount" />
								</c:if>
								<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PROJECT') and fn:length(projectQua) > 0}">
									<li id="li_id_3" class='<c:if test="${liCount == 0}">active</c:if>'>
										<a aria-expanded="false" onmouseup="init_web_upload_in('#tab-3')" href="#tab-3" data-toggle="tab" class="f18">工程资质信息</a>
									</li>
									<c:set value="${liCount+1}" var="liCount" />
								</c:if>
								<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SERVICE') and fn:length(serviceQua) > 0}">
									<li id="li_id_4" class='<c:if test="${liCount == 0}">active</c:if>'>
										<a aria-expanded="false" onmouseup="init_web_upload_in('#tab-4')" href="#tab-4" data-toggle="tab" class="f18">服务资质信息</a>
									</li>
									<c:set value="${liCount+1}" var="liCount" />
								</c:if>
							</ul>
							<div class="tab-content padding-top-20 pr border0 p0" id="tab_content_div_id">
								<c:set value="0" var="divCount" />
								<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PRODUCT') and fn:length(proQua) > 0}">
									<!-- 物资生产型 -->
									<div class="tab-pane active" id="tab-1">
										<h2 class="f16">
							      	<font color="red">*</font> 上传物资-生产型资质文件
										</h2>
										<table class="table table-bordered">
											<c:set value="0" var="proLength" />
											<c:forEach items="${proQua }" var="obj">
												<tr>
													<td width="18%">${obj.categoryName }</td>
													<td>
														<c:forEach items="${obj.list }" var="qua">
															<c:set value="${proLength+1}" var="proLength" />
															<c:set value="${obj.itemId}_${qua.id}" var="quaId" />
															<c:set value="${auditTypeMap['PRODUCT']}" var="auditType" />
															<div class="mr5 fl" <c:if test="${fn:contains(audit,quaId)}">style="border: 1px solid red;" onmouseover="errorMsg(this, '${quaId}','${auditType}')"</c:if>>
																<c:choose>
																	<c:when test="${!fn:contains(audit,quaId) && currSupplier.status==2}">
																		<div class="webuploader-pick">${qua.name}</div>
																		<div class="clear"></div>
																		<u:show showId="pShow${proLength}" delete="false" groups="${fileGroupShow}" businessId="${qua.flag}" sysKey="${sysKey}" typeId="${typeId}" />
																	</c:when>
																	<c:otherwise>
																		<u:upload singleFileSize="300" exts="${properties['file.picture.type']}" id="pUp${proLength}" multiple="true" buttonName="${qua.name}" groups="${fileGroupUp}" businessId="${qua.flag}" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
																		<div class="clear"></div>
																		<u:show showId="pShow${proLength}" groups="${fileGroupShow}" businessId="${qua.flag}" sysKey="${sysKey}" typeId="${typeId}" />
																	</c:otherwise>
																</c:choose>
															</div>
														</c:forEach>
													</td>
												</tr>
											</c:forEach>
										</table>
										<c:set value="${divCount+1}" var="divCount" />
									</div>
								</c:if>
								<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SALES') and fn:length(saleQua) > 0}">
									<!-- 物资销售型 -->
									<div class="tab-pane <c:if test="${divCount==0 }">active in</c:if> fade height-300" id="tab-2">
										<h2 class="f16">
							      	<font color="red">*</font> 上传物资-销售型资质文件
										</h2>
										<table class="table table-bordered">
											<c:set value="0" var="saleLength" />
											<c:forEach items="${saleQua }" var="obj">
												<tr>
													<td class="w200">${obj.categoryName }</td>
													<td>
														<c:forEach items="${obj.list }" var="qua">
															<c:set value="${saleLength+1}" var="saleLength" />
															<c:set value="${obj.itemId}_${qua.id}" var="quaId" />
															<c:set value="${auditTypeMap['SALES']}" var="auditType" />
															<div class="mr5 fl" <c:if test="${fn:contains(audit,quaId)}">style="border: 1px solid red;" onmouseover="errorMsg(this,'${quaId}','${auditType}')"</c:if>>
																<c:choose>
																	<c:when test="${!fn:contains(audit,quaId) && currSupplier.status==2}">
																		<div class="webuploader-pick">${qua.name}</div>
																		<div class="clear"></div>
																		<u:show showId="saleShow${saleLength}" delete="false" groups="${fileGroupShow}" businessId="${qua.flag}" sysKey="${sysKey}" typeId="${typeId}" />
																	</c:when>
																	<c:otherwise>
																		<u:upload singleFileSize="300" exts="${properties['file.picture.type']}" id="saleUp${saleLength}" multiple="true" buttonName="${qua.name}" groups="${fileGroupUp}" businessId="${qua.flag}" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
																		<div class="clear"></div>
																		<u:show showId="saleShow${saleLength}" groups="${fileGroupShow}" businessId="${qua.flag}" sysKey="${sysKey}" typeId="${typeId}" />
																	</c:otherwise>
																</c:choose>
															</div>
														</c:forEach>
													</td>
												</tr>
											</c:forEach>
										</table>
										<c:set value="${divCount+1}" var="divCount" />
									</div>
								</c:if>
								<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PROJECT')}">
									<div class="tab-pane <c:if test="${divCount==0 }">active in</c:if> fade height-300" id="tab-3">
										<h2 class="f16">
											<font color="red">*</font> 上传工程资质文件
										</h2>
										<form id="item_form" method="post" class="col-md-12 col-xs-12 col-sm-12 over_auto p0">
											<table class="table table-bordered table_input m_table_fixed_border">
												<thead>
												<tr>
													<th class="info tc w50">序号</th>
													<%--<th class="info tc w50">类别</th>
                            <th class="info tc">大类</th>
                            <th class="info tc">中类</th>
                            <th class="info tc">小类</th>--%>
													<th class="info tc">产品类别</th>
													<th class="info tc w200">资质类型</th>
													<th class="info tc w100">证书编号</th>
													<th class="info tc w100">专业类别</th>
													<th class="info tc w80">资质等级</th>
													<th class="info tc">证书图片</th>
												</tr>
												</thead>
												<c:forEach items="${projectQua}" var="cate" varStatus="vs">
													<c:set value="${auditTypeMap['PROJECT']}" var="auditType" />
													<tr <c:if test="${fn:contains(audit,cate.itemsId)}">onmouseover="errorMsg(this, '${cate.itemsId}','${auditType}')"</c:if>>
														<!-- 序号 -->
														<td class="tc" <c:if test="${fn:contains(audit,cate.itemsId)}">style="border: 1px solid red;" </c:if>>
															<div class="w50"> ${vs.index + 1}</div>
															<input type="hidden" name="listSupplierItems[${vs.index}].id" value="${cate.itemsId}">
														</td>
														<%--<td class="tc" <c:if test="${fn:contains(audit,cate.itemsId)}">style="border: 1px solid red;" </c:if>>
                                     <div class="w80 lh30"> ${cate.rootNode}</div>
                                 </td>
                                 <td <c:if test="${fn:contains(audit,cate.itemsId)}">style="border: 1px solid red;" </c:if>>
                                     <div class="w150 lh30">${cate.firstNode}</div>
                                 </td>
                                 <td <c:if test="${fn:contains(audit,cate.itemsId)}">style="border: 1px solid red;" </c:if>>
                                     <div class="w200 lh30">${cate.secondNode}</div>
                                 </td>
                                 <td <c:if test="${fn:contains(audit,cate.itemsId)}">style="border: 1px solid red;" </c:if>>
                                     <div class="w200 lh30">${cate.thirdNode}</div>
                                 </td>--%>
                            <!-- 产品类别 -->      
														<td class="tc" <c:if test="${fn:contains(audit,cate.itemsId)}">style="border: 1px solid red;" </c:if>>
															<div class="w200 lh30">
																<c:choose>
																	<c:when test="${cate.fourthNode!=null}">
																		${cate.fourthNode}
																	</c:when>
																	<c:otherwise>
																		<c:choose>
																			<c:when test="${cate.thirdNode!=null}">
																				${cate.thirdNode}
																			</c:when>
																			<c:otherwise>
																				<c:choose>
																					<c:when test="${cate.secondNode!=null}">
																						${cate.secondNode}
																					</c:when>
																					<c:otherwise>
																						${cate.firstNode}
																					</c:otherwise>
																				</c:choose>
																			</c:otherwise>
																		</c:choose>
																	</c:otherwise>
																</c:choose>
															</div>
														</td>
													<!-- 资质类型 -->	
														<td <c:if test="${fn:contains(audit,cate.itemsId)}">style="border: 1px solid red;" </c:if>>
															<select class="border0 p0 w200" name="listSupplierItems[${vs.index}].qualificationType" id="listSupplierItems${vs.index}qualificationType" onchange="getFileByCode(this, '${vs.index}', '1')">
																<c:forEach items="${cate.typeList}" var="type">
																	<option value="${type.id}" <c:if test="${cate.qualificationType eq type.id}">selected</c:if>>${type.name}</option>
																</c:forEach>
															</select>

														</td>
													<!-- 证书编号 -->	
														<td <c:if test="${fn:contains(audit,cate.itemsId)}">style="border: 1px solid red;" </c:if>>
															<input type="text" class="border0" name="listSupplierItems[${vs.index}].certCode" label="${vs.index}" value="${cate.certCode}" 
															onkeydown="onkeydownCertCode(this)"
															onkeyup="onkeyupCertCode(this, '${vs.index}')">
														</td>
													<!-- 专业类别 -->
														<td <c:if test="${fn:contains(audit,cate.itemsId)}">style="border: 1px solid red;" </c:if>>
															<select class="border0 p0 w200" name="listSupplierItems[${vs.index}].professType" onchange="getFileByCode(this, '${vs.index}', '3')">
																<option value="${cate.proName}" selected="selected">${cate.proName}</option>
															</select>
															<%--
															<input type="text" class="border0" name="listSupplierItems[${vs.index}].professType" value="${cate.proName}" onblur="getFileByCode(this, '${vs.index}', '3')">
															 --%>
														</td>
													<!-- 资质等级 -->
														<td <c:if test="${fn:contains(audit,cate.itemsId)}">style="border: 1px solid red;" </c:if>>
															<c:if test="${!empty cate.level}">
																<input type="hidden" name="listSupplierItems[${vs.index}].level" id="listSupplierItems${vs.index}" value="${cate.level.id}" class="w80">
																<input type="text" readonly="readonly" class="border0" value="${cate.level.name}" onload="getFileByCode(this, '${vs.index}', '3')">
															</c:if>
															<c:if test="${empty cate.level}">
																<input type="hidden" name="listSupplierItems[${vs.index}].level" id="listSupplierItems${vs.index}" value="${cate.diyLevel}" class="w80">
																<input type="text" readonly="readonly" class="border0" value="${cate.diyLevel}" onload="getFileByCode(this, '${vs.index}', '3')">
															</c:if>
														</td>
														<c:if test="${currSupplier.status== -1 || currSupplier.status==2}">
															<script>
		                            function s() {
		                                var number = ${vs.index};
		                                //供应商
		                                var supplierId = "${currSupplier.id}";
		                                //证书编号
		                                var certCode = "${cate.certCode}";
		                                //专业类别
		                                var professType = "${cate.proName}";
		                                //资质类型
		                                var typeId = $("#listSupplierItems${vs.index}qualificationType").val();
		                                if (typeId != null && typeId != "" && typeId != "undefined" && certCode != null && certCode != "" && certCode != "undefined" && professType != null && professType != "") {
		                                    getData("#listSupplierItems" + number, typeId, certCode, supplierId, professType, number, 0);
		                                }
		                            }
		                           //s();
															</script>
														</c:if>
														
													<!-- 显示图片 按钮 -->	
														<td class="tc"
															<c:if test="${fn:contains(audit,cate.itemsId)}">style="border: 1px solid red;" </c:if> >
															<div class="w110 fl">
																<c:if test="${!empty cate.fileId}">
																	<u:show showId="eng_show_${vs.index}" delete="false" businessId="${cate.fileId}" typeId="${engTypeId}" sysKey="${sysKey}"/>
																</c:if>
															</div>
														</td>
													</tr>
												</c:forEach>
										  </table>
										</form>
										<c:set value="${divCount+1}" var="divCount" />
									</div>
								</c:if>
								<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SERVICE') and fn:length(serviceQua) > 0}">
									<div class="tab-pane <c:if test="${divCount==0 }">active in</c:if> fade height-300" id="tab-4">
										<h2 class="f16">
							      	<font color="red">*</font> 上传服务资质文件
										</h2>
										<table class="table table-bordered">
											<c:set value="0" var="serLength" />
											<c:forEach items="${serviceQua }" var="obj">
												<tr>
													<td class="w200">${obj.categoryName }</td>
													<td>
														<c:forEach items="${obj.list }" var="qua">
															<c:set value="${serLength+1}" var="serLength" />
															<c:set value="${obj.itemId}_${qua.id}" var="quaId" />
															<c:set value="${auditTypeMap['SERVICE']}" var="auditType" />
															<div class="fl mr5" <c:if test="${fn:contains(audit,quaId)}">style="border: 1px solid red;" onmouseover="errorMsg(this, '${quaId}','${auditType}')"</c:if>>
																<c:choose>
																	<c:when test="${!fn:contains(audit,quaId) && currSupplier.status==2}">
																		<div class="webuploader-pick">${qua.name}</div>
																		<div class="clear"></div>
																		<u:show showId="serverShow${serLength}" delete="false" groups="${fileGroupShow}" businessId="${qua.flag}" sysKey="${sysKey}" typeId="${typeId}" />
																	</c:when>
																	<c:otherwise>
																		<u:upload singleFileSize="300" exts="${properties['file.picture.type']}" id="serverUp${serLength}" multiple="true" buttonName="${qua.name}" groups="${fileGroupUp}" businessId="${qua.flag}" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
																		<div class="clear"></div>
																		<u:show showId="serverShow${serLength}" groups="${fileGroupShow}" businessId="${qua.flag}" sysKey="${sysKey}" typeId="${typeId}" />
																	</c:otherwise>
																</c:choose>
															</div>
														</c:forEach>
													</td>
												</tr>
											</c:forEach>
										</table>
										<c:set value="${divCount+1}" var="divCount" />
									</div>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="btmfix">
			<div style="margin-top: 15px;text-align: center;">
				<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="prev()">上一步</button>
				<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="saveItems()">暂存</button>
				<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="next()">下一步</button>
			</div>
		</div>

		<form id="aptitude_form" action="${pageContext.request.contextPath}/supplier/perfect_aptitude.html" method="post">
			<input name="id" id="supplierId" value="${currSupplier.id}" type="hidden" />
			<input name="supplierTypeIds" id="supplierTypeIds" value="${currSupplier.supplierTypeIds }" type="hidden" />
		</form>
		<div class="footer_margin">
   			<jsp:include page="../../../../../index_bottom.jsp"></jsp:include>
  		</div>
	</body>
</html>