<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>菜单管理</title>
   	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="<%=basePath%>public/ztree/css/demo.css" type="text/css">
	<link rel="stylesheet" href="<%=basePath%>public/ztree/css/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="<%=basePath%>public/ztree/jquery.ztree.core.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/ztree/jquery.ztree.excheck.js"></script>
	<script src="<%=basePath%>public/layer/layer.js"></script>
	<script type="text/javascript">
	    
		$(function(){
			var setting = {
				async:{
					autoParam:["id"],
					enable:true,
					url:"<%=basePath%>preMenu/treedata.do",
					dataType:"json",
					type:"post",
				},
				data: {
					simpleData: {
						enable:true,
						idKey:"id",
						pId:"pId",
						rootPId:-1,
					}
				},
				callback:{
					onClick:zTreeOnClick
				}
			};
			var treeObj=$.fn.zTree.init($("#menuTree"),setting);
			treeObj.expandAll(false);
			getList("0");
		});
		
		function zTreeOnClick(event,treeId,treeNode){
			$("#checkedAll").attr("checked",false);
			getList(treeNode.id);
			$("#mid").val(treeNode.id);
		};
		
		function getList(id){
			$.ajax({   
	            type: "POST",  
	            dataType: "json",
	            async:false,
	            url: "<%=basePath%>preMenu/findListByParent.do?id="+id,         
	            success: function(data) {
	            	var len = data[0].list.length;
	            	var tabhtml = "";
	            	for(var i = 0; i < len; i++){
	            		var state="";
	            		if(data[0].list[i].state==0){
	            			state =+ "可用";
	            		}else if(data[0].list[i].state==1){
	            			state =+ "暂停";
	            		}
	            		var state="";
	            		tabhtml += "<tr>";
	            		tabhtml +="<td class='tc'><input onclick='check()' type='checkbox' name='chkItem' value="+data[0].list[i].id+" /></td>";
	            		tabhtml +="<td class='tc'>"+1+"</td>";
	            		tabhtml +="<td class='tc'>"+data[0].list[i].name+"</td>";
	            		tabhtml +="<td class='tc'>"+data[0].list[i].type+"</td>";
	            		tabhtml +="<td class='tc'>"+data[0].list[i].menulevel+"</td>";
	            		tabhtml +="<td class='tc'>"+data[0].list[i].url+"</td>";
	            		tabhtml +="<td class='tc'>"+state+"</td>";
	            	}
	            	$("#tab_menu tbody").html("");
	            	$("#tab_menu tbody").append(tabhtml);
	            }  
	        });
		}
	</script>
  </head>
  
  <script type="text/javascript">
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
	
  	function view(id){
  		window.location.href="<%=basePath%>role/view.do?id="+id;
  	}
    function edit(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			
			window.location.href="<%=basePath%>role/edit.do?id="+id;
		}else if(id.length>1){
			layer.alert("只能选择一个角色",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选择需要修改的角色",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    function del(){
    	var ids =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length>0){
			layer.confirm('您确定要删除该菜单及其下子菜单吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				layer.close(index);
				window.location.href="<%=basePath%>preMenu/delete_soft.html?ids="+ids;
			});
		}else{
			layer.alert("请选择要删除的菜单",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    function add(){
    	var pid = $("#mid").val();
		layer.open({
		  type: 2, //page层
		  area: ['430px', '400px'],
		  title: '添加菜单',
		  closeBtn: 1,
		  shade:0.01, //遮罩透明度
		  moveType: 1, //拖拽风格，0是默认，1是传统拖动
		  shift: 1, //0-6的动画形式，-1不开启
		  offset: ['120px', '550px'],
		  shadeClose: false,
		  content: '<%=basePath%>preMenu/add.html?pid='+pid
		});
    }
  </script>
  <body>
	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">后台管理</a></li><li class="active"><a href="#">菜单功能管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
	   <div class="headline-v2">
	   		<h2>菜单功能管理</h2>
	   </div>
   </div>
   
	<!-- 表格开始-->
   <div class="container">
   		<!-- 菜单树-->
		<div id="menuTree" class="ztree fl"></div>
		<div class="fr">
			    <div class="col-md-8">
				    <button class="btn btn-windows add" type="button" onclick="add()">新增</button>
					<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
					<button class="btn btn-windows delete" type="button" onclick="del();">删除</button>
			    </div>
			    <input type="hidden" name="nodeId" id="mid">
			    <table id="tab_menu" class="table table-bordered table-condensed">
					<thead >
						<tr>
						  <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
						  <th class="info w50">序号</th>
						  <th class="info">名称</th>
						  <th class="info">类型</th>
						  <th class="info">级别</th>
						  <th class="info">URL</th>
						  <th class="info">状态</th>
						</tr>
					</thead>
					<tbody >
					</tbody>
		        </table>
		    
	   </div>
    </div>
   <div id="pagediv" align="right"></div>
   
  </body>
</html>
