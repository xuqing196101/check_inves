<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/reg_head.jsp"%>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<title>供应商注册</title>
		<%@ include file="/WEB-INF/view/common/validate.jsp"%>
		<script type="text/javascript">
			$().ready(function() {
				$("#basic_info_form_id").validForm();
			});

			$(function() {
				$("#page_ul_id").find("li").click(function() {
					var id = $(this).attr("id");
					var page = "tab-" + id.charAt(id.length - 1);
					$("input[name='defaultPage']").val(page);
				});
				var defaultPage = "${defaultPage}";
				if(defaultPage) {
					var num = defaultPage.charAt(defaultPage.length - 1);
					$("#page_ul_id").find("li").each(function(index) {
						if(index == num - 1) {
							$(this).attr("class", "active");
						} else {
							$(this).removeAttr("class");
						}
					});
					$("#tab_content_div_id").find(".tab-pane").each(function() {
						var id = $(this).attr("id");
						if(id == defaultPage) {
							$(this).attr("class", "tab-pane fade height-200 active in");
						} else {
							$(this).attr("class", "tab-pane fade height-200");
						}
					});
				}

				// loadRootArea();
				autoSelected("business_select_id", "${currSupplier.businessType}");
				autoSelected("overseas_branch_select_id", "${currSupplier.overseasBranch}");
				if($("#overseas_branch_select_id").val() == "1") {
					$("li[name='branch']").show();
				}

				if("${currSupplier.status}" == 7) {
					showReason();
				}
				if("${currSupplier.overseasBranch}" == '0' || "${currSupplier.overseasBranch}" == null) {
					$("li[name='branch']").hide();
				}

			});

			/** 加载地区根节点 */
			function loadRootArea() {
				$.ajax({
					url: globalPath + "/area/find_root_area.do",
					type: "post",
					dataType: "json",
					success: function(result) {
						var html = "";
						html += "<option value=''>请选择</option>";
						for(var i = 0; i < result.length; i++) {
							html += "<option id='" + result[i].id + "' value='" + result[i].id + "'>" + result[i].name + "</option>";
						}
						$("#root_area_select_id").append(html);

						// 自动选中
						var rootArea = "${currSupplier.address}";
						if(rootArea)
							rootArea = rootArea.split(",")[0];
						if(rootArea) {
							autoSelected("root_area_select_id", rootArea);
							loadChildren();
						}

					},
				});
			}

			function loadChildren(obj) {
				var id = $(obj).val();
				if(id) {
					$.ajax({
						url: globalPath + "/area/find_area_by_parent_id.do",
						type: "post",
						dataType: "json",
						data: {
							id: id
						},
						success: function(result) {
							var html = "";
							for(var i = 0; i < result.length; i++) {
								html += "<option value='" + result[i].id + "'>" + result[i].name + "</option>";
							}
							var select = $(obj).parent().next().children();
							$(select).empty();
							$(select).append(html);

							// 自动选中
						},
					});
				}
			}

			/** 全选 */
			function checkAll(ele, id) {
				var checked = $(ele).prop("checked");
				$("#" + id).find("input:checkbox").each(function(index) {
					$(this).prop("checked", checked);
				});
			}

			/** 保存基本信息 */
			function saveBasicInfo(obj) {
				var supplierId = $("input[name='id']").val();
				var msg = "";
				var flag = true;

				$("#address_list_body").find("input[type='text']").each(function(index, element) {
					if(element.value == "") {
						msg = "地址信息不能为空!";
						flag = false;
					}
				});
				if($("#overseas_branch_select_id").val() == "1") {
					// 非空校验
					$("#branch_list").find("input[type='text']").each(function(index, element) {
						if(element.value == "") {
							msg = "境外信息不能为空！";
							flag = false;
						}
					});
					// 非空校验
					$("#list-unstyled").find("select").each(function(index, element) {
						if(element.value == "") {
							msg = "境外信息不能为空！";
							flag = false;
						}
					});
					// 非空校验
					$("#list-unstyled").find("textarea").each(function(index, element) {
						if(element.value == "") {
							msg = "境外信息不能为空！";
							flag = false;
						}
					});
				}
				// 非空校验
				$("#financeInfo").find("input[type='text']").each(function(index, element) {
					if(element.value == "") {
						msg = "近三年财务信息不能为空!";
						flag = false;
					}
				});
				// 事务所联系方式格式校验
				var regTelephone = /^(\d{3,4}-{0,1})?\d{7,8}$/
				$("#financeInfo").find("input[name$='telephone']").each(function(index, element) {
					if(!regTelephone.test(element.value)) {
						msg = "事务所联系方式格式有误!";
						flag = false;
					}
				});
				if(flag) {
					$("input[name='flag']").val(obj);
					$("#basic_info_form_id").submit();
				} else {
					layer.msg(msg, {
						offset: '300px'
					});
				}
			}

			/** 暂存 */
			function temporarySave() {
				$("input[name='flag']").val("");
				$.ajax({
					url: "${pageContext.request.contextPath}/supplier/temporarySave.do",
					type: "post",
					data: $("#basic_info_form_id").serializeArray(),
					contextType: "application/x-www-form-urlencoded",
					success: function(msg) {
						if(msg == 'ok') {
							layer.msg('暂存成功', {
								offset: '300px'
							});
						}
						if(msg == 'failed') {
							layer.msg('暂存失败', {
								offset: '300px'
							});
						}
					}
				});
			}
			$(function() {
				$("input").bind("blur", tempSave);
				$("select").bind("change", tempSave);
			});
			/** 无提示实时保存 */
			function tempSave() {
				$("input[name='flag']").val("");
				$.ajax({
					url: "${pageContext.request.contextPath}/supplier/temporarySave.do",
					type: "post",
					async: false,
					data: $("#basic_info_form_id").serializeArray(),
					contextType: "application/x-www-form-urlencoded",
				});
			}
			//listSupplierStockholders

			function openStockholder() {

				var stocIndex = $("#stockIndex").val();
				var supplierId = $("input[name='id']").val();
				var id;
				$.ajax({
					url: "${pageContext.request.contextPath}/supplier/getUUID.do",
					async: false,
					success: function(data) {
						id = data;
					}
				});
				$("#stockholder_list_tbody_id").append("<tr>" +
					"<td class='tc'><input type='checkbox' value='' /><input type='hidden' name='listSupplierStockholders[" + stocIndex + "].id' value=" + id + "><input type='hidden' style='border:0px;' name='listSupplierStockholders[" + stocIndex + "].supplierId' value=" + supplierId + ">" +
					"</td>" +
					"<td class='tc'>  <select class='w100p border0' name='listSupplierStockholders[" + stocIndex + "].nature'>" +
					"<option value='1'>法人</option>" +
					" <option value='2'>自然人</option>" +
					"</select> </td>" +
					"<td class='tc'><input type='text' style='border:0px;' name='listSupplierStockholders[" + stocIndex + "].name' value=''> </td>" +
					"<td class='tc'><input type='text' style='border:0px;' name='listSupplierStockholders[" + stocIndex + "].identity' value=''> </td>" +
					"<td class='tc'> <input type='text' style='border:0px;' name='listSupplierStockholders[" + stocIndex + "].shares' value=''></td>" +
					"<td class='tc'> <input type='text' style='border:0px;' name='listSupplierStockholders[" + stocIndex + "].proportion' value=''> </td>" + "</tr>");

				stocIndex++;
				$("#stockIndex").val(stocIndex);

				/* 	if (!supplierId) {
						layer.msg("请暂存供应商基本信息 !", {
							offset : '300px',
						});
					} else {
						layer.open({
							type : 2,
							title : '添加供应商股东信息',
							// skin : 'layui-layer-rim', //加上边框
							area : [ '50%', '420px' ], //宽高
							offset : '100px',
							scrollbar : false,
							content : globalPath + '/supplier_stockholder/add_stockholder.html?&supplierId=' + supplierId + '&sign=1', //url
							closeBtn : 1, //不显示关闭按钮
						});
					} */
			}

			function deleteStockholder() {
				var checkboxs = $("#stockholder_list_tbody_id").find(":checkbox:checked");
				var stockholderIds = "";
				var supplierId = $("input[name='id']").val();
				$(checkboxs).each(function(index) {
					var tr = $(this).parent().parent();
					$(tr).remove();
					if(index > 0) {
						stockholderIds += ",";
					}
					stockholderIds += $(this).val();
				});
				var size = checkboxs.length;
				if(size > 0) {
					$.ajax({
						url: "${pageContext.request.contextPath}/supplier_stockholder/delete_stockholder.do",
						async: false,
						data: {
							"stockholderIds": stockholderIds,
							"supplierId": supplierId
						},
					});
				} else {
					layer.alert("请至少勾选一条记录 !", {
						offset: '200px',
						scrollbar: false,
					});
				}
			}

			var infotd;
			var filetd;

			function openFinance(obj, year) {
				infotd = $(obj).parent().next().children(":first").children(":last");
				filetd = $(obj).parent().next().children(":last").children(":last");
				var supplierId = $("input[name='id']").val();
				if(!supplierId) {
					layer.msg("请暂存供应商基本信息 !", {
						offset: '300px',
					});
				} else {
					layer.open({
						type: 2,
						title: '添加供应商财务信息',
						// skin : 'layui-layer-rim', //加上边框
						area: ['650px', '420px'], //宽高
						offset: '100px',
						scrollbar: false,
						content: globalPath + '/supplier_finance/add_finance.html?&supplierId=' + supplierId + '&sign=1&&year=' + year, //url
						closeBtn: 1, //不显示关闭按钮
					});
				}
			}

			function deleteFinance() {
				var checkboxs = $("#finance_list_tbody_id").find(":checkbox:checked");
				var financeIds = "";
				var supplierId = $("input[name='id']").val();
				$(checkboxs).each(function(index) {
					if(index > 0) {
						financeIds += ",";
					}
					financeIds += $(this).val();
				});
				var size = checkboxs.length;
				if(size > 0) {
					layer.confirm("已勾选" + size + "条记录, 确定删除 !", {
						offset: '200px',
						scrollbar: false,
					}, function(index) {
						window.location.href = globalPath + "/supplier_finance/delete_finance.html?financeIds=" + financeIds + "&supplierId=" + supplierId;
						layer.close(index);

					});
				} else {
					layer.alert("请至少勾选一条记录 !", {
						offset: '200px',
						scrollbar: false,
					});
				}
			}

			function autoSelected(id, v) {
				if(v) {
					$("#" + id).find("option").each(function() {
						var value = $(this).val();
						if(value == v) {
							$(this).prop("selected", true);
						} else {
							$(this).prop("selected", false);
						}
					});
				}
			}

			function checkAllForFinance(ele) {
				var flag = $(ele).prop("checked");
				$("#finance_list_tbody_id").find("input:checkbox").prop("checked", flag);
				$("#finance_attach_list_tbody_id").find("input:checkbox").prop("checked", flag);
			}

			function showReason() {
				var supplierId = "${currSupplier.id}";
				var left = document.body.clientWidth - 500;
				var top = window.screen.availHeight / 2 - 150;
				layer.open({
					type: 2,
					title: '审核反馈',
					closeBtn: 0, //不显示关闭按钮
					skin: 'layui-layer-lan', //加上边框
					area: ['500px', '300px'], //宽高
					offset: [top, left],
					shade: 0,
					maxmin: true,
					shift: 2,
					content: globalPath + '/supplierAudit/showReasonsList.html?&auditType=basic_page,finance_page,stockholder_page' + '&jsp=dialog_basic_reason' + '&supplierId=' + supplierId, //url
				});
			}

			function downloadFile(obj) {
				var id = $(obj).parent().children(":last").val();
				var key = 1;
				var form = $("<form>");
				form.attr('style', 'display:none');
				form.attr('method', 'post');
				form.attr('action', globalPath + '/file/download.html?id=' + id + '&key=' + key);
				$('body').append(form);
				form.submit();
			}

			function dis(obj) {
				var vals = $(obj).val();
				if(vals == 1) {
					$("li[name='branch']").show();
					/* 	$('#sup_country').removeAttr('disabled');
						$('#sup_businessScope').removeAttr('disabled');
						$('#sup_branchName').removeAttr('disabled');
						$('#sup_branchAddress').removeAttr('disabled'); */
				} else {
					$("li[name='branch']").hide();
					/* 		$('#sup_country').attr('disabled',"true");
							$('#sup_businessScope').attr('disabled',"true");
							$('#sup_branchName').attr('disabled',"true");
							$('#sup_branchAddress').attr('disabled',"true"); */

				}
			}

			function checknums(obj) {
				var vals = $(obj).val();
				var reg = /^\d+\.?\d*$/;
				if(!reg.exec(vals)) {
					$(obj).val("");
					$("#err_fund").text("数字非法");
				} else {
					$("#err_fund").text();
					$("#err_fund").empty();
				}
			}

			function increaseAddress(obj) {
				var ind = $("#index").val();
				var li = $(obj).parent().parent();
				var id;
				$.ajax({
					url: "${pageContext.request.contextPath}/supplier/getUUID.do",
					async: false,
					success: function(data) {
						id = data;
					}
				});
				$(li).after("<li class='col-md-3 col-sm-6 col-xs-12 pl10'>" +
					"<span class='col-md-12 col-xs-12 col-sm-12  padding-left-5'><i class='red'>*</i> 生产经营地址邮编</span>" +
					"<div class='input-append col-md-12 col-sm-12 col-xs-12 input_group p0'>" +
					"<input type='text' name='addressList[" + ind + "].code' value='' / onblur='tempSave()'>" +
					"<span class='add-on cur_point'>i</span>" +
					" <div class='cue'> </div>" +
					"</div>" +
					"</li> " +
					"<li class='col-md-3 col-sm-6 col-xs-12'>" +
					"<span class='col-md-12 col-xs-12 col-sm-12 padding-left-5'><i class='red'>*</i> 生产经营地址</span>" +
					"<div class='col-md-12 col-xs-12 col-sm-12 select_common p0'>" +
					"<div class='col-md-5 col-xs-5 col-sm-5 mr5 p0'><select id='root_area_select_id' onchange='loadChildren(this)'  name='addressList[" + ind + "].provinceId' >" +
					" <option value=''>请选择</option>" +
					" <c:forEach  items='${privnce }' var='prin'>" +
					" <option value='${prin.id }' onchange='tempSave()' >${prin.name }</option>" +
					" </c:forEach>" +
					" </select></div> " +
					"<div class='col-md-5 col-xs-5 col-sm-5 mr5 p0'><select id='children_area_select_id' name='addressList[" + ind + "].address'>" +
					" <c:forEach  items='${city }' var='city'>" +
					"<option value='${city.id }' onchange='tempSave()' >${city.name }</option>" +
					"</c:forEach>" +

					" </select></div>" +
					"<div class='cue'>  </div>" +
					"</div>" +
					" </li> " +

					" <li class='col-md-3 col-sm-6 col-xs-12'>" +
					"<span class='col-md-12 col-xs-12 col-sm-12 padding-left-5'><i class='red'>*</i> 生产经营详细地址</span>" +
					" <div class='input-append col-md-12 col-sm-12 col-xs-12 input_group p0'>" +
					"<input type='text' name='addressList[" + ind + "].detailAddress'  value='' onblur='tempSave()'>" +
					"<span class='add-on cur_point'>i</span>" +
					"<div class='cue'>  </div>" +
					"</div>" +
					"</li>" +
					"<li class='col-md-3 col-sm-6 col-xs-12'>" +
					"	<span class='col-md-12 col-xs-12 col-sm-12 padding-left-5 white'>操作</span>" +
					"<div class='col-md-12 col-xs-12 col-sm-12 p0 mb25 h30'>" +
					"	<input type='button' onclick='increaseAddress(this)' class='btn list_btn' value='十'/>" +
					"	<input type='button' onclick='delAddress(this)' class='btn list_btn' value='一'/>" +
					"	<input type='hidden' name='addressList[" + ind + "].id' value='" + id + "' />" +
					"</div></li>"
				);
				ind++;
				$("#index").val(ind);
			}

			function delAddress(obj) {
				var btmCount = 0;
				$("#address_list_body").find("input[type='button']").each(function() {
					btmCount++;
				});
				if(btmCount == 2) {
					layer.msg("生产经营地址必须至少保留一个!", {
						offset: '300px'
					});
				} else {
					var id = $(obj).next().val();
					var tag = $(obj).parent().parent();
					var li_1 = $(obj).parent().parent().prev();
					$(li_1).prev().prev().remove(); //邮编
					$(li_1).prev().remove(); //省市
					$(li_1).remove(); //详细地址
					$(tag).remove(); //按钮  
					$.ajax({
						url: "${pageContext.request.contextPath}/supplier/delAddress.do",
						data: {
							"id": id
						},
						success: function() {
							layer.msg("删除成功!", {
								offset: '300px'
							});
						},
						error: function() {
							layer.msg("删除失败!", {
								offset: '300px'
							});
						}
					});
				}
			}

			function addBranch(obj) {
				var li = $(obj).parent().parent().next();
				var inde = $("#branchIndex").val();
				$(li).after("<li name='branch' class='col-md-3 col-sm-6 col-xs-12'>" +
					" <span class='col-md-12 col-xs-12 col-sm-12 padding-left-5'><i class='red'>* </i>机构名称</span>" +
					" <div class='input-append col-md-12 col-sm-12 col-xs-12 input_group p0'>" +
					" <input type='text' name='branchList[" + inde + "].organizationName' id='sup_branchName'  value='' / onblur='tempSave()'>" +
					"   <span class='add-on cur_point'>i</span>" +
					"   </div>" +
					"  </li>" +
					"<li name='branch'  class='col-md-3 col-sm-6 col-xs-12'>" +
					" <span class='col-md-12 col-xs-12 col-sm-12 padding-left-5'><i class='red'>* </i>所在国家（地区）</span>" +
					"  <div class='select_common col-md-12 col-sm-12 col-xs-12 input_group p0'>" +
					"<select name='branchList[" + inde + "].country'  id='overseas_branch_select_id'>" +
					"<c:forEach items='${foregin }' var='fr'>" +
					"<option value='${fr.id }' <c:if test='${bran.country==fr.id}'> onchange='tempSave()' selected='selected' </c:if> >${fr.name }</option>" +
					" </c:forEach> 	</select>" +
					" </div>" +
					" </li>" +

					"  <li name='branch'  class='col-md-3 col-sm-6 col-xs-12'>" +
					" <span class='col-md-12 col-xs-12 col-sm-12 padding-left-5'><i class='red'>* </i>详细地址</span>" +
					" <div class='input-append col-md-12 col-sm-12 col-xs-12 input_group p0'>" +
					" <input type='text' name='branchList[" + inde + "].detailAddress'  id='sup_branchAddress' value='' / onblur='tempSave()'>" +
					"  <span class='add-on cur_point'>i</span>" +
					" </div>" +
					" </li>" +

					" <li name='branch'  class='col-md-3 col-sm-6 col-xs-12'>" +
					" <span class='col-md-12 col-xs-12 col-sm-12 padding-left-5 white'>操作</span>" +
					" <div class='col-md-12 col-xs-12 col-sm-12 p0 mb25 h30'>" +
					" <input type='button' onclick='addBranch(this)' class='btn list_btn' value='十'/>" +
					" <input type='button' onclick='delBranch(this)'class='btn list_btn' value='一'/>" +
					" </div>" +
					" </li>" +

					"  <li name='branch'  class='col-md-12 col-xs-12 col-sm-12 mb25'>" +
					" <span class='col-md-12 col-xs-12 col-sm-12 padding-left-5'><i class='red'>* </i>生产经营范围</span>" +
					" <div class='col-md-12 col-xs-12 col-sm-12 p0'>" +
					"  <textarea class='col-md-12 col-xs-12 col-sm-12 h80'  id='sup_businessScope' onblur='tempSave()' name='branchList[" + inde + "].businessSope'></textarea>" +
					" </div>" +
					" </li>");
				inde++;
				$("#branchIndex").val(inde);

			}

			function delBranch(obj) {
				var btmCount = 0;
				$("#branch_list_body").find("input[type='button']").each(function() {
					btmCount++;
				});
				if(btmCount == 2) {
					layer.msg("境外分支信息必须至少保留一个!", {
						offset: '300px'
					});
				} else {
					var li = $(obj).parent().parent().next();
					var pre = $(obj).parent().parent().prev();
					$(li).remove();
					$(pre).prev().prev().remove();
					$(pre).prev().remove();
					$(pre).remove();
					$(obj).parent().parent().remove();
				}
			}

			function errorMsg(auditField) {
				var supplierId = "${currSupplier.id}";
				$.ajax({
					url: "${pageContext.request.contextPath}/supplier/audit.html",
					data: {
						"supplierId": supplierId,
						"auditField": auditField,
						"auditType": "basic_page"
					},
					dataType: "json",
					success: function(data) {
						/* alert(data.suggest); */
						layer.msg("不通过理由：" + data.suggest, {offset: '300px'
						});
					}
				});
			}
		</script>
	</head>

	<body>
		<div class="wrapper">
			<%@include file="supplierNav.jsp" %>
			<!--基本信息-->
			<div class="container container_box">
				<form id="basic_info_form_id" action="${pageContext.request.contextPath}/supplier/perfect_basic.html" method="post">
					<input name="id" value="${currSupplier.id}" type="hidden" />
					<%-- 	<input name="defaultPage" value="${defaultPage}" type="hidden" />  --%>
					<input name="flag" type="hidden" />
					<div>
						<h2 class="count_flow"> <i>1</i> 基本信息</h2>
						<fieldset class="col-md-12 col-sm-12 col-xs-12 border_font">
							<legend>企业信息</legend>
							<ul class="list-unstyled f14">
								<li class="col-md-3 col-sm-6 col-xs-12 pl10">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 公司名称</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input id="supplierName_input_id" type="text" name="supplierName" required="required" manlength="50" value="${currSupplier.supplierName}" <c:if test="${fn:contains(audit,'supplierName')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('supplierName')"</c:if> />
										<%--  <c:if test="${fn:contains(audit,'supplierName')}">
						    <span class="add-on" style="color: red; border-right: 1px solid #ef0000; border-top: 1px solid #ef0000; border-bottom:  1px solid #ef0000;">×</span>
					    </c:if> --%>
										<%-- <c:if test="${!fn:contains(audit,'supplierName')}">
									
   					    </c:if> --%>
										<span class="add-on">i</span>
										<span class="input-tip">不能为空</span>
										<div class="cue"> ${err_msg_supplierName } </div>
										<div class="cue">
											<sf:errors path="supplierName" />
										</div>

									</div>
								</li>
								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">公司网址</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="website" isUrl="isUrl" value="${currSupplier.website}" <c:if test="${fn:contains(audit,'website')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('website')"</c:if> >
										<%-- <c:if test="${fn:contains(audit,'website')}">
						    <span class="add-on" style="color: red; border-right: 1px solid #ef0000; border-top: 1px solid #ef0000; border-bottom:  1px solid #ef0000;">×</span>
					    </c:if> --%>
										<%-- <c:if test="${!fn:contains(audit,'website')}">
					     </c:if> --%>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">例如：www.baidu.com</span>
										<div class="cue"> ${err_msg_website } </div>
										<div class="cue">
											<sf:errors path="website" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 成立日期</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<fmt:formatDate value="${currSupplier.foundDate}" pattern="yyyy-MM-dd" var="foundDate" />
										<input type="text" readonly="readonly" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'{%y-3}-%M-%d'})" name="foundDate" value="${foundDate}" <c:if test="${fn:contains(audit,'foundDate')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('foundDate')"</c:if> />
										<span class="add-on cur_point">i</span>
										<span class="input-tip">成立时间须大于三年</span>
										<div class="cue"> ${err_msg_foundDate } </div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 营业执照登记类型</span>
									<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
										<select required name="businessType" id="business_select_id" <c:if test="${fn:contains(audit,'businessType')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('businessType')"</c:if>>
											<c:forEach items="${company }" var="obj">
												<option value="${obj.id }" <c:if test="${obj.id==currSupplier.businessType }">selected="selected"</c:if> >${obj.name }</option>
											</c:forEach>
											<!-- <option>外资企业</option>
						<option>民营企业</option>
						<option>股份制企业</option>
						<option>私营企业</option> -->
										</select>
									</div>
								</li>
								<%-- 	 
				  <li class="col-md-3 col-sm-6 col-xs-12">
					   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 生产经营地址</span>
					   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
				        <input type="text" name="businessAddress" value="${currSupplier.businessAddress}" />
				        <span class="add-on cur_point">i</span>
				        <div class="cue"> ${err_bAddress } </div>
			       	   </div>
				  </li> --%>

								<%-- 	  <li class="col-md-3 col-sm-6 col-xs-12">
				    <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 公司地址</span>
				    <div class="col-md-12 col-xs-12 col-sm-12 select_common p0">
				         <div class="col-md-5 col-xs-5 col-sm-5 mr5 p0"><select id="root_area_select_id" onchange="loadChildren(this)">
				     
				         <c:forEach  items="${privnce }" var="prin">
					         <c:if test="${prin.id==area.parentId }">
					          <option value="${prin.id }" selected="selected" >${prin.name }</option>
					         </c:if>
				           <c:if test="${prin.id!=area.parentId }">
					          <option value="${prin.id }"  >${prin.name }</option>
					         </c:if>
				         </c:forEach>
				         
				         
				         </select></div> 
				         <div class="col-md-5 col-xs-5 col-sm-5 mr5 p0"><select id="children_area_select_id" name="address" >
				         
				           <c:forEach  items="${city }" var="city">
					         <c:if test="${city.id==currSupplier.address }">
					          <option value="${city.id }" selected="selected" >${city.name }</option>
					         </c:if>
				           <c:if test="${city.id!=currSupplier.address }">
					          <option value="${city.id }"  >${city.name }</option>
					         </c:if>
				         </c:forEach>
				         
				         
				         </select></div>
				         <div class="cue"> ${err_msg_address } </div>
			        </div>		        
				 </li>   --%>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 基本账户开户行</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="bankName" maxlength="50" required="required" value="${currSupplier.bankName}" <c:if test="${fn:contains(audit,'bankName')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('bankName')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空</span>
										<div class="cue"> ${err_msg_bankName } </div>
										<div class="cue">
											<sf:errors path="bankName" />
										</div>

									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-xs-12 col-sm-12  padding-left-5"><i class="red">*</i> 银行账号</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="bankAccount" isBankCard="true" required="required" value="${currSupplier.bankAccount}" <c:if test="${fn:contains(audit,'bankAccount')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('bankAccount')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空</span>
										<div class="cue"> ${err_msg_bankAccount } </div>
										<div class="cue">
											<sf:errors path="bankAccount" />
										</div>
									</div>
								</li>

								<li id="breach_li_id" class="col-md-3 col-sm-6 col-xs-12 mb25">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" <c:if test="${fn:contains(audit,'supplierBank')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('supplierBank')"</c:if>><i class="red">*</i> 基本账户开户许可证</span>
									<div class="col-md-12 col-sm-12 col-xs-12 p0">
										<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="bank_up" maxcount="1" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBank}" auto="true" />
										<u:show showId="bank_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBank}" />
									</div>
									<%--  <div class="cue"> ${err_bearch } </div> --%>
								</li>
								<li class="col-md-12 col-xs-12 col-sm-12 mb25">
									<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> <i class="red">* </i>公司简介</span>
									<div class="col-md-12 col-xs-12 col-sm-12 p0">
										<textarea class="col-md-12 col-xs-12 col-sm-12 h80" required="required" maxlength="1000" name="description" <c:if test="${fn:contains(audit,'description')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('description')"</c:if>>${currSupplier.description}</textarea>
										<div class="cue">
											<sf:errors path="description" />
										</div>
									</div>
								</li>

								<%-- 			 <li id="breach_li_id" class="col-md-6 col-sm-12 col-xs-12 mb25">
				   <span class="col-md-5 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 基本账户开户许可证</span> 
				   <div class="col-md-6 col-sm-12 col-xs-12 p0">
				     <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="bank_up" multiple="true"  groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBank}" auto="true" /> 
				     <u:show showId="bank_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBank}" />
				   </div>
				    <div class="cue"> ${err_bearch } </div>
				</li> --%>

								<%-- 	 
				 <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 col-xs-12 col-sm-12  padding-left-5"><i class="red">*</i>邮编</span>
				   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
			        <input type="text" name="postCode" value="${currSupplier.postCode}" />
			        <span class="add-on cur_point">i</span>
			         <div class="cue"> ${err_msg_bankAccount } </div>
			       </div>
				 </li>   --%>

								<%-- 	<li class="col-md-12 col-xs-12 col-sm-12 mb25">
			    	<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>详细地址</span>
			    	<div class="col-md-12 col-xs-12 col-sm-12 p0">
				       <textarea class="col-md-12 col-xs-12 col-sm-12 h130"  name="detailAddress">${currSupplier.detailAddress}</textarea>
				       <div class="cue"> ${err_detailAddress } </div>
		       	    </div>
				</li>  --%>
							</ul>
						</fieldset>

						<fieldset class="col-md-12 col-xs-12 col-sm-12 border_font mt20">
							<legend>地址信息</legend>
							<ul class="list-unstyled f14">
								<li class="col-md-3 col-sm-6 col-xs-12 pl10">
									<span class="col-md-12 col-xs-12 col-sm-12  padding-left-5 "><i class="red">*</i> 注册地址邮编</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="postCode" required isZipCode="true" value="${currSupplier.postCode}" <c:if test="${fn:contains(audit,'postCode')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('postCode')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，长度为6位</span>
										<div class="cue"> ${err_msg_postCode } </div>
										<div class="cue">
											<sf:errors path="postCode" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 注册公司地址</span>
									<div class="col-md-12 col-xs-12 col-sm-12 select_common p0">
										<div class="col-md-5 col-xs-5 col-sm-5 mr5 p0">
											<select id="root_area_select_id" onchange="loadChildren(this)" <c:if test="${fn:contains(audit,'address')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('address')"</c:if>>
												<option value="">请选择</option>
												<c:forEach items="${privnce }" var="prin">
													<c:if test="${prin.id==area.parentId }">
														<option value="${prin.id }" selected="selected">${prin.name }</option>
													</c:if>
													<c:if test="${prin.id!=area.parentId }">
														<option value="${prin.id }">${prin.name }</option>
													</c:if>
												</c:forEach>

											</select>
										</div>
										<div class="col-md-5 col-xs-5 col-sm-5 mr5 p0">
											<select id="children_area_select_id" name="address" <c:if test="${fn:contains(audit,'address')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('address')"</c:if>>

												<c:forEach items="${city }" var="city">
													<c:if test="${city.id==currSupplier.address }">
														<option value="${city.id }" selected="selected">${city.name }</option>
													</c:if>
													<c:if test="${city.id!=currSupplier.address }">
														<option value="${city.id }">${city.name }</option>
													</c:if>
												</c:forEach>
											</select>
										</div>
										<div class="cue"> ${err_msg_address } </div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 注册公司详细地址</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="detailAddress" value="${currSupplier.detailAddress}" required maxlength="50" <c:if test="${fn:contains(audit,'detailAddress')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('detailAddress')"</c:if>>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空</span>
										<div class="cue">${err_detailAddress } </div>
										<div class="cue">
											<sf:errors path="detailAddress" />
										</div>
									</div>
								</li>
								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5 white">操作</span>
									<div class="col-md-12 col-xs-12 col-sm-12 p0 mb25 h30">
										<!-- 	<input type="button" class="btn" value="新增"/>
						<input type="button" class="btn" value="删除"/> -->
									</div>
								</li>

								<div id="address_list_body">
									<c:forEach items="${currSupplier.addressList}" var="addr" varStatus="vs">
										<li class="col-md-3 col-sm-6 col-xs-12 pl10">
											<span class="col-md-12 col-xs-12 col-sm-12  padding-left-5"><i class="red">*</i> 生产经营地址邮编</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input type="text" required isZipCode="true" name="addressList[${vs.index }].code" value="${addr.code}" />
												<span class="add-on cur_point">i</span>
												<span class="input-tip">不能为空，长度为6位</span>
												<div class="cue">
													<sf:errors path="addressList[${vs.index }].code" />
												</div>
											</div>
										</li>

										<li class="col-md-3 col-sm-6 col-xs-12">
											<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 生产经营地址</span>
											<div class="col-md-12 col-xs-12 col-sm-12 select_common p0">
												<div class="col-md-5 col-xs-5 col-sm-5 mr5 p0">
													<select id="root_area_select_id" onchange="loadChildren(this)" name="addressList[${vs.index }].provinceId">
														<option value="">请选择</option>
														<c:forEach items="${privnce }" var="prin">
															<c:if test="${prin.id==addr.provinceId }">
																<option value="${prin.id }" selected="selected">${prin.name }</option>
															</c:if>
															<c:if test="${prin.id!=addr.provinceId }">
																<option value="${prin.id }">${prin.name }</option>
															</c:if>
														</c:forEach>

													</select>
												</div>
												<div class="col-md-5 col-xs-5 col-sm-5 mr5 p0">
													<select id="children_area_select_id" name="addressList[${vs.index }].address">
														<c:forEach items="${addr.areaList }" var="city">
															<c:if test="${city.id==addr.address }">
																<option value="${city.id }" selected="selected">${city.name }</option>
															</c:if>
															<c:if test="${city.id!=addr.address }">
																<option value="${city.id }">${city.name }</option>
															</c:if>
														</c:forEach>
													</select>
												</div>
												<div class="cue"> </div>
											</div>
										</li>

										<li class="col-md-3 col-sm-6 col-xs-12">
											<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 生产经营详细地址</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input type="text" name="addressList[${vs.index }].detailAddress" required="required" maxlength="50" value="${addr.detailAddress }">
												<span class="add-on cur_point">i</span>
												<span class="input-tip">不能为空</span>
												<div class="cue">
													<sf:errors path="addressList[${vs.index }].detailAddress" />
												</div>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12">
											<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5 white">操作</span>
											<div class="col-md-12 col-xs-12 col-sm-12 p0 mb25 h30">
												<input type="button" onclick="increaseAddress(this)" class="btn list_btn" value="十" />
												<input type="button" onclick="delAddress(this, '${addr.id}')" class="btn list_btn" value="一" />
												<input type="hidden" name="addressList[${vs.index }].id" value="${addr.id}" />
											</div>
										</li>
									</c:forEach>
								</div>

								<%--  <li class="col-md-12 col-xs-12 col-sm-12 mb25">
			    	<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>详细地址</span>
			    	<div class="col-md-12 col-xs-12 col-sm-12 p0">
				       <textarea class="col-md-12 col-xs-12 col-sm-12 h130"  name="detailAddress">${currSupplier.detailAddress}</textarea>
				       <div class="cue"> ${err_detailAddress } </div>
		       	    </div>
				</li>  --%>
							</ul>
						</fieldset>

						<fieldset class="col-md-12 col-xs-12 col-sm-12 border_font mt20">
							<legend>资质资信</legend>
							<ul class="list-unstyled f14">
								<li class="col-md-6 col-sm-12 col-xs-12 mb25 pl10">
									<span class="col-md-5 col-sm-12 col-xs-12 padding-left-5" <c:if test="${fn:contains(audit,'taxCert')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('taxCert')"</c:if>><i class="red">*</i> 近三个月完税凭证</span>
									<div class="col-md-6 col-sm-12 col-xs-12 p0">
										<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="taxcert_up" multiple="true" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierTaxCert}" auto="true" />
										<u:show showId="taxcert_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierTaxCert}" />
									</div>
									<div class="cue"> ${err_taxCert } </div>
								</li>

								<li id="bill_li_id" class="col-md-6 col-sm-12 col-xs-12 mb25">
									<span class="col-md-5 col-sm-12 col-xs-12 padding-left-5" <c:if test="${fn:contains(audit,'billCert')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('billCert')"</c:if>><i class="red">*</i> 近三年银行基本账户年末对账单</span>
									<div class="col-md-6 col-sm-12 col-xs-12 p0">
										<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="billcert_up" multiple="true" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBillCert}" auto="true" />
										<u:show showId="billcert_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBillCert}" />
									</div>
									<div class="cue"> ${err_bil } </div>
								</li>

								<li id="security_li_id" class="col-md-6 col-sm-12 col-xs-12 mb25">
									<span class="col-md-5 col-sm-12 col-xs-12 padding-left-5" <c:if test="${fn:contains(audit,'securityCert')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('securityCert')"</c:if>><i class="red">*</i> 近三个月缴纳社会保险金凭证</span>
									<div class="col-md-6 col-sm-12 col-xs-12 p0">
										<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="curitycert_up" multiple="true" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierSecurityCert}" auto="true" />
										<u:show showId="curitycert_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierSecurityCert}" />
									</div>
									<div class="cue"> ${err_security } </div>
								</li>

								<li id="breach_li_id" class="col-md-6 col-sm-12 col-xs-12 mb25">
									<span class="col-md-5 col-sm-12 col-xs-12 padding-left-5" <c:if test="${fn:contains(audit,'isIllegal')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('isIllegal')"</c:if>><i class="red">*</i> 近三年内有无重大违法记录</span>
									<div class="col-md-6 col-sm-12 col-xs-12 p0">
										<input type="radio" name="isIllegal" value="1" <c:if test="${'1' eq currSupplier.isIllegal}">checked="checked"</c:if>/> 有违法
										<input type="radio" name="isIllegal" value="0" <c:if test="${'1' ne currSupplier.isIllegal}">checked="checked"</c:if>/> 无违法
									</div>
								</li>
							</ul>
						</fieldset>
						<fieldset class="col-md-12 col-sm-12 col-xs-12 border_font mt20">
							<legend>法定法人信息</legend>
							<ul class="list-unstyled f14">
								<li class="col-md-3 col-sm-6 col-xs-12 pl10">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 姓名</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="legalName" required maxlength="10" value="${currSupplier.legalName}" <c:if test="${fn:contains(audit,'legalName')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('legalName')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空</span>
										<div class="cue"> ${err_legalName } </div>
										<div class="cue">
											<sf:errors path="legalName" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 身份证号</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="legalIdCard" required value="${currSupplier.legalIdCard}" <c:if test="${fn:contains(audit,'legalIdCard')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('legalIdCard')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，长度为15位或者18位</span>
										<div class="cue"> ${err_legalCard } </div>
										<div class="cue">
											<sf:errors path="legalIdCard" />
										</div>
									</div>
								</li>

								<%-- 	    
		     <li id="breach_li_id" class="col-md-3 col-sm-6 col-xs-12 mb25">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 身份证正面</span> 
			   <div class="col-md-12 col-sm-12 col-xs-12 p0 h30">
			     <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="bearchcert_up_up" multiple="true"  groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentityUp}" auto="true" /> 
			     <u:show showId="bearchcert_up_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentityUp}" />
			   </div>
			   <div class="cue"> ${err_bearch } </div>
			</li> --%>

								<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5" <c:if test="${fn:contains(audit,'supplierIdentityUp')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('supplierIdentityUp')"</c:if>><i class="red">*</i> 身份证复印件（正反面在一张上）</span>
									<div class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0">
										<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="bearchcert_up_up" maxcount="1" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentityUp}" auto="true" />
										<u:show showId="bearchcert_up_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentityUp}" />
									</div>
								</li>

								<%--        <li class="col-md-3 col-sm-6 col-xs-12 mb25"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 居民身份证附件</span>
                    <div class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0">
					     <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="bearchcert_up_up" multiple="true"  groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentityUp}" auto="true" /> 
					     <u:show showId="bearchcert_up_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentityUp}" />
                    </div>
                </li> --%>

								<%--      <li class="col-md-3 col-sm-6 col-xs-12 mb25"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 身份证反面</span>
                    <div class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0">
					   			     <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="identity_down_up" multiple="true"  groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentitydown}" auto="true" /> 
			    					 <u:show showId="identity_down_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentitydown}" />
                    </div>
                </li> --%>

								<%--               
			 <li id="breach_li_id" class="col-md-3 col-sm-6 col-xs-12 mb25">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 身份证反面</span> 
			   <div class="col-md-12 col-sm-12 col-xs-12 p0 h30">
			     <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="identity_down_up" multiple="true"  groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentitydown}" auto="true" /> 
			     <u:show showId="identity_down_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentitydown}" />
			   </div>
			   <div class="cue"> ${err_bearch } </div>
			</li> --%>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 固定电话</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="legalMobile" required isTel="true" onkeyup="value=value.replace(/[^\d-]/g,'')" value="${currSupplier.legalMobile}" <c:if test="${fn:contains(audit,'legalMobile')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('legalMobile')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，如: XXXX-XXXXXXX</span>
										<div class="cue"> ${err_legalMobile } </div>
										<div class="cue">
											<sf:errors path="legalMobile" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 手机</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="legalTelephone" onkeyup="value=value.replace(/[^\d]/g,'')" required isPhone="true" value="${currSupplier.legalTelephone}" <c:if test="${fn:contains(audit,'legalTelephone')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('legalTelephone')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，11位手机号码</span>
										<div class="cue"> ${err_legalPhone } </div>
										<div class="cue">
											<sf:errors path="legalTelephone" />
										</div>
									</div>
								</li>

								<%--   <li id="breach_li_id" class="col-md-3 col-sm-6 col-xs-12 mb25">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 身份证正面</span> 
			   <div class="col-md-12 col-sm-12 col-xs-12 p0">
			     <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="bearchcert_up_up" multiple="true"  groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentityUp}" auto="true" /> 
			     <u:show showId="bearchcert_up_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentityUp}" />
			   </div>
			   <div class="cue"> ${err_bearch } </div> 
			</li>--%>

								<%--  <li id="breach_li_id" class="col-md-3 col-sm-6 col-xs-12 mb25">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 身份证反面</span> 
			   <div class="col-md-12 col-sm-12 col-xs-12 p0">
			     <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="identity_down_up" multiple="true"  groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentitydown}" auto="true" /> 
			     <u:show showId="identity_down_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentitydown}" />
			   </div>
			 <div class="cue"> ${err_bearch } </div> 
			</li>--%>
							</ul>
						</fieldset>

						<fieldset class="col-md-12 col-sm-12 col-xs-12 border_font mt20">
							<legend>注册联系人</legend>
							<ul class="list-unstyled f14">
								<li class="col-md-3 col-sm-6 col-xs-12 pl10">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><i class="red">*</i> 姓名</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="contactName" required maxlength="10" value="${currSupplier.contactName}" <c:if test="${fn:contains(audit,'contactName')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('contactName')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空</span>
										<div class="cue"> ${err_conName } </div>
										<div class="cue">
											<sf:errors path="contactName" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 传真</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="contactFax" required isFax="true" onkeyup="value=value.replace(/[^\d-]/g,'')" value="${currSupplier.contactFax}" <c:if test="${fn:contains(audit,'contactFax')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('contactFax')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，如: XXXX-XXXXXXX</span>
										<div class="cue"> ${err_fax } </div>
										<div class="cue">
											<sf:errors path="contactFax" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 固定电话</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="contactMobile" required isTel="true" onkeyup="value=value.replace(/[^\d-]/g,'')" value="${currSupplier.contactMobile}" <c:if test="${fn:contains(audit,'contactMobile')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('contactMobile')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，如: XXXX-XXXXXXX</span>
										<div class="cue"> ${err_catMobile } </div>
										<div class="cue">
											<sf:errors path="contactMobile" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 手机</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="mobile" required readonly="readonly" isPhone="true" onkeyup="value=value.replace(/[^\d]/g,'')" value="${currSupplier.mobile}" <c:if test="${fn:contains(audit,'mobile')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('mobile')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空,11位手机号码</span>
										<div class="cue"> ${err_catTelphone } </div>
										<div class="cue">
											<sf:errors path="mobile" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 邮箱</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="contactEmail" required email value="${currSupplier.contactEmail}" <c:if test="${fn:contains(audit,'contactEmail')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('contactEmail')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，如：XXXX@XX.com</span>
										<div class="cue"> ${err_catEmail } </div>
										<div class="cue">
											<sf:errors path="contactEmail" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 地址</span>
									<div class="col-md-12 col-xs-12 col-sm-12 select_common p0">
										<div class="col-md-5 col-xs-5 col-sm-5 mr5 p0">
											<select id="root_area_select_id" name="concatProvince" onchange="loadChildren(this)" <c:if test="${fn:contains(audit,'concatCity')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('concatCity')"</c:if>>
												<option value="">请选择</option>
												<c:forEach items="${privnce }" var="prin">
													<c:if test="${prin.id==currSupplier.concatProvince }">
														<option value="${prin.id }" selected="selected">${prin.name }</option>
													</c:if>
													<c:if test="${prin.id!=currSupplier.concatProvince }">
														<option value="${prin.id }">${prin.name }</option>
													</c:if>
												</c:forEach>

											</select>
										</div>
										<div class="col-md-5 col-xs-5 col-sm-5 mr5 p0">
											<select id="children_area_select_id" name="concatCity" <c:if test="${fn:contains(audit,'concatCity')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('concatCity')"</c:if>>

												<c:forEach items="${currSupplier.concatCityList }" var="city">
													<c:if test="${city.id==currSupplier.concatCity}">
														<option value="${city.id }" selected="selected">${city.name }</option>
													</c:if>
													<c:if test="${city.id!=currSupplier.concatCity}">
														<option value="${city.id }">${city.name }</option>
													</c:if>
												</c:forEach>
											</select>
										</div>
										<div class="cue">${err_city} </div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 详细地址</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="contactAddress" required maxlength="50" value="${currSupplier.contactAddress}" <c:if test="${fn:contains(audit,'contactAddress')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('contactAddress')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空</span>
										<div class="cue"> ${err_conAddress } </div>
										<div class="cue">
											<sf:errors path="contactAddress" />
										</div>
									</div>
								</li>
							</ul>
						</fieldset>

						<fieldset class="col-md-12 border_font mt20">
							<legend>军队业务联系人</legend>
							<ul class="list-unstyled f14">
								<li class="col-md-3 col-sm-6 col-xs-12 pl10">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><i class="red">*</i> 姓名</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="armyBusinessName" required maxlength="10" value="${currSupplier.armyBusinessName}" <c:if test="${fn:contains(audit,'armyBusinessName')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('armyBusinessName')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空</span>
										<div class="cue"> ${err_armName} </div>
										<div class="cue">
											<sf:errors path="armyBusinessName" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 传真</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="armyBusinessFax" required isFax="true" onkeyup="value=value.replace(/[^\d-]/g,'')" value="${currSupplier.armyBusinessFax}" <c:if test="${fn:contains(audit,'armyBusinessFax')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('armyBusinessFax')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，如: XXXX-XXXXXXX</span>
										<div class="cue"> ${err_armFax } </div>
										<div class="cue">
											<sf:errors path="armyBusinessFax" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 固定电话</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="armyBuinessMobile" required isTel="true" onkeyup="value=value.replace(/[^\d-]/g,'')" value="${currSupplier.armyBuinessMobile}" <c:if test="${fn:contains(audit,'armyBuinessMobile')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('armyBuinessMobile')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，如: XXXX-XXXXXXX</span>
										<div class="cue"> ${err_armMobile } </div>
										<div class="cue">
											<sf:errors path="armyBuinessMobile" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 手机</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="armyBuinessTelephone" required isPhone="true" onkeyup="value=value.replace(/[^\d]/g,'')" value="${currSupplier.armyBuinessTelephone}" <c:if test="${fn:contains(audit,'armyBuinessTelephone')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('armyBuinessTelephone')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，11位手机号码</span>
										<div class="cue"> ${err_armTelephone } </div>
										<div class="cue">
											<sf:errors path="armyBuinessTelephone" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 邮箱</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="armyBuinessEmail" required email value="${currSupplier.armyBuinessEmail}" <c:if test="${fn:contains(audit,'armyBuinessEmail')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('armyBuinessEmail')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，如：XXXX@XX.com</span>
										<div class="cue"> ${err_armEmail } </div>
										<div class="cue">
											<sf:errors path="armyBuinessEmail" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 地址</span>
									<div class="col-md-12 col-xs-12 col-sm-12 select_common p0">
										<div class="col-md-5 col-xs-5 col-sm-5 mr5 p0">
											<select id="root_area_select_id" name="armyBuinessProvince" onchange="loadChildren(this)" <c:if test="${fn:contains(audit,'armyBuinessCity')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('armyBuinessCity')"</c:if>>
												<option value="">请选择</option>
												<c:forEach items="${privnce }" var="prin">
													<c:if test="${prin.id==currSupplier.armyBuinessProvince }">
														<option value="${prin.id }" selected="selected">${prin.name }</option>
													</c:if>
													<c:if test="${prin.id!=currSupplier.armyBuinessProvince }">
														<option value="${prin.id }">${prin.name }</option>
													</c:if>
												</c:forEach>

											</select>
										</div>
										<div class="col-md-5 col-xs-5 col-sm-5 mr5 p0">
											<select id="children_area_select_id" name="armyBuinessCity" <c:if test="${fn:contains(audit,'armyBuinessCity')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('armyBuinessCity')"</c:if>>

												<c:forEach items="${currSupplier.armyCity }" var="city">
													<c:if test="${city.id==currSupplier.armyBuinessCity }">
														<option value="${city.id }" selected="selected">${city.name }</option>
													</c:if>
													<c:if test="${city.id!=currSupplier.armyBuinessCity }">
														<option value="${city.id }">${city.name }</option>
													</c:if>
												</c:forEach>
											</select>
										</div>
										<div class="cue"> ${err_armCity }</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 详细地址</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="armyBuinessAddress" required maxlength="50" value="${currSupplier.armyBuinessAddress}" <c:if test="${fn:contains(audit,'armyBuinessAddress')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('armyBuinessAddress')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空</span>
										<div class="cue"> ${err_armAddress } </div>
										<div class="cue">
											<sf:errors path="armyBuinessAddress" />
										</div>
									</div>
								</li>
							</ul>
						</fieldset>

						<fieldset class="col-md-12 border_font mt20">
							<legend>营业执照</legend>
							<ul class="list-unstyled f14">
								<li class="col-md-3 col-sm-6 col-xs-12 pl10">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 统一社会信用代码</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="creditCode" required maxlength="18" id="creditCode" onkeyup="value=value.replace(/[^\d]/g,'')" value="${currSupplier.creditCode}" <c:if test="${fn:contains(audit,'creditCode')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('creditCode')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，长度为18位</span>
										<div class="cue"> ${err_creditCide} </div>
										<div class="cue">
											<sf:errors path="creditCode" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 登记机关</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="registAuthority" required maxlength="20" value="${currSupplier.registAuthority}" <c:if test="${fn:contains(audit,'registAuthority')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('registAuthority')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，长度不可大于20位</span>
										<div class="cue"> ${err_reAuthoy } </div>
										<div class="cue">
											<sf:errors path="registAuthority" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 注册资本（人民币：万元）</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="registFund" onkeyup="checknums(this)" required value="${currSupplier.registFund}" <c:if test="${fn:contains(audit,'registFund')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('registFund')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，值不可小于零</span>
										<div class="cue" id="err_fund"> ${err_fund } </div>
										<div class="cue">
											<sf:errors path="registFund" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 营业有效期   <input type="checkbox" name="branchName" <c:if test="${currSupplier.branchName=='1'}"> checked='true'</c:if>   value="1"> 长期</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<fmt:formatDate value="${currSupplier.businessStartDate}" pattern="yyyy-MM-dd" var="businessStartDate" />
										<input type="text" readonly="readonly" onClick="WdatePicker()" name="businessStartDate" value="${businessStartDate}" <c:if test="${fn:contains(audit,'businessStartDate')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('businessStartDate')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">如果勾选长期,可不填写有效期</span>
										<div class="cue"> ${err_sDate } </div>
									</div>
								</li>

								<%-- 	    <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 营业截止时间</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
			   	<fmt:formatDate value="${currSupplier.businessEndDate}" pattern="yyyy-MM-dd" var="businessEndDate" />
		        <input type="text" readonly="readonly" onClick="WdatePicker()" name="businessEndDate" value="${businessEndDate}"   />
		        <span class="add-on cur_point">i</span>
		        <div class="cue"> ${err_eDate } </div>
	       	   </div>
		    </li>  --%>

								<%--     <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 生产经营地址</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input type="text" name="businessAddress" value="${currSupplier.businessAddress}" />
		        <span class="add-on cur_point">i</span>
		        <div class="cue"> ${err_bAddress } </div>
	       	   </div>
		    </li>  --%>

								<li class="col-md-3 col-sm-6 col-xs-12 pl10">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 邮编</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="businessPostCode" onkeyup="checknums(this)" required isZipCode="true" value="${currSupplier.businessPostCode}" <c:if test="${fn:contains(audit,'businessPostCode')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('businessPostCode')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，长度必须为6位</span>
										<div class="cue"> ${err_bCode } </div>
										<div class="cue">
											<sf:errors path="businessPostCode" />
										</div>
									</div>

								</li>

								<%--     <li class="col-md-3 col-sm-6 col-xs-12">
		     <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 营业执照:</span> 
				   <div class="col-md-12 col-sm-12 col-xs-12 p0 mb25">
					 <u:show showId="business_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" /> 
		   	   		 <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="business_up" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" auto="true" />
				   </div>
				   <div class="cue"> ${err_business } </div>
		    </li>  --%>

								<%--  	<li id="breach_li_id" class="col-md-3 col-sm-6 col-xs-12 mb25">
				   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 营业执照:</span> 
				   <div class="col-md-12 col-sm-12 col-xs-12 p0 h30">
					 <u:show showId="business_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" /> 
		   	   		 <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="business_up" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" auto="true" />
				   </div>
				   <div class="cue"> ${err_bearch } </div>
				</li>
				 --%>

								<li class="col-md-3 col-sm-6 col-xs-12 mb25"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5" <c:if test="${fn:contains(audit,'businessCert')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('businessCert')"</c:if>><i class="red">*</i> 营业执照</span>
									<div class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0">
										<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="business_up" maxcount="1" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" auto="true" />
										<u:show showId="business_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" />
									</div>
									<div class="cue"> ${err_business} </div>
								</li>

								<li class="col-md-12 col-xs-12 col-sm-12 mb25">
									<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> <i class="red">* </i>营业范围（按照营业执照上填写）</span>
									<div class="col-md-12 col-xs-12 col-sm-12 p0">
										<textarea class="col-md-12 col-xs-12 col-sm-12 h80" required="required" name="businessScope" <c:if test="${fn:contains(audit,'businessScope')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('businessScope')"</c:if>>${currSupplier.businessScope}</textarea>
										<div class="cue">
											<sf:errors path="businessScope" />
										</div>
									</div>
								</li>
							</ul>
						</fieldset>

						<h2 class="count_flow clear pt20"> <i>2</i> 境外信息</h2>
						<fieldset class="col-md-12 border_font mt20">
							<legend>境外分支</legend>
							<ul class="list-unstyled f14" id="list-unstyled">
								<li class="col-md-3 col-sm-6 col-xs-12 pl10">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red"></i>境外分支机构</span>
									<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
										<select name="overseasBranch" onchange="dis(this)" id="overseas_branch_select_id" <c:if test="${fn:contains(audit,'overseasBranch')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('overseasBranch')"</c:if>>
											<option value="0">无</option>
											<option value="1">有</option>
										</select>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5 white">操作</span>
									<div class="col-md-12 col-xs-12 col-sm-12 p0 mb25 h30">

									</div>
								</li>
								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5 white">操作</span>
									<div class="col-md-12 col-xs-12 col-sm-12 p0 mb25 h30">

									</div>
								</li>
								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5 white">操作</span>
									<div class="col-md-12 col-xs-12 col-sm-12 p0 mb25 h30">

									</div>
								</li>
								<div id="branch_list_body">
									<c:forEach items="${currSupplier.branchList }" var="bran" varStatus="vs">

										<li name="branch" style="display: none;" class="col-md-3 col-sm-6 col-xs-12 pl10">
											<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">* </i>机构名称</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input type="text" name="branchList[${vs.index }].organizationName" id="sup_branchName" required maxlength="50" value="${bran.organizationName}" />
												<span class="add-on cur_point">i</span>
												<span class="input-tip">不能为空</span>
												<div class="cue">
													<sf:errors path="branchList[${vs.index }].organizationName" />
												</div>
											</div>
										</li>

										<li name="branch" style="display: none;" class="col-md-3 col-sm-6 col-xs-12">
											<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5 "><i class="red">* </i>所在国家（地区）</span>
											<div class="select_common col-md-12 col-sm-12 col-xs-12  p0">
												<%-- 	<input name="branchList[${vs.index }].country" id="sup_country" type="text" value="${bran.country}" />
			        <span class="add-on cur_point">i</span> --%>
												<select name="branchList[${vs.index }].country" id="overseas_branch_select_id" required>
													<option value="">请选择</option>
													<c:forEach items="${foregin }" var="fr">
														<option value="${fr.id }" <c:if test="${bran.country==fr.id}">selected='selected' </c:if> >${fr.name }</option>
													</c:forEach>
												</select>
											</div>
										</li>

										<li name="branch" style="display: none;" class="col-md-3 col-sm-6 col-xs-12">
											<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">* </i>详细地址</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input type="text" name="branchList[${vs.index }].detailAddress" required maxlength="50" id="sup_branchAddress" value="${bran.detailAddress}" />
												<span class="add-on cur_point">i</span>
												<span class="input-tip">不能为空</span>
												<div class="cue">
													<sf:errors path="branchList[${vs.index }].detailAddress" />
												</div>
											</div>
										</li>

										<li name="branch" style="display: none;" class="col-md-3 col-sm-6 col-xs-12">
											<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5 white">操作</span>
											<div class="col-md-12 col-xs-12 col-sm-12 p0 mb25 h30">
												<input type="button" onclick="addBranch(this)" class="btn list_btn" value="十" />
												<input type="button" onclick="delBranch(this)" class="btn list_btn" value="一" />
											</div>
										</li>

										<li name="branch" style="display: none;" class="col-md-12 col-xs-12 col-sm-12 mb25">
											<span class="col-md-12 c ol-xs-12 col-sm-12 padding-left-5"><i class="red">* </i>生产经营范围</span>
											<div class="col-md-12 col-xs-12 col-sm-12 p0">
												<textarea class="col-md-12 col-xs-12 col-sm-12 h80" id="branchbusinessSope" required name="branchList[${vs.index }].businessSope">${bran.businessSope}</textarea>
												<div class="cue">
													<sf:errors path="branchList[${vs.index }].businessSope" />
												</div>
											</div>
										</li>
									</c:forEach>
								</div>
							</ul>
						</fieldset>
						<!-- 财务信息 -->
						<h2 class="count_flow clear pt20"> <i>3</i><font color=red>*</font> 近三年财务信息
	  <span class="red"> ${err_bearchFile}</span></h2>
						<div class="padding-top-10 clear" id="financeInfo">
							<c:forEach items="${currSupplier.listSupplierFinances}" var="finance" varStatus="vs">
								<h2 class="count_flow clear">${finance.year}年财务信息  <span style="float:right" class="b">（金额单位：万元）</span>  </h2>
								<div class="col-md-12 col-xs-12 col-sm-12 border_font">
									<!--   <legend>列表</legend> -->
									<div class="col-md-12 col-sm-12 col-xs-12 p0 over_auto">
										<div class="col-md-12 col-sm-12 col-xs-12 p0 tl">
											<%-- 	<button type="button" class="btn btn-windows add" onclick="openFinance(this,'${finance.year}')">维护</button> --%>
											<!-- 	<button type="button" class="btn btn-windows delete" onclick="deleteFinance()">删除</button> -->
											<span class="red"></span>
										</div>
										<div class="col-md-12 col-sm-12 col-xs-12 p0">
											<table class="table table-bordered table-condensed mt5 table_wrap table_input">
												<thead>
													<tr>
														<!-- 	infotd=$(obj).parent().next().children().children(":last"); <th class="w30 info"><input type="checkbox" onchange="checkAllForFinance(this)" />
								</th> -->
														<th class="w50 info">年份</th>
														<th class="info">会计事务所名称</th>
														<th class="info">事务所联系电话</th>
														<th class="info">审计人姓名</th>
														<!-- <th class="info">指标</th> -->
														<th class="info">资产总额</th>
														<th class="info">负债总额</th>
														<th class="info">净资产总额</th>
														<th class="info">营业收入</th>
													</tr>
												</thead>
												<tbody id="finance_list_tbody_id">
													<%--  <c:if test="${finance.year!=null}"> --%>
													<c:set var="infoId" value="${finance.id }_info"/>
													<tr <c:if test="${fn:contains(audit,infoId)}"> onmouseover="errorMsg('${infoId}')"</c:if>>
														<%-- <td class="tc">  <input type="checkbox" value="${finance.id}" />  
										</td> --%>
														<td class="tc" <c:if test="${fn:contains(audit,infoId)}">style="border: 1px solid #ef0000;"</c:if>>
															<input type="hidden" name="listSupplierFinances[${vs.index }].id" value="${finance.id}">
															<input type="text" required="required" class="w50 border0 tc" name="listSupplierFinances[${vs.index }].year" value="${finance.year}"> </td>
														<td class="tc" <c:if test="${fn:contains(audit,infoId)}">style="border: 1px solid #ef0000;"</c:if>>
															<input type="text" required="required" class="w200 border0" name="listSupplierFinances[${vs.index }].name" value="${finance.name}">
														</td>
														<td class="tc" <c:if test="${fn:contains(audit,infoId)}">style="border: 1px solid #ef0000;"</c:if>>
															<input type="text" required="required" class="w100 border0" name="listSupplierFinances[${vs.index }].telephone" onkeyup="value=value.replace(/[^\d-]/g,'')" value="${finance.telephone}">
														</td>
														<td class="tc" <c:if test="${fn:contains(audit,infoId)}">style="border: 1px solid #ef0000;"</c:if>>
															<input type="text" required="required" class="w200 border0" name="listSupplierFinances[${vs.index }].auditors" value="${finance.auditors}">

														</td>
														<%-- 	<td class="tc">${finance.quota}</td> --%>
														<td class="tc" <c:if test="${fn:contains(audit,infoId)}">style="border: 1px solid #ef0000;"</c:if>>
															<input type="text" required="required" class="w80 border0" onkeyup="checknums(this)" name="listSupplierFinances[${vs.index }].totalAssets" value="${finance.totalAssets}">

														</td>
														<td class="tc" <c:if test="${fn:contains(audit,infoId)}">style="border: 1px solid #ef0000;"</c:if>>
															<input type="text" required="required" class="w80 border0" onkeyup="checknums(this)" name="listSupplierFinances[${vs.index }].totalLiabilities" value="${finance.totalLiabilities}">
														</td>
														<td class="tc" <c:if test="${fn:contains(audit,infoId)}">style="border: 1px solid #ef0000;"</c:if>>
															<input type="text" required="required" class="w80 border0" onkeyup="checknums(this)" name="listSupplierFinances[${vs.index }].totalNetAssets" value="${finance.totalNetAssets}">
														</td>
														<td class="tc" <c:if test="${fn:contains(audit,infoId)}">style="border: 1px solid #ef0000;"</c:if>>
															<input type="text" required="required" class="w80 border0" onkeyup="checknums(this)" name="listSupplierFinances[${vs.index }].taking" value="${finance.taking}">
														</td>
													</tr>
													<%-- </c:if> --%>
												</tbody>
											</table>

											<table id="finance_attach_list_id" class="table table-bordered table-condensed mt5 table_wrap table_input">
												<thead>
													<tr>
														<!-- <th class="w30 info"><input type="checkbox" onchange="checkAllForFinance(this)" />
								</th> -->
														<th class="w50 info">年份</th>
														<th class="info">财务利润表</th>
														<th class="info">审计报告的审计意见</th>
														<th class="info">资产负债表</th>
														<th class="info">现金流量表</th>
														<th class="info">所有者权益变动表</th>
													</tr>
												</thead>
												<tbody id="finance_attach_list_tbody_id">
													<%-- <c:if test="${finance.year!=null}"> --%>
													<c:set var="file" value="${finance.id }_file"/>
													<tr <c:if test="${fn:contains(audit,file)}"> onmouseover="errorMsg('${file}')"</c:if>>
														<%-- <td class="tc"> <input type="checkbox" value="${finance.id}" /> 
									</td> --%>
														<td class="tc" <c:if test="${fn:contains(audit,file)}">style="border: 1px solid #ef0000;" </c:if>>${finance.year}</td>
														<td class="tc" <c:if test="${fn:contains(audit,file)}">style="border: 1px solid #ef0000;" </c:if>>
															<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="fina_${vs.index}_pro_up" multiple="true" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${finance.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierProfit}" auto="true" />

															<u:show showId="fina_${vs.index}_pro" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${finance.id}" typeId="${supplierDictionaryData.supplierProfit}" sysKey="${sysKey}" />
															<%-- 			 <a class="mt3 color7171C6" href="javascript:download('${finance.profitListId}', '${sysKey}')">${finance.profitList} </a> --%>
														</td>
														<td class="tc" <c:if test="${fn:contains(audit,file)}">style="border: 1px solid #ef0000;" </c:if>>

															<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="fina_${vs.index}_audit_up" multiple="true" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${finance.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierAuditOpinion}" auto="true" />
															<u:show showId="fina_${vs.index}_audit" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${finance.id}" typeId="${supplierDictionaryData.supplierAuditOpinion}" sysKey="${sysKey}" />

															<%-- <a class="mt3 color7171C6" href="javascript:download('${finance.liabilitiesListId}', '${sysKey}')">${finance.liabilitiesList}</a> --%>
														</td>
														<td class="tc" <c:if test="${fn:contains(audit,file)}">style="border: 1px solid #ef0000;" </c:if>>

															<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="fina_${vs.index}_lia_up" multiple="true" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${finance.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierLiabilities}" auto="true" />
															<u:show showId="fina_${vs.index}_lia" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${finance.id}" typeId="${supplierDictionaryData.supplierLiabilities}" sysKey="${sysKey}" />

														</td>

														<td class="tc" <c:if test="${fn:contains(audit,file)}">style="border: 1px solid #ef0000;" </c:if>>
															<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="fina_${vs.index}_cash_up" multiple="true" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${finance.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierCashFlow}" auto="true" />
															<u:show showId="fina_${vs.index}_cash" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${finance.id}" typeId="${supplierDictionaryData.supplierCashFlow}" sysKey="${sysKey}" />

															<%-- 	<a class="mt3 color7171C6" href="javascript:download('${finance.cashFlowStatementId}', '${sysKey}')">${finance.cashFlowStatement}</a>
 --%> </td>
														<td class="tc" <c:if test="${fn:contains(audit,file)}">style="border: 1px solid #ef0000;" </c:if>>

															<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="fina_${vs.index}_change_up" multiple="true" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${finance.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierOwnerChange}" auto="true" />
															<u:show showId="fina_${vs.index}_change" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${finance.id}" typeId="${supplierDictionaryData.supplierOwnerChange}" sysKey="${sysKey}" />

										</div>

										<%-- 									<a class="mt3 color7171C6" href="javascript:download('${finance.changeListId}', '${sysKey}')">${finance.changeList}</a>
 --%> </td>
										</tr>
										<%-- 	</c:if> --%>
										</tbody>
										</table>

									</div>
								</div>
						</div>
						<%--  <fieldset class="col-md-12 col-sm-12 col-xs-12 border_font mt20">
	 	    	   <legend>附件</legend>
	 	    	   <div class="col-md-12 col-sm-12 col-xs-12 p0">
					  <table id="finance_attach_list_id" class="table table-bordered table-condensed mt5">
						<thead>
							<tr>
								<th class="w30 info"><input type="checkbox" onchange="checkAllForFinance(this)" />
								</th>
								<th class="w50 info">年份</th>
								<th class="info">财务利润表</th>
								<th class="info">审计报告的审计意见</th>
								<th class="info">资产负债表</th>
								<th class="info">现金流量表</th>
								<th class="info">所有者权益变动表</th>
							</tr>
						</thead>
						<tbody id="finance_attach_list_tbody_id">
							<c:forEach items="${currSupplier.listSupplierFinances}" var="finance" varStatus="vs">
								<tr>
									<td class="tc"><input type="checkbox" value="${finance.id}" />
									</td>
									<td class="tc">${finance.year}</td>
									<td class="tc"><a class="mt3 color7171C6" href="javascript:download('${finance.auditOpinionId}', '${sysKey}')">${finance.auditOpinion}</a>
									</td>
									<td class="tc"><a class="mt3 color7171C6" href="javascript:download('${finance.liabilitiesListId}', '${sysKey}')">${finance.liabilitiesList}</a>
									</td>
									<td class="tc"><a class="mt3 color7171C6" href="javascript:download('${finance.profitListId}', '${sysKey}')">${finance.profitList}</a>
									</td>
									<td class="tc"><a class="mt3 color7171C6" href="javascript:download('${finance.cashFlowStatementId}', '${sysKey}')">${finance.cashFlowStatement}</a>
									</td>
									<td class="tc"><a class="mt3 color7171C6" href="javascript:download('${finance.changeListId}', '${sysKey}')">${finance.changeList}</a>
									</td>
								</tr>
							</c:forEach>
						  </tbody>
					  </table>
				</div>
			</fieldset> --%>
						</c:forEach>
					</div>

					<div class="padding-top-10 clear">
						<h2 class="count_flow clear pt20"> <i>4</i><font color=red>*</font> 出资人（股东）信息  （说明：出资人（股东）多于10人的，列出出资金额前十位的信息，但出资比例应高于50%）</h2>
						<div class="col-md-12 col-sm-12 col-xs-12 p0 ul_list mb50">
							<div class="col-md-12 col-sm-12 col-xs-12 p15 mt20">
								<div class="col-md-12 col-sm-12 col-xs-12 p0 mb5">
									<button class="btn btn-windows add" type="button" onclick="openStockholder()">新增</button>
									<button class="btn btn-windows delete" type="button" onclick="deleteStockholder()">删除</button>
									<span class="red">${stock }</span>
								</div>
								<div class="col-md-12 col-sm-12 col-xs-12 p0 over_auto">
									<table id="share_table_id" class="table table-bordered table-condensed mt5 table_wrap table_input left_table">
										<thead>
											<tr>
												<th class="info"><input type="checkbox" onchange="checkAll(this, 'stockholder_list_tbody_id')" />
												</th>
												<th class="info">出资人性质</th>
												<th class="info">出资人名称或姓名</th>

												<th class="info">统一社会信用代码或身份证号码</th>
												<th class="info">出资金额或股份（万元/万份）</th>
												<th class="info">比例（%）</th>
											</tr>
										</thead>
										<tbody id="stockholder_list_tbody_id">
											<c:forEach items="${currSupplier.listSupplierStockholders}" var="stockholder" varStatus="stockvs">
												<tr <c:if test="${fn:contains(audit,stockholder.id)}"> onmouseover="errorMsg('${stockholder.id}')"</c:if>>
													<input type="hidden" name='listSupplierStockholders[${stockvs.index }].id' value="${stockholder.id}" />
													<input type="hidden" name='listSupplierStockholders[${stockvs.index }].supplierId' value="${stockholder.supplierId}" />
													<td class="tc" <c:if test="${fn:contains(audit,stockholder.id)}">style="border: 1px solid #ef0000;" </c:if>><input type="checkbox" value="${stockholder.id}" />
													</td>
													<td class="tc" <c:if test="${fn:contains(audit,stockholder.id)}">style="border: 1px solid #ef0000;" </c:if>>
														<select name="listSupplierStockholders[${stockvs.index }].nature" class="w100p border0">
															<option value="1" <c:if test="${stockholder.nature==1}"> selected="selected"</c:if> >法人</option>
															<option value="2" <c:if test="${stockholder.nature==2}"> selected="selected" </c:if> >自然人</option>
														</select>

													</td>

													<td class="tc" <c:if test="${fn:contains(audit,stockholder.id)}">style="border: 1px solid #ef0000;" </c:if>> <input type='text' style='border:0px;' name='listSupplierStockholders[${stockvs.index }].name' value='${stockholder.name}'> </td>

													<td class="tc" <c:if test="${fn:contains(audit,stockholder.id)}">style="border: 1px solid #ef0000;" </c:if>> <input type='text' style='border:0px;' name='listSupplierStockholders[${stockvs.index }].identity' onkeyup="value=value.replace(/[^\d-]/g,'')" value='${stockholder.identity}'> </td>
													<td class="tc" <c:if test="${fn:contains(audit,stockholder.id)}">style="border: 1px solid #ef0000;" </c:if>> <input type='text' style='border:0px;' name='listSupplierStockholders[${stockvs.index }].shares' value='${stockholder.shares}'> </td>
													<td class="tc" <c:if test="${fn:contains(audit,stockholder.id)}">style="border: 1px solid #ef0000;" </c:if>> <input type='text' style='border:0px;' name='listSupplierStockholders[${stockvs.index }].proportion' value='${stockholder.proportion}'></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>

		<input type="hidden" id="index" value="${fn:length(currSupplier.addressList)}">
		<input type="hidden" id="branchIndex" value="${fn:length(currSupplier.branchList)}">
		<input type="hidden" id="stockIndex" value="${fn:length(currSupplier.listSupplierStockholders)}">
		<div class="btmfix">
			<div style="margin-top: 15px;text-align: center;">
				<button type="button" class="btn save" onclick="temporarySave();">暂存</button>
				<button type="button" class="btn" onclick="saveBasicInfo('1')">下一步</button>
			</div>
		</div>
	</body>

</html>