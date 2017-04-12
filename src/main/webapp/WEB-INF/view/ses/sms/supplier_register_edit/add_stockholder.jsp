<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>

<%@ include file="/reg_head.jsp"%>
<title>添加股东信息</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<script type="text/javascript">
	
	function saveOrBack(sign) {
/* 		var action = "${pageContext.request.contextPath}/supplier_stockholder/";
		if (sign) {
			action += "save_or_update_stockholder.html";
		} else {
			action += "back_to_basic_info.html";
		}
		$("#stockholder_form_id").attr("action", action);
		$("#stockholder_form_id").submit(); */
		 var index=parent.layer.getFrameIndex(window.name);
		 $.ajax({
		    type: "POST",  
             url: "${pageContext.request.contextPath}/supplier_stockholder/save_or_update_stockholder.html",  
             data: $("#stockholder_form_id").serialize(),  
             dataType:"json",
             success:function(result){
               var boo=result.bool;
         
            if(result.bool==false){
            	 $("#stock_name").text(result.name);
            	 $("#stock_share").text(result.share);
            	  $("#stock_code1").text(result.idCaerd);
                  $("#stock_code2").text(result.idCaerd); 
                 $("#stock_propor").text(result.portion);
            }
            
               var obj=result.stock;
               var pro=obj.nature;
               if(pro=='1'){
            	   pro="法人";
               }
               if(pro=='2'){
            	   pro="自然人"; 
               }
               if(boo==false){
            	   alert("hehe");
            	   
            	/*    $("#fiance_name").text(result.nature);
	                  $("#finace_phone").text(result.phone);
	                  $("#finace_auditor").text(result.auditors);
	                  $("#finace_gains").text(result.assets);
	                  $("#finace_debt").text(result.bilit);
	                  $("#stock_nature").text(result.noAssets); */
	                  $("#stock_name").text(result.name);
	              /*     $("#cert_sdate").text(result.aDate); */
	            /*       $("#stock_code1").text(result.err_taxCert);
	                  $("#stock_code2").text(result.err_bil); */
	                  $("#stock_share").text(result.share);
	                  $("#stock_propor").text(result.portion);
            	   
               } else{
            	   parent.$('#stockholder_list_tbody_id').append("<tr> <td class='tc'><input type='checkbox' value="+obj.id+"/></td>"+
            			"<td class='tc'>"+pro+"</td>"+	
                   		"<td class='tc'>"+obj.name+"</td>"+	
                   		"<td class='tc'>"+obj.identity+"</td>"+	
                   		"<td class='tc'>"+obj.shares+"</td>"+	
                   		"<td class='tc'>"+obj.proportion+"</td>");
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
	
	function person(obj){
		var val=$(obj).val();
		if(val=='1'){
			$("#society_li").show();
			$("#nature_li").hide();
			/* $('#society').attr('disabled',"true");
			$('#nature').removeAttr('disabled'); */
			$('#society').attr('name','identity');
			$('#nature').attr('name','iden');
		}else{
			$("#nature_li").show();
			$("#society_li").hide();
			/* $('#society').attr('disabled',"true");
			$('#society').removeAttr('disabled'); */
			$('#society').attr('name','iden');
			$('#nature').attr('name','identity');
		}
	}
</script>

</head>

<body>
<div class="wrapper">

		<!--基本信息-->
		<div class="container content height-300">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<form id="stockholder_form_id" method="post" target="_parent">
							<input name="supplierId" value="${supplierId}" type="hidden" />
							<input name="id" value="${uuid}" type="hidden" />
							<div class="tab-content padding-top-20">
								<!-- 详细信息 -->
								<div class="tab-pane fade active in height-200" id="tab-1">
									<div class=" margin-bottom-0">
										<ul class="list-unstyled">
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6 pl15"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>出资人性质：</span>
												<!-- 	<input class="span3" type="text" name="nature" /> -->
													   
													   <div class="col-md-12 col-sm-12 col-xs-12 select_common p0">
													    <select onchange="person(this)" name="nature">
 														 <option value="1">法人</option>
 														 <option value="2">自然人</option>
 													    </select>
 													   </div>
 													<!--  <span class="add-on cur_point">i</span> -->
 													<div class="cue" id="stock_nature"></div>
											</li>
											
											
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>出资人姓名：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
													<input type="text" name="name" />
													<div class="cue" id="stock_name"></div>
												</div>
											</li>
										
											<li  id="society_li"  class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>统一社会信用代码：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
													<input type="text" id="society" name="identity" />
													<div class="cue" id="stock_code1"></div>
												</div>
											</li>
											
											<li style="display:none" id="nature_li"  class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>身份证号码：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
													<input type="text"  id="nature" name="ident" />
													<div class="cue" id="stock_code2"></div>
												</div>
											</li>
											
											
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>出资金额或股份（万元/万份）：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
													<input onkeyup="checknums(this)" type="text" name="shares" />
													<div class="cue" id="stock_share"></div>
												</div>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>比例（%）：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
													<input type="text" name="proportion" />
													<div class="cue" id="stock_propor"></div>
												</div>
											</li>
											<div class="clear"></div>
										</ul>
									</div>
								</div>
							</div>
							<div class="tc mt10 col-md-12 col-xs-12">
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
