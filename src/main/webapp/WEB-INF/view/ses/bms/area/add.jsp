<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/WEB-INF/view/common.jsp"%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'add.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

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
                 <img alt="Logo" src="<%=basePath%>public/ZHH/images/logo_2.png" id="logo-header">
              </a>
            </div>
            <!--搜索开始-->
            <div class="col-md-8 topbar-v1 col-md-12 padding-0">
              <ul class="top-v1-data padding-0">
                <li>
                <a href="#">
                  <div><img src="<%=basePath%>public/ZHH/images/top_01.png"/></div>
                  <span>决策支持</span>
                 </a>
                </li>
                <li>
                <a href="#">
                  <div><img src="<%=basePath%>public/ZHH/images/top_02.png"/></div>
                  <span>业务监管</span>
                 </a>
                </li>
                <li>
                <a href="#">
                  <div><img src="<%=basePath%>public/ZHH/images/top_03.png"/></div>
                  <span>障碍作业</span>
                 </a>
                </li>   
                <li>
                <a href="#">
                  <div><img src="<%=basePath%>public/ZHH/images/top_04.png"/></div>
                  <span>信息服务</span>
                 </a>
                </li>
                <li>
                <a href="#">
                  <div><img src="<%=basePath%>public/ZHH/images/top_05.png"/></div>
                  <span>支撑环境</span>
                 </a>
                </li>
                <li>
                <a href="#">
                  <div><img src="<%=basePath%>public/ZHH/images/top_06.png"/></div>
                  <span>配置配置</span>
                 </a>
                </li>
                <li>
                <a href="#">
                  <div><img src="<%=basePath%>public/ZHH/images/top_07.png"/></div>
                  <span>后台首页</span>
                 </a>
                </li>
                <li>
                <a href="#">
                  <div><img src="<%=basePath%>public/ZHH/images/top_08.png"/></div>
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
           <li><a href="#"> 首页</a></li><li><a href="#">地区管理</a></li><li><a href="#">地区添加</a></li>
           </ul>
        <div class="clear"></div>
      </div>
   </div>
  <div class="container">
     <ul class="list-unstyled list-flow p0_20 ">
    <form action="<%=basePath%>area/save.do" method="post" enctype="multipart/form-data">
    <input type="hidden" value="${id}" name="areaType"/>
            <li class="col-md-12 p0 ">
       <span class="fl tc"><i class="red">＊</i> 地区名称：</span>
       <div class="">
      <input type="text"  name="name"/>
       </div>
         <div class="item"><label class="fl" for="">层级关系：</label>
            <select class="select fl mt10" name="areaType">
                <option value="false">子节点</option>
                <option value="true">父节点</option> 
            </select>
          </div>
     </li> 
            <li class="col-md-12 p0">
       <span class="fl"><i class="red">＊</i> 排序号：</span>
       <div class="">
       <input type="text"  name="position"/>
       </div>
     </li> 
            <li class="col-md-12 p0">
       <span class="fl"><i class="red">＊</i> 优先级：</span>
       <div class="">
       <input type="text"  name="parentId"/>
       </div>
     </li> 
            
   
     
           
     <!--       <li class="col-md-6 p0">
            <span>是否末级：</span>
            <div class="input-append mt5">
                 <label class="fl margin-bottom-0"><input type="radio" name="isEnd" value="0">是</label>
                 <label class="ml10 fl"><input type="radio" name="isEnd" value="1">否</label>
                 <select class="ml10 fl" name="isEnd">
                 <option value="0">是</option>
                 <option value="1">否</option>
                 </select>
       </div>
     </li>  -->
        </ul>
         <div  class="col-md-12">
       <div class="mt40 tc mb50">
        <button class="btn btn-windows save" type="submit">保存</button>
        <input class="btn btn-windows reset" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
    </div>
  </div>
    </form>
        </div>
  </body>
</html>
