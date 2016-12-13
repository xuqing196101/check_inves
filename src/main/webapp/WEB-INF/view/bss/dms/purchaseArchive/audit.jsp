<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

	<head>
		<title>采购档案审核</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript">
			//通过
			function pass() {
				var id = $("#archiveId").val();
				window.location.href = "${pageContext.request.contextPath }/purchaseArchive/passArchive.html?id=" + id;
			}

			//退回
			function retreat() {
				layer.open({
					type: 1,
					title: '信息',
					skin: 'layui-layer-rim',
					offset: ['30%', '40%'],
					shadeClose: true,
					area: ['580px', '310px'],
					content: $("#backReason")
				});
				$(".layui-layer-shade").remove();
			}

			//确定
			function sure() {
				var id = $("#archiveId").val();
				var reason = $("#reason").val();
				window.location.href = "${pageContext.request.contextPath }/purchaseArchive/backReason.html?id=" + id + "&reason=" + reason;
			}

			//取消
			function cancel() {
				layer.closeAll();
			}
		</script>

	</head>

	<body>
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="#">首页</a>
					</li>
					<li>
						<a href="#">保障作业</a>
					</li>
					<li>
						<a href="#">采购档案管理</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>

		<div class="container">
			<div class="headline-v2">
				<h2>采购档案审核</h2>
			</div>
		</div>

		<div class="container mt20">
			<input type="hidden" value="${archive.id }" id="archiveId" />

			<ul class="list-unstyled list-flow p0_20">
				<li class="col-md-12 p0">
					<span class="fl">档案名称：</span>
					<input type="text" value="${archive.name }" readonly="readonly" />
				</li>

				<li class="col-md-12 p0">
					<span class="fl">档案编号：</span>
					<input type="text" value="${archive.code }" readonly="readonly" />
				</li>

				<li class="col-md-12 p0">
					<span class="fl">合同编号：</span>
					<input type="text" value="${archive.contractCode }" readonly="readonly" />
				</li>

				<li class="col-md-12 p0">
					<span class="fl">项目编号：</span>
					<input type="text" value="${archive.projectCode }" readonly="readonly" />
				</li>

				<%--<li class="col-md-12 p0">
	  			<span class="fl">计划文号：</span>
	  			<input type="text" value="${archive.planCode }" readonly="readonly"/>
	    	</li>
	    	
	    	<li class="col-md-12 p0">
	  			<span class="fl">计划下达时间：</span>
	  			<input type="text" value="${archive.planCode }" readonly="readonly"/>
	    	</li>
	    	
	    	--%>
				<li class="col-md-12 p0">
					<span class="fl">预算年度：</span>
					<input type="text" value="${archive.year }" readonly="readonly" />
				</li>

				<li class="col-md-12 p0">
					<span class="fl">采购机构：</span>
					<input type="text" value="${archive.purchaseDep }" readonly="readonly" />
				</li>
				<li class="col-md-12 p0">
					<span class="fl">采购方式：</span>
					<input type="text" value="${archive.purchaseType }" readonly="readonly" />
				</li>

				<li class="col-md-12 p0">
					<span class="fl">产品名称：</span>
					<input type="text" value="${archive.productName }" readonly="readonly" />
				</li>

				<li class="col-md-12 p0">
					<span class="fl">供应商名称：</span>
					<input type="text" value="${archive.supplierName }" readonly="readonly" />
				</li>
			</ul>

			<!-- 按钮 -->
			<div class="padding-top-10 clear">
				<div class="col-md-12 pl200 ">
					<div class="mt40 tc mb50">
						<button class="btn" type="button" onclick="pass()">通过</button>
						<button class="btn" type="button" onclick="retreat()">退回</button>
						<input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
					</div>
				</div>
			</div>
		</div>

		<div class="layui-layer-wrap col-md-12 dnone" id="backReason">
			<span class="col-md-12">退回理由：</span>
			<div class="col-md-12">
				<textarea class="col-md-10 h80 p0" name="reason" id="reason"></textarea>
			</div>
			<div class="col-md-12 mt10 tc">
				<button class="btn btn-windows save" type="button" onclick="sure()">保存</button>
				<button class="btn btn-windows cancel" type="button" onclick="cancel()">取消</button>
			</div>
		</div>
	</body>

</html>