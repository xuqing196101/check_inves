<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
		//供应商注冊頁面
		urlArr[3]="supplier_item/";
		
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
      <c:choose> 
             <c:when test="${fn:length(properties['indexTip9']) > 13}"> 
               <a class="red" href="javascript:void(0);" title="${properties['indexTip9']}" onclick="indexTip('CDF0D498CA134BE8BE2BFC093FA0898F')">${fn:substring(properties['indexTip9'], 0, 13)}...</a>
             </c:when> 
             <c:otherwise> 
              <a class="red" href="javascript:void(0);" title="${properties['indexTip9']}" onclick="indexTip('CDF0D498CA134BE8BE2BFC093FA0898F')">${properties['indexTip9']}</a>
             </c:otherwise>
            </c:choose>
           <br />
      <c:choose> 
             <c:when test="${fn:length(properties['indexTip8']) > 13}"> 
               <a class="red" href="javascript:void(0);" title="${properties['indexTip8']}" onclick="indexTip('0EE315DAC0C0424DBEEA2FDF717ED690')">${fn:substring(properties['indexTip8'], 0, 13)}...</a>
             </c:when> 
             <c:otherwise> 
              <a class="red" href="javascript:void(0);" title="${properties['indexTip8']}" onclick="indexTip('0EE315DAC0C0424DBEEA2FDF717ED690')">${properties['indexTip8']}</a>
             </c:otherwise>
            </c:choose>
           <br />
       <c:choose> 
             <c:when test="${fn:length(properties['indexTip7']) > 13}"> 
               <a class="" href="javascript:void(0);" title="${properties['indexTip7']}" onclick="indexTip('571C7352DA5E422DA44C6BF06EC072A7')">${fn:substring(properties['indexTip7'], 0, 13)}...</a>
             </c:when> 
             <c:otherwise> 
              <a class="" href="javascript:void(0);" title="${properties['indexTip7']}" onclick="indexTip('571C7352DA5E422DA44C6BF06EC072A7')">${properties['indexTip7']}</a>
             </c:otherwise>
            </c:choose>
           <br />
            <c:choose> 
             <c:when test="${fn:length(properties['indexTip6']) > 13}"> 
               <a class="" href="javascript:void(0);" title="${properties['indexTip6']}" onclick="indexTip('BDEB4C3383F5400886A3A451BAF4E889')">${fn:substring(properties['indexTip6'], 0, 13)}...</a>
             </c:when> 
             <c:otherwise> 
              <a class="" href="javascript:void(0);" title="${properties['indexTip6']}" onclick="indexTip('BDEB4C3383F5400886A3A451BAF4E889')">${properties['indexTip6']}</a>
             </c:otherwise>
            </c:choose>
           <br />
		   <c:choose> 
		     <c:when test="${fn:length(properties['indexTip5']) > 13}"> 
		       <a class="" href="javascript:void(0);" title="${properties['indexTip5']}" onclick="indexTip('581DD6335F95404BBB325760B86E6E82')">${fn:substring(properties['indexTip5'], 0, 13)}...</a>
		     </c:when> 
		     <c:otherwise> 
		      <a class="" href="javascript:void(0);" title="${properties['indexTip5']}" onclick="indexTip('581DD6335F95404BBB325760B86E6E82')">${properties['indexTip5']}</a>
		     </c:otherwise>
		    </c:choose>
           <br />
           <a class="" href="javascript:void(0);" onclick="indexTip('06615ACC3B38463CA76AA93BFF2A99B2')">${properties['indexTip4']}</a>
           <br />
           <a  href="javascript:void(0);" onclick="indexTip('7C5808A4C4B34C9CA86F661AF6B6E989')">${properties['indexTip3']}</a>
           <br />
           <a  href="javascript:void(0);" onclick="indexTip('712706E631194823AEF0E77A3FD2807C')">${properties['indexTip2']}</a>
           <br />
           <a href="javascript:void(0);" onclick="indexTip('8B96764A39E64F5CADDA0013DE6B4719')">${properties['indexTip']}</a>
      </div>
      <div class="prompt_btn">
        <button class="btn" onclick="indexTip('712706E631194823AEF0E77A3FD2807C')">了解详情</button>
      </div>
</div>

</body>
</html>
