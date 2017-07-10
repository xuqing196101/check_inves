<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.plap.cn/functions" prefix="my" %>  
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]-->
<title>${properties['system.title']}</title>
<head>
<!--导航js-->

<%@ include file="/WEB-INF/view/portal.jsp" %>
<script type="text/javascript"> 
  		$(function(){
  		    /* 导航延迟两秒 */
  		    var _width=$(window).width();
  		    if(_width>972){
  		        var id;
			    var _self;	    
			    $(".dropdown").each(function(){
			    	$(this).hover(function(){
			    		 _self = this;
						id = setTimeout(function(){
		                   $(_self).find(".drop_next").show();
						},200);	    		
					},function(){
						if(id){
						clearTimeout(id);
						}
		               $(_self).find(".drop_next").hide();		
					});
			    })
  		    }
  		    
  		    
		   $("#close").click(function(){
		   		$(".prompt_tips").hide();
		   });
		});
</script>
<script type="text/javascript">
var user = "${sessionScope.loginUser.relName}";
$(function(){
		$.ajax({
			    url: "${pageContext.request.contextPath}/cacheManage/getPVDate.do",
			    type: "POST",
			    dataType: "json",
			    success: function(data) {
			    	if(data.data.loginName != null && data.status==200){
			    	   $("#welcome_words").html(data.data.loginName+"你好，欢迎来到军队采购网！");
			    	   $("#properties").html("<a class=\"web_number\">网站编号：${properties['website.no']} &nbsp;</a>|<a id=\"my\" onclick=\"myInfo()\">我的信息</a><a href=\"${pageContext.request.contextPath}/login/loginOut.html\" id=\"exit\">&nbsp;|&nbsp;退出</a>")
			    	   // 今日访问量
		           $("#pvThisDay").text(data.data.dayNum);
			    	   // 总访问量
		           $("#pvTotal").text(data.data.totalCount);
			    	}else{
			    	   $("#welcome_words").html("你好，欢迎来到军队采购网！<a href=\"${pageContext.request.contextPath}/index/sign.html\" class=\"red\" id=\"red\">【请登录】</a>");
			    	   $("#properties").html("<a class=\"web_number\">网站编号：${properties['website.no']} &nbsp;</a>");
			    	   // 今日访问量
				       $("#pvThisDay").text(data.data.dayNum);
					    // 总访问量
				       $("#pvTotal").text(data.data.totalCount);
			    	}
			    }
		});
		
	
	
	if(user!=null && user!=''){
		$("#welcome_words").html(user+"你好，欢迎来到军队采购网！");
	}else {
	    $("#exit").remove();
	    
	}
	$(".header-v4 .navbar-default .navbar-nav > .other > a").hover(function(){
		$("#firstPage").attr("Class","dropdown shouye_li mega-menu-fullwidth");
	});
	
})

function myInfo(){
   window.location.href="${pageContext.request.contextPath}/login/index.html";
}
/* 
function myInfo(){
	if(user!=null && user!=''){
		window.location.href="${pageContext.request.contextPath}/login/index.html";
	}else{
		window.location.href="${pageContext.request.contextPath}/index/sign.html";
	}
}
 */
function importAdd(){
	if(user==null){
		layer.alert("请先登录",{offset: ['222px', '390px'], shade:0.01});
		return;
	}
	window.location.href="${pageContext.request.contextPath}/importSupplier/register.html";
}
         
</script>
</head>
<body>
<div class="wrapper">
  <div class="head_top col-md-12 col-xs-12 col-sm-12">
   <div class="container p0">
   
    <div class="row">
    <div class="col-md-5 col-sm-5 pl5 pr5 fl" id="welcome">
    	<span id="welcome_words">
    	      你好，欢迎来到军队采购网!
	      <a href="${pageContext.request.contextPath}/index/sign.html" class="red" id="red">【请登录】</a>
    	</span>
       <% if (environment != null && environment.equals("1")){ %>
         <% if(ipAddressType != null && ipAddressType.equals("0")) { %>
           <a href="http://21.100.16.14" class="red">【旧系统登录】</a> 
         <%} %>
	   <% } %>
    </div> 
       <div class="col-md-7 col-sm-7 head_right pr5 pl0 fr" id="properties"> 
    <!-- 根据session判断 -->
         <c:if test="${properties['ipAddressType'] == 0}">
           <a class="web_number">网站编号：${properties['website.no']} &nbsp;</a>
        </c:if>
    	 <c:if test="${not empty loginUser }"> 
 	 		|<a id="my" onclick="myInfo()">我的信息</a>
 	  </c:if>  
    	 <a href="${pageContext.request.contextPath}/login/loginOut.html" id="exit">&nbsp;|&nbsp;退出</a>
	  
	  
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
        <div class="navbar-header col-md-12 col-xs-12 col-sm-12">
          <div class="row margin-bottom-10">
            <div class="col-md-5 col-sm-5 col-xs-12 head_logo">
              <a href="${pageContext.request.contextPath}/">
                 <img alt="Logo" src="${pageContext.request.contextPath}/public/portal/images/logo.png" width="100%" id="logo-header">
              </a>
            </div>
			<!--搜索开始-->
            <div class="col-md-7 col-sm-7 col-xs-12 search_gpgz">
              <div class="col-xs-12 gpgz_moblie">
                <img src="${pageContext.request.contextPath}/public/portal/images/gpgz.png" width="100%">
              </div>
              <div class="col-md-7 col-xs-12 col-sm-7 search-block-v2 col-md-offset-5 col-sm-offset-5 col-xs-offset-0">
                  <form id="form1" accept-charset="UTF-8" action="${pageContext.request.contextPath}/index/solrSearch1.html" method="post">
				    <div style="display:none">
				     <input name="utf8" value="✓" type="hidden">
					</div>
                    <input id="t" name="t" value="search_products" type="hidden">
                    <div class="col-md-12 col-xs-12 col-sm-12 pull-left p0">
                      <div class="search-group">
                        <input class="search-input" id="k" name="condition" value="${oldCondition}" placeholder="搜索" type="text">
                        <span class="input-group-btn">
                          <input class="btn-search" name="commit" value="搜索" type="submit">
                        </span>
                      </div>
                    </div>
                  </form>
              </div>
            </div>
          <!--搜索结束-->
          </div>
		 </div>
      </div>
    </div>
    <div class="container">
       <button data-target=".navbar-responsive-collapse" data-toggle="collapse" class="navbar-toggle" type="button">
          <span class="full-width-menu">全部菜单</span>
          <span class="icon-toggle">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </span>
       </button>
    </div>
    <div style="height: 0px;" aria-expanded="false" class="navbar-collapse navbar-responsive-collapse collapse">
    <div class="container">
      <ul class="nav navbar-nav m-navbar-nav" >
      <!-- 通知 -->
        <li id="firstPage" class="dropdown shouye_li mega-menu-fullwidth">
          <a class=" dropdown-toggle " href="${pageContext.request.contextPath}/"><i class="shouye nav_icon"></i>首 页</a>
        </li>
      <!-- End 通知 -->

      <!-- 公告 -->
        <li class="dropdown other gonggao_li mega-menu-fullwidth">
          <a class="dropdown-toggle " href="javascript:void(0);" data-toggle="dropdown"><i class="gonggao nav_icon"></i>信息公告</a>
	  <!--	信息公告鼠标移动开始   -->
	  <div class="drop_next dropdown-menu" >
	   <div class="magazine-page clear">
	    <div class="col-md-12 col-xs-12 col-sm-12 drop_hover">
	    <div class="drop_main">
	    <div class="col-md-4 col-sm-12 col-xs-12">
	    <div class="headline-v2">
         <h2>采购公告</h2>
        </div>
		<div class="border1 margin-bottom-10 login_box job-content">
		 <h2 class="f17 bgwhite">
		 <ul class="list-unstyled login_tab">
		  <li class="active fl"><a aria-expanded="true" href="#tab-21" data-toggle="tab"> 物资</a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-22" data-toggle="tab"> 工程</a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-23" data-toggle="tab"> 服务</a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-24" data-toggle="tab">进口</a></li>
		 </ul>
		</h2>
		<div class="tab-content buyer_list">
		    <div id="tab-21" class="categories articleover tab-pane fade active in">
             <ul class="p0_10">   
              <c:forEach items="${indexMapper['xinxicaiwuziList']}" var="sl">
                	<%--<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${fn:substring(name,0,23)}...</a></li>
					</c:if>
					<c:if test="${length<=23}">
						--%><li><a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${sl.name}</a></li>
					<%--</c:if>
	          --%></c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectsumBynews.html?id=3&twoid=24&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("物资","utf-8"),"utf-8") %>">更多>></a>
        </div>
		    <div id="tab-22" class="categories articleover tab-pane fade">
             <ul class="p0_10">   
              <c:forEach items="${indexMapper['xinxicaigongchengList']}" var="sl">
                	<%--<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${fn:substring(name,0,23)}...</a></li>
					</c:if>
					<c:if test="${length<=23}">
						--%><li><a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${sl.name}</a></li>
					<%--</c:if>
	          --%></c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectsumBynews.html?id=8&twoid=29&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("工程","utf-8"),"utf-8") %>">更多>></a>
        </div>
		    <div id="tab-23" class="categories articleover tab-pane fade">
             <ul class="p0_10">   
              <c:forEach items="${indexMapper['xinxicaifuwuList']}" var="sl">
                	<%--<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${fn:substring(name,0,23)}...</a></li>
					</c:if>
					<c:if test="${length<=23}">
						--%><li><a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${sl.name}</a></li>
					<%--</c:if>
	          --%></c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectsumBynews.html?id=13&twoid=34&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("服务","utf-8"),"utf-8") %>">更多>></a>
        </div>
		    <div id="tab-24" class="categories articleover tab-pane fade">
             <ul class="p0_10">   
              <c:forEach items="${indexMapper['xinxicaijinkouList']}" var="sl">
                	<%--<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${fn:substring(name,0,23)}...</a></li>
					</c:if>
					<c:if test="${length<=23}">
						--%><li><a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${sl.name}</a></li>
					<%--</c:if>
	          --%></c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectsumBynews.html?id=18&twoid=39&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("进口","utf-8"),"utf-8") %>">更多>></a>
        </div>
	  </div>
	 </div>
	 </div>

	   <div class="col-md-4 col-sm-12 col-xs-12">
	    <div class="headline-v2">
         <h2>中标公示</h2>
        </div>
		<div class="border1 margin-bottom-10 login_box job-content">
		 <h2 class="f17 bgwhite">
		 <ul class="list-unstyled login_tab">
		  <li class="active fl"><a aria-expanded="true" href="#tab-25" data-toggle="tab"> 物资</a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-26" data-toggle="tab"> 工程</a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-27" data-toggle="tab"> 服务</a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-28" data-toggle="tab">进口</a></li>
		 </ul>
		</h2>
		<div class="tab-content  buyer_list">
		    <div id="tab-25" class="categories articleover tab-pane fade active in">
             <ul class="p0_10">   
              <c:forEach items="${indexMapper['xinxizhongwuziList']}" var="sl">
                	<%--<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${fn:substring(name,0,23)}...</a></li>
					</c:if>
					<c:if test="${length<=23}">
						--%><li><a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${sl.name}</a></li>
					<%--</c:if>
	          --%></c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectsumBynews.html?id=46&twoid=67&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("物资","utf-8"),"utf-8") %>">更多>></a>
        </div>
		    <div id="tab-26" class="categories articleover tab-pane fade">
             <ul class="p0_10">   
              <c:forEach items="${indexMapper['xinxizhonggongchengList']}" var="sl">
                	<%--<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${fn:substring(name,0,23)}...</a></li>
					</c:if>
					<c:if test="${length<=23}">
						--%><li><a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${sl.name}</a></li>
					<%--</c:if>
	          --%></c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectsumBynews.html?id=51&twoid=72&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("工程","utf-8"),"utf-8") %>">更多>></a>
        </div>
		    <div id="tab-27" class="categories articleover tab-pane fade">
             <ul class="p0_10">   
              <c:forEach items="${indexMapper['xinxizhongfuwuList']}" var="sl">
                	<%--<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${fn:substring(name,0,23)}...</a></li>
					</c:if>
					<c:if test="${length<=23}">
						--%><li><a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${sl.name}</a></li>
					<%--</c:if>
	          --%></c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectsumBynews.html?id=56&twoid=77&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("服务","utf-8"),"utf-8") %>">更多>></a>
        </div>
		    <div id="tab-28" class="categories articleover tab-pane fade">
             <ul class="p0_10">   
              <c:forEach items="${indexMapper['xinxizhongjinkouList']}" var="sl">
                	<%--<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${fn:substring(name,0,23)}...</a></li>
					</c:if>
					<c:if test="${length<=23}">
						--%><li><a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${sl.name}</a></li>
					<%--</c:if>
	          --%></c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectsumBynews.html?id=61&twoid=82&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("进口","utf-8"),"utf-8") %>">更多>></a>
        </div>
	  </div>
	 </div>
	 </div>
		   <div class="col-md-4 col-sm-12 col-xs-12">
	    <div class="headline-v2">
         <h2>单一来源公示</h2>
        </div>
		<div class="border1 margin-bottom-10 login_box job-content">
		 <h2 class="f17 bgwhite">
		 <ul class="list-unstyled login_tab">
		  <li class="active fl"><a aria-expanded="true" href="#tab-29" data-toggle="tab"> 物资</a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-30" data-toggle="tab"> 工程</a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-31" data-toggle="tab"> 服务</a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-32" data-toggle="tab">进口</a></li>
		 </ul>
		</h2>
		<div class="tab-content  buyer_list">
		    <div id="tab-29" class="categories articleover tab-pane fade active in">
             <ul class="p0_10">   
              <c:forEach items="${indexMapper['xinxidanwuziList']}" var="sl">
                	<%--<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${fn:substring(name,0,23)}...</a></li>
					</c:if>
					<c:if test="${length<=23}">
						--%><li><a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${sl.name}</a></li>
					<%--</c:if>
	          --%></c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectsumBydanNews.html?id=89&twoid=94&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("物资","utf-8"),"utf-8") %>">更多>></a>
        </div>
		    <div id="tab-30" class="categories tab-pane fade">
             <ul class="p0_10">   
              <c:forEach items="${indexMapper['xinxidangongchengList']}" var="sl">
                	<%--<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${fn:substring(name,0,23)}...</a></li>
					</c:if>
					<c:if test="${length<=23}">
						--%><li><a title="{sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${sl.name}</a></li>
					<%--</c:if>
	          --%></c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectsumBydanNews.html?id=90&twoid=95&tal=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("工程","utf-8"),"utf-8") %>">更多>></a>
        </div>
		    <div id="tab-31" class="categories tab-pane fade">
             <ul class="p0_10">   
              <c:forEach items="${indexMapper['xinxidanfuwuList']}" var="sl">
                	<%--<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${fn:substring(name,0,23)}...</a></li>
					</c:if>
					<c:if test="${length<=23}">
						--%><li><a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${sl.name}</a></li>
					<%--</c:if>
	          --%></c:forEach>
          </ul>
         <a class="tab_more" href="${pageContext.request.contextPath}/index/selectsumBydanNews.html?id=91&twoid=96&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("服务","utf-8"),"utf-8") %>">更多>></a>
        </div>
		    <div id="tab-32" class="categories tab-pane fade">
             <ul class="p0_10">   
              <c:forEach items="${indexMapper['xinxidanjinkouList']}" var="sl">
                	<%--<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${fn:substring(name,0,23)}...</a></li>
					</c:if>
					<c:if test="${length<=23}">
						--%><li><a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${sl.name}</a></li>
					<%--</c:if>
	          --%></c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectsumBydanNews.html?id=92&twoid=97&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("进口","utf-8"),"utf-8") %>">更多>></a>
        </div>
	  </div>
	 </div>
	</div>
   </div>
   </div>
  </div>
 </div>
        </li>
      <!-- End 公告 -->

      <!-- 公示 -->
        <li class="dropdown other gongshi_li mega-menu-fullwidth" >
          <a data-toggle="dropdown" class="dropdown-toggle " href="javascript:void(0);"><i class="gongshi nav_icon"></i>网上采购</a>
<!--	网上采购鼠标移动开始   -->
		  <div class="drop_next dropdown-menu" >
	   <div class="magazine-page clear">
      <div class="col-md-12 col-sm-12 col-xs-12 drop_hover">
	   <div class="drop_main ">
	    <div class="col-md-4 col-sm-6 col-xs-12 mt25" id="drop-1">
		  <div class="ywbl_01 col-md-6 col-sm-6 col-xs-6">
		   <% if (environment != null && environment.equals("1")){ %>
             <% if(ipAddressType != null && ipAddressType.equals("0")) { %>
               <a href="http://21.100.16.6" class="wssc">
             <%} %>
             <% if(ipAddressType != null && ipAddressType.equals("1")) { %>
               <a href="http://mall.plap.cn" class="wssc">
             <%} %>
	       <% } %>
	       <% if (environment != null && environment.equals("0")){ %>
               <a href="javascript:void(0);" class="wssc">
           <%} %>
           	<i></i>
            <span>网上商城</span> 
		   </a>
	      </div>
		  <div class="ywbl_01 col-md-6 col-sm-6 col-xs-6">
	       <a href="${pageContext.request.contextPath }/index/index_productList.html" class="dxcpjj">
            <i></i>
            <span>定型产品</span> 
		   </a>
	     </div>
		 <div class="ywbl_01 col-md-6 col-sm-6 col-xs-6">
	      <a href="#" class="ypcg">
           <i></i>
           <span>药品采购</span> 
		  </a>
	     </div>
		 <div class="ywbl_01 col-md-6 col-sm-6 col-xs-6">
	      <a href="#" class="fwcg">
           <i></i>
           <span>服务采购</span> 
		  </a>
	   </div>
		</div>
		<div class="col-md-6 col-sm-6 col-xs-12 mt10">
		<div class="margin-bottom-10 login_box job-content">
		 <h2 class="f17 bgwhite">
		 <ul class="list-unstyled login_tab">
		  <li class="active fl"><a aria-expanded="true" href="#tab-33" data-toggle="tab"> 需求公告 </a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-34" data-toggle="tab"> 成交公告</a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-35" data-toggle="tab"> 废标公告</a></li>
		 </ul>
		</h2>
		<div class="tab-content  buyer_list">
		    <div id="tab-33" class="categories articleover tab-pane fade active in">
             <ul class="p0_10">   
              <c:forEach items="${indexMapper['select103List']}" var="sl">
                	<%--<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>37}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${fn:substring(name,0,37)}...</a></li>
					</c:if>
					<c:if test="${length<=37}">
						--%><li><a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${sl.name}</a></li>
					<%--</c:if>
	          --%></c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=103">更多>></a>
        </div>
		    <div id="tab-34" class="categories tab-pane fade">
             <ul class="p0_10">   
              <c:forEach items="${indexMapper['select104List']}" var="sl">
                	<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>37}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${fn:substring(name,0,37)}...</a></li>
					</c:if>
					<c:if test="${length<=37}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${name}</a></li>
					</c:if>
	          </c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=104">更多>></a>
        </div>
		    <div id="tab-35" class="categories tab-pane fade">
             <ul class="p0_10">   
              <c:forEach items="${indexMapper['select105List']}" var="sl">
                	<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>37}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${fn:substring(name,0,37)}...</a></li>
					</c:if>
					<c:if test="${length<=37}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${name}</a></li>
					</c:if>
	          </c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=105">更多>></a>
        </div>
		</div>
		</div>
	  </div>
	 </div>
	</div>
	</div>
	</div>
	
        </li>
      <!-- End 公示 -->

    <!-- 供应商 -->
    <li class="dropdown other zhuanjia_li mega-menu-fullwidth">
        <a class="dropdown-toggle" data-toggle="dropdown" href="javascript:void(0);" ><i class="zhuanjia nav_icon"></i>供应商</a>
        
        <!-- 供应商鼠标移动开始 -->
        <div class="drop_next dropdown-menu">
        <div class="magazine-page clear">
        <div class="col-md-12 col-sm-12 col-xs-12 drop_hover">
        <div class="drop_main">
        <%-- <div class="col-md-2 col-sm-2 col-xs-2 mt20 supp_login "> --%>
            <%-- <a href="${pageContext.request.contextPath}/supplier/registration_page.html" > --%>
                <%-- <% if (ipAddressType != null && ipAddressType.equals("1")){ %>
                <a href="${pageContext.request.contextPath}/supplier/registration_page.html" class="" >
                <% } %>
                <% if (ipAddressType != null && ipAddressType.equals("0")){ %>
                <a onclick="supplierRegisterTip();" class="">
                <% } %>
                	供应商注册
                <i></i>
            </a>
        </div> --%>
        
        <!-- 拟入库公示 -->
        <div class="col-md-8 col-sm-8 col-xs-10 mt10">
            <div class="headline-v2 m-headline-v2">
                <h2 class="fl">拟入库公示&nbsp;<span class="red">（如果对公示内容有异议，请拨打举报电话：010-66880147）</span></h2>
                <a href="${pageContext.request.contextPath}/index/indexSupPublicity.html" class="news_more">更多&gt;&gt;</a>
                <div class="clear"></div>
            </div>
            
            <div class="job-content col-md-12 col-sm-12 col-xs-12 p0">
            <div class="categories">
                <c:choose>
                <c:when test="${!empty my:getPublicitySupplier()}">
                <div class="m_nav_scroll_head">
                <table class="table table-bordered mb0">
                    <tbody>
                        <tr>
                            <td class="tc info" width="15%">供应商名称</td>
                            <td class="tc info" width="12%">类型</td>
                            <td class="tc info" width="12%">企业性质</td>
                            <td class="tc info" width="12%">初审单位</td>
                            <td class="tc info" width="34%">审核结果</td>
                            <td class="tc info" width="15%">公示时间</td>
                        </tr>
                    </tbody>
                </table>
                </div>
                <div class="m_nav_scroll">
                <input type="hidden" value="0" name="ishover">
                <ul class="mns_bxslider">
                <li>
                <table class="table table-bordered mb0">
                    <tbody>
                    <c:forEach items="${my:getPublicitySupplier()}" var="item" begin="0" end="4" step="1" varStatus="status">
                        <c:set value="${item.supplierName}" var="supplierName"></c:set>
                        <c:set value="${item.supplierTypeNames}" var="supplierTypeNames"></c:set>
                        <c:set value="${item.businessNature}" var="businessNature"></c:set>
                        <tr>
                            <td width="15%">
                                <c:choose>
                                    <c:when test="${fn:length(supplierName) > 4}">
                                        <c:out value="${fn:substring(supplierName, 0, 4)}.." />
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${supplierName}"></c:out>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="tc" width="12%">
                                <c:choose>
                                    <c:when test="${fn:length(supplierTypeNames) > 4}">
                                        <c:out value="${fn:substring(supplierTypeNames, 0, 4)}..." />
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${supplierTypeNames}"></c:out>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="tc" width="12%">
                                <c:choose>
                                    <c:when test="${fn:length(businessNature) > 2}">
                                        <c:out value="${fn:substring(businessNature, 0, 2)}..." />
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${businessNature}"></c:out>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="tc" width="12%">${ item.orgName }</td>
                            <td class="tl" width="34%">
                                同意入库，选择了${ item.passCateCount }个产品类别，通过了${ item.passCateCount - item.noPassCateCount }个产品类别
                            </td>
                            <td class="tl" width="15%">
                                <fmt:formatDate value="${item.updatedAt}" pattern="yyyy-MM-dd"></fmt:formatDate>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                </li>
                </ul>
                </div>
                </c:when>
                <c:otherwise>
                <div class="tc">暂无数据</div>
                </c:otherwise>
                </c:choose>
            </div>
            </div>
        </div>
        <!-- End 拟入库公示 -->
        
        <div class="login_box job-content col-md-4 col-sm-4 col-xs-12 mt10">
            <h2 class="f17 bgwhite">
            <ul class="list-unstyled login_tab p0">
                <li class="fl active"><a aria-expanded="true" href="#tab-36" data-toggle="tab">入库名单</a></li>
                <li class="fl"><a aria-expanded="true" href="#tab-37" data-toggle="tab">诚信记录</a></li>
                <li class="fl"><a aria-expanded="false" href="#tab-38" data-toggle="tab">处罚公告</a></li>
                <!-- <li class="fl"><a aria-expanded="false" href="#tab-38" data-toggle="tab">地方处罚公告</a></li> -->
                <li class="fl"><a aria-expanded="false" href="#tab-gyshmd" data-toggle="tab">黑名单</a></li>
            </ul>
            </h2>
            
            <div class="tab-content buyer_list m_buyer_list">
                <!-- 诚信记录 -->
                <div id="tab-37" class="categories tab-pane fade">
                <%-- <ul class="p0_10 list-unstyled">
                <table class="table table-bordered " >
                <thead>
                <tr>
                <th class="tc info" width="50%">供应商名称</th>
                <th class="tc info" width="20%">企业等级</th>
                <th class="tc info" width="10%">分数</th>
                <th class="tc info" width="20%">企业性质</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${my:getSupplierCreditRecord()['supplierCreditList']}" var="supplier" begin="0" end="3" step="1" varStatus="status">
                <tr>
                <td class="tc">${supplier.supplierName}</td>
                <td class="tc">${supplier.level}</td>
                <td class="tc">${supplier.score}</td>
                <td class="tc">
                <c:forEach items="${my:getSupplierCreditRecord()['data']}" var="dic">
                <c:if test="${supplier.businessType==dic.id}">
                	${dic.name }									
                </c:if>
                </c:forEach>
                </td>
                </tr>
                </c:forEach>
                </tbody>
                </table>
                </ul> 
                <a class="tab_more" href="${pageContext.request.contextPath}/supplier_level/indexList.html">更多>></a>--%>
                <a class="fr" href="javascript:void(0)">更多&gt;&gt;</a>
                </div>
                <!-- End 诚信记录 -->
            
                <!-- 处罚公告 -->
                <div id="tab-38" class="categories tab-pane fade">
                <ul class="p0_10">
                <c:forEach items="${indexMapper['article116List']}" var="sl">
                <c:set value="${sl.name}" var="name"></c:set>
                <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>25}">
                    <li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${fn:substring(name,0,25)}...</a></li>
                    </c:if>
                    
                    <c:if test="${length<=25}">
                    <li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${name}</a></li>
                    </c:if>
                </c:forEach>     
                </ul>
                <a class="fr" href="${pageContext.request.contextPath}/index/supplierPunishment.html">更多>></a>
                </div>
                <!-- End 处罚公告 -->
                
                <!-- 供应商地方处罚公告 -->
                <%-- <div id="tab-38" class="categories tab-pane fade">
                <ul class="p0_10">
                <c:forEach items="${indexMapper['article117List']}" var="sl">
                <c:set value="${sl.name}" var="name"></c:set>
                <c:set value="${fn:length(name)}" var="length"></c:set>
                <c:if test="${length>25}">
                <li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${fn:substring(name,0,25)}...</a></li>
                </c:if>
                <c:if test="${length<=25}">
                <li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${name}</a></li>
                </c:if>
                </c:forEach>   
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=117">更多>></a>
                </div> --%>
                
                <!-- 专家黑名单 -->
                <div id="tab-gyshmd" class="categories tab-pane fade">
                <ul class="categories li_square col-md-12 col-sm-12 col-xs-12 p0 list_new">
                <c:forEach items="${indexMapper['supplierBlackList']}" var="sl">
                <c:set value="${sl.supplierName}" var="name"></c:set>
                <c:set value="${fn:length(name)}" var="length"></c:set>
                <c:set value="${sl.supplierName}" var="shortName"/>
                    <c:if test="${length>15}">
                        <c:set value="${fn:substring(name,0,15)}..." var="shortName"/>
                    </c:if>
                    <li>
                        <%-- <a class="col-md-8 col-sm-7 col-xs-12" title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="f18 mr5 fl">·</span>${shortName}</a> --%>
                        <a class="col-md-8 col-sm-7 col-xs-12" title="${name}" href="javascript:;"><span class="f18 mr5 fl">·</span>${shortName}</a>
                        <span class="hex pull-right col-md-4 col-sm-5 col-xs-12">
                            <c:if test="${sl.punishType == 0}">警告</c:if>
                            <c:if test="${sl.punishType == 1}">不得参与采购活动</c:if>
                        </span>
                    </li>
                </c:forEach>
                </ul>
                <a class="fr" href="${pageContext.request.contextPath}/index/supplierBlackList.html">更多&gt;&gt;</a>
                </div>
                <!-- End 专家黑名单 -->
            </div>
        </div>
        
        </div>
        </div>
        </div>
        </div>
        <!-- 供应商鼠标移动结束 -->
    </li>
    <!-- End 供应商 -->

   	<!-- 专家评审 -->
    <li class="dropdown other tousu_li mega-menu-fullwidth">
		<a class="dropdown-toggle " data-toggle="dropdown" href="javascript:void(0);"><i class="tousu nav_icon"></i>评审专家</a>
		<!-- 评审鼠标移动开始 -->
		<div class="drop_next dropdown-menu">
		<div class="magazine-page clear">
		<div class="col-md-12 col-sm-12 col-xs-12 drop_hover" id="drop-4">
		<div class="drop_main">
			<!--<div class="col-md-2 col-xs-2 col-sm-2 mt20 supp_login">-->
				<%-- <% if (environment != null && environment.equals("0")){ %>
				<a href="${pageContext.request.contextPath}/expert/toRegisterNotice.html" class="">
				<% } %>
				<% if (environment != null && environment.equals("1")){ %>
				<a onclick="expertRegisterTip();" class="col-md-offset-4 col-sm-offset-0 col-xs-offset-0">
				<% } %> --%>
				<!--<a href="${pageContext.request.contextPath}/expert/toRegisterNotice.html">评审专家注册<i></i></a>	  
			</div>-->
			
			<div class="col-md-8 col-sm-8 col-xs-10 mt10">
				<div class="headline-v2 m-headline-v2">
					<h2 class="fl">拟入库公示&nbsp;<span class="red">（如果对公示内容有异议，请拨打举报电话：010-66880147）</span></h2>
					<a href="${pageContext.request.contextPath}/index/indexExpPublicity.html" class="news_more">更多&gt;&gt;</a>
					<div class="clear"></div>
				</div>
				
				<div class="job-content col-md-12 col-sm-12 col-xs-12 p0">
				<div class="categories zhuanjia_list">
				<c:choose>
				<c:when test="${!empty my:getPublicityExpert()}">
				<table class="table table-bordered mb0">
					<thead>
						<tr>
							<th class="tc info" width="25%">专家名称</th>
							<th class="tc info" width="15%">类别</th>
							<th class="tc info" width="15%">初审单位</th>
							<th class="tc info" width="30%">审核结果</th>
							<th class="tc info" width="15%">公示时间</th>
						</tr>
					</thead>
				</table>
				<div class="m_nav_scroll">
                <input type="hidden" value="0" name="ishover">
                <ul class="mns_bxslider">
                <li>
                <table class="table table-bordered mb0">
					<tbody>
					<c:forEach items="${my:getPublicityExpert()}" var="item" begin="0" end="4" step="1" varStatus="status"> 
					<tr>
						<td width="25%">${ item.relName }</td>
						<td class="tc" width="15%">${ item.expertsTypeId }</td>
						<td class="tc" width="15%">${ item.orgName }</td>
						<td class="tl" width="30%">
                            同意入库，选择了${ item.passCateCount }个小类，通过了${ item.passCateCount - item.noPassCateCount }个小类
                        </td>
                        <td class="tl" width="25%">
                            <fmt:formatDate value="${item.updatedAt}" pattern="yyyy-MM-dd"></fmt:formatDate>
                        </td>
					</tr>
					</c:forEach> 
					</tbody>
				</table>
				</li>
				</ul>
				</div>
				</c:when>
				<c:otherwise>
				<div class="tc">暂无数据</div>
				</c:otherwise>
				</c:choose>
				</div>
				</div>
			</div>
			
			<div class="login_box job-content col-md-4 col-sm-4 col-xs-12 mt10">
			<h2 class="f17 bgwhite">
			<ul class="list-unstyled login_tab">
			<li class="fl active"><a aria-expanded="true" href="#tab-39" data-toggle="tab">入库名单</a></li>
			<li class="fl"><a aria-expanded="true" href="#tab-40" data-toggle="tab">诚信记录</a></li>
			<li class="fl"><a aria-expanded="false" href="#tab-41" data-toggle="tab">处罚公告</a></li>
			<li class="fl"><a aria-expanded="false" href="#tab-zjhmd" data-toggle="tab">黑名单</a></li>
			</ul>
			</h2>
			<div class="tab-content buyer_list m_buyer_list">
				<div id="tab-39" class="categories tab-pane fade active in">
					
					<a class="fr" href="javascript:void(0)">更多&gt;&gt;</a>
				</div>
				
				<div id="tab-40" class="categories tab-pane fade">
					<ul class="p0_10">   
					</ul>
					<a class="fr" href="javascript:void(0)">更多&gt;&gt;</a>
				</div>
				
				<div id="tab-41" class="categories tab-pane fade">
				<ul class="p0_10">   
					<c:forEach items="${indexMapper['article115List']}" var="sl">
					<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>25}">
					<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${fn:substring(name,0,25)}...</a></li>
					</c:if>
					<c:if test="${length<=25}">
					<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${name}</a></li>
					</c:if>
					</c:forEach>
				</ul>
				<a class="fr" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=115">更多&gt;&gt;</a>
				</div>
				
				<div id="tab-zjhmd" class="categories tab-pane fade">
				<ul class="categories li_square col-md-12 col-sm-12 col-xs-12 p0 list_new">
					<c:forEach items="${indexMapper['expertBlackList']}" var="sl">
					<c:set value="${sl.relName}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:set value="${sl.relName}" var="shortName"/>
					<c:if test="${length>15}">
					<c:set value="${fn:substring(name,0,15)}..." var="shortName"/>
					</c:if>
					<li>
					<%-- <a class="col-md-8 col-sm-7 col-xs-12" title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="f18 mr5 fl">·</span>${shortName}</a> --%>
					<a class="col-md-8 col-sm-7 col-xs-12" title="${name}" href="javascript:;"><span class="f18 mr5 fl">·</span>${shortName}</a>
					<span class="hex pull-right col-md-4 col-sm-5 col-xs-12">
					<c:if test="${sl.punishType == 1}">警告</c:if>
					<c:if test="${sl.punishType == 2}">严重警告</c:if>
					<c:if test="${sl.punishType == 3}">取消资格</c:if>
					</span>
					</li>
					</c:forEach>   
				</ul>
				<a class="fr" href="${pageContext.request.contextPath}/index/expertBlackList.html">更多&gt;&gt;</a>
				</div>
			</div>
			</div>
	  
	</div>
   	</div>
   	</div>
   	</div>
	</li>
	<!-- End 评审专家 ->

      <!-- 法规 -->
        <li class="dropdown other cgfw_li mega-menu-fullwidth">
          <a class="dropdown-toggle " data-toggle="dropdown" href="javascript:void(0);"><i class="cgfw nav_icon"></i>采购服务</a>
	<!--采购服务鼠标移动开始-->
		  <div class="drop_next dropdown-menu" >
	   <div class="magazine-page clear">

	<div class="col-md-12 col-xs-12 col-sm-12 drop_hover">
	 <div class="drop_main supp_service">
	 <div class="service_btns col-md-2 col-sm-2 col-xs-3 ">
	  <a href="${pageContext.request.contextPath}/categorys/categoryList.html">
	    <div class="col-md-12 col-xs-12 col-sm-12 tc service_btns_pic cpml">
	    	<img src="${pageContext.request.contextPath}/public/portal/images/cpml.jpg" width="80%" height="80%;"/>
	    </div>
		<div class="tc f18 mt20 pt10 clear">产品目录</div>
	  </a>
	 </div>
	 <div class="service_btns col-md-2 col-sm-2 col-xs-3 ">
	  <a href="${pageContext.request.contextPath}/categorys/parameterList.html">
	    <div class="col-md-12 col-xs-12 col-sm-12 tc service_btns_pic jscsk">
	    	<img src="${pageContext.request.contextPath}/public/portal/images/jscsk.jpg" width="80%" height="80%;"/>
	    </div>
		<div class="tc f18 mt20 pt10 clear">技术参数库</div>
	  </a>
	 </div>
	 <div class="service_btns col-md-2 col-sm-2 col-xs-3 ">
	  <a href="${pageContext.request.contextPath}/park/getIndex.html">
	    <div class="col-md-12 col-xs-12 col-sm-12 tc service_btns_pic cglt">
	    	<img src="${pageContext.request.contextPath}/public/portal/images/cglt.jpg" width="80%" height="80%;"/>
	    </div>
		<div class="tc f18 mt20 pt10 clear">采购论坛</div>
	  </a>
	 </div>
	 <div class="service_btns col-md-2 col-sm-2 col-xs-3 ">
	  <%-- <% if (ipAddressType != null && ipAddressType.equals("0")){ %> --%>
	       <a href="${pageContext.request.contextPath }/dataDownload/getIndexList.html">
	 <%--  <% } %> --%>
	  <%-- <% if (ipAddressType != null && ipAddressType.equals("1")){ %>
	      <a href="javascript:void(0);">
	  <% } %> --%>
	    <div class="col-md-12 col-xs-12 col-sm-12 tc service_btns_pic zlxz">
	    	<img src="${pageContext.request.contextPath}/public/portal/images/new_zlxz.jpg" width="80%" height="80%;"/>
	    </div>
		<div class="tc f18 mt20 pt10 clear">资料下载</div>
	  </a>
	 </div>
	  <div class="service_btns col-md-2 col-sm-2 col-xs-3 ">
	  <%-- <% if (ipAddressType != null && ipAddressType.equals("0")){ %>
	  <% } %>
	  <% if (ipAddressType != null && ipAddressType.equals("1")){ %>
	      <a href="javascript:void(0);">
	  <% } %> --%>
        <a href="${pageContext.request.contextPath }/templateDownload/getIndexList.html">
	    <div class="col-md-12 col-xs-12 col-sm-12 tc service_btns_pic cgmb">
	    	<img src="${pageContext.request.contextPath}/public/portal/images/mb_pic.png" width="80%" height="80%;"/>
	    </div>
		<div class="tc f18 mt20 pt10 clear">采购模板</div>
	  </a>
	 </div>
	 <div class="service_btns col-md-2 col-sm-2 col-xs-3 ">
	  <a href="">
	    <div class="col-md-12 col-xs-12 col-sm-12 tc service_btns_pic yjfk">
	    	<img src="${pageContext.request.contextPath}/public/portal/images/yjfk.jpg" width="80%" height="80%;"/> 
	    </div>
		<div class="tc f18 mt20 pt10 clear">意见反馈</div>
	  </a>
	 </div>
	 <div class="service_btns col-md-2 col-sm-2 col-xs-3 ">
	  <a href="${pageContext.request.contextPath }/index/index_hotLineList.html">
	    <div class="col-md-12 col-xs-12 col-sm-12 tc service_btns_pic shfw">
	    	<img src="${pageContext.request.contextPath}/public/portal/images/new_shfw.jpg" width="80%" height="80%;"/>
	    </div>
		<div class="tc f18 mt20 pt10 clear">服务热线</div>
	  </a>
	 </div>
	</div>
    </div>
	</div>
   </div>
   
        </li>
      <!-- End 法规 -->

        <li class="dropdown other luntan_li mega-menu-fullwidth">
          <a href="javascript:void(0);" data-toggle="dropdown" class="dropdown-toggle " ><i class="luntan nav_icon"></i>投诉处理</a>
	<!-- 投诉处理鼠标移动开始-->
	<div class="drop_next dropdown-menu" >
	 <div class="magazine-page clear">
     <div class="col-md-12 col-xs-12 col-sm-12 col-xs-12 col-sm-12 drop_hover"  id="drop-6">
	  <div class="drop_main">
	   <div class="col-md-4 col-sm-4 col-xs-12 mt20">
	    <a href="${pageContext.request.contextPath}/onlineComplaints/index_add.html">
	     <div class="col-md-12 col-xs-12 col-sm-12 tc p0">
	     <img src="${pageContext.request.contextPath}/public/portal/images/tou_pic.jpg"/></div>
		 <div class="col-md-12 col-xs-12 col-sm-12 f22 tc mt20 p0">网上投诉</div>
		</a>
	  </div>
	  <div class="col-md-8 col-sm-8 col-xs-12 mt10">
	  	<div class="margin-bottom-10 login_box job-content col-md-12 col-sm-12 col-xs-12">
		 <h2 class="f17 bgwhite">
		 <ul class="list-unstyled login_tab fl">
		  <li class="active fl"><a>投诉处理公告</a></li>
		 </ul>
		 <a class="news_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=112">更多>></a>
		</h2>
		<div class="tab-content  buyer_list">
		    <div id="" class="categories tab-pane fade active in">
             <ul class="p0_10">   
              <c:forEach items="${indexMapper['select112List']}" var="sl">
                	<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>30}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${fn:substring(name,0,30)}...</a></li>
					</c:if>
					<c:if test="${length<=30}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${name}</a></li>
					</c:if>
	          </c:forEach>
          </ul>  
        </div>
		</div>
	 </div>
	 </div>
	</div>
	</div>
	</div> 
   </div>
   
        </li>
        <li class="dropdown other fagui_li mega-menu-fullwidth">
          <a class="dropdown-toggle " data-toggle="dropdown" href="javascript:void(0);"><i class="fagui nav_icon"></i>采购法规</a>
    <!--采购法规鼠标动开始-->
	<div class="drop_next dropdown-menu" >
	   <div class="magazine-page clear">
      <div class="col-md-12 col-xs-12 col-sm-12 drop_hover"  id="drop-7">
	   <div class="drop_main">
		<div class="margin-bottom-10 login_box job-content col-md-5 col-sm-12 col-xs-12 mt10">
		 <h2 class="f17 bgwhite">
		 <ul class="list-unstyled login_tab">
		  <li class="active fl"><a aria-expanded="true" href="#tab-41" data-toggle="tab">国家采购法规</a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-42" data-toggle="tab"> 军队采购法规</a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-43" data-toggle="tab"> 重要业务通知</a></li>
		 </ul>
		</h2>
		<div class="tab-content buyer_list">
		    <div id="tab-41" class="categories tab-pane fade active in">
             <ul class="p0_10">   
              <c:forEach items="${indexMapper['select107List']}" var="sl">
                	<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>29}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${fn:substring(name,0,29)}...</a></li>
					</c:if>
					<c:if test="${length<=29}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${name}</a></li>
					</c:if>
	          </c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=107">更多>></a>
        </div>
		    <div id="tab-42" class="categories tab-pane fade">
             <ul class="p0_10">   
              <c:forEach items="${indexMapper['select108List']}" var="sl">
                	<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>29}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${fn:substring(name,0,29)}...</a></li>
					</c:if>
					<c:if test="${length<=29}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${name}</a></li>
					</c:if>
	          </c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=108">更多>></a>
        </div>
		    <div id="tab-43" class="categories tab-pane fade">
             <ul class="p0_10">   
              <c:forEach items="${indexMapper['select109List']}" var="sl">
                	<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>29}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${fn:substring(name,0,29)}...</a></li>
					</c:if>
					<c:if test="${length<=29}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${name}</a></li>
					</c:if>
	          </c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=109">更多>></a>
        </div>
		</div>
		</div>
      <%-- <div class="col-md-7 col-sm-7 col-xs-12 mt20">
	  <div class="col-md-12 col-xs-12 col-sm-12">
	  <div class="col-md-6 col-sm-6 col-xs-12">
	    <div class="col-md-12 col-xs-12 col-sm-12 p0 fg_rule">
		 <img src="${pageContext.request.contextPath}/public/portal/images/fg_01.jpg" class="fl" width="100%" />
		</div>
	  </div>
	  <div class="col-md-6 col-sm-6 col-xs-12">
		 <a href="">主题：2016年9月30日烈士纪念日</a>
	  </div>
	  </div>
	  <div class="col-md-12 col-xs-12 col-sm-12 mt20">
         <div class="job-content col-md-12 col-xs-12 col-sm-12">
		    <div class="categories">
             <ul class="p0_10 list-unstyled">   
              <li></li>
			 </ul>
			</div>
		  </div>
	   </div>
	  </div> --%>  
	  </div>
	 </div>
	</div>
	</div>
   </li>
      </ul>
      </div>
      </div>
      </div>
      </div>
     <!-- 首页APP下载二维码 -->
     <% if (environment != null && environment.equals("0")){ %>
         <div class="m_app_code" id="m_app_code">
		<span>A<br>P<br>P<br>下<br>载<br>二<br>维<br>码</span>
		<div class="mac_img"><img src="${pageContext.request.contextPath}/public/portal/images/AppDownload.png" alt=""></div>
	</div>
	
	<script>
		$(function() {
			$('#m_app_code span').on('click', function() {
				if ($(this).parent().hasClass('hover')) {
					$(this).parent().removeClass('hover');
				} else {
					$(this).parent().addClass('hover');
				}
			});
		});
	</script>
	<% } %>
	
	<!-- 供应商和专家下拉菜单列表滚动效果 -->
	<script>
		var m_nav_scroll = []; // 设置保存滚动插件的数组
		
		// 循环所有插件并初始化
		$('.mns_bxslider').each(function (index) {
			m_nav_scroll[index] = $(this).bxSlider({
				mode: 'vertical',   // 垂直模式
				minSlides: 1,       // 最小显示个数
				maxSlides: 1,       // 最大显示个数
				ticker: true,
				tickerHover: true,  // 鼠标移动上停止滚动
				autoHover: true,    // 鼠标移动上停止滚动
				speed: 5000         // 滚动速度
			});
		});
		
		// 设置下拉菜单出现后重置插件，防止display: none时候插件失效
		$('.navbar-nav > li').mouseenter(function () {
			var ishover = parseInt($(this).find('input[name=ishover]').val());  // 下拉菜单是否出现
			// 如果下拉菜单已经出现防止多次重置插件导致滚动列表抖动
			if (ishover == 0) {
				$(this).find('input[name=ishover]').val(1);
				// 加入延时防止下拉菜单还未出现程序已经执行的bug
				setTimeout(function () {
					for (var i in m_nav_scroll) {
						m_nav_scroll[i].reloadSlider();
					}
				}, 500);
			}
		});
		// 鼠标移除注销插件并初始化判断参数，以便下次打开重新生成
		$('.navbar-nav > li').mouseleave(function () {
			$(this).find('input[name=ishover]').val(0);
			for (var i in m_nav_scroll) {
				m_nav_scroll[i].destroySlider();
			}
		});
	</script>
	<!-- End 供应商和专家下拉菜单列表滚动效果 -->

</body>
</html>
