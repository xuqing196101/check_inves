<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>采购人新增题库</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
	
	$(function(){
		var que = document.getElementsByName("que");
		var judge = document.getElementsByName("judge");
		var option = document.getElementsByName("option");
		document.getElementById("queTopic").setAttribute("disabled",true);
		for(var i=0;i<option.length;i++){
			option[i].setAttribute("disabled",true);
		}
		for(var i=0;i<que.length;i++){
			que[i].setAttribute("disabled",true);
		}
		for(var i=0;i<judge.length;i++){
			judge[i].setAttribute("disabled",true);
		}
		$("#form").validate({
			errorElement: "span",
			focusInvalid : false, //当为false时，验证无效时，没有焦点响应  
			onkeyup : false,
			rules:{
				queType:"required",
				queTopic:"required"
			},
			messages:{
				queType:"题型不能为空",
				queTopic:"题干不能为空"
			}
		});
		
	})
	
	//保存到法律题库
	function save(){
		var ques = "";
		var judges = "";
		var queType = $("#queType").val();
		var que = document.getElementsByName("que");
		var judge = document.getElementsByName("judge");
		var quePoint = $("#quePoint").val();
		if(queType){
			if(queType==1){
				for(var i=0;i<que.length;i++){ 
					if(que[i].checked){
						ques += que[i].value+',';
					}
				}
			}else if(queType==2){
				for(var i=0;i<que.length;i++){
					if(que[i].checked){
						ques += que[i].value+',';
					}
				}
			}else if(queType==3){
				for(var i=0;i<judge.length;i++){
					if(judge[i].checked){
						judges += judge[i].value+',';
					}
				}
			}
		}
		$("#form").submit();
	}
	
	//切换题型
	function changeType(){
		$("span.invalid").remove();
		$("#queJudge").html(" ");
		var queType = $("#queType").val();
		var que = document.getElementsByName("que");
		var judge = document.getElementsByName("judge");
		var option = document.getElementsByName("option");
		for(var i=0;i<que.length;i++){
			$(que[i]).removeAttr("checked");
		}
		for(var i=0;i<judge.length;i++){
			$(judge[i]).removeAttr("checked");
		}
		if(queType){
			if(queType==1){
				$("#queTopic").attr("disabled",false);
				for(var i=0;i<option.length;i++){
					$(option[i]).attr("disabled",false);
				}
				for(var i=0;i<que.length;i++){
					$(que[i]).attr("type","radio");
					$(que[i]).attr("disabled",false);
				}
				$("#optionA").attr("disabled",false);
				$("#optionB").attr("disabled",false);
				$("#optionC").attr("disabled",false);
				$("#optionD").attr("disabled",false);
				$("#optionA").val(" ");
				$("#optionB").val(" ");
				$("#optionC").val(" ");
				$("#optionD").val(" ");
				$("#queTopic").val(" ");
				$("#items").show();
				$("#selects").show();
				$("#judge").hide();
			}else if(queType==2){
				$("#queTopic").attr("disabled",false);
				for(var i=0;i<option.length;i++){
					$(option[i]).attr("disabled",false);
				}
				for(var i=0;i<que.length;i++){
					$(que[i]).attr("type","checkbox");
					$(que[i]).attr("disabled",false);
				}
				$("#optionA").attr("disabled",false);
				$("#optionB").attr("disabled",false);
				$("#optionC").attr("disabled",false);
				$("#optionD").attr("disabled",false);
				$("#optionA").val(" ");
				$("#optionB").val(" ");
				$("#optionC").val(" ");
				$("#optionD").val(" ");
				$("#queTopic").val(" ");
				$("#items").show();
				$("#judge").hide();
				$("#selects").show();
			}else if(queType==3){
				$("#queTopic").attr("disabled",false);
				for(var i=0;i<judge.length;i++){
					$(judge[i]).attr("disabled",false);
				}
				$("#optionA").val(" ");
				$("#optionB").val(" ");
				$("#optionC").val(" ");
				$("#optionD").val(" ");
				$("#queTopic").val(" ");
				$("#items").hide();
				$("#judge").show();
				$("#selects").hide();
			}
		}else{
			document.getElementById("queTopic").setAttribute("disabled",true);
			for(var i=0;i<option.length;i++){
				option[i].setAttribute("disabled",true);
			}
			for(var i=0;i<que.length;i++){
				que[i].setAttribute("disabled",true);
			}
			for(var i=0;i<judge.length;i++){
				judge[i].setAttribute("disabled",true);
			}
			$("#queTopic").val(" ");
			$("#optionA").val(" ");
			$("#optionB").val(" ");
			$("#optionC").val(" ");
			$("#optionD").val(" ");
		}
		
	}
	</script>

  </head>
  
  <body>
    <!--面包屑导航开始-->
	   <div class="margin-top-10 breadcrumbs ">
	      <div class="container">
			   <ul class="breadcrumb margin-left-0">
			   <li><a href="#">首页</a></li><li><a href="#">支撑环境</a></li><li><a href="#">题库管理</a></li><li class="active"><a href="#">采购人题库管理</a></li>
			   </ul>
			<div class="clear"></div>
		  </div>
	   </div>
	   
	 <div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
     <div>
		   <div class="headline-v2">
		   		<h2>新增题目</h2>
		   </div>
  	<form action="<%=path %>/purchaserExam/saveToPurPool.html" method="post" id="form">
  		<ul class="list-unstyled list-flow p0_20">
		     <li class="col-md-12 p0">
	  			<span class="fl">请选择题型：</span>
		  		<select id="queType" name="queType" onchange="changeType()">
		  			<option value="">请选择</option>
		  			<option value="1">单选题</option>
		  			<option value="2">多选题</option>
		  			<option value="3">判断题</option>
		  		</select>
	  		</li>
	  		
	  		
	  		
  			<li class="col-md-12 p0">
			   <span class="fl">题干：</span>
			   <div class="">
		        	<textarea class="text_area col-md-8" name="queTopic" id="queTopic"></textarea>
		       </div>
			</li> 
			
			
  		<li class="col-md-12 p0" id="items">
				<span class="fl">选项：</span>
				<div class="col-md-9 p0">
				<div>
			  		<div class="fl mt5">A</div><textarea name="option" id="optionA" class="ml5 col-md-8"></textarea>
			  		<div class="clear"></div>
			  	</div>
			  	<div class="clear mt10">
					<div class="fl mt5">B</div><textarea name="option" id="optionB" class="ml5 col-md-8"></textarea>
				    <div class="clear"></div>
				</div>
				<div class="clear mt10">
					<div class="fl mt5">C</div><textarea name="option" id="optionC" class="ml5 col-md-8"></textarea>
				    <div class="clear"></div>
				</div>
				<div class="clear mt10">
					<div class="fl mt5">D</div><textarea name="option" id="optionD" class="ml5 col-md-8"></textarea>
				    <div class="clear"></div>
				</div>
		       </div>
			 </li> 
  		
  				`<li class="col-md-12 p0">
					<span class="fl">答案：</span>
				<div class="fl">	
					<div class="fl mt5" id="selects">
			         <input type="radio" id="A" name="que" value="A" class="mt0"/>A 
		  			 <input type="radio" id="B" name="que" value="B" class="mt0"/>B
		  			 <input type="radio" id="C" name="que" value="C" class="mt0"/>C
		  			 <input type="radio" id="D" name="que" value="D" class="mt0"/>D
			       </div>
				   <div class="clear fl mt5" id="judge">
					 <input type="radio" name="judge" value="对" class="mt0"/>对
					 <input type="radio" name="judge" value="错" class="mt0"/>错
			       </div>
			     </div>
				</li>
  		
  		<%--<div>
  			答案:A <input type="radio" name="que" value="A" onclick="queDis()"/> B <input type="radio" name="que" value="B" onclick="queDis()"/> C <input type="radio" name="que" value="C" onclick="queDis()"/> D <input type="radio" name="que" value="D" onclick="queDis()"/><span id="queSelect"></span><br/>
  			对:<input type="radio" name="judge" value="对" onclick="judgeDis()"/>错:<input type="radio" name="judge" value="错" onclick="judgeDis()"/><span id="queJudge"></span>
  		</div>
  		--%>
  		
  			<%--<li class="col-md-12 p0">
	  			<span class="fl">分值:</span>
	  			<select name="quePoint" id="quePoint">
	  				<option value="1">1</option>
	  				<option value="2">2</option>
	  				<option value="3">3</option>
	  				<option value="4">4</option>
	  				<option value="5">5</option>
	  			</select>
  			</li>
  			--%></ul>
  			
  		
  		<!-- 按钮 -->
  		<div class="padding-top-10 clear">
			<div class="col-md-12 pl200 ">
				<div class="mt40 tc mb50">
				 	<button class="btn btn-windows save" type="button" onclick="save()">保存</button>
	    			<input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
				</div>
	  		</div>
	  	</div>
  	</form>
  			</div>
  		</div>
  	</div>
  </body>
</html>
