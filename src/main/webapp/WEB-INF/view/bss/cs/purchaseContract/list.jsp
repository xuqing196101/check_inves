<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>采购合同管理</title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="<%=basePath%>public/ZHH/js/jquery.min.js" type="text/javascript"></script>
	  <script src="<%=basePath%>public/layer/layer.js"></script>
	  <script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
  <script type="text/javascript">
  $(function(){
	  laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    skip: true, //是否开启跳页
		    groups: "${pages}">=3?3:"${pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
		        var page = location.search.match(/page=(\d+)/);
		        return page ? page[1] : 1;
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		        	var articleId = "${articleId}";
		        	var condition = "${userName}";
		            location.href = "<%=basePath%>downloadUser/selectDownloadUserByArticleId.html?page="+e.curr+"&articleId="+articleId+"&userName="+condition;
		        }
		    }
		});
  });
	/** 全选全不选 */
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
	
	/** 单选 */
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
	
  	function view(id){
  		window.location.href="<%=basePath%>articletype/view.html?id="+id;
  	}
    
  	function del(){
    	var ids =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length>0){
			layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				layer.close(index);
				window.location.href="<%=basePath%>downloadUser/deleteDownloadUser.html?ids="+ids;
			});
		}else{
			layer.alert("请选择要删除的信息",{offset: ['222px', '390px'], shade:0.01});
		}
    }
  	
  	function search(){
  		var condition = $("#condition").val();
  		var articleId = "${articleId}";
  		window.location.href="<%=basePath%>downloadUser/selectDownloadUserByArticleId.html?userName="+condition+"&articleId="+articleId;
  	}
  </script>
  </head>
  
  <body>

	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">采购合同管理</a></li><li><a href="#">采购合同列表</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
  <div class="container">
   <div class="headline-v2">
      <h2>查询条件</h2>
   </div>
<!-- 项目戳开始 -->
  <div class="container clear margin-top-30">
   <h2 class="padding-10 border1">
	 <ul class="demand_list list-unstyled">
	   <li class="fl ml8"><label class="fl mt10">采购项目名称：</label><span><input type="text" value="${userName}" id="condition" class="mb0 mt5"/></span></li>
	   <li class="fl ml8"><label class="fl mt10">编号：</label><span><input type="text" value="${userName}" id="condition" class="mb0 mt5"/></span></li>
	   <li class="fl ml8"><label class="fl mt10">采购机构：</label><span><input type="text" value="${userName}" id="condition" class="mb0 mt5"/></span></li>
	   	 <button class="btn btn_back fl ml10 mt8" onclick="search()">查询</button>
	 </ul>
	 <div class="clear"></div>
   </h2>
  </div>
   <div class="headline-v2 fl">
      <h2>采购合同列表
	  </h2>
   </div> 
   	  <span class="fr option_btn margin-top-20">
	    <button class="btn padding-left-10 padding-right-10 btn_back">生成合同</button>
	    <button class="btn padding-left-10 padding-right-10 btn_back">报批合同</button>
	    <button class="btn padding-left-10 padding-right-10 btn_back">编辑合同</button>
	    <button class="btn padding-left-10 padding-right-10 btn_back">查看合同</button>
	  </span>
   <div class="container clear margin-top-30">
   	<table class="table table-bordered table-condensed mt5">
		<thead>
			<tr>
				<th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
			    <th class="info w50">序号</th>
				<th class="info">采购项目名称</th>
				<th class="info">编号</th>
				<th class="info">包号</th>
				<th class="info">成交金额</th>
				<th class="info">成交供应商</th>
				<th class="info">采购机构</th>
			</tr>
		</thead>
		<%--<c:forEach items="${list}" var="downloadUser" varStatus="vs">
			<tr>
				<td class="tc pointer"><input onclick="check()" type="checkbox" name="chkItem" value="${downloadUser.id}" /></td>
				<td class="tc pointer">${(vs.index+1)+(page-1)*(pageSize)}</td>
				<td class="tc pointer">${downloadUser.article.name}</td>
				<td class="tc pointer"><fmt:formatDate value='${downloadUser.createdAt}' pattern="yyyy年MM月dd日  HH:mm:ss" /></td>
				<td class="tc pointer"><fmt:formatDate value='${downloadUser.updatedAt}' pattern="yyyy年MM月dd日  HH:mm:ss" /></td>
				<td class="tc pointer">${downloadUser.userName}</td>
			</tr>
		</c:forEach>
	--%></table>
     </div>
   <div id="pagediv" align="right"></div>
   </div>
</body>
</html>
