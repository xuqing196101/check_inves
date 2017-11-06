<%@ page language="java" pageEncoding="utf-8" %>
<%@ page import="org.apache.commons.lang3.math.NumberUtils" %>
<%String nav_flag = request.getParameter("nav_flag");%>
<%--<%Supplier supplierStatus = NumberUtils.toInt(request.getParameter("suppliers"));%>--%>
<%Integer sign = NumberUtils.toInt(request.getParameter("sign"));%>
<ul class="nav nav-tabs bgwhite">
    <li <%="1".equals(nav_flag) ? "class=\"active\"" : "" %>>
        <a aria-expanded="true" href="#tab-1" data-toggle="tab" class="f18" onclick="tijiao('essential');">基本信息</a>
    </li>
    <li id="financial_nav"  <%="2".equals(nav_flag) ? "class=\"active\"" : "" %>>
        <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('financial');">财务信息</a>
    </li>
    <li id="shareholder_nav"  <%="3".equals(nav_flag) ? "class=\"active\"" : "" %>>
        <a aria-expanded="fale" href="#tab-3" data-toggle="tab" class="f18" onclick="tijiao('shareholder');">股东信息</a>
    </li>
    <li id="supplierType_nav"  <%="4".equals(nav_flag) ? "class=\"active\"" : "" %>>
        <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('supplierType');">供应商类型</a>
    </li>
    <li id="item_nav" <%="5".equals(nav_flag) ? "class=\"active\"" : "" %>>
        <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('item');">产品类别</a>
    </li>
    <li id="aptitude_nav" <%="6".equals(nav_flag) ? "class=\"active\"" : "" %>>
        <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('zizhi');">资质文件</a>
    </li>
    <li id="contract_nav" <%="7".equals(nav_flag) ? "class=\"active\"" : "" %>>
        <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('contract');">销售合同</a>
    </li>
    <li id="template_upload_nav" <%="8".equals(nav_flag) ? "class=\"active\"" : "" %>>
        <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('table');">承诺书/申请表</a>
    </li>
    <%--<%
        if (SupplierConstants.Status.PENDING_REVIEW.getValue() != supplierStatus
                && SupplierConstants.Status.REVIEW_PASSED.getValue() != supplierStatus
                && SupplierConstants.Status.REVIEW_NOT_PASS.getValue() != supplierStatus
                && SupplierConstants.Status.PRE_INVESTIGATE_ENDED.getValue() != supplierStatus
                && SupplierConstants.Status.INVESTIGATE_PASSED.getValue() != supplierStatus
                && SupplierConstants.Status.INVESTIGATE_NOT_PASS.getValue() != supplierStatus) {
    %>--%>
    <%
        if (sign!=2) {
    %>
    <li id="auditInfo_nav" <%="9".equals(nav_flag) ? "class=\"active\"" : "" %>>
        <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('audit');">审核信息</a>
    </li>
    <%
        }
    %>
</ul>