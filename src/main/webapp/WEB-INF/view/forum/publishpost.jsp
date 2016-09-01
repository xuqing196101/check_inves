<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>论坛管理</title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="<%=basePath%>public/ZHQ/css/common.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHQ/css/bootstrap.min.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHQ/css/style.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHQ/css/line-icons.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHQ/css/app.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHQ/css/application.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHQ/css/header-v4.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHQ/css/footer-v2.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHQ/css/img-hover.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHQ/css/page_job.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHQ/css/shop.style.css" media="screen" rel="stylesheet">
	<script src="<%=basePath%>public/ZHQ/js/hm.js"></script>
	<script src="<%=basePath%>public/ZHQ/js/jquery.min.js"></script>
	<!--导航js-->
	<script src="<%=basePath%>public/ZHQ/js/jquery_ujs.js"></script>
	<script src="<%=basePath%>public/ZHQ/js/bootstrap.min.js"></script>
  <script type="text/javascript">

  //2级联动
  function change(id){
		$.ajax({
		    url:"<%=basePath %>topic/getListForSelect.do?parkId="+id,   
		    contentType: "application/json;charset=UTF-8", 
		    dataType:"json",   //返回格式为json
		    type:"POST",   //请求方式		    
	        success : function(data) {     

	            if (data) {          	
	              $("#topics").html("");                
	              $.each(data.topics, function(i, topic) {  
	            	  $("#topics").append("<option  value="+topic.id+">"+topic.name+"</option>");
	            	  
	              });  
	                          
	            }
	        }

		});
  }
 </script>
  </head>
  
  <body>
  
  <jsp:include page="/indexhead.jsp"></jsp:include>
   <div class="container margin-top-10">
     <div class="content padding-left-25 padding-right-25 padding-top-20">	
        <form action="<%=basePath %>post/indexsave.do" method="post"><%--
        <input type="hidden" name="parkId" value="${park.id} ">
        <input type="hidden" name="topicId" value="${topic.id }">
	          	--%>帖子名称：<input  name="name" size="30" type="text"><br/>
	          	帖子内容：<textarea rows="50" cols="50" name="content"></textarea><br/>
	          	所属版块：
	         	 <select name ="parkId" id ="parks" onchange="change(this.options[this.selectedIndex].value)">
					<option></option>
		  	  		<c:forEach items="${parks}" var="park">
		  	  			<option  value="${park.id}">${park.name}</option>
		  	  		</c:forEach> 
	  			</select><br/>
	          	所属主题：
	        	<select id="topics" name="topicId">
	        	<option></option>
	  			</select>
	          	<br/>
	          	
	        	<input value="保存" type="submit"><br/>
     	</form>
   </div>
   </div>
  <jsp:include page="/indexbottom.jsp"></jsp:include>
  </body>
</html>



