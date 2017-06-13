<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
        <%@ include file="/WEB-INF/view/common.jsp"%>
		<title>新建采购档案列表</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript">
			$(function() {
				laypage({
					cont: $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
					pages: "${contract.pages}", //总页数
					skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
					skip: true, //是否开启跳页
					total: "${contract.total}",
					startRow: "${contract.startRow}",
					endRow: "${contract.endRow}",
					groups: "${contract.pages}" >= 5 ? 5 : "${contract.pages}", //连续显示分页数
					curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
						var page = location.search.match(/page=(\d+)/);
						return page ? page[1] : 1;
					}(),
					jump: function(e, first) { //触发分页后的回调
						if(!first) { //一定要加此判断，否则初始时会无限刷新
							location.href = "${pageContext.request.contextPath }/purchaseArchive/add.do?page=" + e.curr;
						}
					}
				});
			})

			//生成采购档案
			function add() {
				var count = 0;
				var info = document.getElementsByName("info");
				var str = "";
				for(var i = 0; i < info.length; i++) {
					if(info[i].checked == true) {
						count++;
					}
				}
				if(count > 1) {
					layer.alert("只能选择一项", {
						offset: ['30%', '40%']
					});
					$(".layui-layer-shade").remove();
					return;
				} else if(count == 0) {
					layer.alert("请先选择一项", {
						offset: ['30%', '40%']
					});
					$(".layui-layer-shade").remove();
					return;
				} else {
					for(var i = 0; i < info.length; i++) {
						if(info[i].checked == true) {
							str = info[i].value;
						}
					}
					$.ajax({
						type: "POST",
						dataType: "json",
						url: "${pageContext.request.contextPath }/purchaseArchive/judgeArchive.do?id=" + str,
						success: function(data) {
							if(data == 0) {
								layer.open({
									type: 1,
									title: '信息',
									skin: 'layui-layer-rim',
									offset: ['20%', '30%'],
									shadeClose: true,
									area: ['40%', '310px'],
									content: $('#news')
								});
								$(".layui-layer-shade").remove();
							} else if(data == 1) {
								layer.alert("该合同已生成采购档案,请重新选择", {
									offset: ['30%', '40%']
								});
								$(".layui-layer-shade").remove();
							}
						}
					});
				}
			}

			//保存
			function save() {
				var name = $("#name").val();
				var archiveCode = $("#archiveCode").val();
				var info = document.getElementsByName("info");
				var str = "";
				for(var i = 0; i < info.length; i++) {
					if(info[i].checked == true) {
						str = info[i].value;
					}
				}
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "${pageContext.request.contextPath }/purchaseArchive/addArchive.do?id=" + str + "&name=" + name + "&code=" + archiveCode,
					success: function(data) {
						if(data == 1) {
							layer.msg('添加成功', {
								offset: ['40%', '45%']
							});
							window.setTimeout(function() {
								window.location.href = "${pageContext.request.contextPath }/purchaseArchive/archiveList.html";
							}, 1000);
						} else {
							var error = eval(data);
							if(error.name) {
								$("#errorName").html(error.name);
							} else {
								$("#errorName").html("");
							}
							if(error.code) {
								$("#errorCode").html(error.code);
							} else {
								$("#errorCode").html("");
							}
						}
					}
				});
			}

			//取消
			function cancel() {
				layer.closeAll();
			}

			//返回
			function back() {
				window.location.href = "${pageContext.request.contextPath }/purchaseArchive/backArchive.html";
			}
		</script>

	</head>

	<body>
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0);">保障作业</a>
					</li>
					<li>
						<a href="javascript:void(0);">采购档案管理</a>
					</li>
					<li>
						<a href="javascript:jumppage('${pageContext.request.contextPath}/purchaseArchive/archiveList.html');">采购档案列表</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>

		<div class="container">
			<div class="col-md-12 pl20 mt10">
				<button class="btn" type="button" onclick="add()">生成采购档案</button>
			</div>

			<div class="content table_box">
				<table class="table table-bordered table-condensed table-hover">
					<thead>
						<tr class="info">
							<th class="w50">选择</th>
							<th class="w50">序号</th>
							<th>合同编号</th>
							<th>项目编号</th>
							<th>预算年度</th>
							<th>采购机构</th>
							<th>采购方式</th>
							<th>产品名称</th>
							<th>供应商名称</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${contract.list }" var="c" varStatus="vs">
							<tr class="tc">
								<td class="w50"><input type="checkbox" name="info" value="${c.id }" /></td>
								<td class="w50">${(vs.index+1)+(contract.pageNum-1)*(contract.pageSize)}</td>
								<td class="tl pl20">${c.contractCode }</td>
								<td class="tl pl20">${c.projectCode }</td>
								<td class="tl pl20">${c.year }</td>
								<td class="tl pl20">${c.purchaseDep }</td>
								<td>
									<c:forEach items="${kind}" var="kind">
										<c:if test="${kind.id == c.purchaseType}">${kind.name}</c:if>
									</c:forEach>
								</td>
								<td class="tl pl20">${c.productName }</td>
								<td class="tl pl20">${c.supplierName }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="pageDiv" align="right"></div>
		</div>

		<!-- 按钮 -->
		<div class="col-md-12 mt10 tc">
			<input class="btn btn-windows back" value="返回" type="button" onclick="back()">
		</div>

		<ul class="list-unstyled dnone mt10" id="news">
			<li class="col-md-6 col-sm-6 col-xs-12 pl15">
				<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><div class="red star_red">*</div>档案名称：</span>
				<div class="col-md-12 col-xs-12 col-sm-12 padding-left-5 p0 input-append input_group">
				    <input type="text" id="name" name="name"/>
				    <div class="cue" id="errorName"></div>
				</div>
			</li>

			<li class="col-md-6 col-sm-6 col-xs-12">
				<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><div class="red star_red">*</div>档案编号：</span>
				<div class="col-md-12 col-xs-12 col-sm-12 padding-left-5 p0 input-append input_group">
				    <input type="text" id="archiveCode" name="archiveCode"/>
				    <div class="clear red" id="errorCode"></div>
				</div>
			</li>

			<div class="col-md-12 col-sm-12 col-xs-12 mt20 tc">
				<input type="button" value="保存" class="btn btn-windows save" onclick="save()" />
				<input type="button" value="取消" class="btn btn-windows cancel" onclick="cancel()" />
			</div>
		</ul>
	</body>

</html>