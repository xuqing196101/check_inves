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
    	   dataType:"json",
    	   type:"post",
    	   url:"<%=basePath%>categoryparam/search_info.html?id="+treeNode.id,
    	   success:function(allListNews){
            $("#result").empty();
    		    var categoryParam = allListNews.categoryParam;
    		   var name = categoryParam.name;
    		   var value = categoryParam.valueType;
    		   var names = name.split(",");
    		    var values = value.split(",");
    		   var html="";
    		   for ( var j = 0 ; j< names.length-1; j++){
   				html = html +"<tr><td>参数名称：<input class='mt10' type='text' value='"+names[j]+"'/></td><td>参数类型："
   				+"<select  name='valueType'>"
   				+"<option value='' selected='selected'>"+values[j]+"</option>"
   				+"<option value='字符型'>字符型</option>"
   				+"<option value='数字型'>数字型</option>"
   				+"<option value='日期'>日期</option><select/></td></tr>";
   	             }
    		
    		 $("#result").prepend(html);
    		     alert( $("#result").html());
    		    var category = allListNews.cate;
    		 
    		    var html1="";
    		   
    		    html1 =html1 +"<tr><td>是否公开:</td><td>"
    		                 +"<span class='ml30'><input type='radio' value='0' name='ispublish' <c:if test='"+${category.isPublish eq 0}+"'>'"+checked+"'</c:if>/>是</span>"
    		                 +"<span class='ml60'><input type='radio' value='1' name='ispublish' <c:if test='"+${category.isPublish eq 1}+"'>'"+checked+"'</c:if>/>否</span>"
    		                 +"</td></tr>";
    		    html1 = html1+"<tr><td>验收规范：</td><td><textarea>"+category.acceptRange+"</textarea></td></tr>";
    		                 $("#result").append(html1);
    		                 alert(html1)
    		 
    		  var caAptitude = allListNews.categoryAptitudes;
    		  var productname= "";
    		  var salename = "";
    		  for(var k =0 ; k< caAptitude.length;k++){
    			  productname = caAptitude[k].productName;
    			  
    			  salename = caAptitude[k].saleName;
    		  }
    		   var productnames = productname.split(",");
    		   var salenames = salename.split(",");
    		   var html2 = "";
    		   var html4="";
    		   var html3 = "";
    		   var html5="";
    		        html4=html+"<tr><td>生产型资质：</td><td id='input_id'></td</tr>";
    		        $("#result").append(html4);
    		   for(var m =0; m< productnames.length-1; m++){
    			   html2 = html2 +"<input class='mt10' type='text' value='"+productnames[m]+"' name='productName'/><br/>";
    		   $("#input_id").append(html2);
    		   }
    		        html5 = html5+"<tr><td>销售型资质：</td><td id='input_ids'></td></tr>";
    		        $("#result").append(html5);
    		   for(var n =0; n< salenames.length-1;n++){
    			   html3 = html3 +"<input class='mt10' type='text' value='"+salenames[n]+"' name='productName'/><br/>";
    		   $("#input_ids").append(html3);
    		   }
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
		   <li><a href="#"> 首页</a><li><a href="#">产品参数管理</a><li><a href="#">目录查询</a><li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
   <div class="col-md-3">
     
	 <div class="tag-box tag-box-v3 mt50">
	 <div><ul id="ztree" class="ztree "></ul></div>
	 </div>
     </div>
		<div class=" tag-box mt10 col-md-6">
            <table id="result"  class="table table-bordered table-condensedb mt15" >
            </table>
         </div>
      </div>
  </body>
</html>
