<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>  
    <title>My JSP 'allocate.jsp' starting page</title>
    
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
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.exedit.js"></script>
<script src="${pageContext.request.contextPath}public/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}public/laypage-v1.3/laypage/laypage.js"></script>
<script type="text/javascript">
var datas;
var treeid=null;
$(document).ready(function(){
     var setting={
	    async:{
				autoParam:["id","name"],
				enable:true,
				url:"${pageContext.request.contextPath}/category/createtree.do",
				dataType:"json",
				type:"post",
			},
			callback:{
		    	onClick:zTreeOnClick,//点击节点触发的事件
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
			    chkboxType:{"Y" : "ps", "N" : "ps"},//勾选checkbox对于父子节点的关联关系  
       		    chkStyle:"checkbox",
       		    nocheckInherit: false,
				enable: true
		   },
		   view:{
		        selectedMulti: false,
		        showTitle: false,
		   },
     };
        $.fn.zTree.init($("#ztree"),setting,datas); 
        var treeObj = $.fn.zTree.getZTreeObj("ztree");
        var nodes = treeObj.getNodes();
        if (nodes.paramStatus=="已分配") {
        	treeObj.updateNode(nodes);
        }
      }); 

    /**点击事件*/
    function zTreeOnClick(event,treeId,treeNode){
	treeid=treeNode.id;
	$("#cateid").val(treeid);
        }
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
		            $("#page").val(e.curr);
		
		            $("#form").submit();
		        }
		    }
		});
  });

    /** 全选全不选 */
	function selectAll(){
		 var checklist = document.getElementsByName ("chkItem");
		 var checkAll = document.getElementById("checkAll");
		 if(checkAll.checked){
			   for(var i=0;i<checklist.length;i++)
			   {
			      checklist[i].checked = true;
			   } 
			 }else{
			  for(var j=0;j<checklist.length;j++)
			  {
			     checklist[j].checked = false;
			  }
		 }
	}
     /** 单选 */
    function check(){
    	 var sta = $("#status").parent().parent().find("td").slice(5,6).text();
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
     
	//获取选中节点 
	
	function allocate(){
		var status =$("#all").val();
		var ids=[];
		var treeObj=$.fn.zTree.getZTreeObj("ztree");  
	     var nodes=treeObj.getCheckedNodes(true);  
	     for(var i=0;i<nodes.length;i++){  
	        //获取选中节点的值  
	         ids.push(nodes[i].id); 
	     } 
		var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1&& ids.length!=0){
			window.location.href="${pageContext.request.contextPath}/categoryparam/edit_allocate.html?id="+id+"&ids="+ids+"&status="+status;
		}else if(id.length>1){
			layer.alert("只能选择一个部门",{offset: ['222px', '390px'], shade:0.01});
		}else if(ids.length<1) {
			layer.alert("请选择需要分配的品目节点",{offset: ['222px', '390px'], shade:0.01});
		
		}else if(sta=="已分配"){
			alert(sta);
			layer.alert("请选择需要分配的部门",{offset: ['222px', '390px'], shade:0.01});
		}
	  }
	function unallocate(){
		var status =$("#unall").val();
		var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		window.location.href="${pageContext.request.contextPath}/categoryparam/abrogate_allocate.html?id="+id+"&status="+status;
			
	}
	//品目管理
	function crud(){
		  window.location.href="${pageContext.request.contextPath}/categoryparam/crudCategory.html";
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
     </div>
   <div class=" tag-box tag-box-v3 mt10 col-md-9">
   <form id="form" action="${pageContext.request.contextPath}/categoryparam/query_orgnization.html" method="post">
        <input type="hidden"/>
   		<input type="hidden" name="page" value="" id="page"/>	
   		<span class="ml40">事业单位：</span><input type="text" name="name" value="" class="mt10"/>
        <span>所属领导：</span><input type="text" name="princinpal" value="" class="mt10"/>
        <input type="hidden" value="" name="status" id="status"/>
        <input type="submit"  value="查询" class="btn"/>
        <button id="all" type="button" value="已分配" onclick="allocate()"class="btn">分配</button>
        <button id="unall" type="button" value="未分配" onclick="unallocate()" class="btn">取消分配</button> 
            <button id="unall" type="button" onclick="crud();" class="btn">crud</button> 
        </form>
      
        <div class="p10_25">
        <table class="table table-bordered table-condensedb mt15 ml10" >
            <thead>    
                <tr>
                <th class="info w50"><input id="checkAll" type="checkbox" onclick="selectAll()"  /></th>
                <th class="info w80">序号</th>
                <th class="info">事业部门</th>
                <th class="info">领导</th>
                <th class="info">电话</th>
                <th class="info">状态</th>
                </tr>
            </thead>
            <c:forEach var="cate" items="${cate}"  varStatus="vs">
            <tr>
                <td class="tc pointer"><input  onclick="check()" type="checkbox" name="chkItem" value="${cate.id}"/></td>
                <td class="tc pointer">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
                <td class="tc pointer">${cate.name}</td>
                <td class="tc pointer">${cate.princinpal}</td>
                <td class="tc pointer">${cate.mobile}</td>
                <td id="status" class="tc pointer">${cate.status}</td></tr>
            </c:forEach>
       </table>
      
         </div>
        <div id="pagediv" align="right"></div>
         </div>
  </body>
</html>
