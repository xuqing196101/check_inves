<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  
    <title></title>
    
    <script src="<%=basePath%>public/layer/layer.js"></script>
<script type="text/javascript">
    function addAttach(){
        html="<input id='pic' type='file' class='toinline' name='attaattach'/><a href='#' onclick='deleteattach(this)' class='toinline red redhover'>x</a><br/>";
        $("#uploadAttach").append(html);
    }
    
    function deleteattach(obj){
        $(obj).prev().remove();
        $(obj).next().remove();
        $(obj).remove();
    }
</script>    
  </head>
  
  <body>
  

   
   <div class="container">
    <form  id ="form" action="<%=basePath%>resultAnnouncement/publishBidAnnouncement.do" method="post" target="_parent" enctype="multipart/form-data">

       <ul class="list-unstyled list-flow p0_20">
     <li class="col-md-6 p0 mb0">
       <span class=""> 公告名称：</span>
       <div class="input-append">
        <input class="span2" id="name" name="name" type="text">
       </div>
     </li>
     <li class="col-md-6  p0 ">
       <span class="">发布范围：</span>
       <div class="input-append">
        <label class="fl margin-bottom-0"><input type="checkbox" name="ranges" value="0">内网</label>
        <label class="ml10 fl"><input type="checkbox" name="ranges" value="1">外网</label>
       </div>
     </li> 
          
     <li class="col-md-12 p0">
        <span class="fl">上传附件：</span>
        <div class="fl" id="uploadAttach" >
          <input id="pic" type="file" class="toinline" name="attaattach"/>
          <input class="toinline" type="button" value="添加" onclick="addAttach()"/><br/>
        </div>
     </li>
     </ul> 
             
     <div  class="">
       <div class="">
        <button class="btn btn-windows apply" type="submit">发布</button>
        <input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
    </div>
  </div>
</form>
  </div>     
  </body>
</html>

