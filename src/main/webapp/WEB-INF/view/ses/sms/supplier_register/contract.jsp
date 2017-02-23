<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML >
<html>
<head>
<%@ include file="/reg_head.jsp"%>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<title>供应商注册</title>
		<style type="text/css">
.current {
	cursor: pointer;
}
</style>
<script type="text/javascript">
	$(function() {
		var err = "${err_contract_files}";
		if (err != null && err != "") {
			layer.msg(err, {offset: '300px'});
		}
		var PRODUCT = $("#li_id_1").text();
		var SALES = $("#li_id_2").text();
		var PROJECT = $("#li_id_3").text();
		var SERVICE = $("#li_id_4").text();
		//加载默认的页签
		if(PRODUCT == "物资-生产型品目信息") {
			loadPageOne('tab-1','supplier/ajaxContract.html','PRODUCT');
			return;
		}
	 		if(SALES == "物资-销售型品目信息") {
			loadPageTwo('tab-2','supplier/ajaxContract.html','SALES');
			return;
		}
		if(PROJECT == "工程品目信息") {
			loadPageThree('tab-3','supplier/ajaxContract.html','PROJECT');
			return;
		}
		if(SERVICE == "服务品目信息") {
			loadPageFour('tab-4','supplier/ajaxContract.html','SERVICE');
			return;
		}
	});



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
		sessionStorage.formF=JSON.stringify($("#items_info_form_id").serializeArray());
		$("#items_info_form_id").submit();
	}
	
	function prev(){
		$("input[name='flag']").val("1");
		$("#items_info_form_id").submit();
	}
	var index;
	function loadPageOne(id, url, supplierTypeId) {
		 index = layer.load(1, {
			  shade: [0.1,'#fff'] //0.1透明度的白色背景
		 });
	     var supplierId = $("#supplierId").val();
	  	 var path = "${pageContext.request.contextPath}/" + url + "?supplierId=" + supplierId + "&supplierTypeId=" + supplierTypeId;
	  	 $("#tab-4").html("");
	  	 $("#tab-2").html("");
	  	 $("#tab-3").html("");
	  	 $("#"+id).load(path);
	  	 init_web_upload_in("#" + id);
	}
	
	function loadPageTwo(id, url, supplierTypeId) {
		 index = layer.load(1, {
			  shade: [0.1,'#fff'] //0.1透明度的白色背景
		 });
	     var supplierId = $("#supplierId").val();
	  	 var path = "${pageContext.request.contextPath}/" + url + "?supplierId=" + supplierId + "&supplierTypeId=" + supplierTypeId;
	  	 $("#tab-1").html("");
	  	 $("#tab-4").html("");
	  	 $("#tab-3").html("");
	  	 $("#"+id).load(path);
	  	 init_web_upload_in("#" + id);
	}
	
	function loadPageThree(id, url, supplierTypeId) {
		 index = layer.load(1, {
			  shade: [0.1,'#fff'] //0.1透明度的白色背景
		 });
	     var supplierId = $("#supplierId").val();
	  	 var path = "${pageContext.request.contextPath}/" + url + "?supplierId=" + supplierId + "&supplierTypeId=" + supplierTypeId;
	  	 $("#tab-1").html("");
	  	 $("#tab-2").html("");
	  	 $("#tab-4").html("");
	  	 $("#"+id).load(path);
	  	 init_web_upload_in("#" + id);
	}
	
	function loadPageFour(id, url, supplierTypeId) {
		 index = layer.load(1, {
			  shade: [0.1,'#fff'] //0.1透明度的白色背景
		 });
	     var supplierId = $("#supplierId").val();
	  	 var path = "${pageContext.request.contextPath}/" + url + "?supplierId=" + supplierId + "&supplierTypeId=" + supplierTypeId;
	  	 $("#tab-1").html("");
	  	 $("#tab-2").html("");
	  	 $("#tab-3").html("");
	  	 $("#"+id).load(path);
	  	 init_web_upload_in("#" + id);
	}
			sessionStorage.locationE=true;
			sessionStorage.index=5;
</script>
</head>

<body>
	<div class="wrapper">
<%@include file="supplierNav.jsp" %>
	<!-- <!-- 	项目戳开始
		<div class="container clear margin-top-30">
				<h2 class="padding-20 mt40 ml30">
					<span class="new_step current fl"><i class="">1</i>
						<div class="line"></div> <span class="step_desc_02">基本信息</span> </span> <span class="new_step current fl"><i class="">2</i>
						<div class="line"></div> <span class="step_desc_01">供应商类型</span> </span> <span class="new_step current fl"><i class="">3</i>
						<div class="line"></div> <span class="step_desc_02">产品类别</span> </span> <span class="new_step current fl"><i class="">4</i>
						<div class="line"></div> <span class="step_desc_01">资质文件维护</span> </span> <span class="new_step current fl"><i class="">5</i>
						<div class="line"></div> <span class="step_desc_02">销售(承包)合同</span> </span> <span class="new_step fl"><i class="">6</i>
						<div class="line"></div> <span class="step_desc_01">采购机构</span> </span> <span class="new_step fl"><i class="">7</i>
						<div class="line"></div> <span class="step_desc_02">承诺书和申请表</span> </span> <span class="new_step fl"><i class="">8</i> 
						<span class="step_desc_01">提交</span> 
					</span>
					<div class="clear"></div>
				</h2>
			</div>
 
  --> -->
 
 		<!--基本信息-->
		<div class="container content height-300">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10" >
						<ul id="page_ul_id" class="nav nav-tabs bgdd supplier_tab">
						  <c:set value="0" var="liCount"/>
							<c:if test="${fn:contains(supplierTypeIds, 'PRODUCT')}">
								<c:set value="${liCount+1}" var="liCount"/>
								<li id="li_id_1" onclick="loadPageOne('tab-1','supplier/ajaxContract.html','PRODUCT')"  class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="f18">物资-生产型品目信息</a></li>
							</c:if>
							<c:if test="${fn:contains(supplierTypeIds, 'SALES')}">
								<c:set var="icount" value="${icount + 1}"></c:set>
								<li id="li_id_2" onclick="loadPageTwo('tab-2','supplier/ajaxContract.html','SALES')" class='<c:if test="${liCount == 0}">active</c:if>'><a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18">物资-销售型品目信息</a></li>
								<c:set value="${liCount+1}" var="liCount"/>
							</c:if>
							<c:if test="${fn:contains(supplierTypeIds, 'PROJECT')}">
								<c:set var="icount" value="${icount + 1}"></c:set>
								<li id="li_id_3" onclick="loadPageThree('tab-3','supplier/ajaxContract.html','PROJECT')" class='<c:if test="${liCount == 0}">active</c:if>'><a aria-expanded="false" href="#tab-3" data-toggle="tab" class="f18">工程品目信息</a></li>
								<c:set value="${liCount+1}" var="liCount"/>
							</c:if>
							<c:if test="${fn:contains(supplierTypeIds, 'SERVICE')}">
								<c:set var="icount" value="${icount + 1}"></c:set>
								<li id="li_id_4" onclick="loadPageFour('tab-4','supplier/ajaxContract.html','SERVICE')" class='<c:if test="${liCount == 0}">active</c:if>'><a aria-expanded="false" href="#tab-4" data-toggle="tab" class="f18">服务品目信息</a></li>
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
							<c:if test="${fn:contains(supplierTypeIds, 'PROJECT')}">
							<!-- 工程 -->
								<div class="tab-pane active in fade height-200 " id="tab-3">
								</div>
							</c:if>
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
	
	
	<form id="items_info_form_id" action="${pageContext.request.contextPath}/supplier_item/save_or_update.html" method="post">
		<input name="supplierId" id="supplierId" value="${supplierId}" type="hidden" /> 
		<input name="supplierTypeIds"  value="${supplierTypeIds}"    type="hidden" /> 
		<input name="flag" value="" id="flag" type="hidden" /> 
	 
	</form>
	<!-- footer -->
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/sms/commons.js"></script>
</html>
