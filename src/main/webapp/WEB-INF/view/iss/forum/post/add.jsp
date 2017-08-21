<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp"%>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
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
			var content = '${post.content}';
			ue.ready(function() {
				ue.setContent(content);
			});
		</script>
		<script type="text/javascript">
			$(function() {
				$("#extensionId").val("bmp,pmg,jpg,gif,png");
				var parkId = "${post.park.id}";
				$("#park").val(parkId);
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
								$("#topics").val("${post.topic.id}");
							}
						}
					});
				}
				$("#isTop").val("${post.isTop}");
				$("#isLocking").val("${post.isLocking}");

				ue.ready(function() {
					ue.setContent("${post.content}");
				});

			});
			//2级联动
			function change(id) {
				$.ajax({
					url: "${ pageContext.request.contextPath }/topic/getListForSelect.do?parkId=" + id,
					contentType: "application/json;charset=UTF-8",
					dataType: "json", //返回格式为json
					type: "POST", //请求方式		    
					success: function(topics) {
						if(topics) {
							$("#topics").html("");
							$.each(topics, function(i, topic) {
								$("#topics").append("<option  value=" + topic.id + ">" + topic.name + "</option>");
							});
						}
					}
				});
			}

			//返回到帖子列表
			function back() {
				window.location.href = "${pageContext.request.contextPath }/post/backPost.html";
			}
		</script>
	</head>

	<body>

		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
					<li><a>信息服务</a></li>
					<li><a>论坛管理</a></li>
					<li>
						<a href="javascript:jumppage('${pageContext.request.contextPath}/post/getlist.html')">帖子管理</a>
					</li>
					<li class="active">
						<a>增加帖子</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<!-- 新增页面开始 -->
		<div class="container container_box">
			<form action="${ pageContext.request.contextPath }/post/save.html" method="post">
				<div>
					<h2 class="list_title">新增帖子</h2>
					<ul class="ul_list mb20">
						<li class="col-md-3 col-sm-6 col-xs-12 pl15">
							<span class="col-md-12 padding-left-5"><div class="red fl">*</div>帖子名称：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 p0 input_group m0">
								<input type="text" name="name" value='${post.name }'>
								<span class="add-on">i</span>
								<div class="cue">${ERR_name}</div>

								<li class="col-md-3 col-sm-6 col-xs-12">

									<span class="col-md-12 padding-left-5"><div class="red fl">*</div>所属版块：</span>
									<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
										<select id="park" name="parkId" class="select col-md-12 col-sm-12 col-xs-12 p0" onchange="change(this.options[this.selectedIndex].value)">
											<option></option>
											<c:forEach items="${parks}" var="park">
												<option value="${park.id}">${park.name}</option>
											</c:forEach>
										</select>
										<div class="cue">${ERR_park}</div>
									</div>
								</li>
								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 padding-left-5"><div class="red fl">*</div>所属主题：</span>
									<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
										<select id="topics" name="topicId" class="col-md-12 col-sm-12 col-xs-12 p0 ">
											<option></option>
										</select>
										<div class="cue">${ERR_topic}</div>
									</div>
								</li>

								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 padding-left-5 ">置顶：</span>
									<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
										<select name="isTop" class="col-md-12 col-sm-12 col-xs-12 p0 ">
											<option value="0" selected="selected">不置顶</option>
											<option value="1">置顶</option>
										</select>
									</div>
								</li>
								<li class="col-md-3 col-sm-6 col-xs-12">
									<span class="col-md-12 padding-left-5 ">锁定：</span>
									<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
										<select name="isLocking" class="w220 ">
											<option value="0" selected="selected">不锁定</option>
											<option value="1">锁定 </option>
										</select>
									</div>
								</li>
								<li class="col-md-12 col-sm-12 col-xs-12">
									<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red fl">*</div> 帖子内容：</span>
									<div class="col-md-12 col-sm-12 col-xs-12 p0">
										<script id="editor" name="content" type="text/plain" class="col-md-12 col-sm-12 col-xs-12 p0"></script>
									</div>
									<div class="red clear f12">${ERR_content}</div>
								</li>
								<input type="hidden" name="id" value='${post.id}'></input>

								<li class="col-md-12 col-sm-12 col-xs-12 mt10">
									<span class="fl">上传附件：</span>
									<u:upload id="post_attach_up" multiple="true" businessId="${post.id}" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
									<u:show showId="post_attach_show" businessId="${post.id}" sysKey="${sysKey}" typeId="${typeId}" />
								</li>
					</ul>

					<!-- 底部按钮 -->
					<div class="col-md-12 tc">
						<button class="btn btn-windows save" type="submit">保存</button>
						<button class="btn btn-windows back" onclick="back()" type="button">返回</button>
					</div>
					</div>
			</form>
			</div>

	</body>

</html>