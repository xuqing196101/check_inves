<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML >
<html>
<head>
<%@ include file="/reg_head.jsp"%>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<script type="text/javascript">

	//暂存
	function saveItems(){
		$("input[name='flag']").val("file");
		$.ajax({
			url : "${pageContext.request.contextPath}/supplier/temporarySave.do",
			type : "post",
			data : $("#items_info_form_id").serializeArray(),
			contextType: "application/x-www-form-urlencoded",
			success:function (msg) {
				if (msg == 'ok'){
					layer.msg('暂存成功');
				} 
				if (msg == 'failed'){
					layer.msg('暂存失败');
				}  
			}
		});
	}
	
	function next(){
		$("#flag").val("5");
		$("#items_info_form_id").submit();
	}
	
	function prev(){
		$("input[name='flag']").val("1");
		$("#items_info_form_id").submit();
	}
	
	function loadPageOne(id, url, supplierTypeId) {
	     var supplierId = $("#supplierId").val();
	  	 var path = "${pageContext.request.contextPath}/" + url + "?supplierId=" + supplierId + "&supplierTypeId=" + supplierTypeId;
	  	 $("#"+id).load(path);
	}
	
	function loadPageTwo(id, url, supplierTypeId) {
	     var supplierId = $("#supplierId").val();
	  	 var path = "${pageContext.request.contextPath}/" + url + "?supplierId=" + supplierId + "&supplierTypeId=" + supplierTypeId;
	  	 $("#"+id).load(path);
	}
	
	function loadPageThree(id, url, supplierTypeId) {
	     var supplierId = $("#supplierId").val();
	  	 var path = "${pageContext.request.contextPath}/" + url + "?supplierId=" + supplierId + "&supplierTypeId=" + supplierTypeId;
	  	 $("#"+id).load(path);
	}
	
	function loadPageFour(id, url, supplierTypeId) {
	     var supplierId = $("#supplierId").val();
	  	 var path = "${pageContext.request.contextPath}/" + url + "?supplierId=" + supplierId + "&supplierTypeId=" + supplierTypeId;
	  	 $("#"+id).load(path);
	}
</script>
</head>

<body>
	<div class="wrapper">

		<!-- 项目戳开始 -->
		<div class="container clear margin-top-30">
				<h2 class="padding-20 mt40 ml30">
					<span class="new_step current fl"><i class="">1</i>
						<div class="line"></div> <span class="step_desc_02">基本信息</span> </span> <span class="new_step current fl"><i class="">2</i>
						<div class="line"></div> <span class="step_desc_01">供应商类型</span> </span> <span class="new_step current fl"><i class="">3</i>
						<div class="line"></div> <span class="step_desc_02">品目信息</span> </span> <span class="new_step current fl"><i class="">4</i>
						<div class="line"></div> <span class="step_desc_01">资质文件维护</span> </span> <span class="new_step current fl"><i class="">5</i>
						<div class="line"></div> <span class="step_desc_02">品目合同上传</span> </span> <span class="new_step fl"><i class="">6</i>
						<div class="line"></div> <span class="step_desc_01">初审采购机构</span> </span> <span class="new_step fl"><i class="">7</i>
						<div class="line"></div> <span class="step_desc_02">打印申请表</span> </span> <span class="new_step fl"><i class="">8</i> 
						<span class="step_desc_01">申请表承诺书上传</span> 
					</span>
					<div class="clear"></div>
				</h2>
			</div>
 
 
 
 		<!--基本信息-->
		<div class="container content height-300">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10" >
						<ul id="page_ul_id" class="nav nav-tabs bgdd supplier_tab">
							<c:set var="icount" value="0"></c:set>
							<c:if test="${fn:contains(supplierTypeIds, 'PRODUCT')}">
								<c:set var="icount" value="${icount + 1}"></c:set>
								<li id="li_id_1" onclick="loadPageOne('tab-1','supplier/ajaxContract.html','PRODUCT')" <c:if test="${icount == 0}">class="active"</c:if>><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="f18">物资-生产型品目信息</a></li>
							</c:if>
							<c:if test="${fn:contains(supplierTypeIds, 'SALES')}">
								<c:set var="icount" value="${icount + 1}"></c:set>
								<li id="li_id_2" onclick="loadPageTwo('tab-2','supplier/ajaxContract.html','SALES')" <c:if test="${icount == 0}">class="active"</c:if>><a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18">物资-销售型品目信息</a></li>
							</c:if>
							<c:if test="${fn:contains(supplierTypeIds, 'PROJECT')}">
								<c:set var="icount" value="${icount + 1}"></c:set>
								<li id="li_id_3" onclick="loadPageThree('tab-3','supplier/ajaxContract.html','PROJECT')" <c:if test="${icount == 0}">class="active"</c:if>><a aria-expanded="false" href="#tab-3" data-toggle="tab" class="f18">工程品目信息</a></li>
							</c:if>
							<c:if test="${fn:contains(supplierTypeIds, 'SERVICE')}">
								<c:set var="icount" value="${icount + 1}"></c:set>
								<li id="li_id_4" onclick="loadPageFour('tab-4','supplier/ajaxContract.html','SERVICE')" <c:if test="${icount == 0}">class="active"</c:if>><a aria-expanded="false" href="#tab-4" data-toggle="tab" class="f18">服务品目信息</a></li>
							</c:if>
						</ul>
						<div class="tab-content padding-top-20" id="tab_content_div_id">
							<c:if test="${fn:contains(supplierTypeIds, 'PRODUCT')}">
								<!-- 物资生产型 -->
								<div class="tab-pane fade active in height-300" id="tab-1">
								</div>
							</c:if>
							<c:if test="${fn:contains(supplierTypeIds, 'SALES')}">
								<!-- 物资销售型 -->
								<div class="tab-pane fade <c:if test="${icount == 0}">class="active"</c:if> height-300 " id="tab-2">
								</div>
							</c:if>
							<c:if test="${fn:contains(supplierTypeIds, 'PROJECT')}">
							<!-- 工程 -->
								<div class="tab-pane fade height-300 " id="tab-3">
								</div>
							</c:if>
							<c:if test="${fn:contains(supplierTypeIds, 'SERVICE')}">
								<!-- 服务 -->
								<div class="tab-pane fade height-300 " id="tab-4">
								</div>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	 <div class="btmfix">
	  	  <div style="margin-top: 15px;text-align: center;">
	  	  	   	<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="prev()">上一步</button>
				<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="saveItems()">暂存</button>
				<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="next()">下一步</button>
	  	  </div>
	</div>
	
	
	<form id="items_info_form_id" action="${pageContext.request.contextPath}/supplier_item/save_or_update.html" method="post">
		<input name="supplierId" id="supplierId" value="${supplierId}" type="hidden" /> 
		<input name="supplierTypeIds"  value="${supplierTypeIds}"    type="hidden" /> 
		<input name="flag" value="" id="flag" type="hidden" /> 
	 
	</form>
	<!-- footer -->
	<c:if test="${currSupplier.status != 7}"><jsp:include page="../../../../../index_bottom.jsp"></jsp:include></c:if>
</body>
</html>
