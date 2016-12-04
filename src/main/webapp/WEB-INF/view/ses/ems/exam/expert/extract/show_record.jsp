<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../../../common.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
<!--<![endif]-->
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title></title>

<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css"
	media="screen" rel="stylesheet">
</head>
<script type="text/javascript">
	function cheClick() {
		var roleIds = "";
		var roleNames = "";
		$('input[name="chkItem"]:checked').each(function() {
			var idName = $(this).val();
			var arr = idName.split(";");
			roleIds += arr[0] + ",";
			roleNames += arr[1] + ",";
		});
		$("#roleId").val(roleIds.substr(0, roleIds.length - 1));
		$("#roleName").val(roleNames.substr(0, roleNames.length - 1));
	}
</script>
<body>
	<!-- 修改订列表开始-->
	<div class="container">
		<div>
			<div class="headline-v2">
				<h2>专家抽取表</h2>
			</div>
			<div class="content table_box">
				<table class="table table-bordered table-condensed">
					<tr>
						<td  class="bggrey" width="100px">项目名称:</td>
						<td colspan="6" width="150px" id="tName">${ExpExtractRecord.projectName}</td>
					</tr>
					<tr>
						<td  class="bggrey">抽取时间:</td>
						<td colspan="2" ><fmt:formatDate
								value="${ExpExtractRecord.extractionTime}"
								pattern="yyyy年MM月dd日   " /></td>
						<td class="bggrey" >抽取地点:</td>
						<td colspan="2" >${fn:replace(ExpExtractRecord.extractionSites,',','')}</td>
					</tr>
					<tr>
						<td colspan="8" align="center" class="bggrey">抽取记录</td>
					</tr>
					<tr>
						<td align="center">序号</td>
						<td align="center">包名</td>
						<td align="center">专家名称</td>
						<td align="center">联系电话</td>
						<td align="center">传真</td>
						<td align="center">工作单位</td>
					</tr>
					<c:forEach items="${listResultExpert}" var="pe" varStatus="vse">
					   <c:forEach items="${pe.listExperts}" var="ext" varStatus="vs">
							<tr>
								<td align="center">${vs.index+1 }</td>
								<td align="center">${pe.name}</td>
								<td align="center">${ext.relName}</td>
								<td align="center">${ext.mobile}</td>
								<td align="center">${ext.fax}</td>
								  <td align="center">${ext.workUnit}</td>
							</tr>
					</c:forEach>
					</c:forEach>
				</table>
			</div>
		</div>
		<div class="col-md-12">
			<div class="fl padding-10">
				<button class="btn btn-windows git" onclick="history.go(-1)"
					type="button">返回</button>
			</div>
		</div>
	</div>
</body>
</html>
