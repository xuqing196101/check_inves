<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
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
	<link rel="stylesheet" href="<%=basePath%>public/ztree/css/demo.css" type="text/css">
	<link rel="stylesheet" href="<%=basePath%>public/ztree/css/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="<%=basePath%>public/ztree/jquery.ztree.core.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/ztree/jquery.ztree.excheck.js"></script>
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
             url: "<%=basePath%>purchaseManage/gettree.do?",
             dataType: "json",
             success: function(zNodes){
                     for (var i = 0; i < zNodes.length; i++) { 
			            if (zNodes[i].isParent) {  
			  
			            } else {  
			                //zNodes[i].icon = "${ctxStatic}/images/532.ico";//设置图标  
			            }  
			        }  
			        tree = $.fn.zTree.init($("#treeOrg"), setting, zNodes);  
			        tree.expandAll(true);//全部展开
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
             url: "<%=basePath%>role/roletree.do?userId="+userId,
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
	</script>
</head>
<body>

<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">后台管理</a></li><li class="active"><a href="#">用户管理</a></li><li class="active"><a href="#">修改用户</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
<!-- 修改订列表开始-->
   <div class="container">
	   <div id="orgContent" class="orgContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
			<ul id="treeOrg" class="ztree"  style="width: 220px"></ul>
	   </div>
	   <div id="roleContent" class="roleContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
			<ul id="treeRole" class="ztree" style="margin-top:0; width:220px;"></ul>
	   </div>
   <form action="<%=basePath %>user/update.html" method="post">
   <div>
   <div class="headline-v2">
   <h2>修改用户</h2>
   </div>
    <input class="span2" name="id" id="uId" type="hidden" value="${user.id}">
   	<input class="span2" name="createdAt" type="hidden" value="<fmt:formatDate value='${user.createdAt}' pattern='yyyy-MM-dd  HH:mm:ss'/>">
   	<input class="span2" name="isDeleted" type="hidden" value="${user.isDeleted}">
   	<input class="span2" name="password" type="hidden" value="${user.password}">
   	<input class="span2" name="randomCode" type="hidden" value="${user.randomCode}">
   <ul class="list-unstyled list-flow p0_20">
	   	<li class="col-md-6 p0">
		   <span class="">用户名：</span>
		   <div class="input-append">
	        <input class="span2" name="loginName" type="text" readonly="readonly" value="${user.loginName}">
	        <span class="add-on">i</span>
	       </div>
		 </li>
	     <li class="col-md-6  p0 ">
		   <span class="">真实姓名：</span>
		   <div class="input-append">
	        <input class="span2" name="relName" type="text" value="${user.relName}">
	        <span class="add-on">i</span>
	       </div>
		 </li>
		 <li class="col-md-6 p0">
		   <span class="">性别：</span>
	        <select name="gender">
	        	<option value="">-请选择-</option> 
	        	<option value="M" <c:if test="${'M' eq user.gender}">selected</c:if> >男</option>
	        	<option value="F" <c:if test="${'F' eq user.gender}">selected</c:if>>女</option>
	        </select>
		 </li>
	     <li class="col-md-6  p0 ">
		   <span class="">手机：</span>
		   <div class="input-append">
	        <input class="span2" name="mobile" value="${user.mobile }" type="text">
	        <span class="add-on">i</span>
	       </div>
		 </li>
	     <li class="col-md-6 p0">
		   <span class="">邮箱：</span>
		   <div class="input-append">
	        <input class="span2" name="email" value="${user.email }" type="text">
	        <span class="add-on">i</span>
	       </div>
		 </li>
	     <li class="col-md-6  p0 ">
		   <span class="">职务：</span>
		   <div class="input-append">
	        <input class="span2" name="duties" value="${user.duties }" type="text">
	        <span class="add-on">i</span>
	       </div>
		 </li>
		 <li class="col-md-6 p0">
		   <span class="">类型：</span>
	        <select name="typeName">
	        	<option value="2" <c:if test="${'2' eq user.typeName}">selected</c:if>>需求人员</option>
	        	<option value="1" <c:if test="${'1' eq user.typeName}">selected</c:if>>采购人员</option>
	        	<option value="0" <c:if test="${'0' eq user.typeName}">selected</c:if>>采购管理人员</option>
	        	<option value="3" <c:if test="${'3' eq user.typeName}">selected</c:if>>其他人员</option>
	        	<option value="4" <c:if test="${'4' eq user.typeName}">selected</c:if>>供应商</option>
	        	<option value="5" <c:if test="${'5' eq user.typeName}">selected</c:if>>专家</option>
	        </select>
		 </li>
		 <li class="col-md-6  p0 ">
		   <span class="">所属机构：</span>
		   <div class="input-append">
		   	<input id="oId" name="orgId" type="hidden">
	        <input id="orgSel" class="span2" type="text" readonly value="${user.org.name }"  onclick="showOrg();" />
	        <span class="add-on">i</span>
	       </div>
		 </li>
	     <li class="col-md-6  p0 ">
		   <span class="">联系电话：</span>
		   <div class="input-append">
	        <input class="span2" name="telephone" type="text" value="${user.telephone}">
	        <span class="add-on">i</span>
	       </div>
		 </li> 
		 <li class="col-md-6 p0">
		   <span class="">角色：</span>
		   <div class="input-append">
		   	<input id="rId" name="roleId" type="hidden" value="${roleId}">
	        <input id="roleSel" class="span2" type="text" readonly value="${roleName}"  onclick="showRole();" />
	        <span class="add-on">i</span>
	       </div>
		 </li>
		 <li class="col-md-12 p0">
		   <span class="fl">详细地址：</span>
		   <div class="col-md-12 pl200 fn mt5 pwr9">
	        <textarea class="text_area col-md-12 " address="address" maxlength="200" title="" placeholder="">${user.address}</textarea>
	       </div>
		 </li>
			 
   </ul>
  </div> 
   
  <div  class="col-md-12">
   	<div class="fl padding-10">
    	<button class="btn btn-windows reset" type="submit">更新</button>
    	<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
	</div>
  </div>
  </form>
 </div>
</body>
</html>
