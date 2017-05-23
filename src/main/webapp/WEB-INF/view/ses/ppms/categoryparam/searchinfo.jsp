<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML PUBLIC>
<html>
  <head> 
<%@ include file="/WEB-INF/view/common.jsp"%>
    <title>My JSP 'serarchinfo.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/ztree/css/zTreeStyle.css"> 
<%-- <link rel="stylesheet" type="text/css" href="<%=basePath%>/public/ztree/css/demo.css"> --%>

<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.excheck.js"></script>
<script type="text/javascript">
    var datas;
    var treeid=null;
    $(document).ready(function(){
	 $.fn.zTree.init($("#ztree"),setting,datas); 
	 setTimeout(function(){  
	        expandAll("ztree");  
	    },100);//延迟加载  
    }); 


     var setting={
	    async:{
				autoParam:["id"],
				enable:true,
				url:"${pageContext.request.contextPath}/category/createtree.do",
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
  			    beforeAsync: beforeAsync,  
                onAsyncSuccess: onAsyncSuccess  
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
      }
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
	$("#cateid").val(treeid);
}

/**重命名和删除的回调函数*/	
    function zTreeOnRemove(event, treeId, treeNode,isCancel) {
	}
    function zTreeOnRename(event, treeId, treeNode, isCancel) {
			 alert(treeNode.tId + ", " + treeNode.name); 
			
	}
    function zTreeOnClick(event, treeId, treeNode,isCancel) {
      
    };
    
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
    function zTreeBeforeClick(treeId, treeNode) {
       $.ajax({
    	   dataType:"json",
    	   type:"post",
    	   url:"${pageContext.request.contextPath}/categoryparam/search_info.html?id="+treeNode.id,
    	   success:function(allListNews){
            $("#result").empty();
    		   var name= allListNews.name;
    		   var value = allListNews.value;
    		   var names = name.split(",");
    		    var values = value.split(",");
    		   var html="";
    		   for ( var j = 0 ; j< names.length-1; j++){
   				html = html +"<tr><td class='info'>参数名称：</td><td><input class='mb0' type='text' value='"+names[j]+"'/></td><td class='info'> 参数类型：</td><td>"
   				+"<select  name='valueType'>"
   				+"<option value='' selected='selected'>"+values[j]+"</option>"
   				+"<option value='字符型'>字符型</option>"
   				+"<option value='数字型'>数字型</option>"
   				+"<option value='日期型'>日期型</option><select/></td></tr>";
   	             }
    		 $("#result").prepend(html);
    		  
    		    var category = allListNews.cate;
    		    var va = category.isPublish;
    		   
    		    var html1="";
    		    html1 = html1 +"<tr><td class='info'>是否公开:</td><td colspan='3'>"
    		                  +"<span><input  type='radio' value='0' name='ispublish' class='mt0'/>是</span>"
    		                  +"<span class='ml60'><input  type='radio' value='1' name='ispublish' class='mt0'/>否</span>"
    		                  +"</td></tr>";
    		    html1 = html1 +"<tr><td class='info'>验收规范：</td><td colspan='3'><textarea class='col-md-12 h80'>"+category.acceptRange+"</textarea></td></tr>";
    		                 $("#result").append(html1);
    		    obj=document.getElementsByName("ispublish");
    		    for(var i=0;i<obj.length;i++){
    		    	if ($(obj[i]).val()==va) {
						$(obj).attr("checked",true);
					}
    		    }
    		                
    		 
    		  var productname = allListNews.productname;
    		  var salename = allListNews.salename;
    		   var productnames = productname.split(",");
    		   var salenames = salename.split(",");
    		   var html2 = "";
    		   var html4 = "";
    		   var html3 = "";
    		   var html5 = "";
    		        html4=html4+"<tr><td class='info'>生产型资质：</td><td id='input_id' colspan='3'></td></tr>";
    		        $("#result").append(html4);
    		   for(var m =0; m< productnames.length-1; m++){
    			   html2 = html2 +"<input class='mr10 mb0' type='text' value='"+productnames[m]+"' name='productName'/>";
    		   }
    		   $("#input_id").append(html2);
    		        html5 = html5+"<tr><td class='info'>销售型资质：</td><td id='input_ids' colspan='3'></td></tr>";
    		        $("#result").append(html5);
    		   for(var n =0; n< salenames.length-1;n++){
    			   html3 = html3 +"<input class='mr10 mb0' type='text' value='"+salenames[n]+"' name='productName'/>";
    		   }
    		   $("#input_ids").append(html3);
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
		   <li><a href="javascript:void(0);"> 首页</a><li><a href="javascript:void(0);">产品参数管理</a></li><li><a href="javascript:void(0);">目录查询</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container content">
     <div class="col-md-3">
	   <div class="tag-box tag-box-v3">
	    <div><ul id="ztree" class="ztree "></ul></div>
	   </div>
     </div>
		<div class="tag-box tag-box-v4 col-md-9">
             <div class="col-md-12"><button class="btn btn-windows back" type="submit">返回</button></div>
            <table id="result"  class="table table-bordered mt20" >
            </table>
         </div>
      </div>
  </body>
</html>
