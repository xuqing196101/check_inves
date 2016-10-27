<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'search.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/public/ztree/css/zTreeStyle.css"> 
<%-- <link rel="stylesheet" type="text/css" href="<%=basePath%>/public/ztree/css/demo.css"> --%>

<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.exedit.js"></script>
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
		            location.href = "<%=basePath%>categoryparam/search_orgnization.html?page="+e.curr;
		        }
		    }
		});
});
function search(name){
	var name =$("#input_cate").val();
	alert(name);
	window.location.href="<%=basePath%>categoryparam/search_categoryname.html?name="+name;
}
function query(){
	window.location.href="<%=basePath%>categoryparam/check_categoryparam.html";
}


</script>
  </head>
  <body>
   <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">产品参数管理</a></li><li><a href="#">产品查询</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>

   <div class="container">
   <div class="headline-v2">
     <h2>查询</h2>
   </div>
 <div class="container clear">
  <div class="p10_25">
     <h2 class="padding-10 border1">
    	<ul class="demand_list">
    	  <li class="fl">
	    	<label class="fl">产品名称：</label><span><input id="input_cate" name="name" type="text" value="" /></span>
	      </li> 
	    	<input type="button" class="btn" value="查询" onclick="search()"/>
	    	<a href="javascript:void(0);" class="btn" onclick="query()" value=""/>目录查询</a> 	
    	</ul>
    	  <div class="clear"></div>
     </h2>
   </div>
  </div>
 <div class="container">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
    	<table class="table table-bordered table-condensed table-hover">
            <thead>
                <tr>
                <th class="info w50"><input id="selectAll" type="checkbox" onclick="selectAll()"/></th>
                <th class="info">序号</th>
                <th class="info">产品名称</th>
                <th class="info">管理部门</th>
                <th class="info">更新时间</th>
                </tr>
            </thead>
            <c:forEach var="cate" items="${cate}" varStatus="vs">
	            <tr>
	            <td class="tc pointer"><input  onclick="check('${cate.id}')" type="checkbox" name="chkItem" value="${cate.id}"/></td>
	            <td class="w50 tc pointer" onclick="view('${cate.id}')">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
	            <td class="tc pointer" onclick="view('${cate.id}')">${cate.name }</td>
	           
	            <td class="tc pointer" onclick="view('${cate.id}')">${cate.orgnization.name }</td>
	            <td class="tc pointer" onclick="view('${cate.id}')">${cate.createdAt }</td>
	            </tr>
	        </c:forEach>
	</table>
	<div id="categoryparam"  class="dnone">
	<tr><td></td><td></td></tr>
	</div>
   </div>
  </div>
  <div id="pagediv" align="right"></div>
</div>
  </body>
</html>
