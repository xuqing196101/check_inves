<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE html>
<html>
  <head>
    <title>专家考试成绩查询</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		//专家考试成绩按条件查询
		function queryResult(){
			var userName = $("#userName").val().trim();
			var userType = $("#userType").val();
			var testState = $("#testState").val();
			if((userName==""||userName==null)&&(userType==""||userType==null)&&(testState==""||testState==null)){
				$("#resultList").html("");
				return;
			}
			$.ajax({
				type:"POST",
				dataType:"json",
				url:"<%=basePath%>expertExam/selectExpertResultByTerm.do?userName="+userName+"&userType="+userType+"&testState="+testState,
				success:function(json){
	       			if(json){
	       				var html = "";
		            	for(var i=0;i<json.length;i++){
		            	  html = html + "<tr>";
		            	  html = html + "<td>"+json[i].expert.relName+"</td>";
		            	  html = html + "<td>"+json[i].expert.relName+"</td>";
		            	  html = html + "<td>"+json[i].expert.relName+"</td>";
		            	  html = html + "<td>"+json[i].score+"</td>";
		            	  html = html + "<td>"+json[i].status+"</td>";
		            	  html = html + "</tr>";
		            	}
		            	$("#resultList").html(html);
	       			}
	       		}
	       	});
		}
		
		//重置方法
		function resetResult(){
			$("#userName").val("");
			var userType = document.getElementById("userType").options;
			userType[0].selected=true;
			var testState = document.getElementById("testState").options;
			testState[0].selected=true;
		}
	</script>
  </head>
  
  <body>
  	<div class="container">
  		<div class="border1 col-md-7">
	  		姓名:<input type="text" id="userName" class="mt10"/>
	  		专家类型:
	  		<select id="userType">
	  			<option value="">请选择</option>
	  			<option value="1">技术</option>
	  			<option value="3">商务</option>
	  			<option value="2">法律</option>
	  		</select>
	  		考试状态:
	  		<select id="testState">
	  			<option value="">请选择</option>
	  			<option value="及格">及格</option>
	  			<option value="不及格">不及格</option>
	  		</select>
	  		<button class="btn" type="button" onclick="queryResult()">查询</button>
	  		<button class="btn" type="button" onclick="resetResult()">重置</button>
  		</div>
  	</div>
  	<div class="container">
  		<div class="content">
	  		<table class="table table-bordered table-condensed">
				<thead>
					<tr>
						<th class="info w100">专家姓名</th>
						<th class="info w100">专家类型</th>
					    <th class="info" width="100">考试时间</th>
						<th class="info" width="100">得分</th>
						<th class="info" width="100">考试状态</th>
					</tr>
				</thead>
				<tbody id="resultList">
				
				</tbody>
			</table>
		</div>
  	</div>
  	
  	
  </body>
</html>
