<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
	<head>
		<link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet">
		<%@ include file="/WEB-INF/view/common.jsp"%>
		<script src="${pageContext.request.contextPath }/public/select2/js/select2.js"></script>
		<script src="${pageContext.request.contextPath }/public/select2/js/select2_locale_zh-CN.js"></script>
		
		<script type="text/javascript">
			$(function() {
				$.ajax({
					url: "${ pageContext.request.contextPath }/park/getUserForSelect.do",
					contentType: "application/json;charset=UTF-8",
					dataType: "json", //返回格式为json
					type: "POST", //请求方式           
					success: function(users) {
						if(users) {
							$("#user").html("<option></option>");
							$.each(users, function(i, user) {
								if(user.relName != null && user.relName != '') {
									$("#user").append("<option  value=" + user.id + ">" + user.relName + "</option>");
								}
							});
						}
						$("#user").select2();
						$("#user").select2("val", "${park.user.id}");
					}
				});
			});

			function change(id) {
				$("#userId").val(id);
			}

			//返回
			function back() {
				window.location.href = "${pageContext.request.contextPath }/park/backPark.html";
			}
		</script>

	</head>

	<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:void(0);"> 首页</a>
					</li>
					<li>
						<a>论坛管理</a>
					</li>
					<li class="active">
						<a>版块管理</a>
					</li>
					<li class="active">
						<a>增加版块</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<!-- 新增页面开始 -->
		<div class="container container_box">
			<form action="${ pageContext.request.contextPath }/park/save.html" method="post">
				<div>
					<h2 class="list_title">新增版块</h2>
					<ul class="ul_list mb20">

						<li class="col-md-3 col-sm-6 col-xs-12 pl15">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><div class="red star_red">*</div>版块名称：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input name="name" type="text" value='${park.name}'>
								<span class="add-on">i</span>
								<div class="cue">${ERR_name}</div>
							</div>
						</li>

						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>版主：</span>
							<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
								<select id="user" name="parker" class="col-md-12 col-sm-12 col-xs-12 p0" onchange="change(this.options[this.selectedIndex].value)"></select>
								<div class="cue">${ERR_parker}</div>
							</div>
						</li>

						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">热门：</span>
							<div class="select_common col-md-12 col-sm-12 col-xs-12 input_group p0">
								<select id="isHoT" name="isHot" class="col-md-12 col-sm-12 col-xs-12 p0">
									<c:if test="${park.isHot==0||park.isHot=='' }">
										<option value="0" selected>不是热门</option>
									</c:if>
									<c:if test="${park.isHot!=0 }">
										<option value="0">不是热门</option>
									</c:if>
									<c:if test="${park.isHot==1 }">
										<option value="1" selected>热门</option>
									</c:if>
									<c:if test="${park.isHot!=1 }">
										<option value="1">热门</option>
									</c:if>
								</select>
								<div class="cue">${ERR_isHot}</div>
							</div>
						</li>

						<input type="hidden" id="userId" name="userId"></input>

						<li class="col-md-12 col-sm-12 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">版块介绍：</span>
							<div class="col-md-12 col-sm-12 col-xs-12 p0">
								<textarea class="w100p h130" title="不超过800个字" name="content">${park.content}</textarea>
							</div>
						</li>

					</ul>
					
					<!-- 底部按钮 -->
					<div class="col-md-12 col-sm-12 col-xs-12 tc">
						<button class="btn btn-windows save " type="submit">保存</button>
						<button class="btn btn-windows back " onclick="back()" type="button">返回</button>
					</div>
				</div>
			</form>
		</div>
	</body>

</html>