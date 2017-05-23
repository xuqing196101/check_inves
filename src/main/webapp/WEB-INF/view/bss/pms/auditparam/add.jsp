<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %> 
</head>

<script type="text/javascript">
		/* 所属字典类型选择 */
		
		function onClickp(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treep");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		}
		function onCheckp(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treep"),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].name + ",";
				$("#pId").val(nodes[i].id);
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			var pObj = $("#pSel");
			pObj.attr("value", v);
			
			hidep();
		}
		function showParent() {
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
					onClick: onClickp,
					onCheck: onCheckp
				}
			};
			$.ajax({
             type: "GET",
             async: false, 
             url: "${pageContext.request.contextPath}/dictionaryData/getPTree.do",
             dataType: "json",
             success: function(zNodes){
                     for (var i = 0; i < zNodes.length; i++) { 
			            if (zNodes[i].isParent) {  
			  
			            } else {  
			                //zNodes[i].icon = "${ctxStatic}/images/532.ico";//设置图标  
			            }  
			        }  
			        tree = $.fn.zTree.init($("#treep"), setting, zNodes);  
			        tree.expandAll(true);//全部展开
               }
         	});
			var pObj = $("#pSel");
			var cityOffset = $("#pSel").offset();
			$("#pContent").css({left:cityOffset.left + "px", top:cityOffset.top + pObj.outerHeight() + "px"}).slideDown("fast");
			$("body").bind("mousedown", onBodyDownRole);
		}
		function hidep() {
			$("#pContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDownRole);
		}
		function onBodyDownRole(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "pSel" || event.target.id == "pContent" || $(event.target).parents("#pContent").length>0)) {
				hidep();
			}
		}
	function back(){
		window.location.href = ${pageContext.request.contextPath}/dictionaryData/list.html?page=1';
	}
</script>
<body>
   <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
	   <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   	<li><a href="javascript:void(0);"> 首页</a></li><li><a href="javascript:void(0);">支撑系统</a></li><li><a href="javascript:void(0);">后台管理</a></li><li class="active"><a href="javascript:void(0);">审核参数添加</a></li><li class="active"><a href="javascript:void(0);">增加附件类型</a></li>
		   </ul>
		   <div class="clear"></div>
	   </div>
   </div>
   <div class="container bggrey border1 mt20">
   	   <div id="pContent" class="pContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
			<ul id="treep" class="ztree"  ></ul>
	   </div>
   	   <sf:form action="${pageContext.request.contextPath}/param/save.html" method="post" modelAttribute="dd">
		   <div>
			   <div class="headline-v2 bggrey">
			   		<h2>新增审核参数类型 </h2>
			   </div>
			   <ul class="list-unstyled list-flow ul_list">
			   	 
			   	 	<li class="col-md-6 p0">
					   	<span class="span2"><div class="fr">审核轮次：</div><div class="red">*</div></span>
				<!-- 	   	<div class="input-append pr"> -->
					     <select name="dictioanryId"  required="required">
				    		<option value="">请选择</option>
				    		<c:forEach items="${dic }" var="obj">
				    			<option value="${obj.id }">${obj.name }</option>
				    		</c:forEach>
				    	</select>
					      <!--   <span class="add-on">i</span> -->
					    <%--     <div class="b f14 red tip pa l260"><sf:errors path="code"/></div> --%>
				    <!--    	</div> -->
				 	</li>
				 	<li class="col-md-6 p0">
					   	<span class="span2"><div class="fr">审核参数：</div><div class="red">*</div></span>
					  <!--  	<div class="input-append pr"> -->
					   	<select name="param" >
					   	<option value="1">采购方式</option>
					    <option value="2">采购机构</option>
					    <option value="3">其他意见</option>
						<option value="4">技术参数意见</option>
					      </select>
					       <!--  <span class="add-on">i</span> -->
					      <%--   <div class="b f14 red tip pa l260"><sf:errors path="name"/></div> --%>
				    <!--    	</div> -->
				 	</li>
				 	 
			   	</ul>
		   </div> 
	       <div class="col-md-12 tc mt20" >
			  					<input class="btn btn-windows save" style="margin-left: 500px;" type="submit"  value="提交"> <input type="button"  class="btn padding-left-20 padding-right-20 btn_back" value="返回"  onclick="location.href='javascript:history.go(-1);'"> 

       	   </div>
  	   </sf:form>
   </div>
</body>
</html>
