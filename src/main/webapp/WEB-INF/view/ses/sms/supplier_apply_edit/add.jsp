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
	 $(document).ready(function(){
	   for(var i=0;i<document.getElementById("overseasBranch").options.length;i++)
	    {
	        if(document.getElementById("overseasBranch").options[i].value == '${supplier.overseasBranch}')
	        {
	            document.getElementById("overseasBranch").options[i].selected=true;
	            break;
	        }
	    }
	});
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
 <div class="container container_box">
 <form action="${pageContext.request.contextPath}/supplier_edit/save.html" method="post">
            <div class=" content height-350">
                <div class="col-md-12 tab-v2 job-content">
                    <h2 class="count_flow"><i>1</i>企业基本信息</h2>
                    <ul class="ul_list">
	                    <li class="col-md-3 margin-0 padding-0">
	                       <span class="" id="supplierName2">供应商名称：</span>
	                       <div class="input-append">
	                           <input name="id" value="${supplier.id }" type="hidden" />
	                           <input class="span5" id="supplierName" name="supplierName"  value="${supplier.supplierName } " type="text">
	                       </div>
	                    </li>
	                    <li class="col-md-3 margin-0 padding-0 ">
	                       <span class="" id="website2">公司网址：</span>
	                       <div class="input-append">
	                           <input class="span5" id="website" name="website" value="${supplier.website } "  type="text">
	                       </div>
	                    </li>
	                    <li class="col-md-3 margin-0 padding-0 ">
	                       <span class="" id="foundDate2">成立日期：</span>
	                       <div class="input-append">
	                           <input class="span5" id="foundDate"  name="foundDate" value="<fmt:formatDate value='${supplier.foundDate}' pattern='yyyy-MM-dd'/>"   type="text">
	                       </div>
	                    </li>
	                    <li class="col-md-3 margin-0 padding-0 ">
	                       <span class="" id="businessType2">营业执照登记类型：</span>
	                       <div class="input-append">
	                           <input class="span5" id="businessType" name="businessType"  value="${supplier.businessType } " type="text">
	                       </div>
	                    </li>
	                    <li class="col-md-3 margin-0 padding-0 ">
	                       <span class="" id="address2">地址：</span>
	                       <div class="input-append">
	                           <input class="span5" id="address" name="address" value="${supplier.address } " type="text"  >
	                       </div>
	                    </li>
	                    <li class="col-md-3 margin-0 padding-0 ">
	                       <span class="" id="bankName2">开户行名称：</span>
	                       <div class="input-append">
	                           <input class="span5" id="bankName" name="bankName" value="${supplier.bankName } "  type="text"  >
	                       </div>
	                    </li>
	                    <li class="col-md-3 margin-0 padding-0 ">
	                       <span class="" id="bankAccount2">开户行账户：</span>
	                       <div class="input-append">
	                           <input class="span5" id="bankAccount" name="bankAccount" value="${supplier.bankAccount } " type="text"  >
	                       </div>
	                    </li>
	                    <li class="col-md-3 margin-0 padding-0 "><span class="" id="postCode2">邮编：</span>
	                       <div class="input-append">
	                           <input class="span5" id="postCode" name="postCode" value="${supplier.postCode }" type="text"  >
	                       </div>
	                   </li>
                   </ul>

                  <h2 class="count_flow"><i>2</i>资质资信</h2>
                  <ul class="ul_list hand">
                     <li id="tax_li_id" class="col-md-3 margin-0 padding-0"><span class="hand">近三个月完税凭证：</span>
						<up:upload id="taxcert_up" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up" businessId="${supplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierTaxCert}" auto="true" />
						<up:show showId="taxcert_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,auditopinion_show,auditopinion_show" businessId="${supplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierTaxCert}"/>
					</li>
					<li id="bill_li_id" class="col-md-3 margin-0 padding-0 "><span class="hand">近三年银行基本账户年末对账单：</span>
						<up:upload id="billcert_up" groups="" businessId="${supplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBillCert}" auto="true" />
						<up:show showId="billcert_show" groups="" businessId="${supplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBillCert}"/>
					</li>
					<li id="security_li_id" class="col-md-3 margin-0 padding-0 "><span class="hand">近三个月缴纳社会保险金凭证：</span>
						<up:upload id="curitycert_up" groups="" businessId="${supplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierSecurityCert}" auto="true" />
						<up:show showId="curitycert_show" groups="" businessId="${supplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierSecurityCert}"/>
					</li>
					<li id="breach_li_id" class="col-md-3 margin-0 padding-0 "><span class="hand">近三年内无重大违法记录声明：</span>
						<up:upload id="bearchcert_up" groups="" businessId="${supplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBearchCert}" auto="true" />
						<up:show showId="bearchcert_show" groups="" businessId="${supplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBearchCert}"/>
					</li>
                  </ul>
                
                  <h2 class="count_flow"><i>3</i>法人代表人信息</h2>
                  <ul class="ul_list">
                    <li class="col-md-3 margin-0 padding-0 ">
                        <span class="" id="legalName2">姓名：</span>
                        <div class="input-append">
                            <input class="span5" id="legalName" name="legalName" value="${supplier.legalName }" type="text" >
                      </div>
                    </li>
                    <li class="col-md-3 margin-0 padding-0 "><span class="" id="legaIdCard2">身份证号：</span>
                        <div class="input-append">
                        <input class="span5" id="legaIdCard" name="legalIdCard" value="${supplier.legalIdCard }"  type="text">
                      </div>
                    </li>
                    <li class="col-md-3 margin-0 padding-0 "><span class="" id="legalTelephone2">固定电话：</span>
                      <div class="input-append">
                        <input class="span5" id="legalTelephone" name="legalTelephone" value="${supplier.legalTelephone } " type="text" >
                      </div>
                    </li>
                    <li class="col-md-3 margin-0 padding-0 "><span class="" id="legalMobile2">手机：</span>
                      <div class="input-append">
                        <input class="span5" id="legalMobile" name="legalMobile" value="${supplier.legalMobile }" type="text">
                      </div>
                    </li>
                  </ul>

                  <h2 class="count_flow"><i>4</i>联系人信息</h2>
                  <ul class="ul_list">
                    <li class="col-md-3 margin-0 padding-0 "><span class="" id="contactName2">姓名：</span>
                      <div class="input-append">
                        <input class="span5" id="contactName" name="contactName" value="${supplier.contactName } " type="text" >
                      </div>
                    </li>
                    <li class="col-md-3 margin-0 padding-0 "><span class="" id="contactFax2">传真：</span>
                      <div class="input-append">
                        <input class="span5" id="contactFax" name="contactFax" value="${supplier.contactFax } "  type="text"  >
                      </div>
                    </li>
                    <li class="col-md-3 margin-0 padding-0 "><span class="" id="contactTelephone1">固定电话：</span>
                      <div class="input-append">
                        <input class="span5" id="contactTelephone" name="contactTelephone" value="${supplier.contactTelephone } " type="text" >
                      </div>
                    </li>
                    <li class="col-md-3 margin-0 padding-0 "><span class="" id="contactMobile2">手机：</span>
                      <div class="input-append">
                        <input class="span5" id="contactMobile" name="contactMobile" value="${supplier.contactMobile } " type="text"  >
                      </div>
                    </li>
                    <li class="col-md-3 margin-0 padding-0 "><span class="" id="contactEmail2">邮箱：</span>
                      <div class="input-append">
                        <input class="span5" id="contactEmail" name="contactEmail" value="${supplier.contactEmail } " type="text"  >
                      </div>
                    </li>
                    <li class="col-md-3 margin-0 padding-0 "><span class="" id="contactAddress2">地址：</span>
                      <div class="input-append">
                        <input class="span5" id="contactAddress" name="contactAddress" value="${supplier.contactAddress } " type="text" >
                      </div>
                    </li>
                  </ul>

                  <h2 class="count_flow"><i>5</i>营业执照</h2>
                  <ul class="ul_list">
                    <li class="col-md-3 margin-0 padding-0 "><span class="" id="creditCode2">统一社会信用代码：</span>
                      <div class="input-append">
                        <input class="span5" id="creditCode" name="creditCode" value="${supplier.creditCode } " type="text" >
                      </div>
                    </li>
                    <li class="col-md-3 margin-0 padding-0 "><span class="" id="registAuthority2">登记机关：</span>
                      <div class="input-append">
                        <input class="span5" id="registAuthority" name="registAuthority" value="${supplier.registAuthority } "  type="text" >
                      </div>
                    </li>
                    <li class="col-md-3 margin-0 padding-0 "><span class="" id="registFund2">注册资本：</span>
                      <div class="input-append">
                        <input class="span5" id="registFund" name="registFund" value="${supplier.registFund } " type="text"  >
                      </div>
                    </li>
                    <li class="col-md-3 margin-0 padding-0 "><span class="" id="businessEndDate2">营业开始时间：</span>
                      <div class="input-append">
                        <input  id="businessStartDate" name="businessStartDate" class="Wdate w200" onclick='WdatePicker()' 
                         value="<fmt:formatDate value='${supplier.businessStartDate}' pattern='yyyy-MM-dd'/>"type="text"/>
                      </div>
                    </li>
                    <li class="col-md-3 margin-0 padding-0 "><span class="" id="businessStartDate2">营业截止时间：</span>
                      <div class="input-append">
                       <input  id="businessEndDate"  name="businessEndDate"   class="Wdate w200" onclick="WdatePicker()" 
                        value="<fmt:formatDate value='${supplier.businessEndDate}' pattern='yyyy-MM-dd'/>"type="text"/>
                      </div>
                    </li>
                    <li class="col-md-3 margin-0 padding-0 "><span class="fl" id="businessAddress2">生产或经营地址：</span>
                      <div class="input-append">
                        <input class="span5" id="businessAddress" name="businessAddress" value="${supplier.businessAddress } " type="text" >
                      </div>
                    </li>
                    <li class="col-md-3 margin-0 padding-0 "><span class="" id="businessPostCode2">邮编：</span>
                      <div class="input-append">
                        <input class="span5" id="businessPostCode" name="businessPostCode" value="${supplier.businessPostCode } " type="text" >
                      </div>
                    </li>
                    <li id="breach_li_id" class="col-md-3 margin-0 padding-0 "><span class="hand">营业执照：</span>
						<up:upload id="business_up" groups="" businessId="${supplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" auto="true" />
						<up:show showId="business_show" groups="" businessId="${supplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}"/>
					</li>
                    <li class="col-md-11 margin-0 padding-0 "><span class="col-md-12 padding-left-5" id="businessScope2">经营范围：</span>
                      <div class="col-md-9 mt5">
                        <div class="row">
                          <textarea class="col-md-12 h100" name="businessScope"  id="businessScope" >${supplier.businessScope }</textarea>
                        </div>
                      </div>
                    </li>
                  </ul>
                 </div> 

                  <h2 class="count_flow"><i>6</i>境外分支</h2>
                  <ul class="ul_list">
                    <li class="col-md-3 margin-0 padding-0 "><span class="" id="overseasBranch2">境外分支机构：</span>
                      <div class="input-append">
						<div class="select_common">
							 <select id="overseasBranch" class="w220" name="overseasBranch">
					           <option value="1">是</option>
					           <option value="0">否</option>
	        				 </select>
						</div>
                      </div>
                    </li>
                    <li class="col-md-3 margin-0 padding-0 "><span class="" id="branchCountry2">境外分支所在国家：</span>
                      <div class="input-append">
                        <input class="span5" id="branchCountry" name="branchCountry" value="${supplier.branchCountry } " type="text" >
                      </div>
                    </li>
                    <li class="col-md-3 margin-0 padding-0 "><span class="" id="branchAddress2">分支地址：</span>
                      <div class="input-append">
                        <input class="span5" id="branchAddress" name="branchAddress" value="${supplier.branchAddress } " type="text"  >
                      </div>
                    </li>
                    <li class="col-md-3 margin-0 padding-0 "><span class="" id="branchName2">机构名称：</span>
                      <div class="input-append">
                        <input class="span5" id="branchName" name="branchName" value="${supplier.branchName } " type="text"  >
                      </div>
                    </li>
                    <li class="col-md-11 margin-0 padding-0 "><span class="col-md-12 padding-left-5" id="branchBusinessScope2">分支生产经营范围：</span>
                      <div class="col-md-9 mt5">
                        <div class="row">
                          <textarea class="col-md-12 h100" name="branchBusinessScope" id="branchBusinessScope" >${supplier.branchBusinessScope }</textarea>
                        </div>
                      </div>
                    </li>
                  </ul>
	            </div>
	            <div class="col-md-12 add_regist tc">
           			  <input  class="btn btn-windows git" type="submit" value="提交" />
                      <input class="btn btn-windows reset" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
                </div>
                </form>
            </div>
</body>
</html>
