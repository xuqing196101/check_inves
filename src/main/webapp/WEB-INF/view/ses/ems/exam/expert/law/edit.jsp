<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

	<head>
		<title>修改法律类专家题目</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript">
			var opt = "";
			var obj = "";
			$(function() {
				opt = ${opt};
				obj = eval(opt);
				var queType = $("#queType").val();
				if(queType == "") {
					document.getElementById("queTopic").setAttribute("disabled", true);
					document.getElementById("options").setAttribute("disabled", true);
				}
				var options = $("#options").val();
				if(options == "") {
					return;
				}
				var array = obj[options].split(",");
				var errorOption = document.getElementsByName("errorOption");
				var queAnswer = $("#queAnswer").val();
				var ohtml = "";
				var ahtml = "";
				for(var i = 0; i < array.length; i++) {
					if($(errorOption[i]).val() == "" || $(errorOption[i]).val() == null) {
						ohtml = ohtml + "<div class='clear mt10 col-md-12 col-sm-12 col-xs-12 p0'><div class='fl mt5'><div class='red fl'>*</div>" + array[i] + "</div><textarea name='option' class='ml5 col-md-10 col-sm-10 col-xs-10 p0'></textarea></div>";
					} else {
						ohtml = ohtml + "<div class='clear mt10 col-md-12 col-sm-12 col-xs-12 p0'><div class='fl mt5'><div class='red fl'>*</div>" + array[i] + "</div><textarea name='option' class='ml5 col-md-10 col-sm-10 col-xs-10 p0'>" + $(errorOption[i]).val() + "</textarea></div>";
					}
					if(queType == 1) {
						if(queAnswer.indexOf(array[i]) > -1) {
							ahtml = ahtml + "<input type='radio' name='answer' value='" + array[i] + "' class='mt0' checked='checked'/>" + array[i] + "&nbsp";
						} else {
							ahtml = ahtml + "<input type='radio' name='answer' value='" + array[i] + "' class='mt0'/>" + array[i] + "&nbsp";
						}
					} else if(queType == 2) {
						if(queAnswer.indexOf(array[i]) > -1) {
							ahtml = ahtml + "<input type='checkbox' name='answer' value='" + array[i] + "' class='mt0' checked='checked'/>" + array[i] + "&nbsp";
						} else {
							ahtml = ahtml + "<input type='checkbox' name='answer' value='" + array[i] + "' class='mt0'/>" + array[i] + "&nbsp";
						}
					}
				}
				$("#items").html(ohtml);
				$("#answers").html(ahtml);
			})

			//保存到法律题库
			function save() {
				$("#form").submit();
			}

			//切换题型
			function changeType() {
				var queType = $("#queType").val();
				var all_options = document.getElementById("options");
				if(queType) {
					if(queType == 1) {
						$("#queTopic").attr("disabled", false);
						$("#queTopic").val(" ");
						$("#options").attr("disabled", false);
						all_options[0].selected = true;
						$("#items").html(" ");
						$("#answers").html(" ");
					} else if(queType == 2) {
						$("#queTopic").attr("disabled", false);
						$("#queTopic").val(" ");
						$("#options").attr("disabled", false);
						all_options[0].selected = true;
						$("#items").html(" ");
						$("#answers").html(" ");
					}
				} else {
					document.getElementById("queTopic").setAttribute("disabled", true);
					document.getElementById("options").setAttribute("disabled", true);
					$("#queTopic").val(" ");
					all_options[0].selected = true;
					$("#items").html(" ");
					$("#answers").html(" ");
				}
			}

			//切换选项数量
			function changeOpt() {
				var queType = $("#queType").val();
				var options = $("#options").val();
				if(options == "" || options == null) {
					$("#items").html(" ");
					$("#answers").html(" ");
					return;
				}
				var array = obj[options].split(",");
				var ohtml = "";
				var ahtml = "";
				for(var i = 0; i < array.length; i++) {
					ohtml = ohtml + "<div class='clear mt10 col-md-12 col-sm-12 col-xs-12 p0'><div class='fl mt5'><div class='red fl'>*</div>" + array[i] + "</div><textarea name='option' class='ml5 col-md-10 col-sm-10 col-xs-10 p0'></textarea></div>";
					if(queType == 1) {
						ahtml = ahtml + "<input type='radio' name='answer' value='" + array[i] + "' class='mt0'/>" + array[i] + "&nbsp";
					} else if(queType == 2) {
						ahtml = ahtml + "<input type='checkbox' name='answer' value='" + array[i] + "' class='mt0'/>" + array[i] + "&nbsp";
					}
				}
				$("#items").html(ohtml);
				$("#answers").html(ahtml);
			}

			//返回
			function back() {
				window.location.href = "${pageContext.request.contextPath }/expertExam/backLaw.html";
			}
		</script>
	</head>

	<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="#">首页</a>
					</li>
					<li>
						<a href="#">支撑环境</a>
					</li>
					<li>
						<a href="#">题库管理</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>

		<c:forEach items="${optContent }" var="opt">
			<input type="hidden" name="errorOption" value="${opt }" />
		</c:forEach>
		<input type="hidden" value="${lawAnswer }" id="queAnswer" />

		<div class="container container_box">
			<form action="${pageContext.request.contextPath }/expertExam/editToLaw.html?id=${lawQue.id }" method="post" id="form">
				<input type="hidden" name="content" value="${lawQue.topic }" />
				<h2 class="list_title">修改法律类题目</h2>
				<div class="ul_list">
					<ul class="list-unstyled col-md-6 col-sm-6 col-xs-12">
						<li class="col-md-12 col-sm-12 col-xs-12 pl15">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red fl">*</div>题型：</span>
							<div class="select_common input_group col-md-6 col-sm-6 col-xs-6 p0">
								<select id="queType" name="queType" onchange="changeType()" class="">
									<option value="">请选择</option>
									<c:forEach items="${examPoolType }" var="e">
										<c:choose>
											<c:when test="${e.id==lawQue.questionTypeId }">
												<option value="${e.id }" selected="selected">${e.name }</option>
											</c:when>
											<c:otherwise>
												<option value="${e.id }">${e.name }</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
								<div class="red">${ERR_type}</div>
							</div>
						</li>

						<li class="col-md-12 col-sm-12 col-xs-12">
							<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red fl">*</div>题干：</span>
							<div class="col-md-12 col-sm-12 col-xs-12 p0">
								<textarea class="col-md-10 col-sm-10 col-xs-10 h80 p0" name="topic" id="queTopic">${lawQue.topic }</textarea>
								<div class="clear red">${ERR_topic}</div>
							</div>
						</li>
					</ul>

					<ul class="list-unstyled col-md-6 col-sm-6 col-xs-12">
						<li class="col-md-12 col-sm-12 col-xs-12 pl15">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red fl">*</div>选项数量：</span>
							<div class="col-md-12 col-sm-12 col-xs-12 p0">
								<select id="options" name="options" onchange="changeOpt()" class="col-md-6 col-sm-6 col-xs-6 p0">
									<option value="">请选择</option>
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
								<div class="red fl clear">${ERR_option }</div>
								<div class="col-md-12 col-sm-12 col-xs-12 clear p0" id="items"></div>
							</div>
						</li>

						<li class="col-md-12 col-sm-12 col-xs-12 mt25">
							<span class="fl"><div class="red fl">*</div>答案：</span>
							<div class="fl" id="answers" class="select_check"></div>
							<div class="red fl">${ERR_answer }</div>
						</li>
					</ul>
				</div>
				<!-- 底部按钮 -->
				<div class="col-md-12 col-sm-12 col-xs-12 mt10 tc">
					<button class="btn btn-windows save" onclick="save()">保存</button>
					<button class="btn btn-windows back" onclick="back()" type="button">返回</button>
				</div>

			</form>
		</div>
	</body>

</html>