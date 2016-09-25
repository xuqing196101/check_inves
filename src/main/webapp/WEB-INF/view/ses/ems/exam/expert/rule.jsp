<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>专家考试规则</title>
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
		   <li><a href="#">首页</a></li><li><a href="#">支撑环境</a></li><li><a href="#">考试规则管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
	   <div class="headline-v2">
	   		<h2>考试规则设置</h2>
	   </div>
   </div>
  	
  	<form action="<%=path %>/expertExam/saveExamRule.html" method="post">
  	  <div class="container mt20">
	  	<ul class="list-unstyled list-flow p0_20">
		     <%--<li class="col-md-12 p0">
	  			<span class="fl">考试用时:</span>
	  			<c:choose>
	  				<c:when test="${rule.testTime!=null }">
	  					<input type="text" name="testTime" value="${rule.testTime }"/>分钟
	  				</c:when>
	  				<c:otherwise>
	  					<input type="text" name="testTime"/>分钟
	  				</c:otherwise>
	  			</c:choose>
	    	</li>
	    	--%><li class="col-md-6 p0_15">
	  			<span class="fl">及格标准：</span>
	  			<c:choose>
	  				<c:when test="${rule.passStandard!=null }">
	  					<input type="text" name="passStandard" value="${rule.passStandard }"/>分
	  				</c:when>
	  				<c:otherwise>
	  					<input type="text" name="passStandard"/>分
	  				</c:otherwise>
	  			</c:choose>
	    	</li>
	    	<li class="col-md-6">
	  			<span class="fl">考试题目数量：</span>
	  			<c:choose>
	  				<c:when test="${rule.questionCount!=null }">
	  					<input type="text" name="queNum" value="${rule.questionCount}"/>条
	  				</c:when>
	  				<c:otherwise>
	  					<input type="text" name="queNum" value="50"/>条
	  				</c:otherwise>
	  			</c:choose>
	    	</li>
	    	<li class="col-md-6">
	  			<span class="fl">试卷分值：</span>
	  			<c:choose>
	  				<c:when test="${rule.paperScore!=null }">
	  					<input type="text" name="paperScore" value="${rule.paperScore}"/>分
	  				</c:when>
	  				<c:otherwise>
	  					<input type="text" name="paperScore" value="100"/>分
	  				</c:otherwise>
	  			</c:choose>
	    	</li>
	    	<li class="col-md-6">
	  			<span class="fl">考试周期：</span>
	  			<c:choose>
	  				<c:when test="${rule.testCycle!=null }">
	  					<input type="text" name="testCycle" value="${rule.testCycle}"/>月
	  				</c:when>
	  				<c:otherwise>
	  					<input type="text" name="testCycle"/>月
	  				</c:otherwise>
	  			</c:choose>
	    	</li>
	    </ul>
	    
	    <div class="padding-top-10 clear">
			<div class="col-md-12 tc">
				<div class="mt40 tc mb50">
				 	<button class="btn btn-windows save" type="submit">保存</button>
	    			<input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
				</div>
	  		</div>
	  	</div>
	 </div>
    </form>
  </body>
</html>
