<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/WEB-INF/view/common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <base href="<%=basePath%>">
    
<title>My JSP 'category.jsp' starting page</title>

 <link rel="stylesheet" type="text/css" href="<%=basePath%>/public/ztree/css/zTreeStyle.css"> 
<%-- <link rel="stylesheet" type="text/css" href="<%=basePath%>/public/ztree/css/demo.css"> --%>

<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.exedit.js"></script>
<script src="<%=basePath%>public/layer/layer.js"></script>
<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
<script type="text/javascript">
	var treeid=null;
	
	 $(document).ready(function(){
	var datas;
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
    /**添加采购目录*/
    function news(){
			if (treeid==null) {
			alert("请选择一个节点");
					return;		
			}else{
			             
						var html = "";
						html = html+"<tr><td>上级目录</td>"+"<td><input  value='"+treename+"'/></td></tr>";
						html = html+"<input type='hidden' name='kind' value='"+parentKind+"'/>" ;
						html = html+"<input type='hidden' name='isEnd' value='"+isEnd+"'/>" ;
						html = html+"<tr><td>目录名称</td>"+"<td><input name='name'/></td></tr>" ;
				        html = html+"<input type='hidden' value='"+treeid+"' name='parentId'/>";
						html = html+"<tr><td>排序</td>"+"<td><input name='position'/></td></tr>";
						html = html+"<tr><td>编码</td>"+"<td><input name='code'/></td></tr>";
						html = html+"<tr><td>图片</td>"+"<td id='uploadAttach'><input type='file' class='toinline' name='attaattach' value='上传图片'/></td></tr>";
						html = html+"<tr><td>描述</td>"+"<td><textarea name='description'/></td></tr>";
						html = html+"<tr><td colspan='2' ><input  type='submit' onclick='add()' value='提交' class='mr30  btn btn-windows git'/>"
						+"<input type='button' class='ml10 btn btn-windows back ' value='返回' onclick=''history.go(-1)''/></td></tr>";
						$("#result").append(html);
						}
				
		
			}

	/**修改节点信息*/
    function update(){
	 		if (treeid==null){
				alert("请选择一个节点");
			}else{
				$.ajax({
					url:"<%=basePath%>category/update.do?id="+treeid,
					dataType:"json",
					type:"POST",
					success:function(cate){
						var attachmentPath = cate.categoryAttchment.attchmentPath;
						var html = "";
					 	html = html+"<tr><td>上级目录</td><td><input value='"+parentname+"' readonly='readonly'/></td></tr>"; 
						html = html+"<tr><td>目录名称</td><td><input value='"+cate.name+"' name='name'/></td></tr>";
						html = html+"<input type='hidden' name='id' value='"+cate.id+"'/>";
						html = html+"<tr><td>排序</td><td><input value='"+cate.position+"' name='position'/></td></tr>";
						html = html+"<tr><td>编码</td><td><input value='"+cate.code+"' name='code'/></td></tr>";
						html = html+"<tr><td>已上传的图片</td><td><button id='button' type='button' onclick='showPic()'>相关图片</button>"
						+"<img class='hide' id='photo' src='"+attachmentPath+"'/>"
						+"<input type='file' name='attaattach' value='重新上传' class='mt10'/></td></tr>";
						html = html+"<tr><td>描述</td><td><textarea name='description'>"+cate.description+"</textarea></td></tr>";
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
			  area: '800px',
			  skin: 'layui-layer-nobg', //没有背景色
			  shadeClose: true,
			  content: $("#photo")
			});
	};
    
 	/**休眠-激活*/
    function ros(){
 			var str="";
	 		var treeObj = $.fn.zTree.getZTreeObj("ztree");
			var nodes = treeObj.getCheckedNodes(true);
			for ( var i = 0; i < nodes.length; i++) {
				str+=nodes[i].id+",";
			}
			$.ajax({
				type:"POST",
				url:"<%=basePath%>category/ros.do?ids="+str,
			});
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
		
    /**新增提交*/		
	function add(id){
	    document.fm.action="<%=basePath%>category/save.do";
		document.fm.submit();
	
	}
	/**更新数据*/
	function renew(id){
		document.fm.action="<%=basePath%>category/edit.do";
		document.fm.submit();
	}
	/**删除附件*/
	function del(){
	     window.location.href="<%=basePath%>category/deleted.do";
	}	
	
	<%-- function look(){
	var key = $("#key").val();
	alert(key);
	$.ajax({
	     dataType:"json",
	     type:"post",
	     url:"<%=basePath%>category/search.do?name="+key,
	     success:function(nodeList){
 	     alert(nodeList);	
	     for ( var i = 0; i < nodeList.length; i++) {
		var node = nodeList[i];
		
	}
	
	      
	     }
	     });
	} --%>
  
</script>
</head>

<body>

<div class="wrapper">
	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a><li><a href="#">采购目录管理</a><li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
   <div class="col-md-3">
     
	 <div>
	   <input id="key" type="text" class="mt10"  placeholder="请输入..."  value=""/>
<button onclick="look()" class="btn  btn-window mr10"  type="button">sou</button>
 	 </div>
 	  <div class="tag-box tag-box-v3">
	 <div><ul id="ztree" class="ztree"></ul></div>
	</div>
	</div>
		<div class=" tag-box tag-box-v4 mt50 col-md-9">
			<span id="add"><a href="javascript:void(0);" onclick="news()" class="btn btn-windows add ">新增 </a></span> 
			<span><a href="javascript:void(0);" onclick="update()"  class="btn btn-windows edit ">修改</a></span> 
			<span><a href="javascript:void(0);" onclick="ros()"  class="btn btn-window ">激活/休眠</a></span>
  <%--  <form action="<%=basePath%>category/save.do" method="post" name="temp" enctype="multipart/form-data">
        
        <table id="result"  class="table table-bordered table-condensedb mt15" ></table>
    </form>
    < --%>
    <form  id="form" action="" name="fm" method="post"  enctype="multipart/form-data">
    <input type="hidden"  onclick="add()" value="submit"/>
    <input type="hidden"  onclick="renew()" value="submit"/>
    <input type="hidden" name="attchmentId" value=""/>
    <table id="result"  class="table table-bordered table-condensedb mt15" ></table>
    </form>
        </div>
	</div>
	</div>
</body>
</html>
