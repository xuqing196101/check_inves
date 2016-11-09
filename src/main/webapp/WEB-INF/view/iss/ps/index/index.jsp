<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../../../../../common.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>

	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
<script type="text/javascript">
$(function(){
	$(document).keyup(function(event){
	  if(event.keyCode ==13){
	    login();
	  }
	});
	
	$("#firstPage").attr("Class","active dropdown shouye_li mega-menu-fullwidth");
	
	$(".header-v4 .navbar-default .navbar-nav > .other > a").hover(function(){
		$("#firstPage").attr("Class","dropdown shouye_li mega-menu-fullwidth");
	});
})

function login(){
	if($("#inputEmail").val()==""){
		layer.tips("请输入用户名","#inputEmail",{
			tips : 1
		});		
	}else if($("#inputPassword").val()==""){
		layer.tips("请输入密码","#inputPassword",{
			tips : 1
		});		
	}else if($("#inputCode").val()==""){
		layer.tips("请输入验证码","#inputCode",{
			tips : 1
		});		
	}else{
		var index=layer.load();
		$.ajax({
			url:"${pageContext.request.contextPath}/login/login.html",
			type:"post",
			data:{loginName:$("#inputEmail").val(),password:$("#inputPassword").val(),rqcode:$("#inputCode").val()},
			success:function(data){
				if(data=="errorcode"){
					layer.tips("验证码不正确","#inputCode",{
						tips : 1
					});	
					layer.close(index);
				}else if(data=="errorlogin"){				
					layer.msg("用户名或密码错误！");
					layer.close(index);
				}else if(data=="nullcontext"){				
					layer.msg("请输入用户名密码或者验证码!");
				}else if(data=="scuesslogin"){				
					layer.close(index);
					window.location.href="${pageContext.request.contextPath}/login/index.html";
				}else if(data="deleteLogin"){
					layer.msg("账号不存在!");
					layer.close(index);
				}
				kaptcha();
			}
		});
	}

}
function kaptcha(){
	$("#kaptchaImage").hide().attr('src','Kaptcha.jpg?' + Math.floor(Math.random() * 100)).fadeIn();
}
</script>
</head>

<body>
  <jsp:include page="/index_head.jsp"></jsp:include>
  <!--/end container-->
  <!-- End Navbar -->
  <div class="container content job-content ">
     <div class="row magazine-page clear">
      <div class="col-md-6  margin-bottom-10">
        <div class="section-focus-pic" id="section-focus-pic">
	      <div class="pages" data-scro="list">
		   <ul>
			<li class="item" style="left:0px;">
				<a href="#" target="_blank"><img src="${pageContext.request.contextPath}/public/ZHQ/images/1.jpg" width="100%" height="100%"></a>
			</li>
			<li class="item">
				<a href="#" target="_blank"><img src="${pageContext.request.contextPath}/public/ZHQ/images/2.jpg" width="100%" height="100%"></a>
			</li>
			<li class="item">
				<a href="#" target="_blank"><img src="${pageContext.request.contextPath}/public/ZHQ/images/3.jpg" width="100%" height="100%"></a>
			</li>
		   </ul>
	      </div>
	      <div class="controler" data-scro="controler">
		   <b class="down">1</b>
		   <b>2</b>
		   <b>3</b>
	      </div>
        </div>
        <script src="${pageContext.request.contextPath}/public/ZHQ/js/script.js"></script>
       </div>
       <div class="col-md-6 ">
        <div class="tab-v1">
          <h2 class="nav nav-tabs bb1 mt0">
            <span class="bg12_white"><a href="#">工作动态</a></span>
		  </h2>
		  </div>
          <div class="">
                <ul class="list-unstyled categories list_common">            
                   <li></li>
                </ul>
          </div>
       </div>
  <!-- Begin 登录 --><%--
  <div class="col-md-3">

    <div class="border1 margin-bottom-10 login_box">
        <h2 class="f17">
		 <ul class="list-unstyled login_tab">
		  <li class="active fl"><a aria-expanded="true" href="#tab-1" data-toggle="tab"> 用户登录</a></li>
		  <li class="fl"><a aria-expanded="false" href="#tab-2" data-toggle="tab"> CA登录</a></li>
		 </ul>
		</h2>
	--%><%--<div class="tab-content p17-0">
	 <div id="tab-1" class="tab-pane fade active in">
      <form class="form-horizontal p15_0">
        <div class="control-group ">
          <label class="control-label" for="inputEmail">用户名：</label>
			  <div class="controls padding-right-10">
            <input type="text" id="inputEmail" class="form-control" placeholder="请输入用户名"/>
          </div>
        </div>
        <div class="control-group padding-right-10 margin-top-10">
          <label class="control-label" for="inputPassword">密码：</label>
          <div class="controls">
          <input type="password" id="inputPassword" class="form-control" placeholder="请输入密码">
          </div>
        </div>
        <div class="control-group  margin-top-10 ">
         <label class="control-label" for="inputPassword">验证码：</label>
        <div class="controls">
          <input type="text" placeholder="" id="inputCode" class="input-mini fl ">
          	<img src="Kaptcha.jpg" onclick="kaptcha();" id="kaptchaImage" />
        </div>
       </div>
       <div class="control-group margin-top-22 clear ml30">
          <button class="btn" type="button" onclick="login();">登录</button>
      </div>
    </form>
  </div>--%><%--
	 <div id="tab-2" class="tab-pane fade">
      <form class="form-horizontal p15_0">
        <div class="control-group ">
          <label class="control-label" for="inputEmail">用户名：</label>
			  <div class="controls padding-right-10">
            <input type="text" id="inputEmail" class="form-control" placeholder="请输入用户名"/>
          </div>
        </div>
        <div class="control-group padding-right-10 margin-top-10">
          <label class="control-label" for="inputPassword">密码：</label>
          <div class="controls">
          <input type="password" id="inputPassword" class="form-control" placeholder="请输入密码">
          </div>
        </div>
        <div class="control-group  margin-top-10 ">
        <label class="control-label" for="inputPassword">验证码：</label>
        <div class="controls">
          <input type="text" placeholder="" id="inputCode" class="input-mini fl ">
          	<img src="Kaptcha.jpg" onclick="kaptcha();" id="kaptchaImage" />
        </div>
       </div>
       <div class="control-group margin-top-22 clear ml30">
          <button class="btn login_btn" type="submit">登录</button>
      </div>
    </form>
  </div>
  </div>--%>
  </div>
  </div>
  </div>
 </div>
</div>

  
  
<!-- /* 集中采购开始*/-->
  <div class="container">
  <div class="row magazine-page clear">
   <div class="col-md-12">
    <h2 class="floor_title col-md-12">
    集中采购
	</h2>
   </div>
   </div>
   <div class="row magazine-page clear">
      <div class="col-md-4 mb10 login_box">
        <div class=" border1 job-content floor_kind">
          <h2>
		  <span class="col-md-5">采购公告</span>
		  <ul class="list-unstyled col-md-7 buyer_news">
		    <li class="active fl"><a aria-expanded="true" href="#tab-3" data-toggle="tab"> 物资</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-4" data-toggle="tab" > 工程</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-5" data-toggle="tab" > 服务</a></li>
		  </ul>
          </h2>
		  <div class=" tab-content">
		    <div id="tab-3" class="tab-pane fade active in">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-4" class="tab-pane fade">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-5" class="tab-pane fade">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
	   </div>
	  </div>
     </div>
	
       <div class="col-md-4 mb10 login_box">
        <div class=" border1 job-content floor_kind">
          <h2>
		  <span class="col-md-5">中标公告</span>
		  <ul class="list-unstyled buyer_news col-md-7">
		    <li class="active fl"><a aria-expanded="true" href="#tab-6" data-toggle="tab"> 物资</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-7" data-toggle="tab" > 工程</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-8" data-toggle="tab" > 服务</a></li>
		  </ul>
          </h2>
		  <div class=" tab-content">
		    <div id="tab-6" class="tab-pane fade active in">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-7" class="tab-pane fade">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-8" class="tab-pane fade">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
	   </div>
	  </div>
     </div>
      <div class="col-md-4 mb10 login_box">
        <div class=" border1 job-content floor_kind">
          <h2>
		  <span class="col-md-5">单一来源公告</span>
		  <ul class="list-unstyled buyer_news col-md-7">
		    <li  class="active fl"><a aria-expanded="true" href="#tab-9" data-toggle="tab"> 物资</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-10" data-toggle="tab" > 工程</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-11" data-toggle="tab" > 服务</a></li>
		  </ul>
          </h2>
		  <div class=" tab-content">
		    <div id="tab-9" class="tab-pane fade active in">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-10" class="tab-pane fade">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-11" class="tab-pane fade">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
	   </div>
	  </div>
     </div> 
  </div> 
</div>
<!--部队采购开始-->

 <div class="container">
  <div class="row magazine-page clear">
   <div class="col-md-12">
    <h2 class="floor_title col-md-12">
    部队采购
	</h2>
   </div>
   </div>
   <div class="row magazine-page clear">
      <div class="col-md-4 mb10 login_box">
        <div class=" border1 job-content floor_kind">
          <h2>
		  <span class="col-md-5">采购公告</span>
		  <ul class="list-unstyled mt0 fr col-md-7 p0">
		    <li  class="active fl"><a aria-expanded="true" href="#tab-12" data-toggle="tab"> 物资</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-13" data-toggle="tab" > 工程</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-14" data-toggle="tab" > 服务</a></li>
		  </ul>
          </h2>
		  <div class=" tab-content">
		    <div id="tab-12" class="tab-pane fade active in">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-13" class="tab-pane fade">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-14" class="tab-pane fade">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
	   </div>
	  </div>
     </div>
	
       <div class="col-md-4 mb10 login_box">
        <div class=" border1 job-content floor_kind">
          <h2>
		  <span class="col-md-5">中标公告</span>
		  <ul class="list-unstyled mt0 fr col-md-7 p0">
		    <li  class="active fl"><a aria-expanded="true" href="#tab-15" data-toggle="tab"> 物资</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-16" data-toggle="tab" > 工程</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-17" data-toggle="tab" > 服务</a></li>
		  </ul>
          </h2>
		  <div class=" tab-content">
		    <div id="tab-15" class="tab-pane fade active in">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-16" class="tab-pane fade">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-17" class="tab-pane fade">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
	   </div>
	  </div>
     </div>
      <div class="col-md-4 mb10 login_box">
        <div class=" border1 job-content floor_kind">
          <h2>
		  <span class="col-md-5">单一来源公告</span>
		  <ul class="list-unstyled mt0 fr col-md-7 p0">
		    <li  class="active fl"><a aria-expanded="true" href="#tab-18" data-toggle="tab"> 物资</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-19" data-toggle="tab" > 工程</a></li>
		    <li class="fl"><a aria-expanded="false" href="#tab-20" data-toggle="tab" > 服务</a></li>
		  </ul>
          </h2>
		  <div class=" tab-content">
		    <div id="tab-18" class="tab-pane fade active in">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-19" class="tab-pane fade">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
		    <div id="tab-20" class="tab-pane fade">
             <ul class="categories">   
              <li></li>
          </ul>
        </div>
	   </div>
	  </div>
     </div>  
  </div>
</div>
<!--业务办理开始-->
<div class="container">
  <div class="row magazine-page clear">
   <div class="col-md-12">
    <h2 class="floor_title col-md-12">
    业务办理
	</h2>
   </div>
   </div>
   <div class="row magazine-page clear">
    <div class="col-md-12">
     <div class="border1 flow_btn fl mr14">
	   <div class="ywbl_01">
	     <a href="#">
          <span>企业注册</span> 
		 </a>
	   </div>
	   <div class="ywbl_01">
	     <a href="${pageContext.request.contextPath}/expert/toRegisterNotice.html" class="zjzc">
          <span>专家注册</span> 
		 </a>
	   </div>
	   <div class="ywbl_01">
	     <a href="#" class="jksdj">
          <span>进口商登记</span> 
		 </a>
	   </div>
	 </div>
	 
     <div class="border1 flow_btn fl">
	   <div class="ywbl_01">
	     <a href="#" class="wssc">
          <span>网上商城</span> 
		 </a>
	   </div>
	   <div class="ywbl_01">
	     <a href="#" class="dxcpjj">
          <span>定型产品竞价</span> 
		 </a>
	   </div>
	   <div class="ywbl_01">
	     <a href="#" class="ypcg">
          <span>药品采购</span> 
		 </a>
	   </div>
	   <div class="ywbl_01">
	     <a href="#" class="fwcg">
          <span>服务采购</span> 
		 </a>
	   </div>
	   <div class="ywbl_01">
	     <a href="#" class="fwrx">
          <span>服务热线</span> 
		 </a>
	   </div>
	 </div>
	 
	 
     <div class="border1 flow_btn clear">
	   <div class="ywbl_02">
	     <a href="#" class="cpml">
          <span>产品目录</span> 
		 </a>
	   </div>
	   <div class="ywbl_02">
	     <a href="#" class="jscsk">
          <span>技术参数库</span> 
		 </a>
	   </div>
	   <div class="ywbl_02">
	     <a href="#" class="zlxz">
          <span>资料下载</span> 
		 </a>
	   </div>
	   <div class="ywbl_02">
	     <a href="#" class="cpshfw">
          <span>产品售后服务</span> 
		 </a>
	   </div>
	   <div class="ywbl_02">
	     <a href="#" class="zxts">
          <span>在线投诉</span> 
		 </a>
	   </div>
	   <div class="ywbl_02">
	     <a href="${pageContext.request.contextPath}/park/getIndex.html" class="cglt">
          <span>采购论坛</span> 
		 </a>
	   </div>
	   <div class="ywbl_02">
	     <a href="#" class="yjfk">
          <span>意见反馈</span> 
		 </a>
	   </div>
	 </div>
	 </div>
   </div>
</div>

<!--采购信息开始-->
<div class="container">
  <div class="row magazine-page clear">
   <div class="col-md-12">
    <h2 class="floor_title col-md-12">
      采购信息
	</h2>
   </div>
   </div>
   <div class="row magazine-page clear">
      <div class="col-md-3 mb10 login_box">
        <div class=" border1 job-content floor_kind">
          <h2 class="p0_10">
		   重要通知<a class="news_more">更多>></a>
          </h2>
		    <div class="categories">
             <ul class="p0_10">   
              <li></li>
          </ul>
        </div>
	</div>
	</div>
	<div class="col-md-3 mb10 login_box">
        <div class=" border1 job-content floor_kind">
          <h2 class="p0_10">
		   采购法规<a class="news_more">更多>></a>
          </h2>
		    <div class="categories">
             <ul class="p0_10">   
              <li></li>
          </ul>
        </div>
	</div>
	</div>
      <div class="col-md-3 mb10 login_box">
        <div class=" border1 job-content floor_kind">
          <h2 class="p0_10">
		   投诉处理公告<a class="news_more">更多>></a>
          </h2>
		    <div class="categories">
             <ul class="p0_10">   
              <li></li>
          </ul>
        </div>
	</div>
	</div>
      <div class="col-md-3 mb10 login_box">
        <div class=" border1 job-content floor_kind">
          <h2 class="p0_10">
		   处罚公告<a class="news_more">更多>></a>
          </h2>
		    <div class="categories">
             <ul class="p0_10">   
              <li></li>
          </ul>
        </div>
	</div>
	</div>
  </div>
</div>
<!--底部代码开始-->
<jsp:include page="/index_bottom.jsp"></jsp:include>
</div>
</div>
<!--[if lt IE 9]>
    <script src="/assets/plugins/respond.js?body=1"></script>
<script src="/assets/plugins/html5shiv.js?body=1"></script>
<script src="/assets/plugins/html5.js?body=1"></script>
<script src="/assets/plugins/placeholder-IE-fixes.js?body=1"></script>
<script src="/assets/ie_9.js?body=1"></script>
<![endif]-->

</body>
</html>
