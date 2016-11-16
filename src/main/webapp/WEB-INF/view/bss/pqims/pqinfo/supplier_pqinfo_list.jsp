<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE html>
<html>
  <head>
  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>

  <script type="text/javascript">
  $(function(){
	  laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${page.pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    skip: true, //是否开启跳页
		    total:"${page.total}",
		    startRow:"${page.startRow}",
		    endRow:"${page.endRow}",
		    groups: "${page.pages}">=5?5:"${page.pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
		        var page = location.search.match(/page=(\d+)/);
		        return page ? page[1] : 1;
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		            location.href = '${pageContext.request.contextPath}/pqinfo/getAllSupplierPqInfo.do?page='+e.curr;
		        }
		    }
		});
  })
 	$(function(){
		$("#supplierDepName").val('${pqinfo.contract.supplierDepName}');
	});
  </script>
  <body>
	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">保障作业</a></li><li><a href="#">产品质量管理</a></li><li class="active"><a href="#">产品质量结果列表</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
    <div class="container">
 		<div class="headline-v2">
	   		<h2>质量结果查询</h2>
	   </div>

    <!-- 查询 -->
    <h2 class="search_detail">
   	<form action="${pageContext.request.contextPath}/pqinfo/searchSupplier.html" method="post" enctype="multipart/form-data" class="mb0" >
	 <ul class="demand_list">
	   <li class="fl">
	   	<label class="fl">供应商名称：</label>
	   	<span>
	   		<input type="text" name="contract.supplierDepName" id="supplierDepName" class="mb0" />
	   	</span>
	   </li>
	   
	   	 <button class="btn" type="submit">查询</button>
	   	 <button type="reset" class="btn">重置</button> 
	 </ul>

	 <div class="clear"></div>
	 </form>
     </h2>
     
<!-- 表格开始-->

     <div class="content table_box">
    	<table class="table table-bordered table-condensed">
		<thead>
		<tr>
		  <th class="info w50">序号</th>
		  <th class="info">供应商名称</th>
		  <th class="info">质检合格次数</th>
		  <th class="info">质检不合格次数</th>
		  <th class="info">质检合格百分比</th>
		</tr>
		</thead>
		<c:forEach items="${list.list}" var="PqInfo" varStatus="vs">
			<tr>
				<td class="tc opinter">${(vs.index+1)+(page.pageNum-1)*(page.pageSize)}</td>
			
				<td class="tc opinter">${PqInfo.supplierName}</td>
			
				<td class="tc opinter">${PqInfo.successCount}</td>
				
				<td class="tc opinter">${PqInfo.failCount}</td>
			
				<td class="tc opinter">${PqInfo.avg}</td>
   				
			</tr>
		</c:forEach>
        </table>
    </div>
   <div id="pagediv" align="right"></div>
  </div>
  </body>
</html>
