<%@ page language="java" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>供应商注册须知</title>

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
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ztree/css/zTreeStyle.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.excheck.js"></script>
<script type="text/javascript">
	var zTreeObj;
	var zNodes;
	var setting = {
		check : {
			enable : true,
			chkboxType: { "Y": "ps", "N": "ps" }
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "parentId"
			}
		},
	};
	$(function() {
		$.ajax({
			url : "${pageContext.request.contextPath}/supplier_type/find_supplier_type.do",
			type : "post",
			dataType : "json",
			data : {
				supplierId : "${currSupplier.id}"
			},
			success : function(result) {
				zNodes = result;
				zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
				
			},
		});
		

	});
	
	function checkedTree(sign) {
		var action = "${pageContext.request.contextPath}/supplier/";
		if (sign == 1) {
			action += "next_step.html";
		} else if (sign == -1) {
			action += "prev_step.html";
		} else {
			action += "stash_step.html";
		}		
		var nodes = zTreeObj.getCheckedNodes(true);
		var ids = "";
		for (var i = 0; i < nodes.length; i++) {
			if (i != 0) {
				ids += ",";
			}
			ids += $(nodes[i]).attr("id");
		}
		$("#supplier_type_input_id").val(ids);
		$("#supplier_type_form_id").attr("action", action);
		$("#supplier_type_form_id").submit();
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
				<span class="new_step current fl"><i class="">1</i>
					<div class="line"></div> <span class="step_desc_01">用户名密码</span> </span> <span class="new_step current fl"><i class="">2</i>
					<div class="line"></div> <span class="step_desc_02">基本信息</span> </span> <span class="new_step current fl"><i class="">3</i>
					<div class="line"></div> <span class="step_desc_01">供应商类型</span> </span> <span class="new_step fl"><i class="">4</i>
					<div class="line"></div> <span class="step_desc_02">专业信息</span> </span> <span class="new_step fl"><i class="">5</i>
					<div class="line"></div> <span class="step_desc_01">品目信息</span> </span> <span class="new_step fl"><i class="">6</i>
					<div class="line"></div> <span class="step_desc_02">产品信息</span> </span> <span class="new_step fl"><i class="">7</i>
					<div class="line"></div> <span class="step_desc_01">初审采购机构</span> </span> <span class="new_step fl"><i class="">8</i>
					<div class="line"></div> <span class="step_desc_02">打印申请表</span> </span> <span class="new_step fl"><i class="">9</i><span class="step_desc_01">申请表承诺书上传</span> </span>
				<div class="clear"></div>
			</h2>
		</div>

		<!--详情开始-->
		<div class="container content height-350">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<div class="padding-top-20">
							<div class="margin-bottom-0 tc">
								<div style="width: 110px; margin: 0 auto; border: 0;">
									<ul id="treeDemo" class="ztree"></ul>
								</div>
								<div class="mt40 tc mb50">
									<button class="btn padding-left-20 padding-right-20 btn_back margin-15" onclick="checkedTree(-1)">上一步</button>
									<button class="btn padding-left-20 padding-right-20 btn_back margin-15" onclick="checkedTree(0)">暂存</button>
									<button class="btn padding-left-20 padding-right-20 btn_back margin-15" onclick="checkedTree(1)">下一步</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<form id="supplier_type_form_id" method="post">
		<input name="id" type="hidden" value="${currSupplier.id}" />
		<input name="sign" type="hidden" value="3" />
		<input id="supplier_type_input_id" name="ids" type="hidden" />
	</form>
	
	<!-- footer -->
	<jsp:include page="../../../../../index_bottom.jsp"></jsp:include>
</body>
</html>
