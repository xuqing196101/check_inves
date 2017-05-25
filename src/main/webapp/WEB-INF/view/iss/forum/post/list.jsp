<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp"%>

		<script type="text/javascript">
			$(function() {
				$("#parkId").val("${parkId}");
				var parkId = "${parkId}";
				if(parkId != null && parkId != "") {
					$.ajax({
						url: "${ pageContext.request.contextPath }/topic/getListForSelect.html?parkId=" + parkId,
						contentType: "application/json;charset=UTF-8",
						dataType: "json", //返回格式为json
						type: "POST", //请求方式           
						success: function(topics) {
							if(topics) {
								$("#topicId").html("<option></option>");
								$.each(topics, function(i, topic) {
									$("#topicId").append("<option  value=" + topic.id + ">" + topic.name + "</option>");
								});
								$("#topicId").val("${topicId}");
							}
						}
					});
				}

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
							var postName = "${postName}";
							var parkId = "${parkId}";
							var topicId = "${topicId}";
							location.href = "${ pageContext.request.contextPath }/post/getlist.do?postName=" + postName + "&parkId=" + parkId + "&topicId=" + topicId + "&page=" + e.curr;
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

			//查看详情
			function view(id) {
				window.location.href = "${pageContext.request.contextPath }/post/view.html?id=" + id;
			}

			//进入门户
			function entryPortal() {
				var id = [];
				$('input[name="chkItem"]:checked').each(function() {
					id.push($(this).val());
				});
				if(id.length == 1) {
					window.parent.location.href = "${pageContext.request.contextPath }/post/getIndexDetail.html?postId=" + id;
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
					window.location.href = "${pageContext.request.contextPath }/post/edit.html?id=" + id;
				} else if(id.length > 1) {
					layer.alert("只能选择一项", {
						offset: ['30%', '40%'],
						shade: 0.01
					});
				} else {
					layer.alert("请选择需要修改的帖子", {
						offset: ['30%', '40%'],
						shade: 0.01
					});
				}
			}

			//删除
			function del() {
				var postName = $("#postName").val();
				var parkId = $("#parkId  option:selected").val();
				var topicId = $("#topicId  option:selected").val();
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
							url: "${pageContext.request.contextPath }/post/delete.html?id=" + id,
							success: function(data) {
								layer.msg('删除成功', {
									offset: ['40%', '45%']
								});
								window.setTimeout(function() {
									window.location.href = "${pageContext.request.contextPath }/post/getlist.do?postName=" + postName + "&parkId=" + parkId + "&topicId=" + topicId;
								}, 1000);
							}
						});
					});
				} else {
					layer.alert("请选择要删除的帖子", {
						offset: ['30%', '40%'],
						shade: 0.01
					});
				}
			}

			function add() {
				window.location.href = "${pageContext.request.contextPath }/post/add.html";
			}

			//鼠标移动显示全部内容
			function out(name) {
				if(name.length >= 10) {
					layer.msg(name, {
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
			//2级联动
			function change(id) {
				$.ajax({
					url: "${pageContext.request.contextPath }/topic/getListForSelect.html?parkId=" + id,
					contentType: "application/json;charset=UTF-8",
					dataType: "json", //返回格式为json
					type: "POST", //请求方式           
					success: function(topics) {
						if(topics) {
							$("#topicId").html("<option></option>");
							$.each(topics, function(i, topic) {
								$("#topicId").append("<option  value=" + topic.id + ">" + topic.name + "</option>");
							});
						}
					}
				});
			}

			function search() {
				var postName = $("#postName").val();
				var parkId = $("#parkId  option:selected").val();
				var topicId = $("#topicId  option:selected").val();
				location.href = "${pageContext.request.contextPath }/post/getlist.do?postName=" + postName + "&parkId=" + parkId + "&topicId=" + topicId;

			}

			function reset() {
				$("#postName").val("");
				var parks = document.getElementById("parkId").options;
				parks[0].selected = true;
				var tipics = document.getElementById("topicId").options;
				tipics[0].selected = true;
			}

			function viewDetail(id) {
				$("#formForViewDetail").attr("action", "${ pageContext.request.contextPath }/post/getIndexDetail.html?postId=" + id);
				$("#formForViewDetail").submit();
			}

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
					<li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
					<li><a>信息服务</a></li>
					<li><a>论坛管理</a></li>
					<li class="active">
						<a href="javascript:jumppage('${pageContext.request.contextPath}/post/getlist.html')">帖子管理</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>

		<div class="container">
			<!-- 项目戳开始 -->
			<div class="search_detail">
     		<ul class="demand_list">
       		<li class="fl">
	       		<label class="fl">帖子名称：</label>
	       		<span><input type="text" id="postName" class="" value="${postName }"/></span>
       		</li>
        
       		<li class="fl">
         		<label class="fl">所属版块：</label>
            	<span>
	            	<select id ="parkId" class="w178" onchange="change(this.options[this.selectedIndex].value)" >
	             	<option></option>
	             	<c:forEach items="${parks}" var="park">
	                  <option  value="${park.id}">${park.name}</option>
	              </c:forEach> 
	             	</select>
            	</span>
       		</li>
       		
        	<li class="fl">
         		<label class="fl">所属主题：</label>
            	<span>
	            	<select id ="topicId" class="w178 " >
	             	<option></option>
	             	</select>
            	</span>
       		</li>
         	<button class="btn fl" onclick="search()">查询</button>
         	<button class="btn fl" onclick="reset()">重置</button>
     	</ul>
     	<div class="clear"></div>
  </div>

			<div class="col-md-12 pl20 mt10">
				<button class="btn btn-windows add" type="button" onclick="add()">新增</button>
				<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
				<button class="btn btn-windows delete" type="button" onclick="del()">删除</button>
				<button class="btn" type="button" onclick="entryPortal()">进入门户</button>
			</div>

			<div class="content table_box">
				<table class="table table-bordered table-condensed table-hover table-striped">

					<thead>
						<tr>
							<th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
							<th class="info w50">序号</th>
							<th class="info" width="17%">帖子名称</th>
							<th class="info" width="5%">置顶</th>
							<th class="info" width="5%">锁定</th>
							<th class="info" width="15%">发布时间</th>
							<th class="info" width="15%">最后回复时间</th>
							<th class="info"  width="10%">最后回复人</th>
							<th class="info">回复数</th>
							<th class="info" width="10%">创建人</th>
							<th class="info">操作</th>
						</tr>
					</thead>

					<c:forEach items="${list.list}" var="post" varStatus="vs">
						<tr>
							<td class="tc pointer"><input onclick="check()" type="checkbox" name="chkItem" value="${post.id}" /></td>
							<td class="tc pointer" onclick="view('${post.id}')">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
							<c:set value="${post.name}" var="name"></c:set>
							<c:set value="${fn:length(name)}" var="length"></c:set>
							<c:if test="${length>10}">
								<td onclick="view('${post.id}')" class="pointer" title="${name }">${fn:substring(name,0,10)}...</td>
							</c:if>
							<c:if test="${length<=10}">
								<td onclick="view('${post.id}')" class="pointer" title="${name }">${name } </td>
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
							<td class="tc pointer">
								<form id="formForViewDetail" action="" target="_parent" method="post" class="m0">
									<input type="button" class="btn" value="查看回复" onclick="viewDetail('${post.id}')">
									</input>
								</form>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div id="pagediv" align="right"></div>
		</div>
	</body>

</html>