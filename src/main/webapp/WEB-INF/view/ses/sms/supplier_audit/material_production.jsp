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
  //默认不显示叉
   $(function() {
    $("td").each(function() {
    $(this).parent("tr").find("td").eq(7).find("a").hide();

    });
  });
   $(function() {
    $(":input").each(function() {
      $(this).parent("div").find("div").eq(0).hide();
      $(this).parent("div").find("div").eq(1).hide();
    });
  });
  
function reason(id){
  var supplierId=$("#supplierId").val();
  /* var auditContent="生产资质证书为："+$("#"+id).text()+"的信息"; */ //审批的字段内容
  var auditType=$("#materialProduction").text();//审核类型
  var auditContent = "供应商资质证书";
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
  var auditType=$("#materialProduction").text();//审核类型
  var fail = false;
  layer.prompt({title: '请填写不通过的理由：', formType: 2,offset:'200px'}, function(text){
    $.ajax({
        url:"<%=basePath%>supplierAudit/auditReasons.html",
        type:"post",
        data:"auditType="+auditType+"&auditFieldName="+auditFieldName+"&auditContent="+auditContent+"&suggest="+text+"&supplierId="+supplierId+"&auditField="+auditField,
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
	     layer.msg("审核不通过的理由是："+text,{offset:'200px'});
	     $("#"+id3).show();
     }
    });
}


function tijiao(url){
  $("#form_id").attr("action",url);
  $("#form_id").submit();
}


//文件下載
  function downloadFile(fileName) {
    $("input[name='fileName']").val(fileName);
    $("#download_form_id").submit();
  }
  
  //只读
  $(function() {
    $(":input").each(function() {
      $(this).attr("readonly", "readonly");
    });
  });
</script>
<script type="text/javascript">
  function zhancun(){
    var supplierId=$("#supplierId").val();
    $.ajax({
      url:"<%=basePath%>supplierAudit/temporaryAudit.html",
      type:"post",
      data:"id="+supplierId,
      dataType:"json",
      success:function(result){
        result = eval("(" + result + ")");
        if(result.msg == "success"){
          layer.msg("暂存成功！",{offset:'200px'});
        }
      },error:function(){
        layer.msg("暂存失败！",{offset:'200px'});
      }
    });
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
	            <li class="active"><a id="materialProduction">物资-生产型专业信息</a></li>
	            </c:if>
	            <c:if test="${fn:contains(supplierTypeNames, '销售型')}">
	            <li class=""><a >物资-销售型专业信息</a></li>
	            </c:if>
	            <c:if test="${fn:contains(supplierTypeNames, '工程')}">
	            <li class=""><a >工程-专业信息</a></li>
	            </c:if>
	            <c:if test="${fn:contains(supplierTypeNames, '服务')}">
	            <li class=""><a >服务-专业信息</a></li>
	            </c:if>
              <li class=""><a >品目信息</a></li>
              <li class=""><a >产品信息</a></li>
              <li class=""><a >申请表</a></li>
              <li class=""><a >审核汇总</a></li>
            </ul>
              <div class="tab-content padding-top-20" style="height:1500px;">
                <div class="tab-pane fade active in height-450" id="tab-1">
                  <form id="form_id" action="" method="post"  enctype="multipart/form-data">
                    <input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
                  </form>
                  <h2 class="f16 jbxx">
                  <i>01</i>供应商资质证书
                  </h2>
                  <table class="table table-bordered table-condensed">
							      <thead>
							        <tr>
							          <th class="info w50">序号</th>
							          <th class="info">资质证书名称</th>
							          <th class="info">资质等级</th>
							          <th class="info">发证机关</th>
							          <th class="info">有效期(起止时间)</th>
							          <th class="info">是否年检</th>
							          <th class="info">附件</th>
							          <th class="info w50"></th>
							        </tr>
							        </thead>
							        <c:forEach items="${materialProduction}" var="m" varStatus="vs">
							          <tr>
							            <td class="tc">${vs.index + 1}</td>
							            <td class="tc" id="${m.id}" onclick="reason('${m.id}');">${m.name }</td>
							            <td class="tc" onclick="reason('${m.id}');">${m.levelCert}</td>
							            <td class="tc" onclick="reason('${m.id}');">${m.licenceAuthorith }</td>
							            <td class="tc" onclick="reason('${m.id}');">
								            <fmt:formatDate value="${m.expStartDate }" pattern='yyyy-MM-dd'/>  至  
								            <fmt:formatDate value="${m.expEndDate }" pattern='yyyy-MM-dd'/>
								          </td>
							            <td class="tc" onclick="reason('${m.id}');">
							             <c:if test="${m.mot==0 }">否</c:if>
							             <c:if test="${m.mot==1 }">是</c:if>
							            </td>
							            <td class="tc">
								            <c:if test="${m.attach !=null}">
								              <a class="green" onclick="downloadFile('${m.attach}')">附件下载</a>
								            </c:if>
								            <c:if test="${m.attach ==null}">
								              <a class="red">无附件下载</a>
								            </c:if>
							            </td>
							            <td class="tc">
	                          <a  id="${m.id }_show" class="b f18 fl ml10 hand">×</a>
                          </td>
							          </tr>
							        </c:forEach>
							    </table>
							    
							    <div class=" margin-bottom-0">
							    <h2 class="f16 jbxx">
                  <i>02</i>组织结构和人员
                  </h2>
                    <ul class="list-unstyled list-flow">
                      <li class="col-md-6 p0"><span class="" id="orgName2">组织机构：</span>
                        <div class="input-append">
                          <input id="orgName" class="span3" type="text" value="${supplierMatPros.orgName }" onclick="reason1(this.id,'orgName')"/>
                          <div id="orgName1"  class="b f18 fl ml10 red hand">√</div>
                          <div id="orgName3"  class="b f18 fl ml10 hand">×</div>
                        </div>
                      </li>
                      <li class="col-md-6 p0"><span class="" id="totalPerson2">人员总数：</span>
                        <div class="input-append">
                          <input id="totalPerson" class="span3" type="text" value="${supplierMatPros.totalPerson }" onclick="reason1(this.id,'totalPerson')"/>
                          <div id="totalPerson1" class="b f18 fl ml10 red hand">√</div>
                        <div id="totalPerson3" class="b f18 fl ml10 hand">×</div>
                        </div>
                      </li>
                      <li class="col-md-6 p0"><span class="" id="totalMange2">管理人员：</span>
                        <div class="input-append">
                          <input id="totalMange" class="span3" type="text"  value="${supplierMatPros.totalMange }" onclick="reason1(this.id,'totalMange')"/>
                          <div id="totalMange1" class="b f18 fl ml10 red hand">√</div>
                        <div id="totalMange3" class="b f18 fl ml10 hand">×</div>
                        </div>
                      </li>
                      <li class="col-md-6 p0"><span class="" id="totalTech2">技术人员：</span>
                        <div class="input-append">
                          <input id="totalTech" class="span3" type="text"  value="${supplierMatPros.totalTech }" onclick="reason1(this.id,'totalTech')"/>
                          <div id="totalTech1" class="b f18 fl ml10 red hand">√</div>
                        <div id="totalTech3" class="b f18 fl ml10 hand">×</div>
                        </div>
                      </li>
                      <li class="col-md-6 p0"><span class="" id="totalWorker2">工人(职员)：</span>
                        <div class="input-append">
                          <input id="totalWorker" class="span3" type="text" value="${supplierMatPros.totalWorker }" onclick="reason1(this.id,'totalWorker')"/>
                          <div id="totalWorker1" class="b f18 fl ml10 red hand">√</div>
                        <div id="totalWorker3" class="b f18 fl ml10 hand">×</div>
                        </div>
                      </li>
                    </ul>
                  </div>
                  
                  <div class=" margin-bottom-0 fl">
                  <h2 class="f16 jbxx">
                  <i>03</i>产品研发能力
                  </h2>
                    <ul class="list-unstyled list-flow">
                      <li class="col-md-6 p0"><span class="" id="scaleTech2">技术人员比例：</span>
                        <div class="input-append">
                          <input id="scaleTech" class="span3" type="text" value="${supplierMatPros.scaleTech }" onclick="reason1(this.id,'scaleTech')" />
                          <div id="scaleTech1"  class="b f18 fl ml10 red hand">√</div>
                          <div id="scaleTech3" class="b f18 fl ml10 hand">×</div>
                        </div>
                      </li>
                      <li class="col-md-6 p0"><span class="" id="scaleHeightTech2">高级技术人员比例：</span>
                        <div class="input-append">
                          <input id="scaleHeightTech" class="span3" type="text" value="${supplierMatPros.scaleHeightTech }" onclick="reason1(this.id,'scaleHeightTech')" />
                          <div id="scaleHeightTech1" class="b f18 fl ml10 red hand">√</div>
                        <div id="scaleHeightTech3" class="b f18 fl ml10 hand">×</div>
                        </div>
                      </li>
                      <li class="col-md-6 p0"><span class="" id=researchName2>研发部门名称：</span>
                        <div class="input-append">
                          <input id="researchName" class="span3" type="text"  value="${supplierMatPros.researchName }" onclick="reason1(this.id,'researchName')" />
                          <div id="researchName1" class="b f18 fl ml10 red hand">√</div>
                        <div id="researchName3" class="b f18 fl ml10 hand">×</div>
                        </div>
                      </li>
                      <li class="col-md-6 p0"><span class="" id="totalResearch2">研发部门人数：</span>
                        <div class="input-append">
                          <input id="totalResearch" class="span3" type="text"  value="${supplierMatPros.totalResearch }" onclick="reason1(this.id,'totalResearch')" />
                          <div id="totalResearch1" class="b f18 fl ml10 red hand">√</div>
                        <div id="totalResearch3" class="b f18 fl ml10 hand">×</div>
                        </div>
                      </li>
                      <li class="col-md-6 p0"><span class="" id="researchLead2">研发部门负责人：</span>
                        <div class="input-append">
                          <input id="researchLead" class="span3" type="text" value="${supplierMatPros.researchLead }" onclick="reason1(this.id,'researchLead')"/>
                          <div id="researchLead1" class="b f18 fl ml10 red hand">√</div>
                        <div id="researchLead3"  class="b f18 fl ml10 hand">×</div>
                        </div>
                      </li>
                      <li class="col-md-12 p0 mt10"><span class="fl" id="countryPro2">承担国家军队科研项目：</span>
	                      <div class="col-md-9 mt5">
	                        <div class="row">
	                          <textarea id="countryPro" class="text_area col-md-12" onclick="reason1(this.id,'countryPro')" >${supplierMatPros.countryPro }</textarea>
	                          <div id="countryPro1" class="b f18 fl ml10 red hand">√</div>
	                          <div id="countryPro3" class="b f18 fl ml10 hand">×</div>
	                        </div>
	                      </div>
                      </li>
                      <li class="col-md-12 p0 mt10"><span class="fl" id="countryReward2">获得国家军队科技项目：</span>
	                      <div class="col-md-9 mt5">
	                        <div class="row">
	                          <textarea id="countryReward" class="text_area col-md-12" onclick="reason1(this.id,'countryReward')" >${supplierMatPros.countryReward }</textarea>
	                          <div id="countryReward1" class="b f18 fl ml10 red hand">√</div>
	                          <div id="countryReward3" onclick="reason1(this.id)" class="b f18 fl ml10 hand">×</div>
	                        </div>
	                      </div>
                      </li>
                    </ul>
                  </div>
                  
                  <div class=" margin-bottom-0 fl">
                  <h2 class="f16 jbxx">
                  <i>04</i>供应商生产能力
                  </h2>
                    <ul class="list-unstyled list-flow">
                      <li class="col-md-6 p0"><span class="" id="totalBeltline2">生产线名称数量：</span>
                        <div class="input-append">
                          <input id="totalBeltline" class="span3" type="text" value="${supplierMatPros.totalBeltline }"  onclick="reason1(this.id,'totalBeltline')" />
                          <div id="totalBeltline1"  class="b f18 fl ml10 red hand">√</div>
                          <div id="totalBeltline3" class="b f18 fl ml10 hand">×</div>
                        </div>
                      </li>
                      <li class="col-md-6 p0"><span class="" id="totalDevice2">生产设备名称数量：</span>
                        <div class="input-append">
                          <input id="totalDevice" class="span3" type="text" value="${supplierMatPros.totalDevice }"  onclick="reason1(this.id,'totalDevice')"/>
                          <div id="totalDevice1" class="b f18 fl ml10 red hand">√</div>
                        <div id="totalDevice3" class="b f18 fl ml10 hand">×</div>
                        </div>
                      </li>
                    </ul>
                    
                  </div>
                  <div class=" margin-bottom-0 fl">
                  <h2 class="f16 jbxx">
                  <i>05</i>物资生产型供应商质量检测登记
                  </h2>
                    <ul class="list-unstyled list-flow">
                      <li class="col-md-6 p0"><span class="" id="qcName2">质量检测部门：</span>
                        <div class="input-append">
                          <input id="qcName" class="span3" type="text" value="${supplierMatPros.qcName }"  onclick="reason1(this.id,'qcName')"/>
                          <div id="qcName1"  class="b f18 fl ml10 red hand">√</div>
                          <div id="qcName3" class="b f18 fl ml10 hand">×</div>
                        </div>
                      </li>
                      <li class="col-md-6 p0"><span class="" id="totalQc2">质量检测人数：</span>
                        <div class="input-append">
                          <input id="totalQc"  class="span3" type="text" value="${supplierMatPros.totalQc }" onclick="reason1(this.id,'totalQc')"/>
                          <div id="totalQc1" class="b f18 fl ml10 red hand">√</div>
                        <div id="totalQc3"  class="b f18 fl ml10 hand">×</div>
                        </div>
                      </li>
                      <li class="col-md-6 p0"><span class="" id="qcLead2">质检部门负责人：</span>
                        <div class="input-append">
                          <input id="qcLead" class="span3" type="text" value="${supplierMatPros.qcLead }" onclick="reason1(this.id,'qcLead')"/>
                          <div id="qcLead1" class="b f18 fl ml10 red hand">√</div>
                        <div id="qcLead3" class="b f18 fl ml10 hand">×</div>
                        </div>
                      </li>
                      <li class="col-md-12 p0 mt10"><span class="fl" id="qcDevice2">质量检测设备名称：</span>
                        <div class="col-md-9 mt5">
                          <div class="row">
                            <textarea id="qcDevice" class="text_area col-md-12" onclick="reason1(this.id,'qcDevice')">${supplierMatPros.qcDevice }</textarea>
                            <div id="qcDevice1" class="b f18 fl ml10 red hand">√</div>
                            <div id="qcDevice3" class="b f18 fl ml10 hand">×</div>
                          </div>
                        </div>
                      </li>
                    </ul>
                  </div>
                  <div class="col-md-12 add_regist tc">
                    <a class="btn btn-windows save" onclick="zhancun();">暂存</a>
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
  <jsp:include page="../../../../../index_bottom.jsp"></jsp:include>
</body>
</html>
