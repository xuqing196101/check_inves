<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<script type="text/javascript">
	function indexTip(id){
  		window.location.href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id="+id;
  	}
	
	//实现显示隐藏底部弹窗功能
	$(document).ready(function(){ 
		var currUrl=window.location.href;
		//定义数组
		var urlArr=new Array();
		//供应商注冊頁面
		urlArr[0]="supplier/";
		//登錄頁面
		urlArr[1]="index/sign";
		//专家注册
		urlArr[2]="expert/";
		
		for(var i=0;i<urlArr.length;i++){
			//如果在url列表中找到就 隐藏，找不到就显示
			if(currUrl.indexOf(urlArr[i]) > 0)
			{
				$("#popupDiv").hide();
				return;
			}
			else{
				$("#popupDiv").show();
			}
		}
		
		}); 
	
</script>
<body>
<!--底部代码开始-->
<div class="footer-v2" id="footer-v2">

      <div class="footer">

            <!-- Address -->
              <address class="">
              Copyright © 2016 版权所有：中央军委后勤保障部 京ICP备09055519号
              </address>
              <div class="ratio">
               浏览本网主页，建议将电脑显示屏的分辨率调为1024*768
              </div> 
            <!-- End Address -->
       </div>
     
<!--/footer--> 
    </div>
 
    <div class="prompt_tips" id="popupDiv">
     <div class="prompt_top">
                   提示信息
       <span class="close_icon" id="close"></span>
     </div>
      <div class="prompt_main">
                  <a class="red" href="javascript:void(0);" onclick="indexTip('712706E631194823AEF0E77A3FD2807C')">${properties['indexTip2']}</a>
                  <br />
                  <a href="javascript:void(0);" onclick="indexTip('8B96764A39E64F5CADDA0013DE6B4719')">${properties['indexTip']}</a>
      </div>
      <div class="prompt_btn">
        <button class="btn" onclick="indexTip('712706E631194823AEF0E77A3FD2807C')">了解详情</button>
      </div>
</div>

</body>
</html>
