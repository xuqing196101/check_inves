<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
							var replyCon = "${replyCon}";
							location.href = "${pageContext.request.contextPath }/reply/getlist.do?replyCon=" + replyCon + "&page=" + e.curr;
						}
					}
				});

				//setInterval("getInstantReply()",1000);
			});

			/** 获得即时回复*/
			function getInstantReply() {
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "${pageContext.request.contextPath }/",
					success: function(data) {

					}
				});
			}

			/** 全选全不选 */
			function selectAll() {
				var checklist = document.getElementsByName("chkItem");
				var checkAll = document.getElementById("checkAll");
				if(checkAll.checked) {
					for(var i = 0; i < checklist.length; i++) {
						checklist[i].checked = true;
					}
				} else {
					for(var j = 0; j < checklist.length; j++) {
						checklist[j].checked = false;
					}
				}
			}

			/** 单选 */
			function check() {
				var count = 0;
				var checklist = document.getElementsByName("chkItem");
				var checkAll = document.getElementById("checkAll");
				for(var i = 0; i < checklist.length; i++) {
					if(checklist[i].checked == false) {
						checkAll.checked = false;
						break;
					}
					for(var j = 0; j < checklist.length; j++) {
						if(checklist[j].checked == true) {
							checkAll.checked = true;
							count++;
						}
					}
				}
			}

			function view(id) {
				window.location.href = "${pageContext.request.contextPath }/reply/view.html?id=" + id;
			}

			function edit() {
				var id = [];
				$('input[name="chkItem"]:checked').each(function() {
					id.push($(this).val());
				});
				if(id.length == 1) {
					window.location.href = "${pageContext.request.contextPath }/reply/edit.html?id=" + id;
				} else if(id.length > 1) {
					layer.alert("只能选择一个", {
						offset: ['30%', '40%'],
						shade: 0.01
					});
				} else {
					layer.alert("请选择需要修改的回复", {
						offset: ['30%', '40%'],
						shade: 0.01
					});
				}
			}

			//删除
			function del() {
				var replyCon = $("#replyCon").val();
				var id = [];
				$('input[name="chkItem"]:checked').each(function() {
					id.push($(this).val());
				});
				if(id.length > 0) {
					layer.confirm('您确定要删除吗?', {
						title: '提示',
						offset: ['30%', '40%'],
						shade: 0.01
					}, function(index) {
						layer.close(index);
						$.ajax({
							type: "POST",
							url: "${pageContext.request.contextPath }/reply/delete.html?id=" + id,
							success: function(data) {
								layer.msg('删除成功', {
									offset: ['40%', '45%']
								});
								window.setTimeout(function() {
									window.location.href = "${pageContext.request.contextPath }/reply/getlist.do?replyCon=" + replyCon;
								}, 1000);
							}
						});
					});
				} else {
					layer.alert("请选择要删除的回复", {
						offset: ['30%', '40%'],
						shade: 0.01
					});
				}
			}

			//新增
			function add() {
				window.location.href = "${pageContext.request.contextPath }/reply/add.html";
			}

			//鼠标移动显示全部内容
			function out(content) {
				if(content.length >= 10) {
					layer.msg(content, {
						skin: 'demo-class',
						shade: false,
						area: ['600px'],
						closeBtn: [0, true],
						time: 4000 //默认消息框不关闭
					}); //去掉msg图标
				} else {
					layer.closeAll(); //关闭消息框
				}
			}

			//查询
			function search() {
				var replyCon = $("#replyCon").val();
				location.href = "${ pageContext.request.contextPath }/reply/getlist.do?replyCon=" + replyCon;
			}

			//重置
			function reset() {
				$("#replyCon").val("");
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
					<li class="active">
						<a href="javascript:jumppage('${pageContext.request.contextPath}/reply/getlist.html')">回复管理</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div class="container">
			<!-- 项目戳开始 -->
			<div class="search_detail">
				<ul class="demand_list ">
					<li class="fl">
						<label class="fl">回复内容：</label>
						<span><input type="text" id="replyCon" class="mb0 " value="${replyCon }"/></span>
					</li>
					<button class="btn fl" onclick="search()">查询</button>
					<button class="btn fl" onclick="reset()">重置</button>
				</ul>
				<div class="clear"></div>
			</div>

			<!-- 表格开始-->
			<div class="col-md-12 pl20 mt10">
				<button class="btn btn-windows delete" type="button" onclick="del()">删除</button>
			</div>

			<div class="content table_box">
				<table class="table table-bordered table-condensed table-hover table-striped">
					<thead>
						<tr>
							<th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
							<th class="info w50">序号</th>
							<th class="info" width="25%">回复内容</th>
							<th class="info" width="15%">发布时间</th>
							<th class="info" width="15%">发表人</th>
							<th class="info" width="15%">所属帖子名称</th>
							<th class="info">所属回复内容</th>
						</tr>
					</thead>

					<c:forEach items="${list.list}" var="reply" varStatus="vs">
						<tr>
							<td class="tc pointer"><input onclick="check()" type="checkbox" name="chkItem" value="${reply.id}" /></td>
							<td class="tc pointer" onclick="view('${reply.id}')">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
							<c:set value="${reply.content}" var="content"></c:set>
							<c:set value="${fn:length(content)}" var="length"></c:set>
							<td onclick="view('${reply.id}')" class="pointer">${content }</td>
							<td class="tc pointer" onclick="view('${reply.id}')">
								<fmt:formatDate value='${reply.publishedAt}' pattern="yyyy-MM-dd  HH:mm:ss" />
							</td>
							<td class="tl pointer" onclick="view('${reply.id}')">${reply.user.relName}</td>
							<c:set value="${reply.post.name}" var="postContent"></c:set>
							<c:set value="${fn:length(postContent)}" var="length"></c:set>
							<c:if test="${length>16}">
								<td onclick="view('${reply.id}')" class="pointer" title="${postContent }">${fn:substring(postContent,0,16)}...</td>
							</c:if>
							<c:if test="${length<=16}">
								<td onclick="view('${reply.id}')" class="pointer" title="${postContent }">${postContent }</td>
							</c:if>
							<c:set value="${reply.reply.content}" var="replyContent"></c:set>
							<c:set value="${fn:length(replyContent)}" var="length"></c:set>
							<td onclick="view('${reply.id}')" class="pointer">${replyContent }</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div id="pagediv" align="right"></div>
		</div>

	</body>

</html>