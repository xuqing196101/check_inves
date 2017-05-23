<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]-->
<head>
<%@ include file="../../../common.jsp"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>
	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">

</head>
<body>


<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0);"> 首页</a></li><li><a href="javascript:void(0);">履约情况管理</a></li><li><a href="javascript:void(0);">履约情况详情</a></li></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
<!-- 修改订列表开始-->
    <div class="container content pt0">
	 <div class="row magazine-page">
	   <div class="col-md-12 col-sm-12 col-cs-12 tab-v2">
	        <div class="padding-top-10">
	        <ul class="nav nav-tabs bgwhite">
	            <li class="active"><a aria-expanded="true" href="javascript:void(0)" data-toggle="tab" class="f18">履约详情</a></li>
            </ul>
  	<div class="tab-content padding-top-20 over_hideen">
	<div class="tab-pane fade active in" id="tab-1">
	    <table class="table table-bordered">
	        <tbody>
	        <tr>
	            <td class="bggrey"  width="14%">产品质量检验结果：</td>
	            <td colspan="5">${performance.checkMass}</td>
	        </tr>
	        <tr>
	            <td class="bggrey"  width="14%">合同草稿签订时间：</td>
	            <td><fmt:formatDate value="${performance.draftSignedAt}" pattern="yyyy/MM/dd"/></td>
	            <td class="bggrey"  width="14%">正式合同签订时间：</td>
	            <td><fmt:formatDate value="${performance.formalSignedAt}" pattern="yyyy/MM/dd"/></td>
	        </tr>
	        <tr>
	            <td class="bggrey"  width="14%">产品交付日期：</td>
	            <td><fmt:formatDate value="${performance.delivery}" pattern="yyyy/MM/dd"/> </td>
	             <td class="bggrey"  width="14%">合同执行状态：</td>
	            <td>
	            <c:if test="${performance.completedStatus==0 }">
	            执行中
	            </c:if>
	            <c:if test="${performance.completedStatus==1}">
	            终止
	            </c:if>
	             <c:if test="${performance.completedStatus==2}">
	            变更
	            </c:if>
	            <c:if test="${ performance.completedStatus==3}">
	            完成
	            </c:if>
	            </td>
	        </tr>
	        <tr>
	           
	            <td class="bggrey"  width="14%">交货进度：</td>
	            <td>${performance.deliverySchedule }</td>
	            <td class="bggrey"  width="14%">资金交付百分比(%)：</td>
	            <td>${performance.fundsPaid}</td>
	        </tr>
	       
	        </tbody>
	        </table>
  	<!-- 按钮 -->
		<div class="col-md-12 tc ">
   			<input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
 		</div>
  	</div>
  	</div>
 </div>
</body>
</html>
