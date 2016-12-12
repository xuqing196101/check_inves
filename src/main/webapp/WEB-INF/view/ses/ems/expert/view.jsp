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
	function backOld() {
		window.location.href="${pageContext.request.contextPath}/expert/findAllExpert.html";
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
				$("#idType").html(response.idType);
				$("#expertsFrom").html(response.expertsFrom);
				$("#expertsType").html(response.expertsTypeId);
			}
		});
	}
</script>
</head>
<body onload="initData()">
	<div id="reg_box_id_6" class="container clear margin-top-30 yinc">
	<input type="hidden" name="id" id="id" value="${expert.id}"/>
  <table class="table">
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
	 <td width="25%" class="bggrey">专业技术职称/执业资格</td>
	 <td width="25%" id="tHey" >${expert.professTechTitles}</td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">居民身份证号码</td>
	 <td width="25%">${expert.idCardNumber}</td>
	 <td width="25%" class="bggrey">民族</td>
	 <td width="25%">${expert.nation}</td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">健康状况</td>
	 <td width="25%">${expert.healthState}</td>
	 <td width="25%" class="bggrey">所在单位</td>
	 <td width="25%" id="tTimeStartWork">${expert.workUnit}</td>
   </tr>
   
   
   <tr>
	 <td width="25%" class="bggrey">缴纳社会保险证明</td>
	 <td width="25%">${expert.coverNote}</td>
	 <td width="25%" class="bggrey">单位邮编</td>
	 <td width="25%">${expert.postCode}</td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">单位地址</td>
	 <td colspan="3">${expert.unitAddress}</td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">军队人员身份证件类型</td>
	 <td width="25%" id="idType"></td>
	 <td width="25%" class="bggrey">证件号码</td>
	 <td width="25%" >${expert.idNumber}</td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">现任职务</td>
	 <td width="25%" >${expert.atDuty}</td>
	 <td width="25%" class="bggrey">从事专业</td>
	 <td width="25%">${expert.major}</td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">从事专业起始年度</td>
	 <td width="25%"><fmt:formatDate value="${expert.timeStartWork}" pattern="yyyy-MM-dd" /></td>
	 <td width="25%" class="bggrey">专家来源</td>
	 <td width="25%" id="expertsFrom"></td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">专业技术职称/执业资格</td>
	 <td width="25%">${expert.professTechTitles}</td>
	 <td width="25%" class="bggrey">取得技术职称时间</td>
	 <td width="25%"><fmt:formatDate value="${expert.makeTechDate}" pattern="yyyy-MM-dd" /></td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">毕业院校及专业</td>
	 <td colspan="3">${expert.graduateSchool}</td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">专家类别</td>
	 <td width="25%" id="expertsType"></td>
	 <td width="25%" class="bggrey">最高学历</td>
	 <td width="25%" id="tHight"></td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">最高学位</td>
	 <td width="25%">${expert.degree}</td>
	 <td width="25%" class="bggrey">个人邮箱</td>
	 <td width="25%">${expert.email}</td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">移动电话</td>
	 <td width="25%">${expert.mobile}</td>
	 <td width="25%" class="bggrey">固定电话</td>
	 <td width="25%">${expert.telephone}</td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">传真电话</td>
	 <td colspan="3">${expert.fax}</td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey" rowspan="3">参评的产品类别 </td>
	 <td colspan="3" rowspan="3">${expert.productCategories}</td>
   </tr>
   <tr>
	 <td width="25%"  class="bggrey" rowspan="3">主要工作经历</td>
	 <td colspan="3" rowspan="3">${expert.productCategories}</td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey" rowspan="3">专业学术成果</td>
	 <td colspan="3" rowspan="3">${expert.academicAchievement}</td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey" rowspan="3">参加军队地方采购评审情况 </td>
	 <td colspan="3" rowspan="3">${expert.reviewSituation}</td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey" rowspan="3">需要申请回避的情况 </td>
	 <td colspan="3" rowspan="3">${expert.avoidanceSituation}</td>
   </tr>
 </table>
    <div class="tc mt20 clear col-md-12 col-sm-12 col-xs-12">
	</div>
	  </div>
	    </div>
	      </div>
</body>
</html>
