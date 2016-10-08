<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>供应商黑名单添加</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script type="text/javascript">
	$(function() {
		$("#suppllier_name_input_id").click(function() {
			var id = $("input[name='id']").val();
			if (id) return;
			layer.open({
				type : 2,
				title : '选择供应商',
				skin : 'layui-layer-rim', //加上边框
				area : [ '1000px', '420px' ], //宽高
				offset : '100px',
				scrollbar : false,
				content : '${pageContext.request.contextPath}/supplier_blacklist/list_supplier.html', //url
				closeBtn : 1, //不显示关闭按钮
			});
		});
		
		autoSelected("term_select_id", "${supplierBlacklist.term}");
		autoSelected("punish_type_select_id", "${supplierBlacklist.punishType}");
		autoSelected("release_type_select_id", "${supplierBlacklist.releaseType}");
	});
	
	function autoSelected(id, v) {
		if (v) {
			$("#" + id).find("option").each(function() {
				var value = $(this).val();
				if(value == v) {
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
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li><a href="#"> 首页</a></li>
					<li><a href="#">业务管理</a></li>
					<li><a href="#">供应商黑名单</a></li>
					<li class="active"><a href="#">添加供应商</a></li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<form action="${pageContext.request.contextPath}/supplier_blacklist/save_or_update_supplier_black.html" method="post">
			<div class="container">
				<div>
					<div class="headline-v2">
						<h2>添加供应商</h2>
					</div>
					<ul class="list-unstyled list-flow p0_20">
						<li class="col-md-6 p0"><span class="">供应商名称：</span>
							<div class="input-append">
								<input name="id" type="hidden" value="${supplierBlacklist.id}"> <input name="supplierId" type="hidden" value="${supplier.id}"> <input class="span2" name="supplierName" readonly="readonly" id="suppllier_name_input_id" type="text" value="${supplier.supplierName}"> <span class="add-on">i</span>
							</div>
						</li>
						<li class="col-md-6 p0"><span class="">起始时间：</span>
							<div class="input-append">
								<fmt:formatDate value="${supplierBlacklist.startTime}" pattern="yyyy-MM-dd" var="startTime" />
								<input class="span2" name="startTime" readonly="readonly" onClick="WdatePicker()" type="text" value="${startTime}"> <span class="add-on">i</span>
							</div>
						</li>
						<li class="col-md-6 p0"><span class="">期限：</span>
							<div class="input-append">
								<select id="term_select_id" class="span2 fz15" name="term">
									<option selected="selected" value="3">3个月</option>
									<option value="6">6个月</option>
									<option value="12">1年</option>
									<option value="24">2年</option>
									<option value="36">3年</option>
									<option value="0">永久</option>
								</select> <span class="add-on">i</span>
							</div>
						</li>
						<li class="col-md-6  p0 "><span class="">处罚形式：</span>
							<div class="input-append">
								<select id="punish_type_select_id" class="span2 fz15" name="punishType">
									<option selected="selected" value="0">警告</option>
									<option value="1">不得参加采购活动</option>
								</select> <span class="add-on">i</span>
							</div>
						</li>
						<li class="col-md-6  p0 "><span class="">发布范围：</span>
							<div class="input-append">
								<select id="release_type_select_id" class="span2 fz15" name="releaseType">
									<option selected="selected" value="0">内外网发布</option>
									<option value="1">内网发布</option>
									<option value="2">外网发布</option>
								</select> <span class="add-on">i</span>
							</div>
						</li>
						<li class="col-md-12 p0"><span class="fl">理由：</span>
							<div class="col-md-12 pl200 fn mt5 pwr9">
								<textarea class="text_area col-md-12" name="reason" title="不超过800个字" placeholder="不超过800个字">${supplierBlacklist.reason}</textarea>
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
