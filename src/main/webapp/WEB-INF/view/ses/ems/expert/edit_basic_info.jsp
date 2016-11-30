<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/tld/upload" prefix="up"%>
<jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//表单标示
	String tokenValue= new Date().getTime()+UUID.randomUUID().toString()+"";
	session.setAttribute("tokenSession", tokenValue);
%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<title>专家个人信息</title>
<script type="text/javascript">
	var addressId="${expert.address}";
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
	    var parentId ;
		var addressId="${expert.address}";
		window.onload=function(){
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
		}
	//显示隐藏树
	function typeShow(){
		 var expertsTypeId = $("#expertsTypeId").val();
		 if(expertsTypeId==1 || expertsTypeId=="1"){
			 $("#ztree").show();
		 }else{
			 $("#ztree").hide();
		 }
		
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
			 $("#hwType").hide();  
			   }
		}
	     $("#categoryId").val(ids);
	}
	function submitForm(){
		getChildren();
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
       <div class="tab-v2">
         <ul class="nav nav-tabs bgwhite">
	       <li class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="f18">基本信息</a></li>
		   <li><a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18">专家类型</a></li>
	     </ul>
 <form action="${pageContext.request.contextPath}/expert/edit.html"  method="post" id="formId"  class="registerform"> 
   <input type="hidden" name="categoryId" id="categoryId">
   <input type="hidden"  name="token2" value="<%=tokenValue%>">
   <input type="hidden" name="id" value="${expert.id}">
   <input type="hidden" name="isPass" id="isPass"/>
     <div class="tab-content">
	   <div class="tab-pane fade in active" id="tab-1">
	   <h2 class="count_flow"><i>1</i>修改个人信息</h2>
         <ul class="ul_list">
		   <li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><i class="red">*</i>  专家姓名</span>
			 <div class="input-append input_group col-sm-12 col-xs-12 p0">
				<input class="input_group" id="relName" name="relName" value="${expert.relName}"   type="text">
			    <span class="add-on">i</span>
			 </div>
		   </li>
		   <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"> 出生日期</span>
			 <div class="input-append input_group col-sm-12 col-xs-12 p0">
				<input class="input_group"   readonly="readonly" value="<fmt:formatDate type='date' value='${expert.birthday}' dateStyle="default" pattern="yyyy-MM-dd"/>" name="birthday" id="birthday" type="text" onclick='WdatePicker()'>
			 </div>
		   </li>
		   <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><i class="red">*</i>  证件号码</span>
			 <div class="input-append input_group col-sm-12 col-xs-12 p0">
				 <input class="input_group" maxlength="30" value="${expert.idNumber}"  name="idNumber" id="idNumber" type="text">
				 <span class="add-on">i</span>
			 </div>
		   </li>
		   <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><i class="red">*</i>  民族</span>
			 <div class="input-append input_group col-sm-12 col-xs-12 p0">
			 <input class="input_group" maxlength="10" value="${expert.nation}"  name="nation" id="nation" type="text">
			 <span class="add-on">i</span>
			 </div>
		   </li>
		   <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>  性别</span>
			 <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
				<select name="gender" id="gender">
				   <option selected="selected" value="">-请选择-</option>
				   <c:forEach items="${sexList }" var="sex" varStatus="vs">
                    <option <c:if test="${expert.gender eq sex.id}">selected="selected"</c:if> value="${sex.id}">${sex.name}</option>
                   </c:forEach>
				</select>
			 </div>
		   </li>
		   <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><i class="red">*</i>  专家来源</span>
			 <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
				<select  name="expertsFrom" id="expertsFrom">
				<option selected="selected" value="">-请选择-</option>
			   	<c:forEach items="${lyTypeList }" var="ly" varStatus="vs"> 
				 <option <c:if test="${expert.expertsFrom eq ly.id }">selected="selected"</c:if> value="${ly.id}">${ly.name}</option>
				</c:forEach>
			   </select>
			 </div>
		   </li>
		<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><i class="red">*</i>  省</span>
            <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
             <select id="addr" onchange="fun();">
                    <option value="">-请选择-</option>
             </select>
            </div>
        </li>
        <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><i class="red">*</i>  市</span>
            <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
             <select  name="address" id="add">
               <option value="">-请选择-</option>
             </select>
            </div>
        </li>
		<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><i class="red">*</i>  证件类型</span>
		  <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
			<select  name="idType" id="idType">
		   	  <option selected="selected" value="">-请选择-</option>
		   	  <c:forEach items="${idTypeList }" var="idType" varStatus="vs">
               <option <c:if test="${expert.idType eq idType.id }">selected="selected"</c:if> value="${idType.id}">${idType.name}</option>
              </c:forEach>
		    </select>
		  </div>
		</li>
		<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><i class="red">*</i> 最高学历</span>
		  <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
			 <select class="input_group" name="hightEducation" id="hightEducation" >
			 	<option selected="selected" value="">-请选择-</option>
			   	<c:forEach items="${xlList}" var="xl" varStatus="vs">
                 <option <c:if test="${expert.hightEducation eq xl.id }">selected="selected"</c:if> value="${xl.id}">${xl.name}</option>
                </c:forEach>
			 </select>
		  </div>
		</li>
		<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">政治面貌</span>
			<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
			  <select class="input_group" name="politicsStatus" id="politicsStatus">
				<option selected="selected" value="">-请选择-</option>
			   	<c:forEach items="${zzList }" var="zz" varStatus="vs">
                 <option <c:if test="${expert.politicsStatus eq zz.id }">selected="selected"</c:if> value="${zz.id}">${zz.name}</option>
                </c:forEach>
			  </select>
			</div>
		</li>
		<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><i class="red">*</i> 毕业院校</span>
		  <div class="input-append input_group col-sm-12 col-xs-12 p0">
			<input class="input_group" maxlength="40" value="${expert.graduateSchool}"  name="graduateSchool" id="graduateSchool" type="text">
			<span class="add-on">i</span>
		  </div>
		</li>
		<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"> 专家技术职称</span>
		  <div class="input-append input_group col-sm-12 col-xs-12 p0">
			<input class="input_group" maxlength="20" value="${expert.professTechTitles}"  name="professTechTitles" id="professTechTitles" type="text">
			<span class="add-on">i</span>
		  </div>
		</li>
		<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"> 参加工作时间</span>
		  <div class="input-append input_group col-sm-12 col-xs-12 p0">
			<input class="input_group"   readonly="readonly" value="<fmt:formatDate type='date' value='${expert.timeToWork}' dateStyle="default" pattern="yyyy-MM-dd"/>" name="timeToWork" id="appendedInput" type="text" onclick='WdatePicker()'>
		  </div>
		</li>
		<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><i class="red">*</i> 专业</span>
		 <div class="input-append input_group col-sm-12 col-xs-12 p0">
			<input class="input_group" maxlength="20" value="${expert.major}"  name="major" id="major" type="text">
			<span class="add-on">i</span>
		  </div>
		</li>
		<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"> 从事专业起始年度</span>
		  <div class="input-append input_group col-sm-12 col-xs-12 p0">
			 <input class="input_group" value="<fmt:formatDate type='date' value='${expert.timeStartWork}' dateStyle="default" pattern="yyyy-MM-dd"/>"  readonly="readonly" name="timeStartWork" id="timeStartWork" type="text" onclick='WdatePicker()'>
	      </div>
		</li>
		<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">工作单位</span>
		  <div class="input-append input_group col-sm-12 col-xs-12 p0">
			<input class="input_group" maxlength="40" value="${expert.workUnit}"  name="workUnit" id="workUnit" type="text">
			<span class="add-on">i</span>
		  </div>
		</li>
		<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><i class="red">*</i> 单位地址</span>
		  <div class="input-append input_group col-sm-12 col-xs-12 p0">
			 <input class="input_group" maxlength="40" value="${expert.unitAddress}"  name="unitAddress" id="unitAddress" type="text">
			 <span class="add-on">i</span>
		  </div>
		</li>
		<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><i class="red">*</i> 联系电话（固话）</span>
		  <div class="input-append input_group col-sm-12 col-xs-12 p0">
			<input class="input_group" maxlength="15" value="${expert.telephone}"  name="telephone" id="telephone" type="text">
			<span class="add-on">i</span>
		  </div>
		</li>
		<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><i class="red">*</i> 手机</span>
		  <div class="input-append input_group col-sm-12 col-xs-12 p0">
			<input class="input_group" maxlength="15" value="${expert.mobile}"  name="mobile" id="mobile" type="text">
			<span class="add-on">i</span>
		  </div>
		</li>
		<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"> 传真</span>
		 <div class="input-append input_group col-sm-12 col-xs-12 p0">
			<input class="input_group" maxlength="10"  value="${expert.fax}"  name="fax" id="fax" type="text">
			<span class="add-on">i</span>
		  </div>
		</li> 
		<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"> 邮编</span>
		 <div class="input-append input_group col-sm-12 col-xs-12 p0">
			<input class="input_group" maxlength="6" value="${expert.postCode}"  name="postCode" id="postCode" type="text">
			<span class="add-on">i</span>
		  </div>
		</li>
		<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"> 获得技术时间</span>
		  <div class="input-append input_group col-sm-12 col-xs-12 p0">
			<input class="input_group" value="<fmt:formatDate type='date' value='${expert.makeTechDate}' dateStyle="default" pattern="yyyy-MM-dd"/>"  readonly="readonly" name="makeTechDate" id="makeTechDate" type="text" onclick='WdatePicker()'>
		  </div>
		</li>
		<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"> 学位</span>
		 <div class="input-append input_group col-sm-12 col-xs-12 p0">
			<input class="input_group" maxlength="10" value="${expert.degree}"  name="degree" id="degree" type="text">
			<span class="add-on">i</span>
		  </div>
		</li>
		<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><i class="red">*</i> 健康状态</span>
		  <div class="input-append input_group col-sm-12 col-xs-12 p0">
			<input class="input_group" maxlength="10" value="${expert.healthState}"  name="healthState" id="healthState" type="text">
			<span class="add-on">i</span>
		  </div>
		</li>
		<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"> 现任职务</span>
		  <div class="input-append input_group col-sm-12 col-xs-12 p0">
			<input class="input_group" maxlength="10" value="${expert.atDuty}"  name="atDuty" id="appendedInput" type="text">
			<span class="add-on">i</span>
		  </div>
		</li>
	</ul>
  <!-- 附件信息-->
  <div class="padding-top-10 clear">
    <h2 class="count_flow"><i>2</i>附件信息</h2>
    <ul class="ul_list">
      <table class="table table-bordered">
	  	 <tr>
	  	    <td  class="info"><i class="red">*</i> 身份证:</td>
	  	    <td colspan="3">
	  	      <up:upload id="expert1"  groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_IDNUMBER_TYPEID}"   auto="true"/>
	          <up:show showId="show1"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_IDNUMBER_TYPEID}"/>
	  	    </td>
   	  	    <td  class="info"><i class="red">*</i> 学历证书:</td>
	  	    <td colspan="3">
	  	      <up:upload id="expert2" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7"  businessId="${sysId}" sysKey="${expertKey}"  typeId="${typeMap.EXPERT_ACADEMIC_TYPEID}" auto="true"/>
	          <up:show showId="show2"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_ACADEMIC_TYPEID}"/>
	  	    </td>
	  	 </tr>
	  	 <tr>
	  	    <td  class="info"><i class="red">*</i> 职称证书:</td>
	  	    <td colspan="3">
	  	      <up:upload id="expert3" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7"  businessId="${sysId}" sysKey="${expertKey}"  typeId="${typeMap.EXPERT_TITLE_TYPEID}" auto="true"/>
	          <up:show  showId="show3"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_TITLE_TYPEID}"/>
	  	    </td>
	  	    <td  class="info"><i class="red">*</i> 学位证书:</td>
	  	    <td colspan="3">
	  	      <up:upload id="expert4" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7"  businessId="${sysId}" sysKey="${expertKey}"   typeId="${typeMap.EXPERT_DEGREE_TYPEID}" auto="true"/>
	          <up:show showId="show4" groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey}"  typeId="${typeMap.EXPERT_DEGREE_TYPEID}"/>
	  	    </td>
	  	 </tr>
	  	 <tr>
	  	    <td  class="info"><i class="red">*</i> 个人照片:</td>
	  	    <td colspan="3">
	  	      <up:upload id="expert5" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7"  businessId="${sysId}" sysKey="${expertKey}"  typeId="${typeMap.EXPERT_PHOTO_TYPEID}" auto="true"/>
	          <up:show showId="show5" groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_PHOTO_TYPEID}"/>
	  	    </td>
	  	    <td class="info"><i class="red">*</i> 专家申请表：</td>
	   	    <td colspan="3">
	   	       <up:upload id="expert6"  groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_APPLICATION_TYPEID}" auto="true"/>
			   <up:show showId="show6"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_APPLICATION_TYPEID}"/>
	   	    </td>
	  	 </tr>
	  	 <tr>
	   	    <td  class="info"><i class="red">*</i> 专家合同书：</td>
	   	    <td colspan="5">
	   	       <up:upload id="expert7" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_CONTRACT_TYPEID}" auto="true"/>
			   <up:show showId="show7"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_CONTRACT_TYPEID}"/>
	   	    </td>
	   	 </tr>
	    </table>
	  </ul>
    </div>
  <div class="padding-top-10 clear">
    <h2 class="count_flow"><i>3</i>采购机构</h2>
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
</div> 
   <div class="tab-pane fade height-450" id="tab-2">   
       <h2 class="list_title">评标专家类型</h2>
    <ul class="ul_list">
	  <div class="margin-bottom-0  categories">
		<ul class="list-unstyled list-flow" style="margin-left: 250px;">
     	  <li class="p0">
		    <span class="">专家类型：</span>
			  <input type="hidden" id="expertsTypeIds" value="${expert.expertsTypeId}">
			  <select name="expertsTypeId" id="expertsTypeId" onchange="typeShow();" class="w178">
		   		 <option value="">-请选择-</option>
		   		 <option <c:if test="${expert.expertsTypeId == '1' }">selected="true"</c:if> value="1">技术</option>
		   		 <option <c:if test="${expert.expertsTypeId == '2' }">selected="true"</c:if> value="2">法律</option>
		   		 <option <c:if test="${expert.expertsTypeId == '3' }">selected="true"</c:if> value="3">商务</option>
			  </select>
		   </li>
         </ul>
         <ul class="" id="ztree" >
  			<div>
		      <div class="col-md-5 title"><span class="star_red fl">*</span>产品服务/分类：</div>
			  <div class="col-md-7 service_list">
				  <c:forEach items="${spList }" var="obj" >
					 <span><input type="checkbox" name="chkItem" onclick="getChildren()" value="${obj.code}" />${obj.name} </span>
			      </c:forEach>
			  </div>
			</div>
			<div id="hwType">
			  <div class="col-md-5 title"><span class="star_red fl">*</span>货物分类：</div>
			  <div class="col-md-7 service_list">
				  <c:forEach items="${hwList }" var="hw" >
					 <span><input type="checkbox" name="chkItem" onclick="getChildren()"  value="${hw.code}" />${hw.name} </span>
			      </c:forEach>
			  </div>
			</div>
 		  </ul>
        </div>
       </ul>
		   <div class="col-md-12 tc">
		      <input class="btn btn-windows edit" type="button" onclick="submitForm();" value="修改">
			  <a class="btn btn-windows back"  onclick="location.href='javascript:history.go(-1);'">返回</a>
		   </div>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>