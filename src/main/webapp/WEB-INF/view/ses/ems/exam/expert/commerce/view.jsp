<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>修改商务类专家题库</title>
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
			var queAnswer = "${comAnswer}";
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
	</script>
  </head>
  
  <body>
  	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">专家考试</a></li><li class="active"><a href="#">题库管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
	   <div class="headline-v2">
	   		<h2>修改考题</h2>
	   </div>
   </div>
  	<form action="<%=path %>/expertExam/editToCom.html?id=${comQue.id }" method="post" id="form">
		<div class="padding-top-10 clear">
			<div class="col-md-12 pl200 ">
				请选择题型:
		  		<select id="queType" name="queType" onchange="changeType()" disabled="disabled">
		  			<option value="">请选择</option>
		  			<c:forEach items="${examPoolType }" var="e">
		  				<c:choose>
		  					<c:when test="${e.id==comQue.questionTypeId }">
		  						<option value="${e.id }" selected="selected">${e.name }</option>
		  					</c:when>
		  					<c:otherwise>
		  						<option value="${e.id }">${e.name }</option>
		  					</c:otherwise>
		  				</c:choose>
		  			</c:forEach>
		  		</select>
	  		</div>
		</div>
		<div class="padding-top-10 clear">
		   <ul class="list-unstyled list-flow ">
		     <li class="col-md-12 p0">
			   <span class="fl">题干：</span>
			   <div class="col-md-12 pl200 fn mt5 pwr9">
		        <textarea disabled="disabled" class="text_area col-md-12 " name="queTopic" id="queTopic">${comQue.topic }</textarea>
		       </div>
			 </li> 
		   </ul>
	  	</div>
	  	
	  	<div class="padding-top-10 clear">
		   <ul class="list-unstyled list-flow p0_20 ">
		     <li class="col-md-12 p0">
			   <span class="fl">选项：</span>
			   <div class="col-md-12 pl200 fn mt5 pwr9">
			   		A<input type="text" name="option" id="optionA" value="${optionA}" disabled="disabled"/>
					B<input type="text" name="option" id="optionB" value="${optionB}" disabled="disabled"/>
					C<input type="text" name="option" id="optionC" value="${optionC}" disabled="disabled"/>
					D<input type="text" name="option" id="optionD" value="${optionD}" disabled="disabled"/>
		        <%--<textarea disabled="disabled" disabled="disabled" class="text_area col-md-12 " name="queOption" id="queOption">${comQue.queOption }</textarea>
		       --%></div>
			 </li> 
		   </ul>
	  	</div>
		  <div class="padding-top-10 clear">
			   <ul class="list-unstyled list-flow p0_20 ">
			     <li class="col-md-12 p0">
				   <span class="fl">答案：</span>
				   <div class="col-md-12 pl200 fn mt5 pwr9">
			        A <input type="radio" id="A" name="que" value="A" disabled="disabled"/> 
		  			B <input type="radio" id="B" name="que" value="B" disabled="disabled"/> 
		  			C <input type="radio" id="C" name="que" value="C" disabled="disabled"/> 
		  			D <input type="radio" id="D" name="que" value="D" disabled="disabled"/>
		  			<span id="queSelect"></span>
			       </div>
				 </li> 
			   </ul>
		  </div>
	  <div class="padding-top-10 clear">
			<div class="col-md-12 pl200 ">
			分值:
  			<select name="quePoint" id="quePoint" disabled="disabled">
  				<option value="1" 
  					<c:if test="${comQue.point==1 }">
  						selected
  					</c:if>
  				>1</option>
  				<option value="2" 
  					<c:if test="${comQue.point==2 }">
  						selected
  					</c:if>
  				>2</option>
  				<option value="3" 
  					<c:if test="${comQue.point==3 }">
  						selected
  					</c:if>
  				>3</option>
  				<option value="4" 
  					<c:if test="${comQue.point==4 }">
  						selected
  					</c:if>
  				>4</option>
  				<option value="5" 
  					<c:if test="${comQue.point==5 }">
  						selected
  					</c:if>
  				>5</option>
  			</select>
  			</div>
  		</div>
  		
	  	<div class="padding-top-10 clear">
			<div class="col-md-12 pl200 ">
	  			<input type="button" onclick="location.href='javascript:history.go(-1);'" value="返回"/>
	  		</div>
	  	</div>
		
  	
  	</form>
  </body>
</html>
