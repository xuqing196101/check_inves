<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
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
	</SCRIPT>
  </head>
  <script type="text/javascript">
    $(function(){
        $("#update").click(function(){
        	var pid = $("#pid").val();
            $.ajax({  
               type: "POST",  
               url: "${pageContext.request.contextPath}/preMenu/update.html?pid="+pid,  
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
		   	   <input type="hidden" name="id" id="mid" value="${menu.id }">
		   	   <input type="hidden" name="isDeleted" value="${menu.isDeleted }">
		   	   <input type="hidden" name="pid" id="pid" value="${menu.parentId.id }">
			    <ul class="list-unstyled">
			     <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6 pl15">
				   <span class="col-md-12 padding-left-5 col-xs-12 col-sm-12"><div class="star_red">*</div>上级</span>
				   <div class="col-md-12 col-xs-12 col-sm-12 p0 input_group input-append">
                       <input id="citySel" class="title" type="text" readonly value="${menu.parentId.name }"  onclick="showMenu();" />
                    </span>
				 </li>
			     <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
				   <span class="col-md-12 padding-left-5 col-xs-12 col-sm-12"><div class="star_red">*</div>名称</span>
				   <div class="col-md-12 col-xs-12 col-sm-12 p0 input_group input-append">
                        <input class="title col-md-12" name="name" value="${menu.name }"  maxlength="30" type="text">
                    </span>
				 </li>
				 <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
				 	<span class="col-md-12 padding-left-5 col-xs-12 col-sm-12"><div class="star_red">*</div>类型</span>
				 	<div class="col-md-12 col-xs-12 col-sm-12 p0 select_common">
					<select name="type">
					 	<option value="">-请选择-</option>
					   	<option value="navigation" <c:if test="${'navigation' eq menu.type}">selected</c:if>>导航</option>
					   	<option value="accordion" <c:if test="${'accordion' eq menu.type}">selected</c:if>>折叠导航</option>
					   	<option value="menu" <c:if test="${'menu' eq menu.type}">selected</c:if>>菜单</option>
					   	<option value="button" <c:if test="${'button' eq menu.type}">selected</c:if>>按钮</option>
					</select>
					</span>
				</li>
				<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
				 	<span class="col-md-12 padding-left-5 col-xs-12 col-sm-12"><div class="star_red">*</div>状态</span>
				 	<div class="col-md-12 col-xs-12 col-sm-12 p0 select_common">
					<select  name="status">
					   	<option value="0" <c:if test="${'0' eq menu.status}">selected</c:if>>可用</option>
					   	<option value="1" <c:if test="${'1' eq menu.status}">selected</c:if>>冻结</option>
				    </select>
				    </span>
				</li>
				<li class="mt10 col-md-12 col-xs-12 col-sm-12">
				  <span class="col-md-12 padding-left-5 col-xs-12 col-sm-12"><div class="star_red"></div>路径</span>
				   <div class="col-md-12 col-xs-12 col-sm-12 p0 input_group input-append">
                        <input name="url" value="${menu.url }" maxlength="300" type="text">
                    </span>
				 </li>
				  <li class="col-sm-6  col-md-6 col-lg-6 col-xs-6">
                   <span class="col-md-12 padding-left-5 col-xs-12 col-sm-12"><div class="star_red">*</div>排序</span>
                    <div class="col-md-12 col-xs-12 col-sm-12 p0 input_group input-append">
                        <input class="title" name="position" value="${menu.position }" maxlength="3" type="text" onkeyup="value=value.replace(/[^\d]/g,'')">
                    </span>
                 </li>
                 <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
                   <span class="col-md-12 padding-left-5 col-xs-12 col-sm-12">
                      <div class="star_red"></div>图标
                   </span>
                    <div class="col-md-12 col-xs-12 col-sm-12 p0 input_group input-append">
                        <input class="title" name="icon" value="${menu.icon }"  type="text">
                    </span>
                 </li>
                  <div class="clear"></div>
			   </ul>
		  </div> 
	   
		  <div class="tc col-sm-12 col-md-12 col-xs-12">
			    <button class="btn btn-windows save" id="update" type="button">更新</button>
			    <button class="btn btn-windows back" id="backups" type="button">返回</button>
		  </div>
	  </form>
  </div>
 </body>
</html>
