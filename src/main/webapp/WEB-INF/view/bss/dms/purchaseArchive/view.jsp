<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTMLL>
<html>

	<head>
		<title>查看页面</title>
        <%@ include file="/WEB-INF/view/common.jsp"%>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript">
			$(function() {
				laypage({
					cont: $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
					pages: "${archiveList.pages}", //总页数
					skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
					skip: true, //是否开启跳页
					total: "${archiveList.total}",
					startRow: "${archiveList.startRow}",
					endRow: "${archiveList.endRow}",
					groups: "${archiveList.pages}" >= 5 ? 5 : "${archiveList.pages}", //连续显示分页数
					curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
						var page = location.search.match(/page=(\d+)/);
						return page ? page[1] : 1;
					}(),
					jump: function(e, first) { //触发分页后的回调
						if(!first) { //一定要加此判断，否则初始时会无限刷新
							location.href = "${pageContext.request.contextPath }/purchaseArchive/viewArchive.do?page=" + e.curr;
						}
					}
				});
			})

			//查看
			function view() {
				var userId = $("#userId").val();
				var count = 0;
				var info = document.getElementsByName("info");
				var id = "";
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
							id = info[i].value;
						}
					}
					$.ajax({
						type: "POST",
						dataType: "json",
						url: "${pageContext.request.contextPath }/purchaseArchive/viewAppointed.do?id=" + id + "&userId=" + userId,
						success: function(data) {
							var html = "";
							for(var i = 0; i < data.length; i++) {
								html = html + "<tr class='tc'>";
								html = html + "<td>" + (i + 1) + "</td>";
								html = html + "<td>" + data[i].name + "</td>";
								html = html + "</tr>";
							}
							$("#newsResult").html(html);
							layer.open({
								type: 1,
								title: '信息',
								skin: 'layui-layer-rim',
								shadeClose: true,
								area: ['380px', '310px'],
								content: $("#news")
							});
							$(".layui-layer-shade").remove();
						}
					});
				}
			}
		</script>

	</head>

	<body>
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:void(0);">首页</a>
					</li>
					<li>
						<a href="javascript:void(0);">保障作业</a>
					</li>
					<li>
						<a href="javascript:void(0);">采购档案授权</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div class="container">
			<div class="headline-v2">
				<h2>档案查看</h2>
			</div>

			<!-- 按钮开始-->
			<c:if test="${resultMap==null }">
				<div class="col-md-12 pl20 mt10">
					<button class="btn" type="button" onclick="view()">查看</button>
				</div>
			</c:if>

			<c:choose>
				<c:when test="${resultMap!=null }">
					<div class="container mt10">
						<div class="col-md-12 f22 tc">
							${resultMap }
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div class="content table_box">
						<table class="table table-bordered table-condensed table-hover">
							<thead>
								<tr class="info">
									<th class="w50">选择</th>
									<th class="w50">序号</th>
									<th>档案名称</th>
									<th>档案编号</th>
									<th>合同编号</th>
									<th>预算年度</th>
									<th>采购机构</th>
									<th>采购方式</th>
									<th>产品名称</th>
									<th>供应商名称</th>
									<th>状态</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${archiveList.list }" var="archive" varStatus="vs">
									<tr class="tc">
										<td><input type="checkbox" value="${archive.id }" name="info" onclick="check()" /></td>
										<td>${(vs.index+1)+(archiveList.pageNum-1)*(archiveList.pageSize)}</td>
										<td class="tl pl20">${archive.name }</td>
										<td class="tl pl20">${archive.code }</td>
										<td class="tl pl20">${archive.contractCode }</td>
										<td class="tl pl20">${archive.year }</td>
										<td class="tl pl20">${archive.purchaseDep }</td>
										<td class="tl pl20">${archive.purchaseType }</td>
										<td class="tl pl20">${archive.productName }</td>
										<td class="tl pl20">${archive.supplierName }</td>
										<c:if test="${archive.status==1 }">
											<td>暂存</td>
										</c:if>
										<c:if test="${archive.status==2 }">
											<td>审核通过</td>
										</c:if>
										<c:if test="${archive.status==3 }">
											<td>审核不通过</td>
										</c:if>
										<c:if test="${archive.status==4 }">
											<td>已归档</td>
										</c:if>
										<c:if test="${archive.status==5 }">
											<td>已提交</td>
										</c:if>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div id="pageDiv" align="right"></div>
				</c:otherwise>
			</c:choose>
		</div>

		<div class="dnone layui-layer-wrap col-md-12" id="news">
			<table class="table table-bordered table-condensed table-hover">
				<thead>
					<tr class="info">
						<th class="w50">序号</th>
						<th>文件名称</th>
					</tr>
				</thead>
				<tbody id="newsResult">
				</tbody>
			</table>
		</div>

		<input type="hidden" value="${userId }" id="userId" />
	</body>

</html>