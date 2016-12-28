<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML >
<html>
<head>
<%@ include file="../../../common.jsp"%>

<title>添加诚信形式内容</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script type="text/javascript">
</script>
</head>

<body>
	<div class="wrapper mt30">
		<form action="${pageContext.request.contextPath}/supplier_credit_ctnt/save_or_update_supplier_credit_ctnt.html" method="post">
			<div class="container">
				<div>
					<div class="headline-v2">
						<h2>添加诚信形式内容</h2>
					</div>
					<ul class="list-unstyled p0_20">
						<li class="col-md-6 col-sm-6 col-xs-6 pl15">
						    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">诚信内容名称：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 p0 input_group">
								<input name="supplierCreditId" type="hidden" value="${supplierCreditCtnt.supplierCreditId}" />
								<input name="id" type="hidden" value="${supplierCreditCtnt.id}" />
								<input name="name" class="span2" type="text" value="${supplierCreditCtnt.name}"> 
								<span class="add-on">i</span>
							</div>
						</li>
						<li class="col-md-6 col-sm-6 col-xs-6 ">
						    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">诚信内容分数：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 p0 input_group">
								<input name="score" class="span2" type="text" value="${supplierCreditCtnt.score}"> 
								<span class="add-on">i</span>
							</div>
						</li>
					</ul>
				</div>
				<div class="col-md-12 tc col-xs-12 col-sm-12 mt10">
					<input class="btn btn-windows save" type="submit" value="保存" />
					<input class="btn btn-windows back" onclick="history.go(-1)" type="button" value="返回">
				</div>
			</div>
		</form>
	</div>
</body>
</html>
