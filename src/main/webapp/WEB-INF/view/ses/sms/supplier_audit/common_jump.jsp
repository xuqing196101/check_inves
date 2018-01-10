<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%@ page import="org.apache.commons.lang3.math.NumberUtils"%>
<%@ page import="ses.constants.SupplierConstants"%>
<%@ page import="ses.model.bms.User" %>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<% 
	String ipAddressType = SupplierConstants.IP_ADDRESS_TYPE;
	String ipInner = SupplierConstants.IP_INNER;
	String ipOuter = SupplierConstants.IP_OUTER;
	
	String currentStep = StringUtils.defaultIfEmpty(request.getParameter("currentStep"), "one");
	String supplierId = request.getParameter("supplierId");
	int supplierSt = NumberUtils.toInt(request.getParameter("supplierStatus"), 0);
	int sign = NumberUtils.toInt(request.getParameter("sign"), 0);
	String account = ((User)session.getAttribute(SupplierConstants.KEY_SESSION_LOGIN_USER)).getLoginName();
	boolean isAudit = SupplierConstants.isAudit(account, supplierSt);
	boolean isStatusToAudit = SupplierConstants.isStatusToAudit(supplierSt);
	
	request.setAttribute("isAudit", isAudit);
	request.setAttribute("isStatusToAudit", isStatusToAudit);
	request.setAttribute("ipAddressType", ipAddressType);
	request.setAttribute("ipInner", ipInner);
	request.setAttribute("ipOuter", ipOuter);
	
	int reviewStatus = NumberUtils.toInt(request.getParameter("reviewStatus"));
	request.setAttribute("reviewStatus", reviewStatus);
%>
<%-- <c:set var="isAudit" value="<%=isAudit %>"/>
<c:set var="isStatusToAudit" value="<%=isStatusToAudit %>"/>
<c:set var="ipAddressType" value="<%=ipAddressType %>"/>
<c:set var="ipInner" value="<%=ipInner %>"/>
<c:set var="ipOuter" value="<%=ipOuter %>"/> --%>
<c:set var="sign" value="<%=sign %>"/>

<ul class="flow_step">
    <li id="reverse_of_one"  onclick="jump('essential')">
        <a aria-expanded="false" href="#tab-1">基本信息</a>
        <i></i>
    </li>
    <li id="reverse_of_two" onclick="jump('financial')">
        <a aria-expanded="true" href="#tab-2">财务信息</a>
        <i></i>
    </li>
    <li id="reverse_of_three" onclick="jump('shareholder')">
        <a aria-expanded="false" href="#tab-3">股东信息</a>
        <i></i>
    </li>
    <%--<c:if test="${fn:contains(supplierTypeNames, '生产')}">
      <li onclick = "jump('materialProduction')">
        <a aria-expanded="false" href="#tab-4">生产信息</a>
        <i></i>
      </li>
    </c:if>
    <c:if test="${fn:contains(supplierTypeNames, '销售')}">
      <li onclick = "jump('materialSales')" >
        <a aria-expanded="false" href="#tab-4" >销售信息</a>
        <i></i>
      </li>
    </c:if>
    <c:if test="${fn:contains(supplierTypeNames, '工程')}">
      <li onclick = "jump('engineering')">
        <a aria-expanded="false" href="#tab-4">工程信息</a>
        <i></i>
      </li>
    </c:if>
    <c:if test="${fn:contains(supplierTypeNames, '服务')}">
      <li onclick = "jump('serviceInformation')" >
        <a aria-expanded="false" href="#tab-4" >服务信息</a>
        <i></i>
      </li>
    </c:if>
    --%>
    <li id="reverse_of_four" onclick="jump('supplierType')">
        <a aria-expanded="false">供应商类型</a>
        <i></i>
    </li>
    <!-- <li onclick = "jump('items')">
      <a aria-expanded="false" href="#tab-4" >产品类别</a>
      <i></i>
    </li>
    <li onclick="jump('aptitude')">
      <a aria-expanded="false">资质文件维护</a>
      <i></i>
    </li>
    <li onclick = "jump('contract')" >
      <a aria-expanded="false" href="#tab-4">销售合同</a>
       <i></i>
    </li>-->
    <li id="reverse_of_five" onclick="jump('aptitude')">
        <a aria-expanded="false" href="#tab-4">产品类别及资质合同</a>
        <i></i>
    </li>
    <li id="reverse_of_six" onclick="jump('applicationForm')">
        <a aria-expanded="false" href="#tab-4">承诺书和申请表</a>
        <i></i>
    </li>
    <c:if test="${sign == 1}">
	    <li id="reverse_of_seven" onclick="jump('reasonsList')">
	        <a aria-expanded="false" href="#tab-4">审核汇总</a>
	        <i id="reverse_of_seven_i" class="display-none"></i>
	    </li>
	    <li id="reverse_of_eight" onclick="jump('uploadApproveFile')" class="display-none">
	        <a aria-expanded="false" href="#tab-4">上传批准审核表</a>
	    </li>
	  </c:if>
    <c:if test="${sign == 2}">
      <c:if test="${reviewStatus == 1}">
	      <li id="reverse_of_eleven" onclick="jump('historyReview')">
	        <a aria-expanded="false" href="#tab-4">历史复核信息</a>
	        <i></i>
	      </li>
      </c:if>
      <li id="reverse_of_nine" onclick="jump('review')">
        <a aria-expanded="false" href="#tab-4">供应商复核</a>
      </li>
    </c:if>
    <c:if test="${sign == 3}">
      <li id="reverse_of_ten" onclick="jump('inves')">
        <a aria-expanded="false" href="#tab-4">供应商实地考察</a>
      </li>
    </c:if>
</ul>
<style type="text/css">
  .border_red{
  	border: 1px solid #FF0000;
  }
</style>
<input type="hidden" id="isAudit" value="<%=isAudit %>" />
<input type="hidden" id="supplierId" value="<%=supplierId %>" />
<input type="hidden" id="supplierSt" value="<%=supplierSt %>" />
<input type="hidden" id="currentStep" value="<%=currentStep %>" />
<input type="hidden" id="sign" value="<%=sign %>" />
<script type="text/javascript">
	var isAudit = <%=isAudit %>;
	var supplierId = "<%=supplierId %>";
	var supplierSt = "<%=supplierSt %>";
	var currentStep = "<%=currentStep %>";
	var sign = "<%=sign %>";
	var reviewStatus = "<%=reviewStatus %>";
	
	$(function(){
		// 导航栏选中
		$("li[id$='reverse_of_']").removeClass("active");
		$("#reverse_of_"+currentStep).addClass("active").removeAttr("onclick");
		if(currentStep != "seven" && currentStep != "eight"){
			// 文本只读
			if(sign == 1){
				$("input[type='text'],textArea").attr("readonly", "readonly");
				// 文本添加title
				$("input[type='text'],textArea").each(function(){
					$(this).attr("title", $(this).val());
				});
				// 文本鼠标移入移出效果
				$("input[type='text'],textArea").each(function () {
				  var onmousemove = "this.style.background='#E8E8E8'";
				  var onmouseout = "this.style.background='#FFFFFF'";
				  $(this).attr("onmousemove", onmousemove);
				  $(this).attr("onmouseout", onmouseout);
				});
				// 隐藏
				//$("td,li").find("p").hide();
			}
		}
		// 预审核结束状态
		if(supplierSt == -2 || supplierSt == -3 || supplierSt == 3 || (supplierSt == 1 && sign == 1)){
			$("#reverse_of_seven_i").show();
			$("#reverse_of_eight").show();
		}
	});
	//以前的判断
	//if(supplierStatus == -2 || supplierStatus == 0 || supplierStatus == 9 || supplierStatus == 4 || (sign == 3 && supplierStatus == 5)){
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/sms/supplier_audit/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/sms/supplier_audit/jump.js"></script>
