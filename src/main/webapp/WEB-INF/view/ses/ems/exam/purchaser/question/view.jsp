<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>采购人查看题库</title>
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
		   			<li><a href="#">首页</a></li><li><a href="#">支撑环境</a></li><li><a href="#">题库管理</a></li>
		   		</ul>
				<div class="clear"></div>
	  		</div>
   		</div>
   
   		<div class="container content pt0">
	 <div class="row magazine-page">
	   <div class="col-md-12 col-sm-12 col-cs-12 tab-v2">
	        <div class="padding-top-10">
	        <ul class="nav nav-tabs bgwhite">
	            <li class="active"><a aria-expanded="true" href="javascript:void(0)" data-toggle="tab" class="f18">采购人员题目详情</a></li>
            </ul>
            <div class="tab-content padding-top-20 over_hideen">
            <div class="tab-pane fade active in" id="tab-1">
                <table class="table table-bordered">
                 <tbody>
                     <tr>
	                  	<td class="bggrey w150">题型：</td>
	                  	<c:if test="${purchaserQue.questionTypeId==1 }">
	                  		<td colspan="3">单选题</td>
	                  	</c:if>
	                  	<c:if test="${purchaserQue.questionTypeId==2 }">
	                  		<td colspan="3">多选题</td>
	                  	</c:if>
	                  	<c:if test="${purchaserQue.questionTypeId==3 }">
	                  		<td colspan="3">判断题</td>
	                  	</c:if>
	                 </tr>
	                 <tr>
	                 	<td class="bggrey w150">题干：</td>
	                 	<td colspan="3">${purchaserQue.topic }</td>
	                 </tr>
	                 <c:if test="${purchaserQue.questionTypeId==1||purchaserQue.questionTypeId==2 }">
	                 	<tr>
		                 	<td class="bggrey w150">选项数量：</td>
		                 	<td colspan="3">${optNum }</td>
	                	 </tr>
	                 </c:if>
	                 <c:if test="${purchaserQue.questionTypeId==1||purchaserQue.questionTypeId==2 }">
		                 <tr>
		                 	<td class="bggrey w150">选项：</td>
		                 	<td colspan="3">${purchaserQue.items }</td>
		                 </tr>
	                 </c:if>
	                 <tr>
	                 	<td class="bggrey w150">答案：</td>
	                 	<td colspan="3">${purchaserAnswer }</td>
	                 </tr>
	                 <tr>
	                 	<td class="bggrey w150">创建时间：</td>
	                  	<td><fmt:formatDate value="${purchaserQue.createdAt }" pattern="yyyy-MM-dd"/></td>
	                  	<td class="bggrey w150">修改时间：</td>
	                  	<td><fmt:formatDate value="${purchaserQue.updatedAt }" pattern="yyyy-MM-dd"/></td>
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
		  	<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>	
	  	</div>
  </body>
</html>
