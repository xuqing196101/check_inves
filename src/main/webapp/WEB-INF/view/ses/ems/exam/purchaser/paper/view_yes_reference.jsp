<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>查看已考人员</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		//打印预览
		function printReView(){
			var paperId = $("#paperId").val();
			window.location.href = "<%=path%>/purchaserExam/printReView.do?paperId="+paperId;
		}
	</script>

  </head>
  
  <body>
     <div class="container">
  		<div class="col-md-12 mb10 border1 bggrey">
  			<div class="fl f18 gary b">已考人员信息</div>
  		</div>
  	</div>
  	
  	<div class="container">
   		<div class="col-md-10">
	    	<input type="button" class="btn btn-windows pl13" value="打印预览" onclick="printReView()"/>
    	</div>
    </div>
    
    <!-- 表格考试 -->
    <div class="container">
  		<div class="content">
	  		<table class="table table-bordered table-condensed">
				<thead>
					<th class="info w100">序号</th>
					<th class="info w100">姓名</th>
					<th class="info w100">试卷编号</th>
					<th class="info w100">所属单位</th>
					<th class="info w100">得分</th>
				</thead>
				<tbody>
					<c:forEach items="${paperUserList }" varStatus="p" var="paper">
						<tr>
							<td class="tc">${p.index+1 }</td>
							<td class="tc">${paper.userName }</td>
							<td class="tc">${paper.code }</td>
							<td class="tc">${paper.unitName }</td>
							<td class="tc">${paper.score }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
  	</div>
  	
  		<!-- 返回按钮 -->
  		<div class="padding-top-10 clear">
			<div class="col-md-12 pl200 ">
				<div class="mt40 tc mb50">
	    			<input class="btn btn-windows reset" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
				</div>
	  		</div>
	  	</div>
	  	
	  	<input type="hidden" value="${examPaper.id }" id="paperId"/>
  </body>
</html>
