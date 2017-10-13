<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
 <div class="wrapper" id="nav">
	<div class="header-v4 header-v5 clear">
    <!-- Navbar -->
    <div class="navbar navbar-default mega-menu" role="navigation">
     <div class="head_main">
       <div class="container">
        <!-- logo和搜索 -->
         <div class="navbar-header w100p">
           <div class="row">
            <div class="col-md-3 col-sm-12 col-xs-12 padding-bottom-30 mt20">
              <a href="${pageContext.request.contextPath}/">
                 <img alt="Logo" src="${pageContext.request.contextPath}/public/backend/images/logo_2.png" id="logo-header">
              </a>
            </div>
           <button data-target=".navbar-responsive-collapse" data-toggle="collapse" class="navbar-toggle" type="button">
            <span class="full-width-menu">全部菜单</span>
            <span class="icon-toggle">
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </span>
           </button>
		   <div style="height: 0px;" aria-expanded="false" class="navbar-collapse navbar-responsive-collapse collapse fr">
            <div class="col-md-12 topbar-v1 col-xs-12 col-sm-12 p0" >
              <ul class="top-v1-data padding-0 nav navbar-nav">
              	<li class="dropdown wdxx ">
					<a href="javascript:void(0);"  aria-expanded="false" data-toggle="dropdown" class="dropdown-toggle p0_30 " target="home">
					  <div class="top_icon wdxx_icon"></div>
					  <span>个人中心</span>
					</a>
					<ul class="dropdown-menu drop_next">
						<li class="line-block drop_two">
				             <a href="${pageContext.request.contextPath}/login/home.html" target="home" class="son-menu"><span class="mr5">◇</span>我的通知</a>
				        </li>
				        <li class="line-block drop_two">
				        	<c:choose> 
							  <c:when test="${sessionScope.loginUserType == 'supplier'}">   
							     <a href="${pageContext.request.contextPath}/supplierQuery/essential.html?person=1" target="home" class="son-menu"><span class="mr5">◇</span>个人信息</a>
							  </c:when> 
							  <c:when test="${sessionScope.loginUserType == 'expert'}">   
							   	 <a href="${pageContext.request.contextPath}/expert/toPersonInfo.html" target="home" class="son-menu"><span class="mr5">◇</span>个人信息</a>
							  </c:when> 
							  <c:otherwise>   
					             <a href="${pageContext.request.contextPath}/user/personalInfo.html" target="home" class="son-menu"><span class="mr5">◇</span>个人信息</a>
							  </c:otherwise> 
							</c:choose>
				        </li>
				        <li class="line-block drop_two">
				             <a href="${pageContext.request.contextPath}/user/resetPassword.html" target="home" class="son-menu"><span class="mr5">◇</span>重置密码</a>
				        </li>
					</ul>
				</li>
			    <c:forEach items="${sessionScope.resource}" var="resource" varStatus="vs">
					 <c:if test="${resource.menulevel == 2 }">
						 <li class="dropdown ${resource.icon}">
							<a  <c:if test='${resource.url == null}'>aria-expanded="false" data-toggle="dropdown" class="dropdown-toggle p0_30 " href="javascript:void(0);"</c:if>
							<c:if test='${resource.url != null && resource.name != "安全退出" && resource.name != "回到门户"}'>href="${pageContext.request.contextPath}/${resource.url}"  target="home"</c:if>
							<c:if test='${resource.url != null && resource.name == "安全退出"}'>href="${pageContext.request.contextPath}/${resource.url}" </c:if> 
							<c:if test='${resource.url != null && resource.name == "回到门户"}'>href="${pageContext.request.contextPath}/" </c:if> >
							  <div class="top_icon ${resource.icon}_icon"><%-- <img src="${pageContext.request.contextPath}/public/ZHH/images/${resource.icon}"/> --%></div>
							  <span>${resource.name }</span>
							</a>
					 		
							<ul class="dropdown-menu drop_next">
							 	<c:forEach items="${sessionScope.resource}" var="res" varStatus="vs">
							 		<c:if test="${resource.id == res.parentId.id}">
				                   		<li class="line-block drop_two">
				                   			<a href="<c:if test='${res.url == null}'>javascript:void(0);</c:if><c:if test='${res.url != null}'>${pageContext.request.contextPath}/${res.url}</c:if>" target="home" class="son-menu"><span class="mr5">◇</span>${res.name }</a>
			                   				<c:set var="count" value="0" />
			                   				<c:forEach items="${sessionScope.resource}" var="r" varStatus="vs">
			                   					<c:if test="${res.id == r.parentId.id}">
			                   						<c:set var="count" value="${count+1}" />
			                   					</c:if>
			                   				</c:forEach>
			                   				<c:if test="${count > 0}">
				                   				<ul class="dropdown-menuson dropdown-menu">
					                   				<c:forEach items="${sessionScope.resource}" var="r" varStatus="vs">
					                   					<c:if test="${res.id == r.parentId.id && res.parentId.id !='EFAE85CBBB1A404493489303A0B30B71'}">
								                   			<li><a href="${pageContext.request.contextPath}/${r.url}" target="home" class="son-menu son-three"><span class="mr5">◇</span>${r.name }</a></li>
					                   					</c:if>
					                   					<c:if test="${res.id == r.parentId.id && res.parentId.id =='EFAE85CBBB1A404493489303A0B30B71'}"><!--判断是否是进出口管理  -->
                                                            <li><a href="${r.url}" target="home" class="son-menu son-three"><span class="mr5">◇</span>${r.name }</a></li>
                                                        </c:if>
					                   				</c:forEach>
				                   				</ul>
			                   				</c:if>
				                   		</li>
				                 	</c:if>
					 			</c:forEach>
			               	</ul>
						</li>
					</c:if>
				</c:forEach>
				<li class="dropdown htsy">
					<a href="${pageContext.request.contextPath}/">
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
</div>
</div>