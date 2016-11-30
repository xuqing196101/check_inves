<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>
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
					if (${templet.name != null}
							&& ${templet.name != ""}
							|| (${templet.temType != "-请选择-"} && ${templet.temType != ""})) {
						location.href = "${pageContext.request.contextPath}/templet/search.html?page="
								+ e.curr
								+ '&name='
								+ "${templet.name}"
								+ '&temType=' + "${templet.temType}";
					} else {
						location.href = '${pageContext.request.contextPath}/templet/getAll.do?page='
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
	function view(id) {
		window.location.href = "${pageContext.request.contextPath}/templet/view.do?id="
				+ id;
	}
	function edit() {
		var id = [];
		$('input[name="chkItem"]:checked').each(function() {
			id.push($(this).val());
		});
		if (id.length == 1) {

			window.location.href = "${pageContext.request.contextPath}/templet/edit.do?id="
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
								window.location.href = "${pageContext.request.contextPath}/templet/delete.do?ids="
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
		window.location.href = "${pageContext.request.contextPath}/templet/add.do";
	}
	$(function() {
		if (${templet.temType!= null } && ${templet.temType != ""} && ${templet.temType != "-请选择-"}) {
			$("#searchType").val('${templet.temType}');
		} else {
			$("#searchType").val('-请选择-');
		}
		$("#tname").val('${templet.name}');
	});
</script>
<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="javascript:void(0)"> 首页</a>
				</li>
				<li><a href="javascript:void(0)">支撑系统</a>
				</li>
				<li><a href="javascript:void(0)">后台管理</a>
				</li>
				<li class="active"><a href="javascript:void(0)">模版管理</a>
				</li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>

	<div class="container">
		<div class="headline-v2">
			<h2>模版管理</h2>
		</div>

		<!-- 查询 -->
		<h2 class="search_detail">
			<form action="${pageContext.request.contextPath}/templet/search.html"
				method="post" enctype="multipart/form-data" class="mb0">
				<ul class="demand_list">
					<li><label class="fl">模板名称：</label>
					<span>
					<input type="text" name="name" id="tname" class="mb0" />
					</span>
					</li>
					<li><label class="fl">模板类型：</label> <span> <select
							id="searchType" name=temType class="w150">
								<option value="-请选择-">-请选择-</option>
								<option value="采购公告">采购公告</option>
								<option value="中标公告">中标公告</option>
								<option value="合同公告">合同公告</option>
								<option value="招标公告">招标公告</option>
								<option value="合同模板">合同模板</option>
						</select> </span></li>

					<button class="btn" type="submit">查询</button>
					<button type="reset" class="btn">重置</button>
				</ul>

				<div class="clear"></div>
			</form>
		</h2>
		<!-- 表格开始-->
			<div class="col-md-12 pl20 mt10">
					<button class="btn btn-windows add" type="button" onclick="add()">新增</button>
					<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
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
								<th class="info">模板类型</th>
								<th class="info">模板名称</th>
							</tr>
						</thead>
						<c:forEach items="${list.list}" var="templet" varStatus="vs">
							<tr>

								<td class="tc pointer"><input onclick="check()"
									type="checkbox" name="chkItem" value="${templet.id}" />
								</td>

								<td class="tc pointer" onclick="view('${templet.id}')">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>

								<td class="tc pointer" onclick="view('${templet.id}')">${templet.temType}</td>

								<td class="tc pointer" onclick="view('${templet.id}')">${templet.name}</td>

							</tr>
						</c:forEach>
					</table>
				<div id="pagediv" align="right"></div>
		</div>
</body>
</html>
