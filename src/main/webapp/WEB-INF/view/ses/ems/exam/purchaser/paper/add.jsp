<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>新增考卷</title>
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
			   <li><a href="#">首页</a></li><li><a href="#">支撑环境</a></li><li><a href="#">考卷管理</a></li>
			   </ul>
			<div class="clear"></div>
		  </div>
	   </div>
	   
     <div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
     	<div>
		   <div class="headline-v2">
		   		<h2>新增考卷</h2>
		   </div>
    <form action="<%=path %>/purchaserExam/saveToExamPaper.html" method="post">
    	<ul class="list-unstyled list-flow p0_20">
		     <li class="col-md-12 p0">
	  			<span class="fl">试卷名称:</span>
		  		<input type="text" name="paperName"/>
	  		</li>
    		
    		<li class="col-md-12 p0">
	  			<span class="fl">试卷编号:</span>
		  		<input type="text" name="paperNo"/>
	  		</li>
    		
    		<li class="col-md-12 p0">
	  			<span class="fl">题型分布:</span>
	  			<div class="fl">
	  			   <label class="fl mt5">单选题:</label><input type="text" name="singleNum" id="singleNum" class="ml10 w50"/>条<input type="text" name="singlePoint" id="singlePoint" class="ml10 w50"/>分/条<br/>
		    	   <label class="fl mt5">多选题:</label><input type="text" name="multipleNum" id="multipleNum" class="ml10 w50"/>条<input type="text" name="multiplePoint" id="multiplePoint" class="ml10 w50"/>分/条<br/>
		    	   <label class="fl mt5">判断题:</label> <input type="text" name="judgeNum" id="judgeNum" class="ml10 w50"/>条<input type="text" name="judgePoint" id="judgePoint" class="ml10 w50"/>分/条<br/>
	  		    </div>
	  		    <div class="clear"></div>
	  		</li>
    		
    		<li class="col-md-12 p0">
	  			<span class="fl">总分值:</span>
		  		<input class="w50" type="text" name="totalPoint" id="totalPoint"/>分
	  		</li>
    		
    		<li class="col-md-12 p0">
	  			<span class="fl">考试开始时间:</span>
		  		<input type="text" name="startTime" id="startTime" class="Wdate" onfocus="WdatePicker({isShowWeek:true})"/>
	  			<select id="hour" name="hour" class="mb8">
	  				<option value="">请选择</option>
	  				<c:forEach items="${hour }" varStatus="h">
	  					<option value="${h.index+1 }">${h.index+1 }</option>
	  				</c:forEach>
	  			</select>时
	  			<select id="second" name="second" class="mb8">
	  				<option value="">请选择</option>
	  				<c:forEach items="${second }" varStatus="s">
	  					<option value="${s.index }">${s.index }</option>
	  				</c:forEach>
	  			</select>分
	  		</li>
	  		
	  		<li class="col-md-12 p0">
	  			<span class="fl">考试用时:</span>
		  		<input class="w50" type="text" name="useTime" id="useTime"/>分钟
	  		</li>
	  		
	  		<li class="col-md-12 p0">
	  			<span class="fl w250">首次考试不及格的是否允许30分钟内重考:</span>
		  		<input type="checkbox" name="isAllow" id="isAllowTrue" value="是">是
    			<input type="checkbox" name="isAllow" id="isAllowFalse" value="否"/>否
	  		</li>
    	</ul>
   
   
  		<!-- 按钮 -->
  		<div class="padding-top-10 clear">
			<div class="col-md-12 pl200 ">
				<div class="mt40 tc mb50">
				 	<button class="btn btn-windows save" type="submit">保存</button>
	    			<input class="btn btn-windows reset" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
				</div>
	  		</div>
	  	</div>
    
    			</form>
    		</div>
    	</div>
    </div>
  </body>
</html>
