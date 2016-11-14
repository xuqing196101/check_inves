<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
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
<script type="text/javascript">
	 $(function(){
		  laypage({
			    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
			    pages: "${seList.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    skip: true, //是否开启跳页
			    total: "${seList.total}",
			    startRow: "${seList.startRow}",
			    endRow: "${seList.endRow}",
			    groups: "${seList.pages}">=5?5:"${seList.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			        var page = location.search.match(/page=(\d+)/);
			        return page ? page[1] : 1;
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			             location.href = '${pageContext.request.contextPath}/supplier_edit/list.do?page='+e.curr;
			        }
			    }
			});
	  });
		function check(){
		 var count=0;
		 var checklist = document.getElementsByName ("chkItem");
		 var checkAll = document.getElementById("checkAll");
		 for(var i=0;i<checklist.length;i++){
			   if(checklist[i].checked == false){
				   checkAll.checked = false;
				   break;
			   }
			   for(var j=0;j<checklist.length;j++){
					 if(checklist[j].checked == true){
						   checkAll.checked = true;
						   count++;
					   }
				 }
		   }
	}
		function selectAll(){
		 var checklist = document.getElementsByName ("chkItem");
		 var checkAll = document.getElementById("checkAll");
		   if(checkAll.checked){
			   for(var i=0;i<checklist.length;i++)
			   {
			      checklist[i].checked = true;
			   } 
			 }else{
			  for(var j=0;j<checklist.length;j++)
			  {
			     checklist[j].checked = false;
			  }
		 	}
		}
  	function show(id){
  		window.location.href="${pageContext.request.contextPath}/supplier_edit/view.html?id="+id;
  	}
  	function add(){
  		 window.location.href="${pageContext.request.contextPath}/supplier_edit/add.html?id="+'${id}'; 
  	}
</script>
</head>
<body>
   <div class="margin-top-10 breadcrumbs">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">供应商管理</a></li><li><a href="#">供应商信息变更记录</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
		<div class="container clear margin-top-30">
		    <button class="btn btn-windows add" type="button" onclick="add()">变更</button>
		  <table id="tb1"  class="table table-striped table-bordered table-hover tc">
		      <thead>
				<tr>
					<th class="info w50 tc">序号</th>
					<th class="info">供应商名称</th>
					<th class="info">变更时间</th>
					<th class="info">变更状态</th>
				</tr>
			  </thead>
			  <tbody>
				 <c:forEach items="${seList.list }" var="se" varStatus="vs">
					<tr>
					    <td>${(vs.index+1)+(seList.pageNum-1)*(seList.pageSize)}</td>
						<td><a onclick="show('${se.id}')" class="pointer">${se.supplierName }</a></td>
						<td><fmt:formatDate value="${se.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>
							<c:if test="${se.status==0 }">
								未审核
							</c:if>
							<c:if test="${se.status==1}">
								审核通过
							</c:if>
							<c:if test="${se.status==2 }">
								审核退回
							</c:if>
						</td>
					</tr>
				</c:forEach> 
			  </tbody>
		 </table>
			<div id="pagediv" align="right"></div>
		 </div>		 
</body>
</html>
