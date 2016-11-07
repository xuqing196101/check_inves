<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>待报项目列表</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>
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
</script>

</head>

<body>
	<div class="wrapper">
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li><a href="#"> 首页</a>
					</li>
					<li><a href="#">业务管理</a>
					</li>
					<li><a href="#">进口项目管理</a>
					</li>
					<li class="active"><a href="#">待报项目列表</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>

		<div class="container">
			<div class="headline-v2">
				<h2>待报项目列表</h2>
			</div>
		</div>


		<!-- 表格开始-->
		<div class="container">
			<div class="col-md-8">
				<button class="btn btn-windows add" type="button" onclick="location='${pageContext.request.contextPath}/supplier_stars/add.html'">新增</button>
				<button class="btn btn-windows edit" type="button" onclick="editSupplierStars()">修改</button>
				<button class="btn btn-windows edit" type="button" onclick="changeStatus()">启/停用</button>
				<button class="btn btn-windows delete" type="button" onclick="deleteStars()">删除</button>
			</div>
		</div>
		
		<div class="container">
			<div class="p10_25">
				<form id="search_form_id" class="padding-10 border1 mb0" action="${pageContext.request.contextPath}/purchaseContract/list.html" method="get">
					<input name="page" type="hidden" />
					<ul class="demand_list">
						<li class="fl">
							<label class="fl mt5">项目名称：</label>
							<span><input name="projectName" type="text" value="${projectName}" /></span>
						</li>
						<li class="fl">
							<label class="fl mt5">项目状态：</label>
							<span>
								<select id="declare_select_id" class="w150" name="isDeclare">
									<option value="0">待报项目</option>
									<option value="1">已报项目</option>
									<option value="2">批准项目</option>
								</select>
							</span>
						</li>
						<li class="fl mt1">
							<button type="button" onclick="search(1)" class="btn">查询</button>
							<button onclick="resetForm('search_form_id')" class="btn" type="button">重置</button>
						</li>
					</ul>
					<div class="clear"></div>
				</form>
			</div>
		</div>
		
		<div class="container margin-top-5">
			<div class="content padding-left-25 padding-right-25 padding-top-5">
				<table class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th class="info w50"><input type="checkbox" onchange="checkAll(this)" />
							</th>
							<th class="info w50">序号</th>
							<th class="info">项目名称</th>
							<th class="info">合同金额</th>
							<th class="info">甲方信息</th>
							<th class="info">乙方信息</th>
							<th class="info">需求部门</th>
							<th class="info">合同签订时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pager.list}" var="pc" varStatus="vs">
							<tr>
								<td class="tc"><input name="checkbox" type="checkbox" value="${pc.id}">
								</td>
								<td class="tc">${vs.index + 1}</td>
								<td class="tc">${pc.projectName}</td>
								<td class="tc">${pc.money}</td>
								<td class="tc">${pc.purchaseDepName}</td>
								<td class="tc">${pc.supplierDepName}</td>
								<td class="tc">${pc.demandSector}</td>
								<td class="tc"><fmt:formatDate value="${pc.createdAt}" pattern="yyyy-MM-dd"/></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="pagediv" align="right"></div>
		</div>
	</div>
</body>
</html>
