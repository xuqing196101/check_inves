<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

	<head>
        <%@ include file="../../front.jsp"%>
        <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<title></title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript">
			//2级联动
			function change(id) {
				$.ajax({
					url: "${pageContext.request.contextPath}/topic/getListForSelect.do?parkId=" + id,
					contentType: "application/json;charset=UTF-8",
					dataType: "json", //返回格式为json
					type: "POST", //请求方式           
					success: function(topics) {
						if(topics) {
							if(topics.length != 0) {
								$("#topics").html("");
								$.each(topics, function(i, topic) {
									$("#topics").append("<option  value=" + topic.id + ">" + topic.name + "</option>");
								});
							} else {
								$("#topics").empty();
								$("#topics").val("");
								layer.alert("该版块下无主题，请重新选择版块。", {
									offset: ['222px', '390px'],
									shade: 0.01
								});
							}
						}
					}
				});
			}
			$(function() {
				var parkId = "${parkId}";
				$("#parkId").val(parkId);
				if(parkId != null && parkId != "") {
					$.ajax({
						url: "${ pageContext.request.contextPath }/topic/getListForSelect.do?parkId=" + parkId,
						contentType: "application/json;charset=UTF-8",
						dataType: "json", //返回格式为json
						type: "POST", //请求方式           
						success: function(topics) {
							if(topics) {
								$("#topics").html("<option></option>");
								$.each(topics, function(i, topic) {
									$("#topics").append("<option  value=" + topic.id + ">" + topic.name + "</option>");
								});
								$("#topics").val("${topicId}");
							}
						}
					});
				}
			})
		</script>
	</head>

	<body>
		<div class="wrapper">
			<jsp:include page="/index_head.jsp"></jsp:include>
			<div class="container content height-350 job-content ">
				<h2 class="f30 tc">发布帖子</h2>
				<div class="col-md-12 p20 border1 margin-top-20 mb40">
					<form id="form" action="${pageContext.request.contextPath}/post/indexsave.html" method="post">
						<ul class="list-unstyled list-flow p0_20 f16 col-md-offset-1 col-sm-offset-0">

							<li class="col-md-12 col-xs-12 col-sm-12 pl10">
								<div class="fl"><div class="red star_red">*</div>帖子名称：</div>
								<div class="input_group input-append col-md-9 col-sm-9 col-xs-12 p0">
									<input id="name" name="name" type="text" value='${post.name }' />
									<div class="cue">${ERR_name}</div>
								</div>
								<%--<span class="add-on">i</span>--%>

							</li>

							<li class="col-md-6 col-sm-6 col-xs-12">
								<div class="fl"><div class="red star_red">*</div>所属版块：</div>
								<div class="select_common col-md-6 col-sm-6 col-xs-12 p0">
									<select id="parkId" name="parkId" onchange="change(this.options[this.selectedIndex].value)">
										<option></option>
										<c:forEach items="${parks}" var="park">
											<option value="${park.id}">${park.name}</option>
										</c:forEach>
									</select>
									<div class="cue">${ERR_park}</div>
								</div>
							</li>

							<li class="col-md-6 col-sm-6 col-xs-12">
								<div class="fl"><div class="red star_red">*</div>所属主题：</div>
								<div class="select_common col-md-6 col-sm-6 col-xs-12 p0">
									<select id="topics" name="topicId">
										<option></option>
									</select>
									<div class="cue">${ERR_topic}</div>
								</div>
							</li>

							<li class="col-md-12 col-xs-12 col-sm-12">
								<div class="fl"><div class="red star_red">*</div>帖子内容：</div>
								<div class="fl mt5 col-md-9 col-sm-9 col-xs-12 p0 cengdie">
									<script id="editor" name="content" type="text/plain" class=""></script>
									<div class="red clear f12">${ERR_content}</div>
								</div>
							</li>
							<input type="hidden" name="id" value='${post.id}'></input>
							<li class="col-md-12 col-xs-12 col-sm-12">
								<div class="fl ml10">上传附件：</div>
								<div class="col-md-6 col-sm-6 col-xs-12 p0">
									<u:upload id="post_attach_up" multiple="true" businessId="${post.id}" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
									<u:show showId="post_attach_show" businessId="${post.id}" sysKey="${sysKey}" typeId="${typeId}" />
								</div>
							</li>
						</ul>
						<!-- 底部按钮 -->
						<div class="mt20 tc col-md-12 col-sm-12 col-xs-12">
							<button class="btn" type="submit">发布</button>
							<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
						</div>

					</form>
				</div>
			</div>
		
			<div class="my_poster">
				<a href='${ pageContext.request.contextPath }/post/mypost.html' class="my_post f18">
					我的帖子
				</a>
				<a href='${ pageContext.request.contextPath }/post/publish.html' class="publish_post f18">
					我要发帖
				</a>
			</div>
			<script type="text/javascript">
				//实例化编辑器
				//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例

				var option = {
					toolbars: [
						[
							'undo', 'redo', '|',
							'bold', 'italic', 'underline', 'formatmatch', 'autotypeset', '|', 'forecolor', 'backcolor',
							'fontfamily', 'fontsize', '|',
							'indent', '|',
							'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'emotion'

						]
					]

				}
				var ue = UE.getEditor('editor', option);
				ue.ready(function() {
					ue.setContent("${post.content}");
				});
			</script>
		</div>
		<!-- footer -->
		<jsp:include page="/index_bottom.jsp"></jsp:include>
	</body>

</html>