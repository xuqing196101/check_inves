<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>

<%@ include file="/reg_head.jsp"%>
<title>注册人员信息</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<script type="text/javascript">
	
	function saveOrBack(sign) {
		/* var action = "${pageContext.request.contextPath}/supplier_reg_person/";
		if (sign) {
			action += "save_or_update_reg_person.html";
		} else {
			action += "back_to_professional.html";
		}
		$("#cert_eng_form_id").attr("action", action);
		$("#cert_eng_form_id").submit(); */
		 var index=parent.layer.getFrameIndex(window.name);
		 $.ajax({
	       type: "POST",  
           url: "${pageContext.request.contextPath}/supplier_reg_person/save_or_update_reg_person.html",  
           data: $("#cert_eng_form_id").serialize(),  
           dataType:"json",
           success:function(result){
        	   var boo=result.bool;
        	   var obj=result.person;
	             if(boo==false){
	            	 $("#cert_type").text(result.type);
	                  $("#cert_count").text(result.regNum);
	             } else{
	          	    // parent.location.reload(); 
	          	 
	            	 parent.$('#reg_person_list_tbody_id').append("<tr> <td class='tc'><input type='checkbox' value="+obj.id+"/></td>"+
	                      		"<td class='tc'>"+obj.regType+"</td>"+	
	                      		"<td class='tc'>"+obj.regNumber+"</td>"
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

</head>

<body>
<div class="wrapper">

		<!--基本信息-->
		<div class="container content height-200">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<form id="cert_eng_form_id" method="post" target="_parent">
							<input name="supplierId" value="${supplierId}" type="hidden" />
							<input name="matEngId" value="${matEngId}" type="hidden" />
							<input name="id" value="${id}" type="hidden" />
							<div class=" padding-top-20">
								<!-- 详细信息 -->
								<div class="tab-pane fade active in height-100" id="tab-1">
									<div class=" margin-bottom-0">
										<ul class="list-unstyled">
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>注册类型：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
													<input type="text" name="regType" />
													<div class="cue" id="cert_type"></div>
												</div>
										    
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>注册人数：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
													<input type="text" onkeyup="checknums(this)" name="regNumber" />
													<div class="cue" id="cert_count"></div>
												</div>
											 
											</li>
											<div class="clear"></div>
										</ul>
									</div>
								</div>
							</div>
							<div class="mt20 tc mb50">
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
