<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
        <%@ include file="/WEB-INF/view/common.jsp"%>
		<title>授权档案</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript">
			$(function() {
				$("#name").val("${name}");
				$("#code").val("${code}");
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
							var id = "${id}";
							var name = "${name}";
							var code = "${code}";
							location.href = "${pageContext.request.contextPath }/purchaseArchive/jumpToAuthorize.do?name=" + name + "&code=" + code + "&id=" + id + "&page=" + e.curr;
						}
					}
				});

			})

			//授权
			function auth() {
				var userId = $("#userId").val();
				var info = document.getElementsByName("info");
				var id = "";
				var count = 0;
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
						url: "${pageContext.request.contextPath }/purchaseArchive/authFileToPeople.do?id=" + id + "&userId=" + userId,
						success: function(data) {
							var html = "";
							for(var i = 0; i < data.length; i++) {
								html = html + "<tr class='tc'>";
								//alert(data[i].status);
								if(data[i].status == 1) {
									html = html + "<td><input type='checkbox' name='fileInfo' checked='checked' onclick='checkFileInfo()' value='" + data[i].id + "'/></td>";
								} else {
									html = html + "<td><input type='checkbox' name='fileInfo' onclick='checkFileInfo()' value='" + data[i].id + "'/></td>";
								}
								html = html + "<td>" + (i + 1) + "</td>";
								html = html + "<td>" + data[i].name + "</td>";
								html = html + "<td>" + data[i].size + "</td>";
								html = html + "</tr>";
							}
							$("#fileNews").html(html);
							/* var count = 0;
							var fileInfo = document.getElementsByName("fileInfo");
							var selectFileAll = document.getElementById("selectFileAll");
							for(var i = 0; i < fileInfo.length; i++) {
								if(fileInfo[i].checked == true) {
									count++;
								}
							}
							if(count == fileInfo.length) {
								selectFileAll.checked = true;
							} */
							layer.open({
								type: 1,
								title: '信息',
								skin: 'layui-layer-rim',
								shadeClose: true,
								area: ['580px', '410px'],
								content: $("#news")
							});
							$(".layui-layer-shade").remove();
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

			//全选方法
			function selectFileAll() {
				var fileInfo = document.getElementsByName("fileInfo");
				var selectFileAll = document.getElementById("selectFileAll");
				if(selectFileAll.checked) {
					for(var i = 0; i < fileInfo.length; i++) {
						fileInfo[i].checked = true;
					}
				} else {
					for(var i = 0; i < fileInfo.length; i++) {
						fileInfo[i].checked = false;
					}
				}
			}

			//检查全选
			function checkFileInfo() {
				var count = 0;
				var fileInfo = document.getElementsByName("fileInfo");
				var selectFileAll = document.getElementById("selectFileAll");
				for(var i = 0; i < fileInfo.length; i++) {
					if(fileInfo[i].checked == false) {
						selectFileAll.checked = false;
						break;
					}
				}
				for(var i = 0; i < fileInfo.length; i++) {
					if(fileInfo[i].checked == true) {
						count++;
					}
				}
				if(count == fileInfo.length) {
					selectFileAll.checked = true;
				}
			}

			//重置
			function resetResult() {
				$("#name").val("");
				$("#code").val("");
			}

			//取消
			function cancel() {
				layer.closeAll();
			}

			//保存
			function save() {
				var userId = $("#userId").val();
				var info = document.getElementsByName("info");
				var fileInfo = document.getElementsByName("fileInfo");
				var id = "";
				var fileId = "";
				for(var i = 0; i < info.length; i++) {
					if(info[i].checked == true) {
						id = info[i].value;
					}
				}
				for(var i = 0; i < fileInfo.length; i++) {
					if(fileInfo[i].checked == true) {
						fileId += fileInfo[i].value + ",";
					}
				}
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "${pageContext.request.contextPath }/purchaseArchive/saveFile.html?id=" + id + "&userId=" + userId + "&fileId=" + fileId,
					success: function(data) {
						layer.msg("设置成功", {
							offset: ['40%', '45%']
						});
						window.setTimeout(function() {
							window.location.reload();
						}, 1000);
					}
				});
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
						<a href="javascript:jumppage('${pageContext.request.contextPath}/purchaseArchive/archiveAuthorize.html');">采购档案授权</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div class="container mt10">
			<form action="${pageContext.request.contextPath }/purchaseArchive/jumpToAuthorize.html" method="post">
				<input type="hidden" value="${id }" name="userId" id="userId" />
				<h2 class="search_detail">
				<ul class="demand_list">
					<li>
				    	<label class="fl">档案名称：</label><span><input type="text" id="name" name="name"/></span>
				    </li>
			  		<li>
				    	<label class="fl">档案编号：</label><span><input type="text" id="code" name="code"/></span>
				    </li>
				  	<button class="btn" type="submit">查询</button>
				  	<button class="btn" type="button" onclick="resetResult()">重置</button>
		  		</ul>
		  		<div class="clear"></div>
		  	</h2>
			</form>

			<!-- 按钮开始-->
			<div class="col-md-12 pl20 mt10">
				<button class="btn" type="button" onclick="auth()">授权设置</button>
				<input class="btn btn-windows back" value="返回" type="button" onclick="history.go(-1)">
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
								<td class="tl pl20">
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

		<div class="dnone layui-layer-wrap col-md-12 mt20" id="news">
			<table class="table table-bordered table-condensed table-hover">
				<thead>
					<tr class="info">
						<th class="w50"><input type="checkbox" id="selectFileAll" onclick="selectFileAll()" /></th>
						<th class="w50">序号</th>
						<th>文件名称</th>
						<th>文件大小</th>
					</tr>
				</thead>
				<tbody id="fileNews">
				</tbody>
			</table>
			<div class="col-md-12 tc">
				<button class="btn btn-windows save" type="button" onclick="save()">保存</button>
				<button class="btn btn-windows cancel" type="button" onclick="cancel()">取消</button>
			</div>
		</div>
	</body>

</html>