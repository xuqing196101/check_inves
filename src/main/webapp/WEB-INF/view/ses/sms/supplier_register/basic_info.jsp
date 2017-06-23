<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/reg_head.jsp"%>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<title>供应商注册</title>
		<style type="text/css">
			.current{
				cursor:pointer;
			}
			.sm_tip{
				color: gray;
				font-size: 14px;
				font-weight: normal;
				margin-top: 5px;
			}
		</style>
		<%@ include file="/WEB-INF/view/common/validate.jsp"%>
		<script type="text/javascript">
			$().ready(function() {
				$("#basic_info_form_id").validForm();
			});
			
			$(function() {
				
				/* var term="${currSupplier.branchName}";
				if(term=="1"){
					$("#expireDate").attr("disabled","disabled");
				} */
				var card="${notPass}";
				if(card=="error_card"){ 
					layer.msg("身份证号已存在！");
				}
				var notPass = "${notPass}";
				if (notPass == "notPass") {
					layer.msg("近3年加权平均净资产不满足注册要求！");
				} else {
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
					autoSelected("nature_select_id", "${currSupplier.businessNature}");
					autoSelected("overseas_branch_select_id", "${currSupplier.overseasBranch}");
					if($("#overseas_branch_select_id").val() == "1") {
						$("li[name='branch']").show();
					}
					autoSelected("isHavingConCert", "${currSupplier.isHavingConCert}");
					if($("#isHavingConCert").val() == "1") {
						$("#bearchCertDiv").show();
					} else {
						$("#bearchCertDiv").hide();
					}

					if("${currSupplier.status}" == 7) {
						showReason();
					}
					if("${currSupplier.overseasBranch}" == '0' || "${currSupplier.overseasBranch}" == null) {
						$("li[name='branch']").hide();
					}
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
				if(id==""){
					var select = $(obj).parent().next().children();
					$(select).empty();
				}
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
//                //进行出资金额或股份的数据校验
//                var _count = 0;
//                var _inputArray = $('#stockholder_list_tbody_id').find('tr').find("td:eq(4)").find('input');
//                var inputSize = _inputArray.length;
//                for(var i=0;i<inputSize;i++){
//                    var _val = _inputArray[i].value;
//                    if(!positiveRegular(_val)){
//                        _count ++;
//                    }
//                }
//                if(_count > 0){
//                    layer.msg('请输入正确的出资金额或股份数据格式(正整数)', {offset: '300px'});
//                    return false;
//                }
				var supplierId = $("input[name='id']").val();
				var msg = "";
				var flag = true;

				$("#address_list_body").find("input[type='text']").each(function(index, element) {
					if(element.value.trim().length <= 0) {
						msg = "地址信息不能为空!";
						flag = false;
					}
				});
				if($("#overseas_branch_select_id").val() == "1") {
					// 非空校验
					$("#branch_list").find("input[type='text']").each(function(index, element) {
                        if(element.value.trim().length <= 0) {
							msg = "境外信息不能为空！";
							flag = false;
						}
					});
					// 非空校验
					$(".cBranchName").each(function(index, element) {
						if(element.value.trim().length <= 0) {
							msg = "境外分支机构名称不能为空！";
							flag = false;
						}
					});
          $(".cOverseas").each(function(index, element) {
              if(element.value.trim().length <= 0) {
                  msg = "境外分支所属国家（地区）不能为空！";
                  flag = false;
              }
          });
          $(".cDetailAdddress").each(function(index, element) {
              if(element.value.trim().length <= 0) {
                  msg = "境外分支详细地址不能为空！";
                  flag = false;
              }
          });
          $(".cPrdArea").each(function(index, element) {
              if(element.value.trim().length <= 0) {
                  msg = "境外分支生产经营范围不能为空！";
                  flag = false;
              }
          });
				}
				// 非空校验
				$("#financeInfo").find("input[type='text']").each(function(index, element) {
					if(element.value.trim().length <= 0) {
						msg = "近三年财务信息不能为空!";
						flag = false;
					}
				});
				// 事务所联系方式格式校验
				$("#financeInfo").find("input[name$='telephone']").each(function(index, element) {
					if(element.value.trim().length <= 0) {
						msg = "近三年财务信息不能为空!";
						flag = false;
					}
				});
				// 出资人（股东）信息比例之和要大于50%(old)
				// 如果数量不超过10个，那占比必须100%，如果数量超过10个，那占比必须高于50%
				var proportionTotal = 0;// 出资比例之和
				var stockholderCount = 0;// 股东数量
				$("input[name^='listSupplierStockholder'][name$='proportion']").each(function(){
					proportionTotal += parseFloat($(this).val());
					stockholderCount++;
				});
				if(proportionTotal !=0 && stockholderCount != 0){
					if(stockholderCount >= 10 && proportionTotal < 50){
						msg = "出资人10个或以上，出资比例之和要高于50%！";
						flag = false;
					}
					proportionTotal = proportionTotal.toFixed(2);
					if(stockholderCount < 10 && proportionTotal != 100.00){
						msg = "出资人不超过10个，出资比例之和必须为100%！";
						flag = false;
					}
				}
				
				// 校验信用代码
				var creditCodeValue = $("#creditCode").val();
				if(creditCodeValue == ""){
					msg = "信用代码不能为空!";
					flag = false;
				}
				if(!checkCreditCode($("#creditCode").val())){
					return false;
				}
				
				if(flag) {
					$("input[name='flag']").val(obj);
					// 提交的时候表单域设置成可编辑
					enableForm();
					$("#basic_info_form_id").submit();
				} else {

					layer.msg(msg, {
						offset: '300px'
					});
				}
			}
			
			// 统一社会信用代码校验
			// 18位数字或18位数字+字母
			function checkCreditCode(creditCodeValue){
				if(creditCodeValue != ""){
					var bool = false;
					if(/[0-9]{18}/.test(creditCodeValue)){// 18位全数字
						bool = true;
					}
					if(/^([a-zA-Z0-9]){18}$/.test(creditCodeValue)){// 18位数字+字母
						if(/^([a-zA-Z])+$/.test(creditCodeValue)){// 全字母
							bool = false;
						}else{
							bool = true;
						}
					}
					if(!bool){
						var msg = "信用代码18位，请按照实际社会信用代码填写!";
						layer.msg(msg);
					}
					return bool;
				}
			}

			/** 暂存 */
			function temporarySave() {
			    //进行出资金额或股份的数据校验
//                var _count = 0;
//                var _inputArray = $('#stockholder_list_tbody_id').find('tr').find("td:eq(4)").find('input');
//                var inputSize = _inputArray.length;
//                for(var i=0;i<inputSize;i++){
//                    var _val = _inputArray[i].value;
//                    if(!positiveRegular(_val)){
//                        _count ++;
//                    }
//                }
//                if(_count > 0){
//                    layer.msg('请输入正确的出资金额或股份数据格式(正整数)', {offset: '300px'});
//                }else{
                    $("input[name='flag']").val("");
                    // 提交的时候表单域设置成可编辑
										enableForm();
                    $.ajax({
                        url: "${pageContext.request.contextPath}/supplier/temporarySave.do",
                        type: "post",
                        data: $("#basic_info_form_id").serializeArray(),
                        contextType: "application/x-www-form-urlencoded",
                        success: function(msg) {
                        	controlForm();
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
                        },
												error: function(){
													controlForm();
												}
                    });
//                }
			}
			$(function() {
				$("input").not("#supplierName_input_id").not(".address_zip_code").bind("blur", tempSave);
//			$("input").not("#supplierName_input_id").not("input[name='legalName']").not("input[name='contactName']").bind("blur", tempSave);
				$("textarea").bind("blur", tempSave);
				$("select").bind("change", tempSave);
				/**供应商名称校验*/
                $("#supplierName_input_id").focus(function(){
                    $(this).attr("data-oval",$(this).val()); //将当前值存入自定义属性
                }).blur(function(){
                    var oldVal=($(this).attr("data-oval")); //获取原值
                    var newVal=($(this).val()); //获取当前值
                    if (oldVal!=newVal){
                        $("#name_span").val(1);
                        tempSave();
                    }
                });
				/**邮编校验*/
                $(".address_zip_code").focus(function(){
                    $(this).attr("data-oval",$(this).val()); //将当前值存入自定义属性
                }).blur(function(){
                    var oldVal=($(this).attr("data-oval")); //获取原值
                    var newVal=($(this).val()); //获取当前值
                    if (oldVal!=newVal){
                        var tel = /^[0-9]{6}$/;
                        if(!tel.test(newVal)){
                            $(this).val("");
                            layer.msg('请输入正确的邮政编码！', {
                                offset: '300px'
                            });
                        }else{
                            tempSave();
                        }
                    }
                });
                /**联系人姓名校验*/
//                $("input[name='contactName']").focus(function(){
//                    $(this).attr("data-oval",$(this).val()); //将当前值存入自定义属性
//                }).blur(function(){
//                    var oldVal=($(this).attr("data-oval")); //获取原值
//                    var newVal=($(this).val()); //获取当前值
//                    if (oldVal!=newVal){
//                        if(newVal==$("input[name='legalName']").val()){
//                            $(this).val("");
//                            layer.msg('姓名已存在，请重新填写！', {
//                                offset: '300px'
//                            });
//                        }else{
//                            $("#name_span").val(3);
//                            tempSave();
//                        }
//                    }
//                });
			});
			/** 无提示实时保存 */
			function tempSave() {
				$("input[name='flag']").val("");
				enableForm();
				$.ajax({
					url: "${pageContext.request.contextPath}/supplier/temporarySave.do",
					type: "post",
					async: false,
					data: $("#basic_info_form_id").serializeArray(),
					contextType: "application/x-www-form-urlencoded",
					success: function(msg) {
						controlForm();
            $("#name_span").val("");//名称校验标识初始化
						if(msg=="notPass"){
							layer.msg('近3年加权平均净资产不足100万元，不满足注册要求！', {
								offset: '300px'
							});
						}
						if(msg=="repeat"){
					 		$("input[name='creditCode']").val("");
							layer.msg('统一社会信用代码重复，请重新填写！', {
								offset: '300px'
							});
						}
						if(msg=="disabled_180"){
              $("input[name='creditCode']").val("");
              layer.msg('统一社会信用代码在180天内禁止再次注册，请重新填写！', {
                  offset: '300px'
              });
            }
						if(msg=="errIdentity"){
							layer.msg('统一社会信用代码或身份证号重复，请重新填写！', {
								offset: '300px'
							});
						}
						if(msg=="supplierNameExists"){
					    $("#supplierName_input_id").val("");
             	layer.msg('供应商名称已存在，请重新填写！', {
               	offset: '300px'
              });
            }
//                        if(msg=="legalNameExists"){
//                            $("input[name='legalName']").val("");
//                            layer.msg('姓名已存在，请重新填写！', {
//                                offset: '300px'
//                            });
//                        }
//                        if(msg=="contactNameExists"){
//                            $("input[name='contactName']").val("");
//                            layer.msg('姓名已存在，请重新填写！', {
//                                offset: '300px'
//                            });
//                        }
					},
					error: function(){
						controlForm();
					}
				});
			}

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
					"<td class='tc'>  <select class='w100p border0'onchange='tempSave()' name='listSupplierStockholders[" + stocIndex + "].nature'>" +
					"<option value='1'>法人</option>" +
					" <option value='2'>自然人</option>" +
					"</select> </td>" +
					"<td class='tc'><input type='text' style='border:0px;' maxlength='50'  onblur='tempSave()' name='listSupplierStockholders[" + stocIndex + "].name' value=''> </td>" +
					"<td class='tc'><input type='text' style='border:0px;'  onblur='tempSave()' name='listSupplierStockholders[" + stocIndex + "].identity' maxlength='18' onkeyup='validateIdentity(this)' value=''> </td>" +
					"<td class='tc'> <input type='text' style='border:0px;'  onblur='tempSave()' name='listSupplierStockholders[" + stocIndex + "].shares' value=''></td>" +
					"<td class='tc'> <input type='text' style='border:0px;' class='proportion_vali' onblur='tempSave()' name='listSupplierStockholders[" + stocIndex + "].proportion' value=''> </td>" + "</tr>");

				stocIndex++;
				$("#stockIndex").val(stocIndex);
                loadProportion();
			}
			
			function validateIdentity(obj){
				$(obj).val($(obj).val().replace(/[^\d|a-zA-Z]/g,''));
			}
			
			function openAfterSaleDep() {

				var afterSaleIndex = $("#afterSaleIndex").val();
				var supplierId = $("input[name='id']").val();
				var id;
				$.ajax({
					url: "${pageContext.request.contextPath}/supplier/getUUID.do",
					async: false,
					success: function(data) {
						id = data;
					}
				});
                var _onkeyup="value=value.replace(/[^\\d-]/g,\"\")";
				$("#afterSaleDep_list_tbody_id").append("<tr>" +
					"<td class='tc'><input type='checkbox' value='" + id + "' /><input type='hidden' name='listSupplierAfterSaleDep[" + afterSaleIndex + "].id' value='" + id + "'><input type='hidden' style='border:0px;' name='listSupplierAfterSaleDep[" + afterSaleIndex + "].supplierId' value=" + supplierId + ">" +
					"</td>" +
					"<td class='tc'><input type='text' style='border:0px;' onblur='tempSave()' name='listSupplierAfterSaleDep[" + afterSaleIndex + "].name' maxlength='90' value=''> </td>" +
					"<td class='tc'> <div class='w120 fl'> <select onchange='tempSave()' class='w100p border0' name='listSupplierAfterSaleDep[" + afterSaleIndex + "].type'>" +
					"<option value='1'>自营</option>" +
					" <option value='2'>合作</option>" +
					"</select></div> </td>" +
					"<td class='tc'><input type='text' onblur='tempSave()' style='border:0px;' name='listSupplierAfterSaleDep[" + afterSaleIndex + "].address' maxlength='30' value=''> </td>" +
					"<td class='tc'> <input type='text' onblur='tempSave()' style='border:0px;' name='listSupplierAfterSaleDep[" + afterSaleIndex + "].leadName' maxlength='20' value=''></td>" +
					"<td class='tc'> <input type='text' onblur='tempSave()' style='border:0px;' onkeyup='"+_onkeyup+"' name='listSupplierAfterSaleDep[" + afterSaleIndex + "].mobile' value=''> </td>" + "</tr>");

				afterSaleIndex++;
				$("#afterSaleIndex").val(afterSaleIndex);
			}
			
			function deleteAfterSaleDep() {
				var all = $("#afterSaleDep_list_tbody_id").find(":checkbox");
				var checkboxs = $("#afterSaleDep_list_tbody_id").find(":checkbox:checked");
				
				if(checkboxs.length == all.length){
					layer.msg("售后服务机构请至少保留一条信息！");
					return;
				}
				
				var size = checkboxs.length;
				if(size > 0) {
					
					// 退回修改审核通过的项不能删除
					var isDel = checkIsDelForTuihui(checkboxs, '${audit}');
					if(!isDel){
						layer.msg("审核通过的项不能删除！");
						return;
					}
					
					var afterSaleDepIds = "";
					$(checkboxs).each(function(index) {
						if(index > 0) {
							afterSaleDepIds += ",";
						}
						afterSaleDepIds += $(this).val();
					});
				
					$.ajax({
						url: "${pageContext.request.contextPath}/supplier/deleteAfterSaleDep.do",
						async: false,
						type: "POST",
						data: {
							"afterSaleDepIds": afterSaleDepIds,
						},
						success: function(){
							layer.msg("删除成功！");
							$(checkboxs).each(function(index) {
								var tr = $(this).parent().parent();
								$(tr).remove();
							});
						},
						error: function(){
							layer.msg("删除失败！");
						}
					});
				} else {
					layer.alert("请至少勾选一条记录 !", {
						offset: '200px',
						scrollbar: false,
					});
				}
			}

			function deleteStockholder() {
				var all = $("#stockholder_list_tbody_id").find(":checkbox");
				var checkboxs = $("#stockholder_list_tbody_id").find(":checkbox:checked");
				
				if(checkboxs.length == all.length){
					layer.msg("出资人（股东）信息请至少保留一条！");
					return;
				}
				
				var size = checkboxs.length;
				if(size > 0) {
				
					// 退回修改审核通过的项不能删除
					var isDel = checkIsDelForTuihui(checkboxs, '${audit}');
					if(!isDel){
						layer.msg("审核通过的项不能删除！");
						return;
					}
					
					// 如果数量不超过10个，那占比必须100%，如果数量超过10个，那占比必须高于50%
					var proportionTotal = 0;// 出资比例之和
					var stockholderCount = 0;// 股东数量
					$("input[name^='listSupplierStockholder'][name$='proportion']").each(function(){
						if(!($(this).parent().parent().find(":checkbox").is(":checked"))){
							proportionTotal += parseFloat($(this).val());
							stockholderCount++;
						}
					});
					var confirmMsg = "确认删除？";
					if(proportionTotal !=0 && stockholderCount != 0){
						if(stockholderCount >= 10 && proportionTotal < 50){
							confirmMsg = "出资人10个或以上，出资比例之和要高于50%！确认删除？";
						}
						if(stockholderCount < 10 && proportionTotal != 100){
							confirmMsg = "出资人不超过10个，出资比例之和必须为100%！确认删除？";
						}
					}
					
					layer.confirm(confirmMsg, {
						offset: '200px',
						scrollbar: false,
						btn: ['确定','取消'] //按钮
					}, function(index) {
						var stockholderIds = "";
						var supplierId = $("input[name='id']").val();		
						$(checkboxs).each(function(index) {
							if(index > 0) {
								stockholderIds += ",";
							}
							stockholderIds += $(this).val();
						});
					
						$.ajax({
							url: "${pageContext.request.contextPath}/supplier_stockholder/delete_stockholder.do",
							async: false,
							type: "POST",
							data: {
								"stockholderIds": stockholderIds,
								"supplierId": supplierId
							},
							success: function(){
								layer.msg("删除成功！");
								$(checkboxs).each(function(index) {
									var tr = $(this).parent().parent();
									$(tr).remove();
								});
							},
							error: function(){
								layer.msg("删除失败！");
							}
						});
						layer.close(index);
					}, function(index) {
						layer.close(index);
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
			
			// 去除请选择选项
			function removeOption(obj) {
				$(obj).find("option").each(function(i, element){
					if (element.value == "") {
						$(element).remove();
					}
				});
			}

			function dis(obj) {
				var vals = $(obj).val();
				if(vals == 1) {
					$("li[name='branch']").show();
				} else {
					$("li[name='branch']").hide();
				}
			}
			
			// 控制保密证书的显示与隐藏
			function dis_bearch(obj){
				if ($(obj).val() == '0') {
					$("#bearchCertDiv").hide();
				} else {
					$("#bearchCertDiv").show();
					init_web_upload();
				}
			}

			function checknums(obj) {
				var vals = $(obj).val();
				if(vals!=""){
                    var reg = /^\d+\.?\d*$/;
                    if(!reg.test(vals)) {
                        $(obj).val("");
                        $("#err_fund").text("数字非法");
                        //解决多提示信息显示问题
                        $(obj).nextAll().last().html("");
                    } else {
                        $("#err_fund").text();
                        $("#err_fund").empty();
                    }
                }
			}
			/**对于金额的小数判断*/
			function checkNumsSale(obj,nonNum){
			    var _val = $(obj).val();
			    if(_val!="" && nonNum!=3){//如果可以为负数的话设置3;净资产总额不进行负数校验
			        if(parseInt(_val)<0){
                        $(obj).val("");
                        layer.msg("请输入正确的金额,非负数保留四位小数", {
                            offset: '300px'
                        });
                        return false;
                    }
                }
                if(_val.indexOf('.')!=-1){
                    var reg = /\d+\.\d{0,2}?$/;
                    if(!reg.test(_val)) {
                        $(obj).val("");
                        if(nonNum==3){
                            layer.msg("请输入正确的金额,保留两位小数", {
                                offset: '300px'
                            });
                        }else{
                            layer.msg("请输入正确的金额,非负数保留两位小数", {
                                offset: '300px'
                            });
                        }
                    }
                }else{
                    if(!positiveRegular(_val)){
                        $(obj).val("");
                        if(nonNum==3){
                            layer.msg("请输入正确的金额,保留两位小数", {
                                offset: '300px'
                            });
                        }else{
                            layer.msg("请输入正确的金额,非负数保留两位小数", {
                                offset: '300px'
                            });
                        }
                    }
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
						$(li).after("<li class='col-md-2 col-sm-6 col-xs-12 pl10'>" +
								"<span class='col-md-12 col-xs-12 col-sm-12  padding-left-5'><i class='red'>*</i> 生产或经营地址邮编</span>" +
								"<div class='input-append col-md-12 col-sm-12 col-xs-12 input_group p0'>" +
								"<input type='text' name='addressList[" + ind + "].code' value='' / onblur='tempSave()'>" +
								"<input type='hidden' name='addressList[" + ind + "].id' value='" + id + "' / onblur='tempSave()'>" +
								"<span class='add-on cur_point'>i</span>" +
								" <div class='cue'> </div>" +
								"</div>" +
								"</li> " +
								"<li class='col-md-3 col-sm-6 col-xs-12'>" +
								"<span class='col-md-12 col-xs-12 col-sm-12 padding-left-5'><i class='red'>*</i> 生产或经营地址（填写所有地址）</span>" +
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

								" <li class='col-md-2 col-sm-6 col-xs-12'>" +
								"<span class='col-md-12 col-xs-12 col-sm-12 padding-left-5'><i class='red'>*</i> 生产或经营详细地址</span>" +
								" <div class='input-append col-md-12 col-sm-12 col-xs-12 input_group p0'>" +
								"<input type='text' name='addressList[" + ind + "].detailAddress'  value='' onblur='tempSave()'>" +
								"<span class='add-on cur_point'>i</span>" +
								"<div class='cue'>  </div>" +
								"</div>" +
								"</li>" +
								"<li class='col-md-2 col-sm-6 col-xs-12'>" +
								"	<span class='col-md-12 col-xs-12 col-sm-12 padding-left-5 white'>操作</span>" +
								"<div class='col-md-12 col-xs-12 col-sm-12 p0 mb25 h30'>" +
								"	<input type='button' onclick='increaseAddress(this)' class='btn list_btn' value='十'/>" +
								"	<input type='button' onclick='delAddress(this)' class='btn list_btn' value='一'/>" +
								"	<input type='hidden'  value='" + id + "' />" +
								"</div></li>"
							);
							ind++;
							$("#index").val(ind);
					}
				});
				
			}
			function increaseAddHouseAddress(obj) {
				var ind = $("#certSaleNumber").val();
				var li = $(obj).parent().parent();
                $.ajax({
                    url : "${pageContext.request.contextPath}/supplier/addAddress.do",
                    async : false,
                    dataType : "html",
                    data : {
                        "ind" : ind
                    },
                    success : function(data) {
                        $("#address_list_tbody_id").append(data);
                        init_web_upload();
                        ind++;
                        $("#certSaleNumber").val(ind);
                    }
                });
			}
			function delAddress(obj,id) {
        var all = $("#address_list_tbody_id").find(":checkbox");
        var checkboxs = $("#address_list_tbody_id").find(":checkbox:checked");
        
        if(checkboxs.length == all.length){
					layer.msg("生产或经营地址请至少保留一条信息！");
					return;
				}
        
        var size = checkboxs.length;
        if(size > 0) {
        	
        	// 退回修改审核通过的项不能删除
					var isDel = checkIsDelForTuihui(checkboxs, '${audit}');
					if(!isDel){
						layer.msg("审核通过的项不能删除！");
						return;
					}
					
					var addressIds = "";
					$(checkboxs).each(function(index) {
						if(index > 0) {
	            addressIds += ",";
	          }
	          addressIds += $(this).val();
					});
        	
          $.ajax({
            url: "${pageContext.request.contextPath}/supplier/delAddress.do",
            async: false,
            type: "POST",
            data: {
              "id": addressIds
            },
            success: function(data) {
              if(data=="ok"){
                layer.msg("删除成功!", {
                  offset: '300px'
                });
                $(checkboxs).each(function(index) {
                  var tr = $(this).parent().parent();
                  $(tr).remove();
                });
              }
            },
            error: function() {
              layer.msg("删除失败!", {
                offset: '300px'
              });
            }
        	});
        }else{
          layer.alert("请至少勾选一条记录 !", {
            offset: '200px',
            scrollbar: false,
          });
        }
			}

			function addBranch(obj) {
				var branId="";
				$.ajax({
					url:"${pageContext.request.contextPath}/supplier/getId.do",
					type:"post",
					success:function(data){
						branId=data;
						var li = $(obj).parent().parent().next();
						var inde = $("#branchIndex").val();
						$(li).after("<li name='branch' class='col-md-3 col-sm-6 col-xs-12'>" +
							" <span class='col-md-12 col-xs-12 col-sm-12 padding-left-5'><i class='red'>* </i>机构名称</span>" +
							" <div class='input-append col-md-12 col-sm-12 col-xs-12 input_group p0'>" +
							//" <input type='hidden' name='branchList[" + inde + "].id'   value='"+branId+"' />" +
							" <input class ='cBranchName' type='text' name='branchList[" + inde + "].organizationName' id='sup_branchName'  value='' / onblur='tempSave()'>" +
							"   <span class='add-on cur_point'>i</span>" +
							"   </div>" +
							"  </li>" +
							"<li name='branch'  class='col-md-3 col-sm-6 col-xs-12'>" +
							" <span class='col-md-12 col-xs-12 col-sm-12 padding-left-5'><i class='red'>* </i>所在国家（地区）</span>" +
							"  <div class='select_common col-md-12 col-sm-12 col-xs-12 input_group p0'>" +
							"<select  class ='cOverseas' name='branchList[" + inde + "].country'  id='overseas_branch_select_id' onchange='tempSave()'>" +
							"<option value=''>请选择</option>"+
							"<c:forEach items='${foregin }' var='fr'>" +
							"<option value='${fr.id }' <c:if test='${bran.country==fr.id}'> selected='selected' </c:if> >${fr.name }</option>" +
							" </c:forEach> 	</select>" +
							" </div>" +
							" </li>" +

							"  <li name='branch'  class='col-md-3 col-sm-6 col-xs-12'>" +
							" <span class='col-md-12 col-xs-12 col-sm-12 padding-left-5'><i class='red'>* </i>详细地址</span>" +
							" <div class='input-append col-md-12 col-sm-12 col-xs-12 input_group p0'>" +
							" <input  class ='cDetailAdddress' type='text' name='branchList[" + inde + "].detailAddress'  id='sup_branchAddress' value='' / onblur='tempSave()'>" +
							"  <span class='add-on cur_point'>i</span>" +
							" </div>" +
							" </li>" +

							" <li name='branch'  class='col-md-3 col-sm-6 col-xs-12'>" +
							" <span class='col-md-12 col-xs-12 col-sm-12 padding-left-5 white'>操作</span>" +
							" <div class='col-md-12 col-xs-12 col-sm-12 p0 mb25 h30'>" +
							" <input type='button' onclick='addBranch(this)' class='btn list_btn' value='十'/>" +
							" <input type='button' onclick='delBranch(this)'class='btn list_btn' value='一'/>" +
							" <input type='hidden' name='branchList[" + inde + "].id'   value='"+branId+"' />" +
							" </div>" +
							" </li>" +

							"  <li name='branch'  class='col-md-12 col-xs-12 col-sm-12 mb25'>" +
							" <span class='col-md-12 col-xs-12 col-sm-12 padding-left-5'><i class='red'>* </i>生产经营范围</span>" +
							" <div class='col-md-12 col-xs-12 col-sm-12 p0'>" +
							" <textarea class='cPrdArea col-md-12 col-xs-12 col-sm-12 h80' maxlength='1000' onkeyup=\"checkCharLimit('branchbusinessSope_"+inde+"','limit_char_branchbusinessSope_"+inde+"',1000);if(value.length==1000){layer.msg('字数过多，不可超过1000字！')}\" id='branchbusinessSope_"+inde+"' onblur='tempSave()' name='branchList[" + inde + "].businessSope'></textarea>" +
							" <span class='sm_tip fr'>还可输入 <span id='limit_char_branchbusinessSope_"+inde+"'>1000</span> 个字</span>" +
							" </div>" +
							" </li>");
						inde++;
						$("#branchIndex").val(inde);
					}
				});
				
			}

			function delBranch(obj) {
			
				// 退回修改状态
				var currSupplierSt = '${currSupplier.status}';
				if(currSupplierSt == '2'){
					var thisLi = $(obj).parents("li[name='branch']");
					var branchId = thisLi.find("input[name^='branchList'][name$='id']");
					branchId = branchId.val();
					//alert('${audit}');
					if('${audit}'.indexOf("organizationName_"+branchId) < 0
						&& '${audit}'.indexOf("countryName_"+branchId) < 0
						&& '${audit}'.indexOf("detailAddress_"+branchId) < 0){
						layer.msg("审核通过项不能删除!");
						return;
					}
				}
			
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
				var id=$(obj).next().val();
				if(id){
					$.ajax({
						url:"${pageContext.request.contextPath}/supplier/deleteBranch.do",
						type:"post",
						data:{"id":id},
						success:function(data){
							
						}
					});
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
						layer.msg("不通过理由：" + data.suggest, {
							offset: '300px'
						});
					}
				});
			}
			sessionStorage.locationA=true;
			sessionStorage.index=1;
			
			function check(obj){
				var ch=$(obj).is(":checked");
				if(ch){
					$(obj).val("1");
					$("#expireDate").val("");
					$("#expireDate").attr("disabled","disabled");
				}else{
					$(obj).val("0");
					$("#expireDate").removeAttr("disabled","disabled");
				}
			}
			
		</script>
	</head>

	<body>
		<div class="wrapper">
			<!-- 项目戳开始 -->
			<div class="container clear margin-top-30">
				<h2 class="step_flow">
					<span id="sp1" class="new_step current fl"><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span>
		            <span id="sp2" class="new_step fl"><i class="">2</i><div class="line"></div> <span class="step_desc_01">供应商类型</span> </span>
		            <span id="ty3" class="new_step fl"><i class="">3</i><div class="line"></div> <span class="step_desc_02">产品类别</span> </span>
		            <span id="sp4" class="new_step fl"><i class="">4</i><div class="line"></div> <span class="step_desc_01">资质文件维护</span> </span>
		            <span id="sp5" class="new_step fl"><i class="">5</i><div class="line"></div> <span class="step_desc_02">销售合同</span> </span>
		            <span id="sp6" class="new_step fl"><i class="">6</i><div class="line"></div> <span class="step_desc_01">采购机构</span> </span>
		            <span id="sp7" class="new_step fl"><i class="">7</i><div class="line"></div> <span class="step_desc_02">承诺书和申请表</span> </span>
		            <span id="sp8" class="new_step fl new_step_last"><i class="">8</i> <span class="step_desc_01">提交审核</span> </span>
		            <div class="clear"></div>
				</h2>
			</div>
			<!--基本信息-->
			<div class="container container_box">
				<form id="basic_info_form_id" action="${pageContext.request.contextPath}/supplier/perfect_basic.html" method="post">
					<input name="id" value="${currSupplier.id}" type="hidden" />
					<input name="flag" type="hidden" />
					<legend class="col-md-12 col-xs-12 col-sm-12 p0">
						<h2 class="count_flow"> <i>1</i> 基本信息</h2>
						<fieldset class="col-md-12 col-sm-12 col-xs-12 border_font">
							<legend>供应商信息</legend>
							<ul class="list-unstyled f14">
								<li class="col-md-3 col-sm-6 col-xs-12 pl10">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 供应商名称</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
									   <!-- onkeyup="replaceAndSetPos(this,/[^\u4e00-\u9fa5（）()\w]/g,'')"  修改-->
										<%--<input id="supplierName_input_id" type="text" name="supplierName" required="required" onkeyup="value=value.replace(/[^\u4e00-\u9fa5（）()\w]/g,'')" manlength="50" value="${currSupplier.supplierName}" <c:if test="${fn:contains(audit,'supplierName')}">style="border: 1px solid red;" onmouseover="errorMsg('supplierName')"</c:if> />--%>
										<input id="supplierName_input_id" type="text" name="supplierName" required="required"   maxlength="300" value="${currSupplier.supplierName}"  <c:if test="${!fn:contains(audit,'supplierName')&&currSupplier.status==2}">readonly="readonly"</c:if>  <c:if test="${fn:contains(audit,'supplierName')}">style="border: 1px solid red;" onmouseover="errorMsg('supplierName')"</c:if> />
										<input type="hidden" id="name_span" name="name_flag"/>
                                            <span class="add-on">i</span>
										<span class="input-tip">不能为空</span>
										<div class="cue"> ${err_msg_supplierName } </div>
										<div class="cue">
											<sf:errors path="supplierName" />
										</div>

									</div>
								</li>
								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">网址</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="website" isUrl="isUrl" value="${currSupplier.website}" <c:if test="${!fn:contains(audit,'website')&&currSupplier.status==2}">readonly="readonly"</c:if>    <c:if test="${fn:contains(audit,'website')}">style="border: 1px solid red;" onmouseover="errorMsg('website')"</c:if> >
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
										<input type="text" readonly="readonly" name="foundDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',startDate:'1970-01-01'})" value="${foundDate}" <c:if test="${fn:contains(audit,'foundDate')&&(currSupplier.status==2||currSupplier.status==-1)}">onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'{%y-3}-%M-%d'})" </c:if> <c:if test="${fn:contains(audit,'foundDate')}">style="border: 1px solid red;" onmouseover="errorMsg('foundDate')"</c:if> />
										<span class="add-on cur_point">i</span>
										<span class="input-tip">成立时间须大于三年 </span>
										<div class="cue"> ${err_msg_foundDate } </div>
									</div>
								</li>

							<%-- 	<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 营业执照登记类型</span>
									<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
										<select required name="businessType" id="business_select_id" <c:if test="${fn:contains(audit,'businessType')}">style="border: 1px solid red;" onmouseover="errorMsg('businessType')"</c:if>>
											<c:forEach items="${company }" var="obj">
												<option value="${obj.id }" <c:if test="${obj.id==currSupplier.businessType }">selected="selected"</c:if> >${obj.name }</option>
											</c:forEach>
										</select>
									</div>
								</li> --%>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 企业性质</span>
									<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
										<select required name="businessNature" id="nature_select_id" <c:if test="${fn:contains(audit,'businessNature')&&currSupplier.status==2}">onchange="this.selectedIndex=this.defaultIndex;"</c:if>   <c:if test="${fn:contains(audit,'businessNature')}">style="border: 1px solid red;" onmouseover="errorMsg('businessNature')"</c:if>>
											<c:forEach items="${nature }" var="obj">
												<option value="${obj.id }" <c:if test="${obj.id eq currSupplier.businessNature}">selected="selected"</c:if>>${obj.name}</option>
											</c:forEach>
										</select>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 基本账户开户银行</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="bankName" maxlength="50" required="required" value="${currSupplier.bankName}" <c:if test="${!fn:contains(audit,'bankName')&&currSupplier.status==2}">readonly="readonly"</c:if>   <c:if test="${fn:contains(audit,'bankName')}">style="border: 1px solid red;" onmouseover="errorMsg('bankName')"</c:if>/>
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
										<input type="text" name="bankAccount" isBankCard="true" required="required" value="${currSupplier.bankAccount}" <c:if test="${!fn:contains(audit,'bankAccount')&&currSupplier.status==2}">readonly="readonly"</c:if>   <c:if test="${fn:contains(audit,'bankAccount')}">style="border: 1px solid red;" onmouseover="errorMsg('bankAccount')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空</span>
										<div class="cue"> ${err_msg_bankAccount } </div>
										<div class="cue">
											<sf:errors path="bankAccount" />
										</div>
									</div>
								</li>
 
								<li id="breach_li_id" class="col-md-3 col-sm-6 col-xs-12 mb25">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"  <c:if test="${fn:contains(audit,'supplierBank')}">style="border: 1px solid red;" onmouseover="errorMsg('supplierBank')"</c:if>><i class="red">*</i> 基本账户开户许可证</span>
									<div class="col-md-12 col-sm-12 col-xs-12 p0">
										<c:if test="${(fn:contains(audit,'supplierBank')&&currSupplier.status==2) || currSupplier.status==-1 || currSupplier.status==1}">	 <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="bank_up" maxcount="1" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBank}" auto="true" /> </c:if>
									  <c:if test="${!fn:contains(audit,'supplierBank')&&currSupplier.status==2}">	 <u:show showId="bank_show" delete="false"   groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBank}" /></c:if>
									  <c:if test="${currSupplier.status==-1 || currSupplier.status==1 || fn:contains(audit,'supplierBank')}">	 <u:show showId="bank_show"  groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBank}" /></c:if>
										<div class="cue"> ${err_supplierBank } </div>
									</div>
								</li>

							</ul>
						</fieldset>

		    	     <fieldset class="col-md-12 border_font mt20">
							<legend>营业执照</legend>
							<ul class="list-unstyled f14">
								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 营业执照登记类型</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p10">
										<c:if test="${fn:length(currSupplier.businessType)!=32}">
											<input type="text" name="businessType"  required="required" value="${currSupplier.businessType}" <c:if test="${!fn:contains(audit,'businessType')&&currSupplier.status==2}">readonly="readonly"</c:if>    <c:if test="${fn:contains(audit,'businessType')}">style="border: 1px solid red;" onmouseover="errorMsg('businessType')"</c:if>/>
											<span class="add-on cur_point">i</span>
											<span class="input-tip">不能为空</span>
										</c:if>
										<c:if test="${fn:length(currSupplier.businessType)==32}">
											<c:forEach items="${company }" var="obj">
											 <c:if test="${obj.id==currSupplier.businessType }">
										      	<input type="text" name="businessType" required="required" value=" ${obj.name }" <c:if test="${!fn:contains(audit,'businessType')&&currSupplier.status==2}">readonly="readonly"</c:if> <c:if test="${fn:contains(audit,'businessType')}">style="border: 1px solid red;" onmouseover="errorMsg('businessType')"</c:if>/>
											 	<span class="add-on cur_point">i</span>
											 	<span class="input-tip">不能为空</span>
											 </c:if> 
											 
									         </c:forEach>
										</c:if>
									</div>
								</li>
								<li class="col-md-3 col-sm-6 col-xs-12 pl10">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>统一社会信用代码</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="creditCode"  required maxlength="18" id="creditCode" onkeyup="value=value.replace(/[^\d|a-zA-Z]/g,'')" onblur="checkCreditCode(this.value);" value="${currSupplier.creditCode}" <c:if test="${!fn:contains(audit,'creditCode')&&currSupplier.status==2}">readonly="readonly"</c:if>  <c:if test="${fn:contains(audit,'creditCode')}">style="border: 1px solid red;" onmouseover="errorMsg('creditCode')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">统一社会信用代码为18位数字或18位数字+字母的组合</span>
										<div class="cue"> ${err_creditCide} </div>
										<div class="cue">
											<sf:errors path="creditCode" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 登记机关</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="registAuthority" required maxlength="20" value="${currSupplier.registAuthority}"  <c:if test="${!fn:contains(audit,'registAuthority')&&currSupplier.status==2}">readonly="readonly"</c:if>  <c:if test="${fn:contains(audit,'registAuthority')}">style="border: 1px solid red;" onmouseover="errorMsg('registAuthority')"</c:if>/>
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
										<input type="text" name="registFund" onchange="checkNumsSale(this, 5)" required value="${currSupplier.registFund}" <c:if test="${!fn:contains(audit,'registFund')&&currSupplier.status==2}">readonly="readonly"</c:if>   <c:if test="${fn:contains(audit,'registFund')}">style="border: 1px solid red;" onmouseover="errorMsg('registFund')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，值不可小于零</span>
										<div class="cue" id="err_fund"> ${err_fund } </div>
										<div class="cue">
											<sf:errors path="registFund" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 营业期限   <input type="checkbox" name="branchName" onclick="check(this);" 
										<c:if test="${currSupplier.branchName=='1'}"> checked='true'</c:if>
										<c:if test="${currSupplier.status==2 && !fn:contains(audit,'businessStartDate')}"> disabled='disabled'</c:if>   
										value="${currSupplier.branchName }"> 长期</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<%-- <fmt:formatDate value="${currSupplier.businessStartDate}" pattern="yyyy-MM-dd" var="businessStartDate" /> --%>
										<input id="expireDate" type="text" readonly="readonly" onClick="WdatePicker()" name="businessStartDate" value="<fmt:formatDate value="${currSupplier.businessStartDate}" pattern="yyyy-MM-dd"/>" 
											<c:if test="${fn:contains(audit,'businessStartDate')}">style="border: 1px solid red;" onmouseover="errorMsg('businessStartDate')"</c:if>
											<c:if test="${currSupplier.branchName=='1'}">disabled="disabled"</c:if> />
										<span class="add-on cur_point">i</span>
										<span class="input-tip">如果勾选长期,可不填写有效期</span>
										<div class="cue"> ${err_sDate } </div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12 mb25"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5" <c:if test="${fn:contains(audit,'businessCert')}">style="border: 1px solid red;" onmouseover="errorMsg('businessCert')"</c:if>><i class="red">*</i> 营业执照</span>
									<div class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0">
										<c:if test="${(fn:contains(audit,'businessCert')&&currSupplier.status==2 ) || currSupplier.status==-1 || currSupplier.status==1}"> <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="business_up" maxcount="1" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" auto="true" /></c:if>
										<c:if test="${!fn:contains(audit,'businessCert')&&currSupplier.status==2 }">  <u:show showId="business_show" delete="false"  groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" /></c:if>
									    <c:if test="${currSupplier.status==-1 || currSupplier.status==1 || fn:contains(audit,'businessCert')}">  <u:show showId="business_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" /></c:if>
										<div class="cue"> ${err_business} </div>
									</div>
								</li>

								<li class="col-md-12 col-xs-12 col-sm-12 mb25">
									<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> <i class="red">* </i>经营范围（按照营业执照填写）</span>
									<div class="col-md-12 col-xs-12 col-sm-12 p0">
										<textarea class="col-md-12 col-xs-12 col-sm-12 h80" maxlength="1000"  
											onkeyup="checkCharLimit('businessScope','limit_char_businessScope',1000);if(value.length==1000){layer.msg('字数过多，不可超过1000字！')}" 
											required="required" name="businessScope" id="businessScope"
											<c:if test="${!fn:contains(audit,'businessScope')&&currSupplier.status==2}">readonly="readonly"</c:if>
											<c:if test="${fn:contains(audit,'businessScope')}">style="border: 1px solid red;" onmouseover="errorMsg('businessScope')"</c:if>>${currSupplier.businessScope}</textarea>
										<span class="sm_tip fr">还可输入 <span id="limit_char_businessScope">1000</span> 个字</span>
										<div class="cue">
											<sf:errors path="businessScope" />
										</div>
									</div>
								</li>
							</ul>
						</fieldset>
						
						<fieldset class="col-md-12 col-sm-12 col-xs-12 border_font mt20">
							<legend>法定代表人信息</legend>
							<ul class="list-unstyled f14">
								<li class="col-md-3 col-sm-6 col-xs-12 pl10">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 姓名</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="legalName" required maxlength="10" value="${currSupplier.legalName}" <c:if test="${!fn:contains(audit,'legalName')&&currSupplier.status==2}">readonly="readonly"</c:if>  <c:if test="${fn:contains(audit,'legalName')}">style="border: 1px solid red;" onmouseover="errorMsg('legalName')"</c:if>/>
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
										<input type="text" name="legalIdCard" required value="${currSupplier.legalIdCard}" <c:if test="${!fn:contains(audit,'legalIdCard')&&currSupplier.status==2}">readonly="readonly"</c:if> <c:if test="${fn:contains(audit,'legalIdCard')}">style="border: 1px solid red;" onmouseover="errorMsg('legalIdCard')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，长度为18位</span>
										<div class="cue"> ${err_legalCard } </div>
										<div class="cue">
											<sf:errors path="legalIdCard" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5" <c:if test="${fn:contains(audit,'supplierIdentityUp')}">style="border: 1px solid red;" onmouseover="errorMsg('supplierIdentityUp')"</c:if>><i class="red">*</i> 身份证复印件（正反面在一张上）</span>
									<div class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0">
										<c:if test="${(fn:contains(audit,'supplierIdentityUp')&&currSupplier.status==2) || currSupplier.status==-1 || currSupplier.status==1}"> <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="bearchcert_up_up" maxcount="1" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentityUp}" auto="true" /></c:if>
										<c:if test="${!fn:contains(audit,'supplierIdentityUp')&&currSupplier.status==2}">  <u:show showId="bearchcert_up_show" delete="false"  groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentityUp}" /></c:if>
										<c:if test="${currSupplier.status==-1 || currSupplier.status==1 || fn:contains(audit,'supplierIdentityUp')}">  <u:show showId="bearchcert_up_show"   groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentityUp}" /></c:if>
										<div class="cue"> ${err_identityUp } </div>
									</div>
								</li>


								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 固定电话</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="legalMobile" required  onkeyup="value=value.replace(/[^\d-]/g,'')" value="${currSupplier.legalMobile}"  <c:if test="${!fn:contains(audit,'legalMobile')&&currSupplier.status==2}">readonly="readonly"</c:if>  <c:if test="${fn:contains(audit,'legalMobile')}">style="border: 1px solid red;" onmouseover="errorMsg('legalMobile')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，如: 0101234567</span>
										<div class="cue"> ${err_legalMobile } </div>
										<div class="cue">
											<sf:errors path="legalMobile" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 手机</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="legalTelephone" onkeyup="value=value.replace(/[^\d]/g,'')" required isPhone="true" value="${currSupplier.legalTelephone}"  <c:if test="${!fn:contains(audit,'legalTelephone')&&currSupplier.status==2}">readonly="readonly"</c:if> <c:if test="${fn:contains(audit,'legalTelephone')}">style="border: 1px solid red;" onmouseover="errorMsg('legalTelephone')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，11位手机号码</span>
										<div class="cue"> ${err_legalPhone } </div>
										<div class="cue">
											<sf:errors path="legalTelephone" />
										</div>
									</div>
								</li>

							</ul>
						</fieldset>
						
						
						<fieldset class="col-md-12 col-xs-12 col-sm-12 border_font mt20">
							<legend>地址信息</legend>
							<ul class="list-unstyled f14">
								<li class="col-md-3 col-sm-6 col-xs-12 pl10">
									<span class="col-md-12 col-xs-12 col-sm-12  padding-left-5 "><i class="red">*</i> 住所邮编</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="postCode" required isZipCode="true" value="${currSupplier.postCode}"   <c:if test="${!fn:contains(audit,'postCode')&&currSupplier.status==2}">readonly="readonly"</c:if>   <c:if test="${fn:contains(audit,'postCode')}">style="border: 1px solid red;" onmouseover="errorMsg('postCode')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，长度为6位</span>
										<div class="cue"> ${err_msg_postCode } </div>
										<div class="cue">
											<sf:errors path="postCode" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 住所地址（营业执照上的登记地址）</span>
									<div class="col-md-12 col-xs-12 col-sm-12 select_common p0">
										<div class="col-md-5 col-xs-5 col-sm-5 mr5 p0">
											<select id="root_area_select_id" onchange="loadChildren(this)" <c:if test="${!fn:contains(audit,'address')&&currSupplier.status==2}">onchange="this.selectedIndex=this.defaultIndex;"</c:if>   <c:if test="${fn:contains(audit,'address')}">style="border: 1px solid red;" onmouseover="errorMsg('address')"</c:if>>
												<option value="" >请选择</option>
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
											<select id="children_area_select_id" name="address"  <c:if test="${!fn:contains(audit,'address')&&currSupplier.status==2}">onchange="this.selectedIndex=this.defaultIndex;"</c:if>  <c:if test="${fn:contains(audit,'address')}">style="border: 1px solid red;" onmouseover="errorMsg('address')"</c:if>>
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
									<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 住所详细地址</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="detailAddress" placeholder="街道名称，门牌号。" value="${currSupplier.detailAddress}" required maxlength="50" <c:if test="${!fn:contains(audit,'detailAddress')&&currSupplier.status==2}">readonly="readonly"</c:if> <c:if test="${fn:contains(audit,'detailAddress')}">style="border: 1px solid red;" onmouseover="errorMsg('detailAddress')"</c:if>>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">请如实填写单位地址！</span>
										<div class="cue">${err_detailAddress } </div>
										<div class="cue">
											<sf:errors path="detailAddress" />
										</div>
									</div>
								</li>
								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5 white">操作</span>
									<div class="col-md-12 col-xs-12 col-sm-12 p0 mb25 h30">
									</div>
								</li>
								<div id="address_list_body">
                                    <div class="col-md-12 col-sm-12 col-xs-12 p0 mb5">
	                                    <c:choose>
	                                    	<c:when test="${currSupplier.status==2 }">
	                                      	<button class="btn btn-Invalid"  type="button" disabled="disabled">新增</button>
	                                      </c:when>
	                                      <c:otherwise>
	                                        <button class="btn btn-windows add" type="button" onclick="increaseAddHouseAddress()">新增</button>
	                                      </c:otherwise>
	                                    </c:choose>
	                                    <button class="btn btn-windows delete" type="button" onclick="delAddress()">删除</button>
	                                    <span class="red">${err_address_token}</span>
                                    </div>
                                    <div class="col-md-12 col-sm-12 col-xs-12 p0 over_auto">
                                        <table id="address_table_id" class="table table-bordered table-condensed mt5 table_wrap table_input left_table">
                                            <thead>
                                                <tr>
                                                    <th class="info" style="width:3%;"><input type="checkbox" onchange="checkAll(this, 'address_list_tbody_id')" /></th>
                                                    <th class="info" style="width:13%;"><font color="red">*</font> 生产或经营地址邮编</th>
                                                    <th class="info" style="width:23%;"><font color="red">*</font> 生产或经营地址（填写所有地址）</th>
                                                    <th class="info"><font color="red">*</font> 生产或经营详细地址</th>
                                                    <th class="info" style="width:22%;"><font color="red">*</font> 房产证明或租赁协议</th>
                                                </tr>
                                            </thead>
                                            <tbody id="address_list_tbody_id">
                                            <c:set var="certSaleNumber" value="0" />
                                            <c:forEach items="${currSupplier.addressList}" var="addr" varStatus="vs">
                                                <tr >
                                                    <td class="tc"><input type="checkbox" value="${addr.id}" /></td>
                                                  
                                                    <td class="tc" <c:if test="${fn:contains(audit,addr.id)}">style="border: 1px solid red;" onmouseover="errorMsg('${addr.id }')"</c:if>>
                                                        <input type="text"  <c:if test="${!fn:contains(audit,addr.id)&&currSupplier.status==2}">readonly="readonly"</c:if>  required class="w200 border0 address_zip_code" name="addressList[${vs.index }].code" value="${addr.code}" />
                                                        <input type='hidden' name='addressList[${vs.index }].id' value='${addr.id}'>
                                                    </td>
                                                    <td class="tc" <c:if test="${fn:contains(audit,addr.id)}">style="border: 1px solid red;" onmouseover="errorMsg('${addr.id }')"</c:if>>
                                                        <div class="col-md-5 col-xs-5 col-sm-5 mr5 p0 ml20">
                                                            <select id="root_area_select_id_${vs.index }" class="w100p" onchange="loadChildren(this)" name="addressList[${vs.index }].provinceId" <c:if test="${!fn:contains(audit,'addr.id')&&currSupplier.status==2}">onchange="this.selectedIndex=this.defaultIndex;"</c:if>>
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
                                                            <select id="children_area_select_id_${vs.index }" class="w100p" name="addressList[${vs.index }].address" <c:if test="${!fn:contains(audit,'address')&&currSupplier.status==2}">onchange="this.selectedIndex=this.defaultIndex;"</c:if>>
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
                                                    </td>
                                                    <td class="tc" <c:if test="${fn:contains(audit,addr.id)}">style="border: 1px solid red;" onmouseover="errorMsg('${addr.id }')"</c:if>>
                                                        <input type="text" class="w200 border0" <c:if test="${!fn:contains(audit,addr.id)&&currSupplier.status==2}">readonly="readonly"</c:if>  placeholder="街道名称，门牌号。" name="addressList[${vs.index }].detailAddress" required maxlength="50" value="${addr.detailAddress }" >

                                                    </td>
                                                    <td class="tc" <c:if test="${fn:contains(audit,addr.id)}">style="border: 1px solid red;" onmouseover="errorMsg('${addr.id }')"</c:if>>
                                                        <div class="w200 fl">
                                                           <%-- <c:if test="${(fn:contains(audit,addr.id)&&currSupplier.status==2 )|| currSupplier.status==-1 || currSupplier.status==1}"> <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="house_up_${certSaleNumber}" multiple="true" businessId="${addr.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierHousePoperty}" auto="true" /></c:if>
                                                            <c:if test="${!fn:contains(audit,addr.id)&&currSupplier.status==2 }"> <u:show showId="house_show_${certSaleNumber}" delete="false" businessId="${addr.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierHousePoperty}" /></c:if>
                                                            <c:if test="${currSupplier.status==-1 || currSupplier.status==1 || fn:contains(audit,addr.id)}"> <u:show showId="house_show_${certSaleNumber}" businessId="${addr.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierHousePoperty}" /></c:if> --%>
                                                            <c:choose>
                                                            	<c:when test="${!fn:contains(audit,addr.id)&&currSupplier.status==2}">
                                                            		<u:show showId="house_show_${certSaleNumber}" delete="false" businessId="${addr.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierHousePoperty}" />
                                                            	</c:when>
                                                            	<c:otherwise>
                                                            		<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="house_up_${certSaleNumber}" multiple="true" businessId="${addr.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierHousePoperty}" auto="true" />
                                                            		<u:show showId="house_show_${certSaleNumber}" businessId="${addr.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierHousePoperty}" />
                                                            	</c:otherwise>
                                                            </c:choose>
                                                            <c:if test="${vs.index == err_house_token}">
                                                                <div class="cue">  </div>
                                                            </c:if>
                                                        </div>
                                                    </td>
                                                </tr>
                                                <c:set var="certSaleNumber" value="${certSaleNumber + 1}" />
                                            </c:forEach>
                                            <input type="hidden" id="certSaleNumber" value=${certSaleNumber}>
                                            </tbody>
                                        </table>
                                    </div>
								</div>
							</ul>
						</fieldset>

						<fieldset class="col-md-12 col-xs-12 col-sm-12 border_font mt20">
							<legend>资质资信</legend>
							<ul class="list-unstyled f14">
								<li class="col-md-6 col-sm-6 col-xs-12 mb25 pl10">
									<span class="col-md-5 col-sm-12 col-xs-12 padding-left-5" <c:if test="${fn:contains(audit,'taxCert')}">style="border: 1px solid red;" onmouseover="errorMsg('taxCert')"</c:if>><i class="red">*</i> 近三个月完税凭证</span>
									<div class="col-md-6 col-sm-12 col-xs-12 p0">
										<c:if test="${(fn:contains(audit,'taxCert')&&currSupplier.status==2 )||(currSupplier.status==-1 || currSupplier.status==1)}"> <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="taxcert_up" multiple="true" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierTaxCert}" auto="true" /></c:if>
										<c:if test="${!fn:contains(audit,'taxCert')&&currSupplier.status==2}">  <u:show showId="taxcert_show"  delete="false" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierTaxCert}" /></c:if>
										<c:if test="${currSupplier.status==-1 || currSupplier.status==1 || fn:contains(audit,'taxCert')}">  <u:show showId="taxcert_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierTaxCert}" /></c:if>
										
										<div class="cue"> ${err_taxCert } </div>
									</div>
								</li>

								<li id="bill_li_id" class="col-md-6 col-sm-6 col-xs-12 mb25">
									<span class="col-md-5 col-sm-12 col-xs-12 padding-left-5 w250" <c:if test="${fn:contains(audit,'billCert')}">style="border: 1px solid red;" onmouseover="errorMsg('billCert')"</c:if>><i class="red">*</i> 近三年银行基本账户年末对账单</span>
									<div class="col-md-6 col-sm-12 col-xs-12 p0">
									<c:if test="${(fn:contains(audit,'billCert')&&currSupplier.status==2 )|| currSupplier.status==-1 || currSupplier.status==1}">	<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="billcert_up" multiple="true" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBillCert}" auto="true" /></c:if>
									<c:if test="${!fn:contains(audit,'billCert')&&currSupplier.status==2 }">	<u:show showId="billcert_show" delete="false"    groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBillCert}" /></c:if>
									<c:if test="${currSupplier.status==-1 || currSupplier.status==1 || fn:contains(audit,'billCert')}">	<u:show showId="billcert_show"     groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBillCert}" /></c:if>
										<div class="cue"> ${err_bil } </div>
									</div>
								</li>

								<li id="security_li_id" class="col-md-6 col-sm-6 col-xs-12 mb25">
									<span class="col-md-5 col-sm-12 col-xs-12 padding-left-5" <c:if test="${fn:contains(audit,'securityCert')}">style="border: 1px solid red;" onmouseover="errorMsg('securityCert')"</c:if>><i class="red">*</i> 近三个月缴纳社会保险金凭证</span>
									<div class="col-md-6 col-sm-12 col-xs-12 p0">
									<c:if test="${(fn:contains(audit,'securityCert')&&currSupplier.status==2) || currSupplier.status==-1 || currSupplier.status==1}">	<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="curitycert_up" multiple="true" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierSecurityCert}" auto="true" /></c:if>
								    <c:if test="${!fn:contains(audit,'securityCert')&&currSupplier.status==2}">	 	<u:show showId="curitycert_show" delete="false"    groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierSecurityCert}" /></c:if>
									<c:if test="${currSupplier.status==-1 || currSupplier.status==1 || fn:contains(audit,'securityCert')}">	 	<u:show showId="curitycert_show"    groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierSecurityCert}" /></c:if>
										<div class="cue"> ${err_security } </div>
									</div>
								</li>

								<li class="col-md-6 col-sm-6 col-xs-12 mb25 mb25">
									<span class="col-md-5 col-sm-12 col-xs-12 padding-left-5 w250" ><i class="red">*</i> 近三年内有无重大违法记录</span>
									<div class="col-md-6 col-sm-12 col-xs-12 p0 h30">
										<select name="isIllegal" id="isIllegal" class="fl mr10 w120" <c:if test="${fn:contains(audit,'isIllegal')}">style="border: 1px solid red;" onmouseover="errorMsg('isIllegal')"</c:if>>
											<option value='' disabled selected style="display: none;">请选择</option>
											<option value="0" <c:if test="${currSupplier.isIllegal eq '0'}">selected</c:if>>无</option>
											<option value="1" <c:if test="${currSupplier.isIllegal eq '1'}">selected</c:if>>有</option>
										</select>
										<div class="cue"> ${err_isIllegal } </div>
									</div>
								</li>
								<li class="col-md-6 col-sm-6 col-xs-12 mb25">
									<span class="col-md-5 col-sm-12 col-xs-12 padding-left-5" <c:if test="${fn:contains(audit,'isHavingConCert')}">style="border: 1px solid red;" onmouseover="errorMsg('isHavingConCert')"</c:if>><i class="red">*</i> 国家或军队保密资格证书</span>
									<div class="col-md-6 col-sm-12 col-xs-12 p0 h30">
										<select name="isHavingConCert" id="isHavingConCert" onchange="dis_bearch(this)" class="fl mr10 w120">
											<option value='' disabled selected style="display: none;">请选择</option>
											<option value="0" <c:if test="${currSupplier.isHavingConCert eq '0'}">selected</c:if>>无</option>
											<option value="1" <c:if test="${currSupplier.isHavingConCert eq '1'}">selected</c:if>>有</option>
										</select>
										<div class="cue"> ${err_isHavingConCert } </div>
									</div>
								</li>
								<li class="col-md-6 col-sm-6 col-xs-12 mb25" id="bearchCertDiv">
									<span class="col-md-5 col-sm-12 col-xs-12 padding-left-5 w250" <c:if test="${fn:contains(audit,'supplierBearchCert')}">style="border: 1px solid red;" onmouseover="errorMsg('supplierBearchCert')"</c:if>><i class="red">*</i> 保密资格证书</span>
									<div class="col-md-6 col-sm-12 col-xs-12 p0">
									<c:if test="${(fn:contains(audit,'supplierBearchCert')&&currSupplier.status==2 ) || currSupplier.status==-1 || currSupplier.status==1}"> 	<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="bearchcert_up" multiple="true" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBearchCert}" auto="true" /></c:if>
									<c:if test="${!fn:contains(audit,'supplierBearchCert')&&currSupplier.status==2}"> 	<u:show showId="bearchcert_show"  delete="false" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBearchCert}" /></c:if>
									<c:if test="${currSupplier.status==-1 || currSupplier.status==1 ||fn:contains(audit,'supplierBearchCert')}"> <u:show showId="bearchcert_show"   businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBearchCert}" /></c:if>
									
										<div class="cue"> ${err_bearch } </div>
									</div>
								</li>
							</ul>
						</fieldset>
						

						<fieldset class="col-md-12 col-sm-12 col-xs-12 border_font mt20">
							<legend>注册联系人</legend>
							<ul class="list-unstyled f14">
								<li class="col-md-3 col-sm-6 col-xs-12 pl10">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><i class="red">*</i> 姓名</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="contactName" required maxlength="10" value="${currSupplier.contactName}" <c:if test="${!fn:contains(audit,'contactName')&&currSupplier.status==2}">readonly="readonly"</c:if>  <c:if test="${fn:contains(audit,'contactName')}">style="border: 1px solid red;" onmouseover="errorMsg('contactName')"</c:if>/>
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
										<input type="text" name="contactFax" required isFax="true" onkeyup="value=value.replace(/[^\d-]/g,'')" value="${currSupplier.contactFax}"  <c:if test="${!fn:contains(audit,'contactFax')&&currSupplier.status==2}">readonly="readonly"</c:if>  <c:if test="${fn:contains(audit,'contactFax')}">style="border: 1px solid red;" onmouseover="errorMsg('contactFax')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，如: 0101234567</span>
										<div class="cue"> ${err_fax } </div>
										<div class="cue">
											<sf:errors path="contactFax" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 固定电话</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="contactMobile" required  onkeyup="value=value.replace(/[^\d-]/g,'')" value="${currSupplier.contactMobile}" <c:if test="${!fn:contains(audit,'contactMobile')&&currSupplier.status==2}">readonly="readonly"</c:if>  <c:if test="${fn:contains(audit,'contactMobile')}">style="border: 1px solid red;" onmouseover="errorMsg('contactMobile')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，如: 0101234567</span>
										<div class="cue"> ${err_catMobile } </div>
										<div class="cue">
											<sf:errors path="contactMobile" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 手机</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="mobile" required isPhone="true" onkeyup="value=value.replace(/[^\d]/g,'')" value="${currSupplier.mobile}"  <c:if test="${!fn:contains(audit,'mobile')&&currSupplier.status==2}">readonly="readonly"</c:if>   <c:if test="${fn:contains(audit,'mobile')}">style="border: 1px solid red;" onmouseover="errorMsg('mobile')"</c:if>/>
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
										<input type="email" name="contactEmail" required email value="${currSupplier.contactEmail}" <c:if test="${!fn:contains(audit,'contactEmail')&&currSupplier.status==2}">readonly="readonly"</c:if>  <c:if test="${fn:contains(audit,'contactEmail')}">style="border: 1px solid red;" onmouseover="errorMsg('contactEmail')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，如：123456@qq.com</span>
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
											<select id="root_area_select_id" name="concatProvince" onchange="loadChildren(this)" <c:if test="${fn:contains(audit,'concatCity')}">style="border: 1px solid red;" onmouseover="errorMsg('concatCity')"</c:if>>
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
											<select id="children_area_select_id" name="concatCity" <c:if test="${fn:contains(audit,'concatCity')}">style="border: 1px solid red;" onmouseover="errorMsg('concatCity')"</c:if>>

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
										<input type="text" name="contactAddress" required maxlength="50" value="${currSupplier.contactAddress}"  <c:if test="${!fn:contains(audit,'contactAddress')&&currSupplier.status==2}">readonly="readonly"</c:if>   <c:if test="${fn:contains(audit,'contactAddress')}">style="border: 1px solid red;" onmouseover="errorMsg('contactAddress')"</c:if>/>
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
							<legend>本单位负责军队业务的人员信息</legend>
							<ul class="list-unstyled f14">
								<li class="col-md-3 col-sm-6 col-xs-12 pl10">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><i class="red">*</i> 姓名</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="armyBusinessName" required maxlength="10" <c:if test="${!fn:contains(audit,'armyBusinessName')&&currSupplier.status==2}">readonly="readonly"</c:if>   value="${currSupplier.armyBusinessName}" <c:if test="${fn:contains(audit,'armyBusinessName')}">style="border: 1px solid red;" onmouseover="errorMsg('armyBusinessName')"</c:if>/>
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
										<input type="text" name="armyBusinessFax" required isFax="true" onkeyup="value=value.replace(/[^\d-]/g,'')"  <c:if test="${!fn:contains(audit,'armyBusinessFax')&&currSupplier.status==2}">readonly="readonly"</c:if> value="${currSupplier.armyBusinessFax}" <c:if test="${fn:contains(audit,'armyBusinessFax')}">style="border: 1px solid red;" onmouseover="errorMsg('armyBusinessFax')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，如: 0101234567</span>
										<div class="cue"> ${err_armFax } </div>
										<div class="cue">
											<sf:errors path="armyBusinessFax" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 固定电话</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="armyBuinessMobile" required   onkeyup="value=value.replace(/[^\d-]/g,'')" value="${currSupplier.armyBuinessMobile}"  <c:if test="${!fn:contains(audit,'armyBuinessMobile')&&currSupplier.status==2}">readonly="readonly"</c:if> <c:if test="${fn:contains(audit,'armyBuinessMobile')}">style="border: 1px solid red;" onmouseover="errorMsg('armyBuinessMobile')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，如: 0101234567</span>
										<div class="cue"> ${err_armMobile } </div>
										<div class="cue">
											<sf:errors path="armyBuinessMobile" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 手机</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="armyBuinessTelephone" required isPhone="true" onkeyup="value=value.replace(/[^\d]/g,'')" value="${currSupplier.armyBuinessTelephone}" <c:if test="${!fn:contains(audit,'armyBuinessTelephone')&&currSupplier.status==2}">readonly="readonly"</c:if>  <c:if test="${fn:contains(audit,'armyBuinessTelephone')}">style="border: 1px solid red;" onmouseover="errorMsg('armyBuinessTelephone')"</c:if>/>
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
										<input type="email" name="armyBuinessEmail" required email value="${currSupplier.armyBuinessEmail}"  <c:if test="${!fn:contains(audit,'armyBuinessEmail')&&currSupplier.status==2}">readonly="readonly"</c:if>   <c:if test="${fn:contains(audit,'armyBuinessEmail')}">style="border: 1px solid red;" onmouseover="errorMsg('armyBuinessEmail')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，如：123456@qq.com</span>
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
											<select id="root_area_select_id" name="armyBuinessProvince" onchange="loadChildren(this)" <c:if test="${fn:contains(audit,'armyBuinessCity')}">style="border: 1px solid red;" onmouseover="errorMsg('armyBuinessCity')"</c:if>>
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
											<select id="children_area_select_id" name="armyBuinessCity" <c:if test="${fn:contains(audit,'armyBuinessCity')}">style="border: 1px solid red;" onmouseover="errorMsg('armyBuinessCity')"</c:if>>

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
										<input type="text" name="armyBuinessAddress" required maxlength="50" value="${currSupplier.armyBuinessAddress}"  <c:if test="${!fn:contains(audit,'armyBuinessAddress')&&currSupplier.status==2}">readonly="readonly"</c:if> 
											<c:if test="${fn:contains(audit,'armyBuinessAddress')}">style="border: 1px solid red;" onmouseover="errorMsg('armyBuinessAddress')"</c:if>/>
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

						<%-- <fieldset class="col-md-12 border_font mt20">
							<legend>营业执照</legend>
							<ul class="list-unstyled f14">
								<li class="col-md-3 col-sm-6 col-xs-12 pl10">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>统一社会信用代码</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="creditCode"  required maxlength="18" id="creditCode" onkeyup="value=value.replace(/[^\d|a-zA-Z]/g,'')" value="${currSupplier.creditCode}" <c:if test="${fn:contains(audit,'creditCode')}">style="border: 1px solid red;" onmouseover="errorMsg('creditCode')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<!-- <span class="input-tip">不能为空，18位数字或字母</span> -->
										<div class="cue"> ${err_creditCide} </div>
										<div class="cue">
											<sf:errors path="creditCode" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 登记机关</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="text" name="registAuthority" required maxlength="20" value="${currSupplier.registAuthority}" <c:if test="${fn:contains(audit,'registAuthority')}">style="border: 1px solid red;" onmouseover="errorMsg('registAuthority')"</c:if>/>
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
										<input type="text" name="registFund" onchange="checkNumsSale(this, 5)" required value="${currSupplier.registFund}" <c:if test="${fn:contains(audit,'registFund')}">style="border: 1px solid red;" onmouseover="errorMsg('registFund')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">不能为空，值不可小于零</span>
										<div class="cue" id="err_fund"> ${err_fund } </div>
										<div class="cue">
											<sf:errors path="registFund" />
										</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 营业期限   <input type="checkbox" name="branchName" onclick="check(this);" <c:if test="${currSupplier.branchName=='1'}"> checked='true'</c:if>   value="1"> 长期</span>
									<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
										<fmt:formatDate value="${currSupplier.businessStartDate}" pattern="yyyy-MM-dd" var="businessStartDate" />
										<input id="expireDate" type="text" readonly="readonly" onClick="WdatePicker()" name="businessStartDate" value="${businessStartDate}" <c:if test="${fn:contains(audit,'businessStartDate')}">style="border: 1px solid red;" onmouseover="errorMsg('businessStartDate')"</c:if>/>
										<span class="add-on cur_point">i</span>
										<span class="input-tip">如果勾选长期,可不填写有效期</span>
										<div class="cue"> ${err_sDate } </div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12 mb25"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5" <c:if test="${fn:contains(audit,'businessCert')}">style="border: 1px solid red;" onmouseover="errorMsg('businessCert')"</c:if>><i class="red">*</i> 营业执照</span>
									<div class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0">
										<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="business_up" maxcount="1" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" auto="true" />
										<u:show showId="business_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" />
										<div class="cue"> ${err_business} </div>
									</div>
								</li>

								<li class="col-md-12 col-xs-12 col-sm-12 mb25">
									<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> <i class="red">* </i>经营范围（按照营业执照填写）</span>
									<div class="col-md-12 col-xs-12 col-sm-12 p0">
										<textarea class="col-md-12 col-xs-12 col-sm-12 h80" maxlength="1000"  onkeyup="if(value.length==1000){layer.msg('字数过多，不可超过1000字！')}" required="required" name="businessScope" <c:if test="${fn:contains(audit,'businessScope')}">style="border: 1px solid red;" onmouseover="errorMsg('businessScope')"</c:if>>${currSupplier.businessScope}</textarea>
										<div class="cue">
											<sf:errors path="businessScope" />
										</div>
									</div>
								</li>
							</ul>
						</fieldset> --%>

						<h2 class="count_flow clear pt20"> <i>2</i> 境外信息</h2>
						<fieldset class="col-md-12 border_font mt20">
							<legend>境外分支</legend>
							<ul class="list-unstyled f14" id="list-unstyled">
								<li class="col-md-3 col-sm-6 col-xs-12 pl10">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red"></i>境外分支机构</span>
									<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
										<select name="overseasBranch" onchange="dis(this)" id="overseas_branch_select_id" <c:if test="${!fn:contains(audit,'overseasBranch')&&currSupplier.status==2}">onchange="this.selectedIndex=this.defaultIndex;"</c:if> <c:if test="${fn:contains(audit,'overseasBranch')}">style="border: 1px solid red;" onmouseover="errorMsg('overseasBranch')"</c:if>>
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
							</ul>
								<ul id="branch_list_body" class="list-unstyled clear">
									<c:forEach items="${currSupplier.branchList }" var="bran" varStatus="vs">
										<li name="branch" style="display: none;" class="col-md-3 col-sm-6 col-xs-12 pl10">
											<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">* </i>机构名称</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input class= 'cBranchName' type="text" name="branchList[${vs.index }].organizationName" id="sup_branchName" required maxlength="50" value="${bran.organizationName}"  <c:if test="${!fn:contains(audit,'organizationName')&&currSupplier.status==2}">readonly="readonly"</c:if>  <c:if test="${fn:contains(audit,'organizationName_'.concat(bran.id))}">style="border: 1px solid red;" onmouseover="errorMsg('organizationName_${bran.id }')"</c:if>/>
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
												<select class='cOverseas' name="branchList[${vs.index }].country" id="overseas_branch_select_id" required <c:if test="${!fn:contains(audit,'overseasBranch')&&currSupplier.status==2}">onchange=""</c:if> <c:if test="${fn:contains(audit,'countryName_'.concat(bran.id))}">style="border: 1px solid red;" onmouseover="errorMsg('countryName_${bran.id }')"</c:if>>
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
												<input class='cDetailAdddress' type="text" name="branchList[${vs.index }].detailAddress" required maxlength="50" id="sup_branchAddress" value="${bran.detailAddress}"
													<c:if test="${!fn:contains(audit,'detailAddress_'.concat(bran.id))&&currSupplier.status==2}">readonly="readonly"</c:if>  
													<c:if test="${fn:contains(audit,'detailAddress_'.concat(bran.id))}">style="border: 1px solid red;" onmouseover="errorMsg('detailAddress_${bran.id }')"</c:if>/>
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
												<c:choose>
                          <c:when test="${currSupplier.status==2 }">
                          	<input type="button" disabled="disabled" class="btn list_btn btn-Invalid" value="十" />
                          </c:when>
                          <c:otherwise>
                            <input type="button" onclick="addBranch(this)" class="btn list_btn" value="十" />
                          </c:otherwise>
                        </c:choose>
												<input type="button" onclick="delBranch(this)" class="btn list_btn" value="一" />
											 	<input type="hidden" name="branchList[${vs.index }].id"  required  value="${bran.id}"/>
											</div>
										</li>

										<li name="branch" style="display: none;" class="col-md-12 col-xs-12 col-sm-12 mb25">
											<span class="col-md-12 c ol-xs-12 col-sm-12 padding-left-5"><i class="red">* </i>生产经营范围</span>
											<div class="col-md-12 col-xs-12 col-sm-12 p0">
												<textarea class="cPrdArea col-md-12 col-xs-12 col-sm-12 h80" maxlength="1000"
													onkeyup="checkCharLimit('branchbusinessSope_${vs.index }','limit_char_branchbusinessSope_${vs.index }',1000);if(value.length==1000){layer.msg('字数过多，不可超过1000字！')}" 
													id="branchbusinessSope_${vs.index }" required name="branchList[${vs.index }].businessSope" 
													<c:if test="${!fn:contains(audit,'businessSope')&&currSupplier.status==2}">readonly="readonly"</c:if>
													<c:if test="${fn:contains(audit,'businessSope_'.concat(bran.id))}">style="border: 1px solid red;" onmouseover="errorMsg('businessSope_${bran.id }')"</c:if>>${bran.businessSope}</textarea>
												<span class="sm_tip fr">还可输入 <span id="limit_char_branchbusinessSope_${vs.index }">1000</span> 个字</span>
												<div class="cue">
													<sf:errors path="branchList[${vs.index }].businessSope" />
												</div>
											</div>
										</li>
									</c:forEach>
								</ul>
						</fieldset>
						<!-- 财务信息 -->
						<h2 class="count_flow clear pt20"> <i>3</i> 近三年财务信息
	  					<span class="red"> ${err_bearchFile}</span></h2>
						<div class="padding-top-10 clear" id="financeInfo">
							<c:forEach items="${currSupplier.listSupplierFinances}" var="finance" varStatus="vs">
								<h2 class="count_flow clear">${finance.year}年财务信息  <span style="float:right" class="b">（金额单位：万元） </span>  </h2>
								<div class="col-md-12 col-xs-12 col-sm-12 border_font <c:if test="${vs.index != 2}">mb10</c:if>">
									<div class="col-md-12 col-sm-12 col-xs-12 p0 over_auto">
										<div class="col-md-12 col-sm-12 col-xs-12 p0 tl">
											<span class="red"></span>
										</div>
										<div class="col-md-12 col-sm-12 col-xs-12 p0">
											<table class="table table-bordered table-condensed mt5 table_wrap table_input">
												<thead>
													<tr>
														<th class="w50 info">年份</th>
														<th class="info"><font color=red>*</font> 会计事务所名称</th>
														<th class="info"><font color=red>*</font> 事务所联系电话</th>
														<th class="info"><font color=red>*</font> 审计人姓名（2人）</th>
														<th class="info"><font color=red>*</font> 资产总额</th>
														<th class="info"><font color=red>*</font> 负债总额</th>
														<th class="info"><font color=red>*</font> 净资产总额</th>
														<th class="info"><font color=red>*</font> 营业收入</th>
													</tr>
												</thead>
												<tbody id="finance_list_tbody_id">
													<c:set var="infoId" value="${finance.id }_info" />
													<tr <c:if test="${fn:contains(audit,infoId)}"> onmouseover="errorMsg('${infoId}')"</c:if>>
														<td class="tc" <c:if test="${fn:contains(audit,infoId)}">style="border: 1px solid red;"</c:if>>
															<input type="hidden" name="listSupplierFinances[${vs.index }].id" value="${finance.id}" required>
															<input type="text" readonly="readonly" required="required" class="w50 border0 tc" name="listSupplierFinances[${vs.index }].year" value="${finance.year}"  > </td>
														<td class="tc" <c:if test="${fn:contains(audit,infoId)}">style="border: 1px solid red;"</c:if>>
															<input type="text" required="required" class="w200 border0" name="listSupplierFinances[${vs.index }].name" value="${finance.name}"  <c:if test="${!fn:contains(audit,infoId)&&currSupplier.status==2}">readonly="readonly"</c:if> >
														</td>
														<td class="tc" <c:if test="${fn:contains(audit,infoId)}">style="border: 1px solid red;"</c:if>>
															<input type="text" required="required" class="w100 border0" name="listSupplierFinances[${vs.index }].telephone" value="${finance.telephone}"
																onkeyup="value=value.replace(/[^\d-]/g,'')" 
																onpropertychange="value=value.replace(/[^\d-]/g,'')" 
																oninput="value=value.replace(/[^\d-]/g,'')" 
																<c:if test="${!fn:contains(audit,infoId)&&currSupplier.status==2}">readonly="readonly"</c:if> >
														</td>
														<td class="tc" <c:if test="${fn:contains(audit,infoId)}">style="border: 1px solid red;"</c:if>>
															<input type="text" required="required" class="w200 border0" name="listSupplierFinances[${vs.index }].auditors" value="${finance.auditors}" <c:if test="${!fn:contains(audit,infoId)&&currSupplier.status==2}">readonly="readonly"</c:if> >

														</td>
														<td class="tc" <c:if test="${fn:contains(audit,infoId)}">style="border: 1px solid red;"</c:if>>
															<input type="text" required="required" class="w80 border0" onchange="checkNumsSale(this, 1)" name="listSupplierFinances[${vs.index }].totalAssets" value="${finance.totalAssets}" <c:if test="${!fn:contains(audit,infoId)&&currSupplier.status==2}">readonly="readonly"</c:if> >

														</td>
														<td class="tc" <c:if test="${fn:contains(audit,infoId)}">style="border: 1px solid red;"</c:if>>
															<input type="text" required="required" class="w80 border0" onchange="checkNumsSale(this, 2)" name="listSupplierFinances[${vs.index }].totalLiabilities" value="${finance.totalLiabilities}" <c:if test="${!fn:contains(audit,infoId)&&currSupplier.status==2}">readonly="readonly"</c:if> >
														</td>
														<td class="tc" <c:if test="${fn:contains(audit,infoId)}">style="border: 1px solid red;"</c:if>>
															<input type="text" required="required" class="w80 border0" onchange="checkNumsSale(this, 3)" name="listSupplierFinances[${vs.index }].totalNetAssets" value="${finance.totalNetAssets}" <c:if test="${!fn:contains(audit,infoId)&&currSupplier.status==2}">readonly="readonly"</c:if> >
														</td>
														<td class="tc" <c:if test="${fn:contains(audit,infoId)}">style="border: 1px solid red;"</c:if>>
															<input type="text" required="required" class="w80 border0" onchange="checkNumsSale(this, 4)" name="listSupplierFinances[${vs.index }].taking" value="${finance.taking}" <c:if test="${!fn:contains(audit,infoId)&&currSupplier.status==2}">readonly="readonly"</c:if>>
														</td>
													</tr>
												</tbody>
											</table>

											<table id="finance_attach_list_id" class="table table-bordered table-condensed mt5 table_wrap table_input">
												<thead>
													<tr>
														<th class="w50 info">年份</th>
														<th class="info"><font color=red>*</font> 审计报告的审计意见</th>
														<th class="info"><font color=red>*</font> 资产负债表</th>
														<th class="info"><font color=red>*</font> 财务利润表</th>
														<th class="info"><font color=red>*</font> 现金流量表</th>
														<th class="info">所有者权益变动表</th>
													</tr>
												</thead>
												<tbody id="finance_attach_list_tbody_id">
													<c:set var="file" value="${finance.id }_file" />
													<tr <c:if test="${fn:contains(audit,file)}"> onmouseover="errorMsg('${file}')"</c:if>>
														<td class="tc" <c:if test="${fn:contains(audit,file)}">style="border: 1px solid red;" </c:if>>${finance.year}</td>
														<td class="tc" <c:if test="${fn:contains(audit,file)}">style="border: 1px solid red;" </c:if>>
														  <div class="w200 fl">
															<c:if test="${(fn:contains(audit,file)&&currSupplier.status==2) || currSupplier.status==-1 || currSupplier.status==1}"> 	<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="fina_${vs.index}_audit_up" multiple="true" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${finance.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierAuditOpinion}" auto="true" /> </c:if>
															<c:if test="${!fn:contains(audit,file)&&currSupplier.status==2}">  <u:show showId="fina_${vs.index}_audit" delete="false"     groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${finance.id}" typeId="${supplierDictionaryData.supplierAuditOpinion}" sysKey="${sysKey}" /></c:if>
														  	<c:if test="${currSupplier.status==-1 || currSupplier.status==1 || fn:contains(audit,file)}">  <u:show showId="fina_${vs.index}_audit"     groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${finance.id}" typeId="${supplierDictionaryData.supplierAuditOpinion}" sysKey="${sysKey}" /></c:if>
														  
														   </div>
														</td>
														<td class="tc" <c:if test="${fn:contains(audit,file)}">style="border: 1px solid red;" </c:if>>
														  <div class="w200 fl">
																<c:if test="${(fn:contains(audit,file)&&currSupplier.status==2) || currSupplier.status==-1 || currSupplier.status==1}">  <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="fina_${vs.index}_lia_up" multiple="true" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${finance.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierLiabilities}" auto="true" /></c:if>
														       <c:if test="${!fn:contains(audit,file)&&currSupplier.status==2}"> 	<u:show showId="fina_${vs.index}_lia" delete="false"   groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${finance.id}" typeId="${supplierDictionaryData.supplierLiabilities}" sysKey="${sysKey}" /></c:if>
														  	   <c:if test="${currSupplier.status==-1 || currSupplier.status==1 || fn:contains(audit,file)}"> 	<u:show showId="fina_${vs.index}_lia"    groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${finance.id}" typeId="${supplierDictionaryData.supplierLiabilities}" sysKey="${sysKey}" /></c:if>
														  </div>
														</td>
														<td class="tc" <c:if test="${fn:contains(audit,file)}">style="border: 1px solid red;" </c:if>>
														  <div class="w200 fl">
															<c:if test="${(fn:contains(audit,file)&&currSupplier.status==2 ) || currSupplier.status==-1 || currSupplier.status==1}"> <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="fina_${vs.index}_pro_up" multiple="true" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${finance.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierProfit}" auto="true" /></c:if>
															<c:if test="${!fn:contains(audit,file)&&currSupplier.status==2}">  <u:show showId="fina_${vs.index}_pro"  delete="false"   groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${finance.id}" typeId="${supplierDictionaryData.supplierProfit}" sysKey="${sysKey}" /></c:if>
															<c:if test="${currSupplier.status==-1 || currSupplier.status==1 || fn:contains(audit,file)}">  <u:show showId="fina_${vs.index}_pro"    groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${finance.id}" typeId="${supplierDictionaryData.supplierProfit}" sysKey="${sysKey}" /></c:if>
														
														  </div>
														</td>
														<td class="tc" <c:if test="${fn:contains(audit,file)}">style="border: 1px solid red;" </c:if>>
														  <div class="w200 fl">
															<c:if test="${(fn:contains(audit,file)&&currSupplier.status==2)|| currSupplier.status==-1 || currSupplier.status==1}"> 	<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="fina_${vs.index}_cash_up" multiple="true" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${finance.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierCashFlow}" auto="true" /></c:if>
															<c:if test="${!fn:contains(audit,file)&&currSupplier.status==2}"> <u:show showId="fina_${vs.index}_cash" delete="false"   groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${finance.id}" typeId="${supplierDictionaryData.supplierCashFlow}" sysKey="${sysKey}" /></c:if>
	 													  	<c:if test="${currSupplier.status==-1 || currSupplier.status==1 || fn:contains(audit,file)}"> <u:show showId="fina_${vs.index}_cash"    groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${finance.id}" typeId="${supplierDictionaryData.supplierCashFlow}" sysKey="${sysKey}" /></c:if>
	 													  
	 													  </div>
	 													</td>
														<td class="tc" <c:if test="${fn:contains(audit,file)}">style="border: 1px solid red;" </c:if>>
														  <div class="w200 fl">
															<c:if test="${(fn:contains(audit,file)&&currSupplier.status==2 ) || currSupplier.status==-1 || currSupplier.status==1}"> 	<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="fina_${vs.index}_change_up" multiple="true" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${finance.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierOwnerChange}" auto="true" /></c:if>
															<c:if test="${!fn:contains(audit,file)&&currSupplier.status==2 }">  <u:show showId="fina_${vs.index}_change" delete="false" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${finance.id}" typeId="${supplierDictionaryData.supplierOwnerChange}" sysKey="${sysKey}" /></c:if>
														  	<c:if test="${currSupplier.status==-1 || currSupplier.status==1 || fn:contains(audit,file)}">  <u:show showId="fina_${vs.index}_change"  groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${finance.id}" typeId="${supplierDictionaryData.supplierOwnerChange}" sysKey="${sysKey}" /></c:if>
														  </div>
														</td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>

					<div class="padding-top-10 clear">
						<h2 class="count_flow clear pt20"> <i>4</i><font color=red>*</font> 出资人（股东）信息  （说明：出资人（股东）多于10人的，可以列出出资金额前十位的信息，但所列的出资比例应高于50%）</h2>
						<div class="col-md-12 col-sm-12 col-xs-12 p0 ul_list mb20">
							<div class="col-md-12 col-sm-12 col-xs-12 p15 mt20">
								<div class="col-md-12 col-sm-12 col-xs-12 p0 mb5">
								<%-- <c:choose>
                                      <c:when test="${currSupplier.status==2 }">
                                      <button class="btn btn-Invalid"  type="button" disabled="disabled">新增</button>
                                      </c:when>
                                      <c:otherwise> --%>
                                        <button class="btn btn-windows add" type="button" onclick="openStockholder()">新增</button>
                                 <%--      </c:otherwise>
                                    </c:choose> --%>
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
													<td class="tc" <c:if test="${fn:contains(audit,stockholder.id)}">style="border: 1px solid red;" </c:if>><input type="checkbox" value="${stockholder.id}" <c:if test="${fn:contains(audit,stockholder.id)}">readonly='readonly'</c:if>  />
													</td>
													<td class="tc" <c:if test="${fn:contains(audit,stockholder.id)}">style="border: 1px solid red;" </c:if>>
														<select name="listSupplierStockholders[${stockvs.index }].nature" class="w100p border0" <c:if test="${!fn:contains(audit,stockholder.id)&&currSupplier.status==2}">onchange="this.selectedIndex=this.defaultIndex;"</c:if> >
															<option value="1" <c:if test="${stockholder.nature==1}"> selected="selected"</c:if> >法人</option>
															<option value="2" <c:if test="${stockholder.nature==2}"> selected="selected" </c:if> >自然人</option>
														</select>

													</td>

													<td class="tc" <c:if test="${fn:contains(audit,stockholder.id)}">style="border: 1px solid red;" </c:if>> <input type='text' style='border:0px;' maxlength="50" name='listSupplierStockholders[${stockvs.index }].name' value='${stockholder.name}'  <c:if test="${!fn:contains(audit,stockholder.id)&&currSupplier.status==2}">readonly='readonly'</c:if>  > </td>
													<td class="tc" <c:if test="${fn:contains(audit,stockholder.id)}">style="border: 1px solid red;" </c:if>> <input type='text' style='border:0px;' name='listSupplierStockholders[${stockvs.index }].identity' maxlength="18" onkeyup="value=value.replace(/[^\d|a-zA-Z]/g,'')" value='${stockholder.identity}' <c:if test="${!fn:contains(audit,stockholder.id)&&currSupplier.status==2}">readonly='readonly'</c:if>  > </td>
													<td class="tc" <c:if test="${fn:contains(audit,stockholder.id)}">style="border: 1px solid red;" </c:if>> <input type='text' style='border:0px;' class="shares" name='listSupplierStockholders[${stockvs.index }].shares' onchange="checkNumsSale(this, 3)" value='${stockholder.shares}' <c:if test="${!fn:contains(audit,stockholder.id)&&currSupplier.status==2}">readonly='readonly'</c:if> > </td>
													<td class="tc" <c:if test="${fn:contains(audit,stockholder.id)}">style="border: 1px solid red;" </c:if>> <input type='text' style='border:0px;' class="proportion_vali" name='listSupplierStockholders[${stockvs.index }].proportion' value='${stockholder.proportion}' <c:if test="${!fn:contains(audit,stockholder.id)&&currSupplier.status==2}">readonly='readonly'</c:if> 
													 	onkeyup="value=value.replace(/[^\d.]/g,'')" onblur="validatePercentage2(this.value)"></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					
					<!-- 售后服务机构信息 -->
					<div class="clear">
						<h2 class="count_flow clear pt20"> <i>5</i><font color=red>*</font> 售后服务机构</h2>
						<div class="col-md-12 col-sm-12 col-xs-12 p0 ul_list mb20">
							<div class="col-md-12 col-sm-12 col-xs-12 p15 mt20">
								<div class="col-md-12 col-sm-12 col-xs-12 p0 mb5">
								<c:choose>
                                      <c:when test="${currSupplier.status==2 }">
                                      <button class="btn btn-Invalid"  type="button" disabled="disabled">新增</button>
                                      </c:when>
                                      <c:otherwise>
                                       <button class="btn btn-windows add" type="button" onclick="openAfterSaleDep()">新增</button>
                                      </c:otherwise>
                                    </c:choose>
									<button class="btn btn-windows delete" type="button" onclick="deleteAfterSaleDep()">删除</button>
									<span class="red">${afterSale}</span>
								</div>
								<div class="col-md-12 col-sm-12 col-xs-12 p0 over_auto">
									<table id="share_table_id" class="table table-bordered table-condensed mt5 table_wrap table_input left_table">
										<thead>
											<tr>
												<th class="info"><input type="checkbox" onchange="checkAll(this, 'afterSaleDep_list_tbody_id')" />
												</th>
												<th class="info">分支（或服务）机构名称</th>
												<th class="info">类别</th>
												<th class="info">所在省市县</th>
												<th class="info">负责人</th>
												<th class="info">联系电话</th>
											</tr>
										</thead>
										<tbody id="afterSaleDep_list_tbody_id">
											<c:forEach items="${currSupplier.listSupplierAfterSaleDep}" var="afterSaleDep" varStatus="dep">
												<tr <c:if test="${fn:contains(audit,afterSaleDep.id)}"> onmouseover="errorMsg('${afterSaleDep.id}')"</c:if>>
													<input type="hidden" name='listSupplierAfterSaleDep[${dep.index }].id' value="${afterSaleDep.id}" />
													<input type="hidden" name='listSupplierAfterSaleDep[${dep.index }].supplierId' value="${afterSaleDep.supplierId}" />
													<td class="tc" <c:if test="${fn:contains(audit,afterSaleDep.id)}">style="border: 1px solid red;" </c:if>>
														<input type="checkbox" value="${afterSaleDep.id}" />
													</td>
													<td class="tc" <c:if test="${fn:contains(audit,afterSaleDep.id)}">style="border: 1px solid red;" </c:if>>
													 <div class="w300 fl">
													 	<input type='text' style='border:0px;' name='listSupplierAfterSaleDep[${dep.index }].name' maxlength="90" value='${afterSaleDep.name}' <c:if test="${!fn:contains(audit,afterSaleDep.id)&&currSupplier.status==2}">readonly='readonly' </c:if>>
													 </div> 
													</td>
													<td class="tc" <c:if test="${fn:contains(audit,afterSaleDep.id)}">style="border: 1px solid red;" </c:if>>
													  <div class="w120 fl">
														<select name="listSupplierAfterSaleDep[${dep.index }].type" class="w100p border0" <c:if test="${!fn:contains(audit,afterSaleDep.id)&&currSupplier.status==2}">onchange="this.selectedIndex=this.defaultIndex;"</c:if>>
															<option value="1" <c:if test="${afterSaleDep.type == 1}"> selected="selected"</c:if> >自营</option>
															<option value="2" <c:if test="${afterSaleDep.type == 2}"> selected="selected" </c:if> >合作</option>
														</select>
													   </div>
													</td>
													<td class="tc" <c:if test="${fn:contains(audit,afterSaleDep.id)}">style="border: 1px solid red;" </c:if>> 
													  <div class="fl w200">
													  	<input type='text' style='border:0px;' name='listSupplierAfterSaleDep[${dep.index }].address' maxlength="30" value='${afterSaleDep.address}' <c:if test="${!fn:contains(audit,afterSaleDep.id)&&currSupplier.status==2}">readonly='readonly' </c:if>>
													  </div>
												    </td>
													<td class="tc" <c:if test="${fn:contains(audit,afterSaleDep.id)}">style="border: 1px solid red;" </c:if>> 
													 <div class="fl w200">
													    <input type='text' style='border:0px;' name='listSupplierAfterSaleDep[${dep.index }].leadName' maxlength="20" value='${afterSaleDep.leadName}' <c:if test="${!fn:contains(audit,afterSaleDep.id)&&currSupplier.status==2}">readonly='readonly' </c:if>>
													 </div>
												    </td>
													<td class="tc" <c:if test="${fn:contains(audit,afterSaleDep.id)}">style="border: 1px solid red;" </c:if>> 
													   <div class="fl w200">
													   		<input type='text' style='border:0px;' name='listSupplierAfterSaleDep[${dep.index }].mobile' onkeyup="value=value.replace(/[^\d-]/g,'')" value='${afterSaleDep.mobile}' <c:if test="${!fn:contains(audit,afterSaleDep.id)&&currSupplier.status==2}">readonly='readonly' </c:if>>
													   </div>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					
					<!-- 参加政府或军队采购经历登记表 -->
					<div class="clear">
						<h2 class="count_flow clear pt20"> <i>6</i> 参加政府或军队采购经历 </h2>
						<div class="col-md-12 col-sm-12 col-xs-12 p0 ul_list mb20">
							<div class="col-md-12 col-sm-12 col-xs-12 p15 mt20">
								<div class="col-md-12 col-sm-12 col-xs-12 p0 mb20">
									<textarea class="col-md-12 col-xs-12 col-sm-12 h80" maxlength="1000"  
										onkeyup="checkCharLimit('purchaseExperience','limit_char_purchaseExperience',1000);if(value.length==1000){layer.msg('字数过多，不可超过1000字！')}" 
										name="purchaseExperience" id="purchaseExperience"
										<c:if test="${!fn:contains(audit,'purchaseExperience')&&currSupplier.status==2}">readonly='readonly' </c:if>
										<c:if test="${fn:contains(audit,'purchaseExperience')}">style="border: 1px solid red;" onmouseover="errorMsg('purchaseExperience')"</c:if>>${currSupplier.purchaseExperience}</textarea>
									<span class="sm_tip fr">还可输入 <span id="limit_char_purchaseExperience">1000</span> 个字</span>
								</div>
							</div>
						</div>
					</div>
					<div class="clear">
						<h2 class="count_flow clear pt20"> <i>7</i><font color=red>*</font> 公司简介 </h2>
						<div class="col-md-12 col-sm-12 col-xs-12 p0 ul_list mb50">
							<div class="col-md-12 col-sm-12 col-xs-12 p15 mt20">
								<div class="col-md-12 col-sm-12 col-xs-12 p0 mb20">
									<textarea class="col-md-12 col-xs-12 col-sm-12 h80" required="required" maxlength="1000"  
										onkeyup="checkCharLimit('description','limit_char_description',1000);if(value.length==1000){layer.msg('字数过多，不可超过1000字！')}" 
										placeholder="包括供应商的基本情况、组织机构设置、人员情况以及产品信息等内容，字数请控制在1000字以内。" 
										name="description" id="description"
										<c:if test="${!fn:contains(audit,'description')&&currSupplier.status==2}">readonly='readonly' </c:if>
										<c:if test="${fn:contains(audit,'description')}">style="border: 1px solid red;" onmouseover="errorMsg('description')"</c:if>>${currSupplier.description}</textarea>
									<span class="sm_tip fr">还可输入 <span id="limit_char_description">1000</span> 个字</span>
									<div class="cue" style="margin-top:50px;">
										<sf:errors path="description" />
									</div>
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
		<input type="hidden" id="afterSaleIndex" value="${fn:length(currSupplier.listSupplierAfterSaleDep)}">
		<div class="btmfix">
			<div style="margin-top: 15px;text-align: center;">
				<button type="button" class="btn save" onclick="temporarySave();">暂存</button>
				<button type="button" class="btn" onclick="saveBasicInfo('1')">下一步</button>
			</div>
		</div>
		<div class="footer_margin">
   			<jsp:include page="../../../../../index_bottom.jsp"></jsp:include>
   		</div>
	</body>
</html>
<script type="text/javascript">
    //对比例进行数据校验
    /*$(".proportion_vali").focus(function(){
        $(this).attr("data-oval",$(this).val()); //将当前值存入自定义属性
    }).blur(function(){
        var oldVal=($(this).attr("data-oval")); //获取原值
        var newVal=($(this).val()); //获取当前值
        if (oldVal!=newVal){
            var _val = $(this).val();
            if(_val.indexOf('.')!=-1){
                if(parseFloat(_val)>100){
                    $(this).val("");
                    layer.msg("请输入正确的比例数据格式,不能超过100", {offset: '300px'});
                }else{
                    var reg = /\d+\.\d{0,2}?$/;
                    if(!reg.test(_val)) {
                        $(this).val("");
                        layer.msg("请输入正确的金额,保留两位小数", {
                            offset: '300px'
                        });
                    }
                }
            }else{
                if(!positiveRegular(_val)){
                    $(this).val("");
                    layer.msg("请输入正确的比例数据格式,保留两位小数", {offset: '300px'});
                }else if(parseInt(_val)<50){
                    $(this).val("");
                    layer.msg("请输入正确的比例数据格式,不小于50", {offset: '300px'});
                }else if(parseInt(_val)>100){
                    $(this).val("");
                    layer.msg("请输入正确的比例数据格式,不能超过100", {offset: '300px'});
                };
            }
        }
    });*/
    //loadProportion();
    function loadProportion() {
        $(".proportion_vali").focus(function(){
            $(this).attr("data-oval",$(this).val()); //将当前值存入自定义属性
        }).blur(function(){
            var oldVal=($(this).attr("data-oval")); //获取原值
            var newVal=($(this).val()); //获取当前值
            if (oldVal!=newVal){
                var _val = $(this).val();
                if(_val.indexOf('.')!=-1){
                    if(parseFloat(_val)>100){
                        $(this).val("");
                        layer.msg("请输入正确的比例数据格式,不能超过100", {offset: '300px'});
                    }else{
                        var reg = /\d+\.\d{0,2}?$/;
                        if(!reg.test(_val)) {
                            $(this).val("");
                            layer.msg("请输入正确的金额,保留两位小数", {
                                offset: '300px'
                            });
                        }
                    }
                }else{
                    if(!positiveRegular(_val)){
                        $(this).val("");
                        layer.msg("请输入正确的比例数据格式,保留两位小数", {offset: '300px'});
                    }else if(parseInt(_val)>100){
                        $(this).val("");
                        layer.msg("请输入正确的比例数据格式,不能超过100", {offset: '300px'});
                    };
                }
            }
        });
    }
    /*$(".proportion_vali").on('change',function () {
        var _val = $(this).val();
        if(_val.indexOf('.')!=-1){
            if(parseFloat(_val)>100){
                $(this).val("");
                layer.msg("请输入正确的比例数据格式,不能超过100", {offset: '300px'});
            }else{
                var reg = /\d+\.\d{0,2}?$/;
                if(!reg.test(_val)) {
                    $(this).val("");
                    layer.msg("请输入正确的金额,保留两位小数", {
                        offset: '300px'
                    });
                }
            }
        }else{
            if(!positiveRegular(_val)){
                $(this).val("");
                layer.msg("请输入正确的比例数据格式,保留两位小数", {offset: '300px'});
            }else if(parseInt(_val)>100){
                $(this).val("");
                layer.msg("请输入正确的比例数据格式,不能超过100", {offset: '300px'});
            };
        }
    })*/
</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/regex.js"></script>
<script type="text/javascript">
	controlForm();
	function controlForm(){
		// 如果供应商状态是退回修改，控制表单域的编辑与不可编辑
		var currSupplierSt = '${currSupplier.status}';
		//alert(currSupplierSt);
		if(currSupplierSt == '2'){
			$("input[type='text'],select,textarea").attr('disabled',true);
			//enableForm();// 调试用代码
			//$("input[type='text'],select,textarea").removeAttr('readonly');//调试用代码
			$("input[type='text'],select,textarea").each(function(){
				// 或者$(this).attr("style").indexOf("border: 1px solid #ef0000;") > 0
				// 或者$(this).css("border") == '1px solid rgb(239, 0, 0)'
				if($(this).css("border-top-color") == 'rgb(255, 0, 0)' 
					|| $(this).css("border-bottom-color") == 'rgb(255, 0, 0)' 
					|| $(this).css("border-left-color") == 'rgb(255, 0, 0)' 
					|| $(this).css("border-right-color") == 'rgb(255, 0, 0)' 
					|| $(this).parents("td").css("border-top-color") == 'rgb(255, 0, 0)'
					|| $(this).parents("td").css("border-bottom-color") == 'rgb(255, 0, 0)'
					|| $(this).parents("td").css("border-left-color") == 'rgb(255, 0, 0)'
					|| $(this).parents("td").css("border-right-color") == 'rgb(255, 0, 0)'
				){
					$(this).attr('disabled',false);
				}
			});
			/* $("select").change(function(){
				this.selectedIndex=this.defaultIndex;
			}); */
			// 营业期限复选框
			//console.log('${audit}');
			if('${audit}'.indexOf('businessStartDate') < 0){
				$("input[type='checkbox'][name='branchName']").attr('disabled',true);
			}
			// 营业期限选择器
			if($("input[type='checkbox'][name='branchName']").val() == "1"){
				$("#expireDate").attr('disabled',true);
			}
		}
	}
	
	// 表单可编辑
	function enableForm(){
		var currSupplierSt = '${currSupplier.status}';
		if(currSupplierSt == '2'){
			$("input[type='text'],input[type='checkbox'],select,textarea").attr('disabled',false);
		}
	}
	
	// 审核通过的项不能删除(列表)
	function checkIsDelForTuihui(checkedObjs, audit){
		var currSupplierSt = '${currSupplier.status}';
		if(currSupplierSt == '2'){
			var isDel = true;
			$(checkedObjs).each(function(index) {
				if(audit.indexOf($(this).val()) < 0){
					isDel = false;
					return false;
				}
			});
			return isDel;
		}
		return true;
	}
</script>

<script type="text/javascript">
	// 核对字符长度
	function checkCharLimit(inputId,countId,limit){
		var inputVal = $("#"+inputId).val();
		var inputLen = inputVal ? inputVal.length : 0;
		$("#"+countId).text(limit - inputLen);
	}
	checkCharLimit('businessScope','limit_char_businessScope',1000);// 经营范围
	checkCharLimit('purchaseExperience','limit_char_purchaseExperience',1000);// 参加政府或军队采购经历
	checkCharLimit('description','limit_char_description',1000);// 公司简介
	// 境外分支生产经营范围
	$("textarea[name^='branchList'][name$='businessSope']").each(function(){
		checkCharLimit($(this).attr("id"),$(this).next().children().attr("id"),1000);
	});
</script>