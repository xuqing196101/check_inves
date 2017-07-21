<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
<script type="text/javascript">
	$(function() {
		laypage({
			cont : $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
			pages : "${list.pages}", //总页数
			skin : '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			skip : true, //是否开启跳页
			total : "${list.total}",
			startRow : "${list.startRow}",
			endRow : "${list.endRow}",
			groups : "${list.pages}" >= 5 ? 5 : "${list.pages}", //连续显示分页数
			curr : function() { //通过url获取当前页，也可以同上（pages）方式获取
				var page = location.search.match(/page=(\d+)/);
				return page ? page[1] : 1;
			}(),
			jump : function(e, first) { //触发分页后的回调
				if (!first) { //一定要加此判断，否则初始时会无限刷新
					if (${dictionaryType.name != null}&& ${dictionaryType.name != ""}
							|| (${dictionaryType.code != null} && ${dictionaryType.code != ""})) {
						location.href = "${pageContext.request.contextPath}/dictionaryType/search.html?page="
								+ e.curr
								+ '&name='
								+ "${dictionaryType.name}"
								+ '&temType=' + "${dictionaryType.code}";
					} else {
						location.href = '${pageContext.request.contextPath}/dictionaryType/list.do?page='
								+ e.curr;
					}
				}
			}
		});
	});
	/** 全选全不选 */
	function selectAll() {
		var checklist = document.getElementsByName("chkItem");
		var checkAll = document.getElementById("checkAll");
		if (checkAll.checked) {
			for ( var i = 0; i < checklist.length; i++) {
				checklist[i].checked = true;
			}
		} else {
			for ( var j = 0; j < checklist.length; j++) {
				checklist[j].checked = false;
			}
		}
	}

	/** 单选 */
	function check() {
		var count = 0;
		var checklist = document.getElementsByName("chkItem");
		var checkAll = document.getElementById("checkAll");
		for ( var i = 0; i < checklist.length; i++) {
			if (checklist[i].checked == false) {
				checkAll.checked = false;
				break;
			}
			for ( var j = 0; j < checklist.length; j++) {
				if (checklist[j].checked == true) {
					checkAll.checked = true;
					count++;
				}
			}
		}
	}

	function edit() {
		var authType = "${authType}";
		if(authType != '4'){
			layer.msg("只有资源服务中心才能操作");
			return;
		} 
		var id = [];
		$('input[name="chkItem"]:checked').each(function() {
			id.push($(this).val());
		});
		if (id.length == 1) {

			window.location.href = "${pageContext.request.contextPath}/dictionaryType/edit.do?id="
					+ id;
		} else if (id.length > 1) {
			layer.alert("只能选择一个", {
				offset : [ '222px', '390px' ],
				shade : 0.01
			});
		} else {
			layer.alert("请选择需要修改的模板", {
				offset : [ '222px', '390px' ],
				shade : 0.01
			});
		}
	}
	function del() {
		var authType = "${authType}";
		if(authType != '4'){
			layer.msg("只有资源服务中心才能操作");
			return;
		} 
		var ids = [];
		$('input[name="chkItem"]:checked').each(function() {
			ids.push($(this).val());
		});
		if (ids.length > 0) {
			layer
					.confirm(
							'您确定要删除吗?',
							{
								title : '提示',
								offset : [ '222px', '360px' ],
								shade : 0.01
							},
							function(index) {
								layer.close(index);
								window.location.href = "${pageContext.request.contextPath}/dictionaryType/delete.do?ids="
										+ ids;
							});
		} else {
			layer.alert("请选择要删除的模板", {
				offset : [ '222px', '390px' ],
				shade : 0.01
			});
		}
	}
	function add() {
		var authType = "${authType}";
		if(authType != '4'){
			layer.msg("只有资源服务中心才能操作");
			return;
		} 
		window.location.href = "${pageContext.request.contextPath}/dictionaryType/add.do";
	}
	$(function() {
		$("#code").val('${dictionaryType.code}');
		$("#tname").val('${dictionaryType.name}');
	});
</script>
</head>
<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')" target="_blank"> 首页</a>
				</li>
				<li><a href="javascript:void(0)">支撑系统</a>
				</li>
				<li><a href="javascript:void(0)">数据字典</a>
				</li>
				<li class="active"><a href="javascript:jumppage('${pageContext.request.contextPath}/dictionaryType/list.html')">数据字典类型管理</a>
				</li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>

	<div class="container">
		<div class="headline-v2">
			<h2>数据字典类型管理</h2>
		</div>

		<!-- 查询 -->
		<h2 class="search_detail">
			<form action="${pageContext.request.contextPath}/dictionaryType/search.html"
				method="post" enctype="multipart/form-data" class="mb0">
				<ul class="demand_list">
					<!-- <li><label class="fl">类型编号：</label>
						<span>
							<input type="text" name="code" id="code" onkeyup="this.value=this.value.replace(/\D/g,'')" class="mb0" />
						</span>
					</li> -->
					<li><label class="fl">类型名称：</label>
						<span>
							<input type="text" name="name" id="tname" class="mb0" />
						</span>
					</li>
					<button class="btn fl mt1" type="submit">查询</button>
					<button type="reset" class="btn fl mt1">重置</button>
				</ul>

				<div class="clear"></div>
			</form>
		</h2>
		<!-- 表格开始-->
			<div class="col-md-12 pl20 mt10">
					<button class="btn btn-windows add" type="button" onclick="add()">新增</button>
					<%--<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>--%>
					<button class="btn btn-windows delete" type="button" onclick="del();">删除</button>
			</div>

		<div class="content table_box">
             <table class="table table-bordered table-condensed table-hover table-striped">
						<thead>
							<tr>
								<th class="info w30"><input id="checkAll" type="checkbox"
									onclick="selectAll()" />
								</th>
								<th class="info w50">序号</th>
								<th class="info" width="45%">类型编号</th>
								<th class="info">类型名称</th>
							</tr>
						</thead>
						<c:forEach items="${list.list}" var="dt" varStatus="vs">
							<tr>

								<td class="tc"><input onclick="check()"
									type="checkbox" name="chkItem" value="${dt.id}" />
								</td>

								<td class="tc pointer">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>

								<td class="tc pointer">${dt.code}</td>

								<td class="tl pointer">${dt.name}</td>

							</tr>
						</c:forEach>
					</table>
				<div id="pagediv" align="right"></div>
		</div>
</body>
</html>
