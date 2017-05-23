<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
	<!--<![endif]-->

	<head>
	<%@ include file="../../../common.jsp"%>
		<script type="text/javascript">
			var parentId;
			var addressId = "${is.address}";

			$(document).ready(function() {
				for(var i = 0; i < document.getElementById("type").options.length; i++) {
					if(document.getElementById("type").options[i].value == '${ir.type}') {
						document.getElementById("type").options[i].selected = true;
						break;
					}
				}
			});

			$(function() {
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
					},
					error: function(obj) {}
				});
			});

			function fun() {
				var parentId = $("#choose1").val();
				$.ajax({
					url: "${pageContext.request.contextPath}/area/find_area_by_parent_id.do",
					data: {
						"id": parentId
					},
					success: function(obj) {
						var data = eval('(' + obj + ')');
						$("#choose2").empty();
						$("#choose2").append("<option value=''>-请选择-</option>");
						$.each(data, function(i, result) {
							$("#choose2").append("<option value='" + result.id + "'>" + result.name + "</option>");
						});
					},
					error: function(obj) {}
				});
			}
		</script>
	</head>

	<body>

		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:void(0);"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0);">售后服务采购管理</a>
					</li>
					<li class="active">
						<a href="javascript:void(0);">售后服务登记</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>

		<!-- 修改订列表开始-->
		<div class="container container_box ">
			<sf:form action="${pageContext.request.contextPath}/importRecommend/save.html" method="post">
				<div>
			        <h2 class="list_title">售后服务信息</h2> 
					
					<table class="table table-bordered">
					<tbody>
						<tr>
						<td class="bggrey ">供应商：</td>
							<td>${ir.name }</td>
						<td class="bggrey ">联系人：</td>
							<td>${ir.name }</td>
						<td class="bggrey ">联系方式：</td>
							<td>${ir.name }</td>
						</tr>
						<tr>
						<td class="bggrey ">全国售后服务地址：</td>
							<td>${ir.name }</td>
						</tr>
					</tbody>
				</table>
						<li id="bill_li_id" class="col-md-6 col-sm-12 col-xs-12 mb25">
									<span class="col-md-5 col-sm-12 col-xs-12 padding-left-5 w250" <c:if test="${fn:contains(audit,'billCert')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('billCert')"</c:if>><i class="red">*</i> 产品使用说明或用户操作手册</span>
									<div class="col-md-6 col-sm-12 col-xs-12 p0">
										<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="billcert_up" multiple="true" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBillCert}" auto="true" />
										<u:show showId="billcert_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBillCert}" />
										<div class="cue"> ${err_bil } </div>
									</div>
								</li>
					</ul>
				</div>
				<div class="col-md-12 tc mt20">
					
					<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
				</div>
			</sf:form>
		</div>
	</body>

</html>