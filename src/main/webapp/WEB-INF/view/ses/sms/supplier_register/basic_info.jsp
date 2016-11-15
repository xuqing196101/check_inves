<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file ="/WEB-INF/view/common/tags.jsp" %>
<%@include file="/WEB-INF/view/front.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript">
$(function() {
	$("#page_ul_id").find("li").click(function() {
		var id = $(this).attr("id");
		var page = "tab-" + id.charAt(id.length - 1);
		$("input[name='defaultPage']").val(page);
	});
	var defaultPage = "${defaultPage}";
	if (defaultPage) {
		var num = defaultPage.charAt(defaultPage.length - 1);
		$("#page_ul_id").find("li").each(function(index) {
			if (index == num - 1) {
				$(this).attr("class", "active");
			} else {
				$(this).removeAttr("class");
			}
		});
		$("#tab_content_div_id").find(".tab-pane").each(function() {
			var id = $(this).attr("id");
			if (id == defaultPage) {
				$(this).attr("class", "tab-pane fade height-200 active in");
			} else {
				$(this).attr("class", "tab-pane fade height-200");
			}
		});
	}

	loadRootArea();
	autoSelected("business_select_id", "${currSupplier.businessType}");
	autoSelected("overseas_branch_select_id", "${currSupplier.overseasBranch}");

	if ("${currSupplier.status}" == 7) {
		showReason();
	}
});

/** 加载地区根节点 */
function loadRootArea() {
	$.ajax({
		url : globalPath + "/area/find_root_area.do",
		type : "post",
		dataType : "json",
		success : function(result) {
			var html = "";
			html += "<option value=''>请选择</option>";
			for ( var i = 0; i < result.length; i++) {
				html += "<option id='" + result[i].id + "' value='" + result[i].name + "'>" + result[i].name + "</option>";
			}
			$("#root_area_select_id").append(html);

			// 自动选中
			var rootArea = "${currSupplier.address}";
			if (rootArea)
				rootArea = rootArea.split(",")[0];
			if (rootArea) {
				autoSelected("root_area_select_id", rootArea);
				loadChildren();
			}

		},
	});
}

function loadChildren() {
	var id = $("#root_area_select_id").find("option:selected").attr("id");
	if (id) {
		$.ajax({
			url : globalPath + "/area/find_area_by_parent_id.do",
			type : "post",
			dataType : "json",
			data : {
				id : id
			},
			success : function(result) {
				var html = "";
				for ( var i = 0; i < result.length; i++) {
					html += "<option value='" + result[i].name + "'>" + result[i].name + "</option>";
				}
				$("#children_area_select_id").empty();
				$("#children_area_select_id").append(html);

				// 自动选中
				var childrenArea = "${currSupplier.address}";
				if (childrenArea)
					childrenArea = childrenArea.split(",")[1];
				if (childrenArea) {
					autoSelected("children_area_select_id", childrenArea);
				}
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
function saveBasicInfo(jsp) {
	$("input[name='jsp']").val(jsp);
	$("#basic_info_form_id").submit();

}

function openStockholder() {
	var supplierId = $("input[name='id']").val();
	if (!supplierId) {
		layer.msg("请暂存供应商基本信息 !", {
			offset : '300px',
		});
	} else {
		layer.open({
			type : 2,
			title : '添加供应商股东信息',
			// skin : 'layui-layer-rim', //加上边框
			area : [ '700px', '420px' ], //宽高
			offset : '100px',
			scrollbar : false,
			content : globalPath + '/supplier_stockholder/add_stockholder.html?&supplierId=' + supplierId + '&sign=1', //url
			closeBtn : 1, //不显示关闭按钮
		});
	}
}

function deleteStockholder() {
	var checkboxs = $("#stockholder_list_tbody_id").find(":checkbox:checked");
	var stockholderIds = "";
	var supplierId = $("input[name='id']").val();
	$(checkboxs).each(function(index) {
		if (index > 0) {
			stockholderIds += ",";
		}
		stockholderIds += $(this).val();
	});
	var size = checkboxs.length;
	if (size > 0) {
		layer.confirm("已勾选" + size + "条记录, 确定删除 !", {
			offset : '200px',
			scrollbar : false,
		}, function(index) {
			window.location.href = globalPath + "/supplier_stockholder/delete_stockholder.html?stockholderIds=" + stockholderIds + "&supplierId=" + supplierId;
			layer.close(index);

		});
	} else {
		layer.alert("请至少勾选一条记录 !", {
			offset : '200px',
			scrollbar : false,
		});
	}
}

function openFinance() {
	var supplierId = $("input[name='id']").val();
	if (!supplierId) {
		layer.msg("请暂存供应商基本信息 !", {
			offset : '300px',
		});
	} else {
		layer.open({
			type : 2,
			title : '添加供应商财务信息',
			// skin : 'layui-layer-rim', //加上边框
			area : [ '650px', '420px' ], //宽高
			offset : '100px',
			scrollbar : false,
			content : globalPath + '/supplier_finance/add_finance.html?&supplierId=' + supplierId + '&sign=1', //url
			closeBtn : 1, //不显示关闭按钮
		});
	}
}

function deleteFinance() {
	var checkboxs = $("#finance_list_tbody_id").find(":checkbox:checked");
	var financeIds = "";
	var supplierId = $("input[name='id']").val();
	$(checkboxs).each(function(index) {
		if (index > 0) {
			financeIds += ",";
		}
		financeIds += $(this).val();
	});
	var size = checkboxs.length;
	if (size > 0) {
		layer.confirm("已勾选" + size + "条记录, 确定删除 !", {
			offset : '200px',
			scrollbar : false,
		}, function(index) {
			window.location.href = globalPath + "/supplier_finance/delete_finance.html?financeIds=" + financeIds + "&supplierId=" + supplierId;
			layer.close(index);

		});
	} else {
		layer.alert("请至少勾选一条记录 !", {
			offset : '200px',
			scrollbar : false,
		});
	}
}

function autoSelected(id, v) {
	if (v) {
		$("#" + id).find("option").each(function() {
			var value = $(this).val();
			if (value == v) {
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
		type : 2,
		title : '审核反馈',
		closeBtn : 0, //不显示关闭按钮
		skin : 'layui-layer-lan', //加上边框
		area : [ '500px', '300px' ], //宽高
		offset : [ top, left ],
		shade : 0,
		maxmin : true,
		shift : 2,
		content : globalPath + '/supplierAudit/showReasonsList.html?&auditType=basic_page,finance_page,stockholder_page' + '&jsp=dialog_basic_reason' + '&supplierId=' + supplierId, //url
	});
}
</script>
</head>

<body>
	<div class="wrapper">
		<%@include file="supplierNav.jsp" %>
		<!--基本信息-->
		<div class="container content height-350">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<ul id="page_ul_id" class="nav nav-tabs bgdd supplier_tab">
							<li id="li_id_1" class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="s_news f18">详细信息</a>
							</li>
							<li id="li_id_2" class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" class="fujian f18">财务信息</a>
							</li>
							<li id="li_id_3" class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" class="fujian f18">股东信息</a>
							</li>
						</ul>
						<form id="basic_info_form_id" action="${pageContext.request.contextPath}/supplier/perfect_basic.html" method="post">
							<input name="id" value="${currSupplier.id}" type="hidden" /> <input name="defaultPage" value="${defaultPage}" type="hidden" /> <input name="jsp" type="hidden" />
							<div class="tab-content padding-top-20" id="tab_content_div_id">
								<!-- 详细信息 -->
								<div class="tab-pane fade active in height-450" id="tab-1">
									<div class=" margin-bottom-0">
										<h2 class="f16 jbxx">
											<i>01</i>基本信息
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 供应商名称：</span>
												<div class="input-append">
													<input class="span3" id="supplierName_input_id" type="text" name="supplierName" value="${currSupplier.supplierName}" /> <span class="fl span-err-msg mt3 ml5"></span>
												</div></li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 公司网址：</span>
												<div class="input-append">
													<input class="span3" type="text" name="website" value="${currSupplier.website}" />
												</div></li>
											<li class="col-md-6  p0 "><span class=""><i class="red">＊</i> 成立日期：</span>
												<div class="input-append">
													<fmt:formatDate value="${currSupplier.foundDate}" pattern="yyyy-MM-dd" var="foundDate" />
													<input class="span2" type="text" readonly="readonly" onClick="WdatePicker()" name="foundDate" value="${foundDate}" /> <span class="add-on"> <img src="${pageContext.request.contextPath}/public/ZHQ/images/time_icon.png" class="mb10" /> </span>
												</div></li>

											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 营业执照类型：</span>
												<div class="input-append">
													<select class="span3 fz13" name="businessType" id="business_select_id">
														<option>国有企业</option>
														<option>外资企业</option>
														<option>民营企业</option>
														<option>股份制企业</option>
														<option>私营企业</option>
													</select>
												</div></li>

											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 公司地址：</span>
												<div class="fl">
													<div class="input-append">
														<select class="w123 fz13" id="root_area_select_id" onchange="loadChildren()" name="address"></select> <select class="w123 ml30 fz13" id="children_area_select_id" name="address">
															<option value="">请选择省份</option>
														</select>
													</div>
												</div></li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 开户行名称：</span>
												<div class="input-append">
													<input class="span3" type="text" name="bankName" value="${currSupplier.bankName}" />
												</div>
											</li>

											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 开户行账号：</span>
												<div class="input-append">
													<input class="span3" type="text" name="bankAccount" value="${currSupplier.bankAccount}" />
												</div></li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 邮编：</span>
												<div class="input-append">
													<input class="span3" type="text" name="postCode" value="${currSupplier.postCode}" />
												</div></li>
											<div class="clear"></div>
										</ul>


										<h2 class="f16 jbxx mt20">
											<i>02</i>资质资信
										</h2>
										<ul class="list-unstyled list-flow">
											<li id="tax_li_id" class="col-md-6 p0"><span class="zzzx w245"><i class="red">＊</i> 近三个月完税凭证：</span> <u:upload id="taxcert_up" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierTaxCert}" auto="true" /> <u:show showId="taxcert_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierTaxCert}" /></li>
											<li id="bill_li_id" class="col-md-6 p0"><span class="zzzx w245"><i class="red">＊</i> 近三年银行基本账户年末对账单：</span> <u:upload id="billcert_up" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBillCert}" auto="true" /> <u:show showId="billcert_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBillCert}" /></li>
											<li id="security_li_id" class="col-md-6 p0"><span class="zzzx w245"><i class="red">＊</i> 近三个月缴纳社会保险金凭证：</span> <u:upload id="curitycert_up" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierSecurityCert}" auto="true" /> <u:show showId="curitycert_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierSecurityCert}" /></li>
											<li id="breach_li_id" class="col-md-6 p0"><span class="zzzx w245"><i class="red">＊</i> 近三年内无重大违法记录声明：</span> <u:upload id="bearchcert_up" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBearchCert}" auto="true" /> <u:show showId="bearchcert_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBearchCert}" /></li>
											<div class="clear"></div>
										</ul>

										<h2 class="f16 jbxx mt20">
											<i>03</i>法定代表人信息
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 姓名：</span>
												<div class="input-append">
													<input class="span3" type="text" name="legalName" value="${currSupplier.legalName}" />
												</div></li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 身份证号：</span>
												<div class="input-append">
													<input class="span3" type="text" name="legalIdCard" value="${currSupplier.legalIdCard}" />
												</div></li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 固定电话：</span>
												<div class="input-append">
													<input class="span3" type="text" name="legalTelephone" value="${currSupplier.legalTelephone}" />
												</div></li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 手机：</span>
												<div class="input-append">
													<input class="span3" type="text" name="legalMobile" value="${currSupplier.legalMobile}" />
												</div></li>
											<div class="clear"></div>
										</ul>
										<h2 class="f16 jbxx mt20">
											<i>04</i>联系人信息
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 姓名：</span>
												<div class="input-append">
													<input class="span3" type="text" name="contactName" value="${currSupplier.contactName}" />
												</div></li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 传真电话：</span>
												<div class="input-append">
													<input class="span3" type="text" name="contactFax" value="${currSupplier.contactFax}" />
												</div></li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 固定电话：</span>
												<div class="input-append">
													<input class="span3" type="text" name="contactTelephone" value="${currSupplier.contactTelephone}" />
												</div></li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 手机：</span>
												<div class="input-append">
													<input class="span3" type="text" name="contactMobile" value="${currSupplier.contactMobile}" />
												</div></li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 邮箱：</span>
												<div class="input-append">
													<input class="span3" type="text" name="contactEmail" value="${currSupplier.contactEmail}" />
												</div></li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 地址：</span>
												<div class="input-append">
													<input class="span3" type="text" name="contactAddress" value="${currSupplier.contactAddress}" />
												</div></li>
											<div class="clear"></div>
										</ul>
										<h2 class="f16 jbxx mt20">
											<i>05</i>营业执照
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 统一信用代码：</span>
												<div class="input-append">
													<input class="span3" type="text" name="creditCode" value="${currSupplier.creditCode}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 登记机关：</span>
												<div class="input-append">
													<input class="span3" type="text" name="registAuthority" value="${currSupplier.registAuthority}" />
												</div></li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 注册资本：</span>
												<div class="input-append">
													<input class="span3" type="text" name="registFund" value="${currSupplier.registFund}" />
												</div></li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 营业开始时间：</span>
												<div class="input-append">
													<fmt:formatDate value="${currSupplier.businessStartDate}" pattern="yyyy-MM-dd" var="businessStartDate" />
													<input class="span2" type="text" readonly="readonly" onClick="WdatePicker()" name="businessStartDate" value="${businessStartDate}" /> <span class="add-on"><img src="${pageContext.request.contextPath}/public/ZHQ/images/time_icon.png" class="mb10" /> </span>
												</div></li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 营业截止时间：</span>
												<div class="input-append">
													<fmt:formatDate value="${currSupplier.businessEndDate}" pattern="yyyy-MM-dd" var="businessEndDate" />
													<input class="span2" type="text" readonly="readonly" onClick="WdatePicker()" name="businessEndDate" value="${businessEndDate}" /> <span class="add-on"><img src="${pageContext.request.contextPath}/public/ZHQ/images/time_icon.png" class="mb10" /> </span>
												</div></li>

											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 生产经营地址：</span>
												<div class="input-append">
													<input class="span3" type="text" name="businessAddress" value="${currSupplier.businessAddress}" />
												</div></li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 邮编：</span>
												<div class="input-append">
													<input class="span3" type="text" name="businessPostCode" value="${currSupplier.businessPostCode}" />
												</div></li>
											<li id="business_li_id" class="col-md-6 p0"><span class=""><i class="red">＊</i> 营业执照：</span> <up:upload id="business_up" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" auto="true" /> <up:show showId="business_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" /></li>
											<li class="col-md-12 p0 mt10"><span class="fl">经营范围：</span>
												<div class="col-md-9 mt5 p0">
													<div class="col-md-12 p0">
														<textarea class="text_area col-md-12" title="不超过800个字" name="businessScope">${currSupplier.bankName}</textarea>
													</div>
												</div>
												<div class="clear"></div>
											</li>
											<div class="clear"></div>
										</ul>
										<h2 class="f16 jbxx mt20">
											<i>05</i>境外分支
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class="">境外分支结构：</span>
												<div class="input-append">
													<select class="span3 fz13" name="overseasBranch" id="overseas_branch_select_id">
														<option value="1">有</option>
														<option value="0">无</option>
													</select>
												</div></li>
											<li class="col-md-6 p0"><span class="">境外分支所在国家：</span>
												<div class="input-append">
													<input class="span3" name="branchCountry" type="text" value="${currSupplier.branchCountry}" />
												</div></li>
											<li class="col-md-6 p0"><span class="">分支地址：</span>
												<div class="input-append">
													<input class="span3" type="text" name="branchAddress" value="${currSupplier.branchAddress}" />
												</div></li>
											<li class="col-md-6 p0"><span class="">机构名称：</span>
												<div class="input-append">
													<input class="span3" type="text" name="branchName" value="${currSupplier.branchName}" />
												</div></li>
											<li class="col-md-6 p0"><span class="">分支生产经营范围：</span>
												<div class="input-append">
													<input class="span3" type="text" name="branchBusinessScope" value="${currSupplier.branchBusinessScope}" />
												</div></li>
											<div class="clear"></div>
										</ul>

									</div>
								</div>

								<!-- 财务信息 -->
								<div class="tab-pane fade height-300" id="tab-2">
									<div class="margin-bottom-0  categories">

										<h2 class="f16 jbxx mt20">
											<i>01</i>财务状况登记表
										</h2>
										<div class="overflow_h">
											<button type="button" class="btn fr mr0" onclick="deleteFinance()">删除</button>
											<button type="button" class="btn fr" onclick="openFinance()">新增</button>
										</div>
										<table class="table table-bordered table-condensed mt5">
											<thead>
												<tr>
													<th class="w30"><input type="checkbox" onchange="checkAllForFinance(this)" />
													</th>
													<th class="w50">年份</th>
													<th class="info">会计实务所名称</th>
													<th class="info">事务所联系电话</th>
													<th class="info">审计人姓名</th>
													<th class="info">指标</th>
													<th class="info">资产总额</th>
													<th class="info">负债总额</th>
													<th class="info">净资产总额</th>
													<th class="info">营业收入</th>
												</tr>
											</thead>
											<tbody id="finance_list_tbody_id">
												<c:forEach items="${currSupplier.listSupplierFinances}" var="finance" varStatus="vs">
													<tr>
														<td class="tc"><input type="checkbox" value="${finance.id}" />
														</td>
														<td class="tc">${finance.year}</td>
														<td class="tc">${finance.name}</td>
														<td class="tc">${finance.telephone}</td>
														<td class="tc">${finance.auditors}</td>
														<td class="tc">${finance.quota}</td>
														<td class="tc">${finance.totalAssets}</td>
														<td class="tc">${finance.totalLiabilities}</td>
														<td class="tc">${finance.totalNetAssets}</td>
														<td class="tc">${finance.taking}</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
										<table id="finance_attach_list_id" class="table table-bordered table-condensed mt5">
											<thead>
												<tr>
													<th class="w30"><input type="checkbox" onchange="checkAllForFinance(this)" />
													</th>
													<th class="w50">年份</th>
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
								</div>

								<!-- 股东信息 -->
								<div class="tab-pane fade height-200" id="tab-3">
									<div class="margin-bottom-0  categories">
										<h2 class="f16 jbxx mt20">
											<i>01</i>股东信息表
										</h2>
										<div class="overflow_h">
											<input type="button" class="btn fr mr0" onclick="deleteStockholder()" value="删除"> <input type="button" class="btn fr" onclick="openStockholder()" value="新增">
										</div>
										<table id="share_table_id" class="table table-bordered table-condensed mt5">
											<thead>
												<tr>
													<th class="info"><input type="checkbox" onchange="checkAll(this, 'stockholder_list_tbody_id')" />
													</th>
													<th class="info">出资人名称或姓名</th>
													<th class="info">出资人性质</th>
													<th class="info">统一社会信用代码或身份证号码</th>
													<th class="info">出资金额或股份（万元/万份）</th>
													<th class="info">比例</th>
												</tr>
											</thead>
											<tbody id="stockholder_list_tbody_id">
												<c:forEach items="${currSupplier.listSupplierStockholders}" var="stockholder" varStatus="vs">
													<tr>
														<td class="tc"><input type="checkbox" value="${stockholder.id}" />
														</td>
														<td class="tc">${stockholder.name}</td>
														<td class="tc">${stockholder.nature}</td>
														<td class="tc">${stockholder.identity}</td>
														<td class="tc">${stockholder.shares}</td>
														<td class="tc">${stockholder.proportion}</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
							<div class="mt40 tc mb50">
								<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveBasicInfo('basic_info')">暂存</button>
								<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveBasicInfo('supplier_type')">下一步</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<form target="_blank" id="download_form_id" action="${pageContext.request.contextPath}/supplier/download.html" method="post">
		<input type="hidden" name="fileName" />
	</form>

</body>
</html>
