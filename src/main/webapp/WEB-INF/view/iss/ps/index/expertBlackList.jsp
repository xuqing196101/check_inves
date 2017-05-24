<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<head>
<jsp:include page="/index_head.jsp"></jsp:include>
<%@ include file="../../../common.jsp"%>
</head>

<body>
  <!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li>
					<a href="${pageContext.request.contextPath}/"> 首页</a>
				</li>
				<li>
					<a href="#">专家黑名单</a>
				</li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>

	<div class="container job-content ">
		<div class="search_box">
			<ul class="demand_list" id="search_con">
				<li>
					<label class="fl">专家姓名：</label> 
					<input type="text" id="relName" value="${relName}" />
				</li>
				<li>
					<label class="fl">专家类型：</label>
					<select id="expertType">
            <option selected="selected"  value=''>全部</option>
            <c:forEach items="${expertTypeList}" var="exp">
              <option <c:if test="${expertTypeId == exp.id}">selected</c:if> value="${exp.id}">${exp.name}</option>
            </c:forEach>          
          </select>
				</li>
			</ul>
			<button type="button" onclick="query(1)" class="btn">查询</button>
			<button type="reset" onclick="reset()" class="btn">重置</button>
		</div>
		<div class="col-md-12 col-sm-12 col-xs-12 border1 p20_20">
			<h2 class="col-md-12 col-sm-12 col-xs-12 bg7 h35">
				<div class="col-md-2 col-xs-2 col-sm-5 tc f16">专家名称</div>
				<div class="col-md-1 col-xs-1 col-sm-5 tc f16">类型</div>
				<div class="col-md-2 col-xs-2 col-sm-5 tc f16">处罚类型</div>
				<div class="col-md-2 col-xs-2 col-sm-5 tc f16">处罚日期</div>
				<div class="col-md-2 col-xs-2 col-sm-5 tc f16">处罚时限</div>
				<div class="col-md-3 col-xs-3 col-sm-5 tc f16">处罚理由</div>
			</h2>
			<ul
				class="categories li_square col-md-12 col-sm-12 col-xs-12 p0 list_new">
				<c:choose>
					<c:when test="${!empty page.list}">
						<c:forEach items="${page.list}" var="data">
							<c:set value="${data.relName}" var="name" />
							<c:set value="${data.reason}" var="reason" />
							<c:if test="${fn:length(name) > 8}">
								<c:set value="${fn:substring(name,0,8)}..." var="name" />
							</c:if>
							<c:if test="${fn:length(reason) > 15}">
								<c:set value="${fn:substring(reason,0,15)}..." var="reason" />
							</c:if>
							<li>
								<span class="col-md-2 col-sm-5 col-xs-12" title="${data.relName}">${name}</span>
								<span class="col-md-1 col-sm-5 col-xs-12 tc">${data.expertTypeName} </span>
								<span class="col-md-2 col-sm-5 col-xs-12 tc">
									<c:if test="${data.punishType == 1}">警告</c:if>
									<c:if test="${data.punishType == 2}">严重警告</c:if>
									<c:if test="${data.punishType == 3}">取消资格</c:if>
								</span>
								<span class="col-md-2 col-sm-5 col-xs-12 tc">
									<fmt:formatDate value='${data.dateOfPunishment}' pattern="yyyy-MM-dd " />
								</span>
								<span class="col-md-2 col-sm-5 col-xs-12 tc">
									${data.punishDate}
								</span>
								<span class="col-md-3 col-sm-5 col-xs-12" title="${data.reason}">${reason}</span>
							</li>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<li class="tc">暂无数据~~</span></li>
					</c:otherwise>
				</c:choose>
			</ul>
			<c:if test="${!empty page.list}">
				<div id="pagediv" align="right"></div>
			</c:if>
		</div>
	</div>
	<!--底部代码开始-->
<jsp:include page="/index_bottom.jsp"></jsp:include>

<script type="text/javascript">
	$(function() {
		laypage({
			cont : $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
			pages : "${page.pages}", //总页数
			skin : '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			skip : true, //是否开启跳页
			groups : "${page.pages}" >= 3 ? 3 : "${page.pages}", //连续显示分页数
			total : "${page.total}",
			startRow : "${page.startRow}",
			endRow : "${page.endRow}",
			curr : function() { //通过url获取当前页，也可以同上（pages）方式获取
				var page = location.search.match(/page=(\d+)/);
				return page ? page[1] : 1;
			}(),
			jump : function(e, first) { //触发分页后的回调
				if (!first) { //一定要加此判断，否则初始时会无限刷新
					query(e.curr);
				}
			}
		});
	});

	function query(current) {
		var relName = $("#relName").val().replace(/\s/g, "");
		var expertType = $("#expertType").val();
		var url = "${pageContext.request.contextPath}/index/expertBlackList.html?relName="
				+ relName
				+ "&expertTypeId="
				+ expertType
				+ "&page="
				+ current;
		window.location.href = encodeURI(encodeURI(url));
	}

	function reset() {
		$("#search_con input[type='text']").val("");
	}
</script>
</body>
</html>
