<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>
</head>

<script type="text/javascript">
$(function idType(){

    $("#idType").find("option[value='${expert.idType}']").attr("selected",true);
    $("#expertsTypeId").find("option[value='${expert.expertsTypeId}']").attr("selected",true);

});
 
function sumbits(){
	alert("as");
	$.ajax({
        cache: true,
        type: "POST",
        url:"${pageContext.request.contextPath}/ExpExtract/AddtemporaryExpert.do",
        data:$('#form').serialize(),// 你的formid
        async: false,
        dataType:"json",
        error: function(request) {
        },
        success: function(data) {
        	alert("asd");
        	alert(data.sccuess);
            if(data.sccuess == "sccuess"){
            	alert(data.sccuess);
            	  window.location.href = '${pageContext.request.contextPath}/packageExpert/assignedExpert.html?projectId='+id;
            }
        }
    });
	}
</script>
<body>

   
<!-- 修改订列表开始-->
<%--    <form action="${pageContext.request.contextPath}/ExpExtract/AddtemporaryExpert.do" id="form" method="post" > --%>
<%--    --%>


<!-- 修改订列表开始-->
   <div class="container container_box">
   <sf:form id="form" action="${pageContext.request.contextPath}/ExpExtract/AddtemporaryExpert.do" method="post" modelAttribute="expert">
     <input type="hidden" value="${projectId}" name="projectId"/>
     <input type="hidden" value="${packageId}" name="packageId"/>
     <input type="hidden" value="${flowDefineId}" name="flowDefineId"/>
   <div>
    <h2 class="count_flow"><i>1</i>添加临时专家</h2>
   <ul class="ul_list">
      <li class="col-md-3 col-sm-6 col-xs-12 pl15">
         <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">专家姓名：</span>
         <div class="input-append input_group col-sm-12 col-xs-12 p0">
         <input class="title col-md-12" maxlength="10" id="appendedInput" name="relName" value="${expert.relName}" type="text"/>
         <span class="add-on">i</span>
         <div class="cue"><sf:errors path="relName"/></div>
        </div>
	   </li>
	    <li class="col-md-3 col-sm-6 col-xs-12 ">
	      <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">证件类型：</span>
	        <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
		        <select class="" name="idType"  id="idType">
		           <option value="">-请选择-</option>
		           <c:forEach items="${idType}" var="idtype">
		            <option value="${idtype.id}">${idtype.name}</option>
		           </c:forEach>
		         </select>
		        <div class="cue" ><sf:errors path="idType"/></div>
	       </div>
     </li>
     <li class="col-md-3 col-sm-6 col-xs-12 ">
        <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">证件号码：</span>
        <div class="input-append input_group col-sm-12 col-xs-12 p0">
         <input class="title col-md-12" id="appendedInput" name="idNumber" value="${expert.idNumber}" maxlength="18" type="text">
         <span class="add-on">i</span>
             <div class="cue" ><sf:errors path="idNumber"/></div>
        </div>
	 </li>
	  <li class="col-md-3 col-sm-6 col-xs-12 ">
	    <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">专家类型：</span>
	     <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
               <select class="" name="expertsTypeId" id="expertsTypeId">
		           <option value="">-请选择-</option>
		           <option value="1">技术</option>
		           <option value="2">法律</option>
		           <option value="3">商务</option>
		         </select>
		        <div class="cue" ><sf:errors path="expertsTypeId"/></div>
           </div>
         
     </li>
     <li class="col-md-3 col-sm-6 col-xs-12 ">
        <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">现任职务：</span>
         <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <input class="title col-md-12" id="appendedInput" name="atDuty" value="${expert.atDuty}" maxlength="10" type="text">
         <span class="add-on">i</span>
           <div class="cue" ><sf:errors path="atDuty"/></div>
        </div>
	 </li> 
<!--      <li class="col-md-3 col-sm-6 col-xs-12 "> -->
<!--        <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">联系地址：</span> -->
<!--         <div class="input-append input_group col-sm-12 col-xs-12 p0"> -->
<!--             <input class="title col-md-12" id="appendedInput" name="unitAddress" maxlength="20" type="text"> -->
<!--          <span class="add-on">i</span> -->
<!--             <div class="cue" id="unitAddressError">不能为空</div> -->
<!--         </div> -->
	   
<!-- 	 </li>  -->
     <li class="col-md-3 col-sm-6 col-xs-12 ">
       <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">联系电话：</span>
        <div class="input-append input_group col-sm-12 col-xs-12 p0">
             <input class="title col-md-12" id="appendedInput" name="mobile" value="${expert.mobile}"  maxlength="11" type="text">
         <span class="add-on">i</span>
          <div class="cue"><sf:errors path="mobile"/></div>
        </div>
	 </li> 
	  <li class="col-md-3 col-sm-6 col-xs-12 ">
	    <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">分配账户：</span>
	      <div class="input-append input_group col-sm-12 col-xs-12 p0">
              <input class="title col-md-12" id="appendedInput" name="loginName" value="${loginName}"  maxlength="11" type="text">
         <span class="add-on">i</span>
         <div class="cue" >${loginNameError}</div>
        </div>
     </li> 
      <li class="col-md-3 col-sm-6 col-xs-12 ">
        <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">分配密码：</span>
         <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <input class="title col-md-12" id="appendedInput" name="loginPwd" value="${loginPwd}" maxlength="11" type="text">
          <span class="add-on">i</span>
          <div class="cue" >${loginPwdError}</div>
        </div>
     </li>  
      <li class="col-md-12 col-sm-12 col-xs-12">
       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">备注</span>
       <div class="col-md-12 col-sm-12 col-xs-12 p0">
          <textarea class="col-md-12 col-sm-12 col-xs-12 h100" name="remarks"   maxlength="200"  title="不超过200个字" placeholder="不超过200个字">${expert.remarks}</textarea>
       </div>
       
     </li> 
   </ul>
   </div>
  <div  class="col-md-12">
   <div class="col-md-6" align="center">
      <button class="btn btn-windows save"  type="submit">保存</button>
      <button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
	</div>
  </div>
  </sf:form>
 </div>
</body>
</html>
