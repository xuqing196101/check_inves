<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="up" uri="/tld/upload"%>
<%@ include file="/WEB-INF/view/front.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>添加工程证书信息</title>

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
		
		 $.ajax({
		       type: "POST",  
	           url: "${pageContext.request.contextPath}/supplier_cert_eng/save_or_update_cert_eng.html",  
	           data: $("#cert_eng_form_id").serialize(),  
	           dataType:"json",
	           success:function(result){
	        	   var boo=result.bool;
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
		          	     parent.location.reload(); 
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
											<li class="col-md-6 p0"><span class="w150"><i class="red">*</i> 资质资格类型：</span>
												<div class="input-append">
													<input class="span3" type="text" name="certType" />
												</div>
												<div class="cue" id="cert_type"></div>
											</li>
											<li class="col-md-6 p0"><span class="w150"><i class="red">*</i> 证书编号：</span>
												<div class="input-append">
													<input class="span3" type="text" name="certCode" />
												</div>
												<div class="cue" id="cert_code"></div>
											</li>
											<li class="col-md-6 p0"><span class="w150"><i class="red">*</i> 资质资格最高等级：</span>
												<div class="input-append">
													<input class="span3" type="text" name="certMaxLevel" />
												</div>
												<div class="cue" id="cert_level"></div>
											</li>
											<li class="col-md-6 p0"><span class="w150"><i class="red">*</i> 技术负责人姓名：</span>
												<div class="input-append">
													<input class="span3" type="text" name="techName" />
												</div>
												<div class="cue" id="cert_name"></div>
											</li>
											<li class="col-md-6 p0"><span class="w150"><i class="red">*</i> 技术负责人职称：</span>
												<div class="input-append">
													<input class="span3" type="text" name="techPt" />
												</div>
												<div class="cue" id="cert_pt"></div>
											</li>
											<li class="col-md-6 p0"><span class="w150"><i class="red">*</i> 技术负责人职务：</span>
												<div class="input-append">
													<input class="span3" type="text" name="techJop" />
												</div>
												<div class="cue" id="cert_job"></div>
											</li>
											<li class="col-md-6 p0"><span class="w150"><i class="red">*</i> 单位负责人姓名：</span>
												<div class="input-append">
													<input class="span3" type="text" name="depName" />
												</div>
												<div class="cue" id="cert_depName"></div>
											</li>
											<li class="col-md-6 p0"><span class="w150"><i class="red">*</i> 单位负责人职称：</span>
												<div class="input-append">
													<input class="span3" type="text" name="depPt" />
												</div>
												<div class="cue" id="cert_depPt"></div>
											</li>
											<li class="col-md-6 p0"><span class="w150"><i class="red">*</i> 单位负责人职务：</span>
												<div class="input-append">
													<input class="span3" type="text" name="depJop" />
												</div>
												<div class="cue" id="cert_depJob"></div>
											</li>
											<li class="col-md-6 p0"><span class="w150"><i class="red">*</i> 发证机关：</span>
												<div class="input-append">
													<input class="span3" type="text" name="licenceAuthorith" />
												</div>
												<div class="cue" id="cert_authorith"></div>
											</li>
											<li class="col-md-6 p0"><span class="w150"><i class="red">*</i> 发证日期 ：</span>
												<div class="input-append">
													<input class="span3" type="text" name="expStartDate" readonly="readonly" onClick="WdatePicker()" />
												</div>
												<div class="cue" id="cert_sdate"></div>
											</li>
											<li class="col-md-6 p0"><span class="w150"><i class="red">*</i> 截止日期 ：</span>
												<div class="input-append">
													<input class="span3" type="text" name="expEndDate" readonly="readonly" onClick="WdatePicker()" />
												</div>
												<div class="cue" id="cert_edate"></div>
											</li>
											<li class="col-md-6 p0"><span class="w150"><i class="red">*</i> 证书状态：</span>
												 <div class="select_common">
													<select name="certStatus">
														<option value="1">是</option>
														<option value="0">否</option>
													</select>
												 </div>
												 
											</li>
											<li class="col-md-6 p0"><span class="w150"><i class="red">*</i> 证书附件：</span>
												<up:upload id="cert_up" businessId="${uuid}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierEngCert}" auto="true" />
												 <up:show showId="cert_show" businessId="${uuid}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierEngCert}"/>
										       	<div class="cue" id="cert_file"></div> 
											</li>
											<div class="clear"></div>
										</ul>
									</div>
								</div>
							</div>
							<div class="mt40 tc mb50">
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
