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
		var urls="${pageContext.request.contextPath}/"+url+"?projectId="+projectId+"&flowDefineId="+flowDefineId;
      $("#as").attr("href",urls);
      var el=document.getElementById('as');
       el.click();//触发打开事件
      // $("#open_bidding_main").load(urls);
	}
	
	function jumpChild(url){
		$("#open_bidding_main").load(url +"#TANGER_OCX");
	}
	
	//页面初始加载将要执行的页面
	function initLoad(){
		var url = $("#initurl").val();
		$("#open_bidding_main").load("${pageContext.request.contextPath}/"+url);
	}
	
	function tips(step){
		if(step != 1){
			layer.msg("请先执行前面步骤",{offset: ['220px']});
		}
	}
	
	function abandoned(id){
	  layer.confirm('您确定要废标吗?',{
              title : '提示',
              offset: ['30%', '40%'],
              shade : 0.01
            },
          function(index) {
            layer.close(index);
           $.ajax({
            url : "${pageContext.request.contextPath}/project/abandoned.html",
            data : "id=" + id,
            type : "post",
            dateType : "text",
            success : function(data) {
              if(data == "\"SCCUESS\"") {
                layer.msg("废标成功", {
                 time: 2000, 
                });
                window.setTimeout(function() {
		              window.location.href = "${pageContext.request.contextPath}/project/list.html";
		            }, 1000);
                
              }
               
              /* window.setTimeout(function() {
              location.reload();
              }, 1000); */
            },
            error : function() {
              layer.msg("废标失败", {
                offset: ['30%', '40%'],
              });
            }
            });
          });
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
	                       <c:forEach items="${fds}" var="fd">
	                       	  	<c:choose> 
								  <c:when test="${fd.status == 4}">   
								    <li onclick="jumpLoad('${fd.url}','${project.id }','${fd.id}')" <c:if test="${fd.step == 1}">class="active"</c:if>>
		                       			<a class="son-menu">${fd.name }</a>
		                       		</li>  
								  </c:when> 
								  <c:when test="${fd.status == 1}">
		                       		<li onclick="jumpLoad('${fd.url}','${project.id }','${fd.id}')" <c:if test="${fd.step == 1}">class="active"</c:if>>
		                       			<a class="son-menu">${fd.name }</a>
		                       		</li> 
								  </c:when> 
								  <c:when test="${fd.status == 2}">
		                       		<li onclick="jumpLoad('${fd.url}','${project.id }','${fd.id}')" <c:if test="${fd.step == 1}">class="active"</c:if>>
		                       			<a class="son-menu">${fd.name }</a>
		                       		</li> 
								  </c:when>
								  <c:otherwise>   
								    <%-- <li  onclick="tips(${fd.step})"> --%>
								    <li onclick="jumpLoad('${fd.url}','${project.id }','${fd.id}')" <c:if test="${fd.step == 1}">class="active"</c:if>>
		                       			<a class="son-menu">${fd.name }</a>
		                       		</li>  
								  </c:otherwise> 
								</c:choose>
	                       </c:forEach>
						 </ul>
					  </div>
					  <!-- 右侧内容开始-->
					  <input type="hidden" id="initurl" value="${url }">
					  <!-- <div class="tag-box tag-box-v4 col-md-9 "  id="open_bidding_main">
					  		
					  </div> -->
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
                      <div class="tag-box tag-box-v4 col-md-9" >
                         <iframe  frameborder="0" name="open_bidding_main" id="open_bidding_iframe"  scrolling="auto" marginheight="0"  width="100%" onLoad="iFrameHeight()"  src="${pageContext.request.contextPath}/${url}"></iframe>
                      </div>
					  <div class="btmfix" >
					    <div style="margin-top: 15px;text-align: center;">
					       <button class="btn btn-windows delete" onclick="abandoned('${project.id}');" type="button">废标</button>
					       <button class="btn btn-windows back" onclick="back();" type="button">返回列表</button>
					    </div>
       	   	</div>
				  </div>
                </div>
        </div><!--/container-->
        	<a id="as" class="dnone" target="open_bidding_main" class="son-menu"></a>
</body>
</html>
