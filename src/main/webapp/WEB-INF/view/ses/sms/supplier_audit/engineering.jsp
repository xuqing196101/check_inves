<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../../../../../index_head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>工程-专业信息</title>
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
    $(this).parent("tr").find("td").eq(14).find("a").hide();
    $(this).parent("tr").find("td").eq(3).find("a").hide();
    });
    
    $(":input").each(function() {
      $(this).parent("div").find("div").hide();
    });
  });

function reason(id,auditContent){
   var supplierId=$("#supplierId").val();
   var auditType=$("#engineering").text();//审核类型
   var fail = false;
   var auditFieldName= auditContent.replace("信息","");
   layer.prompt({title: '请填写不通过的理由：', formType: 2}, function(text){
    $.ajax({
        url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
        type:"post",
        data:"auditType="+auditType+"&auditFieldName="+auditFieldName+"&auditContent="+auditContent+"&suggest="+text+"&supplierId="+supplierId+"&auditField="+id,
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
        $("#"+id+"_show1").show();
        $("#"+id+"_show2").show();
        layer.msg("审核不通过的理由是："+text);
       }
    });
}

function reason1(id,auditContent){
  var supplierId=$("#supplierId").val();
  var id2=id+"2";
  var id1=id+"1";
  var id3=id+"3";
   var auditFieldName=$("#"+id2+"").text().replace("：",""); //审批的字段名字 
  var auditContent= document.getElementById(""+id+"").value; //审批的字段内容
  var auditType=$("#engineering").text();//审核类型
  var fail = false;
  layer.prompt({title: '请填写不通过的理由：', formType: 2}, function(text){
    $.ajax({
        url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
        type:"post",
        data:"auditType="+auditType+"&auditFieldName="+auditFieldName+"&auditContent="+auditContent+"&suggest="+text+"&supplierId="+supplierId+"&auditField="+id,
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
  
  
function nextStep(url){
  $("#form_id").attr("action",url);
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
      url:"${pageContext.request.contextPath}/supplierAudit/temporaryAudit.html",
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
              <div class="tab-content padding-top-20" style="height:900px;">
                <div class="tab-pane fade active in height-450" id="tab-1">
                  <form id="form_id" action="" method="post">
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
	                        <td class="tc" onclick="reason('${s.id}','工程-资质证书信息');" >${s.certType }</td>
	                        <td class="tc" id="${s.id }" onclick="reason('${s.id}','工程-资质证书信息');" >${s.certCode }</td>
	                        <td class="tc" onclick="reason('${s.id}','工程-资质证书信息');" >${s.certMaxLevel }</td>
	                        <td class="tc" onclick="reason('${s.id}','工程-资质证书信息');" >${s.techName }</td>
	                        <td class="tc" onclick="reason('${s.id}','工程-资质证书信息');" >${s.techPt }</td>
	                        <td class="tc" onclick="reason('${s.id}','工程-资质证书信息');" >${s.techJop }</td>
	                        <td class="tc" onclick="reason('${s.id}','供应商资质证书信息');" >${s.depName }</td>
	                        <td class="tc" onclick="reason('${s.id}','工程-资质证书信息');" >${s.depPt }</td>
	                        <td class="tc" onclick="reason('${s.id}','工程-资质证书信息');" >${s.depJop }</td>
	                        <td class="tc" onclick="reason('${s.id}','工程-资质证书信息');" >${s.licenceAuthorith }</td>
	                        <td class="tc" onclick="reason('${s.id}','工程-资质证书信息');" >
	                          <fmt:formatDate value="${s.expStartDate }" pattern='yyyy-MM-dd'/>
	                        </td>
	                        <td class="tc" onclick="reason('${s.id}','工程-资质证书信息');" >${s.certStatus }
	                           <fmt:formatDate value="${s.expStartDate }" pattern='yyyy-MM-dd'/>  至  
	                           <fmt:formatDate value="${s.expEndDate }" pattern='yyyy-MM-dd'/>
	                        </td>
	                        <td class="tc" onclick="reason('${s.id}','工程-资质证书信息');" >
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
	                          <a id="${s.id }_show" class="b f18 fl ml10 hand red">×</a>
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
                        <td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');" >${s.certType }</td>
                        <td class="tc" id="${s.id }" onclick="reason('${s.id}','工程-资质资格证书信息');" >${s.certCode }</td>
                        <td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');" >${s.aptituteSequence }</td>
                        <td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');" >${s.professType }</td>
                        <td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');" >${s.aptituteLevel }</td>
                        <td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');" >
                          <c:if test="${s.isMajorFund==0 }">否</c:if>
                          <c:if test="${s.isMajorFund==1 }">是</c:if>
                        <td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');" >${s.aptituteContent }</td>
                        <td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');" >${s.aptituteCode }</td>
                        <td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');" >
                          <fmt:formatDate value="${s.aptituteDate }" pattern='yyyy-MM-dd'/>
                        </td>
                        <td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');" >${s.aptituteWay }</td>
                        <td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');" >
                          <c:if test="${s.aptituteStatus==0 }">无效</c:if>
                          <c:if test="${s.aptituteStatus==1 }">有效</c:if>
                        </td>
                        <td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');" >
                          <fmt:formatDate value="${s.aptituteChangeAt }" pattern='yyyy-MM-dd'/>
                        </td>
                        <td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');" >${s.aptituteChangeReason }</td>
                        <td class="tc" >
                          <c:if test="${s.attachCert !=null}">
                            <a class="green" onclick="downloadFile('${s.attachCert}')">附件下载</a>
                          </c:if>
                          <c:if test="${s.attachCert ==null}">
                           <a class="red">无附件下载</a>
                          </c:if>
                        </td>
                        <td class="tc">
                          <a id="${s.id }_show1" class="b f18 fl ml10 hand red">×</a>
                        </td>
                      </tr>
                    </c:forEach>
                  </table>
                </div>
                
                 <div class=" margin-bottom-0 fl" >
                     <h2 class="f16 jbxx  mt40">
                     <i>03</i>供应商注册人员登记
                     </h2>
                   <table class="table table-bordered table-condensed">
                   <thead>
                     <tr>
                       <th class="info w50">序号</th>
                       <th class="info">注册名称</th>
                       <th class="info">注册人数</th>
                       <th class="info w50"></th>
                     </tr>
                   </thead>
                     <c:forEach items="${listRegPerson}" var="regPrson" varStatus="vs">
                       <tr onclick="reason('${regPrson.id}','工程-注册人员登记信息');">
                         <td class="tc">${vs.index + 1}</td>
                         <td class="tc">${regPrson.regType}</td>
                         <td class="tc">${regPrson.regNumber}</td>
                         <td class="tc">
                          <a id="${regPrson.id }_show2" class="b f18 fl ml10 hand red">×</a>
                        </td>
                       </tr>
                     </c:forEach>
                  </table>
                </div>
                
                <div class=" margin-bottom-0 fl">
                    <h2 class="f16 jbxx">
                    <i>04</i>供应商组织机构
                    </h2>
                      <ul class="list-unstyled list-flow">
                        <li class="col-md-6 p0"><span class="" id="orgName2">组织机构：</span>
                          <div class="input-append">
                            <input id="orgName" class="span3" type="text" value="${supplierMatEngs.orgName }" onclick="reason1(this.id,supplierMatEng.orgName)"/>
                            <div id="orgName3"  class="b f18 fl ml10 hand red">×</div>
                          </div>
                        </li>
                        <li class="col-md-6 p0"><span class="" id="totalTech2">技术负责人：</span>
                          <div class="input-append">
                            <input id="totalTech" class="span3" type="text" value="${supplierMatEngs.totalTech }" onclick="reason1(this.id,'supplierMatEng.totalTech')"/>
                          <div id="totalTech3" class="b f18 fl ml10 hand red">×</div>
                          </div>
                        </li>
                        <li class="col-md-6 p0"><span class="" id="totalGlNormal2">中级及以上职称人员：</span>
                          <div class="input-append">
                            <input id="totalGlNormal" class="span3" type="text"  value="${supplierMatEngs.totalGlNormal }" onclick="reason1(this.id,'supplierMatEng.totalGlNormal')"/>
                          <div id="totalGlNormal3"  class="b f18 fl ml10 hand red">×</div>
                          </div>
                        </li>
                        <li class="col-md-6 p0"><span class="" id="totalMange2">管理人员：</span>
                          <div class="input-append">
                            <input id="totalMange" class="span3" type="text"  value="${supplierMatEngs.totalMange }" onclick="reason1(this.id,'supplierMatEng.totalMange')"/>
                          <div id="totalMange3"  class="b f18 fl ml10 hand red">×</div>
                          </div>
                        </li>
                        <li class="col-md-6 p0"><span class="" id="totalTechWorker2">技术工人：</span>
                          <div class="input-append">
                            <input id="totalMange" class="span3" type="text" value="${supplierMatEngs.totalTechWorker }" onclick="reason1(this.id,'supplierMatEng.totalTechWorker')"/>
                          <div id="totalTechWorker3"  class="b f18 fl ml10 hand red">×</div>
                          </div>
                        </li>
                      </ul>
                    </div>
                  <div class="col-md-12 add_regist tc">
                    <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="zhancun();">暂存</a>
                    <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="nextStep('${url}');">下一步</a>
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
