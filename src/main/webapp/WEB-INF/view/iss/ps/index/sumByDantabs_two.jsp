<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<head>
<jsp:include page="/index_head.jsp"></jsp:include>
<script type="text/javascript">
var id = "${id}";
var id2 = "${id2}";
var id3 = "${id3}";
var id4 = "${id4}";
var title ="${title}";
$(function(){
	laypage({
	    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
	    pages: "${list.pages}", //总页数
	    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
	    skip: true, //是否开启跳页
	    total: "${list.total}",
	    startRow: "${list.startRow}",
	    endRow: "${list.endRow}",
	    groups: "${list.pages}">=3?3:"${list.pages}", //连续显示分页数
	    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
	        var page = location.search.match(/page=(\d+)/);
	        return page ? page[1] : 1;
	    }(), 
	    jump: function(e, first){ //触发分页后的回调
	        if(!first){ //一定要加此判断，否则初始时会无限刷新
	      		window.location.href="${pageContext.request.contextPath}/index/selectAllByDanTabs.html?page="+e.curr
	      				+"&id="+id+"&id2="+id2+"&id3="+id3+"&id4="+id4+"&title="+title;
	        }
	    }
	});
});

function query(){
	var title = $("#title").val();
	window.location.href="${pageContext.request.contextPath}/index/selectAllByDanTabs.html?id="+id+"&id2="+id2+"&id3="+id3+"&id4="+id4+"&title="+title;
}
</script>
</head>

<body>
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="${pageContext.request.contextPath}/"> 首页</a></li><li><a href="javascript:void(0);">信息公告</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
  <div class="container job-content ">
  <div class="search_box col-md-12 col-sm-12 col-xs-12">
        <input name="title" type="text" id="title" value="${title}"/>
       	<button type="button" onclick="query()" class="btn btn-u-light-grey">查询</button>
      </div>
          <div class="col-md-12 col-sm-12 col-xs-12 border1 p20_20">
            <h2 class="col-md-12 col-sm-12 col-xs-12 bg7 h35">
          		<div class="col-md-6 col-xs-6 col-sm-5 tc f16">标题</div>
                   <div class="fr mr25 f16">发布时间</div>
             </h2>
                <ul class="categories li_square col-md-12 col-sm-12 col-xs-12 p0 list_new">
                <c:forEach items="${indexList}" var="i">
	                  <%--<li>
	                   <a href="${pageContext.request.contextPath}/index/selectArticleNewsById.do?id=${i.id}" title="${i.name }" target="_self"><span class="f18 mr5">·</span>${i.name }</a>
	                   <span class="hex pull-right"><fmt:formatDate value='${i.publishedAt}' pattern="yyyy年MM月dd日 " /></span>
	                  </li> 
	                  --%><c:set value="${i.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>50}">
						<li>
						<a href="${pageContext.request.contextPath}/index/selectArticleNewsById.do?id=${i.id}" title="${i.name }" target="_self" class="col-md-10 col-sm-7 col-xs-12"><span class="f18 mr5 fl">·</span>${fn:substring(name,0,50)}...</a>
	                    <span class="hex pull-right col-md-2 col-sm-5 col-xs-12"><fmt:formatDate value='${i.publishedAt}' pattern="yyyy年MM月dd日 " /></span>
	                    </li>
					</c:if>
					<c:if test="${length<=50}">
					   <li>
					   <a href="${pageContext.request.contextPath}/index/selectArticleNewsById.do?id=${i.id}" title="${i.name }" target="_self" class="col-md-10 col-sm-7 col-xs-12"><span class="f18 mr5 fl">·</span>${i.name }</a>
	                   <span class="hex pull-right col-md-2 col-sm-5 col-xs-12"><fmt:formatDate value='${i.publishedAt}' pattern="yyyy年MM月dd日 " /></span>
	                   </li>
					</c:if>
	                </c:forEach>         
                </ul>
	     <%--<div class="fenye">
           <div class="page_box fr">
	         <span class="pre_page page">上一页</span><span class="curr_page page">1</span><span class="page">2</span><span class="page">3</span><span class="page">4</span><span class="page">5</span><span class="page">6</span><span class="next_page page">下一页</span><span class="ml15">到</span><input type="text" class="page_input" value="1">页 <input type="submit" class="ml10 search_page" value="确 定">
	       </div>
	     </div>
        --%>
        <div id="pagediv" align="right"></div></div>
	  </div>
<!--底部代码开始-->
<jsp:include page="/index_bottom.jsp"></jsp:include>
</body>
</html>
