<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<link
	href="${pageContext.request.contextPath }/public/select2/css/select2.css"
	rel="stylesheet" />
<title>发布定型产品页面</title>
</head>
<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="javascript:void(0)"> 首页</a></li>
				<li><a href="javascript:void(0)">保障作业</a></li>
				<li><a href="javascript:void(0)">定型产品竞价</a></li>
				<li class="active"><a href="javascript:void(0)">定型产品管理</a></li>
				<li class="active"><a href="javascript:void(0)">定型产品详情</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<!-- 发布定型产品页面开始 -->
	<div class="wrapper mt10">
		<div class="container">
			<div class="headline-v2">
				<h2>定型产品详情</h2>
			</div>
			<div class="content table_box">
				<table class="table table-bordered">
					<tbody>
						<tr>
							<td class="info" width="18%"><div class="star_red">*</div>产品代码</td>
							<td width="32%"><input id="code" name=""
								value="${obProduct.code }" type="text" class="w230 mb0" disabled="disabled">
							<td class="info" width="18%"><div class="star_red">*</div>产品名称</td>
							<td width="32%"><input id="name" name=""
								value="${obProduct.name }" type="text" class="w230 mb0" disabled="disabled">
						</tr>
						<tr>
							<td class="info" width="18%"><div class="star_red">*</div>产品目录</td>
							<td width="32%"><input id="name" name="" title="${obProduct.pointsName }"
								value="${categoryName }" type="text" class="w230 mb0" disabled="disabled"></td>
								<td class="info" width="18%"><div class="star_red">*</div>采购机构</td>
							<td width="32%"><input id="code" name=""
								value="${orgName }" type="text" class="w230 mb0" disabled="disabled"></td>
						</tr>
						<tr>
							<td class="info">规格型号</td>
							<td colspan="3">
								<div class="col-md-12 col-sm-12 col-xs-12 p0">
									<textarea id="standardModel" name="" class="w100p"
										style="height: 130px" disabled="disabled">${obProduct.standardModel }</textarea>
								</div>
							</td>
						</tr>
						<tr>
							<td class="info">质量技术标准</td>
							<td colspan="3">
								<div class="col-md-12 col-sm-12 col-xs-12 p0">
									<textarea id="qualityTechnicalStandard" name="" class="w100p"
										style="height: 130px" disabled="disabled">${obProduct.qualityTechnicalStandard }</textarea>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-md-12 clear tc mt10">
				<button class="btn btn-windows back" type="button"
					onclick="javascript:history.go(-1);">返回</button>
			</div>
		</div>
	</div>
</body>
</html>