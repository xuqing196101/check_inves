<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="up" uri="/tld/upload"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
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
					var onMouseMove = "this.style.background='#E8E8E8'";
					var onmouseout = "this.style.background='#FFFFFF'";
	        $(this).attr("onMouseMove",onMouseMove);
	        $(this).attr("onmouseout",onmouseout);
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
				var html = "<a class='abolish'><img src='/zhbj/public/backend/images/sc.png'></a>";
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
							data: "suggestType=two" + "&auditContent=" + auditContent + "&auditReason=" + text + "&expertId=" + expertId + "&auditField=" + auditField +"&type=1",
							success:function(result){
				        result = eval("(" + result + ")");
				        if(result.msg == "fail"){
				           layer.msg('该条信息已审核过！', {	            
				             shift: 6, //动画类型
				             offset:'100px'
				          });
				        }
				      }
						});
						$("#"+obj.id+"").css('border-color','#FF0000');
						$(obj).after(html);
						layer.close(index);
					});
			}
			// 提示之前的信息
			function isCompare(inputName,fieldName, type){
				$.ajax({
					url: "${pageContext.request.contextPath}/expertAudit/getFieldContent.do",
					data: {"field":fieldName,"type":type,"expertId":"${expertId}"},
					async: false,
					success: function(response){
						layer.tips("原值:" + response, "#" + inputName, {
		    				tips : 3
		    			});
					}
				});
			}
			
			//下一步
			function nextStep() {
				var action = "${pageContext.request.contextPath}/expertAudit/expertType.html";
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}

			//上一步
			function lastStep() {
				var action = "${pageContext.request.contextPath}/expertAudit/basicInfo.html";
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}
		</script>
		<script type="text/javascript">
			function jump(str) {
				var action;
				if(str == "basicInfo") {
					action = "${pageContext.request.contextPath}/expertAudit/basicInfo.html";
				}
				if(str=="expertType"){
			    action = "${pageContext.request.contextPath}/expertAudit/expertType.html";
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
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:void(0)">首页</a>
					</li>
					<li>
						<a href="javascript:void(0)">支撑系统</a>
					</li>
					<li>
						<a href="javascript:void(0)">专家管理</a>
					</li>
					<li>
						<a href="javascript:void(0)">专家审核</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div class="container container_box">
			<div class=" content height-350">
				<div class="col-md-12 tab-v2 job-content">
					<ul class="flow_step">
						<li onclick="jump('basicInfo')">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">基本信息</a>
							<i></i>
						</li>
						<li class="active">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">经历经验</a>
							<i></i>
						</li>
						<li onclick="jump('expertType')">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">专家类别</a>
							<i></i>
						</li>
						<li onclick="jump('product')" >
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">产品类别</a>
							<i></i>
						</li>
						<li onclick="jump('expertFile')" >
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">附件</a>
							<i></i>
						</li>
						<li onclick="jump('reasonsList')" >
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">审核汇总</a>
						</li>
					</ul>
					<%-- <h2 class="count_flow"><i>1</i>参评的产品类别</h2>
					<ul class="ul_list">
						<li class="col-md-12 col-sm-12 col-xs-12">
							<div class="col-md-12 col-sm-12 col-xs-12 p0">
								<textarea rows="10" id="productCategories" style="height: 150px; width: 100%; resize: none;" onclick="reason(this,'参评的产品类别');" class="col-md-12 col-xs-12 col-sm-12 h80">${expert.productCategories}</textarea>
							</div>
						</li>
					</ul>
       --%>
					<!-- 主要工作经历-->
					<div class="padding-top-10 clear">
						<h2 class="count_flow"><i>1</i>主要工作经历</h2>
						<ul class="ul_list">
							<li class="col-md-12 col-sm-12 col-xs-12">
								<div class="col-md-12 col-sm-12 col-xs-12 p0">
									<textarea rows="10" <c:if test="${fn:contains(editFields,'getJobExperiences')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('jobExperiences','getJobExperiences','0');"</c:if> id="jobExperiences" style="height: 150px; width: 100%; resize: none;" onclick="reason(this,'主要工作经历');" class="col-md-12 col-xs-12 col-sm-12 h80">${expert.jobExperiences}</textarea>
								</div>
							</li>
						</ul>
					</div>
					<!-- 专业学术成果 -->
					<div class="padding-top-10 clear">
						<h2 class="count_flow"><i>2</i>专业学术成果</h2>
						<ul class="ul_list">
							<li class="col-md-12 col-sm-12 col-xs-12">
								<div class="col-md-12 col-sm-12 col-xs-12 p0">
									<textarea rows="10" <c:if test="${fn:contains(editFields,'getAcademicAchievement')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('academicAchievement','getAcademicAchievement','0');"</c:if> id="academicAchievement" style="height: 150px; width: 100%; resize: none;" onclick="reason(this,'专业学术成果');" class="col-md-12 col-xs-12 col-sm-12 h80">${expert.academicAchievement}</textarea>
								</div>
							</li>
						</ul>
					</div>
					<!-- 主要工作经历-->
					<div class="padding-top-10 clear">
						<h2 class="count_flow"><i>3</i>参加军队地方采购评审情况</h2>
						<ul class="ul_list">
							<li class="col-md-12 col-sm-12 col-xs-12">
								<div class="col-md-12 col-sm-12 col-xs-12 p0">
									<textarea rows="10" <c:if test="${fn:contains(editFields,'getReviewSituation')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('reviewSituation','getReviewSituation','0');"</c:if> id="reviewSituation" style="height: 150px; width: 100%; resize: none;" onclick="reason(this,'参加军队地方采购评审情况');" class="col-md-12 col-xs-12 col-sm-12 h80">${expert.reviewSituation}</textarea>
								</div>
							</li>
						</ul>
					</div>
					<!-- 主要工作经历-->
					<div class="padding-top-10 clear">
						<h2 class="count_flow"><i>4</i>需要申请回避的情况</h2>
						<ul class="ul_list">
							<li class="col-md-12 col-sm-12 col-xs-12">
								<div class="col-md-12 col-sm-12 col-xs-12 p0">
									<textarea rows="10" <c:if test="${fn:contains(editFields,'getAvoidanceSituation')}">style="border: 1px solid #FF8C00;" onmouseover="isCompare('avoidanceSituation','getAvoidanceSituation','0');"</c:if> id="avoidanceSituation" style="height: 150px; width: 100%; resize: none;" onclick="reason(this,'需要申请回避的情况');" class="col-md-12 col-xs-12 col-sm-12 h80">${expert.avoidanceSituation}</textarea>
								</div>
							</li>
						</ul>
					</div>
					<div class="col-md-12 add_regist tc">
						<a class="btn" type="button" onclick="lastStep();">上一步</a>
						<a class="btn" type="button" onclick="nextStep();">下一步</a>
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