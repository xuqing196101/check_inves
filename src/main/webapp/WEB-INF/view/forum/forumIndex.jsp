<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>论坛管理</title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="${ pageContext.request.contextPath }/public/layer/layer.js"></script>
	<script type="text/javascript" src="${ pageContext.request.contextPath }/public/layer/extend/layer.ext.js"></script>
		<link href="${ pageContext.request.contextPath }/public/layer/skin/layer.css" rel="stylesheet" type="text/css" />
	<link href="${ pageContext.request.contextPath }/public/layer/skin/layer.ext.css" rel="stylesheet" type="text/css" />
  <script type="text/javascript">
	$(function(){	 
		$.ajax({
		    url:"<%=basePath %>post/getlistforindex.do",   
		    contentType: "application/json;charset=UTF-8", 
		    dataType:"json",   //返回格式为json
		    type:"POST",   //请求方式		    
	        success : function(data) {         	
	            if (data && data.success == "true") {  
	            	
	              $("#postlist").html();                
	              $.each(data.data, function(i, item) {  
	            	var date = new Date(parseInt(item.publishedTime,10));	            	
	                $("#postlist").append("<li><a href='' target='_blank'></a>"+simple(item.name)+"<span class='hex pull-right'>"+getDate(date)+"</span></li>");  
	              });  
	              
	              
	            }
	        }

		});
	});
	//时间格式转换
	function getDate(date) {
	    var year = date.getFullYear();
	    var month = date.getMonth() + 1;
	    var day = date.getDate();
	    return year + "年" + month + "月" + day + "日" ; 
	}
	//帖子名称精简
	function simple(content){
		var length = content.length;
		if(length<15){
			return content; 
		}else{
			return content.substring(0,15)+"..."
		}
	}
	
 </script>
  </head>
  
  <body>
	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li class="active"><a href="#">论坛</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
	   <div class="headline-v2">
	   		<h2>采购论坛</h2>
	   </div>
   </div>
<!-- 表格开始-->
   <div class="container">
   <div class="col-md-8">
	</div>
            <div class="col-md-4 ">
              <div class="search-block-v2">
                <div class="">
                  <form accept-charset="UTF-8" action="" method="get"><div style="display:none"><input name="utf8" value="✓" type="hidden"></div>
                    <input id="t" name="t" value="search_products" type="hidden">
                    <div class="col-md-12 pull-right">
                      <div class="input-group">
                        <input class="form-control bgnone h37 p0_10" id="k" name="k" placeholder="" type="text">
                        <span class="input-group-btn">
                          <input class="btn-u" name="commit" value="搜索" type="submit">
                        </span>
                      </div>
                    </div>
                  </form>               
			   </div>
              </div>
            </div>	
    </div>
   
   <div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">		
		<c:forEach items="${list}" var="park" varStatus="vs">
			<div class="col-md-6 tab-v2 margin-bottom-10">
		        <div class="tag-box-v1 margin-bottom-0">
		        
		          <h2>${park.name}
		          <span class="badge badge-light pull-right"><a href="#" target="_blank">更多>></a></span>
		          </h2>
		          
		          <ul class="list-unstyled categories tab-content margin-0" id="postlist">
		          </ul>
		        </div>
		      </div>
		</c:forEach>

     </div>
   
   </div>
   

  </body>
</html>

