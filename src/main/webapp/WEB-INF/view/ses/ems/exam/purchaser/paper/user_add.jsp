<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<title>采购人员考试人员添加</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript">
			$(function() {
				$("#relName").val("${relName}");
				$("#card").val("${card}");
				$("#depName").val("${depName}");
				laypage({
					cont: $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
					pages: "${purchaser.pages}", //总页数
					skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
					skip: true, //是否开启跳页
					total: "${purchaser.total}",
					startRow: "${purchaser.startRow}",
					endRow: "${purchaser.endRow}",
					groups: "${purchaser.pages}" >= 5 ? 5 : "${purchaser.pages}", //连续显示分页数
					curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
						var page = location.search.match(/page=(\d+)/);
						return page ? page[1] : 1;
					}(),
					jump: function(e, first) { //触发分页后的回调
						if(!first) { //一定要加此判断，否则初始时会无限刷新
							var paperId = "${id}";
							var relName = "${relName}";
							var card = "${card}";
							var depName = "${depName}";
							location.href = "${pageContext.request.contextPath }/purchaserExam/userAdd.do?paperId=" + paperId + "&relName=" + relName + "&card=" + card + "&depName=" + depName + "&page=" + e.curr;
						}
					}
				});
			})

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

			//取消方法
			function cancel() {
				layer.closeAll();
			}

			//查询
			function query() {
				var paperId = $("#paperId").val();
				var relName = $("#relName").val();
				var card = $("#card").val();
				var depName = $("#depName").val();
				if((relName == "" || relName == null) && (card == "" || card == null) && (depName == "" || depName == null)) {
					window.location.href = "${pageContext.request.contextPath }/purchaserExam/userAdd.html?paperId=" + paperId;
					return;
				} else {
					window.location.href = "${pageContext.request.contextPath }/purchaserExam/userAdd.do?paperId=" + paperId + "&relName=" + relName + "&card=" + card + "&depName=" + depName;
				}
			}

			//重置结果
			function resetResult() {
				$("#relName").val("");
				$("#card").val("");
				$("#depName").val("");
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

			//添加人员
			function add() {
				var paperId = $("#paperId").val();
				var count = 0;
				var id = "";
				var info = document.getElementsByName("info");
				for(var i = 0; i < info.length; i++) {
					if(info[i].checked == true) {
						count++;
					}
				}
				if(count == 0) {
					layer.alert("请选择一项", {
						offset: ['30%', '40%']
					});
					$(".layui-layer-shade").remove();
					return;
				}
				for(var i = 0; i < info.length; i++) {
					if(info[i].checked) {
						id += info[i].value + ',';
					}
				}
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "${pageContext.request.contextPath }/purchaserExam/checkPurchaserInfo.do?id=" + id,
					success: function(data) {
						if(data) {
							var html = "";
							for(var i = 0; i < data.length; i++) {
								html = html + "<tr class='tc'>";
								html = html + "<td>" + (i + 1) + "</td>";
								html = html + "<td>" + data[i].relName + "</td>";
								html = html + "<td>" + data[i].idCard + "</td>";
								html = html + "<td>" + data[i].purchaseDepName + "</td>";
								html = html + "</tr>";
							}
							$("#refResult").html(html);
							layer.open({
								type: 1,
								title: '信息',
								skin: 'layui-layer-rim',
								shadeClose: true,
								area: ['50%', 'auto'],
								content: $("#purchaser")
							});
							$(".layui-layer-shade").remove();
						}
					}
				})
			}

			//审核确定
			function sure() {
				var paperId = $("#paperId").val();
				var count = 0;
				var id = "";
				var info = document.getElementsByName("info");
				for(var i = 0; i < info.length; i++) {
					if(info[i].checked) {
						id += info[i].value + ',';
					}
				}
				$.ajax({
					type: "POST",
					dataType: "text",
					url: "${pageContext.request.contextPath }/purchaserExam/addReferenceById.do?paperId=" + paperId + "&id=" + id,
					success: function(data) {
						if(data.length <= 5) {
							layer.msg('添加成功', {
								offset: ['40%', '45%']
							});
							window.setTimeout(function() {
								window.location.reload();
							}, 1000);
						} else {
							var array = data.split(";");
							if(array[array.length - 1].indexOf("1") > -1) {
								$("#errorNews").html("以下人员已经被添加到本场考试中");
								var html = "";
								for(var i = 0; i < array.length - 1; i++) {
									html = html + "<tr class='tc'>";
									html = html + "<td>" + (i + 1) + "</td>";
									if(i == 0) {
										html = html + "<td>" + array[i].split(",")[0].substring(1) + "</td>";
									} else {
										html = html + "<td>" + array[i].split(",")[0] + "</td>";
									}
									html = html + "<td>" + array[i].split(",")[2] + "</td>";
									html = html + "<td>" + array[i].split(",")[1] + "</td>";
									html = html + "</tr>";
								}
								$("#errResult").html(html);
								layer.open({
									type: 1,
									title: '错误信息',
									skin: 'layui-layer-rim',
									shadeClose: true,
									area: ['400px', '400px'],
									content: $("#errorPurchaser")
								});
								$(".layui-layer-shade").remove();
							} else if(array[array.length - 1].indexOf("2") > -1) {
								$("#errorNews").html("以下人员考试时间有冲突");
								var html = "";
								for(var i = 0; i < array.length - 1; i++) {
									html = html + "<tr class='tc'>";
									html = html + "<td>" + (i + 1) + "</td>";
									if(i == 0) {
										html = html + "<td>" + array[i].split(",")[0].substring(1) + "</td>";
									} else {
										html = html + "<td>" + array[i].split(",")[0] + "</td>";
									}
									html = html + "<td>" + array[i].split(",")[2] + "</td>";
									html = html + "<td>" + array[i].split(",")[1] + "</td>";
									html = html + "</tr>";
								}
								$("#errResult").html(html);
								layer.open({
									type: 1,
									title: '错误信息',
									skin: 'layui-layer-rim',
									shadeClose: true,
									area: ['400px', '400px'],
									content: $("#errorPurchaser")
								});
								$(".layui-layer-shade").remove();
							}
						}
					}
				});
			}

			//返回
			function back() {
				var paperId = $("#paperId").val();
				window.location.href = "${pageContext.request.contextPath }/purchaserExam/viewReference.html?id=" + paperId;
			}
		</script>

	</head>

	<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs">
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

		<div class="container mt20">
			<div class="ml20 mt10 tc f18 b">
				考卷编号：${examPaper.code }
			</div>
		</div>

		<div class="container">
			<h2 class="search_detail">
				<ul class="demand_list">
			    	<li>
				    	<label class="fl">姓名：</label>
				    	<span>
				    		<input type="text" id="relName" name="relName"/>
				    	</span>
				    </li>
				    <li>
				    	<label class="fl">身份证号：</label>
				    	<span>
					    	<input type="text" id="card" name="card"/>
				   		</span>
				     </li>
				     <li>
				    	<label class="fl">采购机构：</label>
				    	<span>
					    	<input type="text" id="depName" name="depName"/>
				   		</span>
				     </li>
				    <button class="btn fl mt1" type="button" onclick="query()">查询</button>
		    		<button class="btn fl mt1" type="button" onclick="resetResult()">重置</button>
			    </ul>
			    <div class="clear"></div>
		 	</h2>

			<!-- 按钮 -->
			<div class="col-md-12 pl20 mt10">
				<button class="btn btn-windows add" type="button" onclick="add()">添加</button>
			    <input class="btn btn-windows back" value="返回" type="button" onclick="back()">
			</div>

			<div class="content table_box">
				<table class="table table-bordered table-condensed table-hover">
					<thead>
						<tr class="info">
							<th class="w50"><input type="checkbox" id="selectAll" onclick="selectAll()" /></th>
							<th class="w50">序号</th>
							<th>姓名</th>
							<th>身份证号</th>
							<th>所属单位</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${purchaser.list }" var="user" varStatus="vs">
							<tr class="tc">
								<td><input type="checkbox" name="info" value="${user.userId }" onclick="check()" /></td>
								<td>${(vs.index+1)+(purchaser.pageNum-1)*(purchaser.pageSize)}</td>
								<td>${user.relName }</td>
								<td>${user.idCard }</td>
								<td>${user.purchaseDepName }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="pageDiv" align="right" class="mr10"></div>
		</div>

		<!-- 返回按钮 -->
		

		<input type="hidden" value="${examPaper.id }" name="paperId" id="paperId" />

		<div class="dnone layui-layer-wrap col-md-12" id="purchaser">
			<table class="table table-bordered table-condensed table-hover">
				<thead>
					<tr class="info">
						<th class="w50">序号</th>
						<th class="w100">姓名</th>
						<th>身份证号</th>
						<th>所属单位</th>
					</tr>
				</thead>
				<tbody id="refResult">
				</tbody>
			</table>
			<div class="col-md-12 col-sm-12 col-xs-12 mt10 tc">
				<button class="btn btn-windows add" type="button" onclick="sure()">确定</button>
				<button class="btn btn-windows cancel" type="button" onclick="cancel()">取消</button>
			</div>
		</div>

		<div class="dnone layui-layer-wrap col-md-12" id="errorPurchaser">
			<div id="errorNews" class="col-md-12 tc red mb5 f18"></div>
			<table class="table table-bordered table-condensed table-hover">
				<thead>
					<tr class="info">
						<th class="w50">序号</th>
						<th class="w60">姓名</th>
						<th>身份证号</th>
						<th>所属单位</th>
					</tr>
				</thead>
				<tbody id="errResult">
				</tbody>
			</table>
		</div>
	</body>

</html>