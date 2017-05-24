<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../../../common.jsp"%>

<script type="text/javascript">
	$(function() {
		laypage({
			cont : $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
			pages : "${listSupplierCredits.pages}", //总页数
			skin : '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			skip : true, //是否开启跳页
			total : "${listSupplierCredits.total}",
			startRow : "${listSupplierCredits.startRow}",
			endRow : "${listSupplierCredits.endRow}",
			groups : "${listSupplierCredits.pages}" >= 5 ? 5 : "${listSupplierCredits.pages}", //连续显示分页数
			curr : function() { //通过url获取当前页，也可以同上（pages）方式获取
				var page = location.search.match(/page=(\d+)/);
				return page ? page[1] : 1;
			}(),
			jump : function(e, first) { //触发分页后的回调
				if (!first) { //一定要加此判断，否则初始时会无限刷新
					//location.href = '${pageContext.request.contextPath}/supplier_blacklist/list_supplier.html?page=' + e.curr;
					//alert(e.curr);
					$("input[name='page']").val(e.curr);
					searchSupplierCredit(0);
				}
			}
		});
	});

	function addCredit() {
		layer.open({
			type : 2,
			title : '添加形式名称',
			// skin : 'layui-layer-rim', //加上边框
			area : [ '450px', '230px' ], //宽高
			offset : '100px',
			scrollbar : false,
			content : '${pageContext.request.contextPath}/supplier_credit/add_credit.html', //url
			closeBtn : 1, //不显示关闭按钮
		});
	}

	function editSupplierCredit() {
		var checkbox = $("input[name='checkbox']:checked");
		if (checkbox.size() != 1) {
			layer.msg("请勾选一条记录 !", {
				//offset : '300px',//注释掉方便提示框在IE8也显示
			});
			return;
		}
		var id = checkbox.val();
		var name = checkbox.parents("tr").find("td").eq(2).text();
		name = $.trim(name);
		layer.open({
			type : 2,
			title : '添加形式名称',
			// skin : 'layui-layer-rim', //加上边框
			area : [ '550px', '230px' ], //宽高
			offset : '100px',
			scrollbar : false,
			content : '${pageContext.request.contextPath}/supplier_credit/add_credit.html?id=' + id + '&name=' + name, //url
			closeBtn : 1, //不显示关闭按钮
		});
	}

	function checkAll(ele) {
		var flag = $(ele).prop("checked");
		$("input[name='checkbox']").each(function() {
			$(this).prop("checked", flag);
		});
	}
	function searchSupplierCredit(sign) {
		if (sign) {
			$("input[name='page']").val(1);
		}
		$("#search_form_id").submit();
	}
	
	function changeStatus() {
		var checkbox = $("input[name='checkbox']:checked");
		if (checkbox.size() != 1) {
			layer.msg("请勾选一条记录 !", {
				//offset : '200px',//当然注释掉，IE8、火狐都能方便显示
			});
			return;
		}
		var id = checkbox.val();
		var text = checkbox.parents("tr").find("td").eq(3).text();
		text = $.trim(text);
		var status = null;
		if (text == "已启用") {
			status = 0;
		} else if (text == "已停用") {
			status = 1;
		}
		window.location.href = "${pageContext.request.contextPath}/supplier_credit/update_status.html?id=" + id + "&status=" + status;
	}
	
	function deleteCredit() {
		var checkbox = $("input[name='checkbox']:checked");
		if (checkbox.size() == 0) {
			layer.msg("请至少勾选一条记录 !", {
				//offset : '200px',//、
			});
			return;
		}
		var ids = "";
		var count = 0;
		checkbox.each(function(index) {
			var value = $(this).val();
			if (index > 0) {
				ids += ",";
			}
			ids += value;
			count ++;
		});
		layer.confirm('已勾选' + count + '条, 确认删除 ？', {
			offset : '200px',
		},function(index) {
			window.location.href = "${pageContext.request.contextPath}/supplier_credit/delete.html?ids=" + ids;
			layer.close(index);
		});
	}
	
	function resetForm() {
		$("input[name='name']").val("");
	}
	
	function viewCtnt(id) {
		layer.open({
			type : 2,
			title : '诚信形式内容',
			// skin : 'layui-layer-rim', //加上边框
			area : [ '670px', '350px' ], //宽高
			offset : '100px',
			scrollbar : false,
			content : '${pageContext.request.contextPath}/supplier_credit_ctnt/list_by_credit_id.html?supplierCreditId=' + id, //url
			closeBtn : 1, //不显示关闭按钮
		});
	}
	
</script>

</head>

<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li><a href="javascript:void(0);"> 首页</a></li>
					<li><a href="javascript:void(0);">业务管理</a></li>
					<li><a href="javascript:void(0);">供应商诚信形式</a></li>
					<li class="active"><a href="javascript:void(0);">供应商诚信形式列表</a></li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<!-- 我的订单页面开始-->
		<div class="container">
			<div class="headline-v2">
				<h2>供应商诚信形式列表</h2>
			</div>
		<!-- 表格开始-->
			 <h2 class="search_detail">
				<form id="search_form_id" class="mb0" action="${pageContext.request.contextPath}/supplier_credit/list.html" method="post">
					<input name="page" type="hidden" />
					<ul class="demand_list">
						<li>
							<label class="fl">形式名称：</label>
							<span><input id="name" name="name" type="text" value="${name}" /></span>
						</li>
							<button type="button" onclick="searchSupplierCredit(1)" class="btn fl mt1">查询</button>
							<button onclick="resetForm()" class="btn fl mt1">重置</button>
					</ul>
					<div class="clear"></div>
				</form>
			</h2>
		<div class="col-md-12 pl20 mt10">
                <button class="btn btn-windows add" type="button" onclick="addCredit()">新增</button>
                <button class="btn btn-windows edit" type="button" onclick="editSupplierCredit()">修改</button>
                <button class="btn btn-windows apply" type="button" onclick="changeStatus()">启/停用</button>
                <button class="btn btn-windows delete" type="button" onclick="deleteCredit()">删除</button>
         </div>
		<div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
					<thead>
						<tr>
							<th class="info w50"><input type="checkbox" onchange="checkAll(this)"></th>
							<th class="info w50">序号</th>
							<th class="info">诚信形式名称</th>
							<th class="info w100">状态</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${listSupplierCredits.list}" var="credit" varStatus="vs">
							<tr class="hand">
								<td class="tc"><input name="checkbox" type="checkbox" value="${credit.id}"></td>
								<td class="tc" onclick="viewCtnt('${credit.id}')">${vs.index + 1}</td>
								<td class="tl" onclick="viewCtnt('${credit.id}')">${credit.name}</td>
								<td class="tc status" onclick="viewCtnt('${credit.id}')">
									<c:if test="${credit.status == 0}"><span class="label rounded-2x label-dark">已停用</span></c:if>
									<c:if test="${credit.status == 1}"><span class="label rounded-2x label-u">已启用</span></c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="pagediv" align="right"></div>
		</div>
</body>
</html>
