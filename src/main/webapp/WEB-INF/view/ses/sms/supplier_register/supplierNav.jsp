<%@ page language="java" pageEncoding="UTF-8"%>
<!-- 项目戳开始 -->
<c:if test="${currSupplier.status != 7}">
	<div class="container clear margin-top-30">
		<h2 class="padding-20 mt20 ml30">
			<span class="new_step current fl"><i class="">1</i>
				<div class="line"></div> <span class="step_desc_01">基本信息</span> </span> <span class="new_step fl"><i class="">2</i>
				<div class="line"></div> <span class="step_desc_02">供应商类型</span> </span> <span class="new_step fl"><i class="">3</i>
				<div class="line"></div> <span class="step_desc_01">专业信息</span> </span> <span class="new_step fl"><i class="">4</i>
				<div class="line"></div> <span class="step_desc_02">品目信息</span> </span> <span class="new_step fl"><i class="">5</i>
				<div class="line"></div> <span class="step_desc_01">产品信息</span> </span> <span class="new_step fl"><i class="">6</i>
				<div class="line"></div> <span class="step_desc_02">初审采购机构</span> </span> <span class="new_step fl"><i class="">7</i>
				<div class="line"></div> <span class="step_desc_01">打印申请表</span> </span> <span class="new_step fl"><i class="">8</i> <span class="step_desc_01">申请表承诺书上传</span> </span>
			<div class="clear"></div>
		</h2>
	</div>
</c:if>