<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="../../../common.jsp"%>
		<title></title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<script type="text/javascript">
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

			function tijiao() {
				if(!validateBusinessSupplierInfo()) {
					return;
				} else {
					form1.submit();
				}
			}

			$(function() {
				/** 校验用户名是否存在 */
				$("#loginName").blur(function() {
					var loginName = $(this).val();
					var id = $("#id").val();
					if(loginName) {
						$.ajax({
							url: "${pageContext.request.contextPath}/importSupplier/checkLoginName.do",
							type: "post",
							data: {
								loginName: loginName,
								id: id
							},
							success: function(result) {
								if(result == false) {
									layer.tips("用户名已存在，请重新填写.", "#loginName");
									$("#loginName").val("");
								}
							},
						});
					}
				});

				$("#name").blur(function() {
					var name = $(this).val();
					var id = $("#id").val();
					if(name) {
						$.ajax({
							url: "${pageContext.request.contextPath}/importSupplier/checkSupName.do",
							type: "post",
							data: {
								name: name,
								id: id
							},
							success: function(result) {
								if(result == false) {
									layer.tips("企业已存在，请重新填写.", "#name");
									$("#name").val("");
								}
							},
						});
					}
				});

				/** 校验手机号是否存在 */
				$("#mobile").blur(function() {
					var mobile = $(this).val();
					if(mobile) {
						$.ajax({
							url: "${pageContext.request.contextPath}/importSupplier/checkMobile.do",
							type: "post",
							data: {
								mobile: mobile
							},
							success: function(result) {
								if(result == false) {
									layer.tips("手机号已注册，请重新填写.", "#mobile", {
										tips: 1
									});
									$("#mobile").val();
								}
							},
						});
					}
				});

				$("#password").change(function() {
					var password = $("#password").val();
					if(!password) {
						layer.tips("请输入密码", "#password", {
							tips: 1
						});
						return false;
					} else if(!password.match(/^(?!(?:\d*$))[A-Za-z0-9_]{6,20}$/)) {
						layer.tips("密码由6-20位字母 数字组成 !", "#password", {
							tips: 1
						});
						return false;
					}
				});

				$("#confirmPassword").change(function() {
					var confirmPassword = $("#confirmPassword").val();
					var password = $("#password").val();
					if(!confirmPassword) {
						layer.tips("请输入确认密码 !", "#confirmPassword", {
							tips: 1
						});
						return false;
					} else if(confirmPassword != password) {
						layer.tips("密码不一致 !", "#confirmPassword", {
							tips: 1
						});
						return false;
					}
				});

			});
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
			<form id="form1" action="${pageContext.request.contextPath}/importSupplier/update.html" method="post">
				<div>
					<h2 class="count_flow">修改进口供应商</h2>
					<ul class="ul_list">
						<li class="col-md-3 col-sm-6 col-xs-12 pl15">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>企业名称</span>
							<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
								<input type="hidden" name="id" value="${is.id }" />
								<input class="input_group" id="name" name="name" value="${is.name }" type="text">
								<span class="add-on">i</span>
								<div class="cue">${ERR_name}</div>
							</div>
						</li>

						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>企业类别</span>
							<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
								<input class="input_group" id="supplierType" name="supplierType" value="${is.supplierType }" type="text">
								<span class="add-on">i</span>
								<div class="cue">${ERR_supplierType}</div>
							</div>
						</li>

						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>中文译名</span>
							<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
								<input class="input_group" id="chinesrName" name="chinesrName" value="${is.chinesrName }" type="text">
								<span class="add-on">i</span>
								<div class="cue">${ERR_chinesrName}</div>
							</div>
						</li>

						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>法定代表人</span>
							<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
								<input class="input_group" id="legalName" name="legalName" value="${is.legalName }" type="text">
								<span class="add-on">i</span>
								<div class="cue">${ERR_legalName}</div>
							</div>
						</li>

						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>企业地址</span>
							<div class="select_common">
								<select id="choose1" class="w100" onchange="fun();">
									<option class="w100">-请选择-</option>
								</select>
								<select name="address" class="w100 ml40" id="choose2">
									<option class="w100">-请选择-</option>
								</select>
								<div class="red">${ERR_address}</div>
							</div>
						</li>

						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>邮政编码</span>
							<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
								<input class="input_group" id="postCode" name="postCode" value="${is.postCode }" type="text">
								<span class="add-on">i</span>
								<div class="cue">${ERR_postCode}</div>
							</div>
						</li>

						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>经营产品大类</span>
							<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
								<input class="input_group" id="productType" name="productType" value="${is.productType }" type="text">
								<span class="add-on">i</span>
								<div class="cue">${ERR_productType}</div>
							</div>
						</li>

						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>主营产品</span>
							<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
								<input class="input_group" id="majorProduct" name="majorProduct" value="${is.majorProduct }" type="text">
								<span class="add-on">i</span>
								<div class="cue">${ERR_majorProduct}</div>
							</div>
						</li>

						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>兼营产品</span>
							<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
								<input class="input_group" id="byproduct" name="byproduct" value="${is.byproduct }" type="text">
								<span class="add-on">i</span>
								<div class="cue">${ERR_byproduct}</div>
							</div>
						</li>

						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>生产商名称</span>
							<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
								<input class="input_group" id="producerName" name="producerName" value="${is.producerName }" type="text">
								<span class="add-on">i</span>
								<div class="cue">${ERR_producerName}</div>
							</div>
						</li>

						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>联系人</span>
							<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
								<input class="input_group" id="contactPerson" name="contactPerson" value="${is.contactPerson }" type="text">
								<span class="add-on">i</span>
								<div class="cue">${ERR_contactPerson}</div>
							</div>
						</li>

						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>电话</span>
							<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
								<input class="input_group" id="telephone" name="telephone" value="${is.telephone }" type="text">
								<span class="add-on">i</span>
								<div class="cue">${ERR_telephone}</div>
							</div>
						</li>

						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>传真</span>
							<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
								<input class="input_group" id="fax" name="fax" value="${is.fax }" type="text">
								<span class="add-on">i</span>
								<div class="cue">${ERR_fax}</div>
							</div>
						</li>

						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>电子邮件</span>
							<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
								<input class="input_group" id="email" name="email" value="${is.email }" type="text">
								<span class="add-on">i</span>
								<div class="cue">${ERR_email}</div>
							</div>
						</li>

						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>企业网址</span>
							<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
								<input class="input_group" id="website" name="website" value="${is.website }" type="text">
								<span class="add-on">i</span>
								<div class="cue">${ERR_website}</div>
							</div>
						</li>

						<li class="col-md-12 col-sm-12 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">国内供货业绩</span>
							<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
								<textarea class="col-md-12 col-sm-12 col-xs-12" style="height:130px" id="civilAchievement" name="civilAchievement" title="不超过800个字">${is.civilAchievement }</textarea>
							</div>
						</li>

						<li class="col-md-12 col-sm-12 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">企业简介</span>
							<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
								<textarea class="col-md-12 col-sm-12 col-xs-12" style="height:130px" id="remark" name="remark" title="不超过800个字">${is.remark }</textarea>
							</div>
						</li>
					</ul>
					<div class="tc mt20 clear col-md-11">
						<button class="btn btn-windows git" onclick="tijiao()">更新</button>
						<button class="btn btn-windows reset" onclick="history.go(-1)" type="button">返回</button>
					</div>
				</div>
			</form>
		</div>
	</body>

</html>