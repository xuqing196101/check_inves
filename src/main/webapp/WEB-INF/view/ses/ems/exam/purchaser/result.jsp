<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>采购人成绩查询</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		//查询方法
		function query(){
			var userName = $("#userName").val();
			var testState = $("#testState").val();
			if((userName==""||userName==null)&&(testState==""||testState==null)){
				alert("请输入内容");
				return;
			}else{
				$.ajax({
					type:"POST",
					dataType:"json",
					url:"<%=basePath%>purchaserExam/queryPurParam.html?userName="+userName+"&testState="+testState,
		       		success:function(data){
		       			if(data){
		       				var html = "";
			            	for(var i=0;i<data.length;i++){
			            	  html = html + "<tr>";
			            	  html += "<td>"+data[i].user.relName +"</td>";
			            	  html = html + "<td>"+data[i].formatDate+"</td>";
			            	  html = html + "<td>"+data[i].userScore+"</td>";
			            	  html = html + "<td>"+data[i].testState+"</td>";
			            	  html = html + "</tr>";
			            	}
			            	$("#purchaserResult").html(html);
		       			}
		       		}
		       	});
			}
		}
	</script>
	
  </head>
  
  <body>
    <div class="container tc border1">
    	姓名:<input type="text" id="userName" name="userName"/>
    	考试状态:<select name="testState" id="testState">
    		<option value="">请选择</option>
    		<option value="及格">及格</option>
    		<option value="不及格">不及格</option>
    	</select>
    	<input type="button" value="查询" onclick="query()"/>
    </div>
    <div style="margin:0 auto;width:960px;height:200px;border:1px solid grey;">
	    <table>
	    	<thead>
	    		<th>专家姓名</th>
	    		<th>考试时间</th>
	    		<th>得分</th>
	    		<th>考试状态</th>
	    	</thead>
	    	<tbody id="purchaserResult">
	    	
	    	</tbody>
	    </table>
    </div>
  </body>
</html>
