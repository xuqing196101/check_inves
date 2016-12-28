<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML >
<html>
<head>
<%@ include file="/WEB-INF/view/front.jsp" %>
<script type="text/javascript">
 
 
$(function() {
	  window.onload=function(){
		  var checkeds = $("#supplierTypeIds").val();
		  var arrays =checkeds.split(",");
		   if(arrays.length>0){
		       for(var i=0;i<arrays.length;i++){
			    				if(arrays[i]!='PROJECT'){
				                     $("#tab-1").attr("class", "tab-pane fade ");
				                  }
			                      if(arrays[i]!='PRODUCT'){
			                    	  $("#tab-2").attr("class", "tab-pane fade ");
			                      }
			                      if(arrays[i]!='SALES'){
				                      $("#stab-3").attr("class", "tab-pane fade ");
				                  }
			                      if(arrays[i]!='SERVICE'){
			                          $("#tab-4").attr("class", "tab-pane fade ");
			                      }
			                      if(arrays[i]=='PRODUCT'){
			                    		$("#tab-1").attr("class", "tab-pane fade  active in");
			                    		var pro="${list3}";
			                    		var data=$.pareJSON(pro);
			                    		var l=1;
			                    		var trhtml = "";
			                    		
			                    		for ( var i = 0; i < data.length; i++) {
			    				 			trhtml += "<tr><td>"+data[i].name+"<td> </tr>";
			    				 		/* 	var s=data[i].list;
			    				 			for ( var j = 0; j < s.length; j++) {
			    				 			trhtml += "<td><div class='col-md-12 col-sm-12 col-xs-12 p0' id='breach_li_id'>"+
												"<u:upload id=pUp"+l +" groups="+${sbUp}+" businessId="+${s[j].id}+" sysKey="1" typeId='1' auto='true'/>" +
												"<u:show showId=pShow"+l +" groups="+${sbShow}+" businessId="+${s[j].id}+" sysKey='1' typeId='1 />"+
										 		  "</div> "+
										     l++;
			    				 			}
										   "<td> </tr>"; */
			    						}
			                    		$("#pro_tr").append(trhtml);
			                    		
			                    		
			                      }
			                      else if(arrays[i]=='SALES'){
			                    	$("#tab-2").attr("class", "tab-pane fade  active in");
			                      }
			                      else if(arrays[i]=='PROJECT'){
			                    	  $("#tab-3").attr("class", "tab-pane fade  active in");
			                      }
			                        else  if(arrays[i]=='SERVICE'){
			                    	  $("#tab-3").attr("class", "tab-pane fade  active in");
			                      }
					 }
		   		}
	  }
		       
});


 
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
						<div class="line"></div> <span class="step_desc_02">基本信息</span> </span> <span class="new_step current fl"><i class="">3</i>
						<div class="line"></div> <span class="step_desc_01">供应商类型</span> </span> <span class="new_step current fl"><i class="">4</i>
						<div class="line"></div> <span class="step_desc_02">品目信息</span> </span> <span class="new_step current fl"> <i class="">5</i>
						<div class="line"></div> <span class="step_desc_01">资质文件维护</span> </span> <span class="new_step  fl"><i class=""> 6</i>
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
						<ul id="page_ul_id" class="nav nav-tabs bgdd supplier_tab">
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PRODUCT')}">
								<li id="li_id_1" class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="f18">物资-生产型品目信息</a></li>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SALES')}">
								<li id="li_id_2"   ><a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18">物资-销售型品目信息</a></li>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PROJECT')}">
								<li id="li_id_3"   ><a aria-expanded="false" href="#tab-3" data-toggle="tab" class="f18">工程品目信息</a></li>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SERVICE')}">
								<li id="li_id_4"  ><a aria-expanded="false" href="#tab-4" data-toggle="tab" class="f18">服务品目信息</a></li>
							</c:if>
						</ul>
						<div class="tab-content padding-top-20" id="tab_content_div_id">
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PRODUCT')}">
								<!-- 物资生产型 -->
								<div class="tab-pane fade active in height-300" id="tab-1">
										  <table class="table table-bordered">
										  <tbody id="pro_tr">
						  					
						  					</tbody>
										<%--   <c:forEach items="${cateList }" var="obj">
									      <tr>
										    <td class="info">${obj.categoryName } 
								 
										    </td>
												    <td class="info">
												     <c:forEach items="${obj.list }" var="qua" varStatus="vs">
													  <c:set value="${group+1}" var="group"/>
													   
													  	  ${qua.name }
													    <div class="col-md-12 col-sm-12 col-xs-12 p0" id="breach_li_id">
																<u:upload id="pUp${group}" groups="${sbUp}" businessId="${qua.id}" sysKey="1" typeId="1" auto="true" />
																<u:show showId="pShow${group}" groups="${sbShow}" businessId="${qua.id}" sysKey="1" typeId="1" />
														   </div>  
													    </c:forEach>
												    </td>
										   
										  </tr>
										</c:forEach> --%>
									</table> 
								</div>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SALES')}">
								<!-- 物资销售型 -->
								<div class="tab-pane fade height-300" id="tab-2">
								
										  <table class="table table-bordered">
						  					<tbody >
						  					
						  					</tbody>
										  
									<%-- 	  <c:forEach items="${saleQua }" var="sale">
									      <tr>
										    <td class="info">${sale.categoryName } 
										    </td>
												    <td class="info">
												     <c:forEach items="${sale.list }" var="qua" varStatus="vs">
													  <c:set value="${group+1}" var="group"/>
													   
													  	  ${qua.name }
													    <div class="col-md-12 col-sm-12 col-xs-12 p0" id="breach_li_id">
																<u:upload id="pUp${group}" groups="${sbUp}" businessId="${qua.id}" sysKey="1" typeId="1" auto="true" />
																<u:show showId="pShow${group}" groups="${sbShow}" businessId="${qua.id}" sysKey="1" typeId="1" />
														   </div>  
													    </c:forEach>
												    </td>
										    </tr>
										</c:forEach> --%>
										
									</table> 
								  
									 
								</div>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PROJECT')}">
							<!-- 服务 -->
								<div class="tab-pane fade height-200" id="tab-3">
								  <table class="table table-bordered">
										 
										  <c:forEach items="${projectQua }" var="project">
									      <tr>
										    <td class="info">${project.categoryName } 
										    </td>
												    <td class="info">
												     <c:forEach items="${project.list }" var="qua" varStatus="vs">
													<%--   <c:set value="${group+1}" var="group"/> --%>
													   
													  	  ${qua.name }
												<%-- 	    <div class="col-md-12 col-sm-12 col-xs-12 p0" id="breach_li_id">
																<u:upload id="pUp${group}" groups="${sbUp}" businessId="${qua.id}" sysKey="1" typeId="1" auto="true" />
																<u:show showId="pShow${group}" groups="${sbShow}" businessId="${qua.id}" sysKey="1" typeId="1" />
														   </div>   --%>
													    </c:forEach>
												    </td>
										     </tr>
										</c:forEach>
									</table> 
								</div>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SERVICE')}">
								<!-- 生产 -->
								<div class="tab-pane fade height-200" id="tab-4">
								   <table class="table table-bordered">
										 
										  <c:forEach items="${serviceQua }" var="server">
									      <tr>
										    <td class="info">${server.categoryName } 
								 
										     </td>
												    <td class="info">
												     <c:forEach items="${server.list }" var="qua" varStatus="vs">
													<%--   <c:set value="${group+1}" var="group"/> --%>
													  	  ${qua.name }
													  <%--   <div class="col-md-12 col-sm-12 col-xs-12 p0" id="breach_li_id">
																<u:upload id="pUp${group}" groups="${sbUp}" businessId="${qua.id}" sysKey="1" typeId="1" auto="true" />
																<u:show showId="pShow${group}" groups="${sbShow}" businessId="${qua.id}" sysKey="1" typeId="1" />
														   </div>  --%> 
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
		
		
  
<%--    <c:set value="0" var="group"/>
		<!--基本信息-->
		<div class="container content height-300">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10" >
						 
				
								
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
	
	
	<form id="items_info_form_id" action="${pageContext.request.contextPath}/supplier/contract.html" method="post">
		<input name="supplierId" id="supplierId" value="${currSupplier.id}" type="hidden" /> 
		<input name="categoryId" value=""  id="categoryId" type="hidden" /> 
		<input name="flag" value=""  id="flag" type="hidden" /> 
		<input name="supplierTypeIds" id="supplierTypeIds" value="${supplierTypeIds }"  type="hidden" /> 
	 
	</form>
	<!-- footer -->
	<c:if test="${currSupplier.status != 7}"><jsp:include page="../../../../../index_bottom.jsp"></jsp:include></c:if>
</body>
</html>
