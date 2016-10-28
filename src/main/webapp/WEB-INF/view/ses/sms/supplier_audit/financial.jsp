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
<title>财务信息</title>
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
  //默认不显示叉
   $(function() {
    $("td").each(function() {
    $(this).find("a").eq(0).hide();
    });
  });
  $(function() {
    $("a").each(function() {
      $(this).parent("div").find("div").eq(0).hide();
    });
  });

function reason(id,auditField){
  var supplierId=$("#supplierId").val();
  /* var auditFieldName=$("#"+id).text()+"年财务";  *///审批的字段名字
  var auditType=$("#financial").text();//审核类型
  var auditContent=$("#"+id).text()+"年财务信息";//审批的字段内容
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

function reason1(year, ele,auditField){
  var supplierId=$("#supplierId").val();
  var value = $(ele).parents("li").find("span").text().replaceAll("：","");//审批的字段名字
 /*  var auditFieldName=year+"年"+value;//审批的字段名字 */
  var auditFieldName=year+"年";//审批的字段名字
  var auditType=$("#financial").text();//审核类型
  var fail = false;
    layer.prompt({title: '请填写不通过的理由：', formType: 2,offset:'200px'}, function(text){
      $.ajax({
          url:"<%=basePath%>supplierAudit/auditReasons.html",
          type:"post",
          /* data:"&auditField="+auditField+"&suggest="+text+"&supplierId="+supplierId, */
          data:"auditType="+auditType+"&auditFieldName="+auditFieldName+"&auditContent=附件"+"&suggest="+text+"&supplierId="+supplierId+"&auditField="+auditField,
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
	        $(ele).parent("li").find("div").eq(1).show(); //隐藏勾
	        layer.msg("审核不通过的理由是："+text,{offset:'200px'});
        }
      });
}

function tijiao(){
  var action = "<%=basePath%>supplierAudit/shareholder.html";
  $("#form_id").attr("action",action);
  $("#form_id").submit();
}

//文件下載
  function downloadFile(fileName) {
    $("input[name='fileName']").val(fileName);
    $("#download_form_id").submit();
  }
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
              <li class=""><a>详细信息</a></li>
              <li class="active"><a id="financial">财务信息</a></li>
              <li class=""><a >股东信息</a></li>
              <c:if test="${fn:contains(supplierTypeNames, '生产型')}">
              <li class=""><a >物资-生产型专业信息</a></li>
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
             <div class="tab-content padding-top-20" style="height:800px;">
                <div class="tab-pane fade active in height-450" id="tab-1">
                  <form id="form_id" action="" method="post"  enctype="multipart/form-data">
                      <input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
                  </form>
                  <table class="table table-bordered table-condensed">
                   <thead>
                     <tr>
                       <th class="info w50">序号</th>
                       <th class="info">年份</th>
                       <th class="info">会计事务所名称</th>
                       <th class="info">事务所联系电话</th>
                       <th class="info">审计人姓名</th>
                       <th class="info">指标</th>
                       <th class="info">资产总额</th>
                       <th class="info">负债总额</th>
                       <th class="info">净资产总额</th>
                       <th class="info">营业收入</th>
                       <th class="info w50"></th>
                     </tr>
                   </thead>
                     <c:forEach items="${financial}" var="f" varStatus="vs">
                       <tr>
                         <td class="tc">${vs.index + 1}</td>
                         <td class="tc" id="${f.id }">${f.year }</td>
                         <td class="tc" onclick="reason('${f.id}');" >${f.name }</td>
                         <td class="tc" onclick="reason('${f.id}');">${f.telephone }</td>
                         <td class="tc" onclick="reason('${f.id}');">${f.auditors }</td>
                         <td class="tc" onclick="reason('${f.id}');">${f.quota }</td>
                         <td class="tc" onclick="reason('${f.id}');">${f.totalAssets }</td>
                         <td class="tc" onclick="reason('${f.id}');">${f.totalLiabilities }</td>
                         <td class="tc" onclick="reason('${f.id}');">${f.totalNetAssets}</td>
                         <td class="tc" onclick="reason('${f.id}');">${f.taking}</td>
                         <td class="tc" >
                         <a id="${f.id}_show" class="b f18 fl ml10 hand" >×</a>
                         </td>
                       </tr>
                     </c:forEach>
                  </table>
                  
                  <c:forEach items="${financial}" var="f" varStatus="vs">
                  <div class=" margin-bottom-0 fl">
                    <h2 class="f16 jbxx1">
                    <i>${vs.index + 1}</i>${f.year }年
                    </h2>
                    <ul class="list-unstyled list-flow">
                      <li class="col-md-6 p0 "><span class="" onclick="reason1('${f.year }', this,'auditOpinion');">财务审计报告意见表：</span>
                        <div class="input-append">
                          <c:if test="${f.auditOpinion != null}">
                            <a class="span3 green" onclick="downloadFile('${f.auditOpinion}')">附件下载</a>
                          </c:if>
                          <c:if test="${f.auditOpinion == null}">
                            <a class="span3 red">无附件下载</a>
                          </c:if>
                          <div  class="b f18 ml10 hand">×</div>
                        </div>
                      </li>
                      <li class="col-md-6 p0 "><span class="" onclick="reason1('${f.year }', this,'liabilitiesList');">资产负债表：</span>
                        <div class="input-append">
                          <c:if test="${f.liabilitiesList !=null}">
                            <a class="span3 green" onclick="downloadFile('${f.liabilitiesList}')">附件下载</a>
                          </c:if>
                          <c:if test="${f.liabilitiesList == null}">
                            <a class="span3 red">无附件下载</a>
                          </c:if>
                          <div  class="b f18 fl ml10 hand">×</div>
                        </div>
                      </li>
                      <li class="col-md-6 p0 "><span class="" onclick="reason1('${f.year }', this,'profitList');">利润表：</span>
                        <div class="input-append">
                          <c:if test="${f.profitList !=null}">
                            <a class="span3 green" onclick="downloadFile('${f.profitList}')">附件下载</a>
                          </c:if>
                          <c:if test="${f.profitList == null}">
                            <a class="span3 red">无附件下载</a>
                          </c:if>
                          <div  class="b f18 fl ml10 hand">×</div>
                        </div>
                      </li>
                      <li class="col-md-6 p0 "><span class="" onclick="reason1('${f.year }', this,'cashFlowStatement');">现金流量表：</span>
                        <div class="input-append">
                          <c:if test="${f.cashFlowStatement !=null}">
                            <a class="span3 green" onclick="downloadFile('${f.cashFlowStatement}')">附件下载</a>
                          </c:if>
                          <c:if test="${f.cashFlowStatement == null}">
                            <a class="span3 red">无附件下载</a>
                          </c:if>
                          <div class="b f18 fl ml10 hand">×</div>
                        </div>
                      </li>
                      <li class="col-md-6 p0 "><span class="" onclick="reason1('${f.year }', this,'changeList');">所有者权益变动表：</span>
                        <div class="input-append">
                          <c:if test="${f.changeList !=null}">
                            <a class="span3 green" onclick="downloadFile('${f.changeList}')">附件下载</a>
                          </c:if>
                          <c:if test="${f.changeList == null}">
                            <a class="span3 red">无附件下载</a>
                          </c:if>
                          <div class="b f18 fl ml10 hand">×</div>
                        </div>
                      </li>
                    </ul>
                  </div>
                  </c:forEach>
                <div class="col-md-12 add_regist tc">
                  <a class="btn btn-windows save" onclick="zhancun();">暂存</a>
                  <a class="btn btn-windows save" onclick="tijiao();">下一步</a>
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
