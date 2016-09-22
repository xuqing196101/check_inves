<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>编辑考卷</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		$(function(){
			var isAllow = ${examPaper.isAllowRetake};
			var typeDistribution = ${examPaper.typeDistribution};
			var obj = eval(typeDistribution);
			$("#singleNum").val(obj.singleNum);
			$("#singlePoint").val(obj.singlePoint);
			$("#multipleNum").val(obj.multipleNum);
			$("#multiplePoint").val(obj.multiplePoint);
			$("#judgeNum").val(obj.judgeNum);
			$("#judgePoint").val(obj.judgePoint);
			if(isAllow==0){
				document.getElementById("isAllowFalse").setAttribute("checked",true);
			}else{
				document.getElementById("isAllowTrue").setAttribute("checked",true);
			}
			
		})
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
		   		<h2>查看考卷</h2>
		   </div>
    	<ul class="list-unstyled list-flow p0_20">
		     <li class="col-md-12 p0">
	  			<span class="fl">试卷名称:</span>
		  		<input type="text" name="paperName" value="${examPaper.name }" disabled="disabled"/>
	  		</li>
    		
    		<li class="col-md-12 p0">
	  			<span class="fl">试卷编号:</span>
		  		<input type="text" name="paperNo" value="${examPaper.code }" disabled="disabled"/>
	  		</li>
    		
    		<li class="col-md-12 p0">
	  			<span class="fl">题型分布:</span>
	  			<div class="fl">
	  			   <label class="fl mt5">单选题:</label><input type="text" name="singleNum" id="singleNum" class="ml10 w50" disabled="disabled"/>条<input type="text" name="singlePoint" id="singlePoint" class="ml10 w50" disabled="disabled"/>分/条<br/>
		    	   <label class="fl mt5">多选题:</label><input type="text" name="multipleNum" id="multipleNum" class="ml10 w50" disabled="disabled"/>条<input type="text" name="multiplePoint" id="multiplePoint" class="ml10 w50" disabled="disabled"/>分/条<br/>
		    	   <label class="fl mt5">判断题:</label> <input type="text" name="judgeNum" id="judgeNum" class="ml10 w50" disabled="disabled"/>条<input type="text" name="judgePoint" id="judgePoint" class="ml10 w50" disabled="disabled"/>分/条<br/>
	  		    </div>
	  		    <div class="clear"></div>
	  		</li>
    		
    		<li class="col-md-12 p0">
	  			<span class="fl mt5">总分值:</span>
		  		<input class="w50 mt5" type="text" name="totalPoint" id="totalPoint" value="${examPaper.score }" disabled="disabled"/>分
	  		</li>
    		
    		<li class="col-md-12 p0">
	  			<span class="fl mt5">考试开始时间:</span>
		  		<input type="text" disabled="disabled" value="${startTime }" name="startTime" id="startTime" class="Wdate mt5" onfocus="WdatePicker({isShowWeek:true})"/>
	  			<select id="hour" name="hour" class="mb8 mt5" disabled="disabled">
	  				<option value="">请选择</option>
	  				<c:forEach items="${hours }" varStatus="h">
	  					<c:choose>
		  					<c:when test="${hour==h.index+1 }">
		  						<option value="${h.index+1 }" selected="selected">${h.index+1 }</option>
		  					</c:when>
		  					<c:otherwise>
		  						<option value="${h.index+1 }">${h.index+1 }</option>
		  					</c:otherwise>
	  					</c:choose>
	  				</c:forEach>
	  			</select>时
	  			<select id="second" name="second" class="mb8 mt5" disabled="disabled">
	  				<option value="">请选择</option>
	  				<c:forEach items="${seconds }" varStatus="s">
	  					<c:choose>
		  					<c:when test="${second==s.index+1 }">
		  						<option value="${s.index+1 }" selected="selected">${s.index+1 }</option>
		  					</c:when>
		  					<c:otherwise>
		  						<option value="${s.index+1 }">${s.index+1 }</option>
		  					</c:otherwise>
	  					</c:choose>
	  				</c:forEach>
	  			</select>分
	  		</li>
	  		
	  		<li class="col-md-12 p0">
	  			<span class="fl mt5">考试用时:</span>
		  		<input class="w50 mt5" type="text" name="useTime" id="useTime" value="${examPaper.testTime }" disabled="disabled"/>分钟
	  		</li>
	  		
	  		<li class="col-md-12 p0">
	  			<span class="fl mt5">考试有效期:</span>
		  		<span class="fl mt5">考试有效期:</span>
		  		<input class="w50 mt5" type="text" name="expiryHour" id="expiryHour" value="${fn:split(examPaper.expiryDate,',')[0]}" disabled="disabled"/>小时
		  		<input class="w50 mt5" type="text" name="expirySecond" id="expirySecond" value="${fn:split(examPaper.expiryDate,',')[1]}" disabled="disabled"/>分钟
	  		</li>
	  		
	  		<li class="col-md-12 p0">
	  			<span class="fl w250">首次考试不及格的是否允许30分钟内重考:</span>
		  		<input type="checkbox" name="isAllowTrue" id="isAllowTrue" disabled="disabled">是
    			<input type="checkbox" name="isAllowFalse" id="isAllowFalse" disabled="disabled"/>否
	  		</li>
    	</ul>
   
   
  		<!-- 按钮 -->
  		<div class="padding-top-10 clear">
			<div class="col-md-12 pl200 ">
				<div class="mt40 tc mb50">
	    			<input class="btn btn-windows reset" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
				</div>
	  		</div>
	  	</div>
    
    			
    		</div>
    	</div>
    </div>
    
  </body>
</html>
