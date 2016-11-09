<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]-->
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>基本信息</title>
<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link href="${pageContext.request.contextPath}/public/ZHH/css/common.css" media="screen" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/style.css" type="text/css"/>
<link href="${pageContext.request.contextPath}/public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/style.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/animate.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/ui-dialog.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/dialog-select.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/line-icons.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/font-awesome.min.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/jquery.fileupload-ui.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/zTreeStyle.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/sky-forms.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/custom-sky-forms.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/jquery.fancybox.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/owl.carousel.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/owl.theme.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/style-switcher.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/shortcode_timeline2.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/app.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/blocks.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/datepicker.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/WdatePicker.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/select2.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/application.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/header-v4.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/img-hover.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/brand-buttons-inversed.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/blog_magazine.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/page_job.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/page_log_reg_v1.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/shop.style.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/header-v5.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/footer-v4.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/masterslider.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/james.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/layer/skin/layer.css" media="screen" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/layer/skin/layer.ext.css" media="screen" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/ZHH/css/WdatePicker(1).css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/public/ZHH/js/hm.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/My97DatePicker/WdatePicker.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery-migrate-1.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery_ujs.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/back-to-top.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.query.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/dialog-plus-min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.fancybox.pack.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/smoothScroll.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.parallax.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/app.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/common.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/dota.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.dragsort-0.5.2.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/fancy-box.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/style-switcher.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/owl.carousel.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/owl-carousel.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/owl-recent-works.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/WdatePicker.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.form.min.js"></script>
<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/public/layer/extend/layer.ext.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.maskedinput.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery-ui.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/masking.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/datepicker.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/timepicker.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/dialog-select.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/locale.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.ui.widget.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/load-image.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/canvas-to-blob.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/tmpl.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.iframe-transport.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.fileupload.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.fileupload-fp.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.fileupload-ui.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery-fileupload.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/form.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/select2.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/select2_locale_zh-CN.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/application.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.counterup.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/modernizr.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/touch.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/product-quantity.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/master-slider.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/shop.app.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/masterslider.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.easing.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/james.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
		/** 下拉框的内容写到 inpput 中 */
	function checkText(ele, id) {
		$("#" + id).val($(ele).text());
		if($(ele).text()=="是"){
			$("#ob_id").val(1);
		}else{
		$("#ob_id").val(0);
		}
	}
	$(function(){
		if('${supplier.overseasBranch}'==1){
			$("#overseasBranch_input_id").val("是");
		}else{
		$("#overseasBranch_input_id").val("否");
		}
	});
	function downloadFile(fileName){
	  fileName=encodeURI(fileName);
      fileName=encodeURI(fileName);
	  window.location.href="${pageContext.request.contextPath}/supplierQuery/downLoadFile.html?fileName="+fileName;
	}
</script>
<style type="text/css">
.jbxx1{
  background:url(../images/down_icon.png) no-repeat 5px !important;
  padding-left:40px !important;
}
.jbxx1 i{
    width: 24px;
    height: 30px;
    background: url(../../../../../zhbj/public/ZHQ/images/round.png) no-repeat center;
    color: #ffffff;
    font-size: 12px;
    text-align: center;
    display: block;
    float: left;
    line-height: 30px;
    font-style: normal;
    margin-right: 10px;
}
</style>
</head>
  
<body>
 <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">供应商管理</a></li><li class="active"><a href="#">供应商变更</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
  <!-- 项目戳开始 -->
  <div class="container clear">
  <!--详情开始-->
  <form action="${pageContext.request.contextPath}/supplier_edit/save.html" method="post">
  <div class="container content height-350">
    <div class="row magazine-page">
      <div class="col-md-12 tab-v2 job-content">
        <div class="padding-top-10">
          <ul class="nav nav-tabs bgdd">
			<li class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" id="essential" onclick="location='${pageContext.request.contextPath}/supplierAudit/essential.html'">基本信息</a></li>
          </ul>
            <div class="tab-content padding-top-20" style="height:1380px;">
              <div class="tab-pane fade active in height-450" id="tab-1">
                <div class=" margin-bottom-0">
					        <h2 class="f16 jbxx1">
					        <i>01</i>企业基本信息
					        </h2>
					        <ul class="list-unstyled list-flow">
					          <li class="col-md-6 p0 "><span class="" id="supplierName2"><i class="red">＊</i>供应商名称：</span>
					            <div class="input-append">
					              <input type="hidden" name="id" value="${supplier.id }" >
					              <input class="span3" id="supplierName3" name="supplierName" value="${supplier.supplierName }" type="text">
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="website2"><i class="red">＊</i>公司网址：</span>
					            <div class="input-append">
					              <input class="span3" id="website3" name="website" value="${supplier.website }"  type="text">
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="foundDate2"><i class="red">＊</i>成立日期：</span>
					            <div class="input-append">
					              <input class="span3" id="foundDate3" name="foundDate" value='<fmt:formatDate value="${supplier.foundDate }" pattern="yyyy-MM-dd" />' type="text">
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="businessType2"><i class="red">＊</i>营业执照登记类型：</span>
					            <div class="input-append">
					              <input class="span3" id="businessType3" name="businessType" value="${supplier.businessType }" type="text">
					            </div>
					          </li>
					          <li class="col-md-6 p0"><span class="" id="address2"><i class="red">＊</i>地址：</span>
					            <div class="input-append">
					              <input class="span3" id="address3" name="address" value="${supplier.address }" type="text">
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="bankName2"><i class="red">＊</i>开户行名称：</span>
					            <div class="input-append">
					              <input class="span3" id="bankName3" name="bankName" value="${supplier.bankName }"  type="text">
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="bankAccount2"><i class="red">＊</i>开户行账户：</span>
					            <div class="input-append">
					              <input class="span3" id="bankAccount3" name="bankAccount" value="${supplier.bankAccount }" type="text">
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="postCode2"><i class="red">＊</i>邮编：</span>
					            <div class="input-append">
					              <input class="span3" id="postCode3" name="postCode" value="${supplier.postCode }" type="text">
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red"></i>近三个月完税凭证：</span>
					            <div class="input-append">
					              <a onclick="downloadFile('${supplier.taxCert}')" >附件下载(已上传的文件)</a>
					            </div>
					          </li>
					           <li class="col-md-6 p0 "><span class=""><i class="red"></i>近三个月完税凭证：</span>
					            <div class="input-append mt5">
					              <a href="#"><i></i><input name="taxCertFile" type="file" class="fl" /></a>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red"></i>近三年银行基本账户年末对账单：</span>
					            <div class="input-append">
					              <a onclick="downloadFile('${supplier.billCert}')" >附件下载(已上传的文件)</a>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red"></i>近三年银行基本账户年末对账单：</span>
					            <div class="input-append mt5">
					              <a href="#"><i></i><input name="billCertFile" type="file"  class="fl"/></a>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red"></i>近三个月缴纳社会保险金凭证：</span>
					            <div class="input-append">
					              <a onclick="downloadFile('${supplier.securityCert}')" >附件下载(已上传的文件)</a>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red"></i>近三个月缴纳社会保险金凭证：</span>
					           <div class="input-append mt5">
					              <a href="#"><i></i><input name="securityCertFile" type="file"  class="fl" /></a>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red"></i>近三年内无重大违法记录声明：</span>
					            <div class="input-append">
					              <a onclick="downloadFile('${supplier.breachCert}')" >附件下载(已上传的文件)</a>
					            </div>
					          </li>
							  <li class="col-md-6 p0 "><span class=""><i class="red"></i>近三年内无重大违法记录声明：</span>
					            <div class="input-append mt5">
					              <a href="#"><i></i><input name="breachCertFile" type="file"  class="fl" /></a>
					            </div>
					          </li>			 
					        </ul>
					      </div>
					      
					      <div class=" margin-bottom-0">
					        <h2 class="f16 jbxx1">
					        <i>02</i>法人代表人信息
					        </h2>
					       <ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 姓名：</span>
												<div class="input-append">
													<input class="span3" type="text" name="legalName" value="${supplier.legalName}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 身份证号：</span>
												<div class="input-append">
													<input class="span3" type="text" name="legalIdCard" value="${supplier.legalIdCard}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 固定电话：</span>
												<div class="input-append">
													<input class="span3" type="text" name="legalTelephone" value="${supplier.legalTelephone}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 手机：</span>
												<div class="input-append">
													<input class="span3" type="text" name="legalMobile" value="${supplier.legalMobile}" />
												</div>
											</li>
											<div class="clear"></div>
										</ul>
					      </div>
					      
					      <div class=" margin-bottom-0">
					        <h2 class="f16 jbxx1">
					        <i>03</i>联系人信息
					        </h2>
					        <ul class="list-unstyled list-flow">
					          <li class="col-md-6 p0 "><span class="" id="contactName2"><i class="red">＊</i>姓名：</span>
					            <div class="input-append">
					              <input class="span3" id="contactName3" name="contactName" value="${supplier.contactName }" type="text">
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="contactFax2"><i class="red">＊</i>传真：</span>
					            <div class="input-append">
					              <input class="span3" id="contactFax3" name="contactFax" value="${supplier.contactFax }"  type="text">
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="contactTelephone1"><i class="red">＊</i>固定电话：</span>
					            <div class="input-append">
					              <input class="span3" id="contactTelephone3" name="contactTelephone" value="${supplier.contactTelephone }" type="text">
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="contactMobile2"><i class="red">＊</i>手机：</span>
					            <div class="input-append">
					              <input class="span3" id="contactMobile3" name="contactMobile" value="${supplier.contactMobile }" type="text">
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="contactEmail2"><i class="red">＊</i>邮箱：</span>
					            <div class="input-append">
					              <input class="span3" id="contactEmail3" name="contactEmail" value="${supplier.contactEmail }" type="text">
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="contactAddress2"><i class="red">＊</i>地址：</span>
					            <div class="input-append">
					              <input class="span3" id="contactAddress3" name="contactAddress" value="${supplier.contactAddress }" type="text">
					            </div>
					          </li>
					        </ul>
					      </div>
					      
					      <div class=" margin-bottom-0">
					       <h2 class="f16 jbxx1 mt30">
											<i>05</i>营业执照
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 统一社会信用代码：</span>
												<div class="input-append">
													<input class="span3" type="text" name="creditCode" value="${supplier.creditCode}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 登记机关：</span>
												<div class="input-append">
													<input class="span3" type="text" name="registAuthority" value="${supplier.registAuthority}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 注册资本：</span>
												<div class="input-append">
													<input class="span3" type="text" name="registFund" value="${supplier.registFund}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>营业开始时间：</span>
												<div class="input-append">
													<input class="span2" type="text" readonly="readonly" onClick="WdatePicker()" name="businessStartDate" value="<fmt:formatDate value="${supplier.businessStartDate }" pattern="yyyy-MM-dd" />" /> <span class="add-on"><img src="${pageContext.request.contextPath}/public/ZHQ/images/time_icon.png" class="mb10" /> </span>
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>营业截止时间：</span>
												<div class="input-append">
													<input class="span2" type="text" readonly="readonly" onClick="WdatePicker()" name="businessEndDate" value="<fmt:formatDate value="${supplier.businessEndDate }" pattern="yyyy-MM-dd" />" /> <span class="add-on"><img src="${pageContext.request.contextPath}/public/ZHQ/images/time_icon.png" class="mb10" /> </span>
												</div>
											</li>

											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 生产经营地址：</span>
												<div class="input-append">
													<input class="span3" type="text" name="businessAddress" value="${supplier.businessAddress}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 邮编：</span>
												<div class="input-append">
													<input class="span3" type="text" name="businessPostCode" value="${supplier.businessPostCode}" />
												</div>
											</li>
							<li class="col-md-6 p0 "><span class=""><i class="red"></i>营业执照：</span>
					            <div class="input-append">
					              <a onclick="downloadFile('${supplier.businessCert}')" >附件下载(已上传的文件)</a>
					            </div>
					          </li>
							  <li class="col-md-6 p0 "><span class=""><i class="red"></i>营业执照：</span>
					            <div class="input-append mt5">
					              <a href="#"><i></i><input name="businessCertFile" type="file"  class="fl" /></a>
					            </div>
					          </li>	
											<li class="col-md-12 p0 mt10"><span class="fl"><i class="red">＊</i>经营范围：</span>
												<div class="col-md-9 mt5">
													<div class="row _mr20">
														<textarea class="text_area col-md-12" title="不超过800个字" name="businessScope">${supplier.businessScope}</textarea>
													</div>
												</div>
												<div class="clear"></div></li>
											<div class="clear"></div>
										</ul>
										<h2 class="f16 jbxx1 mt10">
											<i>06</i>境外分支
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 境外分支结构：</span>
												<div class="input-append">
													<input class="span2" id="overseasBranch_input_id"  type="text" readonly="readonly" />
													<input class="span2" id="ob_id" name="overseasBranch" type="hidden" readonly="readonly" value="${supplier.overseasBranch}" />
													<div class="btn-group">
														<button class="btn dropdown-toggle add-on" data-toggle="dropdown">
															<img src="${pageContext.request.contextPath}/public/ZHQ/images/down.png" class="margin-bottom-5" />
														</button>
														<ul class="dropdown-menu list-unstyled">
															<li class="hand tc" onclick="checkText(this, 'overseasBranch_input_id')">是</li>
															<li class="hand tc" onclick="checkText(this, 'overseasBranch_input_id')">否</li>
														</ul>
													</div>
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 境外分支所在国家：</span>
												<div class="input-append">
													<input class="span3" name="branchCountry" type="text" value="${supplier.branchCountry}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 分支地址：</span>
												<div class="input-append">
													<input class="span3" type="text" name="branchAddress" value="${supplier.branchAddress}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 机构名称：</span>
												<div class="input-append">
													<input class="span3" type="text" name="branchName" value="${supplier.branchName}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 分支生产经营范围：</span>
												<div class="input-append">
													<input class="span3" type="text" name="branchBusinessScope" value="${supplier.branchBusinessScope}" />
												</div>
											</li>
											<div class="clear"></div>
										</ul>

					        <div class="col-md-12 tc">
			  							<input class="btn btn-windows reset" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
			  							<input  class="btn btn-windows git" type="submit" value="提交" />
			 						</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</form>
</div>
</body>
</html>
