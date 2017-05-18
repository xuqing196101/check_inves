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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/sms/supplier_credit/add_credit.js"></script>
<script type="text/javascript">
	
</script>
</head>

<body>
	<div class="wrapper mt50">
	<div class="container">
			<div class="p10_25">
				<form target="_parent" id="search_form_id" class="" action="" method="post">
					<input name="id" type="hidden" value="${supplierCredit.id}">
					<ul class="demand_list">
						<li class="fl">
							<span class="col-md-6 col-xs-6 col-sm-6">诚信形式名称：</span>
							<div class="col-md-6 col-xs-6 col-sm-6">
								<input class="input_group" name="name" id="name" type="text" class="mt10" type="text" value="${supplierCredit.name}" />
								<div class="cue"><span><font id="Err_Name"></span></div>
							</div>
						</li>
					</ul>
					<div class="tc mt10 col-md-12 col-xs-12">
					   <br>
					   <input id="submitForm" class="btn btn-windows save" type="button" value="保存" />
                            <a target="_parent" class="btn btn-windows back" href="${pageContext.request.contextPath}/supplier_credit/list.html">返回</a>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
