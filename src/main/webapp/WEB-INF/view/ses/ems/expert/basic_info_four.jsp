<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
	function pre3(name, i, position) {
		updateStepNumber("three");
		window.location.href="${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
	}
	function pre2(name, i, position) {
		updateStepNumber("two");
		window.location.href="${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
	}
	function pre1(name, i, position) {
		updateStepNumber("one");
		window.location.href="${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
	}

	//下载
	function downloadTable(){
		$("#formExpert").attr("action","${pageContext.request.contextPath}/expert/download.html");
		$("#formExpert").submit();
	}
	function four(att){
		if (att == '1' || att == 'ok') {
			updateStepNumber("five");
			window.location.href="${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
		}
	}
	function updateStepNumber(stepNumber){
		$.ajax({
			url:"${pageContext.request.contextPath}/expert/updateStepNumber.do",
			data:{"expertId":$("#id").val(),"stepNumber":stepNumber},
			async:false,
		});
	}
	function initData(){
		$.ajax({
			url:"${pageContext.request.contextPath}/expert/initData.do",
			data:{"expertId":$("#id").val()},
			async:false,
			dataType:"json",
			success:function(response){
				$("#tSex").html(response.gender);
				$("#tFace").html(response.politicsStatus);
				$("#Taddress").html(response.address);
				$("#tHight").html(response.hightEducation);
			}
		});
	}
</script>
</head>
<body onload="initData()">
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
	<div id="reg_box_id_6" class="container clear margin-top-30 yinc">
		  <h2 class="padding-20 mt40">
			<span id="dy1" class="new_step current fl" onclick='pre1()'><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
			<span id="dy2" class="new_step current fl" onclick='pre2()'><i class="">2</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span> 
			<span id="dy3" class="new_step current fl" onclick='pre3()'><i class="">3</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
			<span id="dy4" class="new_step current fl"><i class="">4</i><div class="line"></div> <span class="step_desc_01">下载申请表</span> </span> 
			<span id="dy5" class="new_step <c:if test="${att eq '1'}">current</c:if> fl" onclick="four('${att}')"><i class="">5</i> <span class="step_desc_02">上传申请表</span> </span> 
			<div class="clear"></div>
		  </h2>
<div class="tab-content padding-top-20">
  <div class="headline-v2">
	 <h2>打印专家申请表</h2>
  </div>  
<div>
  <table class="table table-bordered">
  <div class="margin-top-30"></div>
    <div align="left">
      <a class="btn btn-windows input" onclick='downloadTable()' href="javascript:void(0)">下载</a>
    </div>
    <div class="margin-top-30"></div>
   	<tr>
 	  <td width="25%" class="bggrey">姓名</td>
 	  <td width="25%" id="tName">${expert.relName}</td>
 	  <td width="25%" class="bggrey">性别</td>
      <td width="25%" id="tSex"></td>
   	</tr>
   <tr>
	 <td width="25%" class="bggrey">出生日期</td>
	 <td width="25%" id="tBirthday"><fmt:formatDate value="${expert.birthday}" pattern="yyyy-MM-dd" /></td>
	 <td width="25%" class="bggrey">政治面貌</td>
	 <td width="25%"  id="tFace"></td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">所在地区</td>
	 <td width="25%" id="Taddress"></td>
	 <td width="25%" class="bggrey">职称</td>
	 <td width="25%" id="tHey" >${expert.professTechTitles}</td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">证件号码<td>
	 <td id="tIdNumber" colspan="3">${expert.idNumber}</td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">从事专业类别</td>
	 <td width="25%" id="tExpertsTypeId"></td>
	 <td width="25%" class="bggrey">从事年限</td>
	 <td width="25%" id="tTimeStartWork"><fmt:formatDate value="${expert.timeStartWork}" pattern="yyyy-MM-dd" /></td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">最高学历</td>
	 <td width="25%" id="tHight"></td>
	 <td width="25%" class="bggrey">最高学位</td>
	 <td width="25%" id="tWei">${expert.degree}</td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">执业资格1</td>
	 <td width="25%" > </td>
	 <td width="25%" class="bggrey">注册证书编号1</td>
	 <td width="25%" > </td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">执业资格2</td>
	 <td width="25%" ></td>
	 <td width="25%" class="bggrey">注册证书编号2</td>
	 <td width="25%"></td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">执业资格3</td>
	 <td width="25%"></td>
	 <td width="25%" class="bggrey">注册证书编号3</td>
	 <td width="25%" ></td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">近两年是否接受过评标业务培训</td>
	 <td width="25%"></td>
	 <td width="25%" class="bggrey">是否愿意成为应急专家</td>
	 <td width="25%"></td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">所属行业</td>
	 <td width="25%"></td>
	 <td width="25%" class="bggrey">报送部门</td>
	 <td width="25%"></td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">手机号码</td>
	 <td width="25%" id="tMobile">${expert.mobile}</td>
	 <td width="25%" class="bggrey">单位电话</td>
	 <td width="25%" id="tTelephone"></td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">住宅电话</td>
	 <td width="25%">${expert.telephone}</td>
	 <td width="25%" class="bggrey">电子邮箱</td>
	 <td width="25%"></td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">毕业院校及专业</td>
	 <td id="tGraduateSchool" colspan="3">${expert.graduateSchool} --- ${expert.major}</td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">单位名称</td>
	 <td id="tWorkUnit" colspan="3">${expert.workUnit}</td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">单位地址 </td>
	 <td width="25%" id="tUnitAddress">${expert.unitAddress}</td>
	 <td width="25%" class="bggrey">单位邮编</td>
	 <td width="25%" id="tPostCode">${expert.postCode}</td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">家庭地址 </td>
	 <td width="25%"></td>
	 <td width="25%" class="bggrey">家庭邮编</td>
	 <td width="25%"></td>
   </tr>
   <tr>
   	 <td width="25%" class="bggrey">评标专业一</td>
   	 <td width="25%" colspan="3"></td>
   </tr>
   <tr>
   	 <td width="25%" class="bggrey">评标专业二</td>
   	 <td colspan="3"></td>
   </tr>
   <tr>
   	 <td width="25%" class="bggrey">评标专业三</td>
   	 <td colspan="3"></td>
   </tr>
   <tr>
   	 <td width="25%" class="bggrey">评标专业四</td>
   	 <td colspan="3"></td>
   </tr>
   <tr>
   	 <td width="25%" class="bggrey">评标专业五</td>
   	 <td colspan="3"></td>
   </tr>
   <tr>
   	 <td width="25%" class="bggrey">评标专业六</td>
   	 <td colspan="3"></td>
   </tr>
   <tr>
   	 <td class="bggrey tc f20 b" colspan="4">工作经历</td>
   </tr>
   <tr>
	 <td class="bggrey b tc">起止年月</td>
	 <td class="bggrey b tc" colspan="2">单位及职务</td>
	 <td class="bggrey b tc">证明人</td>
   </tr>
   <tr>
	 <td align="center">至 </td>
	 <td align="center" colspan="2"> </td>
	 <td align="center" > </td>
   </tr>
   <tr>
	 <td align="center"> 至</td>
	 <td align="center" colspan="2"> </td>
	 <td align="center" > </td>
   </tr>
   <tr>
	 <td align="center">至 </td>
	 <td align="center" colspan="2"> </td>
	 <td align="center" > </td>
   </tr>
   <tr>
	 <td align="center">至 </td>
	 <td align="center" colspan="2"> </td>
	 <td align="center" > </td>
   </tr>
   <tr>
     <td align="center">至 </td>
	 <td align="center" colspan="2"> </td>
	 <td align="center"> </td>
   </tr>
 </table>
    <div class="tc mt20 clear col-md-12 col-sm-12 col-xs-12">
   		<button class="btn"   type="button" onclick="pre3()">上一步</button>
		<button class="btn"   type="button" onclick="four('ok')">下一步</button>
	</div>
	  </div>
	    </div>
	      </div>
	</form>
	<jsp:include page="/index_bottom.jsp"></jsp:include>
</body>
</html>
