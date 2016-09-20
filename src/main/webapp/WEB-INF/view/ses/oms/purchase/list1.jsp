<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>采购机构人员列表</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="<%=basePath%>public/oms/css/consume.css"  rel="stylesheet">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->


</head>
<script src="<%=basePath%>public/layer/layer.js"></script>
<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
<script type="text/javascript">
  /* $(function(){
	  laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${listStationMessage.pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    skip: true, //是否开启跳页
		    groups: "${listStationMessage.pages}">=3?3:"${listStationMessage.pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
// 		        var page = location.search.match(/page=(\d+)/);
		        return "${listStationMessage.pageNum}";
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		        	$("#pages").val(e.curr);
		        	$("form:first").submit();
		        }
		    }
		});
  }); */
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
  		window.location.href="<%=basePath%>StationMessage/showStationMessage.do?id="+id+"&&type='view'";
  	}
    function edit(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			window.location.href="<%=basePath%>StationMessage/showStationMessage.do?id="+id+"&&type='edit'";
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选择需要修改的用户",{offset: ['222px', '390px'], shade:0.01});
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
				window.location.href="<%=basePath%>StationMessage/deleteSoftSMIsDelete.do?ids="+ids;
			});
		}else{
			layer.alert("请选择要删除的用户",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    function add(){
    	window.location.href="<%=basePath%>purchaseManage/addPurchaseDep.do";
    }
    function show(id){
    	window.location.href="<%=basePath%>StationMessage/showStationMessage.do?id="+id+"&&type=view";
    }
  </script>
<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="#"> 首页</a></li>
				<li><a href="#">支撑系统</a></li>
				<li><a href="#">后台管理</a></li>
				<li class="active"><a href="#">采购人管理</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<div class="container">
		<div class="headline-v2">
			<h2>采购机构人员列表</h2>
			<div class="mt15">
				<form id="query_form_id" class="mt0"
					action="${pageContext.request.contextPath}/purchase/list.do"
					method="post">
					<input id="page_id" type="hidden" name="page" value="1"> 
					<span>采购人姓名：</span>
					<input type="text" class="query_input ww12" name="depName"
						value="${requestScope.depName}">
					<span class="ml10">上级采购机构：</span>
					<select class="select_opt ww12" name="status">
						<option value="-1">全部</option>
						<option value="0">机构 1</option>
						<option value="1">机构 1</option>
						<option value="2">机构 1</option>
					</select>
					<span class="ml10">人员类型：</span>
					<select class="select_opt ww12" name="status">
						<option value="-1">全部</option>
						<option value="0">军人</option>
						<option value="1">文职</option>
						<option value="2">职工</option>
						<option value="3">战士</option>
					</select> <input id="sub" type="submit" value="查询" class="input_btn orange" />
					<input id="sub" type="button" value="重置" class="input_btn orange"
						onclick="resetForm()" />
				</form>
			</div>
		</div>
	</div>
	<!-- 表格开始-->
	<div class="container">
		<div class="col-md-8">
			<button class="btn btn-windows add" type="button" onclick="add()">新增</button>
			<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
			<button class="btn btn-windows delete" type="button" onclick="del();">删除</button>
		</div>
	</div>

	<div class="container margin-top-5 list">
		<div class="content padding-left-25 padding-right-25 padding-top-5">
				<table class="table table-bordered table-condensed">
					<thead>
						<tr>
							<th class="info w30"><input id="checkAll" type="checkbox"
								onclick="selectAll()" /></th>
							<th class="info w50">序号</th>
							<th class="info">姓名</th>
							<th class="info">所属采购机构</th>
							<th class="info">类型</th>
							<th class="info">性别</th>
							<th class="info">年龄</th>
							<th class="info">职务</th>
							<th class="info">职称</th>
							<th class="info">等级</th>
							<th class="info">学历</th>
							<th class="info">电话</th>
							<th class="info">资质证书类型</th>
							<th class="info">证书编号</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${purchaseDepList}" var="p"
							varStatus="vs">
							<tr class="cursor">
								<!-- 选择框 -->
								<td onclick="null" class="tc"><input onclick="check()"
									type="checkbox" name="chkItem" value="${p.id}" /></td>
								<!-- 序号 -->
								<td class="tc" onclick="show('${p.id}');">${vs.index+1}</td>
								<!-- 标题 -->
								<td class="tc" onclick="show('${p.id}');">${p.name}</td>
								<!-- 内容 -->
								<td class="tc" onclick="show('${p.id}');">${p.postCode}</td>
								<!-- 创建人-->
								<td class="tc" onclick="show('${p.id}');">${p.address}</td>
								<!-- 是否发布 -->
								<td class="tc" onclick="show('${p.id}');">${p.businessRange}</td>
									<!-- 是否发布 -->
								<td class="tc" onclick="show('${p.id}');">${p.quaCode}</td>
									<!-- 是否发布 -->
								<td class="tc" onclick="show('${p.id}');">${p.quaLevel}</td>
									<!-- 是否发布 -->
								<td class="tc" onclick="show('${p.id}');">${p.quaRange}</td>
									<!-- 创建人-->
								<td class="tc" onclick="show('${p.id}');">${p.address}</td>
								<!-- 是否发布 -->
								<td class="tc" onclick="show('${p.id}');">${p.businessRange}</td>
									<!-- 是否发布 -->
								<td class="tc" onclick="show('${p.id}');">${p.quaCode}</td>
									<!-- 是否发布 -->
								<td class="tc" onclick="show('${p.id}');">${p.quaLevel}</td>
									<!-- 是否发布 -->
								<td class="tc" onclick="show('${p.id}');">${p.quaRange}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
		<!-- <div id="pagediv" align="right"></div> -->
		<c:if test="${empty  purchaseDepList}">
			<div class="noData">
				<p>此条件下尚无数据，请重新选择！</p>
			</div>
		</c:if>
		<p class="page">${pagesql}</p>
		<div class="clearfloat"></div>
	</div>
</body>
</html>
