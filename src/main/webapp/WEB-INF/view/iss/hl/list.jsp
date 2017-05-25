<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/WEB-INF/view/common.jsp" %>
	<title>热线电话列表页面</title>
	<jsp:include page="../../ses/bms/page_style/backend_common.jsp"></jsp:include>	
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
	      		location.href = "${pageContext.request.contextPath }/serviceHotline/list.do?page=" + e.curr;
	        }
	      }
	    });
	  });
	
	/** 全选全不选 */
	function selectAll(){
		var checklist = document.getElementsByName ("chkItem");
		var checkAll = document.getElementById("checkAll");
		if(checkAll.checked){
			for(var i=0;i<checklist.length;i++){
				checklist[i].checked = true;
			} 
		}else{
			for(var j=0;j<checklist.length;j++){
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
	
	//重置
	function resetQuery() {
		$("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
		window.location.href = "${pageContext.request.contextPath }/serviceHotline/list.html";
	}

	//修改
	function edit() {
		var orgTyp = "${authType}";
		if(orgTyp != '4'){
			layer.msg("只有资源服务中心才能操作");
			return;
		}
		var id = [];
		$('input[name="chkItem"]:checked').each(function() {
			id.push($(this).val());
		});
		if(id.length == 1) {
			window.location.href = "${pageContext.request.contextPath }/serviceHotline/addoredit.html?type=2&&id="+id;
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
		var orgTyp = "${authType}";
		if(orgTyp != '4'){
			layer.msg("只有资源服务中心才能操作");
			return;
		}
		window.location.href = "${pageContext.request.contextPath }/serviceHotline/addoredit.html?type=1";
	}
	

	//删除
	function del(){
		var orgTyp = "${authType}";
		if(orgTyp != '4'){
			layer.msg("只有资源服务中心才能操作");
			return;
		}
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
					url: "${pageContext.request.contextPath}/serviceHotline/delete.do",
					type: "post",
					data: {
						ids: ids
					},
					success: function() {
						window.location.href = "${pageContext.request.contextPath}/serviceHotline/list.html";
					},
					error: function() {
						layer.msg("删除失败");
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
	
	
	</script>
</head>
<body>
<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">信息服务</a></li><li><a href="javascript:void(0)">热线电话</a></li>
		   </ul>
        <div class="clear"></div>
      </div>
    </div>
    
    <!-- 热线电话列表页面开始 -->
	<div class="container">
	 <div class="headline-v2">
		<h2>热线电话列表</h2>
	 </div>
    <div class="search_detail">
    	<form action="${pageContext.request.contextPath }/serviceHotline/list.html" method="post" class="mb0" id = "form1">
    	<ul class="demand_list">
    		<li>
	    		<label class="fl">服务内容：</label>
					<input type="text" id="" class="" name = "servicecontent" value="${serviceHotline.servicecontent }"/>
	      </li>
	      <input class="btn fl mt1" type="submit" value="查询" /> 
	      <input class="btn fl mt1" type="button" onclick="resetQuery()" value="重置"/>	
    	</ul>
    	  <div class="clear"></div>
       </form>
     </div>
     
<!-- 表格开始 -->
	<div class="col-md-12 pl20 mt10">
		<button class="btn btn-windows add" type="button" onclick="add()">添加</button>
		<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
		<button class="btn btn-windows delete" type="button" onclick="del()">删除</button>
	</div>
	<div class="content table_box">
	<table class="table table-bordered table-condensed table-hover">
		<thead>
			<tr class="info">
				<th class="w30 info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
				<th class="w50">序号</th>
				<th width="">服务内容</th>
				<th width="">联系电话</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${info.list }" var="hotline" varStatus="vs">
			<tr class="tc">
				<td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="${hotline.id }" /></td>
				<td class="w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
				<td class="tl pl20">${hotline.servicecontent }</td>
				<td class="tc">${hotline.contactphonenumber }</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
   </div>
      <div id="pagediv" align="right"></div>
   </div>
</body>
</html>