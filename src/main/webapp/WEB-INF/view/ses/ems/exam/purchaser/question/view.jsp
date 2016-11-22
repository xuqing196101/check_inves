<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>采购人查看题库</title>
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
			var queAnswer = $("#queAnswer").val();
			var ohtml="";
			var ahtml="";
			if(queType==1||queType==2){
				var options = $("#options").val();
				if(options==""){
					return;
				}
				var array = obj[options].split(",");
				var errorOption = document.getElementsByName("errorOption");
				for(var i=0;i<array.length;i++){
					ohtml = ohtml+"<div class='clear mt10 col-md-12 col-sm-12 col-xs-12 p0'><div class='fl mt5'><div class='red star_red'>*</div>"+array[i]+"</div><textarea name='option' class='ml5 col-md-10 col-sm-10 col-xs-10 p0' disabled>"+$(errorOption[i]).val()+"</textarea></div>";
					if(queType==1){
						if(queAnswer.indexOf(array[i])>-1){
							ahtml = ahtml+"<input type='radio' name='answer' value='"+array[i]+"' class='mt0' checked='checked' disabled/>"+array[i]+"&nbsp";
						}else{
							ahtml = ahtml+"<input type='radio' name='answer' value='"+array[i]+"' class='mt0' disabled/>"+array[i]+"&nbsp";
						}
					}else if(queType==2){
						if(queAnswer.indexOf(array[i])>-1){
							ahtml = ahtml+"<input type='checkbox' name='answer' value='"+array[i]+"' class='mt0' checked='checked' disabled/>"+array[i]+"&nbsp";
						}else{
							ahtml = ahtml+"<input type='checkbox' name='answer' value='"+array[i]+"' class='mt0' disabled/>"+array[i]+"&nbsp";
						}
					}
				}
				$("#item").html(ohtml);
				$("#answers").html(ahtml);
			}else if(queType==3){
				$("#items").hide();
				if(queAnswer=="对"){
					$("#answers").html("<input type='radio' name='answer' value='对' class='mt0' checked='checked' disabled/>对<input type='radio' name='answer' value='错' class='mt0' disabled/>错 ");
				}else if(queAnswer=="错"){
					$("#answers").html("<input type='radio' name='answer' value='对' class='mt0' disabled/>对<input type='radio' name='answer' value='错' class='mt0' checked='checked' disabled/>错 ");
				}
			}
		})
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
   
   		<c:forEach items="${optContent }" var="opt">
	   		<input type="hidden" name="errorOption" value="${opt }"/>
	   	</c:forEach>
   		<input type="hidden" value="${purchaserAnswer}" id="queAnswer"/>
   		
    	<div class="container container_box">
	   		 <div class="headline-v2">
      			<h2 class="count_flow">查看题目</h2>
     		 </div>
     		 <div class="ul_list">
  				<ul class="list-unstyled col-md-6">
			     	<li class="col-md-12 col-sm-12 col-xs-12 p0">
			  			<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red fl">*</div>请选择题型：</span>
			  			<div class="select_common input_group col-md-6 col-sm-6 col-xs-6 p0">
				  		  	<select id="queType" name="queType" onchange="changeType()" class="" disabled="disabled">
				  				<option value="">请选择</option>
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
  					</li>
  		
		  		<li class="col-md-12 col-sm-12 col-xs-12 p0">
					<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red fl">*</div>题干：</span>
					<div class="col-md-12 col-sm-12 col-xs-12 p0">
			  			<textarea disabled="disabled" class="col-md-10 h80 p0" name="queTopic" id="queTopic">${purchaserQue.topic }</textarea>
		  			</div>
		  		</li>
  			</ul>
  		
  			<ul class="list-unstyled col-md-6 p0">
				<li class="col-md-12 col-sm-12 col-xs-12 pl15" id="items">
					<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red fl">*</div>请选择选项数量：</span>
					<div class="col-md-12 col-sm-12 col-xs-12 p0">
					  	<select id="options" name="options" onchange="changeOpt()" class="col-md-6 p0" disabled="disabled"> 
			  				<option value=" ">请选择</option>
				  			<c:if test="${optNum==3 }">
				  				<option value="three" selected>3</option>
				  			</c:if>
				  			<c:if test="${optNum!=3 }">
				  				<option value="three">3</option>
				  			</c:if>
				  			<c:if test="${optNum==4 }">
				  				<option value="four" selected>4</option>
				  			</c:if>
				  			<c:if test="${optNum!=4 }">
				  				<option value="four">4</option>
				  			</c:if>
				  			<c:if test="${optNum==5 }">
				  				<option value="five" selected>5</option>
				  			</c:if>
				  			<c:if test="${optNum!=5 }">
				  				<option value="five">5</option>
				  			</c:if>
				  			<c:if test="${optNum==6 }">
				  				<option value="six" selected>6</option>
				  			</c:if>
				  			<c:if test="${optNum!=6 }">
				  				<option value="six">6</option>
				  			</c:if>
				  			<c:if test="${optNum==7 }">
				  				<option value="seven" selected>7</option>
				  			</c:if>
				  			<c:if test="${optNum!=7 }">
				  				<option value="seven">7</option>
				  			</c:if>
				  			<c:if test="${optNum==8 }">
				  				<option value="eight" selected>8</option>
				  			</c:if>
				  			<c:if test="${optNum!=8 }">
				  				<option value="eight">8</option>
				  			</c:if>
				  			<c:if test="${optNum==9 }">
				  				<option value="nine" selected>9</option>
				  			</c:if>
				  			<c:if test="${optNum!=9 }">
				  				<option value="nine">9</option>
				  			</c:if>
				  			<c:if test="${optNum==10 }">
				  				<option value="ten" selected>10</option>
				  			</c:if>
				  			<c:if test="${optNum!=10 }">
				  				<option value="ten">10</option>
				  			</c:if>
		  				</select>
						<div class="col-md-9 clear p0" id="item"></div>
			    	</div>
			 	</li> 
  		
	  			<li class="col-md-12 col-sm-12 col-xs-12 mt25">
					<span class="fl"><div class="red fl">*</div>答案：</span>
					<div class="fl" id="answers" class="select_check"></div>
	  			</li>
  			</ul>
  		</div>
  		
  		<!-- 底部按钮 -->
	  	<div class="col-md-12 mt10 tc ">
		  	<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>	
	  	</div>
  </body>
</html>
