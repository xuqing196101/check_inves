<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript">
    /*分页  */
  $(function() {
    laypage({
      cont : $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
      pages : "${list.pages}", //总页数
      skin : '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
      skip : true, //是否开启跳页
      total : "${list.total}",
      startRow : "${list.startRow}",
      endRow : "${list.endRow}",
      groups : "${list.pages}" >= 3 ? 3 : "${info.pages}", //连续显示分页数
      curr : function() { //通过url获取当前页，也可以同上（pages）方式获取
        return "${list.pageNum}";
      }(),
      jump : function(e, first) { //触发分页后的回调
        if (!first) { //一定要加此判断，否则初始时会无限刷新
          $("#page").val(e.curr);
          $("#form1").submit();
        }
      }
    });
  });
  function fanhui(){
  	window.location.href="${pageContext.request.contextPath}/purchaseManage/add.html"
  }
  function submit(){
  	$("#form1").submit();
  }
function chongzhi(){
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
		
		var parentTabArray = [];
		var parentIndexArray = [];
		parent.$("input[name='selectedItem']").each(function(){
			parentTabArray.push($(this).val());
			parentIndexArray.push($(this).parents('tr').find('td').eq(1).text());
		});
		var parentIndex = 0;
		if (parentIndexArray.length > 0){
			parentIndex = Math.max.apply(null, parentIndexArray);
		}
		
		var count = 1;
		if (parentIndex != 0){
			count = count + parentIndex;
		}
		$("input[name='items']:checked").each(function(){
			 var id = $(this).val();
			 var name = $(this).parents('tr').find('td').eq(2).text();
			 if ($.inArray(id,parentTabArray) < 0){
				 addTables(count,id,name);
				 count ++;
			 }
		});
       parent.layer.close(index); //执行关闭
	}
	
	/** 添加table */
	function addTables(index,id,name){
			parent.$("#tab").append("<tr id="+id+" align='center'>"
					+"<td><input type='checkbox' name='selectedItem' onclick='checkAdd()' value="+id+" /></td>"     
                    +"<td>"+index+"</td>"
                    +"<td>"+name+"</td>"
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
	  <form  class="mb0" action="${pageContext.request.contextPath}/purchaseManage/addPurchaseOrg.html" method="post" id="form1">
		<input type="hidden" name="page" id="page"> 
		<input type="hidden" name="flag" value="0">
		<input type="hidden" name="typeName" value="${orgnization.typeName }" />
		<ul class="demand_list">
	      <li>
	        <label class="fl">机构名称：</label>
	        <span><input type="text" name="name" id="name" value="${orgnization.name}"></span>
	      </li>
	      <button class="btn" onclick="submit()" type="button">查询</button>
          <button class="btn" onclick="chongzhi()" type="button"> 重置</button>
	    </ul>
	    <div class="clear"></div>
	  </form>
	</h2>
	<div class="content table_box">
      <table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		  <tr>
	        <th class="info w30"><input type="checkbox" onclick="selectAll();" id="allId" alt="全选"></th>
			<th class="info w50">序号</th>
			<th class="info">机构名称</th>
			<th class="info">类型</th>
		  </tr>
		</thead>
		<tbody>
		  <c:forEach items="${orgnizationList}" var="p" varStatus="vs">
			<tr class="cursor">
		      <td class="tc"><input type="checkbox" name="items"  value="${p.id}" /></td>
			  <td class="tc">${vs.index+1}</td>
			  <td class="tc">${p.name}</td>
			  <td class="tc">
				<c:choose>
				  <c:when test="${p.typeName=='0'}">管理部门</c:when>
				  <c:when test="${p.typeName=='1'}">采购机构</c:when>
				  <c:when test="${p.typeName=='2'}">管理部门</c:when>
				</c:choose>
			  </td>
			</tr>
		  </c:forEach>
		</tbody>
	  </table>
	  <div id="pagediv" align="right"></div>
	   <div class="col-md-12 col-sm-12 col-xs-12 tc">
    <button class="btn btn-windows add" type="button" onclick="add()">添加</button>
    <button class="btn btn-windows back" type="button" onclick="closess();">取消</button>
  </div>
	</div>
  </div>
</body>
</html>
