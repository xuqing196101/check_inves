<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../../../common.jsp"%>
<%@ taglib prefix="up" uri="/tld/upload"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

	<head>
		<script type="text/javascript">
			function jump(str) {
				var action;
				if(str == "basicInfo") {
					action = "${pageContext.request.contextPath}/expertAudit/basicInfo.html";
				}
				if(str == "experience") {
					action = "${pageContext.request.contextPath}/expertAudit/experience.html";
				}
				if(str == "product") {
					action = "${pageContext.request.contextPath}/expertAudit/product.html";
				}
				if(str == "reasonsList") {
					action = "${pageContext.request.contextPath}/expertAudit/reasonsList.html";
				}
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}
		</script>
		<script type="text/javascript">
			function reason(obj,str){
			  var expertId = $("#expertId").val();
			  var showId =  obj.id+"1";
			  alert(showId);
		    $("#"+obj.id+"").each(function() {
		      auditField = $(this).parents("li").find("span").text().replace("：","");
    		});
    		var auditContent = auditField + "附件信息";
				var index = layer.prompt({
			    title : '请填写不通过的理由：', 
			    formType : 2, 
			    offset : '100px',
				}, 
		    function(text){
				    $.ajax({
				      url:"${pageContext.request.contextPath}/expertAudit/auditReasons.html",
				      type:"post",
				      dataType:"json",
				      data:"suggestType=expertFile"+"&auditContent="+auditContent+"&auditReason="+text+"&expertId="+expertId+"&auditField="+auditField
				    });
					 $("#"+showId+"").css('visibility', 'visible');
		      layer.close(index);
			    });
		  	}
		</script>
	</head>

	<body>
		<div class="container container_box">
			<div class=" content height-350">
				<div class="col-md-12 tab-v2 job-content">
					<ul class="flow_step">
						<li onclick="jump('basicInfo')">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">基本信息</a><i></i>
						</li>
						<li onclick="jump('experience')">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">经历经验</a><i></i>
						</li>
						<li onclick="jump('product')">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">产品目录</a><i></i>
						</li>
						<li class="active">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">附件</a><i></i>
						</li>
						<li onclick="jump('reasonsList')">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">审核汇总</a>
						</li>
					</ul>
					
					<ul class="ul_list">
						<li class="col-md-6 p0 mt10 mb25">
							<span class="hand" onmouseover="this.style.border='solid 1px #FF0000'" onmouseout="this.style.border='solid 1px #FFFFFF'" id="application" onclick="reason(this);">专家申请表：</span>
								<up:show showId="show1" groups="show1,show2" delete="false" businessId="${expertId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_APPLICATION_TYPEID}" />
								<a class="b f18 ml10 red" style="visibility:hidden" id="application1">×</a>
						</li>
						<li class="col-md-6 p0 mt10 mb25">
							<span class="hand" onmouseover="this.style.border='solid 1px #FF0000'" onmouseout="this.style.border='solid 1px #FFFFFF'" id="contract" onclick="reason(this);">专家承诺书：</span>
								<up:show showId="show2" groups="show1,show2" delete="false" businessId="${expertId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_CONTRACT_TYPEID}" />
								<a class="b f18 ml10 red" style="visibility:hidden" id="contract1">×</a>
						</li>
					</ul>
				</div>
			</div>
		</div>

		<input value="${expertId}" id="expertId" type="hidden">

		<form id="form_id" action="" method="post">
			<input name="expertId" value="${expertId}" type="hidden">
		</form>
	</body>

</html>