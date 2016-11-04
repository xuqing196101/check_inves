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


<title>版块管理</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">




<link href="${pageContext.request.contextPath}/public/ZHH/css/common.css" media="screen"
    rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/ZHH/css/bootstrap.min.css"
    media="screen" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/ZHH/css/style.css" media="screen"
    rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/ZHH/css/line-icons.css" media="screen"
    rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/ZHH/css/app.css" media="screen"
    rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/ZHH/css/application.css" media="screen"
    rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/ZHH/css/header-v4.css" media="screen"
    rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/ZHH/css/header-v5.css" media="screen"
    rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/ZHH/css/brand-buttons.css"
    media="screen" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/ZHH/css/footer-v2.css" media="screen"
    rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/ZHH/css/img-hover.css" media="screen"
    rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/ZHH/css/page_job.css" media="screen"
    rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/ZHH/css/shop.style.css" media="screen"
    rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/purchase/css/purchase.css"
    media="screen" rel="stylesheet" type="text/css">
<script type="text/javascript"
    src="${pageContext.request.contextPath}/public/ZHH/js/jquery.min.js"></script>
<script type="text/javascript"
    src="${pageContext.request.contextPath}/public/ZHH/js/jquery_ujs.js"></script>
<script type="text/javascript"
    src="${pageContext.request.contextPath}/public/ZHH/js/bootstrap.min.js"></script>
<script type="text/javascript"
    src="${pageContext.request.contextPath}/public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/ajaxfileupload.js"></script>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}//public/ztree/css/zTreeStyle.css">
<script type="text/javascript" src="${pageContext.request.contextPath}//public/ztree/jquery.ztree.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}//public/ztree/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}//public/ztree/jquery.ztree.exedit.js"></script>

<script type="text/javascript">
    function bag(id){
        window.location.href = "${pageContext.request.contextPath}/project/subPackage.html?id="+id;
    }
</script>
</head>

<body>
    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
        <div class="container">
            <ul class="breadcrumb margin-left-0">
            </ul>
            <div class="clear"></div>
        </div>
    </div>



    <div class="container clear margin-top-30" id="add_div">
    
        

        <form id="add_form" action="${pageContext.request.contextPath}/project/adddetail.html" method="post">
        <div class="tag-box tag-box-v4 col-md-9">
        <input class="btn btn-windows save" type="button" onclick="bag('${project.id}');" value="分包"> 
            <table class="table table-bordered">
        <tr>
            
          <td class="bggrey tr">项目编号:</td><td>${project.projectNumber}</td>
          <td class="bggrey tr">项目名称:</td><td>${project.name}</td>
        </tr>
        <tr>
          <td class="bggrey tr">预算金额:</td><td>${project.purchaseDepName}</td>
          <td class="bggrey tr">经办人:</td><td>${project.ipone}</td>
        </tr>
        <tr>
          <td class="bggrey tr">项目介绍:</td>
          <td colspan="3">
          <textarea rows="3" cols="20" name="prIntroduce">
            ${project.prIntroduce}
          </textarea>
          </td>
        </tr>
        <tr>
          <td class="bggrey tr">工作分工:</td>
          <td colspan="3">
          <textarea rows="3" cols="20" name="divisionOfWork">
            ${project.divisionOfWork}
          </textarea>
          </td>
        </tr>
            </table>
            <button class="btn btn-windows git"   type="button" onclick="window.print()">打印</button>
            <button class="btn btn-windows git" onclick="history.go(-1)" type="button">返回</button>
            </div>
        </form>
    </div>

 

 
</body>
</html>
