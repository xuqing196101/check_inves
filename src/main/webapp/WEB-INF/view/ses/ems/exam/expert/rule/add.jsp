<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<title>新增专家考试规则</title>
		<script type="text/javascript">
			$(function() {
				var errorSingle = $("#errorSingle").val();
				var errorMultiple = $("#errorMultiple").val();
				var single = document.getElementsByName("single");
				var multiple = document.getElementsByName("multiple");
				if(errorSingle == null || errorSingle == "") {
					$("#sin").hide();
				} else if(errorSingle == "无") {
					$(single[1]).attr("checked", "checked");
					$("#sin").hide();
				} else if(errorSingle == "有") {
					$(single[0]).attr("checked", "checked");
					$("#sin").show();
				}
				if(errorMultiple == null || errorMultiple == "") {
					$("#mul").hide();
				} else if(errorMultiple == "无") {
					$(multiple[1]).attr("checked", "checked");
					$("#mul").hide();
				} else if(errorMultiple == "有") {
					$(multiple[0]).attr("checked", "checked");
					$("#mul").show();
				}
			})

			//自动计算总分
			function countScore() {
				var sn = $("#singleNum").val();
				var sp = $("#singlePoint").val();
				var mn = $("#multipleNum").val();
				var mp = $("#multiplePoint").val();
				$("#paperScore").val(sn * sp + mn * mp);
				var paperScore = document.getElementById("paperScore").value;
				if(paperScore == "NaN") {
					$("#paperScore").val("0");
				}
			}

			//勾选单选题的有
			function checkSingleYes(obj) {
				if($(obj).prop("checked")) {
					$("#sin").show();
				}
			}

			//勾选单选题的无
			function checkSingleNo(obj) {
				if($(obj).prop("checked")) {
					$("#sin").hide();
				}
				$("#singleNum").val("");
				$("#singlePoint").val("");
				countScore();
			}

			//勾选多选题的有
			function checkMultipleYes(obj) {
				if($(obj).prop("checked")) {
					$("#mul").show();
				}
			}

			//勾选多选题的无
			function checkMultipleNo(obj) {
				if($(obj).prop("checked")) {
					$("#mul").hide();
				}
				$("#multipleNum").val("");
				$("#multiplePoint").val("");
				countScore();
			}

			//返回到考试规则列表
			function back() {
				window.location.href = "${pageContext.request.contextPath }/expertExam/backRule.html";
			}
		</script>

	</head>

	<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0);">支撑环境</a>
					</li>
					<li>
						<a href="javascript:void(0);">专家管理</a>
					</li>
					<li>
						<a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/expertExam/ruleList.html')">专家考试规则管理</a>
					</li>
					<li>
						<a href="javascript:void(0);">新增规则</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>

		<div class="container container_box">
			<form action="${pageContext.request.contextPath }/expertExam/saveExamRule.html" method="post">
				<input type="hidden" value="${errorData['single'] }" id="errorSingle" />
				<input type="hidden" value="${errorData['multiple'] }" id="errorMultiple" />
				<h2 class="list_title">新增规则</h2>
				<div class="ul_list">
					<ul class="list-unstyled p0_20">
						<li class="col-md-12 col-sm-12 col-xs-12 pl15 mb20">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>题型分布：</span>
							<div class="col-md-12 col-sm-12 col-xs-12 p0">
								<div class="col-md-6 col-sm-12 col-xs-12 input-append p0">
									<label class="fl">单选题：</label>
									<div class="fl">
										<input type="radio" name="single" onclick="checkSingleYes(this)" class="mt0" value="有" />有
										<input type="radio" name="single" onclick="checkSingleNo(this)" class="mt0" value="无" />无
									</div>
									<div class="fl" id="sin">
										<input type="text" value="${errorData['singleNum'] }" name="singleNum" id="singleNum" class="ml10 w50 tc" onkeyup="countScore()" />条<input type="text" value="${errorData['singlePoint'] }" name="singlePoint" id="singlePoint" class="ml10 w50 tc" onkeyup="countScore()" />分/条
									</div>
									<div class="cue">${ERR_single }</div>
								</div>
								<div class="col-md-6 col-sm-12 col-xs-12 input-append p0">
									<label class="fl">多选题：</label>
									<div class="fl">
										<input type="radio" name="multiple" onclick="checkMultipleYes(this)" class="mt0  pl20" value="有" />有
										<input type="radio" name="multiple" onclick="checkMultipleNo(this)" class="mt0 pl20" value="无" />无
									</div>
									<div class="fl" id="mul">
										<input type="text" value="${errorData['multipleNum'] }" name="multipleNum" id="multipleNum" class="ml10 w50 tc" onkeyup="countScore()" />条<input type="text" value="${errorData['multiplePoint'] }" name="multiplePoint" id="multiplePoint" class="ml10 w50 tc"   onkeyup="countScore()" />分/条
									</div>
									<div class="cue">${ERR_multiple }</div>
								</div>
							</div>
						</li>

						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class=" star_red">*</div>总分值：</span>
							<div class="col-md-12 col-sm-12 col-xs-12 input-append p0">
								<input type="text" name="paperScore" id="paperScore" class="pl20" value="${errorData['score'] }" readonly="readonly" />&nbsp;分
							</div>
						</li>

						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>及格标准：</span>
							<div class="col-col-md-12 col-sm-12 col-xs-12md-12 input-append p0">
								<div class="fl"><input type="text" name="passStandard" id="passStandard" class="pl20" value="${errorData['passStandard'] }" />&nbsp;分</div>
								<div class="cue">${ERR_passStandard }</div>
							</div>
						</li>
					</ul>
				</div>

				<div class="col-md-12 col-sm-12 col-xs-12 mt10 tc">
					<button class="btn btn-windows save" type="submit">保存</button>
					<button class="btn btn-windows back" type="button" onclick="back()">返回</button>
				</div>
			</form>
		</div>
	</body>

</html>