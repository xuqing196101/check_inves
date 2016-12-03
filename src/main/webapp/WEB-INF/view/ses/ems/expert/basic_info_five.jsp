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
	//提交
	function addSubmitForm(){
		if(!validateHeTong()){
			return;
		}
		$("#form1").attr("action","${pageContext.request.contextPath}/expert/add1.html?gitFlag=1");
		$("#form1").submit();
	}
	//判断申请表  合同书
	function validateHeTong(){
		var flag;
		var sysId = $("#sysId").val();
		$.ajax({
			url:'${pageContext.request.contextPath}/expert/findAttachment.do',
			data:{"sysId":sysId},
			dataType:"json",
			cache: false,
	        async: false,
			success:function(data){
				if(data.length<7){
					layer.msg("还有附件未上传!",{offset: ['222px', '390px']});
					flag=false;
				}else{
					flag=true;
				}
			}
		});
		return flag;
	}
	function tab1(name, i, position) {
		window.location.href="${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
	}
	function tab2(name, i, position) {
		window.location.href="${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}&pageFlag=two";
	}
	function tab3(name, i, position) {
		window.location.href="${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}&pageFlag=three";
	}
	function tab4(name, i, position) {
		window.location.href="${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}&pageFlag=four";
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
		<div id="reg_box_id_7" class="container clear margin-top-30" >
		   <h2 class="padding-20 mt40">
			 <span id="sc1" class="new_step current fl" onclick='tab1()'><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
			 <span id="sc2" class="new_step current fl" onclick='tab2()'><i class="">2</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span> 
			 <span id="sc3" class="new_step current fl" onclick='tab3()'><i class="">3</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
			 <span id="sc4" class="new_step current fl" onclick='tab4()'><i class="">4</i><div class="line"></div> <span class="step_desc_01">下载申请表</span> </span> 
			 <span id="sc5" class="new_step current fl"><i class="">5</i> <span class="step_desc_02">上传申请表</span> </span> 
			 <div class="clear"></div>
		  </h2>
		<div class="tab-content padding-top-20">
			<div class="headline-v2">
			  <h2>专家申请表、合同书</h2>
			</div>   
	   	  <table class="table table-bordered">
	   	    <tr>
	   	      <td class="bggrey" width="15%"><i class="red">*</i>专家申请表：</td>
	   	      <td>
	   	        <up:upload id="expert6"  groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_APPLICATION_TYPEID}" auto="true"/>
			    <up:show showId="show6"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_APPLICATION_TYPEID}"/>
	   	      </td>
	   	      <td class="bggrey" width="15%" ><i class="red">*</i>专家合同书：</td>
	   	      <td>
	   	        <up:upload id="expert7" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_CONTRACT_TYPEID}" auto="true"/>
			    <up:show showId="show7"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_CONTRACT_TYPEID}"/>
	   	      </td>
	   	    </tr>
		 </table>
		    <div class="tc mt20 clear col-md-12 col-xs-12 col-sm-12">
		   		<button class="btn"   type="button" onclick="tab4()">上一步</button>
				<input  class="btn" type="button" onclick="addSubmitForm()" value="提交" />
			</div>
		</div></div>
	</form>
</body>
</html>
