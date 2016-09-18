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
  	<form action="<%=path %>/expertExam/saveExamRule.html" method="post">
	  	<ul class="list-unstyled list-flow p0_20">
		     <li class="col-md-12 p0">
	  			<span class="fl">考试时间:</span>
	    		<input type="text" name="testTime"/>
	    	</li>
	    	<li class="col-md-12 p0">
	  			<span class="fl">及格标准:</span>
	    		<input type="text" name="passStandard"/>
	    	</li>
	    	<li class="col-md-12 p0">
	  			<span class="fl">考试题目数量:</span>
	    		<input type="text" name="queNum"/>
	    	</li>
	    	<li class="col-md-12 p0">
	  			<span class="fl">试卷分值:</span>
	    		<input type="text" name="paperScore"/>
	    	</li>
	    	<li class="col-md-12 p0">
	  			<span class="fl">考试周期:</span>
	    		<input type="text" name="testCycle"/>
	    	</li>
	    </ul>
	    
	    <div class="padding-top-10 clear">
			<div class="col-md-12 pl200 ">
				<div class="mt40 tc mb50">
				 	<button class="btn btn-windows save" type="submit">保存</button>
	    			<input class="btn btn-windows reset" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
				</div>
	  		</div>
	  	</div>
    </form>
  </body>
</html>
