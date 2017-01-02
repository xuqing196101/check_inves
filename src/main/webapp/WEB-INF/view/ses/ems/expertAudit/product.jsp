<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<script type="text/javascript">
			/*$(function() {
				var expertId = $("#expertId").val();
				$.ajax({
					url: "${pageContext.request.contextPath}/expert/getAllCategory.do",
					data: {
						"expertId": expertId
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
									url: "${pageContext.request.contextPath}/expertAudit/getCategory.do?expertId=" + expertId,
									otherParam: {
										id: id
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
								},
								callback: {
									onClick: ztreeOnClick
								}
							};
							zTreeObj = $.fn.zTree.init($("#tab-" + (parseInt(i) + 1)), setting, zNodes);
							zTreeObj.expandAll(true);
						});
						$("#tab-1").attr("style", "");
					}
				});
			});*/

			/* function showTree(tabId) {
				var expertId = $("#expertId").val();
				var id = $("#" + tabId + "-value").val();
				var zTreeObj;
				var zNodes;
				var setting = {
					async: {
						autoParam: ["id"],
						enable: true,
						url: "${pageContext.request.contextPath}/expert/getCategory.do?expertId="+expertId,
						otherParam: {
							"id": id,
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

		function initTree(){
			showTree("tab-1");
			$("#tab-1").attr("style", "");
			$("li_id_1").attr("class", "active");
			$("li_1").attr("aria-expanded", "true");
			$("#tab-2").attr("style", "display: none");
			$("#tab-3").attr("style", "display: none");
		}
			function showTree(tabId) {
				var id = $("#" + tabId + "-value").val();
				var zTreeObj;
				var zNodes;
				var expertId = $("#expertId").val();
				$.ajax({
					url: "${pageContext.request.contextPath}/expertAudit/getCategory.do",
					async: false,
					data: {"categoryId": id,"expertId": expertId},
					success: function(data){
						zNodes = data;
					},
					dataType: "json"
				});
				var setting = {
					/*async: {
						autoParam: ["id"],
						enable: true,
						url: "${pageContext.request.contextPath}/expertAudit/getCategory.do",
						otherParam: {
							"categoryId": id,
							"expertId": expertId
						},
						dataFilter: ajaxDataFilter,
						dataType: "json",
						type: "get"
					},*/
					check: {
						enable: false,
						chkStyle: "checkbox",
						chkboxType: {
							"Y": "ps",
							"N": "ps"
						}, //勾选checkbox对于父子节点的关联关系  
						chkDisabledInherit: true
					},
					data: {
						simpleData: {
							enable: true,
							idKey: "id",
							pIdKey: "parentId",
						}
					},
					callback: {
						onClick: ztreeOnClick,
						showLine: true
					}
				};
				zTreeObj = $.fn.zTree.init($("#" + tabId), setting, zNodes);
				zTreeObj.expandAll(true); //全部展开
			}

			/*function ajaxDataFilter(treeId, parentNode, childNodes) {
				// 判断是否为空
				if(childNodes) {
					// 判断如果父节点是第三极,则将查询出来的子节点全部改为isParent = false
					if(parentNode != null && parentNode != "undefined" && parentNode.level == 2) {
						for(var i = 0; i < childNodes.length; i++) {
							childNodes[i].isParent += false;
						}
					}
				}
				return childNodes;
			}*/

			function showDivTree(obj) {
				$("#tab-1").attr("style", "display: none");
				$("#tab-2").attr("style", "display: none");
				$("#tab-3").attr("style", "display: none");
				var id = obj.id;
				var page = "tab-" + id.charAt(id.length - 1);
				$("#" + page).attr("style", "");
				showTree(page);
			}

			function initTree() {
				showTree("tab-1");
				$("#tab-1").attr("style", "");
				$("li_id_1").attr("class", "active");
				$("li_1").attr("aria-expanded", "true");
				$("#tab-2").attr("style", "display: none");
				$("#tab-3").attr("style", "display: none");
			}

			/** 点击tree **/
			function ztreeOnClick(event, treeId, treeNode) {
				if(treeNode != null) {
					if(!treeNode.isParent) {
						reason(treeNode.name, treeId);
					} else {
						layer.msg("请选择末级节点进行审核");
					}
				}

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
				if(str == "expertType") {
					action = "${pageContext.request.contextPath}/expertAudit/expertType.html";
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
			
			
			//下一步
			function nextStep() {
				var action = "${pageContext.request.contextPath}/expertAudit/expertFile.html";
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}

			//上一步
			function lastStep() {
				var action = "${pageContext.request.contextPath}/expertAudit/expertType.html";
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}
		</script>
		<script type="text/javascript">
			function reason(auditContent, str) {
				var expertId = $("#expertId").val();
				var auditField = $("input[name='" + str + "']").val();
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
								success: function(result) {
									result = eval("(" + result + ")");
									if(result.msg == "fail") {
										layer.msg('该条信息已审核过！', {
											shift: 6, //动画类型
											offset: '100px'
										});
									}
								}
							}),
							/* $(obj).after(html); */
							layer.close(index);
					});
			}
		</script>
	</head>

	<body onload="initTree()">
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
		<input type="hidden" name="id" id="id" value="${expertId}" />
		<input value="物资" type="hidden" name="tab-1">
		<input value="工程" type="hidden" name="tab-2">
		<input value="服务" type="hidden" name="tab-3">

		<div class="container container_box">
			<div class=" content height-350">
				<div class="col-md-12 tab-v2 job-content">
					<ul class="flow_step">
						<li onclick="jump('basicInfo')">
							<a aria-expanded="false" data-toggle="tab">基本信息</a>
							<i></i>
						</li>
						<li onclick="jump('experience')">
							<a aria-expanded="false" data-toggle="tab">经历经验</a>
							<i></i>
						</li>
						<li onclick="jump('expertType')">
							<a aria-expanded="false" data-toggle="tab">专家类别</a>
							<i></i>
						</li>
						<li class="active">
							<a aria-expanded="false" data-toggle="tab">产品目录</a>
							<i></i>
						</li>
						<li onclick="jump('expertFile')">
							<a aria-expanded="false" data-toggle="tab">附件</a>
							<i></i>
						</li>
						<li onclick="jump('reasonsList')">
							<a aria-expanded="false" data-toggle="tab">审核汇总</a>
						</li>
					</ul>
					<div class="padding-top-10">
						<ul id="page_ul_id" class="nav nav-tabs bgdd supplier_tab">
							<c:set value="0" var="liCount" />
							<c:forEach items="${allCategoryList}" var="cate" varStatus="vs">
								<c:if test="${cate.name eq '物资'}">
									<c:set value="${liCount+1}" var="liCount" />
									<li id="li_id_${vs.index + 1}" class="active" onclick="showDivTree(this);">
										<a id="li_${vs.index + 1}" aria-expanded="true" data-toggle="tab" class="f18">物资</a>
									</li>
								</c:if>
								<c:if test="${cate.name eq '工程'}">
									<li id="li_id_${vs.index + 1}" class='<c:if test="${liCount == 0}">active</c:if>' onclick="showDivTree(this);">
										<a id="li_${vs.index + 1}" aria-expanded="true" data-toggle="tab" class="f18">工程</a>
									</li>
									<c:set value="${liCount+1}" var="liCount" />
								</c:if>
								<c:if test="${cate.name eq '服务'}">
									<li id="li_id_${vs.index + 1}" class='<c:if test="${liCount == 0}">active</c:if>' onclick="showDivTree(this);">
										<a id="li_${vs.index + 1}" aria-expanded="false" data-toggle="tab" class="f18">服务</a>
									</li>
									<c:set value="${liCount+1}" var="liCount" />
								</c:if>
							</c:forEach>
						</ul>
						<c:set var="count" value="0"></c:set>
						<div class="tag-box tag-box-v3 center" id="content_ul_id">
							<c:forEach items="${allCategoryList}" var="cate" varStatus="vs">
								<c:if test="${cate.name eq '物资'}">
									<c:set var="count" value="${count + 1}"></c:set>
									<ul id="tab-${vs.index + 1}" class="ztree_supplier mt30" style="display: none"></ul>
									<input id="tab-${vs.index + 1}-value" value="${cate.id}" type="hidden">
								</c:if>
								<c:if test="${cate.name eq '工程'}">
									<c:set var="count" value="${count + 1}"></c:set>
									<ul id="tab-${vs.index + 1}" class="ztree_supplier mt30" style="display: none"></ul>
									<input id="tab-${vs.index + 1}-value" value="${cate.id}" type="hidden">
								</c:if>
								<c:if test="${cate.name eq '服务'}">
									<c:set var="count" value="${count + 1}"></c:set>
									<ul id="tab-${vs.index + 1}" class="ztree_supplier mt30" style="display: none"></ul>
									<input id="tab-${vs.index + 1}-value" value="${cate.id}" type="hidden">
								</c:if>
							</c:forEach>
						</div>
					</div>
					<div class="col-md-12 add_regist tc">
						<a class="btn" type="button" onclick="lastStep();">上一步</a>
						<a class="btn" type="button" onclick="nextStep();">下一步</a>
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