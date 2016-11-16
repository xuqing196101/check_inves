<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>产品参数待审</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<script src="${pageContext.request.contextPath}public/laypage-v1.3/laypage/laypage.js"></script>
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
		    groups: "${list.pages}">=3?3:"${list.pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
		        var page = location.search.match(/page=(\d+)/);
		        return page ? page[1] : 1;
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		            location.href = "${pageContext.request.contextPath}/categoryparam/search_category.html?page="+e.curr;
		        }
		    }
		});
  });
  function query(){
      var paramstatus =$("#paramstatus").val();
      window.location.href="${pageContext.request.contextPath}/categoryparam/search_category.html?paramstatus="+paramstatus;
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
	function audit(){
	
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			window.location.href="${pageContext.request.contextPath}/categoryparam/query_category.html?id="+id;
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选择需要审核的品目",{offset: ['222px', '390px'], shade:0.01});
		}
    }
	
</script>
</head>

<body>

	  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">产品参数管理</a></li><li><a href="#">审核列表</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>

   <div class="container">
   <div class="headline-v2">
     <h2 class="ml30">待审核</h2>
   </div>
   <h2 class="search_detail">
 <div class="container clear">
    	<ul class="demand_list">
    	  <li class="fl">
	    	<label class="fl">产品状态：</label>
	    	<select id="paramstatus">
	    	<option selected="selected" value="">---请选择---</option>
	    	<option value="0">已提交</option>
	    	<option value="1">暂存</option>
	    	</select>
	      </li> 
	    	<button type="button" onclick="query()" class="btn">查询</button>
	    	<button type="button" onclick="audit()" class="btn">审核</button>  	
    	</ul>
    	  <div class="clear"></div>
  </div>
     </h2>
   <div class="col-md-12 mt10 p0">
     <div class=" context table_box">
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
	            <tr><th class="w50 info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
	                <th class="info w80">序号</th>
	                <th class="info">产品参数名称</th>
	                <th class="info">产品状态</th>
	                <th class="info">创建时间</th>
	            </tr>
	        </thead>	
	        <c:forEach var="cate" items="${cate}" varStatus="vs">
	            <tr>
	            <td class="tc pointer"><input  onclick="check('${cate.id}')" type="checkbox" name="chkItem" value="${cate.id}"/></td>
	            <td class="w50 tc pointer" >${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
	            <td class="tc pointer" >${cate.name }</td>
	            <td class="tc pointer" ><c:choose>
						<c:when test="${cate.paramStatus=='0'}">
							已提交
						</c:when>
						<c:when test="${cate.paramStatus=='1'}">
							暂存
						</c:when>
					</c:choose></td>
	            <td class="tc pointer" ><fmt:formatDate value="${cate.createdAt}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	            </tr>
	        </c:forEach>
	</table>
   </div>

   </div>
      <div id="pagediv" align="right"></div>
   </div>
  </body>
</html>
