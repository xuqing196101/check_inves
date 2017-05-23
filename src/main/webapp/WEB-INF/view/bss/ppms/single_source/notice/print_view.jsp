<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>打印预览页面</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <script type="text/javascript">
        //打印功能
        function dayin() {
            var LODOP = getLodop();
            if (LODOP) {
                LODOP.PRINT_INIT("打印"); 
                LODOP.ADD_PRINT_TABLE("0","0","100%","100%",document.getElementById("div_print").innerHTML);
                
                LODOP.PREVIEW(); 
            }
        }
        
        function goback(){
        	var name = $("#name").val();
        	var content = $("#content").val();
        	var projectId =$("#projectId").val();
        	location.href = '${pageContext.request.contextPath}/open_bidding/printViewBack.html?name='+name+'&content='+content+'&projectId='+projectId;
        }
    </script>
    
    <style type="text/css">
        table thead th{
            border:1px solid black;
        }
        table tbody td{
            border:1px solid black;
        }
    </style>
  </head>
  
  <body>
    <div class="container">
        <div class="col-md-10 tc">
            <input type="button" class="btn btn-windows pl13" value="打印" onclick="dayin()" id="print"/>
            <input type="button" class="btn btn-windows pl13" value="返回" onclick="javascript:history.go(-1);"/>
        </div>
    </div>
  		<input type="hidden" id="name" value="${name }">
	    <input type="hidden" id="projectId" value="${projectId }">
	    <input type="hidden" id="content" value="${content }">
        <div class="container margin-top-5">
            <div class="content padding-left-25 padding-right-25 padding-top-5" id="div_print">
                ${content}
            </div>
        </div>
  </body>
</html>

