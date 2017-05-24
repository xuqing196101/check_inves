<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<title>历史考卷列表</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript">
			//新增考卷
			function addPaper() {
				window.location.href = "${pageContext.request.contextPath }/purchaserExam/addPaper.html";
			}

			$(function() {
				laypage({
					cont: $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
					pages: "${paperList.pages}", //总页数
					skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
					skip: true, //是否开启跳页
					total: "${paperList.total}",
					startRow: "${paperList.startRow}",
					endRow: "${paperList.endRow}",
					groups: "${paperList.pages}" >= 5 ? 5 : "${paperList.pages}", //连续显示分页数
					curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
						var page = location.search.match(/page=(\d+)/);
						return page ? page[1] : 1;
					}(),
					jump: function(e, first) { //触发分页后的回调
						if(!first) { //一定要加此判断，否则初始时会无限刷新
							location.href = "${pageContext.request.contextPath }/purchaserExam/paperManage.do?page=" + e.curr;
						}
					}
				});

			})

			//编辑考卷
			function editPaper() {
				var count = 0;
				var ids = "";
				var info = document.getElementsByName("info");
				for(var i = 0; i < info.length; i++) {
					if(info[i].checked == true) {
						count++;
					}
				}
				if(count == 0) {
					layer.alert("请先选择一项", {
						offset: ['30%', '40%']
					});
					$(".layui-layer-shade").remove();
					return;
				}
				if(count > 1) {
					layer.alert("只能修改一项", {
						offset: ['30%', '40%']
					});
					$(".layui-layer-shade").remove();
					return;
				}
				for(var i = 0; i < info.length; i++) {
					if(info[i].checked) {
						ids += info[i].value + ',';
					}
				}
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "${pageContext.request.contextPath }/purchaserExam/editSelectedPaper.html?id=" + ids,
					success: function(data) {
						if(data == 1) {
							layer.msg('考卷正在考试期间,请勿编辑', {
								offset: ['30%', '40%']
							});
						} else if(data == 2) {
							window.location.href = "${pageContext.request.contextPath }/purchaserExam/editPaper.html?id=" + ids;
						} else if(data == 3) {
							layer.msg('考卷已经过了考试有效期,不好编辑', {
								offset: ['30%', '40%']
							});
						}
					}
				});
			}

			//查看考卷
			function view(obj) {
				window.location.href = "${pageContext.request.contextPath }/purchaserExam/viewPaper.html?id=" + obj;
			}

			//设置参考人员
			function setReference() {
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
					$.ajax({
						type: "POST",
						dataType: "json",
						url: "${pageContext.request.contextPath }/purchaserExam/setReference.do?id=" + str,
						success: function(data) {
							if(data == 1) {
								layer.alert("考卷正在考试中,请选择其它考卷", {
									offset: ['30%', '40%']
								});
								$(".layui-layer-shade").remove();
							} else if(data == 2) {
								window.location.href = "${pageContext.request.contextPath }/purchaserExam/viewReference.do?id=" + str;
							} else if(data == 3) {
								layer.alert("考卷考试时间已结束,请选择其它考卷", {
									offset: ['30%', '40%']
								});
								$(".layui-layer-shade").remove();
							}
						}
					});

				}
			}

			//查看成绩
			function viewScore() {
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
					$.ajax({
						type: "POST",
						dataType: "json",
						url: "${pageContext.request.contextPath }/purchaserExam/setReference.do?id=" + str,
						success: function(data) {
							if(data == 1) {
								layer.alert("考卷正在考试中,请选择其它考卷", {
									offset: ['30%', '40%']
								});
								$(".layui-layer-shade").remove();
							} else if(data == 2) {
								layer.alert("考卷考试时间未结束,请选择其它考卷", {
									offset: ['30%', '40%']
								});
								$(".layui-layer-shade").remove();
							} else if(data == 3) {
								window.location.href = "${pageContext.request.contextPath }/purchaserExam/viewReference.do?id=" + str;
							}
						}
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
				<h2>历史考卷列表</h2>
			</div>

			<div class="col-md-12 pl20 mt10">
				<input type="button" class="btn btn-windows add" value="新增" onclick="addPaper()" />
				<input type="button" class="btn btn-windows edit" value="修改" onclick="editPaper()" />
				<input type="button" class="btn" value="设置参考人员" onclick="setReference()" />
				<input type="button" class="btn" value="查看成绩" onclick="viewScore()" />
			</div>

			<div class="content table_box">
				<table class="table table-bordered table-condensed table-hover">
					<thead>
						<tr class="info">
							<th class="w50">选择</th>
							<th class="w50">序号</th>
							<th>考卷名称</th>
							<th>考卷年度</th>
							<th>考试开始时间</th>
							<th>考试截止时间</th>
							<th>考卷状态</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${paperList.list }" var="paper" varStatus="vs">
							<tr class="tc">
								<td><input type="checkbox" name="info" value="${paper.id }" /></td>
								<td class="pointer" onclick="view('${paper.id }')">${(vs.index+1)+(paperList.pageNum-1)*(paperList.pageSize)}</td>
								<td class="tl pl20 pointer" onclick="view('${paper.id }')">${paper.name }</td>
								<td class="pointer" onclick="view('${paper.id }')">${paper.year }</td>
								<td class="pointer" onclick="view('${paper.id }')">${paper.startTrueDate }</td>
								<td class="pointer" onclick="view('${paper.id }')">${paper.offTrueDate }</td>
								<td class="pointer" onclick="view('${paper.id }')">${paper.status }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="pageDiv" align="right"></div>
		</div>
	</body>

</html>