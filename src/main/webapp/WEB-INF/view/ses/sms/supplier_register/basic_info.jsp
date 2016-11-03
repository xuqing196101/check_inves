<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../../../../../index_head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>供应商完善基本信息</title>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/upload.js"></script>
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
	});
	
	/** 加载地区根节点 */
	function loadRootArea() {
		$.ajax({
			url : "${pageContext.request.contextPath}/area/find_root_area.do",
			type : "post",
			dataType : "json",
			success : function(result) {
				var html = "";
				html += "<option value=''>请选择</option>";
				for(var i = 0; i < result.length; i++) {
					html += "<option id='" + result[i].id + "' value='" + result[i].name + "'>" +  result[i].name + "</option>";
				}
				$("#root_area_select_id").append(html);
				
				// 自动选中
				var rootArea = "${currSupplier.address}";
				if (rootArea) rootArea = rootArea.split(",")[0];
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
				url : "${pageContext.request.contextPath}/area/find_area_by_parent_id.do",
				type : "post",
				dataType : "json",
				data : {
					id : id
				},
				success : function(result) {
					var html = "";
					for(var i = 0; i < result.length; i++) {
						html += "<option value='" + result[i].name + "'>" +  result[i].name + "</option>";
					}
					$("#children_area_select_id").empty();
					$("#children_area_select_id").append(html);
					
					// 自动选中
					var childrenArea = "${currSupplier.address}";
					if (childrenArea) childrenArea = childrenArea.split(",")[1];
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
				content : '${pageContext.request.contextPath}/supplier_stockholder/add_stockholder.html?&supplierId=' + supplierId, //url
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
			},function(index) {
				window.location.href = "${pageContext.request.contextPath}/supplier_stockholder/delete_stockholder.html?stockholderIds=" + stockholderIds +"&supplierId=" + supplierId;
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
				area : [ '700px', '420px' ], //宽高
				offset : '100px',
				scrollbar : false,
				content : '${pageContext.request.contextPath}/supplier_finance/add_finance.html?&supplierId=' + supplierId, //url
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
			},function(index) {
				window.location.href = "${pageContext.request.contextPath}/supplier_finance/delete_finance.html?financeIds=" + financeIds +"&supplierId=" + supplierId;
				layer.close(index);
				
			});
		} else {
			layer.alert("请至少勾选一条记录 !", {
				offset : '200px',
				scrollbar : false,
			});
		}
	}
	
	function uploadNew(id) {
		$("#" + id).find("div").remove();
		var name = "";
		if (id == "tax_li_id") {
			name = "taxCertFile";
		} else if (id == "bill_li_id") {
			name = "billCertFile";
		} else if (id == "security_li_id") {
			name = "securityCertFile";
		} else if (id == "breach_li_id") {
			name = "breachCertFile";
		}
		var html = "<div class='input-append'>";
		html += "<div class='uploader orange h32 m0 fz8'>";
		html += "<input type='text' class='filename fz8 h32' readonly='readonly'/>";
		html += "<input type='button' name='file' class='button' value='选择...'/>";
		html += "<input name='"+ name +"' type='file' size='30'/>";
		html += "</div>";
		html += "</div>";
		$("#" + id).append(html);
		loadFilePlug();
	}
	
	function downloadFile(fileName) {
		$("input[name='fileName']").val(fileName);
		$("#download_form_id").submit();
	}
	
	function autoSelected(id, v) {
		if (v) {
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
</script>

<script type="text/javascript">
	/**$(function() {
		var supplierId = $("input[name='id']").val();
		$.ajax({
			url : "${pageContext.request.contextPath}/supplierAudit/showReasonsList.do",
			type : "post",
			dataType : "json",
			data : {
				supplierId : supplierId
			},
			success : function(result) {
				$(".audit_msg_sign_div_class").each(function(index) {
					var ele = $(this).find("input");
					if (!ele.size()) {
						ele = $(this).find("a");
					}
					if (!ele.size()) {
						ele = $(this).find("select");
					}
					var v = ele.attr("name");
					for (var i = 0; i < result.length; i++) {
						// console.info(v + "---" + result[i].auditField);
						if(v == result[i].auditField) {
							var html = "<a id='a_msg_" + index + "' class='ml10 red fz17' href='javascript:void(0)' onclick='showMsg(this)'><span style='display: none;'>";
							html += result[i].suggest;
							html += "</span><img style='margin-left: 25px; width: 23px; height: 23px;' src='${pageContext.request.contextPath}/public/ZHQ/images/pumpkin.png'></a>";
							$(this).append(html);
							html = "";
						}
					}
				});
			},
		});
	});
	
	function showMsg(ele) {
		var v = $(ele).find("span").text();
		v = $.trim(v);
		var id = $(ele).attr("id");
		layer.tips(v, "#" + id, {
			tips : 1
		});
	}*/
</script>

</head>

<body>
	<div class="wrapper">
		<!-- 项目戳开始 -->
		<div class="container clear margin-top-30">
			<h2 class="padding-20 mt40 ml30">
				<span class="new_step current fl"><i class="">1</i>
					<div class="line"></div> <span class="step_desc_01">用户名密码</span> </span> <span class="new_step current fl"><i class="">2</i>
					<div class="line"></div> <span class="step_desc_02">基本信息</span> </span> <span class="new_step fl"><i class="">3</i>
					<div class="line"></div> <span class="step_desc_01">供应商类型</span> </span> <span class="new_step fl"><i class="">4</i>
					<div class="line"></div> <span class="step_desc_02">专业信息</span> </span> <span class="new_step fl"><i class="">5</i>
					<div class="line"></div> <span class="step_desc_01">品目信息</span> </span> <span class="new_step fl"><i class="">6</i>
					<div class="line"></div> <span class="step_desc_02">产品信息</span> </span> <span class="new_step fl"><i class="">7</i>
					<div class="line"></div> <span class="step_desc_01">初审采购机构</span> </span> <span class="new_step fl"><i class="">8</i>
					<div class="line"></div> <span class="step_desc_02">打印申请表</span> </span> <span class="new_step fl"><i class="">9</i> 
					<span class="step_desc_01">申请表承诺书上传</span> 
				</span>
				<div class="clear"></div>
			</h2>
		</div>

		<!--基本信息-->
		<div class="container content height-350">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<ul id="page_ul_id" class="nav nav-tabs bgdd">
							<li id="li_id_1" class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="s_news f18">详细信息</a></li>
							<li id="li_id_2" class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" class="fujian f18">财务信息</a></li>
							<li id="li_id_3" class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" class="fujian f18">股东信息</a></li>
						</ul>
						<form id="basic_info_form_id" action="${pageContext.request.contextPath}/supplier/perfect_basic.html" method="post" enctype="multipart/form-data">
							<input name="id" value="${currSupplier.id}" type="hidden" /> 
							<input name="defaultPage" value="${defaultPage}" type="hidden" /> 
							<input name="jsp" type="hidden" />
							<div class="tab-content padding-top-20" id="tab_content_div_id">
								<!-- 详细信息 -->
								<div class="tab-pane fade active in height-450" id="tab-1">
									<div class=" margin-bottom-0">
										<h2 class="f16 jbxx">
											<i>01</i>基本信息
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 供应商名称：</span>
												<div class="input-append audit_msg_sign_div_class">
													<input class="span3" id="supplierName_input_id" type="text" name="supplierName" value="${currSupplier.supplierName}" />
													<span class="fl span-err-msg mt3 ml5"></span>
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 公司网址：</span>
												<div class="input-append audit_msg_sign_div_class">
													<input class="span3" type="text" name="website" value="${currSupplier.website}" />
												</div>
											</li>
											<li class="col-md-6  p0 "><span class=""><i class="red">＊</i> 成立日期：</span>
												<div class="input-append audit_msg_sign_div_class">
													<fmt:formatDate value="${currSupplier.foundDate}" pattern="yyyy-MM-dd" var="foundDate"/>
													<input class="span2" type="text" readonly="readonly" onClick="WdatePicker()" name="foundDate" value="${foundDate}" /> 
													<span class="add-on"> 
														<img src="${pageContext.request.contextPath}/public/ZHQ/images/time_icon.png" class="mb10" /> 
													</span>
												</div>
											</li>
											
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 营业执照类型：</span>
												<div class="input-append audit_msg_sign_div_class">
													<select class="span3 fz13" name="businessType" id="business_select_id">
														<option>国有企业</option>
														<option>外资企业</option>
														<option>民营企业</option>
														<option>股份制企业</option>
														<option>私营企业</option>
													</select>
												</div>
											</li>

											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 公司地址：</span>
												<div class="fl">
													<div class="input-append">
														<select class="w123 fz13" id="root_area_select_id" onchange="loadChildren()" name="address"></select>
														<select class="w123 ml30 fz13" id="children_area_select_id" name="address">
															<option value="">请选择省份</option>
														</select>
													</div>
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 开户行名称：</span>
												<div class="input-append">
													<input class="span3" type="text" name="bankName" value="${currSupplier.bankName}" />
												</div></li>

											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 开户行账号：</span>
												<div class="input-append">
													<input class="span3" type="text" name="bankAccount" value="${currSupplier.bankAccount}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 邮编：</span>
												<div class="input-append">
													<input class="span3" type="text" name="postCode" value="${currSupplier.postCode}" />
												</div>
											</li>
											<div class="clear"></div>
										</ul>


										<h2 class="f16 jbxx mt40">
											<i>02</i>资质资信
										</h2>
										<ul class="list-unstyled list-flow">
											<li id="tax_li_id" class="col-md-6 p0"><span class="zzzx w245"><i class="red">＊</i> 近三个月完税凭证：</span>
												<c:if test="${currSupplier.taxCert != null}">
													<div class="audit_msg_sign_div_class">
														<a name="taxCert" class="color7171C6" href="javascript:void(0)" onclick="downloadFile('${currSupplier.taxCert}')">下载附件</a>
														<a title="重新上传" class="ml10 red fz17" href="javascript:void(0)" onclick="uploadNew('tax_li_id')">☓</a>
													</div>
												</c:if>
												<c:if test="${currSupplier.taxCert == null}">
													<div class="input-append">
														<div class="uploader orange h32 m0 fz8">
															<input type="text" class="filename h32 fz8" readonly="readonly"/>
															<input type="button" name="file" class="button" value="选择..."/>
															<input name="taxCertFile" type="file" size="30"/>
														</div>
													</div>
												</c:if>
											</li>
											<li id="bill_li_id" class="col-md-6 p0"><span class="zzzx w245"><i class="red">＊</i> 近三年银行基本账户年末对账单：</span>
												<c:if test="${currSupplier.billCert != null}">
													<div class="audit_msg_sign_div_class">
														<a name="billCert" class="color7171C6" href="javascript:void(0)" onclick="downloadFile('${currSupplier.billCert}')">下载附件</a>
														<a title="重新上传" class="ml10 red fz17" href="javascript:void(0)" onclick="uploadNew('bill_li_id')">☓</a>
													</div>
												</c:if>
												<c:if test="${currSupplier.billCert == null}">
													<div class="input-append">
														<div class="uploader orange h32 m0 fz8">
															<input type="text" class="filename h32 fz8" readonly="readonly"/>
															<input type="button" name="file" class="button" value="选择..."/>
															<input name="billCertFile" type="file" size="30"/>
														</div>
													</div>
												</c:if>
											</li>
											<li id="security_li_id" class="col-md-6 p0"><span class="zzzx w245"><i class="red">＊</i> 近三个月缴纳社会保险金凭证：</span>
												<c:if test="${currSupplier.securityCert != null}">
													<div>
														<a class="color7171C6" href="javascript:void(0)" onclick="downloadFile('${currSupplier.securityCert}')">下载附件</a>
														<a title="重新上传" class="ml10 red fz17" href="javascript:void(0)" onclick="uploadNew('security_li_id')">☓</a>
													</div>
												</c:if>
												<c:if test="${currSupplier.securityCert == null}">
													<div class="input-append">
														<div class="uploader orange h32 m0 fz8">
															<input type="text" class="filename h32 fz8" readonly="readonly"/>
															<input type="button" name="file" class="button" value="选择..."/>
															<input name="securityCertFile" type="file" size="30"/>
														</div>
													</div>
												</c:if>
											</li>
											<li id="breach_li_id" class="col-md-6 p0"><span class="zzzx w245"><i class="red">＊</i> 近三年内无重大违法记录声明：</span>
												<c:if test="${currSupplier.breachCert != null}">
													<div>
														<a class="color7171C6" href="javascript:void(0)" onclick="downloadFile('${currSupplier.breachCert}')">下载附件</a>
														<a title="重新上传" class="ml10 red fz17" href="javascript:void(0)" onclick="uploadNew('breach_li_id')">☓</a>
													</div>
												</c:if>
												<c:if test="${currSupplier.breachCert == null}">
													<div class="input-append">
														<div class="uploader orange h32 m0 fz8">
															<input type="text" class="filename h32 fz8" readonly="readonly"/>
															<input type="button" name="file" class="button" value="选择..."/>
															<input name="breachCertFile" type="file" size="30"/>
														</div>
													</div>
												</c:if>
											</li>
											<div class="clear"></div>
										</ul>

										<h2 class="f16 jbxx mt40">
											<i>03</i>法定代表人信息
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 姓名：</span>
												<div class="input-append">
													<input class="span3" type="text" name="legalName" value="${currSupplier.legalName}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 身份证号：</span>
												<div class="input-append">
													<input class="span3" type="text" name="legalIdCard" value="${currSupplier.legalIdCard}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 固定电话：</span>
												<div class="input-append">
													<input class="span3" type="text" name="legalTelephone" value="${currSupplier.legalTelephone}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 手机：</span>
												<div class="input-append">
													<input class="span3" type="text" name="legalMobile" value="${currSupplier.legalMobile}" />
												</div>
											</li>
											<div class="clear"></div>
										</ul>
										<h2 class="f16 jbxx mt40">
											<i>04</i>联系人信息
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 姓名：</span>
												<div class="input-append">
													<input class="span3" type="text" name="contactName" value="${currSupplier.contactName}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 传真电话：</span>
												<div class="input-append">
													<input class="span3" type="text" name="contactFax" value="${currSupplier.contactFax}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 固定电话：</span>
												<div class="input-append">
													<input class="span3" type="text" name="contactTelephone" value="${currSupplier.contactTelephone}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 手机：</span>
												<div class="input-append">
													<input class="span3" type="text" name="contactMobile" value="${currSupplier.contactMobile}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 邮箱：</span>
												<div class="input-append">
													<input class="span3" type="text" name="contactEmail" value="${currSupplier.contactEmail}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 地址：</span>
												<div class="input-append">
													<input class="span3" type="text" name="contactAddress" value="${currSupplier.contactAddress}" />
												</div>
											</li>
											<div class="clear"></div>
										</ul>
										<h2 class="f16 jbxx mt40">
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
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 注册资本：</span>
												<div class="input-append">
													<input class="span3" type="text" name="registFund" value="${currSupplier.registFund}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 营业开始时间：</span>
												<div class="input-append">
													<fmt:formatDate value="${currSupplier.businessStartDate}" pattern="yyyy-MM-dd" var="businessStartDate"/>
													<input class="span2" type="text" readonly="readonly" onClick="WdatePicker()" name="businessStartDate" value="${businessStartDate}" /> 
													<span class="add-on"><img src="${pageContext.request.contextPath}/public/ZHQ/images/time_icon.png" class="mb10" /> </span>
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 营业截止时间：</span>
												<div class="input-append">
													<fmt:formatDate value="${currSupplier.businessEndDate}" pattern="yyyy-MM-dd" var="businessEndDate"/>
													<input class="span2" type="text" readonly="readonly" onClick="WdatePicker()" name="businessEndDate" value="${businessEndDate}" /> 
													<span class="add-on"><img src="${pageContext.request.contextPath}/public/ZHQ/images/time_icon.png" class="mb10" /> </span>
												</div>
											</li>

											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 生产经营地址：</span>
												<div class="input-append">
													<input class="span3" type="text" name="businessAddress" value="${currSupplier.businessAddress}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 邮编：</span>
												<div class="input-append">
													<input class="span3" type="text" name="businessPostCode" value="${currSupplier.businessPostCode}" />
												</div>
											</li>
											<li id="business_li_id" class="col-md-6 p0"><span class=""><i class="red">＊</i> 营业执照：</span>
												<c:if test="${currSupplier.businessCert != null}">
													<div>
														<a class="color7171C6" href="javascript:void(0)" onclick="downloadFile('${currSupplier.businessCert}')">下载附件</a>
														<a title="重新上传" class="ml10 red fz17" href="javascript:void(0)" onclick="uploadNew('business_li_id')">☓</a>
													</div>
												</c:if>
												<c:if test="${currSupplier.businessCert == null}">
													<div class="input-append">
														<div class="uploader orange h32 m0 fz8">
															<input type="text" class="filename h32 fz8" readonly="readonly"/>
															<input type="button" name="file" class="button" value="选择..."/>
															<input name="businessCertFile" type="file" size="30"/>
														</div>
													</div>
												</c:if>
											</li>
											<li class="col-md-12 p0 mt10"><span class="fl">经营范围：</span>
												<div class="col-md-9 mt5">
													<div class="row _mr20">
														<textarea class="text_area col-md-12" title="不超过800个字" name="businessScope">${currSupplier.bankName}</textarea>
													</div>
												</div>
												<div class="clear"></div></li>
											<div class="clear"></div>
										</ul>
										<h2 class="f16 jbxx mt40">
											<i>05</i>境外分支
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class="">境外分支结构：</span>
												<div class="input-append">
													<select class="span3 fz13" name="overseasBranch" id="overseas_branch_select_id">
														<option value="1">有</option>
														<option value="0">无</option>
													</select>
												</div>
											</li>
											<li class="col-md-6 p0"><span class="">境外分支所在国家：</span>
												<div class="input-append">
													<input class="span3" name="branchCountry" type="text" value="${currSupplier.branchCountry}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class="">分支地址：</span>
												<div class="input-append">
													<input class="span3" type="text" name="branchAddress" value="${currSupplier.branchAddress}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class="">机构名称：</span>
												<div class="input-append">
													<input class="span3" type="text" name="branchName" value="${currSupplier.branchName}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class="">分支生产经营范围：</span>
												<div class="input-append">
													<input class="span3" type="text" name="branchBusinessScope" value="${currSupplier.branchBusinessScope}" />
												</div>
											</li>
											<div class="clear"></div>
										</ul>

									</div>
								</div>

								<!-- 财务信息 -->
								<div class="tab-pane fade height-300" id="tab-2">
									<div class="margin-bottom-0  categories">

										<h2 class="f16 jbxx mt40">
											<i>01</i>财务状况登记表
										</h2>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="deleteFinance()">删除</button>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="openFinance()">新增</button>
										<table id="finance_table_id" class="table table-bordered table-condensed">
											<thead>
												<tr>
													<th class="info"><input type="checkbox" onchange="checkAll(this, 'finance_list_tbody_id')" /></th>
													<th class="info">年份</th>
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
														<td class="tc"><input type="checkbox" value="${finance.id}" /></td>
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
										<div>
											<c:forEach items="${currSupplier.listSupplierFinances}" var="finance" varStatus="vs">
												<h5>${finance.year}年</h5>
												<ul class="list-unstyled list-flow" id="ul_id_${vs.index}">
													<li class="col-md-6 p0"><span class="zzzx">财务审计报告的审计意见：</span>
														 <div class="input-append">
														 	<c:if test="${finance.auditOpinion != null}">
														 		<span class="w70 fz11"><a class="mt3 color7171C6" href="javascript:void(0)" onclick="downloadFile('${finance.auditOpinion}')">下载附件</a></span>
														 	</c:if>
													 		<c:if test="${finance.auditOpinion == null}">
														 	 	<span class="w70 fz11">无附件下载</span>
														 	</c:if>
														 </div>
													</li>
													<li class="col-md-6 p0"><span class="zzzx">资产负债表：</span>
														<div class="input-append">
															<div class="input-append">
														 	 	<c:if test="${finance.liabilitiesList != null}">
														 			<span class="w70 fz11"><a class="mt3 color7171C6" href="javascript:void(0)" onclick="downloadFile('${finance.liabilitiesList}')">下载附件</a></span>
														 		</c:if>
													 			<c:if test="${finance.liabilitiesList == null}">
														 	 		<span class="w70 fz11">无附件下载</span>
														 		</c:if>
															 </div>
														</div>
													</li>
													<li class="col-md-6 p0"><span class="zzzx">利润表：</span>
														<div class="input-append">
															<div class="input-append">
														 	 	<c:if test="${finance.profitList != null}">
														 			<span class="w70 fz11"><a class="mt3 color7171C6" href="javascript:void(0)" onclick="downloadFile('${finance.profitList}')">下载附件</a></span>
														 		</c:if>
													 			<c:if test="${finance.profitList == null}">
														 	 		<span class="w70 fz11">无附件下载</span>
														 		</c:if>
															 </div>
														</div>
													</li>
													<li class="col-md-6 p0"><span class="zzzx">现金流量表：</span>
														<div class="input-append">
															<div class="input-append">
														 	 	<c:if test="${finance.cashFlowStatement != null}">
														 			<span class="w70 fz11"><a class="mt3 color7171C6" href="javascript:void(0)" onclick="downloadFile('${finance.cashFlowStatement}')">下载附件</a></span>
														 		</c:if>
													 			<c:if test="${finance.cashFlowStatement == null}">
														 	 		<span class="w70 fz11">无附件下载</span>
														 		</c:if>
															 </div>
														</div>
													</li>
													<li class="col-md-6 p0"><span class="zzzx">所有者权益变动表：</span>
														<div class="input-append">
															<div class="input-append">
														 	 	<c:if test="${finance.changeList != null}">
														 			<span class="w70 fz11"><a class="mt3 color7171C6" href="javascript:void(0)" onclick="downloadFile('${finance.changeList}')">下载附件</a></span>
														 		</c:if>
													 			<c:if test="${finance.changeList == null}">
														 	 		<span class="w70 fz11">无附件下载</span>
														 		</c:if>
															 </div>
														</div>
													</li>
													<div class="clear"></div>
												</ul>
											</c:forEach>
										</div>
									</div>
								</div>

								<!-- 股东信息 -->
								<div class="tab-pane fade height-200" id="tab-3">
									<div class="margin-bottom-0  categories">
										<h2 class="f16 jbxx mt40">
											<i>01</i>股东信息表
										</h2>
										<input type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="deleteStockholder()" value="删除">
										<input type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="openStockholder()" value="新增">
										<table id="share_table_id" class="table table-bordered table-condensed">
											<thead>
												<tr>
													<th class="info"><input type="checkbox" onchange="checkAll(this, 'stockholder_list_tbody_id')" /></th>
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
														<td class="tc"><input type="checkbox" value="${stockholder.id}" /></td>
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
	
	<!-- footer -->
	<jsp:include page="../../../../../index_bottom.jsp"></jsp:include>
</body>
</html>
