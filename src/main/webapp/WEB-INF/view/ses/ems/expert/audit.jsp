<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/tld/upload" prefix="up"%>
<%
//表单标示
	String tokenValue= new Date().getTime()+UUID.randomUUID().toString()+""; 
%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>
<script type="text/javascript">
	var treeObj;
	var datas;
	

	$(function(){
		var parentId ="";
		var addressId="${expert.address}";
		//alert(addressId);
		//地区回显和数据显示
		$.ajax({
			url : "${pageContext.request.contextPath}/area/find_by_id.do",
			data:{"id":addressId},
			success:function(obj){
				//alert(JSON.stringify(obj));
				//var data = eval('(' + obj+ ')');
				$.each(obj,function(i,result){
					if(addressId == result.id){
						parentId = result.parentId;
						//alert(parentId);
					$("#addr").append(result.name);
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
						$("#add").append(result.name+",");
					}
				});
			}
		});
	});	

	
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
			                onAsyncSuccess: onAsyncSuccess,
			                beforeCheck: zTreeBeforeCheck
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
		 if(expertsTypeId==1 || expertsTypeId=="1"){
		 //treeObj=$.fn.zTree.init($("#ztree"),setting,datas);
		treeObj = $.fn.zTree.init($("#ztree"), setting);  
	     setTimeout(function(){  
	         expandAll("ztree");  
	     },500);//延迟加载  
			 $("#ztree").show();
		 }else{
			 treeObj=$.fn.zTree.init($("#ztree"),setting,datas);
			 $("#ztree").hide();
		 }
	}); 
	   function zTreeBeforeCheck(treeId, treeNode) {
		    return false;
		};
	   
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
	
	function zTreeOnClick(event,treeId,treeNode){
		treeid=treeNode.id;
	}

//地区联动js
	function loadProvince(regionId){
		  $("#id_provSelect").html("");
		  $("#id_provSelect").append("<option value=''>请选择省份</option>");
		  var jsonStr = getAddress(regionId,0);
		  for(var k in jsonStr) {
			$("#id_provSelect").append("<option value='"+k+"'>"+jsonStr[k]+"</option>");
		  }
		  if(regionId.length!=6) {
			$("#id_citySelect").html("");
		    $("#id_citySelect").append("<option value=''>请选择城市</option>");
			$("#id_areaSelect").html("");
		    $("#id_areaSelect").append("<option value=''>请选择区域</option>");
		  } else {
			 $("#id_provSelect").val(regionId.substring(0,2)+"0000");
			 loadCity(regionId);
		  }
	}
	
	function loadCity(regionId){
	  $("#id_citySelect").html("");
	  $("#id_citySelect").append("<option value=''>请选择城市</option>");
	  if(regionId.length!=6) {
		$("#id_areaSelect").html("");
	    $("#id_areaSelect").append("<option value=''>请选择区域</option>");
	  } else {
		var jsonStr = getAddress(regionId,1);
	    for(var k in jsonStr) {
		  $("#id_citySelect").append("<option value='"+k+"'>"+jsonStr[k]+"</option>");
	    }
		if(regionId.substring(2,6)=="0000") {
		  $("#id_areaSelect").html("");
	      $("#id_areaSelect").append("<option value=''>请选择区域</option>");
		} else {
		   $("#id_citySelect").val(regionId.substring(0,4)+"00");
		   loadArea(regionId);
		}
	  }
	}
	
	function loadArea(regionId){
	  $("#id_areaSelect").html("");
	  $("#id_areaSelect").append("<option value=''>请选择区域</option>");
	  if(regionId.length==6) {
	    var jsonStr = getAddress(regionId,2);
	    for(var k in jsonStr) {
		  $("#id_areaSelect").append("<option value='"+k+"'>"+jsonStr[k]+"</option>");
	    }
		if(regionId.substring(4,6)!="00") {$("#id_areaSelect").val(regionId);}
	  }
	}
		
	 //是否通过标示
	 function pass(flag){
		 $("#isPass").val(flag);
		/*  if(flag==1 || flag=="1"){
			 $("#form1").submit();
		 }else { */
			var remark = $("#remark").val(); 
			 if(remark.replace(/(^\s*)|(\s*$)/g, "")=="" || remark==null){
				 layer.alert("请填写意见！",{offset: ['750px', '400px'],shade:0.01});
			 }else{
				 $("#form1").submit();
			 }
		 //}
	 }
</script>
</head>
<body>

<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">业务管理</a></li><li class="active"><a href="javascript:void(0)">评标专家审核</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
    
   <!-- 项目戳开始 -->
		<div class="container">
         <div class="tab-content">
          <div class="tab-v2">
            <ul class="nav nav-tabs bgwhite">
							<li class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="f18">基本信息</a></li>
							<li> <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18">专家类型</a></li>
						</ul>
<!-- 修改订列表开始-->
   <form action="${pageContext.request.contextPath}/expert/shenhe.html"  method="post" id="form1"  class="registerform"> 
   		<%
			session.setAttribute("tokenSession", tokenValue);
		%>
   <input type="hidden"  name="token2" value="<%=tokenValue%>">
   <input type="hidden" name="id" value="${expert.id }">
   <input type="hidden" name="isPass" id="isPass"/>
  <div class="tab-content">
	<div class="tab-pane fade in active" id="tab-1">
	<h2 class="count_flow"><i>1</i>个人信息</h2>
     <ul class="ul_list">
    <table class="table table-bordered">
	  <tbody>
				  <tr>
				    <td  class="info">姓名：</td>
				    <td >${expert.relName }</td>
				    <td  class="info">手机：</td>
				    <td >${expert.mobile }</td>
				  </tr>
				   <tr>
				    <td  class="info">联系电话（固话）：</td>
				    <td >${expert.telephone }</td>
				    <td  class="info">单位地址：</td>
				    <td >${expert.unitAddress }</td>
				  </tr>
				  <tr>
				    <td  class="info">性别：</td>
				    <td > 
				      <c:if test="${expert.gender eq 'M' }">男</c:if>
	     			  <c:if test="${expert.gender eq 'F' }">女</c:if>
	     			</td>
				    <td  class="info">出生日期：：</td>
				    <td ><fmt:formatDate type='date' value='${expert.birthday }' dateStyle="default" pattern="yyyy-MM-dd"/></td>
				  </tr>
				  <tr>
				    <td  class="info">政治面貌：</td>
				    <td >${expert.politicsStatus }</td>
				    <td  class="info">专家来源：</td>
				    <td >${expert.expertsFrom }</td>
				    
				  </tr>
				  <tr>
				    <td  class="info">证件类型：</td>
				    <td >${expert.idType }</td>
				    <td  class="info">证件号码：</td>
				    <td >${expert.idNumber}</td>
				  </tr>
				  <tr>
				    <td  class="info">所在地区：</td>
				    <td >
				      <font id="add"></font><font id="addr"></font>
				    </td>
				    <td  class="info">民族：</td>
				    <td >${expert.nation}</td>
				  </tr>
				  <tr>
				    <td  class="info">毕业院校：</td>
				    <td >${expert.graduateSchool }</td>
				    <td  class="info">专家技术职称：</td>
				    <td >${expert.professTechTitles}</td>
				  </tr>
				  <tr>
				    <td  class="info">参加工作时间：</td>
				    <td ><fmt:formatDate type='date' value='${expert.timeToWork }' dateStyle="default" pattern="yyyy-MM-dd"/></td>
				    <td  class="info">最高学历：</td>
				    <td >${expert.hightEducation}</td>
				  </tr>
				   <tr>
				    <td  class="info">专业：</td>
				    <td >${expert.major }</td>
				    <td  class="info">从事专业年度：</td>
				    <td ><fmt:formatDate type='date' value='${expert.timeStartWork }' dateStyle="default" pattern="yyyy-MM-dd"/></td>
				  </tr>
				   <tr>
				    <td  class="info">工作单位：</td>
				    <td >${expert.workUnit }</td>
				    <td  class="info">传真：</td>
				    <td >${expert.fax }</td>
				  </tr>
				   <tr>
				    <td  class="info">邮政编码：</td>
				    <td >${expert.postCode }</td>
				    <td  class="info">取得技术时间：</td>
				    <td ><fmt:formatDate type='date' value='${expert.makeTechDate }' dateStyle="default" pattern="yyyy-MM-dd"/></td>
				  </tr>
				   <tr>
				    <td  class="info">学位：</td>
				    <td >${expert.degree }</td>
				    <td  class="info">健康状态：</td>
				    <td >${expert.healthState }</td>
				  </tr>
				   <tr>
				    <td  class="info">现任职务：</td>
				    <td >${expert.atDuty }</td>
				    <td  class="info"></td>
				    <td ></td>
				  </tr>
		</tbody>
	 </table>
   </ul>
  <!-- 附件信息-->
  <div class="padding-top-10 clear">
    <h2 class="count_flow"><i>2</i>附件信息</h2>
    <ul class="ul_list">
    									<table class="table table-bordered">
										  	<tr>
										  	   <td  class="info"><i class="red">＊</i>身份证:</td>
										  	   <td>
										          <up:show  delete="false" showId="show1"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_IDNUMBER_TYPEID }"/>
										  	   </td>
										  	   <td  class="info"><i class="red">＊</i>学历证书:</td>
										  	   <td>
										          <up:show  delete="false" showId="show2"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_ACADEMIC_TYPEID }"/>
										  	   </td>
										  	</tr>
										  	<tr>
										  	   <td  class="info"><i class="red">＊</i>职称证书:</td>
										  	   <td>
										          <up:show  delete="false"  showId="show3"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_TITLE_TYPEID }"/>
										  	   </td>
										  	   <td  class="info"><i class="red">＊</i>学位证书:</td>
										  	   <td>
										          <up:show  delete="false" showId="show4"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }"  typeId="${typeMap.EXPERT_DEGREE_TYPEID }"/>
										  	   </td>
										  	</tr>
										  	<tr>
										  	   <td  class="info"><i class="red">＊</i>个人照片:</td>
										  	   <td>
										          <up:show  delete="false" showId="show5"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_PHOTO_TYPEID }"/>
										  	   </td>
										  	    <td  class="info"><i class="red">＊</i>专家申请表：</td>
										   	    <td>
												   <up:show showId="show6" delete="false"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_APPLICATION_TYPEID }"/>
										   	    </td>
										  	</tr>  
										  	 <tr>
										   	    <td  class="info"><i class="red">＊</i>专家合同书：</td>
										   	    <td>
												   <up:show showId="show7" delete="false"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_CONTRACT_TYPEID }"/>
										   	    </td>
										   	    <td></td><td></td>
										   	  </tr>
										  </table>
										 </ul>
								</div>		 
   <div class="padding-top-10 clear">
    <h2 class="count_flow"><i>3</i>采购机构</h2>
    <ul class="ul_list">
   <table class="table table-bordered">
  	<tr>
        <td class="bggrey">采购机构名称：</td><td>${purchase.name }</td>
        <td class="bggrey">采购机构联系人：</td><td>${purchase.princinpal }</td>
    </tr>
    <tr>
           <td class="bggrey">采购机构地址：</td><td>${purchase.detailAddr }</td>
        <td class="bggrey">联系电话：</td><td>${purchase.mobile }</td>
    </tr>
	</table>
	</ul>
  </div>
   <div class="padding-top-10 clear">
	  <ul  class="list-unstyled list-flow p0_20">
	  	<li class="col-md-12 p0">
	   <span class="fl">审核意见：</span>
	   <div class="col-md-12 pl200 fn mt5 pwr9">
        <textarea class="text_area col-md-12 " maxlength="200" id="remark" name="remark"  placeholder="不超过200个字"></textarea>
       </div>
	 </li> 
	  </ul>
  </div>
  <div class="col-md-12 tc">
   <input class="btn btn-windows git" type="button" onclick="pass('1');" value="通过">
	<input class="btn btn-windows cancel" type="button" onclick="pass('2');" value="不通过">
	<input class="btn btn-windows withdraw" type="button" onclick="pass('3');" value="退回修改">
	<a class="btn btn-windows back"  onclick="location.href='javascript:history.go(-1);'">返回</a>
  </div>
  </div>
   <div class="tab-pane fade height-450"  id="tab-2">
		<div class="margin-bottom-0  categories" >
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
			   <span class="">专家类型：</span>
			   <input type="hidden" id="expertsTypeIds" value="${expert.expertsTypeId }">
			   <select name="expertsTypeId" id="expertsTypeId" disabled="disabled">
			   		<option value="0">-请选择-</option>
			   		<option <c:if test="${expert.expertsTypeId eq '1' }">selected="selected"</c:if> value="1">技术</option>
			   		<option <c:if test="${expert.expertsTypeId eq '2' }">selected="selected"</c:if> value="2">法律</option>
			   		<option <c:if test="${expert.expertsTypeId eq '3' }">selected="selected"</c:if> value="3">商务</option>
			   </select>
			 </li>
		</div>
        </ul>
        <div id="ztree" class="ztree" style="margin-left: 240px;"></div>
		</div>
		<!-- <div>
	  <ul  class="list-unstyled list-flow p0_20">
	  	<li class="col-md-12 p0">
	   <span class="fl">审核意见：</span>
	   <div class="col-md-12 pl200 fn mt5 pwr9">
        <textarea class="text_area col-md-12 " maxlength="200" id="remark" name="remark"  placeholder="不超过200个字"></textarea>
       </div>
	 </li> 
	  </ul>
  </div> -->
<!--   <div  class="col-md-12">
   <div class="fl padding-10">
   <input class="btn btn-windows git" type="button" onclick="pass('1');" value="通过">
    <button class="btn btn-windows add" type="submit">下一步</button>
	<button class="btn btn-windows delete" type="submit">删除</button>
	<input class="btn btn-windows cancel" type="button" onclick="pass('2');" value="不通过">
	<input class="btn btn-windows withdraw" type="button" onclick="pass('3');" value="退回修改">
	<a class="btn btn-windows back"  onclick="location.href='javascript:history.go(-1);'">返回</a>
	</div>
  </div> -->
	</div>
  </div>
  
  </form>
           </div>
 	     </div>
		</div>
</body>
</html>