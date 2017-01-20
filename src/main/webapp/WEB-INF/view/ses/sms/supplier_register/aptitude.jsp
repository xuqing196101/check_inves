<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML >
<html>
<head>
<%@ include file="/reg_head.jsp"%>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<title>供应商注册</title>
<script type="text/javascript">
 


 
 	function ajaxFile(){
 		var supplierId=$("#supplierId").va();
 		// var 
 		
 	}
 
	
	function saveItems(){
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
	}
	
	function next(){
		$("input[name='flag']").val("2");
		$("#items_info_form_id").submit();
		/*  var id="${currSupplier.id}";
		 window.location.href="${pageContext.request.contextPath}/supplier/contract.html?supplierId="+id; */
	}
	
	function prev(){
		$("input[name='flag']").val("1");
		$("#items_info_form_id").submit();
	}
	
/*获取内层div的最大高度赋予外层div*/	
function psize(){
  var temp_heights = []
  $(".fades").each(function(){
    temp_heights.push($(this).outerHeight());
  })
  $("#tab_content_div_id").outerHeight(Math.max.apply(null,temp_heights));
}
</script>
 
</script>
 
</head>

<body onload="psize()">
	<div class="wrapper">

		<!-- 项目戳开始 -->
		<c:if test="${currSupplier.status != 7}">
		<div class="container clear margin-top-30">
				<h2 class="padding-20 mt40 ml30">
				       	<span class="new_step current fl"><i class="">1</i>
						<div class="line"></div> <span class="step_desc_02">基本信息</span> </span> <span class="new_step current fl"><i class="">2</i>
						<div class="line"></div> <span class="step_desc_01">供应商类型</span> </span> <span class="new_step current fl"><i class="">3</i>
						<div class="line"></div> <span class="step_desc_02">品目信息</span> </span> <span class="new_step current fl"> <i class="">4</i>
						<div class="line"></div> <span class="step_desc_01">资质文件维护</span> </span> <span class="new_step  fl"><i class="">5</i>
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
		<div class="container content ">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10" >
						<ul id="page_ul_id" class="nav nav-tabs bgdd supplier_tab">
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PRODUCT') and fn:length(cateList) > 0}">
								<li id="li_id_1" class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="f18">物资-生产型品目信息</a></li>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SALES') and fn:length(saleQua) > 0}">
								<li id="li_id_2"   ><a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18">物资-销售型品目信息</a></li>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PROJECT') and fn:length(projectQua) > 0}">
								<li id="li_id_3"   ><a aria-expanded="false" href="#tab-3" data-toggle="tab" class="f18">工程品目信息</a></li>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SERVICE') and fn:length(serviceQua) > 0}">
								<li id="li_id_4"  ><a aria-expanded="false" href="#tab-4" data-toggle="tab" class="f18">服务品目信息</a></li>
							</c:if>
						</ul>
						<div class="tab-content padding-top-20 pr border0" id="tab_content_div_id">
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PRODUCT')}">
								<!-- 物资生产型 -->
							
							<c:set value="0" var="prolength"/> 
								<div class="fades active" id="tab-1">
										  <table class="table table-bordered">
										   <c:forEach items="${cateList }" var="obj" >
						  					 <tr>
						  					  <td class="w200">${obj.categoryName } </td>
						  					    <td>
						  					    
						  					    <c:forEach items="${obj.list }" var="quaPro">
						  					    	<c:set value="${prolength+1}" var="prolength"></c:set>
						  					    	<div class="mr5 fl">
						  					    	<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="pUp${prolength}" multiple="true" buttonName="${quaPro.name}" groups="${saleUp}" businessId="${quaPro.flag}" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
													<div class="clear"></div>
													<u:show showId="pShow${prolength}" groups="${saleShow}" businessId="${quaPro.flag}" sysKey="${sysKey}" typeId="${typeId}" />
						  					        </div>
						  					    </c:forEach>
						  					     </td>
						  					 </tr>
						  					</c:forEach>
						  					
						  					
									</table> 
								</div>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SALES')}">
								<!-- 物资销售型 -->
							<c:set value="0" var="length"> </c:set>
								<div class="tab-pane fades" id="tab-2">
								
										  <table class="table table-bordered">
						  					 <c:forEach items="${saleQua }" var="sale" >
						  					 <tr>
						  					  <td class="w200">${sale.categoryName } </td>
						  					    <td>
						  					    
						  					    <c:forEach items="${sale.list }" var="saua">
						  					    <c:set value="${length+1}" var="length"></c:set>
						  					       <div class="mr5 fl">
						  					    	<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="saleUp${length}" multiple="true" buttonName="${saua.name}"  groups="${saleUp}" businessId="${saua.flag}" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
													<div class="clear"></div>
													<u:show showId="saleShow${length}" groups="${saleShow}"  businessId="${saua.flag}"  sysKey="${sysKey}" typeId="${typeId}" />
						  					       </div>
						  					    </c:forEach>
						  					     </td>
						  					 </tr>
						  					</c:forEach>
										
									</table> 
								  
									 
								</div>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PROJECT')}">
					 
								<div class="fades" id="tab-3">
								  <table class="table table-bordered">
										<c:set value="0" var="plength"> </c:set>	 
									  <c:forEach items="${projectQua }" var="project">
									      <tr>
										    <td class="w200">${project.categoryName } 
										    </td>
											 <td>
						  					    <c:forEach items="${project.list }" var="project">
						  					    <c:set value="${plength+1}" var="plength"></c:set>
						  					       <div class="mr5 fl">
						  					    	<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="projectUp${plength}" multiple="true" buttonName="${project.name}"  groups="${saleUp}" businessId="${project.flag}" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
													<div class="clear"></div>
													<u:show showId="projectShow${plength}" groups="${saleShow}"  businessId="${project.flag}"  sysKey="${sysKey}" typeId="${typeId}" />
						  					       </div>
						  					    </c:forEach>
						  					     </td>
										     </tr>
										</c:forEach>  
									</table> 
								</div>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SERVICE')}">
							 
								<div class="tab-pane fades" id="tab-4">
								   <table class="table table-bordered">
									 <c:set value="0" var="slength"> </c:set>
									 	 
									  <c:forEach items="${serviceQua }" var="server">
									      <tr>
										    <td class="w200">${server.categoryName } 
										    </td>
											 <td>
						  					    <c:forEach items="${server.list }" var="ser">
						  					    <c:set value="${slength+1}" var="slength"></c:set>
						  					     <div class="fl mr5">
						  					    	<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="serverUp${plength}" multiple="true" buttonName="${ser.name}"  groups="${saleUp}" businessId="${ser.flag}" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
													<div class="clear"></div>
													<u:show showId="serverShow${plength}" groups="${saleShow}"  businessId="${ser.flag}"  sysKey="${sysKey}" typeId="${typeId}" />
						  					      </div>
						  					    </c:forEach>
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
		
		
  
 
	</div>
	
	 <div class="btmfix">
	  	  <div style="margin-top: 15px;text-align: center;">
	  	  	   	<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="prev()">上一步</button>
				<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="saveItems()">暂存</button>
				<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="next()">下一步</button>
	  	  </div>
	</div>
	
	
	<form id="items_info_form_id" action="${pageContext.request.contextPath}/supplier/contract.html" method="post">
		<input name="supplierId" id="supplierId" value="${currSupplier.id}" type="hidden" /> 
		<input name="categoryId" value=""  id="categoryId" type="hidden" /> 
		<input name="flag" value=""  id="flag" type="hidden" /> 
		<input name="supplierTypeIds" id="supplierTypeIds" value="${supplierTypeIds }"  type="hidden" /> 
	 
	</form>
</body>
</html>
