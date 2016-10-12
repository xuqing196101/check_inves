<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>新增技术类专家题库</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		$(function(){
			var que = document.getElementsByName("answer");
			document.getElementById("queTopic").setAttribute("disabled",true);
			document.getElementById("quePoint").setAttribute("disabled",true);
			document.getElementById("optionA").setAttribute("disabled",true);
			document.getElementById("optionB").setAttribute("disabled",true);
			document.getElementById("optionC").setAttribute("disabled",true);
			document.getElementById("optionD").setAttribute("disabled",true);
			for(var i=0;i<que.length;i++){
				que[i].setAttribute("disabled",true);
			}
		})
		
		//保存到技术题库
		function save(){
			$("#form").submit();
		}
		
		//切换题型
		function changeType(){
			var queType = $("#queType").val();
			var que = document.getElementsByName("answer");
			var all_options = document.getElementById("quePoint").options;
			for(var i=0;i<que.length;i++){
				$(que[i]).removeAttr("checked");
			}
			if(queType){
				if(queType==1){
					for(var i=0;i<que.length;i++){
						$(que[i]).attr("type","radio");
						$(que[i]).attr("disabled",false);
					}
					$("#queTopic").attr("disabled",false);
					$("#quePoint").attr("disabled",false);
					$("#optionA").attr("disabled",false);
					$("#optionB").attr("disabled",false);
					$("#optionC").attr("disabled",false);
					$("#optionD").attr("disabled",false);
					$("#queTopic").val(" ");
					$("#optionA").val(" ");
					$("#optionB").val(" ");
					$("#optionC").val(" ");
					$("#optionD").val(" ");
					all_options[0].selected = true;
				}else if(queType==2){
					for(var i=0;i<que.length;i++){
						$(que[i]).attr("type","checkbox");
						$(que[i]).attr("disabled",false);
					}
					$("#queTopic").attr("disabled",false);
					$("#quePoint").attr("disabled",false);
					$("#optionA").attr("disabled",false);
					$("#optionB").attr("disabled",false);
					$("#optionC").attr("disabled",false);
					$("#optionD").attr("disabled",false);
					$("#queTopic").val(" ");
					$("#optionA").val(" ");
					$("#optionB").val(" ");
					$("#optionC").val(" ");
					$("#optionD").val(" ");
					all_options[0].selected = true;
				}
			}else{
				for(var i=0;i<que.length;i++){
					$(que[i]).attr("disabled",true);
				}
				document.getElementById("queTopic").setAttribute("disabled",true);
				document.getElementById("quePoint").setAttribute("disabled",true);
				document.getElementById("optionA").setAttribute("disabled",true);
				document.getElementById("optionB").setAttribute("disabled",true);
				document.getElementById("optionC").setAttribute("disabled",true);
				document.getElementById("optionD").setAttribute("disabled",true);
				$("#queTopic").val(" ");
				$("#optionA").val(" ");
				$("#optionB").val(" ");
				$("#optionC").val(" ");
				$("#optionD").val(" ");
				all_options[0].selected = true;
			}
			
		}
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
	   
     <div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
     <div>
		   <div class="headline-v2">
		   		<h2>新增技术类题目</h2>
		   </div>
	   
  	<form action="<%=path %>/expertExam/saveToTec.html" method="post" id="form">
  		<ul class="list-unstyled list-flow p0_20">
		     <li class="col-md-12 p0">
	  			<span class="fl"><div class="red star_red">*</div>请选择题型：</span>
		  		<select id="queType" name="queType" onchange="changeType()">
		  			<option value="">请选择</option>
		  			<option value="1">单选题</option>
		  			<option value="2">多选题</option>
		  		</select>
		  		<div class="validate">${ERR_type}</div>
	  		</li>
  		
		    <li class="col-md-12 p0">
			   <span class="fl"><div class="red star_red">*</div>题干：</span>
			   <div class="red">
		        	<textarea class="text_area col-md-8" name="topic" id="queTopic"></textarea>
		       		${ERR_topic}
		       </div>
			</li> 
		   

		
				<li class="col-md-12 p0">
					<span class="fl"><div class="red star_red">*</div>选项：</span>
					<div class="col-md-9">
						<div>
					  		<div class="fl mt5">A</div><textarea name="optionA" id="optionA" class="ml5 col-md-8"></textarea>
					  		<div class="clear red">${ERR_optionA }</div>
					  	</div>
					  	<div class="clear mt10">
							<div class="fl mt5">B</div><textarea name="optionB" id="optionB" class="ml5 col-md-8"></textarea>
						    <div class="clear red">${ERR_optionB }</div>
						</div>
						<div class="clear mt10">
							<div class="fl mt5">C</div><textarea name="optionC" id="optionC" class="ml5 col-md-8"></textarea>
						    <div class="clear red">${ERR_optionC }</div>
						</div>
						<div class="clear mt10">
							<div class="fl mt5">D</div><textarea name="optionD" id="optionD" class="ml5 col-md-8"></textarea>
						    <div class="clear red">${ERR_optionD }</div>
						</div>
			       	</div>
				 </li> 
			

		
				<li class="col-md-12 p0">
					<span class="fl"><div class="red star_red">*</div>答案：</span>	
					<div class="fl ml5 mt5">
				        <input type="radio" id="A" name="answer" value="A" class="mt0"/>A 
			  			<input type="radio" id="B" name="answer" value="B" class="mt0"/>B 
			  			<input type="radio" id="C" name="answer" value="C" class="mt0"/>C 
			  			<input type="radio" id="D" name="answer" value="D" class="mt0"/>D
			  			<div class="clear red">${ERR_answer }</div>
			       </div>
				</li>
			

			<li class="col-md-12 p0">
	  			<span class="fl"><div class="red star_red">*</div>分值：</span>
	  			<select name="quePoint" id="quePoint">
	  				<option value="1">1</option>
	  				<option value="2">2</option>
	  				<option value="3">3</option>
	  				<option value="4">4</option>
	  				<option value="5">5</option>
	  			</select>
  			</li>
  		</ul>
  	
  	
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
