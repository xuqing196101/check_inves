<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>

<html class=" js cssanimations csstransitions" lang="en">
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<script type="text/javascript">
  $(function(){
      $("#menu a").click(function() {
        $('#menu li').each(function(index) {
          $(this).removeClass('active');  // 删除其他兄弟元素的样式
        });
          $(this).parent().addClass('active'); // 添加当前元素的样式
      });
      /*上面一个的问题就是 $(this).parent().addClass('active')这个a标签不是循环出来的 是自定义的它的id是as */
      $(document).ready(function() {
        $("#menu li").click(function(){
        $(this).addClass("active").siblings().removeClass("active");
      });
    });
  }); 
  
  function back(){
    location.href = '${pageContext.request.contextPath}/project/list.html';
  }
  
  function jumpLoad(url, projectId, flowDefineId){
    var urls="${pageContext.request.contextPath}/"+url+"?projectId="+projectId;
     // $("#as").attr("href",urls);
    //  var el=document.getElementById('as');
     //  el.click();//触发打开事件
      $("#open_bidding_main").load(urls);
  }
  
  function jumpChild(url){
    $("#open_bidding_main").load(url +"#TANGER_OCX");
  }
  
  //页面初始加载将要执行的页面
  function initLoad(){
    var url = $("#initurl").val();
    $("#open_bidding_main").load("${pageContext.request.contextPath}/"+url);
  }
  
 
</script>
</head>

<body onload="initLoad()">
  
   <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs " id="bread_crumbs">
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
                    <li onclick="jumpLoad('advancedProject/mplement.html','${project.id }')" class="active">
                                <a class="son-menu" id="as">项目信息</a>
                              </li> 
                              <li onclick="jumpLoad('advancedProject/mplement.html','${project.id }')" >
                                <a class="son-menu">拟制招标文件</a>
                              </li>  
             </ul>
            </div>
            <!-- 右侧内容开始-->
                      <!-- 右侧内容开始-->
                      <input type="hidden" id="initurl" value="${url }">
                      <div class="tag-box tag-box-v4 col-md-9" id="open_bidding_main">
                      
                      </div>
            <div class="col-md-12 tc mt20" id="iframe_btns">
                <button class="btn btn-windows back" onclick="back();" type="button">返回列表</button>
                  </div>
          </div>
                </div>
        </div><!--/container-->
          <a id="as" class="dnone" target="open_bidding_main" class="son-menu"></a>
</body>
</html>
