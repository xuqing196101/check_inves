<%@ page language="java" pageEncoding="UTF-8"%>
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
        <a aria-expanded="false" href="#tab-4" data-toggle="tab">审核汇总</a>
        <i id="reverse_of_seven_i" class="display-none"></i>
    </li>
    <li id="reverse_of_eight" onclick="jump('uploadApproveFile')" class="display-none">
        <a aria-expanded="false" href="#tab-4" data-toggle="tab">上传批准审核表</a>
    </li>

</ul>