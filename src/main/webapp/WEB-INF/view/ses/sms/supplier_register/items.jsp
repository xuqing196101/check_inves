	<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/reg_head.jsp"%>
<c:if test="${currSupplier.status == 2}">
	<%@ include file="/WEB-INF/view/ses/sms/supplier_register/supplier_purchase_dept.jsp"%>
</c:if>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/sms/supplier_register/items.js"></script>
<title>供应商注册</title>
</head>

<body>
	<div class="wrapper">
		<!-- 隐藏域 -->
		<input type="hidden" id="supplierId" value="${currSupplier.id}" />
		<input type="hidden" id="supplierSt" value="${currSupplier.status}" />
		<input type="hidden" id="productError" value="${productError}" />
		<input type="hidden" id="sellError" value="${sellError}" />
		<input type="hidden" id="projectError" value="${projectError}" />
		<input type="hidden" id="serverError" value="${serverError}" />
		<input type="hidden" id="defaultPage" value="${defaultPage}" />
		<!-- 项目戳开始 -->
		<jsp:include page="/WEB-INF/view/ses/sms/supplier_register/common_jump.jsp">
			<jsp:param value="${currSupplier.id}" name="supplierId"/>
			<jsp:param value="${currSupplier.status}" name="supplierSt"/>
			<jsp:param value="3" name="currentStep"/>
		</jsp:include>
		<!-- <div class="container clear margin-top-30">
			<h2 class="step_flow">
				<span id="sp1" class="new_step current fl" onclick="updateStep('1')"><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span>
	            <span id="sp2" class="new_step current fl" onclick="updateStep('2')"><i class="">2</i><div class="line"></div> <span class="step_desc_01">供应商类型</span> </span>
	            <span id="ty3" class="new_step current fl"><i class="">3</i><div class="line"></div> <span class="step_desc_02">产品类别</span> </span>
	            <span id="sp4" class="new_step fl"><i class="">4</i><div class="line"></div> <span class="step_desc_01">资质文件维护</span> </span>
	            <span id="sp5" class="new_step fl"><i class="">5</i><div class="line"></div> <span class="step_desc_02">销售合同</span> </span>
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
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PRODUCT')}">
								<li id="li_id_1" onclick="loadTab('PRODUCT','tree_ul_id_1',1);" class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="f18">物资-生产型产品类别信息</a></li>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SALES')}">
								<li id="li_id_2" onclick="loadTab('SALES','tree_ul_id_2',2);" ><a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18">物资-销售型产品类别信息</a></li>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PROJECT')}">
								<li id="li_id_3" onclick="loadTab('PROJECT','tree_ul_id_3',null);" ><a aria-expanded="false" href="#tab-3" data-toggle="tab" class="f18">工程产品类别信息</a></li>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SERVICE')}">
								<li id="li_id_4" onclick="loadTab('SERVICE','tree_ul_id_4',null);" ><a aria-expanded="false" href="#tab-4" data-toggle="tab" class="f18">服务产品类别信息</a></li>
							</c:if>
						</ul>
						<div class="tab-content padding-top-20" id="tab_content_div_id">
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PRODUCT')}">
								<!-- 物资生产型 -->
								<div class="tab-pane fade active in height-300" id="tab-1">
									<h2 class="f16 ">
											<font color="red">*</font> 勾选物资生产型产品类别信息
									</h2>
									<div id="div-1" class="mb10 col-md-12 col-xs-12 col-sm-12 p0">
										<div class="fl mr5">
								  			产品类别：<input type="text" id="cate-1">
								  		</div>
								  		<div class="fl mr5">
								                                      目录编码：<input type="text" id="code-1">
								        </div> 
								  	<input class="btn mt1 fl" type="button" value="搜索" onclick="searchCate('cate-1','tree_ul_id_1','PRODUCT',1,'code-1')"/>
								  	</div>
									<div class="lr0_tbauto col-md-12 col-sm-12 col-xs-12 p0">
										<ul id="tree_ul_id_1" class="ztree_supplier mt30"></ul>
									</div>
								</div>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SALES')}">
								<!-- 物资销售型 -->
								<div class="tab-pane fade height-300" id="tab-2">
									<h2 class="f16 ">
											<font color="red">*</font> 勾选物资销售型产品类别信息
									</h2>
									<div id="div-2" class="mb10 col-md-12 col-xs-12 col-sm-12 p0">
										<div class="fl mr5">
								  	                     产品类别：<input type="text" id="cate-2">
								  	    </div>
								  	    <div class="fl mr5">
								                                 目录编码：<input type="text" id="code-2">
								        </div>
								  	<input class="btn mt1 fl" type="button" value="搜索" onclick="searchCate('cate-2','tree_ul_id_2','SALES',2,'code-2')"/>
								  	</div>
									<div class="lr0_tbauto col-md-12 col-sm-12 col-xs-12 p0">
										<ul id="tree_ul_id_2" class="ztree_supplier mt30"></ul>
									</div>
								</div>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PROJECT')}">
							<!-- 服务 -->
								<div class="tab-pane fade height-200" id="tab-3">
									<h2 class="f16  ">
									      	<font color="red">*</font> 勾选工程产品类别信息
									</h2>
									<div id="div-3" class="mb10 col-md-12 col-xs-12 col-sm-12 p0">
									    <div class="fl mr5">
								  			产品类别：<input type="text" id="cate-3">
								  		</div>
								  		<div class="fl mr5">
								          	目录编码：<input type="text" id="code-3">
								        </div>
								  	<input class="btn mt1 fl" type="button" value="搜索" onclick="searchCate('cate-3','tree_ul_id_3','PROJECT',null,'code-3')"/>
								  	</div>
									<div class="lr0_tbauto col-md-12 col-xs-12 col-sm-12 p0">
										<ul id="tree_ul_id_3" class="ztree_supplier mt30"></ul>
									</div>
								</div>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SERVICE')}">
								<!-- 生产 -->
								<div class="tab-pane fade height-200" id="tab-4">
									<h2 class="f16">
										 <font color="red">*</font> 勾选服务产品类别信息
									</h2>
									<div id="div-4" class="mb10 col-md-12 col-xs-12 col-sm-12 p0">
										<div class="fl mr5">
								  			产品类别：<input type="text" id="cate-4">
								  		</div>
								  		<div class="fl mr5">
								        	  目录编码：<input type="text" id="code-4">
								        </div>
								  	<input class="btn mt1 fl" type="button" value="搜索" onclick="searchCate('cate-4','tree_ul_id_4','SERVICE',null,'code-4')"/>
								  	</div>
									<div class="lr0_tbauto col-md-12 col-sm-12 col-xs-12 p0">
										<ul id="tree_ul_id_4" class="ztree_supplier mt30"></ul>
									</div>
								</div>
							</c:if>
							<div class="mt20" id="tbody_category"></div>
							<div id="pagediv" align="right" class="mb50"></div>
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
			<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="next(1)">下一步</button>
 	  </div>
	</div>
	
	
	<form id="items_info_form_id" action="${pageContext.request.contextPath}/supplier/perfect_items.html" method="post">
		<input name="supplierId" id="supplierId" value="${currSupplier.id}" type="hidden" /> 
		<input name="categoryId" value=""  id="categoryId" type="hidden" />
		<input name="clickFlag" value=""  id="clickFlag" type="hidden" />
		<input name="flag" value=""  id="flag" type="hidden" />
		<input name="supplierTypeIds" type="hidden" value="${currSupplier.supplierTypeIds }" />
		<input name="supplierTypeRelateId"  id="supplierTypeRelateId" type="hidden" value="" />
	</form>
   <div class="footer_margin">
   		<jsp:include page="../../../../../index_bottom.jsp"></jsp:include>
   </div>
</body>
</html>
