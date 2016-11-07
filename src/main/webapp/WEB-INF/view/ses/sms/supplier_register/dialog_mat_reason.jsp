<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>审核记录反馈</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/common.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/style.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/line-icons.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/app.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/application.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/header-v4.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/footer-v2.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/img-hover.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/page_job.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/shop.style.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/upload/upload.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/upload.js"></script>

<script type="text/javascript">
	function gotoPosition(tr, auditType, field, fieldName) {
		var content = $(tr).find("td").eq(2).text();
		content = $.trim(content);

		if (auditType == "物资-生产型专业信息") {
			switchTab(1);
			if (fieldName != "生产-资质证书") {
				showBasicReason(field, content);
			} else {
				showTableReason("cert_pro_list_tbody_id", field, content);
			}
		} else if (auditType == "物资-销售型专业信息") {
			switchTab(2);
			if (fieldName != "销售-资质证书") {
				showBasicReason(field, content);
			} else {
				showTableReason("cert_sell_list_tbody_id", field, content);
			}
		} else if(auditType == "工程-专业信息") {
			switchTab(3);
			if (fieldName == "工程-资质证书") {
				showTableReason("cert_eng_list_tbody_id", field, content);
			} else if(fieldName == "工程-资质资格证书") {
				showTableReason("aptitute_list_tbody_id", field, content);
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