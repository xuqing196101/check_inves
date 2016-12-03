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
		//回显已选产品
		   var id="${expert.id}";
		   var count=0;
		   var expertsTypeId = $("#expertsTypeId").val();
			  //控制品目树的显示和隐藏
		   if(expertsTypeId==1 || expertsTypeId=="1"){
			  $.ajax({
				  url:"${pageContext.request.contextPath}/expert/getCategoryByExpertId.do?expertId="+id,
				  success:function(code){
					  var checklist = document.getElementsByName ("chkItem");
					  for(var i=0;i<checklist.length;i++){
							var vals=checklist[i].value;
							 if(code.length>0){
									$.each(code,function(i,result){
										if(vals==result){
						 				checklist[i].checked=true;
						 			   }
										if("GOODS"==result){
											count++;
										}
									});
							} 
						  } 
					    if(count>0){
							$("#hwType").show(); 
						}else{
							$("#hwType").hide(); 
						}
				 }
			 }); 
			  $("#ztree").show();
			}else{
			  $("#ztree").hide();
			}
			}); 
	
	function submitForm1(){
		getChildren();
		$.ajax({
			url:"${pageContext.request.contextPath}/expert/zanCun.do",
			data:$("#form1").serialize(),
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
		getChildren();
		$.ajax({
			url:"${pageContext.request.contextPath}/expert/zanCun.do",
			data:$("#form1").serialize(),
			type: "post",
			async: true,
			success:function(result){
				$("#id").val(result.id);
				window.location.href="${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}&pageFlag=three";
			 }
		});
	}
	//无提示暂存
	function submitForm4(){
		getChildren();
		$.ajax({
			url:"${pageContext.request.contextPath}/expert/zanCun.do",
			data:$("#form1").serialize(),
			type: "post",
			async: true,
			success:function(result){
				$("#id").val(result.id);
				window.location.href="${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}&pageFlag=four";
			 }
		});
	}
	//无提示暂存
	function submitForm5(){
		getChildren();
		$.ajax({
			url:"${pageContext.request.contextPath}/expert/zanCun.do",
			data:$("#form1").serialize(),
			type: "post",
			async: true,
			success:function(result){
				$("#id").val(result.id);
				window.location.href="${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}&pageFlag=five";
			 }
		});
	}
	//获取选中子节点id
	function getChildren(){
		 var checklist = document.getElementsByName ("chkItem");
		 var count=0;
		 var ids=[];
		 for(var i=0;i<checklist.length;i++)
		   {
	 			var vals=checklist[i].value;
	 			if(checklist[i].checked){
	 				ids.push(vals);
	 				if(vals=="GOODS"){
	 				 count++;
	 				}
	 			}
		  } 
		if(count>0){
			 $("#hwType").show();  
		}else{
			var checklist = document.getElementsByName ("chkItem");
			 for(var i=0;i<checklist.length;i++)
			   {
		 			var vals=checklist[i].value;
		 			if(vals=='SALES'){
		 				checklist[i].checked = false;
		 			}
		 			if(vals=='PRODUCT'){
		 				checklist[i].checked = false;
		 			} 
			  }
			 $("#hwType").hide();  
		}
	     $("#categoryId").val(ids);
		
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
		window.location.href="${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
	}
	function fun1(){
		//选中的子节点
		getChildren();
		supplierRegist();
		var expertsTypeId = $("#expertsTypeId").val();
		if(expertsTypeId == "1"){
			$("#tExpertsTypeId").text("技术");
		}
		if(expertsTypeId == "2"){
			$("#tExpertsTypeId").text("法律");
		}
		if(expertsTypeId == "3"){
			$("#tExpertsTypeId").text("商务");
		}
	}
	function fun4(){
		//选中的子节点
		getChildren();
		supplierRegist4();
		var expertsTypeId = $("#expertsTypeId").val();
		if(expertsTypeId == "1"){
			$("#tExpertsTypeId").text("技术");
		}
		if(expertsTypeId == "2"){
			$("#tExpertsTypeId").text("法律");
		}
		if(expertsTypeId == "3"){
			$("#tExpertsTypeId").text("商务");
		}
	}
	function fun5(){
		//选中的子节点
		getChildren();
		supplierRegist5();
		var expertsTypeId = $("#expertsTypeId").val();
		if(expertsTypeId == "1"){
			$("#tExpertsTypeId").text("技术");
		}
		if(expertsTypeId == "2"){
			$("#tExpertsTypeId").text("法律");
		}
		if(expertsTypeId == "3"){
			$("#tExpertsTypeId").text("商务");
		}
	}
	//显示隐藏树
	function typeShow(){
		 var expertsTypeId = $("#expertsTypeId").val();
		 if(expertsTypeId==1 || expertsTypeId=="1"){
			 $("#ztree").show();
			 getChildren();
		}else{
			 $("#ztree").hide();
		}
		
	}
	function tab4(depId){
		if(depId != ""){
			
		} else {
			fun1();
		}
	}
	function tab5(depId){
		if(depId != ""){
			
		} else {
			fun1();
		}
	}
</script>
</head>
<body>
 <form id="form1" action="${pageContext.request.contextPath}/expert/add.html" method="post">
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
		<div id="reg_box_id_4" class="container clear margin-top-30 yinc">
	  		<h2 class="padding-20 mt40">
				<span id="ty1" class="new_step current fl"  onclick='pre()'><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
				<span id="ty2" class="new_step current fl"><i class="">2</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span>
				<span id="ty3" class="new_step <c:if test="${expert.purchaseDepId != null}">current</c:if> fl" onclick='fun1()'><i class="">3</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
				<span id="ty4" class="new_step <c:if test="${att eq '1'}">current</c:if> fl" onclick="tab4('${expert.purchaseDepId}')"><i class="">4</i><div class="line"></div> <span class="step_desc_01">下载申请表</span> </span> 
				<span id="ty5" class="new_step <c:if test="${att eq '1'}">current</c:if> fl" onclick="tab5('${expert.purchaseDepId}')"><i class="">5</i> <span class="step_desc_02">上传申请表</span> </span> 
				<div class="clear"></div>
			</h2>
			<div class="container container_box">
			<div class="headline-v2">
                 <h2>专家类型</h2>
            </div>
			<div>
			  <ul class="ul_list" >
     		    <li class="col-md-3 col-sm-6 col-xs-12" >
			      <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><span class="star_red fl">*</span>专家类型</span>
			      <input type="hidden" id="expertsTypeIds" value="" >
			      <div class="select_common col-md-12 col-xs-12 col-sm-12 p0">
			        <select name="expertsTypeId" id="expertsTypeId" onchange="typeShow();">
			   		  <option value="">-请选择-</option>
			   		  <option <c:if test="${expert.expertsTypeId eq '1'}">selected</c:if> value="1">技术</option>
			   		  <option <c:if test="${expert.expertsTypeId eq '2'}">selected</c:if> value="2">法律</option>
			   		  <option <c:if test="${expert.expertsTypeId eq '3'}">selected</c:if> value="3">商务</option>
			        </select>
			      </div>
			    </li>
   			 </ul>
   			 <div class="" id="ztree" >
   			   <div class="sevice_list col-md-12 container" class="dnone" >
			    <div class="col-md-5 col-sm-6 col-xs-12 title"><span class="star_red fl">*</span>产品服务/分类：</div>
				  <div class="col-md-7 col-sm-6 col-xs-12 service_list">
					  <c:forEach items="${spList}" var="obj" >
						    <span><input type="checkbox" name="chkItem" onclick="getChildren()" value="${obj.code}" />${obj.name} </span>
				      </c:forEach>
				  </div>
			    </div>
				<div class="sevice_list col-md-12 container" id="hwType">
				  <div class="col-md-5 col-sm-6 col-xs-12 title"><span class="star_red fl">*</span>货物分类：</div>
				  <div class="col-md-7 col-sm-6 col-xs-12 service_list">
					  <c:forEach items="${hwList}" var="hw" >
						    <span><input type="checkbox" name="chkItem" onclick="getChildren()"  value="${hw.code}" />${hw.name} </span>
				      </c:forEach>
				  </div>
				</div>
				</div>
   			   
		    <div class="tc mt20 clear col-md-12 col-sm-12 col-xs-12 ">
				<button class="btn"  type="button" onclick="pre()">上一步</button>
				<button class="btn" onclick='submitForm1()'  type="button">暂存</button>
				<button class="btn"  type="button" onclick='fun1()'>下一步</button>
			</div>
		</div>
		</div></div>
	</form>
</body>
</html>
