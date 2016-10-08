<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>添加诚信形式内容</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script type="text/javascript">
	$(function() {
		autoSelected("supplier_credit_select_id", "${supplierCreditCtnt.supplierCreditId}");
	});
	function autoSelected(id, v) {
		if (v) {
			$("#" + id).find("option").each(function() {
				var value = $(this).val();
				if (value == v) {
					$(this).prop("selected", true);
				} else {
					$(this).prop("selected", false);
				}
			});
		}
	}
</script>
</head>

<body>
	<div class="wrapper">
		<form target="_parent" action="${pageContext.request.contextPath}/supplier_credit_ctnt/save_or_update_supplier_credit_ctnt.html" method="post">
			<div class="container">
				<div>
					<div class="headline-v2">
						<h2>添加诚信形式内容</h2>
					</div>
					<ul class="list-unstyled list-flow p0_20">
						<li class="col-md-6 p0"><span class="">诚信内容名称：</span>
							<div class="input-append">
								<input name="id" type="hidden" value="${supplierCreditCtnt.id}" />
								<input name="name" class="span2" type="text" value="${supplierCreditCtnt.name}"> 
								<span class="add-on">i</span>
							</div>
						</li>
						<li class="col-md-6 p0"><span class="">诚信内容分数：</span>
							<div class="input-append">
								<input name="score" class="span2" type="text" value="${supplierCreditCtnt.score}"> 
								<span class="add-on">i</span>
							</div>
						</li>
						<li class="col-md-6 p0"><span class="">诚信形式：</span>
							<div class="input-append">
								<select id="supplier_credit_select_id" class="span2 fz15" name="supplierCreditId">
									<c:forEach items="${listSupplierCredits}" var="supplierCredit">
										<option value="${supplierCredit.id}">${supplierCredit.name}</option>
									</c:forEach>
								</select> 
								<span class="add-on">i</span>
							</div>
						</li>
					</ul>
				</div>
				<div class="col-md-12 tc">
					<input class="btn btn-windows save" type="submit" value="保存" />
					<input class="btn btn-windows reset" onclick="history.go(-1)" type="button" value="返回">
				</div>
			</div>
		</form>
	</div>
</body>
</html>
