<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<title>品目合同</title>
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
				if (str == "zizhi") {
					action = "${pageContext.request.contextPath}/supplierQuery/aptitude.html";
				}
				if (str == "contract") {
					action = "${pageContext.request.contextPath}/supplierQuery/contract.html";
				}
				if(str == "supplierType") {
					action = "${pageContext.request.contextPath}/supplierQuery/supplierType.html";
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
						<a href="#">供应商查看</a>
					</li>
				</ul>
			</div>
		</div>
		<div class="container container_box">
			<div class="content">
				<div class="col-md-12 tab-v2 job-content">
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
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('supplierType');">供应商类型</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('item');">品目信息</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('zizhi');">资质文件</a>
						</li>
						<li class="active">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('contract');">品目合同</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('chengxin');">诚信记录</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('updateHistory');">历史修改记录</a>
						</li>
					</ul>
					<ul class="count_flow ul_list hand">
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
								<li id="li_id_3" class="<c:if test="${liCount == 0}">active</c:if>">
									<a aria-expanded="false" href="#tab-3" data-toggle="tab">工程品目信息</a>
								</li>
								<c:set value="${liCount+1}" var="liCount"/>
							</c:if>
							<c:if test="${fn:contains(supplierTypeNames, '服务')}">
								<li id="li_id_4" class="<c:if test="${liCount == 0}">active</c:if>">
									<a aria-expanded="false" href="#tab-4" data-toggle="tab">服务品目信息</a>
								</li>
								<c:set value="${liCount+1}" var="liCount"/>
							</c:if>
						</ul>
						<div class="count_flow">
							<div class="tab-content padding-top-20" id="tab_content_div_id">
								<c:if test="${fn:contains(supplierTypeNames, '生产')}">
									<div class="tab-pane fade active in height-300" id="tab-1">
										<table class="table table-bordered">
											<tr>
												<td class="tc info"> 品目名称</td>
												<td colspan="3" class="tc info">合同上传</td>
												<td colspan="3" class="tc info">收款进账单</td>
											</tr>
											<tr>
												<td class="tc info"> 末级节点</td>
												<c:forEach items="${years}" var="year">
													<td class="tc info">${year}</td>
												</c:forEach>
												<c:forEach items="${years}" var="year">
													<td class="tc info">${year}</td>
												</c:forEach>
											</tr>
											<c:forEach items="${contract}" var="obj" varStatus="vs">
												<tr>
													<td class="">${obj.name }</td>
													<td class="">
														<u:show showId="pShow${(vs.index+1)*6-1}" delete="false" groups="${sbShow}" businessId="${obj.id}" sysKey="${sysKey }" typeId="${obj.oneContract}" />
													</td>
													<td class="">
														<u:show showId="pShow${(vs.index+1)*6-2}" delete="false" groups="${sbShow}" businessId="${obj.id}" sysKey="${sysKey }" typeId="${obj.twoContract}" />
													</td>
													<td class="">
														<u:show showId="pShow${(vs.index+1)*6-3}" delete="false" groups="${sbShow}" businessId="${obj.id}" sysKey="${sysKey }" typeId="${obj.threeContract}" />
													</td>
													<td class="">
														<u:show showId="pShow${(vs.index+1)*6-4}" delete="false" groups="${sbShow}" businessId="${obj.id}" sysKey="${sysKey }" typeId="${obj.oneBil}" />
													</td>
													<td class="">
														<u:show showId="pShow${(vs.index+1)*6-5}" delete="false" groups="${sbShow}" businessId="${obj.id}" sysKey="${sysKey }" typeId="${obj.twoBil}" />
													</td>
													<td class="">
														<u:show showId="pShow${(vs.index+1)*6-6}" delete="false" groups="${sbShow}" businessId="${obj.id}" sysKey="${sysKey }" typeId="${obj.threeBil}" />
													</td>
												</tr>
											</c:forEach>
										</table>
									</div>
								</c:if>
								<c:if test="${fn:contains(supplierTypeNames, '销售')}">
									<div class="tab-pane <c:if test="${liCount == 1}">active in</c:if> fade height-300" id="tab-2">
										<table class="table table-bordered">
											<tr>
												<td class="tc info"> 品目名称</td>
												<td colspan="3" class="tc info">合同上传</td>
												<td colspan="3" class="tc info">收款进账单</td>
											</tr>
											<tr>
												<td class="tc info"> 末级节点</td>
												<c:forEach items="${years}" var="year">
													<td class="tc info">${year}</td>
												</c:forEach>
												<c:forEach items="${years}" var="year">
													<td class="tc info">${year}</td>
												</c:forEach>
											</tr>
											<c:forEach items="${saleBean}" var="obj" varStatus="vs">
												<tr>
													<td class="tc">${obj.name }</td>
													<td class="">
														<u:show showId="saleShow${(vs.index+1)*6-1}" delete="false" groups="${sbShow}" businessId="${obj.id}" sysKey="${sysKey }" typeId="${obj.oneContract}" />
													</td>
													<td class="">
														<u:show showId="saleShow${(vs.index+1)*6-2}" delete="false" groups="${sbShow}" businessId="${obj.id}" sysKey="${sysKey }" typeId="${obj.twoContract}" />
													</td>
													<td class="">
														<u:show showId="saleShow${(vs.index+1)*6-3}" delete="false" groups="${sbShow}" businessId="${obj.id}" sysKey="${sysKey }" typeId="${obj.threeContract}" />
													</td>
													<td class="">
														<u:show showId="saleShow${(vs.index+1)*6-4}" delete="false" groups="${sbShow}" businessId="${obj.id}" sysKey="${sysKey }" typeId="${obj.oneBil}" />
													</td>
													<td class="">
														<u:show showId="saleShow${(vs.index+1)*6-5}" delete="false" groups="${sbShow}" businessId="${obj.id}" sysKey="${sysKey }" typeId="${obj.twoBil}" />
													</td>
													<td class="">
														<u:show showId="saleShow${(vs.index+1)*6-6}" delete="false" groups="${sbShow}" businessId="${obj.id}" sysKey="${sysKey }" typeId="${obj.threeBil}" />
													</td>
												</tr>
											</c:forEach>
										</table>
									</div>
								</c:if>
								<c:if test="${fn:contains(supplierTypeNames, '工程')}">
									<div class="tab-pane <c:if test="${liCount == 1}">active in</c:if> fade height-200" id="tab-3">
										<table class="table table-bordered">
											<tr>
												<td class="tc info"> 品目名称</td>
												<td colspan="3" class="tc info">合同上传</td>
												<td colspan="3" class="tc info">收款进账单</td>
											</tr>
											<tr>
												<td class="tc info"> 末级节点</td>
												<c:forEach items="${years}" var="year">
													<td class="tc info">${year}</td>
												</c:forEach>
												<c:forEach items="${years}" var="year">
													<td class="tc info">${year}</td>
												</c:forEach>
											</tr>
											<c:forEach items="${projectBean}" var="obj" varStatus="vs">
												<tr>
													<td class="tc">${obj.name }</td>
													<td class="">
														<u:show showId="projectShow${(vs.index+1)*6-1}" delete="false" groups="${sbShow}" businessId="${obj.id}" sysKey="${sysKey }" typeId="${obj.oneContract}" />
													</td>
													<td class="">
														<u:show showId="projectShow${(vs.index+1)*6-2}" delete="false" groups="${sbShow}" businessId="${obj.id}" sysKey="${sysKey }" typeId="${obj.twoContract}" />
													</td>
													<td class="">
														<u:show showId="projectShow${(vs.index+1)*6-3}" delete="false" groups="${sbShow}" businessId="${obj.id}" sysKey="${sysKey }" typeId="${obj.threeContract}" />
													</td>
													<td class="">
														<u:show showId="projectShow${(vs.index+1)*6-4}" delete="false" groups="${sbShow}" businessId="${obj.id}" sysKey="${sysKey }" typeId="${obj.oneBil}" />
													</td>
													<td class="">
														<u:show showId="projectShow${(vs.index+1)*6-5}" delete="false" groups="${sbShow}" businessId="${obj.id}" sysKey="${sysKey }" typeId="${obj.twoBil}" />
													</td>
													<td class="">
														<u:show showId="projectShow${(vs.index+1)*6-6}" delete="false" groups="${sbShow}" businessId="${obj.id}" sysKey="${sysKey }" typeId="${obj.threeBil}" />
													</td>
												</tr>
											</c:forEach>
										</table>
									</div>
								</c:if>
								<c:if test="${fn:contains(supplierTypeNames, '服务')}">
									<div class="tab-pane <c:if test="${liCount == 1}">active in</c:if> fade height-200" id="tab-4">
										<table class="table table-bordered">
											<tr>
												<td class="tc info "> 品目名称</td>
												<td colspan="3" class="tc info">合同上传</td>
												<td colspan="3" class="tc info">收款进账单</td>
											</tr>
											<tr>
												<td class="tc info"> 末级节点</td>
												<c:forEach items="${years}" var="year">
													<td class="tc info">${year}</td>
												</c:forEach>
												<c:forEach items="${years}" var="year">
													<td class="tc info">${year}</td>
												</c:forEach>
											</tr>
											<c:forEach items="${serverBean}" var="obj" varStatus="vs">
												<tr>
													<td class="tc">${obj.name }</td>
													<td class="">
														<u:show showId="serpShow${(vs.index+1)*6-1}" delete="false" groups="${sbShow}" businessId="${obj.id}" sysKey="${sysKey }" typeId="${obj.oneContract}" />
													</td>
													<td class="">
														<u:show showId="serpShow${(vs.index+1)*6-2}" delete="false" groups="${sbShow}" businessId="${obj.id}" sysKey="${sysKey }" typeId="${obj.twoContract}" />
													</td>
													<td class="">
														<u:show showId="serpShow${(vs.index+1)*6-3}" delete="false" groups="${sbShow}" businessId="${obj.id}" sysKey="${sysKey }" typeId="${obj.threeContract}" />
													</td>
													<td class="">
														<u:show showId="serpShow${(vs.index+1)*6-4}" delete="false" groups="${sbShow}" businessId="${obj.id}" sysKey="${sysKey }" typeId="${obj.oneBil}" />
													</td>
													<td class="">
														<u:show showId="serpShow${(vs.index+1)*6-5}" delete="false" groups="${sbShow}" businessId="${obj.id}" sysKey="${sysKey }" typeId="${obj.twoBil}" />
													</td>
													<td class="">
														<u:show showId="serpShow${(vs.index+1)*6-6}" delete="false" groups="${sbShow}" businessId="${obj.id}" sysKey="${sysKey }" typeId="${obj.threeBil}" />
													</td>
												</tr>
											</c:forEach>
										</table>
									</div>
								</c:if>
							</div>
						</div>
					</ul>
				</div>
			</div>
		</div>
		<form id="form_id" action="" method="post">
			<input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
		</form>
	</body>

</html>