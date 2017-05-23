<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<script type="text/javascript">
			$(function() {
				laypage({
					cont: $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
					pages: "${list.pages}", //总页数
					skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
					skip: true, //是否开启跳页
					total: "${list.total}",
					startRow: "${list.startRow}",
					endRow: "${list.endRow}",
					groups: "${list.pages}" >= 5 ? 5 : "${list.pages}", //连续显示分页数
					curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
						var page = location.search.match(/page=(\d+)/);
						return page ? page[1] : 1;
					}(),
					jump: function(e, first) { //触发分页后的回调
						if(!first) { //一定要加此判断，否则初始时会无限刷新
							location.href = "${pageContext.request.contextPath }/expertExam/personalResult.do?page=" + e.curr;
						}
					}
				});
			})
		</script>

	</head>

	<body>
  <!--面包屑导航开始-->
  <div class="margin-top-10 breadcrumbs ">
    <div class="container">
	  <ul class="breadcrumb margin-left-0">
	    <li><a href="javascript:void(0)">首页</a></li>
		<li><a href="javascript:void(0)">考试系统</a></li>
		<li><a href="javascript:void(0)">成绩查询</a></li>
	  </ul>
	  <div class="clear"></div>
    </div>
  </div>
		<div class="container">
			<div class="headline-v2">
				<h2>成绩信息</h2>
			</div>

			<div class="content table_box">
				<table class="table table-bordered table-condensed table-hover">
					<thead>
						<tr class="info">
							<th class="w50 tc">序号</th>
							<th class="tc" width="25%">专家姓名</th>
							<th class="tc" width="20%">得分</th>
							<th class="tc" width="25%">考试状态</th>
							<th class="tc">考试时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list.list}" var="l" varStatus="vs">
							<tr>
								<td class="tc">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
								<td class="tl">${l.relName }</td>
								<td class="tc">${l.score }</td>
								<td class="tc">${l.status }</td>
								<td class="tc">${l.formatDate}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="pageDiv" align="right"></div>
		</div>
	</body>

</html>