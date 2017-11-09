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
        	var reg = /^\d+\.?\d*$/;
        	var typename= $("#typeName").val();
        	var business = $("input[name='business']").val();
        	var valid = $("input[name='valid']").val();
        	var fr = $("input[name='floatingRatio']").val();
        	if (typename == 0) {
        		if (valid == "" || fr =="") {
        			layer.msg("表单需要填写完整",{offset: ['30%', '40%']});
        			return;
        		}
        		if(!reg.exec(valid)) {
					layer.msg("请填写数字",{offset: ['30%', '40%']});
					return;
				}
				if(!reg.exec(fr)) {
					layer.msg("请填写数字",{offset: ['30%', '40%']});
					return;
				}
				if(reg.exec(fr)  < 3  || reg.exec(fr) > 5) {
					layer.msg("浮动比例只能在：3%-5%",{offset: ['30%', '40%']});
					return;
				}
				if(reg.exec(valid) >40) {
					layer.msg("平均值不能高于40%",{offset: ['30%', '40%']});
					return;
				}
        	}
        	if (typename == 2) {
        		if (valid == "" || business =="") {
        			layer.msg("表单需要填写完整",{offset: ['30%', '40%']});
        			return;
        		}
        		if(!reg.exec(valid)) {
					layer.msg("请填写数字",{offset: ['30%', '40%']});
					return;
				}
				if(!reg.exec(business)) {
					layer.msg("请填写数字",{offset: ['30%', '40%']});
					return;
				}
				if(reg.exec(valid) >40) {
					layer.msg("平均值不能高于40%",{offset: ['30%', '40%']});
					return;
				}
				if(reg.exec(business) >30) {
					layer.msg("不能高于30%",{offset: ['30%', '40%']});
					return;
				}
        	}
			form1.submit();
		}
		
		
		function show(id){
		    //综合评分法
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
			//性价比
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
			//基准价法
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
            //最低价
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
				<div class="p20 bggrey">
					<h2 class="list_title">新增评标方法</h2>
					<div class="list-unstyled ul_list">
					<div class="row">
						<input type="hidden" name="projectId" value="${projectId}" />
						<input type="hidden" name="packageId" value="${packageId}" />
						<input type="hidden" name="flowDefineId" value="${flowDefineId}" />
						<div class="col-md-4 col-sm-6 col-xs-12">
					    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 ">评分方法:</span> 
					    <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
							<select class="w180" name="typeName" id="typeName"  onchange="show(this.value);">
								<c:forEach items="${ddList}" var="list" varStatus="vs">
									<c:if test="${vs.index != 1 }">
									<option value="${vs.index}">${list.name}</option>
									</c:if>
								</c:forEach>
							</select>
							</div>
						</div>
						<div class="col-md-4 col-sm-6 col-xs-12" id="floatingRatio">
						  <div class="col-md-12 col-xs- 12 col-sm-12 p0">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 ">浮动比例(%):</span> 
							<div class="input_append input_group col-md-12 col-sm-12 col-xs-12 p0">
						    <input name="floatingRatio"  type="text" value="${bidMethod.floatingRatio }">
						    <span class="add-on hand">i</span>
						    <span class="input-tip">浮动比例只能在：3%-5%</span>
							</div>
							<div class="cue">${fr }</div>
						  </div>
						</div>
						<div class="col-md-4 col-sm-6 col-xs-12" id="valid">
						  <div class="col-md-12 col-xs- 12 col-sm-12 p0">
								<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 ">供应商报价不得高于有效供应商报价平均值的百分比(%)：</span> 
								<div class="input_append input_group col-md-12 col-sm-12 col-xs-12 p0">
							    <input name="valid"   type="text" value="${bidMethod.valid }">
							    <span class="add-on hand">i</span>
							    <span class="input-tip">平均值不能高于40%</span>
								</div>
								<div class="cue">${valid }</div>
						  </div>
							<!-- <span>供应商报价不得超过有效供应商报价平均值百分比</span> -->
						</div>
						<div class="col-md-4 col-sm-6 col-xs-12 dnone" id="business">
							<!-- <span class="">商务技术评分高于上午技术评分百分比:</span> --> 
							<div id="business" class="col-md-12 col-xs- 12 col-sm-12 p0">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 ">抛开价格因素经济技术评分不得低于有效经济技术评分的百分比(%) ：</span> 
							<div class="input_append input_group col-md-12 col-sm-12 col-xs-12 p0">							
							   <input name="business"  type="text" value="${bidMethod.business }">
						     <span class="add-on">i</span>
						     <span class="input-tip">不能高于30%</span>
							</div>
							<div class="cue">${busi }</div>
							</div>
						</div>
				  </div>
				  </div>
				  <div class="clear"></div>
				</div>
				<div class="tc col-md-12 col-sm-12 col-xs-12 mt10">
					<input class="btn btn-windows save w80" readonly onclick="save(this)" value="保存" />
					<button class="btn btn-windows back w80" onclick="history.go(-1)" type="button">返回</button>
				</div>
			</form>
		</div>
	</body>

</html>