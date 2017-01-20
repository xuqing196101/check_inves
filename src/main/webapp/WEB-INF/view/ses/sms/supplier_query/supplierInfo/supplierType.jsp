<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<html>

	<head>
		<%@ include file="../../../../common.jsp"%>
		<title>供应商类型</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">

		<style type="text/css">
			td {
				cursor: pointer;
			}
			
			input {
				cursor: pointer;
			}
		</style>

		<script type="text/javascript">
			//默认不显示叉
			$(function() {
				$("td").each(function() {
					$(this).parent("tr").find("td").eq(6).find("a").hide();
				});

				$(":input").each(function() {
					var onMouseMove = "this.style.background='#E8E8E8'";
					var onmouseout = "this.style.background='#FFFFFF'";
					$(this).attr("onMouseMove", onMouseMove);
					$(this).attr("onmouseout", onmouseout);
				});
			});

			//生产
			function reasonProduction(id, str) {
				var supplierId = $("#supplierId").val();
				var auditContent = "证书名称为:" + str + "的信息";
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
								"auditType": "mat_pro_page",
								"auditFieldName": "生产-资质证书",
								"auditContent": auditContent,
								"suggest": text,
								"supplierId": supplierId,
								"auditField": id
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
						$("#" + id + "_show").show();
						$("#" + id + "_hidden").hide();
						layer.close(index);
					});
			}
			//生产
			function reasonProduction1(obj) {
				var supplierId = $("#supplierId").val();
				var appear = obj.id;
				var auditField = obj.id.replace("_production", "").trim();
				var auditContent;
				var auditFieldName;
				var html = "<a class='abolish' style='margin-top: 6px;'><img src='/zhbj/public/backend/images/sc.png'></a>";
				$("#" + obj.id + "").each(function() {
					auditFieldName = $(this).parents("li").find("span").text().replace("：", "").trim();
					auditContent = $(this).parents("li").find("input").val();
				});
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
								"auditType": "mat_pro_page",
								"auditFieldName": auditFieldName,
								"auditContent": auditContent,
								"suggest": text,
								"supplierId": supplierId,
								"auditField": auditField
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
						$(obj).after(html);
						$("#"+appear+"").css('border-color','#FF0000'); //边框变红色
						layer.close(index);
					});
			}

			//销售
			function reasonSale(id, str) {
				var supplierId = $("#supplierId").val();
				var auditContent = "资质证书名称" + str + "的信息";
				var index = layer.prompt({
					title: '请填写不通过的理由：',
					formType: 2,
					offset: '100px'
				}, function(text) {
					$.ajax({
						url: "${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
						type: "post",
						data: {
							"auditType": "mat_sell_page",
							"auditFieldName": "销售-资质证书",
							"auditContent": auditContent,
							"suggest": text,
							"supplierId": supplierId,
							"auditField": id
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
					$("#" + id + "_hidden").hide();
					$("#" + id + "_show").show();
					layer.close(index);
				});
			}

			function reasonSale1(obj) {
				var supplierId = $("#supplierId").val();
				var appear = obj.id;
				var auditField = obj.id.replace("_sale", "").trim();
				var auditContent;
				var auditFieldName;
				var html = "<a class='abolish' style='margin-top: 6px;'><img src='/zhbj/public/backend/images/sc.png'></a>";
				$("#" + obj.id + "").each(function() {
					auditFieldName = $(this).parents("li").find("span").text().replace("：", "").trim();
					auditContent = $(this).parents("li").find("input").val();
				});
				var index = layer.prompt({
					title: '请填写不通过的理由：',
					formType: 2,
					offset: '100px'
				}, function(text) {
					$.ajax({
						url: "${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
						type: "post",
						data: {
							"auditType": "mat_sell_page",
							"auditFieldName": auditFieldName,
							"auditContent": auditContent,
							"suggest": text,
							"supplierId": supplierId,
							"auditField": auditField
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
					$(obj).after(html);
					$("#"+appear+"").css('border-color','#FF0000'); //边框变红色
					layer.close(index);
				});
			}

			//工程
			function reasonEngineering(id, auditContent, str) {
				var supplierId = $("#supplierId").val();
				var auditFieldName = auditContent.replace("信息", "");
				if(auditFieldName == "工程-注册人员登记"){
					var auditContent = "注册名称为：" + str +"的信息";
				}else{
					var auditContent = "证书编号为：" + str +"的信息";
				}
				
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
								"auditType": "mat_eng_page",
								"auditFieldName": auditFieldName,
								"auditContent": auditContent,
								"suggest": text,
								"supplierId": supplierId,
								"auditField": id
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
						$("#" + id + "_hidden").hide();
						$("#" + id + "_hidden1").hide();
						$("#" + id + "_hidden2").hide();
						$("#" + id + "_show").css('visibility', 'visible');
						$("#" + id + "_show1").css('visibility', 'visible');
						$("#" + id + "_show2").css('visibility', 'visible');
						layer.close(index);
					});
			}

			//工程
			function reasonEngineering1(obj) {
				var supplierId = $("#supplierId").val();
			  var appear = obj.id;
				var auditField = obj.id.replace("_engineering", "").trim();
				var auditContent;
				var auditFieldName;
				var html = "<a class='abolish' style='margin-top: 6px;'><img src='/zhbj/public/backend/images/sc.png'></a>";
				$("#" + obj.id + "").each(function() {
					auditFieldName = $(this).parents("li").find("span").text().replace("：", "").trim();
					auditContent = $(this).parents("li").find("input").val();
				});

				var index = layer.prompt({
						title: '请填写不通过的理由：',
						formType: 2,
						offset: '100px',
					},
					function(text) {
						$.ajax({
							url: "${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
							type: "post",
							data: {
								"auditType": "mat_eng_page",
								"auditFieldName": auditFieldName,
								"auditContent": auditContent,
								"suggest": text,
								"supplierId": supplierId,
								"auditField": auditField
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
						/* $("#"+id3+"").show();
						$("#"+id3+"").parents("li").find("input").css("padding-right","30px"); */
						$(obj).after(html);
						$("#"+appear+"").css('border-color','#FF0000'); //边框变红色
						layer.close(index);
					});
			}
			//服务
			function reasonService(id, auditFieldName, str) {
				var supplierId = $("#supplierId").val();
				var auditContent = "资质证书名称为：" + str +"的信息";
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
								"auditType": "mat_serve_page",
								"auditFieldName": auditFieldName,
								"auditContent": auditContent,
								"suggest": text,
								"supplierId": supplierId,
								"auditField": id
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
						$("#" + id + "_hidden").hide();
						$("#" + id + "_show").show();
						layer.close(index);
					});
			}

			//服务
			function reasonService1(obj) {
				var supplierId = $("#supplierId").val();
				var appear = obj.id;
				var auditField = obj.id.replace("_service", "").trim();
				var auditContent;
				var auditFieldName;
				var html = "<a class='abolish' style='margin-top: 6px;'><img src='/zhbj/public/backend/images/sc.png'></a>";
				$("#" + obj.id + "").each(function() {
					auditFieldName = $(this).parents("li").find("span").text().replace("：", "").trim();
					auditContent = $(this).parents("li").find("input").val();
				});
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
								"auditType": "mat_serve_page",
								"auditFieldName": auditFieldName,
								"auditContent": auditContent,
								"suggest": text,
								"supplierId": supplierId,
								"auditField": auditField
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
						/* $("#"+id3+"").show();
						$("#"+id3+"").parents("li").find("input").css("padding-right","30px"); */
						$(obj).after(html);
						$("#"+appear+"").css('border-color','#FF0000'); //边框变红色
						layer.close(index);
					});
			}

			//下一步
			function nextStep(url) {
				var action = "${pageContext.request.contextPath}/supplierAudit/items.html";
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}

			//上一步
			function lastStep() {
				var action = "${pageContext.request.contextPath}/supplierAudit/shareholder.html";
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}

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

			// 提示退回修改之前的信息
			function isCompare(field, modifyType) {
				var supplierId = $("#supplierId").val();
				$.ajax({
					url: "${pageContext.request.contextPath}/supplierAudit/showModify.do",
					data: {
						"supplierId": supplierId,
						"beforeField": field,
						"modifyType": modifyType
					},
					async: false,
					success: function(result) {
						layer.tips("修改前:" + result, "#" + field, {
							tips: 1
						});
					}
				});
			}
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
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('item');">品目信息</a>
						</li>
							<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('zizhi');">资质文件</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('contract');">品目合同</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('chengxin');">诚信记录</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('updateHistory');">历史修改记录</a>
						</li>
					</ul>

					<ul id="page_ul_id" class="nav nav-tabs bgdd supplier_tab count_flow">
						<c:set value="0" var="liCount"/>
						<c:if test="${fn:contains(supplierTypeNames, '生产')}">
						<c:set value="${liCount+1}" var="liCount"/>
							<li class="active">
								<a aria-expanded="true" href="#tab-1" data-toggle="tab">物资-生产型信息</a>
							</li>
						</c:if>
						<c:if test="${fn:contains(supplierTypeNames, '销售')}">
							<li class='<c:if test="${liCount == 0}">active</c:if>'>
								<a aria-expanded="false" href="#tab-2" data-toggle="tab">物资-销售型信息</a>
							</li>
							<c:set value="${liCount+1}" var="liCount"/>
						</c:if>
						<c:if test="${fn:contains(supplierTypeNames, '工程')}">
							<li class='<c:if test="${liCount == 0}">active</c:if>'>
								<a aria-expanded="false" href="#tab-3" data-toggle="tab">工程信息</a>
							</li>
							<c:set value="${liCount+1}" var="liCount"/>
						</c:if>
						<c:if test="${fn:contains(supplierTypeNames, '服务')}">
							<li class='<c:if test="${liCount == 0}">active</c:if>'>
								<a aria-expanded="false" href="#tab-4" data-toggle="tab">服务信息</a>
							</li>
							<c:set value="${liCount+1}" var="liCount"/>
						</c:if>
					</ul>

					<div class="count_flow">
						<div class="tab-content padding-top-20" id="tab_content_div_id">
							<c:if test="${fn:contains(supplierTypeNames, '生产')}">
								<div class="tab-pane fade active in height-300" id="tab-1">
									<h2 class="count_flow"><i>1</i>供应商物资生产资质证书</h2>
									<div class="ul_list count_flow">
										<table class="table table-bordered table-condensed table-hover">
											<thead>
												<tr>
													<th class="info w50">序号</th>
													<th class="info">资质证书名称</th>
													<th class="info">资质等级</th>
													<th class="info">发证机关</th>
													<th class="info">有效期(起止时间)</th>
													<th class="info">是否年检</th>
												</tr>
											</thead>
											<c:forEach items="${materialProduction}" var="m" varStatus="vs">
												<tr>
													<td class="tc">${vs.index + 1}</td>
													<td class="tc" id="${m.id}">${m.name }</td>
													<td class="tc">${m.levelCert}</td>
													<td class="tc">${m.licenceAuthorith }</td>
													<td class="tc">
														<fmt:formatDate value="${m.expStartDate }" pattern='yyyy-MM-dd' /> 至
														<fmt:formatDate value="${m.expEndDate }" pattern='yyyy-MM-dd' />
													</td>
													<td class="tc">
														<c:if test="${m.mot==0 }">否</c:if>
														<c:if test="${m.mot==1 }">是</c:if>
													</td>
												</tr>
											</c:forEach>
										</table>
									</div>

									
									<div class="tab-pane fade active in">
										<h2 class="count_flow"><i>3</i>组织结构和人员</h2>
										<table class="table table-bordered table-condensed table-hover">
											<thead>
												<tr>
													<th class="info">组织机构</th>
													<th class="info">人员总数</th>
													<th class="info">管理人员</th>
													<th class="info">技术人员</th>
													<th class="info">工人(职员)</th>
												</tr>
											</thead>
												<tr>
													<td class="tc">${supplierMatPros.orgName }</td>
													<td class="tc">${supplierMatPros.totalPerson }</td>
													<td class="tc">${supplierMatPros.totalMange }</td>
													<td class="tc">${supplierMatPros.totalTech }</td>
													<td class="tc">${supplierMatPros.totalWorker }</td>
												</tr>
										</table>
									</div>

									
									
									<div class="tab-pane fade active in">
										<h2 class="count_flow"><i>3</i>产品研发能力</h2>
										<table class="table table-bordered table-condensed table-hover">
											<thead>
												
											</thead>
												<tr>
													<td class="tc">技术人员比例(%)：</td>
													<td class="tc">${supplierMatPros.scaleTech }</td>
													<td class="tc">高级技术人员比例(%)：</td>
													<td class="tc">${supplierMatPros.scaleHeightTech }</td>
													<td class="tc">研发部门名称：</td>
													<td class="tc">${supplierMatPros.researchName }</td>
													<td class="tc">研发部门人数：</td>
													<td class="tc">${supplierMatPros.totalResearch }</td>
												</tr>
												<tr>
													<td class="tc">研发部门负责人：</td>
													<td class="tc">${supplierMatPros.researchLead }</td>
													<td class="tc">国家军队科研项目：</td>
													<td class="tc">${supplierMatPros.countryPro }</td>
													<td class="tc">国家军队科技奖项：</td>
													<td class="tc">${supplierMatPros.countryReward }</td>
												</tr>
										</table>
									</div>
		
									
									<div class="tab-pane fade active in">
										<h2 class="count_flow"><i>4</i>供应商生产能力</h2>
										<table class="table table-bordered table-condensed table-hover">
											<thead>
											</thead>
												<tr>
													<td class="tc">生产线名称数量：</td>
													<td class="tc">${supplierMatPros.totalBeltline }</td>
													<td class="tc">生产设备名称数量：</td>
													<td class="tc">${supplierMatPros.totalDevice }</td>
												</tr>
										</table>
									</div>
		
									
									<div class="tab-pane fade active in">
										<h2 class="count_flow"><i>5</i>物资生产型供应商质量检测登记</h2>
										<table class="table table-bordered table-condensed table-hover">
											<thead>
											</thead>
												<tr>
													<th class="tc">质量检测部门</th>
													<th class="tc">质量检测人数</th>
													<th class="tc">质检部门负责人</th>
													<th class="tc">质量检测设备名称</th>
												</tr>
												<tr>
													<td class="tc">${supplierMatPros.qcName }</td>
													<td class="tc">${supplierMatPros.totalQc }</td>
													<td class="tc">${supplierMatPros.qcLead }</td>
													<td class="tc">${supplierMatPros.qcDevice }</td>
												</tr>
										</table>
									</div>
								</div>
							</c:if>

							<c:if test="${fn:contains(supplierTypeNames, '销售')}">
								<div class="tab-pane <c:if test="${liCount == 1}">active in</c:if> fade  in height-200" id="tab-2">
									<h2 class="count_flow"><i>1</i>供应商物资销售资质证书</h2>
									<ul class="ul_list">
										<table class="table table-bordered table-condensed table-hover">
											<thead>
												<tr>
													<th class="info w50">序号</th>
													<th class="info">资质证书名称</th>
													<th class="info">资质等级</th>
													<th class="info">发证机关</th>
													<th class="info">有效期(起止时间)</th>
													<th class="info">是否年检</th>
												</tr>
											</thead>
											<c:forEach items="${supplierCertSell}" var="s" varStatus="vs">
												<tr>
													<td class="tc">${vs.index + 1}</td>
													<td class="tl pl20" id="${s.id}">${s.name }</td>
													<td class="tc">${s.levelCert}</td>
													<td class="tc">${s.licenceAuthorith }</td>
													<td class="tc">
														<fmt:formatDate value="${s.expStartDate }" pattern='yyyy-MM-dd' /> 至
														<fmt:formatDate value="${s.expEndDate }" pattern='yyyy-MM-dd' />
													</td>
													<td class="tc">
														<c:if test="${s.mot==0 }">否</c:if>
														<c:if test="${s.mot==1 }">是</c:if>
													</td>
												</tr>
											</c:forEach>
										</table>
									</ul>

									
									<div class="tab-pane fade active in">
										<h2 class="count_flow"><i>2</i>供应商组织结构和人员</h2>
										<table class="table table-bordered table-condensed table-hover">
											<thead>
												<tr>
													<th class="info">组织机构</th>
													<th class="info">人员总数</th>
													<th class="info">管理人员</th>
													<th class="info">技术人员</th>
													<th class="info">工人(职员)</th>
												</tr>
											</thead>
												<tr>
													<td class="tc">${supplierMatPros.orgName }</td>
													<td class="tc">${supplierMatPros.totalPerson }</td>
													<td class="tc">${supplierMatPros.totalMange }</td>
													<td class="tc">${supplierMatPros.totalTech }</td>
													<td class="tc">${supplierMatPros.totalWorker }</td>
												</tr>
										</table>
									</div>
									
								</div>
							</c:if>

							<c:if test="${fn:contains(supplierTypeNames, '工程')}">
								<div class="tab-pane <c:if test="${liCount == 1}">active in</c:if> fade height-200" id="tab-3">
									<h2 class="count_flow"><i>1</i>供应商工程证书</h2>
									<div class="ul_list count_flow">
										<table class="table table-bordered table-condensed table-hover">
											<thead>
												<tr>
													<th class="info">资质资格类型</th>
													<th class="info">证书编号</th>
													<th class="info">资质资格最高等级</th>
													<th class="info">技术负责人姓名</th>
													<th class="info">技术负责人职称</th>
													<th class="info">技术负责人职务</th>
													<th class="info">单位负责人姓名</th>
													<th class="info">单位负责人职称</th>
													<th class="info">单位负责人职务</th>
													<th class="info">发证机关</th>
													<th class="info">发证日期</th>
													<th class="info">有效截止日期</th>
													<th class="info">证书状态</th>
												</tr>
											</thead>
											<c:forEach items="${supplierCertEng}" var="s">
												<tr>
													<td class="tc">${s.certType }</td>
													<td class="tc" id="${s.id }">${s.certCode }</td>
													<td class="tc">${s.certMaxLevel }</td>
													<td class="tc">${s.techName }</td>
													<td class="tc">${s.techPt }</td>
													<td class="tc">${s.techJop }</td>
													<td class="tc">${s.depName }</td>
													<td class="tc">${s.depPt }</td>
													<td class="tc">${s.depJop }</td>
													<td class="tc">${s.licenceAuthorith }</td>
													<td class="tc ">
														<fmt:formatDate value="${s.expStartDate }" pattern='yyyy-MM-dd' />
													</td>
													<td class="tc">
														<fmt:formatDate value="${s.expStartDate }" pattern='yyyy-MM-dd' />
														<fmt:formatDate value="${s.expEndDate }" pattern='yyyy-MM-dd' />
													</td>
													<td class="tc">
														<c:if test="${s.certStatus==0 }">无效</c:if>
														<c:if test="${s.certStatus==1 }">有效</c:if>
													</td>
												</tr>
											</c:forEach>
										</table>
									</div>

									<h2 class="count_flow"><i>2</i>供应商资质资格</h2>
									<ul class="ul_list count_flow">
										<table class="table table-bordered table-condensed table-hover">
											<thead>
												<tr>
													<th class="info">资质资格类型</th>
													<th class="info">证书编号</th>
													<th class="info">资质资格序列</th>
													<th class="info">专业类别</th>
													<th class="info">资质资格等级</th>
													<th class="info">是否主项资质</th>
													<th class="info">批准资质资格内容</th>
													<th class="info">首次批准资质资格文号</th>
													<th class="info">首次批准资质资格日期</th>
													<th class="info">资质资格取得方式</th>
													<th class="info">资质资格状态</th>
													<th class="info">资质资格状态变更时间</th>
													<th class="info">资质资格状态变更原因</th>
												</tr>
											</thead>
											<c:forEach items="${supplierAptitutes}" var="s">
												<tr>
													<td class="tc">${s.certType }</td>
													<td class="tc" id="${s.id }">${s.certCode }</td>
													<td class="tc">${s.aptituteSequence }</td>
													<td class="tc">${s.professType }</td>
													<td class="tc">${s.aptituteLevel }</td>
													<td class="tc">
														<c:if test="${s.isMajorFund==0 }">否</c:if>
														<c:if test="${s.isMajorFund==1 }">是</c:if>
														<td class="tc">${s.aptituteContent }</td>
														<td class="tc">${s.aptituteCode }</td>
														<td class="tc">
															<fmt:formatDate value="${s.aptituteDate }" pattern='yyyy-MM-dd' />
														</td>
														<td class="tc">${s.aptituteWay }</td>
														<td class="tc">
															<c:if test="${s.aptituteStatus==0 }">无效</c:if>
															<c:if test="${s.aptituteStatus==1 }">有效</c:if>
														</td>
														<td class="tc">
															<fmt:formatDate value="${s.aptituteChangeAt }" pattern='yyyy-MM-dd' />
														</td>
														<td class="tc">${s.aptituteChangeReason }</td>
												</tr>
											</c:forEach>
										</table>
									</ul>

									<h2 class="count_flow"><i>3</i>供应商注册人员登记</h2>
									<ul class="ul_list count_flow">
										<table class="table table-bordered table-condensed table-hover">
											<thead>
												<tr>
													<th class="info w50">序号</th>
													<th class="info">注册名称</th>
													<th class="info">注册人数</th>
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
									
									<div class="tab-pane fade active in">
										<h2 class="count_flow"><i>4</i>法人代表信息</h2>
										<table class="table table-bordered table-condensed table-hover">
											<thead>
												<tr>
													<th class="info">组织机构</th>
													<th class="info">人员总数</th>
													<th class="info">管理人员</th>
													<th class="info">技术人员</th>
													<th class="info">工人(职员)</th>
												</tr>
											</thead>
												<tr>
													<td class="tc">${supplierMatPros.orgName }</td>
													<td class="tc">${supplierMatPros.totalPerson }</td>
													<td class="tc">${supplierMatPros.totalMange }</td>
													<td class="tc">${supplierMatPros.totalTech }</td>
													<td class="tc">${supplierMatPros.totalWorker }</td>
												</tr>
										</table>
									</div>
								</div>
								
							</c:if>
							
							<c:if test="${fn:contains(supplierTypeNames, '服务')}">
								<div class="tab-pane <c:if test="${liCount == 1}">active in</c:if> fade height-200" id="tab-4">
									<h2 class="count_flow"><i>1</i>供应商服务资质证书</h2>
									<ul class="ul_list count_flow">
										<table class="table table-bordered table-condensed table-hover">
											<thead>
												<tr>
													<th class="info w50">序号</th>
													<th class="info">资质证书名称</th>
													<th class="info">资质等级</th>
													<th class="info">发证机关</th>
													<th class="info">有效期(起止时间)</th>
													<th class="info">是否年检</th>
												</tr>
											</thead>
											<c:forEach items="${supplierCertSes}" var="s" varStatus="vs">
												<tr>
													<td class="tc">${vs.index + 1}</td>
													<td class="tc" id="${s.id}">${s.name }</td>
													<td class="tc">${s.levelCert}</td>
													<td class="tc">${s.licenceAuthorith }</td>
													<td class="tc">
														<fmt:formatDate value="${s.expStartDate }" pattern='yyyy-MM-dd' /> 至
														<fmt:formatDate value="${s.expEndDate }" pattern='yyyy-MM-dd' />
													</td>
													<td class="tc">
														<c:if test="${s.mot==0 }">否</c:if>
														<c:if test="${s.mot==1 }">是</c:if>
													</td>
												</tr>
											</c:forEach>
										</table>
									</ul>

									<div class="tab-pane fade active in">
										<h2 class="count_flow"><i>2</i>法人代表信息</h2>
										<table class="table table-bordered table-condensed table-hover">
											<thead>
												<tr>
													<th class="info">组织机构</th>
													<th class="info">人员总数</th>
													<th class="info">管理人员</th>
													<th class="info">技术人员</th>
													<th class="info">工人(职员)</th>
												</tr>
											</thead>
												<tr>
													<td class="tc">${supplierMatPros.orgName }</td>
													<td class="tc">${supplierMatPros.totalPerson }</td>
													<td class="tc">${supplierMatPros.totalMange }</td>
													<td class="tc">${supplierMatPros.totalTech }</td>
													<td class="tc">${supplierMatPros.totalWorker }</td>
												</tr>
										</table>
									</div>
								</div>
								</div>
							</c:if>
						</div>
					</div>
				</div>
				<form target="_blank" id="download_form_id" action="${pageContext.request.contextPath}/supplierAudit/download.html" method="post">
					<input type="hidden" name="fileName" />
				</form>
				<form id="form_id" action="" method="post">
					<input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
				</form>
	</body>

</html>