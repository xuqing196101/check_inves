<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'serarchinfo.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" type="text/css" href="<%=basePath%>/public/ztree/css/zTreeStyle.css"> 
<%-- <link rel="stylesheet" type="text/css" href="<%=basePath%>/public/ztree/css/demo.css"> --%>

<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.excheck.js"></script>
<script type="text/javascript">
var datas;
var treeid=null;
$(document).ready(function(){
     var setting={
	    async:{
				autoParam:["id"],
				enable:true,
				url:"<%=basePath%>category/createtree.do",
				dataType:"json",
				type:"post",
			},
			callback:{
		    	onClick:zTreeOnClick,//点击节点触发的事件
		    	beforeClick: zTreeBeforeClick,
		    	beforeRemove: zTreeBeforeRemove,
		    	beforeRename: zTreeBeforeRename, 
				onRemove: zTreeOnRemove,
  			    onRename: zTreeOnRename,
  			    
  			  
  			  /*    onNodeCreated: zTreeOnNodeCreated, */
  			   
		    }, 
			data:{
				keep:{
					parent:true
				},
				key:{
					title:"title",
					name:"name",
				},
				simpleData:{
					enable:true,
					idKey:"id",
					pIdKey:"pId",
					rootPId:"0",
				}
		    },
		    edit:{
		    	enable:true,
				editNameSelectAll:true,
				showRemoveBtn: true,
				showRenameBtn: true,
				removeTitle: "删除",
				renameTitle:"重命名",
			},
		   check:{
				enable: true
		   },
		   view:{
		        selectedMulti: false,
		        showTitle: false,
		   },
};
$.fn.zTree.init($("#ztree"),setting,datas); 


}); 

/**点击事件*/
function zTreeOnClick(event,treeId,treeNode){
	treeid=treeNode.id;
	$("#cateid").val(treeid);
}

/**重命名和删除的回调函数*/	
    function zTreeOnRemove(event, treeId, treeNode,isCancel) {
	}
    function zTreeOnRename(event, treeId, treeNode, isCancel) {
			 alert(treeNode.tId + ", " + treeNode.name); 
			
	}
    function zTreeOnClick(event, treeId, treeNode,isCancel) {
        alert(treeNode.tId + ", " + treeNode.name);
    };
    
/**删除目录信息*/
    function zTreeBeforeRemove(treeId, treeNode){
 		$.ajax({
 			type:"post",
 			url:"<%=basePath%>category/del.do?id="+treeNode.id,
 		});
	}
 	
/**节点重命名*/
    function zTreeBeforeRename(treeId,treeNode,newName,isCancel){
		$.ajax({
 			type:"post",
 			url:"<%=basePath%>category/rename.do?id="+treeNode.id+"&name="+newName,
 		});
	}
    function zTreeBeforeClick(treeId, treeNode) {
       $.ajax({
    	   type:"post",
    	   url:"<%=basePath%>categoryparam/search_info.html?id="+treeNode.id,
    	   success:function(cate){
    		   alert(cate);
    		   var 
    		   
    		   
    	   }
       })
    };
</script>
  </head>
  
  <body>
   <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">产品参数管理</a></li><li><a href="#">目录查询</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
   <div class="col-md-3">
     
	 <div class="tag-box tag-box-v3 mt10">
	 <div><ul id="ztree" class="ztree "></ul></div>
	 </div>
   
  </body>
</html>
