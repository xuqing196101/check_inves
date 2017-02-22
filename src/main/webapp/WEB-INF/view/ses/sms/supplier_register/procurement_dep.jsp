<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/reg_head.jsp"%>
<title>供应商注册</title>
		<style type="text/css">
.current {
	cursor: pointer;
}
</style>
<script type="text/javascript">

	$(function() {
		var procurementDepId = "${currSupplier.procurementDepId}";
		$(":radio").each(function() {
			var value = $(this).val();
			if (value == procurementDepId) {
				$(this).prop("checked", true);
			}
		});
		showJiGou($("#children_area_select_id"));
	});
	
	/** 保存基本信息 */
	function saveProcurementDep(flag) {
		var size = $(":radio:checked").size();
		if(flag==='prev'){
			  $("input[name='flag']").val(flag);
			  sessionStorage.formG=JSON.stringify($("#procurement_dep_form_id").serializeArray());
			$("#procurement_dep_form_id").submit();
		}else{
			if (!size) {
				layer.msg("请选择一个初审采购机构", {
					offset : '300px',
				});
				return;
			}
			var procurementDepId = $(":radio:checked").val();
			$("input[name='procurementDepId']").val(procurementDepId);
			  $("input[name='flag']").val(flag);
			  $("#procurement_dep_form_id").submit();
		}
		
		

	}
	function prev(){
		 $("#flag").val("5");
		 $("#items_info_form_id").submit();
	}
	
	///暂存
	function temporarySave(){
		
		var procurementDepId = $("input[type='radio']:checked").val();
		$("#procurementDepId").val(procurementDepId);
		
		
		$("input[name='flag']").val("1");
		$.ajax({
			url : "${pageContext.request.contextPath}/supplier/temporarySave.do",
			type : "post",
			data : $("#procurement_dep_form_id").serializeArray(),
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
	}
	
	function checkDep(obj){
		$("#procurementDepId").val(obj.value);
	}
		sessionStorage.locationF=true;
		sessionStorage.index=6;
</script>

</head>

<body>
	<div class="wrapper">
<%@include file="supplierNav.jsp" %>
		<%-- <!-- 项目戳开始 -->
		<c:if test="${currSupplier.status != 7}">
			<div class="container clear margin-top-30">
				<h2 class="padding-20 mt40 ml30">
					<span class="new_step current fl"><i class="">1</i>
					<div class="line"></div> <span class="step_desc_01">基本信息</span> </span> <span class="new_step current fl"><i class="">2</i>
						<div class="line"></div> <span class="step_desc_02">供应商类型</span> </span> <span class="new_step current fl"><i class="">3</i>
						<div class="line"></div> <span class="step_desc_01">产品类别</span> </span> <span class="new_step current fl"><i class="">4</i>
						<div class="line"></div> <span class="step_desc_02">资质文件维护</span> </span> <span class="new_step current fl"><i class="">5</i>
						<div class="line"></div> <span class="step_desc_01">销售(承包)合同</span> </span> <span class="new_step current  fl"><i class="">6</i>
						<div class="line"></div> <span class="step_desc_02">采购机构</span> </span> <span class="new_step fl"><i class="">7</i>
						<div class="line"></div> <span class="step_desc_01">承诺书和申请表</span> </span> <span class="new_step fl"><i class="">8</i> 
						<span class="step_desc_02">提交</span> 
					</span>
					<div class="clear"></div>
				</h2>
			</div>
		</c:if> --%>

		<!--基本信息-->
		<div class="container content height-350">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<form id="procurement_dep_form_id" action="${pageContext.request.contextPath}/supplier/perfect_dep.html" method="post">
							<input name="id" value="${currSupplier.id}" type="hidden" />
							<input name="procurementDepId" type="hidden" id="procurementDepId"/>
							<input  name="org" id="orgId" value="${orgnization.id  }" type="hidden" />
							<input name="supplierTypeIds"  value="${supplierTypeIds}"    type="hidden" /> 
							<input name="jsp" type="hidden" />
							<input name="flag"  type="hidden" />
						</form>
						<div class="tab-content padding-top-20">
							<div class="tab-pane fade active in height-300" id="tab-1">
								<div class="margin-bottom-0 categories mb50">
									<h2 class="f16 ">
										推荐采购机构（以公司注册地址作为推荐采购机构依据）
									</h2>
									<table class="table table-bordered table-condensed">
										<thead>
											<tr>
												<th class="info w30"><input type="radio" disabled="disabled"></th>
												<th class="info w50">序号</th>
												<th class="info w300">采购机构</th>
												<th class="info">地点</th>
											</tr>
										</thead>
										<tbody id="purchase_orgs2">
											<c:set var="vs" value="1"/>
											<c:forEach items="${allPurList}" var="org1" varStatus="vs1">
											  <c:if test="${org1.cityId eq currSupplier.address}">
												<tr>
													<td class="tc"><input type="radio" value="${org1.id}" onclick="checkDep(this)" name="procurementDepId" <c:if test="${org1.provinceId==currSupplier.procurementDepId}"> checked='checked' </c:if> /></td>
													<td class="tc">${vs}</td>
													<td class="tc">${org1.shortName}</td>
													<td class="tc">${org1.address}</td>
												</tr>
												<c:set var="vs" value="${vs + 1}"/>
											  </c:if>
											</c:forEach>
										</tbody>
									</table>
									<h2 class="f16 ">
										 其他采购机构
									</h2>
									<table class="table table-bordered table-condensed">
										<thead>
											<tr>
												<th class="info w30"><input type="radio" disabled="disabled"></th>
												<th class="info w50">序号</th>
												<th class="info w300">采购机构</th>
												<th class="info">地点</th>
											</tr>
										</thead>
										<tbody id="purchase_orgs2">
											<c:set var="vs" value="1"/>
											<c:forEach items="${allPurList}" var="org1">
												<c:if test="${org1.cityId ne currSupplier.address}">
												<tr>
													<td class="tc"><input type="radio" value="${org1.id}" onclick="checkDep(this)" name="procurementDepId" <c:if test="${org1.provinceId==currSupplier.procurementDepId}"> checked='checked' </c:if> /></td>
													<td class="tc">${vs}</td>
													<td class="tc">${org1.shortName}</td>
													<td class="tc">${org1.address}</td>
												</tr>
												<c:set var="vs" value="${vs + 1}"/>
											  </c:if>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	  <div class="btmfix">
	  	  <div style="margin-top: 15px;text-align: center;">
	  	  	   			<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="prev()">上一步</button>
					    <button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="temporarySave()">暂存</button>
					    <button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveProcurementDep('next')">下一步</button>
	  	  </div>
	  </div>
	  
	  <form id="items_info_form_id" action="${pageContext.request.contextPath}/supplier/contract.html" method="post">
		<input name="supplierId" value="${currSupplier.id}" type="hidden" /> 
		<input name="supplierTypeIds"  value="${supplierTypeIds}"    type="hidden" /> 
		<input name="flag" value="1" id="flag" type="hidden" /> 
	</form>
	
	
	<!-- footer -->
	<c:if test="${currSupplier.status != 7}">
	</c:if>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/sms/commons.js"></script>
</html>
