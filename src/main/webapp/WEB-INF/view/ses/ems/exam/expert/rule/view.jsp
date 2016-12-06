<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>考试规则查看</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		
	</script>

  </head>
  
  <body>
     <!--面包屑导航开始-->
   	<div class="margin-top-10 breadcrumbs ">
      	<div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#">首页</a></li><li><a href="#">支撑环境</a></li><li><a href="#">专家考试规则管理</a></li>
		   </ul>
		<div class="clear"></div>
	  	</div>
   	</div>
   	
   	<div class="container content pt0">
	 <div class="row magazine-page">
	   <div class="col-md-12 col-sm-12 col-cs-12 tab-v2">
	        <div class="padding-top-10">
	        <ul class="nav nav-tabs bgwhite">
	            <li class="active"><a aria-expanded="true" href="javascript:void(0)" data-toggle="tab" class="f18">专家考试规则详情</a></li>
            </ul>
            <div class="tab-content padding-top-20 over_hideen">
            <div class="tab-pane fade active in" id="tab-1">
                <table class="table table-bordered">
                 <tbody>
                     <tr>
	                  	<td class="bggrey w150">题型分布：</td>
	                  	<td colspan="3">${typeDistribution }</td>
	                 </tr>
	                 <tr>
	                 	<td class="bggrey w150">总分值：</td>
	                 	<td>${examRule.paperScore }分</td>
	                 	<td class="bggrey w150">及格标准：</td>
	                 	<td>${examRule.passStandard }分</td>
	                 </tr>
	                 <tr>
	                 	<td class="bggrey w150">创建时间：</td>
	                  	<td><fmt:formatDate value="${examRule.createdAt }" pattern="yyyy-MM-dd"/></td>
	                  	<td class="bggrey w150">修改时间：</td>
	                  	<td><fmt:formatDate value="${examRule.updatedAt }" pattern="yyyy-MM-dd"/></td>
	                 </tr> 
                 </tbody>
                 </table>
                
            </div>
                 
               </div>
			</div>  	
     	</div>
     	</div>
     </div>
   	
   	
	    
	<!-- 底部按钮 -->
	<div class="col-md-12 col-sm-12 col-xs-12 mt10 tc">
		<button class="btn btn-windows back" type="button" onclick="location.href='javascript:history.go(-1);'">返回</button>
	</div>
  </body>
</html>
