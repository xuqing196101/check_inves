<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]-->
<head>
<!--导航js-->

<%@ include file="/WEB-INF/view/portal.jsp" %>
<script type="text/javascript">
var user = "${sessionScope.loginUser.relName}";
$(function(){
	if(user!=null && user!=''){
		$("#welcome").html(user+"你好，欢迎来到军队采购网！");
	}else {
	    $("#exit").remove();
	    
	}
	$(".header-v4 .navbar-default .navbar-nav > .other > a").hover(function(){
		$("#firstPage").attr("Class","dropdown shouye_li mega-menu-fullwidth");
	});
	
})

function myInfo(){
	if(user!=null && user!=''){
		window.location.href="${pageContext.request.contextPath}/login/index.html";
	}else{
		window.location.href="${pageContext.request.contextPath}/index/sign.html";
	}
}

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
    <div class="col-md-5 col-sm-5 pl5 pr5 fl" id="welcome">你好，欢迎来到军队采购网！
      <a href="${pageContext.request.contextPath}/index/sign.html" class="red">【请登录】</a>
       <% if (environment != null && environment.equals("1")){ %>
         <% if(ipAddressType != null && ipAddressType.equals("0")) { %>
           <a href="http://21.100.16.14" class="red">【旧系统登录】</a> 
         <%} %>
	   <% } %>
    </div> 
       <div class="col-md-7 col-sm-7 head_right pr5 pl0 fr"> 
    <!-- 根据session判断 -->
        <c:if test="${properties['ipAddressType'] == 0}">
           <a class="web_number">网站编号：${properties['website.no']} &nbsp;</a>
        </c:if>
    	 <c:if test="${not empty loginUser }">
 	 		|<a onclick="myInfo()">我的信息</a>
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
              <a href="${pageContext.request.contextPath}/index/selectIndexNews.html">
                 <img alt="Logo" src="${pageContext.request.contextPath}/public/portal/images/logo.png" width="100%" id="logo-header">
              </a>
            </div>
			<!--搜索开始-->
            <div class="col-md-7 col-sm-7 col-xs-12 search_gpgz">
              <div class="col-xs-12 gpgz_moblie">
                <img src="${pageContext.request.contextPath}/public/portal/images/gpgz.png" width="100%">
              </div>
              <div class="col-md-7 col-xs-12 col-sm-7 search-block-v2 col-md-offset-5 col-sm-offset-5 col-xs-offset-0">
                  <form id="form1" accept-charset="UTF-8" action="${pageContext.request.contextPath}/index/solrSearch.html" method="get">
				    <div style="display:none">
				     <input name="utf8" value="✓" type="hidden">
					</div>
                    <input id="t" name="t" value="search_products" type="hidden">
                    <div class="col-md-12 col-xs-12 col-sm-12 pull-left p0">
                      <div class="search-group">
                        <input class="search-input" id="k" name="condition" value="${oldCondition}" placeholder="" type="text">
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
      <ul class="nav navbar-nav">
      <!-- 通知 -->
        <li id="firstPage" class="dropdown shouye_li mega-menu-fullwidth">
          <a class=" dropdown-toggle " href="${pageContext.request.contextPath}/index/selectIndexNews.html"><i class="shouye nav_icon"></i>首 页</a>
        </li>
      <!-- End 通知 -->

      <!-- 公告 -->
        <li class="dropdown other gonggao_li mega-menu-fullwidth">
          <a class="dropdown-toggle " href="javascript:void(0);" data-toggle="dropdown"><i class="gonggao nav_icon"></i>信息公告</a>
	  <!--	信息公告鼠标移动开始   -->
	  <div class="drop_next dropdown-menu" >
	   <div class="row magazine-page clear">
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
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectsumBynews.html?id=3&twoid=24">更多>></a>
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
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectsumBynews.html?id=8&twoid=29">更多>></a>
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
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectsumBynews.html?id=13&twoid=34">更多>></a>
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
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectsumBynews.html?id=18&twoid=39">更多>></a>
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
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectsumBynews.html?id=46&twoid=67">更多>></a>
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
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectsumBynews.html?id=51&twoid=72">更多>></a>
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
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectsumBynews.html?id=56&twoid=77">更多>></a>
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
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectsumBynews.html?id=61&twoid=82">更多>></a>
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
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectsumBydanNews.html?id=89&twoid=94">更多>></a>
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
						--%><li><a title="{sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">{sl.name}</a></li>
					<%--</c:if>
	          --%></c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectsumBydanNews.html?id=90&twoid=95">更多>></a>
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
         <a class="tab_more" href="${pageContext.request.contextPath}/index/selectsumBydanNews.html?id=91&twoid=96">更多>></a>
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
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectsumBydanNews.html?id=92&twoid=97">更多>></a>
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
	   <div class="row magazine-page clear">
      <div class="col-md-12 col-sm-12 col-xs-12 drop_hover">
	   <div class="drop_main ">
	    <div class="col-md-4 col-sm-6 col-xs-12 mt25" id="drop-1">
		  <div class="ywbl_01">
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
            <span>网上商城</span> 
		   </a>
	      </div>
		  <div class="ywbl_01">
	       <a href="#" class="dxcpjj">
            <span>定型产品竞价</span> 
		   </a>
	     </div>
		 <div class="ywbl_01">
	      <a href="#" class="ypcg">
           <span>药品采购</span> 
		  </a>
	     </div>
		 <div class="ywbl_01">
	      <a href="#" class="fwcg">
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

      <!-- 专家 -->
        <li class="dropdown other zhuanjia_li mega-menu-fullwidth">
          <a class="dropdown-toggle" data-toggle="dropdown" href="javascript:void(0);" ><i class="zhuanjia nav_icon"></i>供应商</a>
	<!--供应商鼠标移动开始-->
		<div class="drop_next dropdown-menu" >
	     <div class="row magazine-page clear">
	     <div class="col-md-12 col-sm-12 col-xs-12 drop_hover" >
	      <div class="drop_main">
	       <div class="col-md-2 col-sm-2 col-xs-2 mt20 supp_login">
	          <a href="${pageContext.request.contextPath}/supplier/registration_page.html" >
	        <%-- 
	        <% if (environment != null && environment.equals("0")){ %>
	             <a href="${pageContext.request.contextPath}/supplier/registration_page.html" >
	        <% } %>
	        <% if (environment != null && environment.equals("1")){ %>
	              <a onclick="registerTip();" >
	        <% } %>
	        --%>
		     	供应商注册
		     <i></i>
		    </a>	  
	       </div>
	  <div class="col-md-5 col-sm-5 col-xs-10 mt10">
	   <div class="headline-v2">
         <h2>供应商名录<a  href="" class="fr f14">更多>></a></h2>
        </div>
         <div class="job-content col-md-12">
		    <div class="categories">
             <ul class="p0_10 list-unstyled">   
              <li></li>
			 </ul>
			</div>
		  </div>
	  </div>
	  
		<div class="login_box job-content col-md-5 col-sm-5 col-xs-12 mt10">
		 <h2 class="f17 bgwhite">
		 <ul class="list-unstyled login_tab">
		  <li class="active fl"><a aria-expanded="true" href="#tab-36" data-toggle="tab">诚信记录</a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-37" data-toggle="tab"> 军队处罚公告</a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-38" data-toggle="tab"> 地方处罚公告</a></li>
		 </ul>
		</h2>
		<div class="tab-content buyer_list">
		    <div id="tab-36" class="categories tab-pane fade active in">
             <ul class="p0_10">   
          </ul>
          <a class="tab_more" href="#">更多>></a>
        </div>
	    <div id="tab-37" class="categories tab-pane fade">
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
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=116">更多>></a>
        </div>
		<div id="tab-38" class="categories tab-pane fade">
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
        </div>
		</div>
	  </div>
	</div>
   </div>
   </div>
   </div>
        </li>
      <!-- End 专家 -->

      <!-- 投诉 -->
        <li class="dropdown other tousu_li mega-menu-fullwidth">
          <a class="dropdown-toggle " data-toggle="dropdown" href="javascript:void(0);"><i class="tousu nav_icon"></i>评审专家</a>
	<!--评审专家鼠标移动开始-->
		 <div class="drop_next dropdown-menu" >
	   <div class="row magazine-page clear">
	<div class="col-md-12 col-sm-12 col-xs-12 drop_hover"  id="drop-4">
	 <div class="drop_main">
	  <div class="col-md-2 col-xs-2 col-sm-2 mt20 supp_login">
	     <%-- <a href="${pageContext.request.contextPath}/expert/toRegisterNotice.html"> --%>
	     <% if (environment != null && environment.equals("0")){ %>
	        <a href="${pageContext.request.contextPath}/expert/toRegisterNotice.html">
	     <% } %>
	     <% if (environment != null && environment.equals("1")){ %>
	        <a onclick="registerTip();">
	     <% } %>
		   评审专家注册
		 <i></i>
		</a>	  
	  </div>
	  <div class="col-md-5 col-sm-5 col-xs-10 mt10 ">
	   <div class="headline-v2">
         <h2>专家名录<a href="#" class="fr f14">更多>></a></h2>
        </div>
         <div class="job-content col-md-12 col-sm-12 col-xs-12">
		    <div class="categories zhuanjia_list">
               <a href="#" title=" " target="_blank"></a>
			</div>
		  </div>
	  </div>
	  
		<div class="login_box job-content col-md-5 col-sm-5 col-xs-12 mt10">
		 <h2 class="f17 bgwhite">
		 <ul class="list-unstyled login_tab">
		  <li class="active fl"><a aria-expanded="true" href="#tab-39" data-toggle="tab">诚信记录</a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-40" data-toggle="tab">处罚公告</a></li>
		 </ul>
		</h2>
		<div class="tab-content  buyer_list">
		    <div id="tab-39" class="categories tab-pane fade active in">
             <ul class="p0_10">   
          </ul>
          <a class="tab_more" href="#">更多>></a>
          </div>
		  <div id="tab-40" class="categories tab-pane fade">
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
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=115">更多>></a>
        </div>
		</div>
	  </div>
	</div>
   </div>
   </div>
   </div>
        </li>
      <!-- End 投诉 -->

      <!-- 法规 -->
        <li class="dropdown other cgfw_li mega-menu-fullwidth">
          <a class="dropdown-toggle " data-toggle="dropdown" href="javascript:void(0);"><i class="cgfw nav_icon"></i>采购服务</a>
	<!--采购服务鼠标移动开始-->
		  <div class="drop_next dropdown-menu" >
	   <div class="row magazine-page clear">

	<div class="col-md-12 col-xs-12 col-sm-12 drop_hover">
	 <div class="drop_main supp_service">
	 <div class="col-md-2 col-sm-4 col-xs-6 mt60">
	  <a href="">
	    <div class="col-md-12 col-xs-12 col-sm-12 tc"><img src="${pageContext.request.contextPath}/public/portal/images/cpml.jpg" width="80%" height="80%;"/></div>
		<div class="tc f18 mt20 pt10 clear">产品目录</div>
	  </a>
	 </div>
	 <div class="col-md-2 col-sm-4 col-xs-6 mt60">
	  <a href="">
	    <div class="col-md-12 col-xs-12 col-sm-12 tc"><img src="${pageContext.request.contextPath}/public/portal/images/jscsk.jpg" width="80%" height="80%;"/></div>
		<div class="tc f18 mt20 pt10 clear">技术参数库</div>
	  </a>
	 </div>
	 <div class="col-md-2 col-sm-4 col-xs-6 mt60">
	  <a href="${pageContext.request.contextPath}/park/getIndex.html">
	    <div class="col-md-12 col-xs-12 col-sm-12 tc"><img src="${pageContext.request.contextPath}/public/portal/images/cglt.jpg" width="80%" height="80%;"/></div>
		<div class="tc f18 mt20 pt10 clear">采购论坛</div>
	  </a>
	 </div>
	 <div class="col-md-2 col-sm-4 col-xs-6 mt60">
	  <% if (ipAddressType != null && ipAddressType.equals("0")){ %>
	       <a href="${pageContext.request.contextPath }/dataDownload/getIndexList.html">
	  <% } %>
	  <% if (ipAddressType != null && ipAddressType.equals("1")){ %>
	      <a href="javascript:void(0);">
	  <% } %>
	    <div class="col-md-12 col-xs-12 col-sm-12 tc"><img src="${pageContext.request.contextPath}/public/portal/images/new_zlxz.jpg" width="80%" height="80%;"/></div>
		<div class="tc f18 mt20 pt10 clear">资料下载</div>
	  </a>
	 </div>
	 <div class="col-md-2 col-sm-4 col-xs-6 mt60">
	  <a href="">
	    <div class="col-md-12 col-xs-12 col-sm-12 tc"><img src="${pageContext.request.contextPath}/public/portal/images/yjfk.jpg" width="80%" height="80%;"/></div>
		<div class="tc f18 mt20 pt10 clear">意见反馈</div>
	  </a>
	 </div>
	 <div class="col-md-2 col-sm-4 col-xs-6 mt60">
	  <a href="">
	    <div class="col-md-12 col-xs-12 col-sm-12 tc"><img src="${pageContext.request.contextPath}/public/portal/images/new_shfw.jpg" width="80%" height="80%;"/></div>
		<div class="tc f18 mt20 pt10 clear">售后服务</div>
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
	 <div class="row magazine-page clear">
     <div class="col-md-12 col-xs-12 col-sm-12 col-xs-12 col-sm-12 drop_hover"  id="drop-6">
	  <div class="drop_main">
	   <div class="col-md-4 col-sm-4 col-xs-12 mt20">
	    <a href="#">
	     <div class="col-md-12 col-xs-12 col-sm-12 tc p0">
	     <img src="${pageContext.request.contextPath}/public/portal/images/tou_pic.jpg"/></div>
		 <div class="col-md-12 col-xs-12 col-sm-12 f22 tc mt20 p0">在线投诉</div>
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
	   <div class="row magazine-page clear">
      <div class="col-md-12 col-xs-12 col-sm-12 drop_hover"  id="drop-7">
	   <div class="drop_main">
		<div class="margin-bottom-10 login_box job-content col-md-5 col-sm-5 col-xs-12 mt10">
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
</body>
</html>
