<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'bid_file.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHH/css/common.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/style.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/app.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/application.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
    <script src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
    <script src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>public/layer/layer.js"></script>
	<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
    <script type="text/javascript">
        
      function confirmOk(id){
      	   layer.confirm('您已经确认了吗?', {title:'提示',offset: ['100px'],shade:0.01}, function(index){
	 			layer.close(index);
	 			$.ajax({
	 				url:"<%=basePath%>open_bidding/confirmOk.html?projectId="+id,
	 	       		success:function(){
	 	       			layer.msg('确认成功',{offset: ['100px']});
	 		       		window.setTimeout(function(){
	 		       			window.location.reload();
	 		       		}, 500);
	 	       		},
	 	       		error: function(){
	 					layer.msg("确认失败",{offset: ['100px']});
	 				}
	 	       	});
	 		});
      }
  </script>
  </head>
  <body>
	 <div class="col-md-12 p0">
	   <ul class="flow_step">
	     <li class="active">
		   <a  href="<%=basePath%>open_bidding/firstAduitView.html?projectId=${projectId}" >01、符合性</a>
		   <i></i>
		 </li>
		 <li>
		   <a  href="<%=basePath%>open_bidding/packageFirstAuditView.html?projectId=${projectId}" >02、符合性关联</a>
		   <i></i>							  
		 </li>
	     <li>
		   <a  href="<%=basePath%>intelligentScore/packageList.html?projectId=${projectId}">03、评标细则</a>
		   <i></i>
		 </li>
		 <li>
		   <a  href="<%=basePath%>open_bidding/bidFileView.html?id=${projectId}" >04、招标文件</a>
		   <i></i>
		 </li>
		 <li>
		   <a  onclick="confirmOk();" >05、确认</a>
		 </li>
	   </ul>
	 </div>
	 <div class="tab-content clear step_cont">
		<!--第一个  -->
		<div class="col-md-12 tab-pane active"  id="tab-1">
		 <!-- <h1 class="f16 count_flow"><i>01</i>初审项定义</h1> -->
		  <form action="">
		    <table class="table table-bordered table-condensed mt5">
		    	<thead>
			      <tr>
			        <th>初审项名称</th>
			        <th>要求类型</th>
			        <th>创建人</th>
			      </tr>
		     	</thead>
		        <c:forEach items="${list }" var="l" varStatus="vs">
		        <thead>
			       <tr>
			        <td align="center">${l.name }</td>
			        <td align="center">${l.kind }</td>
			        <td align="center">${l.creater }</td>
			       </tr>
		       </thead>
		       </c:forEach>
		    </table>
		  </form>
		</div>
     </div>
  </body>
</html>
