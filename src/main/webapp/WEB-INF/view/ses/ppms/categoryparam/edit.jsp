<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'edit.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">
 /**修改参数*/
   
    $.ajax({
		dataType:"json",
	    type:"post",
		success:function(cateList){
		alert(cateList);
		for ( var i = 0; i < cateList.length; i++) {
		var html="";
			html = html+"<tr><td><span>参数名称：</span></td><td><input name='name' value='"+cateList[i].name+"'/></td><td><span>参数类型：</span></td>"
					   +"<td><select name='valueType' value='"+cateList[i].valueType+"'>"
					   +"<option >请选择</option>"
					   +"<option value='0'>字符型</option>"
					   +"<option value='1'>数字型</option>"
					   +"<option value='2'>日期</option></select></td></tr>";
				    $("#result").find("tr:first").before(html);
					}
					}
        });
    


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
		   <li><a href="#"> 首页</a><><li><a href="#">产品参数管理</a><><li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
   <div class="col-md-3">
     
	 <div class="tag-box tag-box-v3 mt10">
	 <div><ul id="ztree" class="ztree "></ul></div>
	 </div>
	</div >
	<div></div>
    <form action="<%=basePath%>categoryparam/edit.do">
    <table id="result">
      <tr><td >是否公开</td>
					 <td>
					 <span class="ml30"><input type="radio" value="0" name="ispublish" />是</span>
					 <span class="ml60"><input type="radio" value="1" name="ispublish"/>否</span>
					 </td></tr>
					 <!-- <tr><td>公布范围</td>
					 <td>
					 <span><input type="checkbox" value="true" name="scopePublic"/>外网</span>
					 <span><input type="checkbox" value="false" name="scopePublic"/>内网</span>
					 </td></tr> -->
					 <tr><td >产品类型</td>
					 <td>
					 <span class="ml30"><input type="checkbox" value="生产型" name="type" id="box"/>生产型</span>
					 <span class="ml30"><input type="checkbox" value="销售型" name="type" id="box"/>销售型</span>
					 </td></tr>
					 <tr><td>验证规范</td><td><textarea name="acceptRange"></textarea></td></tr>
					 <tr><td>生产型资质</td>
					 <td ><span id="addinput"></span><input type="text" value="" name="productName"/></span>
					 <input  type="button" value="添加" onclick="addAttach()" class="mb10"/><br/>
					 </td></tr>
					 <tr><td>销售型资质</td>
					 <td><span id="addnews"></span><input type="text" name="saleName"/></span>
					 <input  type="button" value="添加" onclick="addAtt()" class="mb10"/><br/>
					 </td></tr>
				     <tr><td colspan="2"  >
					<button type="button" class="btn mr30" onclick="renew()" name="" >提交</button>
					 <input type="button"class="btn" value="返回" onclick="location.href='javascript:history.go(-1);'"/></td>
					 </tr> 
    </table>
    </form>
  </body>
</html>
