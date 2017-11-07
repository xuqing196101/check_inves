<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<title>专家抽取</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/ems/expertExtract/extract.js"></script>
<style type="text/css">
	.aabbcc a.layui-layer-btn1:hover{
		color: #333 !important;
	}

	.textAreafont{
		line-height: 25px;
		color: #ef0000;
		font-size: 12px;
	}
</style>
</head>
<body>
  <!--面包屑导航开始-->
  <c:if test="${expertExtractProject.projectId == null || expertExtractProject.projectId == ''}">
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li><a
            href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')">首页</a></li>
          <li><a href="javascript:void(0);">支撑环境系统</a></li>
          <li><a href="javascript:void(0);">专家管理</a></li>
          <li><a href="javascript:void(0);">专家抽取</a>
          </li>
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
        <input value = "${projectId}" type = "hidden" name = "id" id = "projectId">
        <input value = "${expertExtractProject.projectId}" type = "hidden" name = "projectId" id = "xmProjectId">
        <input value = "${expertExtractProject.packageId}" type = "hidden" name = "packageId" id="packageId">
        <input type = "hidden" name = "isAuto" id = "isAuto">
        <ul class="ul_list">
          <li class="col-md-3 col-sm-4 col-xs-12 pl15">
            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 项目名称:</span>
            <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
              <input class="span5" id="projectName" name=projectName value="${expertExtractProject.projectName}" <c:if test="${expertExtractProject != null }">disabled="disabled"</c:if> type="text" >
              <span class="add-on">i</span>
              <div class="cue" id="err_projectName"></div>
            </div>
          </li>
          <li class="col-md-3 col-sm-4 col-xs-12">
            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span> 项目编号:</span>
            <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
              <input class="span5" id="projectCode" name="code" value="${expertExtractProject.code}" <c:if test="${expertExtractProject != null }">disabled="disabled"</c:if> type="text" onblur="vaCode()" maxlength="80">
              <span class="add-on">i</span>
              <div class="cue" id="err_code"></div>
            </div>
          </li>
          <li class="col-md-3 col-sm-4 col-xs-12 ">
            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span> 采购方式:</span>
            <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
              <select class="col-md-12 col-sm-12 col-xs-12 p0" name="purchaseWay" id="purchaseWay" <c:if test="${expertExtractProject != null }">disabled="disabled"</c:if>>
                <c:forEach items="${purchaseWayList}" var="map">
                  <option value="${map.id}" <c:if test="${map.id == expertExtractProject.purchaseWay }">selected="selected"</c:if> >${map.name}</option>
                </c:forEach>
              </select>
            </div>
          </li>
          <li class="col-md-3 col-sm-4 col-xs-12">
            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"> 包名（标段）:</span>
            <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
              <input class="span5" id="packageName" name="packageName" value="${packageName}" <c:if test="${expertExtractProject != null }">disabled="disabled"</c:if> type="text">
              <span class="add-on">i</span>
              <div class="cue" id=""></div>
            </div>
          </li>
          <li class="col-md-3 col-sm-4 col-xs-12">
            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span> 评审时间:</span>
            <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
              <input class="col-md-12 col-sm-12 col-xs-6 p0" onchange="getCount()" value="<fmt:formatDate value='${reviewTime }' pattern='yyyy-MM-dd HH:mm:ss' />" name = "reviewTime" id = "reviewTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-{%d+1}'});" pattern='yyyy-MM-dd HH:mm:ss' type="text" readonly="readonly">
              <div class="cue" id="err_reviewTime"></div>
            </div>
          </li>
          <li class="col-md-3 col-sm-4 col-xs-12">
            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span> 评审地点:</span>
            <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
              <select class="col-md-6 col-sm-6 col-xs-6 p0" onchange="functionArea()" id="province" name="reviewProvince">
                <option value="0">选择省/市</option>
                <c:forEach items="${areaTree}" var="map">
                  <option value="${map.id}">${map.name}</option>
                </c:forEach>
              </select> 
              <select class="col-md-6 col-sm-6 col-xs-6 p0" id= "city" name = "reviewAddress">
                <option value="0">选择地区</option>
              </select>
              <div class="cue" id="err_aaa"></div>
            </div>
          </li>
          <li class="col-md-3 col-sm-4 col-xs-12 ">
            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 评审详细地址:</span>
            <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
              <input class="span5" type="text" id="reviewSite" name="reviewSite" value="${reviewSite }" maxlength="100"> <span class="add-on">i</span>
              <div class="cue" id="err_reviewSite"></div>
            </div>
          </li>
          <li class="col-md-3 col-sm-4 col-xs-12 ">
            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span> 项目类型:</span>
            <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
              <select class="col-md-12 col-sm-12 col-xs-12 p0" name="projectType" id="projectType" onchange="loadExpertKind()" <c:if test="${expertExtractProject != null }">disabled="disabled"</c:if>>
                <c:forEach items="${projectTypeList}" var="map">
                  <option value="${map.id}" <c:if test="${map.id == expertExtractProject.projectType }">selected="selected"</c:if> >${map.name}</option>
                </c:forEach>
              </select>
            </div>
          </li>
          <li class="col-md-3 col-sm-4 col-xs-12 display-none" id="jsdw">
            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 建设单位名称:</span>
            <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
              <input class="span5" type="text" id="constructionName" name="constructionName" value="${constructionName }"> <span class="add-on">i</span>
              <div class="cue" id="err_constructionName"></div>
            </div>
          </li>
          <li class="col-md-3 col-sm-4 col-xs-12 ">
            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 抽取地址:</span>
            <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
              <input class="span5" type="text" id="extractAddress" name="extractAddress" value="${extractAddress }"> <span class="add-on">i</span>
              <div class="cue" id="err_extractAddress"></div>
            </div>
          </li>
          <li class="col-md-3 col-sm-4 col-xs-12 ">
            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 评审天数:</span>
            <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
              <select class="col-md-12 col-sm-12 col-xs-12 p0" name="reviewDays" id="reviewDays" onchange="getCount()">
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
              <input class="span5" type="text" id="contactPerson" name="contactPerson" value="${contactPerson }"> <span class="add-on">i</span>
              <div class="cue" id="err_contactPerson"></div>
            </div>
          </li>
          <li class="col-md-3 col-sm-4 col-xs-12 ">
            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 联系固话:</span>
            <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
              <input class="span5" type="text" id="landline" name="landline" value="${landline }"> 
              <span class="add-on">i</span>
              <div class="cue" id="err_landline"></div>
            </div>
          </li>
          <li class="col-md-3 col-sm-4 col-xs-12 ">
            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 联系手机:</span>
            <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
              <input class="span5" type="text" id="contactNum" name="contactNum" value="${contactNum }"> 
              <span class="add-on">i</span>
              <div class="cue" id="err_contactNum"></div>
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
      <h2 class="count_flow"><i>2</i>人员信息</h2>
      <div id="div_2">
        <ul class="ul_list">
		 <span class="col-md-12 col-sm-12 col-xs-12 p0"><span class="red">*</span><b> 抽取人员:</b></span><span  class="red" id="eError"></span>
		 <form action="<%=request.getContextPath() %>/extractUser/addPerson.do" onsubmit="return false" id="extractUser">
		 <div class="col-md-12 col-sm-12 col-xs-12 p0 mt10">
		 	<input type="hidden" value="extractUser" id="eu" name="personType">
		 	<input type="hidden" name="recordId" value="${projectId}">
		 	<input type="button" class="btn btn-windows add"  onclick="addPerson(this)" value="新增">
		 	<input type="button" class="btn btn-windows delete" onclick="delPerson(this)" value="删除">
		 	<input type="button" class="btn btn-windows input" onclick="selectHistory(this)" value="引用历史人员">
		 </div>
		 <div class="clear"></div>
		  <table class="table table-bordered table-condensed table_input mt10">
              <thead>				
	              <tr>
	                  <th class="info"><input type="checkbox" onclick="checkAll(this)"> </th>
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
		 <span class="col-md-12 col-sm-12 col-xs-12 p0"><span class="red">*</span><b> 监督人员:</b></span><span  class="red" id="sError"></span>
		  <form action="<%=request.getContextPath() %>/supervise/addPerson.do" id="supervise"  onsubmit="return false" >
		  <div class="col-md-12 col-sm-12 col-xs-12 p0 mt10">
		  <input type="hidden" name="recordId" value="${projectId}">
		 	<input type="hidden" value="supervise" id="su" name="personType">
		 	<input type="button" class="btn btn-windows add" onclick="addPerson(this)" value="新增">
		 	<input type="button" class="btn btn-windows delete" onclick="delPerson(this)" value="删除">
		 	<input type="button" class="btn btn-windows input" onclick="selectHistory(this)" value="引用历史人员">
		 </div>
		 <div class="clear"></div>
		  <table class="table table-bordered table-condensed table_input mt10">
              <thead>				
	              <tr>
	                  <th class="info"><input type="checkbox" onclick="checkAll(this)"> </th>
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
       </ul>
      </div>
      <h2 class="count_flow"><i>3</i>抽取条件</h2>
      <div id="div_3">
      <!--地区id -->
      <!-- 省 -->
      <ul class="ul_list">
        <form id="condition_form">
        <input type = "hidden" id = "conditionId" name = "conId" value = "${conditionId }">
        <input type="hidden" name="areaName" id="provincesel"/>
        <input type="hidden" name="addressId" id="addressId">
          <li class="col-md-3 col-sm-4 col-xs-12 pl15">
            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 专家所在地区:</span>
            <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
              <input class="span5" type="text" readonly id="area" onclick="showTree();"> <span class="add-on">i</span>
              <div class="cue" id="err_provincesel"></div>
            </div>
          </li>
          <li class="col-md-3 col-sm-4 col-xs-12 ">
            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span> 专家类型:</span>
            <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
              <select class="col-md-12 col-sm-12 col-xs-12 p0" name="expertTypeId" onchange="getCount()">
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
              <input class="span5" type="text" name="extractNum" id="extractNum" onchange="getCount()" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"> <span class="add-on">i</span>
              <div class="cue" id="err_extractNum"></div>
            </div>
          </li>
          <li class="col-md-3 col-sm-4 col-xs-12 ">
            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span> 是否抽取候补专家:</span>
            <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
              <select class="col-md-12 col-sm-12 col-xs-12 p0" name="isExtractAlternate" id="isExtractAlternate">
                <option value="0">否</option>
                <option value="1">是</option>
              </select>
            </div>
          </li>
          <li class="col-md-5 col-sm-5 col-xs-12 ">
            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">&nbsp;</span>
            <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">注：选择"是"，取完满足人数后，需要抽取两名候补专家</div>
          </li>
          <li class="clear"></li>
          <li class="col-md-12 col-sm-12 col-xs-12 display-none" id="addressReason">
            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 区域限制理由:</span>
            <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
              <!-- <input class="span5" type="text" maxlength="100" name="addressReason" id="xzReason">
              <span class="add-on">i</span><span class="input-tip">最多100字</span> -->
              <textarea class="w100p h100 resizen" maxlength="500" name="addressReason" id="xzReason" onkeyup="size(this);"></textarea>
              <small>字数：500. 剩余：<span id="textCount">500</span>.</small>
              <div class="textAreafont" id="err_addressReason"></div>
            </div>
          </li>
        </form>
        <!-- 工程技术 -->
        <div class="col-md-12 clear mt20 pt20 borderTS1 display-none" id="PROJECT">
          <form id="PROJECT_form">
            <!-- <input class="span5" type="hidden" name="cateCode" value="PROJECT"> -->
            <div class="col-xs-2 p0"><button class="btn" type="button">当前满足<span id="PROJECT_count">0</span>家</button></div>
            <ul class="col-xs-10 p0">
              <li class="col-md-3 col-sm-4 col-xs-12 pl15 list-style">
                <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 工程技术人数:</span>
                <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                  <input class="span5" type="text" typeCode="PROJECT" id="project_i_count" name="project_i_count" onchange="vaCount(this)" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"> <span class="add-on">i</span>
                  <div class="cue" id="err_project_i_count"></div>
                </div>
              </li>
              <li class="col-md-3 col-sm-4 col-xs-12 list-style">
                <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 工程专业信息:</span>
                <input type="hidden" name="project_eng_isSatisfy" class="isSatisfy">
                <input type="hidden" name="project_eng_info" class="categoryId" id="project_eng_info">
                <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                  <input class="span5" type="text" readonly onclick="opens(this);" typeCode="PROJECT,ENG_INFO_ID" name=""> <span class="add-on">i</span>
                  <div class="cue" id="err_project_eng_info"></div>
                </div>
              </li>
              <li class="col-md-3 col-sm-4 col-xs-12 list-style">
                <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red"></span> 参评类别:</span>
                <input type="hidden" name="project_isSatisfy" class="isSatisfy" id="project_isSatisfy">
                <input type="hidden" name="project_type" class="categoryId"  id="project_type">
                <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                  <input class="span5" type="text" readonly onclick="opens(this);" typeCode="PROJECT"> <span class="add-on">i</span>
                  <div class="cue" id=""></div>
                </div>
              </li>
              <li class="col-md-3 col-sm-4 col-xs-12 list-style">
                <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red"></span> 技术职称:</span>
                <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                  <input class="span5" type="text" name="project_technical" onchange="getCount()" typeCode="PROJECT"> <span class="add-on">i</span>
                  <div class="cue" id=""></div>
                </div>
              </li>
              <li class="col-md-3 col-sm-4 col-xs-12 list-style">
                <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red"></span> 工程执业资格:</span>
                <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                  <input class="span5" type="text" name="project_qualification" onchange="getCount()" typeCode="PROJECT"> <span class="add-on">i</span>
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
            <div class="col-xs-2 p0"><button class="btn" type="button">当前满足<span id="GOODS_PROJECT_count">0</span>家</button></div>
            <ul class="col-xs-10 p0">
              <li class="col-md-3 col-sm-4 col-xs-12 pl15 list-style">
                <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 工程经济人数:</span>
                <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                  <input class="span5" type="text" name="goods_project_i_count" id="goods_project_i_count" typeCode="GOODS_PROJECT" onchange="vaCount(this)" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"> <span class="add-on">i</span>
                  <div class="cue" id="err_goods_project_i_count"></div>
                </div>
              </li>
              <li class="col-md-3 col-sm-4 col-xs-12 list-style">
                <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 工程专业信息:</span>
                <input type="hidden" name="goods_project_eng_isSatisfy" class="isSatisfy">
                <input type="hidden" name="goods_project_eng_info" class="categoryId" id= "goods_project_eng_info">
                <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                  <input class="span5" type="text" readonly onclick="opens(this);" typeCode="GOODS_PROJECT,ENG_INFO_ID" name=""> <span class="add-on">i</span>
                  <div class="cue" id="err_goods_project_eng_info"></div>
                </div>
              </li>
              <li class="col-md-3 col-sm-4 col-xs-12 list-style">
                <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red"></span> 参评类别:</span>
                <input type="hidden" name="goods_project_isSatisfy" class="isSatisfy" id="goods_project_isSatisfy">
                <input type="hidden" name="goods_project_type" class="categoryId" id="goods_project_type">
                <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                  <input class="span5" type="text" readonly onclick="opens(this);" typeCode="GOODS_PROJECT" name=""> <span class="add-on">i</span>
                  <div class="cue" id=""></div>
                </div>
              </li>
              <li class="col-md-3 col-sm-4 col-xs-12 list-style">
                <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red"></span> 技术职称:</span>
                <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                  <input class="span5" type="text" name="goods_project_technical" onchange="getCount()" typeCode="GOODS_PROJECT"> <span class="add-on">i</span>
                  <div class="cue" id=""></div>
                </div>
              </li>
              <li class="col-md-3 col-sm-4 col-xs-12 list-style">
                <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red"></span> 工程执业资格:</span>
                <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                  <input class="span5" type="text" name="goods_project_qualification" onchange="getCount()" typeCode="GOODS_PROJECT"> <span class="add-on">i</span>
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
            <div class="col-xs-2 p0"><button class="btn" type="button">当前满足<span id="GOODS_count">0</span>家</button></div>
            <ul class="col-xs-10 p0">
              <li class="col-md-3 col-sm-4 col-xs-12 pl15 list-style">
                <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 物资技术人数:</span>
                <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                  <input class="span5" type="text" typeCode="GOODS" id="goods_i_count" name="goods_i_count" onchange="vaCount(this)" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"> <span class="add-on">i</span>
                  <div class="cue" id="err_goods_i_count"></div>
                </div>
              </li>
              <li class="col-md-3 col-sm-4 col-xs-12 list-style">
                <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red"></span> 参评类别:</span>
                <input type="hidden" name="goods_isSatisfy" id= "goods_isSatisfy" class="isSatisfy">
                <input type="hidden" name="goods_type" id="goods_type" class="categoryId">
                <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                  <input class="span5" type="text" readonly onclick="opens(this);" typeCode="GOODS" name=""> <span class="add-on">i</span>
                  <div class="cue" id=""></div>
                </div>
              </li>
              <li class="col-md-3 col-sm-4 col-xs-12 list-style">
                <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red"></span> 技术职称:</span>
                <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                  <input class="span5" type="text" onchange="getCount()" typeCode="GOODS" name="goods_technical"> <span class="add-on">i</span>
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
            <div class="col-xs-2 p0"><button class="btn" type="button">当前满足<span id="SERVICE_count">0</span>家</button></div>
            <ul class="col-xs-10 p0">
              <li class="col-md-3 col-sm-4 col-xs-12 pl15 list-style">
                <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 服务技术人数:</span>
                <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                  <input class="span5" type="text" name="service_i_count" id="service_i_count" typeCode="SERVICE" onchange="vaCount(this)" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"> <span class="add-on">i</span>
                  <div class="cue" id="err_service_i_count"></div>
                </div>
              </li>
              <li class="col-md-3 col-sm-4 col-xs-12 list-style">
                <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red"></span> 参评类别:</span>
                <input type="hidden" name="service_isSatisfy" class="isSatisfy" id="service_isSatisfy">
                <input type="hidden" name="service_type" class="categoryId" id="service_type">
                <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                  <input class="span5" type="text" readonly onclick="opens(this);" typeCode="SERVICE" name=""> <span class="add-on">i</span>
                  <div class="cue" id=""></div>
                </div>
              </li>
              <li class="col-md-3 col-sm-4 col-xs-12 list-style">
                <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red"></span> 技术职称:</span>
                <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                  <input class="span5" type="text" name="service_technical" onchange="getCount()" typeCode="SERVICE"> <span class="add-on">i</span>
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
            <div class="col-xs-2 p0"><button class="btn" type="button">当前满足<span id="GOODS_SERVER_count">0</span>家</button></div>
            <ul class="col-xs-10 p0">
              <li class="col-md-3 col-sm-4 col-xs-12 pl15 list-style">
                <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 物资服务经济人数:</span>
                <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                  <input class="span5" type="text" name="goods_server_i_count" id="goods_server_i_count" typeCode="GOODS_SERVER" onchange="vaCount(this)" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"> <span class="add-on">i</span>
                  <div class="cue" id="err_goods_server_i_count"></div>
                </div>
              </li>
              <!-- <li class="col-md-3 col-sm-4 col-xs-12 list-style">
                <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red"></span> 产品类别:</span>
                <input type="hidden" name="goods_server_isSatisfy" class="isSatisfy">
                <input type="hidden" name="goods_server_type" class="categoryId">
                <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                  <input class="span5" type="text" readonly onclick="opens(this);" typeCode="GOODS_SERVER" name=""> <span class="add-on">i</span>
                  <div class="cue" id=""></div>
                </div>
              </li> -->
              <li class="col-md-3 col-sm-4 col-xs-12 list-style">
                <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red"></span> 技术职称:</span>
                <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                  <input class="span5" type="text" name="goods_server_technical" onchange="getCount()" typeCode="GOODS_SERVER"> <span class="add-on">i</span>
                  <div class="cue" id=""></div>
                </div>
              </li>
            </ul>
            <div class="clear"></div>
          </form>
        </div>
        <div class="col-md-12 clear tc mt10">
          <button class="btn" type="button" onclick="artificial_extracting(0)" id="artificial">人工抽取</button>
          <button class="btn" type="button" onclick="artificial_extracting(1)" id="auto">自动抽取</button>
          <button class="btn" type="button" onclick="extractReset()" id="reset">重置</button>
        </div>
        <div class="clear"></div>
      </ul>
      </div>
      <div id="result" class="display-none">
        <h2 class="count_flow"><i>4</i>抽取结果</h2>
        <ul class="ul_list">
          <!-- 物资技术 -->
          <div class="display-none" id="GOODS_h">
            <h2 class="count_flow">物资技术：确认参加的专家共有<span id="GOODS_result_count" class="f26 red">0</span>位，确认不参加的专家共有<span id="GOODS_result_no">0</span>位</h2>
            <div class="content">
              <table class="table table-bordered table-condensed table-hover table-striped" id="GOODS_result">
                <thead>
                  <tr>
                    <th class="w50 info">序号</th>
                    <th class="info w100">专家姓名</th>
                    <th class="info w100">联系电话</th>
                    <th class="info w120">专家类别</th>
                    <th class="info">工作单位名称</th>
                    <th class="info w140">技术职称（职务）</th>
                    <th class="info w100">执业资格</th>
                    <th class="info w80">备注</th>
                    <th class="info w100">操作</th>
                  </tr>
                </thead>
                <tbody></tbody>
              </table>
            </div>
          </div>
          <!-- 物资服务经济 -->
          <div class="display-none" id="GOODS_SERVER_h">
            <h2 class="count_flow">物资服务经济：确认参加的专家共有<span id="GOODS_SERVER_result_count" class="f26 red">0</span>位，确认不参加的专家共有<span id="GOODS_SERVER_result_no">0</span>位</h2>
            <div class="content">
              <table class="table table-bordered table-condensed table-hover table-striped" id="GOODS_SERVER_result">
                <thead>
                  <tr>
                    <th class="w50 info">序号</th>
                    <th class="info w100">专家姓名</th>
                    <th class="info w100">联系电话</th>
                    <th class="info w120">专家类别</th>
                    <th class="info">工作单位名称</th>
                    <th class="info w140">技术职称（职务）</th>
                    <th class="info w100">执业资格</th>
                    <th class="info w80">备注</th>
                    <th class="info w100">操作</th>
                  </tr>
                </thead>
                <tbody></tbody>
              </table>
            </div>
          </div>
          <!-- 工程技术 -->
          <div class="display-none" id="PROJECT_h">
            <h2 class="count_flow">工程技术：确认参加的专家共有<span id="PROJECT_result_count" class="f26 red">0</span>位，确认不参加的专家共有<span id="PROJECT_result_no">0</span>位</h2>
            <div class="content">
              <table class="table table-bordered table-condensed table-hover table-striped" id="PROJECT_result">
                <thead>
                  <tr>
                    <th class="w50 info">序号</th>
                    <th class="info w100">专家姓名</th>
                    <th class="info w100">联系电话</th>
                    <th class="info w120">专家类别</th>
                    <th class="info">工作单位名称</th>
                    <th class="info w140">技术职称（职务）</th>
                    <th class="info w100">执业资格</th>
                    <th class="info w80">备注</th>
                    <th class="info w100">操作</th>
                  </tr>
                </thead>
                <tbody></tbody>
              </table>
            </div>
          </div>
          <!-- 工程经济 -->
          <div class="display-none" id="GOODS_PROJECT_h">
            <h2 class="count_flow">工程经济：确认参加的专家共有<span id="GOODS_PROJECT_result_count" class="f26 red">0</span>位，确认不参加的专家共有<span id="GOODS_PROJECT_result_no">0</span>位</h2>
            <div class="content">
              <table class="table table-bordered table-condensed table-hover table-striped" id="GOODS_PROJECT_result">
                <thead>
                  <tr>
                    <th class="w50 info">序号</th>
                    <th class="info w100">专家姓名</th>
                    <th class="info w100">联系电话</th>
                    <th class="info w120">专家类别</th>
                    <th class="info">工作单位名称</th>
                    <th class="info w140">技术职称（职务）</th>
                    <th class="info w100">执业资格</th>
                    <th class="info w80">备注</th>
                    <th class="info w100">操作</th>
                  </tr>
                </thead>
                <tbody></tbody>
              </table>
            </div>
          </div>
          <!-- 服务 -->
          <div class="display-none" id="SERVICE_h">
            <h2 class="count_flow">服务技术：确认参加的专家共有<span id="SERVICE_result_count" class="f26 red">0</span>位，确认不参加的专家共有<span id="SERVICE_result_no">0</span>位</h2>
            <div class="content">
              <table class="table table-bordered table-condensed table-hover table-striped" id="SERVICE_result">
                <thead>
                  <tr>
                    <th class="w50 info">序号</th>
                    <th class="info w100">专家姓名</th>
                    <th class="info w100">联系电话</th>
                    <th class="info w120">专家类别</th>
                    <th class="info">工作单位名称</th>
                    <th class="info w140">技术职称（职务）</th>
                    <th class="info w100">执业资格</th>
                    <th class="info w80">备注</th>
                    <th class="info w100">操作</th>
                  </tr>
                </thead>
                <tbody></tbody>
              </table>
            </div>
          </div>
        </ul>
      </div>
    </div>
    <div class="col-md-12 clear tc mt10">
      <div id="endButton" class="display-none">
        <button class="btn" type="button" onclick="extract_end()" id="extractEnd">抽取结束</button>
      </div>
    </div>
    <div class="clear"></div>
  </div>

  <!-- 地区树 -->
  <div id="areaContent" class="levelTypeContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
    <ul id="treeArea" class="ztree" style="margin-top:0;"></ul>
  </div>
</body>
</html>