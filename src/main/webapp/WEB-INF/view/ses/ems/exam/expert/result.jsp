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
  	<div>
  		姓名:<input type="text" id="userName"/>
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
  		<input type="button" value="查询" onclick="queryResult()"/>
  		<input type="button" value="重置" onclick="resetResult()"/>
  	</div>
  	<div>
  		<table>
  			<thead>
  				<th>专家姓名</th>
  				<th>专家类型</th>
  				<th>考试时间</th>
  				<th>得分</th>
  				<th>考试状态</th>
  			</thead>
  			<tbody id="resultList">
  				
  			</tbody>
  		</table>
  	</div>
  	
  	
  </body>
</html>
