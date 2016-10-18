<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'audit.jsp' starting page</title>
    
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
<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.exedit.js"></script>

<script type="text/javascript">
	<%-- var datas;
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
       			  /*    onNodeCreated: zTreeOnNodeCreated, */
       			   
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
	
   /**点击事件*/
    function zTreeOnClick(event,treeId,treeNode){
		treeid=treeNode.id
    }
  
	/**修改节点信息*/
    function update(){
	 		if (treeid==null) {
				alert("请选择一个节点");
			}else{
				
			}
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
		}   --%>
     function view(id){
         window.location.href="<%=basePath%>categoryparam/selectOne.html?id="+id;
     }
    
</script>
</head>

<body>

	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a><li><a href="#">产品参数管理</a><li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
   <div class="col-md-9 ">
   <div class="headline-v2 clear">
	   <h2>审核列表</h2>
	  </div>
	  <!-- <div class="tag-box tag-box-v3 mt10">
	 <div><ul id="ztree" class="ztree "></ul></div>
	 </div> -->
	</div>
	<div class=" tag-box mt10 col-md-9">
		<table class="table table-striped table-bordered table-hover">
	        <thead>
	            <tr>
	                <th>序号</th>
	                <th>品目</th>
	                <th>状态</th>
	            </tr>
	        </thead>	
	        <c:forEach var="cate" items="${cate}" varStatus="vs">
	            <tr>
	            <td class="w50 tc pointer" onclick="view('${cate.id}')">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
	            <td class="tc pointer" onclick="view('${cate.id}')">${cate.name }</td>
	            <td class="tc pointer" onclick="view('${cate.id}')"><c:choose><c:when test="${cate.paramStatus=='0'}">待审</c:when></c:choose></td>
	            
	            </tr>
	        </c:forEach>
		</table>
		</div>
	</div>
  </body>
</html>
