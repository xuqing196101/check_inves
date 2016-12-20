<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<script type="text/javascript">
			$(function() {
				$("#code").val("${code}");
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
							var code = "${code}";
							location.href = "${pageContext.request.contextPath }/purchaserExam/personalResult.do?code=" + code + "&page=" + e.curr;
						}
					}
				});
			})

			//查询
			function query() {
				var code = $("#code").val();
				if(code == "" || code == null) {
					window.location.href = "${pageContext.request.contextPath }/purchaserExam/personalResult.do";
				} else {
					window.location.href = "${pageContext.request.contextPath }/purchaserExam/personalResult.do?code=" + code;
				}

			}

			//重置
			function reset() {
				$("#code").val("");
			}
		</script>

	</head>

	<body>
		<div class="container">
			<div class="headline-v2">
				<h2>成绩信息</h2>
			</div>

			<h2 class="search_detail">
			<ul class="demand_list">
				<li>
			    	<label class="fl">考试编号：</label><span><input type="text" id="code"/></span>
			    </li>
		    	<button type="button" onclick="query()" class="btn">查询</button>
		    	<button type="button" onclick="reset()" class="btn">重置</button>
    		</ul>
    		<div class="clear"></div>
    	</h2>

			<div class="content table_box">
				<table class="table table-bordered table-condensed table-hover table-striped">
					<thead>
						<tr class="info">
							<th class="w50">序号</th>
							<th class="w160">采购人员姓名</th>
							<th>考试编号</th>
							<th class="w100">得分</th>
							<th>考试状态</th>
							<th>考试时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list.list}" var="l" varStatus="vs">
							<tr class="tc">
								<td>${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
								<td>${l.relName }</td>
								<td>${l.code }</td>
								<td>${l.score }</td>
								<td>${l.status }</td>
								<td>${l.formatDate}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="pageDiv" align="right"></div>
		</div>
	</body>

</html>