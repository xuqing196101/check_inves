<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>添加诚信形式</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script type="text/javascript">
	
</script>
</head>

<body>
	<div class="wrapper mt50">
	<div class="container">
			<div class="p10_25">
				<form target="_parent" id="search_form_id" class="padding-10 border1 mb0" action="${pageContext.request.contextPath}/supplier_credit/save_or_update_supplier_credit.html" method="post">
					<input name="id" type="hidden" value="${supplierCredit.id}">
					<ul class="demand_list">
						<li class="fl">
							<label class="fl mt5">诚信形式名称：</label>
							<span><input name="name" type="text" value="${supplierCredit.name}" /></span>
						</li>
						<li class="fl mt1">
							<input class="btn" type="submit" value="保存" />
							<a target="_parent" class="btn" href="${pageContext.request.contextPath}/supplier_credit/list.html">返回</a>
						</li>
					</ul>
					<div class="clear"></div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
