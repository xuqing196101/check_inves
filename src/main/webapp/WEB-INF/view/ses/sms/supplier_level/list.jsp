<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp" %>
<script type="text/javascript">
	$(function() {
		laypage({
			cont : $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
			pages : "${listSuppliers.pages}", //总页数
			skin : '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			skip : true, //是否开启跳页
			total : "${listSuppliers.total}",
			startRow : "${listSuppliers.startRow}",
			endRow : "${listSuppliers.endRow}",
			groups : "${listSuppliers.pages}" >= 5 ? 5 : "${listSuppliers.pages}", //连续显示分页数
			curr : function() { //通过url获取当前页，也可以同上（pages）方式获取
				var page = location.search.match(/page=(\d+)/);
				return page ? page[1] : 1;
			}(),
			jump : function(e, first) { //触发分页后的回调
				if (!first) { //一定要加此判断，否则初始时会无限刷新
					//location.href = '${pageContext.request.contextPath}/supplier_blacklist/list_supplier.html?page=' + e.curr;
					//alert(e.curr);
					$("input[name='page']").val(e.curr);
					searchSupplierLevel(0);
				}
			}
		});
		autoSelected("level_select_id", "${level}");
	});

	function changeScore() {
		var checkbox = $("input[name='checkbox']:checked");
		if (checkbox.size() != 1) {
			layer.msg("请勾选一条记录 !", {
				//offset : '300px',//注释掉，会默认都在中间，测试IE8也正常显示
			});
			return;
		}
		var id = checkbox.val();
		var supplierName = checkbox.parents("tr").find("td").eq(2).text();
		supplierName = $.trim(supplierName);
		layer.open({
			type : 2,
			title : '添加形式名称',
			// skin : 'layui-layer-rim', //加上边框
			area : [ '700px', '370px' ], //宽高
			offset : '50px',
			scrollbar : false,
			content : '${pageContext.request.contextPath}/supplier_level/change_score.html?id=' + id + '&supplierName=' + supplierName, //url
			closeBtn : 1, //不显示关闭按钮
		});
	}

	function checkAll(ele) {
		var flag = $(ele).prop("checked");
		$("input[name='checkbox']").each(function() {
			$(this).prop("checked", flag);
		});
	}
	
	function searchSupplierLevel(sign) {
		if (sign) {
			$("input[name='page']").val(1);
		}
		$("#search_form_id").submit();
	}
	
	//重置按钮事件
	function resetForm() {
        $("#supplierName").val("");
        $("#level_select_id").val("");
	}
	
	function autoSelected(id, v) {
		if (v) {
			$("#" + id).find("option").each(function() {
				var value = $(this).val();
				if(value == v) {
					$(this).prop("selected", true);
				} else {
					$(this).prop("selected", false);
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
					<li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
					<li><a href="javascript:void(0);">支撑系统</a></li>
					<li><a href="javascript:void(0);">供应商管理</a></li>
					<li><a href="javascript:void(0);" onclick="jumppage('${pageContext.request.contextPath}/supplier_level/list.html')">供应商诚信管理</a></li>
					<%--<li class="active"><a href="javascript:void(0);">供应商诚信列表</a></li>--%>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div class="container">
			<div class="headline-v2">
				<h2>供应商诚信列表</h2>
			</div>
		<!-- 表格开始-->
			
		<h2 class="search_detail">
			<form id="search_form_id" class="mb0" action="${pageContext.request.contextPath}/supplier_level/list.html" method="post">
				<input name="page" type="hidden" />
				<ul class="demand_list">
					<li>
						<label class="fl">供应商名称：</label>
						<span><input type="text" id="supplierName" name="supplierName" value="${supplierName}"/></span>
					</li>
					<li>
						<label class="fl">等级：</label>
						<span>
							<select id="level_select_id" class="w150" name="level">
								<option selected="selected" value="">全部</option>
								<option value="1">一星级</option>
								<option value="2">二星级</option>
								<option value="3">三星级</option>
								<option value="4">四星级</option>
								<option value="5">五星级</option>
							</select>
						</span>
					</li>
						<button type="button" onclick="searchSupplierLevel(1)" class="btn fl mt1">查询</button>
						<button onclick="resetForm()" class="btn fl mt1">重置</button>
				</ul>
				<div class="clear"></div>
			</form>
		</h2>
		<div class="col-md-12 pl20 mt10">
                <button class="btn btn-windows edit" type="button" onclick="changeScore()">加/减分</button>
        </div>
		<div class="content table_box">
            <table class="table table-bordered table-condensed table-hover">
					<thead>
						<tr>
							<th class="info w50"><input type="checkbox" onchange="checkAll(this)"></th>
							<th class="info w50">序号</th>
							<th class="info" width="45%">供应商名称</th>
							<th class="info"  width="15%">企业等级</th>
							<th class="info"  width="15%">分数</th>
							<!-- <th class="info">企业类型</th> -->
							<th class="info">企业性质</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${listSuppliers.list}" var="supplier" varStatus="vs">
							<tr>
								<td class="tc"><input name="checkbox" type="checkbox" value="${supplier.id}"></td>
								<td class="tc">${vs.index + 1}</td>
								<td class="tl">${supplier.supplierName}</td>
								<td class="tc">${supplier.level}</td>
								<td class="tc">${supplier.score}</td>
								<%-- <td class="tc">
									<c:forEach items="${supplier.listSupplierTypeRelates}" var="relate">
										${relate.supplierTypeName}
									</c:forEach>
								</td> --%>
								<td class="tc">
								<c:forEach items="${data }" var="dic">
									<c:if test="${supplier.businessType==dic.id}">
										${dic.name }									
									</c:if>
								</c:forEach>
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
