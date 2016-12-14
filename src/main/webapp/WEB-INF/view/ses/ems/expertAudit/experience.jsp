<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../../../common.jsp"%>
<%@ taglib prefix="up" uri="/tld/upload"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

	<head>
		<style type="text/css">
			input {
				cursor: pointer;
			}
			
			textarea {
				cursor: pointer;
			}
		</style>

		<script type="text/javascript">
			$(function() {
				$(":input").each(function() {
					var onmouseover = "this.style.border='solid 1px #FF0000'";
					var onmouseout = "this.style.border='solid 1px #D3D3D3'";
					$(this).attr("onmouseover", onmouseover);
					$(this).attr("onmouseout", onmouseout);
				});

				//为只读
				$(":input").each(function() {
					$(this).attr("readonly", "readonly");
				});
			});
		</script>

		<script type="text/javascript">
			function reason(obj, auditField) {
				var expertId = $("#expertId").val();
				var auditContent;
				var html = "<div class='abolish' style='padding-right;30px'>×</div>";
				$("#" + obj.id + "").each(function() {
					auditContent = $(this).parents("li").find("textarea").text();
				});
				var index = layer.prompt({
						title: '请填写不通过的理由：',
						formType: 2,
						offset: '100px',
					},
					function(text) {
						$.ajax({
							url: "${pageContext.request.contextPath}/expertAudit/auditReasons.html",
							type: "post",
							dataType: "json",
							data: "suggestType=experience" + "&auditContent=" + auditContent + "&auditReason=" + text + "&expertId=" + expertId + "&auditField=" + auditField,
						});
						$(obj).after(html);
						layer.close(index);
					});
			}
		</script>
		<script type="text/javascript">
			function jump(str) {
				var action;
				if(str == "basicInfo") {
					action = "${pageContext.request.contextPath}/expertAudit/basicInfo.html";
				}
				if(str == "product") {
					action = "${pageContext.request.contextPath}/expertAudit/product.html";
				}
				if(str == "expertFile") {
					action = "${pageContext.request.contextPath}/expertAudit/expertFile.html";
				}
				if(str == "reasonsList") {
					action = "${pageContext.request.contextPath}/expertAudit/reasonsList.html";
				}
				$("#form_id").attr("action", action);
				$("#form_id").submit();
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
						<li class="active">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">经历经验</a><i></i>
						</li>
						<li onclick="jump('product')" >
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">产品目录</a><i></i>
						</li>
						<li onclick="jump('expertFile')" >
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">附件</a><i></i>
						</li>
						<li onclick="jump('reasonsList')" >
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">审核汇总</a>
						</li>
					</ul>
					<h2 class="count_flow"><i>1</i>参评的产品类别</h2>
					<ul class="ul_list">
						<li class="col-md-12 col-sm-12 col-xs-12">
							<div class="col-md-12 col-sm-12 col-xs-12 p0">
								<textarea rows="10" id="productCategories" style="height: 150px; width: 100%; resize: none;" onclick="reason(this,'参评的产品类别');" class="col-md-12 col-xs-12 col-sm-12 h80">${expert.productCategories}</textarea>
							</div>
						</li>
					</ul>

					<!-- 主要工作经历-->
					<div class="padding-top-10 clear">
						<h2 class="count_flow"><i>2</i>主要工作经历</h2>
						<ul class="ul_list">
							<li class="col-md-12 col-sm-12 col-xs-12">
								<div class="col-md-12 col-sm-12 col-xs-12 p0">
									<textarea rows="10" id="jobExperiences" style="height: 150px; width: 100%; resize: none;" onclick="reason(this,'主要工作经历');" class="col-md-12 col-xs-12 col-sm-12 h80">${expert.jobExperiences}</textarea>
								</div>
							</li>
						</ul>
					</div>
					<!-- 专业学术成果 -->
					<div class="padding-top-10 clear">
						<h2 class="count_flow"><i>3</i>专业学术成果</h2>
						<ul class="ul_list">
							<li class="col-md-12 col-sm-12 col-xs-12">
								<div class="col-md-12 col-sm-12 col-xs-12 p0">
									<textarea rows="10" id="academicAchievement" style="height: 150px; width: 100%; resize: none;" onclick="reason(this,'专业学术成果');" class="col-md-12 col-xs-12 col-sm-12 h80">${expert.academicAchievement}</textarea>
								</div>
							</li>
						</ul>
					</div>
					<!-- 主要工作经历-->
					<div class="padding-top-10 clear">
						<h2 class="count_flow"><i>4</i>参加军队地方采购评审情况</h2>
						<ul class="ul_list">
							<li class="col-md-12 col-sm-12 col-xs-12">
								<div class="col-md-12 col-sm-12 col-xs-12 p0">
									<textarea rows="10" id="reviewSituation" style="height: 150px; width: 100%; resize: none;" onclick="reason(this,'参加军队地方采购评审情况');" class="col-md-12 col-xs-12 col-sm-12 h80">${expert.reviewSituation}</textarea>
								</div>
							</li>
						</ul>
					</div>
					<!-- 主要工作经历-->
					<div class="padding-top-10 clear">
						<h2 class="count_flow"><i>5</i>需要申请回避的情况</h2>
						<ul class="ul_list">
							<li class="col-md-12 col-sm-12 col-xs-12">
								<div class="col-md-12 col-sm-12 col-xs-12 p0">
									<textarea rows="10" id="avoidanceSituation" style="height: 150px; width: 100%; resize: none;" onclick="reason(this,'需要申请回避的情况');" class="col-md-12 col-xs-12 col-sm-12 h80">${expert.avoidanceSituation}</textarea>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>

		<input value="${expertId}" id="expertId" type="hidden">

		<form id="form_id" action="" method="post">
			<input name="expertId" value="${expertId}" type="hidden">
		</form>

	</body>

</html>