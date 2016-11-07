<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>专家个人成绩信息</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="${pageContext.request.contextPath }/public/laypage-v1.3/laypage/laypage.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function(){
			laypage({
			    cont: $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
			    pages: "${list.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    skip: true, //是否开启跳页
			    total: "${list.total}",
			    startRow: "${list.startRow}",
			    endRow: "${list.endRow}",
			    groups: "${list.pages}">=5?5:"${list.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			        var page = location.search.match(/page=(\d+)/);
			        return page ? page[1] : 1;
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			            location.href = "${pageContext.request.contextPath }/expertExam/personalResult.do?page="+e.curr;
			        }
			    }
			});
		})	
	</script>

  </head>
  
  <body>
  	<div class="container">
	   <div class="headline-v2">
	   		<h2>成绩信息</h2>
	   </div>
   `</div>
  
    <div class="container margin-top-5" id="div_print">
     	<div class="content padding-left-25 padding-right-25 padding-top-5">
   			<table class="table table-bordered table-condensed table-hover">
				<thead>
					<tr class="info">
						<th width="50">序号</th>
					    <th width="150">专家姓名</th>
						<th width="100">得分</th>
						<th>考试状态</th>
					    <th>考试时间</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list.list}" var="l" varStatus="vs">
						<tr class="tc">
							<td>${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
							<td>${l.relName }</td>
							<td>${l.score }</td>
							<td>${l.status }</td>
							<td>${l.formatDate}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
     	</div>
     	<div id="pageDiv" align="right"></div>
   </div>
  </body>
</html>
