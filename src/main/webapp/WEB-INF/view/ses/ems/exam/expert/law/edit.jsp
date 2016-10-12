<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>修改法律类专家题库</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		$(function(){
			var answer = document.getElementsByName("answer");
			var queType = $("#queType").val();
			if(queType){
				if(queType==1){
					for(var i=0;i<answer.length;i++){
						$(answer[i]).attr("type","radio");
						$(answer[i]).attr("disabled",false);
					}
				}else if(queType==2){
					for(var i=0;i<answer.length;i++){
						$(answer[i]).attr("type","checkbox");
						$(answer[i]).attr("disabled",false);
					}
				}
			}else{
				for(var i=0;i<answer.length;i++){
					answer[i].setAttribute("disabled",true);
				}
			}
			var queAnswer = "${lawAnswer}";
			if(queAnswer.indexOf('A')>-1){
				var qa= document.getElementById("A");
				qa.setAttribute("checked",true);
			}
			if(queAnswer.indexOf('B')>-1){
				var qb= document.getElementById("B");
				qb.setAttribute("checked",true);
			}
			if(queAnswer.indexOf('C')>-1){
				var qc= document.getElementById("C");
				qc.setAttribute("checked",true);
			}
			if(queAnswer.indexOf('D')>-1){
				var qd= document.getElementById("D");
				qd.setAttribute("checked",true);
			}
		})
		
		//保存到法律题库
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
		   	<h2>修改法律类题目</h2>
		</div>
		
  	<form action="<%=path %>/expertExam/editToLaw.html?id=${lawQue.id }" method="post" id="form">
		<input type="hidden" name="content" value="${lawQue.topic }"/>
		<ul class="list-unstyled list-flow p0_20">
		     <li class="col-md-12 p0">
	  			<span class="fl"><div class="red star_red">*</div>请选择题型：</span>
		  		<select id="queType" name="queType" onchange="changeType()">
		  			<option value="">请选择</option>
		  			<c:forEach items="${examPoolType }" var="e">
		  				<c:choose>
		  					<c:when test="${e.id==lawQue.questionTypeId }">
		  						<option value="${e.id }" selected="selected">${e.name }</option>
		  					</c:when>
		  					<c:otherwise>
		  						<option value="${e.id }">${e.name }</option>
		  					</c:otherwise>
		  				</c:choose>
		  			</c:forEach>
		  		</select>
		  		<div class="validate">${ERR_type}</div>
	  		</li>
		
			<li class="col-md-12 p0">
			   <span class="fl"><div class="red star_red">*</div>题干：</span>
			   <div class="red">
		        	<textarea class="text_area col-md-8" name="topic" id="queTopic">${lawQue.topic }</textarea>
		       		${ERR_topic }
		       </div>
			 </li> 
		   
	  	
	  		<li class="col-md-12 p0">
				<span class="fl"><div class="red star_red">*</div>选项：</span>
				<div class="col-md-9">
				<div>
			  		<div class="fl mt5">A</div><textarea name="optionA" id="optionA" class="ml5 col-md-8">${optionA}</textarea>
			  		<div class="clear red">${ERR_optionA }</div>
			  	</div>
			  	<div class="clear mt10">
					<div class="fl mt5">B</div><textarea name="optionB" id="optionB" class="ml5 col-md-8">${optionB}</textarea>
				    <div class="clear red">${ERR_optionB }</div>
				</div>
				<div class="clear mt10">
					<div class="fl mt5">C</div><textarea name="optionC" id="optionC" class="ml5 col-md-8">${optionC}</textarea>
				    <div class="clear red">${ERR_optionC }</div>
				</div>
				<div class="clear mt10">
					<div class="fl mt5">D</div><textarea name="optionD" id="optionD" class="ml5 col-md-8">${optionD}</textarea>
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
		  				<option value="1" 
		  					<c:if test="${lawQue.point==1 }">
		  						selected
		  					</c:if>
		  				>1</option>
		  				<option value="2" 
		  					<c:if test="${lawQue.point==2 }">
		  						selected
		  					</c:if>
		  				>2</option>
		  				<option value="3" 
		  					<c:if test="${lawQue.point==3 }">
		  						selected
		  					</c:if>
		  				>3</option>
		  				<option value="4" 
		  					<c:if test="${lawQue.point==4 }">
		  						selected
		  					</c:if>
		  				>4</option>
		  				<option value="5" 
		  					<c:if test="${lawQue.point==5 }">
		  						selected
		  					</c:if>
		  				>5</option>
		  			</select>
		  		</li>
  			</ul>
  		
  		<!-- 底部按钮 -->
	  	<div class="padding-top-10 clear">
			<div class="col-md-12 pl200 ">
				<div class="mt40 tc mb50">
					<button class="btn btn-windows save" onclick="save()">保存</button>
		  			<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
	  			</div>
	  		</div>
	  	</div>
	  	
  		</form>
	  		</div>
		</div>
	</div>
  </body>
</html>
