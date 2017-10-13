<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
<!--<![endif]-->

  <head>
    <!-- 前端css样式 -->
		<link href="${pageContext.request.contextPath}/public/front/images/favicon.ico" rel="shortcut icon" type="image/x-icon" />
		<link href="${pageContext.request.contextPath}/public/front/css/style.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/public/front/css/bootstrap.min.css" rel="stylesheet"  type="text/css" />
		<link href="${pageContext.request.contextPath}/public/front/css/common.css" rel="stylesheet"  type="text/css" />
		
		<link href="${pageContext.request.contextPath}/public/front/css/btn.css" rel="stylesheet"  type="text/css" />
		<link href="${pageContext.request.contextPath}/public/ztree/css/zTreeStyle.css" type="text/css" rel="stylesheet" >
		<link href="${pageContext.request.contextPath}/public/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css">
		<link href="${pageContext.request.contextPath}/public/front/css/global.css" rel="stylesheet"  type="text/css" />
		<!-- 前端论坛Css颜色样式 -->
		<link href="${pageContext.request.contextPath}/public/front/css/forum.css" rel="stylesheet" type="text/css">
		
		<style type="text/css">
			.new_step.current i{
				cursor: pointer;
			}
		</style>
			
		<script>
			var globalPath = "${pageContext.request.contextPath}";
		</script>

		<%@ include file="/WEB-INF/view/portal.jsp" %>
		
		<!-- 前端js -->
    <%--<script src="${pageContext.request.contextPath}/public/front/js/jquery.min.js"></script>
		<script src="${pageContext.request.contextPath}/public/front/js/bootstrap.min.js"></script>
		<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
		<script src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>--%>
		<script src="${pageContext.request.contextPath}/public/front/js/common.js"></script>
		<script src="${pageContext.request.contextPath}/public/front/js/main-menu.js"></script>
		<script src="${pageContext.request.contextPath}/public/My97DatePicker/WdatePicker.js"></script>
		
		<!-- js校验 -->  
		<script src="${pageContext.request.contextPath}/public/validate/jquery.validate.min.js"></script>
		<script src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.all.js"></script>
  
		<!--导航js-->

		<!-- global.css与bootstrap.min.css冲突的地方 -->
		<style type="text/css">
			.table>tbody>tr>td, 
			.table>tbody>tr>th, 
			.table>tfoot>tr>td, 
			.table>tfoot>tr>th, 
			.table>thead>tr>td, 
			.table>thead>tr>th {
		    vertical-align: middle;
			}
		</style>
		
		<script type="text/javascript">
			$(function(){
				$("#close").click(function(){
					$(".prompt_tips").hide();
				});
			});
		</script>
    <script type="text/javascript">
      var user = "${sessionScope.loginUser.relName}";
      $(function() {
        $(document).keyup(function(event) {
          if(event.keyCode == 13) {
            $("#form1").submit();
          }
        });

        if(user != null && user != '') {
          $("#welcome").html(user + "你好，欢迎来到军队采购网！");
        }

        $(".header-v4 .navbar-default .navbar-nav > .other > a").hover(function() {
          $("#firstPage").attr("Class", "dropdown shouye_li mega-menu-fullwidth");
        });
      })

      function myInfo() {
        if(user != null && user != '') {
          window.location.href = "${pageContext.request.contextPath}/login/index.html";
        } else {
          window.location.href = "${pageContext.request.contextPath}/index/sign.html";
        }
      }

      function importAdd() {
        if(user == null) {
          layer.alert("请先登录", {
            offset: ['222px', '90px'],
            shade: 0.01
          });
          return;
        }
        window.location.href = "${pageContext.request.contextPath}/importSupplier/register.html";
      }
    </script>
  </head>

  <body>
    <div class="wrapper">
      <div class="head_top col-md-12 col-xs-12 col-sm-12">
        <div class="container p0">
          <div class="row">
          	<c:choose>
          		<c:when test="${!empty sessionScope.loginUser.relName}">
          			<div class="col-md-9 col-xs-9 col-sm-9" id="welcome">${sessionScope.loginUser.relName}你好，欢迎来到军队采购网！
		            </div>
          		</c:when>
          		<c:when test="${empty sessionScope.loginUser.relName and !empty sessionScope.loginName}">
          			<div class="col-md-9 col-xs-9 col-sm-9" id="welcome">你好，欢迎来到军队采购网！
		            </div>
          		</c:when>
          		<c:otherwise>
          			<div class="col-md-9 col-xs-9 col-sm-9" id="welcome">你好，欢迎来到军队采购网！
		              <c:if test="${requestSource != 'zjRegister' }">
		              	<a href="${pageContext.request.contextPath}/index/sign.html" class="red">【请登录】</a> 
		              </c:if>
		            </div>
          		</c:otherwise>
          	</c:choose>
            <%-- <div class="col-md-9 col-xs-9 col-sm-9" id="welcome">你好，欢迎来到军队采购网！
              <c:if test="${requestSource != 'zjRegister' }">
               <a href="${pageContext.request.contextPath}/index/sign.html" class="red">【请登录】</a> 
              </c:if>
            </div> --%>
            <div class="col-md-3 col-xs-3 col-sm-3 head_right">
              <!-- 根据session判断 -->
                <a href="javascript:void(0)">网站编号：${properties['website.no']}</a>
    	 		<c:if test="${not empty loginUser }">
    	 			|<a onclick="myInfo()">我的信息</a>
    	 		</c:if>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="header-v4 clear">
      <!-- Navbar -->
      <div class="navbar navbar-default mega-menu" role="navigation">
        <div class="head_main reg_head">
          <div class="container">
            <!-- logo和搜索 -->
            <div class="navbar-header">
              <div class="row container margin-bottom-10">
                <div class="col-md-5 col-sm-6 col-xs-12 m20_0">
                  <a href="${pageContext.request.contextPath}/index/selectIndexNews.html">
                    <img alt="Logo" src="${pageContext.request.contextPath}/public/portal/images/logo.png" id="logo-header" width="100%">
                  </a>
                </div>
                <div class="col-md-6 col-md-offset-1 col-sm-6 col-xs-12 p0 gpgz">
                
                </div>
              </div>
            </div>
<!-- 
            <button data-target=".navbar-responsive-collapse" data-toggle="collapse" class="navbar-toggle" type="button"> -->
            <span class="icon-toggle">
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </span>
          </button>
          </div>
        </div>
        
      </div>
    </div>
  </body>

</html>