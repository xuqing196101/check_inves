<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<title>资质文件</title>
		<script type="text/javascript">
			//默认不显示叉
			$(function() {
				$("td").each(function() {
					$(this).find("p").hide();
				});
			});
			
			//下一步
			function nextStep() {
				var action = "${pageContext.request.contextPath}/supplierAudit/contract.html";
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}

			//上一步
			function lastStep() {
				/*$("#form_id").attr("action", lastUrl);*/
				var action = "${pageContext.request.contextPath}/supplierAudit/items.html";
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}
			
			/* function reason(auditFieldName, auditContent, dex) {
				var supplierId = $("#supplierId").val();
				var index = layer.prompt({
						title: '请填写不通过的理由：',
						formType: 2,
						offset: '100px'
					},
					function(text) {
						$.ajax({
							url: "${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
							type: "post",
							data: {
								"auditType": "aptitude_page",
								"auditFieldName": auditFieldName,
								"auditContent": auditContent + "附件信息",
								"suggest": text,
								"supplierId": supplierId,
								"auditField": auditContent
							},
							dataType: "json",
							success: function(result) {
								result = eval("(" + result + ")");
								if(result.msg == "fail") {
									layer.msg('该条信息已审核过！', {
										shift: 6, //动画类型
										offset: '100px'
									});
								}
							}
						});

						$("#" + dex + "_hidden").hide();
						$("#" + dex + "_show").show();
						layer.close(index);
					});
			}
			 */
			
			function reason(auditField, auditFieldName, auditContent, str) {
				var supplierId = $("#supplierId").val();
				var index = layer.prompt({
					title: '请填写不通过的理由：',
					formType: 2,
					offset: '100px'
				}, function(text) {
					$.ajax({
						url: "${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
						type: "post",
						data: "&auditFieldName=" + auditFieldName + "&suggest=" + text + "&supplierId=" + supplierId + "&auditType=aptitude_page" + "&auditContent=" + auditContent + "&auditField=" + auditField,
						dataType: "json",
						success: function(result) {
							result = eval("(" + result + ")");
							if(result.msg == "fail") {
								layer.msg('该条信息已审核过！', {
									shift: 6, //动画类型
									offset: '100px'
								});
							}
						}
					});
					$("#" + auditField +"_"+str+"").show(); //显示叉
					layer.close(index);
				});
			}
		</script>
		<script type="text/javascript">
			function jump(str) {
				var action;
				if(str == "essential") {
					action = "${pageContext.request.contextPath}/supplierAudit/essential.html";
				}
				if(str == "financial") {
					action = "${pageContext.request.contextPath}/supplierAudit/financial.html";
				}
				/*if(str == "shareholder") {
					action = "${pageContext.request.contextPath}/supplierAudit/shareholder.html";
				}
				if(str == "materialProduction") {
					action = "${pageContext.request.contextPath}/supplierAudit/materialProduction.html";
				}
				if(str == "materialSales") {
					action = "${pageContext.request.contextPath}/supplierAudit/materialSales.html";
				}
				if(str == "engineering") {
					action = "${pageContext.request.contextPath}/supplierAudit/engineering.html";
				}
				if(str == "serviceInformation") {
					action = "${pageContext.request.contextPath}/supplierAudit/serviceInformation.html";
				}*/
				if(str == "items") {
					action = "${pageContext.request.contextPath}/supplierAudit/items.html";
				}
				if(str == "aptitude") {
					action = "${pageContext.request.contextPath}/supplierAudit/aptitude.html";
				}
				if(str == "contract") {
					action = "${pageContext.request.contextPath}/supplierAudit/contract.html";
				}
				if(str == "applicationForm") {
					action = "${pageContext.request.contextPath}/supplierAudit/applicationForm.html";
				}
				if(str == "reasonsList") {
					action = "${pageContext.request.contextPath}/supplierAudit/reasonsList.html";
				}
				if(str == "supplierType") {
					action = "${pageContext.request.contextPath}/supplierAudit/supplierType.html";
				}
				$("#form_id").attr("action", action);
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
						<a href="#"> 首页</a>
					</li>
					<li>
						<a href="#">支撑环境</a>
					</li>
					<li>
						<a href="#">供应商管理</a>
					</li>
					<li>
						<a href="#">供应商审核</a>
					</li>
				</ul>
			</div>
		</div>
		<div class="container container_box">
			<div class="content ">
				<div class="col-md-12 tab-v2 job-content">
					<ul class="flow_step">
						<li onclick="jump('essential')">
							<a aria-expanded="false" href="#tab-1">详细信息</a>
							<i></i>
						</li>
						<li onclick="jump('financial')">
							<a aria-expanded="true" href="#tab-2">财务信息</a>
							<i></i>
						</li>
						<li onclick="jump('shareholder')">
							<a aria-expanded="false" href="#tab-3">股东信息</a>
							<i></i>
						</li>
						<%--<c:if test="${fn:contains(supplierTypeNames, '生产')}">
							<li onclick="jump('materialProduction')">
								<a aria-expanded="false" href="#tab-4">生产信息</a>
								<i></i>
							</li>
						</c:if>
						<c:if test="${fn:contains(supplierTypeNames, '销售')}">
							<li onclick="jump('materialSales')">
								<a aria-expanded="false" href="#tab-4">销售信息</a>
								<i></i>
							</li>
						</c:if>
						<c:if test="${fn:contains(supplierTypeNames, '工程')}">
							<li onclick="jump('engineering')">
								<a aria-expanded="false" href="#tab-4">工程信息</a>
								<i></i>
							</li>
						</c:if>
						<c:if test="${fn:contains(supplierTypeNames, '服务')}">
							<li onclick="jump('serviceInformation')">
								<a aria-expanded="false" href="#tab-4" data-toggle="tab">服务信息</a>
								<i></i>
							</li>
						</c:if>
						--%>
						<li onclick = "jump('supplierType')">
           	  <a aria-expanded="false">供应商类型</a>
            	<i></i>
	          </li>
						<li onclick="jump('items')">
							<a aria-expanded="false" href="#tab-4">品目信息</a>
							<i></i>
						</li>
						<li onclick="jump('aptitude')" class="active">
							<a aria-expanded="false" href="#tab-4">资质文件</a>
							<i></i>
						</li>
						<li onclick="jump('contract')">
							<a aria-expanded="false" href="#tab-4">品目合同</a>
							<i></i>
						</li>
						<li onclick="jump('applicationForm')">
							<a aria-expanded="false" href="#tab-4">申请表</a>
							<i></i>
						</li>
						<li onclick="jump('reasonsList')">
							<a aria-expanded="false" href="#tab-4">审核汇总</a>
						</li>
					</ul>
					<ul class="count_flow ul_list count_flow">
						<ul id="page_ul_id" class="nav nav-tabs bgdd supplier_tab">
						<c:set value="0" var="liCount"/>
							<c:if test="${fn:contains(supplierTypeNames, '生产')}">
							<c:set value="${liCount+1}" var="liCount"/>
								<li id="li_id_1" class="active">
									<a aria-expanded="true" href="#tab-1" data-toggle="tab">物资-生产型品目信息</a>
								</li>
							</c:if>
							<c:if test="${fn:contains(supplierTypeNames, '销售')}">
								<li id="li_id_2" class='<c:if test="${liCount == 0}">active</c:if>'>
									<a aria-expanded="false" href="#tab-2" data-toggle="tab">物资-销售型品目信息</a>
								</li>
								<c:set value="${liCount+1}" var="liCount"/>
							</c:if>
							<c:if test="${fn:contains(supplierTypeNames, '工程')}">
								<li id="li_id_3" class='<c:if test="${liCount == 0}">active</c:if>'>
									<a aria-expanded="false" href="#tab-3" data-toggle="tab">工程品目信息</a>
									<c:set value="${liCount+1}" var="liCount"/>
								</li>
							</c:if>
							<c:if test="${fn:contains(supplierTypeNames, '服务')}">
								<li id="li_id_4" class='<c:if test="${liCount == 0}">active</c:if>'>
									<a aria-expanded="false" href="#tab-4" data-toggle="tab">服务品目信息</a>
									<c:set value="${liCount+1}" var="liCount"/>
								</li>
							</c:if>
						</ul>

						<div class="tab-content padding-top-20" id="tab_content_div_id">

							<!-- 物资生产型 -->
							<c:if test="${fn:contains(supplierTypeNames, '生产')}">
								<c:set value="0" var="prolength" />
								<div class="tab-pane fade active in height-300" id="tab-1">
									<table class="table table-bordered">
										<!-- <thead>
											<tr>
												<th class="tc info">名称</th>
												<th class="tc info">环保管理体系认证证书</th>
												<th class="tc info">国家行业准入证书</th>
												<th class="tc info">质量管理体系认证证书</th>
												<th class="tc info">审核操作</th>
											</tr>
										</thead> -->
										<c:forEach items="${cateList }" var="obj" varStatus="vs">
											<tr>
												<td class="tc info">${obj.categoryName } </td>
												<c:forEach items="${obj.list }" var="quaPro">
													<td>
														<c:set value="${prolength+1}" var="prolength"></c:set>
														<span class="hand" onclick="reason('${quaPro.id}','${obj.categoryName }','生产-${quaPro.name}','生产');" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'">${quaPro.name}：</span>
														<u:show showId="pShow${prolength}" groups="${saleShow}" delete="false" businessId="${quaPro.id}" sysKey="1" typeId="1" />
														<p id="${quaPro.id}_生产" ><img style="padding-left: 20px;" src='/zhbj/public/backend/images/sc.png'></p>
													</td>
												</c:forEach>
												<%-- <td class="tc w100">
													<a onclick="reason('物资生产品目信息','${obj.categoryName }','${vs.index + 1}');" id="${vs.index + 1}_hidden" class="btn">审核</a>
													<p id="${vs.index + 1}_show"><img src='/zhbj/public/backend/images/sc.png'></p>
												</td> --%>
											</tr>
										</c:forEach>
									</table>
								</div>
							</c:if>

							<!-- 物资销售型 -->
							<c:if test="${fn:contains(supplierTypeNames, '销售')}">
								<c:set value="0" var="length"> </c:set>
								<div class="tab-pane <c:if test="${liCount == 1}">active in</c:if> fade height-300" id="tab-2">
									<table class="table table-bordered">
										<!-- <thead>
											<tr>
												<th class="tc info">名称</th>
												<th class="tc info">环保管理体系认证证书</th>
												<th class="tc info">国家行业准入证书</th>
												<th class="tc info">质量管理体系认证证书</th>
												<th class="tc info">审核操作</th>
											</tr>
										</thead> -->
										<c:forEach items="${saleQua }" var="sale">
											<tr>
												<td class="tc info">${sale.categoryName } </td>
												<c:forEach items="${sale.list }" var="saua" varStatus="vs">
													<td>
														<c:set value="${length+1}" var="length"></c:set>
														<span class="hand" onclick="reason('${saua.id}','${sale.categoryName }','销售-${saua.name}','销售');" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'">${saua.name}：</span>
														<u:show showId="saleShow${length}" groups="${saleShow}" delete="false" businessId="${saua.id}" sysKey="1" typeId="1" />
														<p id="${saua.id}_销售" ><img style="padding-left: 20px;" src='/zhbj/public/backend/images/sc.png'></p>
													</td>
												</c:forEach>
												<%-- <td class="tc w100">
													<a onclick="reason('物资销售品目信息','${sale.categoryName }','${vs.index + 1}');" id="${vs.index + 1}_hidden" class="btn">审核</a>
													<p id="${vs.index + 1}_show"><img src='/zhbj/public/backend/images/sc.png'></p>
												</td> --%>
											</tr>
										</c:forEach>
									</table>
								</div>
							</c:if>

							<!-- 工程 -->
							<c:if test="${fn:contains(supplierTypeNames, '工程')}">
								<div class="tab-pane <c:if test="${liCount == 1}">active in</c:if> fade height-200" id="tab-3">
									<table class="table table-bordered">
										<c:set value="0" var="plength"> </c:set>
										<!-- <thead>
											<tr>
												<th class="tc info">名称</th>
												<th class="tc info">环保管理体系认证证书</th>
												<th class="tc info">国家行业准入证书</th>
												<th class="tc info">质量管理体系认证证书</th>
												<th class="tc info">审核操作</th>
											</tr>
										</thead> -->

										<c:forEach items="${projectQua }" var="project">
											<tr>
												<td class="info">${project.categoryName }
													<c:forEach items="${project.list }" var="pr" varStatus="vs">
														<td class="tc">
															<c:set value="${plength+1}" var="plength"></c:set>
															<span class="hand" onclick="reason('${pr.id}','${project.categoryName }','工程-${pr.name}','工程');" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'">${pr.name}：</span>
															<u:show showId="projectShow${plength}" delete="false" groups="${saleShow}" businessId="${pr.id}" sysKey="1" typeId="1" />
															<p id="${pr.id}_工程" ><img style="padding-left: 20px;" src='/zhbj/public/backend/images/sc.png'></p>
														</td>
													</c:forEach>
												<%-- <td class="tc w100">
													<a onclick="reason('工程品目信息','${project.categoryName }','${vs.index + 1}');" id="${vs.index + 1}_hidden" class="btn">审核</a>
													<p id="${vs.index + 1}_show"><img src='/zhbj/public/backend/images/sc.png'></p>
												</td> --%>
											</tr>
										</c:forEach>
									</table>
								</div>
							</c:if>

							<!-- 服务 -->
							<c:if test="${fn:contains(supplierTypeNames, '服务')}">
								<div class="tab-pane <c:if test="${liCount == 1}">active in</c:if> fade height-200" id="tab-4">
									<table class="table table-bordered">
										<c:set value="0" var="slength"> </c:set>
										<!-- <thead>
											<tr>
												<th class="tc info">名称</th>
												<th class="tc info">环保管理体系认证证书</th>
												<th class="tc info">国家行业准入证书</th>
												<th class="tc info">质量管理体系认证证书</th>
												<th class="tc info">审核操作</th>
											</tr>
										</thead> -->
										<c:forEach items="${serviceQua }" var="server">
											<tr>
												<td class="info">${server.categoryName }
												</td>
													<c:forEach items="${server.list }" var="ser" varStatus="vs">
														<td class="tc">
															<c:set value="${slength+1}" var="slength"></c:set>
															<span class="hand" onclick="reason('${ser.id}','${server.categoryName }','服务-${ser.name}','服务');" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'">${ser.name}：</span>
															<u:show showId="serverShow${plength}" delete="false" groups="${saleShow}" businessId="${ser.id}" sysKey="1" typeId="1" />
															<p id="${ser.id}_服务"><img src='/zhbj/public/backend/images/sc.png'></p>
														</td>
													</c:forEach>
												
												<%-- <td class="tc w100">
													<a onclick="reason('服务品目信息','${ser.categoryName }','${vs.index + 1}');" id="${vs.index + 1}_hidden" class="btn">审核</a>
													<p id="${saua.id}_销售" ><img style="padding-left: 20px;" src='/zhbj/public/backend/images/sc.png'></p>
												</td> --%>
											</tr>
										</c:forEach>
									</table>
								</div>
							</c:if>
						</div>
						
						</ul>
						<div class="col-md-12 col-sm-12 col-xs-12 add_regist tc">
			<a class="btn" type="button" onclick="lastStep();">上一步</a>
			<a class="btn" type="button" onclick="nextStep();">下一步</a>
		</div>
				</div>
				
			
				
			</div>
		</div>
		
		
		
		<form id="form_id" action="" method="post">
			<input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
		</form>
	</body>

</html>