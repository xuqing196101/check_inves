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
<jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>


<script type="text/javascript">
$(function(){
    laypage({
          cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
          pages: "${listTodosf.pages}", //总页数
          skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
          skip: true, //是否开启跳页
          total: "${listTodosf.total}",
          startRow: "${listTodosf.startRow}",
          endRow: "${listTodosf.endRow}",
          groups: "${listTodosf.pages}">=5?5:"${listTodosf.pages}", //连续显示分页数
          curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
              var page = location.search.match(/page=(\d+)/);
              return page ? page[1] : 1;
          }(), 
          jump: function(e, first){ //触发分页后的回调
              if(!first){ //一定要加此判断，否则初始时会无限刷新
                $("#page").val(e.curr);
               $("#form2").submit();
              }
          }
      });
    $("#default").parent().addClass('active');
    var el=document.getElementById('default');
    el.click();//触发打开事件
    
    $("#menu a").click(function() {
        $('#menu li').each(function(index) {
            $(this).removeClass('active');  // 删除其他兄弟元素的样式
          });
        $(this).parent().addClass('active');                            // 添加当前元素的样式
    });
    
});

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
            <div class="col-md-3 col-md-12 padding-0">
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
                                           <span>●</span>${fn:substring(station.name, 0, 14)}......
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
            <div class="col-md-9 padding-0">
                <div class="col-md-12 tab-v2 job-content">
                    <div class="">
                        <ul class="nav nav-tabs" id="menu">
                            <li class=""><a  href="${pageContext.request.contextPath}/todo/todos.html" id="default" target="open_main" class=" f18">待办事项</a></li>
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
    </div>


</body>
</html>
