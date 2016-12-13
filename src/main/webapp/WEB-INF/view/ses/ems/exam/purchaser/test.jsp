<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

	<head>
		<title>采购人员考试页面</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript">
			$(function() {
				$("#submitNoResult").hide();
				$("#submitYesResult").hide();
				var test = "${examPaper.testTime}";
				if("${second}") {
					document.getElementById("second").innerHTML = "${second}" + "分钟" + "${minute}" + "秒";
					document.getElementById("surplusNo").innerHTML = "${second}" + "分钟" + "${minute}" + "秒";
					document.getElementById("surplusYes").innerHTML = "${second}" + "分钟" + "${minute}" + "秒";
				} else {
					document.getElementById("second").innerHTML = test + "分钟" + 0 + "秒";
					document.getElementById("surplusNo").innerHTML = test + "分钟" + 0 + "秒";
					document.getElementById("surplusYes").innerHTML = test + "分钟" + 0 + "秒";
				}
				var exam = document.getElementsByName("exam");
				for(var i = 1; i <= exam.length; i++) {
					if(i == 1) {
						$("#pageNum" + i).show();
					} else {
						$("#pageNum" + i).hide();
					}
				}
				countSurPlusNo();
				countSurPlusYes();
			})

			//答题时上一页下一页切换
			function setTab(index) {
				var exam = document.getElementsByName("exam");
				for(var i = 1; i <= exam.length; i++) {
					if(index == i) {
						$("#pageNum" + index).show();
					} else {
						$("#pageNum" + i).hide();
					}
				}
				$("html,body").animate({
					scrollTop: 0
				}, 100);
			}

			//考试倒计时
			var test = "${examPaper.testTime}";
			if("${second}") {
				var timeLeft = "${second}" * 60 * 1000 + "${minute}" * 1000;
				var timeYes = "${second}" * 60 * 1000 + "${minute}" * 1000;
				var timeNo = "${second}" * 60 * 1000 + "${minute}" * 1000;
			} else {
				var timeLeft = test * 60 * 1000;
				var timeYes = test * 60 * 1000;
				var timeNo = test * 60 * 1000;
			}

			function countTime() {
				if(timeLeft == 0) { //时间到了,系统自动提交
					$("#form").submit();
					return;
				}
				var startMinutes = parseInt(timeLeft / (60 * 1000), 10);
				var startSec = parseInt((timeLeft - startMinutes * 60 * 1000) / 1000);
				document.getElementById("second").innerHTML = startMinutes + "分钟" + startSec + "秒";
				timeLeft = timeLeft - 1000;
				setTimeout('countTime()', 1000);
			}

			function countSurPlusNo() {
				var startMinutes = parseInt(timeNo / (60 * 1000), 10);
				var startSec = parseInt((timeNo - startMinutes * 60 * 1000) / 1000);
				document.getElementById("surplusNo").innerHTML = startMinutes + "分钟" + startSec + "秒";
				timeNo = timeNo - 1000;
				setTimeout('countSurPlusNo()', 1000);
			}

			function countSurPlusYes() {
				var startMinutes = parseInt(timeYes / (60 * 1000), 10);
				var startSec = parseInt((timeYes - startMinutes * 60 * 1000) / 1000);
				document.getElementById("surplusYes").innerHTML = startMinutes + "分钟" + startSec + "秒";
				timeYes = timeYes - 1000;
				setTimeout('countSurPlusYes()', 1000);
			}

			//提交方法
			function git() {
				var num = 0;
				var count = ${queCount};
				for(var i = 1; i <= count; i++) {
					for(var j = 0; j < document.getElementsByName("que" + i).length; j++) {
						if(document.getElementsByName("que" + i)[j].checked) {
							num++;
							break;
						} else if(j == document.getElementsByName("que" + i).length - 1) {
							layer.open({
								type: 1, //page层
								area: ['430px', '200px'],
								closeBtn: 1,
								shade: 0.01, //遮罩透明度
								moveType: 1, //拖拽风格，0是默认，1是传统拖动
								shift: 1, //0-6的动画形式，-1不开启
								offset: ['40%', '30%'],
								shadeClose: false,
								content: $('#submitNoResult')
							});
							$(".layui-layer-shade").remove();
						}
					}
				}
				if(num == count) {
					layer.open({
						type: 1, //page层
						area: ['430px', '200px'],
						closeBtn: 1,
						shade: 0.01, //遮罩透明度
						moveType: 1, //拖拽风格，0是默认，1是传统拖动
						shift: 1, //0-6的动画形式，-1不开启
						offset: ['40%', '30%'],
						shadeClose: false,
						content: $('#submitYesResult')
					});
					$(".layui-layer-shade").remove();
				}
			}

			//确定方法
			function sure() {
				$("#form").submit();
			}

			//取消方法
			function cancel() {
				layer.closeAll();
			}

			//表单防重复提交
			var isCommitted = false; //表单是否已经提交标识，默认为false
			function dosubmit() {
				if(isCommitted == false) {
					isCommitted = true; //提交表单后，将表单是否已经提交标识设置为true
					return true; //返回true让表单正常提交
				} else {
					return false; //返回false那么表单将不提交
				}
			}
		</script>
	</head>

	<body onload="countTime()">
		<div id="submitNoResult">
			<div class="red tc mt20">您还有题目未作答,确定交卷吗?</div>
			<div class="tc mt10">答题剩余时间：<span id="surplusNo"></span></div>
			<div class="col-md-12 tc mt20">
				<button class="btn" type="button" onclick="sure()">确定</button>
				<button class="btn" type="button" onclick="cancel()">取消</button>
			</div>
		</div>

		<div id="submitYesResult">
			<div class="red tc mt20">确定交卷吗?</div>
			<div class="tc mt10">答题剩余时间：<span id="surplusYes"></span></div>
			<div class="col-md-12 tc mt20">
				<button class="btn" type="button" onclick="sure()">确定</button>
				<button class="btn" type="button" onclick="cancel()">取消</button>
			</div>
		</div>

		<div class="container mt10">
			<div class="col-md-12 mb10 border1 bggrey">
				<div class="fl f18">考生姓名：<span class="blue b">${user.relName }</span></div>
				<div class="fr red mt5" id="time">答题剩余时间：<span id="second"></span></div>
			</div>
			<div class="col-md-12 f18 b p0">
				<c:if test="${singlePoint!=0&&multiplePoint!=0&&judgePoint!=0 }">
					本次考试题型包括：单选题、多选题和判断题，其中：单选题${singleNum }题，每题${singlePoint }分，多选题${multipleNum }题，每题${multiplePoint }分，判断题${judgeNum }题，每题${judgePoint }分。
				</c:if>
				<c:if test="${singlePoint!=0&&multiplePoint==0&&judgePoint==0 }">
					本次考试题型包括：单选题，共${singleNum }题，每题${singlePoint }分。
				</c:if>
				<c:if test="${singlePoint==0&&multiplePoint!=0&&judgePoint==0  }">
					本次考试题型包括：多选题，共${multipleNum }题，每题${multiplePoint }分。
				</c:if>
				<c:if test="${singlePoint==0&&multiplePoint==0&&judgePoint!=0  }">
					本次考试题型包括：判断题，共${judgeNum }题，每题${judgePoint }分。
				</c:if>
				<c:if test="${singlePoint!=0&&multiplePoint!=0&&judgePoint==0  }">
					本次考试题型包括：单选题和多选题，其中：单选题${singleNum }题，每题${singlePoint }分，多选题${multipleNum }题，每题${multiplePoint }分。
				</c:if>
				<c:if test="${singlePoint!=0&&multiplePoint==0&&judgePoint!=0  }">
					本次考试题型包括：单选题和判断题，其中：单选题${singleNum }题，每题${singlePoint }分，判断题${judgeNum }题，每题${judgePoint }分分。
				</c:if>
				<c:if test="${singlePoint==0&&multiplePoint!=0&&judgePoint!=0  }">
					本次考试题型包括：多选题和判断题，其中：多选题${multipleNum }题，每题${multiplePoint }分，判断题${judgeNum }题，每题${judgePoint }分。
				</c:if>
			</div>
			<form action="${pageContext.request.contextPath }/purchaserExam/savePurchaserScore.html" method="post" id="form" onsubmit="return dosubmit()">
				<c:choose>
					<c:when test="${pageSize==1 }">
						<table class="clear table table-bordered table-condensed" id="pageNum1" name="exam">
							<tbody>
								<c:forEach items="${question }" var="pur" varStatus="l">
									<tr>
										<td class="col-md-1 tc">${l.index+1 }</td>
										<td class="col-md-11">
											<div><span class="mr10">【${pur.examQuestionType.name}】</span><span>${pur.topic }</span></div>
											<c:if test="${pur.examQuestionType.name=='单选题' }">
												<c:forEach items="${fn:split(pur.items,';')}" var="it">
													<div class="mt10 clear fl">
														<input type="radio" name="que${l.index+1 }" value="${fn:substring(it,0,1)}" class="mt0" />${it }
													</div>
												</c:forEach>
											</c:if>
											<c:if test="${pur.examQuestionType.name=='多选题' }">
												<c:forEach items="${fn:split(pur.items,';')}" var="it">
													<div class="mt10 clear fl">
														<input type="checkbox" name="que${l.index+1 }" value="${fn:substring(it,0,1)}" class="mt0" />${it }
													</div>
												</c:forEach>
											</c:if>
											<c:if test="${pur.examQuestionType.name=='判断题' }">
												<div class="mt10 clear fl"><input type="radio" name="que${l.index+1 }" value="对" class="mt0" />对</div>
												<div class="mt10 clear fl"><input type="radio" name="que${l.index+1 }" value="错" class="mt0" />错</div>
											</c:if>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<div class="col-md-12 tc">
							<button class="btn" type="button" onclick="git()">提交</button>
						</div>
					</c:when>
					<c:otherwise>
						<c:forEach items="${pageNum }" varStatus="p">
							<c:choose>
								<c:when test="${p.first}">
									<div id="pageNum${p.index+1 }" name="exam">
										<table class="clear table table-bordered table-condensed">
											<c:forEach items="${question }" var="pur" varStatus="l" begin="${p.index*5 }" end="${p.index*5+4 }">
												<tr>
													<td class="col-md-1 tc">${l.index+1 }</td>
													<td class="col-md-11">
														<div><span class="mr10">【${pur.examQuestionType.name}】</span><span>${pur.topic }</span></div>
														<c:if test="${pur.examQuestionType.name=='单选题' }">
															<c:forEach items="${fn:split(pur.items,';')}" var="it">
																<div class="mt10 clear fl">
																	<input type="radio" name="que${l.index+1 }" value="${fn:substring(it,0,1)}" class="mt0" />${it }
																</div>
															</c:forEach>
														</c:if>
														<c:if test="${pur.examQuestionType.name=='多选题' }">
															<c:forEach items="${fn:split(pur.items,';')}" var="it">
																<div class="mt10 clear fl">
																	<input type="checkbox" name="que${l.index+1 }" value="${fn:substring(it,0,1)}" class="mt0" />${it }
																</div>
															</c:forEach>
														</c:if>
														<c:if test="${pur.examQuestionType.name=='判断题' }">
															<div class="mt10 clear fl"><input type="radio" name="que${l.index+1 }" value="对" class="mt0" />对</div>
															<div class="mt10 clear fl"><input type="radio" name="que${l.index+1 }" value="错" class="mt0" />错</div>
														</c:if>
													</td>
												</tr>
											</c:forEach>
										</table>
										<div class="col-md-12 col-sm-12 col-xs-12 mt10 tc">
											<button class="btn" onclick="setTab(${p.index+2})" type="button">下一页</button>
										</div>
									</div>
								</c:when>

								<c:when test="${p.last}">
									<div id="pageNum${p.index+1 }" name="exam">
										<table class="clear table table-bordered table-condensed">
											<c:forEach items="${question }" var="pur" varStatus="l" begin="${p.index*5 }" end="${p.index*5+4 }">
												<tr>
													<td class="col-md-1 tc">${l.index+1 }</td>
													<td class="col-md-11">
														<div><span class="mr10">【${pur.examQuestionType.name}】</span><span>${pur.topic }</span></div>
														<c:if test="${pur.examQuestionType.name=='单选题' }">
															<c:forEach items="${fn:split(pur.items,';')}" var="it">
																<div class="mt10 clear fl">
																	<input type="radio" name="que${l.index+1 }" value="${fn:substring(it,0,1)}" class="mt0" />${it }
																</div>
															</c:forEach>
														</c:if>
														<c:if test="${pur.examQuestionType.name=='多选题' }">
															<c:forEach items="${fn:split(pur.items,';')}" var="it">
																<div class="mt10 clear fl">
																	<input type="checkbox" name="que${l.index+1 }" value="${fn:substring(it,0,1)}" class="mt0" />${it }
																</div>
															</c:forEach>
														</c:if>
														<c:if test="${pur.examQuestionType.name=='判断题' }">
															<div class="mt10 clear fl"><input type="radio" name="que${l.index+1 }" value="对" class="mt0" />对</div>
															<div class="mt10 clear fl"><input type="radio" name="que${l.index+1 }" value="错" class="mt0" />错</div>
														</c:if>
													</td>
												</tr>
											</c:forEach>
										</table>
										<div class="col-md-12 col-sm-12 col-xs-12 mt10 tc">
											<button class="btn" type="button" onclick="setTab(${p.index})">上一页</button>
											<button class="btn" type="button" onclick="git()">提交</button>
										</div>
									</div>
								</c:when>

								<c:otherwise>
									<div id="pageNum${p.index+1 }" name="exam">
										<table class="clear table table-bordered table-condensed">
											<c:forEach items="${question }" var="pur" varStatus="l" begin="${p.index*5 }" end="${p.index*5+4 }">
												<tr>
													<td class="col-md-1 tc">${l.index+1 }</td>
													<td class="col-md-11">
														<div><span class="mr10">【${pur.examQuestionType.name}】</span><span>${pur.topic }</span></div>
														<c:if test="${pur.examQuestionType.name=='单选题' }">
															<c:forEach items="${fn:split(pur.items,';')}" var="it">
																<div class="mt10 clear fl">
																	<input type="radio" name="que${l.index+1 }" value="${fn:substring(it,0,1)}" class="mt0" />${it }
																</div>
															</c:forEach>
														</c:if>
														<c:if test="${pur.examQuestionType.name=='多选题' }">
															<c:forEach items="${fn:split(pur.items,';')}" var="it">
																<div class="mt10 clear fl">
																	<input type="checkbox" name="que${l.index+1 }" value="${fn:substring(it,0,1)}" class="mt0" />${it }
																</div>
															</c:forEach>
														</c:if>
														<c:if test="${pur.examQuestionType.name=='判断题' }">
															<div class="mt10 clear fl"><input type="radio" name="que${l.index+1 }" value="对" class="mt0" />对</div>
															<div class="mt10 clear fl"><input type="radio" name="que${l.index+1 }" value="错" class="mt0" />错</div>
														</c:if>
													</td>
												</tr>
											</c:forEach>
										</table>
										<div class="col-md-12 col-sm-12 col-xs-12 mt10 tc">
											<button class="btn" onclick="setTab(${p.index})" type="button">上一页</button>
											<button class="btn" onclick="setTab(${p.index+2})" type="button">下一页</button>
										</div>
									</div>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</c:otherwise>
				</c:choose>

				<input type="hidden" value="${questionAnswer }" name="questionAnswer" />
				<input type="hidden" value="${examPaper.id }" name="paperId" />
				<input type="hidden" value="${questionType }" name="questionType" />
				<input type="hidden" value="${questionId }" name="questionId" />
			</form>
		</div>
	</body>

</html>