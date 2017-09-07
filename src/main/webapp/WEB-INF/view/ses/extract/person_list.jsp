<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
<!--<![endif]-->
<head>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<%@ include file="../../common.jsp"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css" type="text/css">
<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<script type="text/javascript" src="<%=request.getContextPath() %>/js/ses/sms/personList.js"></script>
</head>
<body>
	<div class="p0_10 mt10">
	<form action="" method="post">
		<div class="col-xs-1 h30 lh30 p0 tr">姓名：</div><div class="col-xs-5 pl0 pr10"><input name="name" type="text" class="w100p"></div>
		<div class="col-xs-1 h30 lh30 p0 tr">单位：</div><div class="col-xs-5 p0"><input name="compary" type="text" class="w100p"></div>
		<div class="col-xs-1 h30 lh30 p0 tr">职务：</div><div class="col-xs-5 pl0 pr10"><input name="duty" type="text" class="w100p"></div>
		<div class="col-xs-1 h30 lh30 p0 tr">军衔：</div><div class="col-xs-5 p0"><input name="rank" type="text" class="w100p"></div>
		<input type="hidden" id="personType" value="${personType}">
		<div class="col-xs-12 tr p0">
			<input type="button" onclick="getPersonList()" class="btn" value="查询">
			<input type="reset" class="btn m0" value="重置">
		</div>
		<div class="clear"></div>
	</form>
	<div class="clear"></div>
	<table class="table table-bordered table-hover mt10">
		<thead>				
			<tr>
				<th class="info"><input type="checkbox" onclick="checkAll(this)"> </th>
				<th class="info">序号</th>
				<th class="info" width="15%">姓名</th>
				<th class="info" width="40%">单位</th>
				<th class="info" width="15%">职务</th>
				<th class="info" width="15%">军衔</th>
			</tr>
		</thead>
		<tbody id="personList">
		<c:forEach items="${personList }" var="pl" varStatus="v">
			<tr>
				<td class='tc h30 lh30'> <input type='checkbox' name='id' value="${pl.id }"> </td>
		        <td class='tc h30 lh30'> ${v.count} </td>
            	<td class='tc h30 lh30'> ${pl.name}</td>
             	<td class='tc h30 lh30'>${pl.compary} </td>
             	<td class='tc h30 lh30'>${pl.duty} </td>
             	<td class='tc h30 lh30'> ${pl.rank}</td>
		    </tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
</body>
</html>