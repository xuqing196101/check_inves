<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>查看参考人员</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="${ pageContext.request.contextPath }/public/layer/layer.js"></script>
	<script type="text/javascript" src="${ pageContext.request.contextPath }/public/layer/extend/layer.ext.js"></script>
	<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js" type="text/javascript"></script>
	<link href="${ pageContext.request.contextPath }/public/layer/skin/layer.css" rel="stylesheet" type="text/css" />
	<link href="${ pageContext.request.contextPath }/public/layer/skin/layer.ext.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
		$(function(){
			laypage({
			    cont: $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
			    pages: "${paperUserList.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    skip: true, //是否开启跳页
			    total: "${paperUserList.total}",
			    startRow: "${paperUserList.startRow}",
			    endRow: "${paperUserList.endRow}",
			    groups: "${paperUserList.pages}">=3?3:"${paperUserList.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			        var page = location.search.match(/page=(\d+)/);
			        return page ? page[1] : 1;
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			        	var id = "${id}";
			            location.href = "<%=path%>/purchaserExam/viewReference.do?id="+id+"&page="+e.curr;
			        }
			    }
			});
		})	
	</script>
  </head>
  
  <body>
  	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
	    <div class="container">
			<ul class="breadcrumb margin-left-0">
			   <li><a href="#">首页</a></li><li><a href="#">支撑环境</a></li><li><a href="#">考卷管理</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
  	
  	<div class="container">
	   <div class="headline-v2">
	   		<h2>参考人员列表</h2>
	   </div>
   	</div>
  	
  	<!-- 表格开始 -->
    <div class="container">
  		<div class="content">
	  		<table class="table table-bordered table-condensed">
				<thead>
					<tr>
						<th class="info w50">序号</th>
						<th class="info w50">姓名</th>
						<th class="info">身份证号</th>
					    <th class="info">试卷编号</th>
						<th class="info">所属单位</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${paperUserList.list }" var="paper" varStatus="vs">
						<tr>
							<td class="tc">${(vs.index+1)+(paperUserList.pageNum-1)*(paperUserList.pageSize)}</td>
							<td class="tc">${paper.userName }</td>
							<td class="tc">${paper.card }</td>
							<td class="tc">${paper.code }</td>
							<td class="tc">${paper.unitName }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pageDiv" align="right"></div>
  	</div>
  	
  		<!-- 返回按钮 -->
  		<div class="padding-top-10 clear">
			<div class="col-md-12 pl200 ">
				<div class="mt40 tc mb50">
	    			<input class="btn btn-windows back" value="返回考卷列表" type="button" onclick="location.href='<%=path%>/purchaserExam/paperManage.html'">
				</div>
	  		</div>
	  	</div>
	  	
	  	
  </body>
</html>
