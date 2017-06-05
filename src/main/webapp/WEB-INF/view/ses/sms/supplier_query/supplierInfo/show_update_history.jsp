<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en">
	<!--<![endif]-->

	<head>
		<%@ include file="../../../../common.jsp"%>
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

			function yincang() {
				$("div").removeClass("dnone");
			}
			
			function fanhui() {
				if('${judge}' == 2) {
					window.location.href = "${pageContext.request.contextPath}/supplierQuery/selectByCategory.html";
				} else {
					window.location.href = "${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html?address=" + encodeURI(encodeURI('${suppliers.address}')) + "&judge=${judge}";
				}
			}
		</script>
	</head>

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
		<!-- 项目戳开始 -->
		<div class="container clear margin-top-30">
			<!-- <div class="container">
   <div class="col-md-12">
    <button class="btn btn-windows back" onclick="fanhui()">返回</button> 
    </div>
    </div> -->
			<!--详情开始-->
			<div class="container content pt0">
				<div class="tab-v2">
					<ul class="nav nav-tabs bgwhite">
						<li class="">
							<a aria-expanded="true" class="f18" href="#tab-1" data-toggle="tab" onclick="tijiao('essential');">基本信息</a>
						</li>
						<li class="">
							<a aria-expanded="false" class="f18" href="#tab-2" data-toggle="tab" onclick="tijiao('financial');">财务信息</a>
						</li>
						<li class="">
							<a aria-expanded="fale" class="f18" href="#tab-3" data-toggle="tab" onclick="tijiao('shareholder');">股东信息</a>
						</li>
					   <li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('supplierType');">供应商类型</a>
						</li>
						<li class="">
							<a aria-expanded="false" class="f18" href="#tab-2" data-toggle="tab" onclick="tijiao('item');">品目信息</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('zizhi');">资质文件</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('contract');">品目合同</a>
						</li>
						<li class="">
							<a aria-expanded="false" class="f18" href="#tab-2" data-toggle="tab" onclick="tijiao('chengxin');">诚信记录</a>
						</li>
						<li class="active">
							<a aria-expanded="false" class="f18" href="#tab-2" data-toggle="tab" onclick="tijiao('updateHistory');">历史修改记录</a>
						</li>
					</ul>
					<div class="padding-top-20"></div>
					<form id="form_id" action="" method="post">
						<input name="supplierId" id="id" value="${suppliers.id }" type="hidden">
						<input name="judge" value="${judge}" type="hidden">
					</form>
					<c:if test="${not empty list }">
						<c:forEach items="${list }" var="record" varStatus="vs">
							<c:if test="${vs.index<5 }">
								<div class=" margin-bottom-0">
									<div class="tml_container padding-top-10">
										<div class="dingwei">
											<div class="tml_spine">
												<span class="tml_spine_bg"></span>
												<span id="timeline_start_point" class="start_point"></span>
											</div>
											<div class="tml_poster" id="post_area">
												<div class="poster" id="poster_1">
													<div class=" margin-bottom-0">
														<h2 class="history_icon">修改记录</h2>
														<div class="padding-left-40">
															<c:set value="${fn:substringBefore(record, '^-^')}" var="records"></c:set>
															<ul>
																<li></li>${fn:replace(records,"null", " ")}</ul>
														</div>
													</div>
												</div>
												<div class="period_header"><span>${fn:substringAfter(record, "^-^")}  </span></div>
												<span class="ui_left_arrow"><span class="ui_arrow"></span></span>
												<div class="clear"></div>
											</div>
										</div>
									</div>
								</div>
							</c:if>
							<c:if test="${vs.index==5}">
								<span class="hand" onclick="yincang()"><b>点击更多...</b></span>
							</c:if>
							<c:if test="${vs.index>4}">
								<div class="dnone margin-bottom-0">
									<div class="tml_container padding-top-10">
										<div class="dingwei">
											<div class="tml_spine">
												<span class="tml_spine_bg"></span>
												<span id="timeline_start_point" class="start_point"></span>
											</div>
											<div class="tml_poster" id="post_area">
												<div class="poster" id="poster_1">
													<div class=" margin-bottom-0">
														<h2 class="history_icon">修改记录</h2>
														<div class="padding-left-40">
															<c:set value="${fn:substringBefore(record, '^-^')}" var="records"></c:set>
															<ul>
																<li></li>${fn:replace(records,"null", " ")}</ul>
														</div>
													</div>
												</div>
												<div class="period_header"><span>${fn:substringAfter(record, "^-^")}  </span></div>
												<span class="ui_left_arrow"><span class="ui_arrow"></span></span>
												<div class="clear"></div>
											</div>
										</div>
									</div>
								</div>
							</c:if>
						</c:forEach>
					</c:if>
					<c:if test="${empty list }">
						<div class=" margin-bottom-0">
							<div class="tml_container padding-top-10">
								<div class="dingwei">
									<div class="tml_spine">
										<span class="tml_spine_bg"></span>
										<span id="timeline_start_point" class="start_point"></span>
									</div>
									<div class="tml_poster" id="post_area">
										<div class="poster" id="poster_1">
											<div class=" margin-bottom-0">
												<h2 class="history_icon">修改记录</h2>
												<div class="padding-left-40">
													<span>暂无修改记录 </span>
												</div>
											</div>
										</div>
										<div class="period_header"><span>  </span></div>
										<span class="ui_left_arrow"><span class="ui_arrow"></span></span>
										<div class="clear"></div>
									</div>
								</div>
							</div>
						</div>
					</c:if>
				</div>
				<div class="col-md-12 tc">
			    	<button class="btn btn-windows back" onclick="fanhui()">返回</button> 
			   	</div>
			</div>
	</body>

</html>