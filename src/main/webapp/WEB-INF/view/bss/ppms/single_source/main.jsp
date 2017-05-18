<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
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
        location.href = '${ pageContext.request.contextPath }/project/list.html?page='+page;
    }
</script>
<body id="iframe_special">
  
   <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs " id="bread_crumbs">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
           <li><a href="#">首页</a></li><li><a href="">保障作业</a></li><li><a href="">采购项目管理</a></li><li><a href="">单一来源项目实施</a></li> 
        </ul>
      </div>
   </div>
   <!--=== End Breadcrumbs ===-->

   <!--=== Content Part ===-->
   <div class="container content height-350">
            <div class="row">
                <!-- Begin Content -->
                      <div class="col-md-2 col-sm-3 col-xs-12" id="show_tree_div">
                         <ul class="btn_list" id="menu">
                             <c:forEach items="${fds}" var="fd">
                                <li <c:if test="${fd.step == 1 }">class="active"</c:if>>
                                    <a href="${pageContext.request.contextPath}/${fd.url}?projectId=${project.id}" target="open_bidding_main" class="son-menu">${fd.name }</a>
                                </li>
                           </c:forEach>
                         </ul>
                      </div>
                        <script type="text/javascript" language="javascript">   
                          function getContentSize() {
	         				var he = document.documentElement.clientHeight;
							var btn = $("#iframe_btns").outerHeight(true);
	   						var bread= $("#bread_crumbs").outerHeight(true) ;
							ch = (he - btn - bread) + "px";
							document.getElementById("open_bidding_iframe").style.height = ch;
							}
							window.onload = getContentSize;
							window.onresize = getContentSize;
 					  </script>
                      <!-- 右侧内容开始-->
                      <div class="tag-box tag-box-v4 col-md-10 col-sm-9 col-xs-12" >
                         <iframe  frameborder="0" name="open_bidding_main" id="open_bidding_iframe" scrolling="no" marginheight="0"  width="100%" onLoad="iFrameHeight();"  src="${ pageContext.request.contextPath }/project/mplement.html?id=${project.id}"></iframe>
                      </div>
                      <div class="w100p tc mt20" id="iframe_btns">
                            <button class="btn btn-windows back" onclick="back(${page});" type="button">返回项目列表</button>
                      </div>
                <!-- End Content -->
            </div>
        </div><!--/container-->
</body>
</html>


