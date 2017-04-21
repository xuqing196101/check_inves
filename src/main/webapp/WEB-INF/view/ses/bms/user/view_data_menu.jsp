<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet">
		<script src="${pageContext.request.contextPath }/public/select2/js/select2_locale_zh-CN.js"></script>
	<script type="text/javascript">
		 
		<!-- ztree -->  
		var tree = "";  
		var setting = {
			data: {
				simpleData: {
					enable: true
				}
			}
		};
		
        $(document).ready(function(){
        	var userId=$("#id").val();
			$.ajax({
             type: "GET",
             async: false, 
             url: "${pageContext.request.contextPath}/preMenu/dataViewTree.do?userId="+userId,
             dataType: "json",
             success: function(zNodes){
             		if (zNodes.length > 0) {
						for (var i = 0; i < zNodes.length; i++) { 
			            	if (zNodes[i].isParent) {  
			  
				            } else {  
				                //zNodes[i].icon = "${ctxStatic}/images/532.ico";//设置图标  
				            }  
				        }  
				        tree = $.fn.zTree.init($("#menuTree"), setting, zNodes);  
				        tree.expandAll(true);//全部展开
				        $("#tipMsg").hide();
					} else {
						$("#tipMsg").show();
					}
               }
         	});
		});
		
		$(function(){
	    	  var userId = "${uid}";
	    	  $.ajax({
				url: "${ pageContext.request.contextPath }/preMenu/findForViewDataSelect.do?userId="+userId,
				contentType: "application/json;charset=UTF-8",
				dataType: "json", //返回格式为json
				type: "POST", //请求方式           
				success: function(menus) {
					if(menus) {
						$("#nodeName").html("<option ></option>");
						$.each(menus, function(i, menu) {
							if(menu.name != null && menu.name != '') {
								$("#nodeName").append("<option  value=" + menu.name + ">" + menu.name + "</option>");
							}
						});
					}
					$("#nodeName").select2();
				}
			});
      });
      
      function search(){
			var nodeName = $("#nodeName").val();
			var treeObj = $.fn.zTree.getZTreeObj("menuTree");  
		    var nodes = treeObj.getNodesByParamFuzzy("name", nodeName, null); 
		    for(var i=0;i<nodes.length;i++){
			 	treeObj.selectNode(nodes[i]);
			}
		}
	</script>
  </head>
  
  <body>
  	<input id="id" type="hidden" value="${uid}">
  	<div id="div-3" class="mt5">
  		<div class="col-md-3 com-sm-3 col-xs-3 mt5">名称:</div>
	  	<div class="col-md-9 com-sm-9 col-xs-9">
	  		<select id="nodeName" name="nodeName"  onchange="search(this.options[this.selectedIndex].value)"></select>
	  	</div>
  	</div>
    <!-- 菜单树-->
   <div id="menu" class="clear">
	   <div id="menuTree" class="ztree"></div>
   </div>
   <div class="clear tc mt10" id="tipMsg">暂未分配权限！</div>
  </body>
</html>
