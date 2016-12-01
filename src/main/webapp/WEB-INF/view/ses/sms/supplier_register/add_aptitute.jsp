<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="up" uri="/tld/upload"%>
 <%@ include file="/WEB-INF/view/front.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>添加工程资质资格信息</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript">
	
	function saveOrBack(sign) {
		/* var action = "${pageContext.request.contextPath}/supplier_aptitute/";
		if (sign) {
			action += "save_or_update_aptitute.html";
		} else {
			action += "back_to_professional.html";
		}
		$("#cert_eng_form_id").attr("action", action);
		$("#cert_eng_form_id").submit(); */
		 var index=parent.layer.getFrameIndex(window.name);
		 $.ajax({
		       type: "POST",  
	           url: "${pageContext.request.contextPath}/supplier_aptitute/save_or_update_aptitute.html",  
	           data: $("#cert_eng_form_id").serialize(), 
	           dataType:"json",
	           success:function(result){
	        	 var boo=result.bool;
	        	 var obj=result.aptitute;
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
	            	 
	             } else{
	          	    // parent.location.reload(); 
	          	    var status=obj.certStatus;
	          	    if(status==1){
	          	    	status='有效';
	          	    }else{
	          	    	status='无效';
	          	    }
	          	    var master=obj.isMajorFund;
	          	    if(master==1){
	          	    	master='无';
	          	    }else{
	          	    	master='是';
	          	    }
	          	 
	             	   parent.$('#aptitute_list_tbody_id').append("<tr> <td class='tc'><input type='checkbox' value="+obj.id+"/></td>"+
	                      		"<td class='tc'>"+obj.certType+"</td>"+	
	                      		"<td class='tc'>"+obj.certCode+"</td>"+	
	                      		"<td class='tc'>"+obj.aptituteSequence+"</td>"+	
	                      		"<td class='tc'>"+obj.professType+"</td>"+	
	                      		"<td class='tc'>"+obj.aptituteLevel+"</td>"+
	                      		"<td class='tc'>"+master+"</td>"+	
	                      		"<td class='tc'>"+obj.aptituteContent+"</td>"+	
	                      		"<td class='tc'>"+obj.aptituteCode+"</td>"+	
	                      		"<td class='tc'> "+result.adate+"</td>"+	
	                      		"<td class='tc'>"+obj.aptituteWay+"</td>"+	
	                      		"<td class='tc'>"+status+"</td>"+
	                      		"<td class='tc'> "+result.change+" </td>"+
	                      		"<td class='tc'>"+obj.aptituteChangeReason+"</td>"
	                      		 
	                      		
	                      	 );
	             	   
	               	   parent.layer.close(index);  
	             }
	        	   
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
	
</script>

</head>

<body>
<div class="wrapper">

		<!--基本信息-->
		<div class="container content height-350">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<form id="cert_eng_form_id" method="post" target="_parent">
							<input name="supplierId" value="${supplierId}" type="hidden" />
							<input name="matEngId" value="${matEngId}" type="hidden" />
							<input name="id" value="${uuid}" type="hidden" />
							<div class="tab-content padding-top-20">
								<!-- 详细信息 -->
								<div class="tab-pane fade active in height-300" id="tab-1">
									<div class=" margin-bottom-0">
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class="w175"><i class="red">*</i> 资质资格类型：</span>
												<div class="input-append">
													<input class="span5" type="text" name="certType" />
												</div>
												<div class="cue" id="cert_type"></div>
											</li>
											<li class="col-md-6 p0"><span class="w175"><i class="red">*</i> 证书编号：</span>
												<div class="input-append">
													<input class="span5" type="text" name="certCode" />
												</div>
												<div class="cue" id="cert_code"></div>
											</li>
											<li class="col-md-6 p0"><span class="w175"><i class="red">*</i> 资质资格序列：</span>
												<div class="input-append">
													<input class="span5" type="text" name="aptituteSequence" />
												</div>
												<div class="cue" id="cert_seq"></div>
											</li>
											<li class="col-md-6 p0"><span class="w175"><i class="red">*</i> 专业类别：</span>
												<div class="input-append">
													<input class="span5" type="text" name="professType" />
												</div>
												<div class="cue" id="cert_proType"></div>
											</li>
											<li class="col-md-6 p0"><span class="w175"><i class="red">*</i> 资质资格等级：</span>
												<div class="input-append">
													<input class="span5" type="text" name="aptituteLevel" />
												</div>
												<div class="cue" id="cert_level"></div>
											</li>
											<li class="col-md-6 p0"><span class="w175"><i class="red">*</i> 是否主项资质：</span>
												 <div class="select_common">
													<select name="isMajorFund">
														<option value="1">是</option>
														<option value="0">否</option>
													</select>
												 </div>
											</li>
											<li class="col-md-6 p0"><span class="w175"><i class="red">*</i> 批准资质资格内容：</span>
												<div class="input-append">
													<input class="span5" type="text" name="aptituteContent" />
												</div>
												<div class="cue" id="cert_content"></div>
											</li>
											<li class="col-md-6 p0"><span class="w175"><i class="red">*</i> 首次批准资质资格文号：</span>
												<div class="input-append">
													<input class="span5" type="text" name="aptituteCode" />
												</div>
												<div class="cue" id="cert_ap_code"></div>
											</li>
											<li class="col-md-6 p0"><span class="w175"><i class="red">*</i> 首次批准资质资格日期：</span>
												<div class="input-append">
													<input class="span5" type="text" name="aptituteDate" readonly="readonly" onClick="WdatePicker()" />
												</div>
												<div class="cue" id="cert_sdate"></div>
											</li>
											<li class="col-md-6 p0"><span class="w175"><i class="red">*</i> 资质资格取得方式：</span>
												<div class="input-append">
													<input class="span5" type="text" name="aptituteWay" />
												</div>
												<div class="cue" id="cert_way"></div>
											</li>
											<li class="col-md-6 p0"><span class="w175"><i class="red">*</i> 资质资格状态：</span>
											 <div class="select_common">
													<select name="aptituteStatus">
														<option value="1">有效</option>
														<option value="0">无效</option>
													</select>
												</div>  
											</li>
											<li class="col-md-6 p0"><span class="w175"><i class="red">*</i> 资质资格状态变更时间 ：</span>
												<div class="input-append">
													<input class="span5" type="text" name="aptituteChangeAt" readonly="readonly" onClick="WdatePicker()" />
												</div>
												<div class="cue" id="cert_change_date"></div>
											</li>
											<li class="col-md-6 p0"><span class="w175"><i class="red">*</i> 资质资格状态变更原因：</span>
												<div class="input-append">
													<input class="span5" type="text" name="aptituteChangeReason" />
												</div>
												<div class="cue" id="cert_change_reason"></div>
											</li>
											
											<li class="col-md-6 p0"><span class="w175"><i class="red">*</i> 附件上传：</span>
											 	<up:upload id="cert_up" businessId="${uuid}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierEngCertFile}" auto="true" />
												  <up:show showId="cert_show" businessId="${uuid}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierEngCertFile}"/>
											   <div class="cue" id="cert_file"></div>
											</li>
											
											<div class="clear"></div>
										</ul>
									</div>
								</div>
							</div>
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
</body>
</html>
