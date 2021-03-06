<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML >
<html>
<head>
<%@ include file="/reg_head.jsp"%>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<c:if test="${currSupplier.status == 2}">
	<%@ include file="/WEB-INF/view/ses/sms/supplier_register/supplier_purchase_dept.jsp"%>
</c:if>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/sms/supplier_register/contract.js"></script>
<title>供应商注册</title>
	<style type="text/css">
		.current {
			cursor: pointer;
		}
</style>
</head>

<body>
	<div class="wrapper">
		<!-- 隐藏域 -->
		<input type="hidden" id="supplierId" value="${supplierId}" />
		<input type="hidden" id="supplierSt" value="${supplierSt}" />
		<input type="hidden" id="err_contract_files" value="${err_contract_files}" />
		<input type="hidden" id="flagSupplierTypeAudit" value="${flagSupplierTypeAudit}" />
		<input type="hidden" id="infoSupplierTypeAudit" value="${infoSupplierTypeAudit}" />
		<!-- 项目戳开始 -->
		<jsp:include page="/WEB-INF/view/ses/sms/supplier_register/common_jump.jsp">
			<jsp:param value="${supplierId}" name="supplierId"/>
			<jsp:param value="${supplierSt}" name="supplierSt"/>
			<jsp:param value="5" name="currentStep"/>
		</jsp:include>
		
		<!-- <div class="container clear margin-top-30">
			<h2 class="step_flow">
				<span id="sp1" class="new_step current fl" onclick="updateStep('1')"><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span>
		    <span id="sp2" class="new_step current fl" onclick="updateStep('2')"><i class="">2</i><div class="line"></div> <span class="step_desc_01">供应商类型</span> </span>
		    <span id="ty3" class="new_step current fl" onclick="updateStep('3')"><i class="">3</i><div class="line"></div> <span class="step_desc_02">产品类别</span> </span>
		    <span id="sp4" class="new_step current fl" onclick="updateStep('4')"><i class="">4</i><div class="line"></div> <span class="step_desc_01">资质文件维护</span> </span>
		    <span id="sp5" class="new_step current fl"><i class="">5</i><div class="line"></div> <span class="step_desc_02">销售合同</span> </span>
		    <span id="sp6" class="new_step fl"><i class="">6</i><div class="line"></div> <span class="step_desc_01">采购机构</span> </span>
		    <span id="sp7" class="new_step fl"><i class="">7</i><div class="line"></div> <span class="step_desc_02">承诺书和申请表</span> </span>
		    <span id="sp8" class="new_step fl new_step_last"><i class="">8</i> <span class="step_desc_01">提交审核</span> </span>
		    <div class="clear"></div>
			</h2>
		</div> -->
 
 		<!--基本信息-->
		<div class="container content height-300">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10" >
						<ul id="page_ul_id" class="nav nav-tabs bgdd supplier_tab">
						  <c:set value="0" var="liCount"/>
							<c:if test="${fn:contains(supplierTypeIds, 'PRODUCT')}">
								<c:set value="${liCount+1}" var="liCount"/>
								<li id="li_id_1" onclick="loadPageOne('tab-1','supplier/ajaxContract.html','PRODUCT')"  class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="f18">物资-生产型合同信息</a></li>
							</c:if>
							<c:if test="${fn:contains(supplierTypeIds, 'SALES')}">
								<c:set var="icount" value="${icount + 1}"></c:set>
								<li id="li_id_2" onclick="loadPageTwo('tab-2','supplier/ajaxContract.html','SALES')" class='<c:if test="${liCount == 0}">active</c:if>'><a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18">物资-销售型合同信息</a></li>
								<c:set value="${liCount+1}" var="liCount"/>
							</c:if>
						<%-- 	<c:if test="${fn:contains(supplierTypeIds, 'PROJECT')}">
								<c:set var="icount" value="${icount + 1}"></c:set>
								<li id="li_id_3" onclick="loadPageThree('tab-3','supplier/ajaxContract.html','PROJECT')" class='<c:if test="${liCount == 0}">active</c:if>'><a aria-expanded="false" href="#tab-3" data-toggle="tab" class="f18">工程合同信息</a></li>
								<c:set value="${liCount+1}" var="liCount"/>
							</c:if> --%>
							<c:if test="${fn:contains(supplierTypeIds, 'SERVICE')}">
								<c:set var="icount" value="${icount + 1}"></c:set>
								<li id="li_id_4" onclick="loadPageFour('tab-4','supplier/ajaxContract.html','SERVICE')" class='<c:if test="${liCount == 0}">active</c:if>'><a aria-expanded="false" href="#tab-4" data-toggle="tab" class="f18">服务合同信息</a></li>
								<c:set value="${liCount+1}" var="liCount"/>
							</c:if>
						</ul>
						<div class="tab-content padding-top-20" id="tab_content_div_id">
							<c:if test="${fn:contains(supplierTypeIds, 'PRODUCT')}">
								<!-- 物资生产型 -->
								<div class="tab-pane active in fade active in height-300" id="tab-1">
								</div>
							</c:if>
							<c:if test="${fn:contains(supplierTypeIds, 'SALES')}">
								<!-- 物资销售型 -->
								<div  class="tab-pane active in fade height-300 " id="tab-2">
								</div>
							</c:if>
						<%-- 工程不必上传，直接去掉	<c:if test="${fn:contains(supplierTypeIds, 'PROJECT')}">
								<!-- 工程 -->
								<div class="tab-pane active in fade height-200 " id="tab-3">
								</div>
							</c:if> --%>
							<c:if test="${fn:contains(supplierTypeIds, 'SERVICE')}">
								<!-- 服务 -->
								<div class="tab-pane active in fade height-200 " id="tab-4">
								</div>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	 <div class="btmfix">
		<div class="mt5 mb5 tc">
			<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="prev()">上一步</button>
			<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="saveItems()">暂存</button>
			<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="next()">下一步</button>
		</div>
	</div>
	
	<form id="items_info_form_id" action="${pageContext.request.contextPath}/supplier/perfect_contract.html" method="post">
		<input name="supplierId" id="supplierId" value="${supplierId}" type="hidden" /> 
		<input name="supplierTypeIds" value="${supplierTypeIds}" type="hidden" /> 
	</form>
	<div class="footer_margin">
 		<jsp:include page="../../../../../index_bottom.jsp"></jsp:include>
  </div>
</body>
</html>
