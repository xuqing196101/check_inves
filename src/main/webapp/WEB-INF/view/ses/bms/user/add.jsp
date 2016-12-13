<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
	<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
	<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
	<!--[if !IE]><!-->
	<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>

	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
	<%@ include file="/WEB-INF/view/common.jsp"%>
	<script type="text/javascript">
		/* 机构树 */
		
		function onClickOrg(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeOrg");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		}
		function onCheckOrg(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeOrg"),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].name + ",";
				$("#oId").val(nodes[i].id);
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			var cityObj = $("#orgSel");
			cityObj.attr("value", v);
			
			hideOrg();
		}
		function showOrg() {
			//var typeName_id = $("#typeName_id").val();
			var setting = {
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
					onCheck: onCheckOrg
				}
			};
			$.ajax({
             type: "GET",
             async: false, 
             url: "${pageContext.request.contextPath}/user/getOrgTree.do",
             dataType: "json",
             success: function(zNodes){
                     for (var i = 0; i < zNodes.length; i++) { 
			            if (zNodes[i].isParent) {  
			  
			            } else {  
			                //zNodes[i].icon = "${ctxStatic}/images/532.ico";//设置图标  
			            }  
			        }  
			        tree = $.fn.zTree.init($("#treeOrg"), setting, zNodes);  
			       // tree.expandAll(true);//全部展开
               }
         	});
			var cityObj = $("#orgSel");
			var cityOffset = $("#orgSel").offset();
			$("#orgContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
			$("body").bind("mousedown", onBodyDownRole);
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
		
		function back(){
			var origin = $("input[name='origin']").val();
			var srcOrgId = $("input[name='orgId']").val();
			var typeName = $("input[name='deptTypeName']").val();
			if (origin != null && origin != ""){
				window.location.href = '${pageContext.request.contextPath}/purchaseManage/list.html?srcOrgId='+srcOrgId +"&typeName=" + typeName;
			} else {
				window.location.href = '${pageContext.request.contextPath}/user/list.html?page=1';
			}
			
		}
	</script>
</head>
<body>
   <!--面包屑导航开始-->
   <c:if test="${empty origin}">
   	  <div class="margin-top-10 breadcrumbs ">
	   <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   	<li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">后台管理</a></li><li class="active"><a href="#">用户管理</a></li><li class="active"><a href="#">增加用户</a></li>
		   </ul>
		   <div class="clear"></div>
	   </div>
      </div>
   </c:if>
   <!-- 表单内容开始-->
   <div class="container container_box">
	   <div id="orgContent" class="orgContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
			<ul id="treeOrg" class="ztree"  ></ul>
	   </div>
	   <div id="roleContent" class="roleContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
			<ul id="treeRole" class="ztree" style="margin-top:0;"></ul>
	   </div>
   	   <sf:form action="${pageContext.request.contextPath}/user/save.html" method="post" modelAttribute="user">
		  <input type="hidden"  name="origin" value="${origin}" />
		  <%-- <input type="hidden" name="personTypeId" value="${personTypeId}" />
		  <input type="hidden" name="personTypeName" value="${personTypeName}" /> --%>
		  <input type="hidden" name="org_orgId" value="${orgId}" />
		  <input type="hidden" name="deptTypeName" value="${typeName}"/>
		   <div>
			   <h2 class="count_flow">新增用户</h2>
			   <ul class="ul_list">
			     <li class="col-md-3 col-sm-6 col-xs-12 pl15 col-lg-3">
				   <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><span class="red">*</span>用户名</span>
				   <div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
			        <input  name="loginName" value="${user.loginName}" maxlength="30" type="text">
			        <span class="add-on">i</span>
			       	<div class="cue"><sf:errors path="loginName"/></div>
			       	<div class="cue">${exist }</div>
			       </div>
				 </li>
				 <li class="col-md-3 col-sm-6 col-xs-12 col-lg-3">
				    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><span class="red">*</span>真实姓名</span>
				    <div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
				        <input  name="relName" value="${user.relName}" maxlength="10" type="text">
				        <span class="add-on">i</span>
				        <div class="cue"><sf:errors path="relName"/></div>
			       	</div>
			 	 </li>
			 	 <li class="col-md-3 col-sm-6 col-xs-12 col-lg-3">
			   		<span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><span class="red">*</span>密码</span>
				    <div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
				        <input  name="password" value="${user.password}" maxlength="30" id="password1" type="password">
				        <span class="add-on">i</span>
				        <div class="cue"><sf:errors path="password"/></div>
			        </div>
			 	</li> 
		     	<li class="col-md-3 col-sm-6 col-xs-12 col-lg-3">
				    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 col-lg-12"><span class="red">*</span>确认密码</span>
				    <div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
				        <input  id="password2" value="${user.password2}" maxlength="30" name="password2" type="password">
				        <span class="add-on">i</span>
				        <div class="cue"><sf:errors path="password2"/></div>
				        <div class="cue">${password2_msg}</div>
			        </div>
			 	</li>
			 	<li class="col-md-3 col-sm-6 col-xs-12 col-lg-3">
				    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><span class="red">*</span>性别</span>
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
		     	<li class="col-md-3 col-sm-6 col-xs-12 col-lg-3">
				    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><span class="red">*</span>手机</span>
				    <div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0" >
				        <input  name="mobile" value="${user.mobile}" maxlength="40" type="text">
				        <span class="add-on">i</span>
				        <div class="cue"><sf:errors path="mobile"/></div>
			        </div>
			 	</li>
		        <li class="col-md-3 col-sm-6 col-xs-12 col-lg-3" >
				   	<span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5">邮箱</span>
				   	<div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
				        <input  name="email" value="${user.email}" maxlength="100" type="text">
				        <span class="add-on">i</span>
				        <div class="cue"><sf:errors path="email"/></div>
			       	</div>
			 	</li>
		     	<li class="col-md-3 col-sm-6 col-xs-12 col-lg-3">
				    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5">职务</span>
				    <div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
			        	<input  name="duties" value="${user.duties}"  maxlength="40" type="text">
			        	<span class="add-on">i</span>
			        </div>
				 </li>
				<%-- <li class="col-md-3 col-sm-6 col-xs-12 col-lg-3">
				    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><span class="red">*</span>类型</span>
				    <div class="select_common col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
			       
			       <c:choose> 
				     <c:when  test="${not empty origin}">
			        	<select name="typeName" id="typeName_id">
			        	  	  <option value="${personTypeId}">${personTypeName}</option>
			        	</select>
			         </c:when>
			         <c:otherwise>
			           <select name="typeName" id="typeName_id">
			        	<c:forEach items="${typeNames}" var="t" varStatus="vs">
			        		<c:if test="${t.code != 'SUPPLIER_U' && t.code != 'EXPERT_U' && t.code != 'IMP_SUPPLIER_U' && t.code != 'IMP_AGENT_U'}">
				        		<option value="${t.id}" <c:if test="${t.id eq user.typeName}">selected</c:if>>
									${t.name}
				        		</option>
			        		</c:if>
			        	  </c:forEach>
			            </select>
			         </c:otherwise>
			       </c:choose>
			      </div>
				 </li> --%>
			 	<li class="col-md-3 col-sm-6 col-xs-12 col-lg-3">
				   	<span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><span class="red">*</span>所属机构</span>
				   	<div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
				        <c:choose> 
					        <c:when  test="${not empty origin}">
					            <input id="oId" name="orgId" value="${orgId}" type="hidden" />
					        	<input id="orgSel"  type="text" name="orgName" readonly value="${orgName}"  />
					        </c:when >
					        <c:otherwise>
					        	<input id="oId" name="orgId" value="${user.orgId}" type="hidden" />
					        	<input id="orgSel"  type="text" name="orgName" readonly value="${orgName}"  onclick="showOrg();" />
					        </c:otherwise>
					    </c:choose>
						<div class="drop_up" onclick="showOrg();">
						    <img src="${pageContext.request.contextPath}/public/backend/images/down.png" class="margin-bottom-5"/>
				        </div>
				        <div class="cue"><sf:errors path="orgId"/></div>
			        </div>
			 	</li>
		     	<li class="col-md-3 col-sm-6 col-xs-12 col-lg-3">
				    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5">座机电话</span>
				    <div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
			        	<input  name="telephone" value="${user.telephone}" maxlength="40" type="text">
			        	<span class="add-on">i</span>
			        </div>
			    </li> 
				<li class="col-md-3 col-sm-6 col-xs-12 col-lg-3">
				    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><span class="red">*</span>角色</span>
				    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 col-lg-12 p0">
				        <input id="rId" name="roleId"  type="hidden" value="${user.roleId}">
				        <input id="roleSel"  type="text" name="roleName" readonly value="${roleName}"  onclick="showRole();" />
						<div class="drop_up" onclick="showRole();">
						    <img src="${pageContext.request.contextPath}/public/backend/images/down.png" class="margin-bottom-5"/>
				        </div>
				        <div class="cue"><sf:errors path="roleId"/></div>
			        </div>
			 	</li>
			 	<li class="col-md-12 col-sm-12 col-xs-12 col-lg-12">
			 	   <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5">详细地址</span>
				   <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 p0">
			        	<textarea class="col-md-12 col-sm-12 col-xs-12 col-lg-12" style="height:130px" maxlength="100" name="address" title="不超过100个字">${user.address}</textarea>
			       </div>
			 	</li>
			   </ul>
		   </div> 
	       <div class="col-md-12 col-xs-12 col-sm-12 col-lg-12 tc mt20" >
			   <button class="btn btn-windows save"  type="submit">保存</button>
			   <button class="btn btn-windows back" onclick="back()" type="button">返回</button>
       	   </div>
  	   </sf:form>
   </div>
</body>
</html>
