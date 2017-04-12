<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/reg_head.jsp"%>

<title>添加供应商工程证书信息</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
 

<script type="text/javascript">
	
	function saveOrBack(sign) {
/* 		var action = "${pageContext.request.contextPath}/supplier_cert_eng/";
		if (sign) {
			action += "save_or_update_cert_eng.html";
		} else {
			action += "back_to_professional.html";
		}
		$("#cert_eng_form_id").attr("action", action);
		$("#cert_eng_form_id").submit(); */
		 var index=parent.layer.getFrameIndex(window.name);
		
		 $.ajax({
		       type: "POST",  
	           url: "${pageContext.request.contextPath}/supplier_cert_eng/save_or_update_cert_eng.html",  
	           data: $("#cert_eng_form_id").serialize(),  
	           dataType:"json",
	           success:function(result){
	        	   var boo=result.bool;
	        	   var obj=result.certEng;
		             if(boo==false){
		            	 $("#cert_type").text(result.certType);
		                  $("#cert_code").text(result.certCode);
		                  $("#cert_level").text(result.cerLevel);
		                  $("#cert_name").text(result.techName);
		                  $("#cert_pt").text(result.techPt);
		                  $("#cert_job").text(result.certJob);
		                  $("#cert_depName").text(result.depName);
		                  $("#cert_depPt").text(result.depPt);
		                  $("#cert_depJob").text(result.depJob);
		                  $("#cert_authorith").text(result.authorith);
		                  $("#cert_sdate").text(result.sDate);
		                  $("#cert_edate").text(result.eDate);
		                  $("#cert_file").text(result.file);
		             } else{
		          	    //  parent.location.reload(); 
		          	    var status=obj.certStatus;
		          	    if(status==1){
		          	    	status='有效';
		          	    }else{
		          	    	status='无效';
		          	    }
				          	   parent.$('#cert_eng_list_tbody_id').append("<tr> <td class='tc'><input type='checkbox' value="+obj.id+"/></td>"+
			                      		"<td class='tc'>"+obj.certType+"</td>"+	
			                      		"<td class='tc'>"+obj.certCode+"</td>"+	
			                      		"<td class='tc'>"+obj.certMaxLevel+"</td>"+	
			                      		"<td class='tc'>"+obj.techName+"</td>"+	
			                      		"<td class='tc'>"+obj.techPt+"</td>"+
			                      		"<td class='tc'>"+obj.techJop+"</td>"+	
			                      		"<td class='tc'>"+obj.depName+"</td>"+	
			                      		"<td class='tc'>"+obj.depPt+"</td>"+	
			                      		"<td class='tc'> "+obj.depJop+"</td>"+	
			                      		"<td class='tc'>"+obj.licenceAuthorith+"</td>"+	
			                      		"<td class='tc'>"+result.sdate+"</td>"+
			                      		"<td class='tc'> "+result.edate+" </td>"+
			                      		"<td class='tc'>"+status+"</td>"
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
							<div class="padding-top-20">
								<!-- 详细信息 -->
										<ul class="list-unstyled">
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>资质资格类型：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
													<input type="text" name="certType" />
													<div class="cue" id="cert_type"></div>
												</div>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>证书编号：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
													<input type="text" name="certCode" />
													<div class="cue" id="cert_code"></div>
												</div>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>资质资格最高等级：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
													<input class="span3" type="text" name="certMaxLevel" />
													<div class="cue" id="cert_level"></div>
												</div>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>技术负责人姓名：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
													<input class="span3" type="text" name="techName" />
													<div class="cue" id="cert_name"></div>
												</div>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>技术负责人职称：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
													<input class="span3" type="text" name="techPt" />
													<div class="cue" id="cert_pt"></div>
												</div>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>技术负责人职务：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
													<input class="span3" type="text" name="techJop" />
													<div class="cue" id="cert_job"></div>
												</div>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>单位负责人姓名：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
													<input class="span3" type="text" name="depName" />
													<div class="cue" id="cert_depName"></div>
												</div>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>单位负责人职称：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
													<input class="span3" type="text" name="depPt" />
													<div class="cue" id="cert_depPt"></div>
												</div>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>单位负责人职务：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
													<input class="span3" type="text" name="depJop" />
													<div class="cue" id="cert_depJob"></div>
												</div>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>发证机关：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
													<input class="span3" type="text" name="licenceAuthorith" />
													<div class="cue" id="cert_authorith"></div>
												</div>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>发证日期 ：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
													<input class="span3" type="text" name="expStartDate" readonly="readonly" onClick="WdatePicker()" />
													<div class="cue" id="cert_sdate"></div>
												</div>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>截止日期 ：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
													<input class="span3" type="text" name="expEndDate" readonly="readonly" onClick="WdatePicker()" />
													<div class="cue" id="cert_edate"></div>
												</div>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>证书状态：</span>
												 <div class="select_common col-md-12 col-xs-12 col-sm-12 p0">
													<select name="certStatus" class="w100p">
														<option value="1">是</option>
														<option value="0">否</option>
													</select>
												 </div>
												 
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>证书附件：</span>
												<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="cert_up"  multiple="true" businessId="${uuid}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierEngCert}" auto="true" />
												 <u:show showId="cert_show" businessId="${uuid}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierEngCert}"/>
										       	<div class="cue" id="cert_file"></div> 
											</li>
											<div class="clear"></div>
										</ul>
									</div>
								</div>
							</div>
							<div class="tc mt10 col-md-12 col-xs-12">
								<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveOrBack(1)">保存</button>
								<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="cancels()">返回</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
