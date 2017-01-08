<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>

<%@ include file="/reg_head.jsp"%>
<title>审核记录反馈</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<script type="text/javascript">
	function gotoPosition(tr, auditType, field, fieldName) {
		var content = $(tr).find("td").eq(2).text();
		content = $.trim(content);

		if (auditType == "mat_pro_page") {
			switchTab(1);
			if (fieldName != "生产-资质证书") {
				showBasicReason(field, content);
			} else {
				showTableReason("cert_pro_list_tbody_id", field, content);
			}
		} else if (auditType == "mat_sell_page") {
			switchTab(2);
			if (fieldName != "销售-资质证书") {
				showBasicReason(field, content);
			} else {
				showTableReason("cert_sell_list_tbody_id", field, content);
			}
		} else if(auditType == "mat_eng_page") {
			switchTab(3);
			if (fieldName == "工程-资质证书") {
				showTableReason("cert_eng_list_tbody_id", field, content);
			} else if(fieldName == "工程-资质资格证书") {
				showTableReason("aptitute_list_tbody_id", field, content);
			} else if (fieldName == "工程-注册人员登记") {
				showTableReason("reg_person_list_tbody_id", field, content);
			} else {
				showBasicReason(field, content);
			}
		} else {
			switchTab(4);
			if (fieldName != "服务-资质证书") {
				showBasicReason(field, content);
			} else {
				showTableReason("cert_se_list_tbody_id", field, content);
			}
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

	function showBasicReason(field, content) {
		var ele = parent.$("input[name='" + field + "']");
		ele.focus();
		var id = ele.attr("id");
		if (id) {
			parent.layer.tips(content, "#" + id);
		} else {
			ele.attr("id", "curr_ele_id");
			parent.layer.tips(content, "#curr_ele_id");
			ele.removeAttr("id");
		}
	}

	function showTableReason(id, name, content) {
		parent.$("#" + id).find(":checkbox").each(function() {
			if (name == $(this).val()) {
				$(this).parents("tr").attr("id", "curr_ele_id");
				$(this).focus();
				parent.layer.tips(content, "#curr_ele_id");
				$(this).parents("tr").removeAttr("id");
				return;
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
					<tr onclick="gotoPosition(this, '${audit.auditType}', '${audit.auditField}', '${audit.auditFieldName}')" class="hand">
						<td class="tc">${vs.index + 1}</td>
						<td class="tc">${audit.auditFieldName}</td>
						<td class="tc">${audit.suggest}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>