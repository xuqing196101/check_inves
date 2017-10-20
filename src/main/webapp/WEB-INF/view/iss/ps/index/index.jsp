<%@ page language="java" import="java.util.*,bss.util.PropUtil" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<%
  //生产环境
  String environment = PropUtil.getProperty("environment");
  //内外网
  String ipAddressType = PropUtil.getProperty("ipAddressType");
%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
<!--<![endif]-->
  <head>
    <jsp:include page="/index_head.jsp"></jsp:include>
    <%-- <script type="text/javascript" src="${ pageContext.request.contextPath }/js/iss/ps/index/index.js"></script> --%>
 
    <script type="text/javascript">
        var browser = navigator.appName;
        var b_version = navigator.appVersion;
        var version = parseFloat(b_version);
        var ver_arr = b_version.split(";");
        var msie_ver = ""
        for(var i = 0; i < ver_arr.length; i++) {
          if(ver_arr[i].indexOf('MSIE') != -1) {
            msie_ver = ver_arr[i].substring(5, ver_arr[i].length);
          }
        }
        if((browser == "Netscape" || browser == "Microsoft Internet Explorer") && (version <= 4) && msie_ver < 9) {
          //注释掉下面这行IE8 或低版本的则可以正常通过进入项目
          window.location.href = "${pageContext.request.contextPath}/browser/index.html";
        } else {
          $("#firstPage").attr("Class", "active dropdown shouye_li mega-menu-fullwidth");

          $(".header-v4 .navbar-default .navbar-nav > .other > a").hover(function() {
            $("#firstPage").attr("Class", "dropdown shouye_li mega-menu-fullwidth");
          });
        }

      function kaptcha() {
        $("#kaptchaImage").hide().attr('src', 'Kaptcha.jpg?' + Math.floor(Math.random() * 100)).fadeIn();
      }

/*       function setting() {
        layer.msg("正在建设中");
      } */

      function drugs() {
        layer.msg("正在建设中");
      }
      
      function expertRegisterTip(){
		layer.msg("暂未开放，请耐心等待！");
	  }	
	  
	  function supplierRegisterTip(){
		layer.msg("供应商请到外网注册");
	  }
	  
	  function hotLine(){
	  	layer.alert("服务热线：66946342(吕工)");
	  }
	  
    </script>
  </head>

  <body>
    <!--/end container-->
    <!-- End Navbar -->
    <div class="container content job-content ">
      <div class="row magazine-page clear">
        <div class="col-md-8  margin-bottom-10">
          <div class="section-focus-pic" id="section-focus-pic">
            <div class="pages" data-scro="list">
              <ul>
	              	<li class="item" style="left:0px;">
	                  <a href="javascript:void(0)" target="_blank"><img src="${pageContext.request.contextPath}/public/portal/images/1.png" width="100%" height="100%"></a>
	                </li>
	                <li class="item">
	                  <a href="javascript:void(0)" target="_blank"><img src="${pageContext.request.contextPath}/public/portal/images/2.png" width="100%" height="100%"></a>
	                </li>
	                <li class="item">
	                  <a href="javascript:void(0)" target="_blank"><img src="${pageContext.request.contextPath}/public/portal/images/3.png" width="100%" height="100%"></a>
	                </li>
	           
              </ul>
            </div>
            <div id="picshownum" class="controler" data-scro="controler">
            <c:if test="${nums=='0'}">
              <b class="down">1</b>
              <b>2</b>
              <b>3</b>
            </c:if>
            <c:if test="${nums=='1'}">
              <b class="down">1</b>
            </c:if>
            <c:if test="${nums=='2'}">
              <b class="down">1</b>
              <b>2</b>
            </c:if>
            <c:if test="${nums=='3'}">
              <b class="down">1</b>
              <b>2</b>
              <b>3</b>
            </c:if>
            
            </div>
          </div>
          <script src="${pageContext.request.contextPath}/public/portal/js/script.js"></script>
        </div>

        <div class="col-md-4 col-sm-12 col-xs-12 ">
          <div class="tab-v1">
            <h2 class="nav nav-tabs bb1 mt0">
            <c:choose>
		  	  	<c:when test="${articlebjob=='0'}">
		          <span class="bg12_white"><a href="javascript:void(0)">工作动态</a></span>
		        </c:when>
			    <c:otherwise>
			      <span class="bg12_white"><a href="javascript:void(0)"><b id="redground">${articlebjob}</b>工作动态</a></span>
			    </c:otherwise>
		      </c:choose>
            <a class="news_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=110">更多>></a>
		  </h2>
          </div>

          <div class="">
            <ul class="list-unstyled categories list_common articleover">
              <c:forEach items="${indexMapper['select110List']}" var="sl">
                <%--<c:set value="${sl.name}" var="name"></c:set>
                <c:set value="${fn:length(name)}" var="length"></c:set>
                <c:if test="${length>24}">
                  <li>
                    <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${fn:substring(name,0,24)}...</a>
                  </li>
                </c:if>
                <c:if test="${length<=24}">
                  --%><li>
                    <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${sl.name}</a>
                  </li>
                <%--</c:if>
              --%></c:forEach>
            </ul>

          </div>
        </div>

      </div>
    </div>
    </div>
    </div>
    </div>

    <!-- /* 集中采购开始*/-->
    <div class="container">
      <div class="row magazine-page clear">
        <div class="col-sm-12 col-md-12 col-xs-12">
          <h2 class="floor_title col-sm-12 col-md-12 col-xs-12">集中采购</h2>
        </div>
      </div>
      <div class="row magazine-page clear">
        <div class="col-md-4 col-sm-12 col-xs-12 mb10 login_box">
          <div class=" border1 job-content floor_kind floor_news">
            <h2>
		  	  <ul class="list-unstyled col-md-12 col-sm-12 col-xs-12 buyer_news m0 p0">
		  	  <li class="active fl report_title"><a aria-expanded="true" href="#tab-cjList" data-toggle="tab">采购公告</a></li>
		  	  <c:choose>
		  	  	<c:when test="${articlejcw=='0'}">
		          <li class="fl"><a aria-expanded="true" href="#tab-3" data-toggle="tab">物资</a></li>
		        </c:when>
			    <c:otherwise>
			      <li class="fl"><a aria-expanded="true" href="#tab-3" data-toggle="tab"><b>${articlejcw}</b> 物资</a></li>
			    </c:otherwise>
		      </c:choose>
		      <c:choose>
		  	  	<c:when test="${articlejcg=='0'}">
		          <li class="fl"><a aria-expanded="false" href="#tab-4" data-toggle="tab" > 工程</a></li>
		        </c:when>
			    <c:otherwise>
			      <li class="fl"><a aria-expanded="false" href="#tab-4" data-toggle="tab" ><b>${articlejcg}</b> 工程</a></li>
			    </c:otherwise>
		      </c:choose>
		      <c:choose>
		  	  	<c:when test="${articlejcf=='0'}">
		          <li class="fl"><a aria-expanded="false" href="#tab-5" data-toggle="tab" > 服务</a></li>
		        </c:when>
			    <c:otherwise>
			      <li class="fl"><a aria-expanded="false" href="#tab-5" data-toggle="tab" ><b>${articlejcf}</b> 服务</a></li>
			    </c:otherwise>
		      </c:choose>
		      <c:choose>
		  	  	<c:when test="${articlejcj=='0'}">
		          <li class="fl"><a aria-expanded="false" href="#tabs-6" data-toggle="tab" > 进口</a></li>
		        </c:when>
			    <c:otherwise>
			      <li class="fl"><a aria-expanded="false" href="#tabs-6" data-toggle="tab" ><b>${articlejcj}</b> 进口</a></li>
			    </c:otherwise>
		      </c:choose>
		      </ul>
          </h2>
            <div class=" tab-content">
            <div id="tab-cjList" class="tab-pane fade active in">
                <ul class="categories articleover">
                  <c:forEach items="${indexMapper['cjList']}" var="sl">
                    <c:choose>
                      <c:when  test="${sl.lastArticleType.name=='公开招标'||sl.lastArticleType.name=='邀请招标'||sl.lastArticleType.name=='竞争性谈判'||sl.lastArticleType.name=='询价'}">
                      <li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${sl.name}</a>
                      </li>
                      </c:when>
                      <c:otherwise>
                      	<li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${sl.name}</a>
                      	</li>
                      </c:otherwise>
                      </c:choose>
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectAllByTabs.html?articleTypeId=1&secondArticleTypeId=2">更多>></a>
              </div>
              <div id="tab-3" class="tab-pane fade">
                <ul class="categories articleover">
                  <c:forEach items="${indexMapper['select3List']}" var="sl">
                  	<c:choose>
                      <c:when  test="${sl.lastArticleType.name=='公开招标'||sl.lastArticleType.name=='邀请招标'||sl.lastArticleType.name=='竞争性谈判'||sl.lastArticleType.name=='询价'}">
                      <li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${sl.name}</a>
                      </li>
                      </c:when>
                      <c:otherwise>
                      	<li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${sl.name}</a>
                      	</li>
                      </c:otherwise>
                      </c:choose>  
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByParId.html?id=3&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("物资","utf-8"),"utf-8") %>">更多>></a>
              </div>

              <div id="tab-4" class="tab-pane fade">
                <ul class="categories articleover">
                  <c:forEach items="${indexMapper['select8List']}" var="sl">
                  	<c:choose>
                      <c:when  test="${sl.lastArticleType.name=='公开招标'||sl.lastArticleType.name=='邀请招标'||sl.lastArticleType.name=='竞争性谈判'||sl.lastArticleType.name=='询价'}">
                      <li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${sl.name}</a>
                      </li>
                      </c:when>
                      <c:otherwise>
                      	<li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${sl.name}</a>
                      	</li>
                      </c:otherwise>
                      </c:choose>  
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByParId.html?id=8&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("工程","utf-8"),"utf-8") %>">更多>></a>
              </div>

              <div id="tab-5" class="tab-pane fade">
                <ul class="categories articleover">
                  <c:forEach items="${indexMapper['select13List']}" var="sl">
                  	<c:choose>
                      <c:when  test="${sl.lastArticleType.name=='公开招标'||sl.lastArticleType.name=='邀请招标'||sl.lastArticleType.name=='竞争性谈判'||sl.lastArticleType.name=='询价'}">
                      <li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${sl.name}</a>
                      </li>
                      </c:when>
                      <c:otherwise>
                      	<li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${sl.name}</a>
                      	</li>
                      </c:otherwise>
                      </c:choose>  
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByParId.html?id=13&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("服务","utf-8"),"utf-8") %>">更多>></a>
              </div>
              
              <div id="tabs-6" class="tab-pane fade">
                <ul class="categories articleover">
                  <c:forEach items="${indexMapper['select18List']}" var="sl">
                    <c:choose>
                      <c:when  test="${sl.lastArticleType.name=='公开招标'||sl.lastArticleType.name=='邀请招标'||sl.lastArticleType.name=='竞争性谈判'||sl.lastArticleType.name=='询价'}">
                      <li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${sl.name}</a>
                      </li>
                      </c:when>
                      <c:otherwise>
                      	<li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${sl.name}</a>
                      	</li>
                      </c:otherwise>
                      </c:choose>
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByParId.html?id=18&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("进口","utf-8"),"utf-8") %>">更多>></a>
              </div>
            </div>
          </div>
        </div>

        <div class="col-md-4 col-sm-12 col-xs-12 mb10 login_box">
          <div class=" border1 job-content floor_kind floor_news">
            <h2>
		  <ul class="list-unstyled col-md-12 col-sm-12 col-xs-12 buyer_news m0 p0">
		  <li class="active fl report_title"><a aria-expanded="true" href="#tab-czList" data-toggle="tab">中标公示</a></li>
		  <c:choose>
	  	  	<c:when test="${articlejzw=='0'}">
	          <li class="fl"><a aria-expanded="true" href="#tab-6" data-toggle="tab"> 物资</a></li>
	        </c:when>
		    <c:otherwise>
		      <li class="fl"><a aria-expanded="true" href="#tab-6" data-toggle="tab"><b>${articlejzw}</b> 物资</a></li>
		    </c:otherwise>
		  </c:choose>
		    <c:choose>
	  	  	<c:when test="${articlejzg=='0'}">
	          <li class="fl"><a aria-expanded="false" href="#tab-7" data-toggle="tab" > 工程</a></li>
	        </c:when>
		    <c:otherwise>
		      <li class="fl"><a aria-expanded="false" href="#tab-7" data-toggle="tab" ><b>${articlejzg}</b> 工程</a></li>
		    </c:otherwise>
		  </c:choose>
		  <c:choose>
	  	  	<c:when test="${articlejzf=='0'}">
	          <li class="fl"><a aria-expanded="false" href="#tab-8" data-toggle="tab" > 服务</a></li>
	        </c:when>
		    <c:otherwise>
		      <li class="fl"><a aria-expanded="false" href="#tab-8" data-toggle="tab" ><b>${articlejzf}</b> 服务</a></li>
		    </c:otherwise>
		  </c:choose>
		  <c:choose>
	  	  	<c:when test="${articlejzj=='0'}">
	          <li class="fl"><a aria-expanded="false" href="#tabs-8" data-toggle="tab" > 进口</a></li>
	        </c:when>
		    <c:otherwise>
		      <li class="fl"><a aria-expanded="false" href="#tabs-8" data-toggle="tab" ><b>${articlejzj}</b> 进口</a></li>
		    </c:otherwise>
		  </c:choose>
		  </ul>
          </h2>
            <div class=" tab-content">
            
            <div id="tab-czList" class="tab-pane fade active in">
                <ul class="categories articleover">
                  <c:forEach items="${indexMapper['czList']}" var="sl">
                  	<c:choose>
                      <c:when  test="${sl.lastArticleType.name=='公开招标'||sl.lastArticleType.name=='邀请招标'||sl.lastArticleType.name=='竞争性谈判'||sl.lastArticleType.name=='询价'}">
                      <li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${sl.name}</a>
                      </li>
                      </c:when>
                      <c:otherwise>
                      	<li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${sl.name}</a>
                      	</li>
                      </c:otherwise>
                      </c:choose>  
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectAllByTabs.html?articleTypeId=44&secondArticleTypeId=45">更多>></a>
              </div>
            
              <div id="tab-6" class="tab-pane fade">
                <ul class="categories articleover">
                  <c:forEach items="${indexMapper['select46List']}" var="sl">
                  	<c:choose>
                      <c:when  test="${sl.lastArticleType.name=='公开招标'||sl.lastArticleType.name=='邀请招标'||sl.lastArticleType.name=='竞争性谈判'||sl.lastArticleType.name=='询价'}">
                      <li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${sl.name}</a>
                      </li>
                      </c:when>
                      <c:otherwise>
                      	<li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${sl.name}</a>
                      	</li>
                      </c:otherwise>
                      </c:choose>  
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByParId.html?id=46&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("物资","utf-8"),"utf-8") %>">更多>></a>
              </div>
              <div id="tab-7" class="tab-pane fade">
                <ul class="categories articleover">
                  <c:forEach items="${indexMapper['select51List']}" var="sl">
                  	<c:choose>
                      <c:when  test="${sl.lastArticleType.name=='公开招标'||sl.lastArticleType.name=='邀请招标'||sl.lastArticleType.name=='竞争性谈判'||sl.lastArticleType.name=='询价'}">
                      <li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${sl.name}</a>
                      </li>
                      </c:when>
                      <c:otherwise>
                      	<li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${sl.name}</a>
                      	</li>
                      </c:otherwise>
                      </c:choose>  
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByParId.html?id=51&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("工程","utf-8"),"utf-8") %>">更多>></a>
              </div>
              <div id="tab-8" class="tab-pane fade">
                <ul class="categories articleover">
                  <c:forEach items="${indexMapper['select56List']}" var="sl">
                  	<c:choose>
                      <c:when  test="${sl.lastArticleType.name=='公开招标'||sl.lastArticleType.name=='邀请招标'||sl.lastArticleType.name=='竞争性谈判'||sl.lastArticleType.name=='询价'}">
                      <li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${sl.name}</a>
                      </li>
                      </c:when>
                      <c:otherwise>
                      	<li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${sl.name}</a>
                      	</li>
                      </c:otherwise>
                      </c:choose>  
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByParId.html?id=56&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("服务","utf-8"),"utf-8") %>">更多>></a>
              </div>
              <div id="tabs-8" class="tab-pane fade">
                <ul class="categories articleover">
                  <c:forEach items="${indexMapper['select61List']}" var="sl">
                  	<c:choose>
                      <c:when  test="${sl.lastArticleType.name=='公开招标'||sl.lastArticleType.name=='邀请招标'||sl.lastArticleType.name=='竞争性谈判'||sl.lastArticleType.name=='询价'}">
                      <li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${sl.name}</a>
                      </li>
                      </c:when>
                      <c:otherwise>
                      	<li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${sl.name}</a>
                      	</li>
                      </c:otherwise>
                      </c:choose>  
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByParId.html?id=61&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("进口","utf-8"),"utf-8") %>">更多>></a>
              </div>
            </div>
          </div>
        </div>

        <div class="col-md-4 col-sm-12 col-xs-12 mb10 login_box">
          <div class=" border1 job-content floor_kind floor_news">
            <h2>
		  <ul class="list-unstyled col-md-12 col-sm-12 col-xs-12 buyer_news m0 p0">
		  <li class="active fl report_title"><a aria-expanded="true" href="#tab-jdList" data-toggle="tab">单一来源公示</a></li>
		  <c:choose>
	  	  	<c:when test="${articlejdw=='0'}">
	          <li  class="fl"><a aria-expanded="true" href="#tab-9" data-toggle="tab"> 物资</a></li>
	        </c:when>
		    <c:otherwise>
		      <li  class="fl"><a aria-expanded="true" href="#tab-9" data-toggle="tab"><b>${articlejdw}</b> 物资</a></li>
		    </c:otherwise>
		  </c:choose>
		  <c:choose>
	  	  	<c:when test="${articlejdg=='0'}">
	          <li class="fl"><a aria-expanded="false" href="#tab-10" data-toggle="tab" > 工程</a></li>
	        </c:when>
		    <c:otherwise>
		      <li class="fl"><a aria-expanded="false" href="#tab-10" data-toggle="tab" ><b>${articlejdg}</b> 工程</a></li>
		    </c:otherwise>
		  </c:choose>
		  <c:choose>
	  	  	<c:when test="${articlejdf=='0'}">
	          <li class="fl"><a aria-expanded="false" href="#tab-11" data-toggle="tab" > 服务</a></li>
	        </c:when>
		    <c:otherwise>
		      <li class="fl"><a aria-expanded="false" href="#tab-11" data-toggle="tab" ><b>${articlejdf}</b> 服务</a></li>
		    </c:otherwise>
		  </c:choose>
		  <c:choose>
	  	  	<c:when test="${articlejdj=='0'}">
	          <li class="fl"><a aria-expanded="false" href="#tabs-11" data-toggle="tab" > 进口</a></li>
	        </c:when>
		    <c:otherwise>
		      <li class="fl"><a aria-expanded="false" href="#tabs-11" data-toggle="tab" ><b>${articlejdj}</b> 进口</a></li>
		    </c:otherwise>
		  </c:choose>
		  </ul>
          </h2>
            <div class=" tab-content">
            
            <div id="tab-jdList" class="tab-pane fade active in">
                <ul class="categories articleover">
                  <c:forEach items="${indexMapper['jdList']}" var="sl">
                    <%--<c:set value="${sl.name}" var="name"></c:set>
                    <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a>
                      </li>
                    </c:if>
                    <c:if test="${length<=23}">
                      --%><li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${sl.name}</a>
                      </li>
                    <%--</c:if>
                  --%></c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectAllByTabs.html?articleTypeId=87&secondArticleTypeId=88&flag=1">更多>></a>
              </div>
            
              <div id="tab-9" class="tab-pane fade">
                <ul class="categories articleover">
                  <c:forEach items="${indexMapper['select89List']}" var="sl">
                    <%--<c:set value="${sl.name}" var="name"></c:set>
                    <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a>
                      </li>
                    </c:if>
                    <c:if test="${length<=23}">
                      --%><li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${sl.name}</a>
                      </li>
                    <%--</c:if>
                  --%></c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=89&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("物资","utf-8"),"utf-8") %>">更多>></a>
              </div>
              <div id="tab-10" class="tab-pane fade">
                <ul class="categories articleover">
                  <c:forEach items="${indexMapper['select90List']}" var="sl">
                    <%--<c:set value="${sl.name}" var="name"></c:set>
                    <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a>
                      </li>
                    </c:if>
                    <c:if test="${length<=23}">
                      --%><li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${sl.name}</a>
                      </li>
                    <%--</c:if>
                  --%></c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=90&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("工程","utf-8"),"utf-8") %>">更多>></a>
              </div>
              <div id="tab-11" class="tab-pane fade">
                <ul class="categories articleover">
                  <c:forEach items="${indexMapper['select91List']}" var="sl">
                    <%--<c:set value="${sl.name}" var="name"></c:set>
                    <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a>
                      </li>
                    </c:if>
                    <c:if test="${length<=23}">
                      --%><li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${sl.name}</a>
                      </li>
                    <%--</c:if>
                  --%></c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=91&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("服务","utf-8"),"utf-8") %>">更多>></a>
              </div>
              <div id="tabs-11" class="tab-pane fade">
                <ul class="categories articleover">
                  <c:forEach items="${indexMapper['select92List']}" var="sl">
                    <%--<c:set value="${sl.name}" var="name"></c:set>
                    <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a>
                      </li>
                    </c:if>
                    <c:if test="${length<=23}">
                      --%><li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${sl.name}</a>
                      </li>
                    <%--</c:if>
                  --%></c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=92&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("进口","utf-8"),"utf-8") %>">更多>></a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!--部队采购开始-->

    <div class="container">
      <div class="row magazine-page clear">
        <div class="col-sm-12 col-md-12 col-xs-12">
          <h2 class="floor_title col-sm-12 col-md-12 col-xs-12">部队采购</h2>
        </div>
      </div>
      <div class="row magazine-page clear">
        <div class="col-md-4 col-sm-12 col-xs-12 mb10 login_box">
          <div class=" border1 job-content floor_kind floor_news">
            <h2>
		  <ul class="list-unstyled col-md-12 col-sm-12 col-xs-12 buyer_news m0 p0">
		  <li class="active fl report_title"><a aria-expanded="true" href="#tab-cbList" data-toggle="tab">采购公告</a></li>
		  <c:choose>
	  	  	<c:when test="${articlebcw=='0'}">
	          <li  class="fl"><a aria-expanded="true" href="#tab-12" data-toggle="tab"> 物资</a></li>
	        </c:when>
		    <c:otherwise>
		      <li  class="fl"><a aria-expanded="true" href="#tab-12" data-toggle="tab"><b>${articlebcw}</b> 物资</a></li>
		    </c:otherwise>
		  </c:choose>
		  <c:choose>
	  	  	<c:when test="${articlebcg=='0'}">
	          <li class="fl"><a aria-expanded="false" href="#tab-13" data-toggle="tab" > 工程</a></li>
	        </c:when>
		    <c:otherwise>
		      <li class="fl"><a aria-expanded="false" href="#tab-13" data-toggle="tab" ><b>${articlebcg}</b> 工程</a></li>
		    </c:otherwise>
		  </c:choose>
		  <c:choose>
	  	  	<c:when test="${articlebcf=='0'}">
	          <li class="fl"><a aria-expanded="false" href="#tab-14" data-toggle="tab" > 服务</a></li>
	        </c:when>
		    <c:otherwise>
		      <li class="fl"><a aria-expanded="false" href="#tab-14" data-toggle="tab" ><b>${articlebcf}</b> 服务</a></li>
		    </c:otherwise>
		  </c:choose>
		  <c:choose>
	  	  	<c:when test="${articlebcj=='0'}">
	          <li class="fl"><a aria-expanded="false" href="#tabs-14" data-toggle="tab" > 进口</a></li>
	        </c:when>
		    <c:otherwise>
		      <li class="fl"><a aria-expanded="false" href="#tabs-14" data-toggle="tab" ><b>${articlebcj}</b> 进口</a></li>
		    </c:otherwise>
		  </c:choose>
		  </ul>
          </h2>
            <div class=" tab-content">
            
            <div id="tab-cbList" class="tab-pane fade active in">
                <ul class="categories articleover">
                  <c:forEach items="${indexMapper['cbList']}" var="sl">
                    <c:choose>
                      <c:when  test="${sl.lastArticleType.name=='公开招标'||sl.lastArticleType.name=='邀请招标'||sl.lastArticleType.name=='竞争性谈判'||sl.lastArticleType.name=='询价'}">
                      <li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${sl.name}</a>
                      </li>
                      </c:when>
                      <c:otherwise>
                      	<li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${sl.name}</a>
                      	</li>
                      </c:otherwise>
                      </c:choose>
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectAllByTabs.html?articleTypeId=1&secondArticleTypeId=23">更多>></a>
              </div>
            
              <div id="tab-12" class="tab-pane fade">
                <ul class="categories articleover">
                  <c:forEach items="${indexMapper['select24List']}" var="sl">
                  	<c:choose>
                      <c:when  test="${sl.lastArticleType.name=='公开招标'||sl.lastArticleType.name=='邀请招标'||sl.lastArticleType.name=='竞争性谈判'||sl.lastArticleType.name=='询价'}">
                      <li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${sl.name}</a>
                      </li>
                      </c:when>
                      <c:otherwise>
                      	<li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${sl.name}</a>
                      	</li>
                      </c:otherwise>
                      </c:choose>  
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByParId.html?id=24&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("物资","utf-8"),"utf-8") %>">更多>></a>
              </div>
              <div id="tab-13" class="tab-pane fade">
                <ul class="categories articleover">
                  <c:forEach items="${indexMapper['select29List']}" var="sl">
                    <c:choose>
                      <c:when  test="${sl.lastArticleType.name=='公开招标'||sl.lastArticleType.name=='邀请招标'||sl.lastArticleType.name=='竞争性谈判'||sl.lastArticleType.name=='询价'}">
                      <li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${sl.name}</a>
                      </li>
                      </c:when>
                      <c:otherwise>
                      	<li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${sl.name}</a>
                      	</li>
                      </c:otherwise>
                      </c:choose>
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByParId.html?id=29&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("工程","utf-8"),"utf-8") %>">更多>></a>
              </div>
              <div id="tab-14" class="tab-pane fade">
                <ul class="categories articleover">
                  <c:forEach items="${indexMapper['select34List']}" var="sl">
                  	<c:choose>
                      <c:when  test="${sl.lastArticleType.name=='公开招标'||sl.lastArticleType.name=='邀请招标'||sl.lastArticleType.name=='竞争性谈判'||sl.lastArticleType.name=='询价'}">
                      <li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${sl.name}</a>
                      </li>
                      </c:when>
                      <c:otherwise>
                      	<li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${sl.name}</a>
                      	</li>
                      </c:otherwise>
                      </c:choose>  
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByParId.html?id=34&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("服务","utf-8"),"utf-8") %>">更多>></a>
              </div>
              <div id="tabs-14" class="tab-pane fade">
                <ul class="categories articleover">
                  <c:forEach items="${indexMapper['select39List']}" var="sl">
                  	<c:choose>
                      <c:when  test="${sl.lastArticleType.name=='公开招标'||sl.lastArticleType.name=='邀请招标'||sl.lastArticleType.name=='竞争性谈判'||sl.lastArticleType.name=='询价'}">
                      <li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${sl.name}</a>
                      </li>
                      </c:when>
                      <c:otherwise>
                      	<li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${sl.name}</a>
                      	</li>
                      </c:otherwise>
                      </c:choose>  
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByParId.html?id=39&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("进口","utf-8"),"utf-8") %>">更多>></a>
              </div>
            </div>
          </div>
        </div>

        <div class="col-md-4 col-sm-12 col-xs-12 mb10 login_box">
          <div class=" border1 job-content floor_kind floor_news">
            <h2>
		  <ul class="list-unstyled col-md-12 col-sm-12 col-xs-12 buyer_news m0 p0">
		  <li class="active fl report_title"><a aria-expanded="true" href="#tab-bzList" data-toggle="tab">中标公示</a></li>
		  <c:choose>
	  	  	<c:when test="${articlebzw=='0'}">
	          <li  class="fl"><a aria-expanded="true" href="#tab-15" data-toggle="tab"> 物资</a></li>
	        </c:when>
		    <c:otherwise>
		      <li  class="fl"><a aria-expanded="true" href="#tab-15" data-toggle="tab"><b>${articlebzw}</b> 物资</a></li>
		    </c:otherwise>
		  </c:choose>
		    <c:choose>
	  	  	<c:when test="${articlebzg=='0'}">
	          <li class="fl"><a aria-expanded="false" href="#tab-16" data-toggle="tab" > 工程</a></li>
	        </c:when>
		    <c:otherwise>
		      <li class="fl"><a aria-expanded="false" href="#tab-16" data-toggle="tab" ><b>${articlebzg}</b> 工程</a></li>
		    </c:otherwise>
		  </c:choose>
		  <c:choose>
	  	  	<c:when test="${articlebzf=='0'}">
	          <li class="fl"><a aria-expanded="false" href="#tab-17" data-toggle="tab" > 服务</a></li>
	        </c:when>
		    <c:otherwise>
		      <li class="fl"><a aria-expanded="false" href="#tab-17" data-toggle="tab" ><b>${articlebzf}</b> 服务</a></li>
		    </c:otherwise>
		  </c:choose>
		  <c:choose>
	  	  	<c:when test="${articlebzj=='0'}">
	          <li class="fl"><a aria-expanded="false" href="#tabs-17" data-toggle="tab" > 进口</a></li>
	        </c:when>
		    <c:otherwise>
		      <li class="fl"><a aria-expanded="false" href="#tabs-17" data-toggle="tab" ><b>${articlebzj}</b> 进口</a></li>
		    </c:otherwise>
		  </c:choose>
		  </ul>
          </h2>
            <div class=" tab-content">
            
            <div id="tab-bzList" class="tab-pane fade active in">
                <ul class="categories articleover">
                  <c:forEach items="${indexMapper['bzList']}" var="sl">
                  	<c:choose>
                      <c:when  test="${sl.lastArticleType.name=='公开招标'||sl.lastArticleType.name=='邀请招标'||sl.lastArticleType.name=='竞争性谈判'||sl.lastArticleType.name=='询价'}">
                      <li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${sl.name}</a>
                      </li>
                      </c:when>
                      <c:otherwise>
                      	<li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${sl.name}</a>
                      	</li>
                      </c:otherwise>
                      </c:choose>
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectAllByTabs.html?articleTypeId=44&secondArticleTypeId=66">更多>></a>
              </div>
            
              <div id="tab-15" class="tab-pane fade">
                <ul class="categories articleover">
                  <c:forEach items="${indexMapper['select67List']}" var="sl">
                      <c:choose>
                      <c:when  test="${sl.lastArticleType.name=='公开招标'||sl.lastArticleType.name=='邀请招标'||sl.lastArticleType.name=='竞争性谈判'||sl.lastArticleType.name=='询价'}">
                      <li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${sl.name}</a>
                      </li>
                      </c:when>
                      <c:otherwise>
                      	<li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${sl.name}</a>
                      	</li>
                      </c:otherwise>
                      </c:choose>
                    </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByParId.html?id=67&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("物资","utf-8"),"utf-8") %>">更多>></a>
              </div>
              <div id="tab-16" class="tab-pane fade">
                <ul class="categories articleover">
                  <c:forEach items="${indexMapper['select72List']}" var="sl">
                  	<c:choose>
                      <c:when  test="${sl.lastArticleType.name=='公开招标'||sl.lastArticleType.name=='邀请招标'||sl.lastArticleType.name=='竞争性谈判'||sl.lastArticleType.name=='询价'}">
                      <li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${sl.name}</a>
                      </li>
                      </c:when>
                      <c:otherwise>
                      	<li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${sl.name}</a>
                      	</li>
                      </c:otherwise>
                      </c:choose>  
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByParId.html?id=72&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("工程","utf-8"),"utf-8") %>">更多>></a>
              </div>
              <div id="tab-17" class="tab-pane fade">
                <ul class="categories articleover">
                  <c:forEach items="${indexMapper['select77List']}" var="sl">
                  	<c:choose>
                      <c:when  test="${sl.lastArticleType.name=='公开招标'||sl.lastArticleType.name=='邀请招标'||sl.lastArticleType.name=='竞争性谈判'||sl.lastArticleType.name=='询价'}">
                      <li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${sl.name}</a>
                      </li>
                      </c:when>
                      <c:otherwise>
                      	<li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${sl.name}</a>
                      	</li>
                      </c:otherwise>
                      </c:choose>  
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByParId.html?id=77&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("服务","utf-8"),"utf-8") %>">更多>></a>
              </div>
              <div id="tabs-17" class="tab-pane fade">
                <ul class="categories articleover">
                  <c:forEach items="${indexMapper['select82List']}" var="sl">
                  	<c:choose>
                      <c:when  test="${sl.lastArticleType.name=='公开招标'||sl.lastArticleType.name=='邀请招标'||sl.lastArticleType.name=='竞争性谈判'||sl.lastArticleType.name=='询价'}">
                      <li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${sl.name}</a>
                      </li>
                      </c:when>
                      <c:otherwise>
                      	<li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${sl.name}</a>
                      	</li>
                      </c:otherwise>
                      </c:choose>  
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByParId.html?id=82&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("进口","utf-8"),"utf-8") %>">更多>></a>
              </div>
            </div>
          </div>
        </div>
        <div class="col-md-4 col-sm-12 col-xs-12 mb10 login_box">
          <div class=" border1 job-content floor_kind floor_news">
            <h2>
		  <ul class="list-unstyled col-md-12 col-sm-12 col-xs-12 buyer_news m0 p0">
		  <li class="active fl report_title"><a aria-expanded="true" href="#tab-bdList" data-toggle="tab">单一来源公示</a></li>
		  <c:choose>
	  	  	<c:when test="${articlebdw=='0'}">
	          <li class="fl"><a aria-expanded="true" href="#tab-18" data-toggle="tab"> 物资</a></li>
	        </c:when>
		    <c:otherwise>
		      <li class="fl"><a aria-expanded="true" href="#tab-18" data-toggle="tab"><b>${articlebdw}</b> 物资</a></li>
		    </c:otherwise>
		  </c:choose>
		  <c:choose>
	  	  	<c:when test="${articlebdg=='0'}">
	          <li class="fl"><a aria-expanded="false" href="#tab-19" data-toggle="tab" > 工程</a></li>
	        </c:when>
		    <c:otherwise>
		      <li class="fl"><a aria-expanded="false" href="#tab-19" data-toggle="tab" ><b>${articlebdg}</b> 工程</a></li>
		    </c:otherwise>
		  </c:choose>
		  <c:choose>
	  	  	<c:when test="${articlebdf=='0'}">
	          <li class="fl"><a aria-expanded="false" href="#tab-20" data-toggle="tab" > 服务</a></li>
	        </c:when>
		    <c:otherwise>
		      <li class="fl"><a aria-expanded="false" href="#tab-20" data-toggle="tab" ><b>${articlebdf}</b> 服务</a></li>
		      </c:otherwise>
		  </c:choose>
		  <c:choose>
	  	  	<c:when test="${articlebdj=='0'}">
	          <li class="fl"><a aria-expanded="false" href="#tabs-20" data-toggle="tab" > 进口</a></li>
	        </c:when>
		    <c:otherwise>
		      <li class="fl"><a aria-expanded="false" href="#tabs-20" data-toggle="tab" ><b>${articlebdj}</b> 进口</a></li>
		      </c:otherwise>
		  </c:choose>
		  </ul>
          </h2>
            <div class=" tab-content">
            
            <div id="tab-bdList" class="tab-pane fade active in">
                <ul class="categories articleover">
                  <c:forEach items="${indexMapper['bdList']}" var="sl">
                    <%--<c:set value="${sl.name}" var="name"></c:set>
                    <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a>
                      </li>
                    </c:if>
                    <c:if test="${length<=23}">
                      --%><li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${sl.name}</a>
                      </li>
                    <%--</c:if>
                  --%></c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectAllByTabs.html?articleTypeId=87&secondArticleTypeId=93&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("物资","utf-8"),"utf-8") %>&flag=1">更多>></a>
              </div>
            
              <div id="tab-18" class="tab-pane fade">
                <ul class="categories articleover">
                  <c:forEach items="${indexMapper['select94List']}" var="sl">
                    <%--<c:set value="${sl.name}" var="name"></c:set>
                    <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a>
                      </li>
                    </c:if>
                    <c:if test="${length<=23}">
                      --%><li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${sl.name}</a>
                      </li>
                    <%--</c:if>
                  --%></c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=94&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("工程","utf-8"),"utf-8") %>">更多>></a>
              </div>
              <div id="tab-19" class="tab-pane fade">
                <ul class="categories articleover">
                  <c:forEach items="${indexMapper['select95List']}" var="sl">
                    <%--<c:set value="${sl.name}" var="name"></c:set>
                    <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a>
                      </li>
                    </c:if>
                    <c:if test="${length<=23}">
                      --%><li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${sl.name}</a>
                      </li>
                    <%--</c:if>
                  --%></c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=95&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("服务","utf-8"),"utf-8") %>">更多>></a>
              </div>
              <div id="tab-20" class="tab-pane fade">
                <ul class="categories articleover">
                  <c:forEach items="${indexMapper['select96List']}" var="sl">
                    <%--<c:set value="${sl.name}" var="name"></c:set>
                    <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a>
                      </li>
                    </c:if>
                    <c:if test="${length<=23}">
                      --%><li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${sl.name}</a>
                      </li>
                    <%--</c:if>
                  --%></c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=96&tab=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("进口","utf-8"),"utf-8") %>">更多>></a>
              </div>
              <div id="tabs-20" class="tab-pane fade">
                <ul class="categories articleover">
                  <c:forEach items="${indexMapper['select97List']}" var="sl">
                    <%--<c:set value="${sl.name}" var="name"></c:set>
                    <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a>
                      </li>
                    </c:if>
                    <c:if test="${length<=23}">
                      --%><li>
                        <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${sl.name}</a>
                      </li>
                    <%--</c:if>
                  --%></c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=97">更多>></a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!--业务办理开始-->
    <div class="container">
      <div class="row magazine-page clear">
        <div class="col-sm-12 col-md-12 col-xs-12">
          <h2 class="floor_title col-sm-12 col-md-12 col-xs-12">业务办理</h2>
        </div>
      </div>

      <div class="row magazine-page clear">
        <div class="col-sm-12 col-md-12 col-xs-12">
          <div class="flow_btn fl flow_one">
            <div class="ywbl_01 col-xs-6 col-sm-6">
              <% if (ipAddressType != null && ipAddressType.equals("1")){ %>
              <a href="${pageContext.request.contextPath}/supplier/registration_page.html" class="qyzc">
              <% } %>
              <% if (ipAddressType != null && ipAddressType.equals("0")){ %>
              <a onclick="supplierRegisterTip();" class="qyzc">
              <% } %>
    	        	<i></i>
                <span>供应商注册</span>
              </a>
            </div>

            <div class="ywbl_01 col-xs-6 col-sm-6">
              <a href="${pageContext.request.contextPath}/index/indexSupPublicity.html" class="nrkgyshgsh">
                <i></i>
                <span>拟入库供应商公示</span>
              </a>
            </div>

            <div class="ywbl_01 col-xs-6 col-sm-6">
  	        	<a href="${pageContext.request.contextPath}/expert/toRegisterNotice.html" class="zjzc">
                <i></i>
                <span>评审专家注册</span>
              </a>
            </div>

            <div class="ywbl_01 col-xs-6 col-sm-6">
              <a href="${pageContext.request.contextPath}/index/indexExpPublicity.html" class="nrkzhjgsh">
                <i></i>
                <span>拟入库专家公示</span>
              </a>
            </div>

            <div class="ywbl_01 col-xs-12 col-sm-12">
              <a href="${pageContext.request.contextPath}/index/sign.html" onclick="importAdd()" class="jksdj">
                <i></i>
                <span>进口商登记</span>
              </a>
            </div>
          </div>

          <div class="flow_btn fl">
            <div class="ywbl_01 col-xs-4 col-sm-4">
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

            <div class="ywbl_01 col-xs-4 col-sm-4">
              <a href="${pageContext.request.contextPath }/index/index_productList.html" class="dxcpjj"><!--  onclick="setting()" -->
                <i></i>
                <span>定型产品</span>
              </a>
            </div>

            <div class="ywbl_01 col-xs-4 col-sm-4">
              <a href="javascript:void(0)" onclick="drugs()" class="ypcg">
                <i></i>
                <span>药品采购</span>
              </a>
            </div>
          </div>
          
          <div class="border1 flow_btn">
            <div class="ywbl_02 col-xs-4 col-sm-4">
              <a href="javascript:void(0)" class="fwcg">
                <i></i>
                <span>服务采购</span>
              </a>
            </div>

            <div class="ywbl_02 col-xs-4 col-sm-4">
              <!-- <a href="javascript:void(0)" onclick="hotLine()" class="fwrx"> -->
              <a href="${pageContext.request.contextPath }/index/index_hotLineList.html"  class="fwrx">
                <i></i>
                <span>服务热线</span>
              </a>
            </div>

            <div class="ywbl_02 col-xs-4 col-sm-4">
              <a href="${pageContext.request.contextPath }/categorys/categoryList.html" class="cpml">
                <i></i>
                <span>产品目录</span>
              </a>
            </div>

            <div class="ywbl_02 col-xs-4 col-sm-4">
              <a href="${pageContext.request.contextPath }/categorys/parameterList.html" class="jscsk">
                <i></i>
                <span>技术参数库</span>
              </a>
            </div>

            <div class="ywbl_02 col-xs-4 col-sm-4">
              <% if (ipAddressType != null && ipAddressType.equals("0")){ %>
              <a href="${pageContext.request.contextPath }/dataDownload/getIndexList.html" class="zlxz">
              <% } %>
              <% if (ipAddressType != null && ipAddressType.equals("1")){ %>
              <a onclick="javascript:void(0);" class="zjzc">
              <% } %>
                <i></i>
                <span>资料下载</span>
              </a>
            </div>

            <div class="ywbl_02 col-xs-4 col-sm-4">
              <a href="javascript:void(0)" class="cpshfw">
                <i></i>
                <span>产品售后服务</span>
              </a>
            </div>

            <div class="ywbl_02 col-xs-4 col-sm-4">
              <a href="${pageContext.request.contextPath}/onlineComplaints/index_add.html" class="zxts">
                <i></i>
                <span>网上投诉</span>
              </a>
            </div>

            <div class="ywbl_02 col-xs-4 col-sm-4">
              <a <% if (ipAddressType != null && ipAddressType.equals("0")){ %>
               href="${pageContext.request.contextPath}/park/getIndex.html"
              <% } %> class="cglt">
                <i></i>
                <span>采购论坛</span>
              </a>
            </div>

            <div class="ywbl_02 col-xs-4 col-sm-4">
              <a href="javascript:void(0)" class="yjfk">
                <i></i>
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
        <div class="col-sm-12 col-md-12 col-xs-12">
          <h2 class="floor_title col-sm-12 col-md-12 col-xs-12">采购信息</h2>
        </div>
      </div>
      <div class="row magazine-page clear">
        <div class="col-md-3 mb10 login_box">
          <div class=" border1 job-content floor_kind h240">
            <div class="cgxx_report">
		     <div class="report-tab">重要通知
		     	<c:if test="${articleZytz != 0}">
				      <b>${articleZytz}</b>
			      </c:if>
		     </div>
		     <a class="news_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=109">更多>></a>
            </div>
            <div class="categories articleover">
              <ul class="p0_10">
                <c:forEach items="${indexMapper['select109List']}" var="sl">
                  <%--<c:set value="${sl.name}" var="name"></c:set>
                  <c:set value="${fn:length(name)}" var="length"></c:set>
                  <c:if test="${length>17}">
                    <li>
                      <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${fn:substring(name,0,17)}...</a>
                    </li>
                  </c:if>
                  <c:if test="${length<=17}">
                    --%><li>
                      <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${sl.name}</a>
                    </li>
                  <%--</c:if>
                --%></c:forEach>
              </ul>
            </div>
          </div>
        </div>
        <div class="col-md-3 mb10 login_box">
          <div class=" border1 job-content floor_kind h240">
           <div class="cgxx_report">
		   <div class="report-tab">采购法规
		   		<c:if test="${articleCgfg != 0}">
				      <b>${articleCgfg}</b>
			      </c:if>
		   </div>
		   <a class="news_more" href="${pageContext.request.contextPath}/index/selectsumByParId.html">更多>></a>
           </div>
            <div class="categories">
              <ul class="p0_10">
                <c:forEach items="${indexMapper['faguiList']}" var="sl">
                  <c:set value="${sl.name}" var="name"></c:set>
                  <c:set value="${fn:length(name)}" var="length"></c:set>
                  <c:if test="${length>17}">
                    <li>
                      <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${fn:substring(name,0,17)}...</a>
                    </li>
                  </c:if>
                  <c:if test="${length<=17}">
                    <li>
                      <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${name}</a>
                    </li>
                  </c:if>
                </c:forEach>
              </ul>
            </div>
          </div>
        </div>
        <div class="col-md-3 mb10 login_box">
          <div class=" border1 job-content floor_kind h240">
           <div class="cgxx_report">
		    <div class="report-tab">投诉处理公告
		    	<c:if test="${articleTouSu != 0}">
				    <b>${articleTouSu}</b>
		    	</c:if>
		    </div>
		    <a class="news_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=112">更多>></a>
           </div>
            <div class="categories articleover">
              <ul class="p0_10">
                <c:forEach items="${indexMapper['select112List']}" var="sl">
                  <%--<c:set value="${sl.name}" var="name"></c:set>
                  <c:set value="${fn:length(name)}" var="length"></c:set>
                  <c:if test="${length>17}">
                    <li>
                      <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${fn:substring(name,0,17)}...</a>
                    </li>
                  </c:if>
                  <c:if test="${length<=17}">
                    --%><li>
                      <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${sl.name}</a>
                    </li>
                  <%--</c:if>
                --%></c:forEach>
              </ul>
            </div>
          </div>
        </div>
        <div class="col-md-3 mb10 login_box">
          <div class=" border1 job-content floor_kind h240">
            <div class="cgxx_report">
		      <div class="report-tab">处罚公告
			      <c:if test="${articleChuFa != 0}">
				      <b>${articleChuFa}</b>
			      </c:if>
		      </div>
		      <a class="news_more" href="${pageContext.request.contextPath}/index/selectIndexChuFaNewsByTypeId.html?id=113">更多>></a>
            </div>
            <div class="categories articleover">
              <ul class="p0_10">
                <c:forEach items="${indexMapper['select113List']}" var="sl">
                  <%--<c:set value="${sl.name}" var="name"></c:set>
                  <c:set value="${fn:length(name)}" var="length"></c:set>
                  <c:if test="${length>17}">
                    <li>
                      <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${fn:substring(name,0,17)}...</a>
                    </li>
                  </c:if>
                  <c:if test="${length<=17}">
                    --%><li>
                      <a title="${sl.name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${sl.name}</a>
                    </li>
                  <%--</c:if>
                --%></c:forEach>
              </ul>
            </div>
         </div>
        </div>
      <div class="visit_count">
      	<span class="visit_title">访问量</span>
      	<span class="visit_clip col-md-offset-1 col-sm-offset-1"><b></b>今日访问量：<i id="pvThisDay"></i>次</span>
      	<span class="visit_clip col-md-offset-1 col-sm-offset-1"><b></b>总访问量：<i id="pvTotal"></i>次</span>
      </div>
      <!-- <a href="${pageContext.request.contextPath}/index/init.html">初始化solr</a> -->

	</div>
	</div>
    <!--底部代码开始-->
    <jsp:include page="/index_bottom.jsp"></jsp:include>
  </body>

</html>