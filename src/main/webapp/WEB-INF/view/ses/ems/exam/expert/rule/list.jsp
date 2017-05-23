<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<title>专家考试规则列表</title>
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
							location.href = "${pageContext.request.contextPath }/expertExam/createRule.do?page=" + e.curr;
						}
					}
				});
			})

			//新增
			function add() {
				window.location.href = "${pageContext.request.contextPath }/expertExam/createRule.html";
			}

			//全选方法
			function selectAll() {
				var info = document.getElementsByName("info");
				var selectAll = document.getElementById("selectAll");
				if(selectAll.checked) {
					for(var i = 0; i < info.length; i++) {
						info[i].checked = true;
					}
				} else {
					for(var i = 0; i < info.length; i++) {
						info[i].checked = false;
					}
				}
			}

			//修改
			function edit() {
				var count = 0;
				var info = document.getElementsByName("info");
				var str = "";
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
							str = info[i].value;
						}
					}
					window.location.href = "${pageContext.request.contextPath }/expertExam/editRule.html?id=" + str;
				}
			}

			//检查全选
			function check() {
				var count = 0;
				var info = document.getElementsByName("info");
				var selectAll = document.getElementById("selectAll");
				for(var i = 0; i < info.length; i++) {
					if(info[i].checked == false) {
						selectAll.checked = false;
						break;
					}
				}
				for(var i = 0; i < info.length; i++) {
					if(info[i].checked == true) {
						count++;
					}
				}
				if(count == info.length) {
					selectAll.checked = true;
				}
			}

			//查看考试规则
			function view(obj) {
				window.location.href = "${pageContext.request.contextPath }/expertExam/viewRule.html?id=" + obj;
			}

			//启用
			function startRule() {
				var count = 0;
				var info = document.getElementsByName("info");
				var str = "";
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
							str = info[i].value;
						}
					}
					layer.confirm('您确定要使用这个规则吗?', {
						title: '提示',
						offset: ['30%', '40%'],
						shade: 0.01
					}, function(index) {
						layer.close(index);
						window.setTimeout(function() {
							window.location.href = "${pageContext.request.contextPath }/expertExam/startRule.do?id=" + str;
						}, 1000);
					});
				}
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
						<a href="javascript:void(0);">专家管理</a>
					</li>
					<li>
						<a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/expertExam/ruleList.html')">专家考试规则管理</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div class="container">
			<div class="headline-v2">
				<h2>历史考试规则列表</h2>
			</div>

			<!-- 表格开始-->
			<div class="col-md-12 pl20 mt10">
				<button class="btn btn-windows add" type="button" onclick="add()">新增</button>
				<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
				<button class="btn" type="button" onclick="startRule()">启用</button>
			</div>

			<div class="content table_box">
				<table class="table table-bordered table-condensed table-hover">
					<thead>
						<tr class="info">
							<th class="w50"><input type="checkbox" id="selectAll" onclick="selectAll()" /></th>
							<th class="w50">序号</th>
							<th>题型分布</th>
							<th>总分值(分)</th>
							<th>及格标准(分)</th>
							<th>状态</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list.list }" var="rule" varStatus="vs">
							<tr class="pointer tc">
								<td><input type="checkbox" name="info" value="${rule.id }" onclick="check()" /></td>
								<td onclick="view('${rule.id }')">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
								<td class="tl pl20" onclick="view('${rule.id }')">${rule.discribution }</td>
								<td class="tc" onclick="view('${rule.id }')">${rule.paperScore }</td>
								<td class="tc" onclick="view('${rule.id }')">${rule.passStandard }</td>
								<c:if test="${rule.status==0 }">
									<td onclick="view('${rule.id }')">停用中</td>
								</c:if>
								<c:if test="${rule.status==1 }">
									<td  onclick="view('${rule.id }')">启用中</td>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="pageDiv" align="right"></div>
		</div>
	</body>

</html>