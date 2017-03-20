<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<title>投诉页面</title>
<script type="text/javascript">
	$(function() {
		laypage({
			cont : $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
			pages : "${info.pages}", //总页数
			skin : '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			skip : true, //是否开启跳页
			total : "${info.total}",
			startRow : "${info.startRow}",
			endRow : "${info.endRow}",
			groups : "${info.pages}" >= 5 ? 5 : "${info.pages}", //连续显示分页数
			curr : function() { //通过url获取当前页，也可以同上（pages）方式获取
				var page = location.search.match(/page=(\d+)/);
				return page ? page[1] : 1;
			}(),
			jump : function(e, first) { //触发分页后的回调
				if (!first) { //一定要加此判断，否则初始时会无限刷新
					location.href = "${pageContext.request.contextPath }/onlineComplaints/dealWith.do?page="
							+ e.curr;
				}
			}
		});
	})
   /** 全选全不选 */
	function selectAll(){
		 var checklist = document.getElementsByName ("info");
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
		 var checklist = document.getElementsByName ("info");
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
	/*立项*/
	function lixiang() {
		var id = [];
		$('input[name="info"]:checked').each(function() {
			id.push($(this).val());
		});
		if(id.length == 1) {
			window.location.href = "${pageContext.request.contextPath}/onlineComplaints/dealWith.do?id="+id+"&status=0";
		}  else if(id.length > 1) {
			layer.alert("只能选择一个未处理的信息", {
				offset: ['122px', '390px'],
				shade: 0.01
			});
		} else {
			layer.alert("请选择一条需要处理的信息", {
				offset: ['122px', '390px'],
				shade: 0.01
			});
		}
	}
	/**公布*/
	function gongbu() {
		var id = [];
		console.dir(1);
		$('input[name="info"]:checked').each(function() {
			id.push($(this).val());
		});
		console.dir(2);
		if(id.length == 1) {
			var status = $('input[name="info"]:checked').parent().parent().children("td").eq(6).text();
			if(status="立项处理"){
			window.location.href = "${pageContext.request.contextPath}/onlineComplaints/gongshi.do?id="+id+"&status=1";}
			else{
				layer.alert("只能选择立项处理过的数据", {
					offset: ['122px', '390px'],
					shade: 0.01
				});
			}
		} else if(id.length > 1) {
			layer.alert("只能选择一个信息公布", {
				offset: ['122px', '390px'],
				shade: 0.01
			});
		} else {
			layer.alert("请选择一条需要公布的信息", {
				offset: ['122px', '390px'],
				shade: 0.01
			});
		}
	}
	
	<!--
	$.ajax({
		url:"${pageContext.request.contextPath}/onlineComplaints/Test.do",
		type:"GET",
		data:{"id":id},
		success:function(data){
			if(data != "0"){
				alert("此信息已处理");
			}
			
		},
	});
	-->
</script>
</head>
<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="javascript:void(0)">首页</a></li>
				<li><a href="javascript:void(0)">业务监管</a></li>
				<li><a href="javascript:void(0)">网上投诉处理</a></li>
				<li class="active"><a href="javascript:void(0)">网上投诉</a></li>
				<li class="active"><a href="javascript:void(0)">投诉处理</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>

	<!-- 投诉列表页 -->
	<div class="container">
		<div class="headline-v2">
			<h2>投诉处理列表</h2>
			<div class="col-md-12 pl20 mt10">
	    	       <button class="btn" type="button" onclick="lixiang()">立项</button>
		           <button class="btn" type="button" onclick="gongbu()">公布</button>
            </div>
		</div>
		
   <div class="content table_box">
				
		<form action="" method="post" class="mb0">

			<div class="content table_box">
				
				<table class="table table-bordered table-condensed table-hover">
					<thead>
						<tr class="info">
							<th class="w50"><input id="checkAll" type="checkbox" onclick="check()" /></th>
							<th class="w50">序号</th>
							<th>投诉人名称</th>
							<th>投诉人类型</th>
							<th>投诉对象</th>
							<th width="35%">投诉事项</th>
							<th>处理情况</th>
						</tr>
					</thead>
					<tbody>
						<!-- 获取对象时list.被封装在list里面了complaint集合-  var就是下面的值从result里获取-->
						<c:forEach items="${info.list }" varStatus="vs" var="result">
							<!-- -ondealwith 的值里面要带‘’ -->
							<tr class="tc" >
								<!-- onclick="check"前面选择这个框的触发事件  value="${list.id}获取result集合里id的值 -->
								<td class="w50"><input onclick="check()" type="checkbox"    value="${result.id }" name="info"  /></td>
								<td class="w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
								<td>${result.name }</td>
								<td><c:if test="${result.type=='0'}">
								               单位
								     </c:if> <c:if test="${result.type=='1'}">
								               个人
								     </c:if></td>
								<td>${result.complaintObject }</td>
								<!-- -数据前台展示截取 -->
								<td>
                                  <input class="w230 border0" title="${result.complaintMatter }"  
					              <c:if test="${fn:length(result.complaintMatter) > 12 }">value=" ${fn:substring(result.complaintMatter, 0, 12)}..."</c:if>
					              <c:if test="${fn:length(result.complaintMatter) <= 12 }">value="${result.complaintMatter }"</c:if>
					                      />
                                </td>
								<td><c:if test="${result.status=='0'}">
								           未处理
								     </c:if> 
								     <c:if test="${result.status=='1'}">
								           已立项
								     </c:if> 
								     <c:if test="${result.status=='2'}">
								           已驳回
								     </c:if> 
								     <c:if test="${result.status=='3'}">
								           已公示
								     </c:if>
								 </td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</form>
		<div id="pageDiv" align="right"></div>
	</div>
</body>
</html>