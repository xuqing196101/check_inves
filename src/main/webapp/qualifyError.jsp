<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   <script type="text/javascript">
       $(function() {
        	layer.alert('权限不足',{title:'提示',area : '240px',offset: ['30%' , '40%'],shade:0.01 },function(){window.location.href = globalPath+"/login/home.html";});
       });
  </script>
  </head>
  
  <body>
       <div style='width:1000px; height: 1000px;'></div>
  </body>
</html>
