<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>查看考卷</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		$(function(){
			var single = document.getElementsByName("single");
			var multiple = document.getElementsByName("multiple");
			var judge = document.getElementsByName("judge");
			var singleNum = $("#singleNum").val();
			var multipleNum = $("#multipleNum").val();
			var judgeNum = $("#judgeNum").val();
			var errorSingle = $("#errorSingle").val();
			var errorMultiple = $("#errorMultiple").val();
			var errorJudge = $("#errorJudge").val();
			if(errorSingle==null||errorSingle==""){
				$("#sin").hide();
			}else if(errorSingle=="无"){
				$(single[1]).attr("checked","checked");
				$("#sin").hide();
			}else if(errorSingle=="有"){
				$(single[0]).attr("checked","checked");
				$("#sin").show();
			}
			if(errorMultiple==null||errorMultiple==""){
				$("#mul").hide();
			}else if(errorMultiple=="无"){
				$(multiple[1]).attr("checked","checked");
				$("#mul").hide();
			}else if(errorMultiple=="有"){
				$(multiple[0]).attr("checked","checked");
				$("#mul").show();
			}
			if(errorJudge==null||errorJudge==""){
				$("#ju").hide();
			}else if(errorJudge=="无"){
				$(judge[1]).attr("checked","checked");
				$("#ju").hide();
			}else if(errorJudge=="有"){
				$(judge[0]).attr("checked","checked");
				$("#ju").show();
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
	
	<input type="hidden" value="${errorSingle }" id="errorSingle"/>
  	<input type="hidden" value="${errorMultiple }" id="errorMultiple"/>
	<input type="hidden" value="${errorJudge }" id="errorJudge"/>
	
	<div class="container container_box">
	   	<h2 class="list_title">查看考卷</h2>
       	<div class="ul_list">
    	 	<ul class="list-unstyled p0_20">
		     	<li class="col-md-3 col-sm-6 col-xs-12 pl15">
		  			<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>试卷名称：</span>
			  		<div class="col-col-md-12 col-sm-12 col-xs-12md-12 input-append p0">
			  			<input type="text" name="name" value="${examPaper.name }" disabled="disabled"/>
		  			</div>
	  			</li>
    		
	    		<li class="col-md-3 col-sm-6 col-xs-12">
		  			<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>试卷编号：</span>
			  		<div class="col-col-md-12 col-sm-12 col-xs-12md-12 input-append p0">
			  			<input type="text" name="code" value="${examPaper.code }" disabled="disabled"/>
		  			</div>
		  		</li>
    		
	    		<li class="col-md-3 col-sm-6 col-xs-12">
		  			<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>考试开始时间：</span>
			  		<div class="col-col-md-12 col-sm-12 col-xs-12md-12 input-append p0">
			  			<input type="text" disabled="disabled" value="${startTime }" name="startTime" id="startTime" class="Wdate mt5" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
		  			</div>
		  		</li>
	  		
		  		<li class="col-md-3 col-sm-6 col-xs-12">
		  			<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>考试截止时间：</span>
			  		<div class="col-col-md-12 col-sm-12 col-xs-12md-12 input-append p0">
			  			<input type="text" disabled="disabled" value="${offTime }" name="offTime" id="offTime" class="Wdate mt5" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
		  			</div>
		  		</li>
    		
    		<li class="col-md-12 col-sm-12 col-xs-12 pl15 mb20">
	  			<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>题型分布：</span>
	  			<div class="col-md-12 col-sm-12 col-xs-12 p0">
	  			   	<div class="col-md-6 col-sm-12 col-xs-12 input-append p0">
		  			   	<label class="fl mt5">单选题：</label>
		  			   	<div class="fl mt5">
		  			   	    <input type="radio" name="single" class="mt0" value="有" disabled="disabled"/>有
		  			   	    <input type="radio" name="single" class="mt0" value="无" disabled="disabled"/>无 
		  			   	</div>
		  			   	<div class="fl" id="sin">
		  			   	  	<input type="text" value="${singleNum }" name="singleNum" id="singleNum" class="ml10 w50" disabled="disabled"/>条<input type="text" value="${singlePoint }" name="singlePoint" id="singlePoint" class="ml10 w50" disabled="disabled"/>分/条
		  			   	</div>
	  			   	</div>
	  			   	<div class="col-md-6 col-sm-12 col-xs-12 input-append p0">
		    	   	  	<label class="fl mt5">多选题：</label>
			    	   	<div class="fl mt5">
			    	   	    <input type="radio" name="multiple" class="mt0" value="有" disabled="disabled"/>有
			    	   	    <input type="radio" name="multiple" class="mt0" value="无" disabled="disabled"/>无
			    	   	</div>
			    	   	<div class="fl" id="mul">
			    	   	  	<input type="text" value="${multipleNum }" name="multipleNum" id="multipleNum" class="ml10 w50" disabled="disabled"/>条<input type="text" value="${multiplePoint }" name="multiplePoint" id="multiplePoint" class="ml10 w50" disabled="disabled"/>分/条
		  		        </div>
	  		        </div>
	  		        <div class="col-md-6 col-sm-12 col-xs-12 input-append p0">
		    	   	  	<label class="fl mt5">判断题：</label>
			    	   	<div class="fl mt5">
			    	   	    <input type="radio" name="judge" class="mt0" value="有" disabled="disabled"/>有
			    	   	    <input type="radio" name="judge" class="mt0" value="无" disabled="disabled"/>无
			    	   	</div>
			    	   	<div class="fl" id="ju">
			    	   	  	<input type="text" value="${judgeNum }" name="judgeNum" id="judgeNum" class="ml10 w50" disabled="disabled"/>条<input type="text" value="${judgePoint }" name="judgePoint" id="judgePoint" class="ml10 w50" disabled="disabled"/>分/条
		  		        </div>
	  		        </div>
	  		    </div>
	  		</li>
    		
    		<li class="col-md-3 col-sm-6 col-xs-12">
	  			<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>总分值：</span>
	  			<div class="col-md-12 col-sm-12 col-xs-12 input-append p0">
		  			<input class="mr5" type="text"  value="${examPaper.score }" disabled="disabled"/>分
	  			</div>
	  		</li>
    		
    		<li class="col-md-3 col-sm-6 col-xs-12">
	  			<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>及格标准：</span>
		  		<div class="col-col-md-12 col-sm-12 col-xs-12md-12 input-append p0">
		  			<input class="mr5" type="text" name="passStandard" id="passStandard" value="${examPaper.passStandard }" disabled="disabled"/>分
	  			</div>
	  		</li>
	  		
	  		<li class="col-md-3 col-sm-6 col-xs-12">
	  			<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>答题用时：</span>
		  		<div class="col-col-md-12 col-sm-12 col-xs-12md-12 input-append p0">
		  			<input class="mr5" type="text" value="${examPaper.testTime }" disabled="disabled"/>分钟
	  			</div>
	  		</li>
	  		
	  		</ul>
        </div>
   
	  		<!-- 按钮 -->
	  		<div class="col-md-12 col-sm-12 col-xs-12 mt10 tc">
		    	<input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
		  	</div>
    	</div>
  </body>
</html>
