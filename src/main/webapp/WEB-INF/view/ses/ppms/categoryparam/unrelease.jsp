<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'unrelease.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
 <script src="<%=basePath%>public/layer/layer.js"></script>
<script type="text/javascript">
$(function(){
	  laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${list.pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    skip: true, //是否开启跳页
		    total: "${list.total}",
		    startRow: "${list.startRow}",
		    endRow: "${list.endRow}",
		    groups: "${list.pages}">=3?3:"${list.pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
		        var page = location.search.match(/page=(\d+)/);
		        return page ? page[1] : 1;
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		            location.href = "<%=basePath%>categoryparam/publish.html?page="+e.curr;
		        }
		    }
		});
});
/** 单选 */
function check(){
		 var count=0;
		 var checklist = document.getElementsByName ("chkItem");
		 var checkAll = document.getElementById("checkAll");
		 for(var i=0;i<checklist.length;i++){
			   if(checklist[i].checked == false){
				   checkAll.checked = false;
				   break;
			   }
			   for(var j=0;j<checklist.length;j++){
					 if(checklist[j].checked == true){
						   checkAll.checked = true;
						   count++;
					   }
				 }
		   }
	}
function publish(){
	
	var id=[]; 
	$('input[name="chkItem"]:checked').each(function(){ 
		id.push($(this).val());
	}); 
	if(id.length==1){
		
		window.location.href="<%=basePath%>categoryparam/publish_category.html?id="+id;
	}else if(id.length>1){
		layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
	}else{
		layer.alert("请选择需要发布的产品参数",{offset: ['222px', '390px'], shade:0.01});
	}
}
</script>
  </head>
  <body>
	  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">产品参数管理</a></li><li><a href="#">发布列表</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>

   <div class="container">
   <div class="headline-v2">
     <h2>待发布</h2>
   </div>
 <div class="container clear">
  <div class="p10_25">
     <h2 class="padding-10 border1">
    	<ul class="demand_list">
	    	<button type="button" onclick="publish()" class="btn">发布</button>
    	</ul>
    	  <div class="clear"></div>
       </form>
     </h2>
   </div>
  </div>
   <div class="container">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
    	<table class="table table-bordered table-condensed table-hover">
		<thead>
	            <tr><th class="w50 info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
	                <th class="info w80">序号</th>
	                <th class="info">产品名称</th>
	                <th class="info">产品状态</th>
	                <th class="info">是否公开</th>
	                <th class="info">创建时间</th>
	            </tr>
	        </thead>	
	        <c:forEach var="cate" items="${cate}" varStatus="vs">
	            <tr>
	            <td class="tc pointer"><input  onclick="check()" type="checkbox" name="chkItem" value="${cate.id}"/></td>
	            <td class="w50 tc pointer" >${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
	            <td class="tc pointer" >${cate.name }</td>
	            <td class="tc pointer" ><c:choose>
						<c:when test="${cate.paramStatus=='3'}">
							生效
						</c:when>
					</c:choose></td>
			    <td class="tc pinter"><c:choose>
						<c:when test="${cate.isPublish=='1'}">
							是
						</c:when>
					</c:choose></td>
	            <td class="tc pointer">${cate.createdAt }</td>
	            </tr>
	        </c:forEach>
	</table>
   </div>

   </div>
      <div id="pagediv" align="right"></div>
   </div>
  </body>
</html>
