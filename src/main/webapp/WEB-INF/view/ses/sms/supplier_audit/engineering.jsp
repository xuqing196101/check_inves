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
  //默认不显示叉
   $(function() {
    $("td").each(function() {
    $(this).parent("tr").find("td").eq(14).find("a").hide();
    });
  });
   $(function() {
    $(":input").each(function() {
      $(this).parent("div").find("div").eq(0).hide();
      $(this).parent("div").find("div").eq(1).hide();
    });
  });


function reason(id,auditContent){
   var supplierId=$("#supplierId").val();
   /* var auditContent="工程证书编号为:"+$("#"+id).text()+"的信息"; */ //审批的字段内容 
   var auditType=$("#engineering").text();//审核类型
   var fail = false;
   layer.prompt({title: '请填写不通过的理由：', formType: 2,offset:'200px'}, function(text){
    $.ajax({
        url:"<%=basePath%>supplierAudit/auditReasons.html",
        type:"post",
        data:"auditType="+auditType+"&auditFieldName="+id+"&auditContent="+auditContent+"&suggest="+text+"&supplierId="+supplierId+"&auditField=表格",
        dataType:"json",
        success:function(result){
        result = eval("(" + result + ")");
        if(result.msg == "fail"){
          fail = true;
          layer.msg("该条信息已审核过！",{offset:'200px'});
        }
      }
      });
      if(!fail){
        $("#"+id+"_show").show();
        $("#"+id+"_show1").show();
        layer.msg("审核不通过的理由是："+text,{offset:'200px'});
       }
    });
}

function reason1(id,auditField){
  var supplierId=$("#supplierId").val();
  var id2=id+"2";
  var id1=id+"1";
  var id3=id+"3";
  var auditFieldName=$("#"+id2+"").text().replaceAll("：",""); //审批的字段名字
  var auditContent= document.getElementById(""+id+"").value; //审批的字段内容
  var auditType=$("#engineering").text();//审核类型
  var fail = false;
  layer.prompt({title: '请填写不通过的理由：', formType: 2,offset:'200px'}, function(text){
    $.ajax({
        url:"<%=basePath%>supplierAudit/auditReasons.html",
        type:"post",
        data:"auditType="+auditType+"&auditFieldName="+auditFieldName+"&auditContent="+auditContent+"&suggest="+text+"&supplierId="+supplierId+"&auditField="+auditField,
      });
     layer.msg("审核不通过的理由是："+text,{offset:'200px'});
     $("#"+id3+"").show();
    });
}

//只读
$(function() {
    $(":input").each(function() {
      $(this).attr("readonly", "readonly");
    });
  });
  
  
function tijiao(url){
  $("#form_id").attr("action",url);
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
              <li class=""><a >详细信息</a></li>
              <li class=""><a >财务信息</a></li>
              <li class=""><a >股东信息</a></li>
              <c:if test="${fn:contains(supplierTypeNames, '生产型')}">
	            <li class=""><a >物资-生产型专业信息</a></li>
	            </c:if>
	            <c:if test="${fn:contains(supplierTypeNames, '销售型')}">
	            <li class=""><a >物资-销售型专业信息</a></li>
	            </c:if>
	            <c:if test="${fn:contains(supplierTypeNames, '工程')}">
	            <li class="active"><a id="engineering">工程-专业信息</a></li>
	            </c:if>
	            <c:if test="${fn:contains(supplierTypeNames, '服务')}">
              <li class=""><a >服务-专业信息</a></li>
              </c:if>
              <li class=""><a >品目信息</a></li>
              <li class=""><a >产品信息</a></li>
              <li class=""><a >申请表</a></li>
              <li class=""><a >审核汇总</a></li>
            </ul>
              <div class="tab-content padding-top-20" style="height:800px;">
                <div class="tab-pane fade active in height-450" id="tab-1">
                  <form id="form_id" action="" method="post"  enctype="multipart/form-data">
                    <input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
                  </form>
                  <div class=" margin-bottom-0 fl">
                    <h2 class="f16 jbxx">
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
	                        <th class="info">附件</th>
	                       <th class="info w50"></th>
	                      </tr>
	                    </thead>
	                    <c:forEach items="${supplierCertEng}" var="s" >
	                      <tr>
	                        <td class="tc">${s.certType }</td>
	                        <td class="tc" id="${s.id }">${s.certCode }</td>
	                        <td class="tc" onclick="reason('${s.id}','供应商资质证书信息');" >${s.certMaxLevel }</td>
	                        <td class="tc" onclick="reason('${s.id}','供应商资质证书信息');" >${s.techName }</td>
	                        <td class="tc" onclick="reason('${s.id}','供应商资质证书信息');" >${s.techPt }</td>
	                        <td class="tc" onclick="reason('${s.id}','供应商资质证书信息');" >${s.techJop }</td>
	                        <td class="tc" onclick="reason('${s.id}','供应商资质证书信息');" >${s.depName }</td>
	                        <td class="tc" onclick="reason('${s.id}','供应商资质证书信息');" >${s.depPt }</td>
	                        <td class="tc" onclick="reason('${s.id}','供应商资质证书信息');" >${s.depJop }</td>
	                        <td class="tc" onclick="reason('${s.id}','供应商资质证书信息');" >${s.licenceAuthorith }</td>
	                        <td class="tc" onclick="reason('${s.id}','供应商资质证书信息');" >
	                          <fmt:formatDate value="${s.expStartDate }" pattern='yyyy-MM-dd'/>
	                        </td>
	                        <td class="tc">${s.certStatus }
	                           <fmt:formatDate value="${s.expStartDate }" pattern='yyyy-MM-dd'/>  至  
	                           <fmt:formatDate value="${s.expEndDate }" pattern='yyyy-MM-dd'/>
	                        </td>
	                        <td class="tc" onclick="reason('${s.id}','供应商资质证书信息');" >
	                          <c:if test="${s.certStatus==0 }">无效</c:if>
	                          <c:if test="${s.certStatus==1 }">有效</c:if>
	                        </td>
	                        <td class="tc" >
	                         <c:if test="${s.attachCert !=null}">
                            <a class="green" onclick="downloadFile('${s.attachCert}')">附件下载</a>
                          </c:if>
                          <c:if test="${s.attachCert ==null}">
                           <a class="red">无附件下载</a>
                          </c:if>
	                        </td>
	                        <td class="tc">
	                          <a id="${s.id }_show" class="b f18 fl ml10 hand">×</a>
	                        </td>
	                       </tr>
	                     </c:forEach>
	                   </table>
	                 </div>
	                 
	                 <div class=" margin-bottom-0 fl" >
	                   <h2 class="f16 jbxx">
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
                        <th class="info">附件</th>
                       <th class="info w50"></th>
                      </tr>
                    </thead>
                    <c:forEach items="${supplierAptitutes}" var="s" >
                      <tr>
                        <td class="tc" onclick="reason('${s.id}','供应商资质资格信息');" >${s.certType }</td>
                        <td class="tc" id="${s.id }" onclick="reason('${s.id}','供应商资质资格信息');" >${s.certCode }</td>
                        <td class="tc" onclick="reason('${s.id}','供应商资质资格信息');" >${s.aptituteSequence }</td>
                        <td class="tc" onclick="reason('${s.id}','供应商资质资格信息');" >${s.professType }</td>
                        <td class="tc" onclick="reason('${s.id}','供应商资质资格信息');" >${s.aptituteLevel }</td>
                        <td class="tc" onclick="reason('${s.id}','供应商资质资格信息');" >
                          <c:if test="${s.isMajorFund==0 }">否</c:if>
                          <c:if test="${s.isMajorFund==1 }">是</c:if>
                        <td class="tc" onclick="reason('${s.id}','供应商资质资格信息');" >${s.aptituteContent }</td>
                        <td class="tc" onclick="reason('${s.id}','供应商资质资格信息');" >${s.aptituteCode }</td>
                        <td class="tc" onclick="reason('${s.id}','供应商资质资格信息');" >
                          <fmt:formatDate value="${s.aptituteDate }" pattern='yyyy-MM-dd'/>
                        </td>
                        <td class="tc" onclick="reason('${s.id}','供应商资质资格信息');" >${s.aptituteWay }</td>
                        <td class="tc" onclick="reason('${s.id}','供应商资质资格信息');" >
                          <c:if test="${s.aptituteStatus==0 }">无效</c:if>
                          <c:if test="${s.aptituteStatus==1 }">有效</c:if>
                        </td>
                        <td class="tc" onclick="reason('${s.id}','供应商资质资格信息');" >
                          <fmt:formatDate value="${s.aptituteChangeAt }" pattern='yyyy-MM-dd'/>
                        </td>
                        <td class="tc" onclick="reason('${s.id}','供应商资质资格信息');" >${s.aptituteChangeReason }</td>
                        <td class="tc" >
                          <c:if test="${s.attachCert !=null}">
                            <a class="green" onclick="downloadFile('${s.attachCert}')">附件下载</a>
                          </c:if>
                          <c:if test="${s.attachCert ==null}">
                           <a class="red">无附件下载</a>
                          </c:if>
                        </td>
                        <td class="tc">
                          <a id="${s.id }_show1" class="b f18 fl ml10 hand">×</a>
                        </td>
                      </tr>
                    </c:forEach>
                  </table>
                </div>
                
                <div class=" margin-bottom-0 fl">
                    <h2 class="f16 jbxx">
                    <i>03</i>供应商组织机构
                    </h2>
                      <ul class="list-unstyled list-flow">
                        <li class="col-md-6 p0"><span class="" id="orgName2">组织机构：</span>
                          <div class="input-append">
                            <input id="orgName" class="span3" type="text" value="${supplierMatEngs.orgName }" onclick="reason1(this.id,orgName)"/>
                            <div id="orgName1"  class="b f18 fl ml10 red hand">√</div>
                            <div id="orgName3"  class="b f18 fl ml10 hand">×</div>
                          </div>
                        </li>
                        <li class="col-md-6 p0"><span class="" id="totalTech2">技术负责人：</span>
                          <div class="input-append">
                            <input id="totalTech" class="span3" type="text" value="${supplierMatEngs.totalTech }" onclick="reason1(this.id,'totalTech')"/>
                            <div id="totalTech1" class="b f18 fl ml10 red hand">√</div>
                          <div id="totalTech3" class="b f18 fl ml10 hand">×</div>
                          </div>
                        </li>
                        <li class="col-md-6 p0"><span class="" id="totalGlNormal2">中级及以上职称人员：</span>
                          <div class="input-append">
                            <input id="totalGlNormal" class="span3" type="text"  value="${supplierMatEngs.totalGlNormal }" onclick="reason1(this.id,'totalGlNormal')"/>
                            <div id="totalGlNormal1" class="b f18 fl ml10 red hand">√</div>
                          <div id="totalGlNormal3"  class="b f18 fl ml10 hand">×</div>
                          </div>
                        </li>
                        <li class="col-md-6 p0"><span class="" id="totalMange2">管理人员：</span>
                          <div class="input-append">
                            <input id="totalMange" class="span3" type="text"  value="${supplierMatEngs.totalMange }" onclick="reason1(this.id,'totalMange')"/>
                            <div id="totalMange1" class="b f18 fl ml10 red hand">√</div>
                          <div id="totalMange3"  class="b f18 fl ml10 hand">×</div>
                          </div>
                        </li>
                        <li class="col-md-6 p0"><span class="" id="totalTechWorker2">技术工人：</span>
                          <div class="input-append">
                            <input id="totalMange" class="span3" type="text" value="${supplierMatEngs.totalTechWorker }" onclick="reason1(this.id,'totalTechWorker')"/>
                            <div id="totalTechWorker1" class="b f18 fl ml10 red hand">√</div>
                          <div id="totalTechWorker3"  class="b f18 fl ml10 hand">×</div>
                          </div>
                        </li>
                      </ul>
                    </div>
                  <div class="col-md-12 add_regist tc">
                    <a class="btn btn-windows save" onclick="tijiao('${url}');">下一步</a>
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
</body>
</html>
