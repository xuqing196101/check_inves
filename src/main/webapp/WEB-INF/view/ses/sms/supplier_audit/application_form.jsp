<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../../../../../index_head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>申请表</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/common.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/style.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/line-icons.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/app.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/application.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/header-v4.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/footer-v2.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/img-hover.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/page_job.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/shop.style.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/upload/upload.css" type="text/css" />

<link href="<%=basePath%>public/layer/skin/layer.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/layer/skin/layer.ext.css" media="screen" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/upload.js"></script>
<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/public/layer/extend/layer.ext.js"></script>

<script type="text/javascript">
  $(function() {
    $("a").each(function() {
      $(this).parent("div").find("div").eq(0).hide();
    });
  });


function reason1(ele,auditField){
  var supplierId=$("#supplierId").val();
  var auditFieldName = $(ele).parents("li").find("span").text().replace("：","");//审批的字段名字
  var auditType=$("#applicationFrom").text();//审核类型 
  var fail = false;
    layer.prompt({title: '请填写不通过的理由：', formType: 2},function(text){
      $.ajax({
          url:"<%=basePath%>supplierAudit/auditReasons.html",
          type:"post",
          data:"auditType="+auditType+"&auditFieldName="+auditFieldName+"&auditContent=附件"+"&suggest="+text+"&supplierId="+supplierId+"&auditField="+auditField,
          dataType:"json",
	        success:function(result){
	        result = eval("(" + result + ")");
	        if(result.msg == "fail"){
	          fail = true;
	          layer.msg('该条信息已审核过！', {
            shift: 6 //动画类型
            });
        }
      }
        });
        if(!fail){
	        $(ele).parent("li").find("div").eq(1).show(); //显示叉
	          layer.msg("审核不通过的理由是："+text
          );
          }
      });
}


function tijiao(){
  var action = "<%=basePath%>supplierAudit/reasonsList.html";
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
          layer.msg("暂存成功！");
        }
      },error:function(){
        layer.msg("暂存失败！");
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
              <li class=""><a>产品信息</a></li>
              <li class="active"><a id="applicationFrom">申请表</a></li>
              <li class=""><a >审核汇总</a></li>
            </ul>
              <div class="tab-content padding-top-20">
                <div class="tab-pane fade active in height-250" id="tab-1">
                  <form id="form_id" action="" method="post"  enctype="multipart/form-data">
                      <input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
                  </form>
                <ul class="list-unstyled list-flow hand">
                  <li class="col-md-6 p0 "><span class="w245" onclick="reason1(this,'supplierLevel');" >军队供应商分级方法：</span>
                    <div >
                      <c:if test="${applicationForm.supplierLevel != null}">
                      <a class="span3 green" href="javascript:void(0)" onclick="downloadFile('${applicationForm.supplierLevel}')" >下载附件</a>
                      </c:if>
                      <c:if test="${applicationForm.supplierLevel == null}">
                        <a class="span3 red">无附件下载</a>
                      </c:if>
                      <div class="b f18 ml10 fl hand red">×</div>
                    </div>
                  </li>
                  <li class="col-md-6 p0 "><span class="w245" onclick="reason1(this,'supplierPledge');" >军队供应商承诺书：</span>
                    <div class="input-append">
                      <c:if test="${applicationForm.supplierPledge !=null}">
                        <a class="span3 green" onclick="downloadFile('${applicationForm.supplierPledge}')">下载附件</a>
                      </c:if>
                      <c:if test="${applicationForm.supplierPledge == null}">
                        <a class="span3 red">无附件下载</a>
                      </c:if>
                      <div class="b f18 ml10 fl hand red">×</div>
                    </div>
                  </li>
                  <li class="col-md-6 p0 "><span class="w245" onclick="reason1(this,'supplierRegList');" >军队供应商入库申请表：</span>
                    <div class="input-append">
                      <c:if test="${applicationForm.supplierRegList !=null}">
                        <a class="span3 green" onclick="downloadFile('${applicationForm.supplierRegList}')">下载附件</a>
                      </c:if>
                      <c:if test="${applicationForm.supplierRegList == null}">
                        <a class="span3 red">无附件下载</a>
                      </c:if>
                      <div class="b f18 ml10 fl hand red">×</div>
                    </div>
                  </li>
                  <li class="col-md-6 p0 "><span class="w245" onclick="reason1(this,'supplierReviewList');" >军队供应商实地考察记录表：</span>
                    <div class="input-append">
                      <c:if test="${applicationForm.supplierReviewList !=null}">
                        <a class="span3 green" onclick="downloadFile('${applicationForm.supplierReviewList}')">下载附件</a>
                      </c:if>
                      <c:if test="${applicationForm.supplierReviewList == null}">
                        <a class="span3 red">无附件下载</a>
                      </c:if>
                      <div class="b f18 ml10 fl hand red">×</div>
                    </div>
                  </li>
                  <li class="col-md-6 p0 "><span class="w245" onclick="reason1(this,'supplierInspectList');" >军队供应商实地考察廉政意见函：</span>
                    <div class="input-append">
                      <c:if test="${applicationForm.supplierInspectList !=null}">
                        <a class="span3 green" onclick="downloadFile('${applicationForm.supplierInspectList}')">下载附件</a>
                      </c:if>
                      <c:if test="${applicationForm.supplierInspectList == null}">
                        <a class="span3 red">无附件下载</a>
                      </c:if>
                      <div class="b f18 ml10 fl hand red">×</div>
                    </div>
                  </li>
                  <li class="col-md-6 p0 "><span class="w245" onclick="reason1(this,'supplierChangeList');" >军队供应商注册变更申请表：</span>
                    <div class="input-append">
                      <c:if test="${applicationForm.supplierChangeList != null}">
                        <a class="span3 green" onclick="downloadFile('${applicationForm.supplierChangeList}')">下载附件</a>
                      </c:if>
                      <c:if test="${applicationForm.supplierChangeList == null}">
                        <a class="span3 red">无附件下载</a>
                      </c:if>
                      <div  class="b f18 ml10 fl hand red">×</div>
                    </div>
                  </li>
                  <li class="col-md-6 p0 "><span class="w245" onclick="reason1(this,'supplierExitList');" >军队供应商退库申请表：</span>
                    <div class="input-append">
                      <c:if test="${applicationForm.supplierExitList != null}">
                        <a class="span3 green" onclick="downloadFile('${applicationForm.supplierExitList}')">下载附件</a>
                      </c:if>
                      <c:if test="${applicationForm.supplierExitList == null}">
                        <a class="span3 red">无附件下载</a>
                      </c:if>
                      <div class="b f18 ml10 fl hand red">×</div>
                    </div>
                  </li>
                </ul>
              </div>
            </div>
          </div>
          <div class="col-md-12 add_regist tc">
             <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="zhancun();">暂存</a>
             <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="tijiao('${url}');">下一步</a>
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
