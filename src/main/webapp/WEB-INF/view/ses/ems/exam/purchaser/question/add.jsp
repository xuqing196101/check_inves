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
		var opt = "";
		var obj = "";
		$(function(){
			opt = ${opt};
			obj = eval(opt);
			var queType = $("#queType").val();
			if(queType==""){
				document.getElementById("queTopic").setAttribute("disabled",true);
				document.getElementById("options").setAttribute("disabled",true);
			}
			if(queType==1||queType==2){
				var options = $("#options").val();
				if(options==""){
					return;
				}
				var array = obj[options].split(",");
				var errorOption = document.getElementsByName("errorOption");
				var queAnswer = $("#errorAnswer").val();
				var ohtml = "";
				var ahtml = "";
				for(var i=0;i<array.length;i++){
					if($(errorOption[i]).val()==""||$(errorOption[i]).val()==null){
						ohtml = ohtml+"<div class='clear mt10 col-md-12 p0'><div class='fl mt5'><div class='red fl'>*</div>"+array[i]+"</div><textarea name='option' class='ml5 col-md-9 p0'></textarea></div>";
					}else{
						ohtml = ohtml+"<div class='clear mt10 col-md-12 p0'><div class='fl mt5'><div class='red fl'>*</div>"+array[i]+"</div><textarea name='option' class='ml5 col-md-9 p0'>"+$(errorOption[i]).val()+"</textarea></div>";
					}
					if(queType==1){
						if(queAnswer.indexOf(array[i])>-1){
							ahtml = ahtml+"<input type='radio' name='answer' value='"+array[i]+"' class='mt0' checked='checked'/>"+array[i]+"&nbsp;";
						}else{
							ahtml = ahtml+"<input type='radio' name='answer' value='"+array[i]+"' class='mt0'/>"+array[i]+"&nbsp;";
						}
					}else if(queType==2){
						if(queAnswer.indexOf(array[i])>-1){
							ahtml = ahtml+"<input type='checkbox' name='answer' value='"+array[i]+"' class='mt0' checked='checked'/>"+array[i]+"&nbsp;";
						}else{
							ahtml = ahtml+"<input type='checkbox' name='answer' value='"+array[i]+"' class='mt0'/>"+array[i]+"&nbsp;";
						}
					}
				}
				$("#item").html(ohtml);
				$("#answers").html(ahtml);
			}else if(queType==3){
				$("#items").hide();
				var queAnswer = $("#errorAnswer").val();
				if(queAnswer=="对"){
					$("#answers").html("<input type='radio' name='answer' value='对' class='mr5' checked='checked'/>对<input type='radio' name='answer' value='错' class='ml10'/>错 ");
				}else if(queAnswer=="错"){
					$("#answers").html("<input type='radio' name='answer' value='对' class='mr5'/>对<input type='radio' name='answer' value='错' class='ml10' checked='checked'/>错 ");
				}else{
					$("#answers").html("<input type='radio' name='answer' value='对' class='mr5'/>对<input type='radio' name='answer' value='错' class='ml10'/>错 ");
				}
			}
		})
		
		//保存到采购人题库
		function save(){
			$("#form").submit();
		}
	
		//切换题型
		function changeType(){
			var queType = $("#queType").val();
			var all_options = document.getElementById("options");
				if(queType==1){
					$("#queTopic").attr("disabled",false);
					$("#queTopic").val(" ");
					$("#options").attr("disabled",false);
					all_options[0].selected = true;
					$("#item").html(" ");
					$("#answers").html(" ");
					$("#items").show();
				}else if(queType==2){
					$("#queTopic").attr("disabled",false);
					$("#queTopic").val(" ");
					$("#options").attr("disabled",false);
					all_options[0].selected = true;
					$("#item").html(" ");
					$("#answers").html(" ");
					$("#items").show();
				}else if(queType==3){
					$("#queTopic").attr("disabled",false);
					$("#queTopic").val(" ");
					$("#items").hide();
					$("#answers").html("<input type='radio' name='answer' value='对' class='mt0'/>对<input type='radio' name='answer' value='错' class='mt0'/>错 ");
				}else{
					document.getElementById("queTopic").setAttribute("disabled",true);
					document.getElementById("options").setAttribute("disabled",true);
					$("#queTopic").val(" ");
					all_options[0].selected = true;
					$("#item").html(" ");
					$("#answers").html(" ");
				}
		}
	
		//切换选项数量
		function changeOpt(){
			var queType = $("#queType").val();
			var options = $("#options").val();
			if(options==""||options==null){
				$("#item").html(" ");
				$("#answers").html(" ");
				return;
			}
			var array = obj[options].split(",");
			var ohtml="";
			var ahtml="";
			for(var i=0;i<array.length;i++){
			   	ohtml = ohtml+"<div class='clear mt10 col-md-12 p0'><div class='fl mt5'><div class='red fl'>*</div>"+array[i]+"</div><textarea name='option' class='ml5 col-md-9 p0'></textarea></div>";
				if(queType==1){
					ahtml = ahtml+"<input type='radio' name='answer' value='"+array[i]+"' class='mr5'/>"+array[i]+"&nbsp";
				}else if(queType==2){
					ahtml = ahtml+"<input type='checkbox' name='answer' value='"+array[i]+"' class='ml10 mr5'/>"+array[i]+"&nbsp";
				}
			}
			$("#item").html(ohtml);
			$("#answers").html(ahtml);
		}
		
		//返回
		function back(){
			window.location.href = "${pageContext.request.contextPath }/purchaserExam/backQuestion.html";
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
	   
	    <c:forEach items="${errData['option'] }" var="opt">
	   		<input type="hidden" name="errorOption" value="${opt }"/>
	   </c:forEach>
	   <input type="hidden" id="errorAnswer" value="${errData['answer'] }"/>
	   
	<div class="container container_box">
   	<form action="${pageContext.request.contextPath }/purchaserExam/saveToPurPool.html" method="post" id="form">
    <h2 class="count_flow">新增题目</h2>
   		<div class="ul_list">
  		     <ul class="list-unstyled col-md-6">
		     <li class="col-md-12 p0">
	  			<span class="col-md-12"><div class="red fl">*</div>请选择题型：</span>
	  			<div class="col-md-12 fl">
	  			 <div class="select_common">
		  		  <select id="queType" name="queType" onchange="changeType()" class="w178">
		  			<c:if test="${errData['type']==null }">
		  				<option value="" selected>请选择</option>
		  			</c:if>
		  			<c:if test="${errData['type']!=null }">
		  				<option value="">请选择</option>
		  			</c:if>
		  			<c:if test="${errData['type']==1 }">
		  				<option value="1" selected>单选题</option>
		  			</c:if>
		  			<c:if test="${errData['type']!=1 }">
		  				<option value="1">单选题</option>
		  			</c:if>
		  			<c:if test="${errData['type']==2 }">
		  				<option value="2" selected>多选题</option>
		  			</c:if>
		  			<c:if test="${errData['type']!=2 }">
		  				<option value="2">多选题</option>
		  			</c:if>	
		  			<c:if test="${errData['type']==3 }">
		  				<option value="3" selected>判断题</option>
		  			</c:if>
		  			<c:if test="${errData['type']!=3 }">
		  				<option value="3">判断题</option>
		  			</c:if>		 
		  		</select>
		  		<div class="red">${ERR_type}</div>
		  		</div>
		  	  </div>
	  		</li>
	  		
  			<li class="col-md-12 p0">
			   <span class="col-md-12"><div class="red fl">*</div>题干：</span>
			   <div class="col-md-12">
		        	<textarea class="col-md-10 h80 p0" name="topic" id="queTopic">${errData["topic"] }</textarea>
		       		<div class="clear red">${ERR_topic}</div>
		       </div>
			</li> 
			</ul>
			
  			<ul class="list-unstyled col-md-6 p0">
				<li class="col-md-12 p0" id="items">
					<span class="col-md-12"><div class="red fl">*</div>请选择选项数量：</span>
					<div class="fl col-md-12 mb5">
					  <select id="options" name="options" onchange="changeOpt()" class="w178 fl">
			  			<option value="">请选择</option>
			  			<c:if test="${errData['options']=='three' }">
			  				<option value="three" selected>3</option>
			  			</c:if>
			  			<c:if test="${errData['options']!='three' }">
			  				<option value="three">3</option>
			  			</c:if>
			  			<c:if test="${errData['options']=='four' }">
			  				<option value="four" selected>4</option>
			  			</c:if>
			  			<c:if test="${errData['options']!='four' }">
			  				<option value="four">4</option>
			  			</c:if>
			  			<c:if test="${errData['options']=='five' }">
			  				<option value="five" selected>5</option>
			  			</c:if>
			  			<c:if test="${errData['options']!='five' }">
			  				<option value="five">5</option>
			  			</c:if>
			  			<c:if test="${errData['options']=='six' }">
			  				<option value="six" selected>6</option>
			  			</c:if>
			  			<c:if test="${errData['options']!='six' }">
			  				<option value="six">6</option>
			  			</c:if>
			  			<c:if test="${errData['options']=='seven' }">
			  				<option value="seven" selected>7</option>
			  			</c:if>
			  			<c:if test="${errData['options']!='seven' }">
			  				<option value="seven">7</option>
			  			</c:if>
			  			<c:if test="${errData['options']=='eight' }">
			  				<option value="eight" selected>8</option>
			  			</c:if>
			  			<c:if test="${errData['options']!='eight' }">
			  				<option value="eight">8</option>
			  			</c:if>
			  			<c:if test="${errData['options']=='nine' }">
			  				<option value="nine" selected>9</option>
			  			</c:if>
			  			<c:if test="${errData['options']!='nine' }">
			  				<option value="nine">9</option>
			  			</c:if>
			  			<c:if test="${errData['options']=='ten' }">
			  				<option value="ten" selected>10</option>
			  			</c:if>
			  			<c:if test="${errData['options']!='ten' }">
			  				<option value="ten">10</option>
			  			</c:if>
		  			</select>
					<div class="red fl ml5 mt5">${ERR_option }</div> 
					<div class="col-md-9 clear p0" id="item"></div>
			    </div>
			 </li> 
  		
  				<li class="col-md-12 p0">
					<span class="fl ml15"><div class="red fl">*</div>答案：</span>
					<div class="fl" id="answers"></div>
			       	<div class="red fl">${ERR_answer }</div>
				</li>
  			</ul>
  		</div>
  		
  		<!-- 按钮 -->
  		<div class="col-md-12 mt10 tc ">
			<button class="btn btn-windows save" type="button" onclick="save()">保存</button>
	    	<input class="btn btn-windows back" value="返回" type="button" onclick="back()">
		</div>
  		</form>
  	</div>
  </body>
</html>
