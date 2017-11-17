<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%@ page import="org.apache.commons.lang3.math.NumberUtils"%>
<%@ page import="ses.constants.SupplierConstants"%>
<%@ page import="ses.model.bms.User" %>
<% 
	String ipAddressType = SupplierConstants.IP_ADDRESS_TYPE;
	String ipInner = SupplierConstants.IP_INNER;
	String ipOuter = SupplierConstants.IP_OUTER;
	
	String currentStep = StringUtils.defaultIfEmpty(request.getParameter("currentStep"), "one");
	String supplierId = request.getParameter("supplierId");
	int supplierSt = NumberUtils.toInt(request.getParameter("supplierStatus"), 0);
	String account = ((User)session.getAttribute(SupplierConstants.KEY_SESSION_LOGIN_USER)).getLoginName();
	boolean isAudit = SupplierConstants.isAudit(account, supplierSt);
	boolean isStatusToAudit = SupplierConstants.isStatusToAudit(supplierSt);
	
	request.setAttribute("isAudit", isAudit);
	request.setAttribute("isStatusToAudit", isStatusToAudit);
	request.setAttribute("ipAddressType", ipAddressType);
	request.setAttribute("ipInner", ipInner);
	request.setAttribute("ipOuter", ipOuter);
%>
<%-- <c:set var="isAudit" value="<%=isAudit %>"/>
<c:set var="isStatusToAudit" value="<%=isStatusToAudit %>"/>
<c:set var="ipAddressType" value="<%=ipAddressType %>"/>
<c:set var="ipInner" value="<%=ipInner %>"/>
<c:set var="ipOuter" value="<%=ipOuter %>"/> --%>

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
       <i></i>-->
    </li>
    <li id="reverse_of_five" onclick="jump('aptitude')">
        <a aria-expanded="false" href="#tab-4">产品类别及资质合同</a>
        <i></i>
    </li>
    <li id="reverse_of_six" onclick="jump('applicationForm')">
        <a aria-expanded="false" href="#tab-4">承诺书和申请表</a>
        <i></i>
    </li>
    <li id="reverse_of_seven" onclick="jump('reasonsList')">
        <a aria-expanded="false" href="#tab-4">审核汇总</a>
        <i id="reverse_of_seven_i" class="display-none"></i>
    </li>
    <li id="reverse_of_eight" onclick="jump('uploadApproveFile')" class="display-none">
        <a aria-expanded="false" href="#tab-4">上传批准审核表</a>
    </li>
</ul>

<script type="text/javascript">
	var isAudit = "<%=isAudit %>";
	var supplierId = "<%=supplierId %>";
	var supplierSt = "<%=supplierSt %>";
	
	$(function () {
		// 导航栏选中
		$("li[id$='reverse_of_']").removeClass("active");
    $("#reverse_of_<%=currentStep %>").addClass("active").removeAttr("onclick");
    // 文本只读
    $(":input").attr("readonly", "readonly");
    // 文本添加title
    $("input[type='text']").each(function(){
    	$(this).attr("title", $(this).val());
    });
    // 文本鼠标移入移出效果
    $(":input").each(function () {
      var onmousemove = "this.style.background='#E8E8E8'";
      var onmouseout = "this.style.background='#FFFFFF'";
      $(this).attr("onmousemove", onmousemove);
      $(this).attr("onmouseout", onmouseout);
    });
    // 隐藏
    //$("td,li").find("p").hide();
  });
	//以前的判断
	//if(supplierStatus == -2 || supplierStatus == 0 || supplierStatus == 9 || supplierStatus == 4 || (sign == 3 && supplierStatus == 5)){
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/sms/supplier_audit/common.js"></script>
