<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp"%>
		<script type="text/javascript">
			$(function() {
				laypage({
					cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
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
							var topicId = "${topicId}";
							var name = "${name}";
							location.href = "${pageContext.request.contextPath }/topic/viewPost.do?topicId=" + topicId + "&name=" + name + "&page=" + e.curr;
						}
					}
				});
			})

			//返回
			function back() {
				window.location.href = "${pageContext.request.contextPath }/topic/backTopic.html";
			}

			//重置
			function reset() {
				$("#name").val("");
			}

			//查询
			function search() {
				var topicId = $("#topicId").val();
				var name = $("#name").val();
				window.location.href = "${pageContext.request.contextPath }/topic/viewPost.do?topicId=" + topicId + "&name=" + name;
			}

			//查看详情
			function view(id) {
				window.location.href = "${pageContext.request.contextPath }/post/view.html?id=" + id;
			}
		</script>

	</head>

	<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
					<li><a>信息服务</a></li>
					<li><a>论坛管理</a></li>
					<li class="active"><a href="javascript:jumppage('${pageContext.request.contextPath}/topic/getlist.html')">主题管理</a></li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>

		<div class="container mt20">
			<div class="ml20 mt10 tc f18 b">
				主题名称：${topic.name }
			</div>
		</div>

		<div class="container">
			<div class="search_detail">
				<ul class="demand_list ">
					<li class="fl">
						<label class="fl">帖子名称：</label>
						<span><input type="text" id="name" value="${name }"/></span>
					</li>
					<button class="btn" onclick="search()">查询</button>
					<button class="btn" onclick="reset()">重置</button>
				</ul>
				<div class="clear"></div>
			</div>

			<!-- 表格开始-->
			<div class="col-md-12 pl20 mt10">
				<button class="btn btn-windows back" type="button" onclick="back()">返回</button>
			</div>

			<div class="content table_box">
				<table class="table table-bordered table-condensed table-hover table-striped">

					<thead>
						<tr class="info">
							<th class="w50">序号</th>
							<th>帖子名称</th>
							<th>置顶</th>
							<th>锁定</th>
							<th>发布时间</th>
							<th>最后回复时间</th>
							<th>最后回复人</th>
							<th>回复数</th>
							<th>创建人</th>
						</tr>
					</thead>

					<c:forEach items="${list.list}" var="post" varStatus="vs">
						<tr>
							<td class="tc pointer" onclick="view('${post.id}')">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
							<c:set value="${post.name}" var="name"></c:set>
							<c:set value="${fn:length(name)}" var="length"></c:set>
							<c:if test="${length>10}">
								<td onclick="view('${post.id}')" class="pointer" onmouseover="titleMouseOver('${name}',this)" onmouseout="titleMouseOut()">${fn:substring(name,0,10)}...</td>
							</c:if>
							<c:if test="${length<=10}">
								<td onclick="view('${post.id}')" class="pointer">${name } </td>
							</c:if>
							<c:if test="${post.isTop == 0 ||post.isTop == ''||post.isTop == null }">
								<td class="tc pointer" onclick="view('${post.id}')">否</td>
							</c:if>
							<c:if test="${post.isTop == 1}">
								<td class="tc pointer" onclick="view('${post.id}')">是</td>
							</c:if>
							<c:if test="${post.isLocking == 0||post.isLocking == ''||post.isLocking == null}">
								<td class="tc pointer" onclick="view('${post.id}')">否</td>
							</c:if>
							<c:if test="${post.isLocking == 1}">
								<td class="tc pointer" onclick="view('${post.id}')">是</td>
							</c:if>
							<td class="tc pointer" onclick="view('${post.id}')">
								<fmt:formatDate value='${post.publishedAt}' pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td class="tc pointer" onclick="view('${post.id}')">
								<fmt:formatDate value='${post.lastReplyedAt}' pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td class="tc pointer" onclick="view('${post.id}')">${post.lastReplyer.relName}</td>
							<td class="tc pointer" onclick="view('${post.id}')">${post.replycount}</td>
							<td class="tc pointer" onclick="view('${post.id}')">${post.user.relName}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div id="pagediv" align="right"></div>
		</div>

		<input type="hidden" value="${topicId }" id="topicId" />
	</body>

</html>