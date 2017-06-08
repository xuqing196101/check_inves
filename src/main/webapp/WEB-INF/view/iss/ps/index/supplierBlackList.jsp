<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<head>
<jsp:include page="/index_head.jsp"></jsp:include>
<%@ include file="../../../common.jsp"%>
</head>

<body>
  <!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li>
					<a href="${pageContext.request.contextPath}/"> 首页</a>
				</li>
				<li>
					<a href="#">供应商黑名单</a>
				</li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>

	<div class="container job-content ">
		<div class="search_box form-inline">
			<div class="form-group">
				<label>供应商名称：</label> 
				<input type="text" id="supplierName" value="${supplierName}" class="form-control"/>
			</div>
			<div class="form-group">
				<label>供应商类型：</label>
				<input type="text" id="supplierTypeNames" class="form-control" value="${supplierTypeNames }"
						onclick="showSupplierType();" readonly />
					<input type="hidden" name="supplierTypeIds" id="supplierTypeIds" value="${supplierTypeIds }" />
					<div id="supplierTypeContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
						<ul id="treeSupplierType" class="ztree" style="margin-top:0;"></ul>
					</div>
			</div>
			<button type="button" onclick="query(1)" class="btn">查询</button>
			<button type="reset" onclick="reset()" class="btn">重置</button>
		</div>
		<div class="col-md-12 col-sm-12 col-xs-12 report_list_box">
			<div class="col-md-12 col-sm-12 col-xs-12 report_list_title">
				<div class="col-md-2 col-xs-4 col-sm-2 tc f16">供应商名称</div>
				<div class="col-md-1 col-xs-4 col-sm-1 tc f16">类型</div>
				<div class="col-md-2 col-xs-4 col-sm-2 tc f16">处罚类型</div>
				<div class="col-md-2 col-xs-4 col-sm-2 tc f16">列入时间</div>
				<div class="col-md-2 col-xs-4 col-sm-2 tc f16">截止时间</div>
				<div class="col-md-3 col-xs-4 col-sm-3 tc f16">处罚理由</div>
			</div>
			<ul class="categories li_square col-md-12 col-sm-12 col-xs-12 p0 list_new">
				<c:choose>
					<c:when test="${!empty page.list}">
						<c:forEach items="${page.list}" var="data">
							<c:set value="${data.supplierName}" var="name" />
							<c:set value="${data.reason}" var="reason" />
							<c:if test="${fn:length(name) > 8}">
								<c:set value="${fn:substring(name,0,8)}..." var="name" />
							</c:if>
							<c:if test="${fn:length(reason) > 15}">
								<c:set value="${fn:substring(reason,0,15)}..." var="reason" />
							</c:if>
							<li>
								<span class="col-md-2 col-sm-2 col-xs-4" title="${data.supplierName}">${name}</span>
								<span class="col-md-1 col-sm-1 col-xs-4 tc">${data.supplierTypeName} </span>
								<span class="col-md-2 col-sm-2 col-xs-4 tc">
									<c:if test="${data.punishType == 0}">警告</c:if>
									<c:if test="${data.punishType == 1}">不得参与采购活动</c:if>
								</span>
								<span class="col-md-2 col-sm-2 col-xs-6 tc">
									<fmt:formatDate value='${data.startTime}' pattern="yyyy-MM-dd " />
								</span>
								<span class="col-md-2 col-sm-2 col-xs-6 tc">
									<fmt:formatDate value='${data.endTime}' pattern="yyyy-MM-dd " />
								</span>
								<span class="col-md-3 col-sm-3 col-xs-12" title="${data.reason}">${reason}</span>
							</li>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<li class="tc">暂无数据~~</span></li>
					</c:otherwise>
				</c:choose>
			</ul>
			<c:if test="${!empty page.list}">
				<div id="pagediv" align="right"></div>
			</c:if>
		</div>
	</div>
	<!--底部代码开始-->
<jsp:include page="/index_bottom.jsp"></jsp:include>
<script type="text/javascript">
	function beforeClick(treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeSupplierType");
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		return false;
	}

	function onCheck(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeSupplierType"), nodes = zTree
				.getCheckedNodes(true), v = "";
		var rid = "";
		for ( var i = 0, l = nodes.length; i < l; i++) {
			v += nodes[i].name + ",";
			rid += nodes[i].id + ",";
		}
		if (v.length > 0)
			v = v.substring(0, v.length - 1);
		if (rid.length > 0)
			rid = rid.substring(0, rid.length - 1);
		$("#supplierTypeNames").val(v);
		$("#supplierTypeIds").val(rid);
	}

	function showSupplierType() {
		var setting = {
			check : {
				enable : true,
				chkboxType : {
					"Y" : "",
					"N" : ""
				}
			},
			view : {
				dblClickExpand : false
			},
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "parentId"
				}
			},
			callback : {
				beforeClick : beforeClick,
				onCheck : onCheck
			}
		};

		$.ajax({
			type : "GET",
			async : false,
			url : "${pageContext.request.contextPath}/supplierQuery/find_supplier_type.do?supplierId=''",
			dataType : "json",
			success : function(zNodes) {
				for ( var i = 0; i < zNodes.length; i++) {
					if (zNodes[i].isParent) {

					} else {
						//zNodes[i].icon = "${ctxStatic}/images/532.ico";//设置图标  
					}
				}
				tree = $.fn.zTree.init($("#treeSupplierType"), setting, zNodes);
				tree.expandAll(true); //全部展开
			}
		});
		var cityObj = $("#supplierTypeNames");
		var cityOffset = $("#supplierTypeNames").offset();
		$("#supplierTypeContent").css({
			left : cityOffset.left + "px",
			top : cityOffset.top + cityObj.outerHeight() + "px"
		}).slideDown("fast");
		$("body").bind("mousedown", onBodyDownSupplierType);
	}

	function hideSupplierType() {
		$("#supplierTypeContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDownSupplierType);
	}

	function onBodyDownSupplierType(event) {
		if (!(event.target.id == "menuBtn" || $(event.target).parents(
				"#supplierTypeContent").length > 0)) {
			hideSupplierType();
		}
	}
</script>

<script type="text/javascript">
	$(function() {
		laypage({
			cont : $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
			pages : "${page.pages}", //总页数
			skin : '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			skip : true, //是否开启跳页
			groups : "${page.pages}" >= 3 ? 3 : "${page.pages}", //连续显示分页数
			total : "${page.total}",
			startRow : "${page.startRow}",
			endRow : "${page.endRow}",
			curr : function() { //通过url获取当前页，也可以同上（pages）方式获取
				var page = location.search.match(/page=(\d+)/);
				return page ? page[1] : 1;
			}(),
			jump : function(e, first) { //触发分页后的回调
				if (!first) { //一定要加此判断，否则初始时会无限刷新
					query(e.curr);
				}
			}
		});
	});

	function query(current) {
		var supplierName = $("#supplierName").val().replace(/\s/g, "");
		var supplierTypeIds = $("#supplierTypeIds").val();
		var supplierTypeNames = $("#supplierTypeNames").val();
		var url = "${pageContext.request.contextPath}/index/supplierBlackList.html?supplierName="
				+ supplierName
				+ "&supplierTypeIds="
				+ supplierTypeIds
				+ "&supplierTypeNames="
				+ supplierTypeNames
				+ "&page="
				+ current;
		window.location.href = encodeURI(encodeURI(url));
	}

	function reset() {
		$("#search_con input[type='text']").val("");
		$("#search_con input[type='hidden']").val("");
	}
</script>
</body>
</html>
