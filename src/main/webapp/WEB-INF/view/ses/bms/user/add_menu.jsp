<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
		 
		<!-- ztree -->  
		var tree = "";  
		var setting = {
			check: {
				chkboxType:{"Y" : "ps", "N" : "ps"},//勾选checkbox对于父子节点的关联关系  
        		chkStyle:"checkbox",  
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};
		
        $(document).ready(function(){
        	var userId=$("#id").val();
			$.ajax({
             type: "GET",
             async: false, 
             url: "${pageContext.request.contextPath}/preMenu/treedata.do?userId="+userId,
             dataType: "json",
             success: function(zNodes){
                     for (var i = 0; i < zNodes.length; i++) { 
			            if (zNodes[i].isParent) {  
			  
			            } else {  
			                //zNodes[i].icon = "${ctxStatic}/images/532.ico";//设置图标  
			            }  
			        }  
			        tree = $.fn.zTree.init($("#menuTree"), setting, zNodes);  
			        tree.expandAll(true);//全部展开
               }
         	});
		});
		
		//用户-菜单信息入库  
		function ajaxSubmit(uid,idstr){
			$.ajax({
             type: "POST",
             async: false, 
             url: "${pageContext.request.contextPath}/user/saveUserMenu.do?userId="+uid+"&ids="+idstr,
             dataType: "text",
             success: function(str){
		        	 //var index = layer.getFrameIndex(window.name); //先得到当前iframe层的索引
				    
				     parent.layer.msg(str,{offset: '222px'});
				    //parent.layer.close(index);
				    // parent.layer.closeAll();
               }
         	}); 
		} 
		//获取选中节点  
		function onCheck(uid){  
		     var treeObj=$.fn.zTree.getZTreeObj("menuTree");  
		     var nodes=treeObj.getCheckedNodes(true);  
		     var ids = new Array();  
		     for(var i=0;i<nodes.length;i++){  
		        //获取选中节点的值  
		         ids.push(nodes[i].id); 
		     } 
		   ajaxSubmit(uid,ids);       
		}  
	</script>
  </head>
  
  <body>
  	<input id="id" type="hidden" value="${uid}">
    <!-- 菜单树-->
   <div id="menu">
	   <div id="menuTree" class="ztree"></div>
   </div>
  </body>
</html>
