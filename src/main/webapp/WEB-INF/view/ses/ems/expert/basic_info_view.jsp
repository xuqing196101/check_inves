<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tld/upload" prefix="up"%>
<jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//表单标示
	String tokenValue= new Date().getTime()+UUID.randomUUID().toString()+""; 
%>
<title>评审专家基本信息</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link href="${pageContext.request.contextPath}/public/ZHQ/css/common.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHQ/css/bootstrap.min.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHQ/css/style.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHQ/css/line-icons.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHQ/css/app.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHQ/css/application.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHQ/css/header-v4.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHQ/css/footer-v2.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHQ/css/img-hover.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHQ/css/page_job.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHQ/css/shop.style.css" media="screen" rel="stylesheet">

<link href="${pageContext.request.contextPath}/public/backend/css/unify.css" media="screen" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/backend/css/global.css" media="screen" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/backend/css/btn.css" media="screen" rel="stylesheet" type="text/css">

<script type="text/javascript" src="${pageContext.request.contextPath}/public/My97DatePicker/WdatePicker.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHQ/js/expert/validate_expert_basic_info.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}//public/ztree/css/zTreeStyle.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.exedit.js"></script>
<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="application"/> 
<script>
	var globalPath = "${contextPath}";
</script>
<script type="text/javascript">
	    var treeObj;
		var datas;
		var parentId ;
		var addressId="${expert.address}";
		//alert(addressId);
		//地区回显和数据显示
			 window.onload=function(){
				 $.ajax({
						url : "${pageContext.request.contextPath}/area/find_by_id.do",
						data:{"id":addressId},
						success:function(obj){
							//alert(JSON.stringify(obj));
							//var data = eval('(' + obj+ ')');
							$.each(obj,function(i,result){
								if(addressId == result.id){
									parentId = result.areaType;
								$("#hou").append(result.name);
								$("#haha").append(result.name);
								}
							});
						},
						error:function(obj){
						}
					}); 
			 }
		
			 var setting={
						async:{
									enable:true,
									url:"${pageContext.request.contextPath}/category/createtree.do",
									autoParam:["id", "name=n", "level=lv"],  
						            otherParam:{"otherParam":"zTreeAsyncTest"},  
						            dataFilter: filter,  
									dataType:"json",
									type:"post"
								},
								callback:{
							    	onClick:zTreeOnClick,//点击节点触发的事件
							    	//onAsyncSuccess: zTreeOnAsyncSuccess
							    	beforeAsync: beforeAsync,  
					                onAsyncSuccess: onAsyncSuccess,
					                beforeCheck: zTreeBeforeCheck
							    }, 
								data:{
									keep:{
										parent:true
									},					
									simpleData:{
										enable:true,
										idKey:"id",
										pIdKey:"pId",
										rootPId:0
									}
							    },
							   check:{
									enable: true,
									chkStyle:"checkbox"
							   }
				  };
		   var listId;
	$(function() {
		
		
		//采购机构
		var sup = $("#purchaseDepId").val();
		   var radio=document.getElementsByName("purchaseDepId");
		   for(var i=0;i<radio.length;i++){
		 		if(sup==radio[i].value){
		 			radio[i].checked=true;
		 	 		break;
		 		}
		   }
		   //回显已选品目树
		   var id="${expert.id}";
			  $.ajax({
				  url:"${pageContext.request.contextPath}/expert/getCategoryByExpertId.do?expertId="+id,
				  success:function(result){
					  listId=result;
				  },
				  error:function(result){
					  alert("出错啦！");
				  }
			  }); 
			  var expertsTypeId = $("#expertsTypeId").val();
			  //控制品目树的显示和隐藏
				 if(expertsTypeId==1 || expertsTypeId=="1"){
				 //treeObj=$.fn.zTree.init($("#ztree"),setting,datas);
				treeObj = $.fn.zTree.init($("#ztree"), setting);
				//延迟加载 
		         setTimeout(function(){
		        	 expandAll("ztree");
		        	 },500);
					 $("#ztree").show();
				 }else{
					 treeObj=$.fn.zTree.init($("#ztree"),setting,datas);
					 $("#ztree").hide();
				 }
			  
				//地区
					$.ajax({
						url : "${pageContext.request.contextPath}/area/listByOne.do",
						success:function(obj){
							var data = eval('(' + obj + ')');
							//alert(data);
							$.each( data,function(i,result){
								 if(parentId == result.id){
									$("#addr").append(result.name+",");
									$("#qian").append(result.name+",");
								}
							});
							//alert(JSON.stringify(obj));
						},
						error:function(obj){
						}
						
					});
					
			  
			  
			}); 
	function filter(treeId, parentNode, childNodes) {  
        if (!childNodes) {return null;}  
        for (var i=0, l=childNodes.length; i<l; i++) {  
            childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');  
        }  
        return childNodes;  
    }  
    function beforeAsync(){  
        curAsyncCount++;  
    }  
    function zTreeBeforeCheck(treeId, treeNode) {
	    return false;
	};
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
    var curStatus = "init"; var curAsyncCount = 0; var goAsync = false;  
    function expandAll(){  
        if (!check()){  
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
     	   for(var a=0;a<listId.length;a++){
     		   if(listId[a].categoryId==nodes[i].id){
     			   zTree.checkNode(nodes[i], true, true); 
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
    function check(){  
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
		treeid=treeNode.id;
		
    }
    function func(){
		var parentIds = $("#addr").val();
		$.ajax({
			url : "${pageContext.request.contextPath}/area/find_area_by_parent_id.do",
			data:{"id":parentIds},
			success:function(obj){
				$("#haha").empty();
				//var data = eval('(' + obj + ')');
				$("#haha").append("<option  value=''>-请选择-</option>");
				$.each(obj,function(i,result){
					$("#haha").append("<option value='"+result.id+"'>"+result.name+"</option>");
				});
			
				//alert(JSON.stringify(obj));
			},
			error:function(obj){
				
			}
			
		});
	}
    
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
			  },
			  error:function(result){
				  alert("出错啦！");
			  }
		});
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
	}
		/** 专家完善注册信息页面 */
	function supplierRegist(name, i, position) {
		  
		
		var t = null;
		var l = null;
		if (position == "pre") {
			t = name + "_" + i;
			l = name + "_" + (i - 1);
		}
		if (position == "next") {
			t = name + "_" + i;
			l = name + "_" + (i + 1);
		}
		$("#" + t).hide();
		$("#" + l).show();
		
	}
	function pre(name, i, position) {
		var t = null;
		var l = null;
		if (position == "pre") {
			t = name + "_" + i;
			l = name + "_" + (i - 1);
		}
		if (position == "next") {
			t = name + "_" + i;
			l = name + "_" + (i + 1);
		}
		$("#zancun").val(0);
		$("#" + t).hide();
		$("#" + l).show();
	}

	
	function fun(){
		supplierRegist('reg_box_id', 3, 'next'); 
	}
	function fun1(){
		//选中的子节点
		getChildren();
		supplierRegist('reg_box_id', 4, 'next');
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
	//下载
	function downloadTable(){
		$("#form1").attr("action","${pageContext.request.contextPath}/expert/download.html");
		$("#form1").submit();
	}
	//页签点击跳转
	function tab1(){
		
		$("#reg_box_id_3").show();
		$("#reg_box_id_4").hide();
		$("#reg_box_id_5").hide();
		$("#reg_box_id_6").hide();
		$("#reg_box_id_7").hide();
		$("#reg_box_id_8").hide();
	}
	function tab2(){
		$("#reg_box_id_3").hide();
		$("#reg_box_id_4").show();
		$("#reg_box_id_5").hide();
		$("#reg_box_id_6").hide();
		$("#reg_box_id_7").hide();
		$("#reg_box_id_8").hide();
	}
	function tab3(){
		$("#reg_box_id_3").hide();
		$("#reg_box_id_4").hide();
		$("#reg_box_id_5").show();
		$("#reg_box_id_6").hide();
		$("#reg_box_id_7").hide();
		$("#reg_box_id_8").hide();
	}
	function tab4(){
		$("#reg_box_id_3").hide();
		$("#reg_box_id_4").hide();
		$("#reg_box_id_5").hide();
		$("#reg_box_id_6").show();
		$("#reg_box_id_7").hide();
		$("#reg_box_id_8").hide();
	}
	function tab5(){
		$("#reg_box_id_3").hide();
		$("#reg_box_id_4").hide();
		$("#reg_box_id_5").hide();
		$("#reg_box_id_6").hide();
		$("#reg_box_id_7").show();
		$("#reg_box_id_8").hide();
	}
	//回显采购机构信息
	function addPurList(){
	var radio = $("input[type='radio']:checked");
	var depName = $(radio).parent().next().next().children().val();
	var person =  $(radio).parent().next().next().next().children().val();
	var address = $(radio).parent().next().next().next().next().children().val();
	var phone =  $(radio).parent().next().next().next().next().next().children().val();
	$("#depName_").text(depName);
	$("#person_").text(person);
	$("#address_").text(address);
	$("#phone_").text(phone);
	$("#depName_2").text(depName);
	$("#person_2").text(person);
	$("#address_2").text(address);
	$("#phone_2").text(phone);
	supplierRegist('reg_box_id', 5, 'next'); 
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
	
	
	
	
</script>

</head>

<body>
		<form id="form1" action="${pageContext.request.contextPath}/expert/add.html" method="post">
		<input type="hidden" name="userId" value="${user.id }">
		<input type="hidden" id="purchaseDepId" value="${expert.purchaseDepId }">
		<input type="hidden" name="id" id="id" value="${expert.id }">
		<input type="hidden" name="zancun" id="zancun">
		<input type="hidden" name="sysId" value="${sysId }">
		<%
			session.setAttribute("tokenSession", tokenValue);
		%>
		<input type="hidden" id="categoryId" name="categoryId" value="">
		 <input type="hidden"  name="token2" value="<%=tokenValue%>">
		<div id="reg_box_id_3" class="container clear margin-top-30 job-content">
			<h2 class="padding-20 mt40">
					<!-- <span class="new_step current fl"><i class="">1</i><div class="line"></div> <span class="step_desc_01">用户名密码</span> </span> -->
					<span id="" class="new_step current fl" onclick="tab1();"><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
					<span  class="new_step current fl"   onclick="tab2();"><i class="">2</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span>
					<span class="new_step current fl" onclick="tab3();"><i class="">3</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
					<span class="new_step current fl" onclick="tab4();"><i class="">4</i><div class="line"></div> <span class="step_desc_01">下载申请表</span> </span> 
					<span class="new_step current fl" onclick="tab5();"><i class="">5</i> <span class="step_desc_02">上传申请表</span> </span> 
				<div class="clear"></div>
			</h2>
		<div class="container content height-350">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<div class="tab-content padding-top-20  h900">
							<div class="tab-pane fade active in height-500"  id="tab-1">
								<div class=" margin-bottom-0"><br/>
									<h2 class="f16 jbxx">
										<i>01</i>专家基本信息
									</h2>
	<ul class="ul_list">
	<table class="table table-bordered">
	  <tbody>
	              <tr>
				    <td width="25%" class="info">联系电话（固话）：</td>
				    <td width="25%">${expert.telephone }</td>
				    <td width="25%" class="info">单位地址：</td>
				    <td width="25%">${expert.unitAddress }</td>
				  </tr>
				  <tr>
				    <td width="25%" class="info">姓名：</td>
				    <td width="25%">${expert.relName }</td>
				    <td width="25%" class="info">手机：</td>
				    <td width="25%">${expert.mobile }</td>
				  </tr>
				  <tr>
				    <td width="25%" class="info">性别：</td>
				    <td width="25%"> 
				      <c:if test="${expert.gender eq 'M' }">男</c:if>
	     			  <c:if test="${expert.gender eq 'F' }">女</c:if>
	     			</td>
				    <td width="25%" class="info">出生日期：：</td>
				    <td width="25%"><fmt:formatDate type='date' value='${expert.birthday }' dateStyle="default" pattern="yyyy-MM-dd"/></td>
				  </tr>
				  <tr>
				    <td width="25%" class="info">政治面貌：</td>
				    <td width="25%">${expert.politicsStatus }</td>
				    <td width="25%" class="info">专家来源：</td>
				    <td width="25%">${expert.expertsFrom }</td>
				    
				  </tr>
				  <tr>
				    <td width="25%" class="info">证件类型：</td>
				    <td width="25%">${expert.idType }</td>
				    <td width="25%" class="info">证件号码：</td>
				    <td width="25%">${expert.idNumber}</td>
				  </tr>
				  <tr>
				    <td width="25%" class="info">所在地区：</td>
				    <td width="25%">
				      <font id="addr"></font><font id="haha"></font>
				    </td>
				    <td width="25%" class="info">民族：</td>
				    <td width="25%">${expert.nation}</td>
				  </tr>
				  <tr>
				    <td width="25%" class="info">毕业院校：</td>
				    <td width="25%">${expert.graduateSchool }</td>
				    <td width="25%" class="info">专家技术职称：</td>
				    <td width="25%">${expert.professTechTitles}</td>
				  </tr>
				  <tr>
				    <td width="25%" class="info">参加工作时间：</td>
				    <td width="25%"><fmt:formatDate type='date' value='${expert.timeToWork }' dateStyle="default" pattern="yyyy-MM-dd"/></td>
				    <td width="25%" class="info">最高学历：</td>
				    <td width="25%">${expert.hightEducation}</td>
				  </tr>
				   <tr>
				    <td width="25%" class="info">专业：</td>
				    <td width="25%">${expert.major }</td>
				    <td width="25%" class="info">从事专业年度：</td>
				    <td width="25%"><fmt:formatDate type='date' value='${expert.timeStartWork }' dateStyle="default" pattern="yyyy-MM-dd"/></td>
				  </tr>
				   <tr>
				    <td width="25%" class="info">工作单位：</td>
				    <td width="25%">${expert.workUnit }</td>
				    <td width="25%" class="info">传真：</td>
				    <td width="25%">${expert.fax }</td>
				  </tr>
				   <tr>
				    <td width="25%" class="info">邮政编码：</td>
				    <td width="25%">${expert.postCode }</td>
				    <td width="25%" class="info">取得技术时间：</td>
				    <td width="25%"><fmt:formatDate type='date' value='${expert.makeTechDate }' dateStyle="default" pattern="yyyy-MM-dd"/></td>
				  </tr>
				   <tr>
				    <td width="25%" class="info">学位：</td>
				    <td width="25%">${expert.degree }</td>
				    <td width="25%" class="info">健康状态：</td>
				    <td width="25%">${expert.healthState }</td>
				  </tr>
				   <tr>
				    <td width="25%" class="info">现任职务：</td>
				    <td width="25%">${expert.atDuty }</td>
				    <td width="25%" class="info"></td>
				    <td width="25%"></td>
				  </tr>
		</tbody>
	 </table>
	</ul>
									</div>
									<!-- 附件信息-->
										  <div class="padding-top-10 clear">
										   <div class="headline-v2 clear">
										   <h2>上传附件</h2>
										   </div>
										  </div>
										  <div class="padding-left-30 padding-right-20 clear">
										  <table class="table table-bordered">
										  	<tr>
										  	   <td width="25%" class="info"><i class="red">＊</i>身份证:</td>
										  	   <td>
										          <up:show  delete="false" showId="show1"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_IDNUMBER_TYPEID }"/>
										  	   </td>
										  	   <td width="25%" class="info"><i class="red">＊</i>学历证书:</td>
										  	   <td>
										          <up:show  delete="false" showId="show2"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_ACADEMIC_TYPEID }"/>
										  	   </td>
										  	</tr>
										  	<tr>
										  	   <td width="25%" class="info"><i class="red">＊</i>职称证书:</td>
										  	   <td>
										          <up:show  delete="false"  showId="show3"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_TITLE_TYPEID }"/>
										  	   </td>
										  	   <td width="25%" class="info"><i class="red">＊</i>学位证书:</td>
										  	   <td>
										          <up:show  delete="false" showId="show4"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }"  typeId="${typeMap.EXPERT_DEGREE_TYPEID }"/>
										  	   </td>
										  	</tr>
										  	<tr>
										  	   <td width="25%" class="info"><i class="red">＊</i>个人照片:</td>
										  	   <td>
										          <up:show  delete="false" showId="show5"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_PHOTO_TYPEID }"/>
										  	   </td>
										  	   <td></td><td></td>
										  	</tr>  
										  </table>
										   </div>
									<div class="tc mt20 clear col-md-11">
									        <!-- <button class="btn btn-windows git" onclick="submitForm1();"  type="button">暂存</button> -->
											<button class="btn btn-windows git" id="nextBind"  type="button" onclick="fun();" >下一步</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div id="reg_box_id_4" class="container clear margin-top-30 yinc" style="display: none;">
		  		<h2 class="padding-20 mt40">
					<!-- <span class="new_step current fl"><i class="">1</i><div class="line"></div> <span class="step_desc_01">用户名密码</span> </span> -->
					<span class="new_step current fl"  onclick="tab1();"><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
					<span  class="new_step current fl"   onclick="tab2();"><i class="">2</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span>
					<span class="new_step current fl" onclick="tab3();"><i class="">3</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
					<span class="new_step current fl" onclick="tab4();"><i class="">4</i><div class="line"></div> <span class="step_desc_01">下载申请表</span> </span> 
					<span class="new_step current fl" onclick="tab5();"><i class="">5</i> <span class="step_desc_02">上传申请表</span> </span> 
				<div class="clear"></div>
			</h2>
			<br/>
		    <h2 class="f16 jbxx">
			<i>02</i>专家类型
			</h2>
			<ul class="list-unstyled list-flow" style="margin-left: 250px;">
			 <table>
	           <tbody>
	              <tr>
				    <td width="90px;" class="info"> <h4>专家类型：</h4></td> 
				    <td width="100px;">
				      <h4>
				      <c:if test="${expert.expertsTypeId eq '1' }">技术</c:if>
				      <c:if test="${expert.expertsTypeId eq '2' }">法律</c:if>
				      <c:if test="${expert.expertsTypeId eq '3' }">商务</c:if>
				      </h4>
				    </td>
				  </tr>
				</tbody>
		    </table> 
		 <div style="display: none;">
     		<li class="p0">
			   <span class="col-md-12 padding-left-5">专家类型：</span>
			   <input type="hidden" id="expertsTypeIds" value="" >
			   <select name="expertsTypeId" id="expertsTypeId" onchange="typeShow();">
			   		<option value="">-请选择-</option>
			   		<option <c:if test="${expert.expertsTypeId == '1' }">selected="true"</c:if> value="1">技术</option>
			   		<option <c:if test="${expert.expertsTypeId == '2' }">selected="true"</c:if> value="2">法律</option>
			   		<option <c:if test="${expert.expertsTypeId == '3' }">selected="true"</c:if> value="3">商务</option>
			   </select>
			 </li>
		 </div>
   			 </ul>
   			   <div id="ztree" style="margin-left: 100px;" class="ztree"></div>
		    <div class="tc mt20 clear col-md-11">
				<button class="btn btn-windows back"   type="button" onclick="pre('reg_box_id', 4, 'pre')">上一步</button>
				<!-- <button class="btn btn-windows git" onclick="submitForm1();"  type="button">暂存</button> -->
				<button class="btn btn-windows git"   type="button" onclick="fun1();">下一步</button>
			</div>
		</div>
		
		<!-- 项目戳开始 -->
		<div id="reg_box_id_5" class="container clear margin-top-30 yinc" style="display: none;">
		  		<h2 class="padding-20 mt40">
					<!-- <span class="new_step current fl"><i class="">1</i><div class="line"></div> <span class="step_desc_01">用户名密码</span> </span> -->
					<span class="new_step current fl"  onclick="tab1();"><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
					<span  class="new_step current fl"   onclick="tab2();"><i class="">2</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span>
					<span class="new_step current fl" onclick="tab3();"><i class="">3</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
					<span class="new_step current fl" onclick="tab4();"><i class="">4</i><div class="line"></div> <span class="step_desc_01">下载申请表</span> </span> 
					<span class="new_step current fl" onclick="tab5();"><i class="">5</i> <span class="step_desc_02">上传申请表</span> </span> 
				<div class="clear"></div>
			</h2><br/>
		      <h2 class="f16 jbxx">
			    <i>03</i>选择采购机构
			  </h2>
			    <h2 class="f16">
				  采购机构
			    </h2>
			<table id="tb1"  class="table table-bordered table-condensed">
			
				<thead>
					<tr>
					  <th class="info w30"><input type="radio"   disabled="disabled"  id="purchaseDepId2" ></th>
					  <th class="info w50">序号</th>
					  <th class="info">采购机构</th>
					  <th class="info">联系人</th>
					  <th class="info">联系地址</th>
					  <th class="info">联系电话</th>
					</tr>
				</thead>
				<c:forEach items="${ purchase}" var="p" varStatus="vs">
					<tr align="center">
						<td><input type="radio" disabled="disabled" name="purchaseDepId" <c:if test="${expert.purchaseDepId eq p.id }">checked</c:if>  value="${p.id }" /></td>
						<td>${vs.count}</td>
						<td><input border="0" readonly="readonly" value="${p.name }" style='border-left:0px;border-top:0px;border-right:0px;border-bottom:1px;text-align: center; '></td>
						<td><input border="0" readonly="readonly" value="${p.princinpal }" style='border-left:0px;border-top:0px;border-right:0px;border-bottom:1px;text-align: center; '></td>
						<td><input border="0" readonly="readonly" value="${p.detailAddr }" style='border-left:0px;border-top:0px;border-right:0px;border-bottom:1px;text-align: center; '></td>
						<td><input border="0" readonly="readonly" value="${p.mobile }" style='border-left:0px;border-top:0px;border-right:0px;border-bottom:1px;text-align: center; '></td>
					</tr>
				</c:forEach> 
			</table>
			 <!-- <h2 class="f16 jbxx">
				其他采购机构
			</h2>
			<table id="tb2" class="table table-bordered table-condensed">
				<thead>
					<tr>
					  <th class="info w30"><input type="radio" disabled="disabled"  alt=""></th>
					  <th class="info w50">序号</th>
					  <th class="info">采购机构</th>
					  <th class="info">联系人</th>
					  <th class="info">联系地址</th>
					  <th class="info">联系电话</th>
					</tr>
				</thead> -->
				<%-- <c:forEach items="" var="" varStatus="vs">
					<tr>
						<td><input type="checkbox"  name="cbox" onclick="box(this)" /></td>
						<td>${(l-1)*10+vs.index+1}</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</c:forEach> --%>
				<!-- <tr>
				  <td class="tc w30"><input type="radio" name="purchaseDepId" id="checked" alt="" value="3"></td>
				  <td class="tc w50">1</td>
				  <td class="tc"><input border="0" disabled="disabled" value="哈哈"></td>
				  <td class="tc"><input border="0" disabled="disabled" value="飒飒"></td>
				  <td class="tc"><input border="0" disabled="disabled" value="北京"></td>
				 <td class="tc"><input border="0" disabled="disabled" value="13333333333"></td>
				</tr>
			</table> -->
			<h6>
		               友情提示：请专家记录好初审采购机构的相关信息，以便进行及时沟通
		    </h6>
		    <div class="tc mt20 clear col-md-11">
				<button class="btn btn-windows back"   type="button" onclick="pre('reg_box_id', 5, 'pre')">上一步</button>
				<!-- <button class="btn btn-windows git" onclick="submitForm1();"  type="button">暂存</button> -->
				<button class="btn btn-windows git"   type="button" onclick="addPurList();">下一步</button>
			</div>
		</div>
	<div id="reg_box_id_6" class="container clear margin-top-30 yinc" style="display: none;">
		  <h2 class="padding-20 mt40">
					<!-- <span class="new_step current fl"><i class="">1</i><div class="line"></div> <span class="step_desc_01">用户名密码</span> </span> -->
					<span class="new_step current fl" onclick="tab1();"><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
					<span class="new_step current fl" onclick="tab2();"><i class="">2</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span> 
					<span class="new_step current fl" onclick="tab3();"><i class="">3</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
					<span class="new_step current fl" onclick="tab4();"><i class="">4</i><div class="line"></div> <span class="step_desc_01">下载申请表</span> </span> 
					<span class="new_step current fl" onclick="tab5();"><i class="">5</i> <span class="step_desc_02">上传申请表</span> </span> 
				<div class="clear"></div>
			</h2><br/>
			<h2 class="f16 jbxx">
			<i>04</i>打印专家申请表
			</h2>
			<!-- 供应商申请书上传：<input type="file" name=""/>
			供应商承诺书上传：<input type="file" name=""/> -->
				   	<div ><br/><br/>
<table class="table table-bordered table-condensed">
   	<tr>
   		<td align="center" width="100px">姓名</td>
   		<td align="center" width="150px" id="tName">${expert.relName }</td>
   		<td align="center">性别</td>
		  	<td class="tc" id="tSex" colspan="2">
		  	 <c:if test="${expert.gender eq 'M' }">男</c:if>
	     	 <c:if test="${expert.gender eq 'F' }">女</c:if>
		  	</td>
   		
   		<!-- <td align="center" rowspan="4">照片</td> -->
   	</tr>
   <tr>
   		<td align="center">出生日期</td>
   		<td align="center" width="150px" id="tBirthday"><fmt:formatDate type='date' value='${expert.birthday }' dateStyle="default" pattern="yyyy-MM-dd"/></td>
   		<td align="center">政治面貌</td>
   		<td align="center" width="150px" id="tFace" colspan="2">${expert.politicsStatus }</td>
   </tr>
   <tr>
   		<td align="center">所在地区</td>
   		<td align="center" width="150px" >
   		 <font id="qian"></font><font id="hou"></font>
   		</td>
   		<td align="center">职称</td>
   		<td align="center" width="150px" id="tHey" colspan="2">${expert.professTechTitles}</td>
   </tr>
   <tr>
   		<td align="center">证件号码</td>
   		<td align="center" id="tNumber" colspan="4">${expert.idNumber}</td>
   </tr>
   <tr>
   		<td align="center">从事专业类别</td>
   		<td align="center" id="tExpertsTypeId" width="150px">
   		 <c:if test="${expert.expertsTypeId eq '1' }">技术</c:if>
		 <c:if test="${expert.expertsTypeId eq '2' }">法律</c:if>
		 <c:if test="${expert.expertsTypeId eq '3' }">商务</c:if>
   		</td>
   		<td align="center">从事年限</td>
   		<td align="center" id="tTimeStartWork" colspan="2"><fmt:formatDate type='date' value='${expert.timeStartWork }' dateStyle="default" pattern="yyyy-MM-dd"/></td>
   </tr>
   <tr>
   		<td align="center">最高学历</td>
   		<td align="center" id="tHight" width="150px">${expert.hightEducation}</td>
   		<td align="center">最高学位</td>
   		<td align="center" id="tWei" colspan="2">${expert.degree }</td>
   
   </tr>
   <tr>
   		<td align="center">执业资格1</td>
   		<td align="center" width="150px"> </td>
   		<td align="center">注册证书编号1</td>
   		<td align="center" colspan="2"> </td>
   </tr>
   <tr>
   		<td align="center">执业资格2</td>
   		<td align="center" width="150px"></td>
   		<td align="center">注册证书编号2</td>
   		<td align="center" colspan="2"></td>
   </tr>
   <tr>
   		<td align="center">执业资格3</td>
   		<td align="center" width="150px"></td>
   		<td align="center">注册证书编号3</td>
   		<td align="center" colspan="2"></td>
   </tr>
   <tr>
   		<td align="center">近两年是否接受过评标业务培训</td>
   		<td align="center" width="150px"></td>
   		<td align="center">是否愿意成为应急专家</td>
   		<td align="center" colspan="2"></td>
   </tr>
   <tr>
   		<td align="center">所属行业</td>
   		<td align="center" width="150px"></td>
   		<td align="center">报送部门</td>
   		<td align="center" colspan="2"></td>
   </tr>
   <tr>
   		<td align="center">手机号码</td>
   		<td align="center" id="tMobile" width="150px">${expert.mobile }</td>
   		<td align="center">单位电话</td>
   		<td align="center" id="tTelephone" colspan="2">${expert.telephone }</td>
   </tr>
   <tr>
   		<td align="center">住宅电话</td>
   		<td align="center" width="150px"></td>
   		<td align="center">电子邮箱</td>
   		<td align="center" colspan="2"></td>
   </tr>
   <tr>
   		<td align="center">毕业院校及专业</td>
   		<td align="center" id="tGraduateSchool" colspan="4">${expert.graduateSchool }</td>
   </tr>
   <tr>
   		<td align="center">单位名称</td>
   		<td align="center" id="tWorkUnit" colspan="4">${expert.workUnit }</td>
   </tr>
   <tr>
   		<td align="center">单位地址 </td>
   		<td align="center" id="tUnitAddress" width="150px">${expert.unitAddress }</td>
   		<td align="center">单位邮编</td>
   		<td align="center" id="tPostCode" colspan="2">${expert.postCode }</td>
   </tr>
   <tr>
   		<td align="center">家庭地址 </td>
   		<td align="center" width="150px" ></td>
   		<td align="center">家庭邮编</td>
   		<td align="center" colspan="2"></td>
   </tr>
   <tr>
   		<td align="center">评标专业一</td>
   		<td align="center" colspan="4"></td>
   </tr>
   <tr>
   		<td align="center">评标专业二</td>
   		<td align="center" colspan="4"></td>
   </tr>
   <tr>
   		<td align="center">评标专业三</td>
   		<td align="center" colspan="4"></td>
   </tr>
   <tr>
   		<td align="center">评标专业四</td>
   		<td align="center" colspan="4"></td>
   </tr>
   <tr>
   		<td align="center">评标专业五</td>
   		<td align="center" colspan="4"></td>
   </tr>
   <tr>
   		<td align="center">评标专业六</td>
   		<td align="center" colspan="4"></td>
   </tr>
   <tr>
   		<td align="center" colspan="5">工作经历</td>
   </tr>
   <tr>
   		<td align="center">起止年月</td>
   		<td align="center" colspan="3">单位及职务</td>
   		<td align="center">证明人</td>
   </tr>
   <tr>
   		<td align="center">至 </td>
   		<td align="center" colspan="3"> </td>
   		<td align="center" width="150px"> </td>
   </tr>
   <tr>
   		<td align="center"> 至</td>
   		<td align="center" colspan="3"> </td>
   		<td align="center" width="150px"> </td>
   </tr>
   <tr>
   		<td align="center">至 </td>
   		<td align="center" colspan="3"> </td>
   		<td align="center" width="150px"> </td>
   </tr>
   <tr>
   		<td align="center">至 </td>
   		<td align="center" colspan="3"> </td>
   		<td align="center" width="150px"> </td>
   </tr>
   <tr>
   		<td align="center">至 </td>
   		<td align="center" colspan="3"> </td>
   		<td align="center" width="150px"> </td>
   </tr>
   </table>
		    <div class="tc mt20 clear col-md-11">
		   		<button class="btn btn-windows back"   type="button" onclick="pre('reg_box_id', 6, 'pre')">上一步</button>
				<!-- <button class="btn btn-windows git"   type="button" onclick="window.print()">打印</button> -->
				<!-- <a class="btn btn-windows git" onclick="downloadTable();" href="javascript:void(0)">下载</a> -->
				<!-- <button class="btn btn-windows git" onclick="submitForm1();"  type="button">暂存</button> -->
				<button class="btn btn-windows git"   type="button" onclick="supplierRegist('reg_box_id', 6, 'next')">下一步</button>
			</div>
		</div>
		</div>
		<div id="reg_box_id_7" class="container clear margin-top-30 yinc" style="display: none;">
		 <h2 class="padding-20 mt40">
					<!-- <span class="new_step current fl"><i class="">1</i><div class="line"></div> <span class="step_desc_01">用户名密码</span> </span> -->
					<span class="new_step current fl" onclick="tab1();"><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
					<span class="new_step current fl" onclick="tab2();"><i class="">2</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span> 
					<span class="new_step current fl" onclick="tab3();"><i class="">3</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
					<span class="new_step current fl" onclick="tab4();"><i class="">4</i><div class="line"></div> <span class="step_desc_01">下载申请表</span> </span> 
					<span class="new_step current fl" onclick="tab5();"><i class="">5</i> <span class="step_desc_02">上传申请表</span> </span> 
				<div class="clear"></div>
			</h2><br/>
			<h2 class="f16 jbxx">
			<i>05</i>专家申请表、合同书
			</h2>
				   	<div class="" style="margin-left:100px;">
								<table class="table table-bordered">
				   	  <tr>
				   	    <td width="25%" class="info"><i class="red">＊</i>专家申请表：</td>
				   	    <td>
						   <up:show showId="show6" delete="false" groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_APPLICATION_TYPEID }"/>
				   	    </td>
				   	    <td width="25%" class="info"><i class="red">＊</i>专家合同书：</td>
				   	    <td>
						   <up:show showId="show7" delete="false" groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_CONTRACT_TYPEID }"/>
				   	    </td>
				   	  </tr>
								    
					 </table>
					</div>
			<!-- <div class="col-md-12 add_regist" style="margin-left:170px;">
				 <div class="fl mr20"><label class="regist_name">采购机构名称：</label><span id="depName_" class="regist_desc"></span></div>
				 <div class="fl mr20"><label class="regist_name">采购机构联系人：</label><span id="person_" class="regist_desc"></span></div>
				 <div class="fl mr20"><label class="regist_name">采购机构地址：</label><span id="address_" class="regist_desc"></span></div>
				 <div class="fl mr20"><label class="regist_name">联系电话：</label><span id="phone_" class="regist_desc"></span></div>
			 </div> -->
		    <div class="tc mt20 clear col-md-11">
		   		<button class="btn btn-windows back"   type="button" onclick="pre('reg_box_id', 7, 'pre')">上一步</button>
				<!-- <button class="btn btn-windows git"   type="button" onclick="supplierRegist('reg_box_id', 7, 'next')">下一步</button> -->
			</div>
		</div>
		<%-- <div id="reg_box_id_8" class="container content height-350 yinc" style="display: none;">
		 <div class="row magazine-page pt40 mb40">
		   <div class="login_cl fl col-md-3">
		    <img src="${pageContext.request.contextPath}/public/ZHQ/images/success.jpg"/>
		   </div>
		   <div class="login_cr fl col-md-9 pt20">
		    <div class="col-md-12">
		     <p>
			  <span class="regist_info f18 b">信息填写完成！确认无误后请提交生效，提交后将不能更改！</span>正在等待审核人员审批。
			 </p>
		    </div>
			<div class="col-md-12 add_regist" >
			 <div class="fl mr20"><label class="regist_name">采购机构名称：</label><span id="depName_2" class="regist_desc"></span></div>
			 <div class="fl mr20"><label class="regist_name">采购机构联系人：</label><span id="person_2" class="regist_desc"></span></div>
			 <div class="fl mr20"><label class="regist_name">采购机构地址：</label><span id="address_2" class="regist_desc"></span></div>
			 <div class="fl mr20"><label class="regist_name">联系电话：</label><span id="phone_2" class="regist_desc"></span></div>
			 </div>
			 <div class="mt20 col-md-12">
			  <button class="btn btn-windows back"   type="button" onclick="supplierRegist('reg_box_id', 8, 'pre')">上一步</button>
			  <input  class="btn btn-windows git" type="button" onclick="addSubmitForm();" value="提交" />
			 </div>
			</div>
		   </div>
		 </div> --%>
		</form>
		<div class="footer-v2" id="footer-v2">

      <div class="footer">

            <!-- Address -->
              <address class="">
              Copyright © 2016 版权所有：中央军委后勤保障部 京ICP备09055519号
              </address>
              <div class="">
               浏览本网主页，建议将电脑显示屏的分辨率调为1024*768
              </div> 
            <!-- End Address -->
       </div>
     
<!--/footer--> 
    </div>
</body>
</html>
