<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'allocate.jsp' starting page</title>
    
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
<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
<script src="<%=basePath%>public/layer/layer.js"></script>
<script type="text/javascript">
$(function(){
	  laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${list.pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    skip: true, //是否开启跳页
		    total: "${list.total}",
		    startRow: "${list.startRow}",
		    endRow: "${list.endRow}",
		    groups: "${list.pages}">=3?3:"${list.pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
		        var page = location.search.match(/page=(\d+)/);
		        return page ? page[1] : 1;
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		            location.href = "<%=basePath%>categoryparam/search_category.html?page="+e.curr;
		        }
		    }
		});
  });
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
						title:"title",
						
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
    function query(){
         window.location.href="<%=basePath%>catgoryparam/query_orgnization.html"
    }
     /** 单选 */
    function check(){
		 var count=0;
		 var checklist = document.getElementsByName ("chkItem");
		 var checkAll = document.getElementById("checkAll");
		 for(var i=0;i<checklist.length;i++){
			   if(checklist[i].checked == false){
				   checkAll.checked = false;
				   break;
			   }
			   for(var j=0;j<checklist.length;j++){
					 if(checklist[j].checked == true){
						   checkAll.checked = true;
						   count++;
					   }
				 }
		   }
	}
	function query(){
        window.location.href="<%=basePath%>categoryparam/query_orgnization.html"; 	
	}
	function allocate(){
		var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			
			window.location.href="<%=basePath%>categoryparam/edit_allocate.html?id="+id;
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选择需要分配的部门",{offset: ['222px', '390px'], shade:0.01});
		}
		
	}
	function unallocate(){
		
	}
	
</script>
  </head>
  <body>
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">产品参数管理</a></li><li><a href="#">分配</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
   <div class="col-md-3">
     
	 <div class="tag-box tag-box-v3 mt10">
	 <div><ul id="ztree" class="ztree "></ul></div>
	 </div>
	</div >
   <div class=" tag-box tag-box-v3 mt10 col-md-9">
   <span>事业单位：</span><input type="text" name="name" value="" class="mt10"/>
        <span>所属领导：</span><input type="text" name="princinpal" value="" class="mt10"/>
        <input type="hidden" value="" name="status" id="status"/>
        <input type="button"  value="查询" onclick="query()" class="btn"/>
        <input type="button" value="分配" onclick="allocate('${cate.id}')"class="btn"/>
        <input type="button" value="取消分配" onclick="unallocate('${cate.id}')" class="btn"/> 
        <table class="table table-bordered table-condensedb mt15" >
            <thead>    
                <tr>
                <th class="info w50"><input id="selectAll" type="checkbox" onclick="selectAll()"  /></th>
                <th class="info w80">序号</th>
                <th class="info">事业部门</th>
                <th class="info">领导</th>
                <th class="info">电话</th>
                <th class="info">状态</th>
                </tr>
            </thead>
            <c:forEach var="cate" items="${cate}"  varStatus="vs">
                <td class="tc pointer"><input  onclick="check('${cate.id}')" type="checkbox" name="chkItem" value="${cate.id}"/></td>
                <td class="tc pointer">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
                <td class="tc pointer">${cate.name}</td>
                <td class="tc pointer">${cate.princinpal}</td>
                <td class="tc pointer">${cate.telephone}</td>
                <td class="tc pointer">${cate.status}</td>
            </c:forEach>
       </table>
        <div id="pagediv" align="right"></div>
 </div>
  </body>
</html>
