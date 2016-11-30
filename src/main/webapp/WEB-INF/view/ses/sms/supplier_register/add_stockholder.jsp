<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>添加股东信息</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<%@ include file="/WEB-INF/view/front.jsp" %>

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
		var id=$("input[name='supplierId']").val();
		 $.ajax({
			   type: "POST",  
             url: "${pageContext.request.contextPath}/supplier_stockholder/save_or_update_stockholder.html",  
             data: $("#stockholder_form_id").serialize(),  
             success:function(result){
               if(result=='0'){
              	alert(result);
              	 
               } else{
              	 
              parent.location.reload();
              	  ///parent.window.location.href = "${pageContext.request.contextPath}/supplier/perfect_basic.html?id="+id;
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
		<div class="container content height-300">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<form id="stockholder_form_id" method="post" target="_parent">
							<input name="supplierId" value="${supplierId}" type="hidden" />
							<div class="tab-content padding-top-20">
								<!-- 详细信息 -->
								<div class="tab-pane fade active in height-200" id="tab-1">
									<div class=" margin-bottom-0">
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class="w220"><i class="red">＊</i> 出资人名称或姓名：</span>
												<div class="input-append">
													<input class="span3" type="text" name="name" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class="w220"><i class="red">＊</i> 出资人性质：</span>
												<div class="input-append">
													<input class="span3" type="text" name="nature" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class="w220"><i class="red">＊</i> 统一社会信用代码或身份证号码：</span>
												<div class="input-append">
													<input class="span3" type="text" name="identity" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class="w220"><i class="red">＊</i> 出资金额或股份（万元/万份）：</span>
												<div class="input-append">
													<input class="span3" type="text" name="shares" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class="w220"><i class="red">＊</i> 比例：</span>
												<div class="input-append">
													<input class="span3" type="text" name="proportion" />
												</div>
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
