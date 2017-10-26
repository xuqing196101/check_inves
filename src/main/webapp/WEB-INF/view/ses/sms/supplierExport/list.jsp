<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
<!--<![endif]-->

<head>
<%@ include file="../../../common.jsp"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title></title>
<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<script type="text/javascript">
			
			function liActive(type){
				$("#actives").attr("src","${pageContext.request.contextPath}/supplierExport/supplier_check.html?type="+type);
				$("#form1").attr("action","${pageContext.request.contextPath}/supplierExport/supplier_check.html?type="+type);
				$("#url").val("${pageContext.request.contextPath}/supplierExport/supplier_check.html?type="+type);
				$("#exportType").val(type);
			}
			function search(){
				$("#actives").attr("src",$("#url").val()+"&name="+$("#name").val());
			}
			function clearValue(){
				$("#name").val("");
			}
			function exportExcel(){
				var type = $("#exportType").val();
				window.location.href="${pageContext.request.contextPath}/supplierExport/exportSupplier_check.html?type="+type+"&name="+$("#name").val();
			}
		</script>
</head>

<body onload="liActive(1)">

	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a
					href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')">
						首页</a></li>
				<li><a href="javascript:void(0);">支撑环境</a></li>
				<%--<li>
						<a href="javascript:void(0);">进口代理商</a>
					</li>
					<li>
						<a href="javascript:void(0);">进口代理商管理</a>
					</li>--%>
				<li class="active"><a href="javascript:void(0);">两库审核状态</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<!-- 我的页面开始-->
	<div class="container">
		<div class="headline-v2">
			<h2>两库审核状态列表</h2>
		</div>
		<input type="hidden" id="exportType" value="${exportType}">
		<div class="tab-content mt10">
			<div class="tab-v2">
				<ul class="nav nav-tabs bgwhite">
					<li class="active" id="liActive1" onclick="liActive('1');"><a href="#dep_tab-0" data-toggle="tab"
						class="f18">供应商入库审核</a></li>
					<li id="liActive4" onclick="liActive('4');"><a href="#dep_tab-3" data-toggle="tab" class="f18">专家入库审核</a></li>
					<li id="liActive3" onclick="liActive('3');"><a href="#dep_tab-2" data-toggle="tab" class="f18">供应商增加产品类别</a></li>
					<li id="liActive6" onclick="liActive('6');"><a href="#dep_tab-5" data-toggle="tab" class="f18">专家增加产品类别</a></li>
					<li id="liActive2" onclick="liActive('2');"><a href="#dep_tab-1" data-toggle="tab" class="f18">供应商类型</a></li>
					<li id="liActive5" onclick="liActive('5');"><a href="#dep_tab-4" data-toggle="tab" class="f18">专家类型</a></li>
				</ul>
				<div class="tab-content" style="height: 100%;">
					<div class="tab-pane fade in active" id="dep_tab-0">
						<h2 class="search_detail">
							<form id="form1"
								action="${pageContext.request.contextPath}/supplierExport/supplier_check.html?type=1"
								method="post" class="mb0">
								<input type="hidden"  id="url" value="${pageContext.request.contextPath}/supplierExport/supplier_check.html?type=1" />
								<input type="hidden" name="page" id="page"/>
								<input type="hidden" name="type" id="type" value="1"/>
								<ul class="demand_list">
									<li class="fl"><label class="fl">采购机构：</label><span>
									<select name="name" id="name" class="w220">
										  <option value=''>全部</option>
								           <c:forEach items="${allOrg}" var="org">
								             <option value="${org.shortName}" >${org.shortName}</option>
								           </c:forEach>
						            </select>
									</li>
								</ul>
								<button type="button" class="btn fl" onclick="search();">查询</button>
								<button type="button" onclick="clearValue();" class="btn fl">重置</button>
								<button type="button" onclick="exportExcel();" class="btn fl">导出</button>
								<div class="clear"></div>
							</form>
						</h2>
						<iframe  frameborder="0" name="actives" id="actives" scrolling="auto" marginheight="0" height="800"  width="100%"  src="">
						</iframe>
			</div>
		</div>

	</div>
</body>

</html>