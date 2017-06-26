<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>

<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<title>产品类别及资质合同</title>
<script
	src="${pageContext.request.contextPath}/js/ses/sms/supplier_audit/aptitude.js"></script>
</head>
<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a
					href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')">
						首页</a></li>
				<li><a>支撑环境</a></li>
				<li><a>供应商管理</a></li>
				<c:if test="${sign == 1}">
					<li><a
						href="javascript:jumppage('${pageContext.request.contextPath}/supplierAudit/supplierAll.html?sign=1')">供应商审核</a>
					</li>
				</c:if>
				<c:if test="${sign == 2}">
					<li><a
						href="javascript:jumppage('${pageContext.request.contextPath}/supplierAudit/supplierAll.html?sign=2')">供应商复核</a>
					</li>
				</c:if>
				<c:if test="${sign == 3}">
					<li><a
						href="javascript:jumppage('${pageContext.request.contextPath}/supplierAudit/supplierAll.html?sign=3')">供应商实地考察</a>
					</li>
				</c:if>
			</ul>
		</div>
	</div>
	<div class="container container_box">
		<div class="content ">
			<div class="col-md-12 tab-v2 job-content">
				<ul class="flow_step">
					<li onclick="jump('essential')">
					<a aria-expanded="false" href="#tab-1">基本信息</a> <i></i></li>
					<li onclick="jump('financial')">
					<a aria-expanded="true"	href="#tab-2">财务信息</a> <i></i></li>
					<li onclick="jump('shareholder')">
					<a aria-expanded="false" href="#tab-3">股东信息</a> <i></i></li>
					<li onclick="jump('supplierType')">
					<a aria-expanded="false">供应商类型</a>
						<i></i></li>
					<li onclick="jump('aptitude')" class="active">
					<a	aria-expanded="false" href="#tab-4">产品类别及资质合同</a> <i></i></li>
					<li onclick="jump('applicationForm')">
					<a aria-expanded="false" href="#tab-4">承诺书和申请表</a> <i></i></li>
					<li onclick="jump('reasonsList')">
					<a aria-expanded="false" href="#tab-4">审核汇总</a></li>
				</ul>
				<div class="tab-content padding-top-20" id="tab_content_div_id">
					<table class="table table-bordered table-condensed table-hover table-striped" id="tab_content_2">
						 <thead>
							<tr>
							  <td class="tc info">序号</td>
							  <td class="tc info">类别</td>
							  <td class="tc info">大类</td>
							  <td class="tc info">中类</td>
							  <td class="tc info">小类</td>
							  <td class="tc info">品种名称</td>
							  <td class="tc info">资质文件</td>
							  <td class="tc info">销售合同</td>
							</tr>
						</thead> 
						<tbody>
						</tbody>
					</table>
				</div>
				<div id="pagediv" align="right"></div>
				<div class="col-md-12 col-sm-12 col-xs-12 add_regist tc">
					<a class="btn" type="button" onclick="lastStep();">上一步</a> <a
						class="btn padding-left-20 padding-right-20 btn_back margin-5"
						onclick="zhancun();">暂存</a> <a class="btn" type="button"
						onclick="nextStep();">下一步</a>
				</div>
			</div>
		</div>
	</div>
	<form id="form_id" action="" method="post">
		<input id="supplierId" name="supplierId" value="${supplierId}" type="hidden"> 
		<input name="supplierStatus" value="${supplierStatus}" type="hidden">
		<input type="hidden" name="sign" value="${sign}">
	</form>
	<div  class=" clear margin-top-30" id="show_div"  style="display:none;" >
    </div>
</body>

</html>