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
	
	<jsp:include page="backend_common.jsp"></jsp:include>	
	
<script type="text/javascript">
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
			url:"login/login.do",
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
					window.location.href="login/index.do";
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
	<div class="header-v4">
    <!-- Navbar -->
    <div class="navbar navbar-default mega-menu" role="navigation">
      <div class="container">
        <!-- logo和搜索 -->
        <div class="navbar-header">
          <div class="row container margin-bottom-10">
            <div class="col-md-8">
              <a href="">
                 <img alt="Logo" src="${pageContext.request.contextPath}/public/ZHQ/images/logo.png" id="logo-header">
              </a>
            </div>
			<!--搜索开始-->
            <div class="col-md-4 mt50">
              <div class="search-block-v2">
                <div class="">
                  <form accept-charset="UTF-8" action="" method="get">
				    <div style="display:none">
				     <input name="utf8" value="✓" type="hidden">
					</div>
                    <input id="t" name="t" value="search_products" type="hidden">
                    <div class="col-md-12 pull-right">
                      <div class="input-group bround4">
                        <input class="form-control h38" id="k" name="k" placeholder="" type="text">
                        <span class="input-group-btn">
                          <input class="btn-u h38" name="commit" value="搜索" type="submit">
                        </span>
                      </div>
                    </div>
                  </form>               
               </div>
              </div>
            </div>
          <!--搜索结束-->
          </div>
		 </div>

          <button data-target=".navbar-responsive-collapse" data-toggle="collapse" class="navbar-toggle" type="button">
            <span class="full-width-menu">全部菜单</span>
            <span class="icon-toggle">
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </span>
          </button>
      </div>

    <div class="clearfix"></div>

    <div style="height: 0px;" aria-expanded="false" class="navbar-collapse navbar-responsive-collapse collapse">
    <div class="container">
      <ul class="nav navbar-nav">
      <!-- 通知 -->
        <li class="active dropdown tongzhi_li">
          <a class=" dropdown-toggle p0_30" href=""><i class="tongzhi nav_icon"></i>通知</a>
        </li>
      <!-- End 通知 -->

      <!-- 公告 -->
        <li class="dropdown gonggao_li">
          <a class=" dropdown-toggle p0_30" href=""><i class="gonggao nav_icon"></i>公告</a>
        </li>
      <!-- End 公告 -->

      <!-- 公示 -->
        <li class="dropdown gongshi_li">
          <a data-toggle="dropdown" class="dropdown-toggle p0_30 " href=""><i class="gongshi nav_icon"></i>公示</a>
        </li>
      <!-- End 公示 -->

      <!-- 专家 -->
        <li class="dropdown zhuanjia_li">
          <a  href="#" class="dropdown-toggle p0_30 " data-toggle="dropdown"><i class="zhuanjia nav_icon"></i>专家</a>
        </li>
      <!-- End 专家 -->

      <!-- 投诉 -->
        <li class="dropdown tousu_li">
          <a data-toggle="dropdown" class="dropdown-toggle p0_30" href="" ><i class="tousu nav_icon"></i>投诉</a>
        </li>
      <!-- End 投诉 -->

      <!-- 法规 -->
        <li class="dropdown  fagui_li">
          <a href="" class="dropdown-toggle p0_30" data-toggle="dropdown" ><i class="fagui nav_icon"></i>法规</a>
        </li>
      <!-- End 法规 -->

        <li class="dropdown luntan_li">
          <a aria-expanded="false" href="" class="dropdown-toggle p0_30" data-toggle="dropdown"><i class="luntan nav_icon"></i>论坛</a>
        </li>

      </ul>
    </div>
	</div>
  <!--/end container-->
   </div>
  </div>
  <!-- End Navbar -->
  <div class="container content height-350 job-content ">
     <div class="row magazine-page">
      <div class="col-md-6  margin-bottom-10">
        <div class="tab-v1">
          <h2 class="nav nav-tabs">
           <c:forEach items="${indexMapper['select1List']}" begin="0" end="0" var="i">
           	${i.articleType.name }
           </c:forEach>
           	<span class="pull-right"><a href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.do?id=1" target="_self">更多>></a></span>
		  </h2>
        </div>
          <div class="tab-content">
            <div class="tab-pane fade active in">
              <div class="tag-box margin-bottom-0 padding-left-0">
                <ul class="list-unstyled categories">
                 <c:forEach items="${indexMapper['select1List']}" var="i">
                 <li>
           			<a href="#" title="${i.name }" target="_blank">${i.name }</a>
                    <span class="hex pull-right"><fmt:formatDate value='${i.createdAt}' pattern="yyyy年MM月dd日 " /></span>
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
            <span class="pull-right"><a href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.do?id=2" target="_self">更多>></a></span>
		  </h2>
		  </div>
          <div class="tab-content">
            <div class="tab-pane fade active in">
              <div class="tag-box margin-bottom-0 padding-left-0">
                <ul class="list-unstyled categories">
                 <c:forEach items="${indexMapper['select2List']}" var="i">
                 <li>
           			<a href="#" title="${i.name }" target="_blank">${i.name }</a>
                    <span class="hex pull-right"><fmt:formatDate value='${i.createdAt}' pattern="yyyy年MM月dd日 " /></span>
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
           <span class="pull-right"><a href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.do?id=3" target="_self">更多>></a></span>
          </h2>
          <ul class="list-unstyled categories tab-content margin-0">
	             <c:forEach items="${indexMapper['select3List']}" var="i">
	             <li>
	       			<a href="#" title="${i.name }" target="_blank">${i.name }</a>
	                <span class="hex pull-right"><fmt:formatDate value='${i.createdAt}' pattern="yyyy年MM月dd日 " /></span>
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
          <span class="pull-right"><a href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.do?id=4" target="_self">更多>></a></span>
          </h2>
          <ul class="list-unstyled categories tab-content">
	             <c:forEach items="${indexMapper['select4List']}" var="i">
	             <li>
	       			<a href="#" title="${i.name }" target="_blank">${i.name }</a>
	                <span class="hex pull-right"><fmt:formatDate value='${i.createdAt}' pattern="yyyy年MM月dd日 " /></span>
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
      <form class="form-horizontal p15_0" action="${pageContext.request.contextPath}/login/login.do" method="post">
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
         <input type="text" placeholder="" id="inputCode" class="input-mini fl margin-right-20">
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
  <!-- End 右侧边栏 -->
  <!--/*中间图片*/-->
  <div class="container content">
   <div class="margin-bottom-10">
	  <img src="${pageContext.request.contextPath}/public/ZHQ/images/center_pic.jpg" class="img-responsive full-width"/>
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
          <span class="pull-right"><a href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.do?id=5" target="_self">更多>></a></span>
         </h2>
          <ul class="list-unstyled categories tab-content">
             <c:forEach items="${indexMapper['select5List']}" var="i">
	             <li>
	       			<a href="#" title="${i.name }" target="_blank">${i.name }</a>
	                <span class="hex pull-right"><fmt:formatDate value='${i.createdAt}' pattern="yyyy年MM月dd日 " /></span>
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
      <span class="pull-right"><a href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.do?id=6" target="_self">更多>></a></span>
    </h2>
          <ul class="list-unstyled categories tab-content">
            <c:forEach items="${indexMapper['select6List']}" var="i">
                 <li>
           			<a href="#" title="${i.name }" target="_blank">${i.name }</a>
                    <span class="hex pull-right"><fmt:formatDate value='${i.createdAt}' pattern="yyyy年MM月dd日 " /></span>
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
      <h2 class=""> 用户登录</h2>
	  <div class="padding-top-13 padding-bottom-10">
	  <ul class="list-inline blog-photostream ">
      <li class="fl margin-5"><a href="#" class="content-boxes-v3"><span class="btn-u btn-u-lg btn-u-yellow"><i class="icon-custom icon-sm qyzc"></i>企业注册</span></a></li>
	  <li class="fl margin-5"><a href="${pageContext.request.contextPath}/expert/toExpert.do" class="content-boxes-v3"><span class="btn-u btn-u-lg btn-u-yellow"><i class="icon-custom icon-sm zjzc"></i>专家注册</span></a></li>
	  <li class="fl margin-5"><a href="#" class="content-boxes-v3"><span class="btn-u btn-u-lg btn-u-yellow"><i class="icon-custom icon-sm qyml"></i>企业名录</span></a></li>
	  <li class="fl margin-5"><a href="${pageContext.request.contextPath}/expert/findAllExpert.do" class="content-boxes-v3"><span class="btn-u btn-u-lg btn-u-yellow"><i class="icon-custom icon-sm zjmd"></i>专家名单</span></a></li>
	  <li class="fl margin-5"><a href="#" class="content-boxes-v3"><span class="btn-u btn-u-lg btn-u-yellow"><i class="icon-custom icon-sm xzzq"></i>下载专区</span></a></li>
	  <li class="fl margin-5"><a href="#" class="content-boxes-v3"><span class="btn-u btn-u-lg btn-u-yellow"><i class="icon-custom icon-sm gjfg"></i>国家法规</span></a></li>
	  <li class="fl margin-5"><a href="#" class="content-boxes-v3"><span class="btn-u btn-u-lg btn-u-yellow"><i class="icon-custom icon-sm jgzs"></i>价格指数</span></a></li>
	  <li class="fl margin-5"><a href="#" class="content-boxes-v3"><span class="btn-u btn-u-lg btn-u-yellow"><i class="icon-custom icon-sm cfmd"></i>处罚名单</span></a></li>
	  </ul>
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
			</a></li>
          </ul>
          <div class="tab-content">
            <div class="tab-pane fade active in" id="tab-1">
              <div class=" margin-bottom-0  categories">
                <ul class="list-unstyled categories">              
	                 <c:forEach items="${indexMapper['select7List']}" var="i">
	                 	<li>
	           			<a href="#" title="${i.name }" target="_blank">${i.name }</a>
	                    <span class="hex pull-right"><fmt:formatDate value='${i.createdAt}' pattern="yyyy年MM月dd日 " /></span>
	                    </li>
	           		 </c:forEach>
                 <span class="pull-right"><a href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.do?id=7" target="_self">更多>></a></span>
                </ul>
              </div>
            </div>
            <div class="tab-pane fade" id="tab-2">
              <div class="margin-bottom-0  categories">
                <ul class="list-unstyled categories">
	                 <c:forEach items="${indexMapper['select8List']}" var="i">
	                 <li>
	           			<a href="#" title="${i.name }" target="_blank">${i.name }</a>
	                    <span class="hex pull-right"><fmt:formatDate value='${i.createdAt}' pattern="yyyy年MM月dd日 " /></span>
	                 </li>   
	           		 </c:forEach>
	           		 <span class="pull-right"><a href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.do?id=8" target="_self">更多>></a></span>
			   </ul>
              </div>
            </div>
			
            <div class="tab-pane fade" id="tab-3">
              <div class=" margin-bottom-10  categories">
                <ul class="list-unstyled categories">              
	                 <c:forEach items="${indexMapper['select9List']}" var="i">
	                 <li>
	           			<a href="#" title="${i.name }" target="_blank">${i.name }</a>
	                    <span class="hex pull-right"><fmt:formatDate value='${i.createdAt}' pattern="yyyy年MM月dd日 " /></span>
	                 </li>
	           		 </c:forEach>
	           		 <span class="pull-right"><a href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.do?id=9" target="_self">更多>></a></span>
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
	           			<a href="#" title="${i.name }" target="_blank">${i.name }</a>
	                    <span class="hex pull-right"><fmt:formatDate value='${i.createdAt}' pattern="yyyy年MM月dd日 " /></span>
	                 </li>
	           		 </c:forEach>
	           		 <span class="pull-right"><a href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.do?id=10" target="_self">更多>></a></span>
                </ul>
              </div>
            </div>
            <div class="tab-pane fade" id="tab-5">
              <div class="margin-bottom-0">
                <ul class="list-unstyled categories">
	                 <c:forEach items="${indexMapper['select11List']}" var="i">
	                 <li>
	           			<a href="#" title="${i.name }" target="_blank">${i.name }</a>
	                    <span class="hex pull-right"><fmt:formatDate value='${i.createdAt}' pattern="yyyy年MM月dd日 " /></span>
	                 </li>
	           		 </c:forEach>
	           		 <span class="pull-right"><a href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.do?id=11" target="_self">更多>></a></span>
                </ul>
              </div>
            </div>
            <div class="tab-pane fade" id="tab-6">
              <div class="margin-bottom-0  categories">
                <ul class="list-unstyled categories">
	                 <c:forEach items="${indexMapper['select12List']}" var="i">
	                 <li>
	           			<a href="#" title="${i.name }" target="_blank">${i.name }</a>
	                    <span class="hex pull-right"><fmt:formatDate value='${i.createdAt}' pattern="yyyy年MM月dd日 " /></span>
	                 </li>
	           		 </c:forEach>
	           		 <span class="pull-right"><a href="${pageContext.request.contextPath}/index/selectIndexNewsByTypeId.do?id=12" target="_self">更多>></a></span>
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
        <!-- <a href="http://www.ctba.org.cn/" target="_blank"> -->
      <a href="/" target="_blank">
		   <img src="${pageContext.request.contextPath}/public/ZHQ/images/friend_01.jpg"/>
      </a>
    </li>
    <li>
        <!-- <a href="http://ecp.sgcc.com.cn/" target="_blank"> -->
        <a href="/" target="_blank">
		  <img src="${pageContext.request.contextPath}/public/ZHQ/images/friend_02.jpg"/>
        </a>
    </li>
    <li>
        <!-- <a href="http://www.bidding.csg.cn/" target="_blank"> -->
        <a href="/" target="_blank">
		  <img src="${pageContext.request.contextPath}/public/ZHQ/images/friend_03.jpg"/>
        </a>
    </li>
    <li>
        <!-- <a href="http://eportal.energyahead.com/" target="_blank"> -->
        <a href="/" target="_blank">
		  <img src="${pageContext.request.contextPath}/public/ZHQ/images/friend_04.jpg"/>
        </a>
    </li>
    <li>
        <!-- <a href="http://www.sinograin.com.cn/" target="_blank"> -->
        <a href="/" target="_blank">
          <img src="${pageContext.request.contextPath}/public/ZHQ/images/friend_05.jpg"/>
        </a>
    </li>
  </ul>
	 </div>
   </div>
  </div> 

</div>


</body>
</html>
