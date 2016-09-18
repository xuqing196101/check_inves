<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>采购人修改题库</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
	$(function(){
		var que = document.getElementsByName("que");
		var judge = document.getElementsByName("judge");
		var queType = $("#queType").val();
		if(queType){
			if(queType==1){
				for(var i=0;i<que.length;i++){
					$(que[i]).attr("type","radio");
					$(que[i]).attr("disabled",false);
				}
				for(var i=0;i<judge.length;i++){
					judge[i].setAttribute("disabled",true);
				}
			}else if(queType==2){
				for(var i=0;i<que.length;i++){
					$(que[i]).attr("type","checkbox");
					$(que[i]).attr("disabled",false);
				}
				for(var i=0;i<judge.length;i++){
					judge[i].setAttribute("disabled",true);
				}
			}else if(queType==3){
				for(var i=0;i<judge.length;i++){
					$(judge[i]).attr("disabled",false);
				}
				for(var i=0;i<que.length;i++){
					que[i].setAttribute("disabled",true);
				}
				document.getElementById("queOption").setAttribute("disabled",true);
			}
		}else{
			for(var i=0;i<que.length;i++){
				que[i].setAttribute("disabled",true);
			}
			for(var i=0;i<judge.length;i++){
				judge[i].setAttribute("disabled",true);
			}
		}
		var queAnswer = "${purchaserAnswer}";
		if(queAnswer.indexOf('A')>-1){
			var qa=document.getElementById("A");
			qa.setAttribute("checked",true);
		}
		if(queAnswer.indexOf('B')>-1){
			var qb=document.getElementById("B");
			qb.setAttribute("checked",true);
		}
		if(queAnswer.indexOf('C')>-1){
			var qc=document.getElementById("C");
			qc.setAttribute("checked",true);
		}
		if(queAnswer.indexOf('D')>-1){
			var qd=document.getElementById("D");
			qd.setAttribute("checked",true);
		}
		if(queAnswer=="对"){
			var judgeTrue=document.getElementById("judgeTrue");
			judgeTrue.setAttribute("checked",true);
		}
		if(queAnswer=="错"){
			var judgeFalse=document.getElementById("judgeFalse");
			judgeFalse.setAttribute("checked",true);
		}
		$("#form").validate({
			errorElement: "span",
			focusInvalid : false, //当为false时，验证无效时，没有焦点响应  
			onkeyup : false,
			rules:{
				queType:"required",
				queTopic:"required",
				//queOption:"required",
				quePoint:"required"
			},
			messages:{
				queType:"题型不能为空",
				queTopic:"题干不能为空",
				//queOption:"选项不能为空",
				quePoint:"请选择分值"
			}
		});
		
	})
	
	//保存到采购人题库
	function save(){
		var ques = "";
		var judges = "";
		var queType = $("#queType").val();
		var que = document.getElementsByName("que");
		var judge = document.getElementsByName("judge");
		if(queType){
			if(queType==1){
				for(var i=0;i<que.length;i++){ 
					if(que[i].checked){
						ques += que[i].value+',';
					}
				}
				if(ques==null||ques==""){
					$("#queSelect").html("请选择答案");
					$("#queJudge").html(" ");
					return;
				}
			}else if(queType==2){
				for(var i=0;i<que.length;i++){
					if(que[i].checked){
						ques += que[i].value+',';
					}
				}
				if(ques==null||ques==""){
					$("#queSelect").html("请选择答案");
					$("#queJudge").html(" ");
					return;
				}
			}else if(queType==3){
				for(var i=0;i<judge.length;i++){
					if(judge[i].checked){
						judges+=judge[i].value+',';
					}
				}
				if(judges==null||judges==""){
					$("#queSelect").html("");
					$("#queJudge").html("请选择答案");
					return;
				}
			}
		}else{
			$("#queSelect").html(" ");
			$("#queJudge").html(" ");
		}
		$("#form").submit();
	}
	
	//切换题型
	function changeType(){
		$("span.invalid").remove();
		$("#queSelect").html(" ");
		$("#queJudge").html(" ");
		var queType = $("#queType").val();
		var que = document.getElementsByName("que");
		var judge = document.getElementsByName("judge");
		var all_options = document.getElementById("quePoint").options;
		for(var i=0;i<que.length;i++){
			$(que[i]).removeAttr("checked");
		}
		for(var i=0;i<judge.length;i++){
			$(judge[i]).removeAttr("checked");
		}
		if(queType){
			if(queType==1){
				for(var i=0;i<que.length;i++){
					$(que[i]).attr("type","radio");
					$(que[i]).attr("disabled",false);
				}
				for(var i=0;i<judge.length;i++){
					$(judge[i]).attr("disabled",true);
				}
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
				for(var i=0;i<judge.length;i++){
					$(judge[i]).attr("disabled",true);
				}
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
			}else if(queType==3){
				for(var i=0;i<que.length;i++){
					$(que[i]).attr("disabled",true);
				}
				for(var i=0;i<judge.length;i++){
					$(judge[i]).attr("disabled",false);
				}
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
	
	function judgeDis(){
		$("#queJudge").html(" ");
	}
	</script>
  </head>
  
  <body>
  	<div>采购人修改题库</div>
  	<form action="<%=path %>/purchaserExam/editToPurchaser.html?id=${purchaserQue.id }" method="post" id="form">
  		<div>
	  		请选择题型:
	  		<select id="queType" name="queType" onchange="changeType()">
	  			<option></option>
	  			<c:forEach items="${examPoolType }" var="e">
	  				<c:choose>
	  					<c:when test="${e.id==purchaserQue.questionTypeId }">
	  						<option value="${e.id }" selected="selected">${e.name }</option>
	  					</c:when>
	  					<c:otherwise>
	  						<option value="${e.id }">${e.name }</option>
	  					</c:otherwise>
	  				</c:choose>
	  			</c:forEach>
	  		</select>
  		</div>
  		<div>
  			题干:
  			<textarea width="400" height="200" name="queTopic" id="queTopic">
  			${purchaserQue.topic }
  			</textarea>
  		</div>
  		<div>
  			<ul class="list-unstyled list-flow p0_20 ">
		     <li class="col-md-12 p0">
			   <span class="fl">选项：</span>
			   <div class="col-md-12 pl200 fn mt5 pwr9">
			  		A<input type="text" name="option" id="optionA" value="${optionA}"/>
					B<input type="text" name="option" id="optionB" value="${optionB}"/>
					C<input type="text" name="option" id="optionC" value="${optionC}"/>
					D<input type="text" name="option" id="optionD" value="${optionD}"/>
		        <%--<textarea class="text_area col-md-12 " name="queOption" id="queOption">${lawQue.queOption }</textarea>
		       --%></div>
			 </li> 
		   </ul>
  			<%--<textarea width="400" height="200" name="queOption" id="queOption">
  			${purchaserQue.queOption }
  			</textarea>
  		--%></div>
  		<div>
  			答案:
  			A <input type="radio" id="A" name="que" value="A" onclick="queDis()"/> 
  			B <input type="radio" id="B" name="que" value="B" onclick="queDis()"/> 
  			C <input type="radio" id="C" name="que" value="C" onclick="queDis()"/> 
  			D <input type="radio" id="D" name="que" value="D" onclick="queDis()"/>
  			<span id="queSelect"></span><br/>
  			对 <input type="radio" value="对" name="judge" onclick="judgeDis()" id="judgeTrue"/>
  			错 <input type="radio" value="错" name="judge" onclick="judgeDis()" id="judgeFalse"/>
  			<span id="queJudge"></span>
  		</div>
  		<div>
  			分值:
  			<select name="quePoint" id="quePoint">
  				<option value="1" 
  					<c:if test="${purchaserQue.point==1 }">
  						selected
  					</c:if>
  				>1</option>
  				<option value="2" 
  					<c:if test="${purchaserQue.point==2 }">
  						selected
  					</c:if>
  				>2</option>
  				<option value="3" 
  					<c:if test="${purchaserQue.point==3 }">
  						selected
  					</c:if>
  				>3</option>
  				<option value="4" 
  					<c:if test="${purchaserQue.point==4 }">
  						selected
  					</c:if>
  				>4</option>
  				<option value="5" 
  					<c:if test="${purchaserQue.point==5 }">
  						selected
  					</c:if>
  				>5</option>
  			</select>
  		</div>
  		<div>
  			<input type="button" onclick="save()" value="保存"/>
  			<input type="button" onclick="location.href='javascript:history.go(-1);'" value="返回"/>
  		</div>
  	</form>
  </body>
</html>
