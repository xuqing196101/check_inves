<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML >
<html>
  <head>
    <jsp:include page="/WEB-INF/view/common.jsp"/>
    <title>My JSP 'peraseContract.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<script language="JavaScript">
    function initDoc(){
    var obj = document.getElementById("TANGER_OCX");
    console.info(obj);
    	obj.Menubar = true;
    	obj.createnew("Word.Document");
    
    }
   </script>

 
  </head>
   <body onload="initDoc()">
 
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ntko/ntkoofficecontrol.js"></script>

  </body>
</html>
