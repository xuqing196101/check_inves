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
<title>工程-专业信息</title>
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
  var auditField="证书编号是"+$("#"+id).text(); //审批的字段名字
   layer.prompt({title: '请填写不通过理由', formType: 2}, function(text){
    $.ajax({
        url:"<%=basePath%>supplierAudit/auditReasons.html",
        type:"post",
        data:"&auditField="+auditField+"&suggest="+text+"&supplierId="+supplierId,
      });
        $("#"+id+"_hide").hide();
        $("#"+id+"_hide1").hide();
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
  if(str=="chengxin"){
    action = "<%=basePath%>supplierQuery/list.html";
  }
  if(str=="item"){
     action = "<%=basePath%>supplierQuery/item.html";
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
              <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" onclick="tijiao('materialProduction');">物资-生产型专业信息</a></li>
              <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" onclick="tijiao('materialSales');">物资-销售型专业信息</a></li>
              <li class="active"><a aria-expanded="true" href="#tab-3" data-toggle="tab" onclick="tijiao('engineering');">工程-专业信息</a></li>
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
                  <div class=" margin-bottom-0 fl">
                    <h2 class="f16 jbxx1">
                    <i>01</i>供应商资质证书信息
                    </h2>
	                  <table class="table table-bordered table-condensed">
	                    <thead>
	                      <tr>
	                        <th class="info">资质资格类型</th>
	                        <th class="info">证书编号</th>
	                        <th class="info">资质资格最高等级</th>
	                        <th class="info">技术负责人姓名</th>
	                        <th class="info">技术负责人职称</th>
	                        <th class="info">技术负责人职务</th>
	                        <th class="info">单位负责人姓名</th>
	                        <th class="info">单位负责人职称</th>
	                        <th class="info">单位负责人职务</th>
	                        <th class="info">发证机关</th>
	                        <th class="info">发证日期</th>
	                        <th class="info">证书有效期截止日期</th>
	                        <th class="info">证书状态</th>
	                       <!--  <th class="info">附件上传</th> -->
	                      </tr>
	                    </thead>
	                    <c:forEach items="${supplierCertEng}" var="s" >
	                      <tr>
	                        <td class="tc">${s.certType }</td>
	                        <td class="tc" id="${s.id }">${s.certCode }</td>
	                        <td class="tc">${s.certMaxLevel }</td>
	                        <td class="tc">${s.techName }</td>
	                        <td class="tc">${s.techPt }</td>
	                        <td class="tc">${s.techJop }</td>
	                        <td class="tc">${s.depName }</td>
	                        <td class="tc">${s.depPt }</td>
	                        <td class="tc">${s.depJop }</td>
	                        <td class="tc">${s.licenceAuthorith }</td>
	                        <td class="tc">
	                          <fmt:formatDate value="${s.expStartDate }" pattern='yyyy-MM-dd'/>
	                        </td>
	                        <td class="tc">${s.certStatus }
	                           <fmt:formatDate value="${s.expStartDate }" pattern='yyyy-MM-dd'/>  至  
	                           <fmt:formatDate value="${s.expEndDate }" pattern='yyyy-MM-dd'/>
	                        </td>
	                        <td class="tc">
	                          <c:if test="${s.certStatus==0 }">无效</c:if>
	                          <c:if test="${s.certStatus==1 }">有效</c:if>
	                        </td>
	                        <%-- <td class="tc">${s.attachCert }</td> --%>
	                       </tr>
	                     </c:forEach>
	                   </table>
	                 </div>
	                 
	                 <div class=" margin-bottom-0 fl" >
	                   <h2 class="f16 jbxx1">
	                   <i>02</i>供应商资质资格信息
	                   </h2>
                   <table class="table table-bordered table-condensed">
                    <thead>
                      <tr>
                        <th class="info">资质资格类型</th>
                        <th class="info">证书编号</th>
                        <th class="info">资质资格序列</th>
                        <th class="info">专业类别</th>
                        <th class="info">资质资格等级</th>
                        <th class="info">是否主项资质</th>
                        <th class="info">批准资质资格内容</th>
                        <th class="info">首次批准资质资格文号</th>
                        <th class="info">首次批准资质资格日期</th>
                        <th class="info">资质资格取得方式</th>
                        <th class="info">资质资格状态</th>
                        <th class="info">资质资格状态变更时间</th>
                        <th class="info">资质资格状态变更原因</th>
                       <!--  <th class="info">附件上传</th> -->
                      </tr>
                    </thead>
                    <c:forEach items="${supplierAptitutes}" var="s" >
                      <tr>
                        <td class="tc">${s.certType }</td>
                        <td class="tc" id="${s.id }">${s.certCode }</td>
                        <td class="tc">${s.aptituteSequence }</td>
                        <td class="tc">${s.professType }</td>
                        <td class="tc">${s.aptituteLevel }</td>
                        <td class="tc">
                          <c:if test="${s.isMajorFund==0 }">否</c:if>
                          <c:if test="${s.isMajorFund==1 }">是</c:if>
                        <td class="tc">${s.aptituteContent }</td>
                        <td class="tc">${s.aptituteCode }</td>
                        <td class="tc">
                          <fmt:formatDate value="${s.aptituteDate }" pattern='yyyy-MM-dd'/>
                        </td>
                        <td class="tc">${s.aptituteWay }</td>
                        <td class="tc">
                          <c:if test="${s.aptituteStatus==0 }">无效</c:if>
                          <c:if test="${s.aptituteStatus==1 }">有效</c:if>
                        </td>
                        <td class="tc">
                          <fmt:formatDate value="${s.aptituteChangeAt }" pattern='yyyy-MM-dd'/>
                        </td>
                        <td class="tc">${s.aptituteChangeReason }</td>
                        <%-- <td class="tc">${s.attachCert }</td> --%>
                        <td class="tc">
                        </td>
                      </tr>
                    </c:forEach>
                  </table>
                </div>
                
                <div class=" margin-bottom-0 fl">
                    <h2 class="f16 jbxx1">
                    <i>03</i>供应商组织机构
                    </h2>
                      <ul class="list-unstyled list-flow">
                        <li class="col-md-6 p0"><span class="" id="orgName2"><i class="red">＊</i>组织机构：</span>
                          <div class="input-append">
                            <input class="span3" type="text" value="${supplierMatEngs.orgName }" />
                          </div>
                        </li>
                        <li class="col-md-6 p0"><span class="" id="totalTech2"><i class="red">＊</i>技术负责人：</span>
                          <div class="input-append">
                            <input class="span3" type="text" value="${supplierMatEngs.totalTech }" />
                          </div>
                        </li>
                        <li class="col-md-6 p0"><span class="" id="totalGlNormal2"><i class="red">＊</i>中级及以上职称人员：</span>
                          <div class="input-append">
                            <input class="span3" type="text"  value="${supplierMatEngs.totalGlNormal }"/>
                          </div>
                        </li>
                        <li class="col-md-6 p0"><span class="" id="totalMange2"><i class="red">＊</i>管理人员：</span>
                          <div class="input-append">
                            <input class="span3" type="text"  value="${supplierMatEngs.totalMange }"/>
                          </div>
                        </li>
                        <li class="col-md-6 p0"><span class="" id="totalTechWorker2"><i class="red">＊</i>技术工人：</span>
                          <div class="input-append">
                            <input class="span3" type="text" value="${supplierMatEngs.totalTechWorker }"/>
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
