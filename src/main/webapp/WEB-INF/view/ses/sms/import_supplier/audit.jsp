<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="../../../common.jsp"%>
		<title>进口供应商注册</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<script type="text/javascript">
			function tijiao(status) {
				$("#status").val(status);
				form1.submit();
			}
			var parentId;
			var addressId = "${is.address}";
			$(function() {
				var parentId;
				//地区回显和数据显示
				$.ajax({
					url: "${pageContext.request.contextPath}/area/find_by_id.do",
					data: {
						"id": addressId
					},
					success: function(obj) {
						$.each(obj, function(i, result) {
							if(addressId == result.id) {
								parentId = result.parentId;
								$("#choose2").append("<option selected='true' value='" + result.id + "'>" + result.name + "</option>");
							} else {
								$("#choose2").append("<option value='" + result.id + "'>" + result.name + "</option>");
							}

						});
					}
				});

				$.ajax({
					url: "${pageContext.request.contextPath}/area/listByOne.do",
					success: function(obj) {
						$.each(obj, function(i, result) {
							if(parentId == result.id) {
								$("#choose1").append("<option selected='true' value='" + result.id + "'>" + result.name + "</option>");
							} else {
								$("#choose1").append("<option value='" + result.id + "'>" + result.name + "</option>");
							}
						});
					}

				});
			});

			function fun() {
				var parentId = $("#choose1").val();
				$.ajax({
					url: "${pageContext.request.contextPath}/area/find_by_parent_id.do",
					data: {
						"id": parentId
					},
					success: function(obj) {
						$("#choose2").empty();
						$("#choose2").append("<option value=''>-请选择-</option>");
						$.each(obj, function(i, result) {

							$("#choose2").append("<option value='" + result.id + "'>" + result.name + "</option>");
						});
					},
					error: function(obj) {

					}

				});
			}
		</script>
	</head>

	<body>
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:void(0);"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0);">支撑系统</a>
					</li>
					<li>
						<a href="javascript:void(0);">进口供应商登记</a>
					</li>
					<li class="active">
						<a href="javascript:void(0);">修改进口供应商</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div class="container container_box">
			<form id="form1" action="${pageContext.request.contextPath}/importSupplier/audit.html" method="post">
				<div>
					<h2 class="count_flow">审核进口供应商</h2>
					<ul class="ul_list">
						<li class="col-md-3 margin-0 padding-0 ">
							<span class="col-md-12 padding-left-5">企业名称：</span>
							<div class="input-append">
								<input id="status" name="status" type="hidden">
								<input value="${is.id }" name="id" type="hidden">
								<input class="span5" id="name" name="name" value="${is.name }" type="text">
								<span class="add-on">i</span>
							</div>
						</li>

						<li class="col-md-3 margin-0 padding-0 ">
							<span class="col-md-12 padding-left-5">企业类别：</span>
							<div class="input-append">
								<input class="span5" id="supplierType" name="supplierType" value="${is.supplierType }" type="text">
								<span class="add-on">i</span>
							</div>
						</li>

						<li class="col-md-3 margin-0 padding-0 ">
							<span class="col-md-12 padding-left-5">中文译名：</span>
							<div class="input-append">
								<input class="span5" id="chinesrName" name="chinesrName" value="${is.chinesrName }" type="text">
								<span class="add-on">i</span>
							</div>
						</li>

						<li class="col-md-3 margin-0 padding-0 ">
							<span class="col-md-12 padding-left-5">法定代表人：</span>
							<div class="input-append">
								<input class="span5" id="legalName" name="legalName" value="${is.legalName }" type="text">
								<span class="add-on">i</span>
							</div>
						</li>

						<li class="col-md-3 margin-0 padding-0 ">
							<span class="col-md-12 padding-left-5">企业地址：</span>
							<div class="select_common">
								<select id="choose1" class="w100" onchange="fun();">
									<option class="w100">-请选择-</option>
								</select>
								<select name="address" class="w100" id="choose2">
									<option class="w100">-请选择-</option>
								</select>
							</div>
						</li>

						<li class="col-md-3 margin-0 padding-0 ">
							<span class="col-md-12 padding-left-5">邮政编码：</span>
							<div class="input-append">
								<input class="span5" id="postCode" name="postCode" value="${is.postCode }" type="text">
								<span class="add-on">i</span>
							</div>
						</li>

						<li class="col-md-3 margin-0 padding-0 ">
							<span class="col-md-12 padding-left-5">经营产品大类：</span>
							<div class="input-append">
								<input class="span5" id="productType" name="productType" value="${is.productType }" type="text">
								<span class="add-on">i</span>
							</div>
						</li>

						<li class="col-md-3 margin-0 padding-0 ">
							<span class="col-md-12 padding-left-5">主营产品：</span>
							<div class="input-append">
								<input class="span5" id="majorProduct" name="majorProduct" value="${is.majorProduct }" type="text">
								<span class="add-on">i</span>
							</div>
						</li>

						<li class="col-md-3 margin-0 padding-0 ">
							<span class="col-md-12 padding-left-5">兼营产品：</span>
							<div class="input-append">
								<input class="span5" id="byproduct" name="byproduct" value="${is.byproduct }" type="text">
								<span class="add-on">i</span>
							</div>
						</li>

						<li class="col-md-3 margin-0 padding-0 ">
							<span class="col-md-12 padding-left-5">生产商名称：</span>
							<div class="input-append">
								<input class="span5" id="producerName" name="producerName" value="${is.producerName }" type="text">
								<span class="add-on">i</span>
							</div>
						</li>

						<li class="col-md-3 margin-0 padding-0 ">
							<span class="col-md-12 padding-left-5">联系人：</span>
							<div class="input-append">
								<input class="span5" id="contactPerson" name="contactPerson" value="${is.contactPerson }" type="text">
								<span class="add-on">i</span>
							</div>
						</li>

						<li class="col-md-3 margin-0 padding-0 ">
							<span class="col-md-12 padding-left-5">电话：</span>
							<div class="input-append">
								<input class="span5" id="telephone" name="telephone" value="${is.telephone }" type="text">
								<span class="add-on">i</span>
							</div>
						</li>

						<li class="col-md-3 margin-0 padding-0 ">
							<span class="col-md-12 padding-left-5">传真：</span>
							<div class="input-append">
								<input class="span5" id="fax" name="fax" value="${is.fax }" type="text">
								<span class="add-on">i</span>
							</div>
						</li>

						<li class="col-md-3 margin-0 padding-0 ">
							<span class="col-md-12 padding-left-5">电子邮件：</span>
							<div class="input-append">
								<input class="span5" id="email" name="email" value="${is.email }" type="text">
								<span class="add-on">i</span>
							</div>
						</li>

						<li class="col-md-3 margin-0 padding-0 ">
							<span class="col-md-12 padding-left-5">企业网址：</span>
							<div class="input-append">
								<input class="span5" id="website" name="website" value="${is.website }" type="text">
								<span class="add-on">i</span>
							</div>
						</li>

						<li class="col-md-11 margin-0 padding-0 ">
							<span class="col-md-12 padding-left-5">国内供货业绩</span>
							<div class="">
								<textarea class="col-md-12" id="civilAchievement" name="civilAchievement" class="h130" title="不超过800个字">${is.civilAchievement }</textarea>
							</div>
						</li>

						<li class="col-md-11 margin-0 padding-0 ">
							<span class="col-md-12 padding-left-5">企业简介：</span>
							<div class="">
								<textarea class="col-md-12" id="remark" name="remark" class="h130" title="不超过800个字">${is.remark }</textarea>
							</div>
						</li>
					</ul>
					<div class="tc mt20 clear col-md-11">
						<input class="btn padding-left-20 padding-right-20 btn_back" type="button" onclick="tijiao(1)" value="审核通过">
						<input class="btn padding-left-20 padding-right-20 btn_back" type="button" onclick="tijiao(2)" value="审核不通过">
						<input class="btn padding-left-20 padding-right-20 btn_back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
					</div>
				</div>
			</form>
		</div>
	</body>

</html>