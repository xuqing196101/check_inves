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
							var parkId = "${parkId}";
							var name = "${name}";
							var content = "${content}";
							location.href = "${pageContext.request.contextPath }/park/viewTopic.do?parkId=" + parkId + "&name=" + name + "&content=" + content + "&page=" + e.curr;
						}
					}
				});
			})

			//返回
			function back() {
				window.location.href = "${pageContext.request.contextPath }/park/backPark.html";
			}

			//重置
			function reset() {
				$("#name").val("");
				$("#content").val("");
			}

			//查询
			function search() {
				var parkId = $("#parkId").val();
				var name = $("#name").val();
				var content = $("#content").val();
				window.location.href = "${pageContext.request.contextPath }/park/viewTopic.do?parkId=" + parkId + "&name=" + name + "&content=" + content;
			}

			//查看详情
			function view(id) {
				window.location.href = "${pageContext.request.contextPath }/topic/view.html?id=" + id;
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
					<li><a href="javascript:jumppage('${pageContext.request.contextPath}/park/getlist.html')">版块管理</a></li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>

		<div class="container mt20">
			<div class="ml20 mt10 tc f18 b">
				版块名称：${park.name }
			</div>
		</div>

		<div class="container">
			<div class="search_detail">
	     	<ul class="demand_list ">
	       		<li class="fl">
	       			<label class="fl">主题名称：</label>
	      	 		<span><input type="text" id="name" value="${name }"/></span>
	       		</li>
	        	<li class="fl">
	       			<label class="fl">主题介绍：</label>
	      	 		<span><input type="text" id="content" value="${content }"/></span>
	       		</li>
	     	</ul>
	         	<button class="btn fl mt1" onclick="search()">查询</button>
	         	<button class="btn fl mt1" onclick="reset()">重置</button>
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
							<th>主题名称</th>
							<th>主题介绍</th>
							<th>创建人</th>
							<th>帖子数</th>
							<th>回复数</th>
						</tr>
					</thead>

					<c:forEach items="${list.list}" var="topic" varStatus="vs">
						<tr>
							<td class="tc pointer" onclick="view('${topic.id}')">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
							<td class="pointer pl20" onclick="view('${topic.id}')">${topic.name}</td>
							<c:set value="${topic.content}" var="content"></c:set>
							<c:set value="${fn:length(content)}" var="length"></c:set>
							<c:if test="${length>30}">
								<td onclick="view('${topic.id}')" class="pointer pl20" onmouseover="titleMouseOver('${content}',this)" onmouseout="titleMouseOut()">${fn:substring(content,0,30)}...</td>
							</c:if>
							<c:if test="${length<=30}">
								<td onclick="view('${topic.id}')" class="pointer pl20">${content } </td>
							</c:if>
							<td class="tc pointer" onclick="view('${topic.id}')">${topic.user.relName}</td>
							<td class="tc pointer" onclick="view('${topic.id}')">${topic.postcount }</td>
							<td class="tc pointer" onclick="view('${topic.id}')">${topic.replycount }</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div id="pagediv" align="right"></div>
		</div>

		<input type="hidden" value="${parkId }" id="parkId" />
	</body>

</html>