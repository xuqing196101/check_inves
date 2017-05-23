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
							var parkNameForSerach = "${parkNameForSerach}";
							var parkContentForSerach = "${parkContentForSerach}";
							location.href = "${ pageContext.request.contextPath }/park/getlist.do?page=" + e.curr + "&parkNameForSerach=" + parkNameForSerach + "&parkContentForSerach=" + parkContentForSerach;
						}
					}
				});
			});
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

			//查看
			function view(id) {
				window.location.href = "${pageContext.request.contextPath }/park/view.html?id=" + id;
			}

			//进入门户
			function entryPortal() {
				var id = [];
				$('input[name="chkItem"]:checked').each(function() {
					id.push($(this).val());
				});
				if(id.length == 1) {
					window.parent.location.href = "${pageContext.request.contextPath }/post/getIndexlist.html?parkId=" + id;
				} else if(id.length > 1) {
					layer.alert("只能选择一项", {
						offset: ['30%', '40%'],
						shade: 0.01
					});
				} else {
					layer.alert("请选择一项", {
						offset: ['30%', '40%'],
						shade: 0.01
					});
				}
			}

			//查看板块所属主题
			function viewTopic() {
				var id = [];
				$('input[name="chkItem"]:checked').each(function() {
					id.push($(this).val());
				});
				if(id.length == 1) {
					window.location.href = "${pageContext.request.contextPath }/park/viewTopic.do?parkId=" + id;
				} else if(id.length > 1) {
					layer.alert("只能选择一项", {
						offset: ['30%', '40%'],
						shade: 0.01
					});
				} else {
					layer.alert("请选择一项", {
						offset: ['30%', '40%'],
						shade: 0.01
					});
				}
			}

			//修改
			function edit() {
				var id = [];
				$('input[name="chkItem"]:checked').each(function() {
					id.push($(this).val());
				});
				if(id.length == 1) {
					window.location.href = "${pageContext.request.contextPath }/park/edit.html?id=" + id;
				} else if(id.length > 1) {
					layer.alert("只能选择一项", {
						offset: ['30%', '40%'],
						shade: 0.01
					});
				} else {
					layer.alert("请选择需要修改的版块", {
						offset: ['30%', '40%'],
						shade: 0.01
					});
				}
			}

			//删除
			function del() {
				var parkNameForSerach = $("#parkNameForSerach").val();
				var parkContentForSerach = $("#parkContentForSerach").val();
				var ids = [];
				$('input[name="chkItem"]:checked').each(function() {
					ids.push($(this).val());
				});
				if(ids.length > 0) {
					layer.confirm('您确定要删除吗?', {
						title: '提示',
						offset: ['30%', '40%'],
						shade: 0.01
					}, function(index) {
						layer.close(index);
						$.ajax({
							type: "POST",
							url: "${pageContext.request.contextPath }/park/delete.html?ids=" + ids,
							success: function(data) {
								layer.msg('删除成功', {
									offset: ['40%', '45%']
								});
								window.setTimeout(function() {
									window.location.href = "${pageContext.request.contextPath }/park/getlist.do?parkNameForSerach=" + parkNameForSerach + "&parkContentForSerach=" + parkContentForSerach;
								}, 1000);
							}
						});
					});
				} else {
					layer.alert("请选择要删除的版块", {
						offset: ['30%', '40%'],
						shade: 0.01
					});
				}
			}

			//新增
			function add() {
				window.location.href = "${pageContext.request.contextPath }/park/add.html";
			}

			//鼠标移动显示全部内容
			function out(content) {
				if(content.length >= 10) {
					layer.msg(content, {
						skin: 'demo-class',
						shade: false,
						closeBtn: [0, true],
						area: ['600px'],
						time: 4000 //默认消息框不关闭
					}); //去掉msg图标
				} else {
					layer.closeAll(); //关闭消息框
				}
			}

			//查询
			function search() {
				var parkNameForSerach = $("#parkNameForSerach").val();
				var parkContentForSerach = $("#parkContentForSerach").val();
				location.href = "${pageContext.request.contextPath }/park/getlist.do?parkNameForSerach=" + parkNameForSerach + "&parkContentForSerach=" + parkContentForSerach;
			}

			//重置
			function reset() {
				$("#parkNameForSerach").val("");
				$("#parkContentForSerach").val("");
			}

			//取消
			function cancel() {
				layer.closeAll();
			}
		</script>
	</head>

	<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="#">首页</a>
					</li>
					<li>
						<a>信息服务</a>
					</li>
					<li>
						<a>论坛管理</a>
					</li>
					<li class="active">
						<a>版块管理</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div class="container">

			<div class="search_detail">
				<ul class="demand_list ">
					<li class="fl">
						<label class="fl">版块名称：</label>
						<span><input type="text" id="parkNameForSerach" class="" value="${parkNameForSerach }"/></span>
					</li>

					<li class="fl">
						<label class="">版块介绍：</label>
						<span><input type="text" id="parkContentForSerach" class="" value="${parkContentForSerach }"/></span>
					</li>
					<button class="btn fl mt1" onclick="search()">查询</button>
					<button class="btn fl mt1" onclick="reset()">重置</button>
				</ul>
				<div class="clear"></div>
			</div>

			<!-- 表格开始-->
			<div class="col-md-12 pl20 mt10">
				<c:if test="${admin==1 }">
					<button class="btn btn-windows add" type="button" onclick="add()">新增</button>
				</c:if>
				<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
				<button class="btn btn-windows delete" type="button" onclick="del()">删除</button>
				<button class="btn" type="button" onclick="entryPortal()">进入门户</button>
				<button class="btn" type="button" onclick="viewTopic()">查看主题</button>
			</div>

		</div>

		<div class="container">
			<div class="content table_box">
				<table class="table table-condensed table-bordered table-hover table-striped">
					<thead>
						<tr>
							<th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
							<th class="info w50">序号</th>
							<th class="info" width="15%">版块名称</th>
							<th class="info" width="20%">版块介绍</th>
							<th class="info" width="15%">版主</th>
							<th class="info" width="6%">热门</th>
							<th class="info" width="10%">创建人</th>
							<th class="info">主题数</th>
							<th class="info">帖子数</th>
							<th class="info">回复数</th>
						</tr>
					</thead>

					<c:forEach items="${list.list}" var="park" varStatus="vs">
						<tr>
							<td class="tc pointer"><input onclick="check()" type="checkbox" name="chkItem" value="${park.id}" /></td>
							<td class="tc pointer" onclick="view('${park.id}')">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
							<td class=" tl pointer" onclick="view('${park.id}')">${park.name}</td>
							<c:set value="${park.content}" var="content"></c:set>
							<c:set value="${fn:length(content)}" var="length"></c:set>
							<c:if test="${length>30}">
								<td onclick="view('${park.id}')" class="tl pointer" title="${content }">${fn:substring(content,0,30)}...</td>
							</c:if>
							<c:if test="${length<=30}">
								<td onclick="view('${park.id}')" class="tl pointer" title="${content }">${content } </td>
							</c:if>
							<td class="tl pointer" onclick="view('${park.id}')">${park.user.relName}</td>
							<c:if test="${park.isHot == 0||park.isHot==''||park.isHot==null}">
								<td class="tc pointer" onclick="view('${park.id}')">否</td>
							</c:if>
							<c:if test="${park.isHot == 1}">
								<td class="tc pointer" onclick="view('${park.id}')">是</td>
							</c:if>
							<td class="tl pointer" onclick="view('${park.id}')">${park.creater.relName}</td>
							<td class="tc  pointer" onclick="view('${park.id}')">${park.topiccount }</td>
							<td class="tc pointer" onclick="view('${park.id}')">${park.postcount }</td>
							<td class="tc pointer" onclick="view('${park.id}')">${park.replycount }</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div id="pagediv" align="right"></div>
		</div>
	</body>

</html>