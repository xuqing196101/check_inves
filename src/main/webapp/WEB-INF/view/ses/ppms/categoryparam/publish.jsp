<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'publish.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/public/ztree/css/zTreeStyle.css"> 
<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.core.js"></script>

<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.exedit.js"></script>
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
			    	beforeRemove: zTreeBeforeRemove,
			    	beforeRename: zTreeBeforeRename, 
					onRemove: zTreeOnRemove,
       			    onRename: zTreeOnRename,
			    }, 
				data:{
					keep:{
						parent:true
					},
					key:{
						title:"title"
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
         };
	 
    $.fn.zTree.init($("#ztree"),setting,datas);
    
      }); 
    
  
   /**删除图片*/
   function deletepic(treeid,obj){
		$(obj).prev().remove();
		$(obj).next().remove();
		$(obj).remove();
	}

    /**点击事件*/
    function zTreeOnClick(event,treeId,treeNode){
		treeid=treeNode.id;
		treename=treeNode.name;
	    parentKind=treeNode.kind;
	    isEnd=treeNode.isEnd;
	    parentname=treeNode.getParentNode().name;
    }
    
		
	
   
 	/**重命名和删除的回调函数*/	
    function zTreeOnRemove(event, treeId, treeNode,isCancel) {
		}
    function zTreeOnRename(event, treeId, treeNode, isCancel) {
				 alert(treeNode.tId + ", " + treeNode.name); 
 		}  
 		
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
	
	$(function(){
	    var name  = "${cateParam.name}";
	  
	    var value = "${cateParam.valueType}";
	    var names = name.split(",");
	  
	    var values = value.split(",");
	     var html = "";
	     for ( var i = 0 ; i< names.length-1; i++){
				html = html +"<tr><td>参数名称：<input type='text' value='"+names[i]+"'/></td><td>"
				+"<select  name='valueType'>"
				+"<option value='' selected='selected'>"+values[i]+"</option>"
				+"<option value='字符型'>字符型</option>"
				+"<option value='数字型'>数字型</option>"
				+"<option value='日期'>日期</option><select/></td></tr>";
	     }
	  
	      $("#result").prepend(html);
	});
	</script>
  </head>
  
  <body>
 	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">产品参数管理</a></li><li><a href="#">参数发布</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
  
	<div class=" tag-box tag-box-v3 mt10 col-md-9">
	
	 <span><a href="javascript:void(0);" onclick="publish()" class="btn">发布</a></span>
	 <span><a href="javascript:void(0);" onclick="location.href='javascript:history.go(-1);'" class="btn btn-windows back">返回</a></span>
	      <form action="<%=basePath%>categoryparam/publish_param.html" method="post">
	          <table id="result">
	          <tr><td>
	          
	          
	          </td></tr>
	          </table>
	      </form>
	</div>
	</div>
  </body>
</html>
