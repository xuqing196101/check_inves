function tijiao(str) {
	var action;
	if (reqType != '') {
		if (str == "essential") {
			action = globalPath + "/supplierQuery/essential.html?reqType=analyze&address="+provinceName+"&businessNature="+businessNature+"&orgId="+orgId+"&supplierTypeIds="+supplierTypeIds;
		}
		if (str == "financial") {
			action = globalPath + "/supplierQuery/financial.html?reqType=analyze&address="+provinceName+"&businessNature="+businessNature+"&orgId="+orgId+"&supplierTypeIds="+supplierTypeIds;
		}
		if (str == "shareholder") {
			action = globalPath + "/supplierQuery/shareholder.html?reqType=analyze&address="+provinceName+"&businessNature="+businessNature+"&orgId="+orgId+"&supplierTypeIds="+supplierTypeIds;
		}
		if (str == "chengxin") {
			action = globalPath + "/supplierQuery/list.html?reqType=analyze&address="+provinceName+"&businessNature="+businessNature+"&orgId="+orgId+"&supplierTypeIds="+supplierTypeIds;
		}
		if (str == "item") {
			action = globalPath + "/supplierQuery/item.html?reqType=analyze&address="+provinceName+"&businessNature="+businessNature+"&orgId="+orgId+"&supplierTypeIds="+supplierTypeIds;
		}
		if (str == "product") {
			action = globalPath + "/supplierQuery/product.html?reqType=analyze&address="+provinceName+"&businessNature="+businessNature+"&orgId="+orgId+"&supplierTypeIds="+supplierTypeIds;
		}
		if (str == "updateHistory") {
			action = globalPath + "/supplierQuery/showUpdateHistory.html?reqType=analyze&address="+provinceName+"&businessNature="+businessNature+"&orgId="+orgId+"&supplierTypeIds="+supplierTypeIds;
		}
		if (str == "supplierType") {
			action = globalPath + "/supplierQuery/supplierType.html?reqType=analyze&address="+provinceName+"&businessNature="+businessNature+"&orgId="+orgId+"&supplierTypeIds="+supplierTypeIds;
		}
		if (str == "zizhi") {
			action = globalPath + "/supplierQuery/aptitude.html?reqType=analyze&address="+provinceName+"&businessNature="+businessNature+"&orgId="+orgId+"&supplierTypeIds="+supplierTypeIds;
		}
		if (str == "contract") {
			action = globalPath + "/supplierQuery/contract.html?reqType=analyze&address="+provinceName+"&businessNature="+businessNature+"&orgId="+orgId+"&supplierTypeIds="+supplierTypeIds;
		}
		if (str == "supplierType") {
			action = globalPath + "/supplierQuery/supplierType.html?reqType=analyze&address="+provinceName+"&businessNature="+businessNature+"&orgId="+orgId+"&supplierTypeIds="+supplierTypeIds;
		}
		if (str == "audit") {
			action = globalPath + "/supplierQuery/auditInfo.html?reqType=analyze&address="+provinceName+"&businessNature="+businessNature+"&orgId="+orgId+"&supplierTypeIds="+supplierTypeIds;
		}
        if (str == "table") {
            action = globalPath + "/supplier/show_template_upload.html?reqType=analyze&address="+provinceName+"&businessNature="+businessNature+"&orgId="+orgId+"&supplierTypeIds="+supplierTypeIds;
        }
	} else {
		if (str == "essential") {
			action = globalPath + "/supplierQuery/essential.html";
		}
		if (str == "financial") {
			action = globalPath + "/supplierQuery/financial.html";
		}
		if (str == "shareholder") {
			action = globalPath + "/supplierQuery/shareholder.html";
		}
		if (str == "chengxin") {
			action = globalPath + "/supplierQuery/list.html";
		}
		if (str == "item") {
			action = globalPath + "/supplierQuery/item.html";
		}
		if (str == "product") {
			action = globalPath + "/supplierQuery/product.html";
		}
		if (str == "updateHistory") {
			action = globalPath + "/supplierQuery/showUpdateHistory.html";
		}
		if (str == "supplierType") {
			action = globalPath + "/supplierQuery/supplierType.html";
		}
		if (str == "zizhi") {
			action = globalPath + "/supplierQuery/aptitude.html";
		}
		if (str == "contract") {
			action = globalPath + "/supplierQuery/contract.html";
		}
		if (str == "supplierType") {
			action = globalPath + "/supplierQuery/supplierType.html";
		}
		if (str == "audit") {
			action = globalPath + "/supplierQuery/auditInfo.html";
		}
        if (str == "table") {
            action = globalPath + "/supplier/show_template_upload.html";
        }
	}
	$("#form_id").attr("action", action);
	$("#form_id").submit();
}

function fanhui() {
	if (judge == 2) {
		window.location.href = globalPath + "/supplierQuery/selectByCategory.html";
	} else {
		var action;
		if (reqType != '') {
			$("#address").remove();
			action = globalPath + "/supplierQuery/readOnlyList.html?address="+provinceName+"&businessNature="+businessNature+"&orgId="+orgId+"&supplierTypeIds="+supplierTypeIds;
		} else {
			action = globalPath + "/supplierQuery/findSupplierByPriovince.html";
		}
		$("#form_back").attr("action", action);
		$("#form_back").submit();
	}
};