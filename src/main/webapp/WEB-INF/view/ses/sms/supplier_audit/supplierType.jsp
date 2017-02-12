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
					$(this).parent("tr").find("td").eq(7).find("a").hide();
				});

				$(":input").each(function() {
					var onMouseMove = "this.style.background='#E8E8E8'";
					var onmouseout = "this.style.background='#FFFFFF'";
					$(this).attr("onMouseMove", onMouseMove);
					$(this).attr("onmouseout", onmouseout);
				});
				
				$("li").each(function() {
					$(this).find("p").hide();
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
				var auditContent = "证书名称：" + str + "的信息";
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
				  if(auditField == "confidentialAchievement"){
						auditContent = $(this).parents("li").find("textarea").val();
					} 
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
			
			function reasonFile(ele, auditField) {
				var supplierId = $("#supplierId").val();
				var auditFieldName = $(ele).parents("li").find("span").text().replace("：", "").replace("view", ""); //审批的字段名字
				var index = layer.prompt({
					title: '请填写不通过的理由：',
					formType: 2,
					offset: '100px'
				}, function(text) {
					$.ajax({
						url: "${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
						type: "post",
						data: "&auditFieldName=" + auditFieldName + "&suggest=" + text + "&supplierId=" + supplierId + "&auditType=mat_eng_page" + "&auditContent=附件" + "&auditField=" + auditField,
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
					$(ele).parents("li").find("p").show(); //显示叉
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
			function jump(str) {
				var action;
				if(str == "essential") {
					action = "${pageContext.request.contextPath}/supplierAudit/essential.html";
				}
				if(str == "financial") {
					action = "${pageContext.request.contextPath}/supplierAudit/financial.html";
				}
				if(str == "shareholder") {
					action = "${pageContext.request.contextPath}/supplierAudit/shareholder.html";
				}
				if(str == "supplierType") {
					action = "${pageContext.request.contextPath}/supplierAudit/supplierType.html";
				}
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
			<div class="content">
				<div class="col-md-12 col-sm-12 col-xs-12 tab-v2 job-content">
					<ul class="flow_step">
						<li onclick="jump('essential')">
							<a aria-expanded="false">详细信息</a>
							<i></i>
						</li>
						<li onclick="jump('financial')">
							<a aria-expanded="true">财务信息</a>
							<i></i>
						</li>
						<li onclick="jump('shareholder')">
							<a aria-expanded="false">股东信息</a>
							<i></i>
						</li>
						<li onclick="jump('supplierType')" class="active">
							<a aria-expanded="false">供应商类型</a>
							<i></i>
						</li>
						<li onclick="jump('items')">
							<a aria-expanded="false">品目信息</a>
							<i></i>
						</li>
						<li onclick="jump('aptitude')">
							<a aria-expanded="false">资质文件</a>
							<i></i>
						</li>
						<li onclick="jump('contract')">
							<a aria-expanded="false">品目合同</a>
							<i></i>
						</li>
						<li onclick="jump('applicationForm')">
							<a aria-expanded="false">申请表</a>
							<i></i>
						</li>
						<li onclick="jump('reasonsList')">
							<a aria-expanded="false">审核汇总</a>
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
								<h2 class="count_flow"><i>1</i>组织结构和人员</h2>
									<ul class="ul_list count_flow">
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">组织机构：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="orgName_production" type="text" value="${supplierMatPros.orgName }" onclick="reasonProduction1(this)" <c:if test="${fn:contains(field,'orgName')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('orgName','mat_pro_page');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">人员总数：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalPerson_production" type="text" value="${supplierMatPros.totalPerson }" onclick="reasonProduction1(this)" <c:if test="${fn:contains(field,'totalPerson')}"> style="border: 1px solid #FF8C00;" onMouseOver="isCompare('totalPerson','mat_pro_page');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">管理人员：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalMange_production" type="text" value="${supplierMatPros.totalMange }" onclick="reasonProduction1(this)" <c:if test="${fn:contains(field,'totalMange')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('totalMange','mat_pro_page');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">技术人员：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalTech_production" type="text" value="${supplierMatPros.totalTech }" onclick="reasonProduction1(this)" <c:if test="${fn:contains(field,'totalTech')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('totalTech','mat_pro_page');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">工人(职员)：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalWorker_production" type="text" value="${supplierMatPros.totalWorker }" onclick="reasonProduction1(this)" <c:if test="${fn:contains(field,'totalWorker')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('totalWorker','mat_pro_page');"</c:if>/>
											</div>
										</li>
									</ul>
									
									<h2 class="count_flow"><i>2</i>产品研发能力</h2>
									<ul class="ul_list count_flow">
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">技术人员比例(%)：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="scaleTech_production" type="text" value="${supplierMatPros.scaleTech }" onclick="reasonProduction1(this)" <c:if test="${fn:contains(field,'scaleTech')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('scaleTech','mat_pro_page');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">高级技术人员比例(%)：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="scaleHeightTech_production" type="text" value="${supplierMatPros.scaleHeightTech }" onclick="reasonProduction1(this)" <c:if test="${fn:contains(field,'scaleHeightTech')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('scaleHeightTech','mat_pro_page');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" onclick="reason1(this)">研发部门名称：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="researchName_production" type="text" value="${supplierMatPros.researchName }" onclick="reasonProduction1(this)" <c:if test="${fn:contains(field,'researchName')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('researchName','mat_pro_page');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" onclick="reason1(this)">研发部门人数：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalResearch_production" type="text" value="${supplierMatPros.totalResearch }" onclick="reasonProduction1(this)" <c:if test="${fn:contains(field,'totalResearch')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('totalResearch','mat_pro_page');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">研发部门负责人：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="researchLead_production" type="text" value="${supplierMatPros.researchLead }" onclick="reasonProduction1(this)" <c:if test="${fn:contains(field,'researchLead')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('researchLead','mat_pro_page');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">国家军队科研项目：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="countryPro_production" type="text" onclick="reasonProduction1(this)" value="${supplierMatPros.countryPro }" <c:if test="${fn:contains(field,'countryPro')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('countryPro','mat_pro_page');"</c:if>>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">国家军队科技奖项：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="countryReward_production" type="text" onclick="reasonProduction1(this)" value="${supplierMatPros.countryReward }" <c:if test="${fn:contains(field,'countryReward')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('countryReward','mat_pro_page');"</c:if>>
											</div>
										</li>
									</ul>
									
									<h2 class="count_flow"><i>3</i>生产能力</h2>
									<ul class="ul_list count_flow">
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">生产线名称数量：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalBeltline_production" type="text" value="${supplierMatPros.totalBeltline }" onclick="reasonProduction1(this)" <c:if test="${fn:contains(field,'totalBeltline')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('totalBeltline','mat_pro_page');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">生产设备名称数量：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalDevice_production" type="text" value="${supplierMatPros.totalDevice }" onclick="reasonProduction1(this)" <c:if test="${fn:contains(field,'totalDevice')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('totalDevice','mat_pro_page');"</c:if>/>
											</div>
										</li>
									</ul>
									
									<h2 class="count_flow"><i>4</i>质量检测能力</h2>
									<ul class="ul_list count_flow">
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">质量检测部门：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="qcName_production" type="text" value="${supplierMatPros.qcName }" onclick="reasonProduction1(this)" <c:if test="${fn:contains(field,'qcName')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('qcName','mat_pro_page');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">质量检测人数：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalQc_production" type="text" value="${supplierMatPros.totalQc }" onclick="reasonProduction1(this)" <c:if test="${fn:contains(field,'totalQc')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('totalQc','mat_pro_page');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">质检部门负责人：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="qcLead_production" type="text" value="${supplierMatPros.qcLead }" onclick="reasonProduction1(this)" <c:if test="${fn:contains(field,'qcLead')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('qcLead','mat_pro_page');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">质量检测设备名称：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="qcDevice_production" type="text" onclick="reasonProduction1(this)" value="${supplierMatPros.qcDevice }" <c:if test="${fn:contains(field,'qcDevice')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('qcDevice','mat_pro_page');"</c:if>>
											</div>
										</li>
									</ul>
									
									<h2 class="count_flow"><i>5</i>供应商物资生产资质证书</h2>
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
													<th class="info">附件</th>
													<th class="info w50">操作 </th>
												</tr>
											</thead>
											<c:forEach items="${materialProduction}" var="m" varStatus="vs">
												<tr>
													<td class="tc">${vs.index + 1}</td>
													<td class="tl pl20" id="${m.id}">${m.name }</td>
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
													<td class="tc">
														<u:show showId="pro_show${vs.index+1}" delete="false" businessId="${m.id}" typeId="${supplierDictionaryData.supplierProCert}" sysKey="${sysKey}" />
													</td>
													<td class="tc w50">
														<p onclick="reasonProduction('${m.id}','${m.name}');" id="${m.id}_hidden" class="editItem"><img src='/zhbj/public/backend/images/light_icon.png'></p>
														<a id="${m.id }_show"><img src='/zhbj/public/backend/images/sc.png'></a>
													</td>
												</tr>
											</c:forEach>
										</table>
									</div>
								</div>
							</c:if>

							<c:if test="${fn:contains(supplierTypeNames, '销售')}">
								<div class="tab-pane <c:if test="${liCount == 1}">active in</c:if> fade  in height-200" id="tab-2">
									<h2 class="count_flow"><i>1</i>供应商组织结构和人员</h2>
									<ul class="ul_list">
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">组织机构：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="orgName_sale" type="text" value="${supplierMatSells.orgName }" onclick="reasonSale1(this)" <c:if test="${fn:contains(field,'orgName')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('orgName','mat_sell_page');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">人员总数：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalPerson_sale" type="text" value="${supplierMatSells.totalPerson }" onclick="reasonSale1(this)" <c:if test="${fn:contains(field,'totalPerson')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('totalPerson','mat_sell_page');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">管理人员：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalMange_sale" type="text" value="${supplierMatSells.totalMange }" onclick="reasonSale1(this)" <c:if test="${fn:contains(field,'totalMange')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('totalMange','mat_sell_page');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">技术人员：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalTech_sale" type="text" value="${supplierMatSells.totalTech }" onclick="reasonSale1(this)" <c:if test="${fn:contains(field,'totalTech')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('totalTech','mat_sell_page');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">工人(职员)：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalWorker_sale" type="text" value="${supplierMatSells.totalWorker }" onclick="reasonSale1(this)" <c:if test="${fn:contains(field,'totalWorker')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('totalWorker','mat_sell_page');"</c:if>/>
											</div>
										</li>
									</ul>
								
									<h2 class="count_flow"><i>2</i>供应商物资销售资质证书</h2>
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
												  <th class="info">附件</th>
													<th class="info w50">操作</th>
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
													<td class="tc">
													  <u:show showId="sale_show_${vs.index+1}" delete="false" businessId="${s.id}" typeId="${supplierDictionaryData.supplierSellCert}" sysKey="${sysKey}" />
													</td>
													<td class="tc w50">
														<p onclick="reasonSale('${s.id}','${s.name }');" id="${s.id}_hidden" class="editItem"><img src='/zhbj/public/backend/images/light_icon.png'></p>
														<a id="${s.id }_show"><img src='/zhbj/public/backend/images/sc.png'></a>
													</td>
												</tr>
											</c:forEach>
										</table>
									</ul>
								</div>
							</c:if>

							<c:if test="${fn:contains(supplierTypeNames, '工程')}">
								<div class="tab-pane <c:if test="${liCount == 1}">active in</c:if> fade height-200" id="tab-3">
									<h2 class="count_flow"><i>1</i>组织结构和人员信息</h2>
									<ul class="ul_list count_flow">
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">组织机构：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="orgName_engineering" type="text" value="${supplierMatEngs.orgName }" onclick="reasonEngineering1(this)" <c:if test="${fn:contains(field,'orgName')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('orgName','mat_eng_page');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">技术负责人数：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalTech_engineering" type="text" value="${supplierMatEngs.totalTech }" onclick="reasonEngineering1(this)" <c:if test="${fn:contains(field,'totalTech')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('totalTech','mat_eng_page');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">中级及以上职称人员：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalGlNormal_engineering" type="text" value="${supplierMatEngs.totalGlNormal }" onclick="reasonEngineering1(this)" <c:if test="${fn:contains(field,'totalGlNormal')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('totalGlNormal','mat_eng_page');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">管理人员：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalMange_engineering" type="text" value="${supplierMatEngs.totalMange }" onclick="reasonEngineering1(this)" <c:if test="${fn:contains(field,'totalMange')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('totalMange','mat_eng_page');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">技术工人：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalTechWorker_engineering" type="text" value="${supplierMatEngs.totalTechWorker }" onclick="reasonEngineering1(this)" <c:if test="${fn:contains(field,'totalTechWorker')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('totalTechWorker','mat_eng_page');"</c:if>/>
											</div>
										</li>
									</ul>
									
									<h2 class="count_flow"><i>2</i>保密工程业绩</h2>
									<ul class="ul_list count_flow">
										<li class="col-md-3 col-sm-6 col-xs-12 pl10">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 hand" onclick="reasonFile(this,'supplierConAch');" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'">承包合同主要页及保密协议：</span>
											<u:show showId="conAch_show" delete="false" businessId="${supplierId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierConAch}" />
											<p><img style="padding-left: 125px;" src='/zhbj/public/backend/images/sc.png'></p>
										</li>
										<li class="col-md-12 col-xs-12 col-sm-12 mb25">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">国家或军队保密工程业绩：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<textarea class="col-md-12 col-xs-12 col-sm-12 h80 hand" id="confidentialAchievement_engineering" onclick="reasonEngineering1(this)">${supplierMatEngs.confidentialAchievement}</textarea>
											</div>
										</li>
									</ul>
									
									<h2 class="count_flow"><i>3</i>承揽业务范围：省级行政区对应合同主要页 （体现甲乙双方盖章及工程名称、地点的相关页）</h2>
									<ul class="ul_list">
										<c:forEach items="${rootArea}" var="area" varStatus="st">
											<li class="col-md-3 col-sm-6 col-xs-12 pl15">
												<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 hand" onclick="reasonFile(this,'${area.name}');" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'">${area.name}：</span>
													<u:show showId="area_show_${st.index+1}" delete="false" businessId="${supplierId}_${area.name}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierConAch}" />
													<p><img style="padding-left: 125px;" src='/zhbj/public/backend/images/sc.png'></p>
											</li>
										</c:forEach>
									</ul>
									
									<h2 class="count_flow"><i>4</i>注册人员信息</h2>
									<ul class="ul_list count_flow">
										<table class="table table-bordered table-condensed table-hover">
											<thead>
												<tr>
													<th class="info w50">序号</th>
													<th class="info">注册名称</th>
													<th class="info">注册人数</th>
													<th class="info w50">操作</th>
												</tr>
											</thead>
											<c:forEach items="${listRegPerson}" var="regPrson" varStatus="vs">
												<tr>
													<td class="tc">${vs.index + 1}</td>
													<td class="tc">${regPrson.regType}</td>
													<td class="tc">${regPrson.regNumber}</td>
													<td class="tc w50">
														<p onclick="reasonEngineering('${regPrson.id}','工程-注册人员登记','${regPrson.regType}');" id="${regPrson.id}_hidden2" class="btn">审核</p>
														<a id="${regPrson.id }_show2" style="visibility:hidden"><img src='/zhbj/public/backend/images/sc.png'></a>
													</td>
												</tr>
											</c:forEach>
										</table>
									</ul>
									
									<h2 class="count_flow"><i>5</i>供应商工程证书</h2>
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
													<th class="info">附件</th>
													<th class="info w50">操作</th>
												</tr>
											</thead>
											<c:forEach items="${supplierCertEng}" var="s" varStatus="vs">
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
													<td class="tc">
														<u:show showId="eng_show${vs.index+1}" delete="false" businessId="${s.id}" typeId="${supplierDictionaryData.supplierEngCert}" sysKey="${sysKey}" />
													</td>
													<td class="tc w50">
														<p onclick="reasonEngineering('${s.id}','工程-资质证书','${s.certCode}');" id="${s.id}_hidden" class="editItem"><img src='/zhbj/public/backend/images/light_icon.png'></p>
														<a id="${s.id }_show" style="visibility:hidden"><img src='/zhbj/public/backend/images/sc.png'></a>
													</td>
												</tr>
											</c:forEach>
										</table>
									</div>

									<h2 class="count_flow"><i>6</i>供应商资质资格</h2>
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
													<th class="info">附件</th>
													<th class="info w50">操作</th>
												</tr>
											</thead>
											<c:forEach items="${supplierAptitutes}" var="s" varStatus="vs">
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
														<td class="tc">
															 <u:show showId="apt_show${vs.index+1}" delete="false" businessId="${s.id}" typeId="${supplierDictionaryData.supplierEngCertFile}" sysKey="${sysKey}" />
														</td>
														<td class="tc w50">
															<p onclick="reasonEngineering('${s.id}','工程-资质资格证书','${s.certCode}');" id="${s.id}_hidden1" class="editItem"><img src='/zhbj/public/backend/images/light_icon.png'></p>
															<a id="${s.id }_show1" style="visibility:hidden"><img src='/zhbj/public/backend/images/sc.png'></a>
														</td>
												</tr>
											</c:forEach>
										</table>
									</ul>
								</div>
							</c:if>
							
							<c:if test="${fn:contains(supplierTypeNames, '服务')}">
								<div class="tab-pane <c:if test="${liCount == 1}">active in</c:if> fade height-200" id="tab-4">
									<h2 class="count_flow"><i>1</i>组织结构和人员信息</h2>
									<ul class="ul_list">
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">组织机构：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="orgName_service" class="span5" type="text" value="${supplierMatSes.orgName }" onclick="reasonService1(this)" <c:if test="${fn:contains(field,'orgName')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('orgName','mat_serve_page');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">人员总数：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalPerson_service" class="span5" type="text" value="${supplierMatSes.totalPerson }" onclick="reasonService1(this)" <c:if test="${fn:contains(field,'totalPerson')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('totalPerson','mat_serve_page');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">管理人员：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalMange_service" class="span5" type="text" value="${supplierMatSes.totalMange }" onclick="reasonService1(this)" <c:if test="${fn:contains(field,'totalMange')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('totalMange','mat_serve_page');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">技术人员：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalTech_service" class="span5" type="text" value="${supplierMatSes.totalTech }" onclick="reasonService1(this)" <c:if test="${fn:contains(field,'totalTech')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('totalTech','mat_serve_page');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">工人(职员)：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalWorker_service" class="span5" type="text" value="${supplierMatSes.totalWorker }" onclick="reasonService1(this)" <c:if test="${fn:contains(field,'totalWorker')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('totalWorker','mat_serve_page');"</c:if>/>
											</div>
										</li>
									</ul>
								
									<h2 class="count_flow"><i>2</i>供应商服务资质证书</h2>
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
													<th class="info">附件</th>
													<th class="info w50">操作</th>
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
													<td class="tc">
														<u:show showId="ser_show${vs.index+1}" businessId="${s.id}" delete="false" typeId="${supplierDictionaryData.supplierServeCert}" sysKey="${sysKey}" />
													</td>
													<td class="tc w50">
														<p onclick="reasonService('${s.id}','服务-资质证书','${s.name}');" id="${s.id}_hidden" class="editItem"><img src='/zhbj/public/backend/images/light_icon.png'></p>
														<a id="${s.id}_show"><img src='/zhbj/public/backend/images/sc.png'></a>
													</td>
												</tr>
											</c:forEach>
										</table>
									</ul>					
								</div>
							</c:if>

							<div class="col-md-12 col-sm-12 col-xs-12 add_regist tc">
								<a class="btn" type="button" onclick="lastStep();">上一步</a>
								<a class="btn" type="button" onclick="nextStep();">下一步</a>
							</div>
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