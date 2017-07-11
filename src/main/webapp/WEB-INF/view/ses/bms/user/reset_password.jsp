<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>基本信息</title>
		<!-- Meta -->
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/common/RSA.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/bms/user/initPWD.js"></script>
	</head>

	<body>
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0);">个人中心</a>
					</li>
					<li>
						<a href="javascript:jumppage('${pageContext.request.contextPath}/user/resetPassword.html');">修改密码</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div class="container container_box">
			<form method="post" id="form2" >
			<%-- <input type="hidden" name="id" id="userId" value="${user.id}"> --%>
			<h2 class="list_title">修改密码</h2>
			<ul class="ul_list">
				<li class="col-md-3 col-sm-6 col-xs-12 pl15">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>原密码</span>
					<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
						<input class="input_group" type="password" id="oldPassword" name="oldPassword" maxlength="50">
						<span class="add-on">i</span>
						<div id="ajaxOldPassword" class="cue"></div>
					</div>
				</li>
				<li class="col-md-3 col-sm-6 col-xs-12 pl15">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>新密码</span>
					<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
						<input class="input_group" type="password" id="password" name="password"  maxlength="50">
						<span class="add-on">i</span>
						<div id="ajaxPassword" class="cue"></div>
					</div>
				</li>
				<li class="col-md-3 col-sm-6 col-xs-12 pl15">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>确认新密码</span>
					<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
						<input class="input_group" type="password" id="password2" name="password2" maxlength="50">
						<span class="add-on">i</span>
						<div id="ajaxPassword2" class="cue"></div>
					</div>
				</li>
			</ul>
			<div class="tc mt20 clear col-md-11">
				<input class="btn" id="inputb" name="addr" onclick="resetPasswSubmit();" value="确定" type="button"> 
			</div>
		  </form>
		</div>
	</body>

</html>