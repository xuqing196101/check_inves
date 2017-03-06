<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
	<title>添加供应商页面</title>
</head>
<body>
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">保障作业</a></li><li><a href="javascript:void(0)">定型产品竞价</a></li>
		   <li class="active"><a href="javascript:void(0)">添加供应商</a></li><li class="active"><a href="javascript:void(0)">添加质检信息</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>

<!-- 修改订列表开始-->
   <div class="container container_box">
   <form action="${pageContext.request.contextPath}/addSupplier/add.html" method="post">
   <div>
    <h2 class="count_flow">添加质检信息</h2>
   <ul class="ul_list">
     <li class="col-md-3 col-sm-6 col-xs-12 pl15">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">供应商名称</span>
	   <div class="input-append input_group col-sm-12 col-xs-12 p0">
        <input class="input_group" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li>
	 
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">证书有效期至</span>
	   <div class="input-append input_group col-sm-12 col-xs-12 p0">
        <input class="input_group" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li>
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">质检机构</span>
	   <div class="input-append input_group col-sm-12 col-xs-12 p0">
        <input class="input_group" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">联系人姓名</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">联系人电话</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">资质证书编号</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 ">统一社会信用代码</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
       
	 </li>
	 <li class="col-md-3 col-sm-6 col-xs-12">
	 	<span class="zzzx col-md-12 col-sm-12 col-xs-12 padding-left-5">上传资质证书	：</span>
     	<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
          <a href="#" class="upload"><i></i>上传附件</a>
        </div>
	 </li> 
	 
   </ul>
   <div class="col-md-12 clear tc mt10">
	    	<button class="btn btn-windows save" type="submit">保存</button>
	    	<button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
	    </div>
       <div class="clear"></div> 
  </div> 
  </form>
  </div>
</body>
</html>