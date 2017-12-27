<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*,java.net.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>

<%@page contentType="application/vnd.ms-excel;charset=GBK"%>

<%
	String path = request.getContextPath();

	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>

<head>

<base href="${pageContext.request.contextPath}/">

<title>My JSP 'creatWord.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">

<meta http-equiv="cache-control" content="no-cache">

<meta http-equiv="expires" content="0">

<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">

<meta http-equiv="description" content="This is my page">


<style>
@page {
	mso-page-border-surround-header: no;
	mso-page-border-surround-footer: no;
}

@page Section1 {
	size: 841.9pt 595.3pt;
	mso-page-orientation: landscape;
	margin: 89.85pt 72.0pt 89.85pt 72.0pt;
	mso-header-margin: 42.55pt;
	mso-footer-margin: 49.6pt;
	mso-paper-source: 0;
	layout-grid: 15.6pt;
}

div.Section1 {
	page: Section1;
}
</style>

<%
	Date currentTime = new Date();
  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  String dateString = formatter.format(currentTime);
	String fileName = "任务查询明细信息"+"（" +dateString+ "）";
	String UserAgent = request.getHeader("USER-AGENT").toLowerCase();
	String tem = "";
	if (UserAgent != null) {
		if (UserAgent.indexOf("msie") >= 0)
			tem = "IE";
		if (UserAgent.indexOf("firefox") >= 0)
			tem = "FF";
		if (UserAgent.indexOf("safari") >= 0)
			tem = "SF";
	}
	if ("FF".equals(tem)) {
		// 针对火狐浏览器处理方式不一样了  
		fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1")
				+ ".xlsx";
	} else {
		fileName = URLEncoder.encode(fileName, "UTF-8") + ".xlsx";
	}
	//对中文文件名编码 
	response.setHeader("Content-disposition", "attachment; filename="
			+ fileName);
%>

</head>

<body>
		<table align="center" style="border-left:1px solid #dddddd; border-top:1px solid #dddddd; border-collapse: collapse;width:100%;">
			<thead>
				<tr>
					<th class="info w80" style="border-right: solid 1px #ddd; border-bottom: solid 1px #ddd;">任务名称</th>
					<th class="info w80" style="border-right: solid 1px #ddd; border-bottom: solid 1px #ddd;">任务文号</th>
					<th class="info w80" style="border-right: solid 1px #ddd; border-bottom: solid 1px #ddd;">需求部门</th>
					<th class="info w50" style="border-right: solid 1px #ddd; border-bottom: solid 1px #ddd;">序号</th>
					<th class="info" width="9%" style="border-right: solid 1px #ddd; border-bottom: solid 1px #ddd;">产品名称</th>
					<th class="info" width="9%" style="border-right: solid 1px #ddd; border-bottom: solid 1px #ddd;">质量标准</th>
					<th class="info" width="8%" style="border-right: solid 1px #ddd; border-bottom: solid 1px #ddd;">质量参数</th>
					<th class="info" width="5%" style="border-right: solid 1px #ddd; border-bottom: solid 1px #ddd;">单位</th>
					<th class="info" width="7%" style="border-right: solid 1px #ddd; border-bottom: solid 1px #ddd;">采购数量</th>
					<th class="info" width="7%" style="border-right: solid 1px #ddd; border-bottom: solid 1px #ddd;">单价<br/>(元)</th>
					<th class="info" width="10%" style="border-right: solid 1px #ddd; border-bottom: solid 1px #ddd;">预算价格<br/>(万元)</th>
					<th class="info" width="6%" style="border-right: solid 1px #ddd; border-bottom: solid 1px #ddd;">采购方式</th>
					<th class="w120" style="border-right: solid 1px #ddd; border-bottom: solid 1px #ddd;">采购机构</th>
					<th class="w100" style="border-right: solid 1px #ddd; border-bottom: solid 1px #ddd;">下达时间</th>
					<c:if test="${names ne null}">
					<c:forEach items="${names}" var="name">
						<th class="info" width="9%" style="border-right: solid 1px #ddd; border-bottom: solid 1px #ddd;">${name}</th>
					</c:forEach>
					</c:if>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="obj" varStatus="vs">
					<tr style="cursor: pointer;">
						<td class="tc" style="border-right: solid 1px #ddd; border-bottom: solid 1px #ddd;">${obj.taskName}</td>
						<td class="tc" style="border-right: solid 1px #ddd; border-bottom: solid 1px #ddd;">${obj.taskNumber}</td>
						<td class="tc" style="border-right: solid 1px #ddd; border-bottom: solid 1px #ddd;">${obj.department}</td>
						<td class="tc w50" style="border-right: solid 1px #ddd; border-bottom: solid 1px #ddd; vnd.ms-excel.numberformat:@">${obj.seq}</td>
						<td class="tl" style="border-right: solid 1px #ddd; border-bottom: solid 1px #ddd;">${obj.goodsName}</td>
						<td class="tl" style="border-right: solid 1px #ddd; border-bottom: solid 1px #ddd;">${obj.stand}</td>
						<td class="tl" style="border-right: solid 1px #ddd; border-bottom: solid 1px #ddd;">${obj.qualitStand}</td>
						<td class="tc" style="border-right: solid 1px #ddd; border-bottom: solid 1px #ddd;">${obj.item}</td>
						<td class="tr" style="border-right: solid 1px #ddd; border-bottom: solid 1px #ddd;">${obj.purchaseCount}</td>
						<td class="tc" style="border-right: solid 1px #ddd; border-bottom: solid 1px #ddd;">
							<fmt:formatNumber type="number" pattern="#,##0.00" value="${obj.price}" />
						</td>
						<td class="tr" style="border-right: solid 1px #ddd; border-bottom: solid 1px #ddd;">
							<fmt:formatNumber type="number" pattern="#,##0.00" value="${obj.budget}" />
						</td>
						<td class="tc" style="border-right: solid 1px #ddd; border-bottom: solid 1px #ddd;">${obj.purchaseType}</td>
						<td class="tc" style="border-right: solid 1px #ddd; border-bottom: solid 1px #ddd;">${obj.organization}</td>
						<td class="tc" style="border-right: solid 1px #ddd; border-bottom: solid 1px #ddd;"><fmt:formatDate value="${obj.taskGiveTime}" pattern="yyyy-MM-dd"/></td>
						<c:if test="${types ne null}">
							<c:forEach items="${types}" var="type">
							<td class="tl" style="border-right: solid 1px #ddd; border-bottom: solid 1px #ddd; vnd.ms-excel.numberformat:@">
								<c:if test="${type eq 'demandName'}">${obj.demandName}</c:if>
								<c:if test="${type eq 'demandNumber'}">${obj.demandNumber}</c:if>
								<c:if test="${type eq 'demandMan'}">${obj.demandMan}</c:if>
								<c:if test="${type eq 'planName'}">${obj.planName}</c:if>
								<c:if test="${type eq 'projectName'}">${obj.projectName}</c:if>
								<c:if test="${type eq 'projectNumber'}">${obj.projectName}</c:if>
								<c:if test="${type eq 'contractName'}">${obj.contractName}</c:if>
								<c:if test="${type eq 'contractCode'}">${obj.contractCode}</c:if>
								<c:if test="${type eq 'planNo'}">${obj.planNo}</c:if>
								<c:if test="${type eq 'userId'}">${obj.userId}</c:if>
								<c:if test="${type eq 'packName'}">${obj.packName}</c:if>
								<c:if test="${type eq 'packNumber'}">${obj.packNumber}</c:if>
								<c:if test="${type eq 'bidDate'}"><fmt:formatDate type='date' value='${obj.bidDate}' pattern=" yyyy-MM-dd HH:mm:ss " /></c:if>
								<c:if test="${type eq 'bidAddress'}">${obj.bidAddress}</c:if>
								<c:if test="${type eq 'expertName'}">${obj.expertName}</c:if>
								<c:if test="${type eq 'supplier'}">${obj.supplier}</c:if>
							</td>
							</c:forEach>
						</c:if>
					</tr>
				</c:forEach>
			</tbody>
		</table>
</body>

</html>