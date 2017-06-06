<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/reg_head.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/common/RSA.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/bms/user/initPWD.js"></script>
</head>
<body>
<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
           <li><a href="javascript:void(0)"> 登录</a></li>
           <li><a href="javascript:void(0)">修改密码</a></li>
           </ul>
        <div class="clear"></div>
      </div>
    </div>
	<div class="wrapper">
		<div class="container container_box">
            <form method="post" id="form2" >
            <input type="hidden" name="id" id="userId" value="${uid}">
            <h2 class="list_title">修改密码</h2>
            <div class="red">系统安全升级凡是2017年6月5日之前注册的用户需要重新修改密码才可进入系统，谢谢合作!</div>
            <ul class="ul_list padding-left-20">
                <li class="col-md-4 col-sm-6 col-xs-12 pl15">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>原密码</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                        <input class="input_group" type="password" id="oldPassword" name="oldPassword" onblur="ajaxOldPassword()"  maxlength="50">
                        <span class="add-on">i</span>
                        <div id="ajaxOldPassword" class="cue"></div>
                    </div>
                 </li> 
			     <li class="col-md-4 col-sm-6 col-xs-12">
			       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>新密码</span>
			                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			                        <input class="input_group" type="password" id="password" name="password"  maxlength="50">
			                        <span class="add-on">i</span>
			                        <div id="ajaxPassword" class="cue"></div>
			                    </div>
			     </li> 
			     <li class="col-md-4 col-sm-6 col-xs-12">
			       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>确认新密码</span>
			                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			                        <input class="input_group" type="password" id="password2" name="password2" maxlength="50">
			                        <span class="add-on">i</span>
			                        <div id="ajaxPassword2" class="cue"></div>
			                    </div>
			     </li> 
                </ul>
            <div class="tc mt20 clear col-md-11">
                <input class="btn" id="inputb" name="addr" onclick="initPasswSubmit();" value="确定" type="button"> 
            </div>
          </form>
        </div>
	</div>
	<div class="footer_margin">
   		<div class="container clear tc login-footer">
             <address>
                Copyright © 2016 版权所有：中央军委后勤保障部 京ICP备09055519号<span class="ratio"> 浏览本网主页，建议将电脑显示屏的分辨率调为1024*768</span>
             </address>
        </div>
   </div>
</body>
</html>
