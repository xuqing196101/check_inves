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
%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<title>专家个人信息</title>
<script type="text/javascript">
    var treeObj;
	var datas;

	var addressId="${expert.address}";
		
	
	function fun(){
		var parentId = $("#addr").val();
		$.ajax({
			url : "${pageContext.request.contextPath}/area/find_by_parent_id.do",
			data:{"id":parentId},
			success:function(obj){
				$("#add").empty();
				//var data = eval('(' + obj + ')');
				$("#add").append("<option value=''>-请选择-</option>");
				$.each(obj,function(i,result){
					
					$("#add").append("<option value='"+result.id+"'>"+result.name+"</option>");
				});
			},
			error:function(obj){
				
			}
			
		});
	}
	   var setting={
				async:{
							//autoParam:["id"],
							enable:true,
							url:"${pageContext.request.contextPath}/category/createtree.do",
							autoParam:["id", "name=n", "level=lv"],  
				            otherParam:{"otherParam":"zTreeAsyncTest"},  
				            dataFilter: filter,  
							dataType:"json",
							type:"post",
						},
						callback:{
					    	onClick:zTreeOnClick,//点击节点触发的事件
					    	//onAsyncSuccess: zTreeOnAsyncSuccess
					    	beforeAsync: beforeAsync,  
			                onAsyncSuccess: onAsyncSuccess  
					    }, 
						data:{
							keep:{
								parent:true,
							},					
							simpleData:{
								enable:true,
								idKey:"id",
								pIdKey:"pId",
								rootPId:0,
							}
					    },
					   check:{
							enable: true,
							chkStyle:"checkbox"
					   }
		  };
	   var listId;
	   $(function(){
		   var parentId;
		 //地区回显和数据显示
			$.ajax({
				url : "${pageContext.request.contextPath}/area/find_by_id.do",
				data:{"id":addressId},
				success:function(obj){
					//alert(JSON.stringify(obj));
					$.each(obj,function(i,result){
						if(addressId == result.id){
							parentId  = result.parentId;
							//alert("上"+parentId);
						$("#add").append("<option selected='true' value='"+result.id+"'>"+result.name+"</option>");
						}else{
							$("#add").append("<option value='"+result.id+"'>"+result.name+"</option>");
						}
						
					});
				}
			});

		   
		   $.ajax({
				url : "${pageContext.request.contextPath}/area/listByOne.do",
				success:function(obj){
					//var data = eval('(' + obj + ')');
					//alert(parentId);
					$.each(obj,function(i,result){
						if(parentId == result.id){
							$("#addr").append("<option selected='true' value='"+result.id+"'>"+result.name+"</option>");
						}else{
						$("#addr").append("<option value='"+result.id+"'>"+result.name+"</option>");
						}
					});
				}
				
			});
		   var id="${expert.id}";
			  $.ajax({
				  url:"${pageContext.request.contextPath}/expert/getCategoryByExpertId.do?expertId="+id,
				  success:function(result){
					  listId=result;
				  },
				  error:function(result){
				  }
			  }); 
		  var expertsTypeId = $("#expertsTypeId").val();
		 // alert(expertsTypeId);
		 if(expertsTypeId==1 || expertsTypeId=="1"){
		 //treeObj=$.fn.zTree.init($("#ztree"),setting,datas);
		treeObj = $.fn.zTree.init($("#ztree"), setting);  
         setTimeout(function(){  
             expandAll("ztree");  
         },1000);//延迟加载  
			 $("#ztree").show();
		 }else{
			 treeObj=$.fn.zTree.init($("#ztree"),setting);
			 $("#ztree").hide();
		 }
	}); 
	   
	   function filter(treeId, parentNode, childNodes) {  
           if (!childNodes) return null;  
           for (var i=0, l=childNodes.length; i<l; i++) {  
               childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');  
           }  
           return childNodes;  
       }  
 
       function beforeAsync() {  
           curAsyncCount++;  
       }  
         
       function onAsyncSuccess(event, treeId, treeNode, msg) {  
           curAsyncCount--;  
           if (curStatus == "expand") {  
               expandNodes(treeNode.children);  
           } else if (curStatus == "async") {  
               asyncNodes(treeNode.children);  
           }  
 
           if (curAsyncCount <= 0) {  
               curStatus = "";  
           }  
       }  
 
       var curStatus = "init", curAsyncCount = 0, goAsync = false;  
       function expandAll() {  
           if (!check()) {  
               return;  
           }  
           var zTree = $.fn.zTree.getZTreeObj("ztree");  
           expandNodes(zTree.getNodes());  
           if (!goAsync) {  
               curStatus = "";  
           }  
       }  
       function expandNodes(nodes) {  
           if (!nodes) return;  
           curStatus = "expand";  
           var zTree = $.fn.zTree.getZTreeObj("ztree");  
           for (var i=0, l=nodes.length; i<l; i++) {
        	  if(listId.length>0){
        	   for(var a=0;a<listId.length;a++){
        		   if(listId[a].categoryId==nodes[i].id){
        			   zTree.checkNode(nodes[i], true, true); 
        		   }
        	    }
               }
               zTree.expandNode(nodes[i], true, false, false);//展开节点就会调用后台查询子节点 
                if (nodes[i].isParent && nodes[i].zAsync) {  
                   expandNodes(nodes[i].children);//递归  
               } else {  
                   goAsync = true;  
               }  
           }  
       }  
 
       function check() {  
           if (curAsyncCount > 0) {  
               return false;  
           }  
           return true;  
       }  
 
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg){
        var nodes = treeNode.children;
 
        for(var i=0;i<nodes.length;i++){
            treeObj.expandNode(nodes[i],true,false,true,true);
        }
 
    }
	

	var treeid=null;
	/*树点击事件*/
	function zTreeOnClick(event,treeId,treeNode){
		treeid=treeNode.id
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
		var Obj=$.fn.zTree.getZTreeObj("ztree");  
	     var nodes=Obj.getCheckedNodes(true);  
	     var ids = new Array();  
	     for(var i=0;i<nodes.length;i++){ 
	    	 if(!nodes[i].isParent){
	        //获取选中节点的值  
	         ids.push(nodes[i].id); 
	    	 }
	     } 
	     $("#categoryId").val(ids);
		$("#form1").submit();
	}
	
</script>
</head>
<body>
  <div class="wrapper">
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">业务管理</a></li><li><a href="javascript:void(0)">评标专家信息修改</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <!-- 项目戳开始 -->
		<div class="container clear margin-top-30">
		<!--详情开始-->
		<div class="container content height-350">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<ul class="nav nav-tabs bgdd">
							<li class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="s_news f18">基本信息</a></li>
							<li class="">	   <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="fujian f18">专家类型</a></li>
						</ul>
<!-- 修改订列表开始-->
   <div class="container">
     	<%-- <div style="margin-left: 1000px;">
   		  <img style="width: 80px; height: 100px;" alt="个人照片" src="ftp://${username }:${password }@${host }:${port }/expertFile/${filename }">
        </div> --%>
   <form action="${pageContext.request.contextPath}/expert/edit.html"  method="post" id="form1"  class="registerform"> 
   		<%
			session.setAttribute("tokenSession", tokenValue);
		%>
   <input type="hidden" name="categoryId" id="categoryId">
   <input type="hidden"  name="token2" value="<%=tokenValue%>">
   <input type="hidden" name="id" value="${expert.id }">
   <input type="hidden" name="isPass" id="isPass"/>
  <div class="tab-content padding-top-20" style="height: 1000px;">
	<div style="width:1200px;" class="tab-pane fade active in height-450" id="tab-1">
	<div class="headline-v2 clear">
   <h2>个人信息</h2>
   </div>
   <ul class="ul_list">
		<li class="col-md-3 margin-0 padding-0"><span class="col-md-12 padding-left-5"><i class="red">*</i> 专家姓名：</span>
			<div class="input-append">
				<input class="span5" id="relName" name="relName" value="${expert.relName }"   type="text">
			    <span class="add-on">i</span>
			</div>
		</li>
		<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"> 出生日期：</span>
			<div class="input-append">
										 <input class="span5 Wdate"   readonly="readonly" value="<fmt:formatDate type='date' value='${expert.birthday }' dateStyle="default" pattern="yyyy-MM-dd"/>" name="birthday" id="birthday" type="text" onclick='WdatePicker()'>
								<span class="add-on">i</span>
								</div>
		</li>
		
		
		
		<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"><i class="red">*</i>证件号码：</span>
			<div class="input-append">
				 <input class="span5" maxlength="30" value="${expert.idNumber }"  name="idNumber" id="idNumber" type="text">
									<span class="add-on">i</span>
									</div>
		</li>
		
		<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"><i class="red">*</i>民族：</span>
			<div class="input-append">
			<input class="span5" maxlength="10" value="${expert.nation }"  name="nation" id="nation" type="text">
			<span class="add-on">i</span>
			</div>
		</li>
		
		<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"><i class="red">*</i> 性别：</span>
			<div class="select_common" >
				 <select class="span5" name="gender" id="gender">
				    <option selected="selected" value="">-请选择-</option>
				   	<option <c:if test="${expert.gender eq 'M' }">selected="selected"</c:if> value="M">男</option>
				   	<option <c:if test="${expert.gender eq 'F' }">selected="selected"</c:if> value="F">女</option>
				  </select>
				  <!-- <span class="add-on">i</span> -->
			</div>
		</li>
		
		<li class="col-md-3 margin-0 padding-0"><span class="col-md-12 padding-left-5"><i class="red">*</i>专家来源：</span>
			<div class="select_common" >
				<select class="span5" name="expertsFrom" id="expertsFrom">
				<option selected="selected" value="">-请选择-</option>
			   	<option <c:if test="${expert.expertsFrom eq '军队' }">selected="selected"</c:if> value="军队">军队</option>
			   	<option <c:if test="${expert.expertsFrom eq '地方' }">selected="selected"</c:if> value="地方">地方</option>
			   	<option <c:if test="${expert.expertsFrom eq '其他' }">selected="selected"</c:if> value="其他">其他</option>
			   </select>
			  <!--  <span class="add-on">i</span> -->
			</div>
		</li>
		<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"><i class="red">*</i>省</span>
                                    <div class="select_common">
                                     <select id="addr" onchange="func();">
                                            <option value="">-请选择-</option>
                                     </select>
                                    </div>
                                </li>
                                <li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"><i class="red">*</i>市</span>
                                    <div class="select_common">
                                     <select  name="address" id="add">
                                            <option value="">-请选择-</option>
                                     </select>
                                    </div>
                                </li>
		
		<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"><i class="red">*</i> 证件类型：</span>
			<div class="select_common" >
			<select class="span5" name="idType" id="idType">
		   	<option selected="selected" value="">-请选择-</option>
		   	<option <c:if test="${expert.idType eq '身份证' }">selected="selected"</c:if> value="身份证">身份证</option>
		   	<option <c:if test="${expert.idType eq '士兵证' }">selected="selected"</c:if> value="士兵证">士兵证</option>
		   	<option <c:if test="${expert.idType eq '驾驶证' }">selected="selected"</c:if> value="驾驶证">驾驶证</option>
		   	<option <c:if test="${expert.idType eq '工作证' }">selected="selected"</c:if> value="工作证">工作证</option>
		   	<option <c:if test="${expert.idType eq '护照' }">selected="selected"</c:if> value="护照">护照</option>
		   	<option <c:if test="${expert.idType eq '其他' }">selected="selected"</c:if> value="其他">其他</option>
		   </select>
		   <!-- <span class="add-on">i</span> -->
			</div>
		</li>
		
		
		
		
		
		
		
		<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"><i class="red">*</i>最高学历：</span>
			<div class="select_common" >
			 <select class="span5" name="hightEducation" id="hightEducation" >
			 	<option selected="selected" value="">-请选择-</option>
			   	<option <c:if test="${expert.hightEducation eq '博士' }">selected="selected"</c:if> value="博士">博士</option>
			   	<option <c:if test="${expert.hightEducation eq '硕士' }">selected="selected"</c:if> value="硕士">硕士</option>
			   	<option <c:if test="${expert.hightEducation eq '本科' }">selected="selected"</c:if> value="研究生">本科</option>
			  </select>
			  <!-- <span class="add-on">i</span> -->
			</div>
		</li>
		
		
		
		<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5">政治面貌：</span>
			<div class="select_common" >
				<select class="span5" name="politicsStatus" id="politicsStatus">
				<option selected="selected" value="">-请选择-</option>
			   	<option <c:if test="${expert.politicsStatus eq '党员' }">selected="selected"</c:if> value="党员">党员</option>
			   	<option <c:if test="${expert.politicsStatus eq '团员' }">selected="selected"</c:if> value="团员">团员</option>
			   	<option <c:if test="${expert.politicsStatus eq '群众' }">selected="selected"</c:if> value="群众">群众</option>
			   	<option <c:if test="${expert.politicsStatus eq '其他' }">selected="selected"</c:if> value="其他">其他</option>
			   </select>
			  <!--  <span class="add-on">i</span> -->
			   </div>
		</li>
		
		<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"><i class="red">*</i>毕业院校：</span>
			<div class="input-append">
			<input class="span5" maxlength="40" value="${expert.graduateSchool }"  name="graduateSchool" id="graduateSchool" type="text">
			<span class="add-on">i</span>
			</div>
		</li>
		<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"> 专家技术职称：</span>
			<div class="input-append">
			<input class="span5" maxlength="20" value="${expert.professTechTitles }"  name="professTechTitles" id="professTechTitles" type="text">
			<span class="add-on">i</span>
			</div>
		</li>
		<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"> 参加工作时间：</span>
			<div class="input-append">
			<input class="span5 Wdate"   readonly="readonly" value="<fmt:formatDate type='date' value='${expert.timeToWork }' dateStyle="default" pattern="yyyy-MM-dd"/>" name="timeToWork" id="appendedInput" type="text" onclick='WdatePicker()'>
			<span class="add-on">i</span>
			</div>
		</li>
		
		<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"><i class="red">*</i>专业：</span>
			<div class="input-append">
			<input class="span5" maxlength="20" value="${expert.major }"  name="major" id="major" type="text">
			<span class="add-on">i</span>
			</div>
		</li>
		<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"> 从事专业起始年度：</span>
			<div class="input-append">
			 <input class="span5 Wdate" value="<fmt:formatDate type='date' value='${expert.timeStartWork }' dateStyle="default" pattern="yyyy-MM-dd"/>"  readonly="readonly" name="timeStartWork" id="timeStartWork" type="text" onclick='WdatePicker()'>
			<span class="add-on">i</span>
			</div>
		</li>
		<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5">工作单位：</span>
			<div class="input-append">
			<input class="span5" maxlength="40" value="${expert.workUnit }"  name="workUnit" id="workUnit" type="text">
			<span class="add-on">i</span>
			</div>
		</li>
		<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"><i class="red">*</i>单位地址：</span>
			<div class="input-append">
			 <input class="span5" maxlength="40" value="${expert.unitAddress }"  name="unitAddress" id="unitAddress" type="text">
			<span class="add-on">i</span>
			</div>
		</li>
		<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"><i class="red">*</i>联系电话（固话）：</span>
			<div class="input-append">
			<input class="span5" maxlength="15" value="${expert.telephone }"  name="telephone" id="telephone" type="text">
			<span class="add-on">i</span>
			</div>
		</li>
		<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"><i class="red">*</i>手机：</span>
			<div class="input-append">
			<input class="span5" maxlength="15" value="${expert.mobile }"  name="mobile" id="mobile" type="text">
			<span class="add-on">i</span>
			</div>
		</li>
		<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"> 传真：</span>
			<div class="input-append">
			<input class="span5" maxlength="10"  value="${expert.fax }"  name="fax" id="fax" type="text">
			<span class="add-on">i</span>
			</div>
		</li> 
								<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"> 邮编：</span>
			<div class="input-append">
			<input class="span5" maxlength="6" value="${expert.postCode }"  name="postCode" id="postCode" type="text">
			<span class="add-on">i</span>
			</div>
		</li>
		<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"> 获得技术时间：</span>
			<div class="input-append">
			<input class="span5 Wdate" value="<fmt:formatDate type='date' value='${expert.makeTechDate }' dateStyle="default" pattern="yyyy-MM-dd"/>"  readonly="readonly" name="makeTechDate" id="makeTechDate" type="text" onclick='WdatePicker()'>
			<span class="add-on">i</span>
			</div>
		</li>
		<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"> 学位：</span>
			<div class="input-append">
			<input class="span5" maxlength="10" value="${expert.degree }"  name="degree" id="degree" type="text">
			<span class="add-on">i</span>
			</div>
		</li>
		<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"><i class="red">*</i>健康状态：</span>
			<div class="input-append">
			<input class="span5" maxlength="10" value="${expert.healthState }"  name="healthState" id="healthState" type="text">
			<span class="add-on">i</span>
			</div>
		</li>
		<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"> 现任职务：</span>
			<div class="input-append">
			<input class="span5" maxlength="10" value="${expert.atDuty }"  name="atDuty" id="appendedInput" type="text">
			<span class="add-on">i</span>
			</div>
		</li>
	</ul>
  <!-- 附件信息-->
  <div class="padding-top-10 clear">
   <div class="headline-v2 clear">
   <h2>附件信息</h2>
   </div>
    <table class="table table-bordered">
	  	<tr>
	  	   <td width="" class="info"><i class="red">*</i>身份证:</td>
	  	   <td>
	  	      <up:upload id="expert1"  groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_IDNUMBER_TYPEID }"   auto="true"/>
	          <up:show showId="show1"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_IDNUMBER_TYPEID }"/>
	  	   </td>
	  	   
	  	   <td width="" class="info"><i class="red">*</i>学历证书:</td>
	  	   <td>
	  	      <up:upload id="expert2" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7"  businessId="${sysId }" sysKey="${expertKey }"  typeId="${typeMap.EXPERT_ACADEMIC_TYPEID }" auto="true"/>
	          <up:show showId="show2"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_ACADEMIC_TYPEID }"/>
	  	   </td>
	  	</tr>
	  	<tr>
	  	   <td width="" class="info"><i class="red">*</i>职称证书:</td>
	  	   <td>
	  	      <up:upload id="expert3" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7"  businessId="${sysId }" sysKey="${expertKey }"  typeId="${typeMap.EXPERT_TITLE_TYPEID }" auto="true"/>
	          <up:show  showId="show3"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_TITLE_TYPEID }"/>
	  	   </td>
	  	   <td width="" class="info"><i class="red">*</i>学位证书:</td>
	  	   <td>
	  	      <up:upload id="expert4" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7"  businessId="${sysId }" sysKey="${expertKey }"   typeId="${typeMap.EXPERT_DEGREE_TYPEID }" auto="true"/>
	          <up:show showId="show4" groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }"  typeId="${typeMap.EXPERT_DEGREE_TYPEID }"/>
	  	   </td>
	  	</tr>
	  	<tr>
	  	   <td width="" class="info"><i class="red">*</i>个人照片:</td>
	  	   <td>
	  	      <up:upload id="expert5" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7"  businessId="${sysId }" sysKey="${expertKey }"  typeId="${typeMap.EXPERT_PHOTO_TYPEID }" auto="true"/>
	          <up:show showId="show5" groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_PHOTO_TYPEID }"/>
	  	   </td>
	  	   <td width="" class="info"><i class="red">*</i>专家申请表：</td>
	   	    <td>
	   	       <up:upload id="expert6"  groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_APPLICATION_TYPEID }" auto="true"/>
			   <up:show showId="show6"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_APPLICATION_TYPEID }"/>
	   	    </td>
	  	</tr>
	  	<tr>
	   	    <td width="" class="info"><i class="red">*</i>专家合同书：</td>
	   	    <td>
	   	       <up:upload id="expert7" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_CONTRACT_TYPEID }" auto="true"/>
			   <up:show showId="show7"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_CONTRACT_TYPEID }"/>
	   	    </td>
	   	    <td></td><td></td>
	   	 </tr>
	  </table>
  <div class="padding-top-10 clear">
   <div class="headline-v2 clear">
   <h2>采购机构</h2>
   </div>
   <table class="table table-condensed" >
  	<tr>
		<th>采购机构名称：</th><td>${purchase.name }</td>
		<th>采购机构联系人：</th><td>${purchase.princinpal }</td>
		<th>采购机构地址：</th><td>${purchase.detailAddr }</td>
		<th>联系电话：</th><td>${purchase.mobile }</td>
	</tr>
	</table>
	</ul>
	 <div  class="col-md-12">
   <div class="fl padding-10">
    <input class="btn btn-windows edit" type="button" onclick="getChildren();" value="修改">
	<a class="btn btn-windows back"  onclick="location.href='javascript:history.go(-1);'">返回</a>
	</div>
  </div>
  </div>
  </div>
  </div> 
   <div class="tab-pane fade height-450" id="tab-2">      
    <h2 class="count_flow"><i>1</i>评标专家类型</h2>
    <ul class="ul_list">
		<div class="margin-bottom-0  categories">
		 <ul class="list-unstyled list-flow" style="margin-left: 250px;">
     		<li class="p0">
			   <span class="">专家类型：</span>
			   <input type="hidden" id="expertsTypeIds" value="${expert.expertsTypeId }">
			   <select name="expertsTypeId" id="expertsTypeId" onchange="typeShow();" class="w178">
			   		<option value="">-请选择-</option>
			   		<option <c:if test="${expert.expertsTypeId == '1' }">selected="true"</c:if> value="1">技术</option>
			   		<option <c:if test="${expert.expertsTypeId == '2' }">selected="true"</c:if> value="2">法律</option>
			   		<option <c:if test="${expert.expertsTypeId == '3' }">selected="true"</c:if> value="3">商务</option>
			   </select>
			 </li>
        </ul>
         <div id="ztree" class="ztree" style="margin-left: 260px;"></div>
       </ul>
        <div  class="col-md-12">
   <div class="fl padding-10">
    <input class="btn btn-windows edit" type="button" onclick="getChildren();" value="修改">
	<a class="btn btn-windows back"  onclick="location.href='javascript:history.go(-1);'">返回</a>
	</div>
  </div>
		</div>
	</div>
  </div>
  <br/><br/><br/><br/>
                </div>
 	          </div>
		   </div>
		 </div>
	  </div>
	</div>
</body>
</html>