<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML >
<html>
<head>
<%@ include file="../../../common.jsp"%>
<title>供应商抽取</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css"
	type="text/css">

<style>
.layer-default .layui-layer-btn a.layui-layer-btn1:hover {
	color: #333 !important;
}

.textAreafont {
	line-height: 25px;
	color: #ef0000;
	font-size: 12px;
}
</style>

</head>
<body>
	<div id="packageContent" class="packageContent"
		style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
		<ul id="treePackageType" class="ztree" style="margin-top:0;"></ul>
	</div>
	<!--面包屑导航开始-->
	<c:if test="${typeclassId==null }">
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li><a
						href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')">
							首页</a></li>
					<li><a href="javascript:void(0);">支撑环境</a></li>
					<li><a href="javascript:void(0);">供应商管理</a></li>
					<li><a href="javascript:void(0);"<%-- onclick="jumppage('${pageContext.request.contextPath}/SupplierExtracts_new/projectList.html?typeclassId=${typeclassId}')" --%>>供应商抽取</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
	</c:if>

	<!-- 项目戳开始 -->
	<div class="container">
		<!-- 项目信息开始 -->
		<div
			class="container_box col-md-12 col-sm-12 col-xs-12 extractVerify_disabled">
			<c:set value="false" var="flag"></c:set>
			<c:if test="${projectInfo.projectName !=null }">
				<c:set var="flag" value="true"></c:set>
			</c:if>
			<form id="projectForm"
				action="<%=request.getContextPath()%>/SupplierExtracts_new/saveProjectInfo.do"
				method="post">
				<!-- 项目id  -->
				<input type="hidden" id="projectId"
					value="${projectInfo.projectId }" name="projectId">
				<!-- 包id  -->
				<input type="hidden" id="packageId"	value="${projectInfo.packageId }" name="packageId"> 
				<input type="hidden" id="projectInto" value="${projectInfo.projectInto}" name="projectInto"> 
				<input name="sellBeginTime" type="hidden" id="sellBeginTime">
				<input name="sellEndTime" type="hidden" id="sellEndTime">
				<!-- 记录id -->
				<input type="hidden" value="${projectInfo.id}" name="id">
				<h2 class="count_flow">
					<i>1</i>项目信息
				</h2>
				<ul class="ul_list border0 m0">
					<li class="col-md-3 col-sm-4 col-xs-12 pl15"><span
						class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span
							class="star_red">*</span> 项目名称:</span>
						<div
							class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input id="projectName" name="projectName" value="${projectInfo.projectName}" 	${flag?"readonly":"" } type="text"> <span class="add-on">i</span>
							<div class="cue" id="projectNameError"></div>
						</div></li>
					<li class="col-md-3 col-sm-4 col-xs-12"><span
						class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span
							class="star_red">*</span> 项目编号:</span>
						<div
							class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input id="projectNumber" name="projectCode" value="${projectInfo.projectCode}" ${flag?"readonly":"" } type="text" onchange="checkSole(this)">
							<span class="add-on">i</span>
							<div class="cue" id="projectCodeError"></div>
						</div></li>
					<li class="col-md-3 col-sm-4 col-xs-12"><span
						class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span
							class="star_red">*</span>采购方式:</span>
						<div
							class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<c:if test="${projectInfo.purchaseType ==null}">
								<select name="purchaseType" class="col-md-12 col-sm-12 col-xs-6 p0" ${flag?"readonly":"" }>
									<c:forEach items="${purchaseTypeList}" var="map">
										<option value="${map.id}">${map.name}</option>
									</c:forEach>
								</select>
							</c:if>
							<c:if test="${projectInfo.purchaseType !=null }">
								<input id="purchaseType" name="purchaseType"
									value="${projectInfo.purchaseType}" type="hidden">
								<input value="${projectInfo.purchaseTypeName}"
									readonly=${flag?"readonly":"" } type="text">
							</c:if>

							<div class="cue" id="purchaseTypeError"></div>
						</div></li>
					<li class="col-md-3 col-sm-4 col-xs-12"><span
						class="col-md-12 col-sm-12 col-xs-12 padding-left-5">包名(标段):</span>
						<div
							class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input id="packageName" name="packageName"
								value="${projectInfo.packageName}" ${flag?"readonly":"" } type="text"> <span class="add-on">i</span>
							<div class="cue" id="packageNameError"></div>
						</div></li>
					<li class="col-md-3 col-sm-4 col-xs-12"><span
						class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span
							class="star_red">*</span>售领采购文件起始时间:</span>
						<div
							class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input class="col-md-12 col-sm-12 col-xs-6 p0"
								onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d'});"
								onchange="checkTime()" id="sellBegin" readonly="readonly"
								name="sellBegin"
								value="<fmt:formatDate value='${project}'
                             pattern='yyyy-MM-dd HH:mm:ss' />"
								maxlength="30" type="text">
							<div class="cue" id="sellBeginError"></div>
						</div></li>
					<li class="col-md-3 col-sm-4 col-xs-12"><span
						class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span
							class="star_red">*</span>售领采购文件结束时间:</span>
						<div
							class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input class="col-md-12 col-sm-12 col-xs-6 p0"
								onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',onpicking: checkTime(),minDate:'%y-%M-%d'});"
								id="sellEnd" readonly="readonly" name="sellEnd"
								value="<fmt:formatDate value='${bidDate}'
                             pattern='yyyy-MM-dd HH:mm:ss' />"
								maxlength="30" type="text">
							<div class="cue" id="sellEndError"></div>
						</div></li>
					<li class="col-md-3 col-sm-4 col-xs-12"><span
						class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span
							class="star_red">*</span>售领地区</span>
						<div
							class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<select class="col-md-6 col-sm-6 col-xs-6 p0" id="sellProvince"
								name="sellProvince" onchange="selectArea(this);">
								<option value="">选择省</option>
								<c:forEach items="${province }" var="pro">
									<option value="${pro.id }">${pro.name }</option>
								</c:forEach>
							</select> <select name="sellAddress" class="col-md-6 col-sm-6 col-xs-6 p0"
								id="sellAddress">
								<option value="0">全部</option>
							</select>
							<div class="cue" id="sellProvinceError"></div>
						</div></li>
					<li class="col-md-3 col-sm-4 col-xs-12"><span
						class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span
							class="star_red">*</span>售领详细地址</span>
						<div
							class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input id="sellSite" name="sellSite" type="text"> <span
								class="add-on">i</span>
							<div class="cue" id="sellSiteError"></div>
						</div></li>
					<li class="col-md-3 col-sm-4 col-xs-12"><span
						class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span
							class="star_red">*</span>项目类型</span>
						<div
							class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<c:if test="${flag }">
								<input id="projectType" name="projectType"
									value="${projectInfo.projectType }" type="hidden">
								<input value="${projectInfo.projectTypeName }"
									${flag?"readonly":"" } type="text">
							</c:if>
							<c:if test="${!flag }">
								<select id="projectType" name="projectType"
									class="col-md-12 col-sm-12 col-xs-6 p0"
									onchange="loadSupplierType(this)">
									<option value="GOODS" ${projectInfo.projectType=='GOODS' ? 'selected' : '' }>物资</option>
									<option value="PROJECT" ${projectInfo.projectType=='PROJECT' ? 'selected' : '' }>工程</option>
									<option value="SERVICE" ${projectInfo.projectType=='SERVICE' ? 'selected' : '' }>服务</option>
								</select>
							</c:if>
							<span class="add-on">i</span>
							<div class="cue" id="projectTypeError"></div>
						</div></li>
					<li class="col-md-3 col-sm-4 col-xs-12 dnone" id="buildCompany"><span
						class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span
							class="buildCompany">*</span>建设单位名称</span>
						<div
							class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input  name="buildCompany" type="text"> <span
								class="add-on">i</span>
							<div class="cue" id="buildCompanyError"></div>
						</div></li>
					<li class="col-md-3 col-sm-4 col-xs-12"><span
						class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span
							id="xmss"></span>项目实施地区</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<select class="col-md-6 col-sm-6 col-xs-6 p0"
								id="constructionPro" name="constructionPro"
								onchange="selectArea(this);">
								<option value="">选择省</option>
								<c:forEach items="${province }" var="pro">
									<option value="${pro.id }">${pro.name }</option>
								</c:forEach>
							</select> 
							<select name="constructionAddr"	class="col-md-6 col-sm-6 col-xs-6 p0" id="constructionAddr">
								<option value="0">全部</option>
							</select> 
							<div class="cue" id="constructionProError"></div>
						</div></li>
					<li class="col-md-3 col-sm-4 col-xs-12"><span
						class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span
							class="star_red">*</span>抽取地址</span>
						<div
							class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input name="extractionSites" value="" type="text"> <span
								class="add-on">i</span>
							<div class="cue" id="extractionSitesError"></div>
						</div></li>
					<li class="col-md-3 col-sm-4 col-xs-12"><span
						class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span
							class="star_red">*</span>联系人</span>
						<div
							class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input name="contactPerson" value="" type="text"> <span
								class="add-on">i</span>
							<div class="cue" id="contactPersonError"></div>
						</div></li>
					<li class="col-md-3 col-sm-4 col-xs-12"><span
						class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span
							class="star_red">*</span>联系座机</span>
						<div
							class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input name="contactNum" value="" type="text"> <span
								class="add-on">i</span>
							<div class="cue" id="contactNumError"></div>
						</div></li>
					<li class="col-md-3 col-sm-4 col-xs-12"><span
						class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span
							class="star_red">*</span>联系手机</span>
						<div
							class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input name="contactPhone" value="" type="text"> <span
								class="add-on">i</span>
							<div class="cue" id="contactPhoneError"></div>
						</div></li>
					<li class="col-md-3 col-sm-4 col-xs-12"><span
						class="col-md-12 col-sm-12 col-xs-12 padding-left-5">其他要求</span>
						<div
							class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input name="remark" value="" type="text"> <span
								class="add-on">i</span>
							<div class="cue" id="remarkError"></div>
						</div></li>
				</ul>
			</form>
		</div>
		<!-- 项目信息结束 -->
		<!-- 人员信息开始-->
		<div
			class="container_box col-md-12 col-sm-12 col-xs-12 extractVerify_disabled">
			<h2 class="count_flow">
				<i>2</i>人员信息
			</h2>
			<span class="col-md-12 col-sm-12 col-xs-12 p0"><span
				class="red">*</span><b> 抽取人员:</b> </span><span class="red" id="eError"></span>
			<form action="<%=request.getContextPath()%>/extractUser/addPerson.do"
				onsubmit="return false" id="extractUser">
				<div class="col-md-12 col-sm-12 col-xs-12 p0 mt10">
					<input type="hidden" value="extractUser" id="eu" name="personType">
					<input type="hidden" name="recordId" value="${projectInfo.id }">
					<input type="button" class="btn btn-windows add"
						onclick="addPerson(this)" value="新增"> <input type="button"
						class="btn btn-windows delete" onclick="delPerson(this)"
						value="删除"> <input type="button"
						class="btn btn-windows input" onclick="selectHistory(this)"
						value="引用历史人员">
				</div>
				<div class="clear"></div>
				<table class="table table-bordered table-condensed table_input mt10">
					<thead>
						<tr>
							<th class="info"><input type="checkbox" onclick="checkAll(this)"></th>
							<th class="info">序号</th>
							<th class="info" width="15%">姓名</th>
							<th class="info" width="40%">单位</th>
							<th class="info" width="15%">职务</th>
							<th class="info" width="15%">军衔</th>
						</tr>
					</thead>
					<tbody>

					</tbody>
				</table>
			</form>
			<span class="col-md-12 col-sm-12 col-xs-12 p0"><span
				class="red">*</span><b> 监督人员:</b> </span><span class="red" id="sError"></span>
			<form action="<%=request.getContextPath()%>/supervise/addPerson.do"
				id="supervise" onsubmit="return false">
				<div class="col-md-12 col-sm-12 col-xs-12 p0 mt10">
					<input type="hidden" name="recordId" value="${projectInfo.id }">
					<input type="hidden" value="supervise" id="su" name="personType">
					<input type="button" class="btn btn-windows add"
						onclick="addPerson(this)" value="新增"> <input type="button"
						class="btn btn-windows delete" onclick="delPerson(this)"
						value="删除"> <input type="button"
						class="btn btn-windows input" onclick="selectHistory(this)"
						value="引用历史人员">
				</div>
				<div class="clear"></div>
				<table class="table table-bordered table-condensed table_input mt10">
					<thead>
						<tr>
							<th class="info"><input type="checkbox"
								onclick="checkAll(this)"></th>
							<th class="info">序号</th>
							<th class="info" width="15%">姓名</th>
							<th class="info" width="40%">单位</th>
							<th class="info" width="15%">职务</th>
							<th class="info" width="15%">军衔</th>
						</tr>
					</thead>
					<tbody>

					</tbody>
				</table>
			</form>
		</div>
		<!-- 条件开始 -->
		<div
			class="container_box col-md-12 col-sm-12 col-xs-12 extractVerify_disabled">
			<form id="form1" method="post">
				<!--  项目id -->
				<input type="hidden" id="projectId" value="${projectInfo.projectId }" name="projectId">
				<!-- 记录id -->
				<input type="hidden" name="recordId" id="recordId" value="${projectInfo.id}"> 
				<input type="hidden" id="conditionId" name="id" value="${projectInfo.conditionId }">
				<!-- 省 -->
				<input type="hidden" name="province" id="province" value="0" /> 
				<input type="hidden" name="addressId" id="addressId">
				<h2 class="count_flow">
					<i>3</i>抽取条件       
					<button class="btn" type="button">
						当前满足<span id="count">0</span>家
					</button>
				</h2>
				<ul class="ul_list m0 pl5" style="background-color: #fbfbfb">
					<li class="col-md-3 col-sm-6 col-xs-12 pl10 category">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="star_red">*</span>产品类别：</span>
						<!--  满足多个条件 -->
						<input type="hidden" name="isMulticondition" value="1" id="isSatisfy" class="isSatisfy"> <input type="hidden" name="categoryId" id="categoryIds" class="categoryId">
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input readonly value="${listCon.conTypes[0].categoryName}" id="categoryName" typeCode="" onclick="opens(this);" type="text"> <span class="add-on">i</span>
							<div class="cue" id="categoryIdError"></div>
						</div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">
							<span class="star_red">*</span>供应商类型：
						</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<select id=supplierType name="supplierTypeCode" onchange="initCategoryAndLevel(this)" class="w100p"></select> 
							<span class="add-on">i</span>
							<div class="cue" id="supplierTypeCodeError"></div>
						</div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12 level">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">供应商等级：</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input type="hidden" name="levelTypeId"> 
							<input type="text" readonly id="level" value="${listCon.supplierLevel == null? '':listCon.supplierLevel}" onclick="showLevel(this);" /> <span class="add-on">i</span>
							<div class="cue" id="levelTypeIdError"></div>
						</div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12 level">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">销售类供应商等级：</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input type="hidden" name="salesLevelTypeId"> 
							<input type="text" readonly id="salesLevel" value="${listCon.supplierLevel == null? '':listCon.supplierLevel}" onclick="showLevel(this);" /> <span class="add-on">i</span>
							<div class="cue" id="salesLevelTypeIdError"></div>
						</div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>供应商数量：</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input class="title col-md-12" id='extractNum' name="extractNum" maxlength="11" type="text"> <span class="add-on">i</span>
							<div class="cue" id="extractNumError">${loginPwdError}</div>
						</div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12 dnone projectOwn">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">承揽业务范围：</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input type="hidden"  name="businessScope"> 
							<input type="text" readonly="readonly" id="businessScope"> 
							<span class="add-on">i</span>
							<div class="cue" id="businessScopeError"></div>
						</div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12">
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 ">
							<span class="star_red">*</span>供应商所在地区：
						</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input type="text" readonly="readonly" name="areaName" id="area" onclick="showTree();"> 
							<span class="add-on">i</span>
							<div class="cue" id="areaNameError"></div>
						</div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12 count">
						  <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">企业性质：</span>
							<select name="businessNature" class="w100p"
								onchange="selectLikeSupplier()">
								<option value="">不限</option>
								<c:forEach items="${businessNature }" var="bu">
									<option value="${bu.id }">${bu.name }</option>
								</c:forEach>
							</select>
							<div class="cue">${loginPwdError}</div>
					</li>		
					<li class="col-md-3 col-sm-6 col-xs-12">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">保密要求：</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<select name="isHavingConCert" class="w100p"
								onchange="selectLikeSupplier()">
								<option value="">不限</option>
								<option value="0">无</option>
								<option value="1">有</option>
							</select>
						</div>
						<div class="cue">${loginPwdError}</div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">境外分支机构：</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<select name="overseasBranch" class="w100p"
								onchange="selectLikeSupplier()">
								<option value="">不限</option>
								<option value="1">有</option>
								<option value="0">无</option>
							</select>
							<div class="cue">${loginPwdError}</div>
						</div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12 dnone projectOwn">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">资质信息：</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input type="hidden" name="quaId" id="quaId">
							<input type="text" id="quaName" value="${listCon.supplierLevel == null? '全部资质':listCon.supplierLevel}" 	onkeyup="selectQua()" onclick="showQua(this);" /> <span
								class="add-on">i</span>
							<div class="cue" id="dCount"></div>
						</div>
					</li>
					<li class="col-md-12 col-sm-12 col-xs-12 dnone">
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span
							class="red">*</span> 限制地区理由:</span>
						<div
							class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<textarea class="w100p h100 resizen" maxlength="500"
								name="addressReason" id="areaReson" onkeyup="size(this);"></textarea>
							<small>字数：500. 剩余：<span id="textCount">500</span>.</small>
							<div class="textAreafont" id="areaError"></div>
						</div></li>
				</ul>

				<div class="clear"></div>
				<div class="col-xs-12 tc mt20">
					<button class="btn bu" onclick="extractVerify(1);" type="button">人工抽取</button>
					<button class="btn bu" type="button" >自动抽取</button>
					<!-- onclick="extractVerify(0)" -->
					<button class="btn bu" type="button" onclick="resetCondition(this)">重置</button>
				</div>
			</form>
			<!--=== Content Part ===-->
		</div>
		<div class="container_box col-md-12 col-sm-12 dnone col-xs-12" id="result">
			<h2 class="count_flow" a>
				<i>4</i>抽取结果
			</h2>
			<div class="ul_list">
				<div align="left" id="countdnone">
					工程供应商：确认参加的供应商为<span class="f26 red" id="joinCount">0</span>家，确认不参加的有<span
						class="notJoin">0</span>家
				</div>
				<!-- Begin Content -->
				<table id="table" class="table table-bordered table-condensed">
					<thead>
						<tr>
							<th class="info w50">序号</th>
							<th class="info">供应商名称</th>
							<th class="info w250">类型</th>
							<th class="info w100">联系人名称</th>
							<th class="info w100">联系人电话</th>
							<th class="info w120">联系人手机</th>
							<th class="info w100">操作</th>
						</tr>
					</thead>
					<tbody>

					</tbody>
				</table>
			</div>

			<div class="col-xs-12 tc mt20 dnone" id="end">
				<button class="center btn" onclick="alterEndInfo(this)">结束</button>
			</div>
		</div>
	</div>
	<!-- 地区树 -->
	<div id="areaContent" class="levelTypeContent"
		style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
		<ul id="treeArea" class="ztree" style="margin-top:0;"></ul>
	</div>
	<!-- 类别树 -->
	<div id=supplierTypeContent class="levelTypeContent"
		style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
		<ul id=supplierTypeTree class="ztree" style="margin-top:0;"></ul>
	</div>

	<!-- 等级树 -->
	<div id="levelContent" class="levelTypeContent"
		style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
		<ul id="levelTree" class="ztree" style="margin-top:0;"></ul>
	</div>

	<!-- 等级树 -->
	<div id="salesLevelContent" class="levelTypeContent"
		style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
		<ul id="salesLevelTree" class="ztree" style="margin-top:0;"></ul>
	</div>

	<!-- 资质树 -->
	<div id="quaContent" class="levelTypeContent"
		style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
		<ul id="quaTree" class="ztree" style="margin-top:0;"></ul>
	</div>
	<a href="" target="blank" hidden="hidden" id="down"></a>  
</body>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/ses/sms/supplierExtract.js"></script>
<script type="text/javascript">
	var projectType = "${projectInfo.projectInto}";
	var packageName = "${projectInfo.packageName}";
</script>
</html>