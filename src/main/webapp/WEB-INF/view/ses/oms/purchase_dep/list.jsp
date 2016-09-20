<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>采购机构查询列表</title>

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
				<li class="active"><a href="#">采购机构管理</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<div class="container">
		<div class="headline-v2">
			<h2>采购机构管理列表</h2>
		</div>
	</div>
	<!-- 表格开始-->
	<div class="container">
		<form id="findform" action="${pageContext.request.contextPath}/purchaseManage/purchaseUnitList。do" method="post">
			<div class="topFormArea">
				<p>
					<span>上级监管部门：</span><select name="monitor_dep_name" id="monitor_dep_id"></select>
					<span>名称：</span><input name="name" id="name" value="${purchaseDep.name}"></input>
					<span>等级：</span><input name="levelDep" id="levelDep" value="${purchaseDep.levelDep}"></input>
					<a  id="findbtn" class="searchBtn">查询</a>
				</p>
				<br/>
				<div class="clearfloat"></div>
			</div>
		</form>
	</div>

	<div class="container margin-top-5 list">
		<div class="content padding-left-25 padding-right-25 padding-top-5">
				<div class="col-md-88">
					<button class="btn btn-windows edit fr" type="button" onclick="over()">资质终止</button>
					<button class="btn btn-windows edit fr" type="button" onclick="stash()">资质暂停</button>
					<button class="btn btn-windows add fr" type="button" onclick="purchaseManage()">采购人员管理</button>
					<button class="btn btn-windows git fr" type="button" onclick="show()">查看</button>
					<button class="btn btn-windows delete fr" type="button" onclick="del();">删除</button>
					<button class="btn btn-windows edit fr" type="button" onclick="edit()">修改</button>
					<button class="btn btn-windows add fr" type="button" onclick="add()">新增</button>
					<input id="parentid" type="hidden"/>
			    </div>
				<table class="table table-bordered table-condensed">
					<thead>
						<tr>
							<th class="info w30"><input id="checkAll" type="checkbox"
								onclick="selectAll()" /></th>
							<th class="info w50">序号</th>
							<th class="info">采购机构名称</th>
							<th class="info">邮编</th>
							<th class="info">单位地址</th>
							<th class="info">采购业务范围</th>
							<th class="info">采购资质编号</th>
							<th class="info">采购业务等级</th>
							<th class="info">采购资质范围</th>
						</tr>
					</thead>
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
						</tr>
					</c:forEach>
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
