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
	
  </head>
  
  <body>
  	<div class="container mt10">
  		<div class="col-md-12 mb10 border1 bggrey">
  			<div class="fl f18 gary b">参考人员信息</div>
  		</div>
  	</div>
  	
  	<%--<div class="container">
    	<div class="border1 col-md-12">
	    	姓名:<input type="text" id="userName" name="userName" class="mt10 w80"/>
	    	身份证号:<input type="text" id="card" name="card" class="mt10 w230"/>
	    	<button class="btn btn-windows add" type="button" onclick="add()">添加</button>
	    	<input type="file" name="file" id="excelFile" style="display:inline;"/>
	    	<button class="btn btn-windows pl13" type="button" onclick="poiExcel()">Excel导入</button>
	  		<button class="btn btn-windows delete" type="button" onclick="deleteByPaperUserId()">删除</button>
    	</div>
    </div>
    
    --%><!-- 表格开始 -->
    <div class="container">
  		<div class="content">
	  		<table class="table table-bordered table-condensed">
				<thead>
					<tr>
						<th class="info w100">选择</th>
						<th class="info w100">序号</th>
						<th class="info w100">姓名</th>
					    <th class="info w100">试卷编号</th>
						<th class="info w100">所属单位</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${paperUserList }" var="paper" varStatus="p">
						<tr>
							<td class="tc pointer"><input type="checkbox" name="info" value="${paper.id }"/></td>
							<td class="tc pointer">${p.index+1 }</td>
							<td class="tc pointer">${paper.userName }</td>
							<td class="tc pointer">${paper.code }</td>
							<td class="tc pointer">${paper.unitName }</td>
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
	  	
	  	
  </body>
</html>
