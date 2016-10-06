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
		<form target="_parent" action="${pageContext.request.contextPath}/supplier_credit/save_or_update_supplier_credit.html" method="post">
			<div class="container">
				<div>
					<ul class="list-unstyled list-flow p0_20">
						<li class="col-md-6 p0"><span class="">诚信形式名称：</span>
							<div class="input-append">
								<input name="id" type="hidden" value="${supplierCredit.id}">
								<input class="span2" name="name" type="text" value="${supplierCredit.name}">
								<span class="add-on">i</span>
							</div>
						</li>
					</ul>
				</div>
				<div class="col-md-12 tc">
					<input class="btn btn-windows save" type="submit" value="保存" />
					<a target="_parent" class="btn btn-windows reset" href="${pageContext.request.contextPath}/supplier_credit/list.html">返回</a>
				</div>
			</div>
		</form>
	</div>
</body>
</html>
