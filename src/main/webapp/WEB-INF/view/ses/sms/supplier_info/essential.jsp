<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>
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
        url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
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
     action ="${pageContext.request.contextPath}/supplierQuery/essential.html";
  }
  if(str=="financial"){
    action = "${pageContext.request.contextPath}/supplierQuery/financial.html";
  }
  if(str=="shareholder"){
    action = "${pageContext.request.contextPath}/supplierQuery/shareholder.html";
  }
  if(str=="materialProduction"){
    action = "${pageContext.request.contextPath}/supplierQuery/materialProduction.html";
  }
  if(str=="materialSales"){
    action = "${pageContext.request.contextPath}/supplierQuery/materialSales.html";
  }
  if(str=="engineering"){
    action = "${pageContext.request.contextPath}/supplierQuery/engineering.html";
  }
  if(str=="service"){
    action = "${pageContext.request.contextPath}/supplierQuery/serviceInformation.html";
  }
  if(str=="chengxin"){
    action = "${pageContext.request.contextPath}/supplierQuery/list.html";
  }
  if(str=="item"){
     action = "${pageContext.request.contextPath}/supplierQuery/item.html";
  }
  if(str=="item"){
     action = "${pageContext.request.contextPath}/supplierQuery/item.html";
  }
  $("#form_id").attr("action",action);
  $("#form_id").submit();
}

function downloadFile(fileName){
	  fileName=encodeURI(fileName);
      fileName=encodeURI(fileName);
	  window.location.href="${pageContext.request.contextPath}/supplierQuery/downLoadFile.html?fileName="+fileName;
}
function fanhui(){
      if('${category}'==1){
      window.location.href="${pageContext.request.contextPath}/supplierQuery/selectByCategory.html";
      }else{
	  window.location.href="${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html?address="+encodeURI(encodeURI('${suppliers.address}'))+"&status=${status}";
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
  <div class="container clear">
   <div class="container">
    </div>
  <!--详情开始-->
        <div class="padding-top-10">
          <ul class="nav nav-tabs bgdd">
            <li class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" onclick="tijiao('essential');">基本信息</a></li>
          <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" onclick="tijiao('financial');">财务信息</a></li>
            <li class=""><a aria-expanded="fale" href="#tab-3" data-toggle="tab" onclick="tijiao('shareholder');">股东信息</a></li>
          <li class=""><a aria-expanded="fale" href="#tab-2" data-toggle="tab" onclick="tijiao('materialProduction');">物资-生产型专业信息</a></li>
            <li class=""><a aria-expanded="fale" href="#tab-3" data-toggle="tab" onclick="tijiao('materialSales');">物资-销售型专业信息</a></li>
            <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" onclick="tijiao('engineering');">工程-专业信息</a></li>
            <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" onclick="tijiao('service');">服务-专业信息</a></li>
            <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" onclick="tijiao('item');">品目信息</a></li>
            <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" >产品信息</a></li>
            <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" onclick="tijiao('chengxin');">诚信记录</a></li>
          </ul>
                  <form id="form_id" action="" method="post"  enctype="multipart/form-data">
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
							<td class="hand" colspan="5">${suppliers.businessCert}</td>
						</tr>
					</tbody>
				</table>
</div>
</div>
</body>
</html>
