<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]-->
<head>
<jsp:include page="/WEB-INF/view/portal.jsp" />
<script type="text/javascript">
$(function(){
	
	var browser=navigator.appName;
	var b_version=navigator.appVersion;
	var version=parseFloat(b_version);
	var ver_arr = b_version.split(";");
	var msie_ver = ""
	for(var i=0; i<ver_arr.length; i++){
		if(ver_arr[i].indexOf('MSIE')!= -1){
			msie_ver = ver_arr[i].substring(5,ver_arr[i].length);
		}
	}
	if ((browser=="Netscape"||browser=="Microsoft Internet Explorer") && (version<=4) && msie_ver < 8 ){
		window.location.href="${pageContext.request.contextPath}/browser/index.html";
	} else {
		$("#firstPage").attr("Class","active dropdown shouye_li mega-menu-fullwidth");
		
		$(".header-v4 .navbar-default .navbar-nav > .other > a").hover(function(){
			$("#firstPage").attr("Class","dropdown shouye_li mega-menu-fullwidth");
		});
	}

})

function kaptcha(){
	$("#kaptchaImage").hide().attr('src','Kaptcha.jpg?' + Math.floor(Math.random() * 100)).fadeIn();
}

function setting(){
	layer.msg("正在建设中");
}

function drugs(){
	layer.msg("正在建设中");
}
</script>
</head>

<body>
  <jsp:include page="/index_head.jsp"></jsp:include>
  <!--/end container-->
  <!-- End Navbar -->
  <div class="container content job-content ">
     <div class="row magazine-page clear">
      <div class="col-md-8  margin-bottom-10">
        <div class="section-focus-pic" id="section-focus-pic">
	      <div class="pages" data-scro="list">
		   <ul>
		  	<%-- <c:forEach items="${indexMapper['picList']}" var="pic" varStatus="vs">
		  	<c:choose>
		  	 <c:when test="${vs.index==0}">
			   <li class="item" style="left:0px;">
					<a href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${pic.id}" target="_blank"><img src="${pageContext.request.contextPath}/file/viewFile.html?path=${pic.pic}" width="100%" height="100%"></a>
			   </li>
			 </c:when>
			 <c:otherwise>
			   <li class="item">
					<a href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${pic.id}" target="_blank"><img src="${pageContext.request.contextPath}/file/viewFile.html?path=${pic.pic}" width="100%" height="100%"></a>
			   </li>
			 </c:otherwise>
			 </c:choose>
			</c:forEach>
			--%>
			<li class="item" style="left:0px;">
				<a href="javascript:void(0)" target="_blank"><img src="${pageContext.request.contextPath}/public/portal/images/1.jpg" width="100%" height="100%"></a>
			</li>
			<li class="item">
				<a href="javascript:void(0)" target="_blank"><img src="${pageContext.request.contextPath}/public/portal/images/2.jpg" width="100%" height="100%"></a>
			</li>
			<li class="item">
				<a href="javascript:void(0)" target="_blank"><img src="${pageContext.request.contextPath}/public/portal/images/3.jpg" width="100%" height="100%"></a>
			</li>
		   </ul>
			</ul>
	      </div>
	      <div class="controler" data-scro="controler">
		   <b class="down">1</b>
		   <b>2</b>
		   <b>3</b>
	      </div>
        </div>
        <script src="${pageContext.request.contextPath}/public/portal/js/script.js"></script>
       </div>
       <div class="col-md-4 ">
        <div class="tab-v1">
          <h2 class="nav nav-tabs bb1 mt0">
            <span class="bg12_white"><a href="javascript:void(0)">工作动态</a></span>
            <a class="news_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=27">更多>></a>
		  </h2>
		  </div>
          <div class="">
                <ul class="list-unstyled categories list_common">
	                <c:forEach items="${indexMapper['select27List']}" var="sl">
	                	<c:set value="${sl.name}" var="name"></c:set>
						<c:set value="${fn:length(name)}" var="length"></c:set>
						<c:if test="${length>24}">
							<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${fn:substring(name,0,24)}...</a></li>
						</c:if>
						<c:if test="${length<=24}">
							<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${name}</a></li>
						</c:if>
	                </c:forEach>            
                </ul>
                
          </div>
       </div>
  <!-- Begin 登录 --><%--
  <div class="col-md-3">

    <div class="border1 margin-bottom-10 login_box">
        <h2 class="f17">
		 <ul class="list-unstyled login_tab">
		  <li class="active fl"><a aria-expanded="true" href="#tab-1" data-toggle="tab"> 用户登录</a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-2" data-toggle="tab"> CA登录</a></li>
		 </ul>
		</h2>
	--%><%--<div class="tab-content p17-0">
	 <div id="tab-1" class="tab-pane fade active in">
      <form class="form-horizontal p15_0">
        <div class="control-group ">
          <label class="control-label" for="inputEmail">用户名：</label>
			  <div class="controls padding-right-10">
            <input type="text" id="inputEmail" class="form-control" placeholder="请输入用户名"/>
          </div>
        </div>
        <div class="control-group padding-right-10 margin-top-10">
          <label class="control-label" for="inputPassword">密码：</label>
          <div class="controls">
          <input type="password" id="inputPassword" class="form-control" placeholder="请输入密码">
          </div>
        </div>
        <div class="control-group  margin-top-10 ">
         <label class="control-label" for="inputPassword">验证码：</label>
        <div class="controls">
          <input type="text" placeholder="" id="inputCode" class="input-mini fl ">
          	<img src="Kaptcha.jpg" onclick="kaptcha();" id="kaptchaImage" />
        </div>
       </div>
       <div class="control-group margin-top-22 clear ml30">
          <button class="btn" type="button" onclick="login();">登录</button>
      </div>
    </form>
  </div>--%><%--
	 <div id="tab-2" class="tab-pane fade">
      <form class="form-horizontal p15_0">
        <div class="control-group ">
          <label class="control-label" for="inputEmail">用户名：</label>
			  <div class="controls padding-right-10">
            <input type="text" id="inputEmail" class="form-control" placeholder="请输入用户名"/>
          </div>
        </div>
        <div class="control-group padding-right-10 margin-top-10">
          <label class="control-label" for="inputPassword">密码：</label>
          <div class="controls">
          <input type="password" id="inputPassword" class="form-control" placeholder="请输入密码">
          </div>
        </div>
        <div class="control-group  margin-top-10 ">
        <label class="control-label" for="inputPassword">验证码：</label>
        <div class="controls">
          <input type="text" placeholder="" id="inputCode" class="input-mini fl ">
          	<img src="Kaptcha.jpg" onclick="kaptcha();" id="kaptchaImage" />
        </div>
       </div>
       <div class="control-group margin-top-22 clear ml30">
          <button class="btn login_btn" type="submit">登录</button>
      </div>
    </form>
  </div>
  </div>--%>
  </div>
  </div>
  </div>
 </div>
</div>

  
  
<!-- /* 集中采购开始*/-->
  <div class="container">
  <div class="row magazine-page clear">
   <div class="col-md-12">
    <h2 class="floor_title col-md-12">
    集中采购
	</h2>
   </div>
   </div>
   <div class="row magazine-page clear">
      <div class="col-md-4 mb10 login_box">
        <div class=" border1 job-content floor_kind">
          <h2>
		  <span class="col-md-5">采购公告</span>
		  <ul class="list-unstyled col-md-7 buyer_news m0">
		    <li class="active fl"><a aria-expanded="true" href="#tab-3" data-toggle="tab"> 物资</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-4" data-toggle="tab" > 工程</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-5" data-toggle="tab" > 服务</a></li>
		  </ul>
          </h2>
		  <div class=" tab-content">
		    <div id="tab-3" class="tab-pane fade active in">
             <ul class="categories">   
              <c:forEach items="${indexMapper['select28List']}" var="sl">
                	<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a></li>
					</c:if>
					<c:if test="${length<=23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${name}</a></li>
					</c:if>
	          </c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=28">更多>></a>
        </div>
		    <div id="tab-4" class="tab-pane fade">
             <ul class="categories">   
              <c:forEach items="${indexMapper['select29List']}" var="sl">
                	<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a></li>
					</c:if>
					<c:if test="${length<=23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${name}</a></li>
					</c:if>
	          </c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=29">更多>></a>
        </div>
		    <div id="tab-5" class="tab-pane fade">
             <ul class="categories">   
              <c:forEach items="${indexMapper['select30List']}" var="sl">
                	<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a></li>
					</c:if>
					<c:if test="${length<=23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${name}</a></li>
					</c:if>
	          </c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=30">更多>></a>
        </div>
	   </div>
	  </div>
     </div>
	
       <div class="col-md-4 mb10 login_box">
        <div class=" border1 job-content floor_kind">
          <h2>
		  <span class="col-md-5">中标公告</span>
		  <ul class="list-unstyled buyer_news col-md-7 m0">
		    <li class="active fl"><a aria-expanded="true" href="#tab-6" data-toggle="tab"> 物资</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-7" data-toggle="tab" > 工程</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-8" data-toggle="tab" > 服务</a></li>
		  </ul>
          </h2>
		  <div class=" tab-content">
		    <div id="tab-6" class="tab-pane fade active in">
             <ul class="categories">   
              <c:forEach items="${indexMapper['select31List']}" var="sl">
                	<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a></li>
					</c:if>
					<c:if test="${length<=23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${name}</a></li>
					</c:if>
	          </c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=31">更多>></a>
        </div>
		    <div id="tab-7" class="tab-pane fade">
             <ul class="categories">   
              <c:forEach items="${indexMapper['select32List']}" var="sl">
                	<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a></li>
					</c:if>
					<c:if test="${length<=23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${name}</a></li>
					</c:if>
	          </c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=32">更多>></a>
        </div>
		    <div id="tab-8" class="tab-pane fade">
             <ul class="categories">   
              <c:forEach items="${indexMapper['select33List']}" var="sl">
                	<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a></li>
					</c:if>
					<c:if test="${length<=23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${name}</a></li>
					</c:if>
	          </c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=33">更多>></a>
        </div>
	   </div>
	  </div>
     </div>
      <div class="col-md-4 mb10 login_box">
        <div class=" border1 job-content floor_kind">
          <h2>
		  <span class="col-md-5">单一来源公告</span>
		  <ul class="list-unstyled buyer_news col-md-7 m0">
		    <li  class="active fl"><a aria-expanded="true" href="#tab-9" data-toggle="tab"> 物资</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-10" data-toggle="tab" > 工程</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-11" data-toggle="tab" > 服务</a></li>
		  </ul>
          </h2>
		  <div class=" tab-content">
		    <div id="tab-9" class="tab-pane fade active in">
             <ul class="categories">   
              <c:forEach items="${indexMapper['select34List']}" var="sl">
                	<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a></li>
					</c:if>
					<c:if test="${length<=23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${name}</a></li>
					</c:if>
	          </c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=34">更多>></a>
        </div>
		    <div id="tab-10" class="tab-pane fade">
             <ul class="categories">   
              <c:forEach items="${indexMapper['select35List']}" var="sl">
                	<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a></li>
					</c:if>
					<c:if test="${length<=23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${name}</a></li>
					</c:if>
	          </c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=35">更多>></a>
        </div>
		    <div id="tab-11" class="tab-pane fade">
             <ul class="categories">   
              <c:forEach items="${indexMapper['select36List']}" var="sl">
                	<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a></li>
					</c:if>
					<c:if test="${length<=23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${name}</a></li>
					</c:if>
	          </c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=36">更多>></a>
        </div>
	   </div>
	  </div>
     </div> 
  </div> 
</div>
<!--部队采购开始-->

 <div class="container">
  <div class="row magazine-page clear">
   <div class="col-md-12">
    <h2 class="floor_title col-md-12">
    部队采购
	</h2>
   </div>
   </div>
   <div class="row magazine-page clear">
      <div class="col-md-4 mb10 login_box">
        <div class=" border1 job-content floor_kind">
          <h2>
		  <span class="col-md-5">采购公告</span>
		  <ul class="list-unstyled mt0 fr col-md-7 p0">
		    <li  class="active fl"><a aria-expanded="true" href="#tab-12" data-toggle="tab"> 物资</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-13" data-toggle="tab" > 工程</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-14" data-toggle="tab" > 服务</a></li>
		  </ul>
          </h2>
		  <div class=" tab-content">
		    <div id="tab-12" class="tab-pane fade active in">
             <ul class="categories">   
              <c:forEach items="${indexMapper['select37List']}" var="sl">
                	<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a></li>
					</c:if>
					<c:if test="${length<=23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${name}</a></li>
					</c:if>
	          </c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=37">更多>></a>
        </div>
		    <div id="tab-13" class="tab-pane fade">
             <ul class="categories">   
              <c:forEach items="${indexMapper['select38List']}" var="sl">
                	<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a></li>
					</c:if>
					<c:if test="${length<=23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${name}</a></li>
					</c:if>
	          </c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=38">更多>></a>
        </div>
		    <div id="tab-14" class="tab-pane fade">
             <ul class="categories">   
              <c:forEach items="${indexMapper['select39List']}" var="sl">
                	<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a></li>
					</c:if>
					<c:if test="${length<=23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${name}</a></li>
					</c:if>
	          </c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=39">更多>></a>
        </div>
	   </div>
	  </div>
     </div>
	
       <div class="col-md-4 mb10 login_box">
        <div class=" border1 job-content floor_kind">
          <h2>
		  <span class="col-md-5">中标公告</span>
		  <ul class="list-unstyled mt0 fr col-md-7 p0">
		    <li  class="active fl"><a aria-expanded="true" href="#tab-15" data-toggle="tab"> 物资</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-16" data-toggle="tab" > 工程</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-17" data-toggle="tab" > 服务</a></li>
		  </ul>
          </h2>
		  <div class=" tab-content">
		    <div id="tab-15" class="tab-pane fade active in">
             <ul class="categories">   
              <c:forEach items="${indexMapper['select40List']}" var="sl">
                	<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a></li>
					</c:if>
					<c:if test="${length<=23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${name}</a></li>
					</c:if>
	          </c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=40">更多>></a>
        </div>
		    <div id="tab-16" class="tab-pane fade">
             <ul class="categories">   
              <c:forEach items="${indexMapper['select41List']}" var="sl">
                	<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a></li>
					</c:if>
					<c:if test="${length<=23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${name}</a></li>
					</c:if>
	          </c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=41">更多>></a>
        </div>
		    <div id="tab-17" class="tab-pane fade">
             <ul class="categories">   
              <c:forEach items="${indexMapper['select42List']}" var="sl">
                	<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a></li>
					</c:if>
					<c:if test="${length<=23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${name}</a></li>
					</c:if>
	          </c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=42">更多>></a>
        </div>
	   </div>
	  </div>
     </div>
      <div class="col-md-4 mb10 login_box">
        <div class=" border1 job-content floor_kind">
          <h2>
		  <span class="col-md-5">单一来源公告</span>
		  <ul class="list-unstyled mt0 fr col-md-7 p0">
		    <li  class="active fl"><a aria-expanded="true" href="#tab-18" data-toggle="tab"> 物资</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-19" data-toggle="tab" > 工程</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-20" data-toggle="tab" > 服务</a></li>
		  </ul>
          </h2>
		  <div class=" tab-content">
		    <div id="tab-18" class="tab-pane fade active in">
             <ul class="categories">   
              <c:forEach items="${indexMapper['select43List']}" var="sl">
                	<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a></li>
					</c:if>
					<c:if test="${length<=23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${name}</a></li>
					</c:if>
	          </c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=43">更多>></a>
        </div>
		    <div id="tab-19" class="tab-pane fade">
             <ul class="categories">   
              <c:forEach items="${indexMapper['select44List']}" var="sl">
                	<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a></li>
					</c:if>
					<c:if test="${length<=23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${name}</a></li>
					</c:if>
	          </c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=44">更多>></a>
        </div>
		    <div id="tab-20" class="tab-pane fade">
             <ul class="categories">   
              <c:forEach items="${indexMapper['select45List']}" var="sl">
                	<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a></li>
					</c:if>
					<c:if test="${length<=23}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${name}</a></li>
					</c:if>
	          </c:forEach>
          </ul>
          <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=45">更多>></a>
        </div>
	   </div>
	  </div>
     </div>  
  </div>
</div>
<!--业务办理开始-->
<div class="container">
  <div class="row magazine-page clear">
   <div class="col-md-12">
    <h2 class="floor_title col-md-12">
    业务办理
	</h2>
   </div>
   </div>
   <div class="row magazine-page clear">
    <div class="col-md-12">
     <div class="border1 flow_btn fl mr14">
	   <div class="ywbl_01">
	     <a href="${pageContext.request.contextPath}/supplier/registration_page.html" class="qyzc">
          <span>企业注册</span> 
		 </a>
	   </div>
	   <div class="ywbl_01">
	     <a href="${pageContext.request.contextPath}/expert/toRegisterNotice.html" class="zjzc">
          <span>专家注册</span> 
		 </a>
	   </div>
	   <div class="ywbl_01">
	     <a onclick="importAdd()" class="jksdj">
          <span>进口商登记</span> 
		 </a>
	   </div>
	 </div>
	 
     <div class="border1 flow_btn fl">
	   <div class="ywbl_01">
	     <a href="javascript:void(0)" class="wssc">
          <span>网上商城</span> 
		 </a>
	   </div>
	   <div class="ywbl_01">
	     <a href="javascript:void(0)" onclick="setting()" class="dxcpjj">
          <span>定型产品竞价</span> 
		 </a>
	   </div>
	   <div class="ywbl_01">
	     <a href="javascript:void(0)" onclick="drugs()" class="ypcg">
          <span>药品采购</span> 
		 </a>
	   </div>
	   <div class="ywbl_01">
	     <a href="javascript:void(0)" class="fwcg">
          <span>服务采购</span> 
		 </a>
	   </div>
	   <div class="ywbl_01">
	     <a href="javascript:void(0)" class="fwrx">
          <span>服务热线</span> 
		 </a>
	   </div>
	 </div>
     <div class="border1 flow_btn clear">
	   <div class="ywbl_02">
	     <a href="javascript:void(0)" class="cpml">
          <span>产品目录</span> 
		 </a>
	   </div>
	   <div class="ywbl_02">
	     <a href="javascript:void(0)" class="jscsk">
          <span>技术参数库</span> 
		 </a>
	   </div>
	   <div class="ywbl_02">
	     <a href="javascript:void(0)" class="zlxz">
          <span>资料下载</span> 
		 </a>
	   </div>
	   <div class="ywbl_02">
	     <a href="javascript:void(0)" class="cpshfw">
          <span>产品售后服务</span> 
		 </a>
	   </div>
	   <div class="ywbl_02">
	     <a href="javascript:void(0)" class="zxts">
          <span>在线投诉</span> 
		 </a>
	   </div>
	   <div class="ywbl_02">
	     <a href="${pageContext.request.contextPath}/park/getIndex.html" class="cglt">
          <span>采购论坛</span> 
		 </a>
	   </div>
	   <div class="ywbl_02">
	     <a href="javascript:void(0)" class="yjfk">
          <span>意见反馈</span> 
		 </a>
	   </div>
	 </div>
	 </div>
   </div>
</div>

<!--采购信息开始-->
<div class="container">
  <div class="row magazine-page clear">
   <div class="col-md-12">
    <h2 class="floor_title col-md-12">
      采购信息
	</h2>
   </div>
   </div>
   <div class="row magazine-page clear">
      <div class="col-md-3 mb10 login_box">
        <div class=" border1 job-content floor_kind">
          <h2 class="p0_10">
		   重要通知<a class="news_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=46">更多>></a>
          </h2>
		    <div class="categories">
             <ul class="p0_10">   
              <c:forEach items="${indexMapper['select46List']}" var="sl">
                	<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>17}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${fn:substring(name,0,17)}...</a></li>
					</c:if>
					<c:if test="${length<=17}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${name}</a></li>
					</c:if>
	          </c:forEach>
          </ul>
        </div>
	</div>
	</div>
	<div class="col-md-3 mb10 login_box">
        <div class=" border1 job-content floor_kind">
          <h2 class="p0_10">
		   采购法规<a class="news_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=47">更多>></a>
          </h2>
		    <div class="categories">
             <ul class="p0_10">   
              <c:forEach items="${indexMapper['select47List']}" var="sl">
                	<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>17}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${fn:substring(name,0,17)}...</a></li>
					</c:if>
					<c:if test="${length<=17}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${name}</a></li>
					</c:if>
	          </c:forEach>
          </ul>
        </div>
	</div>
	</div>
      <div class="col-md-3 mb10 login_box">
        <div class=" border1 job-content floor_kind">
          <h2 class="p0_10">
		   投诉处理公告<a class="news_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=48">更多>></a>
          </h2>
		    <div class="categories">
             <ul class="p0_10">   
              <c:forEach items="${indexMapper['select48List']}" var="sl">
                	<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>17}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${fn:substring(name,0,17)}...</a></li>
					</c:if>
					<c:if test="${length<=17}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${name}</a></li>
					</c:if>
	          </c:forEach>
          </ul>
        </div>
	</div>
	</div>
      <div class="col-md-3 mb10 login_box">
        <div class=" border1 job-content floor_kind">
          <h2 class="p0_10">
		   处罚公告<a class="news_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=49">更多>></a>
          </h2>
		    <div class="categories">
             <ul class="p0_10">   
              <c:forEach items="${indexMapper['select49List']}" var="sl">
                	<c:set value="${sl.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>17}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${fn:substring(name,0,17)}...</a></li>
					</c:if>
					<c:if test="${length<=17}">
						<li><a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${name}</a></li>
					</c:if>
	          </c:forEach>
          </ul>
        </div>
	</div>
	</div>
  </div>
  <!-- <a href="${pageContext.request.contextPath}/index/init.html">初始化solr</a> -->
</div>
<!--底部代码开始-->
<jsp:include page="/index_bottom.jsp"></jsp:include>
</div>
</div>
<!--[if lt IE 9]>
    <script src="/assets/plugins/respond.js?body=1"></script>
<script src="/assets/plugins/html5shiv.js?body=1"></script>
<script src="/assets/plugins/html5.js?body=1"></script>
<script src="/assets/plugins/placeholder-IE-fixes.js?body=1"></script>
<script src="/assets/ie_9.js?body=1"></script>
<![endif]-->

</body>
</html>
