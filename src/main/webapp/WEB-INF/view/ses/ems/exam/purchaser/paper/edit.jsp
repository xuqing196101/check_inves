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
		
		//自动计算总分
		function countScore(){
			var sn = $("#singleNum").val();
			var sp =$("#singlePoint").val();
			var mn = $("#multipleNum").val();
			var mp = $("#multiplePoint").val();
			var jn = $("#judgeNum").val();
			var jp = $("#judgePoint").val();
			$("#paperScore").val(sn*sp+mn*mp+jn*jp);
			var paperScore = document.getElementById("paperScore").value;
			if(paperScore=="NaN"){
				$("#paperScore").val("0");
			}
		}
		
		//勾选单选题的有
		function checkSingleYes(obj){
			if($(obj).prop("checked")){
				$("#sin").show();
			}
		}
		
		//勾选单选题的无
		function checkSingleNo(obj){
			if($(obj).prop("checked")){
				$("#sin").hide();
			}
			$("#singleNum").val("");
			$("#singlePoint").val("");
			countScore();
		}
		
		//勾选多选题的有
		function checkMultipleYes(obj){
			if($(obj).prop("checked")){
				$("#mul").show();
			}
		}
		
		//勾选多选题的无
		function checkMultipleNo(obj){
			if($(obj).prop("checked")){
				$("#mul").hide();
			}
			$("#multipleNum").val("");
			$("#multiplePoint").val("");
			countScore();
		}
		
		//勾选判断题的有
		function checkJudgeYes(obj){
			if($(obj).prop("checked")){
				$("#ju").show();
			}
		}
		
		//勾选判断题的无
		function checkJudgeNo(obj){
			if($(obj).prop("checked")){
				$("#ju").hide();
			}
			$("#judgeNum").val("");
			$("#judgePoint").val("");
			countScore();
		}
		
		//返回到考卷列表
		function back(){
			window.location.href = "${pageContext.request.contextPath }/purchaserExam/backPaper.html";
		}
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
     	<form action="${pageContext.request.contextPath }/purchaserExam/editToExamPaper.html" method="post">
      	<input type="hidden" name="paperId" value="${examPaper.id }"/>
      	<input type="hidden" name="paperName" value="${examPaper.name }"/>
      	<input type="hidden" name="paperCode" value="${examPaper.code }"/>
      	<h2 class="list_title">修改考卷</h2>
       	<div class="ul_list">
    	<ul class="list-unstyled p0_20">
		     <li class="col-md-3 col-sm-6 col-xs-12 pl15">
	  			<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>试卷名称：</span>
		  		<div class="col-col-md-12 col-sm-12 col-xs-12md-12 input-append p0">
		  			<input type="text" name="name" value="${examPaper.name }"/>
		  			<div class="cue">${ERR_name }</div>
		  		</div>
	  		</li>
    		
    		<li class="col-md-3 col-sm-6 col-xs-12">
	  			<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>试卷编号：</span>
		  		<div class="col-col-md-12 col-sm-12 col-xs-12md-12 input-append p0">
		  			<input type="text" name="code" value="${examPaper.code }"/>
		  		 	<div class="cue">${ERR_code }</div>
		  		</div>
	  		</li>
    		
    		 <li class="col-md-3 col-sm-6 col-xs-12">
	  			<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>考试开始时间：</span>
		  		<div class="col-col-md-12 col-sm-12 col-xs-12md-12 input-append p0">
		  			<input type="text" value="${startTime }" name="startTime" id="startTime" class="Wdate mt5" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
	  				<div class="cue">${ERR_startTime }</div>
		  		</div>
	  		</li>
    		
    		<li class="col-md-3 col-sm-6 col-xs-12">
	  			<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>考试截止时间：</span>
		  		<div class="col-col-md-12 col-sm-12 col-xs-12md-12 input-append p0">
		  			<input type="text" value="${offTime }" name="offTime" id="offTime" class="Wdate mt5" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
	  				<div class="cue">${ERR_offTime }</div>
	  			</div>
	  		</li>
    		
    		<li class="col-md-12 col-sm-12 col-xs-12 pl15 mb20">
	  			<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>题型分布：</span>
	  			<div class="col-md-12 col-sm-12 col-xs-12 p0">
	  			   	<div class="col-md-6 col-sm-12 col-xs-12 input-append p0">
		  			   	<label class="fl mt5">单选题：</label>
		  			   	<div class="fl mt5">
		  			   	    <input type="radio" name="single" onclick="checkSingleYes(this)" class="mt0" value="有"/>有
		  			   	    <input type="radio" name="single" onclick="checkSingleNo(this)" class="mt0" value="无"/>无 
		  			   	</div>
		  			   	<div class="fl" id="sin">
		  			   	  	<input type="text" value="${singleNum }" name="singleNum" id="singleNum" class="ml10 w50" onkeyup="countScore()"/>条<input type="text" value="${singlePoint }" name="singlePoint" id="singlePoint" class="ml10 w50" onkeyup="countScore()"/>分/条
		  			   	</div>
		  			   	<div class="cue">${ERR_single }</div>
	  			   	</div>
	  			   	<div class="col-md-6 col-sm-12 col-xs-12 input-append p0">
		    	   	  	<label class="fl mt5">多选题：</label>
			    	   	<div class="fl mt5">
			    	   	    <input type="radio" name="multiple" onclick="checkMultipleYes(this)" class="mt0" value="有"/>有
			    	   	    <input type="radio" name="multiple" onclick="checkMultipleNo(this)" class="mt0" value="无"/>无
			    	   	</div>
			    	   	<div class="fl" id="mul">
			    	   	  	<input type="text" value="${multipleNum }" name="multipleNum" id="multipleNum" class="ml10 w50" onkeyup="countScore()"/>条<input type="text" value="${multiplePoint }" name="multiplePoint" id="multiplePoint" class="ml10 w50" onkeyup="countScore()"/>分/条
		  		        </div>
		  		        <div class="cue">${ERR_multiple }</div>
	  		        </div>
	  		        <div class="col-md-6 col-sm-12 col-xs-12 input-append p0">
		    	   	  	<label class="fl mt5">判断题：</label>
			    	   	<div class="fl mt5">
			    	   	    <input type="radio" name="judge" onclick="checkJudgeYes(this)" class="mt0" value="有"/>有
			    	   	    <input type="radio" name="judge" onclick="checkJudgeNo(this)" class="mt0" value="无"/>无
			    	   	</div>
			    	   	<div class="fl" id="ju">
			    	   	  	<input type="text" value="${judgeNum }" name="judgeNum" id="judgeNum" class="ml10 w50" onkeyup="countScore()"/>条<input type="text" value="${judgePoint }" name="judgePoint" id="judgePoint" class="ml10 w50" onkeyup="countScore()"/>分/条
		  		        </div>
		  		        <div class="cue">${ERR_judge }</div>
	  		        </div>
	  		    </div>
	  		</li>
	  		
    		<li class="col-md-3 col-sm-6 col-xs-12">
    			<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>总分值：</span>
	  			<div class="col-md-12 col-sm-12 col-xs-12 input-append p0">
		  			<input class="mr5" type="text" name="paperScore" id="paperScore" value="${examPaper.score }" readonly="readonly"/>分
	  			</div>
	  		</li>
    		
    		<li class="col-md-3 col-sm-6 col-xs-12">
	  			<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>及格标准：</span>
		  		<div class="col-col-md-12 col-sm-12 col-xs-12md-12 input-append p0">
		  			<input class="mr5" type="text" name="passStandard" id="passStandard" value="${examPaper.passStandard }"/>分
	  				<div class="cue">${ERR_passStandard }</div>
	  			</div>
	  		</li>
	  		
	  		<li class="col-md-3 col-sm-6 col-xs-12">
	  			<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>答题用时：</span>
		  		<div class="col-col-md-12 col-sm-12 col-xs-12md-12 input-append p0">
		  			<input class="mr5" type="text" name="testTime" id="testTime" value="${examPaper.testTime }"/>分钟
	  				<div class="cue">${ERR_testTime }</div>
	  			</div>
	  		</li>
	  		
	  		</ul>
   		</div>
   		
  		<!-- 按钮 -->
  		<div class="col-md-12 col-sm-12 col-xs-12 mt10 tc">
			<button class="btn btn-windows save" type="submit">保存</button>
	    	<input class="btn btn-windows back" value="返回" type="button" onclick="back()">
	  	</div>
    
    	</form>
    </div>
  </body>
</html>
