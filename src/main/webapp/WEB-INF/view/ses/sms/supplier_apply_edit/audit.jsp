<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="up" uri="/tld/upload"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
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
    //鼠标移动显示全部内容
	function out(content){
		layer.msg(content, {
		        offset:'100px',
			    skin: 'demo-class',
				shade:false,
				area: ['600px'],
				time : 0    //默认消息框不关闭
			});//去掉msg图标
	}
	
	$(function(){
		 $(":input").each(function() {
      		$(this).attr("readonly", "readonly");
    	});
	});
	
function reason(ele){
  var auditField = $(ele).parents("li").find("span").text().replace("：","");//审批的字段名字
  var content=$("#"+ele.id+"3").val();
    layer.prompt({title: '请填写不通过理由', formType: 2,offset:'200px'}, function(text){
      $.ajax({
          url:"${pageContext.request.contextPath}/supplier_edit/saveReason.html",
          type:"post",
          data:"&name="+auditField+"&auditReason="+text+"&content="+content,
        });
        layer.msg("审核不通过的理由是："+text,{offset:'200px'});
      });
}
</script>
</head>  
<body>
 <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">供应商管理</a></li><li class="active"><a href="#">供应商变更</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
  <!-- 项目戳开始 -->
  <div class="container clear">
  <!--详情开始-->
  <div class="container content height-350">
    <div class="row magazine-page">
      <div class="col-md-12 tab-v2 job-content">
        <div class="padding-top-10">
          <ul class="nav nav-tabs bgdd">
			<li class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" id="essential"  onclick="window.location.href='${pageContext.request.contextPath}/supplier_edit/audit.html'" >基本信息</a></li>
			<li class=""><a aria-expanded="true" href="#tab-2" data-toggle="tab" id="essential" onclick="window.location.href='${pageContext.request.contextPath}/supplier_edit/reasonList.html'" >问题汇总</a></li>
          </ul>
            <div class="tab-content padding-top-20">
              <div class="tab-pane fade active in height-450" id="tab-1">
                <div class=" margin-bottom-0">
					        <h2 class="count_flow"><i>1</i>企业基本信息</h2>
					        <ul class="ul_list">
					          <li class="col-md-3 margin-0 padding-0 "><span class="" id="supplierName2">供应商名称：</span>
					            <div class="input-append">
					              <input type="hidden" name="id" value="${se.id }" >
					              <c:if test="${supplier.supplierName ne se.supplierName }">
					                  <input class="span5" id="supplierName3" name="supplierName" onmouseover="out('${supplier.supplierName }')"  value="${se.supplierName }" type="text">
                        			  <div id="supplierName" onclick="reason(this)" class="b f18 fl ml10 hand red">×</div>
					              </c:if>
					               <c:if test="${supplier.supplierName eq se.supplierName }">
					                  <input class="span5" id="supplierName3" name="supplierName"   value="${se.supplierName }" type="text">
					              </c:if>
					            </div>
					          </li>
					          <li class="col-md-3 margin-0 padding-0  "><span class="" id="website2">公司网址：</span>
					            <div class="input-append">
					               <c:if test="${supplier.website ne se.website }">
					               	   <input class="span5" id="website3" name="website" onmouseover="out('${supplier.website }')" value="${se.website }"  type="text">
                        			  <div id="website" onclick="reason(this)" class="b f18 fl ml10 hand red">×</div>
					              </c:if>
					               <c:if test="${supplier.website eq se.website }">
					               	   <input class="span5" id="website3" name="website"  value="${se.website }"  type="text">
					              </c:if>
					            </div>
					          </li>
					          <li class="col-md-3 margin-0 padding-0  "><span class="" id="foundDate2">成立日期：</span>
					            <div class="input-append">
					              <c:if test="${supplier.foundDate ne se.foundDate }">
					                  <input class="span5" id="foundDate3" name="foundDate" onmouseover="out('<fmt:formatDate value="${supplier.foundDate }" pattern="yyyy-MM-dd" />')" value='<fmt:formatDate value="${se.foundDate }" pattern="yyyy-MM-dd" />' type="text">
                        			  <div id="foundDate" onclick="reason(this)" class="b f18 fl ml10 hand red">×</div>
					              </c:if>
					                <c:if test="${supplier.foundDate eq se.foundDate }">
					                  <input class="span5" id="foundDate3" name="foundDate" value='<fmt:formatDate value="${se.foundDate }" pattern="yyyy-MM-dd" />' type="text">
					              </c:if>
					            </div>
					          </li>
					          <li class="col-md-3 margin-0 padding-0  "><span class="" id="businessType2">营业执照登记类型：</span>
					            <div class="input-append">
					            	 <c:if test="${supplier.businessType ne se.businessType }">
					            	  <input class="span5" id="businessType3" name="businessType" onmouseover="out('${supplier.businessType }')" value="${se.businessType }" type="text">
                        			  <div id="businessType" onclick="reason(this)" class="b f18 fl ml10 hand red">×</div>
					                 </c:if>
					                  <c:if test="${supplier.businessType eq se.businessType }">
					            	  <input class="span5" id="businessType3" name="businessType" value="${se.businessType }" type="text">
					                 </c:if>
					            </div>
					          </li>
					          <li class="col-md-3 margin-0 padding-0 "><span class="" id="address2">地址：</span>
					            <div class="input-append">
					                 <c:if test="${supplier.address ne se.address }">
					                  <input class="span5" id="address3" name="address" onmouseover="out('${supplier.address }')" value="${se.address }" type="text">
                        			  <div id="address" onclick="reason(this)" class="b f18 fl ml10 hand red">×</div>
					                 </c:if>
					                 <c:if test="${supplier.address eq se.address }">
					                  <input class="span5" id="address3" name="address" value="${se.address }" type="text">
					                 </c:if>
					            </div>
					          </li>
					          <li class="col-md-3 margin-0 padding-0  "><span class="" id="bankName2">开户行名称：</span>
					            <div class="input-append">
					                 <c:if test="${supplier.bankName ne se.bankName }">
					                  <input class="span5" id="bankName3" name="bankName" onmouseover="out('${supplier.bankName }')" value="${se.bankName }"  type="text">
                        			  <div id="bankName" onclick="reason(this)" class="b f18 fl ml10 hand red">×</div>
					                 </c:if>
					                 <c:if test="${supplier.bankName eq se.bankName }">
					                  <input class="span5" id="bankName3" name="bankName" value="${se.bankName }"  type="text">
					                 </c:if>
					            </div>
					          </li>
					          <li class="col-md-3 margin-0 padding-0  "><span class="" id="bankAccount2">开户行账户：</span>
					            <div class="input-append">
					                 <c:if test="${supplier.bankAccount ne se.bankAccount }">
					               		 <input class="span5" id="bankAccount3" onmouseover="out('${supplier.bankAccount }')" name="bankAccount" value="${se.bankAccount }" type="text">
                        			     <div id="bankAccount" onclick="reason(this)" class="b f18 fl ml10 hand red">×</div>
					                 </c:if>
					                  <c:if test="${supplier.bankAccount eq se.bankAccount }">
					               		 <input class="span5" id="bankAccount3" name="bankAccount" value="${se.bankAccount }" type="text">
					                 </c:if>
					            </div>
					          </li>
					          <li class="col-md-3 margin-0 padding-0  "><span class="" id="postCode2">邮编：</span>
					            <div class="input-append">
					               <c:if test="${supplier.postCode ne se.postCode }">
					               <input class="span5" id="postCode3" name="postCode" onmouseover="out('${supplier.postCode }')" value="${se.postCode }" type="text">
                        			  <div id="postCode" onclick="reason(this)" class="b f18 fl ml10 hand red">×</div>
					                 </c:if>
					                 <c:if test="${supplier.postCode eq se.postCode }">
					               <input class="span5" id="postCode3" name="postCode" value="${se.postCode }" type="text">
					                 </c:if>
					            </div>
					          </li>
					        </ul>
					       <h2 class="count_flow"><i>2</i>资质资信</h2>
		                  <ul class="ul_list hand">
		                     <li id="tax_li_id" class="col-md-3 margin-0 padding-0"><span class="hand">近三个月完税凭证：</span>
								<up:show showId="taxcert_show" delete="flase"  groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,auditopinion_show,auditopinion_show" businessId="${supplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierTaxCert}"/>
							</li>
							<li id="bill_li_id" class="col-md-3 margin-0 padding-0 "><span class="hand">近三年银行基本账户年末对账单：</span>
								<up:show showId="billcert_show" delete="flase" groups="" businessId="${supplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBillCert}"/>
							</li>
							<li id="security_li_id" class="col-md-3 margin-0 padding-0 "><span class="hand">近三个月缴纳社会保险金凭证：</span>
								<up:show showId="curitycert_show" delete="flase" groups="" businessId="${supplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierSecurityCert}"/>
							</li>
							<li id="breach_li_id" class="col-md-3 margin-0 padding-0 "><span class="hand">近三年内无重大违法记录声明：</span>
								<up:show showId="bearchcert_show" delete="flase" groups="" businessId="${supplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBearchCert}"/>
							</li>
		                  </ul>
					      
					      
					       <h2 class="count_flow"><i>3</i>法人代表人信息 </h2>
					       <ul class="ul_list">
											<li class="col-md-3 margin-0 padding-0 "><span class=""> 姓名：</span>
												<div class="input-append">
													 <c:if test="${supplier.legalName ne se.legalName }">
													    <input class="span5" type="text" id="legalName3" name="legalName" onmouseover="out('${supplier.legalName }')" value="${se.legalName}" />
                        			  					<div id="legalName" onclick="reason(this)" class="b f18 fl ml10 hand red">×</div>
					                 				</c:if>
					                 				<c:if test="${supplier.legalName eq se.legalName }">
													    <input class="span5" type="text" name="legalName" value="${se.legalName}" />
					                 				</c:if>
												</div>
											</li>
											<li class="col-md-3 margin-0 padding-0 "><span class=""> 身份证号：</span>
												<div class="input-append">
													 <c:if test="${supplier.legalIdCard ne se.legalIdCard }">
													    <input class="span5" type="text" id="legalIdCard3" name="legalIdCard" onmouseover="out('${supplier.legalIdCard }')" value="${se.legalIdCard}" />
                        			  					<div id="legalIdCard" onclick="reason(this)" class="b f18 fl ml10 hand red">×</div>
					                 				</c:if>
					                 				 <c:if test="${supplier.legalIdCard eq se.legalIdCard }">
													    <input class="span5" type="text" name="legalIdCard" value="${se.legalIdCard}" />
					                 				</c:if>
												</div>
											</li>
											<li class="col-md-3 margin-0 padding-0 "><span class=""> 固定电话：</span>
												<div class="input-append">
													 <c:if test="${supplier.legalTelephone ne se.legalTelephone }">
													    <input class="span5" type="text" id="legalTelephone3" name="legalTelephone" onmouseover="out('${supplier.legalTelephone }')" value="${se.legalTelephone}" />
                        			  					<div id="legalTelephone" onclick="reason(this)" class="b f18 fl ml10 hand red">×</div>
					                				 </c:if>
					                				  <c:if test="${supplier.legalTelephone eq se.legalTelephone }">
                        			  					<input class="span5" type="text" name="legalTelephone" value="${se.legalTelephone}" />
					                				 </c:if>
												</div>
											</li>
											<li class="col-md-3 margin-0 padding-0 "><span class=""> 手机：</span>
												<div class="input-append">
													 <c:if test="${supplier.legalMobile ne se.legalMobile }">
													     <input class="span5" type="text" id="legalMobile3"  name="legalMobile" onmouseover="out('${supplier.legalMobile }')" value="${se.legalMobile}" />
                        			  					<div id="legalMobile" onclick="reason(this)" class="b f18 fl ml10 hand red">×</div>
					                 				</c:if>
					                 				 <c:if test="${supplier.legalMobile eq se.legalMobile }">
													     <input class="span5" type="text" name="legalMobile"  value="${se.legalMobile}" />
					                 				</c:if>
												</div>
											</li>
										</ul>
					      
					        <h2 class="count_flow"> <i>4</i>联系人信息</h2>
					        <ul class="ul_list">
					          <li class="col-md-3 margin-0 padding-0  "><span class="" id="contactName2">姓名：</span>
					            <div class="input-append">
					               <c:if test="${supplier.contactName ne se.contactName }">
					                  <input class="span5" id="contactName3" name="contactName" onmouseover="out('${supplier.contactName }')" value="${se.contactName }" type="text">
                        			  <div id="contactName" onclick="reason(this)" class="b f18 fl ml10 hand red">×</div>
					                 </c:if>
					                 <c:if test="${supplier.contactName eq se.contactName }">
					                  <input class="span5" id="contactName3" name="contactName" value="${se.contactName }" type="text">
					                 </c:if>
					            </div>
					          </li>
					          <li class="col-md-3 margin-0 padding-0  "><span class="" id="contactFax2">传真：</span>
					            <div class="input-append">
					               <c:if test="${supplier.contactFax ne se.contactFax }">
					                   <input class="span5" id="contactFax3" name="contactFax" onmouseover="out('${supplier.contactFax }')" value="${se.contactFax }"  type="text">
                        			  <div id="contactFax" onclick="reason(this)" class="b f18 fl ml10 hand red">×</div>
					                 </c:if>
					                 <c:if test="${supplier.contactFax eq se.contactFax }">
					                   <input class="span5" id="contactFax3" name="contactFax"  value="${se.contactFax }"  type="text">
					                 </c:if>
					            </div>
					          </li>
					          <li class="col-md-3 margin-0 padding-0  "><span class="" id="contactTelephone1">固定电话：</span>
					            <div class="input-append">
					            	 <c:if test="${supplier.contactTelephone ne se.contactTelephone }">
					            	    <input class="span5" id="contactTelephone3" name="contactTelephone" onmouseover="out('${supplier.contactTelephone }')" value="${se.contactTelephone }" type="text">
                        			  <div id="contactTelephone" onclick="reason(this)" class="b f18 fl ml10 hand red">×</div>
					                 </c:if>
					                 <c:if test="${supplier.contactTelephone eq se.contactTelephone }">
					            	    <input class="span5" id="contactTelephone3" name="contactTelephone" value="${se.contactTelephone }" type="text">
					                 </c:if>
					            </div>
					          </li>
					          <li class="col-md-3 margin-0 padding-0  "><span class="" id="contactMobile2">手机：</span>
					            <div class="input-append">
					            	 <c:if test="${supplier.contactMobile ne se.contactMobile }">
					            	  <input class="span5" id="contactMobile3" name="contactMobile" onmouseover="out('${se.contactMobile }')"  value="${se.contactMobile }" type="text">
                        			  <div id="contactMobile" onclick="reason(this)" class="b f18 fl ml10 hand red">×</div>
					                 </c:if>
					                  <c:if test="${supplier.contactMobile eq se.contactMobile }">
					            	  <input class="span5" id="contactMobile3" name="contactMobile"  value="${se.contactMobile }" type="text">
					                 </c:if>
					            </div>
					          </li>
					          <li class="col-md-3 margin-0 padding-0  "><span class="" id="contactEmail2">邮箱：</span>
					            <div class="input-append">
					            	 <c:if test="${supplier.contactEmail ne se.contactEmail }">
					            	   <input class="span5" id="contactEmail3" name="contactEmail" onmouseover="out('${supplier.contactEmail }')" value="${se.contactEmail }" type="text">
                        			  <div id="contactEmail" onclick="reason(this)" class="b f18 fl ml10 hand red">×</div>
					                 </c:if>
					                  <c:if test="${supplier.contactEmail eq se.contactEmail }">
					            	   <input class="span5" id="contactEmail3" name="contactEmail"  value="${se.contactEmail }" type="text">
					                 </c:if>
					            </div>
					          </li>
					          <li class="col-md-3 margin-0 padding-0  "><span class="" id="contactAddress2">地址：</span>
					            <div class="input-append">
					            	 <c:if test="${supplier.contactAddress ne se.contactAddress }">
					            	  <input class="span5" id="contactAddress3" name="contactAddress" onmouseover="out('${supplier.contactAddress }')" value="${se.contactAddress }" type="text">
                        			  <div id="contactAddress" onclick="reason(this)" class="b f18 fl ml10 hand red">×</div>
					                 </c:if>
					                  <c:if test="${supplier.contactAddress eq se.contactAddress }">
					            	  <input class="span5" id="contactAddress3" name="contactAddress" value="${se.contactAddress }" type="text">
					                 </c:if>
					            </div>
					          </li>
					        </ul>
					      
					    <h2 class="count_flow"><i>5</i>营业执照</h2>
						<ul class="ul_list">
							<li class="col-md-3 margin-0 padding-0 "><span class=""> 统一社会信用代码：</span>
								<div class="input-append">
									<c:if test="${supplier.creditCode ne se.creditCode }">
										<input class="span5" type="text" id="creditCode3" name="creditCode" onmouseover="out('${supplier.creditCode }')" value="${se.creditCode}" />
										<div id="creditCode" onclick="reason(this)" class="b f18 fl ml10 hand red">×</div>
									</c:if>
									<c:if test="${supplier.creditCode eq se.creditCode }">
										<input class="span5" type="text" name="creditCode" value="${se.creditCode}" />
									</c:if>
								</div>
							</li>
							<li class="col-md-3 margin-0 padding-0 "><span class=""> 登记机关：</span>
								<div class="input-append">
									<c:if test="${supplier.registAuthority ne se.registAuthority }">
										<input class="span5" type="text" id="registAuthority3" name="registAuthority" onmouseover="out('${supplier.registAuthority }')" value="${se.registAuthority}" />
										<div id="registAuthority" onclick="reason(this)" class="b f18 fl ml10 hand red">×</div>
									</c:if>
									<c:if test="${supplier.registAuthority eq se.registAuthority }">
										<input class="span5" type="text" name="registAuthority" value="${se.registAuthority}" />
									</c:if>
								</div>
							</li>
							<li class="col-md-3 margin-0 padding-0 "><span class=""> 注册资本：</span>
								<div class="input-append">
									<c:if test="${supplier.registFund ne se.registFund }">
										<input class="span5" type="text" id="registFund3" name="registFund" onmouseover="out('${supplier.registFund }')" value="${se.registFund}" />
										<div id="registFund" onclick="reason(this)" class="b f18 fl ml10 hand red">×</div>
									</c:if>
									<c:if test="${supplier.registFund eq se.registFund }">
										<input class="span5" type="text" name="registFund" value="${se.registFund}" />
									</c:if>
								</div>
							</li>
							<li class="col-md-3 margin-0 padding-0 "><span class="">营业开始时间：</span>
								<div class="input-append">
								<c:if test="${supplier.businessStartDate ne se.businessStartDate }">
									<input class="Wdate w200" type="text" id="businessStartDate3"  readonly="readonly" onmouseover="out('<fmt:formatDate value="${supplier.businessStartDate }" pattern="yyyy-MM-dd" />')" 
									onClick="WdatePicker()" name="businessStartDate" value="<fmt:formatDate value="${se.businessStartDate }" pattern="yyyy-MM-dd" />" /> 
									<div id="businessStartDate" onclick="reason(this)" class="b f18 fl ml10 hand red">×</div>
								</c:if>
								<c:if test="${supplier.businessStartDate eq se.businessStartDate }">
									<input class="Wdate w200" type="text" readonly="readonly" onClick="WdatePicker()" name="businessStartDate" value="<fmt:formatDate value="${se.businessStartDate }" pattern="yyyy-MM-dd" />" /> 
								</c:if>
								</div>
							</li>
							<li class="col-md-3 margin-0 padding-0 "><span class="">营业截止时间：</span>
								<div class="input-append">
								<c:if test="${supplier.businessEndDate ne se.businessEndDate }">
									<input class="Wdate w200" type="text"  id="businessEndDate3" readonly="readonly" onmouseover="out('<fmt:formatDate value="${supplier.businessEndDate }" pattern="yyyy-MM-dd" />')" 
									onClick="WdatePicker()" name="businessEndDate" value="<fmt:formatDate value="${se.businessEndDate }" pattern="yyyy-MM-dd" />" /> 
									<div id="businessEndDate" onclick="reason(this)" class="b f18 fl ml10 hand red">×</div>
								</c:if>
								<c:if test="${supplier.businessEndDate eq se.businessEndDate }">
									<input class="Wdate w200" type="text" readonly="readonly" onClick="WdatePicker()" name="businessEndDate" value="<fmt:formatDate value="${se.businessEndDate }" pattern="yyyy-MM-dd" />" /> 
								</c:if>
								</div>
							</li>

							<li class="col-md-3 margin-0 padding-0 "><span class=""> 生产经营地址：</span>
								<div class="input-append">
								<c:if test="${supplier.businessAddress ne se.businessAddress }">
									<input class="span5" type="text" id="businessAddress3" name="businessAddress" onmouseover="out('${supplier.businessAddress }')" value="${se.businessAddress}" />
									<div id="businessAddress" onclick="reason(this)" class="b f18 fl ml10 hand red">×</div>
								</c:if>
								<c:if test="${supplier.businessAddress eq se.businessAddress }">
									<input class="span5" type="text" name="businessAddress"  value="${se.businessAddress}" />
								</c:if>
								</div>
							</li>
							<li class="col-md-3 margin-0 padding-0 "><span class=""> 邮编：</span>
								<div class="input-append">
								<c:if test="${supplier.businessPostCode ne se.businessPostCode }">
									<input class="span5" type="text" id="businessPostCode3"  name="businessPostCode" onmouseover="out('${supplier.businessPostCode }')" value="${se.businessPostCode}" />
									<div id="businessPostCode" onclick="reason(this)" class="b f18 fl ml10 hand red">×</div>
								</c:if>
								<c:if test="${supplier.businessPostCode eq se.businessPostCode }">
									<input class="span5" type="text" name="businessPostCode"  value="${se.businessPostCode}" />
								</c:if>
								</div>
							</li>
							 <li id="breach_li_id" class="col-md-3 margin-0 padding-0 "><span class="hand">营业执照：</span>
								<up:show showId="business_show" delete="flase" groups="" businessId="${supplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}"/>
							</li>
							<li class="col-md-11 margin-0 padding-0 "><span class="col-md-12 padding-left-5">经营范围：</span>
								<div class="col-md-9 mt5">
									<div class="row _mr20">
									<c:if test="${supplier.businessScope ne se.businessScope }">
										<textarea class="col-md-12 h100" id="businessScope3"  title="不超过800个字" onmouseover="out('${supplier.businessScope }')" name="businessScope">${se.businessScope}</textarea>
										<div id="businessScope" onclick="reason(this)" class="b f18 fl ml10 hand red">×</div>
									</c:if>
									<c:if test="${supplier.businessScope eq se.businessScope }">
										<textarea class="col-md-12 h100" title="不超过800个字" name="businessScope">${se.businessScope}</textarea>
									</c:if>
									</div>
								</div>
								<div class="clear"></div></li>
						</ul>
						</div>
						<h2 class="count_flow"><i>6</i>境外分支</h2>
						<ul class="ul_list">
							<li class="col-md-3 margin-0 padding-0 "><span class=""> 境外分支结构：</span>
									<div class="input-append">
									<c:if test="${supplier.overseasBranch ne se.overseasBranch }">
										<c:if test="${supplier.overseasBranch ==1}">
										    <input class="span5" id="overseasBranch_input_id" name="overseasBranch" type="hidden" readonly="readonly" value="1" />
											<input class="span5" id="overseasBranch3"   onmouseover="out('否')"  type="text" readonly="readonly" value="是" />
										</c:if>
										<c:if test="${supplier.overseasBranch ==0}">
										    <input class="span5" id="overseasBranch_input_id" name="overseasBranch" type="hidden" readonly="readonly" value="0" />
											<input class="span5" id="overseasBranch3"  onmouseover="out('是')"  type="text" readonly="readonly" value="否" />
										</c:if>
										<div id="overseasBranch" onclick="reason(this)" class="b f18 fl ml10 hand red">×</div>
									</c:if>
									<c:if test="${supplier.overseasBranch eq se.overseasBranch }">
										<c:if test="${supplier.overseasBranch ==1}">
										    <input class="span5" id="overseasBranch_input_id" name="overseasBranch" type="hidden" readonly="readonly" value="1" />
											<input class="span5"   type="text" readonly="readonly" value="是" />
										</c:if>
										<c:if test="${supplier.overseasBranch ==0}">
										    <input class="span5" id="overseasBranch_input_id" name="overseasBranch" type="hidden" readonly="readonly" value="0" />
											<input class="span5"  type="text" readonly="readonly" value="否" />
										</c:if>
									</c:if>
								</div>
							</li>
							<li class="col-md-3 margin-0 padding-0 "><span class=""> 境外分支所在国家：</span>
								<div class="input-append">
								<c:if test="${supplier.branchCountry ne se.branchCountry }">
									<input class="span5" name="branchCountry"  id="branchCountry3" onmouseover="out('${supplier.branchCountry }')" type="text" value="${se.branchCountry}" />
									<div id="branchCountry" onclick="reason(this)" class="b f18 fl ml10 hand red">×</div>
								</c:if>
								<c:if test="${supplier.branchCountry eq se.branchCountry }">
									<input class="span5" name="branchCountry" type="text" value="${se.branchCountry}" />
								</c:if>
								</div>
							</li>
							<li class="col-md-3 margin-0 padding-0 "><span class=""> 分支地址：</span>
								<div class="input-append">
								<c:if test="${supplier.branchAddress ne se.branchAddress }">
									<input class="span5" type="text" id="branchAddress3" name="branchAddress" onmouseover="out('${supplier.branchAddress }')" value="${se.branchAddress}" />
									<div id="branchAddress" onclick="reason(this)" class="b f18 fl ml10 hand red">×</div>
								</c:if>
								<c:if test="${supplier.branchAddress eq se.branchAddress }">
									<input class="span5" type="text" name="branchAddress" value="${se.branchAddress}" />
								</c:if>
								</div>
							</li>
							<li class="col-md-11 margin-0 padding-0 "><span class="col-md-12 padding-left-5">分支生产经营范围：</span>
								<div class="col-md-9 mt5">
									<div class="row _mr20">
									<c:if test="${supplier.branchBusinessScope ne se.branchBusinessScope }">
									<textarea class="col-md-12 h100" name="branchBusinessScope" id="branchBusinessScope3" onmouseover="out('${supplier.branchBusinessScope }')"  >${supplier.branchBusinessScope }</textarea>
									<div id="branchBusinessScope" onclick="reason(this)" class="b f18 fl ml10 hand red">×</div>
								</c:if>
								<c:if test="${supplier.branchBusinessScope eq se.branchBusinessScope }">
									<textarea class="col-md-12 h100" name="branchBusinessScope" id="branchBusinessScope3" >${supplier.branchBusinessScope }</textarea>
								</c:if>
									</div>
								</div>
								<div class="clear"></div></li>
						</ul>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>
