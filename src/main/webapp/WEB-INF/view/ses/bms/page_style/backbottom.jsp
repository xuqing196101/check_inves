<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
	
	<jsp:include page="backend_common.jsp"></jsp:include>	
	
</head>
  
<body>
<!--底部代码开始-->

<script type="text/javascript" language="javascript">   
function iFrameHeight() {   
var ifm= document.getElementById("iframepage");   
var subWeb = document.frames ? document.frames["iframepage"].document : ifm.contentDocument;   
if(ifm != null && subWeb != null) {
   ifm.height = subWeb.body.scrollHeight;
   ifm.width = subWeb.body.scrollWidth;
}   
}   
</script>
<div>
	<iframe frameborder="0" name="home" id="iframepage" scrolling="no" marginheight="0"  width="100%" onLoad="iFrameHeight()" src="${pageContext.request.contextPath}/login/home.do"></iframe>
</div>
<div class="footer-v2" id="footer-v2">

      <div class="footer">

            <!-- Address -->
               <address>
	        	Copyright © 2016 版权所有：中央军委后勤保障部 京ICP备09055519号 <span class="ratio">浏览本网主页，建议将电脑显示屏的分辨率调为1024*768</span>
               </address>
            <!-- End Address -->

<!--/footer--> 
    </div>
</div>
		 
</body>
</html>
