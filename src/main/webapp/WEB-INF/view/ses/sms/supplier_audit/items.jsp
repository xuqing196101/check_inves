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
<title>品目信息</title>
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
<script type="text/javascript" src="<%=basePath%>public/layer/layer.js"></script>
<script type="text/javascript" src="<%=basePath%>public/layer/extend/layer.ext.js"></script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ztree/css/zTreeStyle.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.excheck.js"></script>
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

<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/upload/upload.css" type="text/css" />

<script type="text/javascript">
function tijiao(status){
  $("#status").val(status);
  form1.submit();
}

function tijiao(str){
  var action;
  if(str=="essential"){
     action ="${pageContext.request.contextPath}/supplierAudit/essential.html";
  }
  if(str=="financial"){
    action = "${pageContext.request.contextPath}/supplierAudit/financial.html";
  }
  if(str=="shareholder"){
    action = "${pageContext.request.contextPath}/supplierAudit/shareholder.html";
  }
  if(str=="materialProduction"){
    action = "${pageContext.request.contextPath}/supplierAudit/materialProduction.html";
  }
  if(str=="materialSales"){
    action = "${pageContext.request.contextPath}/supplierAudit/materialSales.html";
  }
  if(str=="engineering"){
    action = "${pageContext.request.contextPath}/supplierAudit/engineering.html";
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
    action = "${pageContext.request.contextPath}/supplierAudit/reasonsList.html";
  }
  if(str=="product"){
    action = "${pageContext.request.contextPath}/supplierAudit/product.html";
  }
  $("#form_id").attr("action",action);
  $("#form_id").submit();
}


//填写原因
function reason(id){
  var supplierId=$("#supplierId").val();
  var id1=id+"1";
  var id2=id+"2";
  var auditType=$("#items").text();//审核类型 
  var auditField = $("#"+id2+"").text().replaceAll("：","");//审批的字段名字
    layer.prompt({title: '请填写不通过的理由：', formType: 2,offset:'200px'}, function(text){
      $.ajax({
          url:"<%=basePath%>supplierAudit/auditReasons.html",
          type:"post",
          data:"auditType="+auditType+"&auditField="+auditField+"&suggest="+text+"&supplierId="+supplierId,
        });
        $("#"+id1).show();
        layer.msg("审核不通过的理由是："+text,{offset:'200px'});
      });
}

</script>
<script type="text/javascript">
  var zTreeObj;
  var zNodes;
  $(function() {
    $("#page_ul_id").find("li").click(function() {
      var id = $(this).attr("id");
      var page = "tab-" + id.charAt(id.length - 1);
      $("input[name='defaultPage']").val(page);
    });
    var defaultPage = "${defaultPage}";
    if (defaultPage) {
      var num = defaultPage.charAt(defaultPage.length - 1);
      $("#page_ul_id").find("li").each(function(index) {
        var liId = $(this).attr("id");
        var liNum = liId.charAt(liId.length - 1);
        if (liNum == num) {
          $(this).attr("class", "active");
        } else {
          $(this).removeAttr("class");
        }
      });
      $(".tab-pane").each(function() {
        var id = $(this).attr("id");
        if (id == defaultPage) {
          $(this).attr("class", "tab-pane fade height-300 active in");
        } else {
          $(this).attr("class", "tab-pane fade height-300");
        }
      });
    } else {
      $("#page_ul_id").find("li").each(function(index) {
        if (index == 0) {
          $(this).attr("class", "active");
        } else {
          $(this).removeAttr("class");
        }
      });
      $(".tab-pane").each(function(index) {
        if (index == 0) {
          $(this).attr("class", "tab-pane fade height-300 active in");
        } else {
          $(this).attr("class", "tab-pane fade height-300");
        }
      });
    }
    // ztree
    $(".tab-pane").each(function(index) {
      var kind = "";
      var id = $(this).attr("id");
      if (id == "tab-1") kind = "E73923CC68A44E2981D5EA6077580372";
      if (id == "tab-2") kind = "18A966C6FF17462AA0C015549F9EAD79";
      if (id == "tab-3") kind = "80E7B015FDF543F6A4A053A57C3C6908";
      if (id == "tab-4") kind = "3801E8F39B4C485CA59C3C531E86541E";
      loadZtree(id, kind);
    });
    
  });
  
  function loadZtree(id, kind) {
    var id = "";
    if (kind == "E73923CC68A44E2981D5EA6077580372") id = "tree_ul_id_1";
    if (kind == "18A966C6FF17462AA0C015549F9EAD79") id = "tree_ul_id_2";
    if (kind == "80E7B015FDF543F6A4A053A57C3C6908") id = "tree_ul_id_3";
    if (kind == "3801E8F39B4C485CA59C3C531E86541E") id = "tree_ul_id_4";
    var setting = {
      async : {
        enable : true,
        url : "${pageContext.request.contextPath}/category/find_category.do",
        otherParam : {
          supplierId : "${supplierId}",
          kind : kind
        },
        dataType : "json",
        type : "post",
      },
      check : {
        enable : true,
        chkboxType : {
          "Y" : "s",
          "N" : "s"
        }
      },
      data : {
        simpleData : {
          enable : true,
          idKey : "id",
          pIdKey : "parentId"
        }
      },
    };
    zTreeObj = $.fn.zTree.init($("#" + id), setting, zNodes);
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
              <li class=""><a aria-expanded="fale"  data-toggle="tab" onclick="tijiao('essential');">基本信息</a></li>
              <li class=""><a aria-expanded=""  data-toggle="tab" onclick="tijiao('financial');">财务信息</a></li>
              <li class=""><a aria-expanded="false"  data-toggle="tab" onclick="tijiao('shareholder');">股东信息</a></li>
              <c:if test="${fn:contains(supplierTypeNames, '生产型')}">
              <li class=""><a aria-expanded="fale" data-toggle="tab" onclick="tijiao('materialProduction');">物资-生产型专业信息</a></li>
              </c:if>
              <c:if test="${fn:contains(supplierTypeNames, '销售型')}">
              <li class=""><a aria-expanded="fale" data-toggle="tab" onclick="tijiao('materialSales');">物资-销售型专业信息</a></li>
              </c:if>
              <c:if test="${fn:contains(supplierTypeNames, '工程')}">
              <li class=""><a aria-expanded="false" data-toggle="tab" onclick="tijiao('engineering');">工程-专业信息</a></li>
              </c:if>
              <c:if test="${fn:contains(supplierTypeNames, '服务')}">
              <li class=""><a aria-expanded="false" data-toggle="tab" onclick="tijiao('service');">服务-专业信息</a></li>
              </c:if>
              <li class="active"><a aria-expanded="ture" data-toggle="tab" onclick="tijiao('items');" id="items">品目信息</a></li>
              <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" onclick="tijiao('product');" id="product">产品信息</a></li>
              <li class=""><a aria-expanded="false"  data-toggle="tab" onclick="tijiao('applicationFrom');">申请表</a></li>
              <li class=""><a aria-expanded="false"  data-toggle="tab" onclick="tijiao('reasonsList');">审核汇总</a></li>
            </ul>
            <div class="padding-top-10">
              <ul id="page_ul_id" class="nav nav-tabs bgdd">
                <c:if test="${fn:contains(supplierTypeNames, '生产型')}">
                  <li id="li_id_1" class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" id="production2">物资-生产型品目信息</a></li>
                </c:if>
                <c:if test="${fn:contains(supplierTypeNames, '销售型')}">
                  <li id="li_id_2" class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" id="sale2">物资-销售型品目信息</a></li>
                </c:if>
                <c:if test="${fn:contains(supplierTypeNames, '工程')}">
                  <li id="li_id_3" class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" id="engineering2">工程品目信息</a></li>
                </c:if>
                <c:if test="${fn:contains(supplierTypeNames, '服务')}">
                  <li id="li_id_4" class=""><a aria-expanded="false" href="#tab-4" data-toggle="tab" id="service2">服务品目信息</a></li>
                </c:if>
               </ul>
            </div>
                  <form id="form_id" action="" method="post"  enctype="multipart/form-data">
                      <input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
                  </form>
                  
                  <div class="tab-content padding-top-20">
                  <c:if test="${fn:contains(supplierTypeNames, '生产型')}">
                    <!-- 物资生产型 -->
                    <div class="tab-pane fade active in height-300" id="tab-1">
                      <div class="lr0_tbauto w200" onclick="reason(this.id)" id="production">
                        <ul id="tree_ul_id_1" class="ztree mt30" ></ul>
                        <div id="production1"  class="b f18 fl ml10 hand"style="display: none">×</div>
                      </div>
                    </div>
                  </c:if>
                  <c:if test="${fn:contains(supplierTypeNames, '销售型')}">
                    <!-- 物资销售型 -->
                    <div class="tab-pane fade height-300" id="tab-2">
                      <div class="lr0_tbauto w200" onclick="reason(this.id)" id="sale">
                        <ul id="tree_ul_id_2" class="ztree mt30"></ul>
                        <div id="sale1"  class="b f18 fl ml10 hand" style="display: none">×</div>
                      </div>
                    </div>
                  </c:if>
                  <c:if test="${fn:contains(supplierTypeNames, '工程')}">
                  <!-- 服务 -->
                    <div class="tab-pane fade height-200" id="tab-3">
                      <div class="lr0_tbauto w200" onclick="reason(this.id)" id="engineering">
                        <ul id="tree_ul_id_3" class="ztree mt30" ></ul>
                        <div id="engineering1"  class="b f18 fl ml10 hand" style="display: none">×</div>
                      </div>
                    </div>
                  </c:if>
                  <c:if test="${fn:contains(supplierTypeNames, '服务')}">
                    <!-- 生产 -->
                    <div class="tab-pane fade height-200" id="tab-4">
                      <div class="lr0_tbauto w200" onclick="reason(this.id)" id="service">
                        <ul id="tree_ul_id_4" class="ztree mt30"></ul>
                        <div id="service1" class="b f18 fl ml10 hand" style="display: none">×</div>
                      </div>
                    </div>
                  </c:if>
            </div> 
          </div>     
        </div>
      </div>
    </div>
  </div>
</body>
</html>
