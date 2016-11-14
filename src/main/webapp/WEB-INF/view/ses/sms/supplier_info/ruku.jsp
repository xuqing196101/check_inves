<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]-->
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>基本信息</title>
<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
</head>
<body>
 <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 供应商入库状态查询页面</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
  <!-- 项目戳开始 -->
  <div class="container clear margin-top-30">
   <div class="container">
    </div>
  <!--详情开始-->
        <div class="padding-top-10">
               <table class="table table-bordered">
					<tbody>
						<tr>
							<td style="width:26%" class="bggrey tr">供应商名称：</td>
							<td style="width:26%">${suppliers.supplierName }</td>
							<td style="width:24%" class="bggrey tr">企业类型：</td>
							<td style="width:24%">${supplierType }</td>
						</tr>
						<tr>
							<td style="width:26%" class="bggrey tr">企业性质：</td>
							<td style="width:26%">${suppliers.businessType }</td>
							<td style="width:24%" class="bggrey tr">审核状态：</td>
							<td style="width:24%">
								审核通过
							</td>
						</tr>
					</tbody>
				</table>
</div>
</div>
</body>
</html>
