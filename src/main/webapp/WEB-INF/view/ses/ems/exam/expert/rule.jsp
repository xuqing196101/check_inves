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
		$(function(){
			var errorSingle = $("#errorSingle").val();
			var errorMultiple = $("#errorMultiple").val();
			var single = document.getElementsByName("single");
			var multiple = document.getElementsByName("multiple");
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
		})
		
		//自动计算总分
		function countScore(){
			//document.getElementById("singleNum").value=document.getElementById("singleNum").value.replace(/\D+/g,'');
			//document.getElementById("multipleNum").value=document.getElementById("multipleNum").value.replace(/\D+/g,'');
			var sn = $("#singleNum").val();
			var sp =$("#singlePoint").val();
			var mn = $("#multipleNum").val();
			var mp = $("#multiplePoint").val();
			$("#paperScore").val(sn*sp+mn*mp);
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
	   		<h2>专家考试规则设置</h2>
	   </div>
   </div>
  	<input type="hidden" value="${errorData['single'] }" id="errorSingle"/>
  	<input type="hidden" value="${errorData['multiple'] }" id="errorMultiple"/>
  	<form action="${pageContext.request.contextPath }/expertExam/saveExamRule.html" method="post">
  	  <div class="container mt20">
	  	<ul class="list-unstyled list-flow p0_20">
	    	<li class="col-md-12 p0">
	  			<span class="fl"><div class="red star_red">*</div>题型分布：</span>
	  			<div class="fl">
	  			   	<div class="fl">
		  			   	<label class="fl mt5">单选题：</label>
		  			   	<div class="fl mt5">
		  			   	    <input type="radio" name="single" onclick="checkSingleYes(this)" class="mt0" value="有"/>有
		  			   	    <input type="radio" name="single" onclick="checkSingleNo(this)" class="mt0" value="无"/>无 
		  			   	</div>
		  			   	<div class="fl" id="sin">
		  			   	  	<input type="text" value="${errorData['singleNum'] }" name="singleNum" id="singleNum" class="ml10 w50" onkeyup="countScore()"/>条<input type="text" value="${errorData['singlePoint'] }" name="singlePoint" id="singlePoint" class="ml10 w50" onkeyup="countScore()"/>分/条
		  			   	</div>
		  			   	<div class="fl mt5 ml5 red">${ERR_single }</div>
	  			   	</div>
	  			   	<div class="clear">
		    	   	    <label class="fl mt5">多选题：</label>
		    	   	    <div class="fl mt5">
			    	   	    <input type="radio" name="multiple" onclick="checkMultipleYes(this)" class="mt0" value="有"/>有
			    	   	    <input type="radio" name="multiple" onclick="checkMultipleNo(this)" class="mt0" value="无"/>无
		    	   	    </div>
			    	   	<div class="fl" id="mul">
			    	   	  	<input type="text" value="${errorData['multipleNum'] }" name="multipleNum" id="multipleNum" class="ml10 w50" onkeyup="countScore()"/>条<input type="text" value="${errorData['multiplePoint'] }" name="multiplePoint" id="multiplePoint" class="ml10 w50" onkeyup="countScore()"/>分/条
		  		        </div>
		  		        <div class="fl mt5 ml5 red">${ERR_multiple }</div>
	  		        </div>
	  		    </div>
	  		</li>
	  		
	    	<li class="col-md-12 p0">
	  			<span class="fl"><div class="red star_red">*</div>试卷分值：</span>
	  			<input type="text" name="paperScore" id="paperScore" value="${errorData['score'] }" readonly="readonly"/>分
	    	</li>
	    	
	    	<li class="col-md-12 p0">
	  			<span class="fl"><div class="red star_red">*</div>及格标准：</span>
	  			<div class="fl"><input type="text" name="passStandard" id="passStandard" value="${errorData['passStandard'] }"/>分</div>
	  			<div class="fl mt10 ml5 red">${ERR_passStandard }</div>
	    	</li>
	    	
	  		<li class="col-md-12 p0">
	  			<span class="fl mt5"><div class="red star_red">*</div>考试开始时间：</span>
		  		<input type="text" name="startTime" id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" class="Wdate mt5 fl" value="${errorData['startTime'] }"/>
	  			<div class="fl mt10 ml5 red">${ERR_time }</div>
	  		</li>
	    	
	    	<li class="col-md-12 p0">
	  			<span class="fl"><div class="red star_red">*</div>考试有效时间：</span>
	  			<div class="fl"><input type="text" name="testCycle" id="testCycle" value="${errorData['testCycle'] }"/>月</div>
	  			<div class="fl mt10 ml5 red">${ERR_testCycle }</div>
	    	</li>
	    </ul>
	    
	    <div class="padding-top-10 clear">
			<div class="col-md-12 tc">
				<div class="mt40 tc mb50">
				 	<button class="btn btn-windows save" type="submit">保存</button>
				</div>
	  		</div>
	  	</div>
	 </div>
    </form>
  </body>
</html>
