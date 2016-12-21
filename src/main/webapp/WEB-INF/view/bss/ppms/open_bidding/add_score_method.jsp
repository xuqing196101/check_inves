<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
	<!--<![endif]-->

	<head>
	<%@ include file="../../../common.jsp"%>
	<script type="text/javascript">
		function save(obj){
		    var y;  
        	oRect = obj.getBoundingClientRect();  
        	y=oRect.top-150;  
        	x=oRect.left;
			form1.submit();
		}
		
		function show(id){
			if (id ==0) {
			   if (!$("#business").hasClass("dnone")) {
					$("#business").addClass("dnone");
			    }
			    if ($("#floatingRatio").hasClass("dnone")){
			    	$("#floatingRatio").removeClass("dnone");
			    }
			    if ($("#valid").hasClass("dnone")){
			    	$("#valid").removeClass("dnone");
			    }
			}
			if (id ==1) {
				if (!$("#business").hasClass("dnone")) {
					$("#business").addClass("dnone");
			    }
			    if (!$("#floatingRatio").hasClass("dnone")){
			   	    $("#floatingRatio").addClass("dnone");
			    }
			    if (!$("#valid").hasClass("dnone")){
			    	$("#valid").addClass("dnone");
			    }
			}
			if (id ==2) {
			    if ($("#business").hasClass("dnone")) {
					$("#business").removeClass("dnone");
			    }
			    if (!$("#floatingRatio").hasClass("dnone")){
			    	$("#floatingRatio").addClass("dnone");
			    }
			    if ($("#valid").hasClass("dnone")){
			    	$("#valid").removeClass("dnone");
			    }
			}
			if (id ==3) {
				if (!$("#business").hasClass("dnone")) {
					$("#business").addClass("dnone");
			    }
			    if (!$("#floatingRatio").hasClass("dnone")){
			   	    $("#floatingRatio").addClass("dnone");
			    }
			    if (!$("#valid").hasClass("dnone")){
			    	$("#valid").addClass("dnone");
			    }
			}
		}
	</script>
	</head>
	<body>
		<!-- 修改订列表开始-->
		<div>
			<form id="form1" action="${pageContext.request.contextPath}/intelligentScore/saveScoreMethod.html" method="post">
				<div>
					<div class="headline-v2">
						<h2>新增评标方法</h2>
					</div>
					<ul class="list-unstyled list-flow" style="margin-left: 0px;">
							<input type="hidden" name="projectId" value="${projectId}" />
							<input type="hidden" name="packageId" value="${packageId}" />
							<input type="hidden" name="flowDefineId" value="${flowDefineId}" />
							<li class="col-md-6 p0">
							    <span class="">评分方法:</span> 
								<select class="w180" name="typeName" id="typeName"  onchange="show(this.value);">
										<c:forEach items="${ddList}" var="list" varStatus="vs">
											<option value="${vs.index}">${list.name}</option>
										</c:forEach>
								</select>
							</li>
							<li class="col-md-6 p0 mt20">
							  <div id="floatingRatio">
								<span class="">下浮比例:</span> 
								<input name="floatingRatio" type="text" value="${bidMethod.floatingRatio }">
							  </div>
							</li>
							<li class="col-md-6 p0" >
							   <div id="valid">
								 <span class="">请输入报价百分比  :</span> 
								 <input name="valid"  type="text" value="${bidMethod.valid }">
							   </div>
									<!-- <span>供应商报价不得超过有效供应商报价平均值百分比</span> -->
							</li>
							<li class="col-md-12 p0 dnone" id="business">
								<!-- <span class="">商务技术评分不得低于上午技术评分百分比:</span> --> 
								<div id="business">
								<span class="">请输入商务百分比  :</span> 
								<input name="business"  type="text" value="${bidMethod.business }">
								</div>
							</li>
					  </ul>
				</div>
				<div class="ml200">
					<input class="btn btn-windows save w80" readonly onclick="save(this)" value="保存" />
					<button class="btn btn-windows back w80" onclick="history.go(-1)" type="button">返回</button>
				</div>
			</form>
		</div>
	</body>

</html>