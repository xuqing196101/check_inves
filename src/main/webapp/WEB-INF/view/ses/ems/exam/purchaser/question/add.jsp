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
		var queType = $("#queType").val();
		if(queType){
			for(var i=0;i<que.length;i++){
				$(que[i]).click(function(){
					$("#queSelect").html(" ");
				})
			}
			for(var i=0;i<judge.length;i++){
				$(judge[i]).click(function(){
					$("#queJudge").html(" ");
				})
			}
		}else{
			for(var i=0;i<que.length;i++){
				que[i].setAttribute("disabled",true);
			}
			for(var i=0;i<judge.length;i++){
				judge[i].setAttribute("disabled",true);
			}
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
						judges += judge[i].value+',';
					}
				}
				if(judges==null||judges==""){
					$("#queSelect").html(" ");
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
    <div>新增考题</div>
  	<div>题目信息</div>
  	<form action="<%=path %>/purchaserExam/saveToPurPool.html" method="post" id="form">
  		<div>
	  		请选择题型:
	  		<select id="queType" name="queType" onchange="changeType()">
	  			<option value=""></option>
	  			<option value="1">单选题</option>
	  			<option value="2">多选题</option>
	  			<option value="3">判断题</option>
	  		</select>
  		</div>
  		<div>
  			题干:
  			<textarea width="400" height="200" name="queTopic" id="queTopic">
  			</textarea>
  		</div>
  		<div>
  			<ul class="list-unstyled list-flow ">
				<li class="col-md-12 p0"><span class="fl">选项：</span>
					<div class="col-md-12 pl200 fn mt5 pwr9">
						A<input type="text" name="option" id="optionA"/>
						B<input type="text" name="option" id="optionB"/>
						C<input type="text" name="option" id="optionC"/>
						D<input type="text" name="option" id="optionD"/>
						<%--<textarea class="text_area col-md-8 " name="queOption"
							id="queOption" onblur="judgeInput()"></textarea>
						<span class="col-md-4" style="color: red">
							选项格式:A上;<br/>
							B下;<br/>
							C左;<br/>
							D右;<br/>
							分号必须是英文状态下的分号
						</span>	
					--%></div></li>
			</ul>
  			<%--<textarea width="400" height="200" name="queOption" id="queOption">
  			</textarea>
  		--%></div>
  		<div>
  			答案:A <input type="radio" name="que" value="A" onclick="queDis()"/> B <input type="radio" name="que" value="B" onclick="queDis()"/> C <input type="radio" name="que" value="C" onclick="queDis()"/> D <input type="radio" name="que" value="D" onclick="queDis()"/><span id="queSelect"></span><br/>
  			对:<input type="radio" name="judge" value="对" onclick="judgeDis()"/>错:<input type="radio" name="judge" value="错" onclick="judgeDis()"/><span id="queJudge"></span>
  		</div>
  		<div>
  			分值:
  			<select name="quePoint" id="quePoint">
  				<option value="1">1</option>
  				<option value="2">2</option>
  				<option value="3">3</option>
  				<option value="4">4</option>
  				<option value="5">5</option>
  			</select>
  		</div>
  		<div>
  			<input type="button" onclick="save()" value="保存"/>
  		</div>
  	</form>
  </body>
</html>
