<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript">
 $(function(){
	  laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${listSupplier.pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    skip: true, //是否开启跳页
		    total: "${listSupplier.total}",
		    startRow: "${listSupplier.startRow}",
		    endRow: "${listSupplier.endRow}",
		    groups: "${listSupplier.pages}">=5?5:"${listSupplier.pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
		        var page = location.search.match(/page=(\d+)/);
		        return page ? page[1] : 1;
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		             location.href = '${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.do?page='+e.curr+"&address="+encodeURI(encodeURI('${address}'));
		        }
		    }
		});
  });
  
	  function fanhui(){
	  	window.location.href="${pageContext.request.contextPath}/supplierQuery/highmaps.html";
	  }
  
	function chongzhi(){
		$("#supplierName").val('');
		$("#startDate").val('');
		$("#endDate").val('');
		$("#contactName").val('');
		$("option")[0].selected = true;
		$("#categoryIds").val('');
		$("#supplierTypeIds").val('');
		$("#category").val('');
		$("#supplierType").val('');
		  var address='${address}';
		  address=encodeURI(address);
	      address=encodeURI(address);
		window.location.href="${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html?address="+address;
	}
	
     $(function() {
		var optionNodes = $("option");
		for ( var i = 1; i < optionNodes.length; i++) {
			if ("${supplier.supplierType}" == $(optionNodes[i]).val()) {
				optionNodes[i].selected = true;
			}
			if ("${supplier.status}" == $(optionNodes[i]).val()) {
				optionNodes[i].selected = true;
			}
		}
	 });
	
		function beforeClickCategory(treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeRole");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		}
		    
		function beforeClick(treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeSupplierType");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		}
		
		function onCheckCategory(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeRole"),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			var rid = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].name + ",";
				rid += nodes[i].id + ",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			if (rid.length > 0 ) rid = rid.substring(0, rid.length-1);
			var cityObj = $("#category");
			cityObj.attr("value", v);
			$("#categoryIds").val(rid); 
		}
		
		function onCheck(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeSupplierType"),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			var rid = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].name + ",";
				rid += nodes[i].id + ",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			if (rid.length > 0 ) rid = rid.substring(0, rid.length-1);
			var cityObj = $("#supplierType");
			cityObj.attr("value", v);
			$("#supplierTypeIds").val(rid); 
		}
		
		function showCategory() {
			var setting = {
			check: {
					enable: true,
					chkboxType: {"Y":"", "N":""}
				},
				view: {
					dblClickExpand: false
				},
				data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "parentId"
				}
			},
				callback: {
					beforeClick: beforeClickCategory,
					onCheck: onCheckCategory
				}
			};
	        $.ajax({
             type: "GET",
             async: false, 
             url: "${pageContext.request.contextPath}/category/query_category.do?categoryIds="+" ",
             dataType: "json",
             success: function(zNodes){
                     for (var i = 0; i < zNodes.length; i++) { 
			            if (zNodes[i].isParent) {  
			  
			            } else {  
			                //zNodes[i].icon = "${ctxStatic}/images/532.ico";//设置图标  
			            }  
			        }  
			        tree = $.fn.zTree.init($("#treeRole"), setting, zNodes);  
			        tree.expandAll(true);//全部展开
               }
         	});
			var cityObj = $("#category");
			var cityOffset = $("#category").offset();
			$("#roleContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
			$("body").bind("mousedown", onBodyDownOrg);
		}
		
		function showSupplierType() {
			var setting = {
			check: {
					enable: true,
					chkboxType: {"Y":"", "N":""}
				},
				view: {
					dblClickExpand: false
				},
				data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "parentId"
				}
			},
				callback: {
					beforeClick: beforeClick,
					onCheck: onCheck
				}
			};
			
	        $.ajax({
             type: "GET",
             async: false, 
             url: "${pageContext.request.contextPath}/supplier_type/find_supplier_type.do?supplierId=''",
             dataType: "json",
             success: function(zNodes){
                     for (var i = 0; i < zNodes.length; i++) { 
			            if (zNodes[i].isParent) {  
			  
			            } else {  
			                //zNodes[i].icon = "${ctxStatic}/images/532.ico";//设置图标  
			            }  
			        }  
			        tree = $.fn.zTree.init($("#treeSupplierType"), setting, zNodes);  
			        tree.expandAll(true);//全部展开
               }
         	});
			var cityObj = $("#supplierType");
			var cityOffset = $("#supplierType").offset();
			$("#supplierTypeContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
			$("body").bind("mousedown", onBodyDownSupplierType);
		}
		
		function hideRole() {
			$("#roleContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDownOrg);
			
		}
		
		function hideSupplierType() {
			$("#supplierTypeContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDownSupplierType);
			
		}
		
		function onBodyDownOrg(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "roleSel" || event.target.id == "roleContent" || $(event.target).parents("#roleContent").length>0)) {
				hideRole();
			}
		}
		
		function onBodyDownSupplierType(event) {
			if (!(event.target.id == "menuBtn" || $(event.target).parents("#supplierTypeContent").length>0)) {
				hideSupplierType();
			}
		}
</script>
</head>
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		  <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">供应商管理</a></li><li class="active"><a href="#">供应商列表</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
    	   <div id="roleContent" class="roleContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
			<ul id="treeRole" class="ztree" style="margin-top:0;"></ul>
	   </div>
	    <div id="supplierTypeContent" class="supplierTypeContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
			<ul id="treeSupplierType" class="ztree" style="margin-top:0;"></ul>
	   </div>
  <body>
  	<div class="container">
	   <div class="headline-v2">
	     <h2>供应商信息</h2>
	   </div> 
  				<h2 class="search_detail">
            <form id="form1" action="${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html" method="post" class="mb0">
               <input type="hidden" name="address" value="${address }">
               <input type="hidden" name="page" id="page">
               <ul class="demand_list">
                  <li>
                    <label class="fl">供应商名称：</label><span><input id="supplierName" name="supplierName" value="${sup.supplierName }" type="text"></span>
                  </li>
                  <li>
                    <label class="fl">注册时间：</label><span><input id="startDate" name="startDate" class="Wdate w230" type="text"  value='<fmt:formatDate value="${sup.startDate }" pattern="YYYY-MM-dd"/>'
                        onFocus="var endDate=$dp.$('endDate');WdatePicker({onpicked:function(){endDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"/>
                        <span class="f14">至</span>
                        <input id="endDate" name="endDate" value='<fmt:formatDate value="${sup.endDate }" pattern="YYYY-MM-dd"/>' class="Wdate w230" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})"/>
                        </span>
                  </li>
                  <li>
                    <label class="fl">联系人：</label><span><input id="contactName" name="contactName" value="${sup.contactName }" type="text"></span>
                  </li> 
                  <li>
                    <label class="fl">供应商类型：</label><span><input id="supplierType" class="span2 mt5" type="text" name="supplierType"  readonly value="${supplierType }" onclick="showSupplierType();" />
                              <input   type="hidden" name="supplierTypeIds"  id="supplierTypeIds" value="${supplierTypeIds }" /></span>
                  </li>
                  <li>
                    <label class="fl">供应商状态:</label>
                    <span>
                      <select name="status">
                                    <option  selected="selected" value=''>-请选择-</option>
                                    <option  value="-1">暂存、未提交</option>
                                    <option  value="0">待初审</option>
                                    <option  value="1">待复审</option>
                                    <option  value="2">初审不通过</option>
                                    <option  value="3">复审通过</option>
                                    <option  value="4">复审不通过</option>
                       </select>
                    </span>
                  </li>
                  <li>
                    <label class="fl">品目：</label><span><input id="category" type="text" name="categoryNames" value="${categoryNames }" readonly onclick="showCategory();" />
                           <input type="hidden" name="categoryIds"  id="categoryIds" value="${categoryIds }"   /></span>
                  </li>
                </ul>
                <div class="col-md-12 clear tc mt10">
                    <button type="button" onclick="submit()" class="btn">查询</button>
                    <button type="reset" onclick="chongzhi()" class="btn">重置</button> 
                </div>
                <div class="clear"></div>
             </form>
     </h2>
     <div class="col-md-12 pl20 mt10">
	    <button class="btn btn-windows back" type="button" onclick="location.href='${pageContext.request.contextPath}/supplierQuery/highmaps.html'">返回</button>
	 </div>
     <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
		      <thead>
				<tr>
					<th class="info w50">序号</th>
					<th class="info">供应商名称</th>
					<th class="info">联系人</th>
					<th class="info">创建日期</th>
					
					<th class="info">供应商类型</th>
					<th class="info">供应商状态</th>
					
					<th class="info">经济性质</th>
				</tr>
			  </thead>
			  <tbody>
				 <c:forEach items="${listSupplier.list }" var="list" varStatus="vs">
					<tr>
						<td class="tc">${vs.index+1 }</td>
						<td><a href="${pageContext.request.contextPath}/supplierQuery/essential.html?isRuku=0&supplierId=${list.id}">${list.supplierName }</a></td>
						<td class="tc">${list.contactName }</td>
						<td class="tc"><fmt:formatDate value="${list.createdAt }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td class="tc">${list.supplierType }</td>
						<td class="tc">		
						    <c:if test="${list.status==-1 }">
							暂存、未提交
							</c:if>
							<c:if test="${list.status==0 }">
							待初审
							</c:if>
							<c:if test="${list.status==1 }">
							待复审
							</c:if>
							<c:if test="${list.status==2 }">
							初审不通过
							</c:if>
							<c:if test="${list.status==3 }">
							复审通过
							</c:if>
							<c:if test="${list.status==4 }">
							复审不通过
							</c:if></td>
						<td class="tc">${list.businessType }</td>
					</tr>
				</c:forEach> 
			  </tbody>
		 </table>
		 <div id="pagediv" align="right"></div>
		 </div>
     </div>
  </body>
</html>
