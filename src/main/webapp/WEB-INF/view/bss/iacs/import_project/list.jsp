<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>

<title>待报项目列表</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<%@ include file="/WEB-INF/view/common.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/supplier/js/common.js"></script>
<script type="text/javascript">
	$(function() {
		laypage({
			cont : $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
			pages : "${pager.pages}", //总页数
			skin : '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			skip : true, //是否开启跳页
			total : "${pager.total}",
			startRow : "${pager.startRow}",
			endRow : "${pager.endRow}",
			groups : "${pager.pages}" >= 5 ? 5 : "${pager.pages}", //连续显示分页数
			curr : function() { //通过url获取当前页，也可以同上（pages）方式获取
				var page = location.search.match(/page=(\d+)/);
				return page ? page[1] : 1;
			}(),
			jump : function(e, first) { //触发分页后的回调
				if (!first) { //一定要加此判断，否则初始时会无限刷新
					//location.href = '${pageContext.request.contextPath}/supplier_blacklist/list_supplier.html?page=' + e.curr;
					//alert(e.curr);
					$("input[name='page']").val(e.curr);
					search(0);
				}
			}
		});
		
		autoSelected("declare_select_id", "${isDeclare}");
		
	});
	
	function search(sign) {
		if (sign) {
			$("input[name='page']").val(1);
		}
		$("#search_form_id").submit();
	}
	
	function add() {
		var result = isEmpty("tbody_contract_id", true, "请至少勾选一条记录 !");
		if (result.ids) {
			window.location.href = "${pageContext.request.contextPath}/import_project/add.html?ids=" + result.ids;
		}
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
				<li><a href="javascript:void(0);">进口项目管理</a></li>
				<li class="active"><a href="javascript:void(0);">待报项目列表</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	
	<div class="container">
		
		<div class="headline-v2">
			<h2>合同列表</h2>
   		</div> 
		
		<!-- 搜索条件 -->
		<div class="search_detail">
			<form id="search_form_id" class="padding-10 border1 mb0" action="${pageContext.request.contextPath}/import_project/list.html" method="get">
				<ul class="demand_list">
					<li><label class="fl">项目名称：</label><span><input type="text" name="projectName" value="${projectName}"  /> </span></li>
					<li>
						<label class="fl">项目状态：</label> 
						<span class="fl"> 
							<select id="declare_select_id" name="isDeclare">
								<option value="0">待报项目</option>
								<option value="1">已报项目</option>
								<option value="2">批准项目</option>
							</select> 
						</span>
					</li>
					<button type="button" onclick="search(1)" class="btn">查询</button>
					<button onclick="resetForm('search_form_id')" class="btn" type="button">重置</button>
				</ul>
				<div class="clear"></div>
			</form>
		</div>
		
		<!-- 按钮-->
		<div class="col-md-12 col-sm-12 col-xs-12">
			<button class="btn btn-windows add" type="button" onclick="add()">物资申请</button>
		</div>
	
		<!-- 列表 -->
		<div class="content table_box">
			<table class="table table-bordered table-condensed table-hover">
				<thead>
					<tr>
						<th class="info w50"><input type="checkbox" onchange="checkAll(this, 'tbody_contract_id')" />
						</th>
						<th class="info w50">序号</th>
						<th class="info" width="25%">项目名称</th>
						<th class="info" width="10%">合同金额</th>
						<th class="info" width="15%">甲方信息</th>
						<th class="info" width="15%">乙方信息</th>
						<th class="info" width="17%">需求部门</th>
						<th class="info">合同签订时间</th>
					</tr>
				</thead>
				<tbody id="tbody_contract_id">
					<c:forEach items="${pager.list}" var="pc" varStatus="vs">
						<tr class="pointer">
							<td class="tc"><input name="checkbox" type="checkbox" value="${pc.id}">
							</td>
							<td class="tc">${vs.index + 1}</td>
							<td class="tl">${pc.projectName}</td>
							<td class="tr">${pc.money}</td>
							<td class="tl">${pc.purchaseDepName}</td>
							<td class="tl">${pc.supplierDepName}</td>
							<td class="tl">${pc.demandSector}</td>
							<td class="tc"><fmt:formatDate value="${pc.createdAt}" pattern="yyyy-MM-dd"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div id="pagediv" align="right"></div>
		</div>
	</div>
</body>
</html>
