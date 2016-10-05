<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
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
  var supplierId=$("#id").val();
  var id1=id+"1";
  var id2=id+"2";
  var id3=id+"3";
  var auditField=$("#"+id2+"").text().replaceAll("＊","").replaceAll("：",""); //审批的字段名字
  var  auditContent= document.getElementById(""+id3+"").value; //审批的字段内容
  var auditType=$("#essential").text(); //审核类型
  layer.prompt({title: '请填写不通过理由', formType: 2}, function(text){
    $.ajax({
        url:"<%=basePath%>supplierAudit/auditReasons.html",
        type:"post",
        data:"auditType="+auditType+"&auditField="+auditField+"&auditContent="+auditContent+"&suggest="+text+"&supplierId="+supplierId,
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
function tijiao(str){
  var action;
  if(str=="essential"){
     action ="<%=basePath%>supplierQuery/essential.html";
  }
  if(str=="financial"){
    action = "<%=basePath%>supplierQuery/financial.html";
  }
  if(str=="shareholder"){
    action = "<%=basePath%>supplierQuery/shareholder.html";
  }
  if(str=="materialProduction"){
    action = "<%=basePath%>supplierQuery/materialProduction.html";
  }
  if(str=="materialSales"){
    action = "<%=basePath%>supplierQuery/materialSales.html";
  }
  if(str=="engineering"){
    action = "<%=basePath%>supplierQuery/engineering.html";
  }
  if(str=="service"){
    action = "${pageContext.request.contextPath}/supplierQuery/serviceInformation.html";
  }
  if(str=="reasonsList"){
    action = "<%=basePath%>supplierQuery/reasonsList.html";
  }
  $("#form_id").attr("action",action);
  $("#form_id").submit();
}

function downloadFile(fileName){
	  fileName=encodeURI(fileName);
      fileName=encodeURI(fileName);
	  window.location.href="<%=basePath %>supplierQuery/downLoadFile.html?fileName="+fileName;
}
function fanhui(){
	  window.location.href="<%=basePath%>supplierQuery/findSupplierByPriovince.html?address="+encodeURI(encodeURI('${suppliers.address}'))+"&status=${status}";
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
		   <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">供应商查看</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
  <!-- 项目戳开始 -->
  <div class="container clear margin-top-30">
   <div class="container">
   <div class="col-md-12">
	<button class="btn btn-windows back" onclick="fanhui()">返回</button>	
	</div>
    </div>
  <!--详情开始-->
  <div class="container content height-350">
    <div class="row magazine-page">
      <div class="col-md-12 tab-v2 job-content">
        <div class="padding-top-10">
          <ul class="nav nav-tabs bgdd">
            <li class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" onclick="tijiao('essential');">基本信息</a></li>
          <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" onclick="tijiao('financial');">财务信息</a></li>
            <li class=""><a aria-expanded="fale" href="#tab-3" data-toggle="tab" onclick="tijiao('shareholder');">股东信息</a></li>
          <li class=""><a aria-expanded="fale" href="#tab-2" data-toggle="tab" onclick="tijiao('materialProduction');">物资-生产型专业信息</a></li>
            <li class=""><a aria-expanded="fale" href="#tab-3" data-toggle="tab" onclick="tijiao('materialSales');">物资-销售型专业信息</a></li>
            <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" onclick="tijiao('engineering');">工程-专业信息</a></li>
            <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" onclick="tijiao('service');">服务-专业信息</a></li>
            <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" >品目信息</a></li>
            <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" >产品信息</a></li>
            <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" onclick="tijiao('reasonsList');">诚信记录</a></li>
          </ul>
            <div class="tab-content padding-top-20" style="height:1500px;">
              <div class="tab-pane fade active in height-450" id="tab-1">
                <div class=" margin-bottom-0">
                  <form id="form_id" action="" method="post"  enctype="multipart/form-data">
                    <input name="supplierId" id="id" value="${suppliers.id }" type="hidden">
                  </form>                 
                  <h2 class="f16 jbxx1">
                  <i>01</i>企业基本信息
                  </h2>
                  <ul class="list-unstyled list-flow">
                    <li class="col-md-6 p0 "><span class="" id="supplierName2"><i class="red">＊</i>供应商名称：</span>
                      <div class="input-append">
                        <input class="span3" id="supplierName3" readonly="readonly" value="${suppliers.supplierName } " type="text">
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="website2"><i class="red">＊</i>公司网址：</span>
                      <div class="input-append">
                        <input class="span3" id="website3" value="${suppliers.website } "  type="text">
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="foundDate2"><i class="red">＊</i>成立日期：</span>
                      <div class="input-append">
                        <input class="span3" id="foundDate3" readonly="readonly" value="<fmt:formatDate value='${suppliers.foundDate}' pattern='yyyy-MM-dd'/>" type="text">
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="businessType2"><i class="red">＊</i>营业执照登记类型：</span>
                      <div class="input-append">
                        <input class="span3" id="businessType3" readonly="readonly" value="${suppliers.businessType } " type="text">
                      </div>
                    </li>
                    <li class="col-md-6 p0"><span class="" id="address2"><i class="red">＊</i>地址：</span>
                      <div class="input-append">
                        <input class="span3" id="address3" readonly="readonly" value="${suppliers.address } " type="text">
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="bankName2"><i class="red">＊</i>开户行名称：</span>
                      <div class="input-append">
                        <input class="span3" id="bankName3" readonly="readonly" value="${suppliers.bankName } "  type="text">
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="bankAccount2"><i class="red">＊</i>开户行账户：</span>
                      <div class="input-append">
                        <input class="span3" id="bankAccount3" readonly="readonly" value="${suppliers.bankAccount } " type="text">
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="postCode2"><i class="red">＊</i>邮编：</span>
                      <div class="input-append">
                        <input class="span3" id="postCode3" readonly="readonly" value="${suppliers.postCode }" type="text">
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>近三个月完税凭证：</span>
                      <div class="input-append">
                        <a class="span3" onclick="downloadFile('${suppliers.taxCert}')" >附件下载</a>
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>近三年银行账单：</span>
                      <div class="input-append">
                        <a class="span3" onclick="downloadFile('${suppliers.billCert}')" >附件下载</a>
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>近三个月保险凭证：</span>
                      <div class="input-append">
                        <a class="span3" onclick="downloadFile('${suppliers.securityCert}')" >附件下载</a>
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>近三年违法记录：</span>
                      <div class="input-append">
                        <a class="span3" onclick="downloadFile('${suppliers.breachCert}')" >附件下载</a>
                      </div>
                    </li>
                  </ul>
                </div>
                
                <div class=" margin-bottom-0 fl">
                  <h2 class="f16 jbxx1">
                  <i>02</i>法人代表人信息
                  </h2>
                  <ul class="list-unstyled list-flow">
                    <li class="col-md-6 p0 "><span class="" id="legalName2"><i class="red">＊</i>姓名：</span>
                      <div class="input-append">
                        <input class="span3" id="legalName3" readonly="readonly" value="${suppliers.legalName } " type="text">
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="legaIdCard2"><i class="red">＊</i>身份证号：</span>
                      <div class="input-append">
                        <input class="span3" id="legaIdCard3" readonly="readonly" value="${suppliers.legalIdCard } "  type="text">
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="legalTelephone2"><i class="red">＊</i>固定电话：</span>
                      <div class="input-append">
                        <input class="span3" id="legalTelephone3" readonly="readonly" value="${suppliers.legalTelephone } " type="text">
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="legalMobile2"><i class="red">＊</i>手机：</span>
                      <div class="input-append">
                        <input class="span3" id="legalMobile3" readonly="readonly" value="${suppliers.legalMobile } " type="text">
                      </div>
                    </li>
                  </ul>
                </div>
                
                <div class=" margin-bottom-0 fl">
                  <h2 class="f16 jbxx1">
                  <i>03</i>联系人信息
                  </h2>
                  <ul class="list-unstyled list-flow">
                    <li class="col-md-6 p0 "><span class="" id="contactName2"><i class="red">＊</i>姓名：</span>
                      <div class="input-append">
                        <input class="span3" id="contactName3" readonly="readonly" value="${suppliers.contactName } " type="text">
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="contactFax2"><i class="red">＊</i>传真：</span>
                      <div class="input-append">
                        <input class="span3" id="contactFax3" readonly="readonly" value="${suppliers.contactFax } "  type="text">
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="contactTelephone1"><i class="red">＊</i>固定电话：</span>
                      <div class="input-append">
                        <input class="span3" id="contactTelephone3" readonly="readonly" value="${suppliers.contactTelephone } " type="text">
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="contactMobile2"><i class="red">＊</i>手机：</span>
                      <div class="input-append">
                        <input class="span3" id="contactMobile3" readonly="readonly" value="${suppliers.contactMobile } " type="text">
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="contactEmail2"><i class="red">＊</i>邮箱：</span>
                      <div class="input-append">
                        <input class="span3" id="contactEmail3" readonly="readonly" value="${suppliers.contactEmail } " type="text">
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="contactAddress2"><i class="red">＊</i>地址：</span>
                      <div class="input-append">
                        <input class="span3" id="contactAddress3" readonly="readonly" value="${suppliers.contactAddress } " type="text">
                      </div>
                    </li>
                  </ul>
                </div>
                
                <div class=" margin-bottom-0 fl">
                  <h2 class="f16 jbxx1">
                  <i>04</i>营业执照
                  </h2>
                  <ul class="list-unstyled list-flow">
                    <li class="col-md-6 p0 "><span class="" id="creditCode2"><i class="red">＊</i>统一社会信用代码：</span>
                      <div class="input-append">
                        <input class="span3" id="creditCode3" readonly="readonly" value="${suppliers.creditCode } " type="text">
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="registAuthority2"><i class="red">＊</i>登记机关：</span>
                      <div class="input-append">
                        <input class="span3" id="registAuthority3" readonly="readonly" value="${suppliers.registAuthority } "  type="text">
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="registFund2"><i class="red">＊</i>注册资本：</span>
                      <div class="input-append">
                        <input class="span3" id="registFund3" readonly="readonly" value="${suppliers.registFund } " type="text">
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="businessEndDate2"><i class="red">＊</i>营业期限：</span>
                      <div class="input-append">
                        <input class="span3" id="businessEndDate3" value="<fmt:formatDate value="${suppliers.businessStartDate}" pattern="yyyy-MM-dd" />至 <fmt:formatDate value="${suppliers.businessEndDate}" pattern="yyyy-MM-dd" />" type="text"/>
                      </div>
                    </li>
                    <li class="col-md-12 p0 mt10"><span class="fl" id="businessScope2"><i class="red">＊</i>经营范围：</span>
                      <div class="col-md-9 mt5">
                        <div class="row">
                          <textarea class="text_area col-md-12" readonly="readonly" id="businessScope3">${suppliers.businessScope }</textarea>
                        </div>
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="fl" id="businessAddress2"><i class="red">＊</i>生产或经营地址：</span>
                      <div class="input-append">
                        <input class="span3" id="businessAddress3" readonly="readonly" value="${suppliers.businessAddress } " type="text">
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="businessPostCode2"><i class="red">＊</i>邮编：</span>
                      <div class="input-append">
                        <input class="span3" id="businessPostCode3" readonly="readonly" value="${suppliers.businessPostCode } " type="text">
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="overseasBranch2"><i class="red">＊</i>境外分支机构：</span>
                      <div class="input-append">
                        <input class="span3" id="overseasBranch3" readonly="readonly" value="${suppliers.overseasBranch } " type="text">
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="branchCountry2"><i class="red">＊</i>国家：</span>
                      <div class="input-append">
                        <input class="span3" id="branchCountry3" readonly="readonly" value="${suppliers.branchCountry } " type="text">
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="branchAddress2"><i class="red">＊</i>详细地址：</span>
                      <div class="input-append">
                        <input class="span3" id="branchAddress3" readonly="readonly" value="${suppliers.branchAddress } " type="text">
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="branchName2"><i class="red">＊</i>机构名称：</span>
                      <div class="input-append">
                        <input class="span3" id="branchName3" readonly="readonly" value="${suppliers.branchName } " type="text">
                      </div>
                    </li>
                    <li class="col-md-12 p0 mt10"><span class="fl" id="branchBusinessScope2"><i class="red">＊</i>生产经营范围：</span>
                      <div class="col-md-9 mt5">
                        <div class="row">
                          <textarea class="text_area col-md-12" readonly="readonly" id="branchBusinessScope3">${suppliers.branchBusinessScope }</textarea>
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
</body>
</html>
