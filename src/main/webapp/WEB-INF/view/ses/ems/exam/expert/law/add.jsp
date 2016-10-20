<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>新增法律类专家题库</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		var opt = "";
		var obj = "";
		$(function(){
			opt = ${opt};
			obj = eval(opt);
			document.getElementById("queTopic").setAttribute("disabled",true);
			document.getElementById("options").setAttribute("disabled",true);
		})
		
		//保存到法律题库
		function save(){
			$("#form").submit();
		}
		
		//切换题型
		function changeType(){
			var queType = $("#queType").val();
			var all_options = document.getElementById("options");
			if(queType){
				if(queType==1){
					$("#queTopic").attr("disabled",false);
					$("#queTopic").val(" ");
					$("#options").attr("disabled",false);
					all_options[0].selected = true;
					$("#items").html(" ");
					$("#answers").html(" ");
				}else if(queType==2){
					$("#queTopic").attr("disabled",false);
					$("#queTopic").val(" ");
					$("#options").attr("disabled",false);
					all_options[0].selected = true;
					$("#items").html(" ");
					$("#answers").html(" ");
				}
			}else{
				document.getElementById("queTopic").setAttribute("disabled",true);
				document.getElementById("options").setAttribute("disabled",true);
				$("#queTopic").val(" ");
				all_options[0].selected = true;
				$("#items").html(" ");
				$("#answers").html(" ");
			}
		}
		
		//切换选项数量
		function changeOpt(){
			var queType = $("#queType").val();
			var options = $("#options").val();
			if(options==""||options==null){
				$("#items").html(" ");
				$("#answers").html(" ");
				return;
			}
			var array = obj[options].split(",");
			var ohtml="";
			var ahtml="";
			for(var i=0;i<array.length;i++){
			   	ohtml = ohtml+"<div class='clear mt10 col-md-12 p0'><div class='fl mt5'><div class='red star_red'>*</div>"+array[i]+"</div><textarea name='option' class='ml5 col-md-9 p0'></textarea></div>";
				if(queType==1){
					ahtml = ahtml+"<input type='radio' name='answer' value='"+array[i]+"' class='mt0'/>"+array[i]+"&nbsp";
				}else if(queType==2){
					ahtml = ahtml+"<input type='checkbox' name='answer' value='"+array[i]+"' class='mt0'/>"+array[i]+"&nbsp";
				}
			}
			$("#items").html(ohtml);
			$("#answers").html(ahtml);
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
		   		<h2>新增法律类题目</h2>
		   </div>
	   
  	<form action="<%=path %>/expertExam/saveToLaw.html" method="post" id="form">
  		<ul class="list-unstyled list-flow p0_20">
		     <li class="col-md-12 p0">
	  			<span class="fl"><div class="red star_red">*</div>请选择题型：</span>
		  		<select id="queType" name="queType" onchange="changeType()" class="fl">
		  			<option value="">请选择</option>
		  			<option value="1">单选题</option>
		  			<option value="2">多选题</option>
		  		</select>
		  		<div class="fl red mt5 ml5">${ERR_type}</div>
	  		</li>
  		
		    <li class="col-md-12 p0">
			   <span class="fl"><div class="red star_red">*</div>题干：</span>
			   <div class="fl mt5 col-md-9 p0">
		        	<textarea class="text_area col-md-12" name="topic" id="queTopic"></textarea>
		       		<div class="clear red">${ERR_topic}</div>
		       </div>
			</li> 
		   

		
				<li class="col-md-12 p0">
					<span class="fl"><div class="red star_red">*</div>请选择选项数量：</span>
					<div class="fl col-md-9 p0">
					  <select id="options" name="options" onchange="changeOpt()" class="fl">
			  			<option value="">请选择</option>
			  			<option value="three">3</option>
			  			<option value="four">4</option>
			  			<option value="five">5</option>
			  			<option value="six">6</option>
			  			<option value="seven">7</option>
			  			<option value="eight">8</option>
			  			<option value="nine">9</option>
			  			<option value="ten">10</option>
		  			   </select>
			         	<div class="red fl ml5 mt5">${ERR_option }</div> 
					    <div class="col-md-9 clear p0" id="items"></div>
			         </div>
				 </li> 
			

		
				<li class="col-md-12 p0">
					<span class="fl"><div class="red star_red">*</div>答案：</span>
					<div class="fl ml5 mt5" id="answers"></div>
					<div class="mt5 ml5 red fl">${ERR_answer }</div>
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
