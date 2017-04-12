<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>

<title>添加产品信息</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
</head>


<%@ include file="/reg_head.jsp"%>
<script type="text/javascript">
		function saveOrBack(sign) {
		 var tr=parent.tr;	
		 var index=parent.layer.getFrameIndex(window.name);
		$.ajax({
		    type: "POST",  
		    url: "${pageContext.request.contextPath}/product_param/save_or_update_param.html",  
		    data: $("#products_form_id").serialize(), 
		    dataType:"json",
		    success:function(result){
		    	var list=result.list;
		/*  	   var boo=result.bool;
		      if(boo==false){
		     	 $("#cert_type").text(result.type);
		           $("#cert_code").text(result.code);
		           $("#cert_seq").text(result.sequence);
		           $("#cert_proType").text(result.proType);
		           $("#cert_level").text(result.level);
		           $("#cert_content").text(result.content);
		           $("#cert_ap_code").text(result.aptituteCode);
		           $("#cert_sdate").text(result.aDate);
		           $("#cert_way").text(result.way);
		           $("#cert_change_date").text(result.changeAt);
		           $("#cert_change_reason").text(result.reason);
		           $("#cert_file").text(result.file);
		         
		     	 
		      } else{ */
		   	  //   parent.location.reload(); 
			       $(list).each(function(i){
			    	  // alert(list[i].paramValue);
			    	  if(list[i].paramName==null){
			    		  $(tr).append("<td align='center'>"+list[i].paramValue+" </td>");
			    	  }else{
			    		  $(tr).append("<td class='tc'><a class='mt3 color7171C6' onClick='javascript:downloadFile(this)'>"+list[i].paramName+"</a><input type='hidden' value="+ list[i].paramValue+"> </td>");
			    	  }
			       });
			  	 parent.layer.close(index);
	 
		 	   
		     },
		     error: function(result){
		         layer.msg("添加失败",{offset: ['150px', '180px']});
		         
		     }
		     
		     
			 });
		
		
		}
	function cancels(){
		var index=parent.layer.getFrameIndex(window.name);
		
		 parent.layer.close(index);
	
	}

	function checknums(obj){
		var vals=$(obj).val();
		var reg= /^\d+\.?\d*$/;  
		if(!reg.exec(vals)){
			$(obj).val("");
			 $("#err_fund").text("数字非法");
		}else{
			$("#err_fund").text();
			$("#err_fund").empty();
		}
	}
	

</script>
<body>
<div class="wrapper">
		<div class="tc">
			请上传附件：
		 	<div class="col-md-12 col-sm-12 col-xs-12 p0 mb25">
			      <u:show showId="business_show"  businessId="${categoryId}" sysKey="${sysKey}" typeId="${typeId}" /> 
				  <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="business_up"  businessId="${categoryId}" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
			 </div>
		 </div>			   

<%-- 		<!--基本信息-->
		<div class="container content height-100">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<form id="products_form_id" action="" method="post">
						
							<div class="tab-content padding-top-20">
								<!-- 详细信息 -->
								<div class="tab-pane fade active in height-100" id="tab-1">
									<div class=" margin-bottom-0">
										<ul class="list-unstyled list-flow">
											<c:forEach items="${list}" var="obj" varStatus="vs">
												<li class="col-md-6 col-sm-12 col-xs-12 mb25"><span class=""><i class="red">*</i> ${obj.paramName}:</span>
													<div  >
														<input name="list[${vs.index}].supplierId" value="${supplierId}" type="hidden" />
														<input name="list[${vs.index}].categoryParamId" value="${obj.id}" type="hidden" />
														<input name="list[${vs.index}].categoryId" value="${categoryId}" type="hidden" />
														<c:if test="${obj.paramTypeId=='CHAR' }">
														  <input  type="text" name="list[${vs.index}].paramValue" value="" />
														</c:if>
														<c:if test="${obj.paramTypeId=='DATE' }">
														  <input  type="text" name="list[${vs.index}].paramValue"  readonly="readonly" onClick="WdatePicker()" />
														</c:if>
														<c:if test="${obj.paramTypeId=='INT' }">
														  <input  type="text" name="list[${vs.index}].paramValue" onkeyup="checknums(this)" value="" />
														</c:if>
														<c:if test="${obj.paramTypeId=='ATTACHMENT' }">
														<!-- <input type="text"  value="1"/> -->
														  <input name="list[${vs.index}].paramValue" value="${attid}" type="hidden" />
					 										  <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="promise_up"   businessId="${attid}" sysKey="${sysKey}" typeId="${attachmentId}" auto="true" /> 
															   <u:show showId="promise_show"    businessId="${attid}" sysKey="${sysKey}" typeId="${attachmentId}" />  
														</c:if>
													</div>
												</li>
											</c:forEach>
											<div class="clear"></div>
										</ul>
									</div>
								</div>
							</div>
							<input name="supId" value="${supplierId}" type="hidden" />
							<input name="cateId" value="${categoryId}" type="hidden" />
							<div class="mt40 tc mb50">
								<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveOrBack(1)">保存</button>
								<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="cancels()">取消</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<form target="_blank" id="download_form_id" action="${pageContext.request.contextPath}/supplier/download.html" method="post">
		<input type="hidden" name="fileName" />
	</form> --%>
</body>
</html>
