<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
      <jsp:include page="/index_head.jsp"></jsp:include>
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
					      		var name = "${category.name}";
					      		location.href = "${pageContext.request.contextPath }/catalog/catalogList.do?name=" + name + "&page=" + e.curr;
					        }
					    }
					});
				});
				
				
				function query(){
					var name = $("#name").val();
					window.location.href="${pageContext.request.contextPath}/catalog/catalogList.html?name="+name;
				}
		</script>
  </head>
  
  <body>
    <div class="margin-top-10 breadcrumbs">
	      <div class="container">
			   	<ul class="breadcrumb margin-left-0">
			   		<li><a href="${pageContext.request.contextPath}/index/selectIndexNews.html">首页</a></li><li><a href="#">产品目录</a></li>
			   	</ul>
					<div class="clear"></div>
		  	</div>
	   	</div>
	   	<div class="container job-content ">
		  		<div class="search_box col-md-12 col-sm-12 col-xs-12">
		         	<input name="name" type="text" id="name" value="${category.name }"/>
		        	<button type="button" onclick="query()" class="btn btn-u-light-grey">查询</button>
		 </div>
		 
		 <div class="content table_box">
				<table class="table table-bordered table-condensed table-hover">
					<thead>
						<tr class="info">
							<th class="w50">序号</th>
							<th class="w180">品目名称</th>
							<th class="w180">编码</th>
							<th class="w180">通用资质要求</th>
							<th class="w110">图片</th>
							<th class="w300">描述</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list.list }" var="data" varStatus="vs">
							<tr class="pointer">
								
								<td class="tc" >${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
								<c:if test="${fn:length(data.name)>22}">
									<td class="tl pl20" title="${data.name }">${fn:substring(data.name,0,22)}...</td>
								</c:if>
								<c:if test="${fn:length(data.name)<=22}">
									<td class="tl pl20">${data.name }</td>
								</c:if>
								<td class="tl pl20" >
									${data.code }
								</td>
								<td class="tc"  title="${data.generalQuaNames}">
									<c:if test="${fn:length(data.generalQuaNames)<=13}">
										${data.generalQuaNames}
									</c:if>
									<c:if test="${fn:length(data.generalQuaNames)>13}">
									    ${fn:substring(data.generalQuaNames,0,13)}...
									</c:if>
								</td>
									<td class="tl pl20" ></td>
									<td class="tl pl20"  title="${data.description}">
										<c:if test="${fn:length(data.description)<=20}">
											${data.description}
										</c:if>
										<c:if test="${fn:length(data.description)>20}">
										    ${fn:substring(data.description,0,20)}...
										</c:if>
									</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="pagediv" align="right"></div>
		 
        </div>
    
		<jsp:include page="/index_bottom.jsp"></jsp:include>
  </body>
</html>
