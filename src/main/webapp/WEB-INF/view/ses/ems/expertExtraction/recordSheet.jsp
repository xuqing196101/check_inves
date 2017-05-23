<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
</head>
<body>
  <div class="wrapper">
	<div class="header-v4 header-v5">
    <!-- Navbar -->
    <div class="navbar navbar-default mega-menu" role="navigation">
      <div class="container">
        <!-- logo和搜索 -->
        <div class="navbar-header">
          <div class="row container">
            <div class="col-md-4 padding-bottom-30">
              <a href="">
                 <img alt="Logo" src="${pageContext.request.contextPath}/public/ZHH/images/logo_2.png" id="logo-header">
              </a>
            </div>
			<!--菜单开始-->
            <div class="col-md-8 topbar-v1 col-md-12 ">
              <ul class="top-v1-data padding-0">
			    <li>
				<a href="javascript:void(0);">
				  <div><img src="${pageContext.request.contextPath}/public/ZHH/images/top_01.png"/></div>
				  <span>决策支持</span>
				 </a>
				</li>
			    <li>
				<a href="javascript:void(0);">
				  <div><img src="${pageContext.request.contextPath}/public/ZHH/images/top_02.png"/></div>
				  <span>业务监管</span>
				 </a>
				</li>
			    <li>
				<a href="javascript:void(0);">
				  <div><img src="${pageContext.request.contextPath}/public/ZHH/images/top_03.png"/></div>
				  <span>障碍作业</span>
				 </a>
				</li>	
			    <li>
				<a href="javascript:void(0);">
				  <div><img src="${pageContext.request.contextPath}/public/ZHH/images/top_04.png"/></div>
				  <span>信息服务</span>
				 </a>
				</li>
			   <li class="dropdown">
			     	<a aria-expanded="false" data-toggle="dropdown" class="dropdown-toggle p0_30 " href="">
				  		<div><img src="${pageContext.request.contextPath}/public/ZHH/images/top_05.png"/></div>
				  		<span>支撑环境</span>
				 	</a>
					<ul class="dropdown-menu">
                   		<li class="line-block">
                   			<a href="#" target="_blank" class="son-menu"><span class="mr5">◇</span>后台管理</a>
                   			<ul class="dropdown-menuson dropdown-menu">
                   				<li><a href="#" target="_blank" class="son-menu"><span class="mr5">◇</span>用户管理</a></li>
                   			</ul>
                   		</li>
               		</ul>
				</li>
			    <li>
				<a href="javascript:void(0);">
				  <div><img src="${pageContext.request.contextPath}/public/ZHH/images/top_06.png"/></div>
				  <span>配置配置</span>
				 </a>
				</li>
			    <li>
				<a href="javascript:void(0);">
				  <div><img src="${pageContext.request.contextPath}/public/ZHH/images/top_07.png"/></div>
				  <span>后台首页</span>
				 </a>
				</li>
			    <li>
				<a href="javascript:void(0);">
				  <div><img src="${pageContext.request.contextPath}/public/ZHH/images/top_08.png"/></div>
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

<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0);"> 首页</a></li><li><a href="javascript:void(0);">评审专家管理</a></li><li><a href="javascript:void(0);">评审专家抽取</a></li><li class="active"><a href="javascript:void(0);">抽取记录查询</a></li><li class="active"><a href="javascript:void(0);">专家抽取记录表</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
   <div>
   	<ul class="list-unstyled list-flow p0_20">
  		<li class="col-md-6 p0">
	   		<span class="">项目名称：</span>
        		<input  disabled="disabled" type="text">
		</li>  
    	 <li class="col-md-6 p0">
	   		<span class="">项目编号：</span>
        		<input  disabled="disabled" type="text">
		</li>
		<li class="col-md-6 p0">
	   		<span class="">抽取时间：</span>
        		<input  disabled="disabled" type="text">
		</li>
		<li class="col-md-6 p0">
	   		<span class="">抽取地点：</span>
        		<input  disabled="disabled" type="text">
		</li>
		<li class="col-md-6 p0">
	   		<span class="">抽取条件：</span>
        		<input  disabled="disabled" type="text">
		</li>
		<li class="col-md-6 p0">
	   		<span class="">抽取数量：</span>
        		<input  disabled="disabled" type="text">
		</li>
	</ul> 
   </div>
   
   
<!--标题-->
   <div class="container">
   <div class="headline-v2">
   	<h4>抽取记录</h4>
   </div>
   </div>
<!-- 表格开始-->
   <div class="container">
   <div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
        <table class="table table-bordered table-condensed">
		<thead>
		<tr>
		  <th class="info w50">序号</th>
		  <th class="info">专家名称</th>
		  <th class="info">联系人</th>
		  <th class="info">手机号</th>
		  <th class="info">传真</th>
		  <th class="info">能否参加</th>
		  <th class="info">不参加理由</th>
		</tr>
		</thead>
		<%-- <c:forEach items="${ }" var="e" varStatus=""> --%>
		<tr>
		  <td class="tc w50">1</td>
		  <td class="tc">紫光</td>
		  <td class="tc">北京</td>
		  <td class="tc">2016</td>
		  <td class="tc">34</td>
		  <td class="tc">不参加</td>
		  <td class="tc">未抽取</td>
		</tr>
	<%-- 	</c:forEach> --%>
        </table>
     </div>
   
   </div>
 </div>
 
<!--标题-->
   <div class="container">
   <div class="headline-v2">
   	<h4>抽取人员</h4>
   </div>
   </div>
<!-- 表格开始-->
   <div class="container">
   <div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
        <table class="table table-bordered table-condensed">
		<thead>
		<tr>
		  <th class="info w50">序号</th>
		  <th class="info">姓名</th>
		  <th class="info">单位</th>
		  <th class="info">职务</th>
		  <th class="info">军衔</th>
		  <th class="info">签字</th>
		</tr>
		</thead>
		<%-- <c:forEach items="${ }" var="e" varStatus=""> --%>
		<tr>
		  <td class="tc w50">1</td>
		  <td class="tc">徐庆</td>
		  <td class="tc">军人</td>
		  <td class="tc">2016</td>
		  <td class="tc">司令</td>
		  <td class="tc">不参加</td>
		</tr>
	<%-- 	</c:forEach> --%>
        </table>
     </div>
   
   </div>
 </div>
 
 <!--标题-->
   <div class="container">
   <div class="headline-v2">
   	<h4>监督人员</h4>
   </div>
   </div>
<!-- 表格开始-->
   <div class="container">
   <div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
        <table class="table table-bordered table-condensed">
		<thead>
		<tr>
		  <th class="info w50">序号</th>
		  <th class="info">姓名</th>
		  <th class="info">单位</th>
		  <th class="info">职务</th>
		  <th class="info">军衔</th>
		  <th class="info">签字</th>
		</tr>
		</thead>
		<%-- <c:forEach items="${ }" var="e" varStatus=""> --%>
		<tr>
		  <td class="tc w50">1</td>
		  <td class="tc">徐庆</td>
		  <td class="tc">军人</td>
		  <td class="tc">2016</td>
		  <td class="tc">司令</td>
		  <td class="tc">不参加</td>
		</tr>
	<%-- 	</c:forEach> --%>
        </table>
     </div>
   
   </div>
 </div>
 
<!--底部代码开始-->
<div class="footer-v2" id="footer-v2">
</div>
</body>
</html>
