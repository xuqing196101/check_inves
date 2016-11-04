<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../../../../../index_head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>服务-专业信息</title>
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
<style type="text/css">
td {
  cursor:pointer;
}
input {
  cursor:pointer;
}
</style>
<script type="text/javascript">
  //默认不显示叉
   $(function() {
    $("td").each(function() {
    $(this).parent("tr").find("td").eq(6).find("a").hide();
    });
  });
   $(function() {
    $(":input").each(function() {
      $(this).parent("div").find("div").hide();
    });
  });

function reason(id){
  var supplierId=$("#supplierId").val();
  /* var auditContent="服务资质证书为："+$("#"+id).text()+"的信息"; */ //审批的字段内容
  var auditContent="供应商资质证书";
  var auditType=$("#service").text();//审核类型
  var fail = false;
   layer.prompt({title: '请填写不通过的理由：', formType: 2}, function(text){
    $.ajax({
        url:"<%=basePath%>supplierAudit/auditReasons.html",
        type:"post",
        data:"auditType="+auditType+"&auditFieldName="+id+"&auditContent="+auditContent+"&suggest="+text+"&supplierId="+supplierId+"&auditField=表格",
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
        $("#"+id+"_show").show();
        layer.msg("审核不通过的理由是："+text);
        }
    });
}

function reason1(id,auditField){
  var supplierId=$("#supplierId").val();
  var id2=id+"2";
  var id1=id+"1";
  var id3=id+"3";
  var auditFieldName=$("#"+id2+"").text().replace("：",""); //审批的字段名字
  var auditContent= document.getElementById(""+id+"").value; //审批的字段内容
  var auditType=$("#service").text();//审核类型
  var fail = false;
  layer.prompt({title: '请填写不通过的理由：', formType: 2}, function(text){
    $.ajax({
        url:"<%=basePath%>supplierAudit/auditReasons.html",
        type:"post",
        data:"auditType="+auditType+"&auditFieldName="+auditFieldName+"&auditContent="+auditContent+"&suggest="+text+"&supplierId="+supplierId+"&auditField="+auditField,
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
	     layer.msg("审核不通过的理由是："+text);
	     $("#"+id3+"").show();
     }
    });
}

//只读
$(function() {
    $(":input").each(function() {
      $(this).attr("readonly", "readonly");
    });
  });

function tijiao(){
  var action = "${pageContext.request.contextPath}/supplierAudit/items.html";
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
	            <li class="active"><a id="service">服务-专业信息</a></li>
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
                  <div class=" margin-bottom-0">
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
                      <c:forEach items="${supplierCertSes}" var="s" varStatus="vs">
                        <tr>
                          <td class="tc">${vs.index + 1}</td>
                          <td class="tc" id="${s.id}" onclick="reason('${s.id}');">${s.name }</td>
                          <td class="tc" onclick="reason('${s.id}');">${s.levelCert}</td>
                          <td class="tc" onclick="reason('${s.id}');">${s.licenceAuthorith }</td>
                          <td class="tc" onclick="reason('${s.id}');">
                            <fmt:formatDate value="${s.expStartDate }" pattern='yyyy-MM-dd'/>  至  
                            <fmt:formatDate value="${s.expEndDate }" pattern='yyyy-MM-dd'/>
                          </td>
                          <td class="tc" onclick="reason('${s.id}');">
                           <c:if test="${s.mot==0 }">否</c:if>
                           <c:if test="${s.mot==1 }">是</c:if>
                          </td>
                          <td class="tc">
                            <c:if test="${s.attach !=null}">
                             <a class="green" onclick="downloadFile('${s.attach}')">附件下载</a>
                            </c:if>
                            <c:if test="${s.attach ==null}">
                             <a class="red">无附件下载</a>
                            </c:if>
                          <td class="tc">
                            <a  id="${s.id}_show" class="b f18 fl ml10 hand red">×</a>
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
                        <li class="col-md-6 p0"><span class="" id="orgName2">组织机构：</span>
                          <div class="input-append">
                            <input id="orgName" class="span3" type="text" value="${supplierMatSes.orgName }" onclick="reason1(this.id,'orgName')"/>
                            <div id="orgName3"  class="b f18 fl ml10 hand red">×</div>
                          </div>
                        </li>
                        <li class="col-md-6 p0"><span class="" id="totalPerson2">人员总数：</span>
                          <div class="input-append">
                            <input id="totalPerson" class="span3" type="text" value="${supplierMatSes.totalPerson }" onclick="reason1(this.id,'totalPerson')" />
                          <div id="totalPerson3" class="b f18 fl ml10 hand red">×</div>
                          </div>
                        </li>
                        <li class="col-md-6 p0"><span class="" id="totalMange2">管理人员：</span>
                          <div class="input-append">
                            <input id="totalMange" class="span3" type="text"  value="${supplierMatSes.totalMange }" onclick="reason1(this.id,'totalMange')" />
                          <div id="totalMange3" class="b f18 fl ml10 hand red">×</div>
                          </div>
                        </li>
                        <li class="col-md-6 p0"><span class="" id="totalTech2">技术人员：</span>
                          <div class="input-append">
                            <input id="totalTech" class="span3" type="text"  value="${supplierMatSes.totalTech }" onclick="reason1(this.id,'totalTech')" />
                          <div id="totalTech3" class="b f18 fl ml10 hand red">×</div>
                          </div>
                        </li>
                        <li class="col-md-6 p0"><span class="" id="totalWorker2">工人(职员)：</span>
                          <div class="input-append">
                            <input id="totalWorker" class="span3" type="text" value="${supplierMatSes.totalWorker }" onclick="reason1(this.id,'totalWorker')" />
                            <div id="totalWorker3" class="b f18 fl ml10 hand red">×</div>
                          </div>
                        </li>
                      </ul>
                    </div>
                <div class="col-md-12 add_regist tc">
                   <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="zhancun();">暂存</a>
                   <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="tijiao('${url}');">下一步</a>
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
