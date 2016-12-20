<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<head>
<jsp:include page="/index_head.jsp"></jsp:include>
	<script type="text/javascript">
     $(function(){
	  laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    skip: true, //是否开启跳页
		    groups: "${pages}">=3?3:"${pages}", //连续显示分页数
    		total: "${total}",
		    startRow: "${startRow}",
		    endRow : "${endRow}",
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
		        var page = location.search.match(/page=(\d+)/);
		        return page ? page[1] : 1;
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		        	var condition = "${oldCondition}";
		            location.href = "${pageContext.request.contextPath}/index/solrSearch.html?page="+e.curr+"&condition="+condition;
		        }
		    }
		});
});
</script>
</head>

<body>
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		  <li><a href="${pageContext.request.contextPath}/index/selectIndexNews.html"> 首页</a></li><li><a href="#">全文搜索信息</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
  <div class="container content job-content ">
   <div class="f18">共查到关于<span class="searchFont">${oldCondition}</span>的信息${solrMap['tdsTotal']}条</div>
    <div class="col-md-12 col-sm-12 col-xs-12 border1 p20_20">
       <h2 class="col-md-12 col-sm-12 col-xs-12 bg7 h35">
      		<div class="col-md-6 col-xs-6 col-sm-5 tc f16">标题</div>
            <div class="fr mr25 f16">发布时间</div>
       </h2>
            <ul class="categories li_square col-md-12 col-sm-12 col-xs-12 p0">
            <c:choose>
	            <c:when test="${solrMap['indexList']==null}">
	              <li class="tc">暂无数据</li>
	            </c:when>
            <c:otherwise>
	        <c:forEach items="${solrMap['indexList']}" var="i">
              <li>
               <a href="${pageContext.request.contextPath}/index/selectArticleNewsById.html?id=${i.id}" target="_self"><span class="f18 mr5">·</span>${i.title }</a>
               <span class="hex pull-right"><fmt:formatDate value='${i.publishtime}' pattern="yyyy年MM月dd日 " /></span>
              </li> 
            </c:forEach>  
            </c:otherwise> 
            </c:choose> 
            </ul>
	     <div id="pagediv" align="right"></div></div>
        </div>
<!--底部代码开始-->
<jsp:include page="/index_bottom.jsp"></jsp:include>
</body>
</html>
