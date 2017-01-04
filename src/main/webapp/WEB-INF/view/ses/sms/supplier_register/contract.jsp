<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML >
<html>
<head>
<%@ include file="/WEB-INF/view/front.jsp" %>
<script type="text/javascript">

	//暂存
	function saveItems(){
		/*  getCategoryId();
		$("#flag").val("");
		$("#items_info_form_id").submit(); */
	//	function temporarySave(){
		  $("input[name='flag']").val("file");
			$.ajax({
				url : "${pageContext.request.contextPath}/supplier/temporarySave.do",
				type : "post",
				data : $("#items_info_form_id").serializeArray(),
				contextType: "application/x-www-form-urlencoded",
				success:function(msg){
				 
			 	if (msg == 'ok'){
						layer.msg('暂存成功');
					} 
				  if (msg == 'failed'){
						layer.msg('暂存失败');
					}  
				}
			});
		//}
		
	}
	
	function next(){
		 $("#flag").val("5");
		$("#items_info_form_id").submit();
	}
	
	function prev(){
		  $("input[name='flag']").val("1");
		$("#items_info_form_id").submit();
	}

</script>
</head>

<body>
	<c:if test="${currSupplier.status != 7}">
	<%@ include file="/reg_head.jsp"%>
	</c:if>
	<div class="wrapper">

		<!-- 项目戳开始 -->
		<c:if test="${currSupplier.status != 7}">
		<div class="container clear margin-top-30">
				<h2 class="padding-20 mt40 ml30">
					<span class="new_step current fl"><i class="">1</i>
<!-- 						<div class="line"></div> <span class="step_desc_01">用户名密码</span> </span> <span class="new_step current fl"><i class="">2</i> -->
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
		</c:if>
 
 
 
 		<!--基本信息-->
		<div class="container content height-300">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10" >
						<ul id="page_ul_id" class="nav nav-tabs bgdd supplier_tab">
							<c:if test="${fn:contains(supplierTypeIds, 'PRODUCT')}">
								<li id="li_id_1" class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="f18">物资-生产型品目信息</a></li>
							</c:if>
							<c:if test="${fn:contains(supplierTypeIds, 'SALES')}">
								<li id="li_id_2"   ><a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18">物资-销售型品目信息</a></li>
							</c:if>
							<c:if test="${fn:contains(supplierTypeIds, 'PROJECT')}">
								<li id="li_id_3"   ><a aria-expanded="false" href="#tab-3" data-toggle="tab" class="f18">工程品目信息</a></li>
							</c:if>
							<c:if test="${fn:contains(supplierTypeIds, 'SERVICE')}">
								<li id="li_id_4"  ><a aria-expanded="false" href="#tab-4" data-toggle="tab" class="f18">服务品目信息</a></li>
							</c:if>
						</ul>
						<div class="tab-content padding-top-20" id="tab_content_div_id">
							<c:if test="${fn:contains(supplierTypeIds, 'PRODUCT')}">
								<!-- 物资生产型 -->
								<div class="tab-pane fade active in height-300" id="tab-1">
								<div class="col-md-12 col-xs-12 col-sm-12 p0 over_scroll">
								<table class="table table-bordered space_nowrap">
										  <tr>
										    <td class="info tc"> 品目名称</td>
										    <td colspan="3" class="info tc">合同上传</td>
										    <td colspan="3" class="info tc">收款进账单</td>
										  </tr>
										  
										   <tr>
									        <td class="info" class="tc"> 末级节点</td>
										       <c:forEach items="${years}" var="year">
											     <td class="info w220 tc">${year}</td>
											   </c:forEach>
											   <c:forEach items="${years}" var="year">
											     <td class="info w220 tc">${year}</td>
											   </c:forEach>
										  </tr>
										  
										  
										  <c:forEach items="${contract}" var="obj" varStatus="vs">
									      <tr>
									        <td class="info">${obj.name }</td>
										    <td class="w220">
										  
										     <div class="col-md-12 col-sm-12 col-xs-12 p0 w220" id="breach_li_id">
												 <u:upload id="pUp${(vs.index+1)*6-1}" groups="${sbUp}" businessId="${obj.id}" sysKey="1" typeId="${obj.oneContract}" auto="true" />
												 <u:show showId="pShow${(vs.index+1)*6-1}" groups="${sbShow}" businessId="${obj.id}" sysKey="1" typeId="${obj.oneContract}" />
											  </div>
										    </td>
										    <td class="w220">
										 
										     <div class="col-md-12 col-sm-12 col-xs-12 p0 w220" id="breach_li_id">
												 <u:upload id="pUp${(vs.index+1)*6-2}" groups="${sbUp}" businessId="${obj.id}" sysKey="1" typeId="${obj.twoContract}" auto="true" />
												 <u:show showId="pShow${(vs.index+1)*6-2}" groups="${sbShow}" businessId="${obj.id}" sysKey="1" typeId="${obj.twoContract}" />
											  </div>
										    </td>
										    <td class="w220">
										     
										     <div class="col-md-12 col-sm-12 col-xs-12 p0 w220" id="breach_li_id">
												 <u:upload id="pUp${(vs.index+1)*6-3}" groups="${sbUp}" businessId="${qua.id}" sysKey="1" typeId="${obj.threeContract}" auto="true" />
												 <u:show showId="pShow${(vs.index+1)*6-3}" groups="${sbShow}" businessId="${qua.id}" sysKey="1" typeId="${obj.threeContract}" />
											  </div>
										    </td>
										    <td class="w220"> 
										 
										     <div class="col-md-12 col-sm-12 col-xs-12 p0 w220" id="breach_li_id">
												 <u:upload id="pUp${(vs.index+1)*6-4}" groups="${sbUp}" businessId="${obj.id}" sysKey="1" typeId="${obj.oneBil}" auto="true" />
												 <u:show showId="pShow${(vs.index+1)*6-4}" groups="${sbShow}" businessId="${obj.id}" sysKey="1" typeId="${obj.oneBil}" />
											  </div>
										    </td>
										    <td class="w220">
										 
										     <div class="col-md-12 col-sm-12 col-xs-12 p0 w220" id="breach_li_id">
												 <u:upload id="pUp${(vs.index+1)*6-5}" groups="${sbUp}" businessId="${obj.id}" sysKey="1" typeId="${obj.twoBil}" auto="true" />
												 <u:show showId="pShow${(vs.index+1)*6-5}" groups="${sbShow}" businessId="${obj.id}" sysKey="1" typeId="${obj.twoBil}" />
											  </div>
										    </td>
										    <td class="w220">
									   
										     <div class="col-md-12 col-sm-12 col-xs-12 p0 w220" id="breach_li_id">
												 <u:upload id="pUp${(vs.index+1)*6-6}" groups="${sbUp}" businessId="${obj.id}" sysKey="1" typeId="${obj.threeBil}" auto="true" />
												 <u:show showId="pShow${(vs.index+1)*6-6}" groups="${sbShow}" businessId="${obj.id}" sysKey="1" typeId="${obj.threeBil}" />
											  </div>
										    
										     </td>
										   
										  </tr>
										</c:forEach>
										
									</table> 
									</div>
								</div>
							</c:if>
							<c:if test="${fn:contains(supplierTypeIds, 'SALES')}">
								<!-- 物资销售型 -->
								<div class="tab-pane fade height-300 " id="tab-2">
								<div class="col-xs-12 col-sm-12 col-md-12 p0 over_scroll">
								 <table class="table table-bordered space_nowrap">
						  
										  <tr>
										    <td class="info tc"> 品目名称</td>
										    <td colspan="3" class="info tc">合同上传</td>
										    <td colspan="3" class="info tc">收款进账单</td>
										  </tr>
										  
										   <tr>
									        <td class="info tc"> 末级节点</td>
										       <c:forEach items="${years}" var="year">
											     <td class="info tc">${year}</td>
											   </c:forEach>
											   <c:forEach items="${years}" var="year">
											     <td class="info tc">${year}</td>
											   </c:forEach>
										  </tr>
										  
										  
										  <c:forEach items="${saleBean}" var="obj" varStatus="vs">
									      <tr>
									        <td class="info">${obj.name }</td>
										    <td class="w220">
										  
										     <div class="col-md-12 col-sm-12 col-xs-12 p0 w220" id="breach_li_id">
												 <u:upload id="saleUp${(vs.index+1)*6-1}" groups="${sbUp}" businessId="${obj.id}" sysKey="1" typeId="${obj.oneContract}" auto="true" />
												 <u:show showId="saleShow${(vs.index+1)*6-1}" groups="${sbShow}" businessId="${obj.id}" sysKey="1" typeId="${obj.oneContract}" />
											  </div>
										    </td>
										    <td class="w220">
										 
										     <div class="col-md-12 col-sm-12 col-xs-12 p0 w220" id="breach_li_id">
												 <u:upload id="saleUp${(vs.index+1)*6-2}" groups="${sbUp}" businessId="${obj.id}" sysKey="1" typeId="${obj.twoContract}" auto="true" />
												 <u:show showId="saleShow${(vs.index+1)*6-2}" groups="${sbShow}" businessId="${obj.id}" sysKey="1" typeId="${obj.twoContract}" />
											  </div>
										    </td>
										    <td class="w220">
										     
										     <div class="col-md-12 col-sm-12 col-xs-12 p0 w220" id="breach_li_id">
												 <u:upload id="saleUp${(vs.index+1)*6-3}" groups="${sbUp}" businessId="${qua.id}" sysKey="1" typeId="${obj.threeContract}" auto="true" />
												 <u:show showId="saleShow${(vs.index+1)*6-3}" groups="${sbShow}" businessId="${qua.id}" sysKey="1" typeId="${obj.threeContract}" />
											  </div>
										    </td>
										    <td class="w220"> 
										 
										     <div class="col-md-12 col-sm-12 col-xs-12 p0 w220" id="breach_li_id">
												 <u:upload id="saleUp${(vs.index+1)*6-4}" groups="${sbUp}" businessId="${obj.id}" sysKey="1" typeId="${obj.oneBil}" auto="true" />
												 <u:show showId="saleShow${(vs.index+1)*6-4}" groups="${sbShow}" businessId="${obj.id}" sysKey="1" typeId="${obj.oneBil}" />
											  </div>
										    </td>
										    <td class="w220">
										 
										     <div class="col-md-12 col-sm-12 col-xs-12 p0 w220" id="breach_li_id">
												 <u:upload id="saleUp${(vs.index+1)*6-5}" groups="${sbUp}" businessId="${obj.id}" sysKey="1" typeId="${obj.twoBil}" auto="true" />
												 <u:show showId="saleShow${(vs.index+1)*6-5}" groups="${sbShow}" businessId="${obj.id}" sysKey="1" typeId="${obj.twoBil}" />
											  </div>
										    </td>
										    <td class="w220">
									   
										     <div class="col-md-12 col-sm-12 col-xs-12 p0 w220" id="breach_li_id">
												 <u:upload id="saleUp${(vs.index+1)*6-6}" groups="${sbUp}" businessId="${obj.id}" sysKey="1" typeId="${obj.threeBil}" auto="true" />
												 <u:show showId="saleShow${(vs.index+1)*6-6}" groups="${sbShow}" businessId="${obj.id}" sysKey="1" typeId="${obj.threeBil}" />
											  </div>
										    
										     </td>
										   
										  </tr>
										</c:forEach>
										
									</table> 
									</div>
									 
								  
									 
								</div>
							</c:if>
							<c:if test="${fn:contains(supplierTypeIds, 'PROJECT')}">
							<!-- 服务 -->
								<div class="tab-pane fade height-200" id="tab-3">
								 <table class="table table-bordered">
						  
										  <tr>
										    <td class="info tc"> 品目名称</td>
										    <td colspan="3" class="info tc">合同上传</td>
										    <td colspan="3" class="info tc">收款进账单</td>
										  </tr>
										  
										   <tr>
									        <td class="info"> 末级节点</td>
										       <c:forEach items="${years}" var="year">
											     <td class="info tc">${year}</td>
											   </c:forEach>
											   <c:forEach items="${years}" var="year">
											     <td class="info tc">${year}</td>
											   </c:forEach>
										  </tr>
										  
										  
										  <c:forEach items="${saleBean}" var="obj" varStatus="vs">
									      <tr>
									        <td class="info">${obj.name }</td>
										    <td class="w220">
										  
										     <div class="col-md-12 col-sm-12 col-xs-12 p0" id="breach_li_id">
												 <u:upload id="projectUp${(vs.index+1)*6-1}" groups="${sbUp}" businessId="${obj.id}" sysKey="1" typeId="${obj.oneContract}" auto="true" />
												 <u:show showId="projectShow${(vs.index+1)*6-1}" groups="${sbShow}" businessId="${obj.id}" sysKey="1" typeId="${obj.oneContract}" />
											  </div>
										    </td>
										    <td class="w220">
										 
										     <div class="col-md-12 col-sm-12 col-xs-12 p0" id="breach_li_id">
												 <u:upload id="projectUp${(vs.index+1)*6-2}" groups="${sbUp}" businessId="${obj.id}" sysKey="1" typeId="${obj.twoContract}" auto="true" />
												 <u:show showId="projectShow${(vs.index+1)*6-2}" groups="${sbShow}" businessId="${obj.id}" sysKey="1" typeId="${obj.twoContract}" />
											  </div>
										    </td>
										    <td class="w220">
										     
										     <div class="col-md-12 col-sm-12 col-xs-12 p0" id="breach_li_id">
												 <u:upload id="projectUp${(vs.index+1)*6-3}" groups="${sbUp}" businessId="${qua.id}" sysKey="1" typeId="${obj.threeContract}" auto="true" />
												 <u:show showId="projectShow${(vs.index+1)*6-3}" groups="${sbShow}" businessId="${qua.id}" sysKey="1" typeId="${obj.threeContract}" />
											  </div>
										    </td>
										    <td class="w220"> 
										 
										     <div class="col-md-12 col-sm-12 col-xs-12 p0" id="breach_li_id">
												 <u:upload id="projectUp${(vs.index+1)*6-4}" groups="${sbUp}" businessId="${obj.id}" sysKey="1" typeId="${obj.oneBil}" auto="true" />
												 <u:show showId="projectShow${(vs.index+1)*6-4}" groups="${sbShow}" businessId="${obj.id}" sysKey="1" typeId="${obj.oneBil}" />
											  </div>
										    </td>
										    <td class="w220">
										 
										     <div class="col-md-12 col-sm-12 col-xs-12 p0" id="breach_li_id">
												 <u:upload id="projectUp${(vs.index+1)*6-5}" groups="${sbUp}" businessId="${obj.id}" sysKey="1" typeId="${obj.twoBil}" auto="true" />
												 <u:show showId="projectShow${(vs.index+1)*6-5}" groups="${sbShow}" businessId="${obj.id}" sysKey="1" typeId="${obj.twoBil}" />
											  </div>
										    </td>
										    <td class="w220">
									   
										     <div class="col-md-12 col-sm-12 col-xs-12 p0" id="breach_li_id">
												 <u:upload id="projectUp${(vs.index+1)*6-6}" groups="${sbUp}" businessId="${obj.id}" sysKey="1" typeId="${obj.threeBil}" auto="true" />
												 <u:show showId="projectShow${(vs.index+1)*6-6}" groups="${sbShow}" businessId="${obj.id}" sysKey="1" typeId="${obj.threeBil}" />
											  </div>
										    
										     </td>
										   
										  </tr>
										</c:forEach>
										
									</table> 
									
									
									
								</div>
							</c:if>
							<c:if test="${fn:contains(supplierTypeIds, 'SERVICE')}">
								<!-- 生产 -->
								<div class="tab-pane fade height-200" id="tab-4">
										 <table class="table table-bordered">
						  
										  <tr>
										    <td class="info tc"> 品目名称</td>
										    <td colspan="3" class="info tc">合同上传</td>
										    <td colspan="3" class="info tc">收款进账单</td>
										  </tr>
										  
										   <tr>
									        <td class="info"> 末级节点</td>
										       <c:forEach items="${years}" var="year">
											     <td class="info tc">${year}</td>
											   </c:forEach>
											   <c:forEach items="${years}" var="year">
											     <td class="info tc">${year}</td>
											   </c:forEach>
										  </tr>
										  
										  
										  <c:forEach items="${saleBean}" var="obj" varStatus="vs">
									      <tr>
									        <td class="info">${obj.name }</td>
										    <td class="w220">
										  
										     <div class="col-md-12 col-sm-12 col-xs-12 p0" id="breach_li_id">
												 <u:upload id="serUp${(vs.index+1)*6-1}" groups="${sbUp}" businessId="${obj.id}" sysKey="1" typeId="${obj.oneContract}" auto="true" />
												 <u:show showId="serpShow${(vs.index+1)*6-1}" groups="${sbShow}" businessId="${obj.id}" sysKey="1" typeId="${obj.oneContract}" />
											  </div>
										    </td>
										    <td class="w220">
										 
										     <div class="col-md-12 col-sm-12 col-xs-12 p0" id="breach_li_id">
												 <u:upload id="serUp${(vs.index+1)*6-2}" groups="${sbUp}" businessId="${obj.id}" sysKey="1" typeId="${obj.twoContract}" auto="true" />
												 <u:show showId="serpShow${(vs.index+1)*6-2}" groups="${sbShow}" businessId="${obj.id}" sysKey="1" typeId="${obj.twoContract}" />
											  </div>
										    </td>
										    <td class="w220">
										     
										     <div class="col-md-12 col-sm-12 col-xs-12 p0" id="breach_li_id">
												 <u:upload id="serUp${(vs.index+1)*6-3}" groups="${sbUp}" businessId="${qua.id}" sysKey="1" typeId="${obj.threeContract}" auto="true" />
												 <u:show showId="serpShow${(vs.index+1)*6-3}" groups="${sbShow}" businessId="${qua.id}" sysKey="1" typeId="${obj.threeContract}" />
											  </div>
										    </td>
										    <td class="w220"> 
										 
										     <div class="col-md-12 col-sm-12 col-xs-12 p0" id="breach_li_id">
												 <u:upload id="serUp${(vs.index+1)*6-4}" groups="${sbUp}" businessId="${obj.id}" sysKey="1" typeId="${obj.oneBil}" auto="true" />
												 <u:show showId="serpShow${(vs.index+1)*6-4}" groups="${sbShow}" businessId="${obj.id}" sysKey="1" typeId="${obj.oneBil}" />
											  </div>
										    </td>
										    <td class="w220">
										 
										     <div class="col-md-12 col-sm-12 col-xs-12 p0" id="breach_li_id">
												 <u:upload id="serUp${(vs.index+1)*6-5}" groups="${sbUp}" businessId="${obj.id}" sysKey="1" typeId="${obj.twoBil}" auto="true" />
												 <u:show showId="serpShow${(vs.index+1)*6-5}" groups="${sbShow}" businessId="${obj.id}" sysKey="1" typeId="${obj.twoBil}" />
											  </div>
										    </td>
										    <td class="w220">
									   
										     <div class="col-md-12 col-sm-12 col-xs-12 p0" id="breach_li_id">
												 <u:upload id="serUp${(vs.index+1)*6-6}" groups="${sbUp}" businessId="${obj.id}" sysKey="1" typeId="${obj.threeBil}" auto="true" />
												 <u:show showId="serpShow${(vs.index+1)*6-6}" groups="${sbShow}" businessId="${obj.id}" sysKey="1" typeId="${obj.threeBil}" />
											  </div>
										    
										     </td>
										   
										  </tr>
										</c:forEach>
									</table> 
									
									
									
								</div>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		
		
 
	<%-- 	<!--基本信息-->
		<div class="container content height-300">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10" >
						 
						  <table class="table table-bordered">
						  
										  <tr>
										    <td class="info"> 品目名称</td>
										    <td colspan="3">合同上传</td>
										    <td colspan="3">收款进账单</td>
										  </tr>
										  
										   <tr>
									        <td class="info"> 末级节点</td>
										       <c:forEach items="${years}" var="year">
											     <td class="info">${year}</td>
											   </c:forEach>
											   <c:forEach items="${years}" var="year">
											     <td class="info">${year}</td>
											   </c:forEach>
										  </tr>
										  
										  
										  <c:forEach items="${contract}" var="obj" varStatus="vs">
									      <tr>
									        <td class="info">${obj.name }</td>
										    <td class="info">
										  
										     <div class="col-md-12 col-sm-12 col-xs-12 p0" id="breach_li_id">
												 <u:upload id="pUp${(vs.index+1)*6-1}" groups="${sbUp}" businessId="${obj.id}" sysKey="1" typeId="${obj.oneContract}" auto="true" />
												 <u:show showId="pShow${(vs.index+1)*6-1}" groups="${sbShow}" businessId="${obj.id}" sysKey="1" typeId="${obj.oneContract}" />
											  </div>
										    </td>
										    <td class="info">
										 
										     <div class="col-md-12 col-sm-12 col-xs-12 p0" id="breach_li_id">
												 <u:upload id="pUp${(vs.index+1)*6-2}" groups="${sbUp}" businessId="${obj.id}" sysKey="1" typeId="${obj.twoContract}" auto="true" />
												 <u:show showId="pShow${(vs.index+1)*6-2}" groups="${sbShow}" businessId="${obj.id}" sysKey="1" typeId="${obj.twoContract}" />
											  </div>
										    </td>
										    <td class="info">
										     
										     <div class="col-md-12 col-sm-12 col-xs-12 p0" id="breach_li_id">
												 <u:upload id="pUp${(vs.index+1)*6-3}" groups="${sbUp}" businessId="${qua.id}" sysKey="1" typeId="${obj.threeContract}" auto="true" />
												 <u:show showId="pShow${(vs.index+1)*6-3}" groups="${sbShow}" businessId="${qua.id}" sysKey="1" typeId="${obj.threeContract}" />
											  </div>
										    </td>
										    <td class="info"> 
										 
										     <div class="col-md-12 col-sm-12 col-xs-12 p0" id="breach_li_id">
												 <u:upload id="pUp${(vs.index+1)*6-4}" groups="${sbUp}" businessId="${obj.id}" sysKey="1" typeId="${obj.oneBil}" auto="true" />
												 <u:show showId="pShow${(vs.index+1)*6-4}" groups="${sbShow}" businessId="${obj.id}" sysKey="1" typeId="${obj.oneBil}" />
											  </div>
										    </td>
										    <td class="info">
										 
										     <div class="col-md-12 col-sm-12 col-xs-12 p0" id="breach_li_id">
												 <u:upload id="pUp${(vs.index+1)*6-5}" groups="${sbUp}" businessId="${obj.id}" sysKey="1" typeId="${obj.twoBil}" auto="true" />
												 <u:show showId="pShow${(vs.index+1)*6-5}" groups="${sbShow}" businessId="${obj.id}" sysKey="1" typeId="${obj.twoBil}" />
											  </div>
										    </td>
										    <td class="info">
									   
										     <div class="col-md-12 col-sm-12 col-xs-12 p0" id="breach_li_id">
												 <u:upload id="pUp${(vs.index+1)*6-6}" groups="${sbUp}" businessId="${obj.id}" sysKey="1" typeId="${obj.threeBil}" auto="true" />
												 <u:show showId="pShow${(vs.index+1)*6-6}" groups="${sbShow}" businessId="${obj.id}" sysKey="1" typeId="${obj.threeBil}" />
											  </div>
										    
										     </td>
										   
										  </tr>
										</c:forEach>
										
									</table> 
								
					</div>
				</div>
			</div>
		</div> --%>
	</div>
	
	 <div class="btmfix">
	  	  <div style="margin-top: 15px;text-align: center;">
	  	  	   	<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="prev()">上一步</button>
				<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="saveItems()">暂存</button>
				<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="next()">下一步</button>
	  	  </div>
	</div>
	
	
	<form id="items_info_form_id" action="${pageContext.request.contextPath}/supplier_item/save_or_update.html" method="post">
		<input name="supplierId" value="${supplierId}" type="hidden" /> 
		<input name="supplierTypeIds"  value="${supplierTypeIds}"    type="hidden" /> 
		<input name="flag" value="" id="flag" type="hidden" /> 
	 
	</form>
	<!-- footer -->
	<c:if test="${currSupplier.status != 7}"><jsp:include page="../../../../../index_bottom.jsp"></jsp:include></c:if>
</body>
</html>
