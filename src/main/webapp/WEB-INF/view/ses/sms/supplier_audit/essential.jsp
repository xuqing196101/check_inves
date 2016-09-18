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
alert(id);
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
				    <li class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" onclick="location='<%=basePath%>supplierAudit/essential.html'">基本信息</a></li>
            <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" onclick="location='<%=basePath%>supplierAudit/financial.html'">财务信息</a></li>
				    <li class=""><a aria-expanded="fale" href="#tab-3" data-toggle="tab" onclick="location='<%=basePath%>supplierAudit/shareholder.html'">股东信息</a></li>
            <li class=""><a aria-expanded="fale" href="#tab-2" data-toggle="tab" onclick="location='<%=basePath%>supplierAudit/materialProduction.html'">物资-生产型专业信息</a></li>
				    <li class=""><a aria-expanded="fale" href="#tab-3" data-toggle="tab" onclick="location='<%=basePath%>supplierAudit/materialSales.html'">物资-销售型专业信息</a></li>
				    <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" onclick="location='<%=basePath%>supplierAudit/engineering.html'">工程-专业信息</a></li>
				    <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" >服务-专业信息</a></li>
				    <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" >品目信息</a></li>
				    <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" >产品信息</a></li>
				    <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" >审核汇总</a></li>
          </ul>
            <div class="tab-content padding-top-20" style="height:1500px;">
              <div class="tab-pane fade active in height-450" id="tab-1">
                <div class=" margin-bottom-0">
					        <h2 class="f16 jbxx">
					        <i>01</i>企业基本信息
					        </h2>
					        <ul class="list-unstyled list-flow">
					          <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>供应商名称：</span>
					            <div class="input-append">
					              <input class="span3" id="supplierName" name="supplierName" value="${supplier.supplierName } " type="text">
					              <div id="nameReason1" class="b f18 fl ml10 red hand">√</div>
					              <div id="nameReason" onclick="reason(this.id)" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>公司网址：</span>
					            <div class="input-append">
					              <input class="span3" id="supplierTepe" name="supplierTepe" value="${supplier.website } "  type="text">
					              <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>成立日期：</span>
					            <div class="input-append">
					              <input class="span3" id="supplierChinesrName" name="supplierChinesrName" value="<fmt:formatDate type='date' value='${supplier.foundDate }' dateStyle="default" pattern="yyyy-MM-dd"/>" type="text">
					              <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>营业执照登记类型：</span>
					            <div class="input-append">
					              <input class="span3" id="legalName" name="legalName" value="${supplier.businessType } " type="text">
					              <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0"><span class=""><i class="red">＊</i>地址：</span>
					            <div class="input-append">
					              <input class="span3" id="legalName" name="legalName" value="${supplier.address } " type="text">
					              <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>开户行名称：</span>
					            <div class="input-append">
					              <input class="span3" id="supplierZipCode" name="supplierZipCode" value="${supplier.bankName } "  type="text">
					              <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>开户行账户：</span>
					            <div class="input-append">
					              <input class="span3" id="productType" name="productType" value="${supplier.bankAccount } " type="text">
					              <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>邮编：</span>
					            <div class="input-append">
					              <input class="span3" id="majorProduct" name="majorProduct" value="${supplier.postCode }" type="text">
					              <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>近三个月完税凭证：</span>
					            <div class="input-append">
					              <a >附件下载</a>
					              <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
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
					          <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>姓名：</span>
					            <div class="input-append">
					              <input class="span3" id="supplierName" name="supplierName" value="${supplier.legalName } " type="text">
					              <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>身份证号：</span>
					            <div class="input-append">
					              <input class="span3" id="supplierTepe" name="supplierTepe" value="${supplier.legaIdCard } "  type="text">
					              <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>固定电话：</span>
					            <div class="input-append">
					              <input class="span3" id="supplierChinesrName" name="supplierChinesrName" value="${supplier.legalTelephone } " type="text">
					              <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>手机：</span>
					            <div class="input-append">
					              <input class="span3" id="legalName" name="legalName" value="${supplier.legalMobile } " type="text">
					              <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					        </ul>
					      </div>
					      
					      <div class=" margin-bottom-0">
					        <h2 class="f16 jbxx">
					        <i>03</i>联系人信息
					        </h2>
					        <ul class="list-unstyled list-flow">
					          <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>姓名：</span>
					            <div class="input-append">
					              <input class="span3" id="supplierName" name="supplierName" value="${supplier.contactName } " type="text">
					              <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>传真：</span>
					            <div class="input-append">
					              <input class="span3" id="supplierTepe" name="supplierTepe" value="${supplier.contactFax } "  type="text">
					              <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>固定电话：</span>
					            <div class="input-append">
					              <input class="span3" id="supplierChinesrName" name="supplierChinesrName" value="${supplier.contactTelephone } " type="text">
					              <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>手机：</span>
					            <div class="input-append">
					              <input class="span3" id="legalName" name="legalName" value="${supplier.contactMobile } " type="text">
					              <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>邮箱：</span>
					            <div class="input-append">
					              <input class="span3" id="legalName" name="legalName" value="${supplier.contactEmail } " type="text">
					              <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>地址：</span>
					            <div class="input-append">
					              <input class="span3" id="legalName" name="legalName" value="${supplier.contactAddress } " type="text">
					              <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					        </ul>
					      </div>
					      
					      <div class=" margin-bottom-0">
					        <h2 class="f16 jbxx">
					        <i>04</i>营业执照
					        </h2>
					        <ul class="list-unstyled list-flow">
					          <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>统一社会信用代码：</span>
					            <div class="input-append">
					              <input class="span3" id="supplierName" name="supplierName" value="${supplier.creditCode } " type="text">
					              <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>登记机关：</span>
					            <div class="input-append">
					              <input class="span3" id="supplierTepe" name="supplierTepe" value="${supplier.registAuthority } "  type="text">
					              <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>注册资本：</span>
					            <div class="input-append">
					              <input class="span3" id="supplierChinesrName" name="supplierChinesrName" value="${supplier.registFund } " type="text">
					              <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>营业期限：</span>
					            <div class="input-append">
					              <input class="span3" id="legalName" name="legalName" value="${supplier.businessStartDate }至 ${supplier.businessEndDate} " type="text">
					              <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-12 p0 mt10"><span class="fl"><i class="red">＊</i>经营范围：</span>
					            <div class="col-md-9 mt5">
					              <div class="row">
					                <textarea class="text_area col-md-12" id="supplyLevel" name="supplyLevel"  title="不超过800个字" >${supplier.businessScope }</textarea>
					                <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
					              </div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class="fl"><i class="red">＊</i>生产或经营地址：</span>
					            <div class="input-append">
					              <input class="span3" id="legalName" name="legalName" value="${supplier.businessAddress } " type="text">
					              <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>邮编：</span>
					            <div class="input-append">
					              <input class="span3" id="legalName" name="legalName" value="${supplier.businessPostCode } " type="text">
					              <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>境外分支机构：</span>
					            <div class="input-append">
					              <input class="span3" id="legalName" name="legalName" value="${supplier.overseasBranch } " type="text">
					              <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>国家：</span>
					            <div class="input-append">
					              <input class="span3" id="legalName" name="legalName" value="${supplier.branchCountry } " type="text">
					              <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>详细地址：</span>
					            <div class="input-append">
					              <input class="span3" id="legalName" name="legalName" value="${supplier.branchAddress } " type="text">
					              <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>机构名称：</span>
					            <div class="input-append">
					              <input class="span3" id="legalName" name="legalName" value="${supplier.branchName } " type="text">
					              <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
					            </div>
					          </li>
					          <li class="col-md-12 p0 mt10"><span class="fl"><i class="red">＊</i>生产经营范围：</span>
                      <div class="col-md-9 mt5">
                        <div class="row">
                          <textarea class="text_area col-md-12" id="supplyLevel" name="supplyLevel"  title="不超过800个字" >${supplier.branchBusinessScope }</textarea>
                          <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
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
