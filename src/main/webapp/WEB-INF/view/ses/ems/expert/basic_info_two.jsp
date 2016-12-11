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
	
	$(function(){
		var typeIds = "${expert.expertsTypeId}";
		var ids = typeIds.split(",");
		//回显已选产品
		var checklist1 = document.getElementsByName("chkItem_1");
		for(var i=0;i<checklist1.length;i++){
			var vals=checklist1[i].value;
			for (var j = 0; j < ids.length; j++) {
				if (ids[j] == vals) {
					checklist1[i].checked = true;
				}
			}	
		}
		var checklist2 = document.getElementsByName("chkItem_2");
		for(var i=0;i<checklist2.length;i++){
			var vals=checklist2[i].value;
			for (var j = 0; j < ids.length; j++) {
				if (ids[j] == vals) {
					checklist2[i].checked = true;
				}
			}	
		}
	}); 
	
	function submitformExpert(){
		getChildren();
		$.ajax({
			url:"${pageContext.request.contextPath}/expert/zanCun.do",
			data:$("#formExpert").serialize(),
			type: "post",
			async: true,
			success:function(result){
				$("#id").val(result.id);
				layer.msg("已暂存");
			}
		});
	}
	//无提示暂存
	function submitForm2(){
		var count = "three";
		var checklist1 = document.getElementsByName ("chkItem_1");
		for (var i = 0; i < checklist1.length; i++) {
			if (checklist1[i].checked == true) {
				count = "six";
			}
		}
		updateStepNumber(count);
		getChildren();
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
		getChildren();
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
		getChildren();
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
	//获取选中子节点id
	function getChildren(){
		var ids=new Array();
		var checklist1 = document.getElementsByName ("chkItem_1");
		for(var i=0;i<checklist1.length;i++){
	 		var vals=checklist1[i].value;
	 		if(checklist1[i].checked){
	 			ids.push(vals);
	 		}
		}
		var checklist2 = document.getElementsByName ("chkItem_2");
		for(var i=0;i<checklist2.length;i++){
	 		var vals=checklist2[i].value;
	 		if(checklist2[i].checked){
	 			ids.push(vals);
	 		}
		}
	    $("#expertsTypeId").val(ids);
	}
		/** 专家完善注册信息页面 */
	function supplierRegist() {
		if (!validateType()){
			return;
		}
		//暂存无提示
		submitForm2();
	}
	function supplierRegist4() {
		if (!validateType()){
			return;
		}
		//暂存无提示
		submitForm4();
	}
	function supplierRegist5() {
		if (!validateType()){
			return;
		}
		//暂存无提示
		submitForm5();
	}
	function pre() {
		updateStepNumber("one");
		window.location.href="${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
	}
	function fun1(){
		//选中的子节点
		getChildren();
		supplierRegist();
	}
	function fun4(){
		//选中的子节点
		getChildren();
		supplierRegist4();
	}
	function fun5(){
		//选中的子节点
		getChildren();
		supplierRegist5();
	}
	function tab3(depId){
		if(depId != ""){
			fun1();
		}
	}
	function tab4(depId,att){
		if(depId != "" && att == '1'){
			fun4();
		}
	}
	function tab5(depId,att){
		if(depId != "" && att == '1'){
			fun5();
		}
	}
	function updateStepNumber(stepNumber){
		$.ajax({
			url:"${pageContext.request.contextPath}/expert/updateStepNumber.do",
			data:{"expertId":$("#id").val(),"stepNumber":stepNumber},
			async:false,
		});
	}
	//判断专家类型
	function validateType(){
		var categoryId = $("#expertsTypeId").val();
		if(categoryId == ""){
			layer.msg("请选择专家类别 !" ,{offset: ['222px', '390px']});
			return false;
		}
		return true;
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
  <input type="hidden" id="expertsTypeId" name="expertsTypeId" value=""/>
  <input type="hidden"  name="token2" value="<%=tokenValue%>"/>
		<div id="reg_box_id_4" class="container clear margin-top-30 yinc">
	  		<h2 class="padding-20 mt40">
				<span id="ty1" class="new_step current fl"  onclick='pre()'><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
				<span id="ty2" class="new_step current fl"><i class="">2</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span>
				<span id="ty6" class="new_step fl"><i class="">3</i><div class="line"></div> <span class="step_desc_02">产品目录</span> </span>
				<span id="ty3" class="new_step fl"><i class="">4</i><div class="line"></div> <span class="step_desc_01">采购机构</span> </span> 
				<span id="ty4" class="new_step fl"><i class="">5</i><div class="line"></div> <span class="step_desc_02">下载申请表</span> </span> 
				<span id="ty5" class="new_step fl"><i class="">6</i> <span class="step_desc_01">上传申请表</span> </span> 
				<div class="clear"></div>
			</h2>
			<div class="container container_box">
			<div class="headline-v2">
                 <h2>专家类型</h2>
            </div>
			<div>
			  
   			   <div class="sevice_list col-md-12 container" class="dnone" >
				  <div class="col-md-7 col-sm-6 col-xs-12 service_list">
					  <c:forEach items="${spList}" var="sp" >
						    <span><input type="checkbox" name="chkItem_1" value="${sp.id}" />${sp.name} </span>
				      </c:forEach>
				  </div>
			    </div>
				<div class="sevice_list col-md-12 container">
				  <div class="col-md-7 col-sm-6 col-xs-12 service_list">
					  <c:forEach items="${jjList}" var="jj" >
						    <span><input type="checkbox" name="chkItem_2"  value="${jj.id}" />${jj.name} </span>
				      </c:forEach>
				  </div>
				</div>
   			   
		    <div class="tc mt20 clear col-md-12 col-sm-12 col-xs-12 ">
				<button class="btn"  type="button" onclick="pre()">上一步</button>
				<button class="btn" onclick='submitformExpert()'  type="button">暂存</button>
				<button class="btn"  type="button" onclick='fun1()'>下一步</button>
			</div>
		</div>
		</div></div>
	</form>
	<jsp:include page="/index_bottom.jsp"></jsp:include>
</body>
</html>
