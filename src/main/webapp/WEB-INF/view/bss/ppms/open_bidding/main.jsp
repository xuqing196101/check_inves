<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>
	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
</head>
<script type="text/javascript">
	$(function(){
	    $("#menu a").click(function() {
		    $('#menu li').each(function(index) {
			    $(this).removeClass('active');  // 删除其他兄弟元素的样式
			  });
	        $(this).parent().addClass('active');                            // 添加当前元素的样式
	    });
	}); 
	
	function back(page){
		location.href = '${pageContext.request.contextPath}/project/list.html?page='+page;
	}
	
	function jump(url, projectId, flowDefineId){
		var urls="${pageContext.request.contextPath}/"+url+"?projectId="+projectId+"&flowDefineId="+flowDefineId;
       	    
       //$("#as").attr("href",urls);
      var el=document.getElementById('as');
       el.click();//触发打开事件
       $("#open_bidding_main").load(urls);
	}
	
	$(function(){
		$("#open_bidding_main").load("${pageContext.request.contextPath}/${url}");
	});
	
	function tips(step){
		if(step != 1){
			layer.msg("请先执行前面步骤",{offset: ['220px']});
		}
	}
</script>
<body>
  
   <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		<ul class="breadcrumb margin-left-0">
		   <li><a href="#">首页</a></li><li><a href="">保障作业</a></li><li><a href="">采购项目管理</a></li><li><a href="">采购项目实施</a></li> 
		</ul>
	  </div>
   </div>
   <!--=== End Breadcrumbs ===-->

   <!--=== Content Part ===-->
   <div class="container content height-350">
            <div class="row">
                <!-- Begin Content -->
                  <div class="col-md-12" style="min-height:400px;">
                      <div class="col-md-3 md-margin-bottom-40" id="show_tree_div">
	                     <ul class="btn_list" id="menu">
	                       <c:forEach items="${fds}" var="fd">
	                       	  	<c:choose> 
								  <c:when test="${fd.status == 4}">   
								    <li  onclick="jump('${fd.url}','${project.id }','${fd.id}')" class="active">
		                       			<a   class="son-menu">${fd.name }</a>
		                       		</li>  
								  </c:when> 
								  <c:when test="${fd.status == 1}">
		                       		<li  onclick="jump('${fd.url}','${project.id }','${fd.id}')">
		                       			<a  class="son-menu">${fd.name }</a>
		                       		</li> 
								  </c:when> 
								  <c:when test="${fd.status == 2}">
		                       		<li  onclick="jump('${fd.url}','${project.id }','${fd.id}')">
		                       			<a   class="son-menu">${fd.name }</a>
		                       		</li> 
								  </c:when>
								  <c:otherwise>   
								    <%-- <li  onclick="tips(${fd.step})"> --%>
								    <li  onclick="jump('${fd.url}','${project.id }','${fd.id}')">
		                       			<a   class="son-menu">${fd.name }</a>
		                       		</li>  
								  </c:otherwise> 
								</c:choose>
	                       </c:forEach>
						 </ul>
					  </div>
					  <!-- 右侧内容开始-->
					  <div class="tag-box tag-box-v4 col-md-9"  id="open_bidding_main">
					  </div>
					  <div class="col-md-12 tc mt20" >
					  		<button class="btn btn-windows back" onclick="back(${page});" type="button">返回项目列表</button>
       	   			  </div>
				  </div>
                </div>
                <!-- End Content -->
            </div>
        </div><!--/container-->
        	<a id="as" class="dnone" target="open_bidding_main" class="son-menu"></a>
</body>
</html>
