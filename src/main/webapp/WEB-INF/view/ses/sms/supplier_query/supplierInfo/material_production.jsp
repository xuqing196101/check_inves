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
<title>物资-生产型专业信息</title>
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
<script type="text/javascript">
function reason(id){
  var supplierId=$("#supplierId").val();
  var auditField=$("#"+id).text()+"生产资质证书信息"; //审批的字段名字
   layer.prompt({title: '请填写不通过理由', formType: 2}, function(text){
    $.ajax({
        url:"<%=basePath%>supplierAudit/auditReasons.html",
        type:"post",
        data:"&auditField="+auditField+"&suggest="+text+"&supplierId="+supplierId,
      });
        $("#"+id+"_hide").hide();
        layer.msg("审核不通过的理由是："+text);
    });
}


function reason1(id){
  var supplierId=$("#supplierId").val();
  var id2=id+"2";
  var id1=id+"1";
  var auditField=$("#"+id2+"").text().replaceAll("＊","").replaceAll("：",""); //审批的字段名字
  layer.prompt({title: '请填写不通过理由', formType: 2}, function(text){
    $.ajax({
        url:"<%=basePath%>supplierAudit/auditReasons.html",
        type:"post",
        data:"&auditField="+auditField+"&suggest="+text+"&supplierId="+supplierId,
      });
     layer.msg("审核不通过的理由是："+text);
     $("#"+id1+"").hide();
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
  if(str=="item"){
     action = "<%=basePath%>supplierQuery/item.html";
  }
  $("#form_id").attr("action",action);
  $("#form_id").submit();
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
              <li class=""><a aria-expanded="fale" href="#tab-2" data-toggle="tab" onclick="tijiao('financial');">财务信息</a></li>
              <li class=""><a aria-expanded="fale" href="#tab-3" data-toggle="tab" onclick="tijiao('shareholder');">股东信息</a></li>
              <li class="active"><a aria-expanded="true" href="#tab-2" data-toggle="tab" onclick="tijiao('materialProduction');">物资-生产型专业信息</a></li>
              <li class=""><a aria-expanded="fale" href="#tab-3" data-toggle="tab" onclick="tijiao('materialSales');">物资-销售型专业信息</a></li>
              <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" onclick="tijiao('engineering');">工程-专业信息</a></li>
              <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" onclick="tijiao('service');">服务-专业信息</a></li>
              <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" >品目信息</a></li>
              <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" >产品信息</a></li>
              <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" onclick="tijiao('reasonsList');">诚信记录</a></li>
            </ul>
              <div class="tab-content padding-top-20" style="height:1500px;">
                <div class="tab-pane fade active in height-450" id="tab-1">
                  <form id="form_id" action="" method="post"  enctype="multipart/form-data">
                    <input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
                  </form>
                  <h2 class="f16 jbxx1">
                  <i>01</i>供应商资质证书
                  </h2>
                  <table class="table table-bordered table-condensed">
							      <thead>
							        <tr>
							          <th class="info">资质证书名称</th>
							          <th class="info">资质等级</th>
							          <th class="info">发证机关</th>
							          <th class="info">有效期(起止时间)</th>
							          <th class="info">是否年检</th>
							          <th class="info">附件上传</th>
							        </tr>
							        </thead>
							        <c:forEach items="${materialProduction}" var="m" >
							          <tr>
							            <td class="tc" id="${m.id}">${m.name }</td>
							            <td class="tc">${m.levelCert}</td>
							            <td class="tc">${m.licenceAuthorith }</td>
							            <td class="tc">
								            <fmt:formatDate value="${m.expStartDate }" pattern='yyyy-MM-dd'/>  至  
								            <fmt:formatDate value="${m.expEndDate }" pattern='yyyy-MM-dd'/>
								          </td>
							            <td class="tc">
							             <c:if test="${m.mot==0 }">否</c:if>
							             <c:if test="${m.mot==1 }">是</c:if>
							            </td>
							            <td class="tc">${m.attach }</td>
							          </tr>
							        </c:forEach>
							    </table>
							    
							    <div class=" margin-bottom-0">
							    <h2 class="f16 jbxx1">
                  <i>02</i>组织结构和人员
                  </h2>
                    <ul class="list-unstyled list-flow">
                      <li class="col-md-6 p0"><span class="" id="orgName2"><i class="red">＊</i>组织机构：</span>
                        <div class="input-append">
                          <input class="span3" type="text" value="${supplierMatPros.orgName }" />
                        </div>
                      </li>
                      <li class="col-md-6 p0"><span class="" id="totalPerson2"><i class="red">＊</i>人员总数：</span>
                        <div class="input-append">
                          <input class="span3" type="text" value="${supplierMatPros.totalPerson }" />
                        </div>
                      </li>
                      <li class="col-md-6 p0"><span class="" id="totalMange2"><i class="red">＊</i>管理人员：</span>
                        <div class="input-append">
                          <input class="span3" type="text"  value="${supplierMatPros.totalMange }"/>
                        </div>
                      </li>
                      <li class="col-md-6 p0"><span class="" id="totalTech2"><i class="red">＊</i>技术人员：</span>
                        <div class="input-append">
                          <input class="span3" type="text"  value="${supplierMatPros.totalTech }"/>
                        </div>
                      </li>
                      <li class="col-md-6 p0"><span class="" id="totalWorker2"><i class="red">＊</i>工人(职员)：</span>
                        <div class="input-append">
                          <input class="span3" type="text" value="${supplierMatPros.totalWorker }"/>
                        </div>
                      </li>
                    </ul>
                  </div>
                  
                  <div class=" margin-bottom-0 fl">
                  <h2 class="f16 jbxx1">
                  <i>03</i>产品研发能力
                  </h2>
                    <ul class="list-unstyled list-flow">
                      <li class="col-md-6 p0"><span class="" id="scaleTech2"><i class="red">＊</i>技术人员比例：</span>
                        <div class="input-append">
                          <input class="span3" type="text" value="${supplierMatPros.scaleTech }" />
                        </div>
                      </li>
                      <li class="col-md-6 p0"><span class="" id="scaleHeightTech2"><i class="red">＊</i>高级技术人员比例：</span>
                        <div class="input-append">
                          <input class="span3" type="text" value="${supplierMatPros.scaleHeightTech }" />
                        </div>
                      </li>
                      <li class="col-md-6 p0"><span class="" id=researchName2><i class="red">＊</i>研发部门名称：</span>
                        <div class="input-append">
                          <input class="span3" type="text"  value="${supplierMatPros.researchName }"/>
                        </div>
                      </li>
                      <li class="col-md-6 p0"><span class="" id="totalResearch2"><i class="red">＊</i>研发部门人数：</span>
                        <div class="input-append">
                          <input class="span3" type="text"  value="${supplierMatPros.totalResearch }"/>
                        </div>
                      </li>
                      <li class="col-md-6 p0"><span class="" id="researchLead2"><i class="red">＊</i>研发部门负责人：</span>
                        <div class="input-append">
                          <input class="span3" type="text" value="${supplierMatPros.researchLead }"/>
                        </div>
                      </li>
                      <li class="col-md-12 p0 mt10"><span class="fl" id="countryPro2"><i class="red">＊</i>承担国家军队科研项目：</span>
	                      <div class="col-md-9 mt5">
	                        <div class="row">
	                          <textarea class="text_area col-md-12">${supplierMatPros.countryPro }</textarea>
	                        </div>
	                      </div>
                      </li>
                      <li class="col-md-12 p0 mt10"><span class="fl" id="countryReward2"><i class="red">＊</i>获得国家军队科技项目：</span>
	                      <div class="col-md-9 mt5">
	                        <div class="row">
	                          <textarea class="text_area col-md-12">${supplierMatPros.countryReward }</textarea>
	                        </div>
	                      </div>
                      </li>
                    </ul>
                  </div>
                  
                  <div class=" margin-bottom-0 fl">
                  <h2 class="f16 jbxx1">
                  <i>04</i>供应商生产能力
                  </h2>
                    <ul class="list-unstyled list-flow">
                      <li class="col-md-6 p0"><span class="" id="totalBeltline2"><i class="red">＊</i>生产线名称数量：</span>
                        <div class="input-append">
                          <input class="span3" type="text" value="${supplierMatPros.totalBeltline }" />
                        </div>
                      </li>
                      <li class="col-md-6 p0"><span class="" id="totalDevice2"><i class="red">＊</i>生产设备名称数量：</span>
                        <div class="input-append">
                          <input class="span3" type="text" value="${supplierMatPros.totalDevice }" />
                        </div>
                      </li>
                    </ul>
                    
                  </div>
                  <div class=" margin-bottom-0 fl">
                  <h2 class="f16 jbxx1">
                  <i>05</i>物资生产型供应商质量检测登记
                  </h2>
                    <ul class="list-unstyled list-flow">
                      <li class="col-md-6 p0"><span class="" id="qcName2"><i class="red">＊</i>质量检测部门：</span>
                        <div class="input-append">
                          <input class="span3" type="text" value="${supplierMatPros.qcName }" />
                        </div>
                      </li>
                      <li class="col-md-6 p0"><span class="" id="totalQc2"><i class="red">＊</i>质量检测人数：</span>
                        <div class="input-append">
                          <input class="span3" type="text" value="${supplierMatPros.totalQc }" />
                        </div>
                      </li>
                      <li class="col-md-6 p0"><span class="" id="qcLead2"><i class="red">＊</i>质检部门负责人：</span>
                        <div class="input-append">
                          <input class="span3" type="text" value="${supplierMatPros.qcLead }" />
                        </div>
                      </li>
                      <li class="col-md-12 p0 mt10"><span class="fl" id="qcDevice2"><i class="red">＊</i>质量检测设备名称：</span>
                        <div class="col-md-9 mt5">
                          <div class="row">
                            <textarea class="text_area col-md-12">${supplierMatPros.qcDevice }</textarea>
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
