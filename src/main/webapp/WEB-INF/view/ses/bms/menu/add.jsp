<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
	<script type="text/javascript">
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
             url: "${pageContext.request.contextPath}/preMenu/treedata.do",
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
	</script>
  </head>
  <script type="text/javascript">
    $(function(){
        $("#save").click(function(){
        	
            $.ajax({  
               type: "POST",  
               url: "${pageContext.request.contextPath}/preMenu/save.html",  
               data: $("#form1").serializeArray(),  
               dataType: 'json',  
               success:function(result){
                    if(!result.success){
                        layer.msg(result.msg,{offset: ['150px', '180px']});
                    }else{
                        parent.window.setTimeout(function(){
                            parent.window.location.href = "${pageContext.request.contextPath}/preMenu/list.html";
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
   
   <div class="layui-layer-wrap">
   	   <div id="menuContent" class="menuContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
			<ul id="treeDemo" class="ztree"  style="width: 220px"></ul>
	   </div>
	   <form action="" id="form1" method="post">
		   <div class="drop_window">
		   	   <input type="hidden" name="id" id="pid" value="${pmenu.id }">
			   <ul class="list-unstyled">
			     <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
				   <label class="col-md-12 pl20 col-xs-12">上级</label>
				    <span class="col-md-12 col-xs-12">
	                   <input id="citySel" class="title col-md-12" name="pname" type="text" readonly value="${pmenu.name }"  onclick="showMenu();" />
	                </span>
			       
				 </li>
			     <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
				   <label class="col-md-12 pl20 col-xs-12">名称</label>
				   <span class="col-md-12 col-xs-12">
				        <input class="title col-md-12" name="name" maxlength="30" type="text">
                    </span>
				 </li>
				 <li class="col-sm-6 col-md-6 p0 col-lg-6 col-xs-6">
				 	<label class="col-md-12 pl20 col-xs-12">类型</label>
				 	<span class="col-md-12 col-xs-12">
					<select name="type"  class="w180 mt5">
					 	<option value="">-请选择-</option>
					   	<option value="navigation">导航</option>
					   	<option value="accordion">折叠导航</option>
					   	<option value="menu">菜单</option>
					   	<option value="button">按钮</option>
					</select>
					</span>
				</li>
				<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
				 	<label class="col-md-12 pl20 col-xs-12">状态</label>
				 	<span class="col-md-12 col-xs-12">
					<select  name="status" class="w180 mt5" >
					   	<option value="0">可用</option>
					   	<option value="1">冻结</option>
				    </select>
				    </span>
				</li>
				<li class="mt10 col-md-12 p0 col-xs-12">
				   <label class="col-md-12 pl20 col-xs-12">路径</label>
				     <span class="col-md-12 col-xs-12">
                        <input class="col-xs-12 h80 mt6" name="url" maxlength="300" type="text">
                    </span>
				 </li>
			     <li class="col-sm-6 p0 col-md-6 col-lg-6 col-xs-6">
				   <label class="col-md-12 pl20 col-xs-12">排序</label>
				    <span class="col-md-12 col-xs-12">
                        <input class="title col-md-12" name="position" maxlength="3" type="text">
                    </span>
				 </li>
				<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
				   <label class="col-md-12 pl20 col-xs-12">图标</label>
				    <span class="col-md-12 col-xs-12">
                        <input class="title col-md-12" name="icon" maxlength="200" type="text">
                    </span>
				 </li>
				 <div class="clear"></div>
			   </ul>
		  </div> 
	   
		  <div class="tc mt10 col-md-12 col-xs-12">
			    <button class="btn btn-windows save" id="save" type="button">保存</button>
			    <button class="btn btn-windows back" id="backups" type="button">返回</button>
		  </div>
	  </form>
	  </div>
 </body>
</html>
