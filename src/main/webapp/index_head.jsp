<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String url = (String)pageContext.getRequest().getAttribute("javax.servlet.forward.request_uri");
%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
<!--<![endif]-->
<head>
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
<!--导航js-->
<script src="<%=basePath%>public/ZHQ/js/jquery_ujs.js"></script>
<script src="<%=basePath%>public/ZHQ/js/bootstrap.min.js"></script>
<script type="text/javascript">
$(function(){
	var url = "http://localhost:8080<%=url%>";
	var flag = false;
	$("ul:eq(0) li").each(function(){
		$(this).removeClass();
		var classval = $(this).attr("class");
		var val = classval.split(" ");
		for(var i=0;i<val.length;i++){
			if(val[i]==="active"){
				var newval = classval.substring(6);
				$(this).attr("class",newval);
				break;
			}
		}
		var al = $(this).find("a");
		var alval = al.attr("href");
		var trueAlvals = alval.split("?");
		if(url==trueAlvals[0]){
			var classval = "active "+(al.parent().attr("class"));
			al.parent().attr("class",classval);
			flag = true;
		}
	})
	if(flag==false){
		$("ul:eq(0)").children(":first").attr("class","active active dropdown tongzhi_li");
	}
});

function solrSearch(){
	var condition = $("#k").val();
	window.location.href = "<%=basePath%>index/solrSearch.html?condition=" + condition;
}

function setTab(obj,title){
	$("ul:eq(0) li").each(function(){
		$(this).removeClass();
		var classval = $(this).attr("class");
		var val = classval.split(" ");
		for(var i=0;i<val.length;i++){
			if(val[i]==="active"){
				var newval = classval.substring(6);
				$(this).attr("class",newval);
				break;
			}
		}
	});
	var classval = "active "+($(obj).parent().attr("class"));
	$(obj).parent().attr("class",classval);
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
								<a href=""> <img alt="Logo" src="<%=basePath%>public/ZHQ/images/logo.png" id="logo-header"> </a>
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
													<input class="form-control h38" id="k" name="k" placeholder="请输入关键字" type="text"> <span class="input-group-btn"> <input class="btn-u h38" name="commit" value="搜索" type="button" onclick="solrSearch()"> </span>
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
						<span class="full-width-menu">全部商品分类</span> <span class="icon-toggle"> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </span>
					</button>
				</div>

				<div class="clearfix"></div>


				<div style="height: 0px;" aria-expanded="false" class="navbar-collapse navbar-responsive-collapse collapse">
					<div class="container">
						<ul class="nav navbar-nav">
							<!-- 通知 -->
							<li class="active dropdown tongzhi_li"><a class=" dropdown-toggle p0_30" href="<%=basePath%>index/selectIndexNews.html" onclick="setTab(this,'通知')"><i class="tongzhi nav_icon"></i>通知</a></li>
							<!-- End 通知 -->

							<!-- 公告 -->
							<li class="dropdown gonggao_li"><a class=" dropdown-toggle p0_30" href="http://localhost:8080/zhbj/index/selectArticleNewsById.html?id=A88A958EBCB14DB6A3975BC38F2E001F" onclick="setTab(this,'公告')"><i class="gonggao nav_icon"></i>公告</a></li>
							<!-- End 公告 -->

							<!-- 公示 -->
							<li class="dropdown gongshi_li"><a data-toggle="dropdown" class="dropdown-toggle p0_30 " href="javascript:void(0)" onclick="setTab(this,'公示')"><i class="gongshi nav_icon"></i>公示</a></li>
							<!-- End 公示 -->

							<!-- 专家 -->
							<li class="dropdown zhuanjia_li"><a href="javascript:void(0)" class="dropdown-toggle p0_30 " data-toggle="dropdown" onclick="setTab(this,'专家')"><i class="zhuanjia nav_icon"></i>专家</a></li>
							<!-- End 专家 -->

							<!-- 投诉 -->
							<li class="dropdown tousu_li"><a data-toggle="dropdown" class="dropdown-toggle p0_30" href="javascript:void(0)" onclick="setTab(this,'投诉')"><i class="tousu nav_icon"></i>投诉</a></li>
							<!-- End 投诉 -->

							<!-- 法规 -->
							<li class="dropdown  fagui_li"><a href="javascript:void(0)" class="dropdown-toggle p0_30" data-toggle="dropdown" onclick="setTab(this,'法规')"><i class="fagui nav_icon"></i>法规</a></li>
							<!-- End 法规 -->

							<li class="dropdown luntan_li"><a class=" dropdown-toggle p0_30" href="<%=basePath%>park/getIndex.html" onclick="setTab(this,'论坛')"><i class="luntan nav_icon"></i>论坛</a></li>

						</ul>
					</div>
				</div>
				<!--/end container-->
			</div>
		</div>
	</div>
</body>
</html>
