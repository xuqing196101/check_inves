<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>修改菜单</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="<%=basePath%>public/ZHH/css/common.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/style.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/app.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/application.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet" type="text/css">
	<link rel="stylesheet" href="<%=basePath%>public/ztree/css/demo.css" type="text/css">
	<link rel="stylesheet" href="<%=basePath%>public/ztree/css/zTreeStyle.css" type="text/css">
	
	<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=path %>/public/ZHH/js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/lodop/LodopFuncs.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/ztree/jquery.ztree.core.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/ztree/jquery.ztree.excheck.js"></script>
	<script src="<%=basePath%>public/layer/layer.js"></script>
	<SCRIPT type="text/javascript">
		var setting = {
			check: {
				enable: true,
				chkStyle: "radio",
				radioType: "all"
			},
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: onClick,
				onCheck: onCheck
			}
		};
		function onClick(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		}
		function onCheck(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].name + ",";
				$("#pid").val(nodes[i].id);
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			var cityObj = $("#citySel");
			cityObj.attr("value", v);
			
			hideMenu();
		}
		function showMenu() {
			$.ajax({
             type: "GET",
             async: false, 
             url: "<%=basePath%>preMenu/treedata.do?",
             dataType: "json",
             success: function(zNodes){
                     for (var i = 0; i < zNodes.length; i++) { 
			            if (zNodes[i].isParent) {  
			  
			            } else {  
			                //zNodes[i].icon = "${ctxStatic}/images/532.ico";//设置图标  
			            }  
			        }  
			        tree = $.fn.zTree.init($("#treeDemo"), setting, zNodes);  
			        tree.expandAll(true);//全部展开
               }
         	});
			var cityObj = $("#citySel");
			var cityOffset = $("#citySel").offset();
			$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
			$("body").bind("mousedown", onBodyDown);
		}
		function hideMenu() {
			$("#menuContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown);
		}
		function onBodyDown(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "citySel" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
				hideMenu();
			}
		}
	</SCRIPT>
  </head>
  <script type="text/javascript">
    $(function(){
        $("#update").click(function(){
        	var pid = $("#pid").val();
            $.ajax({  
               type: "POST",  
               url: "<%=basePath %>preMenu/update.html?pid="+pid,  
               data: $("#form1").serializeArray(),  
               dataType: 'json',  
               success:function(result){
                    if(!result.success){
                        layer.msg(result.msg,{offset: ['150px', '180px']});
                    }else{
                        parent.window.setTimeout(function(){
                            parent.window.location.href = "<%=basePath%>preMenu/list.html";
                        }, 1000);
                        layer.msg(result.msg,{offset: ['150px', '180px']});
                    }
                },
                error: function(result){
                    layer.msg("添加失败",{offset: ['150px', '180px']});
                }
            });
            
        });
        $("#backups").click(function(){
            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            parent.layer.close(index); 
        });
    });
  </script>
 <body>
   
   <div class="container">
   	   <div id="menuContent" class="menuContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
			<ul id="treeDemo" class="ztree"  style="width: 220px"></ul>
	   </div>
		
	   <form action="" id="form1" method="post">
		   <div>
		   	   <input type="hidden" name="id" id="mid" value="${menu.id }">
		   	   <input type="hidden" name="isDeleted" value="${menu.isDeleted }">
		   	   <input type="hidden" name="pid" id="pid" value="${menu.parentId.id }">
			   <ul class="list-unstyled mt10 p0_20">
			     <li class="col-md-6 p0">
				   <span class="fl mt5">上级：</span>
				   <div class="input-append ">
				   	<input id="citySel" class="span2" type="text" readonly value="${menu.parentId.name }"  onclick="showMenu();" />
			        <span class="add-on">i</span>
			       </div>
				 </li>
			     <li class="col-md-6 p0">
				   <span class="fl mt5">名称：</span>
				   <div class="input-append">
			        <input class="span2" name="name" maxlength="30" value="${menu.name }" type="text">
			        <span class="add-on">i</span>
			       </div>
				 </li>
				 <li class="col-md-6 p0 ">
				 	<span class="fl mt5">类型：</span>
					<select name="type" class="w180 mt5" >
					 	<option value="">-请选择-</option>
					   	<option value="navigation" <c:if test="${'navigation' eq menu.type}">selected</c:if>>导航</option>
					   	<option value="accordion" <c:if test="${'accordion' eq menu.type}">selected</c:if>>折叠导航</option>
					   	<option value="menu" <c:if test="${'menu' eq menu.type}">selected</c:if>>菜单</option>
					   	<option value="button" <c:if test="${'button' eq menu.type}">selected</c:if>>按钮</option>
					</select>
				</li>
				<li class="col-md-6 p0 ">
				 	<span class="fl mt5">状态：</span>
					<select  name="status" class="w180 mt5" >
					   	<option value="0" <c:if test="${'0' eq menu.status}">selected</c:if>>可用</option>
					   	<option value="1" <c:if test="${'1' eq menu.status}">selected</c:if>>暂停</option>
				    </select>
				</li>
				<li class="col-md-6 p0">
				   <span class="fl mt5">路径：</span>
				   <div class="input-append">
			        <input class="span2 mt5" name="url" value="${menu.url }" maxlength="300" type="text">
			        <span class="add-on mt5">i</span>
			       </div>
				 </li>
			     <li class="col-md-6 p0">
				   <span class="fl mt5">排序：</span>
				   <div class="input-append">
			        <input class="span2" name="position" value="${menu.position }" maxlength="3" type="text">
			        <span class="add-on">i</span>
			       </div>
				 </li>
				<li class="col-md-6 p0">
				   <span class="fl mt5">名称：</span>
				   <div class="input-append">
			        <input class="span2" name="icon" value="${menu.icon }" maxlength="200" type="text">
			        <span class="add-on">i</span>
			       </div>
				 </li>
			   </ul>
		  </div> 
	   
		  <div  class="col-md-12">
		    <div class="fl padding-10">
			    <button class="btn btn-windows reset" id="update" type="button">更新</button>
			    <button class="btn btn-windows back" id="backups" type="button">返回</button>
			</div>
		  </div>
	  </form>
  </div>
 </body>
</html>
