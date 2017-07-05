<%@ page language="java" pageEncoding="UTF-8"%>
<!-- 项目戳开始 -->
<c:if test="${currSupplier.status != 7}">
	<div class="container clear margin-top-30">
		<h2 class="step_flow">
			   <span id="locationA" class="new_step  fl"  ><i class="">1</i>
				<div class="line"></div> 
				<span class="step_desc_01">基本信息</span> </span> <span id="locationB" class="new_step  fl" ><i class="">2</i>
				<div class="line"></div> 
				<span class="step_desc_02">供应商类型</span> </span> <span id="locationC" class="new_step fl" ><i class="">3</i>
				<div class="line"></div> 
				<span class="step_desc_01">产品类别</span> </span> <span id="locationD" class="new_step fl" ><i class="">4</i>
				 <div class="line"></div> 
				 <span class="step_desc_02">资质文件维护</span> </span> <span id="locationE" class="new_step  fl " ><i class=""> 5</i>
				 <div class="line"></div> 
				 <span class="step_desc_01">销售(承包)合同</span> </span> <span id="locationF" class="new_step fl"><i class="">6</i>
				<div class="line"></div> 
				<span class="step_desc_02">采购机构</span> </span> <span id="locationG" class="new_step fl"><i class="">7</i>
				<div class="line"></div> 
				<span class="step_desc_01">承诺书和申请表</span> </span> <span id="locationH" class="new_step fl new_step_last"><i class="">8</i> 
				<span class="step_desc_02">提交审核</span> </span>
			<div class="clear"></div>
		</h2>
	</div>
</c:if>
