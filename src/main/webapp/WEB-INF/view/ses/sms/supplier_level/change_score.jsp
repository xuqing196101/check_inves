<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>供应商诚信列表</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>
<script type="text/javascript">


	function checkAll(ele) {
		var flag = $(ele).prop("checked");
		$("input[name='checkbox']").each(function() {
			$(this).prop("checked", flag);
		});
	}
	
	function loadCreditCtnt(ele) {
		var supplierCreditId = $(ele).val();
		$.ajax({
			url : "${pageContext.request.contextPath}/supplier_level/find_credit_ctnt_by_credit_id.do",
			type : "post",
			data : {
				supplierCreditId : supplierCreditId
			},
			dataType : "json",
			success : function(result) {
				var html = "";
				for(var i = 0; i < result.length; i++) {
					html += "<tr>";
					html += "<td class='tc'><input name='checkbox' type='checkbox' value='"+ result[i].id +"'></td>";
					html += "<td class='tc'>"+ (i + 1) +"</td>";
					html += "<td class='tc'>"+ result[i].name +"</td>";
					html += "<td class='tc'>"+ result[i].score +"</td>";
					html += "<td class='tc'>"+ result[i].supplierCreditName +"</td>";
					html += "</tr>";
				}
				$("#tbody_id").empty();
				$("#tbody_id").append(html);
			},
		});
	}
	
	function changeScore() {
		var checkbox = $("input[name='checkbox']:checked");
		if (!checkbox.size()) {
			layer.msg("请勾选一条记录 !", {
				offset : '100px',
			});
			return;
		}
		var id = $("input[name='id']").val();
		var scores = "";
		checkbox.each(function(index) {
			var currScore = $(this).parents("tr").find("td").eq(3).text();
			currScore = $.trim(currScore);
			if (index > 0) {
				scores += ",";
			}
			scores += currScore;
		});
		$("#supplier_input_id").val(id);
		$("#scores_input_id").val(scores);
		$("#change_score_form_id").submit();
	}
</script>

</head>

<body>
	<div class="wrapper">
		<!-- 我的订单页面开始-->
		<div class="container">
			<div class="headline-v2">
				<h2>供应商诚信加/减分</h2>
			</div>
		</div>

		<div class="container">
			<div class="p10_25">
				<form class="padding-10 border1 mb0">
					<input name="id" type="hidden" value="${supplier.id}" />
					<ul class="demand_list">
						<li class="fl">
							<label class="fl mt5">供应商名称：</label>
							<span><input type="text" readonly="readonly" name="supplierName" value="${supplier.supplierName}"/></span>
						</li>
						<li class="fl">
							<label class="fl mt5">诚信形式：</label>
							<span>
								<select onchange="loadCreditCtnt(this)">
									<option selected="selected" value="">请选择</option>
									<c:forEach items="${listSupplierCredits}" var="credit">
										<option value="${credit.id}">${credit.name}</option>
									</c:forEach>
								</select>
							</span>
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
							<th class="info w50"><input type="checkbox" onchange="checkAll(this)"></th>
							<th class="info w50">序号</th>
							<th class="info">诚信形式内容名称</th>
							<th class="info">分数</th>
							<th class="info">诚信形式</th>
						</tr>
					</thead>
					<tbody id="tbody_id">
						
					</tbody>
				</table>
			</div>
		</div>
		<div class="col-md-12 tc">
			<a class="btn btn-windows reset" target="_parent" href="javascript:void(0)" onclick="changeScore()">保存</a>
			<a class="btn btn-windows reset" target="_parent" href="${pageContext.request.contextPath}/supplier_level/list.html">返回</a>
		</div>
	</div>
	<form id="change_score_form_id" action="${pageContext.request.contextPath}/supplier_level/update_score.html" target="_parent" method="post">
		<input id="supplier_input_id" type="hidden" name="id" />
		<input id="scores_input_id" type="hidden" name="scores" />
	</form>
</body>
</html>