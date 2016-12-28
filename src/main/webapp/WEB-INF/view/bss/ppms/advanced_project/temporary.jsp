<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
		<script type="text/javascript">
			$(function(){
				window.parent.location.href = "${pageContext.request.contextPath }/advancedProject/list.html";
			})
		</script>
	

  </head>
  
  <body>
    
  </body>
</html>
