<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="up" uri="/tld/upload"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>添加物资销售证书信息</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<%@ include file="/WEB-INF/view/front.jsp" %>
<script type="text/javascript">
	
	function saveOrBack() {
/* 		var action = "${pageContext.request.contextPath}/supplier_cert_sell/";
		if (sign) {
			action += "save_or_update_cert_sell.html";
		} else {
			action += "back_to_professional.html";
		}
		$("#cert_sell_form_id").attr("action", action);
		$("#cert_sell_form_id").submit();
		 */
		var id=$("input[name='supplierId']").val();
		 
		 $.ajax({
			 type: "POST",  
             url: "${pageContext.request.contextPath}/supplier_cert_sell/save_or_update_cert_sell.html",  
             data: $("#cert_sell_form_id").serialize(),  
             dataType:"json",
             success:function(result){
            	  var boo=result.bool;
	 	             if(boo==false){
	 	            	 $("#cert_name").text(result.name);
	 	                  $("#cert_level").text(result.lever);
	 	                  $("#cert_auth").text(result.authorith);
	 	                  $("#cert_sdate").text(result.sDate);
	 	                  $("#cert_edate").text(result.eDate);
	 	                  $("#cert_file").text(result.file);
	 	             } else{
	 	          	     
	 	            	  parent.window.location.href = "${pageContext.request.contextPath}/supplier/perfect_professional.html?id="+id;
	 	          	 
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
		<div class="layui-layer-wrap">
		<!-- 	<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10"> -->
						<form id="cert_sell_form_id" method="post" target="_parent">
							<input name="supplierId" value="${supplierId}" type="hidden" />
							<input name="matSellId" value="${matSellId}" type="hidden" />
							<input name="id" value="${uuid}" type="hidden" />
							<div class="drop_window">
								<!-- 详细信息 -->
							<!-- 	<div class="tab-pane fade active in height-300" id="tab-1">
									<div class="margin-bottom-0"> -->
										<ul class="list-unstyled">
										
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
											 <label class="col-md-12 pl20 col-xs-12"><div class="star_red">*</div> 资质证书名称</label>
											 <span class="col-md-12 col-xs-12" >
													<input class="title col-md-12" type="text" name="name" />
													<span class="star_red"  id="cert_name"></span>
											  </span>
											</li>
											
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
											 <label class="col-md-12 pl20 col-xs-12"><div class="star_red">*</div> 资质证书等级</label>
											 <span class="col-md-12 col-xs-12"  >
													<input class="title col-md-12" type="text" name="levelCert" />
													<span class="star_red"  id="cert_level"></span>
											 </span>
											</li>
											
											<li class="col-sm-6 p0 col-md-6 col-lg-6 col-xs-6">
											 <label class="col-md-12 pl20 col-xs-12"><div class="star_red">*</div>供发证机关</label>
												 <span class="col-md-12 col-xs-12" >
													<input class="title col-md-12" type="text" name="licenceAuthorith" />
													 <span class="star_red"  id="cert_auth"></span>
												 </span>
											</li>
											
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
											 <label class="col-md-12 pl20 col-xs-12"><div class="star_red">*</div>有效开始时间 </label>
												 <span class="col-md-12 col-xs-12" >
													<input class="title col-md-12" type="text" name="expStartDate" readonly="readonly" onClick="WdatePicker()" />
												    <span class="star_red"  id="cert_sdate"></span>
												 </span>
											</li>
											
											<li class="col-sm-6  p0 col-md-6 col-lg-6 col-xs-6">
										      <label class="col-md-12 pl20 col-xs-12"><div class="star_red">*</div>有效结束时间 ：</label>
											  <span class="col-md-12 col-xs-12">
													<input class="title col-md-12" type="text" name="expEndDate" readonly="readonly" onClick="WdatePicker()" />
													<!-- <span style="color:red;"  id="cert_edate"></span> -->
												</span>
											</li>
											
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
											<label class="col-md-12 pl20 col-xs-12"><div class="star_red">*</div>是否年检：</label>
										       <span class="col-md-12 col-xs-12">
											        <select name="mot" class="w180 mt5">
											          <option value="1">是</option>
											          <option value="0">无</option>
											        </select>
											      </span>
											</li>
											
											<br> <br>
											<li class="col-md-6 p0"><span class=""><i class="red">*</i> 证书附件：</span>
									 				<up:upload id="cert_up" businessId="${uuid}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierSellCert}" auto="true" />
												   <up:show showId="cert_show" businessId="${uuid}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierSellCert}"/>
											</li>
											<span id="cert_file"></span>
											<div class="clear"></div>
										</ul>
										</div>
							 
							 
							 <div class="tc mt10 col-md-12 col-xs-12">
								<button type="button" class="btn btn-windows save" onclick="saveOrBack()">保存</button>
								<button type="button" class="btn btn-windows back" onclick="cancels()">取消</button>
							</div>
						</form>
	<!-- 			</div>
			</div>
		</div> -->
	</div>
</body>
</html>
