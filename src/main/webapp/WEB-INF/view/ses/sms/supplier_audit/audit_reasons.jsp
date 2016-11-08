<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>审核汇总</title>
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

<link href="${pageContext.request.contextPath}/public/layer/skin/layer.css" media="screen" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/layer/skin/layer.ext.css" media="screen" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/upload.js"></script>
<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/public/layer/extend/layer.ext.js"></script>
<script type="text/javascript">
function tijiao(status){
  $("#status").val(status);
  form1.submit();
}

/* //导航
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

 */
//审核
function shenhe(status){
  var auditId = $("#auditId").val();
  $("input[name='id']").val(auditId);

  $("#status").val(status);
  $("#form_shen").submit();
}

//只读
$(function() {
    $(":input").each(function() {
      $(this).attr("readonly", "readonly");
    });
  });

<%-- function file(){
var supplierInspectListFile = $("#supplierInspectListFile").val();
alert(supplierInspectListFile);
  $.ajax({
        url:"<%=basePath%>supplierAudit/supplierFile.html",
        type:"post",
        data:"supplierInspectListFile="+supplierInspectListFile,
        success:function(){
          layer.msg("上传成功");
        },
         error: function(message){
            layer.msg("上传失败");
          }
      }
      );
       
} --%>
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
              <li class=""><a >产品信息</a></li>
              <li class=""><a >申请表</a></li>
              <li class="active"><a >审核汇总</a></li>
            </ul>
	            <form id="form_id" action="" method="post"  enctype="multipart/form-data">
	                <input name="supplierId" value="${supplierId}" type="hidden">
	            </form>
	            <c:if test="${status==1 }">
	            <h2 class="f16 jbxx1">
	              <i>01</i>问题汇总
	            </h2>
	            </c:if>
	            <table class="table table-bordered table-condensed">
	             <thead>
	               <tr>
	                 <th class="info w50">序号</th>
	                 <th class="info">审批类型</th>
	                 <th class="info">审批字段名字</th>
	                 <th class="info">审批内容</th>
	                 <th class="info">不通过理由</th>
	               </tr>
	             </thead>
	               <c:forEach items="${reasonsList }" var="list" varStatus="vs">
	                <input id="auditId" value="${list.id}" type="hidden">
	                 <tr>
	                   <td class="tc">${vs.index + 1}</td>
	                   <td class="tc">${list.auditType }</td>
	                   <td class="tc">${list.auditFieldName }</td>
	                   <td class="tc">${list.auditContent}</td>
	                   <td class="tc">${list.suggest}</td>
	                 </tr>
	               </c:forEach>
	            </table>
	            <%-- <h2 class="f16 jbxx1">
	              <i>01</i>问题汇总
	            </h2>
	          <div class=" margin-bottom-0">
	              <c:forEach items="${reasonsList }" var="list" >
	                <ul class="list-unstyled list-flow">
	                  <li class="col-md-6 p0 "><span class="" id="bankAccount2">${list.auditField}：</span>
	                    <div class="input-append">
	                      <input class="span3" id="bankAccount3"  type="text" value="${list.suggest}"/>
	                    </div>
	                  </li>
	                </ul>
	              </c:forEach>
	          </div> --%>
	          </div>
	          <c:if test="${status==1 }">
	          <div class=" margin-bottom-0 fl">
				       <h2 class="f16 jbxx1">
				        <i>02</i>供应商考察表
				       </h2>
				      <form id="form_id" action="${pageContext.request.contextPath}/supplierAudit/supplierFile.html" method="post"  enctype="multipart/form-data">
	              <ul class="list-unstyled list-flow p0_20">
	                <li >
	                <span class="" ><i class="red">＊</i>上传考察表:</span>
	                <input name="supplierId" value="${supplierId}" type="hidden">
	                <input class="span3" type="file" name="supplierInspectListFile"/>
	                <button type="submit" class="btn padding-left-20 padding-right-20 btn_back">上传</button>
	                <!-- <a onclick="file();" class="btn padding-left-20 padding-right-20 btn_back">上传</a> -->
	              </li>
	             </ul>
	           </form>
				    </div>
	          </c:if>
	          <!-- <div class="col-md-12 add_regist tc">
	          <ul class="list-unstyled list-flow">
	            <li class="col-md-6 p0"><span class="zzzx"><i class="red">＊</i>供应商考察表：</span>
	              <div class="input-append">
	                <div class="uploader orange m0">
	                  <input type="text" class="filename h32 m0 fz11" readonly="readonly" value="未选择任何文件..." /> 
	                  <input type="button" class="button" value="选择文件..." /> 
	                  <input name="taxCertFile" type="file" size="30" accept="image/*" />
	                </div>
	              </div>
	             </li>
	           </ul>
	          </div> -->
	          <div class="col-md-12 add_regist tc">
	          <form id="form_shen" action="${pageContext.request.contextPath}/supplierAudit/updateStatus.html"  enctype="multipart/form-data">
	            <input name="supplierId" value="${supplierId}" type="hidden">
	            <input name="id" type="hidden">
	             <input type="hidden" name="status" id="status"/>
	            <div class="margin-bottom-0  categories">
	              <div class="col-md-12 add_regist tc">
	              <c:if test="${status==0 || status==5  || status==8}">
	                <c:if test="${num==0}">
	                  <input class="btn btn-windows git"  type="button" onclick="shenhe(1)" value="初审通过 ">
	                </c:if>
	                <c:if test="${num!=0}">
	                  <input class="btn btn-windows reset"  type="button" onclick="shenhe(2)" value="初审不通过">
	                  <input class="btn btn-windows reset"  type="button" onclick="shenhe(7)" value="退回修改">
	                </c:if>
	              </c:if>
	              <c:if test="${status==1 || status==6}">
	                <c:if test="${num==0}">
	                  <input class="btn btn-windows git"  type="button" onclick="shenhe(3)" value="复审通过 ">
	                </c:if>
	                <c:if test="${num!=0}">
	                  <input class="btn btn-windows edit"  type="button" onclick="shenhe(4)" value="复审不通过">
	                  <input class="btn btn-windows reset"  type="button" onclick="shenhe(8)" value="退回修改">
	                </c:if>
	              </c:if>
	              <%-- <input class="btn btn-windows reset" onclick="location='<%=basePath%>supplierAudit/supplierAll.html'" type="button"  value="完成"> --%>
	              </div>
	            </div>
            </form>
          </div>     
        </div>
      </div>
    </div>
  </div>
</body>
</html>
