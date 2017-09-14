<%@ page language="java" import="java.util.*,bss.util.PropUtil" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE>
<html class=" js cssanimations csstransitions" lang="en">
<head>
<%
  //生产环境
  String environment = PropUtil.getProperty("environment");
  //内外网
  String ipAddressType = PropUtil.getProperty("ipAddressType");
%>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/portal/js/jquery.min.js"></script>
	<style>
		.red {
		  color: red;
		  text-decoration:none;
		}
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
	  		width:740px;
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
			background-color:#159F5C;
		}
		.brower_box .firefox{
			background-color:#ff0000;
		}
		.brower_box .internet{
			background-color:#2971EA;
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
		.f28{
			font-size:28px;
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
  	  <div class="show_tips f28">
  	  <% if (environment != null && environment.equals("1")){ %>
         <% if(ipAddressType != null && ipAddressType.equals("0")) { %>
           <a href="http://21.100.16.14" class="red">【旧系统登录】</a>
         <%} %>
	   <% } %>
	   </div>
	  <h2 class="show_tips">	
		亲，您使用的浏览器版本过低，<br/>
		为了正常访问，建议您升级或者下载以下浏览器。
	  </h2>
	  <div class="brower_box">
	  		<div class="bro_squre">
			  <p><img src="${pageContext.request.contextPath}/public/portal/images/firefox.png"/></p>
			  </p><a class="btn tc firefox" href="javascript:downloadBrowser('firefox');">（推荐）使用Firefox浏览器</a></p>
			</div>
	  
			<div class="bro_squre">
			  <p><img src="${pageContext.request.contextPath}/public/portal/images/chrome.png"/></p>
			  <p><a class="btn tc chrome" href="javascript:downloadBrowser('chrome_64');">使用Chrome浏览器(64位)</a></p>
			  <p><a class="btn tc chrome" href="javascript:downloadBrowser('chrome_32');">使用Chrome浏览器(32位)</a></p>
			</div>
	  </div>	
	  <div class="brower_tip"><img src="${pageContext.request.contextPath}/public/portal/images/tips.png"/></div>
	</div>
</body>
</html>