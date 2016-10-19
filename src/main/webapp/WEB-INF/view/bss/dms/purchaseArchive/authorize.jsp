<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>采购档案授权</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="${ pageContext.request.contextPath }/public/layer/layer.js"></script>
	<script type="text/javascript" src="${ pageContext.request.contextPath }/public/layer/extend/layer.ext.js"></script>
	<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js" type="text/javascript"></script>
	<link href="${ pageContext.request.contextPath }/public/layer/skin/layer.css" rel="stylesheet" type="text/css" />
	<link href="${ pageContext.request.contextPath }/public/layer/skin/layer.ext.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
		$(function(){
			$("#name").val("${name}");
			$("#depName").val("${depName}");
			laypage({
			    cont: $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
			    pages: "${purchaseList.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    skip: true, //是否开启跳页
			    total: "${purchaseList.total}",
			    startRow: "${purchaseList.startRow}",
			    endRow: "${purchaseList.endRow}",
			    groups: "${purchaseList.pages}">=5?5:"${purchaseList.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			        var page = location.search.match(/page=(\d+)/);
			        return page ? page[1] : 1;
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			        	var name = "${name}";
			        	var depName = "${depName}";
			        	location.href = "<%=path%>/purchaseArchive/archiveAuthorize.do?name="+name+"&depName="+depName+"&page="+e.curr;
			        }
			    }
			});
		})
		
		//按条件查询
		function queryResult(){
			var name = $("#name").val();
			var depName = $("#depName").val();
			if((name==null||name=="")&&(depName==null||depName=="")){
				window.location.href = "<%=path%>/purchaseArchive/archiveAuthorize.html";
				return;
			}else{
				window.location.href = "<%=path%>/purchaseArchive/archiveAuthorize.do?name="+name+"&depName="+depName;
			}
		}
		
		//重置
		function resetResult(){
			$("#name").val("");
			$("#depName").val("");
		}
		
		//临时授权
		function auth(){
			
		}
		
		//全选方法
		function selectAll(){
			var info = document.getElementsByName("info");
			var selectAll = document.getElementById("selectAll");
			if(selectAll.checked){
				for(var i=0;i<info.length;i++){
					info[i].checked = true;
				}
			}else{
				for(var i=0;i<info.length;i++){
					info[i].checked = false;
				}
			}
		}
	</script>

  </head>
  
  <body>
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#">首页</a></li><li><a href="#">保障作业</a></li><li><a href="#">采购档案授权</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
	   <div class="headline-v2">
	   		<h2>查询条件</h2>
	   </div>
   </div>
   
   <div class="container">
  		<div class="border1 col-md-12 ml30">
	  		姓名:<input type="text" id="name" name="name" class="mt10"/>
	  		采购机构:<input type="text" id="depName" name="depName" class="mt10"/>
	  		<button class="btn" type="button" onclick="queryResult()">查询</button>
	  		<button class="btn" type="button" onclick="resetResult()">重置</button>
  		</div>
  	</div>
  	
  		<!-- 按钮开始-->
	   <div class="container">
	   		<div class="col-md-12">
			    <button class="btn btn-windows pl13" type="button" onclick="auth()">临时授权</button>
			</div>
	    </div>
  	
  	<div class="container">
  		<div class="content padding-left-25 padding-right-25 padding-top-5">
	  		<table class="table table-bordered table-condensed table-hover">
				<thead>
					<tr class="info">
						<th class="w50"><input type="checkbox" id="selectAll" onclick="selectAll()"/></th>
						<th>序号</th>
						<th>姓名</th>
						<th>采购机构</th>
						<th>联系电话</th>
						<th>联系地址</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${purchaseList.list }" var="purchase" varStatus="vs">
						<tr class="tc">
							<td class="tc"><input type="checkbox" name="info" value="${purchase.id }"/></td>
							<td>${(vs.index+1)+(purchaseList.pageNum-1)*(purchaseList.pageSize)}</td>
							<td>${purchase.relName }</td>
							<td>${purchase.purchaseDepName }</td>
							<td>${purchase.telephone }</td>
							<td>${purchase.address }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pageDiv" align="right"></div>
  	</div>
  </body>
</html>
