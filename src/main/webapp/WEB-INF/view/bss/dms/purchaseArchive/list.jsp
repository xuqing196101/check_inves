<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
        <%@ include file="/WEB-INF/view/common.jsp"%>
		<title>采购档案列表</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript">
			$(function() {
				$("#name").val("${name}");
				$("#archiveCode").val("${archiveCode}");
				$("#contractCode").val("${contractCode}");
				//$("#planCode").val("${planCode}");
				var status_options = document.getElementById("status").options;
				for(var i = 0; i < status_options.length; i++) {
					if($(status_options[i]).attr("value") == "${status}") {
						status_options[i].selected = true;
					}
				}
				laypage({
					cont: $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
					pages: "${archiveList.pages}", //总页数
					skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
					skip: true, //是否开启跳页
					total: "${archiveList.total}",
					startRow: "${archiveList.startRow}",
					endRow: "${archiveList.endRow}",
					groups: "${archiveList.pages}" >= 5 ? 5 : "${archiveList.pages}", //连续显示分页数
					curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
						var page = location.search.match(/page=(\d+)/);
						return page ? page[1] : 1;
					}(),
					jump: function(e, first) { //触发分页后的回调
						if(!first) { //一定要加此判断，否则初始时会无限刷新
							var name = "${name}";
							var archiveCode = "${archiveCode}";
							var contractCode = "${contractCode}";
							//var planCode = "${planCode}";
							var status = "${status}";
							location.href = "${pageContext.request.contextPath }/purchaseArchive/archiveList.do?name=" + name + "&archiveCode=" + archiveCode + "&contractCode=" + contractCode + "&status=" + status + "&page=" + e.curr;
						}
					}
				});
			})

			//归档
			function placeArchive() {
				var count = 0;
				var id = "";
				var info = document.getElementsByName("info");
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
				}
				if(count == 0) {
					layer.alert("请选择要归档的档案", {
						offset: ['30%', '40%']
					});
					$(".layui-layer-shade").remove();
					return;
				}
				for(var i = 0; i < info.length; i++) {
					if(info[i].checked) {
						id += info[i].value;
					}
				}
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "${pageContext.request.contextPath }/purchaseArchive/placeArchiveById.html?id=" + id,
					success: function(data) {
						if(data == 0) {
							layer.open({
								type: 1,
								title: '信息',
								skin: 'layui-layer-rim',
								shadeClose: true,
								area: ['580px', '210px'],
								content: $("#guidang")
							});
							$(".layui-layer-shade").remove();
						} else if(data == 1) {
							layer.alert("请选择审核通过的档案归档", {
								offset: ['30%', '40%']
							});
							$(".layui-layer-shade").remove();
							return;
						}
					}
				});
			}

			//提交
			function git() {
				var count = 0;
				var ids = "";
				var info = document.getElementsByName("info");
				for(var i = 0; i < info.length; i++) {
					if(info[i].checked == true) {
						count++;
					}
				}
				if(count == 0) {
					layer.alert("请选择要提交的档案", {
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
					url: "${pageContext.request.contextPath }/purchaseArchive/gitArchiveById.html?id=" + ids,
					success: function(data) {
						if(data == 0) {
							layer.msg("提交成功", {
								offset: ['40%', '45%']
							});
							window.setTimeout(function() {
								window.location.href = "${pageContext.request.contextPath }/purchaseArchive/gitStatus.html?id=" + ids;
							}, 1000);
						} else if(data == 1) {
							layer.alert("请选择正确的档案提交", {
								offset: ['30%', '40%']
							});
							$(".layui-layer-shade").remove();
							return;
						}
					}
				});
			}

			//审核
			function audit() {
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
						url: "${pageContext.request.contextPath }/purchaseArchive/auditArchive.html?id=" + str,
						success: function(data) {
							if(data == 0) {
								window.location.href = "${pageContext.request.contextPath }/purchaseArchive/auditGitArchive.html?id=" + str;
							} else if(data == 1) {
								layer.alert("请选择已提交的档案审核", {
									offset: ['30%', '40%']
								});
								$(".layui-layer-shade").remove();
								return;
							}
						}
					});
				}
			}

			//借阅
			function borrow() {
				var count = 0;
				var info = document.getElementsByName("info");
				var id = "";
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
							id = info[i].value;
						}
					}
					$.ajax({
						type: "POST",
						dataType: "json",
						url: "${pageContext.request.contextPath }/purchaseArchive/judgeBorrow.html?id=" + id,
						success: function(data) {
							if(data == 0) {
								window.location.href = "${pageContext.request.contextPath }/purchaseArchive/borrowArchive.do?id=" + id;
							} else if(data == 1) {
								layer.alert("对不起，您无权浏览本档案，请选择其它档案", {
									offset: ['30%', '40%']
								});
								$(".layui-layer-shade").remove();
								return;
							}
						}
					});

				}
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

			//重置
			function resetResult() {
				$("#name").val("");
				$("#archiveCode").val("");
				$("#contractCode").val("");
				//$("#planCode").val("");
				var status = document.getElementById("status").options;
				status[0].selected = true;
			}

			//保存
			function save() {
				var type = $("#type").val();
				var info = document.getElementsByName("info");
				var str = "";
				for(var i = 0; i < info.length; i++) {
					if(info[i].checked == true) {
						str = info[i].value;
					}
				}
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "${pageContext.request.contextPath }/purchaseArchive/leadArchive.do?id=" + str + "&type=" + type,
					success: function(data) {
						if(data == 1) {
							layer.msg("归档成功", {
								offset: ['40%', '45%']
							});
							window.setTimeout(function() {
								window.location.reload();
							}, 1000);
						} else {
							var obj = new Function("return" + data)();
							$("#errorType").html(obj.type);
						}
					}
				});
			}

			//取消
			function cancel() {
				layer.closeAll();
			}

			//采购档案录入
			function add() {
				window.setTimeout(function() {
					window.location.href = "${pageContext.request.contextPath }/purchaseArchive/add.html";
				}, 500);
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
	  
		</script>
	</head>

	<body>
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0);">保障作业</a>
					</li>
					<li>
						<a href="javascript:void(0);">采购档案管理</a>
					</li>
					<li>
						<a href="javascript:jumppage('${pageContext.request.contextPath}/purchaseArchive/archiveList.html');">采购档案列表</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div class="container">
			<div class="headline-v2">
				<h2>档案列表</h2>
			</div>

			<form action="${pageContext.request.contextPath }/purchaseArchive/archiveList.html" method="post">
				<h2 class="search_detail">
				<ul class="demand_list">
					<li>
			    	<label class="fl">档案名称：</label><span><input type="text" id="name" name="name" class=""/></span>
			    </li>
		  		<li>
			    	<label class="fl">档案编号：</label><span><input type="text" id="archiveCode" name="archiveCode" class=""/></span>
			    </li>
		  		<li>
			    	<label class="fl">合同编号：</label><span><input type="text" id="contractCode" name="contractCode" class=""/></span>
			    </li>
			    <li>
			    	<label class="fl">状态：</label>
			    	<span>
			    		<select id="status" name="status">
				  			<option value="">请选择</option>
				  			<option value="1">暂存</option>
				  			<option value="2">审核通过</option>
				  			<option value="3">审核不通过</option>
				  			<option value="4">已归档</option>
				  			<option value="5">已提交</option>
				  		</select>
			    	</span>
			    </li>
			    <div class="col-md-12 col-sm-12 col-xs-12 mt10 tc">
			  		<button class="btn" type="submit">查询</button>
			  		<button class="btn" type="button" onclick="resetResult()">重置</button>
		  		</div>
	  		</ul>
	  		<div class="clear"></div>
	  	</h2>
			</form>

			<!-- 按钮开始-->
			<div class="col-md-12 pl20 mt10">
				<button class="btn btn-windows add" type="button" onclick="add()">录入</button>
				<button class="btn btn-windows git" type="button" onclick="git()">提交</button>
				<button class="btn btn-windows check" type="button" onclick="audit()">审核</button>
				<button class="btn" type="button" onclick="placeArchive()">归档</button>
				<button class="btn" type="button" onclick="borrow()">借阅</button>
			</div>

			<div class="content table_box">
				<table class="table table-bordered table-condensed table-hover">
					<thead>
						<tr class="info">
							<th class="w50"><input type="checkbox" id="selectAll" onclick="selectAll()" /></th>
							<th class="w50">序号</th>
							<th>档案名称</th>
							<th>档案编号</th>
							<th>合同编号</th>
							<th>预算年度</th>
							<th>采购机构</th>
							<th>采购方式</th>
							<th>产品名称</th>
							<th>供应商名称</th>
							<th>状态</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${archiveList.list }" var="archive" varStatus="vs">
							<tr class="tc">
								<td class="w50"><input type="checkbox" value="${archive.id }" name="info" onclick="check()" /></td>
								<td class="w50">${(vs.index+1)+(archiveList.pageNum-1)*(archiveList.pageSize)}</td>
								<td class="tl pl20">${archive.name }</td>
								<td class="tl pl20">${archive.code }</td>
								<td class="tl pl20">${archive.contractCode }</td>
								<td class="tl pl20">${archive.year }</td>
								<td class="tl pl20">${archive.purchaseDep }</td>
								<td>
									<c:forEach items="${kind}" var="kind">
										<c:if test="${kind.id == archive.purchaseType}">${kind.name}</c:if>
									</c:forEach>
								</td>
								<td class="tl pl20">${archive.productName }</td>
								<td class="tl pl20">${archive.supplierName }</td>
								<c:if test="${archive.status==1 }">
									<td>暂存</td>
								</c:if>
								<c:if test="${archive.status==2 }">
									<td>审核通过</td>
								</c:if>
								<c:if test="${archive.status==3 }">
									<td>审核不通过</td>
								</c:if>
								<c:if test="${archive.status==4 }">
									<td>已归档</td>
								</c:if>
								<c:if test="${archive.status==5 }">
									<td>已提交</td>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="pageDiv" align="right"></div>
		</div>

		<ul class="list-unstyled list-flow dnone mt10" id="guidang">
			<li class="col-md-12 ml15">
				<span class="span3 fl mt5"><div class="red star_red">*</div>档案类型：</span>
				<select id="type" name="type" class="w178 fl">
					<option value="">请选择</option>
					<option value="1">临时档案</option>
					<option value="2">永久档案</option>
				</select>
				<div class="clear red" id="errorType"></div>
			</li>
			<div class="col-md-12 mt10 tc">
				<button class="btn btn-windows save" type="button" onclick="save()">保存</button>
				<button class="btn btn-windows cancel" type="button" onclick="cancel()">取消</button>
				
			</div>
		</ul>
	</body>

</html>