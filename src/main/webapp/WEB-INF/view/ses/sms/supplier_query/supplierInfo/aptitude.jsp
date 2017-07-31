<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<title>资质文件</title>
		<%@ include file="/WEB-INF/view/ses/sms/supplier_query/supplierInfo/common.jsp"%>
		<script type="text/javascript" src="${ pageContext.request.contextPath }/js/ses/ems/expertQuery/common.js"></script>
	</head>

	<body>
		<!--面包屑导航开始-->
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
			<div class="content ">
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
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('item');">产品类别</a>
						</li>
						<li class="active">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('zizhi');">资质文件</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('contract');">销售合同</a>
						</li>
						<li class="">
              <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('audit');">审核信息</a>
            </li>
					</ul>
					<div class="ul_list">
						<ul id="page_ul_id" class="nav nav-tabs bgdd supplier_tab">
							<c:set value="0" var="liCountPro" />
							<c:set value="0" var="liCountSell" />
							<c:set value="0" var="liCountEng" />
							<c:set value="0" var="liCountSer" />
							<c:if test="${fn:contains(supplierTypeNames, '生产') and fn:length(cateList) > 0}">
							<c:set value="${liCountPro+1}" var="liCountPro"/>
								<li id="li_id_1" class="active">
									<a aria-expanded="true" href="#tab-1" data-toggle="tab">物资-生产型资质信息</a>
								</li>
							</c:if>
							<c:if test="${fn:contains(supplierTypeNames, '销售') and fn:length(saleQua) > 0}">
								<li id="li_id_2" class='<c:if test="${liCountPro == 0}">active <c:set value="${liCountSell+1}" var="liCountSell"/></c:if>'>
									<a aria-expanded="false" href="#tab-2" data-toggle="tab">物资-销售型资质信息</a>
								</li>
							</c:if>
							<c:if test="${fn:contains(supplierTypeNames, '工程')}">
								<li id="li_id_3" class='<c:if test="${liCountSell == 0  && liCountPro == 0}">active <c:set value="${liCountEng+1}" var="liCountEng"/></c:if>'>
									<a aria-expanded="false" href="#tab-3" data-toggle="tab">工程资质信息</a>
								</li>
							</c:if>
							<c:if test="${fn:contains(supplierTypeNames, '服务') and fn:length(serviceQua) > 0}">
								<li id="li_id_4" class='<c:if test="${liCountEng == 0 && liCountPro == 0 && liCountEng == 0}">active <c:set value="${liCountSer+1}" var="liCountSer"/></c:if>'>
									<a aria-expanded="false" href="#tab-4" data-toggle="tab">服务资质信息</a>
								</li>
							</c:if>
						</ul>

						<div class="tab-content padding-top-20" id="tab_content_div_id">

							<!-- 物资生产型 -->
							<c:if test="${fn:contains(supplierTypeNames, '生产')}">
								<c:set value="0" var="prolength" />
								<div class="tab-pane fade active in" id="tab-1">
									<table class="table table-bordered">
										<c:forEach items="${cateList }" var="obj" varStatus="vs">
											<tr>
												<td class="tc info">${obj.categoryName } </td>
												<c:forEach items="${obj.list }" var="quaPro">
													<td>
														<c:set value="${prolength+1}" var="prolength"></c:set>
														<span class="hand" onclick="reason('${quaPro.flag}','${obj.categoryName }','生产-${quaPro.name}');" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'">${quaPro.name}：</span>
														<u:show showId="pShow${prolength}" groups="${saleShow}" delete="false" businessId="${quaPro.flag}" sysKey="${sysKey }" typeId="${typeId}" />
														<p id="${quaPro.flag}"></p>
													</td>
												</c:forEach>
											</tr>
										</c:forEach>
									</table>
								</div>
							</c:if>

							<!-- 物资销售型 -->
							<c:if test="${fn:contains(supplierTypeNames, '销售')}">
								<c:set value="0" var="length"> </c:set>
								<div class="tab-pane <c:if test=" ${liCountSell==1 } ">active in</c:if> fade height-300" id="tab-2">
									<table class="table table-bordered">
										<c:forEach items="${saleQua }" var="sale">
											<tr>
												<td class="tc info">${sale.categoryName } </td>
												<c:forEach items="${sale.list }" var="saua" varStatus="vs">
													<td>
														<c:set value="${length+1}" var="length"></c:set>
														<span class="hand" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'">${saua.name}：</span>
														<u:show showId="saleShow${length}" groups="${saleShow}" delete="false" businessId="${saua.flag}" sysKey="${sysKey }" typeId="${typeId}" />
														<p id="${saua.flag}"></p>
													</td>
												</c:forEach>
											</tr>
										</c:forEach>
									</table>
								</div>
							</c:if>

							<!-- 工程 -->
							<c:if test="${fn:contains(supplierTypeNames, '工程')}">
								<div class="tab-pane <c:if test="${liCountEng==1}">active in</c:if> fade height-200" id="tab-3">
									<c:set value="0" var="plength"> </c:set>
									<table class="table table-bordered">
										<thead>
											<tr>
												<th class="info tc w50">序号</th>
												<!-- <th class="info tc">类别</th>
												<th class="info tc">大类</th>
												<th class="info tc">中类</th> -->
												<th class="info tc">产品类别</th>
												<th class="info tc">资质类型</th>
												<th class="info tc">证书编号</th>
												 <th class="info tc">专业类别</th>
												<th class="info tc">资质等级</th>
												<th class="info tc">证书图片</th>
											</tr>
										</thead>
										<c:forEach items="${allTreeList}" var="cate" varStatus="vs">
											<tr>
												<td><div class="w50">${vs.index + 1}</div></td>
												<td>${cate.rootNode}</td>
												<td>${cate.firstNode}</td>
												<td>${cate.secondNode}</td>
												<td>
													<c:forEach items="${cate.typeList}" var="type">
														<c:if test="${cate.qualificationType eq type.id}">${type.name}</c:if>
													</c:forEach>
												</td>
												<td>
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
								      	</td>
												<td>
												<div class="w110 fl">
													<u:show showId="eng_show_${vs.index}" businessId="${cate.fileId}" typeId="${engTypeId}" sysKey="${sysKey}" delete="false" />
												</div>
												</td>
												
											</tr>
										</c:forEach>
									</table>
								</div>
							</c:if>

							<!-- 服务 -->
							<c:if test="${fn:contains(supplierTypeNames, '服务')}">
								<div class="tab-pane <c:if test=" ${liCountSer==1 } ">active in</c:if> fade height-200" id="tab-4">
									<table class="table table-bordered">
										<c:set value="0" var="slength"> </c:set>
										<c:forEach items="${serviceQua }" var="server">
											<tr>
												<td class="info">${server.categoryName }
												</td>
												<c:forEach items="${server.list }" var="ser" varStatus="vs">
													<td class="tc">
														<c:set value="${slength+1}" var="slength"></c:set>
														<span class="hand" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'">${ser.name}：</span>
														<u:show showId="serverShow${plength}" delete="false" groups="${saleShow}" businessId="${ser.flag}" sysKey="${sysKey }" typeId="${typeId}" />
														<p id="${ser.flag}"></p>
													</td>
												</c:forEach>
											</tr>
										</c:forEach>
									</table>
								</div>
							</c:if>
						</div>
					</div>
					<div class="col-md-12 tc">
			    	<button class="btn btn-windows back" onclick="fanhui()">返回</button> 
			   	</div>
				</div>
			</div>
		</div>

		<form id="form_id" action="" method="post">
			<input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
			<input name="judge" value="${judge}" type="hidden">
			<input name="sign" value="${sign}" type="hidden">
		</form>
		
		<form id="form_back" action="" method="post">
			<input name="judge" value="${judge}" type="hidden">
			<c:if test="${sign!=1 and sign!=2 }">
				<input name="address" id="address" value="${suppliers.address}" type="hidden">
			</c:if>
			<input name="sign" value="${sign}" type="hidden">
		</form>
	</body>

</html>