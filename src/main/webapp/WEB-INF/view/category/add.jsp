<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../common.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>

    
    <title>My JSP 'add.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">
	


</script>
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


  
    <div class="curr_add">当前位置：首页>采购目录管理</div>
	<form action="<%=basePath%>category/save.do" method="post">
	<input type="hidden" value="${id }" name="ancestry"/>
	       <div class=""><span>目录名称:</span><input type="text" name="name" /></div>
	  	   <div class=""><span>是否末级</span>
	       		 <select name="status">
	  	  		<option value="0">激活</option>
	  	  		<option value="1">休眠</option> 
	  		</select>
	       </div>
	       <div class=""><span>排序:</span><input type="text"name="orderNum" /></div>
	       <div class=""><span>前台展示优先级</span><input type="text" name="code" /></div>
	       <div class=""><span>图片:</span><input id="pic" type="file" name="attchment"/></div>
	       <div class=""><span>描述:</span><input type="text"  name="description"/></div>
	       <div class=""><span>是否末级</span>
	       		 <select name="isEnd">
	  	  		<option value="0">是</option>
	  	  		<option value="1">否</option> 
	  		</select>
	       </div>
	    <div>
			<input class="btn btn-window " value="提交" type="submit"> 
			<input class="btn btn-window " onclick="location.href='javascript:history.go(-1);'" value="返回" type="button"/>
		</div>
	</form>
  </body>
</html>
