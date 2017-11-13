<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>

<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<title>产品类别及资质合同</title>
<script	src="${pageContext.request.contextPath}/js/ses/sms/supplier_audit/aptitude.js"></script>
<script src="${pageContext.request.contextPath}/js/ses/sms/supplier_audit/merge_aptitude.js"></script>
<script src="${pageContext.request.contextPath}/js/ses/sms/supplier_audit/aptitude_items.js"></script>
</head>
<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a
					href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')">
						首页</a></li>
				<li><a>支撑环境</a></li>
				<li><a>供应商管理</a></li>
				<c:if test="${sign == 1}">
					<li><a
						href="javascript:jumppage('${pageContext.request.contextPath}/supplierAudit/supplierAll.html?sign=1')">供应商审核</a>
					</li>
				</c:if>
				<c:if test="${sign == 2}">
					<li><a
						href="javascript:jumppage('${pageContext.request.contextPath}/supplierAudit/supplierAll.html?sign=2')">供应商复核</a>
					</li>
				</c:if>
				<c:if test="${sign == 3}">
					<li><a
						href="javascript:jumppage('${pageContext.request.contextPath}/supplierAudit/supplierAll.html?sign=3')">供应商实地考察</a>
					</li>
				</c:if>
			</ul>
		</div>
	</div>
	
	<div class="container container_box">
		<div class="content ">
			<div class="col-md-12 tab-v2 job-content">
				<%-- <%@include file="/WEB-INF/view/ses/sms/supplier_audit/common_jump.jsp"%> --%>
        <jsp:include page="/WEB-INF/view/ses/sms/supplier_audit/common_jump.jsp">
        	<jsp:param value="${supplierStatus }" name="supplierStatus"/>
        </jsp:include>
				<ul id="page_ul_id" class="nav nav-tabs bgdd supplier_tab count_flow ">
				</ul>
				<div class="tab-content padding-top-20 tab-pane fade display-none"   id="tab_1">
				<div class="mb10">
				  <c:if test="${isAudit}">
            <button class="btn btn-windows cancel" type="button" onclick="auditCategory('content_1');">不通过</button>
            <button class="btn btn-windows cancel" type="button" onclick="auditContract('content_1');">退回合同</button>
            <button class="btn btn-windows edit" type="button" onclick="updateAudit('content_1');">更新审核</button>
          </c:if>
        </div>
					<table class="table table-bordered table-condensed table-hover m_table_fixed_border" id="content_1">
						 <thead>
							<tr>
							 <th class="w50 info"><input id="content_1checkAll" type="checkbox" onclick="selectAll('content_1')" /></th>
							  <td class="tc info">序号</td>
							  <td class="tc info">类别</td>
							  <td class="tc info">大类</td>
							  <td class="tc info">中类</td>
							  <td class="tc info">小类</td>
							  <td class="tc info">品种名称</td>
							  <td class="tc info">专业资质要求</td>
							  <td class="tc info">销售合同</td>
							</tr>
						</thead> 
						<tbody>
						</tbody>
					</table>
				</div>
				<div class="tab-content padding-top-20 tab-pane fade display-none"   id="tab_2">
				<div class="mb10">
				  <c:if test="${isAudit}">
            <button class="btn btn-windows cancel" type="button" onclick="auditCategory('content_2');">不通过</button>
            <button class="btn btn-windows cancel" type="button" onclick="auditContract('content_2');">退回合同</button>
            <button class="btn btn-windows edit" type="button" onclick="updateAudit('content_2');">更新审核</button>
          </c:if>
                </div>
                    <table class="table table-bordered table-condensed table-hover m_table_fixed_border" id="content_2">
                    
                         <thead>
                            <tr>
                              <th class="w50 info"><input id="content_2checkAll" type="checkbox" onclick="selectAll('content_2')" /></th>
                              <td class="tc info">序号</td>
                              <td class="tc info">类别</td>
                              <td class="tc info">大类</td>
                              <td class="tc info">中类</td>
                              <td class="tc info">小类</td>
                              <td class="tc info">品种名称</td>
                              <td class="tc info">专业资质要求</td>
                              <td class="tc info">销售合同</td>
                            </tr>
                        </thead> 
                        <tbody>
                        </tbody>
                    </table>
                </div>
                <div class="tab-content padding-top-20 tab-pane fade display-none"   id="tab_3">
                <div class="mb10">
                  <c:if test="${isAudit}">
                    <button class="btn btn-windows cancel" type="button" onclick="auditCategory('content_3');">不通过</button>
                    <button class="btn btn-windows edit" type="button" onclick="updateAudit('content_3');">更新审核</button>
                  </c:if>
                </div>
                    <table class="table table-bordered table-condensed table-hover m_table_fixed_border" id="content_3">
                         <thead>
                            <tr>
                            <th class="w50 info"><input id="content_3checkAll" type="checkbox" onclick="selectAll('content_3')" /></th>
                              <td class="tc info">序号</td>
                              <td class="tc info">类别</td>
                              <td class="tc info">大类</td>
                              <td class="tc info">中类</td>
                              <td class="tc info">小类</td>
                              <td class="tc info">品种名称</td>
                              <td class="tc info">专业资质要求</td>
                            </tr>
                        </thead> 
                        <tbody>
                        </tbody>
                    </table>
                </div>
                <div class="tab-content padding-top-20 tab-pane fade display-none"   id="tab_4">
                 <div class="mb10">
                   <c:if test="${isAudit}">
                     <button class="btn btn-windows cancel" type="button" onclick="auditCategory('content_4');">不通过</button>
                     <button class="btn btn-windows cancel" type="button" onclick="auditContract('content_4');">退回合同</button>
                     <button class="btn btn-windows edit" type="button" onclick="updateAudit('content_4');">更新审核</button>
                   </c:if>
                </div>
                    <table class="table table-bordered table-condensed table-hover m_table_fixed_border" id="content_4">
                         <thead>
                            <tr>
                            <th class="w50 info"><input id="content_4checkAll" type="checkbox" onclick="selectAll('content_4')" /></th>
                              <td class="tc info">序号</td>
                              <td class="tc info">类别</td>
                              <td class="tc info">大类</td>
                              <td class="tc info">中类</td>
                              <td class="tc info">小类</td>
                              <td class="tc info">品种名称</td>
                              <td class="tc info">专业资质要求</td>
                              <td class="tc info">销售合同</td>
                            </tr>
                        </thead> 
                        <tbody>
                        </tbody>
                    </table>
                </div>
				<div id="pagediv" align="right"></div>
				<div class="col-md-12 col-sm-12 col-xs-12 add_regist tc">
					<a class="btn" type="button" onclick="lastStep();">上一步</a>
					  <c:if test="${isStatusToAudit}">
					    <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="zhancun();">暂存</a>
					  </c:if>
						 <a class="btn" type="button" onclick="nextStep();">下一步</a>
				</div>
			</div>
		</div>
	</div>
	<form id="form_id" action="" method="post">
		<input id="supplierId" name="supplierId" value="${supplierId}" type="hidden"> 
		<input id="pageNum" name="pageNum" value="1" type="hidden">
		<input name="supplierStatus" id="status" value="${supplierStatus}" type="hidden">
		<input type="hidden" id="supplierTypes" value="${supplierTypes}">
		<input type="hidden" name="supplierType" id="supplierType">
		<input type="hidden" name="sign" value="${sign}">
		<input type="hidden" id="tablerId">
	</form>
</body>

</html>