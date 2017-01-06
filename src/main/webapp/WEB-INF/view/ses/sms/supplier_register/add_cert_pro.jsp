<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML >
<html>
<head>

<title>添加物资生产证书信息</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="application"/> 
<script>var globalPath = "${contextPath}";</script>
<script type="text/javascript">
	
	function saveOrBack(sign) {
/* 		var action = "${pageContext.request.contextPath}/supplier_cert_pro/";
		if (sign) {
			action += "save_or_update_cert_pro.html";
		} else {
			action += "back_to_professional.html";
		}
		$("#cert_pro_form_id").attr("action", action);
		$("#cert_pro_form_id").submit(); */
		 var index=parent.layer.getFrameIndex(window.name);
		 $.ajax({
			 type: "POST",  
             url: "${pageContext.request.contextPath}/supplier_cert_pro/save_or_update_cert_pro.html",  
             data: $("#cert_pro_form_id").serialize(),
             dataType:"json",
             success:function(result){
            	 var boo=result.bool;
            	 var obj=result.certPro;
	             if(boo==false){
	            	  $("#cert_name").text(result.name);
	                  $("#cert_level").text(result.level);
	                  $("#cert_authorith").text(result.authorith);
	                  $("#cert_sdate").text(result.sDate);
	                  $("#cert_edate").text(result.eDate);
	                  $("#cert_file").text(result.file);
	             } else{
	            	  var mot=obj.mot;
		          	    if(mot==1){
		          	    	mot='是';
		          	    }else{
		          	    	mot='否';
		          	    }
	          	    //  parent.location.reload(); 
	            	  parent.$('#cert_pro_list_tbody_id').append("<tr> <td class='tc'><input type='checkbox' value="+obj.id+"/></td>"+
	                      		"<td class='tc'>"+obj.name+"</td>"+	
	                      		"<td class='tc'>"+obj.levelCert+"</td>"+	
	                      		"<td class='tc'>"+obj.licenceAuthorith+"</td>"+	
	                      		"<td class='tc'>"+result.sdate+"</td>"+
	                      		"<td class='tc'>"+result.edate+"</td>"+	
	                      		"<td class='tc'>"+mot+"</td>"
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
						<form id="cert_pro_form_id" method="post" target="_parent">
							<input name="id" value="${uuid}" type="hidden" />
							<input name="supplierId" value="${supplierId}" type="hidden" />
							<input name="matProId" value="${supplierCertPro.matProId}" type="hidden" />
							<input name="sign" value="${sign}" type="hidden" />
							<div class="padding-top-20">
								<!-- 详细信息 -->
								<div class="tab-pane fade active in height-300" id="tab-1">
									<div class=" margin-bottom-0">
										<ul class="list-unstyled">
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6 pl15"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>资质证书名称：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
													<input type="text" name="name" />
													<div class="cue" id="cert_name"></div>
												</div>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>资质等级：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
													<input type="text" name="levelCert" />
													<div class="cue" id="cert_level"></div>
												</div>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>供发证机关：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
													<input type="text" name="licenceAuthorith" />
													<div class="cue" id="cert_authorith"></div>
												</div>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>有效开始时间 ：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
													<input type="text" name="expStartDate" readonly="readonly" onClick="WdatePicker()" />
													<div class="cue" id="cert_sdate"></div>
												</div>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>有效结束时间 ：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
													<input type="text" name="expEndDate" readonly="readonly" onClick="WdatePicker()" />
													<div class="cue" id="cert_edate"></div>
												</div>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
											   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>是否年检：</span>
												 <div class="select_common col-md-12 col-xs-12 col-sm-12 p0">
											        <select name="mot">
											          <option value="1">是</option>
											          <option value="0">无</option>
											        </select>
											      </div>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>证书附件：</span>
											    <div class="col-md-12 col-sm-12 col-xs-12 input_group p0">
												  <u:upload  multiple="true" id="cert_up" businessId="${uuid}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierProCert}" auto="true" />
												  <u:show showId="cert_show" businessId="${uuid}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierProCert}"/>
											      <div class="cue" id="cert_file"></div>
											    </div>
											</li>
											<div class="clear"></div>
										</ul>
									</div>
								</div>
							</div>
							<div class="tc mt10 col-md-12 col-xs-12 col-sm-12">
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
