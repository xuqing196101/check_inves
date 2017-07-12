<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
        <%@ include file="/WEB-INF/view/common.jsp"%>
		<title>采购档案授权</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript">
			$(function() {
				$("#temporary").hide();
				$("#name").val("${name}");
				$("#depName").val("${depName}");
				laypage({
					cont: $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
					pages: "${purchaseList.pages}", //总页数
					skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
					skip: true, //是否开启跳页
					total: "${purchaseList.total}",
					startRow: "${purchaseList.startRow}",
					endRow: "${purchaseList.endRow}",
					groups: "${purchaseList.pages}" >= 5 ? 5 : "${purchaseList.pages}", //连续显示分页数
					curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
						var page = location.search.match(/page=(\d+)/);
						return page ? page[1] : 1;
					}(),
					jump: function(e, first) { //触发分页后的回调
						if(!first) { //一定要加此判断，否则初始时会无限刷新
							var name = "${name}";
							var depName = "${depName}";
							location.href = "${pageContext.request.contextPath }/purchaseArchive/archiveAuthorize.do?name=" + name + "&depName=" + depName + "&page=" + e.curr;
						}
					}
				});
			})

			//按条件查询
			function queryResult() {
				var name = $("#name").val();
				var depName = $("#depName option:selected").val();
				if((name == null || name == "") && (depName == null || depName == "")) {
					window.location.href = "${pageContext.request.contextPath }/purchaseArchive/archiveAuthorize.html";
					return;
				} else {
					window.location.href = "${pageContext.request.contextPath }/purchaseArchive/archiveAuthorize.do?name=" + name + "&depName=" + depName;
				}
			}

			//重置
			function resetResult() {
				$("#name").val("");
				$("#depName").val("");
			}

			//临时授权
			function auth() {
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
					window.location.href = "${pageContext.request.contextPath }/purchaseArchive/jumpToAuthorize.html?userId=" + id;
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

			//取消
			function cancel() {
				layer.closeAll();
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

			//查看某个人可以看得扫描件
			function viewArchive() {
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
					window.location.href = "${pageContext.request.contextPath }/purchaseArchive/viewArchive.do?id=" + id;
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
						<a href="javascript:jumppage('${pageContext.request.contextPath}/purchaseArchive/archiveAuthorize.html');">采购档案授权</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div class="container">
			<div class="headline-v2">
				<h2>授权管理</h2>
			</div>

			<h2 class="search_detail">
			<ul class="demand_list">
				<li>
			    	<label class="fl">姓名：</label><span><input type="text" id="name" name="name" class=""/></span>
			    </li>
		  		<li>
			    	<label class="fl">采购机构：</label>
					<span>
			    		<select id="depName" name="depName">
		  					<option value="">--请选择--</option>
                            <c:forEach items="${purchaseOrgList}" var="p">
                                <option value="${p.id}">${p.name}</option>
                            </c:forEach>
		  				</select>
			    	</span>
			    </li>
		  		<button class="btn" type="button" onclick="queryResult()">查询</button>
		  		<button class="btn" type="button" onclick="resetResult()">重置</button>
	  		</ul>
	  		<div class="clear"></div>
	  	</h2>

			<!-- 按钮开始-->
			<div class="col-md-12 pl20 mt10">
				<button class="btn" type="button" onclick="auth()">授权</button>
				<button class="btn" type="button" onclick="viewArchive()">查看</button>
			</div>

			<div class="content table_box">
				<table class="table table-bordered table-condensed table-hover">
					<thead>
						<tr class="info">
							<th class="w50"><input type="checkbox" id="selectAll" onclick="selectAll()" /></th>
							<th class="w50">序号</th>
							<th class="w160">姓名</th>
							<th class="w260">采购机构</th>
							<th class="w160">联系电话</th>
							<th>联系地址</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${purchaseList.list }" var="purchase" varStatus="vs">
							<tr class="tc">
								<td class="tc"><input type="checkbox" name="info" value="${purchase.userId }" onclick="check()" /></td>
								<td class="tl pl20">${(vs.index+1)+(purchaseList.pageNum-1)*(purchaseList.pageSize)}</td>
								<td class="tl pl20">${purchase.relName }</td>
								<td class="tl pl20">${purchase.purchaseDepName }</td>
								<td class="tl pl20">${purchase.telephone }</td>
								<td class="tl pl20">${purchase.address }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="pageDiv" align="right"></div>
		</div>
	</body>

</html>