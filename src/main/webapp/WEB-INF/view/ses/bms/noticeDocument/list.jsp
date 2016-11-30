<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
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
		        	if("${noticeDocument.name}"!=null && "${noticeDocument.name}"!="" || ("${noticeDocument.docType}"!="-请选择-" && "${noticeDocument.docType}"!="")){
		        		location.href = '${ pageContext.request.contextPath }/noticeDocument/search.html?page='+e.curr+'&name='+"${noticeDocument.name}"+'&docType='+ "${noticeDocument.docType}";
		        	}else{
		            	location.href = '${ pageContext.request.contextPath }/noticeDocument/getAll.do?page='+e.curr;
		        	}
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
  		window.location.href="${ pageContext.request.contextPath }/noticeDocument/view.do?id="+id;
  	}
    function edit(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			
			window.location.href="${ pageContext.request.contextPath }/noticeDocument/edit.do?id="+id;
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选择需要修改的须知文档",{offset: ['222px', '390px'], shade:0.01});
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
				window.location.href="${ pageContext.request.contextPath }/noticeDocument/delete.do?ids="+ids;
			});
		}else{
			layer.alert("请选择要删除的须知文档",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    function add(){
    	window.location.href="${ pageContext.request.contextPath }/noticeDocument/add.do";
    }
    
    function search(){
	    var tname = $("#tname").val();
	    var docType = $("#searchType  option:selected").val();
	    location.href = "${ pageContext.request.contextPath }/noticeDocument/search.html?name="+tname+"&docType="+docType;

	 }

	 function reset(){
		 $("#tname").val("");
		 $("#tname").text("");
	 }
	 $(function(){
		 if(${noticeDocument.docType!=null}&&${noticeDocument.docType!=""}&&${noticeDocument.docType!="-请选择-"}){
			  $("#searchType").val("${noticeDocument.docType}");  
		  }else{
			  $("#searchType").val("-请选择-"); 
		  }
		 $("#tname").val('${noticeDocument.name}');
	 });
  </script>
  <body>
	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">支撑系统</a></li><li><a href="javascript:void(0)">后台管理</a></li><li class="active"><a href="javascript:void(0)">须知文档管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
   <div class="container">
	   <div class="headline-v2">
	   		<h2>须知文档查询</h2>
	   </div>

    <!-- 查询 -->
       <h2 class="search_detail">
       	<form action="${ pageContext.request.contextPath }/noticeDocument/search.html"
				method="post" enctype="multipart/form-data" class="mb0">
       	<ul class="demand_list">
    	  <li>
	    	<label class="fl">须知文档名称：</label>
	    	<span>
	    		<input type="text" id="tname" name="name" />
	    	</span>
	      </li>
	      <li>
	    	<label class="fl">须知文档类型：</label>
	    	<span>
	    		<select id="searchType" name =docType class="w150" >
					<option value="-请选择-">-请选择-</option>
			  	 	<option value="供应商须知文档">供应商须知文档</option>
			  	 	<option value="专家须知文档">专家须知文档</option>
	  			</select>
	  		</span>
	      </li>
	    	<button type="submit" class="btn">查询</button>
	    	<button type="reset" onclick="reset()" class="btn">重置</button>  	
    	</ul>
    	</form>
    	  <div class="clear"></div>
     </h2>
    
<!-- 表格开始-->
   
  <div class="col-md-12 pl20 mt10">
    <button class="btn btn-windows add" type="button" onclick="add()">新增</button>
	<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
	<button class="btn btn-windows delete" type="button" onclick="del();">删除</button>
  </div>	
   
 
       <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
		  <th class="info w50">序号</th>
		  <th class="info">须知文档类型</th>
		  <th class="info">须知文档名称</th>
		  <th class="info">创建日期</th>
		  <th class="info">修改日期</th>
		</tr>
		</thead>
		<c:forEach items="${list.list}" var="noticeDocument" varStatus="vs">
			<tr>
				
				<td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${noticeDocument.id}" /></td>
				
				<td class="tc pointer" onclick="view('${noticeDocument.id}')">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
				
				<td class="tc pointer" onclick="view('${noticeDocument.id}')">${noticeDocument.docType}</td>
				
				<td class="tc pointer" onclick="view('${noticeDocument.id}')">${noticeDocument.name}</td>
			
				<td class="tc pointer" onclick="view('${noticeDocument.id}')"><fmt:formatDate value='${noticeDocument.createdAt}' pattern="yyyy-MM-dd" /></td>
			
				<td class="tc pointer" onclick="view('${noticeDocument.id}')"><fmt:formatDate value='${noticeDocument.updatedAt}' pattern="yyyy-MM-dd " /></td>
			</tr>
		</c:forEach>
        </table>
     </div>
   	<div id="pagediv" align="right"></div>
   </div>
  </body>
</html>
