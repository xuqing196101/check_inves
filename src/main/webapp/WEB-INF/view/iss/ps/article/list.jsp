<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>信息发布</title>
    
    
<script type="text/javascript">
	$(function(){
		  laypage({
			    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
			    pages: "${list.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    skip: true, //是否开启跳页
			    total:"${list.total}",
			    startRow:"${list.startRow}",
			    endRow:"${list.endRow}",
			    groups: "${list.pages}">=5?5:"${list.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			        var page = location.search.match(/page=(\d+)/);
			        return page ? page[1] : 1;
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			            location.href = '${ pageContext.request.contextPath }/article/getAll.html?page='+e.curr;
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
		window.location.href="${ pageContext.request.contextPath }/article/view.html?id="+id;
	}
	
	function add(){
		window.location.href="${ pageContext.request.contextPath }/article/add.html";
	}
	
	function find(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			
			window.location.href="${ pageContext.request.contextPath }/article/view.html?id="+id;
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选择需要查看的信息",{offset: ['222px', '390px'], shade:0.01});
		}
    }
	
	function edit(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			window.location.href="${ pageContext.request.contextPath }/article/edit.html?id="+id;
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选择需要修改的信息",{offset: ['222px', '390px'], shade:0.01});
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
				window.location.href="${ pageContext.request.contextPath }/article/delete.html?ids="+ids;
			});
		}else{
			layer.alert("请选择要删除的信息",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    
    
    function audit(){
    	window.location.href="${pageContext.request.contextPath }/article/auditlist.html?status=1";
    }
    
    function sub(){
    	var ids =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length>0){
			layer.confirm('您确定要提交吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				layer.close(index);
				window.location.href="${ pageContext.request.contextPath }/article/sumbit.html?ids="+ids;
			});
		}else{
			layer.alert("请选择要提交的信息",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    
    function search(){
	    var kname = $("#kname").val();
	    var parkId = $("#parkId  option:selected").val();
	    location.href = "${ pageContext.request.contextPath }/article/serch.html?kname="+kname;

	 }

	 function reset(){
		 $("#kname").val("");
	 }
</script>

  </head>
  
  <body>

	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">信息管理</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
   
   <div class="container">
	   <div class="headline-v2">
	   		<h2>信息发布</h2>
	   </div>
   
   <h2 class="search_detail">
   		<ul class="demand_list">
    	  <li>
	    	<label class="fl">信息标题：</label>
	    	<span>
	    		<input type="text" id="kname" name="kname" value="${name }"/>
	    	</span>
	      </li>
	    	<button onclick="search()" class="btn">查询</button>
	    	<button onclick="reset()" class="btn">重置</button>  	
    	</ul>
    	  <div class="clear"></div>
     </h2>
  
	   <input type="hidden" id="depid" name="depid">
	  	
		
		<div class="col-md-12 pl20 mt10">
	   		<button class="btn btn-windows add" type="button" onclick="add()">新增</button>
			<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
			<button class="btn btn-windows delete" type="button" onclick="del()">删除</button>
			<button class="btn btn-windows git" type="button" onclick="sub()">提交</button>
		</div>

			

	<div class="content table_box">
		  <table class="table table-bordered table-condensed table-hover table-striped">
		  	<thead>
	  			<tr>
	  				<th class="info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
	  				<th class="info">序号</th>
	  				<th class="info">信息标题</th>
	  				<th class="info">发布范围</th>
	  				<th class="info">发布时间</th>
	  				<th class="info">信息类型</th>
	  				<th class="info">是否发布</th>
	  				<th class="info">浏览量</th>
	  				<th class="info">下载量</th>
	  			</tr>
	  		</thead>
	  		<c:forEach items="${list.list}" var="article" varStatus="vs">
		  		<tr class="pointer">
		  			<td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${article.id }" /></td>
		  			<td class="tc">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
		  			<td class="tc" onclick="view('${article.id }')">${article.name }</td>
		  			<td class="tc" onclick="view('${article.id }')">
		  				<c:if test="${article.range=='0' }">
		  					内网
		  				</c:if>
		  				<c:if test="${article.range=='1' }">
		  					外网
		  				</c:if>
		  				<c:if test="${article.range=='2' }">
		  					内网/外网
		  				</c:if>
		  			</td>
		  			<td class="tc" onclick="view('${article.id }')">
		  				<fmt:formatDate value='${article.publishedAt }' pattern="yyyy年MM月dd日   HH:mm:ss" />
		  			</td>
		  			<td class="tc" onclick="view('${article.id }')">${article.articleType.name }</td>
		  			<td class="tc">
		  				<c:if test="${article.status=='0' }">
		  					<input type="hidden" name="status" value="${article.status }">暂存
		  				</c:if>
		  				<c:if test="${article.status=='1' }">
		  					<input type="hidden" name="status" value="${article.status }">已提交
		  				</c:if>
		  				<c:if test="${article.status=='2' }">
		  					<input type="hidden" name="status" value="${article.status }">发布
		  				</c:if>
		  				<c:if test="${article.status=='3' }">
		  					<input type="hidden" name="status" value="${article.status }">审核未通过
		  				</c:if>
		  			</td>
		  			<td class="tc">${article.showCount }</td>
		  			<td class="tc"><a href="${ pageContext.request.contextPath }/downloadUser/selectDownloadUserByArticleId.html?articleId=${article.id}">${article.downloadCount }</a></td>
		  		</tr>
	  		</c:forEach>
		  </table>
	  	</div>  
	  	<div id="pagediv" align="right"></div>
    </div>
   </div>
  </body>
</html>
