<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <title>审核信息</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <%@ include file="/WEB-INF/view/ses/sms/supplier_query/supplierInfo/common.jsp"%>
    <script type="text/javascript" src="${ pageContext.request.contextPath }/js/ses/ems/expertQuery/common.js"></script>
  </head>
  </head>

  <body>
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
          </li>
          <c:choose>
            <c:when test="${person == 1 }">
              <li>
                <a href="javascript:void(0);">个人中心</a>
              </li>
              <li>
                <a href="javascript:void(0);">个人信息</a>
              </li>
            </c:when>
            <c:otherwise>
              <li>
                <a href="javascript:void(0);">支撑环境</a>
              </li>
              <li>
                <a href="javascript:void(0);">供应商管理</a>
              </li>
              <li>
                <a href="javascript:void(0);" onclick="jumppage('${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html?sign=1')">供应商列表</a>
              </li>
              <li>
                <a href="javascript:void(0);">供应商查看</a>
              </li>
            </c:otherwise>
          </c:choose>
        </ul>
      </div>
    </div>
    <div class="container container_box">
      <div class=" content height-350">
        <div class="col-md-12 tab-v2 job-content">
          <jsp:include page="/WEB-INF/view/ses/sms/supplier_query/supplierInfo/nav.jsp">
            <jsp:param name="nav_flag" value="9"></jsp:param>
            <jsp:param name="supplierStatus" value="${suppliers.status}"></jsp:param>
          </jsp:include>
          <div class="ul_list">
            <table class="table table-bordered table-condensed table-hover">
              <thead>
                <tr>
                  <th class="info">序号</th>
                  <th class="info">审批类型</th>
                  <th class="info">审批字段名字</th>
                  <th class="info">审批内容</th>
                  <th class="info">不通过理由</th>
                  <th class="info">审核时间</th>
                  <th class="info">状态</th>
                </tr>
              </thead>
              <c:forEach items="${auditList }" var="audit" varStatus="vs">
                <input id="auditId" value="${list.id}" type="hidden">
                <tr>
                  <td class="tc">${vs.index + 1}</td>
                  <td class="tc">
                    <c:if test="${audit.auditType eq 'basic_page'}">基本信息</c:if>
                    <c:if test="${audit.auditType eq 'finance_page'}">财务信息</c:if>
                    <c:if test="${audit.auditType eq 'shareholder_page'}">股东信息</c:if>
                    <c:if test="${audit.auditType eq 'mat_pro_page' || audit.auditType eq 'mat_sell_page' || audit.auditType eq 'mat_eng_page' || audit.auditType eq 'mat_serve_page' || audit.auditType eq 'supplierType_page'}">供应商类型</c:if>
                    <c:if test="${audit.auditType eq 'items_page'}">品目信息</c:if>
                    <c:if test="${audit.auditType eq 'items_product_page' or audit.auditType eq 'items_sales_page' or audit.auditType eq 'contract_product_page' or audit.auditType eq 'contract_sales_page' or audit.auditType eq 'aptitude_product_page' or audit.auditType eq 'aptitude_sales_page'}">产品类别及资质合同</c:if>
                    <c:if test="${audit.auditType eq 'aptitude_page'}">资质文件</c:if>
                    <c:if test="${audit.auditType eq 'contract_page'}">品目合同</c:if>
                    <c:if test="${audit.auditType eq 'download_page'}">申请表</c:if>
                  </td>
                  <td class="tl hand" title="${audit.auditFieldName }">
                    <c:if test="${fn:length (audit.auditFieldName) > 12}">${fn:substring(audit.auditFieldName,0,12)}...</c:if>
                    <c:if test="${fn:length(audit.auditFieldName) <= 12}">${audit.auditFieldName}</c:if>
                  </td>
                  <td class="tl hand" title="${audit.auditContent}">
                    <c:if test="${fn:length (audit.auditContent) > 20}">${fn:substring(audit.auditContent,0,20)}...</c:if>
                    <c:if test="${fn:length(audit.auditContent) <= 20}">${audit.auditContent}</c:if>
                  </td>
                  <td class="tl" title="${audit.suggest}">
                    <c:if test="${fn:length (audit.suggest) > 20}">${fn:substring(audit.suggest,0,20)}...</c:if>
                    <c:if test="${fn:length(audit.suggest) <= 20}">${audit.suggest}</c:if>
                  </td>
									<td class="tc" title="<fmt:formatDate value="${audit.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/>">
										<fmt:formatDate value="${audit.createdAt}" pattern="yyyy-MM-dd"/>
									</td>
									<td class="tc">
										<c:choose>
											<c:when test="${audit.returnStatus == 1}">退回修改</c:when>
											<c:when test="${audit.returnStatus == 2}">审核不通过</c:when>
											<c:when test="${audit.returnStatus == 3}">已修改</c:when>
											<c:when test="${audit.returnStatus == 4}">未修改</c:when>
											<c:when test="${audit.returnStatus == 5}">撤销退回</c:when>
											<c:when test="${audit.returnStatus == 6}">撤销不通过</c:when>
										</c:choose>
									</td>
                </tr>
              </c:forEach>
            </table>
          </div>
        </div>
        <div class="col-md-12 tc">
          <c:choose>
            <c:when test="${person == 1 }">
              <button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
            </c:when>
            <c:otherwise>
              <button class="btn btn-windows back" onclick="fanhui()">返回</button>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
      
      <form id="form_id" action="" method="post">
	      <input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
	      <input name="judge" value="${judge}" type="hidden">
	      <input name="sign" value="${sign}" type="hidden">
          <input name="person" value="${person}" type="hidden">
	    </form>
	    
	    <form id="form_back" action="" method="post">
	      <input name="judge" value="${judge}" type="hidden">
	      <c:if test="${sign!=1 and sign!=2 }">
	        <input name="address" id="address" value="${suppliers.address}" type="hidden">
	      </c:if>
	      <input name="sign" value="${sign}" type="hidden">
	    </form>
  </body>

</html>