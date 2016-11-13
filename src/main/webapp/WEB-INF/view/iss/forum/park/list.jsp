<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>版块管理</title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="${ pageContext.request.contextPath }/public/ZHH/js/jquery.min.js" type="text/javascript"></script>
	 <script src="${ pageContext.request.contextPath }/public/layer/layer.js"></script>
	 <script src="${ pageContext.request.contextPath }/public/laypage-v1.3/laypage/laypage.js"></script>
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
		        return page ? page[1] : 1;
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		            location.href = '${ pageContext.request.contextPath }/park/getlist.do?page='+e.curr;
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
  		window.location.href="${ pageContext.request.contextPath }/park/view.html?id="+id;
  	}
  	
    function edit(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			
			window.location.href="${ pageContext.request.contextPath }/park/edit.html?id="+id;
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选择需要修改的版块",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    
    function del(){
    	var ids =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length>0){
			layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				layer.close(index);
				window.location.href="${ pageContext.request.contextPath }/park/delete.html?ids="+ids;
			});
		}else{
			layer.alert("请选择要删除的版块",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    
    function add(){
    	window.location.href="${ pageContext.request.contextPath }/park/add.html";
    }
    
	//鼠标移动显示全部内容
	function out(content){
	if(content.length >= 10){
	layer.msg(content, {
		    skin: 'demo-class',
			shade:false,
			closeBtn : [0 , true],
			area: ['600px'],
			time : 4000    //默认消息框不关闭
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
		   <li><a href="#"> 首页</a></li><li><a>信息服务</a></li><li><a >论坛管理</a></li><li class="active"><a >版块管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
<div class="container">
	<div class="headline-v2">
	   <h2>版块管理</h2>
	</div>

<!-- 表格开始-->

   <div class="col-md-12 pl20 mt10">
    <button class="btn btn-windows add" type="button" onclick="add()">新增</button>
	<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
	<button class="btn btn-windows delete" type="button" onclick="del();">删除</button>
	</div>
    </div>
   
   <div class="container">
     <div class="content table_box">
   	<table class=" table table-condensed table-bordered table-hover table-striped">
    
		<thead>
			<tr>
				<th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
			    <th class="info" >序号</th>
				<th class="info" >版块名</th>
				<th class="info">版块介绍</th>
			    <th class="info">版主</th>
			    <th class="info">热门</th>
				<th class="info">创建人</th>
				<th class="info">主题数</th>
				<th class="info">帖子数</th>
				<th class="info">回复数</th>
			</tr>
		</thead>
		
		<c:forEach items="${list.list}" var="park" varStatus="vs">
			<tr>
				<td class="tc pointer"><input onclick="check()" type="checkbox" name="chkItem" value="${park.id}" /></td>
				<td class="tc pointer" onclick="view('${park.id}')">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
				<td class="tc pointer" onclick="view('${park.id}')">${park.name}</td>
				
				<c:set value="${park.content}" var="content"></c:set>
				<c:set value="${fn:length(content)}" var="length"></c:set>
				<c:if test="${length>10}">
					<td onclick="view('${park.id}')" onmouseover="out('${park.content}')" class="tc pointer ">${fn:substring(content,0,10)}...</td>
				</c:if>
				<c:if test="${length<=10}">
					<td onclick="view('${park.id}')" onmouseover="out('${park.content}')" class="tc pointer ">${content } </td>
				</c:if>	
				<td class="tc pointer" onclick="view('${park.id}')">${park.user.relName}</td>
				<td class="tc pointer" onclick="view('${park.id}')">${park.isHot}</td>
				<td class="tc pointer" onclick="view('${park.id}')">${park.creater.relName}</td>
				<td class="tc pointer" onclick="view('${park.id}')">${park.topiccount }</td>
				<td class="tc pointer" onclick="view('${park.id}')">${park.postcount }</td>
				<td class="tc pointer" onclick="view('${park.id}')">${park.replycount }</td>
			</tr>
		</c:forEach>
	</table>
     </div>
   	<div id="pagediv" align="right"></div>
   </div>

	 </body>
</html>
