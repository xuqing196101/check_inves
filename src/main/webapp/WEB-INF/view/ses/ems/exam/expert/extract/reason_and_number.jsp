<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<link rel="stylesheet"
    href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css"
    type="text/css">
<script type="text/javascript">

 /**点击触发事件*/
 function chane(){
	 var sun="0";
	  var goodsCount = $("#goodsCount").val();
	  if(goodsCount != null && goodsCount != ''){
		  sun =parseInt(sun)+parseInt(goodsCount);
	  }
	    var projectCount = $("#projectCount").val();
	    if(projectCount != null && projectCount != ''){
	        sun= parseInt(sun)+parseInt(projectCount);
	      }
	    var serviceCount = $("#serviceCount").val();
	    if(serviceCount != null && serviceCount != ''){
	        sun =parseInt(sun) + parseInt(serviceCount);
	      }
	    var goodsServerCount = $("#goodsServerCount").val();
	    if(goodsServerCount != null && goodsServerCount != ''){
	        sun = parseInt(sun) + parseInt(goodsServerCount);
	      }
	    var goodsProjectCount = $("#goodsProjectCount").val();
	    if(goodsProjectCount != null && goodsProjectCount != ''){
	        sun = parseInt(sun)+parseInt(goodsProjectCount);
	      }
	    $("#span").text(sun);
 }

  function save(){
	  
	  var eCount = "${eCount}"; 
	  var count=   $("#span").text();
	  if(parseInt(count) > parseInt(eCount)){
		  layer.msg("数量不能大于总数量");
	  }else if(parseInt(count) < parseInt(eCount)){
		  layer.msg("数量不能小于总数量");
	  }else{
		  parent.$("#goodsCount").val($("#goodsCount").val());
		  parent.$("#projectCount").val($("#projectCount").val());
		  parent.$("#serviceCount").val($("#serviceCount").val());
		  parent.$("#goodsServerCount").val($("#goodsServerCount").val());
		  parent.$("#goodsProjectCount").val($("#goodsProjectCount").val());
		  parent.$("#addressReson").val($("#addressReson").val());
		  parent.$("#hiddentype").val("1");
	  }
   
 }
</script>
</head>
<body>
	<!-- 修改订列表开始-->
	<div class="container padding-top-20   ">
<div class="container ">
     <span>选择数量<span id="span">0</span>/${eCount}</span>
   <sf:form id="form" action="${pageContext.request.contextPath}/ExpExtract/AddtemporaryExpert.do" method="post" modelAttribute="expert">
     <input type="hidden" value="${projectId}" name="projectId"/>
     <input type="hidden" value="${packageId}" name="packageId"/>
     <input type="hidden" value="${flowDefineId}" name="flowDefineId"/>
     <c:set value="" var="set"></c:set>
      <c:forEach items="${expertsTypeCode}" var="count">
      
     <c:if test="${count == 'GOODS'}">
      <li class="col-md-3 col-sm-6 col-xs-3 lsnone" >
        <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">物资技术专家：</span>
         <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <input class="title col-md-12" id="goodsCount" name="goodsCount" onchange="chane();"  value="${loginPwd}" maxlength="11" type="text">
          <span class="add-on">i</span>
          <div class="cue" >${loginPwdError}</div>
        </div>
     </li>  
     </c:if>
    <c:if test="${count == 'PROJECT'}">
      <li class="col-md-3 col-sm-6 col-xs-3 lsnone">
        <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">工程技术专家：</span>
         <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <input class="title col-md-12" id="projectCount" name="projectCount" onchange="chane();" value="${loginPwd}" maxlength="11" type="text">
          <span class="add-on">i</span>
          <div class="cue" >${loginPwdError}</div>
        </div>
     </li> 
     </c:if>
    <c:if test="${count == 'SERVICE'}">
      <li class="col-md-3 col-sm-6 col-xs-3 lsnone">
        <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">服务技术专家：</span>
         <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <input class="title col-md-12" id="serviceCount" name="serviceCount" onchange="chane();" value="${loginPwd}" maxlength="11" type="text">
          <span class="add-on">i</span>
          <div class="cue" >${loginPwdError}</div>
        </div>
     </li> 
     </c:if>
     <c:if test="${count == 'GOODS_SERVER'}">
      <li class="col-md-3 col-sm-6 col-xs-3 lsnone">
        <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">物资服务经济：</span>
         <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <input class="title col-md-12" id="goodsServerCount" name="goodsServerCount" onchange="chane();" value="${loginPwd}" maxlength="11" type="text">
          <span class="add-on">i</span>
          <div class="cue" >${loginPwdError}</div>
        </div>
     </li> 
     </c:if>
     <c:if test="${count == 'GOODS_PROJECT'}">
         <li class="col-md-3 col-sm-6 col-xs-3 lsnone">
        <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">工程经济：</span>
         <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <input class="title col-md-12" id="goodsProjectCount" name="goodsProjectCount" onchange="chane();" value="${loginPwd}" maxlength="11" type="text">
          <span class="add-on">i</span>
          <div class="cue" >${loginPwdError}</div>
        </div>
     </li> 
     </c:if>
     </c:forEach>
     <c:if test="${addressReson != null && addressReson != ''}">
      <li class="col-md-12 col-sm-12 col-xs-12 lsnone">
       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">地区限制原因：</span>
       <div class="col-md-12 col-sm-12 col-xs-12 p0">
          <textarea class="col-md-12 col-sm-12 col-xs-12 h100" name="addressReson" id="addressReson"   maxlength="200"  title="不超过200个字" placeholder="不超过200个字">${expert.remarks}</textarea>
       </div>
     </li> 
     </c:if>
  </sf:form>
 </div>
	</div>
</body>
</html>
