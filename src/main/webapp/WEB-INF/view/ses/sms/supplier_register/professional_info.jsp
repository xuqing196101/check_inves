<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>供应商专业信息</title>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/bootstrap.min.js"></script>
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
			$(".tab-pane").each(function() {
				var id = $(this).attr("id");
				if (id == defaultPage) {
					$(this).attr("class", "tab-pane fade height-300 active in");
				} else {
					$(this).attr("class", "tab-pane fade height-300");
				}
			});
		}
	});

	/** 打开物资证书 */
	function openCertPro() {
		var matProId = $("input[name='supplierMatPro.id']").val();
		var supplierId = $("input[name='id']").val();
		if (!matProId) {
			layer.msg("请暂存物资生产专业信息 !", {
				offset : '300px',
			});
		} else {
			layer.open({
				type : 2,
				title : '添加物资生产证书信息',
				skin : 'layui-layer-rim', //加上边框
				area : [ '600px', '500px' ], //宽高
				offset : '100px',
				scrollbar : false,
				content : '${pageContext.request.contextPath}/supplier_cert_pro/add_cert_pro.html?matProId=' + matProId + '&supplierId=' + supplierId, //url
				closeBtn : 1, //不显示关闭按钮
			});
		}
	}
	
	/** 供应商保存专业生产信息 */	
	function savePro(sign) {
		var action = "${pageContext.request.contextPath}/supplier/";
		if (sign == -1) {
			action += "prev_step.html";
		} else if (sign == 1) {
			action += "next_step.html";
		} else {
			action += "stash_step.html";
		}
		$("#save_pro_form_id").attr("action", action);
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
		var certProIds="";
		var supplierId = $("input[name='id']").val();
		$(checkboxs).each(function(index) {
			if (index > 0) {
				certProIds += ",";
			}
			certProIds += $(this).val();
		});
		var size = checkboxs.length;
		if (size > 0) {
			layer.confirm("已勾选" + size + "条记录, 确定删除 !", {
				offset : '200px',
				scrollbar : false,
			},function(index) {
				window.location.href = "${pageContext.request.contextPath}/supplier_cert_pro/delete_cert_pro.html?certProIds=" + certProIds +"&supplierId=" + supplierId;
				layer.close(index);
				
			});
		} else {
			layer.alert("请至少勾选一条记录 !", {
				offset : '200px',
				scrollbar : false,
			});
		}
	}
	
	function openItemsPro() {
		var matProId = $("input[name='supplierMatPro.id']").val();
		var supplierId = $("input[name='id']").val();
		if (!matProId) {
			layer.msg("请暂存物资生产专业信息 !", {
				offset : '300px',
			});
		} else {
			layer.open({
				type : 2,
				title : '添加物资生产品目信息',
				skin : 'layui-layer-rim', //加上边框
				area : [ '600px', '500px' ], //宽高
				offset : '100px',
				scrollbar : false,
				content : '${pageContext.request.contextPath}/supplier_items_pro/add_items_pro.html?matProId=' + matProId + '&supplierId=' + supplierId, //url
				closeBtn : 1, //不显示关闭按钮
			});
		}
	}
	
	function deleteItemsPro() {
		var checkboxs = $("#items_pro_list_tbody_id").find(":checkbox:checked");
		var itemsProIds="";
		var supplierId = $("input[name='id']").val();
		$(checkboxs).each(function(index) {
			if (index > 0) {
				itemsProIds += ",";
			}
			itemsProIds += $(this).val();
		});
		var size = checkboxs.length;
		if (size > 0) {
			layer.confirm("已勾选" + size + "条记录, 确定删除 !", {
				offset : '200px',
				scrollbar : false,
			},function(index) {
				window.location.href = "${pageContext.request.contextPath}/supplier_items_pro/delete_items_pro.html?itemsProIds=" + itemsProIds +"&supplierId=" + supplierId;
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
		if (!matSellId) {
			layer.msg("请暂存物资销售专业信息 !", {
				offset : '300px',
			});
		} else {
			layer.open({
				type : 2,
				title : '添加物资生产证书信息',
				skin : 'layui-layer-rim', //加上边框
				area : [ '600px', '500px' ], //宽高
				offset : '100px',
				scrollbar : false,
				content : '${pageContext.request.contextPath}/supplier_cert_sell/add_cert_sell.html?matSellId=' + matSellId + '&supplierId=' + supplierId, //url
				closeBtn : 1, //不显示关闭按钮
			});
		}
	}
	
	function deleteCertSell() {
		var checkboxs = $("#cert_sell_list_tbody_id").find(":checkbox:checked");
		var certSellIds="";
		var supplierId = $("input[name='id']").val();
		$(checkboxs).each(function(index) {
			if (index > 0) {
				certSellIds += ",";
			}
			certSellIds += $(this).val();
		});
		var size = checkboxs.length;
		if (size > 0) {
			layer.confirm("已勾选" + size + "条记录, 确定删除 !", {
				offset : '200px',
				scrollbar : false,
			},function(index) {
				window.location.href = "${pageContext.request.contextPath}/supplier_cert_sell/delete_cert_sell.html?certSellIds=" + certSellIds +"&supplierId=" + supplierId;
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
		if (!matSeId) {
			layer.msg("请暂存服务专业信息 !", {
				offset : '300px',
			});
		} else {
			layer.open({
				type : 2,
				title : '添加物资生产证书信息',
				skin : 'layui-layer-rim', //加上边框
				area : [ '600px', '500px' ], //宽高
				offset : '100px',
				scrollbar : false,
				content : '${pageContext.request.contextPath}/supplier_cert_se/add_cert_se.html?matSeId=' + matSeId + '&supplierId=' + supplierId, //url
				closeBtn : 1, //不显示关闭按钮
			});
		}
	}
	
	function deleteCertSe() {
		var checkboxs = $("#cert_se_list_tbody_id").find(":checkbox:checked");
		var certSeIds="";
		var supplierId = $("input[name='id']").val();
		$(checkboxs).each(function(index) {
			if (index > 0) {
				certSeIds += ",";
			}
			certSeIds += $(this).val();
		});
		var size = checkboxs.length;
		if (size > 0) {
			layer.confirm("已勾选" + size + "条记录, 确定删除 !", {
				offset : '200px',
				scrollbar : false,
			},function(index) {
				window.location.href = "${pageContext.request.contextPath}/supplier_cert_se/delete_cert_se.html?certSeIds=" + certSeIds +"&supplierId=" + supplierId;
				layer.close(index);
				
			});
		} else {
			layer.alert("请至少勾选一条记录 !", {
				offset : '200px',
				scrollbar : false,
			});
		}
	}
	
	function openCertEng() {
		var matEngId = $("input[name='supplierMatEng.id']").val();
		var supplierId = $("input[name='id']").val();
		if (!matEngId) {
			layer.msg("请暂存工程专业信息 !", {
				offset : '300px',
			});
		} else {
			layer.open({
				type : 2,
				title : '添加工程证书信息',
				skin : 'layui-layer-rim', //加上边框
				area : [ '600px', '500px' ], //宽高
				offset : '100px',
				scrollbar : false,
				content : '${pageContext.request.contextPath}/supplier_cert_eng/add_cert_eng.html?matEngId=' + matEngId + '&supplierId=' + supplierId, //url
				closeBtn : 1, //不显示关闭按钮
			});
		}
	}
	
	function deleteCertEng() {
		var checkboxs = $("#cert_eng_list_tbody_id").find(":checkbox:checked");
		var certEngIds="";
		var supplierId = $("input[name='id']").val();
		$(checkboxs).each(function(index) {
			if (index > 0) {
				certEngIds += ",";
			}
			certEngIds += $(this).val();
		});
		var size = checkboxs.length;
		if (size > 0) {
			layer.confirm("已勾选" + size + "条记录, 确定删除 !", {
				offset : '200px',
				scrollbar : false,
			},function(index) {
				window.location.href = "${pageContext.request.contextPath}/supplier_cert_eng/delete_cert_eng.html?certEngIds=" + certEngIds +"&supplierId=" + supplierId;
				layer.close(index);
				
			});
		} else {
			layer.alert("请至少勾选一条记录 !", {
				offset : '200px',
				scrollbar : false,
			});
		}
	}
	
	function openRegPerson() {
		var matEngId = $("input[name='supplierMatEng.id']").val();
		var supplierId = $("input[name='id']").val();
		if (!matEngId) {
			layer.msg("请暂存工程专业信息 !", {
				offset : '300px',
			});
		} else {
			layer.open({
				type : 2,
				title : '添加注册类型和人数',
				skin : 'layui-layer-rim', //加上边框
				area : [ '600px', '350px' ], //宽高
				offset : '100px',
				scrollbar : false,
				content : '${pageContext.request.contextPath}/supplier_reg_person/add_reg_person.html?matEngId=' + matEngId + '&supplierId=' + supplierId, //url
				closeBtn : 1, //不显示关闭按钮
			});
		}
	}
	
	function deleteRegPerson() {
		var checkboxs = $("#reg_person_list_tbody_id").find(":checkbox:checked");
		var regPersonIds="";
		var supplierId = $("input[name='id']").val();
		$(checkboxs).each(function(index) {
			if (index > 0) {
				regPersonIds += ",";
			}
			regPersonIds += $(this).val();
		});
		var size = checkboxs.length;
		if (size > 0) {
			layer.confirm("已勾选" + size + "条记录, 确定删除 !", {
				offset : '200px',
				scrollbar : false,
			},function(index) {
				window.location.href = "${pageContext.request.contextPath}/supplier_reg_person/delete_reg_person.html?regPersonIds=" + regPersonIds +"&supplierId=" + supplierId;
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
		if (!matEngId) {
			layer.msg("请暂存工程专业信息 !", {
				offset : '300px',
			});
		} else {
			layer.open({
				type : 2,
				title : '添加资质资格信息',
				skin : 'layui-layer-rim', //加上边框
				area : [ '600px', '500px' ], //宽高
				offset : '100px',
				scrollbar : false,
				content : '${pageContext.request.contextPath}/supplier_aptitute/add_aptitute.html?matEngId=' + matEngId + '&supplierId=' + supplierId, //url
				closeBtn : 1, //不显示关闭按钮
			});
		}
	}
	
	function deleteAptitute() {
		var checkboxs = $("#aptitute_list_tbody_id").find(":checkbox:checked");
		var aptituteIds="";
		var supplierId = $("input[name='id']").val();
		$(checkboxs).each(function(index) {
			if (index > 0) {
				aptituteIds += ",";
			}
			aptituteIds += $(this).val();
		});
		var size = checkboxs.length;
		if (size > 0) {
			layer.confirm("已勾选" + size + "条记录, 确定删除 !", {
				offset : '200px',
				scrollbar : false,
			},function(index) {
				window.location.href = "${pageContext.request.contextPath}/supplier_aptitute/delete_aptitute.html?aptituteIds=" + aptituteIds +"&supplierId=" + supplierId;
				layer.close(index);
				
			});
		} else {
			layer.alert("请至少勾选一条记录 !", {
				offset : '200px',
				scrollbar : false,
			});
		}
	}
</script>

</head>

<body>
	<div class="wrapper">
		<!-- header -->
		<jsp:include page="../../../../../index_head.jsp"></jsp:include>

		<!-- 项目戳开始 -->
		<div class="container clear margin-top-30">
			<h2 class="padding-20 mt40 ml30">
				<span class="new_step current fl"><i class="">1</i><div class="line"></div> <span class="step_desc_01">用户名密码</span> </span>
				<span class="new_step current fl"><i class="">2</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span>
				<span class="new_step current fl"><i class="">3</i><div class="line"></div> <span class="step_desc_01">供应商类型</span> </span>
			 	<span class="new_step current fl"><i class="">4</i><div class="line"></div> <span class="step_desc_02">专业信息</span> </span>
			 	<span class="new_step fl"><i class="">5</i><div class="line"></div> <span class="step_desc_01">品目信息</span></span> 
			 	<span class="new_step fl"><i class="">6</i><div class="line"></div> <span class="step_desc_02">产品信息</span> </span>
			 	<span class="new_step fl"><i class="">7</i><div class="line"></div> <span class="step_desc_01">初审采购机构</span> </span>
			 	<span class="new_step fl"><i class="">8</i><div class="line"></div> <span class="step_desc_02">打印申请表</span> </span>
			 	<span class="new_step fl"><i class="">9</i> <span class="step_desc_01">申请表承诺书上传</span> </span>
				<div class="clear"></div>
			</h2>
		</div>

		<!--基本信息-->
		<div class="container content height-350">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<ul id="page_ul_id" class="nav nav-tabs bgdd">
							<li id="li_id_1" class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="s_news f18">物资-生产型专业信息</a></li>
							<li id="li_id_2" class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" class="fujian f18">物资-销售型专业信息</a></li>
							<li id="li_id_3" class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" class="fujian f18">工程专业信息</a></li>
							<li id="li_id_4" class=""><a aria-expanded="false" href="#tab-4" data-toggle="tab" class="fujian f18">服务专业信息</a></li>
						</ul>
						<form id="save_pro_form_id" method="post">
							<input type="hidden" name="id" value="${currSupplier.id}" />
							<input type="hidden" name="supplierMatPro.id" value="${currSupplier.supplierMatPro.id}" />
							<input type="hidden" name="sign" value="4" />
							<input type="hidden" name="supplierMatPro.supplierId" value="${currSupplier.id}" />
							<input type="hidden" name="defaultPage" value="${defaultPage}" />
							<div class="tab-content padding-top-20">
								<!-- 物资生产型专业信息 -->
								<div class="tab-pane fade active in height-450" id="tab-1">
									<div class=" margin-bottom-0">
										<h2 class="f16 jbxx">
											<i>01</i>供应商组织机构和人员
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 组织机构：</span>
												<div class="input-append">
													<input class="span3" id="supplierName_input_id" type="text" name="supplierMatPro.orgName" value="${currSupplier.supplierMatPro.orgName}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 人员总数：</span>
												<div class="input-append">
													<input class="span3" type="text" name="supplierMatPro.totalPerson" value="${currSupplier.supplierMatPro.totalPerson}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 管理人员：</span>
												<div class="input-append">
													<input class="span3" type="text" name="supplierMatPro.totalMange" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>技术人员：</span>
												<div class="input-append">
													<input class="span3" type="text" name="supplierMatPro.totalTech" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>工人：</span>
												<div class="input-append">
													<input class="span3" type="text" name="supplierMatPro.totalWorker" />
												</div>
											</li>
											<div class="clear"></div>
										</ul>


										<h2 class="f16 jbxx mt40">
											<i>02</i>产品研发能力
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>技术人员比例(%)：</span>
												<div class="input-append">
													<input class="span3" type="text" name="supplierMatPro.scaleTech" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>高级技术人员比例：</span>
												<div class="input-append">
													<input class="span3" type="text" name="supplierMatPro.scaleHeightTech" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>研发部门名称：</span>
												<div class="input-append">
													<input class="span3" type="text" name="supplierMatPro.researchName" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>研发部门人数：</span>
												<div class="input-append">
													<input class="span3" type="text" name="supplierMatPro.totalResearch" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>研发部门负责人：</span>
												<div class="input-append">
													<input class="span3" type="text" name="supplierMatPro.researchLead" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>国家军队科研项目：</span>
												<div class="input-append">
													<input class="span3" type="text" name="supplierMatPro.countryPro" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>国家军队科技奖项：</span>
												<div class="input-append">
													<input class="span3" type="text" name="supplierMatPro.countryReward" />
												</div>
											</li>
											<div class="clear"></div>
										</ul>

										<h2 class="f16 jbxx mt40">
											<i>03</i>供应商生产能力
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 生产线名称数量：</span>
												<div class="input-append">
													<input class="span3" type="text" name="supplierMatPro.totalBeltline" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 生产设备名称数量：</span>
												<div class="input-append">
													<input class="span3" type="text" name="supplierMatPro.totalDevice" />
												</div>
											</li>
											<div class="clear"></div>
										</ul>
										<h2 class="f16 jbxx mt40">
											<i>04</i>物资生产型供应商质量检测能力登记
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 质量检测部门：</span>
												<div class="input-append">
													<input class="span3" type="text" name="supplierMatPro.qcName" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 质量部门人数：</span>
												<div class="input-append">
													<input class="span3" type="text" name="supplierMatPro.totalQc" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 质监部门负责人：</span>
												<div class="input-append">
													<input class="span3" type="text" name="supplierMatPro.qcLead" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 质量检测设备名称：</span>
												<div class="input-append">
													<input class="span3" type="text" name="supplierMatPro.qcDevice" />
												</div>
											</li>
											<div class="clear"></div>
										</ul>
										<h2 class="f16 jbxx mt40">
											<i>05</i>供应商资质证书
										</h2>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="deleteCertPro()">删除</button>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="openCertPro()">新增</button>
										<table class="table table-bordered table-condensed">
											<thead>
												<tr>
													<th class="info"><input type="checkbox" onchange="checkAll(this, 'cert_pro_list_tbody_id')"/></th>
													<th class="info">资质证书名称</th>
													<th class="info">资质等级</th>
													<th class="info">发证机关</th>
													<th class="info">有效期（起止时间）</th>
													<th class="info">有效期（结束时间）</th>
													<th class="info">是否年检</th>
													<th class="info">附件</th>
												</tr>
											</thead>
											<tbody id="cert_pro_list_tbody_id">
												<c:forEach items="${currSupplier.supplierMatPro.listSupplierCertPros}" var="certPro" varStatus="vs">
													<tr>
														<td class="tc"><input type="checkbox" value="${certPro.id}" /></td>
														<td class="tc">${certPro.name}</td>
														<td class="tc">${certPro.levelCert}</td>
														<td class="tc">${certPro.licenceAuthorith}</td>
														<td class="tc"><fmt:formatDate value="${certPro.expStartDate}" pattern="yyyy-MM-dd" /></td>
														<td class="tc"><fmt:formatDate value="${certPro.expEndDate}" pattern="yyyy-MM-dd" /></td>
														<td class="tc">${certPro.mot}</td>
														<td class="tc">${certPro.attach}</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
										<h2 class="f16 jbxx mt40">
											<i>06</i>可提供品目类别
										</h2>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="deleteItemsPro()">删除</button>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="openItemsPro()">新增</button>
										<table class="table table-bordered table-condensed">
											<thead>
												<tr>
													<th class="info"><input type="checkbox"/></th>
													<th class="info">品目类别</th>
													<th class="info">大类名称</th>
													<th class="info">中类名称</th>
													<th class="info">小类名称</th>
													<th class="info">品种名称</th>
												</tr>
											</thead>
											<tbody id="items_sell_list_tbody_id">
												<c:forEach items="${currSupplier.supplierMatPro.listSupplierItemsPros}" var="itemsPro" varStatus="vs">
													<tr>
														<td class="tc"><input type="checkbox" value="${itemsPro.id}" /></td>
														<td class="tc">${itemsPro.itemsId}</td>
														<td class="tc">${itemsPro.bigKindName}</td>
														<td class="tc">${itemsPro.normalKindName}</td>
														<td class="tc">${itemsPro.smallKingName}</td>
														<td class="tc">${itemsPro.kindName}</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
										
									</div>
								</div>
								
								<!-- 物资销售型专业信息 -->
								<div class="tab-pane fade height-300" id="tab-2">
									<input type="hidden" name="supplierMatSell.id" value="${currSupplier.supplierMatSell.id}" />
									<input type="hidden" name="supplierMatSell.supplierId" value="${currSupplier.id}" />
									<div class=" margin-bottom-0">
										<h2 class="f16 jbxx">
											<i>01</i>供应商组织机构和人员
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 组织机构：</span>
												<div class="input-append">
													<input class="span3" type="text" name="supplierMatSell.orgName" value="${currSupplier.supplierMatSell.orgName}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 人员总数：</span>
												<div class="input-append">
													<input class="span3" type="text" name="supplierMatSell.totalPerson" value="${currSupplier.supplierMatSell.totalPerson}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 管理人员：</span>
												<div class="input-append">
													<input class="span3" type="text" name="supplierMatSell.totalMange" value="${currSupplier.supplierMatSell.totalMange}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>技术人员：</span>
												<div class="input-append">
													<input class="span3" type="text" name="supplierMatSell.totalTech" value="${currSupplier.supplierMatSell.totalTech}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>工人（职员）：</span>
												<div class="input-append">
													<input class="span3" type="text" name="supplierMatSell.totalWorker" value="${currSupplier.supplierMatSell.totalWorker}" />
												</div>
											</li>
											<div class="clear"></div>
										</ul>
										<h2 class="f16 jbxx mt40">
											<i>02</i>供应商资质证书
										</h2>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="deleteCertSell()">删除</button>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="openCertSell()">新增</button>
										<table class="table table-bordered table-condensed">
											<thead>
												<tr>
													<th class="info"><input type="checkbox" onchange="checkAll(this, 'cert_sell_list_tbody_id')" /></th>
													<th class="info">资质证书名称</th>
													<th class="info">资质等级</th>
													<th class="info">发证机关</th>
													<th class="info">有效期（起止时间）</th>
													<th class="info">有效期（结束时间）</th>
													<th class="info">是否年检</th>
													<th class="info">附件</th>
												</tr>
											</thead>
											<tbody id="cert_sell_list_tbody_id">
												<c:forEach items="${currSupplier.supplierMatSell.listSupplierCertSells}" var="certSell" varStatus="vs">
													<tr>
														<td class="tc"><input type="checkbox" value="${certSell.id}" /></td>
														<td class="tc">${certSell.name}</td>
														<td class="tc">${certSell.levelCert}</td>
														<td class="tc">${certSell.licenceAuthorith}</td>
														<td class="tc"><fmt:formatDate value="${certSell.expStartDate}" pattern="yyyy-MM-dd" /></td>
														<td class="tc"><fmt:formatDate value="${certSell.expEndDate}" pattern="yyyy-MM-dd" /></td>
														<td class="tc">${certSell.mot}</td>
														<td class="tc">${certSell.attach}</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
								
								<!-- 工程专业信息 -->
								<div class="tab-pane fade height-200" id="tab-3">
									<input type="hidden" name="supplierMatEng.id" value="${currSupplier.supplierMatEng.id}" />
									<input type="hidden" name="supplierMatEng.supplierId" value="${currSupplier.id}" />
									<div class=" margin-bottom-0">
										<h2 class="f16 jbxx">
											<i>01</i>供应商组织机构和人员
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 组织机构：</span>
												<div class="input-append">
													<input class="span3" type="text" name="supplierMatEng.orgName" value="${currSupplier.supplierMatEng.orgName}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 技术负责人：</span>
												<div class="input-append">
													<input class="span3" type="text" name="supplierMatEng.totalTech" value="${currSupplier.supplierMatEng.totalTech}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 中级以上职称人员：</span>
												<div class="input-append">
													<input class="span3" type="text" name="supplierMatEng.totalGlNormal" value="${currSupplier.supplierMatEng.totalGlNormal}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>现场管理人员：</span>
												<div class="input-append">
													<input class="span3" type="text" name="supplierMatEng.totalMange" value="${currSupplier.supplierMatEng.totalMange}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>技术和工人：</span>
												<div class="input-append">
													<input class="span3" type="text" name="supplierMatEng.totalTechWorker" value="${currSupplier.supplierMatEng.totalTechWorker}" />
												</div>
											</li>
											<div class="clear"></div>
										</ul>
										<h2 class="f16 jbxx mt40">
											<i>02</i>供应商注册人员登记
										</h2>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="deleteRegPerson()">删除</button>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="openRegPerson()">新增</button>
										<table class="table table-bordered table-condensed">
											<thead>
												<tr>
													<th class="info"><input type="checkbox"  onchange="checkAll(this, 'reg_person_list_tbody_id')"/></th>
													<th class="info">注册名称</th>
													<th class="info">注册人数</th>
												</tr>
											</thead>
											<tbody id="reg_person_list_tbody_id">
												<c:forEach items="${currSupplier.supplierMatEng.listSupplierRegPersons}" var="regPerson" varStatus="vs">
													<tr>
														<td class="tc"><input type="checkbox" value="${regPerson.id}" /></td>
														<td class="tc">${regPerson.regType}</td>
														<td class="tc">${regPerson.regNumber}</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
										<h2 class="f16 jbxx mt40">
											<i>03</i>供应商资质资格证书信息
										</h2>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="deleteCertEng()">删除</button>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="openCertEng()">新增</button>
										<table class="table table-bordered table-condensed">
											<thead>
												<tr>
													<th class="info"><input type="checkbox" onchange="checkAll(this, 'cert_eng_list_tbody_id')"/></th>
													<th class="info">资质资格类型</th>
													<th class="info">证书编号</th>
													<th class="info">资质资格最高等级</th>
													<th class="info">技术负责人姓名</th>
													<th class="info">技术负责人职称</th>
													<th class="info">技术负责人职务</th>
													<th class="info">单位负责人姓名</th>
													<th class="info">单位负责人职称</th>
													<th class="info">单位负责人职务</th>
													<th class="info">发证机关</th>
													<th class="info minw100">发证日期</th>
													<th class="info minw100">证书有效期截止日期</th>
													<th class="info">证书状态</th>
													<th class="info">附件</th>
												</tr>
											</thead>
											<tbody id="cert_eng_list_tbody_id">
												<c:forEach items="${currSupplier.supplierMatEng.listSupplierCertEngs}" var="certEng" varStatus="vs">
													<tr>
														<td class="tc"><input type="checkbox" value="${certEng.id}" /></td>
														<td class="tc">${certEng.certType}</td>
														<td class="tc">${certEng.certCode}</td>
														<td class="tc">${certEng.certMaxLevel}</td>
														<td class="tc">${certEng.techName}</td>
														<td class="tc">${certEng.techPt}</td>
														<td class="tc">${certEng.techJop}</td>
														<td class="tc">${certEng.depName}</td>
														<td class="tc">${certEng.depPt}</td>
														<td class="tc">${certEng.depJop}</td>
														<td class="tc">${certEng.licenceAuthorith}</td>
														<td class="tc"><fmt:formatDate value="${certEng.expStartDate}" pattern="yyyy-MM-dd" /></td>
														<td class="tc"><fmt:formatDate value="${certEng.expEndDate}" pattern="yyyy-MM-dd" /></td>
														<td class="tc">${certEng.certStatus}</td>
														<td class="tc">${certEng.attachCert}</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
										<h2 class="f16 jbxx mt40">
											<i>04</i>供应商资质资格信息
										</h2>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="deleteAptitute()">删除</button>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="openAptitute()">新增</button>
										<table class="table table-bordered table-condensed">
											<thead>
												<tr>
													<th class="info"><input type="checkbox" onchange="checkAll(this, 'aptitute_list_tbody_id')"/></th>
													<th class="info">资质资格类型</th>
													<th class="info">证书编号</th>
													<th class="info">资质资格序列</th>
													<th class="info">专业类别</th>
													<th class="info">资质资格等级</th>
													<th class="info">是否主项资质</th>
													<th class="info">批准资质资格内容</th>
													<th class="info">首次批准资质资格文号</th>
													<th class="info">首次批准资质资格日期</th>
													<th class="info">资质资格取得方式</th>
													<th class="info">资质资格状态</th>
													<th class="info">资质资格状态变更时间</th>
													<th class="info">资质资格状态变更原因</th>
													<th class="info">附件</th>
												</tr>
											</thead>
											<tbody id="aptitute_list_tbody_id">
												<c:forEach items="${currSupplier.supplierMatEng.listSupplierAptitutes}" var="aptitute" varStatus="vs">
													<tr>
														<td class="tc"><input type="checkbox" value="${aptitute.id}" /></td>
														<td class="tc">${aptitute.certType}</td>
														<td class="tc">${aptitute.certCode}</td>
														<td class="tc">${aptitute.aptituteSequence}</td>
														<td class="tc">${aptitute.professType}</td>
														<td class="tc">${aptitute.aptituteLevel}</td>
														<td class="tc">${aptitute.isMajorFund}</td>
														<td class="tc">${aptitute.aptituteContent}</td>
														<td class="tc">${aptitute.aptituteCode}</td>
														<td class="tc"><fmt:formatDate value="${aptitute.aptituteDate}" pattern="yyyy-MM-dd" /></td>
														<td class="tc">${aptitute.aptituteWay}</td>
														<td class="tc">${aptitute.aptituteStatus}</td>
														<td class="tc"><fmt:formatDate value="${aptitute.aptituteChangeAt}" pattern="yyyy-MM-dd" /></td>
														<td class="tc">${aptitute.aptituteChangeReason}</td>
														<td class="tc">${aptitute.attachCert}</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
								
								<!-- 服务专业信息 -->
								<div class="tab-pane fade height-200" id="tab-4">
									<input type="hidden" name="supplierMatSe.id" value="${currSupplier.supplierMatSe.id}" />
									<input type="hidden" name="supplierMatSe.supplierId" value="${currSupplier.id}" />
									<div class=" margin-bottom-0">
										<h2 class="f16 jbxx">
											<i>01</i>供应商组织机构和人员
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 组织机构：</span>
												<div class="input-append">
													<input class="span3" type="text" name="supplierMatSe.orgName" value="${currSupplier.supplierMatSe.orgName}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 人员总数：</span>
												<div class="input-append">
													<input class="span3" type="text" name="supplierMatSe.totalPerson" value="${currSupplier.supplierMatSe.totalPerson}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 管理人员：</span>
												<div class="input-append">
													<input class="span3" type="text" name="supplierMatSe.totalMange" value="${currSupplier.supplierMatSe.totalMange}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>技术人员：</span>
												<div class="input-append">
													<input class="span3" type="text" name="supplierMatSe.totalTech" value="${currSupplier.supplierMatSe.totalTech}" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>工人（职员）：</span>
												<div class="input-append">
													<input class="span3" type="text" name="supplierMatSe.totalWorker" value="${currSupplier.supplierMatSe.totalWorker}" />
												</div>
											</li>
											<div class="clear"></div>
										</ul>
										<h2 class="f16 jbxx mt40">
											<i>02</i>供应商资质证书
										</h2>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="deleteCertSe()">删除</button>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="openCertSe()">新增</button>
										<table class="table table-bordered table-condensed">
											<thead>
												<tr>
													<th class="info"><input type="checkbox" onchange="checkAll(this, 'cert_se_list_tbody_id')" /></th>
													<th class="info">资质证书名称</th>
													<th class="info">资质等级</th>
													<th class="info">发证机关</th>
													<th class="info">有效期（起始时间）</th>
													<th class="info">有效期（结束时间）</th>
													<th class="info">是否年检</th>
													<th class="info">附件</th>
												</tr>
											</thead>
											<tbody id="cert_se_list_tbody_id">
												<c:forEach items="${currSupplier.supplierMatSe.listSupplierCertSes}" var="certSe" varStatus="vs">
													<tr>
														<td class="tc"><input type="checkbox" value="${certSe.id}" /></td>
														<td class="tc">${certSe.name}</td>
														<td class="tc">${certSe.levelCert}</td>
														<td class="tc">${certSe.licenceAuthorith}</td>
														<td class="tc"><fmt:formatDate value="${certSe.expStartDate}" pattern="yyyy-MM-dd" /></td>
														<td class="tc"><fmt:formatDate value="${certSe.expEndDate}" pattern="yyyy-MM-dd" /></td>
														<td class="tc">${certSe.mot}</td>
														<td class="tc">${certSe.attach}</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
							<div class="mt40 tc mb50">
								<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5"  onclick="savePro(-1)">上一步</button>
								<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="savePro(0)">暂存</button>
								<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5"  onclick="savePro(1)">下一步</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- footer -->
	<jsp:include page="../../../../../index_bottom.jsp"></jsp:include>
</body>
</html>
