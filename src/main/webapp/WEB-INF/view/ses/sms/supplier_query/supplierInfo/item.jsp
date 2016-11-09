<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/public/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ztree/css/zTreeStyle.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.excheck.js"></script>
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
        url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
        type:"post",
        data:"auditType="+auditType+"&auditField="+auditField+"&auditContent="+auditContent+"&suggest="+text+"&supplierId="+supplierId,
      });
  $("#"+id1+"").hide();
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
    if(str=="product"){
     action = "${pageContext.request.contextPath}/supplierQuery/product.html";
  }
     if(str=="updateHistory"){
     action = "${pageContext.request.contextPath}/supplierQuery/showUpdateHistory.html";
  }
  $("#form_id").attr("action",action);
  $("#form_id").submit();
}

function downloadFile(fileName){
	  fileName=encodeURI(fileName);
      fileName=encodeURI(fileName);
	  window.location.href="${pageContext.request.contextPath}/supplierQuery/downLoadFile.html?fileName="+fileName;
}
function fanhui(){
	  window.location.href="${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html?address="+encodeURI(encodeURI('${suppliers.address}'))+"&status=${status}";
}
   $(function() {
    var zTreeObj;
	var zNodes;
	loadZtree();
	function loadZtree() {
		var setting = {
			async : {
				enable : true,
				url : "${pageContext.request.contextPath}/category/find_category_and_disabled.do",
				otherParam : {
					supplierId : "${id}",
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
		zTreeObj = $.fn.zTree.init($("#ztree"), setting, zNodes);
	}
	});
</script>

<style type="text/css">
.line{
	border: 4px solid #fff0;
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
          <ul class="nav nav-tabs bgdd">
            <li class=""><a aria-expanded="true" href="#tab-1" data-toggle="tab" onclick="tijiao('essential');">基本信息</a></li>
            <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" onclick="tijiao('financial');">财务信息</a></li>
            <li class=""><a aria-expanded="fale" href="#tab-3" data-toggle="tab" onclick="tijiao('shareholder');">股东信息</a></li>
            <c:if test="${fn:contains(suppliers.supplierType, '生产型')}">
            <li class=""><a aria-expanded="fale" href="#tab-2" data-toggle="tab" onclick="tijiao('materialProduction');">物资-生产型专业信息</a></li>
            </c:if>
             <c:if test="${fn:contains(suppliers.supplierType, '销售型')}">
            <li class=""><a aria-expanded="fale" href="#tab-3" data-toggle="tab" onclick="tijiao('materialSales');">物资-销售型专业信息</a></li>
            </c:if>
            <c:if test="${fn:contains(suppliers.supplierType, '工程')}">
            <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" onclick="tijiao('engineering');">工程-专业信息</a></li>
            </c:if>
             <c:if test="${fn:contains(suppliers.supplierType, '服务')}">
            <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" onclick="tijiao('service');">服务-专业信息</a></li>
            </c:if>
            <li class="active"><a aria-expanded="false" href="#tab-2" data-toggle="tab" onclick="tijiao('item');">品目信息</a></li>
            <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" onclick="tijiao('product');" >产品信息</a></li>
            <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" onclick="tijiao('chengxin');">诚信记录</a></li>
            <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" onclick="tijiao('updateHistory');">历史修改记录</a></li>
          </ul>
            <div class="tab-content padding-top-20">
              <div class="tab-pane fade active in height-450" id="tab-1">
                <form id="form_id" action="" method="post">
                    <input name="supplierId" id="id" value="${id }" type="hidden">
                  </form> 
                  <div class="col-md-3">
	 					<div id="ztree" class="ztree"></div>
					</div>                
              </div>
          </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>
