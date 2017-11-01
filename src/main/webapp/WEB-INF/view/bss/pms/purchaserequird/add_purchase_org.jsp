<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<script type="text/javascript">
    /*分页  */
    
    $(function(){
	  laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${list.pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    total: "${list.total}",
		    startRow: "${list.startRow}",
		    endRow: "${list.endRow}",
		    skip: true, //是否开启跳页
		    groups: "${list.pages}">=3?3:"${list.pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
//			        var page = location.search.match(/page=(\d+)/);
//			        return page ? page[1] : 1;
				return "${list.pageNum}";
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		            if(!first){ //一定要加此判断，否则初始时会无限刷新
		        	$("#page").val(e.curr);
		        	  $("#form1").submit();
		        	
		       <%--  location.href = '${pageContext.request.contextPath}/purchaser/list.do?page='+e.curr; --%>
		        }  
		    }
		});
  });
  /* $(function() {
    laypage({
      cont : $("#pagediv"), 
      pages : "${list.pages}", 
      skin : '#2c9fA6', 
      skip : true, 
      total : "${list.total}",
      startRow : "${list.startRow}",
      endRow : "${list.endRow}",
      groups : "${list.pages}">=3?3:"${list.pages}", //连续显示分页数
      curr : function() { 
    	  return "${list.pageNum}";
      }(),
      jump : function(e, first) { //触发分页后的回调
        if (!first) { //一定要加此判断，否则初始时会无限刷新
          $("#page").val(e.curr);
          $("#form1").submit();
        }
      }
    });
  }); */
  function fanhui(){
  	window.location.href="${pageContext.request.contextPath}/purchaseManage/add.html"
  }
  function submit(){
  	$("#form1").submit();
  }
function resets(){
	$("#name").val('');
}

   
	function selectAll(){
		if ($("#allId").prop("checked")) {  
            $("input[name=items]").each(function() {  
                $(this).prop("checked", true);  
            });  
        } else {  
            $("input[name=items]").each(function() {  
                $(this).prop("checked", false);  
            });  
        }   
	}
	
	/** 添加 */
	function add(){
		
		 var index = parent.layer.getFrameIndex(window.name);
	 
		var id=$("input[name='items']:checked").val();
		$("#manage_id").val(id);
		
		$.ajax({
			url: "${pageContext.request.contextPath}/purchaser/submanage.do",
			type: "post",
			data:$("#sub_form").serialize(),
			success: function(result) {
				parent.location.reload(); // 父页面刷新
				parent.layer.close(index);
			}
		});
		
		
	//	$("#sub_form").submit();
     //  parent.layer.close(index); //执行关闭
	}
	
	/** 添加table */
	function addTables(index,id,name){
			parent.$("#tab").append("<tr id="+id+" align='center'>"
					+"<td><input type='checkbox' name='selectedItem' onclick='checkAdd()' value="+id+" /></td>"     
                    +"<td>"+index+"</td>"
                    +"<td class='tl pl20'>"+name+"</td>"
                    +"</tr>"); 
		}
		
	function closess(){
	   var index = parent.layer.getFrameIndex(window.name);
	   parent.layer.close(index);
	}
</script>
</head>
<body>
  <div class="container">
	<h2 class="search_detail">
	  <form  class="mb0" action="${pageContext.request.contextPath}/purchaser/submit.html" method="post" id="form1">
		<input type="hidden" name="page" id="page"> 
		<input type="hidden" name="flag" value="0">
		<input type="hidden" name="planNo" value="${uniqueId}">
		<input type="hidden" name="typeName" value="${orgnization.typeName }" />
		<ul class="demand_list">
	      <li>
	        <label class="fl">采购管理部门名称：</label>
	        <span><input type="text" name="name" id="name" value="${name}"></span>
	      </li>
	    </ul>
	        <button class="btn fl" onclick="submit()" type="button">查询</button>
          <button class="btn fl" onclick="resets()" type="button"> 重置</button>
	    <div class="clear"></div>
	  </form>
	</h2>
	<div class="content table_box">
      <table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		  <tr>
	        <th class="info w30"><input type="checkbox" onclick="selectAll();" id="allId" alt="全选"></th>
			<th class="info w50">序号</th>
			<th class="info">采购管理部门名称</th>
		  </tr>
		</thead>
		<tbody>
		  <c:forEach items="${list.list}" var="p" varStatus="vs">
			<tr class="cursor">
		      <td class="tc"><input type="radio" name="items"  value="${p.orgnization.id}" /></td>
			  <td class="tc">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
			  <td class="tl pl20">${p.orgnization.name}</td>
			</tr>
		  </c:forEach>
		</tbody>
	  </table>
	  <div id="pagediv" align="right"></div>
	   <div class="col-md-12 col-sm-12 col-xs-12 tc">
    <button class="btn btn-windows git" type="button" onclick="add()">提交</button>
    <button class="btn btn-windows cancel" type="button" onclick="closess();">取消</button>
  </div>
	</div>
  </div>
  <div style="display:none;">
  <form id="sub_form" action="${pageContext.request.contextPath}/purchaser/submanage.html" method="post">
  	<input type="hidden" name="uniqueId" id="uniqueId" value="${uniqueId }">
    <input type="hidden" name="managementId" id="manage_id" >
  </form>
  </div>
  
</body>
</html>
