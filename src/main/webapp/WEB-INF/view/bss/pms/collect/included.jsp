<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>


<title>采购需求管理</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">


 <jsp:include page="/WEB-INF/view/common.jsp"/> 

<script type="text/javascript">
 
    
 
 
</script>
</head>

<body>
		<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="#"> 首页</a></li>
				<li><a href="#">障碍作业系统</a></li>
				<li><a href="#">采购计划管理</a></li>
				<li class="active"><a href="#">采购计划查看明细</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
 
	<div class="container">
	 
		<div class="container clear margin-top-30">

		 
				<table class="table table-bordered table-condensed mt5">
					<thead>
						<tr>
							<th class="info w50">序号</th>
							<th class="info">需求部门</th>
							<th class="info">物资类别及物种名称</th>
							<th class="info">规格型号</th>
							<th class="info">质量技术标准（技术参数）</th>
							<th class="info">计量单位</th>
							<th class="info">采购数量</th>
							<th class="info">单位（元）</th>
							<th class="info">预算金额（万元）</th>
							<th class="info">交货期限</th>
							<th class="info">采购方式建议</th>
							<th class="info">供应商名称</th>
							<th class="info">是否申请办理免税</th>
							<th class="info">物资用途（仅进口）</th>
							<th class="info">使用单位（仅进口）</th>
							<th class="info">备注</th>
						</tr>
					</thead>

				 <c:forEach items="${list}" var="obj">
						<tr>
							<td class="tc w50">${obj.seq } 
							</td>
							<td> ${obj.department }  </td>
							<td>${obj.goodsName }</td>
							<td class="tc"> ${obj.stand }</td>
							<td class="tc"> ${obj.qualitStand }</td>
							<td class="tc"> ${obj.item }</td>
							<td class="tc">${obj.purchaseCount }</td>
							<td class="tc">${obj.price }</td>
							<td class="tc">${obj.budget }</td>
							<td>${obj.deliverDate }</td>
							<td>${obj.purchaseType }</td>
							<td class="tc">${obj.supplier }</td>
							<td class="tc">${obj.isFreeTax }</td>
							<td class="tc">${obj.goodsUse }</td>
							<td class="tc">${obj.useUnit }</td>
							<td class="tc">${obj.memo }
						
							</td>
						</tr>

					</c:forEach>  
					</table>
 
		 <input class="btn btn-windows reset" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
		</div>
	</div>

</body>
</html>
