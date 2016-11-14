<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/WEB-INF/view/common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>   
<title>采购目录管理</title>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/ztree/css/zTreeStyle.css"> 
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.exedit.js"></script>
<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/ajaxfileupload.js"></script>
<script src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>

<script type="text/javascript">
	var treeid=null;
	var datas;
	 $(document).ready(function(){  
     $.fn.zTree.init($("#ztree"),setting,datas);
	 var treeObj = $.fn.zTree.getZTreeObj("ztree");
	 var nodes =  treeObj.transformToArray(treeObj.getNodes()); 
	 for(var i=0 ;i<nodes.length;i++){
		alert(nodes[i].status);
		if (nodes[i].status==1) {
			check==true;
		}
	}
     setTimeout(function(){  
        expandAll("ztree");  
    },100);//延迟加载  
	 }); 
	 var setting={
		   async:{
					autoParam:["id"],
					enable:true,
					url:"${pageContext.request.contextPath}/category/createtree.do",
					otherParam:{"otherParam":"zTreeAsyncTest"},  
					dataType:"json",
					datafilter:filter,
					type:"get",
				},
				callback:{
			    	onClick:zTreeOnClick,//点击节点触发的事件
			    	beforeRemove: zTreeBeforeRemove,
			    	beforeRename: zTreeBeforeRename, 
					onRemove: zTreeOnRemove,
       			    onRename: zTreeOnRename,
       			    beforeAsync: beforeAsync,  
                    onAsyncSuccess: onAsyncSuccess,
                   
                    beforeCheck: beforeCheck
       			    
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
			   view:{
			        selectedMulti: false,
			        showTitle: false,
			   },
         };
	function filter(treeId,parentNode,childNode){
		 if (!childNodes) return null;
		for(var i = 0; i<childNodes.length;i++){
			childNodes[i].name = childNodes[i].name.replace(/\.n/g,'.');
		}
		return childNodes;
	 }
    function beforeAsync(){
    	curAsyncCount++;
    }
   
    function onAsyncSuccess(event,treeId,treeNode,msg){
    	curAsyncCount--;
    	if(curStatus =="expand"){
    		expandNodes(treeNode.children);
    	}else if(curStatus == "async"){
    		asyncNodes(treeNode.children);
    	}
    	if(curAsyncCount <=0 ){
    		curStatus = "";
    	}
    }
    
    var curStatus = "init" , curAsyncCount = 0,goAsync =false;
    function expandAll(){
    	if(!check()){
    		return;
    	}
    	var tree = $.fn.zTree.getZTreeObj("ztree");
    	expandNodes(tree.getNodes());
    	if(!goAsync){
    		curStatus ="";
    	}
    }
    function expandNodes(nodes){
    	if(!nodes) return;
    	curStatus = "expand";
    	var tree = $.fn.zTree.getZTreeObj("ztree");
    	for(var  i =0;i<nodes.length;i++){
    		tree.expandNode(nodes[i],true,false,false);//展开节点就会调用后台查询子节点
    		if(nodes[i].isParent && nodes[i].zAsync){
    			expandNodes(nodes[i].children);//递归
    		}else{
    			goAsync = true;
    		}
    			
    	}
    }
    function check(){
    	if(curAsyncCount > 0){
    		return false;
    	}
    	return true;
    }
  
  
    /**点击事件*/
    function zTreeOnClick(event,treeId,treeNode){
		treeid=treeNode.id;
		treename=treeNode.name;
	    parentKind=treeNode.kind;
	    isEnd=treeNode.isEnd;
	    parentname=treeNode.getParentNode().name;
	    status = treeNode.status;
	    
    }
    function beforeCheck(treeId, treeNode) {
        return true;
    };
    
    /**添加采购目录*/
    function news(){
			if (treeid==null) {
				layer.alert("请选择一个节点",{offset: ['150px', '500px'], shade:0.01});
			return;		
			      }else{
			    	    $("#result").empty();
			    	    var zTree = $.fn.zTree.getZTreeObj("ztree");
						nodes = zTree.getSelectedNodes();
						var node = nodes[0];
						if(node.isParent){
							parentname==treename;
						}
						var html = "";
						html = html+"<tr><td class='info'>上级目录</td>"+"<td><input  value='"+treename+"'/></td></tr>";
						html = html+"<input type='hidden' name='kind' value='"+parentKind+"'/>" ;
					    html = html+"<tr><td class='info'>目录名称</td>"+"<td><input name='name' type='text'/></td></tr>" ;
				        html = html+"<input type='hidden' value='"+treeid+"' name='parentId'/>";
						html = html+"<tr><td class='info'>排序</td>"+"<td><input name='position' type='text'/></td></tr>";
						html = html+"<tr><td class='info'>编码</td>"+"<td><input name='code' type='text'/></td></tr>";
						html = html+"<tr><td class='info'>图片</td>"+"<td><input id='pic' type='file' name='attaattach' value='上传图片'/></td></tr>";
						html = html+"<tr><td class='info'>描述</td>"+"<td><textarea name='description'/></td></tr>";
						html = html+"<tr><td colspan='2'  ><input  type='button' onclick='add()'  value='提交'  class='mr30  btn btn-windows git'/>"
						+"<input type='button' class='ml10 btn btn-windows  back' value='返回' onclick='history.go(-1)''/></td></tr>";
						$("#result").append(html);
					}
			}

	/**修改节点信息*/
    function update(){
	 		if (treeid==null){
	 			layer.alert("请选择一个节点",{offset: ['150px', '500px'], shade:0.01});
			}else{
				$.ajax({
					url:"${pageContext.request.contextPath}/category/update.do?id="+treeid,
					dataType:"json",
					type:"POST",
					success:function(cate){
						$("#result").empty();
						var attachmentPath = cate.categoryAttchment.attchmentPath;
						var pic = cate.categoryAttchment.fileName;
						var picname = pic.split("_");
						var html = "";
					 	html = html+"<tr><td class='info'>上级目录</td><td><input value='"+parentname+"' readonly='readonly'/></td></tr>"; 
						html = html+"<tr><td class='info'>目录名称</td><td><input value='"+cate.name+"' name='name'/></td></tr>";
						html = html+"<input type='hidden' name='id' value='"+cate.id+"'/>";
						html = html+"<tr><td class='info'>排序</td><td><input value='"+cate.position+"' name='position'/></td></tr>";
						html = html+"<tr><td class='info'>编码</td><td><input value='"+cate.code+"' name='code'/></td></tr>";
						if (attachmentPath!=null&&attachmentPath!="") {
					    html = html+"<tr><td class='info'>已上传的图片</td><td><a id='button' class='pointer' name='attaattach' type='button' onclick='showPic()'>"+picname[1]+"</a>"
						+"<img class='hide' id='photo' src='"+attachmentPath+"'/>"
						+"<input type='file' name='attaattach' value='重新上传' class='mt10'/></td></tr>";
						}
						html = html+"<tr><td class='info'>描述</td><td><textarea name='description'>"+cate.description+"</textarea></td></tr>";
						html = html+"<tr><td colspan='2'><input  type='submit' onclick='renew()' value='更新' class=' mr30  btn btn-windows reset '/>"
						+"<input type='button' class='ml10 btn btn-windows  back' value='返回' onclick='history.go(-1)''/></td></tr>";
						$("#result").append(html);
				      }
                   });
                }
             }
	

	
      /**图片展示*/      
    function showPic(){
		layer.open({
			  type: 1,
			  title: false,
			  closeBtn: 0,
			  area: '800',
			  skin: 'layui-layer-nobg', //没有背景色
			  shadeClose: true,
			  content: $("#photo")
			});
	};
    
 	/**休眠-激活*/
    function ros(ids){
    	var str="";
 		var treeObj = $.fn.zTree.getZTreeObj("ztree");
		var nodes = treeObj.getCheckedNodes(true);
		for ( var i = 0; i < nodes.length; i++) {
			str+=nodes[i].id+",";
		}
      window.location.href="${pageContext.request.contextPath}/category/del.do?ids="+str;
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
	 			url:"${pageContext.request.contextPath}/category/del.do?id="+treeNode.id,
	 		});
		}
	 	
	/**节点重命名*/
    function zTreeBeforeRename(treeId,treeNode,newName,isCancel){
			$.ajax({
	 			type:"post",
	 			url:"${pageContext.request.contextPath}/category/rename.do?id="+treeNode.id+"&name="+newName,
	 		});
		} 
    function add(id){
    	
    	$.ajaxFileUpload({
    		cache:true,
    		dataType:"text",
    		type:"post",
    		url:"${pageContext.request.contextPath}/category/save.html",
    		success:callback
    	});
    }
   function callback(allListNews){
	   
   }
   
	/**更新数据*/
	function renew(id){
		document.fm.action="${pageContext.request.contextPath}/category/edit.do";
		document.fm.submit();
	}
	/**删除附件*/
	function del(){
	     window.location.href="${pageContext.request.contextPath}/category/deleted.do";
	}	
	
    /**根据关键字查询*/
   
</script>
</head>

<body>
	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">采购目录管理</a></li><li><a href="#">首页</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
   <div class="col-md-3"><%--
   <div>
   <span><input id="key" type="text" /><input id="query" type="button" value="搜索"/></span>
   </div>
	--%><div class="tag-box tag-box-v3 mt15">
	 <div><ul id="ztree" class="ztree"></ul></div>
	</div>
	</div>
		<div class=" tag-box tag-box-v3 mt15 col-md-8">
			<span><a href="javascript:void(0);" onclick="news()" class="btn btn-windows add ">新增 </a></span> 
			<span><a href="javascript:void(0);" onclick="update()"  class="btn btn-windows edit ">修改</a></span> 
			<span><a href="javascript:void(0);" onclick="ros()"  class="btn btn-window ">激活/休眠</a></span>
            <form  id="form" action="" name="fm" method="post" enctype="multipart/form-data" >
            <input type="hidden"  onclick="add()" value=""/>
            <input type="hidden"  onclick="renew()" value=""/>
            <table id="result"  class="table table-bordered table-condensedb mt15" ></table>
            </form>
        </div>
	</div>
</body>
</html>
