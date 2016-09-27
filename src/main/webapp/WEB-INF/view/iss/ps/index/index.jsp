<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
<link href="<%=basePath%>public/ZHQ/css/line-icons.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHQ/css/app.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHQ/css/application.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHQ/css/header-v4.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHQ/css/footer-v2.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHQ/css/img-hover.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHQ/css/page_job.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHQ/css/shop.style.css" media="screen" rel="stylesheet">
<script src="<%=basePath%>public/ZHQ/js/hm.js"></script>
<script src="<%=basePath%>public/ZHQ/js/jquery.min.js"></script>
<!--导航js-->
<script src="<%=basePath%>public/ZHQ/js/jquery_ujs.js"></script>
<script src="<%=basePath%>public/ZHQ/js/bootstrap.min.js"></script>
<script src="<%=basePath%>public/layer/layer.js"></script>
<script type="text/javascript">
function login(){
	alert(111);
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
			url:"<%=basePath%>login/login.html",
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
					window.location.href="<%=basePath%>login/index.html";
				}else if(data=="scuesslogin_auditNotPass"){
					layer.close(index);
					window.location.href="<%=basePath%>importSupplier/updateRegister.html";
				}else if(data=="auditing"){
					layer.msg("注册帐号在审核中...审核通过才能登录!");
				}else if(data=="heimingdan"){
					layer.msg("不好意思(先生/女士),您的企业信息不符合我们的相关规定,禁止注册.");
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
  <div class="wrapper">
  <jsp:include page="/index_head.jsp"></jsp:include>
  <!-- End Navbar -->
  <div class="container content height-350 job-content ">
     <div class="row magazine-page">
      <div class="col-md-6  margin-bottom-10">
        <div class="tab-v1">
          <h2 class="nav nav-tabs">
           <c:forEach items="${indexMapper['select1List']}" begin="0" end="0" var="i">
           	${i.articleType.name }
           </c:forEach>
           	<span class="pull-right"><a href="<%=basePath%>index/selectIndexNewsByTypeId.html?id=1" target="_self">更多>></a></span>
		  </h2>
        </div>
          <div class="tab-content">
            <div class="tab-pane fade active in">
              <div class="tag-box margin-bottom-0 padding-left-0">
                <ul class="list-unstyled categories">
                 <c:forEach items="${indexMapper['select1List']}" var="i">
                 <li>
           			<a href="<%=basePath%>index/selectArticleNewsById.html?id=${i.id}" title="${i.name }" target="_self">${i.name }</a>
                    <span class="hex pull-right"><fmt:formatDate value='${i.publishedAt}' pattern="yyyy年MM月dd日 " /></span>
                 </li>
           		 </c:forEach>
                </ul>
              </div>
            </div>
          </div>
       </div>
	  
	  
       <div class="col-md-6 ">
        <div class="tab-v1">
          <h2 class="nav nav-tabs">
            <c:forEach items="${indexMapper['select2List']}" begin="0" end="0" var="i">
           		${i.articleType.name }
            </c:forEach>
            <span class="pull-right"><a href="<%=basePath%>index/selectIndexNewsByTypeId.html?id=2" target="_self">更多>></a></span>
		  </h2>
		  </div>
          <div class="tab-content">
            <div class="tab-pane fade active in">
              <div class="tag-box margin-bottom-0 padding-left-0">
                <ul class="list-unstyled categories">
                 <c:forEach items="${indexMapper['select2List']}" var="i">
                 <li>
           			<a href="<%=basePath%>index/selectArticleNewsById.html?id=${i.id}" title="${i.name }" target="_self">${i.name }</a>
                    <span class="hex pull-right"><fmt:formatDate value='${i.publishedAt}' pattern="yyyy年MM月dd日 " /></span>
                 </li>
           		 </c:forEach>
                </ul>
             </div>
	        </div>
          </div>
       </div>
	  </div>
  </div>
  <div class="container content height-350">
   <div class="row magazine-page">
    <div class="col-md-9">
	
    <div class="row job-content tab-v2">
	
      <div class="col-md-6 tab-v2 margin-bottom-10">
        <div class="tag-box-v1 margin-bottom-0">
          <h2>
          <c:forEach items="${indexMapper['select3List']}" begin="0" end="0" var="i">
           	${i.articleType.name }
           </c:forEach>
           <span class="pull-right f14 mr10"><a href="<%=basePath%>index/selectIndexNewsByTypeId.html?id=3" target="_self">更多>></a></span>
             <div class="clear">
          </h2>
          <ul class="list-unstyled categories tab-content margin-0">
	             <c:forEach items="${indexMapper['select3List']}" var="i">
	             <li>
	       			<a href="<%=basePath%>index/selectArticleNewsById.html?id=${i.id}" title="${i.name }" target="_self">${i.name }</a>
	                <span class="hex pull-right"><fmt:formatDate value='${i.publishedAt}' pattern="yyyy年MM月dd日 " /></span>
	             </li>
	       		 </c:forEach>
          </ul>
        </div>
      </div>

      <div class="col-md-6 tab-v2 ">
        <div class="tag-box-v1 margin-bottom-10">
          <h2>
           <c:forEach items="${indexMapper['select4List']}" begin="0" end="0" var="i">
           	${i.articleType.name }
           </c:forEach>
          <span class="pull-right f14 mr10"><a href="<%=basePath%>index/selectIndexNewsByTypeId.html?id=4" target="_self">更多>></a></span>
          <div class="clear">
          </h2>
          <ul class="list-unstyled categories tab-content">
	             <c:forEach items="${indexMapper['select4List']}" var="i">
	             <li>
	       			<a href="<%=basePath%>index/selectArticleNewsById.html?id=${i.id}" title="${i.name }" target="_self">${i.name }</a>
	                <span class="hex pull-right"><fmt:formatDate value='${i.publishedAt}' pattern="yyyy年MM月dd日 " /></span>
	             </li>
	       		 </c:forEach>
          </ul>
        </div>
      </div>
	</div>  
	</div>
   <!-- Begin 右侧边栏 -->
  <div class="col-md-3">

    <div class="tag-box-v6 margin-bottom-10 tag-box-v1 padding-bottom-30">
      <div class=""><h2 class=""> 用户登录</h2></div>
      <form class="form-horizontal p15_0" action=#" method="post">
        <div class="control-group margin-top-15">
          <label class="control-label" for="inputEmail">用户名：</label>
			  <div class="controls padding-right-10">
            <input type="text" id="inputEmail" name="loginName" class="form-control" placeholder="请输入用户名"/>
          </div>
        </div>
        <div class="control-group  margin-top-20 padding-right-10">
          <label class="control-label" for="inputPassword">密码：</label>
          <div class="controls">
          <input type="password" id="inputPassword" name="password" class="form-control" placeholder="请输入密码">
          </div>
        </div>
        <div class="control-group  margin-top-20 ">
        <label class="control-label" for="inputPassword">验证码：</label>
        <div class="controls">
         <input type="text" placeholder="" id="inputCode" class="input-mini fl ">
          	<img src="Kaptcha.jpg" onclick="kaptcha();" id="kaptchaImage" /> 
        </div>
       </div>
       <div class="control-group margin-top-22 clear">
        <div class="controls">
    <button class="btn" type="button" onclick="login();">登录</button>
		  <button onclick="kaptcha();"  class="btn btn-u-light-grey margin-left-20" type="reset">重置</button>
        </div>
      </div>
    </form>
    </div>
    </div>
    </div>
  </div>
  </div>
  <!-- End 右侧边栏 -->
  <!--/*中间图片*/-->
  <div class="container content">
   <div class="margin-bottom-10">
	  <img src="<%=basePath%>public/ZHQ/images/center_pic.jpg" class="img-responsive full-width"/>
    </div>
</div>
  <div class="container content height-350">
   <div class="row magazine-page">
    <div  class="col-md-9">
	
    <div class="row job-content margin-bottom-10 tab-v2">
	
      <div class="col-md-6 margin-bottom-10">
        <div class="tag-box-v1 margin-bottom-0">
          <h2>
           <c:forEach items="${indexMapper['select5List']}" begin="0" end="0" var="i">
           	${i.articleType.name }
           </c:forEach>
          <span class="pull-right f14 mr10"><a href="<%=basePath%>index/selectIndexNewsByTypeId.html?id=5" target="_self">更多>></a></span>
          <div class="clear"></div>
         </h2>
          <ul class="list-unstyled categories tab-content">
             <c:forEach items="${indexMapper['select5List']}" var="i">
	             <li>
	       			<a href="<%=basePath%>index/selectArticleNewsById.html?id=${i.id}" title="${i.name }" target="_self">${i.name }</a>
	                <span class="hex pull-right"><fmt:formatDate value='${i.publishedAt}' pattern="yyyy年MM月dd日 " /></span>
	             </li>
       		 </c:forEach>
          </ul>
        </div>
      </div>

      <div class="col-md-6">
        <div class="tag-box-v1 margin-bottom-0">
          <h2>
           <c:forEach items="${indexMapper['select6List']}" begin="0" end="0" var="i">
           	${i.articleType.name }
           </c:forEach>
            <span class="pull-right f14 mr10"><a href="<%=basePath%>index/selectIndexNewsByTypeId.html?id=6" target="_self">更多>></a></span>
             <div class="clear"></div>
       </h2>
          <ul class="list-unstyled categories tab-content">
            <c:forEach items="${indexMapper['select6List']}" var="i">
                 <li>
           			<a href="<%=basePath%>index/selectArticleNewsById.html?id=${i.id}" title="${i.name }" target="_self">${i.name }</a>
                    <span class="hex pull-right"><fmt:formatDate value='${i.publishedAt}' pattern="yyyy年MM月dd日 " /></span>
                 </li>
           	</c:forEach>
          </ul>
        </div>
      </div>
	</div>  
	</div>
  <!-- Begin 右侧边栏 -->
  <div class="col-md-3">

    <div class="tag-box-v6 margin-bottom-10 tag-box-v1">
      <h2 class="">业务办理</h2>
	  <div class="padding-top-13 padding-bottom-10">
	  <ul class="list-inline blog-photostream ">
      <li class="fl margin-5"><a href="#" class="content-boxes-v3"><span class="btn-u btn-u-lg btn-u-yellow"><i class="icon-custom icon-sm qyzc"></i>企业注册</span></a></li>
	  <li class="fl margin-5"><a href="<%=basePath %>expert/toRegisterNotice.html" class="content-boxes-v3"><span class="btn-u btn-u-lg btn-u-yellow"><i class="icon-custom icon-sm zjzc"></i>专家注册</span></a></li>
	  <li class="fl margin-5"><a href="#" class="content-boxes-v3"><span class="btn-u btn-u-lg btn-u-yellow"><i class="icon-custom icon-sm qyml"></i>企业名录</span></a></li>
	  <li class="fl margin-5"><a href="<%=basePath %>expert/findAllExpert.html?flag='0'" class="content-boxes-v3"><span class="btn-u btn-u-lg btn-u-yellow"><i class="icon-custom icon-sm zjmd"></i>专家名单</span></a></li>
	  <%--<li class="fl margin-5"><a href="<%=basePath %>expert/toBackLog.html" class="content-boxes-v3"><span class="btn-u btn-u-lg btn-u-yellow"><i class="icon-custom icon-sm zjmd"></i>专家审核信息</span></a></li>
	  --%><li class="fl margin-5"><a href="#" class="content-boxes-v3"><span class="btn-u btn-u-lg btn-u-yellow"><i class="icon-custom icon-sm xzzq"></i>下载专区</span></a></li>
	  <li class="fl margin-5"><a href="#" class="content-boxes-v3"><span class="btn-u btn-u-lg btn-u-yellow"><i class="icon-custom icon-sm gjfg"></i>国家法规</span></a></li>
	  <li class="fl margin-5"><a href="#" class="content-boxes-v3"><span class="btn-u btn-u-lg btn-u-yellow"><i class="icon-custom icon-sm jgzs"></i>价格指数</span></a></li>
	  <li class="fl margin-5"><a href="#" class="content-boxes-v3"><span class="btn-u btn-u-lg btn-u-yellow"><i class="icon-custom icon-sm cfmd"></i>处罚名单</span></a></li>
	   <%--<li class="fl margin-5"><a href="<%=basePath %>importSupplier/registerStart.html" class="content-boxes-v3"><span class="btn-u btn-u-lg btn-u-yellow" style="width:170px;"><i class="icon-custom icon-sm qyzc"></i>进口供应商注册</span></a></li>
	  <li class="fl margin-5"><a href="<%=basePath %>importSupplier/daiban.html" class="content-boxes-v3"><span class="btn-u btn-u-lg btn-u-yellow" style="width:170px;"><i class="icon-custom icon-sm qyzc"></i>进口供应商审核</span></a></li>
	  --%></ul>
	  <div class="clear"></div>
	  </div>
    </div>
    </div>
  </div>



</div>


<div class="container content height-350">
 <div class="row magazine-page">
   <div class="col-md-6 tab-v2 job-content margin-bottom-10">
        <div class="">
          <ul class="nav nav-tabs">
            <li class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" style="font-size:18px;">
            	<c:forEach items="${indexMapper['select7List']}" begin="0" end="0" var="i">
           			${i.articleType.name }
           		</c:forEach>
            </a></li>
            <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" style="font-size: 18px;">
            	<c:forEach items="${indexMapper['select8List']}" begin="0" end="0" var="i">
           			${i.articleType.name }
           		</c:forEach>
            </a></li>
			<li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" style="font-size: 18px;">
				<c:forEach items="${indexMapper['select9List']}" begin="0" end="0" var="i">
           			${i.articleType.name }
           		</c:forEach>
			</a>
			</li>
          </ul>
          <div class="tab-content">
            <div class="tab-pane fade active in" id="tab-1">
              <div class=" margin-bottom-0  categories">
                <ul class="list-unstyled categories">              
	                 <c:forEach items="${indexMapper['select7List']}" var="i">
	                 	<li>
	           			<a href="<%=basePath%>index/selectArticleNewsById.html?id=${i.id}" title="${i.name }" target="_self">${i.name }</a>
	                    <span class="hex pull-right"><fmt:formatDate value='${i.publishedAt}' pattern="yyyy年MM月dd日 " /></span>
	                    </li>
	           		 </c:forEach>
                 <span class="pull-right"><a href="<%=basePath%>index/selectIndexNewsByTypeId.html?id=7" target="_self">更多>></a></span>
                 <div class="clear"></div>
                </ul>
              </div>
            </div>
            <div class="tab-pane fade" id="tab-2">
              <div class="margin-bottom-0  categories">
                <ul class="list-unstyled categories">
	                 <c:forEach items="${indexMapper['select8List']}" var="i">
	                 <li>
	           			<a href="<%=basePath%>index/selectArticleNewsById.html?id=${i.id}" title="${i.name }" target="_self">${i.name }</a>
	                    <span class="hex pull-right"><fmt:formatDate value='${i.publishedAt}' pattern="yyyy年MM月dd日 " /></span>
	                 </li>   
	           		 </c:forEach>
	           		 <span class="pull-right"><a href="<%=basePath%>index/selectIndexNewsByTypeId.html?id=8" target="_self">更多>></a></span>
			      <div class="clear"></div>
			   </ul>
              </div>
            </div>
			
            <div class="tab-pane fade" id="tab-3">
              <div class=" margin-bottom-10  categories">
                <ul class="list-unstyled categories">              
	                 <c:forEach items="${indexMapper['select9List']}" var="i">
	                 <li>
	           			<a href="<%=basePath%>index/selectArticleNewsById.html?id=${i.id}" title="${i.name }" target="_self">${i.name }</a>
	                    <span class="hex pull-right"><fmt:formatDate value='${i.publishedAt}' pattern="yyyy年MM月dd日 " /></span>
	                 </li>
	           		 </c:forEach>
	           		 <span class="pull-right"><a href="<%=basePath%>index/selectIndexNewsByTypeId.html?id=9" target="_self">更多>></a></span>
	           		 <div class="clear"></div>
                </ul>
              </div>
            </div>
          </div> 
		</div> 
  </div>
    <div class="col-md-6 job-content tab-v2">
        <div class="">
          <ul class="nav nav-tabs">
            <li class="active"><a aria-expanded="true" href="#tab-4" data-toggle="tab" style="font-size:18px;">
            	<c:forEach items="${indexMapper['select10List']}" begin="0" end="0" var="i">
           			${i.articleType.name }
           		</c:forEach>
            </a></li>
            <li class=""><a aria-expanded="false" href="#tab-5" data-toggle="tab" style="font-size:18px;">
            	<c:forEach items="${indexMapper['select11List']}" begin="0" end="0" var="i">
           			${i.articleType.name }
           		</c:forEach>
            </a></li>
			<li class=""><a aria-expanded="false" href="#tab-6" data-toggle="tab" style="font-size:18px;">
				<c:forEach items="${indexMapper['select12List']}" begin="0" end="0" var="i">
           			${i.articleType.name }
           		</c:forEach>
			</a></li>
          </ul>
          <div class="tab-content">
            <div class="tab-pane fade active in" id="tab-4">
              <div class="margin-bottom-0  categories">
                <ul class="list-unstyled categories">
	                 <c:forEach items="${indexMapper['select10List']}" var="i">
	                 <li>
	           			<a href="<%=basePath%>index/selectArticleNewsById.html?id=${i.id}" title="${i.name }" target="_self">${i.name }</a>
	                    <span class="hex pull-right"><fmt:formatDate value='${i.publishedAt}' pattern="yyyy年MM月dd日 " /></span>
	                 </li>
	           		 </c:forEach>
	           		 <span class="pull-right"><a href="<%=basePath%>index/selectIndexNewsByTypeId.html?id=10" target="_self">更多>></a></span>
	           		 <div class="clear"></div>
                </ul>
              </div>
            </div>
            <div class="tab-pane fade" id="tab-5">
              <div class="margin-bottom-0">
                <ul class="list-unstyled categories">
	                 <c:forEach items="${indexMapper['select11List']}" var="i">
	                 <li>
	           			<a href="<%=basePath%>index/selectArticleNewsById.html?id=${i.id}" title="${i.name }" target="_self">${i.name }</a>
	                    <span class="hex pull-right"><fmt:formatDate value='${i.publishedAt}' pattern="yyyy年MM月dd日 " /></span>
	                 </li>
	           		 </c:forEach>
	           		 <span class="pull-right"><a href="<%=basePath%>index/selectIndexNewsByTypeId.html?id=11" target="_self">更多>></a></span>
	           		 <div class="clear"></div>
                </ul>
              </div>
            </div>
            <div class="tab-pane fade" id="tab-6">
              <div class="margin-bottom-0  categories">
                <ul class="list-unstyled categories">
	                 <c:forEach items="${indexMapper['select12List']}" var="i">
	                 <li>
	           			<a href="<%=basePath%>index/selectArticleNewsById.html?id=${i.id}" title="${i.name }" target="_self">${i.name }</a>
	                    <span class="hex pull-right"><fmt:formatDate value='${i.publishedAt}' pattern="yyyy年MM月dd日 " /></span>
	                 </li>
	           		 </c:forEach>
	           		 <span class="pull-right"><a href="<%=basePath%>index/selectIndexNewsByTypeId.html?id=12" target="_self">更多>></a></span>
	           		 <div class="clear"></div>
                </ul>
              </div>
            </div>
          </div>
        </div>
    </div>
  </div>
</div> 
	 
<!--友情链接代码开始-->
   <div class="container content padding-top-0">
     <div class=" magazine-page">
     <div class="partners">
       <div class="headline margin-top-30"><h2 class="padding-left-15">
       	   <c:forEach items="${indexMapper['select13List']}" begin="0" end="0" var="i">
           	 ${i.articleType.name }
           </c:forEach></h2></div>
       <ul class="list-inline our-clients margin-top-20" id="effect-2">

    <li>
        <a href="http://www.ctba.org.cn/" target="_blank">
		  <img src="<%=basePath%>public/ZHQ/images/friend_01.jpg"/>
        </a>
    </li>
    <li>
        <a href="http://ecp.sgcc.com.cn/" target="_blank">
		  <img src="<%=basePath%>public/ZHQ/images/friend_02.jpg"/>
        </a>
    </li>
    <li>
        <a href="http://www.bidding.csg.cn/" target="_blank">
		  <img src="<%=basePath%>public/ZHQ/images/friend_03.jpg"/>
        </a>
    </li>
    <li>
        <a href="http://eportal.energyahead.com/" target="_blank">
		  <img src="<%=basePath%>public/ZHQ/images/friend_04.jpg"/>
        </a>
    </li>
    <li>
        <a href="http://www.sinograin.com.cn/" target="_blank">
          <img src="<%=basePath%>public/ZHQ/images/friend_05.jpg"/>
        </a>
    </li>
  </ul>
	 </div>
   </div>
  </div> 
<jsp:include page="/index_bottom.jsp"></jsp:include>
<!--[if lt IE 9]>
    <script src="/assets/plugins/respond.js?body=1"></script>
<script src="/assets/plugins/html5shiv.js?body=1"></script>
<script src="/assets/plugins/html5.js?body=1"></script>
<script src="/assets/plugins/placeholder-IE-fixes.js?body=1"></script>
<script src="/assets/ie_9.js?body=1"></script>
<![endif]-->

</body>
</html>
