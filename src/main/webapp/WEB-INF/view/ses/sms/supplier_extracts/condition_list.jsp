<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML >
<html>
<head>
    <%@ include file="../../../common.jsp"%>
    <title>任务管理</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css" type="text/css">

</head>
<body>
<div id="packageContent" class="packageContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
    <ul id="treePackageType" class="ztree" style="margin-top:0;"></ul>
</div>
<!--面包屑导航开始-->
<c:if test="${typeclassId==null }">
    <div class="margin-top-10 breadcrumbs ">
        <div class="container">
            <ul class="breadcrumb margin-left-0">
                <li>
                    <a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
                </li>
                <li>
                    <a href="javascript:void(0);">支撑环境</a>
                </li>
                <li>
                    <a href="javascript:void(0);">供应商管理</a>
                </li>
                <li>
                    <a href="javascript:void(0);" onclick="jumppage('${pageContext.request.contextPath}/SupplierExtracts/projectList.html?typeclassId=${typeclassId}')">供应商抽取</a>
                </li>
                <li class="active">
                    <a href="javascript:void(0);">供应商抽取列表</a>
                </li>
            </ul>
            <div class="clear"></div>
        </div>
    </div>
</c:if>

<!-- 项目戳开始 -->
<div class="container">
<!-- 项目信息开始 -->
<div class="container_box col-md-12 col-sm-12 col-xs-12 extractVerify_disabled">
<c:set value="false" var="flag"></c:set>
<c:if test="${projectInfo.projectName !=null }">
	<c:set var="flag" value="true"></c:set>
</c:if>
    <form id="projectForm" action="<%=request.getContextPath() %>/SupplierExtracts/saveProjectInfo.do" method="post" >
        <!-- 打开类型 -->
      <%--   <input type="hidden" value="${typeclassId}" name="typeclassId" /> --%>
        <!-- 项目id  -->
        <input type="hidden" id="pojectId" value="${projctInfo.projectId}" name="pojectId">
        <!-- 记录id -->
        <input type="hidden" value="${projectInfo.id}" name="id">
         <h2 class="count_flow"><i>1</i>项目信息</h2>
         <ul class="ul_list border0 m0">
             <li class="col-md-3 col-sm-4 col-xs-12 pl15">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span> 项目名称:</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                     <input id="projectName" name="projectName"  value="${projectInfo.projectName}" readonly= '${flag=="true"?"readonly":"555" }'   type="text">
                     <span class="add-on">i</span>
                     <div class="cue" id="projectNameError"></div>
                 </div>
             </li>
             <li class="col-md-3 col-sm-4 col-xs-12">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span> 项目编号:</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                     <input id="projectNumber" name="projectCode" value="${projectInfo.projectCode}" readonly=${flag?"readonly":"" } type="text" >
                     <span class="add-on">i</span>
                     <div class="cue" id="projectNumberError"></div>
                 </div>
             </li>
             <li class="col-md-3 col-sm-4 col-xs-12">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>采购方式:</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
           			<c:if test="${projectInfo.purchaseType ==null }">
                   	<select name="purchaseType" class="col-md-12 col-sm-12 col-xs-6 p0">
                   	 	<option value="3CF3C643AE0A4499ADB15473106A7B80" >竞争性谈判</option>
                        <option value="EF33590F956F4450A43C1B510EBA7923" >询价采购</option>
                        <option value="209C109291F241D88188521A7F8FA308" >邀请招标</option>
                     </select>
                    </c:if>
                   	<c:if test="${projectInfo.purchaseType !=null }">
                		  <input id="purchaseType" name="purchaseType" value="${projectInfo.purchaseType}" type="hidden" >
                		  <input id="purchaseType" name="purchaseType" value="${projectInfo.purchaseTypeName}" readonly=${flag?"readonly":"" } type="text" >
                   	</c:if>
                            
                 	<div class="cue" id="purchaseTypeError"></div>
                 </div>
             </li>
             <li class="col-md-3 col-sm-4 col-xs-12">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">包名(标段):</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                     <input id="packageName" name="packageName" value="${projectInfo.projectCode}" readonly=${flag?"readonly":"" } type="text" >
                     <span class="add-on">i</span>
                     <div class="cue" id="packageNameError"></div>
                 </div>
             </li>
             <li class="col-md-3 col-sm-4 col-xs-12">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>售领采购文件起始时间:</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                     <input class="col-md-12 col-sm-12 col-xs-6 p0"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" onchange="checkTime()"  id="sellBegin" readonly="readonly"  name="sellBegin" value="<fmt:formatDate value='${project}'
                             pattern='yyyy-MM-dd HH:mm:ss' />" maxlength="30" type="text">
                     <div class="cue" id="sellBeginError"></div>
                 </div>
             </li>
             <li class="col-md-3 col-sm-4 col-xs-12">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>售领采购文件结束时间:</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                     <input class="col-md-12 col-sm-12 col-xs-6 p0"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',onpicking: checkTime()});" id="sellEnd" readonly="readonly"  name="sellEnd" value="<fmt:formatDate value='${bidDate}'
                             pattern='yyyy-MM-dd HH:mm:ss' />" maxlength="30" type="text">
                     <div class="cue" id="sellEndError"></div>
                 </div>
             </li>
             <li class="col-md-3 col-sm-4 col-xs-12">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>售领地区</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                     <select class="col-md-6 col-sm-6 col-xs-6 p0" id="sellProvince" name="sellProvince" onchange="selectArea(this);">
                            <c:forEach items="${province }" var="pro">
                                    <option value="${pro.id }">${pro.name }</option>
                            </c:forEach>
                        </select>
                        <select name="sellAddress" class="col-md-6 col-sm-6 col-xs-6 p0" id="sellAddress">
                       		 <c:forEach items="${address }" var="add">
                                <option value="${add.id }">${add.name }</option>
                       		 </c:forEach>
                        </select>
                     <div class="cue" id="sellAreaError"></div>
                 </div>
             </li>
             <li class="col-md-3 col-sm-4 col-xs-12">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>售领详细地址</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                     <input id="sellSite" name="sellSite"  type="text" >
                     <span class="add-on">i</span>
                     <div class="cue" id="sellSiteError"></div>
                 </div>
             </li>
             <li class="col-md-3 col-sm-4 col-xs-12">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>项目类型</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                 <%-- <c:if test="${flag }">
                 	 <input id="projectType" name="projectType" value="${projectInfo.projectType }" type="text" >
                 </c:if> --%>
                 <c:if test="${flag }">
                     <select id="projectType" name="projectType" class="col-md-12 col-sm-12 col-xs-6 p0" onchange="loadSupplierType()">
                          <option value="GOODS" ${projectInfo.projectType == 'GOODS' ? 'selected' : '' }>物资</option>
                          <option value="PROJECT" ${projectInfo.projectType == 'PROJECT' ? 'selected' : '' }>工程</option>
                          <option value="SERVICE" ${projectInfo.projectType == 'SERVICE' ? 'selected' : '' }>服务</option>
                     </select>
                 </c:if>
                     <span class="add-on">i</span>
                     <div class="cue" id="projectTypeError"></div>
                 </div>
             </li>
             <li class="col-md-3 col-sm-4 col-xs-12">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>项目实施地区</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                    <select class="col-md-6 col-sm-6 col-xs-6 p0" id="constructionPro" name="constructionPro" onchange="selectArea(this);">
                            <c:forEach items="${province }" var="pro">
                                    <option value="${pro.id }">${pro.name }</option>
                            </c:forEach>
                        </select>
                        <select name="constructionAddr" class="col-md-6 col-sm-6 col-xs-6 p0" id="constructionAddr">
                           <c:forEach items="${address }" var="add">
                                <option value="${add.id }">${add.name }</option>
                       		 </c:forEach>
                        </select>
                     <span class="add-on">i</span>
                     <div class="cue" id="constructionProError"></div>
                 </div>
             </li>
             <li class="col-md-3 col-sm-4 col-xs-12">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>联系人</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                     <input name="contactPerson" value="" type="text" >
                     <span class="add-on">i</span>
                     <div class="cue" id="contactPersonError"></div>
                 </div>
             </li>
             <li class="col-md-3 col-sm-4 col-xs-12">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>联系电话</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                     <input name="contactNum" value="" type="text" >
                     <span class="add-on">i</span>
                     <div class="cue" id="contactNumError"></div>
                 </div>
             </li>
             <li class="col-md-3 col-sm-4 col-xs-12">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">其他要求</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                     <input name="remark" value="" type="text" >
                     <span class="add-on">i</span>
                     <div class="cue" id="remarkError"></div>
                 </div>
             </li>
         </ul>
	 </form>
	</div><!-- 项目信息结束 -->
	<!-- 人员信息开始-->
	<div class="container_box col-md-12 col-sm-12 col-xs-12 extractVerify_disabled">
		 <h2 class="count_flow"><i>2</i>人员信息</h2>
		 <span class="col-md-12 col-sm-12 col-xs-12 p0"><span class="red">*</span><b> 抽取人员:</b></span><span  class="red" id="eError"></span>
		 <form action="<%=request.getContextPath() %>/extractUser/addPerson.html" onsubmit="return false" id="extractUser">
		 <div class="col-md-12 col-sm-12 col-xs-12 p0 mt10">
		 	<input type="hidden" value="extractUser" id="eu" name="personType">
		 	<input type="hidden" name="recordId" value="${projectInfo.id }">
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
		  <form action="<%=request.getContextPath() %>/supervise/addPerson.html" id="supervise"  onsubmit="return false" >
		  <div class="col-md-12 col-sm-12 col-xs-12 p0 mt10">
		  <input type="hidden" name="recordId" value="${projectInfo.id }">
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
	</div>	
	<!-- 条件开始 -->
	<div class="container_box col-md-12 col-sm-12 col-xs-12 extractVerify_disabled" >
    <form id="form1" method="post" >
        <input id="sunCount" type="hidden">
        <!--    地區id -->
        <input type="hidden" name="addressId" id="addressId">
        <!--        项目id -->
        <input type="hidden" name="projectId" id="pid" value="${packageId}">
        <!-- 记录id -->
        <input type="hidden" name="recordId" id="recordId" value="${projectInfo.id}">
        <!-- 地区 -->
        <input type="hidden" name="address" id="address">
		<input type="hidden" id="conditionId" name="id" value="${projectInfo.conditionId }">
        <!-- 类型id -->
        <input type="hidden" name="supplierTypeId" id="supplierTypeId">
        <input type="hidden" name="expertsTypeId" id="expertsTypeId">

        <!--  满足多个条件 -->
        <!-- <input type="hidden" name="isMulticondition" id="isSatisfy" value="1"> -->
        <!-- 品目Name ， -->
        <input type="hidden" name="categoryName" id="extCategoryNames">
        <!--     品目id -->
       <!--  <input type='hidden' name='categoryId' id='extCategoryId'> -->
        <input type="hidden" name="addressReason" id="addressReason">
        <!--         省 -->
        <input type="hidden" name="province" id="province"/>
        <input type="hidden" name="" id="hiddentype">
          <h2 class="count_flow"><i>3</i>抽取条件</h2>
          <ul class="ul_list m0" style="background-color: #fbfbfb">
              <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                  <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><div
                          class="star_red">*</div>所在地区：</span>
                  <div class="input-append input_group col-sm-12 col-xs-12 p0">
                  	<input class="input_group " readonly  id="area" onclick="showTree();">
                  	 <span class="add-on">i</span>
                  	  <div class="cue" id="dCategoryName"></div>
                  </div>
              </li>
               <li class="col-md-3 col-sm-6 col-xs-12  dnone">
                  <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>限制地区理由：</span>
                  <div class="input-append input_group col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="areaReson" id="areaReson" value=""
                             type="text">
                      <span class="add-on">i</span>
                      <div class="cue" ></div>
                  </div>
              </li>
              <li class="col-md-3 col-sm-6 col-xs-12">
                  <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div
                          class="star_red">*</div>供应商类型：</span>
                  <div class="input-append input_group col-sm-12 col-xs-12 p0">
                  		<%-- <input name="supplierTypeCode" id="supplierTypeCode" type="hidden">
                      <input id="supplierType" class="" type="text" readonly
                             value="${listCon.conTypes[0].supplierTypeName }" name="supplierTypeName"
                             onclick="showSupplierType();"/> --%>
                      <select id=supplierType name="supplierTypeCode" onchange="initCategoryAndLevel(this)" class="w100p">
                      </select>
                      <span class="add-on">i</span>
                      <div class="cue" id="dCount"></div>
                  </div>
              </li>
              <li class="col-md-3 col-sm-6 col-xs-12">
                  <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="star_red">*</span>抽取总数量：</span>
                  <div class="input-append input_group col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="supplierCount" id="supplierCount" value="${sumCount}"
                             type="text">
                      <span class="add-on">i</span>
                      <div class="cue" id="countSupplier"></div>
                  </div>
              </li>
              
		<li class="clear"></li>
		 <li class="dnone projectCount">
          <div class="col-xs-2 p0"><button class="btn" type="button">当前满足<span id="projectCount">0</span>人</button></div>
        	</li>
				<li class="col-md-3 col-sm-3 col-xs-3 dnone projectCount">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>供应商数量：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
            <input class="title col-md-12" id='projectExtractNum' name="projectExtractNum" 
                 maxlength="11" type="text">
            <span class="add-on">i</span>
            <div class="cue">${loginPwdError}</div>
          </div>
        </li>
				<li class="col-md-3 col-sm-3 col-xs-3 dnone projectCount">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">工程品目：</span>
          <!--  满足多个条件 -->
          <input type="hidden" name="projectIsMulticondition" class="isSatisfy">
          <input type="hidden" name="projectCategoryIds" id="projectCategoryIds" class="categoryId">
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
            <input class="input_group " readonly  typeCode="PROJECT"
                 value="${listCon.conTypes[0].categoryName}" onclick="opens(this);" type="text">
            <span class="add-on">i</span>
            <div class="cue" id="dCategoryName"></div>
          </div>
          </li>
          <li class="col-md-3 col-sm-3 col-xs-3 dnone projectCount">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"></span>工程资质：</span>
           <div class="input-append input_group col-sm-12 col-xs-12 p0">
            <input type="hidden" name="qua" id="quaId" >
            <input type="text" readonly  id="quaName" treeHome="quaContent"
                 value="${listCon.supplierLevel == null? '全部资质':listCon.supplierLevel}" onclick="showQua(this);"/>
            <span class="add-on">i</span>
            <div class="cue" id="dCount"></div>
          </div>
          </li>
          <li class="col-md-3 col-sm-3 col-xs-3 dnone projectCount">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">工程等级：</span>
           <div class="input-append input_group col-sm-12 col-xs-12 p0">
            <input type="hidden" name="projectLevel" >
            <input type="text" readonly  id="projectLevel" treeHome="projectLevelContent"
                 value="${listCon.supplierLevel == null? '所有级别':listCon.supplierLevel}" onclick="showLevel(this);"/>
            <span class="add-on">i</span>
            <div class="cue" id="dCount"></div>
          </div>
          </li>
          
        
        <li class="clear"></li>
         <li class="dnone serviceCount">
          <div class="col-xs-2 p0"><button class="btn" type="button">当前满足<span id="serviceCount">0</span>人</button></div>
        </li>
         <li class="col-md-3 col-sm-3 col-xs-3 dnone serviceCount">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>供应商数量：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
            <input class="title col-md-12" id='serviceExtractNum' name="serviceExtractNum" 
                 maxlength="11" type="text">
            <span class="add-on">i</span>
            <div class="cue">${loginPwdError}</div>
          </div>
        </li>
        <li class="col-md-3 col-sm-3 col-xs-3 dnone serviceCount">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">服务品目：</span>
          <!--  满足多个条件 -->
          <input type="hidden" name="serviceIsMulticondition" class="isSatisfy">
          <input type="hidden" name="serviceCategoryIds" id="serviceCategoryIds" class="categoryId">
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
            <input class="input_group " readonly  typeCode="SERVICE"
                 value="${listCon.conTypes[0].categoryName}" onclick="opens(this);" type="text">
            <span class="add-on">i</span>
            <div class="cue" id="dCategoryName"></div>
          </div>
          </li>
          <li class="col-md-3 col-sm-3 col-xs-3 dnone serviceCount">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">服务等级：</span>
           <div class="input-append input_group col-sm-12 col-xs-12 p0">
            <input type="hidden" name="serviceLevel" >
            <input type="text" readonly  id="serviceLevel" treeHome="serviceLevelContent"
                 value="${listCon.supplierLevel == null? '所有级别':listCon.supplierLevel}" onclick="showLevel(this);"/>
            <span class="add-on">i</span>
            <div class="cue" id="dCount"></div>
          </div>
          </li>
         
        
        <!-- <li class="clear"></li> -->
        <li class="dnone productCount">
          <div class="col-xs-2 p0"><button class="btn" type="button">当前满足<span id="productCount">0</span>人</button></div>
        </li>
         <li class="col-md-3 col-sm-3 col-xs-3 dnone productCount">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>供应商数量：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
            <input class="title col-md-12" id='productExtractNum' name="productExtractNum"
                 maxlength="11" type="text">
            <span class="add-on">i</span>
            <div class="cue">${loginPwdError}</div>
          </div>
        </li>
        <li class="col-md-3 col-sm-3 col-xs-3 dnone productCount">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">生产品目：</span>
          <!--  满足多个条件 -->
          <input type="hidden" name="productIsMulticondition" class="isSatisfy">
          <input type="hidden" name="productCategoryIds" id="productCategoryIds" class="categoryId">
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
            <input class="input_group " readonly  typeCode="PRODUCT"
                 value="${listCon.conTypes[0].categoryName}" onclick="opens(this);" type="text">
            <span class="add-on">i</span>
            <div class="cue" id="dCategoryName"></div>
          </div>
          </li>
          <li class="col-md-3 col-sm-3 col-xs-3 dnone productCount">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">生产等级：</span>
           <div class="input-append input_group col-sm-12 col-xs-12 p0">
            <input type="hidden" name="productLevel" >
            <input type="text" readonly  id="productLevel" treeHome="productLevelContent"
                 value="${listCon.supplierLevel == null? '所有级别':listCon.supplierLevel}" onclick="showLevel(this);"/>
            <span class="add-on">i</span>
            <div class="cue" id="dCount"></div>
          </div>
          </li>
        <li class="clear"></li>
        <li class="dnone salesCount">
          <div class="col-xs-2 p0"><button class="btn" type="button">当前满足<span  id="salesCount">0</span>人</button></div>
        </li>
          <li class="col-md-3 col-sm-3 col-xs-3 dnone salesCount">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>供应商数量：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
            <input class="title col-md-12" id='salesExtractNum' name="salesExtractNum" 
                 maxlength="11" type="text">
            <span class="add-on">i</span>
            <div class="cue">${loginPwdError}</div>
          </div>
        </li>
        
	        <li class="col-md-3 col-sm-3 col-xs-3 dnone salesCount">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">销售品目：</span>
          <!--  满足多个条件 -->
          <input type="hidden" name="salesIsMulticondition" class="isSatisfy">
          <input type="hidden" name="salesCategoryIds" id="salesCategoryIds" class="categoryId">
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
            <input class="input_group " readonly  typeCode="SALES"
                 value="${listCon.conTypes[0].categoryName}" onclick="opens(this);" type="text">
            <span class="add-on">i</span>
            <div class="cue" id="dCategoryName"></div>
          </div>
          </li>
          <li class="col-md-3 col-sm-3 col-xs-3 dnone salesCount">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">销售等级：</span>
           <div class="input-append input_group col-sm-12 col-xs-12 p0">
            <input type="hidden" name="salesLevel" >
            <input type="text" readonly  id="salesLevel" treeHome="salesLevelContent"
                 value="${listCon.supplierLevel == null? '所有级别':listCon.supplierLevel}" onclick="showLevel(this);"/>
            <span class="add-on">i</span>
            <div class="cue" id="dCount"></div>
          </div>
          </li>
        
        
          <li class="col-md-3 col-sm-3 col-xs-3 elseInfo">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">企业性质：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
                 <select name="businessNature" class="w100p" onchange="selectLikeSupplier()">
                 	<option value="">不限</option>
                 <c:forEach items="${businessNature }" var="bu">
                 	<option value="${bu.id }">${bu.name }</option>
                 </c:forEach>
                 </select>
            <div class="cue">${loginPwdError}</div>
          </div>
        </li>
          <li class="col-md-3 col-sm-3 col-xs-3 else">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">保密要求：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
           <select name="isHavingConCert" class="w100p" onchange="selectLikeSupplier()">
                 	<option value="0">无</option>
                 	<option value="1">有</option>
                 </select>
            <div class="cue">${loginPwdError}</div>
          </div>
        </li>
          <li class="col-md-3 col-sm-3 col-xs-3 else">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">境外分支：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
			<select name="overseasBranch" class="w100p" onchange="selectLikeSupplier()">
                 	<option value="1">有</option>
                 	<option value="0">无</option>
                 </select>
            <div class="cue">${loginPwdError}</div>
          </div>
        </li>
          </ul>
          <div class="clear"></div>
	         <div class="col-xs-12 tc mt20">
	           <button class="btn" onclick="extractVerify();" type="button">人工抽取</button>
	           <button class="btn" type="button">自动抽取</button>
	           <button class="btn"  type="reset">重置</button>
	         </div>
          </form>
          <!--=== Content Part ===-->
          </div>
          <div class="container_box col-md-12 col-sm-12 col-xs-12" id="result">
          <h2 class="count_flow"><i>4</i>抽取结果</h2>
	         <div class="ul_list" id="projectResult">
	          	<div align="left" id="countdnone" >工程供应商：确认参加的供应商为<span class="f26 red" id="count">0</span>人，确认不参加的有<span class="notJoin">0</span>人</div>
	           	<!-- Begin Content -->
                 <table id="table" class="table table-bordered table-condensed">
                     <thead>
                     <tr>
                         <th class="info w50">序号</th>
                         <th class="info" width="15%">供应商名称</th>
                         <th class="info" width="15%">类型</th>
                         <th class="info" width="15%">联系人名称</th>
                         <th class="info" width="18%">联系人电话</th>
                         <th class="info" width="18%">联系人手机</th>
                         <th class="info">操作</th>
                     </tr>
                     </thead>
                     <tbody>
                     
                     </tbody>
                </table>
			</div>
          <div class="ul_list dnone clear" id="serviceResult">
          <div align="left" id="countdnone" >服务供应商：确认参加的供应商为<span class="f26 red" id="count">0</span>人，确认不参加的有<span class="notJoin">0</span>人</div>
           <!-- Begin Content -->
                  <table id="table" class="table table-bordered table-condensed">
                      <thead>
                      <tr>
                          <th class="info w50">序号</th>
                          <th class="info" width="15%">供应商名称</th>
                          <th class="info" width="15%">类型</th>
                          <th class="info" width="15%">联系人名称</th>
                          <th class="info" width="18%">联系人电话</th>
                          <th class="info" width="18%">联系人手机</th>
                          <th class="info">操作</th>
                      </tr>
                      </thead>
                      <tbody>
                      
                      </tbody>
                 </table>
			</div>
          <div class="ul_list dnone clear" id="productResult">
          <div align="left" id="countdnone" >生产供应商：确认参加的供应商为<span class="f26 red" id="count">0</span>人，确认不参加的有<span class="notJoin">0</span>人</div>
           <!-- Begin Content -->
                  <table id="table" class="table table-bordered table-condensed">
                      <thead>
                      <tr>
                          <th class="info w50">序号</th>
                          <th class="info" width="15%">供应商名称</th>
                          <th class="info" width="15%">类型</th>
                          <th class="info" width="15%">联系人名称</th>
                          <th class="info" width="18%">联系人电话</th>
                          <th class="info" width="18%">联系人手机</th>
                          <th class="info">操作</th>
                      </tr>
                      </thead>
                      <tbody>
                      
                      </tbody>
                 </table>
			</div>
          <div class="ul_list dnone clear" id="salesResult">
          <div align="left" id="countdnone" >销售供应商：确认参加的供应商为<span class="f26 red" id="count">0</span>人，确认不参加的有<span class="notJoin">0</span>人</div>
           <!-- Begin Content -->
                  <table id="table" class="table table-bordered table-condensed">
                      <thead>
                      <tr>
                          <th class="info w50">序号</th>
                          <th class="info" width="15%">供应商名称</th>
                          <th class="info" width="15%">类型</th>
                          <th class="info" width="15%">联系人名称</th>
                          <th class="info" width="18%">联系人电话</th>
                          <th class="info" width="18%">联系人手机</th>
                          <th class="info">操作</th>
                      </tr>
                      </thead>
                      <tbody>
                      
                      </tbody>
                 </table>
			</div>
		</div>
</div>
<!-- 地区树 -->
<div id="areaContent" class="levelTypeContent"
     style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
    <ul  id="treeArea" class="ztree" style="margin-top:0;"></ul>
</div>
<!-- 类别树 -->
<div id=supplierTypeContent class="levelTypeContent"
     style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
    <ul id="treeSupplierType" class="ztree" style="margin-top:0;"></ul>
</div>

<!-- 工程等级树 -->
<div id="projectLevelContent" class="levelTypeContent"
     style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
    <ul id="projectLevelTree" class="ztree" style="margin-top:0;"></ul>
</div>
<!-- 服务等级树 -->
<div id="serviceLevelContent" class="levelTypeContent"
     style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
    <ul id="serviceLevelTree" class="ztree" style="margin-top:0;"></ul>
</div>
<!-- 生产等级树 -->
<div id="productLevelContent" class="levelTypeContent"
     style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
    <ul id="productLevelTree" class="ztree" style="margin-top:0;"></ul>
</div>
<!-- 销售等级树 -->
<div id="salesLevelContent" class="levelTypeContent"
     style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
    <ul id="salesLevelTree" class="ztree" style="margin-top:0;"></ul>
</div>
<!-- 工程资质树 -->
<div id="quaContent" class="levelTypeContent"
     style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
    <ul id="quaTree" class="ztree" style="margin-top:0;"></ul>
</div>

</body>
<script type="text/javascript">
        /*分页  */
        $(function() {
            laypage({
                cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
                pages: "${list.pages}", //总页数
                skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
                skip: true, //是否开启跳页
                total: "${list.total}",
                startRow: "${list.startRow}",
                endRow: "${list.endRow}",
                groups: "${list.pages}" >= 5 ? 5 : "${list.pages}", //连续显示分页数
                curr: function() { //通过url获取当前页，也可以同上（pages）方式获取

                    return "${list.pageNum}";
                }(),
                jump: function(e, first) { //触发分页后的回调
                    if(!first) { //一定要加此判断，否则初始时会无限刷新
                        //location.href = '${pageContext.request.contextPath}/SupplierExtracts/Extraction.html?id=${projectId}&page='+e.curr;
                    }
                }
            });

            var typeclassId = "${typeclassId}";

            if(typeclassId != null && typeclassId != "") {
                $(".star_red").each(function(){
                    for(var i = 2; i < 4; i++){
                        $("#red"+i).addClass("dnone");
                    }
                });
            } else {
                for(var i = 0; i < 4; i++){
                    $("#red"+i).addClass("dnone");
                }
            }


            //获取包id
            var projectId = "${projectId}";
            if(projectId != null && projectId != '') {
                $("#projectName").attr("readonly", true);
                $("#projectNumber").attr("readonly", true);
                $("#packageName").attr("readonly", true);
                $("#tenderTimeId").attr("disabled", true);
            } else {
                $("#projectName").attr("readonly", false);
                $("#projectNumber").attr("readonly", false);
                $("#packageName").attr("readonly", false);
                $("#tenderTimeId").attr("disabled", false);
            }
            var index = 0 ;
            var divObj = $(".p0" + index);
            $(divObj).removeClass("hide");
            $("#package").removeClass("shrink");
            $("#package").addClass("spread");
            //对于采购机构人员进行判断
            var isCurment = '${isCurment}';
            if(isCurment == '1'){
                $('.isCurment_div').removeClass('hide');
                $('.isCurment_div').addClass('block');
            }else if(isCurment == '0'){
                $('.isCurment_div').removeClass('block');
                $('.isCurment_div').addClass('hide');
            }

        });


        function ycDiv(obj, index) {
            if ($(obj).hasClass("shrink") && !$(obj).hasClass("spread")) {
                $(obj).removeClass("shrink");
                $(obj).addClass("spread");
            } else {
                if ($(obj).hasClass("spread") && !$(obj).hasClass("shrink")) {
                    $(obj).removeClass("spread");
                    $(obj).addClass("shrink");
                }
            }

            var divObj = new Array();
            divObj = $(".p0" + index);
            for (var i =0; i < divObj.length; i++) {
                if ($(divObj[i]).hasClass("p0"+index) && $(divObj[i]).hasClass("hide")) {
                    $(divObj[i]).removeClass("hide");
                } else {
                    if ($(divObj[i]).hasClass("p0"+index)) {
                        $(divObj[i]).addClass("hide");
                    };
                };
            };
        }
        $(function() {
            $("#statusBid").find("option[value='${statusBid}']").attr("selected", true);
            var index=0;
            var divObj = $(".p0" + index);
            $(divObj).removeClass("hide");
            $("#package").removeClass("shrink");
            $("#package").addClass("spread");
        });

        /* function add(type) {

            var packageId=$("#packageId").val();
            var typeclassId = "${typeclassId}";
            $.ajax({
                cache: true,
                type: "POST",
                dataType: "json",
                url: '${pageContext.request.contextPath}/SupplierExtracts/validateAddExtraction.do?type='+type,
                data: $('#form').serialize(), // 你的formid
                async: false,
                success: function(data) {
                    $("#projectNameError").text("");
                    $("#projectNumberError").text("");
                    $("#packageNameError").text("");
                    $("#dSupervise").text("");
                    $("#extractionSitesError").text("");
                    var map = data;
                    $("#projectNameError").text(map.projectNameError);
                    $("#projectNumberError").text(map.projectNumberError);
                    $("#packageNameError").text(map.packageNameError);
                    $("#dSupervise").text(map.supervise);
                    $("#extractionSitesError").text(map.extractionSitesError);
                    var projectId = map.projectId;
                    if(map.isSuccess=="false"){
                        layer.alert(map.msg, {shade: 0.01});
                        return false;
                    }
                    if(map.status != null && map.status != 0) {
                        layer.confirm('上次抽取未完成，是否继续上次抽取？', {
                            btn: ['确定','取消'], shade:0.01 //按钮
                        }, function(){
                            window.location.href = '${pageContext.request.contextPath}/SupplierExtracts/addExtractions.html?projectId=' + projectId + '&&typeclassId=${typeclassId}&&packageId='+packageId;
                        }, function(){
                            layer.closeAll();
                        });
                    }
                    if(map.error == null && map.error != 'error'){
                        if(map.sccuess == "SCCUESS") {
                            window.location.href = '${pageContext.request.contextPath}/SupplierExtracts/addExtractions.html?projectId=' + projectId + '&&typeclassId=${typeclassId}&&packageId='+packageId;
                        }else if(map.packageError != null && map.packageError != ''){
                            layer.alert("请选择包", {
                                shade: 0.01
                            });
                        }else if(typeclassId != null && typeclassId != ''){
                            $("#projectId").val(projectId);
                            $("#pId").val(projectId);
                            if(map.type != null && map.type == '1'){
                                var iframeWin;
                                layer.open({
                                    type: 2,
                                    title: "选择包",
                                    shadeClose: true,
                                    shade: 0.01,
                                    offset: '20px',
                                    move: false,
                                    area: ['50%', '50%'],
                                    content: '${pageContext.request.contextPath}/SupplierExtracts/showPackage.do?projectId='+projectId,
                                    success: function(layero, index) {
                                        iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                                    },
                                    btn: ['保存', '关闭'],
                                    yes: function() {
                                        iframeWin.add();

                                    },
                                    btn2: function() {
                                        layer.closeAll();
                                    }
                                });

                            }
                        }
                    }
                }
            });

        } */
        /**抽取页面*/
        function opens(){

            window.location.href = '${pageContext.request.contextPath}/SupplierExtracts/addExtractions.html?projectId=' + pachageId + '&&typeclassId=${typeclassId}&&packageId='+packageId;
        }

       /*  //选择监督人员
        function supervise() {
            //  iframe层
            var iframeWin;
            layer.open({
                type: 2,
                title: "选择监督人员",
                shadeClose: true,
                shade: 0.01,
                offset: '20px',
                move: false,
                area: ['90%', '50%'],
                content: '${pageContext.request.contextPath}/SupplierExtracts/showSupervise.do?projectId=${projectId}',
                success: function(layero, index) {
                    iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                },
                btn: ['保存', '关闭'],
                yes: function() {
                    iframeWin.add();

                },
                btn2: function() {
                    layer.closeAll();
                }
            });
        } */
    </script>

    <script type="text/javascript">
        function showPackageType() {
            var setting = {
                check: {
                    enable: true,
                    chkboxType: {
                        "Y": "",
                        "N": ""
                    }
                },
                view: {
                    dblClickExpand: false
                },
                data: {
                    simpleData: {
                        enable: true,
                        idKey: "id",
                        pIdKey: "parentId"
                    }
                },
                callback: {
                    beforeClick: beforeClick,
                    onCheck: onCheck
                }
            };
            var projectId =$("#projectId").val();
            $.ajax({
                type: "GET",
                async: false,
                url: "${pageContext.request.contextPath}/SupplierExtracts/getpackage.do?projectId="+projectId,
                dataType: "json",
                success: function(zNodes) {
                    tree = $.fn.zTree.init($("#treePackageType"), setting, zNodes);
                    tree.expandAll(true); //全部展开
                }
            });
            var cityObj = $("#packageName");
            var cityOffset = $("#packageName").offset();
            $("#packageContent").css({
                left: cityOffset.left + "px",
                top: cityOffset.top + cityObj.outerHeight() + "px"
            }).slideDown("fast");
            $("body").bind("mousedown", onBodyDownPackageType);
        }

        function onBodyDownPackageType(event) {
            if(!(event.target.id == "menuBtn" || $(event.target).parents("#packageContent").length > 0)) {
                hidePackageType();
            }
        }

        function hidePackageType() {
            $("#packageContent").fadeOut("fast");
            $("body").unbind("mousedown", onBodyDownPackageType);

        }

        function beforeClick(treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("treePackageType");
            zTree.checkNode(treeNode, !treeNode.checked, null, true);
            return false;
        }

        function onCheck(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("treePackageType"),
                nodes = zTree.getCheckedNodes(true),
                v = "";
            var rid = "";
            for(var i = 0, l = nodes.length; i < l; i++) {
                v += nodes[i].name + ",";
                rid += nodes[i].id + ",";
            }
            if(v.length > 0) v = v.substring(0, v.length - 1);
            if(rid.length > 0) rid = rid.substring(0, rid.length - 1);
            var cityObj = $("#packageName");
            cityObj.attr("value", v);
            cityObj.attr("title", v);
            $("#packageId").attr("value",rid);

        }
    </script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/ses/sms/supplierExtract.js"></script>
</html>