<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../../common.jsp"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>待办</title>
<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script type="text/javascript">
  function tijiao(status){
  $("#status").val(status);
  form1.submit();
}
</script>
</head>
<body>
<!--面包屑导航开始-->
  <div class="margin-top-10 breadcrumbs ">
    <div class="container">
		  <ul class="breadcrumb margin-left-0">
		    <li><a href="#">供应商审核</a></li><li><a href="#">我的待办</a></li>
		  </ul>
	  </div>
  </div>
   
  <div class="container mt20">
    <form id="form1" action="${pageContext.request.contextPath}/supplierAudit/supplierList.html" method="post">
       <input type="hidden" name="status" id="status" />
       <div class="col-md-4" style="cursor: pointer">
          <span onclick="tijiao(0)">供应商未审核</span>（<a class="red b">${weishen }</a>）
       </div>
       <div class="col-md-4"></div>
       <div class="col-md-4"></div>
       <div class="col-md-4" style="cursor: pointer">
          <span onclick="tijiao(1)">供应商审核中</span>（<a class="red b">${shenhezhong }</a>）
       </div> 
       <div class="col-md-4"></div>
       <div class="col-md-4"></div>
       <div class="col-md-4" style="cursor: pointer">
          <span onclick="tijiao(3)">供应商已审核</span>（<a class="red b" style="cursor: pointer">${yishen }</a>）
       </div>
       <div class="col-md-4"></div>
       <div class="col-md-4"></div>
    </form>
  </div>
    
</body>
</html>
