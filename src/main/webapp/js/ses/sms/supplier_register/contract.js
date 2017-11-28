$(function() {
	var err = $("#err_contract_files").val();
	if (err != null && err != "") {
		layer.msg(err, {
			offset : '300px'
		});
	}
	var PRODUCT = $("#li_id_1").text();
	var SALES = $("#li_id_2").text();
	var PROJECT = $("#li_id_3").text();
	var SERVICE = $("#li_id_4").text();
	if ($.trim(PRODUCT) == "" && $.trim(SALES) == "" && $.trim(SERVICE) == "") {
		layer.alert("没有必须上传的合同信息，可以点击下一步！");
	}
	// 加载默认的页签
	if (PRODUCT == "物资-生产型合同信息") {
		loadPageOne('tab-1', 'supplier/ajaxContract.html', 'PRODUCT');
		return;
	}
	if (SALES == "物资-销售型合同信息") {
		loadPageTwo('tab-2', 'supplier/ajaxContract.html', 'SALES');
		return;
	}
	if (PROJECT == "工程合同信息") {
		// loadPageThree('tab-3','supplier/ajaxContract.html','PROJECT');
		return;
	}
	if (SERVICE == "服务合同信息") {
		loadPageFour('tab-4', 'supplier/ajaxContract.html', 'SERVICE');
		return;
	}

});

// 暂存
function saveItems() {
	layer.msg('暂存成功');
}

function next() {
	$("#items_info_form_id").submit();
}

function prev() {
	updateStep(4);
}
var index;
function loadPageOne(id, url, supplierTypeId) {
	index = layer.load(1, {
		shade : [ 0.1, '#fff' ]
	// 0.1透明度的白色背景
	});
	var supplierId = $("#supplierId").val();
	var path = globalPath + "/" + url + "?supplierId="
			+ supplierId + "&supplierTypeId=" + supplierTypeId;
	$("#tab-4").html("");
	$("#tab-2").html("");
	$("#tab-3").html("");
	$("#" + id).load(path);
	init_web_upload_in("#" + id);
}

function loadPageTwo(id, url, supplierTypeId) {
	index = layer.load(1, {
		shade : [ 0.1, '#fff' ]
	// 0.1透明度的白色背景
	});
	var supplierId = $("#supplierId").val();
	var path = globalPath + "/" + url + "?supplierId="
			+ supplierId + "&supplierTypeId=" + supplierTypeId;
	$("#tab-1").html("");
	$("#tab-4").html("");
	$("#tab-3").html("");
	$("#" + id).load(path);
	init_web_upload_in("#" + id);
}

function loadPageThree(id, url, supplierTypeId) {
	index = layer.load(1, {
		shade : [ 0.1, '#fff' ]
	// 0.1透明度的白色背景
	});
	var supplierId = $("#supplierId").val();
	var path = globalPath + "/" + url + "?supplierId="
			+ supplierId + "&supplierTypeId=" + supplierTypeId;
	$("#tab-1").html("");
	$("#tab-2").html("");
	$("#tab-4").html("");
	$("#" + id).load(path);
	init_web_upload_in("#" + id);
}

function loadPageFour(id, url, supplierTypeId) {
	index = layer.load(1, {
		shade : [ 0.1, '#fff' ]
	// 0.1透明度的白色背景
	});
	var supplierId = $("#supplierId").val();
	var path = globalPath + "/" + url + "?supplierId="
			+ supplierId + "&supplierTypeId=" + supplierTypeId;
	$("#tab-1").html("");
	$("#tab-2").html("");
	$("#tab-3").html("");
	$("#" + id).load(path);
	init_web_upload_in("#" + id);
}
sessionStorage.locationE = true;
sessionStorage.index = 5;