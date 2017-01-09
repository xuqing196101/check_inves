<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
<%@ include file="/WEB-INF/view/common.jsp" %>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//表单标示
	String tokenValue= new Date().getTime()+UUID.randomUUID().toString()+"";
	session.setAttribute("tokenSession", tokenValue);
%>
<title>专家个人信息</title>
<script type="text/javascript">
	function fun(){
		var parentId = $("#addr").val();
		$.ajax({
			url : "${pageContext.request.contextPath}/area/find_by_parent_id.do",
			data:{"id":parentId},
			success:function(obj){
				$("#add").empty();
				$("#add").append("<option value=''>-请选择-</option>");
				$.each(obj,function(i,result){
					
					$("#add").append("<option value='"+result.id+"'>"+result.name+"</option>");
				});
			}
		});
	}
	$(function(){
		//回显已选产品
		var id="${expert.id}";
		var parentId ;
		var addressId="${expert.address}";
		//地区回显和数据显示
		$.ajax({
			url : "${pageContext.request.contextPath}/area/find_by_id.do",
			data:{"id":addressId},
			success:function(obj){
				$.each(obj,function(i,result){
					if(addressId == result.id){
						parentId = result.parentId;
						$("#add").append("<option selected='true' value='"+result.id+"'>"+result.name+"</option>");
					}else{
						$("#add").append("<option value='"+result.id+"'>"+result.name+"</option>");
					}
				});
			}
		}); 
		//地区
		$.ajax({
			url : "${pageContext.request.contextPath}/area/listByOne.do",
			success:function(obj){
				$.each(obj,function(i,result){
					if(parentId == result.id){
						$("#addr").append("<option selected='true' value='"+result.id+"'>"+result.name+"</option>");
					}else{ 
						$("#addr").append("<option value='"+result.id+"'>"+result.name+"</option>");
					}
				});
			}
		});
	});
	function submitForm(){
		$("#formId").submit();
	}
</script>
</head>
<body>
<!--面包屑导航开始-->
 <div class="margin-top-10 breadcrumbs ">
    <div class="container">
      <ul class="breadcrumb margin-left-0">
        <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">业务管理</a></li><li><a href="javascript:void(0)">评标专家信息修改</a></li>
      </ul>
    </div>
    </div>
   <!-- 项目戳开始 -->
   <div class="container">
     <div class="tab-content">
 <form action="${pageContext.request.contextPath}/expert/edit.html"  method="post" id="formId"  class="registerform"> 
   <input type="hidden" name="categoryId" id="categoryId">
   <input type="hidden"  name="token2" value="<%=tokenValue%>">
   <input type="hidden" name="id" value="${expert.id}">
   <input type="hidden" name="isPass" id="isPass"/>
     <div class="tab-content">
	   <div class="container container_box">
		  <h2 class="count_flow"><i>1</i>专家个人信息</h2>
			<ul class="ul_list">
				<li class="col-md-3 col-sm-6 col-xs-12 pl15">
				    <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 专家姓名</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
						<input id="relName" name="relName" value="${expert.relName}" type="text"/>
					    <span class="add-on">i</span>
					</div>
				</li>
				<li class="col-md-3 col-sm-6 col-xs-12">
					<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 性别</span>
                    <div class="select_common col-md-12 col-xs-12 col-sm-12 p0">
                       <select  name="gender" id="gender">
                          <option selected="selected" value="">-请选择-</option>
                          <c:forEach items="${sexList}" var="sex" varStatus="vs">
                            <option <c:if test="${expert.gender eq sex.id}">selected="selected"</c:if> value="${sex.id}">${sex.name}</option>
                          </c:forEach>
                        </select>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 出生日期</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
 					  <input readonly="readonly" value="<fmt:formatDate type='date' value='${expert.birthday}' dateStyle='default' pattern='yyyy-MM-dd'/>" name="birthday" id="birthday" type="text" onclick='WdatePicker()'/>
					    <span class="add-on">i</span>
					</div>
				</li> 
				<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>省</span>
                   <div class="select_common col-md-12 col-xs-12 col-sm-12 p0">
                    <select id="addr" onchange="func123()">
                           <option value="">-请选择-</option>
                    </select>
                   </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>市</span>
                    <div class="select_common col-md-12 col-xs-12 col-sm-12 p0">
                     <select  name="address" id="add">
                            <option value="">-请选择-</option>
                     </select>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">政治面貌</span>
                    <div class="select_common col-md-12 col-xs-12 col-sm-12 p0">
                        <select  name="politicsStatus" id="politicsStatus">
                        <option selected="selected" value="">-请选择-</option>
                        <c:forEach items="${zzList}" var="zz" varStatus="vs">
                          <option <c:if test="${expert.politicsStatus eq zz.id}">selected="selected"</c:if> value="${zz.id}">${zz.name}</option>
                        </c:forEach>
                       </select>
                    </div>
                </li>
				<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>民族</span>
                    <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
	                    <input  maxlength="10" value="${expert.nation}"  name="nation" id="nation" type="text"/>
						<span class="add-on">i</span>
						<span class="input-tip">请填写名族,如:汉族</span>
                    </div>
                </li>
                <c:if test="${expert.expertsFrom eq 'LOCAL'}">
                <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 缴纳社会保险证明</span>
                    <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
                    	<input  maxlength="30" value="${expert.coverNote}"  name="coverNote" id="coverNote" type="text"/>
						<span class="add-on">i</span>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 社保证明附件</span>
                    <div class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0">
                    <u:upload id="expert5" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7,expert8" multiple="true" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_PHOTO_TYPEID}" auto="true"/>
				    <u:show showId="show5" groups="show1,show2,show3,show4,show5,show6,show7,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_PHOTO_TYPEID}"/>
                    </div>
                </li>
                </c:if>
                <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 居民身份证号码</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
						<input  maxlength="30" value="${expert.idCardNumber}"  name="idCardNumber" id="idCardNumber" type="text"/>
						<span class="add-on">i</span>
  					</div>
				</li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 居民身份证附件</span>
                    <div class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0">
                    <u:upload id="expert8" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7,expert8"  multiple="true" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_IDCARDNUMBER_TYPEID}" auto="true"/>
				    <u:show showId="show8" groups="show1,show2,show3,show4,show5,show6,show7,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_IDCARDNUMBER_TYPEID}"/>
                    </div>
                </li>
                <c:if test="${expert.expertsFrom eq 'ARMY'}">
                <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 军队人员身份证件类型</span>
                    <div class="select_common col-md-12 col-xs-12 col-sm-12 p0">
                    <select  name="idType" id="idType">
                    <option selected="selected" value="">-请选择-</option>
                    <c:forEach items="${idTypeList}" var="idType" varStatus="vs">
                      <option <c:if test="${expert.idType eq idType.id}">selected="selected"</c:if> value="${idType.id}">${idType.name}</option>
                    </c:forEach>
                   </select>
                    </div>
                </li>
				<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>证件号码</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
						<input  maxlength="30" value="${expert.idNumber}"  name="idNumber" id="idNumber" type="text"/>
						<span class="add-on">i</span>
  					</div>
				</li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 军队人员身份证件附件</span>
                    <div class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0">
                    <u:upload id="expert1" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7,expert8" multiple="true" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_IDNUMBER_TYPEID}" auto="true"/>
				    <u:show showId="show1" groups="show1,show2,show3,show4,show5,show6,show7,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_IDNUMBER_TYPEID}"/>
                    </div>
                </li>
				</c:if>
				<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>手机</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
						<input  maxlength="15" value="${expert.mobile}" readonly="readonly" name="mobile" id="mobile" type="text"/>
						<span class="add-on">i</span>
				    	<span class="input-tip">手机号码暂不支持修改</span>	
					</div>
				</li>
				<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 固定电话</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
						<input  maxlength="15" value="${expert.telephone}"  name="telephone" id="telephone" type="text"/>
						<span class="add-on">i</span>
					    <span class="input-tip">如: XXX - XXXXXXX</span>	
					</div>
				</li>
				<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 传真电话</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
						<input value="${expert.fax}"  name="fax" id="fax" type="text"/>
						<span class="add-on">i</span>
					    <span class="input-tip">如: XXX - XXXXXXX</span>	
					</div>
				</li> 
				<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>个人邮箱</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
						<input  maxlength="30" value="${expert.email}" name="email" id="email" type="text"/>
						<span class="add-on">i</span>
					</div>
				</li>
				<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>健康状态</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
						<input  maxlength="10" value="${expert.healthState}"  name="healthState" id="healthState" type="text"/>
						<span class="add-on">i</span>
					</div>
				</li>
			</ul>
			<!-- 专家学历信息 -->
			<h2 class="count_flow"><i>2</i>专家学历信息</h2>
			<ul class="ul_list">
                <li class="col-md-3 col-sm-6 col-xs-12 pl10"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>毕业院校及专业</span>
                    <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
                      <input  maxlength="40" value="${expert.graduateSchool}"  name="graduateSchool" id="graduateSchool" type="text"/>
						<span class="add-on">i</span>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 毕业证书附件</span>
                    <div class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0">
                    <u:upload id="expert2" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7,expert8" multiple="true" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_ACADEMIC_TYPEID}" auto="true"/>
				    <u:show showId="show2" groups="show1,show2,show3,show4,show5,show6,show7,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_ACADEMIC_TYPEID}"/>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>最高学历</span>
                    <div class="select_common col-md-12 col-xs-12 col-sm-12 p0">
                     <select  name="hightEducation" id="hightEducation">
                        <option selected="selected" value="">-请选择-</option>
                        <c:forEach items="${xlList}" var="xl" varStatus="vs">
                        <option <c:if test="${expert.hightEducation eq xl.id}">selected="selected"</c:if> value="${xl.id}">${xl.name}</option>
                        </c:forEach>
                      </select>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 最高学位</span>
                    <div class="select_common col-md-12 col-xs-12 col-sm-12 p0">
                        <select  name="degree" id="degree">
                        <option selected="selected" value="">-请选择-</option>
                        <c:forEach items="${xwList}" var="xw" varStatus="vs">
                          <option <c:if test="${expert.degree eq xw.id}">selected="selected"</c:if> value="${xw.id}">${xw.name}</option>
                        </c:forEach>
                       </select>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 学位证书附件</span>
                    <div class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0">
                    <u:upload id="expert4" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7,expert8" multiple="true" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_DEGREE_TYPEID}" auto="true"/>
				    <u:show showId="show4" groups="show1,show2,show3,show4,show5,show6,show7,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_DEGREE_TYPEID}"/>
                    </div>
                </li>
			</ul>
			<!-- 专家专业信息 -->
			<h2 class="count_flow"><i>3</i>专家专业信息</h2>
			<ul class="ul_list">
				<li class="col-md-3 col-sm-6 col-xs-12 pl10"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 所在单位</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
					  	<input  maxlength="40" value="${expert.workUnit}"  name="workUnit" id="workUnit" type="text"/>
						<span class="add-on">i</span>
					</div>
				</li>
				<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 单位地址</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0" >
					 	<input  maxlength="40" value="${expert.unitAddress}"  name="unitAddress" id="unitAddress" type="text"/>
						<span class="add-on">i</span>
					</div>
				</li>
				<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 单位邮编</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
						<input  maxlength="6" value="${expert.postCode}"  name="postCode" id="postCode" type="text"/>
						<span class="add-on">i</span>
					</div>
				</li>
				<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 现任职务</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
					<input  maxlength="10" value="${expert.atDuty}"  name="atDuty" id="appendedInput" type="text"/>
					<span class="add-on">i</span>
					</div>
				</li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>从事专业</span>
                    <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
                    <input  maxlength="20" value="${expert.major}"  name="major" id="major" type="text"/>
					<span class="add-on">i</span>
                    </div>
                </li>
				<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 从事专业起始年度</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
					 <input value="<fmt:formatDate type='date' value='${expert.timeStartWork}' dateStyle='default' pattern='yyyy-MM'/>"  readonly="readonly" name="timeStartWork" id="timeStartWork" type="text" onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM'})"/>
					<span class="add-on">i</span>
					</div>
				</li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 专家技术职称/执业资格</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
					<input maxlength="20" value="${expert.professTechTitles}"  name="professTechTitles" id="professTechTitles" type="text"/>
					<span class="add-on">i</span>
					</div>
				</li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                    <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 技术职称/执业资格证书附件</span>
                    <div class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0">
                    <u:upload id="expert3" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7,expert8" multiple="true" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_TITLE_TYPEID}" auto="true"/>
				    <u:show  showId="show3" groups="show1,show2,show3,show4,show5,show6,show7,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_TITLE_TYPEID}"/>
                    </div>
                </li>
				<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 取得技术职称时间</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
					<input value="<fmt:formatDate type='date' value='${expert.makeTechDate}' dateStyle='default' pattern='yyyy-MM'/>"  readonly="readonly" name="makeTechDate" id="makeTechDate" type="text" onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM'})"/>
					<span class="add-on">i</span>
					</div>
				</li>
				<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 参加工作时间</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
					<input readonly="readonly" value="<fmt:formatDate value='${expert.timeToWork}' pattern='yyyy-MM'/>" name="timeToWork"  type="text" onmouseover="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM'})"/>
					<span class="add-on">i</span>
					</div>
				</li>
			</ul>
  <!-- 附件信息-->
  <div class="padding-top-10 clear">
    <h2 class="count_flow"><i>4</i>专家申请表、承诺书</h2>
    <ul class="ul_list">
      <table class="table table-bordered">
	  	 <tr>
	  	    <td class="info"><i class="red">*</i> 专家申请表：</td>
	   	    <td colspan="3">
	   	       <up:upload id="expert6"  groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7,expert8" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_APPLICATION_TYPEID}" auto="true"/>
			   <up:show showId="show6"  groups="show1,show2,show3,show4,show5,show6,show7,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_APPLICATION_TYPEID}"/>
	   	    </td>
	   	    <td  class="info"><i class="red">*</i> 专家承诺书：</td>
	   	    <td colspan="5">
	   	       <up:upload id="expert7" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7,expert8" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_CONTRACT_TYPEID}" auto="true"/>
			   <up:show showId="show7"  groups="show1,show2,show3,show4,show5,show6,show7,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_CONTRACT_TYPEID}"/>
	   	    </td>
	  	 </tr>
	  	 <tr>
	   	    
	   	 </tr>
	    </table>
	  </ul>
    </div>
  <div class="padding-top-10 clear">
    <h2 class="count_flow"><i>4</i>采购机构</h2>
    <ul class="ul_list">
      <table class="table table-bordered">
        <tbody>
	  	  <tr>
			 <td class="bggrey">采购机构名称：</td><td>${purchase.name}</td>
			 <td class="bggrey">采购机构联系人：</td><td>${purchase.princinpal}</td>
		  </tr>
		  <tr>
		     <td class="bggrey">采购机构地址：</td><td>${purchase.detailAddr}</td>
	         <td class="bggrey">联系电话：</td><td>${purchase.mobile}</td>
		  </tr>
	   </tbody>
	 </table>
	</ul>
    
  </div>
  <div class="col-md-12 tc">
      <input class="btn btn-windows edit" type="button" onclick="submitForm();" value="修改">
      <a class="btn btn-windows back"  onclick="location.href='javascript:history.go(-1);'">返回</a>
    </div>
</div></div></form>
    </div>
  </div>
</div>
</body>
</html>