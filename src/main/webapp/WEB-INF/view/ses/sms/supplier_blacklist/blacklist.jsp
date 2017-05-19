<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../../../common.jsp"%>
<script type="text/javascript">
	$(function() {
		laypage({
		 	cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
			pages: "${listSupplierBlacklists.pages}", //总页数
			skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			skip: true, //是否开启跳页
			total: "${listSupplierBlacklists.total}",
			startRow: "${listSupplierBlacklists.startRow}",
			endRow: "${listSupplierBlacklists.endRow}",
			groups: "${listSupplierBlacklists.pages}">=5?5:"${listSupplierBlacklists.pages}", //连续显示分页数
			curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			    var page = location.search.match(/page=(\d+)/);
			    return page ? page[1] : 1;
			}(), 
			jump : function(e, first) { //触发分页后的回调
				if (!first) { //一定要加此判断，否则初始时会无限刷新
					//location.href = '${pageContext.request.contextPath}/supplier_blacklist/list_supplier.html?page=' + e.curr;
					//alert(e.curr);
					$("input[name='page']").val(e.curr);
					searchSupplierBlacklist(0);
				} 
			}
		});	
	});
	function searchSupplierBlacklist(sign) {
		if (sign) {
			$("input[name='page']").val(1);
		}
		$("#search_form_id").submit();
	}
	function resetForm() {
		$("input[name='supplierName']").val("");
		$("input[name='startTime']").val("");
		$("input[name='endTime']").val("");
	}
	
	function editSupplierBlacklist() {
		var checkbox = $("input[name='checkbox']:checked");
		if (checkbox.size() != 1) {
			layer.msg("请勾选一条记录 !", {
				offset : '300px',
			});
			return;
		}
		var v = $("input[name='checkbox']:checked").parents("tr").find("td").eq(7).text();
			v = $.trim(v);
		if(v=='手动移除'){
		layer.msg("不能修改手动移除!", {
				offset : '300px',
			});
			return;
		}
		var id = checkbox.val().split(",")[0];
		$("input[name='supplierBlacklistId']").val(id);
		$("#edit_form_id").submit();
	}
	
	function operatorRemove() {
		var checkbox = $("input[name='checkbox']:checked");
		if (!checkbox.size()) {
			layer.msg("请至少勾选一条记录 !", {
				offset : '300px',
			});
			return;
		}
		var ids = "";
		var count = 0;
		checkbox.each(function() {
			var v = $(this).parents("tr").find("td").eq(7).text();
			v = $.trim(v);
			if (v == "过期") {
				count ++;
				layer.msg("已过期的不能手动移除 !", {
					offset : '300px',
				});
				return;
			} else if (v == "手动移除") {
				count ++;
				layer.msg("不能重复手动移除 !", {
					offset : '300px',
				});
				return;
			}
			if (ids) {
				ids += ",";
			}
			ids += $(this).val().split(",")[0];
		});
		if (count) {
			return;
		}
		window.location.href = "${pageContext.request.contextPath}/supplier_blacklist/operator_remove.html?ids=" + ids;
	}
	
	function checkAll(ele) {
		var flag = $(ele).prop("checked");
		$("input[name='checkbox']").each(function() {
			$(this).prop("checked", flag);
		});
	}
	
	function findLog(supplierId) {
		layer.open({
			type : 2,
			title : '供应商黑名单记录表',
			// skin : 'layui-layer-rim', //加上边框
			area : [ '1000px', '420px' ], //宽高
			offset : '50px',
			scrollbar : false,
			content : '${pageContext.request.contextPath}/blacklist_log/list.html?supplierId=' + supplierId, //url
			closeBtn : 1, //不显示关闭按钮
		});
	}
	
	function searchBlacklist() {
		var checkbox = $("input[name='checkbox']:checked");
		if (checkbox.size() != 1) {
			layer.msg("请勾选一条记录 !", {
				offset : '300px',
			});
			return;
		}
		var supplierId = checkbox.val().split(",")[1];
		findLog(supplierId);
	}
</script>

</head>

<body>
	<div class="wrapper">
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li><a href="#"> 首页</a></li>
					<li><a href="#">业务管理</a></li>
					<li><a href="#">供应商</a></li>
					<li class="active"><a href="#">供应商黑名单</a></li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<!-- 我的订单页面开始-->
		<div class="container">
			<div class="headline-v2">
				<h2>供应商黑名单列表</h2>
			</div>
		<!-- 表格开始-->
			
			<h2 class="search_detail">
				<form id="search_form_id" class="mb0" action="${pageContext.request.contextPath}/supplier_blacklist/list_blacklist.html" method="get">
					<input name="page" type="hidden" />
					<ul class="demand_list">
						<li>
							<label class="fl">供应商名称：</label>
							<span><input name="supplierName" type="text" value="${supplierName}" /></span>
						</li>
						<li>
							<label class="fl">起始时间：</label>
							<span><input type="text" name="startTime" readonly="readonly" onClick="WdatePicker()" value="${startTime}" /></span>
						</li>
						<li>
							<label class="fl">终止时间：</label>
							<span><input name="endTime" type="text" readonly="readonly" onClick="WdatePicker()" value="${endTime}" /></span>
						</li>
							<button type="button" onclick="searchSupplierBlacklist(1)" class="btn fl">查询</button>
							<button onclick="resetForm()" class="btn fl">重置</button>
					</ul>
					<div class="clear"></div>
				</form>
		  </h2>
		  <div class="col-md-12 pl20 mt10">
                <button class="btn btn-windows add" type="button" onclick="location='${pageContext.request.contextPath}/supplier_blacklist/add_supplier.html'">新增</button>
                <button class="btn btn-windows edit" type="button" onclick="editSupplierBlacklist()">修改</button>
                <button class="btn btn-windows delete" type="button" onclick="operatorRemove()">手动移除</button>
                <button class="btn btn-windows git" type="button" onclick="searchBlacklist()">历史记录</button>
            </div>

		 <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
					<thead>
						<tr>
							<th class="info w50"><input type="checkbox" onchange="checkAll(this)"></th>
							<th class="info w50">序号</th>
							<th class="info">供应商名称</th>
							<th class="info">起始时间</th>
							<th class="info">结束时间</th>
							<th class="info">处罚类型</th>
							<th class="info">发布类型</th>
							<th class="info">状态</th>
							<th class="info">列入黑名单原因</th>
						</tr>
					</thead>
					<tbody id="black_tbody_id">
						<c:forEach items="${listSupplierBlacklists.list}" var="supplierBlacklist" varStatus="vs">
							<tr class="hand">
								<td class="tc"><input id="${supplierBlacklist.supplierId}" name="checkbox" value="${supplierBlacklist.id},${supplierBlacklist.supplierId}" type="checkbox"></td>
								<td class="tc" onclick="findLog('${supplierBlacklist.supplierId}')">${vs.index + 1}</td>
								<td class="tl pl20" onclick="findLog('${supplierBlacklist.supplierId}')">${supplierBlacklist.supplierName}</td>
								<td class="tc" onclick="findLog('${supplierBlacklist.supplierId}')"><fmt:formatDate value="${supplierBlacklist.startTime}" pattern="yyyy-MM-dd"/></td>
								<td class="tc" onclick="findLog('${supplierBlacklist.supplierId}')">
									<%-- <c:if test="${supplierBlacklist.term == 3}">3个月</c:if>
									<c:if test="${supplierBlacklist.term == 6}">6个月</c:if>
									<c:if test="${supplierBlacklist.term == 12}">1年</c:if>
									<c:if test="${supplierBlacklist.term == 24}">2年</c:if>
									<c:if test="${supplierBlacklist.term == 36}">3年</c:if>
									<c:if test="${supplierBlacklist.term == 0}">永久</c:if> --%>
									<fmt:formatDate value="${supplierBlacklist.endTime}" pattern="yyyy-MM-dd"/>
								</td>
								<td class="tc" onclick="findLog('${supplierBlacklist.supplierId}')">
									<c:if test="${supplierBlacklist.punishType == 0}">警告</c:if>
									<c:if test="${supplierBlacklist.punishType == 1}">不得参与采购活动</c:if>
								</td>
								<td class="tc" onclick="findLog('${supplierBlacklist.supplierId}')">
									<c:if test="${supplierBlacklist.releaseType == 0}">
										内外网发布
									</c:if>
									<c:if test="${supplierBlacklist.releaseType == 1}">
										内网发布
									</c:if>
									<c:if test="${supplierBlacklist.releaseType == 2}">
										外网发布
									</c:if>
								</td>
								<td class="tc" onclick="findLog('${supplierBlacklist.supplierId}')">
									<c:if test="${supplierBlacklist.status == 0}">
										处罚中
									</c:if>
									<c:if test="${supplierBlacklist.status == 1}">
										过期
									</c:if>
									<c:if test="${supplierBlacklist.status == 2}">
										手动移除
									</c:if>
								</td>
								<td class="tl pl20" onclick="findLog('${supplierBlacklist.supplierId}')" title="${supplierBlacklist.reason}">
									 <c:if test="${supplierBlacklist.reason.length() > 10}">
									 	${supplierBlacklist.reason.substring(0,9)}...
                    				 </c:if>  
									 <c:if test="${supplierBlacklist.reason.length() <= 10}">
									 	${supplierBlacklist.reason}
                    				</c:if>  
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="pagediv" align="right"></div>
		</div>
	</div>
	
	<form id="edit_form_id" action="${pageContext.request.contextPath}/supplier_blacklist/add_supplier.html" method="post">
		<input name="supplierBlacklistId" type="hidden" />
	</form>
</body>
</html>
