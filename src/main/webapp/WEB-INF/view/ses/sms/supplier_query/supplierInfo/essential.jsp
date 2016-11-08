<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
  if(str=="chengxin"){
    action = "<%=basePath%>supplierQuery/list.html";
  }
  if(str=="item"){
     action = "<%=basePath%>supplierQuery/item.html";
  }
  if(str=="product"){
     action = "<%=basePath%>supplierQuery/product.html";
  }
  if(str=="updateHistory"){
     action = "<%=basePath%>supplierQuery/showUpdateHistory.html";
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
      if('${category}'==1){
      window.location.href="<%=basePath%>supplierQuery/selectByCategory.html";
      }else{
	  window.location.href="<%=basePath%>supplierQuery/findSupplierByPriovince.html?address="+encodeURI(encodeURI('${suppliers.address}'))+"&status=${status}";
      }
}
	//鼠标移动显示全部内容
	function out(content){
	if(content.length >= 10){
	layer.msg(content, {
	        offset:'200px',
		    skin: 'demo-class',
			shade:false,
			area: ['600px'],
			time : 0    //默认消息框不关闭
		});//去掉msg图标
	}else{
		layer.closeAll();//关闭消息框
	}
}
</script>
<style type="text/css">

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
        <div class="padding-top-10">
          <ul class="nav nav-tabs bgdd">
            <li class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" onclick="tijiao('essential');">基本信息</a></li>
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
             <%-- <c:if test="${fn:contains(suppliers.supplierType, '服务')}">
            <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" onclick="tijiao('service');">服务-专业信息</a></li>
            </c:if> --%>
            <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" onclick="tijiao('item');">品目信息</a></li>
            <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" onclick="tijiao('product');" >产品信息</a></li>
            <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" onclick="tijiao('chengxin');">诚信记录</a></li>
            <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" onclick="tijiao('updateHistory');">历史修改记录</a></li>
          </ul>
                  <form id="form_id" action="" method="post">
                    <input name="supplierId" id="id" value="${suppliers.id }" type="hidden">
                  </form>                 
               <table class="table table-bordered">
					<tbody>
						<tr><td colspan="6" class="bggrey tl">一、企业基本信息：</td></tr>
						
						<tr>
							<td class="bggrey tr" style="width:17%">供应商名称：</td>
							<td style="width:16%" onmouseover="out('${suppliers.supplierName }')">
								<c:choose>
									<c:when test="${fn:length(suppliers.supplierName)>10}">
									${fn:substring(suppliers.supplierName,0,10)}...
									</c:when>
									<c:otherwise>
									${suppliers.supplierName}
									</c:otherwise>
								</c:choose>
							</td>
							<td class="bggrey tr" style="width:17%">公司网址：</td>
							<td style="width:17%">
								<c:choose>
									<c:when test="${fn:length(suppliers.website)>10}">
									${fn:substring(suppliers.website,0,10)}...
									</c:when>
									<c:otherwise>
									${suppliers.website}
									</c:otherwise>
								</c:choose>
							</td>
							<td style="width:17%" class="bggrey tr">成立日期：</td>
							<td style="width:17%"><fmt:formatDate value='${suppliers.foundDate}' pattern='yyyy-MM-dd'/></td>
						</tr>
						
						<tr>
							<td class="bggrey tr">营业执照登记类型：</td>
							<td onmouseover="out('${suppliers.businessType }')">${suppliers.businessType}</td>
							<td class="bggrey tr">地址：</td>
							<td>
								<c:choose>
									<c:when test="${fn:length(suppliers.address)>10}">
									${fn:substring(suppliers.address,0,10)}...
									</c:when>
									<c:otherwise>
									${suppliers.address}
									</c:otherwise>
								</c:choose>
							</td>
							<td class="bggrey tr">开户行名称：</td>
							<td onmouseover="out('${suppliers.bankName }')">
								<c:choose>
									<c:when test="${fn:length(suppliers.bankName)>10}">
									${fn:substring(suppliers.bankName,0,10)}...
									</c:when>
									<c:otherwise>
									${suppliers.bankName}
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						
						<tr>
							<td class="bggrey tr">开户行账户：</td>
							<td onmouseover="out('${suppliers.bankAccount }')">
								<c:choose>
									<c:when test="${fn:length(suppliers.bankAccount)>10}">
									${fn:substring(suppliers.bankAccount,0,10)}...
									</c:when>
									<c:otherwise>
									${suppliers.bankAccount}
									</c:otherwise>
								</c:choose>
							</td>
							<td class="bggrey tr">邮编：</td>
							<td>${suppliers.postCode }</td>
							<td class="bggrey tr">近三个月完税凭证：</td>
							<td class="pointer" onmouseover="out('${suppliers.taxCert }')"
							 onclick="downloadFile('${suppliers.taxCert}')">${fn:substring(suppliers.taxCert,0,10)}...</td>
						</tr> 
						
						<tr>
							<td class="bggrey tr">近三年银行账单：</td>
							<td class="pointer" onmouseover="out('${suppliers.billCert }')"
							 onclick="downloadFile('${suppliers.billCert}')">${fn:substring(suppliers.billCert,0,10)}...</td>
							<td class="bggrey tr">近三个月保险凭证：</td>
							<td class="pointer" onmouseover="out('${suppliers.securityCert }')"
							onclick="downloadFile('${suppliers.securityCert}')">${fn:substring(suppliers.securityCert,0,10)}...</td>
							<td class="bggrey tr">近三年违法记录：</td>
							<td class="pointer" onmouseover="out('${suppliers.breachCert }')"
							onclick="downloadFile('${suppliers.breachCert}')">${fn:substring(suppliers.breachCert,0,10)}...</td>
						</tr>
						
						<tr><td colspan="6" class="bggrey tl">二、法定代表人信息：</td></tr>
						
						<tr>
							<td class="bggrey tr">法定代表人姓名：</td>
							<td>${suppliers.legalName}</td>
							<td class="bggrey tr">身份证号：</td>
							<td onmouseover="out('${suppliers.legalIdCard }')">
								<c:choose>
									<c:when test="${fn:length(suppliers.legalIdCard)>10}">
									${fn:substring(suppliers.legalIdCard,0,10)}...
									</c:when>
									<c:otherwise>
									${suppliers.legalIdCard}
									</c:otherwise>
								</c:choose>
							</td>
							<td class="bggrey tr">固定电话：</td>
							<td>${suppliers.legalTelephone}</td>
						</tr>
						
						<tr>
							<td class="bggrey tr">手机：</td>
							<td colspan="5">${suppliers.legalMobile }</td>
						</tr>
						
						<tr><td colspan="6" class="bggrey tl">三、联系人信息：</td></tr>
						
						<tr>
							<td class="bggrey tr">姓名：</td><td>${suppliers.contactName } </td>
							<td class="bggrey tr">传真：</td><td>${suppliers.contactFax }</td>
							<td class="bggrey tr">地址：</td>
							<td onmouseover="out('${suppliers.contactAddress }')">
								<c:choose>
									<c:when test="${fn:length(suppliers.contactAddress)>10}">
									${fn:substring(suppliers.contactAddress,0,10)}...
									</c:when>
									<c:otherwise>
									${suppliers.contactAddress}
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						
						<tr>
							<td class="bggrey tr">固定电话：</td><td>${suppliers.contactTelephone }</td>
							<td class="bggrey tr">手机：</td><td>${suppliers.contactMobile } </td>
							<td class="bggrey tr">邮箱：</td><td>${suppliers.contactEmail }</td>
						</tr>
						
						<tr><td colspan="6" class="bggrey tl">四、营业执照：</td></tr>
						
						<tr>
							<td class="bggrey tr">统一社会信用代码：</td><td>${suppliers.creditCode } </td>
							<td class="bggrey tr">登记机关：</td>
							<td onmouseover="out('${suppliers.registAuthority }')">
								<c:choose>
									<c:when test="${fn:length(suppliers.registAuthority)>10}">
									${fn:substring(suppliers.registAuthority,0,10)}...
									</c:when>
									<c:otherwise>
									${suppliers.registAuthority}
									</c:otherwise>
								</c:choose>
							</td>
							<td class="bggrey tr">注册资本：</td><td>${suppliers.registFund }</td>
						</tr>
						
						<tr>
							<td class="bggrey tr">营业期限：</td>
							<td><fmt:formatDate value="${suppliers.businessStartDate}" pattern="yyyy-MM-dd" />至 <fmt:formatDate value="${suppliers.businessEndDate}" pattern="yyyy-MM-dd" /> </td>
							<td class="bggrey tr">经营范围：</td>
							<td onmouseover="out('${suppliers.businessScope }')">
								<c:choose>
									<c:when test="${fn:length(suppliers.businessScope)>10}">
									${fn:substring(suppliers.businessScope,0,10)}...
									</c:when>
									<c:otherwise>
									${suppliers.businessScope}
									</c:otherwise>
								</c:choose>
							</td>
							<td class="bggrey tr">生产或经营地址：</td>
							<td onmouseover="out('${suppliers.businessAddress }')">
								<c:choose>
									<c:when test="${fn:length(suppliers.businessAddress)>10}">
									${fn:substring(suppliers.businessAddress,0,10)}...
									</c:when>
									<c:otherwise>
									${suppliers.businessAddress}
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						
						<tr>
							<td class="bggrey tr">邮编：</td><td> ${suppliers.businessPostCode } </td>
							<td class="bggrey tr">境外分支机构：</td><td>${suppliers.overseasBranch }</td>
							<td class="bggrey tr">国家：</td>
							<td onmouseover="out('${suppliers.branchCountry }')">
								<c:choose>
									<c:when test="${fn:length(suppliers.branchCountry)>10}">
											${fn:substring(suppliers.branchCountry,0,10)}...
									</c:when>
									<c:otherwise>
										${suppliers.branchCountry}
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						
						<tr>
							<td class="bggrey tr">详细地址：</td>
							<td onmouseover="out('${suppliers.branchAddress }')">
								<c:choose>
									<c:when test="${fn:length(suppliers.branchAddress)>10}">
									${fn:substring(suppliers.branchAddress,0,10)}...
									</c:when>
									<c:otherwise>
									${suppliers.branchAddress}
									</c:otherwise>
								</c:choose>
							 </td>
							<td class="bggrey tr">机构名称：</td>
							<td onmouseover="out('${suppliers.branchName }')">
								<c:choose>
									<c:when test="${fn:length(suppliers.branchName)>10}">
									${fn:substring(suppliers.branchName,0,10)}...
									</c:when>
									<c:otherwise>
									${suppliers.branchName}
									</c:otherwise>
								</c:choose>
							</td>
							<td class="bggrey tr">生产经营范围：</td>
							<td onmouseover="out('${suppliers.branchBusinessScope }')">
								<c:choose>
									<c:when test="${fn:length(suppliers.branchBusinessScope)>10}">
										${fn:substring(suppliers.branchBusinessScope,0,10)}...
									</c:when>
									<c:otherwise>
										${suppliers.branchBusinessScope}
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						
						<tr>
						<td class="bggrey tr">营业执照：</td>
						<td colspan="5" class="hand" onclick="downloadFile('${suppliers.businessCert}')" >${suppliers.businessCert}</td>
					   </tr>
					</tbody>
				</table>
</div>
</div>
</body>
</html>
