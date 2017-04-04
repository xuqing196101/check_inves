<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<head>
<jsp:include page="/index_head.jsp"></jsp:include>
<script type="text/javascript">
var id = "${id}";
var twoid = "${twoid}";
var title = "${title}";
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
	        	var relName = $("#relName").val();
	        	var status=$("#status").val();
	      		window.location.href="${pageContext.request.contextPath}/index/selectsumBynews.html?page="+e.curr+"&atc=1&relName="+relName+"&status="+status;
	        }
	    }
	});
});

function query(){
	var relName = $("#relName").val();
	var status=$("#status").val();
	//title = decodeURI(title);
	//alert(title);
	window.location.href="${pageContext.request.contextPath}/index/selectsumByDirectory.html?act=1&relName="+relName+"&status="+status;
}
</script>
</head>

<body>
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="${pageContext.request.contextPath}/"> 首页</a></li><li><a href="#">专家名录</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
  <div class="container job-content ">
  <div class="search_box col-md-12 col-sm-12 col-xs-12">
  			状态：<select name="status" id="status" >
  			 <option value="">全部</option>
  			 <c:choose>
           	 <c:when test="${!empty status && fn:contains('4,6,8',status)}">
           	 	<option value="4,6,8" selected="selected" >复审通过</option>
           	 </c:when>
           	  <c:otherwise>
				<option value="8" >复审通过</option>				       
			</c:otherwise>
           	 </c:choose>
  			 <c:choose>
           	 <c:when test="${'7' eq status}">
           	 	<option value="7" selected="selected" >复查通过</option>
           	 </c:when>
           	  <c:otherwise>
				<option value="7" >复查通过</option>				       
			</c:otherwise>
           	 </c:choose>
  			
  			</select>
         	专家名称：<input name="relName" type="text" id="relName" value="${relName }"/>
         	编号：<input name="code" type="text" id="code" value="${productType }"/>
        	<button type="button" onclick="query()" class="btn btn-u-light-grey">查询</button>
      </div>
          <div class="col-md-12 col-sm-12 col-xs-12 border1 p20_20">
           <c:choose>
           			   <c:when test="${!empty list.list}">
         				<table class="table table-bordered "  >
       					 <thead>
         					 <tr >
								<th class="tc info" width="55%">专家名称</th>
								<th class="tc info" width="25%">编号</th>
								<th class="tc info" width="20%">状态</th>
							 </tr>
						 </thead>
				     	 <tbody>
							<c:forEach items="${list.list}" var="item" varStatus="status" > 
								<tr>
									<td>${item.relName }</td>
									<td class="tc"></td>
									<td class="tc"> <c:choose>
          						      <%-- <c:when test="${item.status == 0}">
										          未审核
									    </c:when>
									     <c:when test="${item.status == 1}">
											初审通过
										 </c:when>
									     <c:when test="${item.status == 2}">
						       				 初审未通过
						   				 </c:when>
						   				 <c:when test="${item.status == 3}">
						       				 退回修改
						  				 </c:when>
						   				 <c:when test="${item.status == 4}">
						 					    待复审
						  			     </c:when> --%>
						  			     <c:when test="${item.status eq '4' or item.status == '6' or item.status == '8'}">
						     		  	   复审通过
						  			     </c:when>
						  			     <c:when test="${item.status eq '7'}">
						     			   复查通过
						 			     </c:when>
						   			    <c:otherwise>
								       </c:otherwise>
               					     </c:choose>
               				      </td>
								</tr>
							 </c:forEach> 
							</tbody>
						  </table>
                		</c:when>
                		<c:otherwise>
                			暂无数据
                		</c:otherwise>
               		</c:choose>   
        <div id="pagediv" align="right"></div></div>
	  </div>
<!--底部代码开始-->
<jsp:include page="/index_bottom.jsp"></jsp:include>
</body>
</html>
