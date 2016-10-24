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
				$("#time").hide();
				document.getElementById("isAllowFalse").setAttribute("checked",true);
			}else{
				$("#time").show();
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
	  			<span class="fl">试卷名称：</span>
		  		<input type="text" name="paperName" value="${examPaper.name }" disabled="disabled"/>
	  		</li>
    		
    		<li class="col-md-12 p0">
	  			<span class="fl">试卷编号：</span>
		  		<input type="text" name="paperNo" value="${examPaper.code }" disabled="disabled"/>
	  		</li>
    		
    		<li class="col-md-12 p0">
	  			<span class="fl">题型分布：</span>
	  			<div class="fl">
	  			   <label class="fl mt5">单选题：</label><input type="text" name="singleNum" id="singleNum" class="ml10 w50" disabled="disabled"/>条<input type="text" name="singlePoint" id="singlePoint" class="ml10 w50" disabled="disabled"/>分/条<br/>
		    	   <label class="fl mt5">多选题：</label><input type="text" name="multipleNum" id="multipleNum" class="ml10 w50" disabled="disabled"/>条<input type="text" name="multiplePoint" id="multiplePoint" class="ml10 w50" disabled="disabled"/>分/条<br/>
		    	   <label class="fl mt5">判断题：</label> <input type="text" name="judgeNum" id="judgeNum" class="ml10 w50" disabled="disabled"/>条<input type="text" name="judgePoint" id="judgePoint" class="ml10 w50" disabled="disabled"/>分/条<br/>
	  		    </div>
	  		    <div class="clear"></div>
	  		</li>
    		
    		<li class="col-md-12 p0">
	  			<span class="fl mt5">总分值：</span>
		  		<input class="w50 mt5" type="text" name="totalPoint" id="totalPoint" value="${examPaper.score }" disabled="disabled"/>分
	  		</li>
    		
    		<li class="col-md-12 p0">
	  			<span class="fl">及格标准：</span>
		  		<input class="w50" type="text" name="passStandard" id="passStandard" value="${examPaper.passStandard }" disabled="disabled"/>分
	  		</li>
    		
    		<li class="col-md-12 p0">
	  			<span class="fl mt5">考试开始时间：</span>
		  		<input type="text" disabled="disabled" value="${startTime }" name="startTime" id="startTime" class="Wdate mt5" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
	  		</li>
	  		
	  		<li class="col-md-12 p0">
	  			<span class="fl mt5">考试截止时间：</span>
		  		<input type="text" disabled="disabled" value="${offTime }" name="offTime" id="offTime" class="Wdate mt5" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
	  		</li>
	  		
	  		<li class="col-md-12 p0">
	  			<span class="fl mb5">允许30分钟内重考：</span>
		  		<input class="mt0" type="checkbox" id="isAllowTrue" value="是" disabled="disabled"/>是
    			<input class="mt0" type="checkbox" id="isAllowFalse" value="否" disabled="disabled"/>否
	  		</li>
	  		
	  		<li class="col-md-12 p0" id="time">
	  			<span class="fl mt5">考试答题时间：</span>
		  		<input class="w50 mt5" type="text" name="useTime" id="useTime" value="${examPaper.testTime }" disabled="disabled"/>分钟
	  		</li>
	  		
	  		<%--<li class="col-md-12 p0 mt10 red">
	  			*注意:每份考卷的登录有效期为15分钟,如:考卷开始时间上午9点,请相关参考人员于上午9点至上午9点15分内登录考试系统考试,否则视为弃考,计0分。
	  		</li>
    	--%></ul>
   
   
	  		<!-- 按钮 -->
	  		<div class="padding-top-10 clear">
				<div class="col-md-12 pl200 ">
					<div class="mt40 tc mb50">
		    			<input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
					</div>
		  		</div>
		  	</div>
    
    			
    		</div>
    	</div>
    </div>
    
  </body>
</html>
