<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<title>供应商类型</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<script type="text/javascript">
			//文件下載
			/*  function downloadFile(fileName) {
			   $("input[name='fileName']").val(fileName);
			   $("#download_form_id").submit();
			 } */

			function download(id, key) {
				var form = $("<form>");
				form.attr('style', 'display:none');
				form.attr('method', 'post');
				form.attr('action', globalPath + '/file/download.html?id=' + id + '&key=' + key);
				$('body').append(form);
				form.submit();
			}
			//只读
			$(function() {
				$(":input").each(function() {
					$(this).attr("readonly", "readonly");
				});
			});
		</script>

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
			}
			
			/* function fanhui() {
				if('${judge}' == 2) {
					window.location.href = "${pageContext.request.contextPath}/supplierQuery/selectByCategory.html";
				} else {
					window.location.href = "${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html?address=" + encodeURI(encodeURI('${suppliers.address}')) + "&judge=${judge}";
				}
			} */
			
			function fanhui() {
				if('${judge}' == 2) {
					window.location.href = "${pageContext.request.contextPath}/supplierQuery/selectByCategory.html";
				} else {
					var action = "${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html";
					$("#form_back").attr("action", action);
					$("#form_back").submit();
				};
			};
		</script>
	</head>

	<!--面包屑导航开始-->

	<body>
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0);">支撑环境</a>
					</li>
					<li>
						<a href="javascript:void(0);">供应商管理</a>
					</li>
					<li>
						<a href="javascript:void(0);" onclick="jumppage('${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html?sign=1')">供应商列表</a>
					</li>
					<li>
						<a href="javascript:void(0);">供应商查看</a>
					</li>
				</ul>
			</div>
		</div>
		<div class="container container_box">
			<div class="content">
				<div class="col-md-12 col-sm-12 col-xs-12 tab-v2 job-content">
					<ul class="nav nav-tabs bgwhite">
						<li class="">
							<a aria-expanded="true" href="#tab-1" data-toggle="tab" class="f18" onclick="tijiao('essential');">基本信息</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('financial');">财务信息</a>
						</li>
						<li class="">
							<a aria-expanded="fale" href="#tab-3" data-toggle="tab" class="f18" onclick="tijiao('shareholder');">股东信息</a>
						</li>
						<li class="active">
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
					<div class="content ">
						<div class="col-md-12 tab-v2 job-content">
							<div class="tab-v2">
								<ul id="page_ul_id" class="nav nav-tabs bgdd supplier_tab">
									<c:set value="0" var="liCountPro" />
									<c:set value="0" var="liCountSell" />
									<c:set value="0" var="liCountEng" />
									<c:set value="0" var="liCountSer" />
									<c:if test="${fn:contains(supplierTypeNames, '生产')}">
										<c:set value="${liCountPro+1}" var="liCountPro" />
										<li class="active">
											<a aria-expanded="true" href="#tab-1" data-toggle="tab">物资-生产型专业信息</a>
										</li>
									</c:if>
									<c:if test="${fn:contains(supplierTypeNames, '销售')}">
										<li class='<c:if test="${liCountPro == 0}">active <c:set value="${liCountSell+1}" var="liCountSell" /></c:if>'>
											<a aria-expanded="false" href="#tab-2" data-toggle="tab">物资-销售型专业信息</a>
										</li>
									</c:if>
									<c:if test="${fn:contains(supplierTypeNames, '工程')}">
										<li class='<c:if test="${liCountSell == 0 && liCountPro == 0}">active <c:set value="${liCountEng+1}" var="liCountEng" /></c:if>'>
											<a aria-expanded="false" href="#tab-3" data-toggle="tab">工程专业信息</a>
										</li>
									</c:if>
									<c:if test="${fn:contains(supplierTypeNames, '服务')}">
										<li class='<c:if test="${liCountEng == 0 && liCountPro == 0 && liCountEng == 0}">active <c:set value="${liCountSer+1}" var="liCountSer" /></c:if>'>
											<a aria-expanded="false" href="#tab-4" data-toggle="tab">服务专业信息</a>
										</li>
									</c:if>
								</ul>

								<div class="count_flow">
									<div class="tab-content padding-top-20" id="tab_content_div_id">
										<c:if test="${fn:contains(supplierTypeNames, '生产')}">
											<div class="tab-pane fade active in height-300" id="tab-1">
												<h2 class="count_flow"><i>1</i>产品研发能力</h2>
												<ul class="ul_list count_flow">
													<li class="col-md-3 col-sm-6 col-xs-12 pl15">
														<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">技术人员数量比例(%)：</span>
														<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
															<input id="scaleTech_production" type="text" value="${supplierMatPros.scaleTech }" 
																<c:if test="${fn:contains(fieldProOne,'scaleTech')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('scaleTech','mat_pro_page');"</c:if>
																onclick="reasonProduction1(this)" maxlength="10"
																onkeyup="value=value.replace(/[^\d.]/g,'')"
																onblur="validatePercentage2(this.value)"
															/>
														</div>
													</li>
													<li class="col-md-3 col-sm-6 col-xs-12 pl15">
													<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">高级技术人员数量比例(%)：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input id="scaleHeightTech_production" type="text" value="${supplierMatPros.scaleHeightTech }" 
															<c:if test="${fn:contains(fieldProOne,'scaleHeightTech')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('scaleHeightTech','mat_pro_page');"</c:if>
															onclick="reasonProduction1(this)" maxlength="10"
															onkeyup="value=value.replace(/[^\d.]/g,'')"
															onblur="validatePercentage2(this.value)"
														/>
													</div>
													</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" onclick="reason1(this)">研发部门名称：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input type="text" value="${supplierMatPros.researchName }" />
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" onclick="reason1(this)">研发部门人数：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input type="text" value="${supplierMatPros.totalResearch }" />
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">研发部门负责人：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input type="text" value="${supplierMatPros.researchLead }" />
											</div>
										</li>
										<li class="col-md-12 col-sm-12 col-xs-12 ">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">承担国家军队科研项目：</span>
											<div class="col-md-12 col-sm-12 col-xs-12 p0">
												<textarea class="col-md-12 col-xs-12 col-sm-12 h80">${supplierMatPros.countryPro }</textarea>
											</div>
										</li>
										<li class="col-md-12 col-sm-12 col-xs-12 ">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">获得国家军队科技奖项：</span>
											<div class="col-md-12 col-sm-12 col-xs-12 p0">
												<textarea class="col-md-12 col-xs-12 col-sm-12 h80">${supplierMatPros.countryReward }</textarea>
											</div>
										</li>
										</ul>

										<h2 class="count_flow"><i>2</i>资质证书信息</h2>
										<div class="ul_list count_flow">
											<table class="table table-bordered table-condensed table-hover">
												<thead>
													<tr>
														<th class="info w50">序号</th>
														<th class="info">资质证书名称</th>
														<th class="info">证书编号</th>
														<th class="info">资质等级</th>
														<th class="info">发证机关或机构</th>
														<th class="info">有效期(起止时间)</th>
														<th class="info">有效期(起止时间)</th>
														<th class="info">证书状态</th>
														<th class="info">证书图片</th>
													</tr>
												</thead>
												<c:forEach items="${materialProduction}" var="m" varStatus="vs">
													<tr>
														<td class="tc">${vs.index + 1}</td>
														<td class="tl pl20">${m.name }</td>
														<td class="tc">${m.code} </td>
														<td class="tc">${m.levelCert}</td>
														<td class="tc">${m.licenceAuthorith }</td>
														<td class="tc">
															<fmt:formatDate value="${m.expStartDate }" pattern='yyyy-MM-dd' />
														</td>
														<td class="tc">
															<fmt:formatDate value="${m.expEndDate }" pattern='yyyy-MM-dd' />
														</td>
														<td class="tc">${m.mot}</td>
														<td class="tc">
															<u:show showId="pro_show${vs.index+1}" delete="false" businessId="${m.id}" typeId="${supplierDictionaryData.supplierProCert}" sysKey="${sysKey}" />
														</td>
													</tr>
												</c:forEach>
											</table>
										</div>
										</div>
										</c:if>

										<c:if test="${fn:contains(supplierTypeNames, '销售')}">
											<div class="tab-pane <c:if test="${liCountSell==1}">active in</c:if> fade  in height-200" id="tab-2">
												<ul class="ul_list">
													<table class="table table-bordered table-condensed table-hover">
														<thead>
															<tr>
																<th class="info w50">序号</th>
																<th class="info">资质证书名称</th>
																<th class="info">证书编号</th>
																<th class="info">资质等级</th>
																<th class="info">发证机关或机构</th>
																<th class="info">有效期（起始时间）</th>
																<th class="info">有效期（结束时间）</th>
																<th class="info">证书状态</th>
																<th class="info">证书图片</th>
															</tr>
														</thead>
														<c:forEach items="${supplierCertSell}" var="s" varStatus="vs">
															<tr>
																<td class="tc">${vs.index + 1}</td>
																<td class="tl pl20">${s.name }</td>
																<td class="tc">${s.code}</td>
																<td class="tc">${s.levelCert}</td>
																<td class="tc">${s.licenceAuthorith }</td>
																<td class="tc">
																	<fmt:formatDate value="${s.expStartDate }" pattern='yyyy-MM-dd' />
																</td>
																<td class="tc" id="expEndDate_${s.id }">
																	<fmt:formatDate value="${s.expEndDate }" pattern='yyyy-MM-dd' />
																</td>
																<td class="tc" id="mot_${s.id }">${s.mot}</td>
																<td class="tc">
																	<u:show showId="sale_show_${vs.index+1}" delete="false" businessId="${s.id}" typeId="${supplierDictionaryData.supplierSellCert}" sysKey="${sysKey}" />
																</td>
															</tr>
														</c:forEach>
													</table>
												</ul>
											</div>
										</c:if>

										<c:if test="${fn:contains(supplierTypeNames, '工程')}">
											<div class="tab-pane <c:if test="${liCountEng==1}">active in</c:if> fade height-200" id="tab-3">
												<h2 class="count_flow"><i>1</i>保密工程业绩</h2>
												<ul class="ul_list count_flow">
													<c:if test="${supplierMatEngs.isHavingConAchi eq '0'}">
														<li class="col-md-3 col-sm-6 col-xs-12 pl15">
															<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" style="width: 230px;">是否有国家或军队保密工程业绩：</span>
															<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
																<input id="isHavingConAchi_engineering" type="text" value="无" onclick="reasonEngineering1(this)" />
															</div>
														</li>
													</c:if>
													<c:if test="${supplierMatEngs.isHavingConAchi eq '1'}">
														<li class="col-md-3 col-sm-6 col-xs-12 pl10">
															<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 hand" onclick="reasonFile(this,'supplierConAch');">承包合同主要页及保密协议：</span>
															<u:show showId="conAch_show" delete="false" businessId="${supplierId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierConAch}" />
														</li>
													</c:if>
													<li class="col-md-12 col-xs-12 col-sm-12 mb25">
														<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">国家或军队保密工程业绩：</span>
														<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
															<textarea class="col-md-12 col-xs-12 col-sm-12 h80 hand" id="confidentialAchievement_engineering" onclick="reasonEngineering1(this)">${supplierMatEngs.confidentialAchievement}</textarea>
														</div>
													</li>
												</ul>

												<h2 class="count_flow"><i>2</i>承揽业务范围：省级行政区对应合同主要页 （体现甲乙双方盖章及工程名称、地点的相关页）</h2>
												<ul class="ul_list">
													<c:forEach items="${rootArea}" var="area" varStatus="st">
														<li class="col-md-3 col-sm-6 col-xs-12 pl15">
															<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 hand" onclick="reasonFile(this,'${area.name}');">${area.name}：</span>
															<u:show showId="area_show_${st.index+1}" delete="false" businessId="${supplierId}_${area.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierProContract}" />
														</li>
													</c:forEach>
												</ul>

												<h2 class="count_flow"><i>3</i>取得注册资质的人员信息</h2>
												<ul class="ul_list count_flow">
													<table class="table table-bordered table-condensed table-hover">
														<thead>
															<tr>
																<th class="info w50">序号</th>
																<th class="info">注册资格名称</th>
																<th class="info">注册人姓名</th>
															</tr>
														</thead>
														<c:forEach items="${listRegPerson}" var="regPrson" varStatus="vs">
															<tr>
																<td class="tc">${vs.index + 1}</td>
																<td class="tc">${regPrson.regType}</td>
																<td class="tc">${regPrson.regNumber}</td>
															</tr>
														</c:forEach>
													</table>
												</ul>

												<h2 class="count_flow"><i>4</i>供应商资质（认证）证书信息</h2>
												<div class="ul_list count_flow">
													<table class="table table-bordered table-condensed table-hover">
														<thead>
															<tr>
																<th class="info">证书名称</th>
																<th class="info">证书编号</th>
																<th class="info">资质等级</th>
																<th class="info">发证机关或机构</th>
																<th class="info">发证日期</th>
																<th class="info">有效截止日期</th>
																<th class="info">证书状态</th>
																<!-- <th class="info">证书图片</th> -->
															</tr>
														</thead>
														<c:forEach items="${supplierCertEng}" var="s" varStatus="vs">
															<tr>
																<td class="tc" id="certType_${s.id }">${s.certType }</td>
																<td class="tc" id="certCode_${s.id }">${s.certCode }</td>
																<td class="tc" id="certMaxLevel_${s.id }">${s.certMaxLevel }</td>
																<td class="tc" id="licenceAuthorith_${s.id }">${s.licenceAuthorith }</td>
																<td class="tc " id="expStartDate_${s.id }">
																	<fmt:formatDate value="${s.expStartDate }" pattern='yyyy-MM-dd' />
																</td>
																<td class="tc" id="expEndDate_${s.id }">
																	<fmt:formatDate value="${s.expEndDate }" pattern='yyyy-MM-dd' />
																</td>
																<td class="tc" id="certStatus_${s.id }">${s.certStatus}</td>
																<%-- <td class="tc">
																	<u:show showId="eng_show${vs.index+1}" delete="false" businessId="${s.id}" typeId="${supplierDictionaryData.supplierEngCert}" sysKey="${sysKey}" />
																</td> --%>
															</tr>
														</c:forEach>
													</table>
												</div>

												<h2 class="count_flow"><i>5</i>供应商资质证书详细信息</h2>
												<ul class="ul_list count_flow">
													<table class="table table-bordered table-condensed table-hover">
														<thead>
															<tr>
																<th class="info">证书名称</th>
																<th class="info">证书编号</th>
																<th class="info">资质类型</th>
																<th class="info">资质序列</th>
																<th class="info">专业类别</th>
																<th class="info">资质等级</th>
																<th class="info">是否主项资质</th>
																<th class="info w50">证书图片</th>
															</tr>
														</thead>
														<c:forEach items="${supplierAptitutes}" var="s" varStatus="vs">
															<tr>
																<td class="tc">${s.certName }</td>
																<td class="tc">${s.certCode }</td>
																<td class="tc">
																	<c:forEach items="${typeList}" var="type">
																		<c:if test="${s.certType eq type.id}">${type.name}</c:if>
																	</c:forEach>
																</td>
																<td class="tc">${s.aptituteSequence }</td>
																<td class="tc">${s.professType }</td>
																<td class="tc">${s.aptituteLevel }</td>
																<td class="tc">
																	<c:if test="${s.isMajorFund==0 }">否</c:if>
																	<c:if test="${s.isMajorFund==1 }">是</c:if>
																</td>
																<td>
																	<u:show showId="apt_show${vs.index+1}" delete="false" businessId="${s.id}" typeId="${supplierDictionaryData.supplierEngCert}" sysKey="${sysKey}" />
																</td>
															</tr>
														</c:forEach>
													</table>
												</ul>
											</div>
										</c:if>

										<c:if test="${fn:contains(supplierTypeNames, '服务')}">
											<div class="tab-pane <c:if test="${liCountSer==1}">active in</c:if> fade height-200" id="tab-4">
												<ul class="ul_list count_flow">
													<table class="table table-bordered table-condensed table-hover">
														<thead>
															<tr>
																<th class="info w50">序号</th>
																<th class="info">资质证书名称</th>
																<th class="info">证书编号</th>
																<th class="info">资质等级</th>
																<th class="info">发证机关或机构</th>
																<th class="info">有效期（起始时间）</th>
																<th class="info">有效期（结束时间）</th>
																<th class="info">证书状态</th>
																<th class="info">证书图片</th>
															</tr>
														</thead>
														<c:forEach items="${supplierCertSes}" var="s" varStatus="vs">
															<tr>
																<td class="tc">${vs.index + 1}</td>
																<td class="tc">${s.name }</td>
																<td class="tc">${s.code}</td>
																<td class="tc">${s.levelCert}</td>
																<td class="tc">${s.licenceAuthorith }</td>
																<td class="tc">
																	<fmt:formatDate value="${s.expStartDate }" pattern='yyyy-MM-dd' />
																</td>
																<td class="tc">
																	<fmt:formatDate value="${s.expEndDate }" pattern='yyyy-MM-dd' />
																</td>
																<td class="tc">${s.mot}</td>
																<td class="tc">
																	 <div class="w110 fl">
																		<u:show showId="ser_show${vs.index+1}" businessId="${s.id}" delete="false" typeId="${supplierDictionaryData.supplierServeCert}" sysKey="${sysKey}" />
																	 </div>
																</td>
															</tr>
														</c:forEach>
													</table>
												</ul>
												
											</div>
										</c:if>		
									</div>
									<div class="col-md-12 tc">
										<button class="btn btn-windows back" onclick="fanhui()">返回</button> 
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<form target="_blank" id="download_form_id" action="${pageContext.request.contextPath}/supplierAudit/download.html" method="post">
			<input type="hidden" name="fileName" />
		</form>
		<form id="form_id" action="" method="post">
			<input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
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
	</body>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/regex.js"></script>

</html>