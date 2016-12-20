<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
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
        <div class="col-md-12 col-xs-12 col-sm-12 p0 mb5">
            <!-- <input type="button" class="btn btn-windows pl13" value="打印" onclick="dayin()" id="print"/> -->
            <input type="button" class="btn btn-windows pl13" value="返回" onclick="javascript:history.go(-1);"/>
        </div>
  		<input type="hidden" id="name" value="${name }">
	    <input type="hidden" id="projectId" value="${projectId }">
	    <input type="hidden" id="content" value="${content }">
            <div class="content padding-left-25 padding-right-25 padding-top-5" id="div_print">
                ${content}
            </div>
  </body>
</html>

