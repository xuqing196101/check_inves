<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>

<%@ include file="/reg_head.jsp"%>
<title>审核记录反馈</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<script type="text/javascript">
	function gotoPosition(tr, auditType) {
		var content = $(tr).find("td").eq(2).text();
		content = $.trim(content);
		if (auditType == "item_pro_page") {
			switchTab(1);
			parent.layer.tips(content, "#tree_ul_id_1");
		} else if (auditType == "item_sell_page") {
			switchTab(2);
			parent.layer.tips(content, "#tree_ul_id_2");
		} else if(auditType == "item_eng_page") {
			switchTab(3);
			parent.layer.tips(content, "#tree_ul_id_3");
		} else {
			switchTab(4);
			parent.layer.tips(content, "#tree_ul_id_4");
		}
	}

	function switchTab(num) {
		parent.$("#page_ul_id").find("li").each(function() {
			var id = $(this).attr("id");
			var n = id.charAt(id.length - 1);
			if (num == n) {
				$(this).attr("class", "active");
			} else {
				$(this).removeAttr("class");
			}
		});
		parent.$("#tab_content_div_id").find(".tab-pane").each(function() {
			var id = $(this).attr("id");
			var n = id.charAt(id.length - 1);
			if (num == n) {
				$(this).attr("class", "tab-pane fade height-200 active in");
			} else {
				$(this).attr("class", "tab-pane fade height-200");
			}
		});
	}
</script>

</head>

<body>
	<div class="col-md-12 tab-v2 job-content" style="margin-top: 10px;">
		<table id="finance_table_id" class="table table-bordered table-condensed">
			<thead>
				<tr>
					<th class="info">序号</th>
					<th class="info">审核字段</th>
					<th class="info">审核原因</th>
				</tr>
			</thead>
			<tbody id="finance_list_tbody_id">
				<c:forEach items="${list}" var="audit" varStatus="vs">
					<tr onclick="gotoPosition(this, '${audit.auditType}')" class="hand">
						<td class="tc">${vs.index + 1}</td>
						<td class="tc">${audit.auditFieldName}</td>
						<td class="tc">${audit.suggest}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>