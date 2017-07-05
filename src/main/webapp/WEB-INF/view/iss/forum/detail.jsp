<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
    <%@ include file="/WEB-INF/view/front.jsp"%>
		<script type="text/javascript">
			$(function() {
				var replyLength = "${replyLength}";
				if(replyLength == 0) {
					$("#repliesForJudge").hide();
				}

				laypage({
					cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
					pages: "${list.pages}", //总页数
					skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
					skip: true, //是否开启跳页
					total: "${list.total}",
					startRow: "${list.startRow}",
					endRow: "${list.endRow}",
					groups: "${list.pages}" >= 5 ? 5 : "${list.pages}", //连续显示分页数
					curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
						var page = location.search.match(/page=(\d+)/);
						return page ? page[1] : 1;
					}(),
					jump: function(e, first) { //触发分页后的回调
						if(!first) { //一定要加此判断，否则初始时会无限刷新
							var postId = "${post.id}";
							location.href = "${ pageContext.request.contextPath }/post/getIndexDetail.html?postId=" + postId + "&page=" + e.curr;
						}
					}
				});
				$("#laypage_0").addClass("pt10");
			});

			function publishForPost(postId) {
				var isLocking = "${post.isLocking}";
				if(isLocking == 1) {
					layer.alert("该帖子已被锁定，暂不提供回复功能。", {
						offset: ['222px', '390px'],
						shade: 0.01
					});
				} else {
					var ue = UE.getEditor('editor');
					var text = ue.getContent();
					$.ajax({
						url: "${ pageContext.request.contextPath }/reply/save.html?postId=" + postId + "&content=" + text,
						contentType: "application/json;charset=UTF-8",
						type: "POST", //请求方式           
						dataType: 'json',
						success: function(result) {
							if(!result.success) {
								layer.msg(result.msg, {
									offset: ['20px']
								});
							} else {
								parent.window.setTimeout(function() {
									parent.window.location.href = "${ pageContext.request.contextPath }/post/getIndexDetail.html?postId=" + postId;
								}, 1000);
								layer.msg(result.msg, {
									offset: ['20px']
								});
							}
						},
						error: function(result) {
							layer.msg("回复失败", {
								offset: ['20px']
							});
						}
					});
				}
			}

			function writeHtml(id) {
				var isLocking = "${post.isLocking}";
				if(isLocking == 1) {
					layer.alert("该帖子已被锁定，暂不提供回复功能。", {
						offset: ['222px', '390px'],
						shade: 0.01
					});
				} else {
					var pu = $("#" + id);
					var html = "<div class='sign_answer'>";
					html += $("#reply").html();
					html += "</div>";
					if(pu.next(".sign_answer").size() == 0) {
						$("div").remove(".sign_answer");
						pu.after(html);
						$("#replyPublishButton").attr("onclick", "publishForReply('" + id + "')");
					}
				}
			}

			function publishForReply(replyId) {
				var ue = UE.getEditor('replyEditor');
				var text = ue.getContent();
				var postId = "${post.id}";
				$.ajax({
					url: "${pageContext.request.contextPath }/reply/saveReply.html?postId=" + postId + "&content=" + text + "&replyId=" + replyId,
					contentType: "application/json;charset=UTF-8",
					type: "POST", //请求方式         
					dataType: 'json',
					success: function(result) {
						if(!result.success) {
							layer.msg(result.msg, {
								offset: ['20px']
							});
						} else {
							var postId = "${post.id}";
							parent.window.setTimeout(function() {
								parent.window.location.href = "${pageContext.request.contextPath }/post/getIndexDetail.html?postId=" + postId;
							}, 1000);
							layer.msg(result.msg, {
								offset: ['20px']
							});
						}
					},
					error: function(result) {
						layer.msg("回复失败", {
							offset: ['20px']
						});
					}

				});
			}
		</script>
	</head>

	<body>
		<jsp:include page="/index_head.jsp"></jsp:include>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb m0">
					<li>
						<a href="${pageContext.request.contextPath }/park/getIndex.html">论坛首页</a>
					</li>
					<li>
						<a>帖子详情</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div class="container content job-content ">
			<div class="col-md-12 p30_40 border1">
				<h3 class="tc f30">
       <div class="title bbgrey">${post.name }</div>
     </h3>
				<div class="p15_0">
					<div class="fr"><span>作者：${post.user.relName }</span>
						<span class="ml15"><i class="mr5">
	     <img src="${ pageContext.request.contextPath }/public/front/images/block.png"/></i>
	     <fmt:formatDate value='${post.publishedAt}' pattern="yyyy.MM.dd" />
	     </span>
						<span class="ml15">回复数：<span class="red">${post.replycount }</span></span>
					</div>
				</div>

				<div class="clear margin-top-20 new_content f18">
					${post.content }
				</div>
				<div class="extra_file" id="file">
					<span class="f14 fl">附件：</span>
					<up:show showId="post_attach_show" delete="false" businessId="${post.id}" sysKey="${sysKey}" typeId="${typeId}" />
				</div>
			</div>
			<div id="repliesForJudge">
				<!-- 回复列表 -->
				<div class="col-md-12 p30_40 border1 margin-top-20">

					<c:forEach items="${list.list}" var="reply" varStatus="vs">
						<div id="${reply.id}" class="col-md-12 comment_main border1">
							<!--左半部分  -->
							<div class="comment_flow ">
								<div class="comment_pic"><img src="${pageContext.request.contextPath }/public/front/images/boy.png" /></div>
								<div class="clear">
									<p class="b f18 mb0 tc">${reply.user.relName }</p>

								</div>
							</div>

							<!-- 右半部分 -->
							<div class="comment_desc col-md-12">
								<div class="col-md-12 p0">
									<span class="comment_name fr">[<fmt:formatDate value='${reply.publishedAt}' pattern="yyyy年MM月dd日" />] | ${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}楼  </span>
									<div class="clear comment_report">${reply.content }</div>

									<c:forEach items="${reply.replies }" var="replytoreply">
										<p class="b blue mb0">@ ${replytoreply.user.relName } [
											<fmt:formatDate value='${replytoreply.publishedAt}' pattern="yyyy年MM月dd日" />]：</p>
										<p class="clear mb0 gary">${replytoreply.content }</p>
									</c:forEach>

									<span class="fr blue pointer" onclick="writeHtml('${reply.id}')">回复</span>

								</div>

							</div>
						</div>

					</c:forEach>
				</div>

				<!-- 分页Div -->
				<div id="pagediv" align="right"></div>
			</div>

			<!-- 我要评论Div -->
			<div class="col-md-12 p30_40 border1 mt10" id="publish">
				<div class="clear col-md-12 p0">
					<span class="f18 b">我要回复</span>
				</div>
				<div class="clear col-md-12 p0 mt10">
					<span><div class="red star_red">*</div>回复内容：</span>
					<script id="editor" name="content" type="text/plain" class=""></script>
					<div class="validate">${ERR_content}</div>
				</div>
				<div class="clear col-md-12 pt10 tc">
					<button class="btn btn-windows" id="publishButton" onclick="publishForPost('${post.id}','${post.isLocking }')">发表</button>
				</div>
			</div>

			<div class="col-md-12 p30_40 border1 mt10 dnone" id="reply">
				<div class="clear col-md-12 p0">
					<span class="f18 b">我要回复</span>
				</div>
				<div class="clear col-md-12 p0 mt10">
					<span><div class="red star_red">*</div>回复内容：</span>
					<script id="replyEditor" name="replyContent" type="text/plain" class=""></script>
					<div class="validate">${ERR_replyContent}</div>
				</div>
				<div class="clear col-md-12 pt10 tc">
					<button class="btn btn-windows" id="replyPublishButton" onclick="publishForPost('${post.id}','${post.isLocking }')">发表</button>
				</div>
			</div>
		</div>
		<div class="my_poster">
			<a href='${pageContext.request.contextPath }/post/mypost.html' class="my_post f18">
				我的帖子
			</a>
			<a href='${pageContext.request.contextPath }/post/publish.html' class="publish_post f18">
				我要发帖
			</a>
		</div>
		<!--底部代码开始-->
		<jsp:include page="/index_bottom.jsp"></jsp:include>
		<script type="text/javascript">
			//自定义实例化编辑器
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

			};
			UE.getEditor('editor', option);
			UE.getEditor('replyEditor', option);
		</script>
	</body>

</html>