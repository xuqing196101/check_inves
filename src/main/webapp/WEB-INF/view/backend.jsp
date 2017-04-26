<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="ses.model.bms.User" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
<!--<![endif]-->
<head>
 <script src="${pageContext.request.contextPath}/public/backend/js/jquery.min.js"></script>
<link href="${pageContext.request.contextPath}/public/backend/css/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/backend/css/common.css" media="screen" rel="stylesheet" type="text/css">  
<link href="${pageContext.request.contextPath}/public/backend/css/unify.css" media="screen" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/backend/css/global.css" media="screen" rel="stylesheet" type="text/css">
 
  
 
 
 
<script type="text/javascript">

  
$(function(){$("#menu a").click(function() {
        $('#menu li').each(function(index) {
            $(this).removeClass('active');  // 删除其他兄弟元素的样式
          });
        $(this).parent().addClass('active');                            // 添加当前元素的样式
      })
    })


//点击url
function clickuri(url){
	
var uri=url.split("^");	
	
if('downloadabiddocument' == uri[0] ){
	window.location.href="${pageContext.request.contextPath}/file/download.html?id="+ uri[1] +"&key=${sysId}";
}else{
 window.location.href="${pageContext.request.contextPath}/"+uri[0];	
}

	
}

</script>
</head>
<body>
    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
        <div class="container">
            <ul class="breadcrumb margin-left-0">
                <li><a href="#"> 首页</a></li>
                <li><a href="#" class="active">后台管理</a></li>
            </ul>
            <div class="clear"></div>
        </div>
    </div>

    <!-- 后台管理内容开始-->
    <div class="container content height-350 job-content ">
    
        <div class="row magazine-page">
            <div class="col-md-3 col-sm-4 col-xs-12 padding-0">
                <div class="col-md-12 p0_10 margin-bottom-20">
                    <div class="tag-box tag-box-v3 margin-0 p0_10">
                        <div class="margin-0">
                            <h2 class="margin-0 news cursor"
                                onclick="window.location.href='${pageContext.request.contextPath}/StationMessage/listStationMessage.do'">通知</h2>
                        </div>
                        <ul class="backend_new" >
                            <c:forEach items="${stationMessage}" var="station">
                                <li><a href="javascript:void(0);" onclick="clickuri('${station.url}^${station.id}');" title="${station.name }"> <c:choose>
                                            <c:when test="${fn:length(station.name) > 10}">  
                                           <span>●</span>${fn:substring(station.name, 0, 30)}......
                                        </c:when>
                                            <c:otherwise>  
                                          	<span>●</span>${station.name}
                                        </c:otherwise>
                                        </c:choose>
                                </a></li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="col-md-9 col-sm-8 col-xs-12 padding-0">
                <div class="col-md-12 col-sm-12 col-xs-12 tab-v2 job-content">
                        <ul class="nav nav-tabs" id="menu">
                            <li class="active"><a  href="${pageContext.request.contextPath}/todo/todos.html" id="default" target="open_main" class=" f18">待办事项</a></li>
                            <li class=""><a  href="${pageContext.request.contextPath}/todo/havetodo.html" target="open_main"  class=" f18">已办事项</a></li>
                        </ul>
                         <script type="text/javascript" language="javascript">   
                        function iFrameHeight() {   
                        var ifm= document.getElementById("open_main");   
                        var subWeb = document.frames ? document.frames["open_main"].document : ifm.contentDocument;   
                        if(ifm != null && subWeb != null) {
                           ifm.height = subWeb.body.scrollHeight;
                           /*ifm.width = subWeb.body.scrollWidth;*/
                        }   
                        }   
                        </script>
                      <!-- 右侧内容开始-->
                      <div class="">
                         <iframe  frameborder="0" name="open_main" id="open_main" scrolling="no" marginheight="0"  width="100%" onLoad="iFrameHeight()" src="${pageContext.request.contextPath}/todo/todos.html" ></iframe>
                      </div>
                </div>
            </div>
        </div>
    </div>


</body>
</html>
