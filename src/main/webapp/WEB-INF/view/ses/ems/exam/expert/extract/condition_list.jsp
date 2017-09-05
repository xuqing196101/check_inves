<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<title>抽取列表</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/ems/expertExtract/extract.js"></script>
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
			<form id="form">
				<h2 class="count_flow"><i>1</i>项目信息</h2>
				<input value = "${projectId}" type = "hidden" name = "id">
				<ul class="ul_list">
					<li class="col-md-3 col-sm-4 col-xs-12 pl15">
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 项目名称:</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input class="span5" id="projectName" name="name" value="${name}" type="text">
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
							<select class="col-md-6 col-sm-6 col-xs-6 p0" onchange="functionArea()" id = "province">
								<option value="">选择省</option>
							</select> 
							<select class="col-md-6 col-sm-6 col-xs-6 p0" id= "city" name = "reviewArea">
								<option value="">选择地区</option>
							</select>
						</div>
					</li>
					<li class="col-md-3 col-sm-4 col-xs-12 ">
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span> 项目类型:</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<select class="col-md-12 col-sm-12 col-xs-12 p0" name="projectType">
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
							<input class="span5" type="text" name="remark" value = "${remark }">
							<div class="cue" id="extractionSitesError"></div>
						</div>
					</li>
				</ul>
			</form>
			
			<h2 class="count_flow"><i>2</i>人员信息</h2>
			<ul class="ul_list">
				<h2 class="count_flow"><span class="red">*</span> 抽取人员</h2>
				<div class="col-md-12 pl20 mt10">
					<button class="btn btn-windows add" type="button" onclick="">新增</button>
					<button class="btn btn-windows delete" type="button" onclick="">删除</button>
					<button class="btn" type="button" onclick="">引用历史人员</button>
				</div>
				<div class="content table_box">
					<table class="table table-bordered table-condensed table-hover table-striped">
						<thead>
							<tr>
								<th class="w30 info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
								<th class="w50 info">序号</th>
								<th class="info" width="19%">姓名</th>
								<th class="info" width="35%">单位</th>
								<th class="info" width="19%">职务</th>
								<th class="info" width="19%">军衔</th>
							</tr>
						</thead>
							<tr>
								<td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="" /></td>
								<td class="tc">1</td>
								<td><input value = ""></td>
								<td><input value = ""></td>
								<td><input value = ""></td>
								<td><input value = ""></td>
							</tr>
							<tr>
								<td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="" /></td>
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
					<button class="btn btn-windows add" type="button" onclick="">新增</button>
					<button class="btn btn-windows delete" type="button" onclick="">删除</button>
					<button class="btn" type="button" onclick="">引用历史人员</button>
				</div>
				<div class="content table_box">
					<table class="table table-bordered table-condensed table-hover table-striped">
						<thead>
							<tr>
								<th class="w30 info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
								<th class="w50 info">序号</th>
								<th class="info" width="19%">姓名</th>
								<th class="info" width="35%">单位</th>
								<th class="info" width="19%">职务</th>
								<th class="info" width="19%">军衔</th>
							</tr>
						</thead>
							<tr>
								<td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="" /></td>
								<td class="tc">1</td>
								<td><input value = ""></td>
								<td><input value = ""></td>
								<td><input value = ""></td>
								<td><input value = ""></td>
							</tr>
					</table>
				</div>
			</ul>
			
			<h2 class="count_flow"><i>3</i>抽取条件</h2>
			<ul class="ul_list">
				<li class="col-md-3 col-sm-4 col-xs-12 pl15">
					<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 区域要求:</span>
					<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
						<input class="span5" type="text"> <span class="add-on">i</span>
						<div class="cue" id=""></div>
					</div>
				</li>
				<li class="col-md-3 col-sm-4 col-xs-12 ">
					<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 区域限制理由:</span>
					<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
						<input class="span5" type="text"> <span class="add-on">i</span>
						<div class="cue" id=""></div>
					</div>
				</li>
				<li class="col-md-3 col-sm-4 col-xs-12 ">
					<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span> 专家类型:</span>
					<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
						<select class="col-md-12 col-sm-12 col-xs-12 p0" name="">
							<option value="">不限</option>
							<option value="">军队</option>
							<option value="">地方</option>
						</select>
					</div>
				</li>
				<li class="col-md-3 col-sm-4 col-xs-12 ">
					<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 专家类别:</span>
					<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
						<input class="span5" type="text"> <span class="add-on">i</span>
						<div class="cue" id=""></div>
					</div>
				</li>
				<li class="col-md-3 col-sm-4 col-xs-12 ">
					<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 抽取总人数:</span>
					<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
						<input class="span5" type="text"> <span class="add-on">i</span>
						<div class="cue" id=""></div>
					</div>
				</li>
				<li class="col-md-3 col-sm-4 col-xs-12 ">
					<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span> 是否抽取后补专家:</span>
					<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
						<select class="col-md-12 col-sm-12 col-xs-12 p0" name="">
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</div>
				</li>
				<span>注：选择"是"，取完满足人数后，需要抽取两名候补专家</span>
				<div class="col-md-12 clear tc mt10">
					<button class="btn" type="button" onclick="artificial_extracting()">人工抽取</button>
					<button class="btn" type="button" onclick="">自动抽取</button>
					<button class="btn" type="button" onclick="">重置</button>
				</div>
				<div class="clear"></div>
			</ul>
			<h2 class="count_flow"><i>4</i>抽取结果</h2>
			<ul class="ul_list">
				<table class="table table-bordered table-condensed table-hover table-striped">
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
				</table>
			</ul>
		</div>
	</div>
</body>
</html>