<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
 <div class="wrapper" id="nav">
	<div class="header-v4 header-v5 clear">
    <!-- Navbar -->
    <div class="navbar navbar-default mega-menu" role="navigation">
      <div class="container">
        <!-- logo和搜索 -->
        <div class="navbar-header">
          <div class="row container">
            <div class="col-md-3 col-sm-3 col-xs-12 padding-bottom-30 mt10">
              <a href="${pageContext.request.contextPath}/">
                 <img alt="Logo" src="${pageContext.request.contextPath}/public/backend/images/logo_2.png" id="logo-header">
              </a>
            </div>
			<!--搜索开始-->
            <div class="col-md-9 topbar-v1 col-xs-12 col-sm-9 padding-0">
              <ul class="top-v1-data padding-0">
              	<li class="dropdown wdxx">
					<a href="${pageContext.request.contextPath}/login/home.html"  target="home">
					  <div class="top_icon wdxx_icon"></div>
					  <span>我的通知</span>
					</a>
				</li>
			    <c:forEach items="${sessionScope.resource}" var="resource" varStatus="vs">
					 <c:if test="${resource.menulevel == 2 }">
						 <li class="dropdown ${resource.icon}">
							<a <c:if test='${resource.url == null}'>ria-expanded="false" data-toggle="dropdown" class="dropdown-toggle p0_30 " href="javascript:void(0);"</c:if>
							<c:if test='${resource.url != null && resource.name != "安全退出" && resource.name != "回到门户"}'>href="${pageContext.request.contextPath}/${resource.url}"  target="home"</c:if>
							<c:if test='${resource.url != null && resource.name == "安全退出"}'>href="${pageContext.request.contextPath}/${resource.url}" </c:if> 
							<c:if test='${resource.url != null && resource.name == "回到门户"}'>href="${pageContext.request.contextPath}/${resource.url}" </c:if> >
							  <div class="top_icon ${resource.icon}_icon"><%-- <img src="${pageContext.request.contextPath}/public/ZHH/images/${resource.icon}"/> --%></div>
							  <span>${resource.name }</span>
							</a>
					 		
							<ul class="dropdown-menu ">
							 	<c:forEach items="${sessionScope.resource}" var="res" varStatus="vs">
							 		<c:if test="${resource.id == res.parentId.id}">
				                   		<li class="line-block drop_two">
				                   			<a href="<c:if test='${res.url == null}'>javascript:void(0);</c:if><c:if test='${res.url != null}'>${pageContext.request.contextPath}/${res.url}</c:if>" target="home" class="son-menu"><span class="mr5">◇</span>${res.name }</a>
			                   				<ul class="dropdown-menuson dropdown-menu">
				                   				<c:forEach items="${sessionScope.resource}" var="r" varStatus="vs">
				                   					<c:if test="${res.id == r.parentId.id}">
							                   			<li><a href="${pageContext.request.contextPath}/${r.url}" target="home" class="son-menu son-three"><span class="mr5">◇</span>${r.name }</a></li>
				                   					</c:if>
				                   				</c:forEach>
			                   				</ul>
				                   		</li>
				                 	</c:if>
					 			</c:forEach>
			               	</ul>
						</li>
					</c:if>
				</c:forEach>
				<li class="dropdown htsy">
					<a href="${pageContext.request.contextPath}/index/selectIndexNews.html">
					  <div class="top_icon htsy_icon"></div>
					  <span>回到门户</span>
					</a>
				</li>
				<li class="dropdown aqtc">
					<a href="${pageContext.request.contextPath}/login/loginOut.html">
					  <div class="top_icon aqtc_icon"></div>
					  <span>安全退出</span>
					</a>
				</li>
			  </ul>
			</div>
    </div>
	</div>
	</div>
   </div>
</div>
</div>