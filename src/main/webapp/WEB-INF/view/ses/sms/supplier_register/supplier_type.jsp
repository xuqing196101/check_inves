<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>

<head>
<%@ include file="/reg_head.jsp"%>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<title>供应商注册</title>
<%@ include file="/WEB-INF/view/common/validate.jsp"%>
<script src="${pageContext.request.contextPath}/public/easyui/jquery.easyui.min.js"></script>
<link href="${pageContext.request.contextPath}/public/easyui/themes/icon.css" media="screen" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/easyui/themes/default/easyui.css" media="screen" rel="stylesheet" type="text/css">
<style type="text/css">
.current {
	cursor: pointer;
}
</style>
<script type="text/javascript">
	$().ready(function() {
		$("#save_pro_form_id").validForm();
	});

	//显示生产的信息
	function product(obj) {
		if (obj == "PRODUCT") {
			$("#productId").show();
		}
	}
	//显示销售的信息
	function sales(obj) {
		if (obj == "SALES") {
			$("#salesId").show();
		}
	}
	//显示工程的信息
	function project(obj) {
		if (obj == "PROJECT") {
			$("#projectId").show();
		}
	}
	//显示服务的信息
	function services(obj) {
		if (obj == "SERVICE") {
			$("#serviceId").show();
		}
	}

	//初始化所有的tab标题
	function hideTabTitle() {
		$("#productId").hide();
		$("#salesId").hide();
		$("#projectId").hide();
		$("#serviceId").hide();
	}

	//初始化tab的标题样式
	function initTabTitleCss() {
		$("#productId").removeClass("active");
		$("#salesId").removeClass("active");
		$("#projectId").removeClass("active");
		$("#serviceId").removeClass("active");
	}

	//选中信息头
	function checks(obj) {
		var selectedArray = [];
		hideTabTitle();
		$("input[name='chkItem']:checked").each(function() {
			var value = $(this).val();
			$("#tab_div").addClass("opacity_1");
			selectedArray.push(value);
			product(value);
			sales(value);
			project(value);
			services(value)
		});

		if (selectedArray.length == 0) {
			$("#tab_div").attr("class", "container opacity_0");
		}
		var first = selectedArray[0];

		if (first != null && first != "" && first != "undefined") {
			loadTab(first);
		}
	}

	// 页签切换
	function loadTab(val) {
		initTabTitleCss();
		$("#production_div").attr("class", "tab-pane fades");
		$("#sale_div").attr("class", "tab-pane fades");
		$("#project_div").attr("class", "tab-pane fades ");
		$("#server_div").attr("class", "tab-pane fades ");
		if (val == 'PRODUCT') {
			$("#productId").addClass("active");
			$("#production_div").attr("class", "tab-pane fades active in");
		}
		if (val == 'SALES') {
			$("#salesId").addClass("active");
			$("#sale_div").attr("class", "tab-pane fades active in");
		}
		if (val == 'PROJECT') {
			$("#projectId").addClass("active");
			$("#project_div").attr("class", "tab-pane fades active in");
		}
		if (val == 'SERVICE') {
			$("#serviceId").addClass("active");
			$("#server_div").attr("class", "tab-pane fades active in");
		}
		init_web_upload();
	}

	//上一步
	function prev() {
		var id = $("#sid").val();
		window.location.href = "${pageContext.request.contextPath}/supplier/register.html?id="
				+ id;
	}

	//暂存
	function ajaxSave() {
		var id = [];
		$('input[name="chkItem"]:checked').each(function() {
			id.push($(this).val());
		});
		$("input[name='supplierTypeIds']").val(id);

		if (id.length == 0) {
			layer.msg("请选择供应商类型");
			return false;
		}
		// 保存工程地址附件信息
		var areaIds = "";
		$("#areaSelect").find("option").each(function(i, element){
			if (element.selected == true) {
				areaIds = areaIds + element.value + ",";
			}
		});
		$("#businessScope").val(areaIds);
		$.ajax({
			url : "${pageContext.request.contextPath}/supplier/saveSupplierType.do",
			type : "post",
			data : $("#save_pro_form_id").serializeArray(),
			contextType : "application/x-www-form-urlencoded",
			success : function(msg) {
				layer.msg('暂存成功');
				var data = msg.split(",");
				if (data[0] != "null" && data[0] != null) {
					$("input[name='supplierMatPro.id']").val(data[0]);
				} else {
					$("input[name='supplierMatPro.id']").val("");
				}
				if (data[1] != "null" && data[1] != null) {
					$("input[name='supplierMatSell.id']").val(data[1]);
				} else {
					$("input[name='supplierMatSell.id']").val("");
				}
				if (data[2] != "null" && data[2] != null) {
					$("input[name='supplierMatEng.id']").val(data[2]);
				} else {
					$("input[name='supplierMatEng.id']").val("");
				}
				if (data[3] != "null" && data[3] != null) {
					$("input[name='supplierMatSe.id']").val(data[3]);
				} else {
					$("input[name='supplierMatSe.id']").val("");
				}
			},
			error : function() {
				layer.msg('暂存失败!');
			}
		});
	}

	//无提示实时暂存
	function tempSave() {
		var id = [];
		$('input[name="chkItem"]:checked').each(function() {
			id.push($(this).val());
		});
		$("input[name='supplierTypeIds']").val(id);
		// 保存工程地址附件信息
		var areaIds = "";
		$("#areaSelect").find("option").each(function(i, element){
			if (element.selected == true) {
				areaIds = areaIds + element.value + ",";
			}
		});
		$("#businessScope").val(areaIds);
		$.ajax({
					url : "${pageContext.request.contextPath}/supplier/saveSupplierType.do",
					type : "post",
					data : $("#save_pro_form_id").serializeArray(),
					contextType : "application/x-www-form-urlencoded",
					success : function(msg) {
						var data = msg.split(",");
						if (data[0] != "null" && data[0] != null) {
							$("input[name='supplierMatPro.id']").val(data[0]);
						} else {
							$("input[name='supplierMatPro.id']").val("");
						}
						if (data[1] != "null" && data[1] != null) {
							$("input[name='supplierMatSell.id']").val(data[1]);
						} else {
							$("input[name='supplierMatSell.id']").val("");
						}
						if (data[2] != "null" && data[2] != null) {
							$("input[name='supplierMatEng.id']").val(data[2]);
						} else {
							$("input[name='supplierMatEng.id']").val("");
						}
						if (data[3] != "null" && data[3] != null) {
							$("input[name='supplierMatSe.id']").val(data[3]);
						} else {
							$("input[name='supplierMatSe.id']").val("");
						}
					}
				});
	}

	function next(obj) {

		var id = [];
		$('input[name="chkItem"]:checked').each(function() {
			id.push($(this).val());
		});
		var bool = false;
		var boo = false;
		for ( var i = 0; i < id.length; i++) {
			if (id[i] == 'GOODS') {
				bool = true;
			}
			if (id[i] == 'SALES' || id[i] == 'PRODUCT') {
				boo = true;
			}
		}
		var flag = true;
		$("input[name='supplierTypeIds']").val(id);
		$("input[name='flag']").val(obj);
		if (bool == true && boo != true) {
			layer.msg("请勾选产品货物类属性");
		} else {
			if (id.length > 0) {
				flag = true;
			} else {
				flag = false;
				layer.msg("请选择供应商类型");
			}
		}
		// 判断有没有勾选物资生产
		var isProCheck = false;
		var isSaleCheck = false;
		var isEngCheck = false;
		var isServerCheck = false;
		$("input[name='chkItem']").each(function(index, element) {
			if (element.value == "PRODUCT" && element.checked == true) {
				isProCheck = true;
			}
			if (element.value == "SALES" && element.checked == true) {
				isSaleCheck = true;
			}
			if (element.value == "PROJECT" && element.checked == true) {
				isEngCheck = true;
			}
			if (element.value == "SERVICE" && element.checked == true) {
				isServerCheck = true;
			}
		});
		var count = 0;
		if (isProCheck == true) {
			$("#cert_pro_list_tbody_id").find("tr").each(
					function(index, element) {
						if (element.value == "" || !isProCheck) {
							flag = false;
							layer.msg("物资生产资质证书信息不能为空! ");
						}
						count++;
					});
			$("#cert_pro_list_tbody_id").find("input[type='text']").each(
					function(index, element) {
						if (element.value == "" || !isProCheck) {
							flag = false;
							layer.msg("物资生产资质证书信息不能为空! ");
						}
						count++;
					});
			if (count == 0) {
				flag = false;
				layer.msg("物资生产资质证书信息不能为空! ");
			}
		}
		// 判断有没有勾选工程
		if (isEngCheck == true) {
			$("#cert_eng_list_tbody_id").find("input[type='text']").each(
					function(index, element) {
						if (element.value == "" || !isEngCheck) {
							flag = false;
							layer.msg("工程资质（认证）证书信息不能为空! ");
						}
					});
			$("#aptitute_list_tbody_id").find("input[type='text']").each(
					function(index, element) {
						if (element.value == "" || !isEngCheck) {
							flag = false;
							layer.msg("工程资质证书详细信息不能为空! ");
						}
					});
		}
		$("input[name$='expEndDate']").each(
				function() {
					var startDate = $(this).parent().prev().children(
							"input[name$='expStartDate']").val();
					if ($(this).val() != "" && startDate != ""
							&& $(this).val() <= startDate) {
						flag = false;
						layer.msg("结束时间应大于开始时间!");
					}
				});
		if (flag == true) {
			$("#save_pro_form_id").submit();
		}

	}

	function openRegPerson() {
		var matEngId = $("input[name='supplierMatEng.id']").val();
		var supplierId = $("input[name='id']").val();
		var id;
		$.ajax({
			url : "${pageContext.request.contextPath}/supplier/getUUID.do",
			async : false,
			success : function(data) {
				id = data;
			}
		});
		var certPersonNumber = $("#certPersonNumber").val();
		$("#reg_person_list_tbody_id")
				.append(
						"<tr>"
								+ "<td class='tc'><input type='checkbox' value='" + id + "' class='border0'/><input type='hidden' name='supplierMatEng.listSupplierRegPersons[" + certPersonNumber + "].id' value='" + id + "'></td>"
								+ "<td class='tc'><input type='text' class='border0' onblur='tempSave()' name='supplierMatEng.listSupplierRegPersons["
								+ certPersonNumber
								+ "].regType'/> </td>"
								+ "<td class='tc'><input type='text' class='border0' onblur='tempSave()' name='supplierMatEng.listSupplierRegPersons["
								+ certPersonNumber + "].regNumber'/> </td>"
								+ "</tr>");
		certPersonNumber++;
		$("#certPersonNumber").val(certPersonNumber);
	}

	function deleteRegPerson() {
		var checkboxs = $("#reg_person_list_tbody_id")
				.find(":checkbox:checked");
		var regPersonIds = "";
		var supplierId = $("input[name='id']").val();
		$(checkboxs).each(function(index) {
			if (index > 0) {
				regPersonIds += ",";
			}
			regPersonIds += $(this).val();
		});
		var size = checkboxs.length;
		if (size > 0) {
			layer
					.confirm(
							"已勾选" + size + "条记录, 确定删除 !",
							{
								offset : '200px',
								scrollbar : false,
							},
							function(index) {
								window.location.href = "${pageContext.request.contextPath}/supplier_reg_person/delete_reg_person.html?regPersonIds="
										+ regPersonIds
										+ "&supplierId="
										+ supplierId;
								layer.close(index);

							});
		} else {
			layer.alert("请至少勾选一条记录 !", {
				offset : '200px',
				scrollbar : false,
			});
		}
	}

	function openAptitute() {
		var matEngId = $("input[name='supplierMatEng.id']").val();
		var supplierId = $("input[name='id']").val();
		var certAptNumber = $("#certAptNumber").val();
		$.ajax({
			url : "${pageContext.request.contextPath}/supplier/addAptCert.do",
			async : false,
			dataType : "html",
			data : {
				"number" : certAptNumber
			},
			success : function(data) {
				$("#aptitute_list_tbody_id").append(data);
				init_web_upload();
			}
		});
		certAptNumber++;
		$("#certAptNumber").val(certAptNumber);
	}

	function deleteAptitute() {
		var checkboxs = $("#aptitute_list_tbody_id").find(":checkbox:checked");
		var aptituteIds = "";
		var supplierId = $("input[name='id']").val();
		$(checkboxs).each(function(index) {
			if (index > 0) {
				aptituteIds += ",";
			}
			aptituteIds += $(this).val();
		});
		var size = checkboxs.length;
		if (size > 0) {
			layer.confirm(
				"已勾选" + size + "条记录, 确定删除 !",
				{
					offset : '200px',
					scrollbar : false,
				},
				function(index) {
					window.location.href = "${pageContext.request.contextPath}/supplier_aptitute/delete_aptitute.html?aptituteIds="
							+ aptituteIds
							+ "&supplierId="
							+ supplierId;
					layer.close(index);
	
				});
		} else {
			layer.alert("请至少勾选一条记录 !", {
				offset : '200px',
				scrollbar : false,
			});
		}
	}

	/** 打开物资生产证书 */
	var proIndex;

	function openCertPro() {
		var matProId = $("input[name='supplierMatPro.id']").val();
		var supplierId = $("input[name='id']").val();
		var certProNumber = $("#certProNumber").val();
		$.ajax({
			url : "${pageContext.request.contextPath}/supplier/addProductCert.do",
			async : false,
			dataType : "html",
			data : {
				"number" : certProNumber
			},
			success : function(data) {
				$("#cert_pro_list_tbody_id").append(data);
				init_web_upload();
			}
		});
		certProNumber++;
		$("#certProNumber").val(certProNumber);
	}

	/** 供应商保存专业生产信息 */
	function savePro(jsp) {
		$("input[name='jsp']").val(jsp);
		$("#save_pro_form_id").submit();
	}

	function checkAll(ele, id) {
		var flag = $(ele).prop("checked");
		$("#" + id).find("input:checkbox").each(function() {
			$(this).prop("checked", flag);
		});

	}

	function deleteCertPro() {
		var checkboxs = $("#cert_pro_list_tbody_id").find(":checkbox:checked");
		var certProIds = "";
		var supplierId = $("input[name='id']").val();
		$(checkboxs).each(function(index) {
			if (index > 0) {
				certProIds += ",";
			}
			certProIds += $(this).val();
		});
		var size = checkboxs.length;
		if (size > 0) {
			layer
					.confirm(
							"已勾选" + size + "条记录, 确定删除 !",
							{
								offset : '200px',
								scrollbar : false,
							},
							function(index) {
								window.location.href = "${pageContext.request.contextPath}/supplier_cert_pro/delete_cert_pro.html?certProIds="
										+ certProIds
										+ "&supplierId="
										+ supplierId;
								layer.close(index);

							});
		} else {
			layer.alert("请至少勾选一条记录 !", {
				offset : '200px',
				scrollbar : false,
			});
		}
	}

	function openCertSell() {
		var matSellId = $("input[name='supplierMatSell.id']").val();
		var supplierId = $("input[name='id']").val();
		var certSaleNumber = $("#certSaleNumber").val();
		$.ajax({
			url : "${pageContext.request.contextPath}/supplier/addSaleCert.do",
			async : false,
			dataType : "html",
			data : {
				"number" : certSaleNumber
			},
			success : function(data) {
				$("#cert_sell_list_tbody_id").append(data);
				init_web_upload();
			}
		});
		certSaleNumber++;
		$("#certSaleNumber").val(certSaleNumber);
	}

	function deleteCertSell() {
		var checkboxs = $("#cert_sell_list_tbody_id").find(":checkbox:checked");
		var certSellIds = "";
		var supplierId = $("input[name='id']").val();
		$(checkboxs).each(function(index) {
			if (index > 0) {
				certSellIds += ",";
			}
			certSellIds += $(this).val();
		});
		var size = checkboxs.length;
		if (size > 0) {
			layer
					.confirm(
							"已勾选" + size + "条记录, 确定删除 !",
							{
								offset : '200px',
								scrollbar : false,
							},
							function(index) {
								window.location.href = "${pageContext.request.contextPath}/supplier_cert_sell/delete_cert_sell.html?certSellIds="
										+ certSellIds
										+ "&supplierId="
										+ supplierId;
								layer.close(index);

							});
		} else {
			layer.alert("请至少勾选一条记录 !", {
				offset : '200px',
				scrollbar : false,
			});
		}
	}

	function openCertSe() {
		var matSeId = $("input[name='supplierMatSe.id']").val();
		var supplierId = $("input[name='id']").val();
		var certSeNumber = $("#certSeNumber").val();
		$.ajax({
			url : "${pageContext.request.contextPath}/supplier/addSeCert.do",
			async : false,
			dataType : "html",
			data : {
				"number" : certSeNumber
			},
			success : function(data) {
				$("#cert_se_list_tbody_id").append(data);
				init_web_upload();
			}
		});
		certSeNumber++;
		$("#certSeNumber").val(certSeNumber);
	}

	function deleteCertSe() {
		var checkboxs = $("#cert_se_list_tbody_id").find(":checkbox:checked");
		var certSeIds = "";
		var supplierId = $("input[name='id']").val();
		$(checkboxs).each(function(index) {
			if (index > 0) {
				certSeIds += ",";
			}
			certSeIds += $(this).val();
		});
		var size = checkboxs.length;
		if (size > 0) {
			layer
					.confirm(
							"已勾选" + size + "条记录, 确定删除 !",
							{
								offset : '200px',
								scrollbar : false,
							},
							function(index) {
								window.location.href = "${pageContext.request.contextPath}/supplier_cert_se/delete_cert_se.html?certSeIds="
										+ certSeIds
										+ "&supplierId="
										+ supplierId;
								layer.close(index);

							});
		} else {
			layer.alert("请至少勾选一条记录 !", {
				offset : '200px',
				scrollbar : false,
			});
		}
	}
	
	//控制省市附件的显示与隐藏
	function disAreaFile(obj){
		$(obj).find("option").each(function(i,element){
			if (element.selected == true) {
				$("#area_" + element.value).show();
				init_web_upload();
			} else {
				$("#area_" + element.value).hide();
			}
		});
	}

	$(function() {
		window.onload = function() {
			$(".textbox").css({
				"border": "0px",
			});
			var isHavingConAchi = "${currSupplier.supplierMatEng.isHavingConAchi}";
			if (isHavingConAchi == '1') {
				$("#conAchiDiv").show();
			} else {
				$("#conAchiDiv").hide();
			}

			// 工程类
			var businessScope = "${currSupplier.supplierMatEng.businessScope}";
			$("#areaSelect").find("option").each(function(i, element){
				if (businessScope.indexOf(element.value) != -1) {
					element.selected = true;
					$("#area_" + element.value).show();
					init_web_upload();
				} else {
					element.selected = false;
					$("#area_" + element.value).hide();
				}
			});
			
			$("select[id^='certType_']").each(function(i, element){
				getAptLevel($(element));
			});
			
			$("input").bind("blur", tempSave);
			$("select").bind("change", tempSave);
			var pro = "${pro}";
			var server = "${server}";
			var sale = "${sale}";
			var project = "${project}";
			var msg = "";
			if (pro == "false") {
				msg = msg + "物资-生产专业信息、";
			}
			if (sale == "false") {
				msg = msg + "物资-销售专业信息、";
			}
			if (project == "false") {
				msg = msg + "工程专业信息、";
			}
			if (server == "false") {
				msg = msg + "服务专业信息、";
			}
			if (msg != "") {
				var msg = msg.substring(0, msg.length - 1);
				layer.msg(msg + "没有通过校验!");
			}
			var checkeds = $("#supplierTypes").val();
			if (checkeds != "") {
				$("#tab_div").show();
				$("#tab_content_div_id").show();
			}
			var arrays = checkeds.split(",");
			var checkedArray = [];
			var checkBoxAll = $("input[name='chkItem']");
			if (arrays.length > 0) {
				initTabTitleCss();
				for ( var i = 0; i < arrays.length; i++) {
					$.each(checkBoxAll,
							function(j, checkbox) {
								//获取复选框的value属性
								var checkValue = $(checkbox).val();
								if (arrays[i] == checkValue) {
									$(checkbox).attr("checked", true);
									if (arrays[i] != 'PROJECT') {
										$("#project_div").attr("class",
												"dis_none fades ");
									}
									if (arrays[i] != 'PRODUCT') {
										$("#production_div").attr("class",
												"dis_none fades ");
									}
									if (arrays[i] != 'SALES') {
										$("#sale_div").attr("class",
												"dis_none fades ");
									}
									if (arrays[i] != 'SERVICE') {
										$("#server_div").attr("class",
												"dis_none fades ");
									}
									if (arrays[i] == 'PRODUCT') {
										$("#productId").show();
										$("#production_div").attr("class",
												"fades active in");
									} else if (arrays[i] == 'SALES') {
										$("#salesId").show();
										$("#sale_div").attr("class",
												"fades active in");
									} else if (arrays[i] == 'PROJECT') {
										$("#projectId").show();
										$("#project_div").attr("class",
												"fades active in");
									} else if (arrays[i] == 'SERVICE') {
										$("#serviceId").show();
										$("#server_div").attr("class",
												"fades active in");
									}
									checkedArray.push(arrays[i]);
								}
							});
				}
			}

			if (checkedArray.length == 0) {
				$("#tab_div").attr("class", "container opacity_0");
			} else {
				$("#tab_div").attr("class", "container opacity_1");
			}
			var first = checkedArray[0];
			if (first != null && first != "" && first != "undefined") {
				loadTab(first);
			}
		}
	});

	function openCertEng() {
		var matEngId = $("input[name='supplierMatEng.id']").val();
		var supplierId = $("input[name='id']").val();
		var certEngNumber = $("#certEngNumber").val();
		$.ajax({
			url : "${pageContext.request.contextPath}/supplier/addEngCert.do",
			async : false,
			dataType : "html",
			data : {
				"number" : certEngNumber
			},
			success : function(data) {
				$("#cert_eng_list_tbody_id").append(data);
				init_web_upload();
			}
		});
		certEngNumber++;
		$("#certEngNumber").val(certEngNumber);
	}

	function deleteCertEng() {
		var checkboxs = $("#cert_eng_list_tbody_id").find(":checkbox:checked");
		var certEngIds = "";
		var supplierId = $("input[name='id']").val();
		$(checkboxs).each(function(index) {
			if (index > 0) {
				certEngIds += ",";
			}
			certEngIds += $(this).val();
		});
		var size = checkboxs.length;
		if (size > 0) {
			layer.confirm(
				"已勾选" + size + "条记录, 确定删除 !",
				{
					offset : '200px',
					scrollbar : false,
				},
				function(index) {
					window.location.href = "${pageContext.request.contextPath}/supplier_cert_eng/delete_cert_eng.html?certEngIds="
							+ certEngIds
							+ "&supplierId="
							+ supplierId;
					layer.close(index);
	
				});
		} else {
			layer.alert("请至少勾选一条记录 !", {
				offset : '200px',
				scrollbar : false,
			});
		}
	}

	function seach(obj) {
		var id = $(obj).next().val();
		var sid = $("#sid").val();
		if (id.length > 0) {
			layer.open({
				type : 2,
				title : '查询产品分类',
				// skin : 'layui-layer-rim', //加上边框
				area : [ '800px', '500px' ], //宽高
				offset : '100px',
				scrollbar : false,
				content : '${pageContext.request.contextPath}/supplier/category.html?id='
						+ id + '&&sid=' + sid, //url
				closeBtn : 1, //不显示关闭按钮
			});
		} else {
			layer.alert("请至少勾选一条记录 !", {
				offset : '200px',
				scrollbar : false,
			});
		}
	}

	function name() {
		var id = [];
		$('input[name="chkItem"]:checked').each(function() {
			id.push($(this).next().val());
		});
		return id;
	}

	function valus() {
		var id = [];
		$('input[name="chkItem"]:checked').each(function() {
			id.push($(this).val());
		});
		return id;
	}

	function checknums(obj) {
		var vals = $(obj).val();
		var reg = /^\d+\.?\d*$/;
		if (!reg.exec(vals)) {
			$(obj).val("");
			$("#err_fund").text("数字非法");
		} else {
			$("#err_fund").text();
			$("#err_fund").empty();
		}
	}
	
	//之前的代码，管用
	function getAptLevel_annotation(obj){
		var typeId = $(obj).val();
		if (typeId != null && typeId != "") {
			$(obj).parent().next().next().next().find("select").html("");
			$.ajax({
				url: "${pageContext.request.contextPath}/supplier/getAptLevel.do",
				data: {
					"typeId": typeId,
				},
				dataType: "json",
				success: function(data){
					for(var i = 0; i < data.length; i++){
						var optionDOM = "";
						if ($(obj).next().val() != "" && $(obj).next().val() == data[i].id) {
							optionDOM = "<option value='" + data[i].id + "' selected='selected'>" + data[i].name + "</option>";
						} else {
							var optionDOM = "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
						}
						$(obj).parent().next().next().next().find("select").append(optionDOM);
					}
				}
			});
		}
	}
	
	//资质类型下拉框改变时调用的方法
	function getAptLevel(obj){
		if(obj instanceof jQuery) {
			var typeId = obj.val();
			if (typeId != null && typeId != "") {
				obj.parent().next().next().next().find("select").html("");
				$.ajax({
					url: "${pageContext.request.contextPath}/supplier/getAptLevel.do",
					data: {
						"typeId": typeId,
					},
					dataType: "json",
					success: function(data){
						var easyuiData = [];
						for(var i = 0; i < data.length; i++){
							var optionDOM = "";
							var cur_str = "";
							if (obj.parent().children(".forSelectId").val() != "" && obj.parent().children(".forSelectId").val() == data[i].id) {
								//optionDOM = "<option value='" + data[i].id + "' selected='selected'>" + data[i].name + "</option>";
								cur_str = {label : data[i].id,value : data[i].name,selected : true};
							} else {
								//var optionDOM = "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
								cur_str = {label : data[i].id,value : data[i].name};
							}
							
							easyuiData.push(cur_str);
							//obj.parent().next().next().next().find("select").append(optionDOM);
						}
						$("#certGrade_select").combobox({
							valueField: 'label',
							textField: 'value',
							data: easyuiData
						});
						
					}
				});
			}
		} else {
			var typeId = $(obj).val();
			if (typeId != null && typeId != "") {
				$(obj).parent().next().next().next().find("select").html("");
				$.ajax({
					url: "${pageContext.request.contextPath}/supplier/getAptLevel.do",
					data: {
						"typeId": typeId,
					},
					dataType: "json",
					success: function(data){
						for(var i = 0; i < data.length; i++){
							var optionDOM = "";
							if ($(obj).next().val() != "" && $(obj).next().val() == data[i].id) {
								optionDOM = "<option value='" + data[i].id + "' selected='selected'>" + data[i].name + "</option>";
							} else {
								var optionDOM = "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
							}
							$(obj).parent().next().next().next().find("select").append(optionDOM);
						}
					}
				});
			}
		}
	}

	// 高亮不通过的字段
	function displayReason(auditField, auditType) {
		var supplierId = "${currSupplier.id}";
		$.ajax({
			url : "${pageContext.request.contextPath}/supplierAudit/displayReason.do",
			data : {
				"supplierId" : supplierId,
				"auditField" : auditField,
				"auditType" : auditType
			},
			dataType : json,
			success : function(data) {
				layer.msg(data.suggest, {
					offset : '200px'
				});
			}
		});
	}

	//显示不通过的理由
	function errorMsg(auditField, auditType) {
		var supplierId = "${currSupplier.id}";
		$.ajax({
			url : "${pageContext.request.contextPath}/supplier/audit.html",
			data : {
				"supplierId" : supplierId,
				"auditField" : auditField,
				"auditType" : auditType
			},
			dataType : "json",
			success : function(data) {
				layer.msg("不通过理由：" + data.suggest, {
					offset : '200px'
				});
			}
		});
	}

	function disConAchi() {
		if ($("#isHavingConAchi").val() == '1') {
			$("#conAchiDiv").show();
			init_web_upload();
			$("#conAchi").attr("required", true);
		} else {
			$("#conAchiDiv").hide();
			$("#conAchi").attr("required", false);
		}
	}
	
	sessionStorage.locationB=true;
	sessionStorage.index=2;
</script>

</head>

<body>
	<div class="wrapper">
	<%@include file="supplierNav.jsp" %>
		<!-- 项目戳开始 -->
		<!-- <div class="container clear margin-top-30">
			<h2 class="padding-20 mt40 ml30">
				<span class="new_step current fl"><i class="">1</i> 						<div class="line"></div> <span class="step_desc_01">用户名密码</span> </span> <span class="new_step current fl"><i class="">2</i>
					<div class="line"></div> <span class="step_desc_02">基本信息</span> </span> <span
					class="new_step current fl"><i class="">2</i>
					<div class="line"></div> <span class="step_desc_01">供应商类型</span> </span> <span
					class="new_step fl"><i class="">3</i>
					<div class="line"></div> <span class="step_desc_02">产品类别</span> </span> <span
					class="new_step fl"><i class="">4</i>
					<div class="line"></div> <span class="step_desc_01">资质文件维护</span> </span>
				<span class="new_step  fl"><i class="">5</i>
					<div class="line"></div> <span class="step_desc_02">销售(承包)合同</span>
				</span> <span class="new_step fl"><i class="">6</i>
					<div class="line"></div> <span class="step_desc_01">采购机构</span> </span> <span
					class="new_step fl"><i class="">7</i>
					<div class="line"></div> <span class="step_desc_02">承诺书和申请表</span>
				</span> <span class="new_step fl"><i class="">8</i> <span
					class="step_desc_01">提交</span> </span>
				<div class="clear"></div>
			</h2>
		</div> -->
		<!--详情开始-->
		<div class="sevice_list container mt60">
			<h2>供应商类型</h2>
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="col-md-5 col-sm-6 col-xs-6 title tr"></div>
				<div class="col-md-7 col-sm-6 col-xs-12 service_list">
					<c:forEach items="${wlist }" var="obj">
						<span
							<c:if test="${fn:contains(typePageField,obj.id)}">style="color: red;" onmouseover="errorMsg('${obj.id }','supplierType_page')"</c:if>><input
							type="checkbox" name="chkItem" onclick="checks(this)"
							value="${obj.code}" /> ${obj.name }</span>
					</c:forEach>
					<c:forEach items="${supplieType }" var="obj">
						<span
							<c:if test="${fn:contains(typePageField,obj.id)}">style="color: red;" onmouseover="errorMsg('${obj.id }','supplierType_page')"</c:if>><input
							type="checkbox" name="chkItem" onclick="checks(this)"
							value="${obj.code }" />${obj.name } </span>
					</c:forEach>

				</div>
			</div>
		</div>

		<div class="container opacity_0" id="tab_div">
			<div class="magazine-page">
				<div class="col-md-12 col-sm-12 col-xs-12 p0 tab-v2 job-content">
					<ul id="page_ul_id" class="nav nav-tabs supplier_tab">
						<li id="productId" style="display:none;"><a
							aria-expanded="true"
							onclick="init_web_upload_in('#production_div')"
							href="#production_div" data-toggle="tab" class=" f18">物资-生产型专业信息</a>
						</li>
						<li id="salesId" style="display:none;"><a
							aria-expanded="false" onclick="init_web_upload_in('#sale_div')"
							href="#sale_div" data-toggle="tab" class="f18">物资-销售型专业信息</a></li>
						<li id="projectId" style="display:none;"><a
							aria-expanded="false"
							onclick="init_web_upload_in('#project_div')" href="#project_div"
							data-toggle="tab" class="f18">工程专业信息</a></li>
						<li id="serviceId" style="display:none;"><a
							aria-expanded="false" onclick="init_web_upload_in('#server_div')"
							href="#server_div" data-toggle="tab" class="f18">服务专业信息</a></li>
					</ul>

					<div style="margin-top: 40px; position:relative;">
						<form id="save_pro_form_id"
							action="${pageContext.request.contextPath}/supplier/perfect_professional.html"
							method="post">
							<input type="hidden" name="id" id="sid"
								value="${currSupplier.id}" /> <input type="hidden" name="flag" />
							<input type="hidden" name="defaultPage" value="${defaultPage}" />
							<div id="tab_content_div_id"
								class="tab-content p0 bgwhite border0 tab_repair">
								<!-- 物资生产型专业信息 -->

								<div class="tab-pane fades" id="production_div">
									<div class=" ">
										<h2 class="list_title">物资-生产型专业信息</h2>
										<ul class="list-unstyled f14 overflow_h">
											<input type="hidden" name="supplierMatPro.id"
												value="${currSupplier.supplierMatEng.id}" />
											<input type="hidden" name="supplierMatPro.supplierId"
												value="${currSupplier.id}" />

											<fieldset
												class="col-md-12 col-sm-12 col-xs-12 border_font mt20">
												<legend>产品研发能力 </legend>

												<li class="col-md-3 col-sm-6 col-xs-12"><span
													class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i
														class="red">*</i> 技术人员数量比例(%)：</span>
													<div
														class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.scaleTech"
															required value="${currSupplier.supplierMatPro.scaleTech}"
															<c:if test="${fn:contains(proPageField,'scaleTech')}">style="border: 1px solid red;" onmouseover="errorMsg('scaleTech','mat_pro_page')"</c:if> />
														<span class="add-on cur_point">i</span> <span
															class="input-tip">不能为空</span>
														<div class="cue">${stech }</div>
														<div class="cue">
															<sf:errors path="supplierMatPro.scaleTech" />
														</div>
													</div></li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span
													class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i
														class="red">*</i> 高级技术人员数量比例(%)：</span>
													<div
														class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.scaleHeightTech"
															required
															value="${currSupplier.supplierMatPro.scaleHeightTech}"
															<c:if test="${fn:contains(proPageField,'scaleHeightTech')}">style="border: 1px solid red;" onmouseover="errorMsg('scaleHeightTech','mat_pro_page')"</c:if> />
														<span class="add-on cur_point">i</span> <span
															class="input-tip">不能为空</span>
														<div class="cue">${height }</div>
														<div class="cue">
															<sf:errors path="supplierMatPro.scaleHeightTech" />
														</div>
													</div></li>

												<li class="col-md-3 col-sm-6 col-xs-12"><span
													class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i
														class="red">*</i> 研发部门名称：</span>
													<div
														class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.researchName"
															required maxlength="20"
															value="${currSupplier.supplierMatPro.researchName}"
															<c:if test="${fn:contains(proPageField,'researchName')}">style="border: 1px solid red;" onmouseover="errorMsg('researchName','mat_pro_page')"</c:if> />
														<span class="add-on cur_point">i</span> <span
															class="input-tip">不能为空</span>
														<div class="cue">${reName }</div>
														<div class="cue">
															<sf:errors path="supplierMatPro.researchName" />
														</div>
													</div></li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span
													class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i
														class="red">*</i> 研发部门人数：</span>
													<div
														class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.totalResearch"
															required onkeyup="checknums(this)"
															value="${currSupplier.supplierMatPro.totalResearch}"
															<c:if test="${fn:contains(proPageField,'totalResearch')}">style="border: 1px solid red;" onmouseover="errorMsg('totalResearch','mat_pro_page')"</c:if> />
														<span class="add-on cur_point">i</span> <span
															class="input-tip">不能为空，且为数字</span>
														<div class="cue">${tRe }</div>
														<div class="cue">
															<sf:errors path="supplierMatPro.totalResearch" />
														</div>
													</div></li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span
													class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i
														class="red">*</i> 研发部门负责人：</span>
													<div
														class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.researchLead"
															required maxlength="20"
															value="${currSupplier.supplierMatPro.researchLead}"
															<c:if test="${fn:contains(proPageField,'researchLead')}">style="border: 1px solid red;" onmouseover="errorMsg('researchLead','mat_pro_page')"</c:if> />
														<span class="add-on cur_point">i</span> <span
															class="input-tip">不能为空</span>
														<div class="cue">${leader }</div>
														<div class="cue">
															<sf:errors path="supplierMatPro.researchLead" />
														</div>
													</div></li>
												<li class="col-md-12 col-xs-12 col-sm-12 mb25"><span
													class="col-md-12 col-xs-12 col-sm-12 padding-left-5">
														承担国家军队科研项目：</span>
													<div class="col-md-12 col-xs-12 col-sm-12 p0">
														<textarea class="col-md-12 col-xs-12 col-sm-12 h80"
															maxlength="1000" name="supplierMatPro.countryPro"
															<c:if test="${fn:contains(proPageField,'countryPro')}">style="border: 1px solid red;" onmouseover="errorMsg('countryPro','mat_pro_page')"</c:if>>${currSupplier.supplierMatPro.countryPro}</textarea>
														<div class="cue">
															<sf:errors path="supplierMatPro.countryPro" />
														</div>
													</div></li>
												<li class="col-md-12 col-xs-12 col-sm-12 mb25"><span
													class="col-md-12 col-xs-12 col-sm-12 padding-left-5">
														获得国家军队科技奖项：</span>
													<div class="col-md-12 col-xs-12 col-sm-12 p0">
														<textarea class="col-md-12 col-xs-12 col-sm-12 h80"
															maxlength="1000" name="supplierMatPro.countryReward"
															<c:if test="${fn:contains(proPageField,'countryReward')}">style="border: 1px solid red;" onmouseover="errorMsg('countryReward','mat_pro_page')"</c:if>>${currSupplier.supplierMatPro.countryReward}</textarea>
														<div class="cue">
															<sf:errors path="supplierMatPro.countryReward" />
														</div>
													</div></li>
											</fieldset>
										</ul>

										<div class="col-md-12 col-sm-12 col-xs-12 border_font mt20">
											<span class="font_line"><font class="red">*</font> 资质证书信息 </span>
											<div class="col-md-12 col-sm-12 col-xs-12 mb10 p0">
												<button type="button" class="btn btn-windows add"
													onclick="openCertPro()">新增</button>
												<button type="button" class="btn btn-windows delete"
													onclick="deleteCertPro()">删除</button>
												<span class="red">${cert_pro }</span>
											</div>
											<div class="col-md-12 col-xs-12 col-sm-12 over_scroll p0">
												<table
													class="table table-bordered table-condensed mt5 table_wrap left_table table_input">
													<thead>
														<tr>
															<th class="info"><input type="checkbox"
																onchange="checkAll(this, 'cert_pro_list_tbody_id')" />
															</th>
															<th class="info" style="width: 120px">资质证书名称</th>
															<th class="info">证书编号</th>
															<th class="info">资质等级</th>
															<th class="info">发证机关或机构</th>
															<th class="info">有效期（起始时间）</th>
															<th class="info">有效期（结束时间）</th>
															<th class="info">证书状态</th>
															<th class="info w200">证书图片（可上传多张）</th>
														</tr>
													</thead>
													<tbody id="cert_pro_list_tbody_id">
														<c:set var="certProNumber" value="0" />
														<c:forEach
															items="${currSupplier.supplierMatPro.listSupplierCertPros}"
															var="certPro" varStatus="vs">
															<tr
																<c:if test="${fn:contains(proPageField,certPro.id)}"> onmouseover="errorMsg('${certPro.id}','mat_pro_page')"</c:if>>
																<td class="tc"
																	<c:if test="${fn:contains(proPageField,certPro.id)}">style="border: 1px solid red;" </c:if>>
																	<input type="checkbox" class="border0"
																	value="${certPro.id}" /> <input type="hidden"
																	required="required"
																	name="supplierMatPro.listSupplierCertPros[${certProNumber}].id"
																	value="${certPro.id}" class="mt5 border0">
																	<div class="cue">
																		<sf:errors
																			path="supplierMatPro.listSupplierCertPros[${certProNumber}].id" />
																	</div></td>
																<td class="tc"
																	<c:if test="${fn:contains(proPageField,certPro.id)}">style="border: 1px solid red;" </c:if>><input
																	required="required" type="text"
																	name="supplierMatPro.listSupplierCertPros[${certProNumber}].name"
																	value="${certPro.name}" class="border0" /></td>
																<td class="tc"
																	<c:if test="${fn:contains(proPageField,certPro.id)}">style="border: 1px solid red;" </c:if>><input
																	required="required" type="text"
																	name="supplierMatPro.listSupplierCertPros[${certProNumber}].code"
																	value="${certPro.code}" class="border0" /></td>
																<td class="tc"
																	<c:if test="${fn:contains(proPageField,certPro.id)}">style="border: 1px solid red;" </c:if>><input
																	required="required" type="text"
																	name="supplierMatPro.listSupplierCertPros[${certProNumber}].levelCert"
																	value="${certPro.levelCert}" class="border0" /></td>
																<td class="tc"
																	<c:if test="${fn:contains(proPageField,certPro.id)}">style="border: 1px solid red;" </c:if>><input
																	required="required" type="text"
																	name="supplierMatPro.listSupplierCertPros[${certProNumber}].licenceAuthorith"
																	value="${certPro.licenceAuthorith}" class="border0" />
																</td>
																<td class="tc"
																	<c:if test="${fn:contains(proPageField,certPro.id)}">style="border: 1px solid red;" </c:if>>
																	<input type="text" required="required"
																	readonly="readonly" onClick="WdatePicker()"
																	name="supplierMatPro.listSupplierCertPros[${certProNumber}].expStartDate"
																	value="<fmt:formatDate value="${certPro.expStartDate}" pattern="yyyy-MM-dd "/>"
																	class="border0" /></td>
																<td class="tc"
																	<c:if test="${fn:contains(proPageField,certPro.id)}">style="border: 1px solid red;" </c:if>>
																	<input type="text" required="required"
																	name="supplierMatPro.listSupplierCertPros[${certProNumber}].expEndDate"
																	onClick="WdatePicker()" readonly="readonly"
																	value="<fmt:formatDate value="${certPro.expEndDate}" pattern="yyyy-MM-dd "/>"
																	class="border0" /></td>
																<td class="tc"
																	<c:if test="${fn:contains(proPageField,certPro.id)}">style="border: 1px solid red;" </c:if>><input
																	required="required" type="text"
																	name="supplierMatPro.listSupplierCertPros[${certProNumber}].mot"
																	value="${certPro.mot}" class="border0" /></td>
																<td class="tc"
																	<c:if test="${fn:contains(proPageField,certPro.id)}">style="border: 1px solid red;" </c:if>>
																	<u:upload
																		singleFileSize="${properties['file.picture.upload.singleFileSize']}"
																		exts="${properties['file.picture.type']}"
																		id="pro_up_${certProNumber}" multiple="true"
																		businessId="${certPro.id}"
																		typeId="${supplierDictionaryData.supplierProCert}"
																		sysKey="${sysKey}" auto="true" /> <u:show
																		showId="pro_show_${certProNumber}"
																		businessId="${certPro.id}"
																		typeId="${supplierDictionaryData.supplierProCert}"
																		sysKey="${sysKey}" /></td>
															</tr>
															<c:set var="certProNumber" value="${certProNumber + 1}" />
														</c:forEach>
													</tbody>
												</table>
												<input type="hidden" id="certProNumber"
													value=${certProNumber}>
											</div>
										</div>
									</div>
								</div>
								<!-- 物资销售型专业信息 -->
								<div class="tab-pane fades" id="sale_div">
									<h2 class="list_title">物资-销售专业信息</h2>
									<ul class="list-unstyled">
										<input type="hidden" name="supplierMatSell.id"
											value="${currSupplier.supplierMatSell.id}" />
										<input type="hidden" name="supplierMatSell.supplierId"
											value="${currSupplier.id}" />
									</ul>

									<div class="col-md-12 col-sm-12 col-xs-12 border_font mt20">
										<span class="font_line"> 资质证书信息 </span>
										<div class="col-md-12 col-sm-12 col-xs-12 p0">
											<button type="button" class="btn" onclick="openCertSell()">新增</button>
											<button type="button" class="btn" onclick="deleteCertSell()">删除</button>
											<span class="red">${sale_cert }</span>
										</div>
										<div class="col-md-12 col-sm-12 col-xs-12 over_scroll p0">
											<table id="share_table_id"
												class="table table-bordered table-condensed mt5 table_input left_table table_wrap">
												<thead>
													<tr>
														<th class="info"><input type="checkbox"
															onchange="checkAll(this, 'cert_sell_list_tbody_id')" />
														</th>
														<th class="info">资质证书名称</th>
														<th class="info">证书编号</th>
														<th class="info">资质等级</th>
														<th class="info">发证机关或机构</th>
														<th class="info">有效期（起始时间）</th>
														<th class="info">有效期（结束时间）</th>
														<th class="info">证书状态</th>
														<th class="info w200">证书图片（可上传多张）</th>
													</tr>
												</thead>
												<tbody id="cert_sell_list_tbody_id">
													<c:set var="certSaleNumber" value="0" />
													<c:forEach
														items="${currSupplier.supplierMatSell.listSupplierCertSells}"
														var="certSell" varStatus="vs">
														<tr
															<c:if test="${fn:contains(sellPageField,certSell.id)}"> onmouseover="errorMsg('${certSell.id}','mat_sell_page')"</c:if>>
															<td class="tc"
																<c:if test="${fn:contains(sellPageField,certSell.id)}">style="border: 1px solid red;" </c:if>>
																<input type="checkbox" value="${certSell.id}"
																class="border0" /> <input type="hidden"
																required="required"
																name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].id"
																value="${certSell.id}" class="border0"></td>
															<td class="tc"
																<c:if test="${fn:contains(sellPageField,certSell.id)}">style="border: 1px solid red;" </c:if>><input
																required="required" type="text"
																name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].name"
																value="${certSell.name}" class="border0" />
															</td>
															<td class="tc"
																<c:if test="${fn:contains(sellPageField,certSell.id)}">style="border: 1px solid red;" </c:if>><input
																required="required" type="text"
																name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].code"
																value="${certSell.code}" class="border0" />
															</td>
															<td class="tc"
																<c:if test="${fn:contains(sellPageField,certSell.id)}">style="border: 1px solid red;" </c:if>><input
																required="required" type="text"
																name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].levelCert"
																value="${certSell.levelCert}" class="border0" />
															</td>
															<td class="tc"
																<c:if test="${fn:contains(sellPageField,certSell.id)}">style="border: 1px solid red;" </c:if>><input
																required="required" type="text"
																name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].licenceAuthorith"
																value="${certSell.licenceAuthorith}" class="border0" />
															</td>
															<td class="tc"
																<c:if test="${fn:contains(sellPageField,certSell.id)}">style="border: 1px solid red;" </c:if>>
																<input type="text" readonly="readonly"
																required="required" onClick="WdatePicker()"
																name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].expStartDate"
																value="<fmt:formatDate value="${certSell.expStartDate}" pattern="yyyy-MM-dd "/>"
																class="border0" /></td>
															<td class="tc"
																<c:if test="${fn:contains(sellPageField,certSell.id)}">style="border: 1px solid red;" </c:if>>
																<input type="text" readonly="readonly"
																required="required" onClick="WdatePicker()"
																name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].expEndDate"
																value="<fmt:formatDate value="${certSell.expEndDate}" pattern="yyyy-MM-dd "/>"
																class="border0" /></td>
															<td class="tc"
																<c:if test="${fn:contains(sellPageField,certSell.id)}">style="border: 1px solid red;" </c:if>><input
																required="required" type="text"
																name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].mot"
																value="${certSell.mot}" class="border0" />
															</td>
															<td class="tc"
																<c:if test="${fn:contains(sellPageField,certSell.id)}">style="border: 1px solid red;" </c:if>>
																<div class="w200 fl">
																	<u:upload
																		singleFileSize="${properties['file.picture.upload.singleFileSize']}"
																		exts="${properties['file.picture.type']}"
																		id="sale_up_${certSaleNumber}" multiple="true"
																		businessId="${certSell.id}"
																		typeId="${supplierDictionaryData.supplierSellCert}"
																		sysKey="${sysKey}" auto="true" />
																	<u:show showId="sale_show_${certSaleNumber}"
																		businessId="${certSell.id}"
																		typeId="${supplierDictionaryData.supplierSellCert}"
																		sysKey="${sysKey}" />
																</div></td>
														</tr>
														<c:set var="certSaleNumber" value="${certSaleNumber + 1}" />
													</c:forEach>
												</tbody>
											</table>
											<input type="hidden" id="certSaleNumber"
												value=${certSaleNumber}>
										</div>
									</div>
								</div>

								<!-- 工程专业信息 -->
								<div class="tab-pane fades" id="project_div">
									<h2 class="list_title">工程专业信息</h2>

									<!--   <div class="col-md-5 title"><span class="star_red fl">*</span>工程专业信息：</div> -->
									<input type="hidden" name="supplierMatEng.id"
										value="${currSupplier.supplierMatEng.id }" /> <input
										type="hidden" name="supplierMatEng.supplierId"
										value="${currSupplier.id}" />

									<fieldset
										class="col-md-12 col-sm-12 col-xs-12 border_font mt10">
										<legend> 保密工程业绩 </legend>
										<ul class="list-unstyled overflow_h">
											<li class="col-md-3 col-sm-6 col-xs-12 pl10"><span
												class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i
													class="red">*</i> 是否有国家或军队保密工程业绩</span>
												<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
													<select name="supplierMatEng.isHavingConAchi"
														id="isHavingConAchi" onchange="disConAchi()"
														<c:if test="${fn:contains(audit,'isHavingConAchi')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('isHavingConAchi')"</c:if>>
														<option value="0"
															<c:if test="${currSupplier.supplierMatEng.isHavingConAchi == '0'}">selected</c:if>>无</option>
														<option value="1"
															<c:if test="${currSupplier.supplierMatEng.isHavingConAchi == '1'}">selected</c:if>>有</option>
													</select>
												</div></li>
											<li class="col-md-3 col-sm-6 col-xs-12"><span
												class="col-md-12 col-xs-12 col-sm-12 padding-left-5 white">操作</span>
												<div class="col-md-12 col-xs-12 col-sm-12 p0 mb25 h30">

												</div></li>
											<li class="col-md-3 col-sm-6 col-xs-12"><span
												class="col-md-12 col-xs-12 col-sm-12 padding-left-5 white">操作</span>
												<div class="col-md-12 col-xs-12 col-sm-12 p0 mb25 h30">

												</div></li>
											<li class="col-md-3 col-sm-6 col-xs-12"><span
												class="col-md-12 col-xs-12 col-sm-12 padding-left-5 white">操作</span>
												<div class="col-md-12 col-xs-12 col-sm-12 p0 mb25 h30">

												</div></li>
											<div id="conAchiDiv">
												<li class="col-md-3 col-sm-6 col-xs-12 pl10"><span
													class="col-md-12 col-sm-12 col-xs-12 padding-left-5"
													<c:if test="${fn:contains(engPageField,'supplierConAch')}">style="border: 1px solid red;" onmouseover="errorMsg('supplierConAch','mat_eng_page')"</c:if>><i
														class="red">*</i> 承包合同主要页及保密协议：</span>
													<div
														class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<u:upload
															singleFileSize="${properties['file.picture.upload.singleFileSize']}"
															businessId="${currSupplier.id}" sysKey="${sysKey}"
															typeId="${supplierDictionaryData.supplierConAch}"
															exts="${properties['file.picture.type']}" id="conAch_up"
															multiple="true" auto="true" />
														<u:show showId="conAch_show"
															businessId="${currSupplier.id}" sysKey="${sysKey}"
															typeId="${supplierDictionaryData.supplierConAch}" />
														<div class="cue">${err_conAch}</div>
													</div>
													</li>
												<li class="col-md-12 col-xs-12 col-sm-12 mb25"><span
													class="col-md-12 col-xs-12 col-sm-12 padding-left-5">
														<i class="red">* </i>国家或军队保密工程业绩：</span>
													<div class="col-md-12 col-xs-12 col-sm-12 p0">
														<textarea class="col-md-12 col-xs-12 col-sm-12 h80"
															maxlength="1000" id="conAchi"
															<c:if test="${currSupplier.supplierMatEng.isHavingConAchi == '1'}">required="required"</c:if>
															name="supplierMatEng.confidentialAchievement"
															<c:if test="${fn:contains(engPageField,'confidentialAchievement')}">style="border: 1px solid red;" onmouseover="errorMsg('confidentialAchievement','mat_eng_page')"</c:if>>${currSupplier.supplierMatEng.confidentialAchievement}</textarea>
														<div class="cue">
															<sf:errors path="supplierMatEng.confidentialAchievement" />
														</div>
													</div></li>
											</div>
										</ul>
									</fieldset>

									<fieldset
										class="col-md-12 col-sm-12 col-xs-12 border_font mt10">
										<legend> 承揽业务范围：省级行政区对应合同主要页 （体现甲乙双方盖章及工程名称、地点的相关页）</legend>
										<div class="ml20">
											省、直辖市：
										    <select multiple="multiple" size="5" id="areaSelect" onchange="disAreaFile(this)">
										    	<c:forEach items="${rootArea}" var="area" varStatus="st">
										    	  	<option value="${area.id}">${area.name}</option>
										    	</c:forEach>
										    </select>
										</div>
										<ul class="list-unstyled overflow_h">
											<input type="hidden" name="supplierMatEng.businessScope" id="businessScope" value="${currSupplier.supplierMatEng.businessScope}">
											<c:forEach items="${rootArea}" var="area" varStatus="st">
												<li class="col-md-3 col-sm-6 col-xs-12 pl10" id="area_${area.id}">
													<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" <c:if test="${fn:contains(engPageField,area.name)}">style="border: 1px solid red;" onmouseover="errorMsg('${area.name}','mat_eng_page')"</c:if>>${area.name}</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" maxcount="5" businessId="${currSupplier.id}_${area.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierProContract}" exts="${properties['file.picture.type']}" id="conAch_up_${st.index+1}" multiple="true" auto="true" />
														<u:show showId="area_show_${st.index+1}" businessId="${currSupplier.id}_${area.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierProContract}" />
														<div class="cue">${area.errInfo}</div>
													</div>
												</li>
											</c:forEach>
										</ul>
									</fieldset>

									<div class="col-md-12 col-sm-12 col-xs-12 border_font mt20">
										<span class="font_line">取得注册资质的人员信息 </span>
										<div class="fl col-md-12 col-xs-12 col-sm-12 p0">
											<button type="button" class="btn" onclick="openRegPerson()">新增</button>
											<button type="button" class="btn" onclick="deleteRegPerson()">删除</button>
											<span class="red">${eng_persons }</span>
										</div>
										<div
											class="col-md-12 col-xs-12 col-sm-12 p0 over_scroll clear">
											<table
												class="table table-bordered table-condensed mt5 table_input left_table table_wrap">
												<thead>
													<tr>
														<th class="info"><input type="checkbox"
															onchange="checkAll(this, 'reg_person_list_tbody_id')" />
														</th>
														<th class="info">注册资格名称</th>
														<th class="info">注册人姓名</th>
													</tr>
												</thead>
												<tbody id="reg_person_list_tbody_id">
													<c:set var="certPersonNumber" value="0" />
													<c:forEach
														items="${currSupplier.supplierMatEng.listSupplierRegPersons}"
														var="regPerson" varStatus="vs">
														<tr
															<c:if test="${fn:contains(engPageField,regPerson.id)}"> onmouseover="errorMsg('${regPerson.id}','mat_eng_page')"</c:if>>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,regPerson.id)}">style="border: 1px solid red;" </c:if>>
																<input type="checkbox" class="border0"
																value="${regPerson.id}" /> <input type="hidden"
																required="required"
																name="supplierMatEng.listSupplierRegPersons[${certPersonNumber}].id"
																value="${regPerson.id}"></td>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,regPerson.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" required="required" class="border0"
																name="supplierMatEng.listSupplierRegPersons[${certPersonNumber}].regType"
																value="${regPerson.regType}" />
															</td>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,regPerson.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" required="required" class="border0"
																name="supplierMatEng.listSupplierRegPersons[${certPersonNumber}].regNumber"
																value="${regPerson.regNumber}" />
															</td>
														</tr>
														<c:set var="certPersonNumber"
															value="${certPersonNumber + 1}" />
													</c:forEach>
												</tbody>
											</table>
											<input type="hidden" id="certPersonNumber"
												value="${certPersonNumber}">
										</div>
									</div>

									<!--  <h2 class="count_flow">供应商工程资质资格证书信息  </h2> -->
									<div class="col-md-12 col-sm-12 col-xs-12 border_font mt20">
										<span class="font_line"><font class="red">*</font> 供应商资质（认证）证书信息</span>
										<div class="fl col-md-12 col-xs-12 col-sm-12 p0">
											<button type="button" class="btn" onclick="openCertEng()">新增</button>
											<button type="button" class="btn" onclick="deleteCertEng()">删除</button>
											<span class="red">${eng_cert}</span>
										</div>
										<div
											class="clear over_scroll col-md-12 col-xs-12 col-sm-12 p0">
											<table
												class="table table-bordered table-condensed mt5 table_input left_table">
												<thead>
													<tr class="space_nowrap">
														<th class="info"><input type="checkbox"
															onchange="checkAll(this, 'cert_eng_list_tbody_id')" />
														</th>
														<th class="info">证书名称</th>
														<th class="info">证书编号</th>
														<th class="info">资质等级</th>
														<th class="info">发证机关或机构</th>
														<th class="info">
															<div class="w120">发证日期</div></th>
														<th class="info minw100">证书有效期截止日期</th>
														<th class="info">证书状态</th>
														<th class="info w200">证书图片（可上传多张）</th>
													</tr>
												</thead>
												<tbody id="cert_eng_list_tbody_id">
													<c:set var="certEngNumber" value="0" />
													<c:forEach
														items="${currSupplier.supplierMatEng.listSupplierCertEngs}"
														var="certEng" varStatus="vs">
														<tr
															<c:if test="${fn:contains(engPageField,certEng.id)}"> onmouseover="errorMsg('${certEng.id}','mat_eng_page')"</c:if>>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,certEng.id)}">style="border: 1px solid red;" </c:if>>
																<input type="checkbox" class="border0"
																value="${certEng.id}" /> <input type="hidden"
																name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].id"
																value="${certEng.id}"></td>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,certEng.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" required="required" class="border0"
																name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].certType"
																value="${certEng.certType}" />
															</td>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,certEng.id)}">style="border: 1px solid red;" </c:if>><input
																class="w120 border0" required="required" type="text"
																name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].certCode"
																value="${certEng.certCode}" />
															</td>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,certEng.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" required="required" class="border0"
																name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].certMaxLevel"
																value="${certEng.certMaxLevel}" />
															</td>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,certEng.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" required="required" class="border0"
																name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].licenceAuthorith"
																value="${certEng.licenceAuthorith}" />
															</td>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,certEng.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" required="required" class="border0"
																readonly="readonly" onClick="WdatePicker()"
																name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].expStartDate"
																value="<fmt:formatDate value="${certEng.expStartDate}" pattern="yyyy-MM-dd "/>" />
															</td>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,certEng.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" required="required" class="border0"
																readonly="readonly" onClick="WdatePicker()"
																name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].expEndDate"
																value="<fmt:formatDate value="${certEng.expEndDate}"/>"
																pattern="yyyy-MM-dd" />
															</td>
															<td class="tc" <c:if test="${fn:contains(engPageField,certEng.id)}">style="border: 1px solid red;" </c:if>>
																<input type="text" required="required" class="border0" name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].certStatus" value="${certEng.certStatus}" />
															</td>
															<td class="tc">
																<div class="w200 fl">
																	<u:upload
																		singleFileSize="${properties['file.picture.upload.singleFileSize']}"
																		exts="${properties['file.picture.type']}"
																		id="eng_up_${certEngNumber}" multiple="true"
																		businessId="${certEng.id}"
																		typeId="${supplierDictionaryData.supplierEngCert}"
																		sysKey="${sysKey}" auto="true" />
																	<u:show showId="eng_show_${certEngNumber}"
																		businessId="${certEng.id}"
																		typeId="${supplierDictionaryData.supplierEngCert}"
																		sysKey="${sysKey}" />
																</div></td>
														</tr>
														<c:set var="certEngNumber" value="${certEngNumber + 1}" />
													</c:forEach>
												</tbody>
											</table>
											<input type="hidden" id="certEngNumber"
												value="${certEngNumber}">
										</div>
									</div>

									<!-- 	    <h2 class="count_flow">供应商资质资格信息   </h2> -->
									<div class="col-md-12 col-sm-12 col-xs-12 border_font mt20">
										<span class="font_line"><font class="red">*</font> 供应商资质证书详细信息 </span>
										<div class="col-md-12 col-md-12 col-xs-12 col-sm-12 p0">
											<button type="button" class="btn" onclick="openAptitute()">新增</button>
											<button type="button" class="btn" onclick="deleteAptitute()">删除</button>
											<span class="red">${eng_aptitutes }</span>
										</div>
										<div
											class="over_scroll clear col-md-12 col-xs-12 col-sm-12 p0">
											<table
												class="table table-bordered table-condensed mt5 table_input left_table">
												<thead>
													<tr class="space_nowrap">
														<th class="info"><input type="checkbox"
															onchange="checkAll(this, 'aptitute_list_tbody_id')" />
														</th>
														<th class="info w150">证书名称</th>
														<th class="info w150">证书编号</th>
														<th class="info w200">资质类型</th>
														<th class="info">资质序列</th>
														<th class="info">专业类别</th>
														<th class="info w200">资质等级</th>
														<th class="info">是否主项资质</th>
													</tr>
												</thead>
												<tbody id="aptitute_list_tbody_id">
													<c:set var="certAptNumber" value="0" />
													<c:forEach
														items="${currSupplier.supplierMatEng.listSupplierAptitutes}"
														var="aptitute" varStatus="vs">
														<tr
															<c:if test="${fn:contains(engPageField,aptitute.id)}"> onmouseover="errorMsg('${aptitute.id}','mat_eng_page')"</c:if>>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,aptitute.id)}">style="border: 1px solid red;" </c:if>>
																<input type="checkbox" class="border0"
																value="${aptitute.id}" /> <input type="hidden"
																name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].id"
																value="${aptitute.id}"></td>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,aptitute.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" required="required" class="border0"
																name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].certName"
																value="${aptitute.certName}" />
															</td>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,aptitute.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" required="required" class="border0"
																name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].certCode"
																value="${aptitute.certCode}" />
															</td>
															<td class="tc" <c:if test="${fn:contains(engPageField,aptitute.id)}">style="border: 1px solid red;" </c:if>>
																 <!-- 
																<select id="certType_${certAptNumber}" name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].certType" class="w100p border0" onchange="getAptLevel(this)">
																	<c:forEach items="${typeList}" var="type">
																		<option value="${type.id}" <c:if test="${aptitute.certType eq type.id}">selected</c:if>>${type.name}</option>
																	</c:forEach>
																</select> -->
																<select title="cnjewfn" id="certType_${certAptNumber}" class="w100p border0" name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].certType" style="width:200px;border: none;">   
																    <c:forEach items="${typeList}" var="type">
																		<option value="${type.id}" <c:if test="${aptitute.certType eq type.id}">selected</c:if>>${type.name}</option>
																	</c:forEach>
																</select>
																
																<input type="hidden" class="forSelectId" value="${aptitute.aptituteLevel}">
															</td>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,aptitute.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" required="required" class="border0"
																name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].aptituteSequence"
																value="${aptitute.aptituteSequence}" />
															</td>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,aptitute.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" required="required" class="border0"
																name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].professType"
																value="${aptitute.professType}" />
															</td>
															<td class="tc" <c:if test="${fn:contains(engPageField,aptitute.id)}">style="border: 1px solid red;" </c:if>>
																<!-- 
																<select name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].aptituteLevel" class="w100p border0" onchange="tempSave()"></select>
																 -->
																<select id="certGrade_select" title="cnjewfn" name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].aptituteLevel" class="w100p border0" onchange="tempSave()" style="width:200px;border: none;">
																	
																</select>
																<script type="text/javascript">
																	$("select[title='cnjewfn']").each(function() {
																		var $obj = $(this);
																		$obj.combobox({
																			onChange : function(newValue,oldValue) {
																				getAptLevel($obj);
																			},
																		});
																	});
																</script>
															</td>
															<td class="tc"
																<c:if test="${fn:contains(engPageField,aptitute.id)}">style="border: 1px solid red;" </c:if>>
																<select
																name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].isMajorFund"
																class="w100p border0">
																	<option value="1"
																		<c:if test="${aptitute.isMajorFund==1}"> selected="selected"</c:if>>是</option>
																	<option value="0"
																		<c:if test="${aptitute.isMajorFund==0}"> selected="selected"</c:if>>否</option>
															</select></td>
														</tr>
														<c:set var="certAptNumber" value="${certAptNumber + 1}" />
													</c:forEach>
												</tbody>
											</table>
											<input type="hidden" id="certAptNumber"
												value="${certAptNumber}">
										</div>
									</div>
								</div>

								<div class="tab-pane fades" id="server_div">

									<h2 class="list_title">服务专业信息</h2>
									<ul class="list-unstyled">
										<input type="hidden" name="supplierMatSe.id"
											value="${currSupplier.supplierMatSe.id}" />
										<input type="hidden" name="supplierMatSe.supplierId"
											value="${currSupplier.id}" />
									</ul>

									<div class="col-md-12 col-sm-12 col-xs-12 border_font mt20">
										<span class="font_line"> 资质证书信息 </span>
										<div class="col-md-12 col-xs-12 col-sm-12 p0">
											<button type="button" class="btn" onclick="openCertSe()">新增</button>
											<button type="button" class="btn" onclick="deleteCertSe()">删除</button>
											<span class="red">${fw_cert }</span>
										</div>
										<div class="col-md-12 col-xs-12 col-sm-12 over_scroll p0">
											<table id="share_table_id"
												class="table table-bordered table-condensed mt5 table_wrap left_table table_input">
												<thead>
													<tr>
														<th class="info"><input type="checkbox"
															onchange="checkAll(this, 'cert_se_list_tbody_id')" />
														</th>
														<th class="info">资质证书名称</th>
														<th class="info">证书编号</th>
														<th class="info">资质等级</th>
														<th class="info">发证机关或机构</th>
														<th class="info">有效期（起始时间）</th>
														<th class="info">有效期（结束时间）</th>
														<th class="info">证书状态</th>
														<th class="info w200">证书图片（可上传多张）</th>
													</tr>
												</thead>
												<tbody id="cert_se_list_tbody_id">
													<c:set var="certSeNumber" value="0"></c:set>
													<c:forEach
														items="${currSupplier.supplierMatSe.listSupplierCertSes}"
														var="certSe" varStatus="vs">
														<tr
															<c:if test="${fn:contains(servePageField,certSe.id)}"> onmouseover="errorMsg('${certSe.id}','mat_serve_page')"</c:if>>
															<td class="tc"
																<c:if test="${fn:contains(servePageField,certSe.id)}">style="border: 1px solid red;" </c:if>>
																<input type="checkbox" class="border0"
																value="${certSe.id}" /> <input type="hidden"
																required="required"
																name="supplierMatSe.listSupplierCertSes[${certSeNumber}].id"
																value="${certSe.id}"></td>
															<td class="tc"
																<c:if test="${fn:contains(servePageField,certSe.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" required="required" class="border0"
																name="supplierMatSe.listSupplierCertSes[${certSeNumber}].name"
																value="${certSe.name}" />
															</td>
															<td class="tc"
																<c:if test="${fn:contains(servePageField,certSe.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" required="required" class="border0"
																name="supplierMatSe.listSupplierCertSes[${certSeNumber}].code"
																value="${certSe.code}" />
															</td>
															<td class="tc"
																<c:if test="${fn:contains(servePageField,certSe.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" required="required" class="border0"
																name="supplierMatSe.listSupplierCertSes[${certSeNumber}].levelCert"
																value="${certSe.levelCert}" />
															</td>
															<td class="tc"
																<c:if test="${fn:contains(servePageField,certSe.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" required="required" class="border0"
																name="supplierMatSe.listSupplierCertSes[${certSeNumber}].licenceAuthorith"
																value="${certSe.licenceAuthorith}" />
															</td>
															<td class="tc"
																<c:if test="${fn:contains(servePageField,certSe.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" required="required" class="border0"
																readonly="readonly" onClick="WdatePicker()"
																name="supplierMatSe.listSupplierCertSes[${certSeNumber}].expStartDate"
																value="<fmt:formatDate value="${certSe.expStartDate}" pattern="yyyy-MM-dd "/>" />
															</td>
															<td class="tc"
																<c:if test="${fn:contains(servePageField,certSe.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" required="required" class="border0"
																readonly="readonly" onClick="WdatePicker()"
																name="supplierMatSe.listSupplierCertSes[${certSeNumber}].expEndDate"
																value="<fmt:formatDate value="${certSe.expEndDate}" pattern="yyyy-MM-dd "/>" />
															</td>
															<td class="tc"
																<c:if test="${fn:contains(servePageField,certSe.id)}">style="border: 1px solid red;" </c:if>><input
																type="text" required="required" class="border0"
																name="supplierMatSe.listSupplierCertSes[${certSeNumber}].mot"
																value="${certSe.mot}" />
															</td>
															<td class="tc"
																<c:if test="${fn:contains(servePageField,certSe.id)}">style="border: 1px solid red;" </c:if>>
																<u:upload
																	singleFileSize="${properties['file.picture.upload.singleFileSize']}"
																	exts="${properties['file.picture.type']}"
																	id="se_up_${certSeNumber}" multiple="true"
																	businessId="${certSe.id}"
																	typeId="${supplierDictionaryData.supplierServeCert}"
																	sysKey="${sysKey}" auto="true" /> <u:show
																	showId="se_show_${certSeNumber}"
																	businessId="${certSe.id}"
																	typeId="${supplierDictionaryData.supplierServeCert}"
																	sysKey="${sysKey}" /></td>
														</tr>
														<c:set var="certSeNumber" value="${certSeNumber + 1}"></c:set>
													</c:forEach>
												</tbody>
											</table>
											<input type="hidden" id="certSeNumber"
												value="${certSeNumber}">
										</div>
									</div>

									<%-- </c:if> --%>

								</div>
								<input name="supplierTypeIds" type="hidden" /> <input
									type="hidden" value="${currSupplier.supplierTypeIds }"
									id="supplierTypes">
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="btmfix">
		<div style="margin-top: 15px;text-align: center;">
			<button type="button"
				class="btn padding-left-20 padding-right-20 margin-5"
				onclick="prev();">上一步</button>
			<button type="button"
				class="btn padding-left-20 padding-right-20 margin-5"
				onclick="ajaxSave();">暂存</button>
			<input type="button"
				class="btn padding-left-20 padding-right-20 margin-5" value="下一步"
				onclick="next(1)" />
		</div>
	</div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/sms/commons.js"></script>
</html>