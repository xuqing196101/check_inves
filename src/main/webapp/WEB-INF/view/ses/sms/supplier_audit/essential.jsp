<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../../../common.jsp"%>
<%@ taglib prefix="up" uri="/tld/upload"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>详细信息</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript">
$(function(){
  layer.alert('点击审核项,弹出不通过理由框！', {
      title: '审核操作说明：',
      skin: 'layui-layer-molv', //样式类名
      closeBtn: 0,
      offset:'300px',
      shift: 1 //动画类型
  });
});

  //隐藏叉 
   $(function() {
    $(":input").each(function() {
      $(this).parent("div").find("div").hide();
      
      var onmouseover = "this.style.border='solid 1px #FF0000'";
      var onmouseout = "this.style.border='solid 1px #D3D3D3'";
       $(this).attr("onmouseover",onmouseover);
       $(this).attr("onmouseout",onmouseout);
    });
    
    $("li").each(function() {
      $(this).find("p").hide();
    });
  });

function reason(id,auditField){
  var offset = "";
  if (window.event) {
    e = event || window.event;
    var x = "";
    var y = "";
    x = e.clientX + 20 + "px";
    y = e.clientY + 20 + "px";
    offset = [y, x];
  } else {
      offset = "200px";
  }
  var supplierId=$("#id").val();
  var id2=id+"2";
  var id3=id+"3";
  var auditFieldName=$("#"+id2+"").text().replace("：",""); //审批的字段名字
  var  auditContent= document.getElementById(""+id+"").value; //审批的字段内容
  var index = layer.prompt({
      title : '请填写不通过的理由：', 
      formType : 2, 
      offset : offset
   }, 
      function(text){
	    $.ajax({
	        url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
	        type:"post",
	        dataType:"json",
	        data:"auditType=basic_page"+"&auditFieldName="+auditFieldName+"&auditContent="+auditContent+"&suggest="+text+"&supplierId="+supplierId+"&auditField="+auditField,
	        success:function(result){
	          result = eval("(" + result + ")");
	          if(result.msg == "fail"){
	            layer.msg('该条信息已审核过！', {	            
	                shift: 6, //动画类型
	                offset:'300px'
	            });
	          }
	        }
	      });
        $("#"+id3+"").show();
        $("#"+id3+"").parents("li").find("input").css("padding-right","30px");
        layer.close(index);
/*    $("input[name='auditType']").val(auditType);
   $("input[name='auditField']").val(auditField);
   $("input[name='auditContent']").val(auditContent);
   $("input[name='suggest']").val(text);
    
   $("#save_reaeon").submit(); */
    });
    
}

function reason1(ele,auditField){
  var supplierId=$("#id").val();
  var auditFieldName = $(ele).parents("li").find("span").text().replace("：","").replace("view","");//审批的字段名字
  var index = layer.prompt({title: '请填写不通过的理由：', formType: 2, offset:'300px'}, function(text){
  $.ajax({
      url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
      type:"post",
      data:"&auditFieldName="+auditFieldName+"&suggest="+text+"&supplierId="+supplierId+"&auditType=basic_page"+"&auditContent=附件"+"&auditField="+auditField,
      dataType:"json",
      success:function(result){
	      result = eval("(" + result + ")");
	      if(result.msg == "fail"){
	        layer.msg('该条信息已审核过！', {
                shift: 6, //动画类型
                offset:'300px'
            });
	      }
    }
    });
       $(ele).parents("li").find("p").show(); //显示叉
       layer.close(index);
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
/*   function zhancun(){
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
  } */
</script>
</head>
    <body>
        <!--面包屑导航开始-->
        <div class="margin-top-10 breadcrumbs ">
            <div class="container">
                <ul class="breadcrumb margin-left-0">
                    <li><a href="#"> 首页</a></li><li><a href="#">供应商管理</a></li><li><a href="#">供应商审核</a></li>
                </ul>
            </div>
        </div> 
        <div class="container container_box">
            <div class=" content height-350">
                <div class="col-md-12 tab-v2 job-content">
	                <%-- <ul class="nav nav-tabs bgdd">
			            <li class="active"><a >详细信息</a></li>
			            <li class=""><a >财务信息</a></li>
			            <li class=""><a >股东信息</a></li>
			            <c:if test="${fn:contains(supplierTypeNames, '生产')}">
			            <li class=""><a >物资-生产专业信息</a></li>
			            </c:if>
			            <c:if test="${fn:contains(supplierTypeNames, '销售')}">
			            <li class=""><a >物资-销售专业信息</a></li>
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
	                </ul> --%>
	                <ul class="flow_step">
	                    <li class="active">
	                        <a aria-expanded="true" href="#tab-1" data-toggle="tab">详细信息</a>
	                        <i></i>
	                    </li>
	                    <li>
	                        <a aria-expanded="false" href="#tab-2" >财务信息</a>
	                        <i></i>                            
	                    </li>
	                    <li>
	                        <a aria-expanded="false" href="#tab-3" >股东信息</a>
	                        <i></i>
	                    </li>
	                    <c:if test="${fn:contains(supplierTypeNames, '生产')}">
		                    <li>
		                        <a aria-expanded="false" href="#tab-4" >生产信息</a>
		                        <i></i>
		                    </li>
	                    </c:if>
	                    <c:if test="${fn:contains(supplierTypeNames, '销售')}">
		                    <li>
		                        <a aria-expanded="false" href="#tab-4" >销售信息</a>
		                        <i></i>
		                    </li>
	                    </c:if>
	                    <c:if test="${fn:contains(supplierTypeNames, '工程')}">
		                    <li>
		                        <a aria-expanded="false" href="#tab-4" >工程信息</a>
		                        <i></i>
		                    </li>
		                </c:if>
		                <c:if test="${fn:contains(supplierTypeNames, '服务')}">
		                    <li>
		                        <a aria-expanded="false" href="#tab-4" >服务信息</a>
		                        <i></i>
		                    </li>
	                    </c:if>
	                    <li>
	                        <a aria-expanded="false" href="#tab-4" >产品信息</a>
	                        <i></i>
	                    </li>
	                    <li>
	                        <a aria-expanded="false" href="#tab-4" >申请表</a>
	                        <i></i>
	                    </li>
	                    <li>
	                        <a aria-expanded="false" href="#tab-4" >审核汇总</a>
	                    </li>
                    </ul>
	                
                    <form id="form_id" action="${pageContext.request.contextPath}/supplierAudit/financial.html" method="post">
                        <input name="supplierId" id="id" value="${suppliers.id }" type="hidden">
                    </form>
                    
                    <h2 class="count_flow"><i>1</i>企业基本信息</h2>
                    <ul class="ul_list">
	                    <li class="col-md-3 col-sm-6 col-xs-12 pl15">
	                       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="supplierName2">供应商名称：</span>
	                       <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
	                           <input id="supplierName" onclick="reason(this.id,'supplierName')" value="${suppliers.supplierName } " type="text">
	                           <div id="supplierName3" class="abolish">×</div>
	                       </div>
	                    </li>
	                    <li class="col-md-3 col-sm-6 col-xs-12">
	                       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="website2">公司网址：</span>
	                       <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
	                           <input class="hand " id="website" value="${suppliers.website } " onclick="reason(this.id,'website')" type="text">
	                           <div  id="website3" class="abolish">×</div>
	                       </div>
	                    </li>
	                    <li class="col-md-3 col-sm-6 col-xs-12">
	                       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="foundDate2">成立日期：</span>
	                       <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
	                           <input id="foundDate" class="hand " value="<fmt:formatDate value='${suppliers.foundDate}' pattern='yyyy-MM-dd'/>"  onclick="reason(this.id,'foundDate')" type="text">
	                           <div id="foundDate3" class="abolish">×</div>
	                       </div>
	                    </li>
	                    <li class="col-md-3 col-sm-6 col-xs-12">
	                       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="businessType2">营业执照登记类型：</span>
	                       <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
	                           <input id="businessType" class="hand " value="${suppliers.businessType } " type="text" onclick="reason(this.id,'businessType')">
	                           <div id="businessType3"  class="abolish">×</div>
	                       </div>
	                    </li>
	                    <li class="col-md-3 col-sm-6 col-xs-12">
	                       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="address2">地址：</span>
	                       <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
	                           <input id="address" class="hand " value="${suppliers.address } " type="text" onclick="reason(this.id,'address')" >
	                           <div id="address3" class="abolish">×</div>
	                       </div>
	                    </li>
	                    <li class="col-md-3 col-sm-6 col-xs-12">
	                       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="bankName2">开户行名称：</span>
	                       <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
	                           <input id="bankName" class="hand " value="${suppliers.bankName } "  type="text" onclick="reason(this.id,'bankName')" >
	                           <div id="bankName3" class="abolish">×</div>
	                       </div>
	                    </li>
	                    <li class="col-md-3 col-sm-6 col-xs-12">
	                       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="bankAccount2">开户行账户：</span>
	                       <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
	                           <input id="bankAccount" class="hand " value="${suppliers.bankAccount } " type="text" onclick="reason(this.id,'bankAccount')" >
	                           <div id="bankAccount3" class="abolish">×</div>
	                       </div>
	                    </li>
	                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="postCode2">邮编：</span>
	                       <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
	                           <input id="postCode" class="hand " value="${suppliers.postCode }" type="text" onclick="reason(this.id,'postCode')" >
	                           <div id="postCode3" class="abolish">×</div>
	                       </div>
	                   </li>
                   </ul>

                  <h2 class="count_flow"><i>2</i>资质资信</h2>
                  <ul class="ul_list hand">
                    <li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="hand" onclick="reason1(this,'taxCert');" onmouseover="this.style.border='solid 1px #FF0000'" onmouseout="this.style.border='solid 1px #FFFFFF'" >近三个月完税凭证：</span>
                       <div class="col-md-12 col-xs-12 col-sm-12 p0">
                        <up:show showId="taxcert_show" delete="false" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierTaxCert}"/>
                        <p class="b f18 ml10 red">×</p>
                       </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="hand" onclick="reason1(this,'billCert');" onmouseover="this.style.border='solid 1px #FF0000'" onmouseout="this.style.border='solid 1px #FFFFFF'" >近三年银行基本账户年末对账单：</span>
                      <div class="col-md-12 col-xs-12 col-sm-12 p0">
                        <up:show showId="billcert_show" delete="false" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBillCert}"/>
                        <p class="b f18 ml10 red">×</p>
                      </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="hand" onclick="reason1(this,'securityCert');" onmouseover="this.style.border='solid 1px #FF0000'" onmouseout="this.style.border='solid 1px #FFFFFF'" >近三个月缴纳社会保险金凭证：</span>
                      <div class="col-md-12 col-xs-12 col-sm-12 p0">
                        <up:show showId="curitycert_show" delete="false" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierSecurityCert}"/>
                        <p class="b f18 ml10 red">×</p>
                      </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="hand" onclick="reason1(this,'breachCert');" onmouseover="this.style.border='solid 1px #FF0000'" onmouseout="this.style.border='solid 1px #FFFFFF'" >近三年内无重大违法记录声明：</span>
                     <div class="col-md-12 col-xs-12 col-sm-12 p0">
                      <up:show showId="bearchcert_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show" delete="false" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBearchCert}"/>
                      <p class="b f18 ml10 red">×</p>
                     </div>
                    </li>
                  </ul>
                
                  <h2 class="count_flow"><i>3</i>法人代表人信息</h2>
                  <ul class="ul_list">
                    <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                        <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="legalName2">姓名：</span>
                        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                            <input id="legalName" class="hand " value="${suppliers.legalName } " type="text" onclick="reason(this.id,'legalName')">
                            <div id="legalName3" class="abolish">×</div>
                      </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="legaIdCard2">身份证号：</span>
                        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="legaIdCard" class="hand " value="${suppliers.legalIdCard } "  type="text" onclick="reason(this.id,'legalIdCard')">
                        <div id="legaIdCard3"  class="abolish">×</div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="legalTelephone2">固定电话：</span>
                      <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="legalTelephone" class="hand "  value="${suppliers.legalTelephone } " type="text" onclick="reason(this.id,'legalTelephone')">
                        <div id="legalTelephone3" class="abolish">×</div>
                      </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="legalMobile2">手机：</span>
                      <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="legalMobile" class="hand " value="${suppliers.legalMobile } " type="text" onclick="reason(this.id,'legalMobile')">
                        <div id="legalMobile3" class="abolish">×</div>
                      </div>
                    </li>
                  </ul>

                  <h2 class="count_flow"><i>4</i>联系人信息</h2>
                  <ul class="ul_list">
                    <li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="contactName2">姓名：</span>
                      <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="contactName" class="hand " value="${suppliers.contactName } " type="text" onclick="reason(this.id,'contactName')" >
                        <div id="contactName3" class="abolish">×</div>
                      </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="contactFax2">传真：</span>
                      <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="contactFax" class="hand " value="${suppliers.contactFax } "  type="text" onclick="reason(this.id,'contactFax')" >
                        <div id="contactFax3" class="abolish">×</div>
                      </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="contactTelephone1">固定电话：</span>
                      <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="contactTelephone" class="hand " value="${suppliers.contactTelephone } " type="text" onclick="reason(this.id,'contactTelephone')" >
                        <div id="contactTelephone3" class="abolish">×</div>
                      </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="contactMobile2">手机：</span>
                      <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="contactMobile" class="hand " value="${suppliers.contactMobile } " type="text" onclick="reason(this.id,'contactMobile')" >
                        <div id="contactMobile3" onclick="reason(this.id)" class="abolish">×</div>
                      </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="contactEmail2">邮箱：</span>
                      <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="contactEmail" class="hand " value="${suppliers.contactEmail } " type="text" onclick="reason(this.id,'contactEmail')" >
                        <div id="contactEmail3" onclick="reason(this.id)" class="abolish">×</div>
                      </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="contactAddress2">地址：</span>
                      <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 p0">
                        <input id="contactAddress" class="hand " value="${suppliers.contactAddress } " type="text" onclick="reason(this.id,'contactAddress')" >
                        <div id="contactAddress3" onclick="reason(this.id)" class="abolish">×</div>
                      </div>
                    </li>
                  </ul>

                  <h2 class="count_flow"><i>5</i>营业执照</h2>
                  <ul class="ul_list">
                    <li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="creditCode2">统一社会信用代码：</span>
                      <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="creditCode" class="hand " value="${suppliers.creditCode } " type="text" onclick="reason(this.id,'creditCode')" >
                        <div id="creditCode3" class="abolish">×</div>
                      </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="registAuthority2">登记机关：</span>
                      <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="registAuthority" class="hand " value="${suppliers.registAuthority } "  type="text" onclick="reason(this.id,'registAuthority')" >
                        <div id="registAuthority3" class="abolish">×</div>
                      </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="registFund2">注册资本：</span>
                      <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="registFund" class="hand " value="${suppliers.registFund } " type="text" onclick="reason(this.id,'registFund')" >
                        <div id="registFund3" onclick="reason(this.id)" class="abolish">×</div>
                      </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="businessEndDate2">营业开始时间：</span>
                      <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="businessEndDate" class="hand " onclick="reason(this.id,'businessStartDate')" value="<fmt:formatDate value='${suppliers.businessStartDate}' pattern='yyyy-MM-dd'/>"type="text"/>
                        <div id="businessEndDate3" onclick="reason(this.id)" class="abolish">×</div>
                      </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="businessStartDate2">营业截止时间：</span>
                      <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="businessStartDate" class="hand " onclick="reason(this.id,'businessStartDate')" value="<fmt:formatDate value='${suppliers.businessEndDate}' pattern='yyyy-MM-dd'/>"type="text"/>
                        <div id="businessStartDate3" onclick="reason(this.id)" class="abolish">×</div>
                      </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="fl" id="businessAddress2">生产或经营地址：</span>
                      <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="businessAddress" class="hand " value="${suppliers.businessAddress } " type="text" onclick="reason(this.id,'businessAddress')" >
                        <div id="businessAddress3" class="abolish">×</div>
                      </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="businessPostCode2">邮编：</span>
                      <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="businessPostCode" class="hand " value="${suppliers.businessPostCode } " type="text" onclick="reason(this.id,'businessPostCode')" >
                        <div id="businessPostCode3" class="abolish">×</div>
                      </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" onclick="reason1(this,'businessCert');">营业执照：</span>
                      <%-- <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <c:if test="${suppliers.businessCert != null }">
                          <a class="span5 green" onclick="downloadFile('${suppliers.businessCert}')">下载附件</a>
                        </c:if>
                        <c:if test="${suppliers.businessCert == null}">
                          <a class="span5 red">无附件下载</a>
                        </c:if>
                        <div class="b f18 ml10 fl hand red">×</div>
                      </div> --%>
                      <up:show showId="business_show" delete="false" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" /> 
                    </li>
                    <li class="col-md-12 col-sm-12 col-xs-12">
                      <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="businessScope2">经营范围：</span>
                      <div class="col-md-12 col-sm-12 col-xs-12 p0">
                          <textarea class="col-md-12 col-sm-12 col-xs-12" style="height:130px" id="businessScope" onclick="reason(this.id,'businessScope')" >${suppliers.businessScope }</textarea>
                          <div id="businessScope3" class="b f18 fl ml10 hand red">×</div>
                      </div>
                    </li>
                  </ul>

                  <h2 class="count_flow"><i>6</i>境外分支</h2>
                  <ul class="ul_list">
                    <li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="overseasBranch2">境外分支机构：</span>
                      <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="overseasBranch" class="hand " value="${suppliers.overseasBranch } " type="text" onclick="reason(this.id,'overseasBranch')" >
                        <div id="overseasBranch3" class="abolish">×</div>
                      </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="branchCountry2">境外分支所在国家：</span>
                      <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="branchCountry" class="hand " value="${suppliers.branchCountry } " type="text" onclick="reason(this.id,'branchCountry')" >
                        <div id="branchCountry3" class="abolish">×</div>
                      </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12 "><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="branchAddress2">分支地址：</span>
                      <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="branchAddress" class="hand " value="${suppliers.branchAddress } " type="text" onclick="reason(this.id,'branchAddress')" >
                        <div id="branchAddress3" class="abolish">×</div>
                      </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="branchName2">机构名称：</span>
                      <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="branchName" class="hand " value="${suppliers.branchName } " type="text" onclick="reason(this.id,'branchName')" >
                        <div id="branchName3" class="abolish">×</div>
                      </div>
                    </li>
                    <li class="col-md-12 col-sm-12 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" id="branchBusinessScope2">分支生产经营范围：</span>
                      <div class="col-md-12 col-sm-12 col-xs-12 p0">
                          <textarea class="col-md-12 col-sm-12 col-xs-12 hand" style="height:130px" id="branchBusinessScope" onclick="reason(this.id,'branchBusinessScope')" >${suppliers.branchBusinessScope }</textarea>
                          <div id="branchBusinessScope3" onclick="reason(this.id)" class="abolish">×</div>
                      </div>
                    </li>
                  </ul>
	            </div>
	            <div class="col-md-12 col-sm-12 col-xs-12 add_regist tc">
	             <!-- <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="zhancun();">暂存</a> -->
	                <a class="btn"  type="button" onclick="nextStep();">下一步</a>
                </div>
            </div>
		    <form target="_blank" id="download_form_id" action="${pageContext.request.contextPath}/supplierAudit/download.html" method="post">
		        <input type="hidden" name="fileName" />
		    </form>
        </div> 
    </body>
</html>
