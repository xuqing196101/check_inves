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
                    <a href="javascript:void(0);" <%-- onclick="jumppage('${pageContext.request.contextPath}/SupplierExtracts_new/projectList.html?typeclassId=${typeclassId}')" --%>>供应商抽取</a>
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
    <form id="projectForm" action="<%=request.getContextPath() %>/SupplierExtracts_new/saveProjectInfo.do" method="post" >
    <input type="submit" value="提交"> <input type="button" value="存储项目人员信息" onclick="submitInfo()"> <input onclick="showEndButton()" type="button" value="抽取完成">
        <!-- 打开类型 -->
      <%--   <input type="hidden" value="${typeclassId}" name="typeclassId" /> --%>
        <!-- 项目id  -->
        <input type="hidden" id="projectId" value="${projectInfo.projectId }" name="projectId">
        <!-- 记录id -->
        <input type="hidden" value="${projectInfo.id}" name="id">
         <h2 class="count_flow"><i>1</i>项目信息</h2>
         <ul class="ul_list border0 m0">
             <li class="col-md-3 col-sm-4 col-xs-12 pl15">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span> 项目名称:</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                     <input id="projectName" name="projectName"  value="${projectInfo.projectName}" ${flag?"readonly":"" } type="text">
                     <span class="add-on">i</span>
                     <div class="cue" id="projectNameError"></div>
                 </div>
             </li>
             <li class="col-md-3 col-sm-4 col-xs-12">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span> 项目编号:</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                     <input id="projectNumber" name="projectCode" value="${projectInfo.projectCode}" ${flag?"readonly":"" } type="text" onchange="checkSole(this)" >
                     <span class="add-on">i</span>
                     <div class="cue" id="projectCodeError"></div>
                 </div>
             </li>
             <li class="col-md-3 col-sm-4 col-xs-12">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>采购方式:</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
           			<c:if test="${projectInfo.purchaseType ==null }">
                   	<select name="purchaseType" class="col-md-12 col-sm-12 col-xs-6 p0" ${flag?"readonly":"" }>
                        <option value="EF33590F956F4450A43C1B510EBA7923" >询价</option>
                        <option value="209C109291F241D88188521A7F8FA308" >邀请招标</option>
                   	 	<option value="3CF3C643AE0A4499ADB15473106A7B80" >竞争性谈判</option>
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
                     <input id="packageName" name="packageName" value="${projectInfo.projectCode}" ${flag?"readonly":"" } type="text" >
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
                     		<option value="">选择省</option>
                            <c:forEach items="${province }" var="pro"  >
                       		 	<option value="${pro.id }">${pro.name }</option>
                            </c:forEach>
                        </select>
                        <select name="sellAddress" class="col-md-6 col-sm-6 col-xs-6 p0" id="sellAddress">
                        	<option value="">选择地区</option>
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
                     <select id="projectType" name="projectType" class="col-md-12 col-sm-12 col-xs-6 p0" onchange="loadSupplierType()">
                          <option value="GOODS" ${projectInfo.projectType == 'GOODS' ? 'selected' : '' }>物资</option>
                          <option value="PROJECT" ${projectInfo.projectType == 'PROJECT' ? 'selected' : '' }>工程</option>
                          <option value="SERVICE" ${projectInfo.projectType == 'SERVICE' ? 'selected' : '' }>服务</option>
                     </select>
                     <span class="add-on">i</span>
                     <div class="cue" id="projectTypeError"></div>
                 </div>
             </li>
             <li class="col-md-3 col-sm-4 col-xs-12">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span id="xmss"></span>项目实施地区</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                    <select class="col-md-6 col-sm-6 col-xs-6 p0" id="constructionPro" name="constructionPro" onchange="selectArea(this);">
                    		<option value="">选择省</option>
                            <c:forEach items="${province }" var="pro">
                                    <option value="${pro.id }">${pro.name }</option>
                            </c:forEach>
                        </select>
                        <select name="constructionAddr" class="col-md-6 col-sm-6 col-xs-6 p0" id="constructionAddr">
                        	<option value="">选择地区</option>
                           <%-- <c:forEach items="${address }" var="add">
                                <option value="${add.id }">${add.name }</option>
                       		 </c:forEach> --%>
                        </select>
                     <span class="add-on">i</span>
                     <div class="cue" id="constructionProError"></div>
                 </div>
             </li>
             <li class="col-md-3 col-sm-4 col-xs-12">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>抽取地址</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                     <input name="extractionSites" value="" type="text" >
                     <span class="add-on">i</span>
                     <div class="cue" id="extractionSitesError"></div>
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
                     <input name="contactNum" value=""  type="text" >
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
		 <form action="<%=request.getContextPath() %>/extractUser/addPerson.do" onsubmit="return false" id="extractUser">
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
		  <form action="<%=request.getContextPath() %>/supervise/addPerson.do" id="supervise"  onsubmit="return false" >
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
    <!-- <input type="button" onclick="submitCondition()" value="提交条件"> -->
        <input id="sunCount" type="hidden">
        <!--        项目id -->
         <input type="hidden" id="projectId" value="${projectInfo.projectId }" name="projectId">
        <!-- 记录id -->
        <input type="hidden" name="recordId" id="recordId" value="${projectInfo.id}">
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
        <!--         省 -->
        <input type="hidden" name="province" id="province"/>
        <input type="hidden" name="addressId" id="addressId">
          <h2 class="count_flow"><i>3</i>抽取条件</h2>
          <ul class="ul_list m0" style="background-color: #fbfbfb">
              <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                  <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><div
                          class="star_red">*</div>所在地区：</span>
                  <div class="input-append input_group col-sm-12 col-xs-12 p0">
                  	<input class="input_group " readonly name="areaName" id="area" onclick="showTree();">
                  	 <span class="add-on">i</span>
                  	  <div class="cue" id="areaNameError"></div>
                  </div>
              </li>
               <li class="col-md-3 col-sm-6 col-xs-12  dnone">
                  <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>限制地区理由：</span>
                  <div class="input-append input_group col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="addressReason" id="areaReson" value=""
                             type="text">
                      <span class="add-on">i</span>
                      <div class="cue" id="areaError" ></div>
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
             <%--  <li class="col-md-3 col-sm-6 col-xs-12">
                  <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="star_red">*</span>抽取总数量：</span>
                  <div class="input-append input_group col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="supplierCount" id="supplierCount" value="${sumCount}"
                             type="text">
                      <span class="add-on">i</span>
                      <div class="cue" id="countSupplier"></div>
                  </div>
                  
                  
              </li> --%>
              
    <li id="extractNumber" class="col-md-3 col-sm-6 col-xs-12">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>供应商数量：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <input class="title col-md-12" id='projectExtractNum' name="projectExtractNum" maxlength="11" type="text">
          <span class="add-on">i</span>
          <div class="cue" id=projectExtractNumError>${loginPwdError}</div>
        </div>
    </li>
	<li class="clear"></li>
    <li class="col-xs-12 borderTS1 mt10 pt10 dnone projectCount">
      
      <div class="col-xs-2 p0">
        <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">&nbsp;</span>
        <button class="btn" type="button">当前满足<span id="projectCount">0</span>人</button>
      </div>
      
      <div class="col-xs-10">
        <div class="col-xs-3">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">企业性质：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <select name="projectBusinessNature" class="w100p" onchange="selectLikeSupplier()">
          <option value="">不限</option>
          <c:forEach items="${businessNature }" var="bu">
          <option value="${bu.id }">${bu.name }</option>
          </c:forEach>
          </select>
          <div class="cue">${loginPwdError}</div>
          </div>
        </div>
        <div class="col-xs-3">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">保密要求：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <select name="projectIsHavingConCert" class="w100p" onchange="selectLikeSupplier()">
          <option value="">不限</option>
          <option value="0">无</option>
          <option value="1">有</option>
          </select>
          <div class="cue">${loginPwdError}</div>
          </div>
        </div>
        <div class="col-xs-3">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">境外分支机构：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <select name="projectOverseasBranch" class="w100p" onchange="selectLikeSupplier()">
		  <option value="">不限</option>
          <option value="1">有</option>
          <option value="0">无</option>
          </select>
          <div class="cue">${loginPwdError}</div>
          </div>
        </div>
        <div class="col-xs-3">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">产品类目：</span>
          <!--  满足多个条件 -->
          <input type="hidden" name="projectIsMulticondition" class="isSatisfy">
          <input type="hidden" name="projectCategoryIds" id="projectCategoryIds" class="categoryId">
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <input class="input_group " readonly  typeCode="PROJECT" value="${listCon.conTypes[0].categoryName}" onclick="opens(this);" type="text">
          <span class="add-on">i</span>
          <div class="cue" id="dCategoryName"></div>
          </div>
        </div>
        <div class="col-xs-3">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"></span>工程资质：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <input type="hidden" name="quaId" id="quaId" >
          <input type="text"  id="quaName" treeHome="quaContent"
         	 value="${listCon.supplierLevel == null? '全部资质':listCon.supplierLevel}"  onclick="showQua(this);"/>
          <span class="add-on">i</span>
          <div class="cue" id="dCount"></div>
          </div>
        </div>
        <div class="col-xs-3">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">资质等级：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <input type="hidden" name="projectLevel" >
          <input type="text" readonly  id="projectLevel" treeHome="projectLevelContent"
          value="${listCon.supplierLevel == null? '':listCon.supplierLevel}" onclick="showLevel(this);"/>
          <span class="add-on">i</span>
          <div class="cue" id="dCount"></div>
          </div>
        </div>
      </div>
    
    </li>
        
    <li class="clear"></li>
    <li class="col-xs-12 borderTS1 mt10 pt10 dnone serviceCount">
      
      <div class="col-xs-2 p0">
        <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">&nbsp;</span>
        <div class="col-xs-2 p0"><button class="btn" type="button">当前满足<span id="serviceCount">0</span>人</button></div>
      </div>
      <div class="col-xs-10">
     <%--  <div class="col-xs-3">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>供应商数量：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <input class="title col-md-12" id='serviceExtractNum' name="serviceExtractNum" 
          maxlength="11" type="text">
          <span class="add-on">i</span>
          <div class="cue" id="serviceExtractNumError">${loginPwdError}</div>
          </div>
        </div> --%>
        <div class="col-xs-3">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">企业性质：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <select name="serviceBusinessNature" class="w100p" onchange="selectLikeSupplier()">
          <option value="">不限</option>
          <c:forEach items="${businessNature }" var="bu">
          <option value="${bu.id }">${bu.name }</option>
          </c:forEach>
          </select>
          <div class="cue">${loginPwdError}</div>
          </div>
        </div>
        <div class="col-xs-3">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">保密要求：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <select name="serviceIsHavingConCert" class="w100p" onchange="selectLikeSupplier()">
          <option value="">不限</option>
          <option value="0">无</option>
          <option value="1">有</option>
          </select>
          <div class="cue">${loginPwdError}</div>
          </div>
        </div>
        <div class="col-xs-3">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">境外分支机构：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <select name="serviceOverseasBranch" class="w100p" onchange="selectLikeSupplier()">
          <option value="">不限</option>
          <option value="1">有</option>
          <option value="0">无</option>
          </select>
          <div class="cue">${loginPwdError}</div>
          </div>
        </div>
        <div class="col-xs-3">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">产品类目：</span>
          <!--  满足多个条件 -->
          <input type="hidden" name="serviceIsMulticondition" class="isSatisfy">
          <input type="hidden" name="serviceCategoryIds" id="serviceCategoryIds" class="categoryId">
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <input class="input_group " readonly  typeCode="SERVICE"
          value="${listCon.conTypes[0].categoryName}" onclick="opens(this);" type="text">
          <span class="add-on">i</span>
          <div class="cue" id="dCategoryName"></div>
          </div>
        </div>
        <div class="col-xs-3">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"></span>资质信息：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <input type="hidden" name="serviceQuaId" id="serviceQuaId" >
          <input type="text"  id="serviceQuaName" treeHome="serviceQuaContent"
         	 value="${listCon.supplierLevel == null? '全部资质':listCon.supplierLevel}"  onclick="showQua(this);"/>
          <span class="add-on">i</span>
          <div class="cue" id="dCount"></div>
          </div>
        </div>
        <div class="col-xs-3">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">供应商等级：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <input type="hidden" name="serviceLevel" >
          <input type="text" readonly  id="serviceLevel" treeHome="serviceLevelContent"
          value="${listCon.supplierLevel == null? '':listCon.supplierLevel}" onclick="showLevel(this);"/>
          <span class="add-on">i</span>
          <div class="cue" id="dCount"></div>
          </div>
        </div>
      </div>
      
    </li>
    
    <li class="clear"></li>
    <li class="col-xs-12 borderTS1 mt10 pt10 dnone productCount">
      
      <div class="col-xs-2 p0">
        <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">&nbsp;</span>
        <button class="btn" type="button">当前满足<span id="productCount">0</span>人</button>
      </div>
      
      <div class="col-xs-10">
      <%--  <div class="col-xs-3">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>供应商数量：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <input class="title col-md-12" id='productExtractNum' name="productExtractNum"
          maxlength="11" type="text">
          <span class="add-on">i</span>
          <div class="cue" id="productExtractNumError">${loginPwdError}</div>
          </div>
        </div> --%>
        <div class="col-xs-3">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">企业性质：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
            <select name="productBusinessNature" class="w100p" onchange="selectLikeSupplier()">
              <option value="">不限</option>
              <c:forEach items="${businessNature }" var="bu">
              <option value="${bu.id }">${bu.name }</option>
              </c:forEach>
            </select>
            <div class="cue">${loginPwdError}</div>
          </div>
        </div>
        <div class="col-xs-3">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">保密要求：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <select name="productIsHavingConCert" class="w100p" onchange="selectLikeSupplier()">
          <option value="">不限</option>
          <option value="0">无</option>
          <option value="1">有</option>
          </select>
          <div class="cue">${loginPwdError}</div>
          </div>
        </div>
        <div class="col-xs-3">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">境外分支机构：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <select name="productOverseasBranch" class="w100p" onchange="selectLikeSupplier()">
          <option value="">不限</option>
          <option value="1">有</option>
          <option value="0">无</option>
          </select>
          <div class="cue">${loginPwdError}</div>
          </div>
        </div>
       
        <div class="col-xs-3">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">产品类目：</span>
          <!--  满足多个条件 -->
          <input type="hidden" name="productIsMulticondition" class="isSatisfy">
          <input type="hidden" name="productCategoryIds" id="productCategoryIds" class="categoryId">
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <input class="input_group " readonly  typeCode="PRODUCT"
          value="${listCon.conTypes[0].categoryName}" onclick="opens(this);" type="text">
          <span class="add-on">i</span>
          <div class="cue" id="dCategoryName"></div>
          </div>
        </div>
        <div class="col-xs-3">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"></span>资质信息：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <input type="hidden" name="productQuaId" id="productQuaId" >
          <input type="text"  id="productQuaName" treeHome="productQuaContent"
         	 value="${listCon.supplierLevel == null? '全部资质':listCon.supplierLevel}"  onclick="showQua(this);"/>
          <span class="add-on">i</span>
          <div class="cue" id="dCount"></div>
          </div>
        </div>
        <div class="col-xs-3">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">供应商等级：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <input type="hidden" name="productLevel" >
          <input type="text" readonly  id="productLevel" treeHome="productLevelContent"
          value="${listCon.supplierLevel == null? '':listCon.supplierLevel}" onclick="showLevel(this);"/>
          <span class="add-on">i</span>
          <div class="cue" id="dCount"></div>
          </div>
        </div>
      <div>
        
    </li>
    
    <li class="clear"></li>
    <li class="col-xs-12 borderTS1 mt10 pt10 dnone goodsCount">
      
      <div class="col-xs-2 p0">
        <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">&nbsp;</span>
        <button class="btn" type="button">当前满足<span id="goodsCount">0</span>人</button>
      </div>
      
      <div class="col-xs-10">
      <%--  <div class="col-xs-3">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>供应商数量：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <input class="title col-md-12" id='goodsExtractNum' name="goodsExtractNum"
          maxlength="11" type="text">
          <span class="add-on">i</span>
          <div class="cue" id="goodsExtractNumError">${loginPwdError}</div>
          </div>
        </div> --%>
        <div class="col-xs-3">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">企业性质：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
            <select name="goodsBusinessNature" class="w100p" onchange="selectLikeSupplier()">
              <option value="">不限</option>
              <c:forEach items="${businessNature }" var="bu">
              <option value="${bu.id }">${bu.name }</option>
              </c:forEach>
            </select>
            <div class="cue">${loginPwdError}</div>
          </div>
        </div>
        <div class="col-xs-3">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">保密要求：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <select name="goodsIsHavingConCert" class="w100p" onchange="selectLikeSupplier()">
          <option value="">不限</option>
          <option value="0">无</option>
          <option value="1">有</option>
          </select>
          <div class="cue">${loginPwdError}</div>
          </div>
        </div>
        <div class="col-xs-3">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">境外分支机构：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <select name="goodsOverseasBranch" class="w100p" onchange="selectLikeSupplier()">
          <option value="">不限</option>
          <option value="1">有</option>
          <option value="0">无</option>
          </select>
          <div class="cue">${loginPwdError}</div>
          </div>
        </div>
       
        <div class="col-xs-3">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">产品类目：</span>
          <!--  满足多个条件 -->
          <input type="hidden" name="goodsIsMulticondition" class="isSatisfy">
          <input type="hidden" name="goodsCategoryIds" id="goodsCategoryIds" class="categoryId">
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <input class="input_group " readonly  typeCode="GOODS"
          value="${listCon.conTypes[0].categoryName}" onclick="opens(this);" type="text">
          <span class="add-on">i</span>
          <div class="cue" id="dCategoryName"></div>
          </div>
        </div>
        <div class="col-xs-3">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"></span>资质信息：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <input type="hidden" name="goodsQuaId" id="goodsQuaId" >
          <input type="text"  id="goodsQuaName" treeHome="goodsQuaContent"
         	 value="${listCon.supplierLevel == null? '全部资质':listCon.supplierLevel}"  onclick="showQua(this);"/>
          <span class="add-on">i</span>
          <div class="cue" id="dCount"></div>
          </div>
        </div>
        <div class="col-xs-3">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">供应商等级：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <input type="hidden" name="goodsLevel" >
          <input type="text" readonly  id="goodsLevel" treeHome="goodsLevelContent"
          value="${listCon.supplierLevel == null? '':listCon.supplierLevel}" onclick="showLevel(this);"/>
          <span class="add-on">i</span>
          <div class="cue" id="dCount"></div>
          </div>
        </div>
      <div>
        
    </li>
    
    
    <li class="clear"></li>
    <li class="col-xs-12 borderTS1 mt10 pt10 dnone salesCount">
      
      <div class="col-xs-2 p0">
        <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">&nbsp;</span>
        <div class="col-xs-2 p0"><button class="btn" type="button">当前满足<span  id="salesCount">0</span>人</button></div>
      </div>
      
      <div class="col-xs-10">
      <%--  <div class="col-xs-3">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>供应商数量：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <input class="title col-md-12" id='salesExtractNum' name="salesExtractNum" 
          maxlength="11" type="text">
          <span class="add-on">i</span>
          <div class="cue" id="salesExtractNumError">${loginPwdError}</div>
          </div>
        </div> --%>
        <div class="col-xs-3">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">企业性质：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <select name="salesBusinessNature" class="w100p" onchange="selectLikeSupplier()">
          <option value="">不限</option>
          <c:forEach items="${businessNature }" var="bu">
          <option value="${bu.id }">${bu.name }</option>
          </c:forEach>
          </select>
          <div class="cue">${loginPwdError}</div>
          </div>
        </div>
        <div class="col-xs-3">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">保密要求：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <select name="salesIsHavingConCert" class="w100p" onchange="selectLikeSupplier()">
          <option value="">不限</option>
          <option value="0">无</option>
          <option value="1">有</option>
          </select>
          <div class="cue">${loginPwdError}</div>
          </div>
        </div>
        <div class="col-xs-3">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">境外分支机构：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <select name="salesOverseasBranch" class="w100p" onchange="selectLikeSupplier()">
          <option value="">不限</option>
          <option value="1">有</option>
          <option value="0">无</option>
          </select>
          <div class="cue">${loginPwdError}</div>
          </div>
        </div>
       
        <div class="col-xs-3">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">产品类目：</span>
          <!--  满足多个条件 -->
          <input type="hidden" name="salesIsMulticondition" class="isSatisfy">
          <input type="hidden" name="salesCategoryIds" id="salesCategoryIds" class="categoryId">
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <input class="input_group " readonly  typeCode="SALES"
          value="${listCon.conTypes[0].categoryName}" onclick="opens(this);" type="text">
          <span class="add-on">i</span>
          <div class="cue" id="dCategoryName"></div>
          </div>
        </div>
        
        <div class="col-xs-3">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"></span>资质信息：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <input type="hidden" name="salesQuaId" id="salesQuaId" >
          <input type="text"  id="salesQuaName" treeHome="salesQuaContent"
         	 value="${listCon.supplierLevel == null? '全部资质':listCon.supplierLevel}"  onclick="showQua(this);"/>
          <span class="add-on">i</span>
          <div class="cue" id="dCount"></div>
          </div>
        </div>
        <div class="col-xs-3">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">供应商等级：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <input type="hidden" name="salesLevel" >
          <input type="text" readonly  id="salesLevel" treeHome="salesLevelContent"
          value="${listCon.supplierLevel == null? '':listCon.supplierLevel}" onclick="showLevel(this);"/>
          <span class="add-on">i</span>
          <div class="cue" id="dCount"></div>
          </div>
        </div>
      </div>
      
    </li>
        </ul>
          
          <div class="clear"></div>
	         <div class="col-xs-12 tc mt20">
	           <button class="btn bu" onclick="extractVerify();" type="button">人工抽取</button>
	           <button class="btn bu" type="button">自动抽取</button>
	           <button class="btn bu"  type="reset">重置</button>
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
                         <th class="info">供应商名称</th>
                         <th class="info w120">类型</th>
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
          <div class="ul_list dnone clear" id="serviceResult">
          <div align="left" id="countdnone" >服务供应商：确认参加的供应商为<span class="f26 red" id="count">0</span>人，确认不参加的有<span class="notJoin">0</span>人</div>
           <!-- Begin Content -->
                  <table id="table" class="table table-bordered table-condensed">
                      <thead>
                      <tr>
                        <th class="info w50">序号</th>
                        <th class="info">供应商名称</th>
                        <th class="info w120">类型</th>
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
          <div class="ul_list dnone clear" id="productResult">
          <div align="left" id="countdnone" >生产供应商：确认参加的供应商为<span class="f26 red" id="count">0</span>人，确认不参加的有<span class="notJoin">0</span>人</div>
           <!-- Begin Content -->
                  <table id="table" class="table table-bordered table-condensed">
                      <thead>
                      <tr>
                        <th class="info w50">序号</th>
                        <th class="info">供应商名称</th>
                        <th class="info w120">类型</th>
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
          <div class="ul_list dnone clear" id="salesResult">
          <div align="left" id="countdnone" >销售供应商：确认参加的供应商为<span class="f26 red" id="count">0</span>人，确认不参加的有<span class="notJoin">0</span>人</div>
           <!-- Begin Content -->
                  <table id="table" class="table table-bordered table-condensed">
                      <thead>
                      <tr>
                        <th class="info w50">序号</th>
                        <th class="info">供应商名称</th>
                        <th class="info w120">类型</th>
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
			<div class="ul_list dnone clear" id="goodsResult">
          <div align="left" id="countdnone" >物资供应商：确认参加的供应商为<span class="f26 red" id="count">0</span>人，确认不参加的有<span class="notJoin">0</span>人</div>
           <!-- Begin Content -->
                  <table id="table" class="table table-bordered table-condensed">
                      <thead>
                      <tr>
                        <th class="info w50">序号</th>
                        <th class="info">供应商名称</th>
                        <th class="info w120">类型</th>
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
			<did class="col-xs-12 tc mt20 dnone" id="end"> <button class="center btn"  onclick="alterEndInfo(this)">结束</button> </did>
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
<!-- 非工程资质树资质树 -->
<div id="goodsContent" class="levelTypeContent" 
     style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
    <ul id="Tree" class="ztree" style="margin-top:0;"></ul>
</div>
<!-- 生产等级树 -->
<div id="goodsLevelContent" class="levelTypeContent"
     style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
    <ul id="goodsLevelTree" class="ztree" style="margin-top:0;"></ul>
</div>
</body>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/ses/sms/supplierExtract.js"></script>
<script type="text/javascript">
	function submitInfo(){

	//存储项目信息
    /* 	$.ajax({
    		type: "POST",
    		url: $("#projectForm").attr('action'),
    		data:$("#projectForm").serialize(),
    		dataType: "json",
    		success: function (msg) {
    			for ( var k in msg) {
					console.log(k);
					$("#"+k+"Error").html(msg[k]);
				}
    		}
    	}); */
    	//存储人员信息
    	$.ajax({
    		type: "POST",
    		url: $("#supervise").attr('action'),
    		data:  $("#supervise").serialize(),
    		dataType: "json",
    		async:false,
    		success: function (msg) {
    			if(null !=msg){
    				flag++;
    				for ( var k in msg) {
    					if("All"!=k){
    						$("#supervise").find("[name='"+k+"']").parent().append("<span class='red'>"+msg[k]+"</span>");
    					}else{
    						$("#eError").html(msg[k]);
    					}
					}
    			}else{
    				$("#eError").empty();
    			}
    		}
    	});
    	
    	
    	$.ajax({
    		type: "POST",
    		url: $("#extractUser").attr('action'),
    		data:  $("#extractUser").serialize(),
    		dataType: "json",
    		success: function (msg) {
    			if(null !=msg){
    				for ( var k in msg) {
						$("#extractUser").find("[name='"+k+"']").parent().append("<span class='red'>"+msg[k]+"</span>");
					}
    			}
    		}
    	});
	}

	function submitCondition(){
		$.ajax({
    		type: "POST",
    		url: globalPath+'/SupplierCondition_new/selectLikeSupplier.do',
    		data: $('#form1').serialize() ,
    		dataType: "json",
    		async:false,
    		success: function (msg) {
    			if(null != msg.list){
    				var su = msg.list;
    				if(null !=su.PROJECT){
    					projects =su.PROJECT;
    				}
    				if(null !=su.SERVICE){
    					services = su.SERVICE;
    				}
    				if(null !=su.PRODUCT){
    					products = su.PRODUCT;
    				}
    				if(null !=su.SALES){
    					sales = su.SALES;
    				}
    			}
	    		if(null !=msg){
	    			$("#"+msg.error).html("不能为空");
	    			return false;
	    		}	
	    		if(null !=msg){
	    			$("#"+msg.error).html("不能为空");
	    			return false;
	    		}	
    		}
    	});
	}



	/* $("#quaName").change(function(){
		var name = $(this).val();
		if(""!=name && null!=name){
			$.ajax({
	    		type: "POST",
	    		url: globalPath+"/qualification/list.do",
	    		data:  {name:name,type:4},
	    		dataType: "json",
	    		success: function (msg) {
	    			if(null !=msg){
	    				//console.log(msg);
	    				loadQuaList(msg.obj.list);
	    			}
	    		}
	    	});
		}else{
			$("#quaId").val("");
			$("[name='projectLevel']").val("");
			$("#projectLevel").val("全部等级");
			$("#quaName").val("全部资质");
		}
	}); */

	function showEndButton(){
		$("#end").removeClass("dnone");
	}

	function alterEndInfo(obj){
		layer.alert("是否需要发送短信至确认参加供应商");
		var index = layer.alert("完成抽取,打印记录表",function(){
			window.location.href = globalPath+"/SupplierExtracts_new/printRecord.html?id="+$("[name='recordId']").val();
			$(obj).prop("disabled",true);
			//window.location.href = globalPath+"/SupplierExtracts_new/projectList.html";
			layer.close(index);
			// 
		});
	}
	
	
	
</script>
</html>