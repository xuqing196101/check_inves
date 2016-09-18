<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>站内消息</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="<%=basePath%>public/supplier/css/supplieragents.css" media="screen" rel="stylesheet">
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
    	window.location.href="<%=basePath%>StationMessage/showInsertSM.do";
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
				<li class="active"><a href="#">模版管理</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<div class="container">
		<div class="headline-v2">
			<h2>站内消息管理</h2>
		</div>
	</div>
	<!-- 表格开始-->
	<div class="container">
		<div class="col-md-8">
			<button class="btn btn-windows add" type="button" onclick="add()">新增</button>
			<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
			<button class="btn btn-windows delete" type="button" onclick="del();">删除</button>
		</div>
		<div class="col-md-4 ">
			<div class="search-block-v2">
				<div class="">
					<form accept-charset="UTF-8" action="<%=basePath%>StationMessage/listStationMessage.do" method="post">
						<div style="display: none">
							<input name="utf8" value="✓" type="hidden">
								<input type="hidden" id="pages" name="page" value="">
						</div>
						<input id="t" name="t" value="search_products" type="hidden">
						<div class="col-md-12 pull-right">
							<div class="input-group">
								<input class="form-control bgnone h37 p0_10" id="k" name="title"
									placeholder="" type="text"> <span
									class="input-group-btn"> <input class="btn-u"
									name="commit" value="搜索" type="submit">
								</span>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<div class="container margin-top-5">
		<div class="content padding-left-25 padding-right-25 padding-top-5">
		
				<table class="table table-bordered table-condensed">
					<thead>
						<tr>
							<th class="info w30"><input id="checkAll" type="checkbox"
								onclick="selectAll()" /></th>
							<th class="info w50">序号</th>
							<th class="info">标题</th>
							<th class="info">内容</th>
							<th class="info">创建人</th>
							<th class="info">是否发布</th>
						</tr>
					</thead>
					<c:forEach items="${listStationMessage.list}" var="listsm"
						varStatus="vs">
						<tr class="cursor">
							<!-- 选择框 -->
							<td onclick="null" class="tc"><input onclick="check()"
								type="checkbox" name="chkItem" value="${listsm.id}" /></td>
							<!-- 序号 -->
							<td class="tc" onclick="show('${listsm.id}');">${vs.index+1}</td>
							<!-- 标题 -->
							<td class="tc" onclick="show('${listsm.id}');">${listsm.title}</td>
							<!-- 内容 -->
							<td class="tc" onclick="show('${listsm.id}');">${listsm.content}</td>
							<!-- 创建人-->
							<td class="tc" onclick="show('${listsm.id}');">${llitsm.userName}</td>
							<!-- 是否发布 -->
							<td class="tc"><c:choose>
									<c:when test="${listsm.isPublish==0}">
										<a
											href="StationMessage/updateSMIsIssuance.do?id=${listsm.id}&&isIssuance=1">发布</a>
									</c:when>
									<c:otherwise>
										<a
											href="StationMessage/updateSMIsIssuance.do?id=${listsm.id}&&isIssuance=0">撤回</a>
									</c:otherwise>
								</c:choose></td>
						</tr>
					</c:forEach>
				</table>
		</div>
		<div id="pagediv" align="right"></div>
	</div>
</body>
</html>
