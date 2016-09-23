<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>评论管理</title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="${ pageContext.request.contextPath }/public/layer/layer.js"></script>
	<script type="text/javascript" src="${ pageContext.request.contextPath }/public/layer/extend/layer.ext.js"></script>
	<link href="${ pageContext.request.contextPath }/public/layer/skin/layer.css" rel="stylesheet" type="text/css" />
	<link href="${ pageContext.request.contextPath }/public/layer/skin/layer.ext.css" rel="stylesheet" type="text/css" />
	<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
  <script type="text/javascript">
  $(function(){
	  laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${list.pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    skip: true, //是否开启跳页
		    groups: "${list.pages}">=3?3:"${list.pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
		        var page = location.search.match(/page=(\d+)/);
		        return page ? page[1] : 1;
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		            location.href = '<%=basePath%>reply/getlist.do?page='+e.curr;
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
  		window.location.href="<%=basePath%>reply/view.html?id="+id;
  	}
  	
    function edit(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			
			window.location.href="<%=basePath%>reply/edit.html?id="+id;
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选择需要修改的回复",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    
    function del(){
    	var id =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val()); 
		}); 
		if(id.length>0){
			layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				layer.close(index);
				window.location.href="<%=basePath%>reply/delete.html?id="+id;
			});
		}else{
			layer.alert("请选择要删除的回复",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    
    function add(){
    	window.location.href="<%=basePath%>reply/add.html";
    }
    
	//鼠标移动显示全部内容
	function out(content){
	if(content.length>10){
	layer.msg(content, {
			icon:6,
			shade:false,
			area: ['600px'],
			time : 1000    //默认消息框不关闭
		});//去掉msg图标
	}else{
		layer.closeAll();//关闭消息框
	}
}
  </script>
  </head>
  
  <body>
	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">信息服务</a></li><li><a href="#">论坛管理</a></li><li class="active"><a href="#">主题管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
   <div class="headline-v2">
      <h2>查询条件</h2>
   </div>

<!-- 项目戳开始 -->
  <div class="container clear">
   <h2 class="padding-10 border1 m0_30">
     <ul class="demand_list list-unstyled">
       <li class="fl">
       <label class="fl mt10">内容：</label>
       <span><input type="text" id="condition" class="mb0 mt5"/></span>
       </li>
        
       <li class="fl">
         <label class="fl mt10 ml10">所属板块：</label>
           <span>
           <select class="w220">
               <option></option>
           </select>
           </span>
       </li>
         <button class="btn btn_back fl ml10 mt8" onclick="search()">查询</button>
         <button class="btn btn_back fl ml10 mt8" onclick="reset()">重置</button>
     </ul>
     <div class="clear"></div>
   </h2>
  </div>
	   <div class="headline-v2">
	   		<h2>回复管理</h2>
	   </div>

<!-- 表格开始-->
   <div class="container">
   <div class="col-md-8">
	<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
	<button class="btn btn-windows delete" type="button" onclick="del();">删除</button>
	</div>
    </div>
   
   <div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
   	<table class="table table-bordered table-condensed">
		<thead>
			<tr>
				<th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
			    <th class="info" >序号</th>
				<th class="info">回复内容</th>
				<th class="info">发布时间</th>
				<th class="info">发表人</th>
				<th class="info">所属帖子名称</th>
				<th class="info">所属回复内容</th>
			</tr>
		</thead>
		
		<c:forEach items="${list.list}" var="reply" varStatus="vs">
			<tr>
				<td class="tc pointer"><input onclick="check()" type="checkbox" name="chkItem" value="${reply.id}" /></td>
				<td class="tc pointer" onclick="view('${reply.id}')">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>		
				<c:set value="${reply.content}" var="content"></c:set>
				<c:set value="${fn:length(content)}" var="length"></c:set>
				<c:if test="${length>10}">
					<td onclick="view('${reply.id}')" onmouseover="out('${reply.content}')" class="tc pointer ">${fn:substring(content,0,10)}...</td>
				</c:if>
				<c:if test="${length<10}">
					<td onclick="view('${reply.id}')" onmouseover="out('${reply.content}')" class="tc pointer ">${content } </td>
				</c:if>	
				
				<td class="tc pointer" onclick="view('${reply.id}')"><fmt:formatDate value='${reply.publishedAt}' pattern="yyyy-MM-dd  HH:mm:ss" /></td>
				<td class="tc pointer" onclick="view('${reply.id}')">${reply.user.relName}</td>
				<c:set value="${reply.post.name}" var="postContent"></c:set>
				<c:set value="${fn:length(postContent)}" var="length"></c:set>
                <c:if test="${length>8}">
                    <td onclick="view('${reply.id}')" class="tc pointer ">${fn:substring(postContent,0,8)}...</td>
                </c:if>
                <c:if test="${length<8}">
                    <td onclick="view('${reply.id}')" class="tc pointer ">${postContent } </td>
                </c:if> 
          
                <c:set value="${reply.reply.content}" var="replyContent"></c:set>
                <c:set value="${fn:length(replyContent)}" var="length"></c:set>
                <c:if test="${length>8}">
                    <td onclick="view('${reply.id}')" class="tc pointer ">${fn:substring(replyContent,0,8)}...</td>
                </c:if>
                <c:if test="${length<8}">
                    <td onclick="view('${reply.id}')" class="tc pointer ">${replyContent } </td>
                </c:if> 
				
			</tr>
		</c:forEach>
	</table>
     </div>
   <div id="pagediv" align="right"></div>
   </div>
   </div>
  </body>
</html>

