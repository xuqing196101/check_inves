<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>修改技术类专家题库</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		$(function(){
			var que = document.getElementsByName("que");
			var queType = $("#queType").val();
			if(queType){
				if(queType==1){
					for(var i=0;i<que.length;i++){
						$(que[i]).attr("type","radio");
						$(que[i]).attr("disabled",false);
					}
				}else if(queType==2){
					for(var i=0;i<que.length;i++){
						$(que[i]).attr("type","checkbox");
						$(que[i]).attr("disabled",false);
					}
				}
				for(var i=0;i<que.length;i++){
					$(que[i]).click(function(){
						$("#queSelect").html(" ");
					})
				}
			}else{
				for(var i=0;i<que.length;i++){
					que[i].setAttribute("disabled",true);
				}
			}
			var queAnswer = "${tecAnswer}";
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
			$("#form").validate({
				errorElement: "span",
				focusInvalid : false, //当为false时，验证无效时，没有焦点响应  
				onkeyup : false,
				rules:{
					queTypeName:"required",
					queTopic:"required"
					//queOption:"required"
				},
				messages:{
					queTypeName:"题型不能为空",
					queTopic:"题干不能为空"
					//queOption:"选项不能为空"
				}
			});
			
		})
		
		//保存到法律题库
		function save(){
			var ques = "";
			var judges = "";
			var queType = $("#queType").val();
			var que = document.getElementsByName("que");
			var quePoint = $("#quePoint").val();
			if(queType){
				if(queType==1){
					for(var i=0;i < que.length;i++){ 
						if(que[i].checked){
							ques += que[i].value+',';
						}
					}
					if(ques==null||ques==""){
						$("#queSelect").html("请选择答案");
						return;
					}
				}else if(queType==2){
					for(var i=0;i < que.length;i++){
						if(que[i].checked){
							ques += que[i].value+',';
						}
					}
					if(ques==null||ques==""){
						$("#queSelect").html("请选择答案");
						return;
					}
				}
			}else{
				$("#queSelect").html(" ");
			}
			$("#form").submit();
		}
		
		//切换题型
		function changeType(){
			$("span.invalid").remove();
			$("#queSelect").html(" ");
			var queType = $("#queType").val();
			var que = document.getElementsByName("que");
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
					//$("#queOption").attr("disabled",false);
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
					//$("#queOption").attr("disabled",false);
					$("#queTopic").val(" ");
					$("#optionA").val(" ");
					$("#optionB").val(" ");
					$("#optionC").val(" ");
					$("#optionD").val(" ");
					all_options[0].selected = true;
				}
			}else{
				for(var i=0;i<que.length;i++){
					que[i].setAttribute("disabled",true);
				}
				$("#queTopic").val(" ");
				$("#optionA").val(" ");
				$("#optionB").val(" ");
				$("#optionC").val(" ");
				$("#optionD").val(" ");
				all_options[0].selected = true;
			}
			
		}
		
		function queDis(){
			$("#queSelect").html(" ");
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
		   	<h2>修改技术类题目</h2>
		</div>
		
  	<form action="<%=path %>/expertExam/editToTec.html?id=${tecQue.id }" method="post" id="form">
		<ul class="list-unstyled list-flow p0_20">
		     <li class="col-md-12 p0">
	  			<span class="fl">请选择题型：</span>
		  		<select id="queType" name="queType" onchange="changeType()">
		  			<option value="">请选择</option>
		  			<c:forEach items="${examPoolType }" var="e">
		  				<c:choose>
		  					<c:when test="${e.id==tecQue.questionTypeId }">
		  						<option value="${e.id }" selected="selected">${e.name }</option>
		  					</c:when>
		  					<c:otherwise>
		  						<option value="${e.id }">${e.name }</option>
		  					</c:otherwise>
		  				</c:choose>
		  			</c:forEach>
		  		</select>
	  		</li>
		
			<li class="col-md-12 p0">
			   <span class="fl">题干：</span>
			   <div class="">
		        	<textarea class="text_area col-md-8" name="queTopic" id="queTopic">${tecQue.topic }</textarea>
		       </div>
			 </li> 
		   
	  	
	  		<li class="col-md-12 p0">
				<span class="fl">选项：</span>
				<div class="col-md-9 p0">
				<div>
			  		<div class="fl mt5">A</div><textarea name="option" id="optionA" class="ml5 col-md-8">${optionA}</textarea>
			  		<div class="clear"></div>
			  	</div>
			  	<div class="clear mt10">
					<div class="fl mt5">B</div><textarea name="option" id="optionB" class="ml5 col-md-8">${optionB}</textarea>
				    <div class="clear"></div>
				</div>
				<div class="clear mt10">
					<div class="fl mt5">C</div><textarea name="option" id="optionC" class="ml5 col-md-8">${optionC}</textarea>
				    <div class="clear"></div>
				</div>
				<div class="clear mt10">
					<div class="fl mt5">D</div><textarea name="option" id="optionD" class="ml5 col-md-8">${optionD}</textarea>
				    <div class="clear"></div>
				</div>
		       </div>
			 </li> 
		   
		  		<li class="col-md-12 p0">
					<span class="fl">答案：</span>	
					<div class="fl ml5 mt5">
			        A <input type="radio" id="A" name="que" value="A" class="mt0"/> 
		  			B <input type="radio" id="B" name="que" value="B" class="mt0"/> 
		  			C <input type="radio" id="C" name="que" value="C" class="mt0"/> 
		  			D <input type="radio" id="D" name="que" value="D" class="mt0"/>
			       </div>
					<span id="queSelect"></span>
				</li>
				
			  <li class="col-md-12 p0">
			  		<span class="fl">分值：</span>
		  			<select name="quePoint" id="quePoint">
		  				<option value="1" 
		  					<c:if test="${tecQue.point==1 }">
		  						selected
		  					</c:if>
		  				>1</option>
		  				<option value="2" 
		  					<c:if test="${tecQue.point==2 }">
		  						selected
		  					</c:if>
		  				>2</option>
		  				<option value="3" 
		  					<c:if test="${tecQue.point==3 }">
		  						selected
		  					</c:if>
		  				>3</option>
		  				<option value="4" 
		  					<c:if test="${tecQue.point==4 }">
		  						selected
		  					</c:if>
		  				>4</option>
		  				<option value="5" 
		  					<c:if test="${tecQue.point==5 }">
		  						selected
		  					</c:if>
		  				>5</option>
		  			</select>
		  		</li>
  			</ul>
  		</div>
  		<!-- 底部按钮 -->
	  	<div class="padding-top-10 clear">
			<div class="col-md-12 pl200 ">
				<div class="mt40 tc mb50">
					<button class="btn btn-windows save" onclick="save()">保存</button>
		  			<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
	  			</div>
	  		</div>
	  	</div>
	  	
		</div>
		</div>
  	
  	</form>
  </body>
</html>
