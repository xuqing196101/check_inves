<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
	<title>定型产品列表页面</title>

	<jsp:include page="../../../ses/bms/page_style/backend_common.jsp"></jsp:include>	
	<script type="text/javascript">
	/* 分页 */
	$(function() {
	    laypage({
	      cont : $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
	      pages : "${info.pages}", //总页数
	      skin : '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
	      skip : true, //是否开启跳页
	      total : "${info.total}",
	      startRow : "${info.startRow}",
	      endRow : "${info.endRow}",
	      groups : "${info.pages}" >= 3 ? 3 : "${info.pages}", //连续显示分页数
	      curr : function() { //通过url获取当前页，也可以同上（pages）方式获取
	        return "${info.pageNum}";
	      }(),
	      jump : function(e, first) { //触发分页后的回调
        	if(!first){ //一定要加此判断，否则初始时会无限刷新
	      		location.href = "${pageContext.request.contextPath }/product/list.do?page=" + e.curr;
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
	
	/* 删除 */
	function del(){
		var id = [];
		$('input[name="chkItem"]:checked').each(function() {
			id.push($(this).val());
		});
		var ids = id.toString();
		if(id.length > 0) {
			layer.confirm('您确定要删除吗?', {
				title: '提示',
				offset: ['222px', '360px'],
				shade: 0.01
			}, function(index) {
				layer.close(index);
				$.ajax({
					url: "${pageContext.request.contextPath }/product/delete.html",
					type: "post",
					data: {
						oBProductids: ids
					},
					success: function() {
						window.location.href = "${pageContext.request.contextPath }/product/list.html";
					},
					error: function() {

					}
				});
			});
		} else {
			layer.alert("请选择要删除的版块", {
				offset: ['222px', '390px'],
				shade: 0.01
			});
		}
	}
	
	/* 修改 */
	function edit() {
		var id = [];
		$('input[name="chkItem"]:checked').each(function() {
			id.push($(this).val());
		});
		if(id.length == 1) {
			window.location.href = "${pageContext.request.contextPath }/product/tiaozhuan.html?proid=" + id + "&&type=1";
		} else if(id.length > 1) {
			layer.alert("只能选择一个", {
				offset: ['222px', '390px'],
				shade: 0.01
			});
		} else {
			layer.alert("请选择需要修改的版块", {
				offset: ['222px', '390px'],
				shade: 0.01
			});
		}
	}
	//重置
	function resetQuery() {
		$("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
		window.location.href = "${pageContext.request.contextPath}/product/list.html";
	}
	
	</script>
</head>
<body>

<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">保障作业</a></li><li><a href="javascript:void(0)">定型产品竞价</a></li>
		   <li class="active"><a href="javascript:void(0)">定型产品管理</a></li>
		   </ul>
        <div class="clear"></div>
      </div>
    </div>
    
<!-- 定型产品列表页面开始 -->
	<div class="container">
	 <div class="headline-v2">
		<h2>定型产品列表</h2>
	 </div>
    <div class="search_detail">
       <form action="${pageContext.request.contextPath}/product/list.html" method="post" class="mb0" id = "form1">
    	<ul class="demand_list">
    	  <li>
	    	<label class="fl">产品代码：</label>
			<input type="text" id="topic" class="" name = "code" value="${productExample.code }"/>
	      </li>
    	  <li>
	    	<label class="fl">产品状态：</label>
	    	  <select class="w178" name="status">
	    	    <option></option>
	    	    <option value="1" <c:if test="${'1'==productExample.status}">selected="selected"</c:if>>暂存</option>
	    	    <option value="2" <c:if test="${'2'==productExample.status}">selected="selected"</c:if>>发布</option>
	    	  </select>
	      </li>
    	  <li>
	    	<label class="fl">产品名称：</label>
			<input type="text" id="topic" class="" name = "name" value="${productExample.name }"/>
	      </li> 
	    	<input class="btn fl mt1" type="submit" value="查询" /> 
	    	<input class="btn fl mt1" type="button" onclick="resetQuery()" value="重置"/>	
    	</ul>
    	  <div class="clear"></div>
       </form>
     </div>
     
<!-- 表格开始 -->
	<div class="col-md-12 pl20 mt10">
		<button class="btn btn-windows apply" type="button" onclick="window.location.href = '${pageContext.request.contextPath }/product/tiaozhuan.html?type=2'">发布定型产品</button>
		<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
		<button class="btn btn-windows delete" type="button" onclick="del()">删除</button>
	</div>   
	<div class="content table_box">
	
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="w30 info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
		  <th class="w50 info">序号</th>
		  <th class="info" width="17%">产品代码</th>
		  <th class="info" width="20%">产品名称</th>
		  <th class="info" width="22%">大类</th>
		  <th class="info" width="22%">小类</th>
		  <th class="info">合格供应商数量</th>
		</tr>
		</thead>
		<c:forEach items="${info.list }" var="product" varStatus="vs">
		<tr>
		  <td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="${product.id }" /></td>
		  <td class="tc w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
		  <td>${product.code}</td>
		  <td><a href="javascript:void(0)">${product.name}</a></td>
		  <td class="tl">${product.categoryParent.name }</td>
		  <td class="tl">${product.category.name }</td>
		  <td class="tc"><a href = "${pageContext.request.contextPath}/product/supplier.html?prodid=${product.id }">
		  	<c:forEach items="${numlist }" var="num">
		  		<c:if test="${num.productId == product.id }">${num.nCount }</c:if>
		  	</c:forEach>
		  </a></td>
		</tr>
		</c:forEach>
		
	</table>
   </div>
      <div id="pagediv" align="right"></div>
   </div>

</body>
</html>