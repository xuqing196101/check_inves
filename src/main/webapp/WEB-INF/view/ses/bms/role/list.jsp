<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>角色管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="<%=basePath%>public/layer/layer.js"></script>
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
	
	function openPreMenu(){
		var ids =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length==1){
			var iframeWin;
			layer.open({
			  type: 2, //page层
			  area: ['300px', '500px'],
			  title: '配置权限',
			  closeBtn: 1,
			  shade:0.01, //遮罩透明度
			  moveType: 1, //拖拽风格，0是默认，1是传统拖动
			  shift: 1, //0-6的动画形式，-1不开启
			  offset: ['180px', '550px'],
			  shadeClose: false,
			  //content: menucon,
			  content: '<%=basePath%>role/openPreMenu.html?id='+ids,
			  success: function(layero, index){
			    iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
			  },
			  btn: ['保存', '关闭'] 
			  ,yes: function(){
			    iframeWin.onCheck(ids);
			  }
			  ,btn2: function(){
			    layer.closeAll();
			  }
			});
		}else if(ids.length>1){
			layer.alert("只能同时选择一个角色",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选择一个角色",{offset: ['222px', '390px'], shade:0.01});
		}
	
	}
	
  	function view(id){
  		wi.htmlw.location.href="<%=basePath%>role/view.html?id="+id;
  	}
  	
    function edit(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			layer.open({
			  type: 2, //page层
			  area: ['450px', '350px'],
			  title: '修改角色',
			  closeBtn: 1,
			  shade:0.01, //遮罩透明度
			  moveType: 1, //拖拽风格，0是默认，1是传统拖动
			  shift: 1, //0-6的动画形式，-1不开启
			  offset: ['80px', '400px'],
			  shadeClose: false,
			  content: '<%=basePath%>role/edit.html?id='+id
			});
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
			layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				layer.close(index);
				window.location.href="<%=basePath%>role/delete.html?ids="+ids;
			});
		}else{
			layer.alert("请选择要删除的角色",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    
    function add(){
    	layer.open({
			  type: 2, //page层
			  area: ['40%', '350px'],
			  title: '新增角色',
			  closeBtn: 1,
			  shade:0.01, //遮罩透明度
			  moveType: 1, //拖拽风格，0是默认，1是传统拖动
			  shift: 1, //0-6的动画形式，-1不开启
			  offset: ['80px', '400px'],
			  shadeClose: false,
			  content: '<%=basePath%>role/add.html'
			});
    }
  </script>
  <body>
	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">后台管理</a></li><li class="active"><a href="#">角色管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
	   <div class="headline-v2">
	   		<h2>角色管理</h2>
	   </div>
   </div>
   <!-- 菜单树-->
   <div id="menu">
	   <div id="menuTree" class="ztree"></div>
   </div>
	<!-- 表格开始-->
   <div class="container">
	   <div class="col-md-8">
	    <button class="btn btn-windows add" type="button" onclick="add()">新增</button>
		<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
		<button class="btn btn-windows delete" type="button" onclick="del();">删除</button>
		<button class="btn btn-windows edit" type="button" onclick="openPreMenu()">设置权限</button>
	   </div>
       <div class="col-md-4 ">
         <div class="search-block-v2">
           <div class="">
             <form accept-charset="UTF-8" action="" method="get"><div style="display:none"><input name="utf8" value="✓" type="hidden"></div>
               <input id="t" name="t" value="search_products" type="hidden">
               <div class="col-md-12 pull-right">
                 <div class="input-group">
                   <input class="form-control bgnone h37 p0_10" id="k" name="k" placeholder="" type="text">
                   <span class="input-group-btn">
                     <input class="btn-u" name="commit" value="搜索" type="submit">
                   </span>
                 </div>
               </div>
             </form>               
			</div>
         </div>
       </div>	
    </div>
   
   <div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
        <table class="table table-bordered table-condensed">
			<thead>
				<tr>
				  <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
				  <th class="info w50">序号</th>
				  <th class="info">名称</th>
				  <th class="info">用户数</th>
				  <th class="info">描述</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="role" varStatus="vs">
				   <tr>
					  <td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${role.id}" /></td>
					  <td class="tc">${vs.index+1}</td>
					  <td class="tc" onclick="view(${role.id});">${role.name}</td>
					  <td class="tc">${role.users.size()}</td>
					  <td class="tc">${role.description}</td>
				   </tr>
				</c:forEach>
			</tbody>
        </table>
     </div>
   
   </div>
  </body>
</html>
