<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<title>查看已考人员</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript">
			$(function() {
				laypage({
					cont: $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
					pages: "${paperUserList.pages}", //总页数
					skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
					skip: true, //是否开启跳页
					total: "${paperUserList.total}",
					startRow: "${paperUserList.startRow}",
					endRow: "${paperUserList.endRow}",
					groups: "${paperUserList.pages}" >= 5 ? 5 : "${paperUserList.pages}", //连续显示分页数
					curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
						var page = location.search.match(/page=(\d+)/);
						return page ? page[1] : 1;
					}(),
					jump: function(e, first) { //触发分页后的回调
						if(!first) { //一定要加此判断，否则初始时会无限刷新
							var id = "${id}";
							location.href = "${pageContext.request.contextPath }/purchaserExam/viewReference.do?id=" + id + "&page=" + e.curr;
						}
					}
				});
			})

			//打印预览
			function printReView() {
				var paperId = $("#paperId").val();
				window.location.href = "${pageContext.request.contextPath }/purchaserExam/printReView.do?paperId=" + paperId;
			}
		</script>

	</head>

	<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0);">支撑环境</a>
					</li>
					<li>
						<a href="javascript:void(0);">人员管理</a>
					</li>
					<li>
						<a href="javascript:void(0);" onclick="jumppage('${pageContext.request.contextPath}/purchaserExam/paperManage.html')">考卷管理</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>

		<div class="container">
			<div class="headline-v2">
				<h2>已考人员列表</h2>
			</div>

			<div class="col-md-12 pl20 mt10">
				<input type="button" class="btn" value="打印预览" onclick="printReView()" />
				<input class="btn btn-windows back" value="返回" type="button" onclick="location.href='${pageContext.request.contextPath }/purchaserExam/paperManage.html'">
			</div>

			<!-- 表格开始 -->
			<div class="content table_box">
				<table class="table table-bordered table-condensed table-hover">
					<thead>
						<tr>
							<th class="w50">序号</th>
							<th class="w100">姓名</th>
							<th>身份证号</th>
							<th>试卷编号</th>
							<th>所属单位</th>
							<th>得分</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${paperUserList.list }" varStatus="vs" var="paper">
							<tr>
								<td class="tc">${(vs.index+1)+(paperUserList.pageNum-1)*(paperUserList.pageSize)}</td>
								<td class="tc">${paper.relName }</td>
								<td class="tc">${paper.card }</td>
								<td class="tc">${paper.code }</td>
								<td class="tl pl20">${paper.unitName }</td>
								<td class="tc">${paper.score }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="pageDiv" align="right"></div>
		</div>

		

		<input type="hidden" value="${examPaper.id }" id="paperId" />
	</body>

</html>