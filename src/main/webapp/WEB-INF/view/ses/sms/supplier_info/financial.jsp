<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]-->
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>财务信息</title>
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
<script type="text/javascript">
function reason(id){
  var supplierId=$("#supplierId").val();
  var auditField=$("#"+id).text()+"年财务信息"; //审批的字段名字
   layer.prompt({title: '请填写不通过理由', formType: 2}, function(text){
    $.ajax({
        url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
        type:"post",
        data:"&auditField="+auditField+"&suggest="+text+"&supplierId="+supplierId,
      });
      $("#"+id+"_hide").hide();
      layer.msg("审核不通过的理由是："+text);
    });
}

function reason1(year, ele){
  var supplierId=$("#supplierId").val();
  var value = $(ele).parents("li").find("span").text().replaceAll("＊","").replaceAll("：","");//审批的字段名字
  var auditField=year+"年"+value;
	  layer.prompt({title: '请填写不通过理由', formType: 2}, function(text){
	    $.ajax({
	        url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
	        type:"post",
	        data:"&auditField="+auditField+"&suggest="+text+"&supplierId="+supplierId,
	      });
	      $(ele).parent("div").find("div").eq(0).hide(); //隐藏勾
	      layer.msg("审核不通过的理由是："+text);
	    });
}

function tijiao(str){
  var action;
  if(str=="essential"){
     action ="${pageContext.request.contextPath}/supplierQuery/essential.html";
  }
  if(str=="financial"){
    action = "${pageContext.request.contextPath}/supplierQuery/financial.html";
  }
  if(str=="shareholder"){
    action = "${pageContext.request.contextPath}/supplierQuery/shareholder.html";
  }
  if(str=="materialProduction"){
    action = "${pageContext.request.contextPath}/supplierQuery/materialProduction.html";
  }
  if(str=="materialSales"){
    action = "${pageContext.request.contextPath}/supplierQuery/materialSales.html";
  }
  if(str=="engineering"){
    action = "${pageContext.request.contextPath}/supplierQuery/engineering.html";
  }
  if(str=="service"){
    action = "${pageContext.request.contextPath}/supplierQuery/serviceInformation.html";
  }
  if(str=="chengxin"){
    action = "${pageContext.request.contextPath}/supplierQuery/list.html";
  }
  if(str=="item"){
     action = "${pageContext.request.contextPath}/supplierQuery/item.html";
  }
  if(str=="item"){
     action = "${pageContext.request.contextPath}/supplierQuery/item.html";
  }
  $("#form_id").attr("action",action);
  $("#form_id").submit();
}
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
		   <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">供应商查看</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
  <!-- 项目戳开始 -->
  <div class="container clear margin-top-30">
    <!--详情开始-->
    <div class="container content height-350">
      <div class="row magazine-page">
        <div class="col-md-12 tab-v2 job-content">
          <div class="padding-top-10">
            <ul class="nav nav-tabs bgdd">
              <li class=""><a aria-expanded="fale" href="#tab-1" data-toggle="tab" onclick="tijiao('essential');">基本信息</a></li>
              <li class="active"><a aria-expanded="true" href="#tab-2" data-toggle="tab" onclick="tijiao('financial');">财务信息</a></li>
              <li class=""><a aria-expanded="fale" href="#tab-3" data-toggle="tab" onclick="tijiao('shareholder');">股东信息</a></li>
              <li class=""><a aria-expanded="fale" href="#tab-2" data-toggle="tab" onclick="tijiao('materialProduction');">物资-生产型专业信息</a></li>
              <li class=""><a aria-expanded="fale" href="#tab-3" data-toggle="tab" onclick="tijiao('materialSales');">物资-销售型专业信息</a></li>
              <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" onclick="tijiao('engineering');">工程-专业信息</a></li>
              <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" onclick="tijiao('service');">服务-专业信息</a></li>
              <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" onclick="tijiao('item');">品目信息</a></li>
              <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" >产品信息</a></li>
              <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" onclick="tijiao('chengxin');">诚信记录</a></li>
            </ul>
              <div class="tab-content padding-top-20" style="height:1400px;">
                <div class="tab-pane fade active in height-450" id="tab-1">
                  <form id="form_id" action="" method="post"  enctype="multipart/form-data">
                      <input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
                  </form>
                  <table class="table table-bordered table-condensed">
                   <thead>
                     <tr>
                       <th class="info">年份</th>
                       <th class="info">会计事务所名称</th>
                       <th class="info">事务所联系电话</th>
                       <th class="info">审计人姓名</th>
                       <th class="info">指标</th>
                       <th class="info">资产总额</th>
                       <th class="info">负债总额</th>
                       <th class="info">净资产总额</th>
                       <th class="info">营业收入</th>
                     </tr>
                   </thead>
                     <c:forEach items="${financial}" var="f" >
                       <tr>
                         <td class="tc" id="${f.id }">${f.year }</td>
                         <td class="tc">${f.name }</td>
                         <td class="tc">${f.telephone }</td>
                         <td class="tc">${f.auditors }</td>
                         <td class="tc">${f.quota }</td>
                         <td class="tc">${f.totalAssets }</td>
                         <td class="tc">${f.totalLiabilities }</td>
                         <td class="tc">${f.totalNetAssets}</td>
                         <td class="tc">${f.taking}</td>
                       </tr>
                     </c:forEach>
                  </table>
                  
                  <c:forEach items="${financial}" var="f" varStatus="vs">
                  <div class=" margin-bottom-0 fl">
	                  <h2 class="f16 jbxx1">
	                  <i>0${vs.index+1 }</i>${f.year }年
	                  </h2>
                    <ul class="list-unstyled list-flow">
                      <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>财务审计报告意见表：</span>
                        <div class="input-append">
                          <a class="span3" onclick="downloadFile('${f.auditOpinion}')">附件下载</a>
                        </div>
                      </li>
                      <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>资产负债表：</span>
                        <div class="input-append">
                          <a class="span3" onclick="downloadFile('${f.liabilitiesList}')">附件下载</a>
                        </div>
                      </li>
                      <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>利润表：</span>
                        <div class="input-append">
                          <a class="span3" onclick="downloadFile('${f.profitList}')">附件下载</a>
                        </div>
                      </li>
                      <li class="col-md-6 p0 "><span class="" ><i class="red">＊</i>现金流量表：</span>
                        <div class="input-append">
                          <a class="span3" onclick="downloadFile('${f.cashFlowStatement}')">附件下载</a>
                        </div>
                      </li>
                      <li class="col-md-6 p0 "><span class="" ><i class="red">＊</i>所有者权益变动表：</span>
                        <div class="input-append">
                          <a class="span3" onclick="downloadFile('${f.changeList}')">附件下载</a>
                        </div>
                      </li>
                    </ul>
                  </div>
                  </c:forEach>
                  
                </div>
              </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>
