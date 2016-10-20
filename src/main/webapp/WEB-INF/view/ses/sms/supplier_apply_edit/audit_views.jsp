<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/public/My97DatePicker/WdatePicker.js"></script>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
		/** 下拉框的内容写到 inpput 中 */
	function checkText(ele, id) {
		$("#" + id).val($(ele).text());
	}
	function downloadFile(fileName){
	  fileName=encodeURI(fileName);
      fileName=encodeURI(fileName);
	  window.location.href="<%=basePath %>supplierQuery/downLoadFile.html?fileName="+fileName;
	}
	$(function(){
		 $(":input").each(function() {
      		$(this).attr("readonly", "readonly");
    	});
	});
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
  <form action="<%=basePath %>supplier_edit/save.html" method="post" enctype="multipart/form-data">
  <div class="container content height-350">
    <div class="row magazine-page">
      <div class="col-md-12 tab-v2 job-content">
        <div class="padding-top-10">
          <ul class="nav nav-tabs bgdd">
			<li class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" id="essential"  onclick="window.location.href='<%=basePath %>supplier_edit/auditView.html?id=${supplier.id }'" >基本信息</a></li>
			<li class=""><a aria-expanded="true" href="#tab-2" data-toggle="tab" id="essential" onclick="window.location.href='<%=basePath %>supplier_edit/viewReason.html'" >问题汇总</a></li>
          </ul>
            <div class="tab-content padding-top-20" style="height:1380px;">
              <div class="tab-pane fade active in height-450" id="tab-1">
                <div class=" margin-bottom-0">
					        <h2 class="f16 jbxx1">
					        <i>01</i>企业基本信息
					        </h2>
					        <ul class="list-unstyled list-flow">
					          <li class="col-md-6 p0 "><span class="" id="supplierName2">供应商名称：</span>
					            <div class="input-append">
					              <input type="hidden" name="id" value="${se.id }" >
					              <c:if test="${supplier.supplierName ne se.supplierName }">
					                  <input class="span3" id="supplierName3" name="supplierName" onmouseover="out('${supplier.supplierName }')"  value="${se.supplierName }" type="text">
					              </c:if>
					               <c:if test="${supplier.supplierName eq se.supplierName }">
					                  <input class="span3" id="supplierName3" name="supplierName"   value="${se.supplierName }" type="text">
					              </c:if>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="website2">公司网址：</span>
					            <div class="input-append">
					               <c:if test="${supplier.website ne se.website }">
					               	   <input class="span3" id="website3" name="website" onmouseover="out('${supplier.website }')" value="${se.website }"  type="text">
					              </c:if>
					               <c:if test="${supplier.website eq se.website }">
					               	   <input class="span3" id="website3" name="website"  value="${se.website }"  type="text">
					              </c:if>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="foundDate2">成立日期：</span>
					            <div class="input-append">
					              <c:if test="${supplier.foundDate ne se.foundDate }">
					                  <input class="span3" id="foundDate3" name="foundDate" onmouseover="out('<fmt:formatDate value="${supplier.foundDate }" pattern="yyyy-MM-dd" />')" value='<fmt:formatDate value="${se.foundDate }" pattern="yyyy-MM-dd" />' type="text">
					              </c:if>
					                <c:if test="${supplier.foundDate eq se.foundDate }">
					                  <input class="span3" id="foundDate3" name="foundDate" value='<fmt:formatDate value="${se.foundDate }" pattern="yyyy-MM-dd" />' type="text">
					              </c:if>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="businessType2">营业执照登记类型：</span>
					            <div class="input-append">
					            	 <c:if test="${supplier.businessType ne se.businessType }">
					            	  <input class="span3" id="businessType3" name="businessType" onmouseover="out('${supplier.businessType }')" value="${se.businessType }" type="text">
					                 </c:if>
					                  <c:if test="${supplier.businessType eq se.businessType }">
					            	  <input class="span3" id="businessType3" name="businessType" value="${se.businessType }" type="text">
					                 </c:if>
					            </div>
					          </li>
					          <li class="col-md-6 p0"><span class="" id="address2">地址：</span>
					            <div class="input-append">
					                 <c:if test="${supplier.address ne se.address }">
					                  <input class="span3" id="address3" name="address" onmouseover="out('${supplier.address }')" value="${se.address }" type="text">
					                 </c:if>
					                 <c:if test="${supplier.address eq se.address }">
					                  <input class="span3" id="address3" name="address" value="${se.address }" type="text">
					                 </c:if>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="bankName2">开户行名称：</span>
					            <div class="input-append">
					                 <c:if test="${supplier.bankName ne se.bankName }">
					                  <input class="span3" id="bankName3" name="bankName" onmouseover="out('${supplier.bankName }')" value="${se.bankName }"  type="text">
					                 </c:if>
					                 <c:if test="${supplier.bankName eq se.bankName }">
					                  <input class="span3" id="bankName3" name="bankName" value="${se.bankName }"  type="text">
					                 </c:if>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="bankAccount2">开户行账户：</span>
					            <div class="input-append">
					                 <c:if test="${supplier.bankAccount ne se.bankAccount }">
					               		 <input class="span3" id="bankAccount3" onmouseover="out('${supplier.bankAccount }')" name="bankAccount" value="${se.bankAccount }" type="text">
					                 </c:if>
					                  <c:if test="${supplier.bankAccount eq se.bankAccount }">
					               		 <input class="span3" id="bankAccount3" name="bankAccount" value="${se.bankAccount }" type="text">
					                 </c:if>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="postCode2">邮编：</span>
					            <div class="input-append">
					               <c:if test="${supplier.postCode ne se.postCode }">
					               <input class="span3" id="postCode3" name="postCode" onmouseover="out('${supplier.postCode }')" value="${se.postCode }" type="text">
					                 </c:if>
					                 <c:if test="${supplier.postCode eq se.postCode }">
					               <input class="span3" id="postCode3" name="postCode" value="${se.postCode }" type="text">
					                 </c:if>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red"></i>近三个月完税凭证：</span>
					            <div class="input-append">
					                <c:if test="${supplier.taxCert ne se.taxCert }">
					                 <a onmouseover="out('${supplier.taxCert }')" onclick="downloadFile('${se.taxCert}')" >附件下载(已上传的文件)</a>
					                 </c:if>
					                 <c:if test="${supplier.taxCert eq se.taxCert }">
					                 <a onclick="downloadFile('${se.taxCert}')" >附件下载(已上传的文件)</a>
					                 </c:if>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red"></i>近三年银行基本账户年末对账单：</span>
					            <div class="input-append">
					                 <c:if test="${supplier.billCert ne se.billCert }">
					                   <a onmouseover="out('${supplier.billCert }')" onclick="downloadFile('${se.billCert}')" >附件下载(已上传的文件)</a>
					                 </c:if>
					                 <c:if test="${supplier.billCert eq se.billCert }">
					                   <a onclick="downloadFile('${se.billCert}')" >附件下载(已上传的文件)</a>
					                 </c:if>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red"></i>近三个月缴纳社会保险金凭证：</span>
					            <div class="input-append">
					                 <c:if test="${supplier.securityCert ne se.securityCert }">
					                  <a onmouseover="out('${supplier.securityCert }')" onclick="downloadFile('${se.securityCert}')" >附件下载(已上传的文件)</a>
					                 </c:if>
					                 <c:if test="${supplier.securityCert eq se.securityCert }">
					                  <a onclick="downloadFile('${se.securityCert}')" >附件下载(已上传的文件)</a>
					                 </c:if>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red"></i>近三年内无重大违法记录声明：</span>
					            <div class="input-append">
					                <c:if test="${supplier.breachCert ne se.breachCert }">
                        			 	<a onmouseover="out('${supplier.breachCert }')" onclick="downloadFile('${se.breachCert}')" >附件下载(已上传的文件)</a>
					                 </c:if>
					                  <c:if test="${supplier.breachCert eq se.breachCert }">
                        			 	<a onclick="downloadFile('${se.breachCert}')" >附件下载(已上传的文件)</a>
					                 </c:if>
					            </div>
					          </li>
					        </ul>
					      </div>
					      <div class=" margin-bottom-0">
					        <h2 class="f16 jbxx1">
					        <i>02</i>法人代表人信息
					        </h2>
					       <ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class=""> 姓名：</span>
												<div class="input-append">
													 <c:if test="${supplier.legalName ne se.legalName }">
													    <input class="span3" type="text" id="legalName3" name="legalName" onmouseover="out('${supplier.legalName }')" value="${se.legalName}" />
					                 				</c:if>
					                 				<c:if test="${supplier.legalName eq se.legalName }">
													    <input class="span3" type="text" name="legalName" value="${se.legalName}" />
					                 				</c:if>
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""> 身份证号：</span>
												<div class="input-append">
													 <c:if test="${supplier.legalIdCard ne se.legalIdCard }">
													    <input class="span3" type="text" id="legalIdCard3" name="legalIdCard" onmouseover="out('${supplier.legalIdCard }')" value="${se.legalIdCard}" />
					                 				</c:if>
					                 				 <c:if test="${supplier.legalIdCard eq se.legalIdCard }">
													    <input class="span3" type="text" name="legalIdCard" value="${se.legalIdCard}" />
					                 				</c:if>
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""> 固定电话：</span>
												<div class="input-append">
													 <c:if test="${supplier.legalTelephone ne se.legalTelephone }">
													    <input class="span3" type="text" id="legalTelephone3" name="legalTelephone" onmouseover="out('${supplier.legalTelephone }')" value="${se.legalTelephone}" />
					                				 </c:if>
					                				  <c:if test="${supplier.legalTelephone eq se.legalTelephone }">
                        			  					<input class="span3" type="text" name="legalTelephone" value="${se.legalTelephone}" />
					                				 </c:if>
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""> 手机：</span>
												<div class="input-append">
													 <c:if test="${supplier.legalMobile ne se.legalMobile }">
													     <input class="span3" type="text" id="legalMobile3"  name="legalMobile" onmouseover="out('${supplier.legalMobile }')" value="${se.legalMobile}" />
					                 				</c:if>
					                 				 <c:if test="${supplier.legalMobile eq se.legalMobile }">
													     <input class="span3" type="text" name="legalMobile"  value="${se.legalMobile}" />
					                 				</c:if>
												</div>
											</li>
										</ul>
					      </div>
					      
					      <div class=" margin-bottom-0">
					        <h2 class="f16 jbxx1">
					        <i>03</i>联系人信息
					        </h2>
					        <ul class="list-unstyled list-flow">
					          <li class="col-md-6 p0 "><span class="" id="contactName2">姓名：</span>
					            <div class="input-append">
					               <c:if test="${supplier.contactName ne se.contactName }">
					                  <input class="span3" id="contactName3" name="contactName" onmouseover="out('${supplier.contactName }')" value="${se.contactName }" type="text">
					                 </c:if>
					                 <c:if test="${supplier.contactName eq se.contactName }">
					                  <input class="span3" id="contactName3" name="contactName" value="${se.contactName }" type="text">
					                 </c:if>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="contactFax2">传真：</span>
					            <div class="input-append">
					               <c:if test="${supplier.contactFax ne se.contactFax }">
					                   <input class="span3" id="contactFax3" name="contactFax" onmouseover="out('${supplier.contactFax }')" value="${se.contactFax }"  type="text">
					                 </c:if>
					                 <c:if test="${supplier.contactFax eq se.contactFax }">
					                   <input class="span3" id="contactFax3" name="contactFax"  value="${se.contactFax }"  type="text">
					                 </c:if>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="contactTelephone1">固定电话：</span>
					            <div class="input-append">
					            	 <c:if test="${supplier.contactTelephone ne se.contactTelephone }">
					            	    <input class="span3" id="contactTelephone3" name="contactTelephone" onmouseover="out('${supplier.contactTelephone }')" value="${se.contactTelephone }" type="text">
					                 </c:if>
					                 <c:if test="${supplier.contactTelephone eq se.contactTelephone }">
					            	    <input class="span3" id="contactTelephone3" name="contactTelephone" value="${se.contactTelephone }" type="text">
					                 </c:if>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="contactMobile2">手机：</span>
					            <div class="input-append">
					            	 <c:if test="${supplier.contactMobile ne se.contactMobile }">
					            	  <input class="span3" id="contactMobile3" name="contactMobile" onmouseover="out('${se.contactMobile }')"  value="${se.contactMobile }" type="text">
					                 </c:if>
					                  <c:if test="${supplier.contactMobile eq se.contactMobile }">
					            	  <input class="span3" id="contactMobile3" name="contactMobile"  value="${se.contactMobile }" type="text">
					                 </c:if>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="contactEmail2">邮箱：</span>
					            <div class="input-append">
					            	 <c:if test="${supplier.contactEmail ne se.contactEmail }">
					            	   <input class="span3" id="contactEmail3" name="contactEmail" onmouseover="out('${supplier.contactEmail }')" value="${se.contactEmail }" type="text">
					                 </c:if>
					                  <c:if test="${supplier.contactEmail eq se.contactEmail }">
					            	   <input class="span3" id="contactEmail3" name="contactEmail"  value="${se.contactEmail }" type="text">
					                 </c:if>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="contactAddress2">地址：</span>
					            <div class="input-append">
					            	 <c:if test="${supplier.contactAddress ne se.contactAddress }">
					            	  <input class="span3" id="contactAddress3" name="contactAddress" onmouseover="out('${supplier.contactAddress }')" value="${se.contactAddress }" type="text">
					                 </c:if>
					                  <c:if test="${supplier.contactAddress eq se.contactAddress }">
					            	  <input class="span3" id="contactAddress3" name="contactAddress" value="${se.contactAddress }" type="text">
					                 </c:if>
					            </div>
					          </li>
					        </ul>
					      </div>
					      
					      <div class=" margin-bottom-0">
					       <h2 class="f16 jbxx1 mt30">
											<i>05</i>营业执照
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class=""> 统一社会信用代码：</span>
												<div class="input-append">
													<c:if test="${supplier.creditCode ne se.creditCode }">
														<input class="span3" type="text" id="creditCode3" name="creditCode" onmouseover="out('${supplier.creditCode }')" value="${se.creditCode}" />
													</c:if>
													<c:if test="${supplier.creditCode eq se.creditCode }">
														<input class="span3" type="text" name="creditCode" value="${se.creditCode}" />
													</c:if>
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""> 登记机关：</span>
												<div class="input-append">
													<c:if test="${supplier.registAuthority ne se.registAuthority }">
														<input class="span3" type="text" id="registAuthority3" name="registAuthority" onmouseover="out('${supplier.registAuthority }')" value="${se.registAuthority}" />
													</c:if>
													<c:if test="${supplier.registAuthority eq se.registAuthority }">
														<input class="span3" type="text" name="registAuthority" value="${se.registAuthority}" />
													</c:if>
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""> 注册资本：</span>
												<div class="input-append">
													<c:if test="${supplier.registFund ne se.registFund }">
														<input class="span3" type="text" id="registFund3" name="registFund" onmouseover="out('${supplier.registFund }')" value="${se.registFund}" />
													</c:if>
													<c:if test="${supplier.registFund eq se.registFund }">
														<input class="span3" type="text" name="registFund" value="${se.registFund}" />
													</c:if>
												</div>
											</li>
											<li class="col-md-6 p0"><span class="">营业开始时间：</span>
												<div class="input-append">
												<c:if test="${supplier.businessStartDate ne se.businessStartDate }">
													<input class="span2" type="text" id="businessStartDate3"  readonly="readonly" onmouseover="out('<fmt:formatDate value="${supplier.businessStartDate }" pattern="yyyy-MM-dd" />')" onClick="WdatePicker()" name="businessStartDate" value="<fmt:formatDate value="${se.businessStartDate }" pattern="yyyy-MM-dd" />" /> <span class="add-on"><img src="${pageContext.request.contextPath}/public/ZHQ/images/time_icon.png" class="mb10" /> </span>
												</c:if>
												<c:if test="${supplier.businessStartDate eq se.businessStartDate }">
													<input class="span2" type="text" readonly="readonly" onClick="WdatePicker()" name="businessStartDate" value="<fmt:formatDate value="${se.businessStartDate }" pattern="yyyy-MM-dd" />" /> <span class="add-on"><img src="${pageContext.request.contextPath}/public/ZHQ/images/time_icon.png" class="mb10" /> </span>
												</c:if>
												</div>
											</li>
											<li class="col-md-6 p0"><span class="">营业截止时间：</span>
												<div class="input-append">
												<c:if test="${supplier.businessEndDate ne se.businessEndDate }">
													<input class="span2" type="text"  id="businessEndDate3" readonly="readonly" onmouseover="out('<fmt:formatDate value="${supplier.businessEndDate }" pattern="yyyy-MM-dd" />')" onClick="WdatePicker()" name="businessEndDate" value="<fmt:formatDate value="${se.businessEndDate }" pattern="yyyy-MM-dd" />" /> <span class="add-on"><img src="${pageContext.request.contextPath}/public/ZHQ/images/time_icon.png" class="mb10" /> </span>
												</c:if>
												<c:if test="${supplier.businessEndDate eq se.businessEndDate }">
													<input class="span2" type="text" readonly="readonly" onClick="WdatePicker()" name="businessEndDate" value="<fmt:formatDate value="${se.businessEndDate }" pattern="yyyy-MM-dd" />" /> <span class="add-on"><img src="${pageContext.request.contextPath}/public/ZHQ/images/time_icon.png" class="mb10" /> </span>
												</c:if>
												</div>
											</li>

											<li class="col-md-6 p0"><span class=""> 生产经营地址：</span>
												<div class="input-append">
												<c:if test="${supplier.businessAddress ne se.businessAddress }">
													<input class="span3" type="text" id="businessAddress3" name="businessAddress" onmouseover="out('${supplier.businessAddress }')" value="${se.businessAddress}" />
												</c:if>
												<c:if test="${supplier.businessAddress eq se.businessAddress }">
													<input class="span3" type="text" name="businessAddress"  value="${se.businessAddress}" />
												</c:if>
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""> 邮编：</span>
												<div class="input-append">
												<c:if test="${supplier.businessPostCode ne se.businessPostCode }">
													<input class="span3" type="text" id="businessPostCode3"  name="businessPostCode" onmouseover="out('${supplier.businessPostCode }')" value="${se.businessPostCode}" />
												</c:if>
												<c:if test="${supplier.businessPostCode eq se.businessPostCode }">
													<input class="span3" type="text" name="businessPostCode"  value="${se.businessPostCode}" />
												</c:if>
												</div>
											</li>
											<li class="col-md-12 p0 mt10"><span class="fl">经营范围：</span>
												<div class="col-md-9 mt5">
													<div class="row _mr20">
													<c:if test="${supplier.businessScope ne se.businessScope }">
														<textarea class="text_area col-md-12" id="businessScope3"  title="不超过800个字" onmouseover="out('${supplier.businessScope }')" name="businessScope">${se.businessScope}</textarea>
													</c:if>
													<c:if test="${supplier.businessScope eq se.businessScope }">
														<textarea class="text_area col-md-12" title="不超过800个字" name="businessScope">${se.businessScope}</textarea>
													</c:if>
													</div>
												</div>
												<div class="clear"></div></li>
										</ul>
										<h2 class="f16 jbxx1 mt10">
											<i>06</i>境外分支
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class=""> 境外分支结构：</span>
												<div class="input-append">
													<input class="span2" id="overseasBranch_input_id" name="overseasBranch" type="text" readonly="readonly" value="${se.overseasBranch}" />
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
											<li class="col-md-6 p0"><span class=""> 境外分支所在国家：</span>
												<div class="input-append">
												<c:if test="${supplier.branchCountry ne se.branchCountry }">
													<input class="span3" name="branchCountry"  id="branchCountry3" onmouseover="out('${supplier.branchCountry }')" type="text" value="${se.branchCountry}" />
												</c:if>
												<c:if test="${supplier.branchCountry eq se.branchCountry }">
													<input class="span3" name="branchCountry" type="text" value="${se.branchCountry}" />
												</c:if>
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""> 分支地址：</span>
												<div class="input-append">
												<c:if test="${supplier.branchAddress ne se.branchAddress }">
													<input class="span3" type="text" id="branchAddress3" name="branchAddress" onmouseover="out('${supplier.branchAddress }')" value="${se.branchAddress}" />
												</c:if>
												<c:if test="${supplier.branchAddress eq se.branchAddress }">
													<input class="span3" type="text" name="branchAddress" value="${se.branchAddress}" />
												</c:if>
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""> 机构名称：</span>
												<div class="input-append">
												<c:if test="${supplier.branchName ne se.branchName }">
													<input class="span3" type="text" id="branchName3" name="branchName" onmouseover="out('${supplier.branchName }')" value="${se.branchName}" />
												</c:if>
												<c:if test="${supplier.branchName eq se.branchName }">
													<input class="span3" type="text" name="branchName" value="${se.branchName}" />
												</c:if>
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""> 分支生产经营范围：</span>
												<div class="input-append">
												<c:if test="${supplier.branchBusinessScope ne se.branchBusinessScope }">
													<input class="span3" type="text" name="branchBusinessScope" id="branchBusinessScope3" onmouseover="out('${supplier.branchBusinessScope }')" value="${se.branchBusinessScope}" />
												</c:if>
												<c:if test="${supplier.branchBusinessScope eq se.branchBusinessScope }">
													<input class="span3" type="text" name="branchBusinessScope" value="${se.branchBusinessScope}" />
												</c:if>
												</div>
											</li>
										</ul>
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
