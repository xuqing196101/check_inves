<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
<!--<![endif]-->
<head>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<%@ include file="../../../common.jsp"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<link rel="stylesheet"
    href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css"
    type="text/css">

<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
  <script type="text/javascript" src="<%=request.getContextPath() %>/js/ses/sms/personList.js"></script></head>
  <body>
  	<form action="" method="post" >
  		姓名：<input name="name">
  		单位：<input name="compary">
  		职务：<input name="duty">
  		军衔：<input name="rank">
  		<input type="hidden" id="personType" value="${personType}">
  		<input type="button" onclick="getPersonList()" class="btn list_btn" value="查询">
  		<input type="reset" class="btn list_btn" value="重置">
  	</form>
     <table class="table table-bordered table-condensed table_input left_table">
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
            </tbody>
          </table>
  </body>
</html>
