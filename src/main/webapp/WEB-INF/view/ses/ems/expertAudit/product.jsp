<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

	<head>

		<script type="text/javascript">
			$(function() {
				$.ajax({
					url: "${pageContext.request.contextPath}/expert/getAllCategory.do",
					data: {
						"expertId": $("#expertId").val()
					},
					async: false,
					dataType: "json",
					success: function(response) {
						$.each(response, function(i, result) {
							var id = result.id;
							var zTreeObj;
							var zNodes;
							var setting = {
								async: {
									autoParam: ["id"],
									enable: true,
									url: "${pageContext.request.contextPath}/expert/getCategory.do?expertId=${expertId}",
									otherParam: {
										categoryIds: id,
									},
									dataType: "json",
									type: "post"
								},
								check: {
									enable: true,
									chkStyle: "checkbox",
									chkboxType: {
										"Y": "ps",
										"N": "ps"
									}, //勾选checkbox对于父子节点的关联关系  
								},
								data: {
									simpleData: {
										enable: true,
										idKey: "id",
										pIdKey: "parentId"
									}
								}
							};
							zTreeObj = $.fn.zTree.init($("#tab-" + (parseInt(i) + 1)), setting, zNodes);
						});
						$("#tab-1").attr("style", "");
					}
				});
			});
			
				function showTree(tabId) {
		var id = $("#" + tabId + "-value").val();
		var zTreeObj;
		var zNodes;
		var expertId="${expertId}";
		var setting = {
			async: {
				autoParam: ["id"],
				enable: true,
				url: "${pageContext.request.contextPath}/expert/getCategory.do?expertId=${expertId}",
				otherParam: {
					"categoryIds": id,
				},
				callback: {
					
				},
				dataType: "json",
				type: "post"
			},
			check: {
				enable: true,
				chkboxType: {
					"Y": "s",
					"N": "s"
				}
			},
			data: {
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "parentId"
				}
			}
		};
		zTreeObj = $.fn.zTree.init($("#" + tabId), setting, zNodes);
	}

			function showDivTree(obj) {
				$("#tab-1").attr("style", "display: none");
				$("#tab-2").attr("style", "display: none");
				$("#tab-3").attr("style", "display: none");
				var id = obj.id;
				var page = "tab-" + id.charAt(id.length - 1);
				$("#" + page).attr("style", "");
				showTree(page);
			}
		</script>
		<script type="text/javascript">
			function jump(str) {
				var action;
				if(str == "basicInfo") {
					action = "${pageContext.request.contextPath}/expertAudit/basicInfo.html";
				}
				if(str == "experience") {
					action = "${pageContext.request.contextPath}/expertAudit/experience.html";
				}
				if(str == "expertFile") {
					action = "${pageContext.request.contextPath}/expertAudit/expertFile.html";
				}
			  if(str == "reasonsList") {
					action = "${pageContext.request.contextPath}/expertAudit/reasonsList.html";
				}
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}
		</script>
		<script type="text/javascript">
			function reason(obj,str) {
				var expertId = $("#expertId").val();
				var auditField = str.replace("技术","");
				var auditContent = auditField + "产品目录信息";
				var index = layer.prompt({
						title: '请填写不通过的理由：',
						formType: 2,
						offset: '100px',
					},
					function(text) {
						$.ajax({
							url: "${pageContext.request.contextPath}/expertAudit/auditReasons.html",
							type: "post",
							dataType: "json",
							data: "suggestType=six" + "&auditContent=" + auditContent + "&auditReason=" + text + "&expertId=" + expertId + "&auditField=" + auditField,
						}),
						/* $(obj).after(html); */
						layer.close(index);
					});
			}
		</script>
	</head>

	<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:void(0)">首页</a>
					</li>
					<li>
						<a href="javascript:void(0)">支撑系统</a>
					</li>
					<li>
						<a href="javascript:void(0)">专家管理</a>
					</li>
					<li>
						<a href="javascript:void(0)">专家审核</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div class="col-md-12 tab-v2 job-content">
			<input type="hidden" name="id" id="id" value="${expertId}" />
			<div id="reg_box_id_4" class="container container_box">
				<div class=" content height-350">
					<ul class="nav nav-tabs bgdd">
						<li onclick="jump('basicInfo')">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">基本信息</a><i></i>
						</li>
						<li onclick="jump('experience')">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">经历经验</a><i></i>
						</li>
						<li class="active">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">产品目录</a><i></i>
						</li>
						<li onclick="jump('expertFile')">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">附件</a><i></i>
						</li>
						<li onclick="jump('reasonsList')">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">审核汇总</a>
						</li>
					</ul>
					<div class="col-md-12 tab-v2 job-content">
						<div class="padding-top-10">
							<ul id="page_ul_id" class="nav nav-tabs bgdd supplier_tab">
								<c:forEach items="${allCategoryList}" var="cate" varStatus="vs">
									<c:if test="${cate.name eq '物资技术'}">
										<li id="li_id_${vs.index + 1}" class="" onclick="showDivTree(this);">
											<a aria-expanded="true" data-toggle="tab" class="f18">物资</a>
										</li>
									</c:if>
									<c:if test="${cate.name eq '工程技术'}">
										<li id="li_id_${vs.index + 1}" class="" onclick="showDivTree(this);">
											<a aria-expanded="true" data-toggle="tab" class="f18">工程</a>
										</li>
									</c:if>
									<c:if test="${cate.name eq '服务技术'}">
										<li id="li_id_${vs.index + 1}" class="" onclick="showDivTree(this);">
											<a aria-expanded="false" data-toggle="tab" class="f18">服务</a>
										</li>
									</c:if>
								</c:forEach>
							</ul>
							<c:set var="count" value="0"></c:set>
							<div class="tag-box tag-box-v3 center" id="content_ul_id">
								<c:forEach items="${allCategoryList}" var="cate" varStatus="vs">
									<c:if test="${cate.name eq '物资技术'}">
										<c:set var="count" value="${count + 1}"></c:set>
										<ul id="tab-${vs.index + 1}" class="ztree center" style="display: none" onclick="reason(this,'${cate.name}');"></ul>
										<input id="tab-${vs.index + 1}-value" value="${cate.id}" type="hidden">
									</c:if>
									<c:if test="${cate.name eq '工程技术'}">
										<c:set var="count" value="${count + 1}"></c:set>
										<ul id="tab-${vs.index + 1}" class="ztree center" style="display: none" onclick="reason(this,'${cate.name}');"></ul>
										<input id="tab-${vs.index + 1}-value" value="${cate.id}" type="hidden">
									</c:if>
									<c:if test="${cate.name eq '服务技术'}">
										<c:set var="count" value="${count + 1}"></c:set>
										<ul id="tab-${vs.index + 1}" class="ztree center" style="display: none" onclick="reason(this,'${cate.name}');"></ul>
										<input id="tab-${vs.index + 1}-value" value="${cate.id}" type="hidden">
									</c:if>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<input value="${expertId}" id="expertId" type="hidden">
		<form id="form_id" action="" method="post">
			<input name="expertId" value="${expertId}" type="hidden">
		</form>
	</body>
</html>