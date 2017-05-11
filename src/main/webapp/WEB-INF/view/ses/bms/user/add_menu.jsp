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
			check: {
				chkboxType:{"Y" : "ps", "N" : "ps"},//勾选checkbox对于父子节点的关联关系  
        		chkStyle:"checkbox",  
				enable: true
			},
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
             url: "${pageContext.request.contextPath}/preMenu/treedata.do?userId="+userId,
             dataType: "json",
             success: function(zNodes){
                     for (var i = 0; i < zNodes.length; i++) { 
			            if (zNodes[i].isParent) {  
			  
			            } else {  
			                //zNodes[i].icon = "${ctxStatic}/images/532.ico";//设置图标  
			            }  
			        }  
			        tree = $.fn.zTree.init($("#menuTree"), setting, zNodes);  
			        tree.expandAll(true);//全部展开
               }
         	});
		});
		
		//用户-菜单信息入库  
		function ajaxSubmit(uid,idstr){
			$.ajax({
             type: "POST",
             async: false, 
             url: "${pageContext.request.contextPath}/user/saveUserMenu.do?userId="+uid+"&ids="+idstr,
             dataType: "text",
             success: function(str){
		        	 //var index = layer.getFrameIndex(window.name); //先得到当前iframe层的索引
				     parent.layer.msg(str,{offset: '222px'});
				     //parent.layer.close(index);
				     //parent.layer.closeAll();
               }
         	}); 
		} 
		//获取选中节点  
		function onCheck(uid){  
		     var treeObj=$.fn.zTree.getZTreeObj("menuTree");  
		     var nodes=treeObj.getCheckedNodes(true);  
		     var ids = new Array();  
		     for(var i=0;i<nodes.length;i++){  
		        //获取选中节点的值  
		         ids.push(nodes[i].id); 
		     } 
		   ajaxSubmit(uid,ids);       
		}  
		
		$(function(){
	    	  var userId = "${uid}";
	    	  $.ajax({
				url: "${ pageContext.request.contextPath }/preMenu/findForSelect.do?userId="+userId,
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
		    
		   var index = layer.load(0, {
				shade : [ 0.1, '#fff' ],
				offset : [ '45%', '53%' ]
			});
			//延迟定位
			setTimeout( function(){
            for(var i=0;i<nodes.length;i++){
			 treeObj.selectNode(nodes[i],true,false);
			}
			layer.close(index);
           },  1* 1000 );
		   
			
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
  </body>
</html>
