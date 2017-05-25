<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<jsp:include page="/index_head.jsp"></jsp:include>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<script type="text/javascript">
	/* 分页 */
	$(function() {
		laypage({
			cont : $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
			pages : "${info.pages}", //总页数
			skin : '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			skip : true, //是否开启跳页
			total : "${info.total}",
			startRow : "${info.startRow}",
			endRow : "${info.endRow}",
			groups : "${info.pages}" >= 3 ? 3 : "${info.pages}", //连续显示分页数
			curr : function() { //通过url获取当前页，也可以同上（pages）方式获取
				return "${info.pageNum}";
			}(),
			jump : function(e, first) { //触发分页后的回调
				if (!first) { //一定要加此判断，否则初始时会无限刷新
					var servicecontent = $("#servicecontent").val();
					window.location.href="${pageContext.request.contextPath }/serviceHotline/index_list.html?servicecontent="+servicecontent+"&&page="+e.curr;
				}
			}
		});
	});
	
	//查询
	function query(){
		var servicecontent = $("#servicecontent").val();
		window.location.href="${pageContext.request.contextPath }/serviceHotline/index_list.html?servicecontent="+servicecontent;
	}
	
	//重置
	function res(){
		window.location.href="${pageContext.request.contextPath }/serviceHotline/index_list.html";
	}
</script>
</head>

<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a
					href="${pageContext.request.contextPath}/index/selectIndexNews.html">首页</a></li>
				<li><a href="javascript:void(0);">服务热线</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<!-- 热线电话列表页面 -->
	<div class="container job-content ">
		<div class="search_box col-md-12 col-sm-12 col-xs-12">
			服务内容：<input name = "servicecontent" value="${serviceHotline.servicecontent }" class="m0" type="text" id="servicecontent"/>
			<div class="inline-block ml5">
				<button type="button" onclick="query()" class="btn btn-u-light-grey">查询</button>
				<button type="button" onclick="res()" class="btn btn-u-light-grey">重置</button>
			</div>
		</div>
		<div class="col-md-12 col-sm-12 col-xs-12 border1 p20_20 lh35">
			<div class="col-md-12 col-sm-12 col-xs-12 bg7 h35">
				<div class="col-md-6 col-xs-4 col-sm-4 tc f16">服务内容</div>
				<div class="col-md-6 col-xs-3 col-sm-4 tc f16">联系电话</div>
			</div>
			<ul class="categories li_square col-md-12 col-sm-12 col-xs-12 p0">
				<c:forEach items="${info.list }" var="hotline" varStatus="vs">
					<li>
						<div class="col-md-6 col-xs-4 col-sm-4">
							<span class="f16 mr5 fl">·</span>${hotline.servicecontent }
						</div>
						<div class="col-md-6 col-xs-3 col-sm-4 tc">
							<span class="f16 mr5">${hotline.contactphonenumber }</span>
						</div>
					</li>
				</c:forEach>
			</ul>
			<div id="pagediv" align="right"></div>
		</div>
	</div>
	<!--底部代码开始-->
	<jsp:include page="/index_bottom.jsp"></jsp:include>
</body>
</html>
