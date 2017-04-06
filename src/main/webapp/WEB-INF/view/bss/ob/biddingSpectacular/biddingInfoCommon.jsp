<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<ul class="ul_list">
	<li class="col-md-3 col-sm-6 col-xs-12 pl15">
		<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>竞价项目编号</span>
		<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			<input class="input_group" disabled="disabled"  value="${ obProject.projectNumber }" name="name" type="text"  maxlength="100">
			<span class="add-on">i</span>
		</div>
	</li>
	<li class="col-md-3 col-sm-6 col-xs-12">
		<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>竞价项目名称</span>
		<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			<input class="input_group" disabled="disabled"  value="${ obProject.name }" name="name" type="text"  maxlength="100">
			<span class="add-on">i</span>
		</div>
	</li>
	</li>
	<li class="col-md-3 col-sm-6 col-xs-12">
		<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>交货地点</span>
		<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			<input class="input_group"  disabled="disabled"  value="${ obProject.deliveryAddress }" name="name" type="text"  maxlength="100">
			<span class="add-on">i</span>
		</div>
	</li>
	<li class="col-md-3 col-sm-6 col-xs-12">
		<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>交货时间</span>
		<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			<input class="input_group" disabled="disabled"  value="<fmt:formatDate value="${ obProject.deliveryDeadline }" pattern="yyyy-MM-dd HH:mm:ss"/>" name="name" type="text"  maxlength="100">
			<span class="add-on">i</span>
		</div>
	</li>
	<li class="col-md-3 col-sm-6 col-xs-12">
		<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>参与供应商数</span>
		<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			<c:if test="${obProject.qualifiedSupplier==0}">
				0
			</c:if>
			<c:if test="${obProject.qualifiedSupplier==null}">
				0
			</c:if>
			<c:if test="${obProject.qualifiedSupplier!=0}">
				<input class="input_group" disabled="disabled" value="${obProject.qualifiedSupplier}" type="text"  maxlength="100">
			</c:if>
			<span class="add-on">i</span>
		</div>
	</li>
	<li class="col-md-3 col-sm-6 col-xs-12">
		<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>成交供应商数</span>
		<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			<input class="input_group" disabled="disabled" value="${ obProject.tradedSupplierCount }" type="text"  maxlength="100">
			<span class="add-on">i</span>
		</div>
	</li>
	<li class="col-md-3 col-sm-6 col-xs-12">
		<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>运杂费</span>
		<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			<input class="input_group" disabled="disabled" value="${ transportFees }" type="text"  maxlength="100">
			<span class="add-on">i</span>
		</div>
	</li>
	<c:if test="${ !empty obProject.transportFeesPrice }">
		<li class="col-md-3 col-sm-6 col-xs-12">
			<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>运杂费用（元）</span>
			<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
				<input class="input_group" id="name" disabled="disabled" value="${obProject.transportFeesPrice}" type="text"  maxlength="100">
				<span class="add-on">i</span>
			</div>
		</li>
	</c:if>

	<li class="col-md-3 col-sm-6 col-xs-12">
		<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>需求单位</span>
		<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			<input class="input_group" disabled="disabled" value="${ demandUnit }" type="text"  maxlength="100">
			<span class="add-on">i</span>
		</div>
	</li>
	<li class="col-md-3 col-sm-6 col-xs-12">
		<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>采购机构</span>
		<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			<input class="input_group" disabled="disabled" value="${ orgName }" type="text"  maxlength="100">
			<span class="add-on">i</span>
		</div>
	</li>
	<li class="col-md-3 col-sm-6 col-xs-12">
		<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>需求联系人</span>
		<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			<input class="input_group" disabled="disabled" value="${ obProject.contactName }" type="text"  maxlength="100">
			<span class="add-on">i</span>
		</div>
	</li>
	<li id="transportFeesPriceLi" class="col-md-3 col-sm-6 col-xs-12">
		<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>采购联系人</span>
		<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			<input class="input_group" disabled="disabled" value="${ obProject.orgContactName }" type="text"  maxlength="100">
			<span class="add-on">i</span>
		</div>
	</li>
	<li class="col-md-3 col-sm-6 col-xs-12">
		<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>需求联系电话</span>
		<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			<input class="input_group" disabled="disabled" value="${ obProject.contactTel }" type="text"  maxlength="100">
			<span class="add-on">i</span>
		</div>
	</li>
	<li class="col-md-3 col-sm-6 col-xs-12">
		<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>采购联系电话</span>
		<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			<input class="input_group" disabled="disabled" value="${ obProject.orgContactTel }" type="text"  maxlength="100">
			<span class="add-on">i</span>
		</div>
	</li>
	<li class="col-md-3 col-sm-6 col-xs-12">
		<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>竞价开始时间</span>
		<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			<input class="input_group" disabled="disabled" value="<fmt:formatDate value="${ obProject.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" type="text"  maxlength="100">
			<span class="add-on">i</span>
		</div>
	</li>
	<li class="col-md-3 col-sm-6 col-xs-12 pl15">
		<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>竞价结束时间</span>
		<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			<input class="input_group" disabled="disabled" value="<fmt:formatDate value="${ obProject.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" type="text"  maxlength="100">
			<span class="add-on">i</span>
		</div>
	</li>
	<li class="col-md-3 col-sm-6 col-xs-12 pl15">
		<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>竞价文件</span>
		<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			<div><u:show showId="project" groups="b,c,d"  delete="false" businessId="${fileid}" sysKey="${sysKey}" typeId="${typeId }" /></div>
		</div>
	</li>
	<li class="col-md-12 col-sm-12 col-xs-12">
		<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>竞价内容</span>
		<div class="col-md-12 col-sm-12 col-xs-12 p0">
			<textarea class="col-md-12 col-sm-12 col-xs-12" style="height:50px" name="content"  disabled="disabled" maxlength="1000">${ obProject.content }</textarea>
		</div>
	</li>
</ul>