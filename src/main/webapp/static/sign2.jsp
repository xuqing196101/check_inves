<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
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
	<link href="<%=basePath%>public/ZHQ/css/common.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHQ/css/bootstrap.min.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHQ/css/style.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHQ/css/app.css" media="screen" rel="stylesheet">
   <!--  <link href="css/header-v4.css" media="screen" rel="stylesheet">
    <link href="css/footer-v2.css" media="screen" rel="stylesheet">
    <link href="css/page_job.css" media="screen" rel="stylesheet">
    <link href="css/shop.style.css" media="screen" rel="stylesheet">
	<link href="css/page_log_reg_v1.css" media="screen" rel="stylesheet"> -->
</head>
<body class="sign_bg">
    <div class="container content">
     <div class="row">
	   <div class="col-md-8"><img src="images/logo.png" width="50%" height="90%"/></div><div class="col-md-4"></div>
    <div class="container content">
     <div class="row">
	   <div class="sign_left col-md-5 mt60 ">
	     <img src="images/sign_left.jpg" width="100%" height="100%"/>
	   </div>
	   <div class="col-md-5 mt60 login_right  col-md-offset-1 ">
        <div class="col-md-10 col-sm-8 clear">
         <div class="box-shadow shadow-effect-2 opacity-80 sign_box">
          <header class="margin-top-10 ofh">
		   <ul class="list-unstyled sign_kinds col-md-12 p0">
		     <li class="active fl col-md-5">
			   <a aria-expanded="true" href="#tab-1" data-toggle="tab" class="col-md-12">
			   <!--<span class="icon-user common_user"></span> --> �û���¼</a>
			 </li>
		     <li class="fl col-md-5">
			   <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="col-md-12">
			   <!--<span class="icon-user ca-user" ></span>--> CA��¼</a>
			 </li>
		 </ul>
		  </header>
         <div class="tab-content">
		   <div class="tab-pane active in" id="tab-1">
            
			 <form accept-charset="UTF-8"  class="sky-form reg-page" method="post" >
             <section  class="mb5">
              <div class="row">
                <label class="label col col-md-3">�û���</label>
                <div class="col col-md-8">
                  <label class="input">
   
                    <input id="" name="" placeholder="�û���" type="text">
                  </label>
                </div>
              </div>
            </section>

            <section class="mb5">
              <div class="row">
                <label class="label col col-md-3">�� ��</label>
                <div class="col col-md-8">
                  <label class="input">
    
                    <input id="" name="" placeholder="�� ��" type="password">
                  </label>
                  <div class="note"></div>
                </div>
              </div>
            </section>
			<section  class="mb20">
			 <div class="row">
                <label class="label col col-md-3">��֤��</label>
                <div class="col col-md-8">
                  <label class="input">
				     <div class="col-md-8 p0"><input type="password" placeholder="" class="fl col-md-8"></div>
                     <div class="col-md-4"><img src="images/yzm.jpg" width="100%" height="34px"/></div>
                  </label>
                  <div class="note"></div>
                </div>
              </div>
			</section>
          <div class="content border-top-1 tc">
            <button type="submit" class="btn login_btn" id="">�� ¼</button>
          </div>
        </form> 
	   </div>   
		   <div class="tab-pane" id="tab-2">
			 <form accept-charset="UTF-8"  class="sky-form reg-page" method="post" >
             <section  class="mb5">
              <div class="row">
                <label class="label col col-md-3">�û���</label>
                <div class="col col-md-8">
                  <label class="input">
                    <input id="" name="" placeholder="�û���" type="text">
                  </label>
                </div>
              </div>
            </section>

            <section class="mb5">
              <div class="row">
                <label class="label col col-md-3">�� ��</label>
                <div class="col col-md-8">
                  <label class="input">
    
                    <input id="" name="" placeholder="�� ��" type="password">
                  </label>
                  <div class="note"></div>
                </div>
              </div>
            </section>
			<section  class="mb20">
			 <div class="row">
                <label class="label col col-md-3">��֤��</label>
                <div class="col col-md-8">
                  <label class="input">
				     <div class="col-md-8 p0"><input type="password" placeholder="" class="fl col-md-8"></div>
                     <div class="col-md-4"><img src="images/yzm.jpg" width="100%" height="34px"/></div>
                  </label>
                  <div class="note"></div>
                </div>
              </div>
			</section>
   
          <div class="content border-top-1 tc">
            <button type="submit" class="btn login_btn" id="">�� ¼</button>
          </div>
        </form> 
	   </div>  
      </div>

     </div>
    </div>
	
	   </div>
	 </div>
	 <div class="container clear tc mt60">Copyright  2016 ��Ȩ���У������ί���ڱ��ϲ�</div>
	</div>
</body>
</html>
