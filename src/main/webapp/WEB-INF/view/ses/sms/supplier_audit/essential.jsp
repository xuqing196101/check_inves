<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>

<html>
<head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <%@ include file="/WEB-INF/view/common/webupload.jsp" %>
    <title>详细信息</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
		<script src="${pageContext.request.contextPath}/js/ses/sms/supplier_audit/essential.js"></script>
    <style type="text/css">
        input {
            cursor: pointer;
        }

        textarea:not(.layui-layer-input) {
            cursor: pointer;
        }
        
        .abolish_img_file{
    			position: absolute;
			    right: 20px;
			    top: 4px;
			    color: #ef0000;
			    font-weight: bold;
			    font-size: 18px;
			    cursor: pointer;
        }
        
        .icon_edit,.icon_sc{
        	padding: 5px;
        }
    </style>
</head>

<body>
<!--面包屑导航开始-->
<div class="margin-top-10 breadcrumbs ">
    <div class="container">
        <ul class="breadcrumb margin-left-0">
            <li>
                <a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
            </li>
            <li>
                <a>支撑环境</a>
            </li>
            <li>
                <a>供应商管理</a>
            </li>
            <c:if test="${sign == 1}">
                <li>
                    <a href="javascript:jumppage('${pageContext.request.contextPath}/supplierAudit/supplierAll.html?sign=1')">供应商审核</a>
                </li>
            </c:if>
            <c:if test="${sign == 2}">
                <li>
                    <a href="javascript:jumppage('${pageContext.request.contextPath}/supplierAudit/supplierAll.html?sign=2')">供应商复核</a>
                </li>
            </c:if>
            <c:if test="${sign == 3}">
                <li>
                    <a href="javascript:jumppage('${pageContext.request.contextPath}/supplierAudit/supplierAll.html?sign=3')">供应商实地考察</a>
                </li>
            </c:if>
        </ul>
    </div>
</div>
<div class="container container_box">
    <div class=" content height-350">
        <div class="col-md-12 tab-v2 job-content">
            <%-- <%@include file="/WEB-INF/view/ses/sms/supplier_audit/common_jump.jsp"%> --%>
            <jsp:include page="/WEB-INF/view/ses/sms/supplier_audit/common_jump.jsp">
            	<jsp:param value="one" name="currentStep"/>
            	<jsp:param value="${currSupplier.id }" name="supplierId"/>
            	<jsp:param value="${currSupplier.status }" name="supplierStatus"/>
            	<jsp:param value="${sign }" name="sign"/>
            </jsp:include>
            
            <%-- <form id="form_id" action="${pageContext.request.contextPath}/supplierAudit/financial.html" method="post">
                <input name="supplierId" id="id" value="${currSupplier.id }" type="hidden">
                <input id="status" name="supplierStatus" value="${currSupplier.status }" type="hidden">
                <input type="hidden" name="sign" value="${sign}">
            </form> --%>

            <h2 class="count_flow"><i>1</i>供应商信息</h2>
            <ul class="ul_list">
                <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">供应商名称：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input title="${currSupplier.supplierName }" id="supplierName" onclick="auditText(this,'basic_page','supplierName')"
                               value="${currSupplier.supplierName } " type="text"
                               <c:if test="${fn:contains(field,'supplierName') && !fn:contains(auditField,'supplierName')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="showModify(this,'basic_page','supplierName');"</c:if>
                               <c:if test="${fn:contains(auditField,'supplierName')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(unableField,'supplierName')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">网址：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input title="${currSupplier.website }" class="hand " id="website" value="${currSupplier.website }"
                               type="text" onclick="auditText(this,'basic_page','website')"
                               <c:if test="${fn:contains(field,'website') && !fn:contains(auditField,'website')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="showModify(this,'basic_page','website');"</c:if>
                               <c:if test="${fn:contains(auditField,'website')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(unableField,'website')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">成立日期：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="foundDate" onclick="auditText(this,'basic_page','foundDate')" class="hand "
                               value="<fmt:formatDate value='${currSupplier.foundDate}' pattern='yyyy-MM-dd'/>" type="text"
                               <c:if test="${fn:contains(field,'foundDate') && !fn:contains(auditField,'foundDate')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="showModify(this,'basic_page','foundDate');"</c:if>
                               <c:if test="${fn:contains(auditField,'foundDate')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(unableField,'foundDate')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">企业性质：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="businessNature" class="hand " value="${currSupplier.businessNature } " type="text"
                               onclick="auditText(this,'basic_page','businessNature')"
                               <c:if test="${fn:contains(field,'businessNature') && !fn:contains(auditField,'businessNature')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="showModify(this,'basic_page','businessNature');"</c:if>
                               <c:if test="${fn:contains(auditField,'businessNature')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(unableField,'businessNature')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">基本账户开户银行：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input title="${currSupplier.bankName }" id="bankName" class="hand "
                               value="${currSupplier.bankName } " type="text" onclick="auditText(this,'basic_page','bankName')"
                               <c:if test="${fn:contains(field,'bankName') && !fn:contains(auditField,'bankName')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="showModify(this,'basic_page','bankName');"</c:if>
                               <c:if test="${fn:contains(auditField,'bankName')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(unableField,'bankName')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">银行账号：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="bankAccount" class="hand " value="${currSupplier.bankAccount } " type="text"
                               onclick="auditText(this,'basic_page','bankAccount')"
                               <c:if test="${fn:contains(field,'bankAccount') && !fn:contains(auditField,'bankAccount')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="showModify(this,'basic_page','bankAccount');"</c:if>
                               <c:if test="${fn:contains(auditField,'bankAccount')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(unableField,'bankAccount')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                		<div <c:if test="${fn:contains(unableField,'supplierBank')}">style="border: 1px solid #FF0000;"</c:if>>
                			<span
                        <c:if test="${fn:contains(fileModifyField,supplierDictionaryData.supplierBank)}">style="border: 1px solid #FF8C00;"</c:if>
                        <c:if test="${fn:contains(auditField,'supplierBank') && !fn:contains(unableField,'supplierBank')}">style="border: 1px solid #FF0000;"</c:if>
                        class="hand" onclick="auditFile(this,'basic_page','supplierBank');"
                        onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'">基本账户开户许可证：</span>
                      <c:if test="${fn:contains(unableField,'supplierBank')}">
                      	<img src='${pageContext.request.contextPath}/public/backend/images/sc.png' class="abolish_img_file"/>
                      </c:if>
                		</div>
                    <u:show showId="bank_show" delete="false"
                            groups="bank_show,taxcert_show,billcert_show,curitycert_show,bearchcert_show,bearchcert_up_show,business_show"
                            businessId="${currSupplier.id}" sysKey="${sysKey}"
                            typeId="${supplierDictionaryData.supplierBank}"/>
                    <%-- <p class='abolish'><img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p>
                    <c:if test="${fn:contains(unableField,'supplierBank')}">
                      <a class='abolish'>
                      <img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
                      </a>
                    </c:if> --%>
                </li>
            </ul>

            <div class="clear"></div>
            <h2 class="count_flow"><i>2</i>营业执照</h2>
            <ul class="ul_list">
                <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">营业执照登记类型：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="businessType" class="hand " value="${currSupplier.businessType } " type="text"
                               onclick="auditText(this,'basic_page','businessType')"
                               <c:if test="${fn:contains(field,'businessType') && !fn:contains(auditField,'businessType')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="showModify(this,'basic_page','businessType');"</c:if>
                               <c:if test="${fn:contains(auditField,'businessType')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(unableField,'businessType')}">
                            <a class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">统一社会信用代码：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="creditCode" class="hand " value="${currSupplier.creditCode } " type="text"
                               onclick="auditText(this,'basic_page','creditCode')"
                               <c:if test="${fn:contains(field,'creditCode') && !fn:contains(auditField,'creditCode')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="showModify(this,'basic_page','creditCode');"</c:if>
                               <c:if test="${fn:contains(auditField,'creditCode')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(unableField,'creditCode')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">登记机关：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="registAuthority" class="hand " value="${currSupplier.registAuthority } " type="text"
                               onclick="auditText(this,'basic_page','registAuthority')"
                               <c:if test="${fn:contains(field,'registAuthority') && !fn:contains(auditField,'registAuthority')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="showModify(this,'basic_page','registAuthority');"</c:if>
                               <c:if test="${fn:contains(auditField,'registAuthority')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(unableField,'registAuthority')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">注册资本（人民币：万元）：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="registFund" class="hand " value="${currSupplier.registFund } " type="text"
                               onclick="auditText(this,'basic_page','registFund')"
                               <c:if test="${fn:contains(field,'registFund') && !fn:contains(auditField,'registFund')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="showModify(this,'basic_page','registFund');"</c:if>
                               <c:if test="${fn:contains(auditField,'registFund')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(unableField,'registFund')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">营业期限 ：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="businessStartDate" class="hand " onclick="auditText(this,'basic_page','businessStartDate')"
                               value="<c:choose><c:when test="${currSupplier.branchName eq '1'}">长期有效</c:when><c:otherwise> <fmt:formatDate value='${currSupplier.businessStartDate}' pattern='yyyy-MM-dd'/></c:otherwise></c:choose>"
                               type="text"
                               <c:if test="${fn:contains(field,'businessStartDate') && !fn:contains(auditField,'businessStartDate')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="showModify(this,'basic_page','businessStartDate');"</c:if>
                               <c:if test="${fn:contains(auditField,'businessStartDate')}">style="border: 1px solid red;"</c:if>/>
                        <c:if test="${fn:contains(unableField,'businessStartDate')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <%-- <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">营业截止时间：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="businessStartDate" class="hand " onclick="auditText(this,'basic_page','businessStartDate')" value="<fmt:formatDate value='${currSupplier.businessEndDate}' pattern='yyyy-MM-dd'/>" type="text" />
                    </div>
                </li> --%>
                <%-- <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="fl" id="businessAddress2">生产或经营地址：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="businessAddress" class="hand " value="${currSupplier.businessAddress } " type="text" onclick="reason(this.id,'businessAddress')">
                        <div id="businessAddress3" class="abolish">×</div>
                    </div>
                </li> --%>
                <%-- <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">邮编：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="businessPostCode" class="hand " value="${currSupplier.businessPostCode } " type="text" onclick="auditText(this,'basic_page','businessPostCode')" <c:if test="${fn:contains(field,'businessPostCode') && !fn:contains(auditField,'businessPostCode')}">style="border: 1px solid #FF8C00;"  onMouseOver="showModify(this,'basic_page','businessPostCode');"</c:if>>
                    </div>
                </li> --%>
                <li class="col-md-3 col-sm-6 col-xs-12">
                		<div <c:if test="${fn:contains(unableField,'businessCert')}">style="border: 1px solid #FF0000;"</c:if>>
	                    <span
	                      <c:if test="${fn:contains(fileModifyField,supplierDictionaryData.supplierBusinessCert) && !fn:contains(auditField,'businessCert')}">style="border: 1px solid #FF8C00;"</c:if>
	                      <c:if test="${fn:contains(auditField,'businessCert') && !fn:contains(unableField,'businessCert')}">style="border: 1px solid #FF0000;"</c:if>
	                      class="hand" onmouseover="this.style.background='#E8E8E8'"
	                      onmouseout="this.style.background='#FFFFFF'"
	                      onclick="auditFile(this,'basic_page','businessCert');">营业执照：</span>
                      <c:if test="${fn:contains(unableField,'businessCert')}">
                      	<img src='${pageContext.request.contextPath}/public/backend/images/sc.png' class="abolish_img_file"/>
                      </c:if>
                    </div>
                    <div class="col-md-12 col-sm-12 col-xs-12 p0 mb25 h30">
                        <u:show showId="business_show" delete="false"
                                groups="bank_show,taxcert_show,billcert_show,curitycert_show,bearchcert_show,bearchcert_up_show,business_show"
                                businessId="${currSupplier.id}" sysKey="${sysKey}"
                                typeId="${supplierDictionaryData.supplierBusinessCert}"/>
                        <%-- <c:if test="${fn:contains(unableField,'businessCert')}">
                            <a class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                        <p class='abolish'><img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p> --%>
                    </div>
                </li>
                <li class="col-md-12 col-sm-12 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">营业范围（按照营业执照上填写）：</span>
                    <div class="col-md-12 col-sm-12 col-xs-12 p0">
                        <textarea class="col-md-12 col-xs-12 col-sm-12 h80" id="businessScope" onclick="auditText(this,'basic_page','businessScope')"
                                  <c:if test="${fn:contains(field,'businessScope') && !fn:contains(auditField,'businessScope')}">style="border: 1px solid #FF8C00;"
                                  onMouseOver="showModify(this,'basic_page','businessScope');"</c:if>
                                  <c:if test="${fn:contains(auditField,'businessScope')}">style="border: 1px solid red;"</c:if>>${currSupplier.businessScope }</textarea>
                        <c:if test="${fn:contains(unableField,'businessScope')}">
                            <a class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
            </ul>
  
            <div class="clear"></div>
            <h2 class="count_flow"><i>3</i>法定代表人信息</h2>
            <ul class="ul_list">
                <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">姓名：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="legalName" class="hand " value="${currSupplier.legalName } " type="text"
                               onclick="auditText(this,'basic_page','legalName')"
                               <c:if test="${fn:contains(field,'legalName') && !fn:contains(auditField,'legalName')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="showModify(this,'basic_page','legalName');"</c:if>
                               <c:if test="${fn:contains(auditField,'legalName')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(unableField,'legalName')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">身份证号：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="legalIdCard" class="hand " value="${currSupplier.legalIdCard } " type="text"
                               onclick="auditText(this,'basic_page','legalIdCard')"
                               <c:if test="${fn:contains(field,'legalIdCard') && !fn:contains(auditField,'legalIdCard')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="showModify(this,'basic_page','legalIdCard');"</c:if>
                               <c:if test="${fn:contains(auditField,'legalIdCard')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(unableField,'legalIdCard')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">固定电话：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="legalMobile" class="hand " value="${currSupplier.legalMobile } " type="text"
                               onclick="auditText(this,'basic_page','legalMobile')"
                               <c:if test="${fn:contains(field,'legalMobile') && !fn:contains(auditField,'legalMobile')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="showModify(this,'basic_page','legalMobile');"</c:if>
                               <c:if test="${fn:contains(auditField,'legalMobile')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(unableField,'legalMobile')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">手机：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="legalTelephone" class="hand " value="${currSupplier.legalTelephone } " type="text"
                               onclick="auditText(this,'basic_page','legalTelephone')"
                               <c:if test="${fn:contains(field,'legalTelephone') && !fn:contains(auditField,'legalTelephone')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="showModify(this,'basic_page','legalTelephone');"</c:if>
                               <c:if test="${fn:contains(auditField,'legalTelephone')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(unableField,'legalTelephone')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <%-- <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="hand" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'" onclick="auditFile(this,'basic_page','supplierIdentityUp');">身份证正面: </span>
                    <u:show showId="bearchcert_up_show" delete="false" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentityUp}" />
                    <p class="b f18 ml10 red">×</p>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="hand" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'" onclick="auditFile(this,'basic_page','supplierIdentitydown');">身份证反面: </span>
                    <u:show showId="identity_down_show" delete="false" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentitydown}" />
                    <p class="b f18 ml10 red">×</p>
                </li> --%>

                <li class="col-md-3 col-sm-6 col-xs-12">
                		<div <c:if test="${fn:contains(unableField,'supplierIdentityUp')}">style="border: 1px solid #FF0000;"</c:if>>
	                    <span
	                      <c:if test="${fn:contains(fileModifyField,supplierDictionaryData.supplierIdentityUp) && !fn:contains(auditField,'supplierIdentityUp')}">style="border: 1px solid #FF8C00;"</c:if>
	                      <c:if test="${fn:contains(auditField,'supplierIdentityUp') && !fn:contains(unableField,'supplierIdentityUp')}">style="border: 1px solid #FF0000;"</c:if>
	                      class="hand" onmouseover="this.style.background='#E8E8E8'"
	                      onmouseout="this.style.background='#FFFFFF'" onclick="auditFile(this,'basic_page','supplierIdentityUp');"> 身份证复印件（正反面在一张上）:</span>
                      <c:if test="${fn:contains(unableField,'supplierIdentityUp')}">
                      	<img src='${pageContext.request.contextPath}/public/backend/images/sc.png' class="abolish_img_file"/>
                      </c:if>
                    </div>
                    <div class="col-md-12 col-sm-12 col-xs-12 p0 mb25 h30">
                        <u:show showId="bearchcert_up_show" delete="false"
                                groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show"
                                businessId="${currSupplier.id}" sysKey="${sysKey}"
                                typeId="${supplierDictionaryData.supplierIdentityUp}"/>
                        <%-- <p class='abolish'><img style="padding-left: 125px;"
                                src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p>
                        <c:if test="${fn:contains(unableField,'supplierIdentityUp')}">
                            <a class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if> --%>
                    </div>
                </li>
            </ul>
            
            <div class="clear"></div>
            <h2 class="count_flow"><i>4</i>地址信息</h2>
            <ul class="ul_list hand">
                <li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">住所邮编：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="postCode" class="hand " value="${currSupplier.postCode }" type="text"
                               onclick="auditText(this,'basic_page','postCode')"
                        <c:if test="${fn:contains(field,'postCode') && !fn:contains(auditField,'postCode')}"> style="border: 1px solid #FF8C00;"
                               onMouseOver="showModify(this,'basic_page','postCode');"</c:if>
                               <c:if test="${fn:contains(auditField,'postCode')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(unableField,'postCode')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"> 住所地址：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="address" class="hand " value="${parentAddress}${sonAddress } " type="text"
                               onclick="auditText(this,'basic_page','address')"
                        <c:if test="${fn:contains(field,'address') && !fn:contains(auditField,'address')}"> style="border: 1px solid #FF8C00;"
                               onMouseOver="showModify(this,'basic_page','address');"</c:if>
                               <c:if test="${fn:contains(auditField,'address')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(unableField,'address')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">住所详细地址：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="detailAddress" class="hand fl" onclick="auditText(this,'basic_page','detailAddress')" type="text"
                               value="${currSupplier.detailAddress}"
                               <c:if test="${fn:contains(field,'detailAddress') && !fn:contains(auditField,'detailAddress')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="showModify(this,'basic_page','detailAddress');"</c:if>
                               <c:if test="${fn:contains(auditField,'detailAddress')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(unableField,'detailAddress')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <div class="clear"></div>
                <!-- 遍历生产地址 -->
                <%-- <c:forEach items="${supplierAddress }" var="supplierAddress" varStatus="vs">
                    <li class="col-md-3 col-sm-6 col-xs-12">
                        <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">生产或经营地址邮编：</span>
                        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                            <input type="text" id="code_${supplierAddress.id }" value="${supplierAddress.code}" class="hand " onclick="auditText(this,'basic_page','code_${supplierAddress.id }')" <c:if test="${fn:contains(fieldAddress,supplierAddress.id.concat('_code'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'basic_page','code','${supplierAddress.id}','1');"</c:if> <c:if test="${fn:contains(auditField,'code_'.concat(supplierAddress.id))}">style="border: 1px solid red;"</c:if>>
                            <c:if test="${fn:contains(unableField,'code_'.concat(supplierAddress.id))}">
                                <a class="abolish">
                                    <img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
                                </a>
                            </c:if>
                        </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12">
                        <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">生产或经营地址：</span>
                        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                            <input type="text" id="residence_${supplierAddress.id }" value="${supplierAddress.parentName }${supplierAddress.subAddressName }" class="hand " onclick="auditText(this,'basic_page','residence_${supplierAddress.id }')" <c:if test="${fn:contains(fieldAddress,supplierAddress.id.concat('_residence'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'basic_page','residence','${supplierAddress.id}','1');"</c:if> <c:if test="${fn:contains(auditField,'residence_'.concat(supplierAddress.id))}">style="border: 1px solid red;"</c:if>>
                            <c:if test="${fn:contains(unableField,'residence_'.concat(supplierAddress.id))}">
                                <a class="abolish">
                                    <img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
                                </a>
                            </c:if>
                        </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12 pl10">
                        <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">生产或经营详细地址：</span>
                        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                            <input type="text" id="detailedResidence_${supplierAddress.id }" value="${supplierAddress.detailAddress}" class="hand " onclick="auditText(this,'basic_page','detailedResidence_${supplierAddress.id }')"  <c:if test="${fn:contains(fieldAddress,supplierAddress.id.concat('_detailedResidence'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'basic_page','detailedResidence','${supplierAddress.id}','1');"</c:if> <c:if test="${fn:contains(auditField,'detailedResidence_'.concat(supplierAddress.id))}">style="border: 1px solid red;"</c:if>>
                            <c:if test="${fn:contains(unableField,'detailedResidence_'.concat(supplierAddress.id))}">
                                <a class="abolish">
                                    <img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
                                </a>
                            </c:if>
                        </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12 pl10" >
                    <span <c:if test="${fn:contains(houseFileModifyField,supplierAddress.id.concat(supplierDictionaryData.supplierHousePoperty))}">style="border: 1px solid #FF8C00;"</c:if> class="hand" onclick="auditFile(this,'basic_page','supplierHousePoperty_${supplierAddress.id}');" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'">房产证明或租赁协议：</span>
                    <div class="col-md-12 col-sm-12 col-xs-12 p0 mb25 h30">
                        <u:show delete="false" showId="house_show_${vs.index+1}" businessId="${supplierAddress.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierHousePoperty}" />
                        <p><img style="padding-left: 100px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p>
                        <c:if test="${fn:contains(unableField,'supplierHousePoperty_'.concat(supplierAddress.id))}">
                            <img style="padding-left: 100px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
                        </c:if>
                    </div>
                </li>
                    <div class="clear"></div>
                </c:forEach> --%>

                <table class="table table-bordered  table-condensed table-hover m_table_fixed_border">
                    <thead>
                    <tr>
                        <th class="info w50">序号</th>
                        <th class="info">生产或经营地址邮编</th>
                        <th class="info">生产或经营地址（填写所有地址）</th>
                        <th class="info">生产或经营详细地址</th>
                        <th class="info">房产证明或租赁协议</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="finance_attach_list_tbody_id">
                    <c:forEach items="${supplierAddress}" var="supplierAddress" varStatus="vs">
                        <tr>
                            <td class="tc">${vs.index+1}</td>
                            <td id="code_${supplierAddress.id}"
                                <c:if test="${fn:contains(fieldAddress,supplierAddress.id.concat('_code'))}">style="border: 1px solid #FF8C00;"
                                onMouseOver="showModifyList(this,'basic_page','code','${supplierAddress.id}','1');"</c:if>>${supplierAddress.code}</td>
                            <td id="residence_${supplierAddress.id}"
                                <c:if test="${fn:contains(fieldAddress,supplierAddress.id.concat('_residence'))}">style="border: 1px solid #FF8C00;"
                                onMouseOver="showModifyList(this,'basic_page','residence','${supplierAddress.id}','1');"</c:if>>${supplierAddress.parentName}${supplierAddress.subAddressName}</td>
                            <td id="detailedResidence_${supplierAddress.id}"
                                <c:if test="${fn:contains(fieldAddress,supplierAddress.id.concat('_detailedResidence'))}">style="border: 1px solid #FF8C00;"
                                onMouseOver="showModifyList(this,'basic_page','detailedResidence','${supplierAddress.id}','1');"</c:if>>${supplierAddress.detailAddress}</td>
                            <td
                                    <c:if test="${fn:contains(houseFileModifyField,supplierAddress.id.concat(supplierDictionaryData.supplierHousePoperty))}">style="border: 1px solid #FF8C00;"</c:if>>
                                <u:show delete="false" showId="house_show_${vs.index+1}"
                                        businessId="${supplierAddress.id}" sysKey="${sysKey}"
                                        typeId="${supplierDictionaryData.supplierHousePoperty}"/>
                            </td>
                            <td class="tc w50 hand" id="hand_${supplierAddress.id}">
                            	<%-- <c:if test="${!fn:contains(unableField,supplierAddress.id)}">
                                <p onclick="auditList(this,'basic_page','${supplierAddress.id}','地址信息','${supplierAddress.parentName}${supplierAddress.subAddressName}');"
                                   class="editItem" id="${supplierAddress.id}_hidden">
                                   <c:if test="${!fn:contains(auditField,supplierAddress.id)}">
                                     <img src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'/>
                                   </c:if>
                                   <c:if test="${fn:contains(auditField,supplierAddress.id)}">
                                     <img src='${pageContext.request.contextPath}/public/backend/images/light_icon_2.png'/>
                                   </c:if>
                                </p>
                              </c:if>
                              <c:if test="${fn:contains(unableField,supplierAddress.id)}">
                                <img src='${pageContext.request.contextPath}/public/backend/images/sc.png' onclick="javascript:layer.msg('该条信息已审核并退回过！');"/>
                              </c:if> --%>
                              <c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/light_icon.png" />
                              <c:if test="${!fn:contains(unableField,supplierAddress.id) && fn:contains(auditField,supplierAddress.id)}">
                              	<c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/light_icon_2.png" />
                              </c:if>
                              <c:if test="${fn:contains(unableField,supplierAddress.id)}">
                                <c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/sc.png" />
                              </c:if>
                              <img src="${iconUrl}" class="icon_edit"
                              onclick="auditList(this,'basic_page','${supplierAddress.id}','地址信息','${supplierAddress.parentName}${supplierAddress.subAddressName}');" />
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </ul>

            <div class="clear"></div>
            <h2 class="count_flow"><i>5</i>资质资信</h2>
            <ul class="ul_list hand">
                <li class="col-md-3 col-sm-6 col-xs-12 pl15 h70">
                		<div <c:if test="${fn:contains(unableField,'taxCert')}">style="border: 1px solid #FF0000;"</c:if>>
                			<span
	                      <c:if test="${fn:contains(fileModifyField,supplierDictionaryData.supplierTaxCert) && !fn:contains(auditField,'taxCert')}">style="border: 1px solid #FF8C00;"</c:if>
	                      <c:if test="${fn:contains(auditField,'taxCert') && !fn:contains(unableField,'taxCert')}">style="border: 1px solid #FF0000;"</c:if>
	                      class="hand" onclick="auditFile(this,'basic_page','taxCert');"
	                      onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'">近三个月完税凭证：</span>
	                    <c:if test="${fn:contains(unableField,'taxCert')}">
	                    	<img src='${pageContext.request.contextPath}/public/backend/images/sc.png' class="abolish_img_file"/>
	                    </c:if>
                		</div>
                    <div class="col-md-12 col-sm-12 col-xs-12 p0 mb25 h30">
                        <u:show showId="taxcert_show" delete="false"
                                groups="bank_show,taxcert_show,billcert_show,curitycert_show,bearchcert_show,bearchcert_up_show,business_show"
                                businessId="${currSupplier.id}" sysKey="${sysKey}"
                                typeId="${supplierDictionaryData.supplierTaxCert}"/>
                        <%-- <p class='abolish'><img style="padding-left: 125px;"
                                src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p>
                        <c:if test="${fn:contains(unableField,'taxCert')}">
                          <a class='abolish'>
                            <img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
                          </a>
                        </c:if> --%>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12 h70">
                		<div <c:if test="${fn:contains(unableField,'billCert')}">style="border: 1px solid #FF0000;"</c:if>>
                			<span
	                      <c:if test="${fn:contains(fileModifyField,supplierDictionaryData.supplierBillCert) && !fn:contains(auditField,'billCert')}">style="border: 1px solid #FF8C00;"</c:if>
	                      <c:if test="${fn:contains(auditField,'billCert') && !fn:contains(unableField,'billCert')}">style="border: 1px solid #FF0000;"</c:if>
	                      class="hand" onclick="auditFile(this,'basic_page','billCert');"
	                      onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'">近三年银行基本账户年末对账单：</span>
	                    <c:if test="${fn:contains(unableField,'billCert')}">
	                    	<img src='${pageContext.request.contextPath}/public/backend/images/sc.png' class="abolish_img_file"/>
	                    </c:if>
                		</div>
                    <div class="col-md-12 col-sm-12 col-xs-12 p0 mb25 h30">
                        <u:show showId="billcert_show" delete="false"
                                groups="bank_show,taxcert_show,billcert_show,curitycert_show,bearchcert_show,bearchcert_up_show,business_show"
                                businessId="${currSupplier.id}" sysKey="${sysKey}"
                                typeId="${supplierDictionaryData.supplierBillCert}"/>
                        <%-- <p class='abolish'><img style="padding-left: 125px;"
                                src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p>
                        <c:if test="${fn:contains(unableField,'billCert')}">
                          <a class='abolish'>
                            <img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
                          </a>
                        </c:if> --%>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12 h70">
                		<div <c:if test="${fn:contains(unableField,'securityCert')}">style="border: 1px solid #FF0000;"</c:if>>
               				<span class="hand"
                        <c:if test="${fn:contains(fileModifyField,supplierDictionaryData.supplierSecurityCert) && !fn:contains(auditField,'securityCert')}">style="border: 1px solid #FF8C00;"</c:if>
                        <c:if test="${fn:contains(auditField,'securityCert') && !fn:contains(unableField,'securityCert')}">style="border: 1px solid #FF0000;"</c:if>
                        onclick="auditFile(this,'basic_page','securityCert');" onmouseover="this.style.background='#E8E8E8'"
                        onmouseout="this.style.background='#FFFFFF'">近三个月缴纳社会保险金凭证：</span>
	                    <c:if test="${fn:contains(unableField,'securityCert')}">
	                    	<img src='${pageContext.request.contextPath}/public/backend/images/sc.png' class="abolish_img_file"/>
	                    </c:if>
                		</div>
                    <div class="col-md-12 col-sm-12 col-xs-12 p0 mb25 h30">
                        <u:show showId="curitycert_show" delete="false"
                                groups="bank_show,taxcert_show,billcert_show,curitycert_show,bearchcert_show,bearchcert_up_show,business_show"
                                businessId="${currSupplier.id}" sysKey="${sysKey}"
                                typeId="${supplierDictionaryData.supplierSecurityCert}"/>
                        <%-- <p class='abolish'><img style="padding-left: 125px;"
                                src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p>
                        <c:if test="${fn:contains(unableField,'securityCert')}">
                          <a class='abolish'>
                            <img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
                          </a>
                        </c:if> --%>
                    </div>
                </li>
                <%-- <li class="col-md-3 col-sm-6 col-xs-12"><span class="hand" onclick="auditFile(this,'basic_page','breachCert');" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'">近三年内无重大违法记录声明：</span>
                    <u:show showId="bearchcert_show" groups="bank_show,taxcert_show,billcert_show,curitycert_show,bearchcert_show,bearchcert_up_show,business_show" delete="false" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBearchCert}" />
                    <p><img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p>
                </li> --%>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">近三年内有无重大违法记录：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <c:if test="${'1' eq currSupplier.isIllegal }">
                            <input id="isIllegal" class="hand " value="有" type="text" onclick="auditText(this,'basic_page','isIllegal')"
                                   <c:if test="${fn:contains(auditField,'isIllegal')}">style="border: 1px solid red;"</c:if>
                                   <c:if test="${fn:contains(field,'isIllegal') && !fn:contains(auditField,'isIllegal')}">style="border: 1px solid #FF8C00;"
                                   onMouseOver="showModify(this,'basic_page','isIllegal');"</c:if> >
                        </c:if>
                        <c:if test="${'0' eq currSupplier.isIllegal }">
                            <input id="isIllegal" class="hand " value="无" type="text" onclick="auditText(this,'basic_page','isIllegal')"
                                   <c:if test="${fn:contains(auditField,'isIllegal')}">style="border: 1px solid red;"</c:if>
                                   <c:if test="${fn:contains(field,'isIllegal') && !fn:contains(auditField,'isIllegal')}">style="border: 1px solid #FF8C00;"
                                   onMouseOver="showModify(this,'basic_page','isIllegal');"</c:if> >
                        </c:if>
                        <c:if test="${fn:contains(unableField,'isIllegal')}"><a class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a></c:if>
                    </div>
                </li>
                <c:if test="${currSupplier.isHavingConCert eq '0'}">
                    <li class="col-md-3 col-sm-6 col-xs-12">
                        <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">国家或军队保密资格证书：</span>
                        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                            <input id="isHavingConCert" class="hand " value="无" type="text" onclick="auditText(this,'basic_page','isHavingConCert')"
                                   <c:if test="${fn:contains(auditField,'isHavingConCert')}">style="border: 1px solid red;"</c:if>
                                   <c:if test="${fn:contains(field,'isHavingConCert') && !fn:contains(auditField,'isHavingConCert')}">style="border: 1px solid #FF8C00;"
                                   onMouseOver="showModify(this,'basic_page','isHavingConCert');"</c:if> >
                            <c:if test="${fn:contains(unableField,'isHavingConCert')}"><a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a></c:if>
                        </div>
                    </li>
                </c:if>
                <c:if test="${currSupplier.isHavingConCert eq '1'}">
                    <li class="col-md-3 col-sm-6 col-xs-12">
                      <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">国家或军队保密资格证书：</span>
                        <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                          <input id="isHavingConCert" class="hand " value="有" type="text" onclick="auditText(this,'basic_page','isHavingConCert')"
                          <c:if test="${fn:contains(auditField,'isHavingConCert')}">style="border: 1px solid red;"</c:if>
                          <c:if test="${fn:contains(field,'isHavingConCert') && !fn:contains(auditField,'isHavingConCert')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'basic_page','isHavingConCert');"</c:if> >
                          <c:if test="${fn:contains(unableField,'isHavingConCert')}"><a class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a></c:if>
                        </div>
                    </li>
                    <li class="col-md-3 col-sm-6 col-xs-12">
                    		<div <c:if test="${fn:contains(unableField,'supplierBearchCert')}">style="border: 1px solid #FF0000;"</c:if>>
                    			<span 
	                        	<c:if test="${fn:contains(fileModifyField,supplierDictionaryData.supplierBearchCert) && !fn:contains(auditField,'supplierBearchCert')}">style="border: 1px solid #FF8C00;"</c:if>
	                        	<c:if test="${fn:contains(auditField,'supplierBearchCert') && !fn:contains(unableField,'supplierBearchCert')}">style="border: 1px solid #FF0000;"</c:if>
	                                class="hand" onclick="auditFile(this,'basic_page','supplierBearchCert');"
	                                onmouseover="this.style.background='#E8E8E8'"
	                                onmouseout="this.style.background='#FFFFFF'">保密资格证书：</span>
	                        <c:if test="${fn:contains(unableField,'supplierBearchCert')}">
			                    	<img src='${pageContext.request.contextPath}/public/backend/images/sc.png' class="abolish_img_file"/>
			                    </c:if>
                    		</div>
                        <div class="col-md-12 col-sm-12 col-xs-12 p0 mb25 h30">
                            <u:show showId="bearchcert_show" delete="false"
                                    groups="bank_show,taxcert_show,billcert_show,curitycert_show,bearchcert_show,bearchcert_up_show,business_show"
                                    businessId="${currSupplier.id}" sysKey="${sysKey}"
                                    typeId="${supplierDictionaryData.supplierBearchCert}"/>
                            <%-- <p class='abolish'><img style="padding-left: 125px;"
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p>
                            <c:if test="${fn:contains(unableField,'supplierBearchCert')}">
                              <a class='abolish'>
                                <img style="padding-left: 125px;"  src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
                              </a>
                            </c:if> --%>
                        </div>
                    </li>
                </c:if>
            </ul>

            <div class="clear"></div>
            <h2 class="count_flow"><i>6</i>注册联系人</h2>
            <ul class="ul_list">
                <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">姓名：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="contactName" class="hand " value="${currSupplier.contactName } " type="text"
                               onclick="auditText(this,'basic_page','contactName')"
                               <c:if test="${fn:contains(field,'contactName') && !fn:contains(auditField,'contactName')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="showModify(this,'basic_page','contactName');"</c:if>
                               <c:if test="${fn:contains(auditField,'contactName')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(unableField,'contactName')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">传真：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="contactFax" class="hand " value="${currSupplier.contactFax } " type="text"
                               onclick="auditText(this,'basic_page','contactFax')"
                               <c:if test="${fn:contains(field,'contactFax') && !fn:contains(auditField,'contactFax')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="showModify(this,'basic_page','contactFax');"</c:if>
                               <c:if test="${fn:contains(auditField,'contactFax')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(unableField,'contactFax')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">固定电话：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="contactMobile" class="hand " value="${currSupplier.contactMobile } " type="text"
                               onclick="auditText(this,'basic_page','contactMobile')"
                               <c:if test="${fn:contains(field,'contactMobile') && !fn:contains(auditField,'contactMobile')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="showModify(this,'basic_page','contactMobile');"</c:if>
                               <c:if test="${fn:contains(auditField,'contactMobile')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(unableField,'contactMobile')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">手机：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="mobile" class="hand " value="${currSupplier.mobile } " type="text"
                               onclick="auditText(this,'basic_page','mobile')"
                               <c:if test="${fn:contains(field,'mobile') && !fn:contains(auditField,'mobile')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="showModify(this,'basic_page','mobile');"</c:if>
                               <c:if test="${fn:contains(auditField,'mobile')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(unableField,'mobile')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">邮箱：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="contactEmail" class="hand " value="${currSupplier.contactEmail } " type="text"
                               onclick="auditText(this,'basic_page','contactEmail')"
                               <c:if test="${fn:contains(field,'contactEmail') && !fn:contains(auditField,'contactEmail')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="showModify(this,'basic_page','contactEmail');"</c:if>
                               <c:if test="${fn:contains(auditField,'contactEmail')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(unableField,'contactEmail')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">地址：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="concatCity" class="hand " value="${parentConcatProvince } ${sonConcatProvince}"
                               type="text" onclick="auditText(this,'basic_page','concatCity')"
                               <c:if test="${fn:contains(field,'concatCity') && !fn:contains(auditField,'concatCity')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="showModify(this,'basic_page','concatCity');"</c:if>
                               <c:if test="${fn:contains(auditField,'concatCity')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(unableField,'concatCity')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">详细地址：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="contactAddress" class="hand " value="${currSupplier.contactAddress } " type="text"
                               onclick="auditText(this,'basic_page','contactAddress')"
                               <c:if test="${fn:contains(field,'contactAddress') && !fn:contains(auditField,'contactAddress')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="showModify(this,'basic_page','contactAddress');"</c:if>
                               <c:if test="${fn:contains(auditField,'contactAddress')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(unableField,'contactAddress')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
            </ul>

            <div class="clear"></div>
            <h2 class="count_flow"><i>7</i>本单位军队业务联系人</h2>
            <ul class="ul_list">
                <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">姓名：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="armyBusinessName" class="hand " value="${currSupplier.armyBusinessName } " type="text"
                               onclick="auditText(this,'basic_page','armyBusinessName')"
                               <c:if test="${fn:contains(field,'armyBusinessName') && !fn:contains(auditField,'armyBusinessName')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="showModify(this,'basic_page','armyBusinessName');"</c:if>
                               <c:if test="${fn:contains(auditField,'armyBusinessName')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(unableField,'armyBusinessName')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">传真：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="armyBusinessFax" class="hand " value="${currSupplier.armyBusinessFax } " type="text"
                               onclick="auditText(this,'basic_page','armyBusinessFax')"
                               <c:if test="${fn:contains(field,'armyBusinessFax') && !fn:contains(auditField,'armyBusinessFax')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="showModify(this,'basic_page','armyBusinessFax');"</c:if>
                               <c:if test="${fn:contains(auditField,'armyBusinessFax')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(unableField,'armyBusinessFax')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">固定电话：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="armyBuinessMobile" class="hand " value="${currSupplier.armyBuinessMobile } " type="text"
                               onclick="auditText(this,'basic_page','armyBuinessMobile')"
                               <c:if test="${fn:contains(field,'armyBuinessMobile') && !fn:contains(auditField,'armyBuinessMobile')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="showModify(this,'basic_page','armyBuinessMobile');"</c:if>
                               <c:if test="${fn:contains(auditField,'armyBuinessMobile')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(unableField,'armyBuinessMobile')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">手机：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="armyBuinessTelephone" class="hand " value="${currSupplier.armyBuinessTelephone } "
                               type="text" onclick="auditText(this,'basic_page','armyBuinessTelephone')"
                               <c:if test="${fn:contains(field,'armyBuinessTelephone') && !fn:contains(auditField,'armyBuinessTelephone')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="showModify(this,'basic_page','armyBuinessTelephone');"</c:if>
                               <c:if test="${fn:contains(auditField,'armyBuinessTelephone')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(unableField,'armyBuinessTelephone')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">邮箱：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="armyBuinessEmail" class="hand " value="${currSupplier.armyBuinessEmail } " type="text"
                               onclick="auditText(this,'basic_page','armyBuinessEmail')"
                               <c:if test="${fn:contains(field,'armyBuinessEmail') && !fn:contains(auditField,'armyBuinessEmail')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="showModify(this,'basic_page','armyBuinessEmail');"</c:if>
                               <c:if test="${fn:contains(auditField,'armyBuinessEmail')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(unableField,'armyBuinessEmail')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">地址：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <input id="armyBuinessCity" class="hand "
                               value="${parentArmyBuinessProvince} ${sonArmyBuinessProvince}" type="text"
                               onclick="auditText(this,'basic_page','armyBuinessCity')"
                               <c:if test="${fn:contains(field,'armyBuinessCity') && !fn:contains(auditField,'armyBuinessCity')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="showModify(this,'basic_page','armyBuinessCity');"</c:if>
                               <c:if test="${fn:contains(auditField,'armyBuinessCity')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(unableField,'armyBuinessCity')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">详细地址：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 p0">
                        <input id="armyBuinessAddress" class="hand " value="${currSupplier.armyBuinessAddress } "
                               type="text" onclick="auditText(this,'basic_page','armyBuinessAddress')"
                               <c:if test="${fn:contains(field,'armyBuinessAddress') && !fn:contains(auditField,'armyBuinessAddress')}">style="border: 1px solid #FF8C00;"
                               onMouseOver="showModify(this,'basic_page','armyBuinessAddress');"</c:if>
                               <c:if test="${fn:contains(auditField,'armyBuinessAddress')}">style="border: 1px solid red;"</c:if>>
                        <c:if test="${fn:contains(unableField,'armyBuinessAddress')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
            </ul>

            <div class="clear"></div>
            <h2 class="count_flow"><i>8</i>境外分支</h2>
            <ul class="ul_list">
                <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">境外分支机构：</span>
                    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                        <c:if test="${currSupplier.overseasBranch == 0}">
                            <input id="overseasBranch" class="hand " value="无" type="text" onclick="auditText(this,'basic_page','overseasBranch')"
                                   <c:if test="${fn:contains(auditField,'overseasBranch')}">style="border: 1px solid red;"</c:if>>
                        </c:if>
                        <c:if test="${currSupplier.overseasBranch == 1}">
                            <input id="overseasBranch" class="hand " value="有" type="text" onclick="auditText(this,'basic_page','overseasBranch')"
                                   <c:if test="${fn:contains(auditField,'overseasBranch')}">style="border: 1px solid red;"</c:if>>
                        </c:if>
                        <c:if test="${fn:contains(unableField,'overseasBranch')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
                <div class="clear"></div>
                <c:forEach items="${supplierBranchList }" var="supplierBranch" varStatus="vs">
                    <c:if test="${currSupplier.overseasBranch == 1}">
                        <li class="col-md-3 col-sm-6 col-xs-12">
                            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">机构名称：</span>
                            <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                                <input id="organizationName_${supplierBranch.id }" class="hand "
                                       value="${supplierBranch.organizationName } " type="text" onclick="auditText(this,'basic_page','organizationName_${supplierBranch.id }')"
                                       <c:if test="${fn:contains(auditField,'organizationName_'.concat(supplierBranch.id))}">style="border: 1px solid red;"</c:if>
                                       <c:if test="${fn:contains(fieldBranch,supplierBranch.id.concat('_organizationName'))}">style="border: 1px solid #FF8C00;"
                                       onMouseOver="showModifyList(this,'basic_page','organizationName','${supplierBranch.id}','2');"</c:if>>
                                <c:if test="${fn:contains(unableField,'organizationName_'.concat(supplierBranch.id))}">
                                    <a class='abolish'><img
                                            src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                                </c:if>
                            </div>
                        </li>
                        <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">所在国家(地区)：</span>
                            <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                                <input id="countryName_${supplierBranch.id }" class="hand "
                                       value="${supplierBranch.countryName } " type="text" onclick="auditText(this,'basic_page','countryName_${supplierBranch.id }')"
                                       <c:if test="${fn:contains(auditField,'countryName_'.concat(supplierBranch.id))}">style="border: 1px solid red;"</c:if>
                                       <c:if test="${fn:contains(fieldBranch,supplierBranch.id.concat('_countryName'))}">style="border: 1px solid #FF8C00;"
                                       onMouseOver="showModifyList(this,'basic_page','countryName','${supplierBranch.id}','2');"</c:if>>
                                <c:if test="${fn:contains(unableField,'countryName_'.concat(supplierBranch.id))}">
                                    <a class='abolish'><img
                                            src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                                </c:if>
                            </div>
                        </li>
                        <li class="col-md-3 col-sm-6 col-xs-12 ">
                            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">详细地址：</span>
                            <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                                <input id="detailAddress_${supplierBranch.id }" class="hand "
                                       value="${supplierBranch.detailAddress } " type="text" onclick="auditText(this,'basic_page','detailAddress_${supplierBranch.id }')"
                                       <c:if test="${fn:contains(auditField,'detailAddress_'.concat(supplierBranch.id))}">style="border: 1px solid red;"</c:if>
                                       <c:if test="${fn:contains(fieldBranch,supplierBranch.id.concat('_detailAddress'))}">style="border: 1px solid #FF8C00;"
                                       onMouseOver="showModifyList(this,'basic_page','detailAddress','${supplierBranch.id}','2');"</c:if>>
                                <c:if test="${fn:contains(unableField,'detailAddress_'.concat(supplierBranch.id))}">
                                    <a class='abolish'><img
                                            src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                                </c:if>
                            </div>
                        </li>
                        <li class="col-md-12 col-sm-12 col-xs-12">
                            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">分支生产经营范围：</span>
                            <div class="col-md-12 col-sm-12 col-xs-12 p0">
                                <textarea class="col-md-12 col-xs-12 col-sm-12 h80"
                                          id="businessSope_${supplierBranch.id}" onclick="auditText(this,'basic_page','businessSope_${supplierBranch.id}')"
                                          <c:if test="${fn:contains(auditField,'businessSope_'.concat(supplierBranch.id))}">style="border: 1px solid red;"</c:if>
                                          <c:if test="${fn:contains(fieldBranch,supplierBranch.id.concat('_businessSope'))}">style="border: 1px solid #FF8C00;"
                                          onMouseOver="showModifyList(this,'basic_page','businessSope','${supplierBranch.id}','2');"</c:if>>${supplierBranch.businessSope }</textarea>
                                <c:if test="${fn:contains(unableField,'businessSope_'.concat(supplierBranch.id))}">
                                    <a class='abolish'><img
                                            src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                                </c:if>
                            </div>
                        </li>
                    </c:if>
                </c:forEach>
            </ul>

            <div class="clear"></div>
            <h2 class="count_flow"><i>9</i>售后服务机构</h2>
            <ul class="ul_list">

                <table class="table table-bordered  table-condensed table-hover m_table_fixed_border">
                    <thead>
                    <tr>
                        <th class="info w50">序号</th>
                        <th class="info">分支（或服务）机构名称</th>
                        <th class="info">类别</th>
                        <th class="info">所在省市县</th>
                        <th class="info">负责人</th>
                        <th class="info">联系电话</th>
                        <th class="info">操作</th>
                    </tr>
                    </thead>
                    <tbody id="finance_attach_list_tbody_id">
                    <c:forEach items="${listSupplierAfterSaleDep}" var="a" varStatus="vs">
                        <tr>
                            <td class="tc w50">${vs.index + 1}</td>
                            <td class="tc" id="name_${a.id}"
                                <c:if test="${fn:contains(fieldAfterSaleDep,a.id.concat('_name'))}">style="border: 1px solid #FF8C00;"
                                onMouseOver="showModifyList(this,'basic_page','name','${a.id}','11');"</c:if>>${a.name}</td>
                            <td class="tc" id="type_${a.id}"
                                <c:if test="${fn:contains(fieldAfterSaleDep,a.id.concat('_type'))}">style="border: 1px solid #FF8C00;"
                                onMouseOver="showModifyList(this,'basic_page','type','${a.id}','11');"</c:if>>
                                <c:if test="${a.type == 1}">自营</c:if>
                                <c:if test="${a.type == 2}">合作</c:if>
                            </td>
                            <td class="tc" id="address_${a.id }"
                                <c:if test="${fn:contains(fieldAfterSaleDep,a.id.concat('_address'))}">style="border: 1px solid #FF8C00;"
                                onMouseOver="showModifyList(this,'basic_page','address','${a.id}','11');"</c:if>>${a.address}</td>
                            <td class="tc" id="leadName_${a.id}"
                                <c:if test="${fn:contains(fieldAfterSaleDep,a.id.concat('_leadName'))}">style="border: 1px solid #FF8C00;"
                                onMouseOver="showModifyList(this,'basic_page','leadName','${a.id}','11');"</c:if>>${a.leadName}</td>
                            <td class="tc" id="mobile_${a.id}"
                                <c:if test="${fn:contains(fieldAfterSaleDep,a.id.concat('_mobile'))}">style="border: 1px solid #FF8C00;"
                                onMouseOver="showModifyList(this,'basic_page','mobile','${a.id}','11');"</c:if>>${a.mobile}</td>
                            <td class="tc w50 hand">
                            	<%-- <c:if test="${!fn:contains(unableField,a.id)}">
                                <p onclick="auditList(this,'basic_page','${a.id}','售后服务机构','${a.name}');" id="${a.id}_hidden" class="editItem">
                                   <c:if test="${!fn:contains(auditField,a.id)}">
                                     <img src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'/>
                                   </c:if>
                                   <c:if test="${fn:contains(auditField,a.id)}">
                                     <img src='${pageContext.request.contextPath}/public/backend/images/light_icon_2.png'/>
                                   </c:if>
                                </p>
                              </c:if>
                              <c:if test="${fn:contains(unableField,a.id)}">
                                <img src='${pageContext.request.contextPath}/public/backend/images/sc.png' onclick="javascript:layer.msg('该条信息已审核并退回过！');"/>
                              </c:if> --%>
                              <c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/light_icon.png" />
                              <c:set var="iconCls" value="icon_edit" />
                              <c:if test="${!fn:contains(unableField,a.id) && fn:contains(auditField,a.id)}">
                              	<c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/light_icon_2.png" />
                              </c:if>
                              <c:if test="${fn:contains(unableField,a.id)}">
                                <c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/sc.png" />
                                <c:set var="iconCls" value="icon_sc" />
                              </c:if>
                              <img src="${iconUrl}" class="${iconCls}"
                              onclick="auditList(this,'basic_page','${a.id}','售后服务机构','${a.name}');" />
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </ul>
            
            <div class="clear"></div>
            <h2 class="count_flow"><i>10</i>参加政府或军队采购经历</h2>
            <ul class="ul_list">
                <li class="col-md-12 col-sm-12 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"
                          style="display:none">参加政府或军队采购经历登记表：</span>
                    <div class="col-md-12 col-sm-12 col-xs-12 p0">
                        <textarea class="col-md-12 col-xs-12 col-sm-12 h80" id="purchaseExperience"
                                  onclick="auditText(this,'basic_page','purchaseExperience')" <c:if test="${fn:contains(auditField,'purchaseExperience')}">style="border: 1px solid red;"</c:if><c:if
                                test="${fn:contains(field,'purchaseExperience')}"> style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'basic_page','purchaseExperience');"</c:if>
                                  >${currSupplier.purchaseExperience }</textarea>
                        <c:if test="${fn:contains(unableField,'purchaseExperience')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
            </ul>
            
            <div class="clear"></div>
            <h2 class="count_flow"><i>11</i>公司简介</h2>
            <ul class="ul_list">
                <li class="col-md-12 col-sm-12 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" style="display:none">公司简介：</span>
                    <div class="col-md-12 col-sm-12 col-xs-12 p0">
                        <textarea class="col-md-12 col-xs-12 col-sm-12 h80" id="description" onclick="auditText(this,'basic_page','description')"<c:if
                                test="${fn:contains(field,'description')}"> style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'basic_page','description');"</c:if>
                                  <c:if test="${fn:contains(auditField,'description')}">style="border: 1px solid red;"</c:if>>${currSupplier.description }</textarea>
                        <c:if test="${fn:contains(unableField,'description')}">
                            <a class='abolish'><img
                                    src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
                        </c:if>
                    </div>
                </li>
            </ul>
        </div>

        <div class="col-md-12 col-sm-12 col-xs-12 add_regist tc">
          <c:if test="${isStatusToAudit}">
            <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="tempAudit();">暂存</a>
          </c:if>
          <a class="btn" type="button" onclick="toStep('two');">下一步</a>
        </div>
    </div>
    <form target="_blank" id="download_form_id" action="${pageContext.request.contextPath}/supplierAudit/download.html"
          method="post">
        <input type="hidden" name="fileName"/>
    </form>
    <input name="supplierId" id="supplierId" value="${currSupplier.id }" type="hidden">
</div>
</body>

</html>