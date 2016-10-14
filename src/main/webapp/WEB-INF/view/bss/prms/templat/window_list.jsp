<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>模版管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	
  </head>
  <script src="<%=basePath%>public/layer/layer.js"></script>
   <script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
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
		    groups: "${list.pages}">=3?3:"${list.pages}",
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
//	 			        var page = location.search.match(/page=(\d+)/);
//	 			        return page ? page[1] : 1;
			return "${list.pageNum}";
			}(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		        	$("#page").val(e.curr);
		        	$("#form1").submit();
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
	function clearSearch(){
		$("#name").attr("value","");
	}
	//关联事件
    function guanlian(){
    	var ids=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length==1){
			var projectId = parent.$("#projectId").val();
			 var index=parent.layer.getFrameIndex(window.name);
				$.ajax({
					url:'<%=basePath%>firstAudit/relate.html?id='+ids+'&projectId='+projectId,
					success:function(){
						parent.location.reload();
					},
					error:function(){
						layer.msg("关联失败",{offset: ['222px', '390px']});
					}
				});
		}else{
			layer.alert("请选择要关联的模板,只能选择一个。",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    function cancel(){
	    var index=parent.layer.getFrameIndex(window.name);
	    parent.layer.close(index);
	    
	}
   
    function cancel(){
	    var index=parent.layer.getFrameIndex(window.name);
	    parent.layer.close(index);
	    
	}
    function palte(){
    	$("#a").attr("target","_blank");
    	parent.location.href="<%=basePath %>auditTemplat/list.html";
    }
  </script>
  <body>
   <div class="container">
	   <div class="headline-v2">
	   		<h2>模板查询</h2>
	   </div>
   </div>
    <!-- 查询 -->
   <div class="container clear margin-top-0">
   <div class="padding-10 border1 m0_30 tc">
   	<form action="<%=basePath %>firstAudit/toTemplatList.html" id="form1" method="post" enctype="multipart/form-data" class="mb0" >
	 <ul class="demand_list">
	  
	   <li class="fl mr15"><label class="fl mt5">模板名称：</label><span><input type="text" id="name" name="name" value="${name }" class="mb0"/></span></li>
	  </li>
	  
	   	 <button class="btn fl ml20 mt1" type="submit">查询</button>
	   	 <input class="btn fl ml20 mt1" id="button" onclick="clearSearch();" value="重置" type="reset">
	 </ul>

	 <div class="clear"></div>
	 <input type="hidden" name="page" id="page">
	 </form>
   </div>
  </div>
   
   <div class="container">
	   <div class="headline-v2">
	   		<h2>模版选择</h2>
	   </div>
   </div>
<!-- 表格开始-->
   <div class="container">
   <div class="padding-left-25 padding-right-25">
   <div class="col-md-8">
   <!-- <a target="_blank" id="a" class="btn btn-windows"onclick="palte();" href="javascript:void(0)">模板管理</a> -->
   <button class="btn btn-windows ht_add" type="button" onclick="guanlian();">关联</button>
     <button class="btn btn-windows cancel" type="button" onclick="cancel();">取消</button>
	</div>	
       </div>
    </div>
   
  <!--  <button type="button" class="btn fl ml20 mt1" onclick="palte();"></button> -->
   <div class="container">
     <div class="content padding-left-25 padding-right-25 padding-top-0">
       <div class="col-md-12">
        
         
         <br/>
        <table class="table table-bordered table-condensed">
		<thead>
		<tr>
		  <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
		  <th class="info w50">序号</th>
		  <th class="info">模板类型</th>
		  <th class="info">模板名称</th>
		  <th class="info">是否公开</th>
		  <th class="info">创建日期</th>
		  <th class="info">修改日期</th>
		</tr>
		</thead>
		<c:forEach items="${list.list}" var="templet" varStatus="vs">
			<tr>
				
				<td class="tc opinter"><input onclick="check()" type="checkbox" name="chkItem" value="${templet.id}" /></td>
				
				<td class="tc opinter" >${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
				
				<td class="tc opinter" >${templet.kind}</td>
				
				<td class="tc opinter" >${templet.name}</td>
				<c:if test="${templet.isOpen eq '0'}">
					<td class="tc opinter" >公开</td>
				</c:if>
				<c:if test="${templet.isOpen eq '1'}">
					<td class="tc opinter" >私有</td>
				</c:if>
				
			
			
				<td class="tc opinter" ><fmt:formatDate value='${templet.createdAt}' pattern="yyyy-MM-dd" /></td>
			
				<td class="tc opinter" ><fmt:formatDate value='${templet.updatedAt}' pattern="yyyy-MM-dd " /></td>
			</tr>
		</c:forEach>
        </table>
     </div>
   	<div id="pagediv" align="right"></div>
   </div>
  </body>
</html>
