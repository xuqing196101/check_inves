<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>My JSP 'category.jsp' starting page</title>

 <link rel="stylesheet" type="text/css" href="<%=basePath%>/public/ztree/css/zTreeStyle.css"> 
<%-- <link rel="stylesheet" type="text/css" href="<%=basePath%>/public/ztree/css/demo.css"> --%>

<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.exedit.js"></script>
<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
<script type="text/javascript">
 $(function() {
    var zTreeObj;
	var zNodes;
	loadZtree();
	function loadZtree() {
		var setting = {
			async : {
				enable : true,
				url : "${pageContext.request.contextPath}/category/query_category.do",
				otherParam : {
					categoryIds : "${categoryIds}",
				},
				dataType : "json",
				type : "post",
			},
			check : {
				enable : true,
				chkboxType : {
					"Y" : "s",
					"N" : "s"
				}
			},
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "parentId"
				}
			},
		};
		zTreeObj = $.fn.zTree.init($("#ztree"), setting, zNodes);
	}
	});	
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
			            location.href = '<%=basePath%>supplierQuery/selectByCategory.do?page='+e.curr;
			        }
			    }
			});
	  }); 
	  function tijiao(){
	    var Obj=$.fn.zTree.getZTreeObj("ztree");  
	     var nodes=Obj.getCheckedNodes(true);  
	     var ids = new Array();  
	     for(var i=0;i<nodes.length;i++){ 
	    	 if(!nodes[i].isParent){
	        //获取选中节点的值  
	         ids.push(nodes[i].id); 
	    	 }
	     } 
	      $("#categoryIds").val(ids);
	  	  form1.submit();
	  }
</script>
</head>

<body>
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		    <li><a href="#"> 首页</a></li><li><a href="#">供应商管理</a></li><li><a href="#">按照品目查询供应商</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
   <div class="col-md-3">
	 <div id="ztree" class="ztree"></div>
	</div>
		<div class="mt10 col-md-9">
			  <form id="form1" action="${pageContext.request.contextPath}/supplierQuery/selectByCategory.html" method="post">
		       <input type="hidden" name="page" id="page">
		       <input type="hidden" id="categoryIds" name="categoryIds"/>
			   <span class="">供应商名称：</span>
			   <div class="input-append">
		        <input class="span2" name="supplierName" value="${supplier.supplierName }" id="supplierName"  type="text">
		       </div>
		       <input class="btn padding-left-20 padding-right-20 btn_back" onclick="tijiao()" type="button" value="查询">
		     </form>
   		<table id="tb1"  class="table table-striped table-bordered table-hover tc">
		      <thead>
				<tr>
				    <th class="info w50">序号</th>
					<th class="info">供应商名称</th>
					<th class="info">联系人</th>
					<th class="info">电话</th>
					<th class="info">级别</th>
				</tr>
			  </thead>
			  <tbody>
				 <c:forEach items="${listSupplier.list }" var="list" varStatus="vs">
					<tr>
					    <td>${(vs.index+1)+(listSupplier.pageNum-1)*(listSupplier.pageSize)}</td>
						<td><a href="<%=basePath%>supplierQuery/essential.html?isRuku=2&supplierId=${list.id}">${list.supplierName }</a></td>
						<td>${list.contactName}</td>
						<td>${list.contactTelephone}</td>
						<td></td>
					</tr>
				</c:forEach> 
			  </tbody>
		 </table>
		 <div id="pagediv" align="right"></div>
		 </div>
    <form  id="form" action="" name="fm" method="post"  enctype="multipart/form-data">
	    <input type="hidden"  onclick="check()" value="submit"/>
	    <input type="hidden"  onclick="mysubmit()" value="submit"/>
    <table id="result"  class="table table-bordered table-condensedb mt15" ></table>
    </form>
	</div>
</body>
</html>
