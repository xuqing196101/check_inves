<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
function reason(id){
  var id1=id+"1"; //审批的字段
  var id2=id+"2";
  var id3=id+"3";
  var auditField=$("#"+id2+"").text().replaceAll("＊","").replaceAll("：",""); //审批的字段名字
  var  auditContent= document.getElementById(""+id3+"").value; //审批的字段内容
  var auditType=$("#essential").text(); //审核类型
  layer.prompt({title: '请填写不通过理由', formType: 2}, function(text){
    $.ajax({
        url:"<%=basePath%>supplierAudit/auditReasons.html",
        type:"post",
        data:"auditType="+auditType+"&auditField="+auditField+"&auditContent="+auditContent+"&suggest="+text,
      });
  $("#"+id1+"").hide();
  layer.msg("审核不通过的理由是："+text);    
/*    $("input[name='auditType']").val(auditType);
   $("input[name='auditField']").val(auditField);
   $("input[name='auditContent']").val(auditContent);
   $("input[name='suggest']").val(text);
    
   $("#save_reaeon").submit(); */
    });
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
				    <li class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" id="essential" onclick="location='<%=basePath%>supplierAudit/essential.html'">基本信息</a></li>
            <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" onclick="location='<%=basePath%>supplierAudit/financial.html'">财务信息</a></li>
				    <li class=""><a aria-expanded="fale" href="#tab-3" data-toggle="tab" onclick="location='<%=basePath%>supplierAudit/shareholder.html'">股东信息</a></li>
            <li class=""><a aria-expanded="fale" href="#tab-2" data-toggle="tab" onclick="location='<%=basePath%>supplierAudit/materialProduction.html'">物资-生产型专业信息</a></li>
				    <li class=""><a aria-expanded="fale" href="#tab-3" data-toggle="tab" onclick="location='<%=basePath%>supplierAudit/materialSales.html'">物资-销售型专业信息</a></li>
				    <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" onclick="location='<%=basePath%>supplierAudit/engineering.html'">工程-专业信息</a></li>
				    <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" >服务-专业信息</a></li>
				    <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" >品目信息</a></li>
				    <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" >产品信息</a></li>
				    <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" onclick="location='<%=basePath%>supplierAudit/reasonsList.html'">审核汇总</a></li>
          </ul>
            <div class="tab-content padding-top-20" style="height:1500px;">
              <div class="tab-pane fade active in height-450" id="tab-1">
                <div class=" margin-bottom-0">
					        <h2 class="f16 jbxx">
					        <i>01</i>企业基本信息
					        </h2>
					        <ul class="list-unstyled list-flow">
					          <li class="col-md-6 p0 "><span class="" id="supplierName2"><i class="red">＊</i>供应商名称：</span>
					            <div class="input-append">
					              <input class="span3" id="supplierName3" value="${supplier.supplierName } " type="text">
					              <div id="supplierName1" class="b f18 fl ml10 red hand">√</div>
					              <div id="supplierName" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="website2"><i class="red">＊</i>公司网址：</span>
					            <div class="input-append">
					              <input class="span3" id="website3" value="${supplier.website } "  type="text">
					              <div id="website1"  class="b f18 fl ml10 red hand">√</div>
					              <div  id="website" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="foundDate2"><i class="red">＊</i>成立日期：</span>
					            <div class="input-append">
					              <input class="span3" id="foundDate3" value="${supplier.foundDate }" type="text">
					              <div id="foundDate1" class="b f18 fl ml10 red hand">√</div>
					              <div id="foundDate" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="businessType2"><i class="red">＊</i>营业执照登记类型：</span>
					            <div class="input-append">
					              <input class="span3" id="businessType3" value="${supplier.businessType } " type="text">
					              <div id="businessType1" class="b f18 fl ml10 red hand">√</div>
					              <div id="businessType" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0"><span class="" id="address2"><i class="red">＊</i>地址：</span>
					            <div class="input-append">
					              <input class="span3" id="address3" value="${supplier.address } " type="text">
					              <div id="address1" class="b f18 fl ml10 red hand">√</div>
					              <div id="address" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="bankName2"><i class="red">＊</i>开户行名称：</span>
					            <div class="input-append">
					              <input class="span3" id="bankName3" value="${supplier.bankName } "  type="text">
					              <div id="bankName1" class="b f18 fl ml10 red hand">√</div>
					              <div id="bankName" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="bankAccount2"><i class="red">＊</i>开户行账户：</span>
					            <div class="input-append">
					              <input class="span3" id="bankAccount3" value="${supplier.bankAccount } " type="text">
					              <div id="bankAccount1" class="b f18 fl ml10 red hand">√</div>
					              <div id="bankAccount" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="postCode2"><i class="red">＊</i>邮编：</span>
					            <div class="input-append">
					              <input class="span3" id="postCode3" value="${supplier.postCode }" type="text">
					              <div id="postCode1" class="b f18 fl ml10 red hand">√</div>
					              <div id="postCode" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>近三个月完税凭证：</span>
					            <div class="input-append">
					              <a >附件下载</a>
					              <div class="b f18 fl ml10 red hand">√</div>
					              <div onclick="reason()" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>近三年银行账单：</span>
					            <div class="input-append">
					              <a>附件下载</a>
					              <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>近三个月保险凭证：</span>
					            <div class="input-append">
					              <a>附件下载</a>
					              <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>近三年违法记录：</span>
					            <div class="input-append">
					              <a>附件下载</a>
					              <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					        </ul>
					      </div>
					      
					      <div class=" margin-bottom-0">
					        <h2 class="f16 jbxx">
					        <i>02</i>法人代表人信息
					        </h2>
					        <ul class="list-unstyled list-flow">
					          <li class="col-md-6 p0 "><span class="" id="legalName2"><i class="red">＊</i>姓名：</span>
					            <div class="input-append">
					              <input class="span3" id="legalName3" value="${supplier.legalName } " type="text">
					              <div id="legalName1" class="b f18 fl ml10 red hand">√</div>
					              <div id="legalName" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="legaIdCard2"><i class="red">＊</i>身份证号：</span>
					            <div class="input-append">
					              <input class="span3" id="legaIdCard3" value="${supplier.legaIdCard } "  type="text">
					              <div id="legaIdCard1" class="b f18 fl ml10 red hand">√</div>
					              <div id="legaIdCard" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="legalTelephone2"><i class="red">＊</i>固定电话：</span>
					            <div class="input-append">
					              <input class="span3" id="legalTelephone3" value="${supplier.legalTelephone } " type="text">
					              <div id="legalTelephone1" class="b f18 fl ml10 red hand">√</div>
					              <div id="legalTelephone" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="legalMobile2"><i class="red">＊</i>手机：</span>
					            <div class="input-append">
					              <input class="span3" id="legalMobile3" value="${supplier.legalMobile } " type="text">
					              <div id="legalMobile1" class="b f18 fl ml10 red hand">√</div>
					              <div id="legalMobile" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					        </ul>
					      </div>
					      
					      <div class=" margin-bottom-0">
					        <h2 class="f16 jbxx">
					        <i>03</i>联系人信息
					        </h2>
					        <ul class="list-unstyled list-flow">
					          <li class="col-md-6 p0 "><span class="" id="contactName2"><i class="red">＊</i>姓名：</span>
					            <div class="input-append">
					              <input class="span3" id="contactName3" value="${supplier.contactName } " type="text">
					              <div id="contactName1" class="b f18 fl ml10 red hand">√</div>
					              <div id="contactName" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="contactFax2"><i class="red">＊</i>传真：</span>
					            <div class="input-append">
					              <input class="span3" id="contactFax3" value="${supplier.contactFax } "  type="text">
					              <div id="contactFax1" class="b f18 fl ml10 red hand">√</div>
					              <div id="contactFax" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="contactTelephone1"><i class="red">＊</i>固定电话：</span>
					            <div class="input-append">
					              <input class="span3" id="contactTelephone3" value="${supplier.contactTelephone } " type="text">
					              <div id="contactTelephone1" class="b f18 fl ml10 red hand">√</div>
					              <div id="contactTelephone" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="contactMobile2"><i class="red">＊</i>手机：</span>
					            <div class="input-append">
					              <input class="span3" id="contactMobile3" value="${supplier.contactMobile } " type="text">
					              <div id="contactMobile1" class="b f18 fl ml10 red hand">√</div>
					              <div id="contactMobile" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="contactEmail2"><i class="red">＊</i>邮箱：</span>
					            <div class="input-append">
					              <input class="span3" id="contactEmail3" value="${supplier.contactEmail } " type="text">
					              <div id="contactEmail1" class="b f18 fl ml10 red hand">√</div>
					              <div id="contactEmail" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="contactAddress2"><i class="red">＊</i>地址：</span>
					            <div class="input-append">
					              <input class="span3" id="contactAddress3" value="${supplier.contactAddress } " type="text">
					              <div id="contactAddress1"class="b f18 fl ml10 red hand">√</div>
					              <div id="contactAddress" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					        </ul>
					      </div>
					      
					      <div class=" margin-bottom-0">
					        <h2 class="f16 jbxx">
					        <i>04</i>营业执照
					        </h2>
					        <ul class="list-unstyled list-flow">
					          <li class="col-md-6 p0 "><span class="" id="creditCode2"><i class="red">＊</i>统一社会信用代码：</span>
					            <div class="input-append">
					              <input class="span3" id="creditCode3" value="${supplier.creditCode } " type="text">
					              <div id="creditCode1" class="b f18 fl ml10 red hand">√</div>
					              <div id="creditCode" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="registAuthority2"><i class="red">＊</i>登记机关：</span>
					            <div class="input-append">
					              <input class="span3" id="registAuthority3" value="${supplier.registAuthority } "  type="text">
					              <div id="registAuthority1" class="b f18 fl ml10 red hand">√</div>
					              <div id="registAuthority" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="registFund2"><i class="red">＊</i>注册资本：</span>
					            <div class="input-append">
					              <input class="span3" id="registFund3" value="${supplier.registFund } " type="text">
					              <div id="registFund1" class="b f18 fl ml10 red hand">√</div>
					              <div id="registFund" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="businessEndDate2"><i class="red">＊</i>营业期限：</span>
					            <div class="input-append">
					              <input class="span3" id="businessEndDate3" value="${supplier.businessStartDate }至 ${supplier.businessEndDate} " type="text">
					              <div id="businessEndDate1" class="b f18 fl ml10 red hand">√</div>
					              <div id="businessEndDate" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-12 p0 mt10"><span class="fl" id="businessScope2"><i class="red">＊</i>经营范围：</span>
					            <div class="col-md-9 mt5">
					              <div class="row">
					                <textarea class="text_area col-md-12" id="businessScope3"  title="不超过800个字" >${supplier.businessScope }</textarea>
					                <div id="businessScope1" class="b f18 fl ml10 red hand">√</div>
					                <div id="businessScope" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
					              </div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="fl" id="businessAddress2"><i class="red">＊</i>生产或经营地址：</span>
					            <div class="input-append">
					              <input class="span3" id="businessAddress3" value="${supplier.businessAddress } " type="text">
					              <div id="businessAddress1" class="b f18 fl ml10 red hand">√</div>
					              <div id="businessAddress" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="businessPostCode2"><i class="red">＊</i>邮编：</span>
					            <div class="input-append">
					              <input class="span3" id="businessPostCode3" value="${supplier.businessPostCode } " type="text">
					              <div id="businessPostCode1" class="b f18 fl ml10 red hand">√</div>
					              <div id="businessPostCode" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="overseasBranch2"><i class="red">＊</i>境外分支机构：</span>
					            <div class="input-append">
					              <input class="span3" id="overseasBranch3" value="${supplier.overseasBranch } " type="text">
					              <div id="overseasBranch1" class="b f18 fl ml10 red hand">√</div>
					              <div id="overseasBranch" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="branchCountry2"><i class="red">＊</i>国家：</span>
					            <div class="input-append">
					              <input class="span3" id="branchCountry3" value="${supplier.branchCountry } " type="text">
					              <div id="branchCountry1" class="b f18 fl ml10 red hand">√</div>
					              <div id="branchCountry" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="branchAddress2"><i class="red">＊</i>详细地址：</span>
					            <div class="input-append">
					              <input class="span3" id="branchAddress3" value="${supplier.branchAddress } " type="text">
					              <div id="branchAddress1" class="b f18 fl ml10 red hand">√</div>
					              <div id="branchAddress" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="" id="branchName2"><i class="red">＊</i>机构名称：</span>
					            <div class="input-append">
					              <input class="span3" id="branchName3" value="${supplier.branchName } " type="text">
					              <div id="branchName1" class="b f18 fl ml10 red hand">√</div>
					              <div id="branchName" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-12 p0 mt10"><span class="fl" id="branchBusinessScope2"><i class="red">＊</i>生产经营范围：</span>
                      <div class="col-md-9 mt5">
                        <div class="row">
                          <textarea class="text_area col-md-12" id="branchBusinessScope3" title="不超过800个字" >${supplier.branchBusinessScope }</textarea>
                          <div id="branchBusinessScope1" class="b f18 fl ml10 red hand">√</div>
                          <div id="branchBusinessScope" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
                        </div>
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
</div>
<%-- <form action="<%=basePath%>supplierAudit/auditReasons.html" id="save_reaeon" method="post">
  <input name="auditType">
  <input name="auditField">
  <input name="auditContent">
  <input name="suggest">
</form> --%>
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
