<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en">
	<!--<![endif]-->

	<head>
		<%@ include file="../../../../common.jsp"%>
		<%@ include file="/WEB-INF/view/ses/sms/supplier_query/supplierInfo/common.jsp"%>
		<script type="text/javascript" src="${ pageContext.request.contextPath }/js/ses/ems/expertQuery/common.js"></script>
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

		<!--详情开始-->
		<div class="container container_box">
			<div class="content height-350">
				<div class="col-md-12 tab-v2 job-content">
					<jsp:include page="/WEB-INF/view/ses/sms/supplier_query/supplierInfo/nav.jsp">
						<jsp:param name="nav_flag" value="3"></jsp:param>
						<jsp:param name="supplierStatus" value="${suppliers.status}"></jsp:param>
					</jsp:include>
					<div class="tab-content padding-top-20">
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
						<table class="table table-bordered table-condensed table-hover">
							<thead>
								<tr>
									<th>序号</th>
									<th class="info">出资人性质</th>
									<th class="info">出资人名称或姓名</th>
									<th class="info">统一社会信用代码或身份证号码</th>
									<th class="info">出资金额或股份（万元/万份）</th>
									<th class="info">比例（%）</th>
								</tr>
							</thead>
							<c:forEach items="${shareholder}" var="s" varStatus="vs">
								<tr>
									<td class="tc">${vs.index + 1}</td>
									<td class="tc">
										<c:if test="${s.nature eq '1'}">法人</c:if>
		              	<c:if test="${s.nature eq '2'}">自然人</c:if>
									</td>
									<td class="tc" id="${s.id }">${s.name}</td>
									<td class="tc">${s.identity}</td>
									<td class="tc">${s.shares}</td>
									<td class="tc">${s.proportion}</td>
								</tr>
							</c:forEach>
						</table>
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
				</div>  
			</div>
     </div>
	</body>

</html>