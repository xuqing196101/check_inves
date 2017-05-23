<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
	<%@ include file="/WEB-INF/view/common.jsp"%>
  <script type="text/javascript">
  $(function(){
	  laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    skip: true, //是否开启跳页
		    total: "${total}",
		    startRow: "${startRow}",
		    endRow : "${endRow}",
		    groups: "${pages}">=3?3:"${pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
		        var page = location.search.match(/page=(\d+)/);
		        return page ? page[1] : 1;
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		        	var articleId = "${articleId}";
		        	var condition = "${userName}";
		            location.href = "${pageContext.request.contextPath}/downloadUser/selectDownloadUserByArticleId.html?page="+e.curr+"&articleId="+articleId+"&userName="+condition;
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
  		window.location.href="${ pageContext.request.contextPath }/articletype/view.html?id="+id;
  	}
    
  	function del(){
    	var ids =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length>0){
			layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				layer.close(index);
				window.location.href="${pageContext.request.contextPath}/downloadUser/deleteDownloadUser.html?ids="+ids;
			});
		}else{
			layer.alert("请选择要删除的信息",{offset: ['222px', '390px'], shade:0.01});
		}
    }
  	
  	function search(){
  		var condition = $("#condition").val();
  		var articleId = "${articleId}";
  		window.location.href="${pageContext.request.contextPath}/downloadUser/selectDownloadUserByArticleId.html?userName="+condition+"&articleId="+articleId;
  	}
  	
  	function reset(){
  		$("#condition").val("");
  	}
  </script>
  </head>
  
  <body>

	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0);"> 首页</a></li><li><a href="javascript:void(0);">信息服务</a></li><li><a href="javascript:void(0);">下载人管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
   <div class="headline-v2">
      <h2>下载人列表
	  </h2>
   </div> 
	<!-- 项目戳开始 -->
     <h2 class="search_detail">
    	<ul class="demand_list">
	     <li class="fl"><label class="fl">下载人姓名：</label><span><input type="text" value="${userName}" id="condition" class=""/></span></li>
	   	 <button class="btn" onclick="search()">查询</button>
	   	 <button class="btn" onclick="reset()">重置</button>
	   </ul>
    	  <div class="clear"></div>
     </h2>
   <div class="col-md-12 pl20 mt10">
	  <button onclick="del()" class="btn btn-windows delete">删除</button>
   </div>  
     <div class="content table_box">
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
			<tr>
				<th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
			    <th class="info w50">序号</th>
				<th class="info">文章名</th>
				<th class="info">创建时间</th>
				<th class="info">更新时间</th>
				<th class="info">下载人</th>
			</tr>
		</thead>
		<c:forEach items="${list}" var="downloadUser" varStatus="vs">
			<tr>
				<td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${downloadUser.id}" /></td>
				<td class="tc">${(vs.index+1)+(page-1)*(pageSize)}</td>
				<td class="tc">${downloadUser.article.name}</td>
				<td class="tc"><fmt:formatDate value='${downloadUser.createdAt}' pattern="yyyy年MM月dd日  HH:mm:ss" /></td>
				<td class="tc"><fmt:formatDate value='${downloadUser.updatedAt}' pattern="yyyy年MM月dd日  HH:mm:ss" /></td>
				<td class="tc">${downloadUser.userName}</td>
			</tr>
		</c:forEach>
	</table>
     </div>
      <div id="pagediv" align="right"></div>
   </div>
</body>
</html>
