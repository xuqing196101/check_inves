<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/index_head.jsp"></jsp:include>
	<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
	<script type="text/javascript">
	$(function(){
		laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${list.pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    skip: true, //是否开启跳页
		    total: "${list.total}",
		    startRow: "${list.startRow}",
		    endRow: "${list.endRow}",
		    groups: "${list.pages}">=5?5:"${list.pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
		    	var page = location.search.match(/page=(\d+)/);
			      if(page == null) {
				      page = {};
				      page[0] = "${list.pageNum}";
				      page[1] = "${list.pageNum}";
			      }
			      return page ? page[1] : 1;
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		      		var name = "${data.name}";
		      		location.href = "${pageContext.request.contextPath }/templateDownload/getTemplateList.do?name=" + name + "&page=" + e.curr;
		        }
		    }
		});
	});
	
	//查询
	function query(){
		var name = $("#name").val();
		window.location.href="${pageContext.request.contextPath}/templateDownload/getIndexList.html?name="+name;
	}
	</script>
</head>

<body>
	  	<!--面包屑导航开始-->
	   	<div class="margin-top-10 breadcrumbs">
	      <div class="container">
			   	<ul class="breadcrumb mb0">
			   		<li><a href="${pageContext.request.contextPath}/index/selectIndexNews.html">首页</a></li><li><a href="javascript:void(0);">采购模板下载</a></li>
			   	</ul>
					<div class="clear"></div>
		  	</div>
	   	</div>
		  <div class="container job-content ">
		  		<div class="search_box col-md-12 col-sm-12 col-xs-12">
		         	<input name="name" type="text" id="name" value="${data.name }"/>
		        	<button type="button" onclick="query()" class="btn btn-u-light-grey">查询</button>
		         </div>
          <div class="report_list_box">
            <div class="col-md-12 col-sm-12 col-xs-12 report_list_title">
          		<div class="col-md-7 col-xs-6 col-sm-6 tc f16 p0">采购模板名称</div>
              	<div class="col-md-3 col-sm-3 col-xs-4 tc f16 p0">发布时间</div>
          		<div class="col-md-2 col-sm-3 col-xs-2 tc f16 p0">下载</div>
             </div>
             <ul class="categories li_square col-md-12 col-sm-12 col-xs-12 p0">
                <c:choose>
                	<c:when test="${notData==null }">
                		<c:forEach items="${list.list}" var="data">
		                  <li>
		                   <span class="col-md-7 col-sm-6 col-xs-12">·${data.name }</span>
		                 	<span class="col-md-3 col-sm-3 col-xs-6 tc p0"><fmt:formatDate value='${data.publishAt}' pattern="yyyy年MM月dd日 " /></span>
		                     <span class="col-md-2 col-sm-3 col-xs-6"><u:show showId="${data.groupShow }" groups="${data.groupsUploadId }" delete="false" businessId="${data.id }" sysKey="${sysKey}" typeId="${dataTypeId }" zipFileName="${data.name}" fileName="${data.name}"/></span>
		                  </li> 
		                </c:forEach> 
                	</c:when>
                	<c:otherwise>
                		<li class="tc">暂无数据</li>
                	</c:otherwise>
                </c:choose>
             </ul>
        	<div id="pagediv" align="right"></div>
        </div>
	  </div>
		<!--底部代码开始-->
		<jsp:include page="/index_bottom.jsp"></jsp:include>
</body>
</html>
