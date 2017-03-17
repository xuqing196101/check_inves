<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<title>投诉页面</title>
<script type="text/javascript">
$(function() {
	laypage({
		cont: $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
		pages: "${info.pages}", //总页数
		skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		skip: true, //是否开启跳页
		total: "${info.total}",
		startRow: "${info.startRow}",
		endRow: "${info.endRow}",
		groups: "${info.pages}" >= 5 ? 5 : "${info.pages}", //连续显示分页数
		curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
			var page = location.search.match(/page=(\d+)/);
			return page ? page[1] : 1;
		}(),
		jump: function(e, first) { //触发分页后的回调
			if(!first) { //一定要加此判断，否则初始时会无限刷新
				location.href = "${pageContext.request.contextPath }/onlineComplaints/dealWith.do?page=" + e.curr;
			}
		}
	});
})
    
			function dealWith(id) {
				   
					window.location.href = "${pageContext.request.contextPath}/onlineComplaints/dealWith.do?id=" + id;
				    
			}
	

	
</script>
</head>
<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="javascript:void(0)"> p2首页</a></li>
				<li><a href="javascript:void(0)">业务监管</a></li>
				<li><a href="javascript:void(0)">网上投诉处理</a></li>
				<li class="active"><a href="javascript:void(0)">网上投诉</a></li>
				<li class="active"><a href="javascript:void(0)">投诉处理</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>

	<!-- 投诉列表页 -->
	<div class="container">
		<div class="headline-v2">
			<h2>投诉处理列表</h2>
		</div>
			<form action="" method="post" class="mb0">
			
			<div class="content table_box">
				<table class="table table-bordered table-condensed table-hover">
					<thead>
						<tr class="info">
						    
							<th class="w50">序号</th>
							<th>投诉人名称</th>
							<th>投诉人类型</th>
							<th>投诉对象</th>
							<th width="35%">投诉事项</th>
						</tr>
					</thead>
					<tbody>
					     <!-- 获取对象时list.被封装在list里面了complaint集合-  var就是下面的值从result里获取-->
						<c:forEach items="${info.list }" varStatus="vs" var="result">
						     <!-- -ondealwith 的值里面要带‘’ -->
							<tr class="tc" onclick="dealWith('${result.id}')" >
								<!-- onclick="check"前面选择这个框的触发事件  value="${list.id}获取result集合里id的值 -->
								<td class="w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
								<td>${result.name }</td>
								<td><c:if test="${result.type=='0'}">
								               单位
								     </c:if> <c:if test="${result.type=='1'}">
								               个人
								     </c:if></td>
								<td>${result.complaintObject }</td>
								<td>${result.complaintMatter }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</form>
		<div id="pageDiv" align="right">
		</div>
	</div>
</body>
</html>