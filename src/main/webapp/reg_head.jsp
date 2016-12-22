<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
<!--<![endif]-->

  <head>
    <!--导航js-->

    <%@ include file="/WEB-INF/view/portal.jsp" %>

    <script type="text/javascript">
      var user = "${sessionScope.loginUser.relName}";
      $(function() {
        $(document).keyup(function(event) {
          if(event.keyCode == 13) {
            $("#form1").submit();
          }
        });

        if(user != null && user != '') {
          $("#welcome").html(user + "你好，欢迎来到中国军队采购网！");
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
            <div class="col-md-9 col-xs-9 col-sm-9" id="welcome">你好，欢迎来到中国军队采购网！
              <a href="${pageContext.request.contextPath}/index/sign.html" class="red">【请登录】</a>
            </div>
            <div class="col-md-3 col-xs-3 col-sm-3 head_right">
              <!-- 根据session判断 -->
              <a onclick="myInfo()">我的信息</a>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="header-v4 clear">
      <!-- Navbar -->
      <div class="navbar navbar-default mega-menu" role="navigation">
        <div class="head_main">
          <div class="container">
            <!-- logo和搜索 -->
            <div class="navbar-header">
              <div class="row container margin-bottom-10">
                <div class="col-md-8 m20_0">
                  <a href="${pageContext.request.contextPath}/index/selectIndexNews.html">
                    <img alt="Logo" src="${pageContext.request.contextPath}/public/portal/images/logo.png" id="logo-header">
                  </a>
                </div>
              </div>
            </div>

            <button data-target=".navbar-responsive-collapse" data-toggle="collapse" class="navbar-toggle" type="button">
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