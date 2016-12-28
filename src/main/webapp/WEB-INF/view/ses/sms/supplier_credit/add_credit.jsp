<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../../../common.jsp"%>

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
				<form target="_parent" id="search_form_id" class="" action="${pageContext.request.contextPath}/supplier_credit/save_or_update_supplier_credit.html" method="post">
					<input name="id" type="hidden" value="${supplierCredit.id}">
					<ul class="demand_list tc">
						<li class=" fl">
							<label class="col-md-6 col-xs-6 col-sm-6">诚信形式名称：</label>
							<span class="col-md-6 col-xs-6 col-sm-6"><input name="name" class="mt10" type="text" value="${supplierCredit.name}" /></span>
						</li>
							<div class="clear"></div>
					</ul>
					<div class="tc mt10 col-md-12 col-xs-12">
					   <br>
					   <input class="btn" type="submit" value="保存" />
                            <a target="_parent" class="btn" href="${pageContext.request.contextPath}/supplier_credit/list.html">返回</a>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
