<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../../../../../index_head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>财务信息</title>
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
input {
  cursor:pointer;
}
textarea {
  cursor:pointer;
}
</style>
<script type="text/javascript">
$(function(){
  layer.alert('点击需要审核的内容,弹出不通过理由框！', {
      title: '审核操作说明：',
      skin: 'layui-layer-molv', //样式类名
      closeBtn: 0,
      shift: 1 //动画类型
  });
})

  //默认不显示勾  
   $(function() {
    $(":input").each(function() {
      $(this).parent("div").find("div").hide();
    });
    
    $("a").each(function() {
      $(this).parent("div").find("div").hide();
    });
  });
  
function reason(id,auditField){
  var supplierId=$("#id").val();
  var id2=id+"2";
  var id3=id+"3";
  var auditFieldName=$("#"+id2+"").text().replace("：",""); //审批的字段名字
  var  auditContent= document.getElementById(""+id+"").value; //审批的字段内容
  var auditType=$("#essential").text(); //审核类型
  var fail = false;
  layer.prompt({title: '请填写不通过的理由：', formType: 2}, function(text){
    $.ajax({
        url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
        type:"post",
        dataType:"json",
        data:"auditType="+auditType+"&auditFieldName="+auditFieldName+"&auditContent="+auditContent+"&suggest="+text+"&supplierId="+supplierId+"&auditField="+auditField,
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
        $("#"+id3+"").show();
        layer.msg("审核不通过的理由是："+text);
      }
        
/*    $("input[name='auditType']").val(auditType);
   $("input[name='auditField']").val(auditField);
   $("input[name='auditContent']").val(auditContent);
   $("input[name='suggest']").val(text);
    
   $("#save_reaeon").submit(); */
    });
}

function reason1(ele,auditField){
  var supplierId=$("#id").val();
  var auditType=$("#essential").text(); //审核类型
  var auditFieldName = $(ele).parents("li").find("span").text().replace("：","");//审批的字段名字
  layer.prompt({title: '请填写不通过的理由：', formType: 2}, function(text){
  var fail = false;
  $.ajax({
      url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
      type:"post",
      data:"&auditFieldName="+auditFieldName+"&suggest="+text+"&supplierId="+supplierId+"&auditType="+auditType+"&auditContent=附件"+"&auditField="+auditField,
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
       $(ele).parents("li").find("div").show(); //显示叉
       layer.msg("审核不通过的理由是："+text);
    }
  });
}


function nextStep(){
  $("#form_id").submit();
}


//文件下載
  function downloadFile(fileName) {
    $("input[name='fileName']").val(fileName);
    $("#download_form_id").submit();
  }
  
  //为只读
  $(function() {
    $(":input").each(function() {
      $(this).attr("readonly", "readonly");
    });
  });
</script>
<script type="text/javascript">
  function zhancun(){
    var supplierId=$("#id").val();
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
            <li class="active"><a id="essential">详细信息</a></li>
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
            <li class=""><a>申请表</a></li>
            <li class=""><a>审核汇总</a></li>
          </ul>
            <div class="tab-content padding-top-20" style="height:1500px;">
              <div class="tab-pane fade active in height-450" id="tab-1">
                <div class=" margin-bottom-0">
                  <form id="form_id" action="${pageContext.request.contextPath}/supplierAudit/financial.html" method="post">
                    <input name="supplierId" id="id" value="${suppliers.id }" type="hidden">
                  </form>
                  <h2 class="f16 jbxx mt40">
                  <i>01</i>企业基本信息
                  </h2>
                  <ul class="list-unstyled list-flow">
                    <li class="col-md-6 p0"><span class="" id="supplierName2">供应商名称：</span>
                      <div class="input-append">
                        <input class="span3" id="supplierName" onclick="reason(this.id,'supplierName')" value="${suppliers.supplierName } " type="text">
                        <div id="supplierName3" class="b f18 fl ml10 hand red">×</div>
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="website2">公司网址：</span>
                      <div class="input-append">
                        <input class="span3" id="website" value="${suppliers.website } " onclick="reason(this.id,'website')" type="text">
                        <div  id="website3" class="b f18 fl ml10 hand red">×</div>
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="foundDate2">成立日期：</span>
                      <div class="input-append">
                        <input class="span3" id="foundDate" value="<fmt:formatDate value='${suppliers.foundDate}' pattern='yyyy-MM-dd'/>"  onclick="reason(this.id,'foundDate')" type="text">
                        <div id="foundDate3" class="b f18 fl ml10 hand red">×</div>
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="businessType2">营业执照登记类型：</span>
                      <div class="input-append">
                        <input class="span3" id="businessType" value="${suppliers.businessType } " type="text" onclick="reason(this.id,'businessType')">
                        <div id="businessType3"  class="b f18 fl ml10 hand red">×</div>
                      </div>
                    </li>
                    <li class="col-md-6 p0"><span class="" id="address2">地址：</span>
                      <div class="input-append">
                        <input class="span3" id="address" value="${suppliers.address } " type="text" onclick="reason(this.id,'address')" >
                        <div id="address3" class="b f18 fl ml10 hand red">×</div>
                      </div>
                    </li>

                    <li class="col-md-6 p0 "><span class="" id="bankName2">开户行名称：</span>
                      <div class="input-append">
                        <input class="span3" id="bankName" value="${suppliers.bankName } "  type="text" onclick="reason(this.id,'bankName')" >
                        <div id="bankName3" class="b f18 fl ml10 hand red">×</div>
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="bankAccount2">开户行账户：</span>
                      <div class="input-append">
                        <input class="span3" id="bankAccount" value="${suppliers.bankAccount } " type="text" onclick="reason(this.id,'bankAccount')" >
                        <div id="bankAccount3" class="b f18 fl ml10 hand red">×</div>
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="postCode2">邮编：</span>
                      <div class="input-append">
                        <input class="span3" id="postCode" value="${suppliers.postCode }" type="text" onclick="reason(this.id,'postCode')" >
                        <div id="postCode3" class="b f18 fl ml10 hand red">×</div>
                      </div>
                     </li>
                   </ul>
                  </div>
                  
                  <div class=" margin-bottom-0 fl">
                  <h2 class="f16 jbxx mt40">
                  <i>02</i>企业基本信息
                  </h2>
                  <ul class="list-unstyled list-flow hand">
                    <li class="col-md-6 p0 "><span class="" onclick="reason1(this,'taxCert');">近三个月完税凭证：</span>
                      <div class="input-append">
                        <c:if test="${suppliers.taxCert !=null}">
                          <a class="span3 green" onclick="downloadFile('${suppliers.taxCert}')" >下载附件</a>
                        </c:if>
                        <c:if test="${suppliers.taxCert == null}">
                          <a class="span3 red">无附件下载</a>
                        </c:if>
                        <div  class="b f18 ml10 fl hand red">×</div>
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" onclick="reason1(this,'billCert');">近三年银行基本账户年末对账单：</span>
                      <div class="input-append">
                        <c:if test="${suppliers.billCert !=null}">
                          <a class="span3 green" onclick="downloadFile('${suppliers.billCert}')">下载附件</a>
                        </c:if>
                        <c:if test="${suppliers.billCert == null}">
                          <a class="span3 red">无附件下载</a>
                        </c:if>
                        <div  class="b f18 ml10 fl hand red">×</div>
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" onclick="reason1(this,'securityCert');">近三个月缴纳社会保险金凭证：</span>
                      <div class="input-append">
                        <c:if test="${suppliers.securityCert != null}">
                          <a class="span3 green" onclick="downloadFile('${suppliers.securityCert}')">下载附件</a>
                        </c:if>
                        <c:if test="${suppliers.securityCert == null}">
                          <a class="span3 red">无附件下载</a>
                        </c:if>
                        <div class="b f18 ml10 fl hand red">×</div>
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" onclick="reason1(this,'breachCert');">近三年内无重大违法记录声明：</span>
                      <div class="input-append">
                        <c:if test="${suppliers.breachCert != null }">
                          <a class="span3 green" onclick="downloadFile('${suppliers.breachCert}')">下载附件</a>
                        </c:if>
                        <c:if test="${suppliers.breachCert == null}">
                          <a class="span3 red">无附件下载</a>
                        </c:if>
                        <div class="b f18 ml10 fl hand red">×</div>
                      </div>
                    </li>
                  </ul>
                </div>
                
                <div class=" margin-bottom-0 fl">
                  <h2 class="f16 jbxx mt40">
                  <i>03</i>法人代表人信息
                  </h2>
                  <ul class="list-unstyled list-flow">
                    <li class="col-md-6 p0 "><span class="" id="legalName2">姓名：</span>
                      <div class="input-append">
                        <input class="span3" id="legalName" value="${suppliers.legalName } " type="text" onclick="reason(this.id,'legalName')">
                        <div id="legalName3" class="b f18 fl ml10 hand red">×</div>
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="legaIdCard2">身份证号：</span>
                      <div class="input-append">
                        <input class="span3" id="legaIdCard" value="${suppliers.legalIdCard } "  type="text" onclick="reason(this.id,'legalIdCard')">
                        <div id="legaIdCard3"  class="b f18 fl ml10 hand red">×</div>
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="legalTelephone2">固定电话：</span>
                      <div class="input-append">
                        <input class="span3" id="legalTelephone" value="${suppliers.legalTelephone } " type="text" onclick="reason(this.id,'legalTelephone')">
                        <div id="legalTelephone3" class="b f18 fl ml10 hand red">×</div>
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="legalMobile2">手机：</span>
                      <div class="input-append">
                        <input class="span3" id="legalMobile" value="${suppliers.legalMobile } " type="text" onclick="reason(this.id,'legalMobile')">
                        <div id="legalMobile3" class="b f18 fl ml10 hand red">×</div>
                      </div>
                    </li>
                  </ul>
                </div>
                
                <div class=" margin-bottom-0 fl">
                  <h2 class="f16 jbxx mt40">
                  <i>04</i>联系人信息
                  </h2>
                  <ul class="list-unstyled list-flow">
                    <li class="col-md-6 p0 "><span class="" id="contactName2">姓名：</span>
                      <div class="input-append">
                        <input class="span3" id="contactName" value="${suppliers.contactName } " type="text" onclick="reason(this.id,'contactName')" >
                        <div id="contactName3" class="b f18 fl ml10 hand red">×</div>
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="contactFax2">传真：</span>
                      <div class="input-append">
                        <input class="span3" id="contactFax" value="${suppliers.contactFax } "  type="text" onclick="reason(this.id,'contactFax')" >
                        <div id="contactFax3" class="b f18 fl ml10 hand red">×</div>
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="contactTelephone1">固定电话：</span>
                      <div class="input-append">
                        <input class="span3" id="contactTelephone" value="${suppliers.contactTelephone } " type="text" onclick="reason(this.id,'contactTelephone')" >
                        <div id="contactTelephone3" class="b f18 fl ml10 hand red">×</div>
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="contactMobile2">手机：</span>
                      <div class="input-append">
                        <input class="span3" id="contactMobile" value="${suppliers.contactMobile } " type="text" onclick="reason(this.id,'contactMobile')" >
                        <div id="contactMobile3" onclick="reason(this.id)" class="b f18 fl ml10 hand red">×</div>
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="contactEmail2">邮箱：</span>
                      <div class="input-append">
                        <input class="span3" id="contactEmail" value="${suppliers.contactEmail } " type="text" onclick="reason(this.id,'contactEmail')" >
                        <div id="contactEmail3" onclick="reason(this.id)" class="b f18 fl ml10 hand red">×</div>
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="contactAddress2">地址：</span>
                      <div class="input-append">
                        <input class="span3" id="contactAddress" value="${suppliers.contactAddress } " type="text" onclick="reason(this.id,'contactAddress')" >
                        <div id="contactAddress3" onclick="reason(this.id)" class="b f18 fl ml10 hand red">×</div>
                      </div>
                    </li>
                  </ul>
                </div>
                
                <div class=" margin-bottom-0 fl">
                  <h2 class="f16 jbxx mt40">
                  <i>05</i>营业执照
                  </h2>
                  <ul class="list-unstyled list-flow">
                    <li class="col-md-6 p0 "><span class="" id="creditCode2">统一社会信用代码：</span>
                      <div class="input-append">
                        <input class="span3" id="creditCode" value="${suppliers.creditCode } " type="text" onclick="reason(this.id,'creditCode')" >
                        <div id="creditCode3" class="b f18 fl ml10 hand red">×</div>
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="registAuthority2">登记机关：</span>
                      <div class="input-append">
                        <input class="span3" id="registAuthority" value="${suppliers.registAuthority } "  type="text" onclick="reason(this.id,'registAuthority')" >
                        <div id="registAuthority3" class="b f18 fl ml10 hand red">×</div>
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="registFund2">注册资本：</span>
                      <div class="input-append">
                        <input class="span3" id="registFund" value="${suppliers.registFund } " type="text" onclick="reason(this.id,'registFund')" >
                        <div id="registFund3" onclick="reason(this.id)" class="b f18 fl ml10 hand red">×</div>
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="businessEndDate2">营业开始时间：</span>
                      <div class="input-append">
                        <input class="span3" id="businessEndDate" onclick="reason(this.id,'businessStartDate')" value="<fmt:formatDate value='${suppliers.businessStartDate}' pattern='yyyy-MM-dd'/>"type="text"/>
                        <div id="businessEndDate3" onclick="reason(this.id)" class="b f18 fl ml10 hand red">×</div>
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="businessStartDate2">营业截止时间：</span>
                      <div class="input-append">
                       <input class="span3" id="businessStartDate" onclick="reason(this.id,'businessStartDate')" value="<fmt:formatDate value='${suppliers.businessEndDate}' pattern='yyyy-MM-dd'/>"type="text"/>
                        <div id="businessStartDate3" onclick="reason(this.id)" class="b f18 fl ml10 hand red">×</div>
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="fl" id="businessAddress2">生产或经营地址：</span>
                      <div class="input-append">
                        <input class="span3" id="businessAddress" value="${suppliers.businessAddress } " type="text" onclick="reason(this.id,'businessAddress')" >
                        <div id="businessAddress3" class="b f18 fl ml10 hand red">×</div>
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="businessPostCode2">邮编：</span>
                      <div class="input-append">
                        <input class="span3" id="businessPostCode" value="${suppliers.businessPostCode } " type="text" onclick="reason(this.id,'businessPostCode')" >
                        <div id="businessPostCode3" class="b f18 fl ml10 hand red">×</div>
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" onclick="reason1(this,'businessCert');">营业执照：</span>
                      <div class="input-append">
                        <c:if test="${suppliers.businessCert != null }">
                          <a class="span3 green" onclick="downloadFile('${suppliers.businessCert}')">下载附件</a>
                        </c:if>
                        <c:if test="${suppliers.businessCert == null}">
                          <a class="span3 red">无附件下载</a>
                        </c:if>
                        <div class="b f18 ml10 fl hand red">×</div>
                      </div>
                    </li>
                    <li class="col-md-12 p0 mt10"><span class="fl" id="businessScope2">经营范围：</span>
                      <div class="col-md-9 mt5">
                        <div class="row">
                          <textarea class="text_area col-md-12" id="businessScope" onclick="reason(this.id,'businessScope')" >${suppliers.businessScope }</textarea>
                          <div id="businessScope3" class="b f18 fl ml10 hand red">×</div>
                        </div>
                      </div>
                    </li>
                  </ul>
                 </div> 
                 <div class=" margin-bottom-0 fl">
                  <h2 class="f16 jbxx mt40">
                  <i>06</i>境外分支
                  </h2>
                  <ul class="list-unstyled list-flow">
                    <li class="col-md-6 p0 "><span class="" id="overseasBranch2">境外分支机构：</span>
                      <div class="input-append">
                        <input class="span3" id="overseasBranch" value="${suppliers.overseasBranch } " type="text" onclick="reason(this.id,'overseasBranch')" >
                        <div id="overseasBranch3" class="b f18 fl ml10 hand red">×</div>
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="branchCountry2">境外分支所在国家：</span>
                      <div class="input-append">
                        <input class="span3" id="branchCountry" value="${suppliers.branchCountry } " type="text" onclick="reason(this.id,'branchCountry')" >
                        <div id="branchCountry3" class="b f18 fl ml10 hand red">×</div>
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="branchAddress2">分支地址：</span>
                      <div class="input-append">
                        <input class="span3" id="branchAddress" value="${suppliers.branchAddress } " type="text" onclick="reason(this.id,'branchAddress')" >
                        <div id="branchAddress3" class="b f18 fl ml10 hand red">×</div>
                      </div>
                    </li>
                    <li class="col-md-6 p0 "><span class="" id="branchName2">机构名称：</span>
                      <div class="input-append">
                        <input class="span3" id="branchName" value="${suppliers.branchName } " type="text" onclick="reason(this.id,'branchName')" >
                        <div id="branchName3" class="b f18 fl ml10 hand red">×</div>
                      </div>
                    </li>
                    <li class="col-md-12 p0 mt10"><span class="fl" id="branchBusinessScope2">分支生产经营范围：</span>
                      <div class="col-md-9 mt5">
                        <div class="row">
                          <textarea class="text_area col-md-12" id="branchBusinessScope" onclick="reason(this.id,'branchBusinessScope')" >${suppliers.branchBusinessScope }</textarea>
                          <div id="branchBusinessScope3" onclick="reason(this.id)" class="b f18 fl ml10 hand red">×</div>
                        </div>
                      </div>
                    </li>
                  </ul>
	              </div>
              </div>
            </div>
            <div class="col-md-12 add_regist tc">
             <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="zhancun();">暂存</a>
             <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="nextStep();">下一步</a>
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
