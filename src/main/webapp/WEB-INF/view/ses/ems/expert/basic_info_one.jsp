<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<%@ taglib uri="/tld/upload" prefix="up"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="/WEB-INF/view/front.jsp"></jsp:include>
<script src="${pageContext.request.contextPath}/js/ems/expert/validate_expert_basic_info.js"></script>
<script src="${pageContext.request.contextPath}/js/ems/expert/validate_regester.js"></script>
<%
//表单标示
String tokenValue= new Date().getTime()+UUID.randomUUID().toString()+""; 
session.setAttribute("tokenSession", tokenValue);
%>
<script type="text/javascript">
		function func123(){
			var parentId = $("#addr").val();
			$.ajax({
				url:"${pageContext.request.contextPath}/area/find_by_parent_id.do",
				data:{"id":parentId},
				dataType:"json",
				async:false,
				success:function(response){
					$("#add").empty();
					$("#add").append("<option value=''>-请选择-</option>");
					$.each(response,function(i,result){
						$("#add").append("<option value='"+result.id+"'>"+result.name+"</option>");
					});
				}
			});
		}
		var parentId ;
		var addressId="${expert.address}";
		window.onload=function(){
			//地区回显和数据显示
			$.ajax({
				url : "${pageContext.request.contextPath}/area/find_by_id.do",
				data:{"id":addressId},
				async:false,
				dataType:"json",
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
				async:false,
				dataType:"json",
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
			//validateBase();
			//func();
		}
	function submitformExpert(){
		$.ajax({
			url:"${pageContext.request.contextPath}/expert/zanCun.do",
			data:$("#formExpert").serialize(),
			type: "post",
			async: true,
			success:function(result){
				$("#id").val(result.id);
				layer.msg("已暂存",{offset: ['300px', '750px']});
			 }
		});
	}
	//无提示暂存
	function submitForm2(){
		updateStepNumber("two");
		$.ajax({
			url:"${pageContext.request.contextPath}/expert/zanCun.do",
			data:$("#formExpert").serialize(),
			type: "post",
			async: true,
			success:function(result){
				$("#id").val(result.id);
				window.location.href="${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
			}
		});
	}
	//无提示暂存
	function submitForm22(){
		updateStepNumber("two");
		$.ajax({
			url:"${pageContext.request.contextPath}/expert/zanCun.do",
			data:$("#formExpert").serialize(),
			type: "post",
			async: true,
			success:function(result){
				$("#id").val(result.id);
				window.location.href="${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
			}
		});
	}
	//无提示暂存
	function submitForm3(){
		updateStepNumber("three");
		$.ajax({
			url:"${pageContext.request.contextPath}/expert/zanCun.do",
			data:$("#formExpert").serialize(),
			type: "post",
			async: true,
			success:function(result){
				$("#id").val(result.id);
				window.location.href="${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
			}
		});
	}
	//无提示暂存
	function submitForm4(){
		updateStepNumber("four");
		$.ajax({
			url:"${pageContext.request.contextPath}/expert/zanCun.do",
			data:$("#formExpert").serialize(),
			type: "post",
			async: true,
			success:function(result){
				$("#id").val(result.id);
				window.location.href="${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
			}
		});
	}
	//无提示暂存
	function submitForm5(){
		updateStepNumber("five");
		$.ajax({
			url:"${pageContext.request.contextPath}/expert/zanCun.do",
			data:$("#formExpert").serialize(),
			type: "post",
			async: true,
			success:function(result){
				$("#id").val(result.id);
				window.location.href="${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
			}
		});
	}
		/** 专家完善注册信息页面 */
	function supplierRegist() {
		if (!validateformExpert()){
			return;
		} else {
			//暂存无提示
			submitForm2();
		}
	}
	/** 专家完善注册信息页面 */
	function supplierRegist2() {
		if (!validateformExpert()){
			return;
		} else {
			//暂存无提示
			submitForm22();
		}
	}
	/** 专家完善注册信息页面 */
	function supplierRegist3() {
		if (!validateformExpert()){
			return;
		} else {
			//暂存无提示
			submitForm3();
		}
	}
	/** 专家完善注册信息页面 */
	function supplierRegist4() {
		if (!validateformExpert()){
			return;
		} else {
			//暂存无提示
			submitForm4();
		}
	}
	/** 专家完善注册信息页面 */
	function supplierRegist5() {
		if (!validateformExpert()){
			return;
		} else {
			//暂存无提示
			submitForm5();
		}
	}

	//回显基本信息到表中
	function editTable(){
		var name = $("#relName").val();
		$("#tName").text(name);
		//性别
		var obj = document.getElementById("gender"); //selectid

		var index = obj.selectedIndex; // 选中索引

		var text = obj.options[index].text;
		  $("#tSex").text(text);
		var birthday = $("#birthday").val();
		$("#tBirthday").text(birthday);
		//政治面貌
		var obj3 = document.getElementById("politicsStatus"); //selectid

		var index3 = obj3.selectedIndex; // 选中索引

		var tFace = obj3.options[index3].text;
		$("#tFace").text(tFace);
		var professTechTitles = $("#professTechTitles").val();
		$("#tHey").text(professTechTitles);
		var idNumber = $("#idNumber").val();
		$("#tNumber").text(idNumber);
		//最高学历
		var obj2 = document.getElementById("hightEducation"); //selectid

		var index2 = obj2.selectedIndex; // 选中索引

		var text2 = obj2.options[index2].text;
		
		$("#tHight").text(text2);
		var degree = $("#degree").val();
		$("#tWei").text(degree);
		var mobile = $("#mobile").val();
		$("#tMobile").text(mobile);
		var telephone = $("#telephone").val();
		$("#tTelephone").text(telephone);
		var workUnit = $("#workUnit").val();
		$("#tWorkUnit").text(workUnit);
		var graduateSchool = $("#graduateSchool").val();
		$("#tGraduateSchool").text(graduateSchool);
		var unitAddress = $("#unitAddress").val();
		$("#tUnitAddress").text(unitAddress);
		var postCode = $("#postCode").val();
		$("#tPostCode").text(postCode);
		var timeStartWork = $("#timeStartWork").val();
		$("#tTimeStartWork").text(timeStartWork);
		//父地区
		var add= document.getElementById("addr"); //selectid

		var addiIdex = add.selectedIndex; // 选中索引

		var addValue1 =add.options[addiIdex].text;
		//子地区
		var add2= document.getElementById("add"); //selectid

		var addiIdex2 = add2.selectedIndex; // 选中索引

		var addValue2 =add2.options[addiIdex2].text;
		
		$("#Taddress").text(addValue1+","+addValue2);
	}
	
	// 点击下一步事件
	function fun(){
		supplierRegist(); 
		editTable();
	}
	// 点击2事件
	function fun2(){
		supplierRegist2(); 
		editTable();
	}
	// 点击3事件
	function fun3(){
		supplierRegist3(); 
		editTable();
	}
	// 点击4事件
	function fun4(){
		supplierRegist4(); 
		editTable();
	}
	// 点击5事件
	function fun5(){
		supplierRegist5(); 
		editTable();
	}
	
	//校验基本信息 不能为空的字段
	function validateformExpert(){
		var relName = $("#relName").val();
		if(!relName){
			layer.msg("请输入姓名 !",{offset: ['300px', '750px']});
			return false;
		}
		var gender = $("#gender").val();
		if(!gender){
			layer.msg("请选择性别 !",{offset: ['300px', '750px']});
			return false;
		}
		var expertsFrom = $("#expertsFrom").val();
		if(!expertsFrom){
			layer.msg("请选择来源 !",{offset: ['300px', '750px']});
			return false;
		}
		
		var nation = $("#nation").val();
		if(!nation){
			layer.msg("请填写民族 !",{offset: ['300px', '750px']});
			return false;
		}
		var graduateSchool = $("#graduateSchool").val();
		if(!graduateSchool){
			layer.msg("请填写毕业院校及专业 !",{offset: ['300px', '750px']});
			return false;
		}
		var hightEducation = $("#hightEducation").val();
		if(!hightEducation){
			layer.msg("请选择最高学历!",{offset: ['300px', '750px']});
			return false;
		}
		
		var major = $("#major").val();
		if(!major){
			layer.msg("请填写从事专业!",{offset: ['300px', '750px']});
			return false;
		}
		
		var unitAddress = $("#unitAddress").val();
		if(!unitAddress){
			layer.msg("请填写单位地址!",{offset: ['300px', '750px']});
			return false;
		}
		var jobExperiences = $("#jobExperiences").val();
		if(!jobExperiences){
			layer.msg("请填写主要工作经历!",{offset: ['300px', '750px']});
			return false;
		}
		if(jobExperiences != "" && jobExperiences.length > 999){
			layer.msg("工作经历不能超过999字!",{offset: ['300px', '750px']});
			return false;
		}
		var telephone = $("#telephone").val();
		if(!telephone){
			layer.msg("请填写固定电话!",{offset: ['300px', '750px']});
			return false;
		}
		
		if(telephone != ""){
			var reg = /^(\d{3,4}-)?\d{7,8}$/
			if(!reg.test(telephone)){
				layer.msg("固定电话格式有误!",{offset: ['300px', '750px']});
				return false;
			}
		}
		
		var mobile = $("#mobile").val();
		if(!mobile){
			layer.msg("请填写手机号!",{offset: ['300px', '750px']});
			return false;
		}
		
		var healthState = $("#healthState").val();
		if(!healthState){
			layer.msg("请填写健康状态!",{offset: ['300px', '750px']});
			return false;
		}
		
		var idType = $("#idType").val();
		if(!idType){
			layer.msg("请选择证件类型 !",{offset: ['300px', '750px']});
			return false;
		}
		var idNumber = $("#idNumber").val();
		if(!idNumber){
			layer.msg("请填写证件号码 !",{offset: ['300px', '750px']});
			return false;
		}
		var email = $("#email").val();
		if(!email){
			layer.msg("请填写个人邮箱!",{offset: ['300px', '750px']});
			return false;
		}
		var emailReg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/
		if(email != "" && !emailReg.test(email)){
			layer.msg("个人邮箱格式有误 !",{offset: ['300px', '750px']});
			return false;
		}
		var postCode = $("#postCode").val();
		if(idNumber != "" && isNaN(postCode)){
			layer.msg("邮编格式只能输入数字 !",{offset: ['300px', '750px']});
			return false;
		}
		var fax = $("#fax").val();
		var faxReg = /^(\d{3,4}-)?\d{7,8}$/
		if(fax != "" && !faxReg.test(fax)){
			layer.msg("传真电话格式有误 !",{offset: ['300px', '750px']});
			return false;
		}
		if(idType=="EDA3B3274C2E4182BD3C968931772DD6" && idNumber != ""){
			var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(X|x)$)/;
			if(!reg.test(idNumber)){
				layer.msg("身份证号码格式有误 !",{offset: ['300px', '750px']});
				return false;
			}
		}
		if(idType=="EDA3B3274C2E4182BD3C968931772DD6" && idNumber != ""){
			var isok = 0;
			$.ajax({
				url:'${pageContext.request.contextPath}/expert/validateIdNumber.do',
				type:"post",
				async:false,
				data:{"idNumber":idNumber,"expertId":$("#id").val()},
				success:function(obj){
					if(obj=='1'){
						layer.msg("该身份证号已被占用!",{offset: ['300px', '750px']});
						isok = 1;
					}
				}
			});
		}
		if (isok == 1) {
			return false;
		}
		var id_areaSelect = $("#add").val();
		if(!id_areaSelect){
			layer.msg("请选择区域 !",{offset: ['300px', '750px']});
			return false;
		}
		var sysId = $("#sysId").val();
		var flag;
		$.ajax({
			url:"${pageContext.request.contextPath}/expert/findAttachment.do",
			data:{"sysId":sysId},
			cache: false,
	        async: false,
			success:function(data){
				if(data.length<5){
					layer.msg("还有附件未上传!",{offset: ['300px', '750px']});
					flag=false;
				}else{
					flag=true;
				}
			},
			dataType:"json"
		});
		return flag;
	}
	function tab3(typeId, depId){
		if(typeId != ""){
			if(depId != ""){
				fun3();
			}
		}
	}
	function tab2(typeId){
		if(typeId != ""){
			fun2();
		}
	}
	function tab4(typeId, depId){
		if(typeId != ""){
			if(depId != ""){
				fun4();
			}
		}
	}
	function tab5(typeId, depId){
		if(typeId != ""){
			if(depId != ""){
				fun5();
			}
		}
	}
	function updateStepNumber(stepNumber){
		$.ajax({
			url:"${pageContext.request.contextPath}/expert/updateStepNumber.do",
			data:{"expertId":$("#id").val(),"stepNumber":stepNumber},
			async:false,
		});
	}
</script>
</head>
<body>
	<jsp:include page="/index_head.jsp"></jsp:include>
 <form id="formExpert" action="${pageContext.request.contextPath}/expert/add.html" method="post">
  <input type="hidden" name="userId" value="${user.id}"/>
  <input type="hidden" id="purchaseDepId" value="${expert.purchaseDepId}"/>
  <input type="hidden" name="id" id="id" value="${expert.id}"/>
  <input type="hidden" name="zancun" id="zancun" value=""/>
  <input type="hidden" name="sysId" id="sysId" value="${sysId}"/>
  <input type="hidden" value="${errorMap.realName}" id="error1">
  <input type="hidden" value="${errorMap.nation}" id="error2">
  <input type="hidden" value="${errorMap.gender}" id="error3">
  <input type="hidden" value="${errorMap.idType}" id="error4">
  <input type="hidden" value="${errorMap.idNumber}" id="error5">
  <input type="hidden" value="${errorMap.address}" id="error6">
  <input type="hidden" value="${errorMap.hightEducation}" id="error7">
  <input type="hidden" value="${errorMap.graduateSchool}" id="error8">
  <input type="hidden" value="${errorMap.major}" id="error9">
  <input type="hidden" value="${errorMap.expertsFrom}" id="error10">
  <input type="hidden" value="${errorMap.unitAddress}" id="error11">
  <input type="hidden" value="${errorMap.telephone}" id="error12">
  <input type="hidden" value="${errorMap.mobile}" id="error13">
  <input type="hidden" value="${errorMap.healthState}" id="error14">
  <input type="hidden" value="${errorMap.mobile2}" id="error15">
  <input type="hidden" value="${errorMap.idNumber2}" id="error16">
  <input type="hidden" id="categoryId" name="categoryId" value=""/>
  <input type="hidden"  name="token2" value="<%=tokenValue%>"/>
    <div id="reg_box_id_3" class="container clear margin-top-30 job-content">
	  <h2 class="padding-20 mt40">
	    <span id="sp1" class="new_step current fl" onclick='tab1()'><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
	    <span id="sp2" class="new_step <c:if test="${expert.expertsTypeId != null}">current</c:if> fl" onclick="tab2('${expert.expertsTypeId}')"><i class="">2</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span>
	    <span id="sp3" class="new_step <c:if test="${expert.purchaseDepId != null}">current</c:if> fl" onclick="tab3('${expert.expertsTypeId}','${expert.purchaseDepId}')"><i class="">3</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
	    <span id="sp4" class="new_step <c:if test="${att eq '1'}">current</c:if> fl" onclick="tab4('${expert.expertsTypeId}','${expert.purchaseDepId}')"><i class="">4</i><div class="line"></div> <span class="step_desc_01">下载申请表</span> </span> 
	    <span id="sp5" class="new_step <c:if test="${att eq '1'}">current</c:if> fl" onclick="tab5('${expert.expertsTypeId}','${expert.purchaseDepId}')"><i class="">5</i> <span class="step_desc_02">上传申请表</span> </span> 
	    <div class="clear"></div>
	  </h2>
	    <div class="container container_box">
		  <h2 class="count_flow"><i>1</i>专家基本信息</h2>
			<ul class="ul_list">
				<li class="col-md-3 col-sm-6 col-xs-12">
				    <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>专家来源</span>
					<div class="select_common col-md-12 col-xs-12 col-sm-12 p0">
						<select  name="expertsFrom" id="expertsFrom">
						<option selected="selected" value="">-请选择-</option>
						<c:forEach items="${lyTypeList}" var="ly" varStatus="vs"> 
					     	<option <c:if test="${expert.expertsFrom eq ly.id}">selected="selected"</c:if> value="${ly.id}">${ly.name}</option>
						</c:forEach>
					   </select>
					</div>
				</li>
				<li class="col-md-3 col-sm-6 col-xs-12 pl15">
				    <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 专家姓名</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
						<input id="relName" name="relName" value="${expert.relName}"   type="text"/>
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
                <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 出生日期</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
 					  <input    readonly="readonly" value="<fmt:formatDate type='date' value='${expert.birthday}' dateStyle='default' pattern='yyyy-MM-dd'/>" name="birthday" id="birthday" type="text" onclick='WdatePicker()'/>
					  <span class="add-on">i</span>
					</div>
				</li> 
				<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>手机</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
					<input  maxlength="15" value="${user.mobile}" readonly="readonly" name="mobile" id="mobile" type="text"/>
					<span class="add-on">i</span>
					    <span class="input-tip">手机号码暂不支持修改</span>
					</div>
				</li>
				<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>个人邮箱</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
						<input  maxlength="30" value="${expert.email}" name="email" id="email" type="text"/>
						<span class="add-on">i</span>
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
					<span class="input-tip">如: XXX - XXXXXXXX</span>
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
                <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 证件类型</span>
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
				<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>民族</span>
                    <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
                    <input  maxlength="10" value="${expert.nation}"  name="nation" id="nation" type="text"/>
                    <span class="add-on">i</span>
					    <span class="input-tip">请填写名族,如:汉族</span>
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
                <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>最高学历</span>
                    <div class="select_common col-md-12 col-xs-12 col-sm-12 p0">
                     <select  name="hightEducation" id="hightEducation" >
                        <option selected="selected" value="">-请选择-</option>
                        <c:forEach items="${xlList}" var="xl" varStatus="vs">
                        <option <c:if test="${expert.hightEducation eq xl.id}">selected="selected"</c:if> value="${xl.id}">${xl.name}</option>
                        </c:forEach>
                      </select>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>毕业院校及专业</span>
                    <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
                      <input  maxlength="40" value="${expert.graduateSchool}"  name="graduateSchool" id="graduateSchool" type="text"/>
                   	  <span class="add-on">i</span>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 学位</span>
                    <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
                    <input  maxlength="10" value="${expert.degree}"  name="degree" id="degree" type="text"/>
                    <span class="add-on">i</span>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>从事专业</span>
                    <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
                    <input  maxlength="20" value="${expert.major}"  name="major" id="major" type="text"/>
                    <span class="add-on">i</span>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">专家技术职称/职业资格</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
					<input  maxlength="20" value="${expert.professTechTitles}"  name="professTechTitles" id="professTechTitles" type="text"/>
					<span class="add-on">i</span>
					</div>
				</li>
				<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 取得技术职称时间</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
					<input  value="<fmt:formatDate type='date' value='${expert.makeTechDate}' dateStyle='default' pattern='yyyy-MM-dd'/>"  readonly="readonly" name="makeTechDate" id="makeTechDate" type="text" onclick='WdatePicker()'/>
					<span class="add-on">i</span>
					</div>
				</li>
				<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 参加工作时间</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
					<input readonly="readonly" value="<fmt:formatDate value='${expert.timeToWork}' pattern='yyyy-MM'/>" name="timeToWork"  type="text" onfocus="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM'})"/>
					<span class="add-on">i</span>
					</div>
				</li>
				<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 从事专业起始年度</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
					 <input  value="<fmt:formatDate type='date' value='${expert.timeStartWork}' dateStyle='default' pattern='yyyy-MM-dd'/>"  readonly="readonly" name="timeStartWork" id="timeStartWork" type="text" onclick='WdatePicker()'/>
					<span class="add-on">i</span>
					</div>
				</li>
				<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 现任职务</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
					<input  maxlength="10" value="${expert.atDuty}"  name="atDuty" id="appendedInput" type="text"/>
					<span class="add-on">i</span>
					</div>
				</li>
				<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">工作单位</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
					<input  maxlength="40" value="${expert.workUnit}"  name="workUnit" id="workUnit" type="text"/>
					<span class="add-on">i</span>
					</div>
				</li>
				<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>单位地址</span>
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
				<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>健康状态</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
					<input  maxlength="10" value="${expert.healthState}"  name="healthState" id="healthState" type="text"/>
					<span class="add-on">i</span>
					</div>
				</li>
			</ul>
			
			<!-- 主要工作经历-->
			  <div class="padding-top-10 clear">
			    <h2 class="count_flow"><i>2</i>主要工作经历</h2>
			    <ul class="ul_list">
				<li>  
				  <textarea rows="10" name="jobExperiences" id="jobExperiences" cols="168" style="height: 150px; resize: none;" placeholder="包括时间、工作单位、职务、工作内容等">${expert.jobExperiences}</textarea>
				</li>
			    </ul>
			   </div>
			
			  <!-- 附件信息-->
			  <div class="padding-top-10 clear">
			    <h2 class="count_flow"><i>3</i>上传附件</h2>
			    <ul class="ul_list">
				 <table class="table table-bordered">
				  <tbody>
				  	<tr>
				  	   <td class="bggrey" width="15%"><i class="red">*</i>身份证</td>
				  	   <td>
				  	      <u:upload id="expert1" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_IDNUMBER_TYPEID}" auto="true"/>
				          <u:show showId="show1" groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_IDNUMBER_TYPEID}"/>
				  	   </td>
				  	   <td class="bggrey" width="15%"><i class="red">*</i>学历证书</td>
				  	   <td>
				  	      <u:upload id="expert2" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_ACADEMIC_TYPEID}" auto="true"/>
				          <u:show showId="show2" groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_ACADEMIC_TYPEID}"/>
				  	   </td>
				  	</tr>
				  	<tr>
				  	   <td class="bggrey"><i class="red">*</i>职称证书</td>
				  	   <td>
				  	      <u:upload id="expert3" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_TITLE_TYPEID}" auto="true"/>
				          <u:show  showId="show3" groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_TITLE_TYPEID}"/>
				  	   </td>
				  	   <td class="bggrey"><i class="red">*</i>学位证书</td>
				  	   <td>
				  	      <u:upload id="expert4" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_DEGREE_TYPEID}" auto="true"/>
				          <u:show showId="show4" groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_DEGREE_TYPEID}"/>
				  	   </td>
				  	</tr>
				  	<tr>
				  	   <td class="bggrey"><i class="red">*</i>个人照片</td>
				  	   <td colspan="3">
				  	      <u:upload id="expert5" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_PHOTO_TYPEID}" auto="true"/>
				          <u:show showId="show5" groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_PHOTO_TYPEID}"/>
				  	   </td>
				  	</tr>
				  	</tbody>
				  </table>
			    </ul>
			   </div>
			   
				<div class="tc mt20 clear col-md-12 col-sm-12 col-xs-12">
			        <button class="btn" onclick='submitformExpert()'  type="button">暂存</button>
					<button class="btn" id="nextBind"  type="button" onclick='fun()' >下一步</button>
				</div>
				</div>
					 </div>
	</form>
	<jsp:include page="/index_bottom.jsp"></jsp:include>
</body>
</html>
