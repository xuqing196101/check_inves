<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
    
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
			     <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6 pl15">
				   <span class="col-md-12 padding-left-5 col-xs-12 col-sm-12"><div class="star_red">*</div>上级</span>
				    <div class="col-md-12 col-xs-12 col-sm-12 p0 input_group input-append">
	                   <input id="citySel" class="title col-md-12" name="pname" type="text" readonly value="${pmenu.name }"  onclick="showMenu();" />
	                </div>			       
				 </li>
			     <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
				  <span class="col-md-12 padding-left-5 col-xs-12 col-sm-12"><div class="star_red">*</div>名称</span>
				  <div class="col-md-12 col-xs-12 col-sm-12 p0 input_group input-append">
				        <input class="title col-md-12" name="name" maxlength="30" type="text">
                  </div>
				 </li>
				 <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
				 	<span class="col-md-12 padding-left-5 col-xs-12 col-sm-12"><div class="star_red">*</div>类型</span>
				 	<div class="col-md-12 col-xs-12 col-sm-12 p0 select_common">
					<select name="type">
					 	<option value=""> -请选择-</option>
					   	<option value="navigation">导航</option>
					   	<option value="accordion">折叠导航</option>
					   	<option value="menu">菜单</option>
					   	<option value="button">按钮</option>
					</select>
					</span>
				</li>
				<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
				 	<span class="col-md-12 padding-left-5 col-xs-12 col-sm-12"><div class="star_red">*</div>状态</span>
				 	<div class="col-md-12 col-xs-12 col-sm-12 p0 select_common">
					<select  name="status">
					   	<option value="0">可用</option>
					   	<option value="1">冻结</option>
				    </select>
				    </div>
				</li>
				<li class="col-md-12 col-xs-12 col-sm-12">
				     <span class="col-md-12 padding-left-5 col-xs-12 col-sm-12"><div class="star_red">*</div>路径</span>
				     <div class="col-md-12 col-xs-12 col-sm-12 p0 input_group input-append">
                        <input name="url" type="text">
                     </div>
				 </li>
			     <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
				    <span class="col-md-12 padding-left-5 col-xs-12 col-sm-12"><div class="star_red">*</div>排序</span>
				    <div class="col-md-12 col-xs-12 col-sm-12 input_group input-append p0">
                        <input class="title" name="position" maxlength="3" type="text">
                    </div>
				 </li>
				<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
				    <span class="col-md-12 padding-left-5 col-xs-12 col-sm-12"><div class="star_red">*</div>图标</span>
				    <div class="col-md-12 col-xs-12 col-sm-12 p0 input_group input-append">
                        <input name="icon" type="text">
                    </div>
				 </li>
				 <div class="clear"></div>
			   </ul>
		  </div> 
	   
		  <div class="tc col-md-12 col-xs-12 col-sm-12">
			    <button class="btn btn-windows save" id="save" type="button">保存</button>
			    <button class="btn btn-windows back" id="backups" type="button">返回</button>
		  </div>
	  </form>
	  </div>
 </body>
</html>
