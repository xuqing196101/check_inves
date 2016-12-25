<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>
    <title>供应商信息</title>  
  </head>
  
  <body>
  <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">个人首页</a></li><li><a href="javascript:void(0)">项目评审</a></li><li><a href="javascript:void(0)">包内供应商信息</a></li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
	<!-- 录入采购计划开始-->
 	<div class="container">
	  <!-- 项目戳开始 -->
      <table class="margin-left-10">
 	      <th><h2>项目名称：</h2></th><th><h2>${packages.project.name }（${packages.name }）&nbsp;&nbsp;&nbsp;&nbsp;</h2></th>
 	      <th><h2>编号：</h2></th><th><h2>${packages.project.projectNumber }</h2></th>
 	      <tr>
 	        <th></th>
 	      </tr>
 	  </table>
      <div align="right">
	    <button class="btn padding-rightp-10" onclick="window.print();" type="button">打印</button>
      </div>      
      <div class="container margin-top-5">
      <table class="table table-striped table-bordered table-hover">
        <tr>
          <th class="info w50">序号</th>
          <th class="info">供应商名称</th>
          <th class="info">网址</th>
          <th class="info">成立日期</th>
          <th class="info">联系人</th>
          <th class="info">联系方式</th>
        </tr>
        <c:forEach items="${supplierList}" var="supplier" varStatus="vs">
	      <tr style="cursor: pointer;">
	        <td class="tc w30">${vs.count}</td>
	        <td class="tc w50">${supplier.suppliers.supplierName}</td>
	        <td class="tc">${supplier.suppliers.website}</td>
            <td class="tc"><fmt:formatDate value="${supplier.suppliers.foundDate}" pattern="yyyy-MM-dd"/></td>
            <td class="tc">${supplier.suppliers.contactName}</td>
            <td class="tc">${supplier.suppliers.contactTelephone}</td>
          </tr>
        </c:forEach>
      </table>
      <div class="tc">
        <input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'"><br/>
      </div>      
    </div>
  </body>
</html>
