<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<script type="text/javascript">
	    
		$(function(){
			var setting = {
				async:{
					autoParam:["id"],
					enable:true,
					url:"${pageContext.request.contextPath}/preMenu/treedata.do",
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
			treeObj.expandAll(true);
			getDetail("0");
		});
		
		function zTreeOnClick(event,treeId,treeNode){
			$("#checkedAll").attr("checked",false);
			getDetail(treeNode.id);
			$("#mid").val(treeNode.id);
		};
		
		function getDetail(id){
			$.ajax({   
	            type: "POST",  
	            dataType: "json",
	            async:false,
	            url: "${pageContext.request.contextPath}/preMenu/get.do?id="+id,         
	            success: function(data) {
	            	if(data != null && data != ''){
		            	var tabhtml = "";
	            		var state;
	            		var kind;
	            		var pName;
	            		if(data[0].status == 0){
	            			state = "<span class='label rounded-2x label-u'>可用</span>";
	            		}else if(data[0].status == 1){
	            			state = "<span class='label rounded-2x label-dark'>暂停</span>";
	            		}
	            		if(data[0].kind == 0){
	            			kind = "采购管理后台";
	            		}else if(data[0].kind == 1){
	            			kind = "供应商后台";
	            		}else if(data[0].kind == 2){
	            			kind = "专家后台";
	            		}else if(data[0].kind == 3){
	            			kind = "进口供应商后台";
	            		}
	            		if(data[0].parentId == null){
	            			pName = "";
	            		}else{
	            			pName = data[0].parentId.name;
	            		}
	            		tabhtml +='<h2 class="f16 jbxx">菜单详情</h2><table class="table table-bordered"><tbody>';
	            		tabhtml +='<tr><td class="bggrey tr">上级菜单：</td><td>'+pName+'</td>';
						tabhtml +='<td class="bggrey tr">菜单名称：</td><td>'+data[0].name+'</td>';
						tabhtml +='<td class="bggrey tr">请求路径：</td><td>'+data[0].url+'</td></tr>';
						tabhtml +='<tr><td class="bggrey tr">菜单类型：</td><td>'+data[0].type+'</td>';
						tabhtml +='<td class="bggrey tr">菜单序号：</td><td>'+data[0].position+'</td>';
						tabhtml +='<td class="bggrey tr">菜单级别：</td><td>'+data[0].menulevel+'</td></tr>';
						tabhtml +='<tr><td class="bggrey tr">菜单状态：</td><td>'+state+'</td>';
						tabhtml +='<td class="bggrey tr">菜单图标：</td><td>'+data[0].icon+'</td>';
						tabhtml +='<td class="bggrey tr">创建时间：</td><td>'+data[0].createdAt+'</td></tr>';
						tabhtml +='<tr><td class="bggrey tr">修改时间：</td><td colspan="5">'+data[0].updatedAt+'</td></tr>';
						tabhtml +='</tbody></table>';
		            	$("#show_content_div").html("");
		            	$("#show_content_div").append(tabhtml);
	            	}
	            }  
	        });
		}
	</script>
  </head>
  
  <script type="text/javascript">
	
    function edit(){
    	var mid = $("#mid").val();
		if(mid != null && mid != '' ){
			layer.open({
			  type: 2, //page层
			  area: ['430px', '400px'],
			  title: '修改菜单',
			  closeBtn: 1,
			  shade:0.01, //遮罩透明度
			  moveType: 1, //拖拽风格，0是默认，1是传统拖动
			  shift: 1, //0-6的动画形式，-1不开启
			  offset: '120px',
			  shadeClose: false,
			  content: '${pageContext.request.contextPath}/preMenu/edit.html?id='+mid
			});
		}else{
			layer.alert("请选择一个节点",{offset: '222px', shade:0.01});
		}
    }
    
    function del(){
    	var mid = $("#mid").val();
		if(mid != null && mid != '' ){
			layer.confirm('您确定要删除该菜单吗?', {title:'提示',offset: '222px',shade:0.01}, function(index){
				layer.close(index);
				window.location.href="${pageContext.request.contextPath}/preMenu/delete.html?ids="+mid;
			});
		}else{
			layer.alert("请选择要删除的菜单",{offset: '222px', shade:0.01});
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
		  offset: '120px',
		  shadeClose: false,
		  content: '${pageContext.request.contextPath}/preMenu/add.html?pid='+pid
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
   <div class="container content height-350">
       <div class="row">
                <!-- Begin Content -->
                <div class="col-md-12" style="min-height:400px;">
					<div class="col-md-3 md-margin-bottom-40" id="show_tree_div">
						<div class="tag-box tag-box-v3">
							<ul id="ztree_show" class="ztree">
								<!-- 菜单树-->
								<div id="menuTree" class="ztree fl"></div>
							</ul>
						</div>
					</div>
					<div style="margin-bottom: 6px; ">
						<button class="btn btn-windows add" type="button" onclick="add()">新增</button>
						<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
						<button class="btn btn-windows delete" type="button" onclick="del();">删除</button>
					</div>
					<input type="hidden" id="mid">
					<div class="tag-box tag-box-v4 col-md-9" id="show_content_div">
		                
			        </div>
             	 </div>
       </div>
   </div>
</body>
</html>
