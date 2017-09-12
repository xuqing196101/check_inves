<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<title>抽取列表</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/ems/expertExtract/extract.js"></script>
<script type="text/javascript">

</script>
</head>

<body>
	<div id="packageContent" class="packageContent" style="display: none; position: absolute; left: 0px; top: 0px; z-index: 999;">
		<ul id="treePackageType" class="ztree" style="margin-top: 0;"></ul>
	</div>
	<!--面包屑导航开始-->
	<c:if test="${typeclassId!=null && typeclassId !=''  }">
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li><a
						href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')">
							首页</a></li>
					<li><a href="javascript:void(0);">支撑环境系统</a></li>
					<li><a href="javascript:void(0);">专家管理</a></li>
					<li><a href="javascript:void(0);"
						onclick="jumppage('${pageContext.request.contextPath}/ExpExtract/projectList.html?typeclassId=typeclassId')">专家抽取</a>
					</li>
					<li class="active"><a href="javascript:void(0);">专家抽取列表</a></li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
	</c:if>

	<!-- 项目戳开始 -->
	<div class="container">
		<div class="col-md-12 col-sm-12 col-xs-12 container_box">
		<div id="div_1">
			<form id="form">
				<h2 class="count_flow"><i>1</i>项目信息</h2>
				<input value = "${projectId}" type = "hidden" name = "id">
				<ul class="ul_list">
					<li class="col-md-3 col-sm-4 col-xs-12 pl15">
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 项目名称:</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input class="span5" id="projectName" name=projectName value="${name}" type="text">
							<span class="add-on">i</span>
							<div class="cue" id=""></div>
						</div>
					</li>
					<li class="col-md-3 col-sm-4 col-xs-12">
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span> 项目编号:</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input class="span5" id="projectNumber" name="code" value="${code}" type="text">
							<span class="add-on">i</span>
							<div class="cue" id=""></div>
						</div>
					</li>
					<li class="col-md-3 col-sm-4 col-xs-12 ">
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span> 采购方式:</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<select class="col-md-12 col-sm-12 col-xs-12 p0" name="purchaseWay">
								<c:forEach items="${purchaseWayList}" var="map">
                                	<option value="${map.id}">${map.name}</option>
                            	</c:forEach>
							</select>
						</div>
					</li>
					<li class="col-md-3 col-sm-4 col-xs-12">
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"> 包名（标段）:</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input class="span5" id="" name="packageName" value="${packageName}" type="text">
							<span class="add-on">i</span>
							<div class="cue" id=""></div>
						</div>
					</li>
					<li class="col-md-3 col-sm-4 col-xs-12">
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span> 评审时间:</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input class="col-md-12 col-sm-12 col-xs-6 p0" value="<fmt:formatDate value="${reviewTime }" pattern="yyyy-MM-dd" /> " name = "reviewTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" pattern='yyyy-MM-dd HH:mm:ss' type="text" readonly="readonly">
							<div class="cue" id=""></div>
						</div>
					</li>
					<li class="col-md-3 col-sm-4 col-xs-12">
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span> 评审地点:</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<select class="col-md-6 col-sm-6 col-xs-6 p0" onchange="functionArea()" id="province" name="reviewProvince">
								<option value="">选择省</option>
								<c:forEach items="${areaTree}" var="map">
									<option value="${map.id}">${map.name}</option>
								</c:forEach>
							</select> 
							<select class="col-md-6 col-sm-6 col-xs-6 p0" id= "city" name = "reviewAddress">
								<option value="">选择地区</option>
							</select>
						</div>
					</li>
					<li class="col-md-3 col-sm-4 col-xs-12 ">
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span> 项目类型:</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<select class="col-md-12 col-sm-12 col-xs-12 p0" name="projectType" id="projectType" onchange="loadExpertKind()">
								<c:forEach items="${projectTypeList}" var="map">
                                	<option value="${map.id}">${map.name}</option>
                            	</c:forEach>
							</select>
						</div>
					</li>
					<li class="col-md-3 col-sm-4 col-xs-12 ">
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 抽取地址:</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input class="span5" type="text" name="extractAddress" value="${extractAddress }"> <span class="add-on">i</span>
							<div class="cue" id="extractionSitesError"></div>
						</div>
					</li>
					<li class="col-md-3 col-sm-4 col-xs-12 ">
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 评审天数:</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<select class="col-md-12 col-sm-12 col-xs-12 p0" name="reviewDays" id="reviewDays">
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
							</select>
						</div>
					</li>
					<li class="col-md-3 col-sm-4 col-xs-12 ">
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 联系人:</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input class="span5" type="text" name="contactPerson" value="${contactPerson }"> <span class="add-on">i</span>
							<div class="cue" id="extractionSitesError"></div>
						</div>
					</li>
					<li class="col-md-3 col-sm-4 col-xs-12 ">
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 联系电话:</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input class="span5" type="text" name="contactNum" value="${contactNum }"> 
							<span class="add-on">i</span>
							<div class="cue" id="extractionSitesError"></div>
						</div>
					</li>
					<li class="col-md-3 col-sm-4 col-xs-12 ">
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "> 其他要求:</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input class="span5" type="text" name="remark" value = "${remark }"><span class="add-on">i</span>
							<div class="cue" id="extractionSitesError"></div>
						</div>
					</li>
				</ul>
			</form>
			</div>
			<!-- <h2 class="count_flow"><i>2</i>人员信息</h2>
			<ul class="ul_list">
				<h2 class="count_flow"><span class="red">*</span> 抽取人员</h2>
				<div class="col-md-12 pl20 mt10">
					<button class="btn btn-windows add" type="button" onclick="addPerson(1)">新增</button>
					<button class="btn btn-windows delete" type="button" onclick="deletePerson(1)">删除</button>
					<button class="btn" type="button" onclick="">引用历史人员</button>
				</div>
				<div class="content table_box">
					<table class="table table-bordered table-condensed table-hover table-striped" id="extractPerson">
						<thead>
							<tr>
								<th class="w30 info"><input id="checkAll1" type="checkbox" onclick="selectAll(1)" /></th>
								<th class="w50 info">序号</th>
								<th class="info" width="19%">姓名</th>
								<th class="info" width="35%">单位</th>
								<th class="info" width="19%">职务</th>
								<th class="info" width="19%">军衔</th>
							</tr>
						</thead>
							<tr>
								<td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem1" value="" /></td>
								<td class="tc">1</td>
								<td><input value = ""></td>
								<td><input value = ""></td>
								<td><input value = ""></td>
								<td><input value = ""></td>
							</tr>
							<tr>
								<td class="tc w30"><input onclick="check(1)" type="checkbox" name="chkItem1" value="" /></td>
								<td class="tc">2</td>
								<td><input value = ""></td>
								<td><input value = "" width="100%"></td>
								<td><input value = ""></td>
								<td><input value = ""></td>
							</tr>
					</table>
				</div>
				
				<h2 class="count_flow"><span class="red">*</span> 监督人员</h2>
				<div class="col-md-12 pl20 mt10">
					<button class="btn btn-windows add" type="button" onclick="addPerson(2)">新增</button>
					<button class="btn btn-windows delete" type="button" onclick="deletePerson(2)">删除</button>
					<button class="btn" type="button" onclick="">引用历史人员</button>
				</div>
				<div class="content table_box">
					<table class="table table-bordered table-condensed table-hover table-striped" id="supervisesPerson">
						<thead>
							<tr>
								<th class="w30 info"><input id="checkAll2" type="checkbox" onclick="selectAll(2)" /></th>
								<th class="w50 info">序号</th>
								<th class="info" width="19%">姓名</th>
								<th class="info" width="35%">单位</th>
								<th class="info" width="19%">职务</th>
								<th class="info" width="19%">军衔</th>
							</tr>
						</thead>
							<tr>
								<td class="tc w30"><input onclick="check(2)" type="checkbox" name="chkItem2" value="" /></td>
								<td class="tc">1</td>
								<td><input value = ""></td>
								<td><input value = ""></td>
								<td><input value = ""></td>
								<td><input value = ""></td>
							</tr>
					</table>
				</div>
			</ul> -->
			<div class="container_box col-md-12 col-sm-12 col-xs-12">
			<div id="div_2">
				<h2 class="count_flow"><i>2</i>人员信息</h2>
				<ul class="ul_list">
					<span class="col-md-12 col-sm-12 col-xs-12 p0"><span class="star_red">*</span><b> 抽取人员:</b></span>
					<form action="<%=request.getContextPath()%>/extractUser/addPerson.html" onsubmit="return false" id="extractUser">
						<div class="col-md-12 col-sm-12 col-xs-12 p0 mt10">
							<input type="hidden" value="extractUser" id="eu" name="personType">
							<input type="hidden" name="recordId" value="${projectInfo.id }"> 
							<input type="button" class="btn btn-windows add" onclick="addPerson(this)" value="新增">
							<input type="button" class="btn btn-windows delete" onclick="delPerson(this)" value="删除">
							<input type="button" class="btn btn-windows input" onclick="selectHistory(this)" value="引用历史人员">
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
							<tbody></tbody>
						</table>
					</form>
					<span class="col-md-12 col-sm-12 col-xs-12 p0"><span class="star_red">*</span><b> 监督人员:</b></span>
					<form action="<%=request.getContextPath()%>/supervise/addPerson.html" id="supervise" onsubmit="return false">
						<div class="col-md-12 col-sm-12 col-xs-12 p0 mt10">
							<input type="hidden" name="recordId" value="${projectInfo.id }">
							<input type="hidden" value="supervise" id="su" name="personType">
							<input type="button" class="btn btn-windows add" onclick="addPerson(this)" value="新增">
							<input type="button" class="btn btn-windows delete" onclick="delPerson(this)" value="删除">
							<input type="button" class="btn btn-windows input" onclick="selectHistory(this)" value="引用历史人员">
						</div>
						<div class="clear"></div>
						<table
							class="table table-bordered table-condensed table_input mt10">
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
							<tbody></tbody>
						</table>
					</form>
				</ul>
			</div>
			</div>
			<h2 class="count_flow"><i>3</i>抽取条件</h2>
			<div id="div_3">
			<!--地区id -->
			<input type="hidden" name="addressId" id="addressId">
			<!-- 省 -->
			<ul class="ul_list">
				<form id="condition_form">
				<input type="hidden" name="areaName" id="provincesel"/>
					<li class="col-md-3 col-sm-4 col-xs-12 pl15">
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 区域要求:</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input class="span5" type="text" readonly id="area" onclick="showTree();"> <span class="add-on">i</span>
							<div class="cue" id=""></div>
						</div>
					</li>
					<li class="col-md-3 col-sm-4 col-xs-12 display-none" id="addressReason">
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 区域限制理由:</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input class="span5" type="text" maxlength="100" name="addressReason">
							<span class="add-on">i</span><span class="input-tip">最多100字</span>
							<div class="cue" id=""></div>
						</div>
					</li>
					<li class="col-md-3 col-sm-4 col-xs-12 ">
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span> 专家类型:</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<select class="col-md-12 col-sm-12 col-xs-12 p0" name="expertTypeId" onchange="getCount(this)">
								<option value="0">不限</option>
								<c:forEach items="${expertTypeList}" var="map">
									<option value="${map.id}">${map.name}</option>
								</c:forEach>
							</select>
						</div>
					</li>
					<li class="col-md-3 col-sm-4 col-xs-12 ">
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 专家类别:</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<select class="col-md-12 col-sm-12 col-xs-12 p0" name="expertKindId" id="expertKind" onchange="changeKind()">
								<option value="0">不限</option>
							</select>
						</div>
					</li>
					<li class="col-md-3 col-sm-4 col-xs-12 ">
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 抽取总人数:</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input class="span5" type="text" name="extractNum" onchange="getCount(this)"> <span class="add-on">i</span>
							<div class="cue" id=""></div>
						</div>
					</li>
					<li class="col-md-3 col-sm-4 col-xs-12 ">
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span> 是否抽取候补专家:</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<select class="col-md-12 col-sm-12 col-xs-12 p0" name="isExtractAlternate" onchange="getCount(this)">
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
						</div>
					</li>
					<li class="col-md-5 col-sm-5 col-xs-12 ">
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">&nbsp;</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
						注：选择"是"，取完满足人数后，需要抽取两名候补专家
						</div>
					</li>
				</form>
				<!-- 工程技术 -->
				<div class="col-md-12 clear mt20 pt20 borderTS1 display-none" id="PROJECT">
					<form id="PROJECT_form">
						<!-- <input class="span5" type="hidden" name="cateCode" value="PROJECT"> -->
						<div class="col-xs-2 p0"><button class="btn" type="button">当前满足<span id="PROJECT_count">0</span>人</button></div>
						<ul class="col-xs-10 p0">
							<li class="col-md-3 col-sm-4 col-xs-12 pl15 list-style">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 工程技术人数:</span>
								<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
									<input class="span5" type="text" typeCode="PROJECT" name="project_i_count"> <span class="add-on">i</span>
									<div class="cue" id=""></div>
								</div>
							</li>
							<li class="col-md-3 col-sm-4 col-xs-12 list-style">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 工程专业信息:</span>
								<input type="hidden" name="project_eng_isSatisfy" class="isSatisfy">
          						<input type="hidden" name="project_eng_info" class="categoryId">
								<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
									<input class="span5" type="text" readonly onclick="opens(this);" typeCode="ENG_INFO_ID" name=""> <span class="add-on">i</span>
									<div class="cue" id=""></div>
								</div>
							</li>
							<li class="col-md-3 col-sm-4 col-xs-12 list-style">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red"></span> 产品类别:</span>
								<input type="hidden" name="project_isSatisfy" class="isSatisfy">
          						<input type="hidden" name="project_type" class="categoryId">
								<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
									<input class="span5" type="text" readonly onclick="opens(this);" typeCode="PROJECT"> <span class="add-on">i</span>
									<div class="cue" id=""></div>
								</div>
							</li>
							<li class="col-md-3 col-sm-4 col-xs-12 list-style">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red"></span> 技术职称:</span>
								<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
									<input class="span5" type="text" name="project_technical" onchange="getCount(this)" typeCode="PROJECT"> <span class="add-on">i</span>
									<div class="cue" id=""></div>
								</div>
							</li>
							<li class="col-md-3 col-sm-4 col-xs-12 list-style">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red"></span> 工程执业资格:</span>
								<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
									<input class="span5" type="text" name="project_qualification" onchange="getCount(this)" typeCode="PROJECT"> <span class="add-on">i</span>
									<div class="cue" id=""></div>
								</div>
							</li>
						</ul>
						<div class="clear"></div>
					</form>
				</div>
				<!-- 工程经济 -->
				<div class="col-md-12 clear mt20 pt20 borderTS1 display-none" id="GOODS_PROJECT">
					<form id="GOODS_PROJECT_form">
						<!-- <input class="span5" type="hidden" name="cateCode" value="GOODS_PROJECT"> -->
						<div class="col-xs-2 p0"><button class="btn" type="button">当前满足<span id="GOODS_PROJECT_count">0</span>人</button></div>
						<ul class="col-xs-10 p0">
							<li class="col-md-3 col-sm-4 col-xs-12 pl15 list-style">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 工程经济人数:</span>
								<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
									<input class="span5" type="text" name="goods_project_i_count" typeCode="GOODS_PROJECT"> <span class="add-on">i</span>
									<div class="cue" id=""></div>
								</div>
							</li>
							<li class="col-md-3 col-sm-4 col-xs-12 list-style">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 工程专业信息:</span>
								<input type="hidden" name="goods_project_eng_isSatisfy" class="isSatisfy">
          						<input type="hidden" name="goods_project_eng_info" class="categoryId">
								<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
									<input class="span5" type="text" readonly onclick="opens(this);" typeCode="ENG_INFO_ID" name=""> <span class="add-on">i</span>
									<div class="cue" id=""></div>
								</div>
							</li>
							<li class="col-md-3 col-sm-4 col-xs-12 list-style">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red"></span> 产品类别:</span>
								<input type="hidden" name="goods_project_isSatisfy" class="isSatisfy">
          						<input type="hidden" name="goods_project_type" class="categoryId">
								<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
									<input class="span5" type="text" readonly onclick="opens(this);" typeCode="GOODS_PROJECT" name=""> <span class="add-on">i</span>
									<div class="cue" id=""></div>
								</div>
							</li>
							<li class="col-md-3 col-sm-4 col-xs-12 list-style">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red"></span> 技术职称:</span>
								<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
									<input class="span5" type="text" name="goods_project_technical" onchange="getCount(this)" typeCode="GOODS_PROJECT"> <span class="add-on">i</span>
									<div class="cue" id=""></div>
								</div>
							</li>
							<li class="col-md-3 col-sm-4 col-xs-12 list-style">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red"></span> 工程执业资格:</span>
								<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
									<input class="span5" type="text" name="goods_project_qualification" onchange="getCount(this)" typeCode="GOODS_PROJECT"> <span class="add-on">i</span>
									<div class="cue" id=""></div>
								</div>
							</li>
						</ul>
						<div class="clear"></div>
					</form>
				</div>
				<!-- 物资技术 -->
				<div class="col-md-12 clear mt20 pt20 borderTS1 display-none" id="GOODS">
					<form id="GOODS_form">
						<!-- <input class="span5" type="hidden" name="cateCode" value="GOODS"> -->
						<div class="col-xs-2 p0"><button class="btn" type="button">当前满足<span id="GOODS_count">0</span>人</button></div>
						<ul class="col-xs-10 p0">
							<li class="col-md-3 col-sm-4 col-xs-12 pl15 list-style">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 物资技术人数:</span>
								<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
									<input class="span5" type="text" typeCode="GOODS" name="goods_i_count"> <span class="add-on">i</span>
									<div class="cue" id=""></div>
								</div>
							</li>
							<li class="col-md-3 col-sm-4 col-xs-12 list-style">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red"></span> 产品类别:</span>
								<input type="hidden" name="goods_isSatisfy" class="isSatisfy">
          						<input type="hidden" name="goods_type" class="categoryId">
								<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
									<input class="span5" type="text" readonly onclick="opens(this);" typeCode="GOODS" name=""> <span class="add-on">i</span>
									<div class="cue" id=""></div>
								</div>
							</li>
							<li class="col-md-3 col-sm-4 col-xs-12 list-style">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red"></span> 技术职称:</span>
								<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
									<input class="span5" type="text" onchange="getCount(this)" typeCode="GOODS" name="goods_technical"> <span class="add-on">i</span>
									<div class="cue" id=""></div>
								</div>
							</li>
						</ul>
						<div class="clear"></div>
					</form>
				</div>
				<!-- 物资服务经济 -->
				<div class="col-md-12 clear mt20 pt20 borderTS1 display-none" id="GOODS_SERVER">
					<form id="GOODS_SERVER_form">
						<!-- <input class="span5" type="hidden" name="cateCode" value="GOODS_SERVER"> -->
						<div class="col-xs-2 p0"><button class="btn" type="button">当前满足<span id="GOODS_SERVER_count">0</span>人</button></div>
						<ul class="col-xs-10 p0">
							<li class="col-md-3 col-sm-4 col-xs-12 pl15 list-style">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 物资服务经济人数:</span>
								<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
									<input class="span5" type="text" name="goods_server_i_count" typeCode="GOODS_SERVER"> <span class="add-on">i</span>
									<div class="cue" id=""></div>
								</div>
							</li>
							<li class="col-md-3 col-sm-4 col-xs-12 list-style">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red"></span> 产品类别:</span>
								<input type="hidden" name="goods_server_isSatisfy" class="isSatisfy">
          						<input type="hidden" name="goods_server_type" class="categoryId">
								<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
									<input class="span5" type="text" readonly onclick="opens(this);" typeCode="GOODS_SERVER" name=""> <span class="add-on">i</span>
									<div class="cue" id=""></div>
								</div>
							</li>
							<li class="col-md-3 col-sm-4 col-xs-12 list-style">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red"></span> 技术职称:</span>
								<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
									<input class="span5" type="text" name="goods_server_technical" onchange="getCount(this)" typeCode="GOODS_SERVER"> <span class="add-on">i</span>
									<div class="cue" id=""></div>
								</div>
							</li>
						</ul>
						<div class="clear"></div>
					</form>
				</div>
				<!-- 服务 -->
				<div class="col-md-12 clear mt20 pt20 borderTS1 display-none" id="SERVICE">
					<form id="SERVICE_form">
						<!-- <input class="span5" type="hidden" name="cateCode" value="SERVICE"> -->
						<div class="col-xs-2 p0"><button class="btn" type="button">当前满足<span id="SERVICE_count">0</span>人</button></div>
						<ul class="col-xs-10 p0">
							<li class="col-md-3 col-sm-4 col-xs-12 pl15 list-style">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 服务技术人数:</span>
								<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
									<input class="span5" type="text" name="service_i_count" typeCode="SERVICE"> <span class="add-on">i</span>
									<div class="cue" id=""></div>
								</div>
							</li>
							<li class="col-md-3 col-sm-4 col-xs-12 list-style">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red"></span> 产品类别:</span>
								<input type="hidden" name="service_isSatisfy" class="isSatisfy">
          						<input type="hidden" name="service_type" class="categoryId">
								<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
									<input class="span5" type="text" readonly onclick="opens(this);" typeCode="SERVICE" name=""> <span class="add-on">i</span>
									<div class="cue" id=""></div>
								</div>
							</li>
							<li class="col-md-3 col-sm-4 col-xs-12 list-style">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red"></span> 技术职称:</span>
								<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
									<input class="span5" type="text" name="service_technical" onchange="getCount(this)" typeCode="SERVICE"> <span class="add-on">i</span>
									<div class="cue" id=""></div>
								</div>
							</li>
						</ul>
						<div class="clear"></div>
					</form>
				</div>
				
				<div class="col-md-12 clear tc mt10">
					<button class="btn" type="button" onclick="artificial_extracting()" id="artificial">人工抽取</button>
					<button class="btn" type="button" onclick="" id="auto">自动抽取</button>
					<button class="btn" type="button" onclick="" id="reset">重置</button>
				</div>
				<div class="clear"></div>
			</ul>
			</div>
			<div id="result" class="display-none">
				<h2 class="count_flow"><i>4</i>抽取结果</h2>
				<ul class="ul_list">
					<h2 class="count_flow">物资技术：确认参加的专家共有<span id="">0</span>位，确认不参加的专家共有<span id="">0</span>位</h2>
					<div class="content">
						<table class="table table-bordered table-condensed table-hover table-striped" id="GOODS_result">
							<thead>
								<tr>
									<th class="w50 info">序号</th>
									<th class="info">专家姓名</th>
									<th class="info">联系电话</th>
									<th class="info">专家类别</th>
									<th class="info">工作单位名称</th>
									<th class="info">技术职称（职务）</th>
									<th class="info">职业资格</th>
									<th class="info">备注</th>
									<th class="info">操作</th>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>
					<h2 class="count_flow">物资服务经济：确认参加的专家共有<span id="">0</span>位，确认不参加的专家共有<span id="">0</span>位</h2>
					<div class="content">
						<table class="table table-bordered table-condensed table-hover table-striped" id="GOODS_SERVER_result">
							<thead>
								<tr>
									<th class="w50 info">序号</th>
									<th class="info">专家姓名</th>
									<th class="info">联系电话</th>
									<th class="info">专家类别</th>
									<th class="info">工作单位名称</th>
									<th class="info">技术职称（职务）</th>
									<th class="info">执业资格</th>
									<th class="info">备注</th>
									<th class="info">操作</th>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>
				</ul>
			</div>
		</div>
	</div>
	
	<!-- 地区树 -->
	<div id="areaContent" class="levelTypeContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
	    <ul id="treeArea" class="ztree" style="margin-top:0;"></ul>
	</div>
</body>
</html>