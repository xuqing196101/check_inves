<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<script src="${pageContext.request.contextPath}/js/ses/bms/user/add.js"></script>
	<script type="text/javascript">
		/* 机构树 */
		function onClickOrg(e, treeId, treeNode) {
			/*var zTree = $.fn.zTree.getZTreeObj("treeOrg");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;*/
            var cityObj = $("#orgSel");
            cityObj.attr("value", treeNode.name);
            $("#oId").val(treeNode.id);
            hideOrg()
            $("#ajax_orgId").html("");
		}
		function onCheckOnlyOrg(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeOrg"),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			ids="";
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].name + ",";
				ids +=nodes[i].id+",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			var cityObj = $("#orgSel");
			cityObj.attr("value", v);
			$("#oId").val(ids);
			hideOrg();
		}
		function onCheckOrg(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeOrg");
			var nodes = zTree.getCheckedNodes(true),
			v = "";
			ids="";
			pid="";
			 for (var i=0, l=nodes.length; i<l; i++) {
				 v += nodes[i].name + ",";
				ids +=nodes[i].id+",";
			    /* if(!nodes[i].isParent){
			    v += nodes[i].name + ",";
				ids +=nodes[i].id+",";
			    }else{
			      pid +=nodes[i].id+",";
			    } */
				if(nodes[i].isParent){
					 pid +=nodes[i].id+",";
			    }else{
			      
			    }
			} 
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			var cityObj = $("#orgSel");
			cityObj.attr("value", v);
			cityObj.attr("title", v);
			$("#oId").val(ids);
			$("#orgParent").val(pid);
			
		}
		function showOrg() {
		
			//var typeName_id = $("#typeName_id").val();
			//获取机构类型
			var orgType = $("#org_type").val();
			var setting;
			if (orgType == '3') {
				return;
			}
			var index = layer.load(0, {
				shade : [ 0.1, '#fff' ],
				offset : [ '45%', '53%' ]
			});
  		 if(orgType =='4' || orgType=='5'){
			  setting = {
			data: {
				simpleData: {
					enable: true
				}
			   },
			  check: {
				chkboxType:{"Y" : "ps", "N" : "ps"},//勾选checkbox对于父子节点的关联关系  
        		chkStyle:"checkbox",  
				enable: true
			},
			   callback: {
					onClick: onClickOrg,
					onCheck: onCheckOrg
				}
		     };
		     var id=$("#oId").val();
		     if($("#orgParent").val()){
		     id+=$("#orgParent").val();
		     }
		     $.ajax({
             type: "POST",
             async: false, 
             url: "${pageContext.request.contextPath}/preMenu/dataTree.do",
             success: function(zNodes){
             		if (zNodes.length > 0) {
             		//循环便利 选中 的check机构
             		 var  idArray=id.split(",");
             		  for (var i = 0; i < zNodes.length; i++) { 
             		   var  item= zNodes[i].id;
             		    for (var j=0;idArray.length>j;j++){
             		      if (item==idArray[j]) { 
             		         zNodes[i].checked=true;
             		       }
             		     }
             		   }
				        tree = $.fn.zTree.init($("#treeOrg"), setting, zNodes); 
				        tree.expandAll(true);//全部展开 
					}
               }
         	});
			}else{
			 setting = {
				check: {
					enable: true,
					chkStyle: "radio",
					radioType: "all"
				},
				view: {
					dblClickExpand: false
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					onClick: onClickOrg,
					onCheck: onCheckOnlyOrg
				}
			};
			
			$.ajax({
             type: "GET",
             async: false, 
             url: "${pageContext.request.contextPath}/user/getOrgTree.do?orgType="+orgType,
             dataType: "json",
             success: function(zNodes){
                 /*alert(JSON.stringify(zNodes))*/
                     for (var i = 0; i < zNodes.length; i++) {
                         if($("#orgSel").val()==zNodes[i].name){
                             zNodes[i].checked=true;
						 }
			        }
			        tree = $.fn.zTree.init($("#treeOrg"), setting, zNodes);  
			       // tree.expandAll(true);//全部展开
               }
         	});
         	}
			var cityObj = $("#orgSel");
			var cityOffset = $("#orgSel").offset();
			$("#orgContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
			$("body").bind("mousedown", onBodyDownRole);
			layer.close(index);
		}
		function hideOrg() {
			$("#orgContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDownRole);
		}
		function onBodyDownRole(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "orgSel" || event.target.id == "orgContent" || $(event.target).parents("#orgContent").length>0)) {
				hideOrg();
			}
		}

		function ajaxDataFilter(treeId, parentNode, responseData){
		    alert(JSON.stringify(responseData))
		}
		/* 角色 */
		function beforeClick(treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeRole");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		}
		
		function onCheck(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeRole"),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			var rid = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].name + ",";
				rid += nodes[i].id + ",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			if (rid.length > 0 ) rid = rid.substring(0, rid.length-1);
			var cityObj = $("#roleSel");
			cityObj.attr("value", v);
			$("#rId").val(rid);
		}
		
		function showRole() {
			var userId = $("#uId").val();
			var setting = {
			check: {
					enable: true,
					chkboxType: {"Y":"", "N":""}
				},
				view: {
					dblClickExpand: false
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					beforeClick: beforeClick,
					onCheck: onCheck
				}
			};
	        $.ajax({
             type: "GET",
             async: false, 
             url: "${pageContext.request.contextPath}/role/roletree.do?userId="+userId,
             dataType: "json",
             success: function(zNodes){
                     for (var i = 0; i < zNodes.length; i++) { 
			            if (zNodes[i].isParent) {  
			  
			            } else {  
			                //zNodes[i].icon = "${ctxStatic}/images/532.ico";//设置图标  
			            }  
			        }  
			        tree = $.fn.zTree.init($("#treeRole"), setting, zNodes);  
			        tree.expandAll(true);//全部展开
               }
         	});
			var cityObj = $("#roleSel");
			var cityOffset = $("#roleSel").offset();
			$("#roleContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
			$("body").bind("mousedown", onBodyDownOrg);
		}
		
		function hideRole() {
			$("#roleContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDownOrg);
		}
		
		function onBodyDownOrg(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "roleSel" || event.target.id == "roleContent" || $(event.target).parents("#roleContent").length>0)) {
				hideRole();
			}
		}
		
		function goback(){
			
			var origin = $("input[name='origin']").val();
			var srcOrgId = $("input[name='orgId']").val();
			var deptTypeName = $("input[name='deptTypeName']").val();
			if (origin != null && origin != ""){
				window.location.href = '${pageContext.request.contextPath}/purchaseManage/list.html?srcOrgId='+srcOrgId + "&typeName=" + deptTypeName;
			} else {
				var currpage = $("#currpage").val();
				location.href = '${pageContext.request.contextPath}/user/list.html?page='+currpage;
			}
			
		}
		
		//控制显示输入框和下来框
		function viewOrgType(flag){
			//获取机构类型
			var orgType = $("#org_type").val();
			if (flag == 1) {
				
				$("#tempOrgNameId").val("");
				$("#orgSel").attr("value", "");
				$("#orgParent").val("");
				$("#oId").val("");
			}
		    
			if (orgType == '3') {
				$("#select_org").show();
			   $("#isOrgShow").show();
			   $("#orgTitle").html("所属机构");
				$("#orgSel").hide();
                $("#ajax_orgId").html("");
                $("#tempOrg").hide();
				$("#oId").attr("type","text");
			} else if (orgType =='5') {
				$("#select_org").show();
			   $("#isOrgShow").hide();
			   $("#orgTitle").html("监管对象");
			   $("#orgSel").show();
                $("#ajax_orgId").html("");
                $("#tempOrg").show();
			   $("#oId").attr("type","hidden");
			}else if (  orgType == '4') {
			   $("#select_org").hide();
			   $("#oId").attr("type","hidden");
			   $("#tempOrg").show();
			}else{
				$("#select_org").show();
			  	$("#isOrgShow").show();
			   	$("#orgTitle").html("所属机构");
				$("#orgSel").show();
                $("#ajax_orgId").html("");
                $("#tempOrg").hide();
				$("#oId").attr("type","hidden");
			}
		}
		
		//回显机构
		$(function(){
			var orgTypeName = "${user.typeName}";
			if (orgTypeName == '3') {
				$("#orgSel").hide();
				$("#oId").attr("type","text");
				$("#oId").val("${user.orgName}");
			}
			if(orgTypeName == '4'){
				$("#select_org").hide();
			}
		});
		
		function ajaxMoblie(){
			 var is_error = 0;
			 var mobile = $("#mobile").val();
			 var mobile2 = $("#mobile2").val();
			 var id = $("#uId").val();
			 if (mobile == mobile2) {
				 $("#errMobile").html("");
				 $("#ajax_mobile").html("两个手机号不能一致");
				 is_error = 1;
			 } else {
				 $.ajax({
	             type: "GET",
	             async: false, 
	             url: "${pageContext.request.contextPath}/user/ajaxMoblie.do?mobile="+mobile+"&id="+id,
	             dataType: "json",
	             success: function(data){
	                     if (!data.success) {
	                    	$("#errMobile").html("");
							$("#ajax_mobile").html(data.msg);
							is_error = 1;
						 } else {
						 	$("#ajax_mobile").html("");
						 }
	               }
	         	});
			 }
         	return is_error;
		}
		
		function ajaxMoblie2(){
			var is_error = 0;
			var mobile2 = $("#mobile2").val();
			var id = $("#uId").val();
			if (mobile2 != null && mobile2 != "") {
				var mobile = $("#mobile").val();
				if (mobile == mobile2) {
					 $("#errMobile2").html("");
					 $("#ajax_mobile2").html("两个手机号不能一致");
					 is_error = 1;
				} else {
					$.ajax({
			            type: "GET",
			            async: false, 
			            url: "${pageContext.request.contextPath}/user/ajaxMoblie.do?mobile="+mobile2+"&id="+id,
			            dataType: "json",
			            success: function(data){
		                    if (!data.success) {
		                    	$("#errMobile2").html("");
								$("#ajax_mobile2").html(data.msg);
								is_error = 1;
							 } else {
							 	$("#ajax_mobile2").html("");
							 }
		                }
			        });
				}
			}
        	return is_error;
		}
		
		function ajaxIdNumber(){
			 var is_error = 0;
			 var idNumber = $("#idNumber").val();
			  var msg=validateIdCard(idNumber);
			 if(msg!='success'){
			 is_error = 1;
			 $("#ajax_idNumber").html(msg);
			    return is_error;
			 }else{
			 $("#ajax_idNumber").html("");
			 }
			 var id = $("#uId").val();
			 $.ajax({
             type: "GET",
             async: false, 
             url: "${pageContext.request.contextPath}/user/ajaxIdNumber.do?idNumber="+idNumber+"&id="+id,
             dataType: "json",
             success: function(data){
                     if (!data.success) {
						$("#ajax_idNumber").html(data.msg);
						is_error = 1;
					 } else {
					 	$("#ajax_idNumber").html("");
					 }
               }
         	});
         	return is_error;
		}
		
		function ajaxOfficerCertNo(){
			 var is_error = 0;
			 var officerCertNo = $("#officerCertNo").val();
			 var id = $("#uId").val();
			 $.ajax({
             type: "GET",
             async: false, 
             url: "${pageContext.request.contextPath}/user/ajaxOfficerCertNo.do?officerCertNo="+officerCertNo+"&id="+id,
             dataType: "json",
             success: function(data){
                     if (!data.success) {
						$("#ajax_officerCertNo").html(data.msg);
						is_error = 1;
					 } else {
					 	$("#ajax_officerCertNo").html("");
					 }
               }
         	});
         	return is_error;
		}
		
		$(document).ready(function(){  
		  viewOrgType(0);
    		$("#form1").bind("submit", function(){  
    			var error = 0;
    			if (ajaxIdNumber() == 1) {
					error += 1;
				} 
				if (ajaxMoblie() == 1){
					error += 1;
				} 
				if (ajaxOfficerCertNo() == 1){
					error += 1;
				}
				if (error > 0) {
					return false;
				} else {
					return true;
				}
    		})
    	})
    	
    	function searchs(){
		var name=$("#search").val();
		if(name!=""){
			var userId = $("#uId").val();
			var setting = {
			check: {
					enable: true,
					chkboxType: {"Y":"", "N":""}
				},
				view: {
					dblClickExpand: false
				},
				data: {
					simpleDatas: {
						enable: true
					}
				},
				callback: {
					beforeClick: beforeClick,
					onCheck: onCheck
				}
			};
	        $.ajax({
             type: "GET",
             async: false, 
             url: "${pageContext.request.contextPath}/role/roletree.do?userId="+userId,
             dataType: "json",
             success: function(zNodes){
                     /* for (var i = 0; i < zNodes.length; i++) { 
			            if (zNodes[i].isParent) {  
			 
			            } else {  
			                //zNodes[i].icon = "${ctxStatic}/images/532.ico";//设置图标  
			            }  
			        }   */
			        tree = $.fn.zTree.init($("#treeRole"), setting, zNodes);  
			        tree.expandAll(true);//全部展开
               }
         	});
			
			// 加载中的菊花图标
			//var loading = layer.load(1);
			
			$.ajax({
				url: "${pageContext.request.contextPath}/role/roletree.do",
				data: { "name" : encodeURI(name), "userId": userId},
				async: false,
				dataType: "json",
				success: function(data){
					if (data.length == 0) {
						layer.msg("没有符合查询条件的角色！");
					} else {
						zNodes = data;
						zTreeObj = $.fn.zTree.init($("#treeRole"), setting, zNodes);
						zTreeObj.expandAll(true);//全部展开
					}
					// 关闭加载中的菊花图标
					//layer.close(loading);
				}
			});
		}else{
			showRole();
		}
	}
	</script>
</head>
<body>
   <c:if test="${empty  origin}">
     <!--面包屑导航开始-->
     <div class="margin-top-10 breadcrumbs ">
       <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')" target="_blank"> 首页</a></li><li><a href="javascript:void(0);">支撑系统</a></li><li><a href="javascript:void(0);">后台管理</a></li><li class="active"><a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/user/list.html')">用户管理</a></li><li class="active"><a href="javascript:void(0);">修改用户</a></li>
		   </ul>
		<div class="clear"></div>
	   </div>
     </div>
   </c:if>
   
   
   <!-- 表单内容开始-->
   <div class="container container_box">
	   <div id="orgContent" class="orgContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
			<ul id="treeOrg" class="ztree"></ul>
	   </div>
	   <div id="roleContent" class="roleContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
			<div class=" input_group col-md-3 col-sm-6 col-xs-12 col-lg-12 p0">
				<div class="w100p">
			    	<input type="text" id="search" class="fl m0">
				      <img alt="" style="position:absolute; top:8px;right:10px;" src="${pageContext.request.contextPath }/public/backend/images/view.png"  onclick="searchs()">
			    </div>
				<ul id="treeRole" class="ztree mt0"></ul>
			</div>
	   </div>
	   <sf:form id="form1" action="${pageContext.request.contextPath}/user/update.html" method="post" modelAttribute="user">
	   	  	<input type="hidden" name="origin"  value="${origin}"/>
	   	  	<input type="hidden" name="deptTypeName" value="${typeName}"/>
	   	   <div>
			    <h2 class="count_flow">修改用户</h2>
			    <input type="hidden" id="currpage" name="currpage" value="${currPage}">
			    <input class="span2" name="id" id="uId" type="hidden" value="${user.id}">
			   	<input class="span2" name="createdAt" type="hidden" value="<fmt:formatDate value='${user.createdAt}' pattern='yyyy-MM-dd  HH:mm:ss'/>">
			   	<input class="span2" name="isDeleted" type="hidden" value="${user.isDeleted}">
			   	<input class="span2" name="password" type="hidden" value="${user.password}">
			   	<input class="span2" name="password2" type="hidden" value="${user.password}">
			   	<input class="span2" name="randomCode" type="hidden" value="${user.randomCode}">
	   			<ul class="ul_list">
	   				<li class="col-md-3 col-sm-6 col-xs-12 pl15 col-lg-3">
					   <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><span class="star_red">*</span>用户名</span>
					   <div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
				        <input id="loginName" name="loginName" readonly="readonly" value="${user.loginName }" maxlength="30" type="text">
				        <span class="add-on">i</span>
				       	<div class="cue"><sf:errors path="loginName"/></div>
				       	<div id="is_exist" class="cue">${exist }</div>
				       </div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12 col-lg-3">
					    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><span class="star_red">*</span>真实姓名</span>
					    <div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
					        <input name="relName" value="${user.relName }" maxlength="10" type="text">
					        <span class="add-on">i</span>
					        <div class="cue"><sf:errors path="relName"/></div>
				       	</div>
				 	</li>
			 		<li class="col-md-3 col-sm-6 col-xs-12 col-lg-3 ">
					    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><span class="star_red">*</span>性别</span>
				        <div class="select_common col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
				        <select id="gender" name="gender">
				        	<c:forEach items="${genders}" var="g" varStatus="vs">
				        		<option value="${g.id }" <c:if test="${g.id eq user.gender}">selected</c:if>>
				        			${g.name}
				        		</option>
				        	</c:forEach>
				        </select>
				        </div>
				 	</li>
			     	<li class="col-md-3 col-sm-6 col-xs-12 col-lg-3 ">
					    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><span class="star_red">*</span>手机(常用)</span>
					    <div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0" >
					        <input class="span5" id="mobile" name="mobile" value="${user.mobile }" maxlength="40" onblur="ajaxMoblie()" type="text">
					        <span class="add-on">i</span>
					        <div class="cue" id="errMobile"><sf:errors path="mobile"/></div>
					        <div id="ajax_mobile" class="cue"></div>
				        </div>
				 	</li>
				 	<li class="col-md-3 col-sm-6 col-xs-12 col-lg-3">
					    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5">手机(备用)</span>
					    <div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0" >
					        <input id="mobile2" name="mobile2" value="${user.mobile2}" maxlength="40" type="text">
					        <span class="add-on">i</span>
					        <div class="cue" id="errMobile2"><sf:errors path="mobile2"/></div>
					        <div id="ajax_mobile2" class="cue"></div>
				        </div>
				 	</li>
			        <%-- <li class="col-md-3 col-sm-6 col-xs-12 col-lg-3" >
					   	<span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5">邮箱</span>
					   	<div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
					        <input class="span5" name="email" value="${user.email }" maxlength="100" type="text">
					        <span class="add-on">i</span>
					        <div class="cue"><sf:errors path="email"/></div>
				       	</div>
				 	</li> --%>
		     		<li class="col-md-3 col-sm-6 col-xs-12 col-lg-3">
					    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5">职务</span>
					    <div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
					        <input class="span5" name="duties" value="${user.duties }" maxlength="40" type="text">
					        <span class="add-on">i</span>
				        </div>
			 		</li>
			 		<%-- <li class="col-md-3 col-sm-6 col-xs-12 col-lg-3 ">
					    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><span class="star_red">*</span>类型</span>
					    <div class="select_common col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
					        <c:choose>
					        	<c:when test="${not empty origin}">
					        	  <select name="typeName" id="typeName_id">
					        	    <option value="${personTypeId}">${personTypeName}</option>
					        	  </select>
					        	</c:when>
					        	<c:otherwise>
					        		<select name="typeName" id="typeName_id">
					        		  <c:forEach items="${typeNames}" var="t" varStatus="vs">
						        		<c:if test="${t.code != 'SUPPLIER_U' && t.code != 'EXPERT_U' && t.code != 'IMP_SUPPLIER_U' && t.code != 'IMP_AGENT_U'}">
							        		<option value="${t.id }" <c:if test="${t.id eq user.typeName}">selected</c:if>>
												<c:if test="${'NEED_U' eq t.code}">需求人员</c:if>
												<c:if test="${'PURCHASER_U' eq t.code}">采购人员</c:if>
												<c:if test="${'PUR_MG_U' eq t.code}">采购管理人员</c:if>
												<c:if test="${'OTHER_U' eq t.code}">其他人员</c:if>
												<c:if test="${'SUPPLIER_U' eq t.code}">供应商</c:if>
												<c:if test="${'EXPERT_U' eq t.code}">专家</c:if>
												<c:if test="${'IMP_SUPPLIER_U' eq t.code}">进口供应商</c:if>
												<c:if test="${'IMP_AGENT_U' eq t.code}">进口代理商</c:if>
												<c:if test="${'SUPERVISER_U' eq t.code}">监督人员</c:if>
							        		</option>
						        		</c:if>
				        			</c:forEach>
					       		  </select>
					        	</c:otherwise>
					        </c:choose>
				        </div>
			 		</li> --%>
			 		<li class="col-md-3 col-sm-6 col-xs-12 col-lg-3">
				    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><span class="star_red">*</span>发布类型</span>
			        <div class="select_common col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
			        <select id="publishType" name="publishType">
			        	<option value="0" <c:if test="${'0' eq user.publishType}">selected</c:if>>集中采购</option>
			        	<option value="1" <c:if test="${'1' eq user.publishType}">selected</c:if>>部队采购</option>
			        </select>
			        </div>
			 	 </li>
				 <li class="col-md-3 col-sm-6 col-xs-12 col-lg-3">
				    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5">身份证号</span>
				    <div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
			        	<input id="idNumber" name="idNumber"   value="${user.idNumber}" onblur="ajaxIdNumber()" maxlength="18" type="text">
			        	<span class="add-on">i</span>
			        	<div id="ajax_idNumber" class="cue">
			        	</div>
			        </div>
				 </li>
				 <li class="col-md-3 col-sm-6 col-xs-12 col-lg-3">
				    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5">军官证号</span>
				    <div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
			        	<input  name="officerCertNo" id="officerCertNo" value="${user.officerCertNo}" onblur="ajaxOfficerCertNo()"  maxlength="20" type="text">
			        	<span class="add-on">i</span>
			        	<div id="ajax_officerCertNo" class="cue">${ajax_officerCertNo}</div>
			        </div>
				 </li>
				 <li class="col-md-3 col-sm-6 col-xs-12 col-lg-3 ">
					    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5">座机电话(地方)</span>
					    <div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
				        	<input class="span5" name="telephone" value="${user.telephone }" maxlength="40" type="text">
				        	<span class="add-on">i</span>
				        </div>
				    </li>
				    <li class="col-md-3 col-sm-6 col-xs-12 col-lg-3">
					    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5">座机电话(军线)</span>
					    <div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
				        	<input  name="telephone2" value="${user.telephone2}" maxlength="40" type="text">
				        	<span class="add-on">i</span>
				        </div>
				    </li> 
			 		<li class="col-md-3 col-sm-6 col-xs-12 col-lg-3">
					    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><span class="star_red">*</span>机构类型</span>
				        <div class="select_common col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
					        <c:choose> 
						        <c:when  test="${not empty origin}">
						            <select id="org_type" name="typeName" >
							        	<option value="${typeName}" >
							        	<c:if test="${typeName == '1'}">采购机构</c:if>
						        		<c:if test="${typeName == '2'}">采购管理部门</c:if>
						        		<c:if test="${typeName == '0'}">需求部门</c:if>
						        		<c:if test="${typeName == '4'}">采购服务中心</c:if>
						        		<c:if test="${typeName == '5'}">监管部门</c:if>
						        		<c:if test="${typeName == '3'}">其他</c:if>
							        		
							        	</option>
							        </select>
						        </c:when >
						        <c:otherwise>
						        	<select id="org_type" name="typeName" onchange="viewOrgType(1)">
						        	<option value="1" <c:if test="${user.typeName == '1'}">selected</c:if>>采购机构</option>
						        	<option value="2" <c:if test="${user.typeName == '2'}">selected</c:if>>采购管理部门</option>
						        	<option value="0" <c:if test="${user.typeName == '0'}">selected</c:if>>需求部门</option>
						        	<option value="4" <c:if test="${user.typeName == '4'}">selected</c:if>>采购服务中心</option>
						        	<option value="5" <c:if test="${user.typeName == '5'}">selected</c:if>>监管部门</option>
						        	<option value="3" <c:if test="${user.typeName == '3'}">selected</c:if>>其他</option>	
						        </select>
						        </c:otherwise>
						    </c:choose>
					        <div class="cue"><sf:errors path="typeName"/></div>
				        </div>
				 	</li>
			 		<li class="col-md-3 col-sm-6 col-xs-12 col-lg-3" id="select_org">
					    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5">
					  <c:if test="${ user.typeName == '5' }">
					    <span class="red display-none" id="isOrgShow">*</span><span id="orgTitle">监管对象</span>
					  </c:if>
					  <c:if test="${user.typeName != '5'}">  
					 <span class="red " id="isOrgShow">*</span><span id="orgTitle">所属机构</span>
					  </c:if>
					    </span>
						<div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
							<input id="oId" name="orgId" type="hidden" value="${orgId}">
							<c:choose>
                          <c:when test="${user.typeName!='4' && user.typeName!='5'}">
							<c:if test="${user.org != null && user.org.fullName != null && user.org.fullName != ''}">
								<input id="orgSel" class="span5" name="orgName" type="text" readonly
									   value="${user.org.fullName}" onclick="showOrg();"/>
							</c:if>
							<c:if test="${user.org != null && (user.org.fullName == null || user.org.fullName == '')}">
								<input id="orgSel" class="span5" name="orgName" type="text" readonly
									   value="${user.org.name}" onclick="showOrg();"/>
							</c:if>
							<c:if test="${user.org == null }">
                                      <input id="orgSel" class="span5" name="orgName" type="text" readonly
									   value="${user.orgName}" onclick="showOrg();"/>
							 </c:if>
								</c:when>
                          <c:otherwise>
                          <input id="orgSel" class="span5" name="orgName" type="text" readonly
									   value="${orgName}" onclick="showOrg();"/>
					  	   </c:otherwise>
                          </c:choose>
							<input type="hidden" id="orgParent" value=""/>
							<div class="drop_up" onclick="showOrg();">
								<img src="${pageContext.request.contextPath}/public/backend/images/down.png"
									 class="margin-bottom-5"/>
							</div>
							 <div id="ajax_orgId" class="cue">${ajax_orgId }</div>
						</div>
			 		</li>
			 		
			 		<li class="col-md-3 col-sm-6 col-xs-12 col-lg-3" id="tempOrg">
	                    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5">单位</span>
	                    <div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
	                        <input id="tempOrgNameId" name="tempOrgName" value="${user.orgName}" maxlength="400" type="text">
	                        <span class="add-on">i</span>
	                    </div>
                    </li> 
					<li class="col-md-3 col-sm-6 col-xs-12 col-lg-3 ">
					    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><span class="star_red">*</span>角色</span>
					    <div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
						   	<input id="rId" name="roleId" type="hidden" value="${roleId}">
					        <input id="roleSel" class="span5" name="roleName" type="text" readonly value="${roleName}"  onclick="showRole();" />
					        <div class="drop_up" onclick="showRole();">
							    <img src="${pageContext.request.contextPath}/public/backend/images/down.png" class="margin-bottom-5"/>					          
					        </div>
					        <div class="cue"><sf:errors path="roleId"/></div>
				        </div>
					 </li>
					 <li class="col-md-3 col-sm-6 col-xs-12 col-lg-3">
					    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><span class="star_red">*</span>数据查看权限</span>
				        <div class="select_common col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
					        <select name="dataAccess">
				        		<option value="1" <c:if test="${user.dataAccess == 1}">selected</c:if>>所有</option>
						        <option value="2" <c:if test="${user.dataAccess == 2}">selected</c:if>>本单位</option>
						        <option value="3" <c:if test="${user.dataAccess == 3}">selected</c:if>>本人</option>
					        </select>
				        </div>
				 	</li>
				 	<li class="col-md-3 col-sm-6 col-xs-12 col-lg-3">
				 	   <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5">上传用户注册申请表</span>
					   <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 p0">
		                <u:upload id="fileApply"  multiple="true"  businessId="${user.id}" sysKey="${sysKey}" typeId="${data.id}" auto="true" />
		                <u:show showId="fileApplyShow"  businessId="${user.id}" sysKey="${sysKey}" typeId="${data.id}" />
		               </div>
				 	</li>
				     <li class="col-md-12 col-sm-12 col-xs-12 col-lg-12 ">
				 	   <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5">详细地址</span>
					   <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 p0">
				        	<textarea class="col-md-12 col-sm-12 col-xs-12 col-lg-12" style="height:130px" maxlength="100" name="address" title="不超过100个字">${user.address }</textarea>
				       </div>
				 	</li>
	   			</ul>
	  	  </div> 
	  	   <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12  tc mt20" >
		    	<button class="btn btn-windows save" type="submit">更新</button>
		    	<button class="btn btn-windows back" onclick="goback()" type="button">返回</button>
		   </div>
  	   </sf:form>
 	</div>
</body>
</html>
