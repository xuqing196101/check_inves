<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]-->
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>申请表</title>
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
<script type="text/javascript">
function reason1(ele){
  var supplierId=$("#supplierId").val();
  var auditField = $(ele).parents("li").find("span").text().replaceAll("：","");//审批的字段名字
    layer.prompt({title: '请填写不通过理由', formType: 2}, function(text){
      $.ajax({
          url:"<%=basePath%>supplierAudit/auditReasons.html",
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
     action ="<%=basePath%>supplierAudit/essential.html";
  }
  if(str=="financial"){
    action = "<%=basePath%>supplierAudit/financial.html";
  }
  if(str=="shareholder"){
    action = "<%=basePath%>supplierAudit/shareholder.html";
  }
  if(str=="materialProduction"){
    action = "<%=basePath%>supplierAudit/materialProduction.html";
  }
  if(str=="materialSales"){
    action = "<%=basePath%>supplierAudit/materialSales.html";
  }
  if(str=="engineering"){
    action = "<%=basePath%>supplierAudit/engineering.html";
  }
  if(str=="service"){
    action = "${pageContext.request.contextPath}/supplierAudit/serviceInformation.html";
  }
  if(str=="items"){
  action = "${pageContext.request.contextPath}/supplierAudit/items.html";
  }
  if(str=="applicationFrom"){
    action = "${pageContext.request.contextPath}/supplierAudit/applicationForm.html";
  }
  if(str=="reasonsList"){
    action = "<%=basePath%>supplierAudit/reasonsList.html";
  }
  $("#form_id").attr("action",action);
  $("#form_id").submit();
}

//文件下載
  function downloadFile(fileName) {
    $("input[name='fileName']").val(fileName);
    $("#download_form_id").submit();
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
              <li class=""><a aria-expanded="fale" href="#tab-1" data-toggle="tab" onclick="tijiao('essential');">基本信息</a></li>
              <li class=""><a aria-expanded="fale" href="#tab-2" data-toggle="tab" onclick="tijiao('financial');">财务信息</a></li>
              <li class=""><a aria-expanded="fale" href="#tab-3" data-toggle="tab" onclick="tijiao('shareholder');">股东信息</a></li>
              <c:if test="${fn:contains(supplierTypeNames, '生产型')}">
	            <li class=""><a aria-expanded="fale" href="#tab-2" data-toggle="tab" onclick="tijiao('materialProduction');">物资-生产型专业信息</a></li>
	            </c:if>
	            <c:if test="${fn:contains(supplierTypeNames, '销售型')}">
	            <li class=""><a aria-expanded="fale" href="#tab-3" data-toggle="tab" onclick="tijiao('materialSales');">物资-销售型专业信息</a></li>
	            </c:if>
	            <c:if test="${fn:contains(supplierTypeNames, '工程')}">
	            <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" onclick="tijiao('engineering');">工程-专业信息</a></li>
	            </c:if>
	            <c:if test="${fn:contains(supplierTypeNames, '服务')}">
	            <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" onclick="tijiao('service');">服务-专业信息</a></li>
	            </c:if>
              <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" onclick="tijiao('items');">品目信息</a></li>
              <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" >产品信息</a></li>
              <li class="active"><a aria-expanded="ture" href="#tab-2" data-toggle="tab" onclick="tijiao('applicationFrom');">申请表</a></li>
              <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" onclick="tijiao('reasonsList');">审核汇总</a></li>
            </ul>
              <div class="tab-content padding-top-20">
                <div class="tab-pane fade active in height-450" id="tab-1">
                  <form id="form_id" action="" method="post"  enctype="multipart/form-data">
                      <input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
                  </form>
                  
                <%--   <div class=" margin-bottom-0 fl">
	                  <h2 class="f16 jbxx1">
	                  <i>01</i>申请表:&nbsp;&nbsp;<span>${applicationForm.supplierRegList }
	                   <a class="b f18 ml10 red hand">√</a>
                     <a class="b f18 ml10 hand" onclick="reason('${applicationForm.id }'_2);">×</a>
	                  </span>
	                  </h2>
                  </div>
                
                 <div class=" margin-bottom-0 fl">
	                  <h2 class="f16 jbxx1" id="d">
	                   <i>02</i>供应商承若书:&nbsp;&nbsp;<span>${applicationForm.supplierPledge }</span>
	                   <a class="b f18 ml10 red hand">√</a>
                     <a class="b f18 ml10 hand" onclick="reason('${applicationForm.id }'_1);">×</a>
	                  </h2>  
                </div> --%>
                <ul class="list-unstyled list-flow">
                  <li class="col-md-6 p0 "><span class="">军队供应商分级方法：</span>
                    <div class="input-append">
                      <c:if test="${applicationForm.supplierLevel != null}">
                      <a class="span3 red" href="javascript:void(0)" onclick="downloadFile('${applicationForm.supplierLevel}')" >下载附件</a>
                      </c:if>
                      <c:if test="${applicationForm.supplierLevel == null}">
                        <a class="span3 red">无附件下载</a>
                      </c:if>
                      <div  class="b f18 ml10 red fl hand">√</div>
                      <div onclick="reason1(this);" class="b f18 ml10 fl hand">×</div>
                    </div>
                  </li>
                  <li class="col-md-6 p0 "><span class="">军队供应商承诺书：</span>
                    <div class="input-append">
                      <c:if test="${applicationForm.supplierPledge !=null}">
                        <a class="span3 red" href="javascript:void(0)" onclick="downloadFile('${applicationForm.supplierPledge}')">下载附件</a>
                      </c:if>
                      <c:if test="${applicationForm.supplierPledge == null}">
                        <a class="span3 red">无附件下载</a>
                      </c:if>
                      <div  class="b f18 ml10 red fl hand">√</div>
                      <div onclick="reason1(this);" class="b f18 ml10 fl hand">×</div>
                    </div>
                  </li>
                  <li class="col-md-6 p0 "><span class="">军队供应商入库申请表：</span>
                    <div class="input-append">
                      <c:if test="${applicationForm.supplierRegList !=null}">
                        <a class="span3 red" href="javascript:void(0)" onclick="downloadFile('${applicationForm.supplierRegList}')">下载附件</a>
                      </c:if>
                      <c:if test="${applicationForm.supplierRegList == null}">
                        <a class="span3 red">无附件下载</a>
                      </c:if>
                      <div  class="b f18 ml10 red fl hand">√</div>
                      <div onclick="reason1(this);" class="b f18 ml10 fl hand">×</div>
                    </div>
                  </li>
                  <li class="col-md-6 p0 "><span class="">军队供应商实地考察记录表：</span>
                    <div class="input-append">
                      <c:if test="${applicationForm.supplierReviewList !=null}">
                        <a class="span3 red" href="javascript:void(0)" onclick="downloadFile('${applicationForm.supplierReviewList}')">下载附件</a>
                      </c:if>
                      <c:if test="${applicationForm.supplierReviewList == null}">
                        <a class="span3 red">无附件下载</a>
                      </c:if>
                      <div  class="b f18 ml10 red fl hand">√</div>
                      <div onclick="reason1(this);" class="b f18 ml10 fl hand">×</div>
                    </div>
                  </li>
                  <li class="col-md-6 p0 "><span class="">军队供应商实地考察廉政意见函：</span>
                    <div class="input-append">
                      <c:if test="${applicationForm.supplierInspectList !=null}">
                        <a class="span3 red" href="javascript:void(0)" onclick="downloadFile('${applicationForm.supplierInspectList}')">下载附件</a>
                      </c:if>
                      <c:if test="${applicationForm.supplierInspectList == null}">
                        <a class="span3 red">无附件下载</a>
                      </c:if>
                      <div  class="b f18 ml10 red fl hand">√</div>
                      <div onclick="reason1(this);" class="b f18 ml10 fl hand">×</div>
                    </div>
                  </li>
                  <li class="col-md-6 p0 "><span class="">军队供应商注册变更申请表：</span>
                    <div class="input-append">
                      <c:if test="${applicationForm.supplierChangeList != null}">
                        <a class="span3 red" href="javascript:void(0)" onclick="downloadFile('${applicationForm.supplierChangeList}')">下载附件</a>
                      </c:if>
                      <c:if test="${applicationForm.supplierChangeList == null}">
                        <a class="span3 red">无附件下载</a>
                      </c:if>
                      <div  class="b f18 ml10 red fl hand">√</div>
                      <div onclick="reason1(this);" class="b f18 ml10 fl hand">×</div>
                    </div>
                  </li>
                  <li class="col-md-6 p0 "><span class="">军队供应商退库申请表：</span>
                    <div class="input-append">
                      <c:if test="${applicationForm.supplierExitList != null}">
                        <a class="span3 red" href="javascript:void(0)" onclick="downloadFile('${applicationForm.supplierExitList}')">下载附件</a>
                      </c:if>
                      <c:if test="${applicationForm.supplierExitList == null}">
                        <a class="span3 red">无附件下载</a>
                      </c:if>
                      <div  class="b f18 ml10 red fl hand">√</div>
                      <div onclick="reason1(this);" class="b f18 ml10 fl hand">×</div>
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
  <form target="_blank" id="download_form_id" action="${pageContext.request.contextPath}/supplierAudit/download.html" method="post">
   <input type="hidden" name="fileName" />
  </form>
  <jsp:include page="../../../../../index_bottom.jsp"></jsp:include>
</body>
</html>
