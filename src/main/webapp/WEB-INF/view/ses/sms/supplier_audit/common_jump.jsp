<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang3.math.NumberUtils"%>
<%@ page import="ses.constants.SupplierConstants"%>
<%@ page import="ses.model.bms.User" %>
<% 
	String ipAddressType = SupplierConstants.IP_ADDRESS_TYPE;
	String ipInner = SupplierConstants.IP_INNER;
	String ipOuter = SupplierConstants.IP_OUTER;
	int status = NumberUtils.toInt(request.getParameter("supplierStatus"), 0);
	String account = ((User)session.getAttribute(SupplierConstants.KEY_SESSION_LOGIN_USER)).getLoginName();
	boolean isAudit = SupplierConstants.isAudit(account, status);
	boolean isStatusToAudit = SupplierConstants.isStatusToAudit(status);
	
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
	var isAudit = <%=isAudit %>;
	//以前的判断
	//if(supplierStatus == -2 || supplierStatus == 0 || supplierStatus == 9 || supplierStatus == 4 || (sign == 3 && supplierStatus == 5)){
</script>
<script type="text/javascript">

	// 删除左右两端的空格
	function trim(str) {
		return str.replace(/(^\s*)|(\s*$)/g, "");
	}

	// 获取旧的审核记录
	function getOldAudit(auditData) {
		var result = null;
		$.ajax({
			url : "${pageContext.request.contextPath}/supplierAudit/ajaxOldAudit.do",
			type : "post",
			dataType : "json",
			data : auditData,
			async : false,
			success : function(data) {
				result = data;
			}
		});
		return result;
	}
	
	// 获取旧的审核记录
	function getOldAuditMuti(auditData) {
		var result = null;
		$.ajax({
			url : "${pageContext.request.contextPath}/supplierAudit/ajaxOldAuditMuti.do",
			type : "post",
			dataType : "json",
			contentType: "application/json",
			data : auditData,
			async : false,
			success : function(data) {
				result = data;
			}
		});
		return result;
	}

	// 撤销审核记录
	function cancelAudit(auditData) {
		var bool = false;
		$.ajax({
			url : "${pageContext.request.contextPath}/supplierAudit/cancelAudit.do",
			type : "post",
			dataType : "json",
			data : auditData,
			async : false,
			success : function(result) {
				if (result && result.status == 500) {
					bool = true;
					layer.msg('撤销成功！');
				}
			}
		});
		return bool;
	}
	
	// 撤销审核记录
	function cancelAuditMuti(auditData) {
		var bool = false;
		$.ajax({
			url : "${pageContext.request.contextPath}/supplierAudit/cancelAuditMuti.do",
			type : "post",
			dataType : "json",
			contentType: "application/json",
			data : auditData,
			async : false,
			success : function(result) {
				if (result && result.status == 500) {
					bool = true;
					layer.msg('撤销成功！');
				}
			}
		});
		return bool;
	}
</script>
