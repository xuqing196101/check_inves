<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="/WEB-INF/view/common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <link href="${pageContext.request.contextPath}/public/webupload/css/viewer.css" />
  <script type="text/javascript" src="${pageContext.request.contextPath}/public/webupload/js/viewer.js"></script>
  <script type="text/javascript" >
  
  var viewer;
  $(function(){
	  
	  
	  var businessId = $("#businessId").val();
	  var typeId = $("#typeId").val();
	  var key = $("#keyId").val();
	  display(businessId,typeId,key);
	  
	  viewer = $("#showPicId").viewer({
		  url:'data-original'
	  }); 
	 
  });
  
  
  function clo(){
	  alert(1);
	 // var pictures = document.querySelector('#showPicId');
	  viewer.show();
  }
  
  </script>
  <style type="text/css">
    * { margin: 0; padding: 0;}
#showPicId { width: 700px; margin: 0 auto; font-size: 0;}
#showPicId li { display: inline-block; width: 32%; margin-left: 1%; padding-top: 1%;}
#showPicId li img { width: 100%;}
  </style>
</head>
<body>
    <input type="hidden" id="businessId"  value="${businessId}" />
    <input type="hidden" id="typeId" value="${typeId}" />
    <input type="hidden" id="keyId"  value="${key}" />
     <ul id="showPicId"></ul>
</body>
</html>