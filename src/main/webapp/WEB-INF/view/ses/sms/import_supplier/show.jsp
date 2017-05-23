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
			//鼠标移动显示全部内容
			function out(content) {
				if(content.length >= 10) {
					layer.msg(content, {
						offset: '200px',
						skin: 'demo-class',
						shade: false,
						area: ['600px'],
						time: 0 //默认消息框不关闭
					}); //去掉msg图标
				} else {
					layer.closeAll(); //关闭消息框
				}
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
						<a href="javascript:void(0);">查看进口供应商</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div class="container content height-350">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<div class="tab-content padding-top-20">
							<div class="tab-pane fade active in height-450">
								<table class="table table-bordered">
									<tbody>
										<tr>
											<td class="bggrey tr">企业名称：</td>
											<td>${is.name}</td>
											<td class="bggrey tr"> 企业类别：</td>
											<td>${is.supplierType }</td>
											<td class="bggrey tr">中文译名：</td>
											<td>${is.chinesrName }</td>
										</tr>
										<tr>
											<td class="bggrey tr">法定代表人：</td>
											<td>${is.legalName}</td>
											<td class="bggrey tr">企业注册地址：</td>
											<td>
												<select id="choose1" disabled class="w100" onchange="fun();">
													<option class="w100">-请选择-</option>
												</select>
												<select name="address" disabled class="w100" id="choose2">
													<option class="w100">-请选择-</option>
												</select>
											</td>
											<td class="bggrey tr">邮政编码：</td>
											<td colspan="3">${is.postCode }</td>
										</tr>
										<tr>
											<td class="bggrey tr">经营产品大类：</td>
											<td>${is.productType}</td>
											<td class="bggrey tr"> 主营产品：</td>
											<td>${is.majorProduct }</td>
											<td class="bggrey tr">兼营产品：</td>
											<td>${is.byproduct }</td>
										</tr>
										<tr>
											<td class="bggrey tr">生产商名称：</td>
											<td>${is.producerName}</td>
											<td class="bggrey tr"> 联系人：</td>
											<td>${is.contactPerson }</td>
											<td class="bggrey tr">电话：</td>
											<td>${is.telephone }</td>
										</tr>
										<tr>
											<td class="bggrey tr">传真：</td>
											<td>${is.fax}</td>
											<td class="bggrey tr"> 电子邮件：</td>
											<td>${is.email }</td>
											<td class="bggrey tr">企业网址：</td>
											<td>${is.website }</td>
										</tr>
										<tr>
											<td class="bggrey tr">国内供货业绩：</td>
											<td colspan="5">${is.civilAchievement}</td>
										</tr>
										<tr>
											<td class="bggrey tr"> 企业简介：</td>
											<td colspan="5">${is.remark}</td>
										</tr>
									</tbody>
								</table>
								<div class="tc mt20 clear col-md-11">
									<button class="btn btn-windows reset" onclick="history.go(-1)" type="button">返回</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>

</html>