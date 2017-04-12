<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
	      		location.href = "${pageContext.request.contextPath }/onlineComplaints/complaints.do?page=" + e.curr;
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
					url: "${pageContext.request.contextPath}/onlineComplaints/delete.do",
					type: "post",
					data: {
						ids: ids
					},
					success: function() {
						window.location.href = "${pageContext.request.contextPath}/onlineComplaints/complaints.html";
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
			window.location.href = "${pageContext.request.contextPath }/onlineComplaints/addoredit.html?type=2&&id="+id;
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
	
	//新增
	function add(){
		window.location.href = "${pageContext.request.contextPath }/onlineComplaints/addoredit.html?type=1";
	}
	//重置
	function resetQuery() {
		$("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
		window.location.href = "${pageContext.request.contextPath }/onlineComplaints/complaints.html";
	}
	</script>
</head>
<body>
<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">业务监管</a></li><li><a href="javascript:void(0)">网上投诉</a></li>
		   </ul>
        <div class="clear"></div>
      </div>
    </div>
    
    <!-- 网上投诉列表页面开始 -->
	<div class="container">
	 <div class="headline-v2">
		<h2>投诉记录列表</h2>
	 </div>
    <div class="search_detail">
       <form action="${pageContext.request.contextPath }/onlineComplaints/complaints.html" method="post" class="mb0" id = "form1">
    	<ul class="demand_list">
    	  <li>
	    	<label class="fl">投诉对象：</label>
			<input type="text" id="" class="" name = "complaintObject" value="${complaint.complaintObject }"/>
	      </li>
	      <input class="btn fl mt1" type="submit" value="查询" /> 
	      <input class="btn fl mt1" type="button" onclick="resetQuery()" value="重置"/>	
    	</ul>
    	  <div class="clear"></div>
       </form>
     </div>
     
<!-- 表格开始 -->
	<div class="col-md-12 pl20 mt10">
		<button class="btn btn-windows add" type="button" onclick="add()">新增</button>
		<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
		<button class="btn btn-windows delete" type="button" onclick="del()">删除</button>
	</div>   
	<div class="content table_box">
	<table class="table table-bordered table-condensed table-hover">
		<thead>
			<tr class="info">
				<th class="w30 info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
				<th class="w50">序号</th>
				<th>投诉人名称</th>
				<th>投诉人类型</th>
				<th>投诉对象</th>
				<th width="25%">投诉事项</th>
				<th>处理情况</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${info.list }" var="complaint" varStatus="vs">
			<tr class="tc">
				<td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="${complaint.id }" /></td>
				<td class="w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
				<td class="tc">${complaint.name }</td>
				<td class="tc">
					<c:if test="${complaint.type == 1 }">个人</c:if>
					<c:if test="${complaint.type == 0 }">单位</c:if>
				</td>
				<td class="tc">${complaint.complaintObject }</td>
				<td class="tl" title="${complaint.complaintMatter }">  
					<c:if test="${fn:length(complaint.complaintMatter) > 12 }">${fn:substring(complaint.complaintMatter, 0, 12)}...</c:if>
					<c:if test="${fn:length(complaint.complaintMatter) <= 12 }">${complaint.complaintMatter }</c:if>
				</td>
				<td class="tc">
					<c:if test="${complaint.status == 0 }">未处理</c:if>
					<c:if test="${complaint.status == 1 }">已立项</c:if>
					<c:if test="${complaint.status == 2 }">已驳回</c:if>
					<c:if test="${complaint.status == 3 }">已公示</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
   </div>
      <div id="pagediv" align="right"></div>
   </div>
    
</body>
</html>