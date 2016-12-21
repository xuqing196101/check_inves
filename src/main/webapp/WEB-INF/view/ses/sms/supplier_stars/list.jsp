<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML">
<html>
<head>
<%@ include file="../../../common.jsp"%>
<script type="text/javascript">
	function editSupplierStars() {
		var checkbox = $("input[name='checkbox']:checked");
		var size = $(checkbox).size();
		if (size != 1) {
			layer.msg("请勾选一条记录 !", {
				offset : '300px',
			});
			return;
		}
		var id = checkbox.val();
		$("input[name='id']").val(id);
		$("#edit_form_id").submit();
	}
	
	function changeStatus() {
		var checkbox = $("input[name='checkbox']:checked");
		var size = $(checkbox).size();
		if (size != 1) {
			layer.msg("请勾选一条记录 !", {
				offset : '300px',
			});
			return;
		}
		
		var id = checkbox.val();
		var currText = checkbox.parents("tr").find("td").eq(7).find("span").text();
		currText = $.trim(currText);
		if (currText == "已停用") {
			var status = "${status}";
			console.info(status);
			if (status == "fail") {
				layer.msg("只能启用一条记录 !", {
					offset : '300px',
				});
				return;
			} else {
				window.location.href = "${pageContext.request.contextPath}/supplier_stars/update_status.html?id=" + id + "&status=1";
			}
		} else if (currText == "已启用") {
			window.location.href = "${pageContext.request.contextPath}/supplier_stars/update_status.html?id=" + id + "&status=0";
		}
	}
	
	function deleteStars() {
		var checkbox = $("input[name='checkbox']:checked");
		if (!checkbox.size()) {
			layer.msg("请至少勾选一条记录 !", {
				offset : '200px'
			});
			return;
		}
		var ids = "";
		var count = 0;
		checkbox.each(function(index) {
			var value = $(this).val();
			if (index > 0) {
				ids += ",";
			}
			ids += value;
			count ++;
		});
		layer.confirm('已勾选' + count + '条, 确认删除 ？', {
			offset : '200px',
		},function(index) {
			window.location.href = "${pageContext.request.contextPath}/supplier_stars/delete.html?ids=" + ids;
			layer.close(index);
		});
	}
	
	function checkAll(ele) {
		var flag = $(ele).prop("checked");
		$("input[name='checkbox']").each(function() {
			$(this).prop("checked", flag);
		});
	}
	
</script>

</head>

<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li><a href="#"> 首页</a></li>
					<li><a href="#">业务管理</a></li>
					<li><a href="#">供应商星级规则</a></li>
					<li class="active"><a href="#">供应商星级规则列表</a></li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<!-- 我的订单页面开始-->
		<div class="container">
			<div class="headline-v2">
				<h2>供应商星级规则列表</h2>
			</div>
		<!-- 表格开始-->
			<div class="col-md-12 pl20 mt10">
				<button class="btn btn-windows add" type="button" onclick="location='${pageContext.request.contextPath}/supplier_stars/add.html'">新增</button>
				<button class="btn btn-windows edit" type="button" onclick="editSupplierStars()">修改</button>
				<button class="btn btn-windows edit" type="button" onclick="changeStatus()">启/停用</button>
				<button class="btn btn-windows delete" type="button" onclick="deleteStars()">删除</button>
			</div>

		<div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
					<thead>
						<tr>
							<th class="info w50"><input type="checkbox" onchange="checkAll(this)" /></th>
							<th class="info w50">序号</th>
							<th class="info">一星级所需分数</th>
							<th class="info">二星级所需分数</th>
							<th class="info">三星级所需分数</th>
							<th class="info">四星级所需分数</th>
							<th class="info">五星级所需分数</th>
							<th class="info">状态</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list}" var="stars" varStatus="vs">
							<tr>
								<td class="tc"><input name="checkbox" type="checkbox" value="${stars.id}"></td>
								<td class="tc">${vs.index + 1}</td>
								<td class="tc">${stars.oneStars}</td>
								<td class="tc">${stars.twoStars}</td>
								<td class="tc">${stars.threeStars}</td>
								<td class="tc">${stars.fourStars}</td>
								<td class="tc">${stars.fiveStars}</td>
								<td class="tc status">
									<c:if test="${stars.status == 0}"><span class="label rounded-2x label-dark">已停用</span></c:if>
									<c:if test="${stars.status == 1}"><span class="label rounded-2x label-u">已启用</span></c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="pagediv" align="right"></div>
		</div>
	
	<form id="edit_form_id" action="${pageContext.request.contextPath}/supplier_stars/add.html" method="post">
		<input name="id" type="hidden" />
	</form>
	
</body>
</html>
