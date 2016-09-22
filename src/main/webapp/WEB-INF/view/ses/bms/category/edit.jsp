<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/WEB-INF/view/common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>采购目录更新</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <script type="text/javascript">
	function cheClick(id,name){
		$("#articleTypeId").val(id);
		$("#articleTypeName").val(name);
	}
	
	function addAttach(){
		html="<input id='pic' type='file' class='toinline' name='attaattach'/><a href='#' onclick='deleteattach(this)' class='toinline'>x</a><br/>";
		$("#uploadAttach").append(html);
	}
	
	function deleteattach(obj){
		$(obj).prev().remove();
		$(obj).next().remove();
		$(obj).remove();
	}
    $(function(){
    	$("#selected").val();
    	
    })
    
    
    

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
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">采购目录管理</a></li><li><a href="#">采购目录修改</a><><li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
   <ul class="breadcrumb margin-left-0">
	<form action="<%=basePath%>category/edit.do" method="post">
		<input type="hidden" value="${id}" name="ancestry"/>
	        <li class="col-md-12 p0">
	   <span class="fl"><i class="red">＊</i> 目录名称：</span>
	   <div class="">
	   <input type="text"  name="mane" value="${category.name}"/>
       </div>
	 </li> 
	      <li class="col-md-12 p0">
	   <span class="fl"><i class="red">＊</i> 父节点：</span>
	   <div class="">
	   <input type="text"  name="parentId" value="${category.parentId}"/>
       </div>
	 </li> 
	       <li class="col-md-12 p0">
	   <span class="fl"><i class="red">＊</i> 排序号：</span>
	   <div class="">
	   <input type="text"  name="position" value="${category.position}"/>
       </div>
	 </li> 
	       <li class="col-md-12 p0">
	   <span class="fl"><i class="red">＊</i>编码：</span>
	   <div class="">
	   <input type="text"  name="code" value="${category.code}"/>
       </div>
	 </li> 
	 
	  <li class="col-md-12 p0">
	 <span class="f14 fl">已上传的附件：</span>
	 <div class="fl mt5">
  	   <c:forEach items="${category.attchment}" var="a">
  	   	<a href="#">${fn:split(a.fileName, '_')[1]}</a><a href="#" onclick="deleteAtta('${a.id}',this)" class="red redhover ml10">x</a>
  	   </c:forEach>
	 </div>
	 </li>
	       <li class="col-md-12 p0">
	    <span class="f14 fl">上传附件：</span>
	    <div class="fl" id="uploadAttach" >
	      <input id="pic" type="file" class="toinline" name="attaattach"/>
	      <input class="toinline" type="button" value=" ${category.attchment }" onclick="addAttach()"/><br/>
	    </div>
	 </li>
	       
	       <li class="col-md-12 p0">
	   <span class="fl"><i class="red">＊</i> 描述：</span>
	   <div class="">
	   <input type="text"  name="description" value="${category.description }"/>
       </div>
	<li class="col-md-6 p0">
	       	<span>是否末级：</span>
	       	<div class="input-append mt5">
	       		 <select id="selected" class="ml10 fl" name="isEnd" >
        		 <option value="0">是</option>
        		 <option value="1">否</option>
        		 </select>
       </div>
	 </li> 
	       </ul>
	       <div  class="col-md-12">
	   <div class="mt40  mb50">
	    <button class="btn btn-windows save" type="submit">更新</button>
	    <input class="btn btn-windows reset" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
	</div>
	</div>
		</form>
		<!--底部代码开始-->
			<div class="footer-v2 clear" id="footer-v2">
      			<div class="footer">
           		 <!-- Address -->
              <address class="">
			  Copyright © 2016 版权所有：中央军委后勤保障部 京ICP备09055519号
              </address>
              <div class="">
		       浏览本网主页，建议将电脑显示屏的分辨率调为1024*768
              </div> 
            <!-- End Address -->
<!--/footer--> 
    </div>
  </body>
</html>
