<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link href="${pageContext.request.contextPath}/public/ZHH/css/import_supplier.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/common.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/style.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/animate.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/ui-dialog.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/dialog-select.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/line-icons.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/font-awesome.min.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/jquery.fileupload-ui.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/zTreeStyle.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/sky-forms.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/custom-sky-forms.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/jquery.fancybox.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/owl.carousel.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/owl.theme.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/style-switcher.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/shortcode_timeline2.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/app.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/blocks.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/datepicker.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/WdatePicker.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/select2.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/application.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/header-v4.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/img-hover.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/brand-buttons-inversed.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/blog_magazine.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/page_job.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/page_log_reg_v1.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/shop.style.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/header-v5.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/footer-v4.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/masterslider.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/james.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/WdatePicker(1).css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ztree/css/demo.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ztree/css/zTreeStyle.css" type="text/css">
<script src="${pageContext.request.contextPath}/public/ZHH/js/hm.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery-migrate-1.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery_ujs.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/back-to-top.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.query.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/dialog-plus-min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.fancybox.pack.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/smoothScroll.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.parallax.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/app.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/common.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/dota.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.dragsort-0.5.2.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/fancy-box.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/style-switcher.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/owl.carousel.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/owl-carousel.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/owl-recent-works.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.form.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.maskedinput.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery-ui.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/masking.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/datepicker.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/timepicker.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/dialog-select.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/locale.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.ui.widget.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/load-image.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/canvas-to-blob.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/tmpl.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.iframe-transport.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.fileupload.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.fileupload-fp.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.fileupload-ui.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery-fileupload.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/form.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/select2.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/select2_locale_zh-CN.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/application.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.counterup.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/modernizr.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/touch.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/product-quantity.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/master-slider.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/shop.app.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/masterslider.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.easing.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/james.js"></script>
<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/public/My97DatePicker/WdatePicker.js"></script>
<script src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.core.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.excheck.js"></script>
<title>My JSP 'index.jsp' starting page</title>
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
			             location.href = '${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.do?page='+e.curr+"&address=${address}";
			        }
			    }
			});
	  });
	    function fanhui(){
	  	window.location.href="${pageContext.request.contextPath}/supplierQuery/highmaps.html?status=3";
	  }
function chongzhi(){
	$("#supplierName").val('');
	$("#startDate").val('');
	$("#endDate").val('');
	$("#contactName").val('');
	$("#category").val('');
	$("#supplierType").val('');
	$("#categoryIds").val('');
	$("#supplierTypeIds").val('');
	var address='${address}';
	address=encodeURI(address);
    address=encodeURI(address);
     window.location.href="${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html?address="+address+"&status=3";
}
$(function() {
		var optionNodes = $("option");
		for ( var i = 1; i < optionNodes.length; i++) {
			if ("${supplier.supplierType}" == $(optionNodes[i]).val()) {
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
             url: "${pageContext.request.contextPath}/supplier_type/find_supplier_type.do?supplierId='${supplierId}'",
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
			$("body").unbind("mousedown", onBodyDownOrg);
			
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
		  <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">供应商管理</a></li><li class="active"><a href="#">入库供应商列表</a></li>
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
  	<div class="container clear margin-top-30">
  			<h2>供应商信息查询</h2>
  				<form id="form1" action="${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html" method="post">
		       <input type="hidden" name="page" id="page">
		       <input type="hidden" name="status" value="3">
		       <input type="hidden" name="address" value="${address }">
		       <table class="table table-bordered table-condensed tc">
		       	<tbody>
		       		<tr>
		       			<td style="text-align:right">供应商名称：</td>
		       			<td style="text-align:right"><input id="supplierName" class="span2" name="supplierName" value="${supplier.supplierName }" type="text"></td>
		       			<td style="text-align:right">注册时间：</td>
		       			<td>
		       			<div class="mt5">
		       			<input id="startDate" name="startDate" class="span2 fl" type="text" value='<fmt:formatDate value="${supplier.startDate }" pattern="YYYY-MM-dd"/>'
		       			onFocus="var endDate=$dp.$('endDate');WdatePicker({onpicked:function(){endDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"/>
		       			<span class="add-on fl"><img src="${pageContext.request.contextPath}/public/ZHQ/images/time_icon.png" class="mb10" /> </span>
		       			<span class="fl mt5">至</span>
		       			<input id="endDate" name="endDate" value='<fmt:formatDate value="${supplier.endDate }" pattern="YYYY-MM-dd"/>'  class="span2 ml10" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})"/>
		       			<span class="add-on fl"><img src="${pageContext.request.contextPath}/public/ZHQ/images/time_icon.png" class="mb10" /> </span>
		       			</div>
		       			</td>
		       		</tr>
		       		<tr>
		       			<td style="text-align:right">联系人：</td>
		       			<td><input class="span2" id="contactName" name="contactName" value="${supplier.contactName }" type="text"></td>
		       			<td style="text-align:right">供应商类型：</td>
		       			<td><input id="supplierType" class="span2" readonly type="text" name="supplierType" value="${supplierType }" onclick="showSupplierType();" />
		       			     <input   type="hidden" name="supplierTypeIds"  id="supplierTypeIds" value="${supplierTypeIds }" />
		       			</td>
		       		</tr>
		       		<tr>
		       			<td  style="text-align:right">品目：</td>
		       			<td> 
		       			   <input id="category" class="span2" type="text" readonly name="categoryNames" value="${categoryNames }" onclick="showCategory();" />
		       			   <input   type="hidden" name="categoryIds"  id="categoryIds" value="${categoryIds }"   />
		       			</td>
		       			<td colspan="4">
		       				 <input class="btn padding-left-20 padding-right-20 btn_back" onclick="submit()" type="button" value="查询">
		     				 <input class="btn padding-left-20 padding-right-20 btn_back" onclick="chongzhi()" type="button" value="重置"> 
		     				 <input class="btn padding-left-20 padding-right-20 btn_back" value="返回" type="button" onclick="fanhui()">
		       			</td>
		       		</tr>
		       	</tbody>
		       </table>
		     </form>
		       <h2>供应商信息</h2>
		      <table id="tb1"  class="table table-striped table-bordered table-hover tc">
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
						<td>${vs.index+1 }</td>
						<td><a href="${pageContext.request.contextPath}/supplierQuery/essential.html?isRuku=1&supplierId=${list.id}">${list.supplierName }</a></td>
						<td>${list.contactName }</td>
						<td><fmt:formatDate value="${list.createdAt }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td>${list.supplierType }</td>
						<td>
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
							</c:if>
						</td>
						<td>${list.businessType }</td>
					</tr>
				</c:forEach> 
			  </tbody>
		 </table>
		 <div id="pagediv" align="right"></div>
     </div>
  </body>
</html>
