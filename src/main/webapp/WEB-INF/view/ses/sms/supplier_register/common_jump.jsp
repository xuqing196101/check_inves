<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang3.math.NumberUtils"%>
<%@ page import="ses.constants.SupplierConstants"%>
<% 
	int currentStep = NumberUtils.toInt(request.getParameter("currentStep"), 1);// 当前步骤
	String supplierId = request.getParameter("supplierId");// 供应商id
	String supplierSt = request.getParameter("supplierSt");// 供应商状态
%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<c:set var="currentStep" value="<%=currentStep %>" />
<style type="text/css">
	.current {
		cursor: pointer;
	}
</style>
<div class="container clear margin-top-30">
	<h2 class="step_flow">
		<span id="sp1" class="new_step ${currentStep>=1?'current':''} fl" onclick="${currentStep>1?'updateStep(1)':''}"><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span>
    <span id="sp2" class="new_step ${currentStep>=2?'current':''} fl" onclick="${currentStep>2?'updateStep(2)':''}"><i class="">2</i><div class="line"></div> <span class="step_desc_01">供应商类型</span> </span>
    <span id="ty3" class="new_step ${currentStep>=3?'current':''} fl" onclick="${currentStep>3?'updateStep(3)':''}"><i class="">3</i><div class="line"></div> <span class="step_desc_02">产品类别</span> </span>
    <span id="sp4" class="new_step ${currentStep>=4?'current':''} fl" onclick="${currentStep>4?'updateStep(4)':''}"><i class="">4</i><div class="line"></div> <span class="step_desc_01">资质文件维护</span> </span>
    <span id="sp5" class="new_step ${currentStep>=5?'current':''} fl" onclick="${currentStep>5?'updateStep(5)':''}"><i class="">5</i><div class="line"></div> <span class="step_desc_02">销售合同</span> </span>
    <span id="sp6" class="new_step ${currentStep>=6?'current':''} fl" onclick="${currentStep>6?'updateStep(6)':''}"><i class="">6</i><div class="line"></div> <span class="step_desc_01">采购机构</span> </span>
    <span id="sp7" class="new_step ${currentStep>=7?'current':''} fl" onclick="${currentStep>7?'updateStep(7)':''}"><i class="">7</i><div class="line"></div> <span class="step_desc_02">承诺书和申请表</span> </span>
    <span id="sp8" class="new_step ${currentStep>=8?'current':''} fl new_step_last"><i class="">8</i> <span class="step_desc_01">提交审核</span> </span>
    <div class="clear"></div>
	</h2>
</div>

<script type="text/javascript">

	// 删除左右两端的空格
	function trim(str) {
		return str.replace(/(^\s*)|(\s*$)/g, "");
	}
	
	// 流程步骤跳转
	function updateStep(step){
		var supplierId = "<%=supplierId%>";
		location.href = "${pageContext.request.contextPath}/supplier/updateStep.html?step=" + step + "&supplierId=" + supplierId;
	}
	
	//显示不通过的理由
	function errorMsg(_this, auditField, auditType) {
		// 如果加载过错误信息，则不再加载
		var errorMsg = $(_this).attr("data-errorMsg");
		if (errorMsg) {
			/* layer.msg("不通过理由：" + errorMsg, {
				offset : '300px'
			}); */
			layer.tips("不通过理由：" + errorMsg, _this, {
			  tips: [3, '#666']
			});
			return;
		}

		var supplierId = "<%=supplierId%>";
		$.ajax({
			url : "${pageContext.request.contextPath}/supplier/audit.html",
			data : {
				"supplierId" : supplierId,
				"auditField" : auditField,
				"auditType" : auditType
			},
			dataType : "json",
			success : function(data) {
				$(_this).attr("data-errorMsg", data.suggest);
				/* layer.msg("不通过理由：" + data.suggest, {
					offset : '200px'
				}); */
				layer.tips("不通过理由：" + data.suggest, _this, {
				  //tips: [3, 'rgba(0,0,0,0.6)']
				  tips: [3, '#666']
				});
			}
		});
	}

</script>
