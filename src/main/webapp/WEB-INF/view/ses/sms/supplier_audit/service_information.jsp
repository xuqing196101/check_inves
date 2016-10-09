<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]-->
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>服务-专业信息</title>
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
  var auditField=$("#"+id).text()+"服务资质证书信息"; //审批的字段名字
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

//只读
$(function() {
    $(":input").each(function() {
      $(this).attr("readonly", "readonly");
    });
  });

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
    action = "<%=basePath%>supplierAudit/serviceInformation.html";
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
	            <li class="active"><a aria-expanded="ture" href="#tab-3" data-toggle="tab" onclick="tijiao('service');">服务-专业信息</a></li>
	            </c:if>
              <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" onclick="tijiao('items');">品目信息</a></li>
              <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" >产品信息</a></li>
              <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" onclick="tijiao('applicationFrom');">申请表</a></li>
              <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" onclick="tijiao('reasonsList');">审核汇总</a></li>
            </ul>
              <div class="tab-content padding-top-20" style="height:1400px;">
                <div class="tab-pane fade active in height-450" id="tab-1">
                  <form id="form_id" action="" method="post"  enctype="multipart/form-data">
                    <input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
                  </form>
                  <div class=" margin-bottom-0">
                    <h2 class="f16 jbxx">
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
                          <th class="info">附件</th>
                          <th class="info">操作</th>
                        </tr>
                      </thead>
                      <c:forEach items="${supplierCertSes}" var="s" >
                        <tr>
                          <td class="tc" id="${s.id}">${s.name }</td>
                            <td class="tc">${s.levelCert}</td>
                            <td class="tc">${s.licenceAuthorith }</td>
                            <td class="tc">
                              <fmt:formatDate value="${s.expStartDate }" pattern='yyyy-MM-dd'/>  至  
                              <fmt:formatDate value="${s.expEndDate }" pattern='yyyy-MM-dd'/>
                            </td>
                            <td class="tc">
                             <c:if test="${s.mot==0 }">否</c:if>
                             <c:if test="${s.mot==1 }">是</c:if>
                            </td>
                            <td class="tc" style="cursor: pointer;" onclick="downloadFile('${m.attach}')">${s.attach }</td>
                            <td class="tc">
                              <a id="${s.id}_hide" class="b f18 fl ml10 red hand">√</a>
                              <a onclick="reason('${s.id}');" class="b f18 fl ml10 hand">×</a>
                            </td>
                        </tr>
                      </c:forEach>
                    </table>
                  </div>
                  
                  <div class=" margin-bottom-0">
                    <h2 class="f16 jbxx">
                    <i>02</i>供应商组织结构和人员
                    </h2>
                      <ul class="list-unstyled list-flow">
                        <li class="col-md-6 p0"><span class="" id="orgName2"><i class="red">＊</i>组织机构：</span>
                          <div class="input-append">
                            <input class="span3" type="text" value="${supplierMatSes.orgName }" />
                            <div id="orgName1"  class="b f18 fl ml10 red hand">√</div>
                            <div id="orgName" onclick="reason1(this.id)" class="b f18 fl ml10 hand">×</div>
                          </div>
                        </li>
                        <li class="col-md-6 p0"><span class="" id="totalPerson2"><i class="red">＊</i>人员总数：</span>
                          <div class="input-append">
                            <input class="span3" type="text" value="${supplierMatSes.totalPerson }" />
                            <div id="totalPerson1" class="b f18 fl ml10 red hand">√</div>
                          <div id="totalPerson" onclick="reason1(this.id)" class="b f18 fl ml10 hand">×</div>
                          </div>
                        </li>
                        <li class="col-md-6 p0"><span class="" id="totalMange2"><i class="red">＊</i>管理人员：</span>
                          <div class="input-append">
                            <input class="span3" type="text"  value="${supplierMatSes.totalMange }"/>
                            <div id="totalMange1" class="b f18 fl ml10 red hand">√</div>
                          <div id="totalMange" onclick="reason1(this.id)" class="b f18 fl ml10 hand">×</div>
                          </div>
                        </li>
                        <li class="col-md-6 p0"><span class="" id="totalTech2"><i class="red">＊</i>技术人员：</span>
                          <div class="input-append">
                            <input class="span3" type="text"  value="${supplierMatSes.totalTech }"/>
                            <div id="totalTech1" class="b f18 fl ml10 red hand">√</div>
                          <div id="totalTech" onclick="reason1(this.id)" class="b f18 fl ml10 hand">×</div>
                          </div>
                        </li>
                        <li class="col-md-6 p0"><span class="" id="totalWorker2"><i class="red">＊</i>工人(职员)：</span>
                          <div class="input-append">
                            <input class="span3" type="text" value="${supplierMatSes.totalWorker }"/>
                            <div id="totalWorker1" class="b f18 fl ml10 red hand">√</div>
                          <div id="totalWorker" onclick="reason1(this.id)" class="b f18 fl ml10 hand">×</div>
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
  <form target="_blank" id="download_form_id" action="${pageContext.request.contextPath}/supplierAudit/download.html" method="post">
   <input type="hidden" name="fileName" />
  </form>
  <jsp:include page="../../../../../index_bottom.jsp"></jsp:include>
</body>
</html>
