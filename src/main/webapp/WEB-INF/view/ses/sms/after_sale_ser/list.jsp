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
		var id = checkbox.val();
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
			ids += $(this).val();
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
		var supplierId = checkbox.val();
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
					<li>
            <a href="#"> 首页</a>
          </li>
          <li>
            <a href="#">售后服务采购管理</a>
          </li>
          <li class="active">
            <a href="#">售后服务登记</a>
          </li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<!-- 我的订单页面开始-->
		<div class="container">
			<div class="headline-v2">
        <h2>售后服务登记列表</h2>
      </div>
      <h2 class="search_detail ">
      <form id="form1" action="${pageContext.request.contextPath}/after_sales/list.html" method="post" class="mb0" > 
      <ul class="demand_list">
        <li class="fl">
        <label class="fl">产品名称：</label><span><input class="span2"   type="text"></span>
        </li>
        <li class="fl">
        <label class="fl">合同名称：</label><span><input class="span2"    type="text"></span>
        </li>
        <li class="fl">
        <label class="fl">合同编号：</label><span><input class="span2"   type="text"></span>
        </li>
        <button type="button" onclick="submit()" class="btn">查询</button>
        <button type="button" onclick="chongzhi()" class="btn">重置</button>    
      </ul>
        <div class="clear"></div>
       </form>
     </h2>
      <div class="content table_box">
		  <div class="col-md-12 pl20 mt10">
                <button class="btn btn-windows add" type="button" onclick="location='${pageContext.request.contextPath}/supplier_blacklist/add_supplier.html'">新增</button>
                <button class="btn btn-windows edit" type="button" onclick="editSupplierBlacklist()">修改</button>
                <button class="btn btn-windows delete" type="button" onclick="operatorRemove()">删除</button>
            </div>

		 <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
					<thead>
						<tr>
							<th class="info w50"><input type="checkbox" onchange="checkAll(this)"></th>
							<th class="info w50">序号</th>
							<th class="info">合同编号</th>
              				<th class="info">产品名称</th>
              				<th class="info">技术参数</th>
             				<th class="info">合同金额（元）</th>
						</tr>
					</thead>
					<tbody id="black_tbody_id">
						<c:forEach items="${listSupplierBlacklists.list}" var="supplierBlacklist" varStatus="vs">
							<tr class="hand">
								<td class="tc"><input id="${supplierBlacklist.supplierId}" name="checkbox" value="${supplierBlacklist.id}" type="checkbox"></td>
								<td class="tc" onclick="findLog('${supplierBlacklist.supplierId}')">${vs.index + 1}</td>
								<td class="tl pl20" onclick="findLog('${supplierBlacklist.supplierId}')">${supplierBlacklist.supplierName}</td>
								<td class="tc" onclick="findLog('${supplierBlacklist.supplierId}')"><fmt:formatDate value="${supplierBlacklist.startTime}" pattern="yyyy-MM-dd"/></td>
								<td class="tl pl20" onclick="findLog('${supplierBlacklist.supplierId}')">${supplierBlacklist.reason}</td>
								<td class="tl pl20" onclick="findLog('${supplierBlacklist.supplierId}')">${supplierBlacklist.reason}</td>
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
