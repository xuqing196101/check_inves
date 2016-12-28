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
						<div class="line"></div> <span class="step_desc_02">基本信息</span> </span> <span class="new_step current fl"><i class="">3</i>
						<div class="line"></div> <span class="step_desc_01">供应商类型</span> </span> <span class="new_step current fl"><i class="">4</i>
						<div class="line"></div> <span class="step_desc_02">品目信息</span> </span> <span class="new_step current fl"><i class="">5</i>
						<div class="line"></div> <span class="step_desc_01">资质文件维护</span> </span> <span class="new_step current fl"><i class="">6</i>
						<div class="line"></div> <span class="step_desc_02">品目合同上传</span> </span> <span class="new_step fl"><i class="">7</i>
						<div class="line"></div> <span class="step_desc_01">初审采购机构</span> </span> <span class="new_step fl"><i class="">8</i>
						<div class="line"></div> <span class="step_desc_02">打印申请表</span> </span> <span class="new_step fl"><i class="">9</i> 
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
		<input name="supplierId" value="${supplierId}" type="hidden" /> 
		<input name="supplierTypeIds"  value="${supplierTypeIds}"    type="hidden" /> 
		<input name="flag" value="" id="flag" type="hidden" /> 
	 
	</form>
	<!-- footer -->
	<c:if test="${currSupplier.status != 7}"><jsp:include page="../../../../../index_bottom.jsp"></jsp:include></c:if>
</body>
</html>
