<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>

	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
<link href="${pageContext.request.contextPath}/public/ZHH/css/import_supplier.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/common.css" media="screen" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/style.css" type="text/css"/>
<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/style.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/animate.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/ui-dialog.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/dialog-select.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/font-awesome.min.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/jquery.fileupload-ui.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/zTreeStyle.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/sky-forms.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/custom-sky-forms.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/jquery.fancybox.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/owl.carousel.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/owl.theme.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/style-switcher.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/shortcode_timeline2.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/app.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/blocks.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/datepicker.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/WdatePicker.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/select2.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/application.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/brand-buttons-inversed.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/blog_magazine.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/page_log_reg_v1.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/footer-v4.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/masterslider.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/james.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/layer/skin/layer.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/layer/skin/layer.ext.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/WdatePicker(1).css" rel="stylesheet" type="text/css">
<script src="<%=basePath%>public/ZHH/js/hm.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery-migrate-1.2.1.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
<script src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/back-to-top.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.query.js"></script>
<script src="<%=basePath%>public/ZHH/js/dialog-plus-min.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.fancybox.pack.js"></script>
<script src="<%=basePath%>public/ZHH/js/smoothScroll.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.parallax.js"></script>
<script src="<%=basePath%>public/ZHH/js/app.js"></script>
<script src="<%=basePath%>public/ZHH/js/common.js"></script>
<script src="<%=basePath%>public/ZHH/js/dota.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.dragsort-0.5.2.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/fancy-box.js"></script>
<script src="<%=basePath%>public/ZHH/js/style-switcher.js"></script>
<script src="<%=basePath%>public/ZHH/js/owl.carousel.js"></script>
<script src="<%=basePath%>public/ZHH/js/owl-carousel.js"></script>
<script src="<%=basePath%>public/ZHH/js/owl-recent-works.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/WdatePicker.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.form.min.js"></script>
<script src="<%=basePath%>public/layer/layer.js"></script>
<script src="<%=basePath%>public/layer/extend/layer.ext.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.validate.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.maskedinput.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery-ui.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/masking.js"></script>
<script src="<%=basePath%>public/ZHH/js/datepicker.js"></script>
<script src="<%=basePath%>public/ZHH/js/timepicker.js"></script>
<script src="<%=basePath%>public/ZHH/js/dialog-select.js"></script>
<script src="<%=basePath%>public/ZHH/js/locale.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.ui.widget.js"></script>
<script src="<%=basePath%>public/ZHH/js/load-image.js"></script>
<script src="<%=basePath%>public/ZHH/js/canvas-to-blob.js"></script>
<script src="<%=basePath%>public/ZHH/js/tmpl.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.iframe-transport.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.fileupload.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.fileupload-fp.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.fileupload-ui.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery-fileupload.js"></script>
<script src="<%=basePath%>public/ZHH/js/form.js"></script>
<script src="<%=basePath%>public/ZHH/js/select2.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/select2_locale_zh-CN.js"></script>
<script src="<%=basePath%>public/ZHH/js/application.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.counterup.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/modernizr.js"></script>
<script src="<%=basePath%>public/ZHH/js/touch.js"></script>
<script src="<%=basePath%>public/ZHH/js/product-quantity.js"></script>
<script src="<%=basePath%>public/ZHH/js/master-slider.js"></script>
<script src="<%=basePath%>public/ZHH/js/shop.app.js"></script>
<script src="<%=basePath%>public/ZHH/js/masterslider.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.easing.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/james.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/expert/TestAddress1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/expert/TestChooseAddress.js"></script>
<SCRIPT LANGUAGE="JavaScript">
function loadProvince(regionId){
  $("#id_provSelect").html("");
  $("#id_provSelect").append("<option value=''>请选择省份</option>");
  var jsonStr = getAddress(regionId,0);
  for(var k in jsonStr) {
	$("#id_provSelect").append("<option value='"+k+"'>"+jsonStr[k]+"</option>");
  }
  if(regionId.length!=6) {
	$("#address").html("");
    $("#address").append("<option value=''>请选择城市</option>");
  } else {
	 $("#id_provSelect").val(regionId.substring(0,2)+"0000");
	 loadCity(regionId);
  }
}

function loadCity(regionId){
  $("#address").html("");
  $("#address").append("<option value=''>请选择城市</option>");
  if(regionId.length==6) {
	var jsonStr = getAddress(regionId,1);
    for(var k in jsonStr) {
	  $("#address").append("<option value='"+k+"'>"+jsonStr[k]+"</option>");
    }
	var str = regionId.substring(0,2);//四个直辖市
	if(str=="11" || str=="12" || str=="31" || str=="50") {
	   $("#address").val(regionId);
	} else {
	   $("#address").val(regionId.substring(0,4)+"00");
	}
  }
}
</SCRIPT>
<script type="text/javascript">
function reason(id){
	var value=document.getElementsByName(id)[0];
	if(value){
		return;
	}
	var id1=id+"1";
	var id2=id+"2";
	var supplierName=$("#"+id2+"").text().replaceAll("＊","");
    layer.confirm('确认审核不通过？', {
  		btn: ['不通过','通过'] //按钮
	}, function(){
  		$("#"+id1+"").hide();
  		layer.prompt({title: '请填写不通过理由', formType: 2}, function(text){
  		    var ul=document.getElementById("reason");             
        	var obj=document.createElement("li"); 
        	obj.className="col-md-6 p0";
       	 	obj.innerHTML="<span>"+supplierName+"</span><div class='input-append'><input class='span3 red' id='supplierType' name="+id+" value="+text+"  type='text'></div>";
        	ul.appendChild(obj); 
    		layer.msg("审核不通过的理由是："+text);
 		});
	});
}

function tijiao(status){
	$("#status").val(status);
	form1.submit();
}
</script>
</head>
<body>
		<!-- 项目戳开始 -->
		<div class="container clear margin-top-30">
		<!--详情开始-->
		<div class="container content height-350">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<ul class="nav nav-tabs bgdd">
							<li class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="s_news f18">基本信息</a></li>
							<li class="">	   <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="fujian f18">申请表</a></li>
							<li class="">	   <a aria-expanded="false" href="#tab-3" data-toggle="tab" class="fujian f18">审核问题汇总</a></li>
						</ul>
							<div class="tab-content padding-top-20 h900">
								<div class="tab-pane fade active in height-450" id="tab-1">
									<div class=" margin-bottom-0">
										<h2 class="f16 jbxx">
										<i>01</i>企业基本信息
										</h2>
									<ul class="list-unstyled list-flow">
										<li class="col-md-6 p0 "><span id="nameReason2" class=""><i class="red">＊</i> 企业名称：</span>
											<div class="input-append">
												<input class="span3" id="supplierName" name="supplierName" value="${is.name }" type="text" readonly="readonly">
												<div id="nameReason1" class="b f18 fl ml10 red hand">√</div>
												<div id="nameReason" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span id="supplierTypeReason2" ><i class="red">＊</i> 企业类别：</span>
											<div class="input-append">
												<input class="span3" id="supplierType" name="supplierType" value="${is.supplierType }"  type="text" readonly="readonly">
												<div id="supplierTypeReason1" class="b f18 fl ml10 red hand">√</div>
												<div id="supplierTypeReason" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span id="chinesrNameReason2"><i class="red">＊</i> 中文译名：</span>
											<div class="input-append">
												<input class="span3" id="chinesrName" name="chinesrName" value="${is.chinesrName }" type="text" readonly="readonly">
												<div id="chinesrNameReason1" class="b f18 fl ml10 red hand">√</div>
												<div id="chinesrNameReason" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span id="legalNameReason2"><i class="red">＊</i> 法定代表人：</span>
											<div class="input-append">
												<input class="span3" id="legalName" name="legalName" value="${is.legalName }" type="text" readonly="readonly">
												<div id="legalNameReason1" class="b f18 fl ml10 red hand">√</div>
												<div id="legalNameReason" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
											</div>
										</li>
										<li class="col-md-6 p0"><span id="addressReason2"><i class="red">＊</i>企业注册地址：</span>
											<div>
												<select id="id_provSelect" name="provSelect" class="fl mr30" disabled="disabled"><option value="">请选择省份</option></select>
  												<select id="address" class="fl" name="address" disabled="disabled"><option value="" >请选择城市</option></select>
  												<SCRIPT LANGUAGE="JavaScript">loadProvince('${is.address}');</SCRIPT>
												<div id="addressReason1" class="b f18 fl ml10 red hand">√</div>
												<div id="addressReason" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span id="postCodeReason2" class=""><i class="red">＊</i>邮政编码：</span>
											<div class="input-append">
												<input class="span3" id="postCode" name="postCode" value="${is.postCode }"  type="text" readonly="readonly">
												<div id="postCodeReason1" class="b f18 fl ml10 red hand">√</div>
												<div id="postCodeReason" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span id="productTypeReason2"><i class="red">＊</i>经营产品大类：</span>
											<div class="input-append">
												<input class="span3" id="productType" name="productType" value="${is.productType }" type="text" readonly="readonly">
												<div id="productTypeReason1" class="b f18 fl ml10 red hand">√</div>
												<div id="productTypeReason" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span id="majorpRoductReason2" class=""><i class="red">＊</i>主营产品：</span>
											<div class="input-append">
												<input class="span3" id="majorProduct" name="majorProduct" value="${is.majorProduct }" type="text" readonly="readonly">
												<div id="majorpRoductReason1" class="b f18 fl ml10 red hand">√</div>
												<div id="majorpRoductReason" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span id="byproductReason2"><i class="red">＊</i>兼营产品：</span>
											<div class="input-append">
												<input class="span3" id="byproduct" name="byproduct" value="${is.byproduct }" type="text" readonly="readonly">
												<div id="byproductReason1" class="b f18 fl ml10 red hand">√</div>
												<div id="byproductReason" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span id="producerNameReason2"><i class="red">＊</i>生产商名称：</span>
											<div class="input-append">
												<input class="span3" id="producerName" name="producerName" value="${is.producerName }"  type="text" readonly="readonly">
												<div id="producerNameReason1" class="b f18 fl ml10 red hand">√</div>
												<div id="producerNameReason" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span id="contactPersonReason2"><i class="red">＊</i> 联系人：</span>
											<div class="input-append">
												<input class="span3" id="contactPerson" name="contactPerson" value="${is.contactPerson }" type="text" readonly="readonly">
												<div id="contactPersonReason1" class="b f18 fl ml10 red hand">√</div>
												<div id="contactPersonReason" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span id="telephoneReason2"><i class="red">＊</i> 电话：</span>
											<div class="input-append">
												<input class="span3" id="telephone" name="telephone" value="${is.telephone }" type="text" readonly="readonly">
												<div id="telephoneReason1" class="b f18 fl ml10 red hand">√</div>
												<div id="telephoneReason" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span id="faxReason2"><i class="red">＊</i> 传真：</span>
											<div class="input-append">
												<input class="span3" id="fax" name="fax" value="${is.fax }" type="text" readonly="readonly">
												<div id="faxReason1" class="b f18 fl ml10 red hand">√</div>
												<div id="faxReason" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span id="emailReason2"><i class="red">＊</i> 电子邮件：</span>
											<div class="input-append">
												<input class="span3" id="email" name="email" value="${is.email }" type="text" readonly="readonly">
												<div id="emailReason1" class="b f18 fl ml10 red hand">√</div>
												<div id="emailReason" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span id="websiteReason2"><i class="red">＊</i> 企业网址：</span>
											<div class="input-append">
												<input class="span3" id="website" name="website" value="${is.website }" type="text" readonly="readonly">
												<div id="websiteReason1" class="b f18 fl ml10 red hand">√</div>
												<div id="websiteReason" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
											</div>
										</li>
										<li class="col-md-12 p0 mt10"><span id="civilAchievementReason2" class="fl"><i class="red">＊</i>国内供货业绩：</span>
											<div class="col-md-9 mt5">
												<div class="row">
													<textarea class="text_area col-md-12" id="civilAchievement" name="civilAchievement" readonly="readonly"  title="不超过800个字" placeholder=""> ${is.civilAchievement }</textarea>
													<div id="civilAchievementReason1" class="b f18 fl ml10 red hand">√</div>
													<div id="civilAchievementReason" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
												</div>
											</div>
											<div class="clear"></div>
										</li>
										<li class="col-md-12 p0 mt10"><span  id="remarkReason2" class="fl"><i class="red">＊</i>企业简介：</span>
											<div class="col-md-9 mt5">
												<div class="row">
													<textarea class="text_area col-md-12" id="remark" name="remark" title="不超过800个字" readonly="readonly" placeholder="">${is.remark }</textarea>
													<div id="remarkReason1" class="b f18 fl ml10 red hand">√</div>
													<div id="remarkReason" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
												</div>
											</div>
											<div class="clear"></div>
										</li>
									</ul>
									</div>
								</div>
								<div class="tab-pane fade height-450" id="tab-2">
									<div class="margin-bottom-0  categories"><span  id="reglistReason2" class="fl yinc"><i class="red">＊</i>申请表：</span>
										<img src="${is.regList }" width="300px" height="200px" class="fl" >
										<div id="reglistReason1" class="b f18 fl ml10 red hand">√</div>
										<div id="reglistReason" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
									</div>
								</div>
								<div class="tab-pane fade height-450" id="tab-3">
									<div class="margin-bottom-0  categories">
									<form id="form1" action="${pageContext.request.contextPath}/importSupplier/auditReason.html" method="post">
									    <input type="hidden" name="status" id="status"/>
									    <input type="hidden" name="sfiId" value="${is.id }"/>
										<ul class="list-unstyled list-flow" id="reason">
										</ul>
									
									<div class="col-md-12 add_regist tc">
									<c:if test="${is.status==0 }">
										<input class="btn padding-left-20 padding-right-20 btn_back"  type="button" onclick="tijiao(1)" value="审核通过">
										<input class="btn padding-left-20 padding-right-20 btn_back"  type="button" onclick="tijiao(2)" value="审核不通过">
										<input class="btn padding-left-20 padding-right-20 btn_back"  type="button" onclick="tijiao(3)" value="退回修改">
									</c:if>
									<c:if test="${is.status==1 }">
										<input class="btn padding-left-20 padding-right-20 btn_back"  type="button" onclick="tijiao(4)" value="审核通过">
										<input class="btn padding-left-20 padding-right-20 btn_back"  type="button" onclick="tijiao(2)" value="审核不通过">
										<input class="btn padding-left-20 padding-right-20 btn_back"  type="button" onclick="tijiao(3)" value="退回修改">
									</c:if>
									</div>
									</form>
									</div>
								</div>
							</div>
					</div>
				</div>
			</div>
	</div>
	</div>
	<!--底部代码开始-->
	<div class="footer-v2" id="footer-v2">

		<div class="footer">

			<!-- Address -->
			<address class="">Copyright &#38;#169 2016 版权所有：中央军委后勤保障部 京ICP备09055519号</address>
			<div class="">浏览本网主页，建议将电脑显示屏的分辨率调为1024*768</div>
			<!-- End Address -->
			<!--/footer-->
		</div>
	</div>
</body>
</html>
