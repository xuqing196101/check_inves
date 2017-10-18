<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<%@ include file="/WEB-INF/view/ses/ems/expertQuery/common.jsp"%>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/ems/expertQuery/merge_jump.js"></script>
		<script type="text/javascript">
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
		</script>
		<script type="text/javascript">
			$(function() {
				var expertId = $("#expertId").val();
				var mat = $("#mat").val();
				var eng = $("#eng").val();
				var ser = $("#ser").val();
				var goodsProject = $("#goodsProject").val();
				var goodsEngInfo = $("#goodsEngInfo").val();
				var engInfo = $("#engInfo").val();

				var matCodeId = $("#matCodeId").val();
				var engCodeId = $("#engCodeId").val();
				var serCodeId = $("#serCodeId").val();
				var goodsProjectId = $("#goodsProjectId").val();
				var goodsEngInfoId = $("#goodsEngInfoId").val();
				if(mat == "mat_page") {
					// 物资品目信息
					loading = layer.load(1);
					var path = "${pageContext.request.contextPath}/expertQuery/getCategories.html?expertId=" + expertId + "&typeId=" + matCodeId;
					$("#tbody_category").load(path);
				} else if(eng == "eng_page") {
					// 工程品目信息
					loading = layer.load(1);
					var path = "${pageContext.request.contextPath}/expertQuery/getCategories.html?expertId=" + expertId + "&typeId=" + engCodeId;
					$("#tbody_category").load(path);
				} else if(ser == "ser_page") {
					// 服务
					loading = layer.load(1);
					var path = "${pageContext.request.contextPath}/expertQuery/getCategories.html?expertId=" + expertId + "&typeId=" + serCodeId;
					$("#tbody_category").load(path);
				} else if(goodsProject == "goodsProject_page") {
					// 工程产品类别信息
					loading = layer.load(1);
					var path = "${pageContext.request.contextPath}/expertQuery/getCategories.html?expertId=" + expertId + "&typeId=" + goodsProjectId;
					$("#tbody_category").load(path);
				} else if(goodsEngInfo == "goodsEngInfo_page") {
					// 工程专业属性信息
					loading = layer.load(1);
					var path = "${pageContext.request.contextPath}/expertQuery/getCategories.html?expertId=" + expertId + "&typeId=" + goodsEngInfoId;
					$("#tbody_category").load(path);
				}
			});

			function showDivTree(code) {
				// 加载已选品目列表
				loading = layer.load(1);
				var expertId = $("#expertId").val();
				var path = "${pageContext.request.contextPath}/expertQuery/getCategories.html?expertId=" + expertId + "&typeId=" + code;
				$("#tbody_category").load(path);
			};
		</script>

	</head>

	<body>
		<!--面包屑导航开始-->
		<%-- <jsp:include page="navigation.jsp" flush="ture" /> --%>
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0)">支撑环境</a>
					</li>
					<li>
						<a href="javascript:void(0)">专家管理</a>
					</li>
					<li>
						<c:if test="${sign == 1}">
							<a href="javascript:jumppage('${pageContext.request.contextPath}/expert/findAllExpert.html')">全部专家查询</a>
						</c:if>
						<c:if test="${sign == 2}">
							<a  href="javascript:jumppage('${pageContext.request.contextPath}/expertQuery/list.html')">入库专家查询</a>
						</c:if>
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
					<ul class="nav nav-tabs bgwhite">
						<li class="">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab" class="f18" onclick="jump('basicInfo');">基本信息</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="jump('expertType');">专家类别</a>
						</li>
						<li class="active">
							<a aria-expanded="true" href="#tab-3" data-toggle="tab" class="f18" onclick="jump('product');">产品类别</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="jump('expertFile');">承诺书和申请表</a>
						</li>
						<li class="">
              <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="jump('auditInfo');">审核意见</a>
            </li>
					</ul>
					<div class="padding-top-10">
						<ul id="page_ul_id" class="nav nav-tabs bgdd supplier_tab hand">
							<c:set value="0" var="liCount" />
							<c:forEach items="${allCategoryList}" var="cate" varStatus="vs">
								<c:if test="${cate.code eq 'GOODS'}">
									<c:set value="${liCount+1}" var="liCount" />
									<%-- <li id="li_id_${vs.index + 1}" class="active" onclick="showDivTree(this);"> --%>
									<li id="li_id_${vs.index + 1}" class="active" onclick="showDivTree('${matCodeId }');">
										<a id="li_${vs.index + 1}" aria-expanded="true" data-toggle="tab" class="f18">物资产品类别信息</a>
										<input type="hidden" id="mat" value="mat_page">
										<input id="matCodeId" type="hidden" value="${matCodeId }">
									</li>
								</c:if>
								<c:if test="${cate.code eq 'PROJECT'}">
									<%-- <li id="li_id_${vs.index + 1}" class='<c:if test="${liCount == 0}">active</c:if>' onclick="showDivTree(this);"> --%>
									<li id="li_id_${vs.index + 1}" class='<c:if test="${liCount == 0}">active</c:if>' onclick="showDivTree('${engCodeId }');">
										<a id="li_${vs.index + 1}" aria-expanded="true" data-toggle="tab" class="f18">工程产品类别信息</a>
										<input type="hidden" id="eng" value="eng_page">
										<input id="engCodeId" type="hidden" value="${engCodeId }">
									</li>
									<c:set value="${liCount+1}" var="liCount" />
								</c:if>
								<c:if test="${cate.code eq 'PROJECT'}">
									<li id="li_id_${vs.index + 1}" class='<c:if test="${liCount == 0}">active</c:if>' onclick="showDivTree('${engInfoId }');">
										<a id="li_${vs.index + 1}" aria-expanded="true" data-toggle="tab" class="f18">工程专业属性信息</a>
										<input type="hidden" id="engInfo" value="engInfo_page">
										<input id="engInfoId" type="hidden" value="${engInfoId }">
									</li>
									<c:set value="${liCount+1}" var="liCount" />
								</c:if>
								<c:if test="${cate.code eq 'SERVICE'}">
									<%-- <li id="li_id_${vs.index + 1}" class='<c:if test="${liCount == 0}">active</c:if>' onclick="showDivTree(this);"> --%>
									<li id="li_id_${vs.index + 1}" class='<c:if test="${liCount == 0}">active</c:if>' onclick="showDivTree('${serCodeId }');">
										<a id="li_${vs.index + 1}" aria-expanded="false" data-toggle="tab" class="f18">服务产品类别信息</a>
										<input type="hidden" id="ser" value="ser_page">
										<input id="serCodeId" type="hidden" value="${serCodeId }">
									</li>
									<c:set value="${liCount+1}" var="liCount" />
								</c:if>

								<!-- 经济 -->
								<c:if test="${cate.code eq 'GOODS_PROJECT'}">
									<li id="li_id_${vs.index + 1}" class='<c:if test="${liCount == 0}">active</c:if>' onclick="showDivTree('${goodsProjectId }');">
										<a id="li_${vs.index + 1}" aria-expanded="true" data-toggle="tab" class="f18">工程产品类别信息</a>
										<input type="hidden" id="goodsProject" value="goodsProject_page">
										<input id=goodsProjectId type="hidden" value="${goodsProjectId }">
									</li>
									<c:set value="${liCount+1}" var="liCount" />
								</c:if>
								<c:if test="${cate.code eq 'GOODS_PROJECT'}">
									<li id="li_id_${vs.index + 1}" class='<c:if test="${liCount == 0}">active</c:if>' onclick="showDivTree('${engInfoId }');">
										<a id="li_${vs.index + 1}" aria-expanded="true" data-toggle="tab" class="f18">工程专业属性信息</a>
										<input type="hidden" id="goodsEngInfo" value="goodsEngInfo_page">
										<input id="goodsEngInfoId" type="hidden" value="${engInfoId }">
									</li>
									<c:set value="${liCount+1}" var="liCount" />
								</c:if>

							</c:forEach>
						</ul>
						<div class="mt20" id="tbody_category"></div>
						<div id="pagediv" align="right" class="mb50"></div>
						
						<div class="tc mt20 clear col-md-12 col-sm-12 col-xs-12">
							<%-- <c:if test="${empty reqType }"> --%>
								<c:if test="${sign == 1}">
									<a class="btn btn-windows reset" href="${pageContext.request.contextPath}/expert/findAllExpert.html">返回列表</a>
								</c:if>
								<c:if test="${sign == 2}">
									<a class="btn btn-windows reset" href="${pageContext.request.contextPath}/expertQuery/list.html">返回列表</a>
								</c:if>
							<%-- </c:if>
							<c:if test="${not empty reqType }">
								<a class="btn btn-windows reset" href="${pageContext.request.contextPath}/expertQuery/readOnlyList.html?address=${expertAnalyzeVo.address}&expertsTypeId=${expertAnalyzeVo.expertsTypeId}&expertsFrom=${expertAnalyzeVo.expertsFrom}&orgId=${expertAnalyzeVo.orgId}">返回列表</a>
						</c:if> --%>
						</div>
					</div>
				</div>
			</div>
		</div>
		<form id="form_id" action="" method="post">
			<input name="expertId" id="expertId" value="${expertId}" type="hidden">
			<input name="sign" value="${sign}" type="hidden">
			<input name="status" value="${status}" type="hidden">
		</form>
	</body>

</html>