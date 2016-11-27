<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>添加产品信息</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<%@ include file="/WEB-INF/view/front.jsp" %>
</head>


<script type="text/javascript">
		function saveOrBack(sign) {
		$.ajax({
		    type: "POST",  
		    url: "${pageContext.request.contextPath}/product_param/save_or_update_param.html",  
		    data: $("#products_form_id").serialize(), 
		    success:function(result){
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
		   	     parent.location.reload(); 
		       
		   	 
		  /*     } */
		 	   
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
<body>
<div class="wrapper">

		<!--基本信息-->
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
												<li class="col-md-6 p0"><span class=""><i class="red">*</i> ${obj.paramName}</span>
													<div class="input-append">
														<input name="list[${vs.index}].supplierId" value="${supplierId}" type="hidden" />
														<input name="list[${vs.index}].categoryParamId" value="${obj.id}" type="hidden" />
														<input name="list[${vs.index}].categoryId" value="${categoryId}" type="hidden" />
														<input  type="text" name="list[${vs.index}].paramValue" value="" />
													</div>
												</li>
											</c:forEach>
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
	<form target="_blank" id="download_form_id" action="${pageContext.request.contextPath}/supplier/download.html" method="post">
		<input type="hidden" name="fileName" />
	</form>
</body>
</html>
