<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE>
<html class=" js cssanimations csstransitions" lang="en">
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/portal/js/jquery.min.js"></script>
	<style>
		.brower{
			margin-top:30px;
		}
		.brower_tip{
	  		width:900px;
			margin:0 auto;
			overflow:hidden;
			min-width:290px;
		}
		.brower_box{
	  		width:700px;
			margin:0 auto;
			overflow:hidden;
			min-width:290px;
		}
		.brower_box img {
			width: 75px;
			height: 75px;
		}
		.brower_box .bro_squre{
	   		overflow:hidden;
			padding:10px;
			display:block;
			float:left;
			text-align:center;
			margin:0px 20px;
		}
		.brower_box .btn{
	   		padding:3px 10px;
			font-size:16px;
			color:#ffffff;
			border-radius:0px!important;
			cursor:pointer;
			text-decoration:none;
		}
		.brower_box .chrome{
			background-color:#FB4125;
		}
		.brower_box .firefox{
			background-color:#166BAE;
		}
		.brower_box .internet{
			background-color:#2971EA;
		}
		.brower_box .chrome{
			background-color:#FF0000;
		}
		.brower_box .intel{
			background-color:#23bae9;
		}
		.tc{
			text-align:center;
		}
		.f22{
			font-size:22px;
		}
		.container{
			width:1170px;
			margin:0 auto;
			margin-top:40px;
		}
		.show_tips{
			width:600px;
			margin:20px auto;
			margin-top: 50px;
			background-color: #ffffff;
		}
	</style>
	<script type="text/javascript">
	  function downloadBrowser(version){
		  var form = $("<form>");   
		    form.attr('style', 'display:none');   
		    form.attr('method', 'post');
		    form.attr('action', '${pageContext.request.contextPath}/browser/download.html?ver='+version);
		    $('body').append(form); 
		    form.submit();
	  }
	</script>
</head>
<body>
  <div class="container brower">
	  <h2 class="show_tips">	
		亲，您使用的浏览器版本过低，<br/>
		为了正常访问，建议您升级或者下载以下浏览器。
	  </h2>
	  <div class="brower_box">
			<div class="bro_squre">
			  <p><img src="${pageContext.request.contextPath}/public/portal/images/chrome.png"/></p>
			  <p><a class="btn tc chrome" href="javascript:downloadBrowser('chrome_64');">使用Chrome浏览器(64位)</a></p>
			  <p><a class="btn tc chrome" href="javascript:downloadBrowser('chrome_32');">使用Chrome浏览器(32位)</a></p>
			</div>

			<div class="bro_squre">
			  <p><img src="${pageContext.request.contextPath}/public/portal/images/firefox.png"/></p>
			  </p><a class="btn tc firefox" href="javascript:downloadBrowser('firefox');">使用Firefox浏览器</a></p>
			</div>
			<div class="bro_squre">
			  <p><img src="${pageContext.request.contextPath}/public/portal/images/ie11.png"/></p>
			  <p><a class="btn tc internet" href="javascript:downloadBrowser('ie10_64');">升级到IE10(64位)</a></p>
			  <p><a class="btn tc intel" href="javascript:downloadBrowser('ie11_32');">升级到IE11(32位)</a></p>
			  <p><a class="btn tc intel" href="javascript:downloadBrowser('ie11_64');">升级到IE11(64位)</a></p>
			</div>
	  </div>	
	  <div class="brower_tip"><img src="${pageContext.request.contextPath}/public/portal/images/tips.png"/></div>
	</div>
</body>
</html>