<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
<!--<![endif]-->
  <head>

    <jsp:include page="/index_head.jsp"></jsp:include>

    <script type="text/javascript">
      $(function() {
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
        if((browser == "Netscape" || browser == "Microsoft Internet Explorer") && (version <= 4) && msie_ver < 7) {
          window.location.href = "${pageContext.request.contextPath}/browser/index.html";
        } else {
          $("#firstPage").attr("Class", "active dropdown shouye_li mega-menu-fullwidth");

          $(".header-v4 .navbar-default .navbar-nav > .other > a").hover(function() {
            $("#firstPage").attr("Class", "dropdown shouye_li mega-menu-fullwidth");
          });
        }

      })

      function kaptcha() {
        $("#kaptchaImage").hide().attr('src', 'Kaptcha.jpg?' + Math.floor(Math.random() * 100)).fadeIn();
      }

      function setting() {
        layer.msg("正在建设中");
      }

      function drugs() {
        layer.msg("正在建设中");
      }
      
      function registerTip(){
		layer.msg("暂未开放，请耐心等待！");
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
              <c:choose>
              	<c:when test="${picSize<=0}">
	              	<li class="item" style="left:0px;">
	                  <a href="javascript:void(0)" target="_blank"><img src="${pageContext.request.contextPath}/public/portal/images/1.jpg" width="100%" height="100%"></a>
	                </li>
	                <li class="item">
	                  <a href="javascript:void(0)" target="_blank"><img src="${pageContext.request.contextPath}/public/portal/images/2.jpg" width="100%" height="100%"></a>
	                </li>
	                <li class="item">
	                  <a href="javascript:void(0)" target="_blank"><img src="${pageContext.request.contextPath}/public/portal/images/3.jpg" width="100%" height="100%"></a>
	                </li>
	            </c:when>
	            <c:otherwise>
	            	<c:forEach items="${indexMapper['picList']}" var="pic" varStatus="vs">
					  	<c:choose>
					  	 <c:when test="${vs.index==0}">
						   <li class="item" style="left:0px;">
								<a href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${pic.id}" target="_blank"><img src="${pageContext.request.contextPath}/file/viewFile.html?id=${pic.uploadId}&key=${key}" width="100%" height="100%"></a>
						   </li>
						 </c:when>
						 <c:otherwise>
						   <li class="item">
								<a href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${pic.id}" target="_blank"><img src="${pageContext.request.contextPath}/file/viewFile.html?id=${pic.uploadId}&key=${key}" width="100%" height="100%"></a>
						   </li>
						 </c:otherwise>
						 </c:choose>
						</c:forEach>
	            </c:otherwise>
              </c:choose>
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
            <c:if test="${nums=='4'}">
              <b class="down">1</b>
              <b>2</b>
              <b>3</b>
              <b>4</b>
            </c:if>
            <c:if test="${nums=='5'}">
              <b class="down">1</b>
              <b>2</b>
              <b>3</b>
              <b>4</b>
              <b>5</b>
            </c:if>
            <c:if test="${nums=='6'}">
              <b class="down">1</b>
              <b>2</b>
              <b>3</b>
              <b>4</b>
              <b>5</b>
              <b>6</b>
            </c:if>
            </div>
          </div>
          <script src="${pageContext.request.contextPath}/public/portal/js/script.js"></script>
        </div>

        <div class="col-md-4 ">
          <div class="tab-v1">
            <h2 class="nav nav-tabs bb1 mt0">
            <span class="bg12_white"><a href="javascript:void(0)">工作动态</a></span>
            <a class="news_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=110">更多>></a>
		  </h2>
          </div>

          <div class="">
            <ul class="list-unstyled categories list_common">
              <c:forEach items="${indexMapper['select110List']}" var="sl">
                <c:set value="${sl.name}" var="name"></c:set>
                <c:set value="${fn:length(name)}" var="length"></c:set>
                <c:if test="${length>24}">
                  <li>
                    <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${fn:substring(name,0,24)}...</a>
                  </li>
                </c:if>
                <c:if test="${length<=24}">
                  <li>
                    <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}">${name}</a>
                  </li>
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

    <!-- /* 集中采购开始*/-->
    <div class="container">
      <div class="row magazine-page clear">
        <div class="col-md-12">
          <h2 class="floor_title col-md-12">集中采购</h2>
        </div>
      </div>
      <div class="row magazine-page clear">
        <div class="col-md-4 mb10 login_box">
          <div class=" border1 job-content floor_kind floor_news">
            <h2>
		 	  <span class="col-md-4 col-sm-4 col-xs-6">采购公告</span>
		  	  <ul class="list-unstyled col-md-8 col-sm-8 col-xs-6 buyer_news m0 p0">
		  	  <c:choose>
		  	  	<c:when test="${articlejcw=='0'}">
		          <li class="active fl"><a aria-expanded="true" href="#tab-3" data-toggle="tab">物资</a></li>
		        </c:when>
			    <c:otherwise>
			      <li class="active fl"><a aria-expanded="true" href="#tab-3" data-toggle="tab"><b>${articlejcw}</b> 物资</a></li>
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
              <div id="tab-3" class="tab-pane fade active in">
                <ul class="categories">
                  <c:forEach items="${indexMapper['select3List']}" var="sl">
                    <c:set value="${sl.name}" var="name"></c:set>
                    <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${fn:substring(name,0,16)}...</a>
                      </li>
                    </c:if>
                    <c:if test="${length<=23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${name}</a>
                      </li>
                    </c:if>
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByParId.html?id=3">更多>></a>
              </div>

              <div id="tab-4" class="tab-pane fade">
                <ul class="categories">
                  <c:forEach items="${indexMapper['select8List']}" var="sl">
                    <c:set value="${sl.name}" var="name"></c:set>
                    <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${fn:substring(name,0,16)}...</a>
                      </li>
                    </c:if>
                    <c:if test="${length<=23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${name}</a>
                      </li>
                    </c:if>
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByParId.html?id=8">更多>></a>
              </div>

              <div id="tab-5" class="tab-pane fade">
                <ul class="categories">
                  <c:forEach items="${indexMapper['select13List']}" var="sl">
                    <c:set value="${sl.name}" var="name"></c:set>
                    <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${fn:substring(name,0,16)}...</a>
                      </li>
                    </c:if>
                    <c:if test="${length<=23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${name}</a>
                      </li>
                    </c:if>
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByParId.html?id=13">更多>></a>
              </div>
              
              <div id="tabs-6" class="tab-pane fade">
                <ul class="categories">
                  <c:forEach items="${indexMapper['select18List']}" var="sl">
                    <c:set value="${sl.name}" var="name"></c:set>
                    <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${fn:substring(name,0,16)}...</a>
                      </li>
                    </c:if>
                    <c:if test="${length<=23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${name}</a>
                      </li>
                    </c:if>
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByParId.html?id=18">更多>></a>
              </div>
            </div>
          </div>
        </div>

        <div class="col-md-4 mb10 login_box">
          <div class=" border1 job-content floor_kind floor_news">
            <h2>
		  <span class="col-md-4 col-sm-4 col-xs-6">中标公告</span>
		  <ul class="list-unstyled col-md-8 col-sm-8 col-xs-6 buyer_news m0 p0">
		  <c:choose>
	  	  	<c:when test="${articlejzw=='0'}">
	          <li class="active fl"><a aria-expanded="true" href="#tab-6" data-toggle="tab"> 物资</a></li>
	        </c:when>
		    <c:otherwise>
		      <li class="active fl"><a aria-expanded="true" href="#tab-6" data-toggle="tab"><b>${articlejzw}</b> 物资</a></li>
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
              <div id="tab-6" class="tab-pane fade active in">
                <ul class="categories">
                  <c:forEach items="${indexMapper['select46List']}" var="sl">
                    <c:set value="${sl.name}" var="name"></c:set>
                    <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${fn:substring(name,0,16)}...</a>
                      </li>
                    </c:if>
                    <c:if test="${length<=23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${name}</a>
                      </li>
                    </c:if>
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByParId.html?id=46">更多>></a>
              </div>
              <div id="tab-7" class="tab-pane fade">
                <ul class="categories">
                  <c:forEach items="${indexMapper['select51List']}" var="sl">
                    <c:set value="${sl.name}" var="name"></c:set>
                    <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${fn:substring(name,0,16)}...</a>
                      </li>
                    </c:if>
                    <c:if test="${length<=23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${name}</a>
                      </li>
                    </c:if>
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByParId.html?id=51">更多>></a>
              </div>
              <div id="tab-8" class="tab-pane fade">
                <ul class="categories">
                  <c:forEach items="${indexMapper['select56List']}" var="sl">
                    <c:set value="${sl.name}" var="name"></c:set>
                    <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${fn:substring(name,0,16)}...</a>
                      </li>
                    </c:if>
                    <c:if test="${length<=23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${name}</a>
                      </li>
                    </c:if>
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByParId.html?id=56">更多>></a>
              </div>
              <div id="tabs-8" class="tab-pane fade">
                <ul class="categories">
                  <c:forEach items="${indexMapper['select61List']}" var="sl">
                    <c:set value="${sl.name}" var="name"></c:set>
                    <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${fn:substring(name,0,16)}...</a>
                      </li>
                    </c:if>
                    <c:if test="${length<=23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name】${name}</a>
                      </li>
                    </c:if>
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByParId.html?id=61">更多>></a>
              </div>
            </div>
          </div>
        </div>

        <div class="col-md-4 mb10 login_box">
          <div class=" border1 job-content floor_kind floor_news">
            <h2>
		  <span class="col-md-4 col-sm-4 col-xs-6">单一来源公告</span>
		  <ul class="list-unstyled col-md-8 col-sm-8 col-xs-6 buyer_news m0 p0">
		  <c:choose>
	  	  	<c:when test="${articlejdw=='0'}">
	          <li  class="active fl"><a aria-expanded="true" href="#tab-9" data-toggle="tab"> 物资</a></li>
	        </c:when>
		    <c:otherwise>
		      <li  class="active fl"><a aria-expanded="true" href="#tab-9" data-toggle="tab"><b>${articlejdw}</b> 物资</a></li>
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
              <div id="tab-9" class="tab-pane fade active in">
                <ul class="categories">
                  <c:forEach items="${indexMapper['select89List']}" var="sl">
                    <c:set value="${sl.name}" var="name"></c:set>
                    <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a>
                      </li>
                    </c:if>
                    <c:if test="${length<=23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${name}</a>
                      </li>
                    </c:if>
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=89">更多>></a>
              </div>
              <div id="tab-10" class="tab-pane fade">
                <ul class="categories">
                  <c:forEach items="${indexMapper['select90List']}" var="sl">
                    <c:set value="${sl.name}" var="name"></c:set>
                    <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a>
                      </li>
                    </c:if>
                    <c:if test="${length<=23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${name}</a>
                      </li>
                    </c:if>
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=90">更多>></a>
              </div>
              <div id="tab-11" class="tab-pane fade">
                <ul class="categories">
                  <c:forEach items="${indexMapper['select91List']}" var="sl">
                    <c:set value="${sl.name}" var="name"></c:set>
                    <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a>
                      </li>
                    </c:if>
                    <c:if test="${length<=23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${name}</a>
                      </li>
                    </c:if>
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=91">更多>></a>
              </div>
              <div id="tabs-11" class="tab-pane fade">
                <ul class="categories">
                  <c:forEach items="${indexMapper['select92List']}" var="sl">
                    <c:set value="${sl.name}" var="name"></c:set>
                    <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a>
                      </li>
                    </c:if>
                    <c:if test="${length<=23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${name}</a>
                      </li>
                    </c:if>
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=92">更多>></a>
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
          <h2 class="floor_title col-md-12">部队采购</h2>
        </div>
      </div>
      <div class="row magazine-page clear">
        <div class="col-md-4 mb10 login_box">
          <div class=" border1 job-content floor_kind floor_news">
            <h2>
		  <span class="col-md-4 col-sm-4 col-xs-6">采购公告</span>
		  <ul class="list-unstyled col-md-8 col-sm-8 col-xs-6 buyer_news m0 p0">
		  <c:choose>
	  	  	<c:when test="${articlebcw=='0'}">
	          <li  class="active fl"><a aria-expanded="true" href="#tab-12" data-toggle="tab"> 物资</a></li>
	        </c:when>
		    <c:otherwise>
		      <li  class="active fl"><a aria-expanded="true" href="#tab-12" data-toggle="tab"><b>${articlebcw}</b> 物资</a></li>
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
              <div id="tab-12" class="tab-pane fade active in">
                <ul class="categories">
                  <c:forEach items="${indexMapper['select24List']}" var="sl">
                    <c:set value="${sl.name}" var="name"></c:set>
                    <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${fn:substring(name,0,16)}...</a>
                      </li>
                    </c:if>
                    <c:if test="${length<=23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${name}</a>
                      </li>
                    </c:if>
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByParId.html?id=24">更多>></a>
              </div>
              <div id="tab-13" class="tab-pane fade">
                <ul class="categories">
                  <c:forEach items="${indexMapper['select29List']}" var="sl">
                    <c:set value="${sl.name}" var="name"></c:set>
                    <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${fn:substring(name,0,16)}...</a>
                      </li>
                    </c:if>
                    <c:if test="${length<=23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${name}</a>
                      </li>
                    </c:if>
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByParId.html?id=29">更多>></a>
              </div>
              <div id="tab-14" class="tab-pane fade">
                <ul class="categories">
                  <c:forEach items="${indexMapper['select34List']}" var="sl">
                    <c:set value="${sl.name}" var="name"></c:set>
                    <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${fn:substring(name,0,16)}...</a>
                      </li>
                    </c:if>
                    <c:if test="${length<=23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${name}</a>
                      </li>
                    </c:if>
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByParId.html?id=34">更多>></a>
              </div>
              <div id="tabs-14" class="tab-pane fade">
                <ul class="categories">
                  <c:forEach items="${indexMapper['select39List']}" var="sl">
                    <c:set value="${sl.name}" var="name"></c:set>
                    <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${fn:substring(name,0,16)}...</a>
                      </li>
                    </c:if>
                    <c:if test="${length<=23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${name}</a>
                      </li>
                    </c:if>
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByParId.html?id=39">更多>></a>
              </div>
            </div>
          </div>
        </div>

        <div class="col-md-4 mb10 login_box">
          <div class=" border1 job-content floor_kind floor_news">
            <h2>
		  <span class="col-md-4 col-sm-4 col-xs-6">中标公告</span>
		  <ul class="list-unstyled col-md-8 col-sm-8 col-xs-6 buyer_news m0 p0">
		  <c:choose>
	  	  	<c:when test="${articlebzw=='0'}">
	          <li  class="active fl"><a aria-expanded="true" href="#tab-15" data-toggle="tab"> 物资</a></li>
	        </c:when>
		    <c:otherwise>
		      <li  class="active fl"><a aria-expanded="true" href="#tab-15" data-toggle="tab"><b>${articlebzw}</b> 物资</a></li>
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
              <div id="tab-15" class="tab-pane fade active in">
                <ul class="categories">
                  <c:forEach items="${indexMapper['select67List']}" var="sl">
                    <c:set value="${sl.name}" var="name"></c:set>
                    <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${fn:substring(name,0,16)}...</a>
                      </li>
                    </c:if>
                    <c:if test="${length<=23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${name}</a>
                      </li>
                    </c:if>
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByParId.html?id=67">更多>></a>
              </div>
              <div id="tab-16" class="tab-pane fade">
                <ul class="categories">
                  <c:forEach items="${indexMapper['select72List']}" var="sl">
                    <c:set value="${sl.name}" var="name"></c:set>
                    <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${fn:substring(name,0,16)}...</a>
                      </li>
                    </c:if>
                    <c:if test="${length<=23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${name}</a>
                      </li>
                    </c:if>
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByParId.html?id=72">更多>></a>
              </div>
              <div id="tab-17" class="tab-pane fade">
                <ul class="categories">
                  <c:forEach items="${indexMapper['select77List']}" var="sl">
                    <c:set value="${sl.name}" var="name"></c:set>
                    <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${fn:substring(name,0,16)}...</a>
                      </li>
                    </c:if>
                    <c:if test="${length<=23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${name}</a>
                      </li>
                    </c:if>
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByParId.html?id=77">更多>></a>
              </div>
              <div id="tabs-17" class="tab-pane fade">
                <ul class="categories">
                  <c:forEach items="${indexMapper['select82List']}" var="sl">
                    <c:set value="${sl.name}" var="name"></c:set>
                    <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${fn:substring(name,0,16)}...</a>
                      </li>
                    </c:if>
                    <c:if test="${length<=23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>【${sl.lastArticleType.name}】${name}</a>
                      </li>
                    </c:if>
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByParId.html?id=82">更多>></a>
              </div>
            </div>
          </div>
        </div>
        <div class="col-md-4 mb10 login_box">
          <div class=" border1 job-content floor_kind floor_news">
            <h2>
		  <span class="col-md-4 col-sm-4 col-xs-6">单一来源公告</span>
		  <ul class="list-unstyled col-md-8 col-sm-8 col-xs-6 buyer_news m0 p0">
		  <c:choose>
	  	  	<c:when test="${articlebdw=='0'}">
	          <li class="active fl"><a aria-expanded="true" href="#tab-18" data-toggle="tab"> 物资</a></li>
	        </c:when>
		    <c:otherwise>
		      <li class="active fl"><a aria-expanded="true" href="#tab-18" data-toggle="tab"><b>${articlebdw}</b> 物资</a></li>
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
              <div id="tab-18" class="tab-pane fade active in">
                <ul class="categories">
                  <c:forEach items="${indexMapper['select94List']}" var="sl">
                    <c:set value="${sl.name}" var="name"></c:set>
                    <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a>
                      </li>
                    </c:if>
                    <c:if test="${length<=23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${name}</a>
                      </li>
                    </c:if>
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=94">更多>></a>
              </div>
              <div id="tab-19" class="tab-pane fade">
                <ul class="categories">
                  <c:forEach items="${indexMapper['select95List']}" var="sl">
                    <c:set value="${sl.name}" var="name"></c:set>
                    <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a>
                      </li>
                    </c:if>
                    <c:if test="${length<=23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${name}</a>
                      </li>
                    </c:if>
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=95">更多>></a>
              </div>
              <div id="tab-20" class="tab-pane fade">
                <ul class="categories">
                  <c:forEach items="${indexMapper['select96List']}" var="sl">
                    <c:set value="${sl.name}" var="name"></c:set>
                    <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a>
                      </li>
                    </c:if>
                    <c:if test="${length<=23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${name}</a>
                      </li>
                    </c:if>
                  </c:forEach>
                </ul>
                <a class="tab_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=96">更多>></a>
              </div>
              <div id="tabs-20" class="tab-pane fade">
                <ul class="categories">
                  <c:forEach items="${indexMapper['select97List']}" var="sl">
                    <c:set value="${sl.name}" var="name"></c:set>
                    <c:set value="${fn:length(name)}" var="length"></c:set>
                    <c:if test="${length>23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${fn:substring(name,0,23)}...</a>
                      </li>
                    </c:if>
                    <c:if test="${length<=23}">
                      <li>
                        <a title="${name}" href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${sl.id}"><span class="list_squre">■</span>${name}</a>
                      </li>
                    </c:if>
                  </c:forEach>
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
        <div class="col-md-12">
          <h2 class="floor_title col-md-12">业务办理</h2>
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
          <h2 class="floor_title col-md-12">采购信息</h2>
        </div>
      </div>
      <div class="row magazine-page clear">
        <div class="col-md-3 mb10 login_box">
          <div class=" border1 job-content floor_kind">
            <h2 class="p0_10">
		   重要通知<a class="news_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=109">更多>></a>
          </h2>
            <div class="categories">
              <ul class="p0_10">
                <c:forEach items="${indexMapper['select109List']}" var="sl">
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
          <div class=" border1 job-content floor_kind">
            <h2 class="p0_10">
		   采购法规<a class="news_more" href="${pageContext.request.contextPath}/index/selectsumByParId.html">更多>></a>
          </h2>
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
          <div class=" border1 job-content floor_kind">
            <h2 class="p0_10">
		   投诉处理公告<a class="news_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=112">更多>></a>
          </h2>
            <div class="categories">
              <ul class="p0_10">
                <c:forEach items="${indexMapper['select112List']}" var="sl">
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
          <div class=" border1 job-content floor_kind">
            <h2 class="p0_10">
		   处罚公告<a class="news_more" href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.html?id=113">更多>></a>
          </h2>
            <div class="categories">
              <ul class="p0_10">
                <c:forEach items="${indexMapper['select113List']}" var="sl">
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
      </div>
      <!-- <a href="${pageContext.request.contextPath}/index/init.html">初始化solr</a> -->
    </div>
    <!--底部代码开始-->
    <jsp:include page="/index_bottom.jsp"></jsp:include>
    </div>
    </div>

  </body>

</html>